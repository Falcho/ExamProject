package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.*;
import dat.exceptions.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class GenericDAOTest
{
    private static EntityManagerFactory emf;
    private static GenericDAO genericDAO;
    private static SkiLesson t1, t2;
    private static Instructor g1, g2, g3, g4;

    @BeforeAll
    static void setUpClass()
    {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        genericDAO = new GenericDAO(emf);
    }

    @AfterAll
    static void tearDownClass()
    {
        if (emf != null)
        {
            emf.close();
        }
    }

    @BeforeEach
    void setUp()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            g1 = new Instructor("Jack", "Sparrow");
            g2 = new Instructor("Iron","Man");
            g3 = new Instructor("Peter", "Parker");
            g4 = new Instructor("Bruce", "Wayne");
            t1 = new SkiLesson("Level A");
            t2 = new SkiLesson("Level B");
            t1.addInstructor(g1);
            t1.addInstructor(g2);
            t2.addInstructor(g3);
            t2.addInstructor(g4);
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Instructor").executeUpdate();
            em.createQuery("DELETE FROM SkiLesson").executeUpdate();
            em.persist(t1);
            em.persist(t2);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            fail("Setup failed: " + e.getMessage());
        }
    }

    @Test
    void getInstance()
    {
        assertNotNull(emf);
    }

    @Test
    void create()
    {
        SkiLesson t3 = new SkiLesson("Level C");
        Instructor g5 = new Instructor("Clark", "Kent");
        t3.addInstructor(g5);

        SkiLesson result = genericDAO.create(t3);

        assertNotNull(result);
        assertThat(result, samePropertyValuesAs(t3));
        try (EntityManager em = emf.createEntityManager())
        {
            SkiLesson found = em.find(SkiLesson.class, result.getId());
            assertThat(found, samePropertyValuesAs(t3, "rooms"));
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM SkiLesson t", Long.class).getSingleResult();
            assertThat(amountInDb, is(3L));
        }
    }

    @Test
    void createMany()
    {
        SkiLesson t3 = new SkiLesson("TestEntityC");
        SkiLesson t4 = new SkiLesson("TestEntityD");
        List<SkiLesson> testEntities = List.of(t3, t4);

        List<SkiLesson> result = genericDAO.create(testEntities);

        assertNotNull(result);
        assertThat(result.get(0), samePropertyValuesAs(t3, "rooms"));
        assertThat(result.get(1), samePropertyValuesAs(t4, "rooms"));
        try (EntityManager em = emf.createEntityManager())
        {
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM SkiLesson t", Long.class).getSingleResult();
            assertThat(amountInDb, is(4L));
        }
    }

    @Test
    void read()
    {
        SkiLesson expected = t1;

        SkiLesson result = genericDAO.getById(SkiLesson.class, t1.getId());

        assertNotNull(result);
        assertThat(result, samePropertyValuesAs(expected, "rooms"));
    }

    @Test
    void read_notFound()
    {
        DaoException exception = assertThrows(DaoException.class, () -> genericDAO.getById(SkiLesson.class, 1000L));

        assertThat(exception.getMessage(), is("Error reading object from db"));
    }

    @Test
    void findAll()
    {
        List<SkiLesson> expected = List.of(t1, t2);

        List<SkiLesson> result = genericDAO.getAll(SkiLesson.class);

        assertNotNull(result);
        assertThat(result.size(), is(2));
        assertThat(result.get(0), samePropertyValuesAs(expected.get(0), "rooms"));
        assertThat(result.get(1), samePropertyValuesAs(expected.get(1), "rooms"));
    }

    @Test
    void update()
    {
        t1.setName("UpdatedName");

        SkiLesson result = genericDAO.update(t1);

        assertNotNull(result);
        assertThat(result, samePropertyValuesAs(t1, "rooms"));
    }

    @Test
    void updateMany()
    {
        t1.setName("UpdatedName");
        t2.setName("UpdatedName");
        List<SkiLesson> testEntities = List.of(t1, t2);

        List<SkiLesson> result = genericDAO.update(testEntities);

        assertNotNull(result);
        assertThat(result.size(), is(2));
        assertThat(result.get(0), samePropertyValuesAs(t1, "rooms"));
        assertThat(result.get(1), samePropertyValuesAs(t2, "rooms"));
    }

    @Test
    void delete()
    {
        genericDAO.delete(t1);

        try (EntityManager em = emf.createEntityManager())
        {
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM SkiLesson t", Long.class).getSingleResult();
            assertThat(amountInDb, is(1L));
            SkiLesson found = em.find(SkiLesson.class, t1.getId());
            assertNull(found);
        }
    }

    @Test
    void delete_byId()
    {
        genericDAO.delete(SkiLesson.class, t2.getId());

        try (EntityManager em = emf.createEntityManager())
        {
            Long amountInDb = em.createQuery("SELECT COUNT(t) FROM SkiLesson t", Long.class).getSingleResult();
            assertThat(amountInDb, is(1L));
            SkiLesson found = em.find(SkiLesson.class, t2.getId());
            assertNull(found);
        }
    }
}