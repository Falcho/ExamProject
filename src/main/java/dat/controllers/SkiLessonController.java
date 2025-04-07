package dat.controllers;

import dat.config.HibernateConfig;
import dat.dao.SkiLessonDAO;

import dat.dto.SkiLessonDTO;
import dat.entities.SkiLesson;
import dat.entities.Instructor;
import dat.enums.Level;
import dat.exceptions.ApiException;
import dat.utils.Populator;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SkiLessonController implements IController
{
    private final SkiLessonDAO dao;
    private static final Logger logger = LoggerFactory.getLogger(SkiLessonController.class);
    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public SkiLessonController(EntityManagerFactory emf)
    {
        dao = new SkiLessonDAO(emf);
    }

    public SkiLessonController(SkiLessonDAO dao)
    {
        this.dao = dao;
    }

    @Override
    public void getAll(Context ctx)
    {
        try
        {
            ctx.json(dao.getAll(SkiLesson.class));
        } catch (Exception ex)
        {
            logger.error("Error finding all trips", ex);
            throw new ApiException(404, "No entities found", ex);
        }
    }

    @Override
    public void getById(Context ctx)
    {
        try
        {
            //long id = Long.parseLong(ctx.pathParam("id"));
            Long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            SkiLessonDTO foundEntity = new SkiLessonDTO(dao.getById(SkiLesson.class, id));
            ctx.json(foundEntity);

        } catch (Exception ex)
        {
            logger.error("Error finding SkiLesson from ID", ex);
            throw new ApiException(404, "No entity with that id found", ex);
        }
    }

    @Override
    public void create(Context ctx)
    {
        try
        {
            SkiLessonDTO incomingTest = ctx.bodyAsClass(SkiLessonDTO.class);
            SkiLesson entity = new SkiLesson(incomingTest);
            SkiLesson createdEntity = dao.create(entity);
            ctx.json(new SkiLessonDTO(createdEntity));
        } catch (Exception ex)
        {
            logger.error("Error creating SkiLesson", ex);
            throw new ApiException(400, "Error creating SkiLesson", ex);
        }
    }

    public void addInstructor(Context ctx)
    {
        try
        {
            Long tripId = ctx.pathParamAsClass("lessonId", Long.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            SkiLesson skiLesson = dao.getById(SkiLesson.class, tripId);
            Long instructorId = ctx.pathParamAsClass("instructorId", Long.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            Instructor instructor = dao.getById(Instructor.class, instructorId);
            skiLesson.addInstructor(instructor);
            SkiLesson updatedSkiLesson = dao.update(skiLesson);
            ctx.json(new SkiLessonDTO(updatedSkiLesson));
        } catch (Exception ex)
        {
            logger.error("Error adding instructor to SkiLesson", ex);
            throw new ApiException(400, "Error adding instructor to SkiLesson", ex);
        }
    }

    public void update(Context ctx)
    {
        try
        {
            Long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            SkiLessonDTO incomingEntity = ctx.bodyAsClass(SkiLessonDTO.class);
            SkiLesson skiLessonToUpdate = dao.getById(SkiLesson.class, id);
            if (incomingEntity.getName() != null)
            {
                skiLessonToUpdate.setName(incomingEntity.getName());
            }
            if (incomingEntity.getPrice() != null)
            {
                skiLessonToUpdate.setPrice(incomingEntity.getPrice());
            }
            if (incomingEntity.getStartPosition() != null)
            {
                skiLessonToUpdate.setStartPosition(incomingEntity.getStartPosition());
            }
            if (incomingEntity.getStartTime() != null)
            {
                skiLessonToUpdate.setStartTime(incomingEntity.getStartTime());
            }
            if (incomingEntity.getEndTime() != null)
            {
                skiLessonToUpdate.setEndTime(incomingEntity.getEndTime());
            }
            if (incomingEntity.getLevel() != null)
            {
                skiLessonToUpdate.setLevel(incomingEntity.getLevel());
            }
            SkiLesson updatedEntity = dao.update(skiLessonToUpdate);
            SkiLessonDTO returnedEntity = new SkiLessonDTO(updatedEntity);
            ctx.json(returnedEntity);
        } catch (Exception ex)
        {
            logger.error("Error updating SkiLesson", ex);
            throw new ApiException(400, "Error updating SkiLesson", ex);
        }
    }

    public void delete(Context ctx)
    {
        try
        {
            //Long id = Long.parseLong(ctx.pathParam("id"));
            Long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            dao.delete(SkiLesson.class, id);
            ctx.status(204);
        } catch (Exception ex)
        {
            logger.error("Error deleting SkiLesson", ex);
            throw new ApiException(400, "Error deleting SkiLesson", ex);
        }
    }

    public void filterByLevel(Context ctx)
    {
        try
        {
            Level level = Level.valueOf(ctx.queryParam("level"));
            if (level == null)
            {
                throw new BadRequestResponse("Level query parameter is required");
            }
            List<SkiLesson> filteredSkiLessons = dao.filterByLevel(level);
            List<SkiLessonDTO> skiLessonDTOS = filteredSkiLessons.stream()
                    .map(SkiLessonDTO::new)
                    .collect(Collectors.toList());
            ctx.json(skiLessonDTOS);
        }
        catch (Exception ex)
        {
            logger.error("Error filtering trips by category", ex);
            throw new ApiException(400, "Error filtering trips by category", ex);
        }
    }

    public void getInstructorsOverview(Context ctx)
    {
        try
        {
            Long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i > 0, "id must be at least 0")
                    .getOrThrow((validator) -> new BadRequestResponse("Invalid id"));
            Instructor instructor = dao.getById(Instructor.class, id);
            double totalSumPrice = instructor.getSkiLessons().stream().mapToDouble(SkiLesson::getPrice).sum();
            Map<String, Object> instructorInfo = new HashMap<>();
            instructorInfo.put("instructorId", instructor.getId());
            instructorInfo.put("totalSumPrice", totalSumPrice);
            ctx.json(instructorInfo);
        } catch (Exception ex)
        {
            logger.error("Error getting instructor overview", ex);
            throw new ApiException(400, "Error getting instructor overview", ex);
        }

    }

    public void populate(Context ctx)
    {
        try
        {
            EntityManager em = emf.createEntityManager();
            new Populator(em).populate();
        } catch (Exception ex)
        {
            logger.error("Error populating database", ex);
            throw new ApiException(400, "Error populating database", ex);
        }
    }
}
