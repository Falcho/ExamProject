package dat.dao;

import dat.entities.Instructor;
import dat.entities.SkiLesson;
import dat.enums.Level;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class SkiLessonDAO extends GenericDAO implements ISkiLessonDAO
{
    public SkiLessonDAO(EntityManagerFactory emf)
    {
        super(emf);
    }

    public List<SkiLesson> getAllSkiLessons()
    {
        return super.getAll(SkiLesson.class);
    }

    public SkiLesson getSkiLessonsById(Long id)
    {
        return super.getById(SkiLesson.class, id);
    }

    public SkiLesson createSkiLesson(SkiLesson skiLesson)
    {
        return super.create(skiLesson);
    }

    public SkiLesson updateSkiLesson(SkiLesson skiLesson)
    {
        return super.update(skiLesson);
    }

    public void deleteSkiLesson(Long id)
    {
        super.delete(SkiLesson.class, id);
    }

    public List<SkiLesson> filterByLevel(Level level)
    {
        List<SkiLesson> allSkiLessons = super.getAll(SkiLesson.class);
        return allSkiLessons.stream()
                .filter(trip -> level.equals(trip.getLevel()))
                .collect(Collectors.toList());
    }

    public List<SkiLesson> getSkiLessonsByInstructor(Long instructorId)
    {
        List<SkiLesson> allSkiLessons = super.getAll(SkiLesson.class);
        return allSkiLessons.stream()
                .filter(skiLesson -> skiLesson.getInstructor() != null && skiLesson.getInstructor().getId().equals(instructorId))
                .collect(Collectors.toList());
    }

    @Override
    public SkiLesson addInstructor(SkiLesson skiLesson, Instructor instructor)
    {
        skiLesson.addInstructor(instructor);
        return update(skiLesson);
    }

    @Override
    public SkiLesson removeInstructor(SkiLesson skiLesson, Instructor instructor)
    {
        skiLesson.removeInstructor(instructor);
        SkiLesson updatedSkiLesson = update(skiLesson);
        delete(instructor);
        return updatedSkiLesson;
    }

}
