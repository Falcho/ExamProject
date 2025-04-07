package dat.controllers;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dto.SkiLessonDTO;
import dat.entities.Instructor;
import dat.entities.SkiLesson;
import dat.routes.Routes;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LevelResourceTest
{

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    final ObjectMapper objectMapper = new ObjectMapper();
    SkiLesson t1, t2;
    final Logger logger = LoggerFactory.getLogger(LevelResourceTest.class.getName());


    @BeforeAll
    static void setUpAll()
    {
        SkiLessonController skiLessonController = new SkiLessonController(emf);
        SecurityController securityController = new SecurityController(emf);
        Routes routes = new Routes(skiLessonController, securityController);
        ApplicationConfig
                .getInstance()
                .initiateServer()
                .setRoute(routes.getRoutes())
                .handleException()
                .setApiExceptionHandling()
                .checkSecurityRoles()
                .startServer(7078);
        RestAssured.baseURI = "http://localhost:7078/api";
    }

    @BeforeEach
    void setUp()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            //TestEntity[] entities = EntityPopulator.populate(genericDAO);
            t1 = new SkiLesson("TestEntityA");
            t2 = new SkiLesson("TestEntityB");
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Instructor ").executeUpdate();
            em.createQuery("DELETE FROM SkiLesson ").executeUpdate();

            em.persist(t1);
            em.persist(t2);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            logger.error("Error setting up test", e);
        }
    }

    @Test
    void getAll()
    {
        given().when().get("/hotel").then().statusCode(200).body("size()", equalTo(2));
    }

    @Test
    void getById()
    {
        given().when().get("/hotel/" + t2.getId()).then().statusCode(200).body("id", equalTo(t2.getId().intValue()));
    }

    @Test
    void create()
    {
        SkiLesson entity = new SkiLesson("Thor Partner Level", "Carl Gustavs Gade 1");
        Instructor instructor = new Instructor("Bruce","Banner");
        entity.addInstructor(instructor);
        try
        {
            String json = objectMapper.writeValueAsString(new SkiLessonDTO(entity));
            given().when()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(json)
                    .post("/hotel")
                    .then()
                    .statusCode(200)
                    .body("name", equalTo(entity.getName()))
                    .body("address", equalTo(entity.getStartPosition()));
                    //.body("rooms.size()", equalTo(1));
        } catch (JsonProcessingException e)
        {
            logger.error("Error creating hotel", e);

            fail();
        }
    }

    @Test
    void update()
    {
        SkiLesson entity = new SkiLesson("New entity2");
        try
        {
            String json = objectMapper.writeValueAsString(new SkiLessonDTO(entity));
            given().when()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(json)
                    .put("/hotel/" + t1.getId()) // double check id
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("New entity2"));
        } catch (JsonProcessingException e)
        {
            logger.error("Error updating hotel", e);
            fail();
        }
    }

    @Test
    void delete()
    {
        given().when()
                .delete("/hotel/" + t1.getId())
                .then()
                .statusCode(204);
    }
}