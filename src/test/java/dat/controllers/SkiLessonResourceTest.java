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
class SkiLessonResourceTest
{

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    final ObjectMapper objectMapper = new ObjectMapper();
    SkiLesson t1, t2;
    Instructor in1, in2;
    final Logger logger = LoggerFactory.getLogger(SkiLessonResourceTest.class.getName());


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
            in1 = new Instructor("TestInstructorA", "TestInstructorA");
            in2 = new Instructor("TestInstructorB", "TestInstructorB");

            em.getTransaction().begin();
            em.createQuery("DELETE FROM Instructor ").executeUpdate();
            em.createQuery("DELETE FROM SkiLesson ").executeUpdate();

            em.persist(t1);
            em.persist(t2);
            em.persist(in1);
            em.persist(in2);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            logger.error("Error setting up test", e);
        }
    }

    @Test
    void test_getAll()
    {
        given()
                .when()
                .get("/skilesson")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));
    }

    @Test
    void test_getById()
    {
        given()
                .when()
                .get("/skilesson/" + t2.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(t2.getId().intValue()));
    }

    @Test
    void test_create()
    {
        SkiLesson entity = new SkiLesson("Beginner Snowboarding", "47.3455, 12.7965");
        Instructor instructor = new Instructor("Bruce", "Banner");
        entity.addInstructor(instructor);
        try
        {
            String json = objectMapper.writeValueAsString(new SkiLessonDTO(entity));
            given().when()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(json)
                    .post("/skilesson")
                    .then()
                    .statusCode(200)
                    .body("name", equalTo(entity.getName()))
                    .body("startPosition", equalTo(entity.getStartPosition()));
        } catch (JsonProcessingException e)
        {
            logger.error("Error creating skilesson", e);

            fail();
        }
    }

    @Test
    void test_update()
    {
        SkiLesson entity = new SkiLesson("New entity2");
        try
        {
            String json = objectMapper.writeValueAsString(new SkiLessonDTO(entity));
            given().when()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(json)
                    .patch("/skilesson/" + t1.getId()) // double check id
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("New entity2"));
        } catch (JsonProcessingException e)
        {
            logger.error("Error updating skilesson", e);
            fail();
        }
    }

    @Test
    void test_delete()
    {
        given()
                .when()
                .delete("/skilesson/" + t1.getId())
                .then()
                .statusCode(204);
    }

    @Test
    void test_addInstructor()
    {
        try{
            String json = objectMapper.writeValueAsString(in1);
            given()
                    .when()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(json)
                    .put("/skilesson/" + t1.getId() + "/instructor/" + in1.getId())
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(in1.getId().intValue()))
                    .body("firstName", equalTo(in1.getFirstName()))
                    .body("lastName", equalTo(in1.getLastName()));
        } catch (JsonProcessingException e)
        {
            logger.error("Error updating skilesson", e);
            fail();
        }
    }
}