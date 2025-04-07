package dat.utils;

import dat.entities.Instructor;
import dat.entities.SkiLesson;
import dat.enums.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Populator {

    private final EntityManager em;

    public Populator(EntityManager em) {
        this.em = em;
    }

    public void populate() {
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Skip if already populated
            Long instructorCount = em.createQuery("SELECT COUNT(i) FROM Instructor i", Long.class).getSingleResult();
            if (instructorCount > 0) {
                tx.rollback();
                System.out.println("Data already exists, skipping population.");
                return;
            }

            // === Instructors data ===
            List<String[]> instructorData = List.of(
                    new String[]{"Clark", "Kent", "CK@example.com", "+111111111", "5"},
                    new String[]{"Bruce", "Wayne", "BW@example.com", "+222222222", "7"},
                    new String[]{"Tony", "Stark", "TS@example.com", "+333333333", "4"},
                    new String[]{"Peter", "Parker", "PP@example.com", "+444444444", "6"}
            );

            // === SkiLessons data ===
            List<SkiLesson> allSkiLessons = List.of(
                    new SkiLesson(null, LocalTime.of(8, 0), LocalTime.of(10, 30), "47.1287, 10.2641", "Advanced Alpine Skiing", 30.0, Level.ADVANCED, null),
                    new SkiLesson(null, LocalTime.of(9, 30), LocalTime.of(12, 0), "47.4465, 12.3929", "Intermediate Slope Training", 35.0, Level.INTERMEDIATE, null),
                    new SkiLesson(null, LocalTime.of(10, 0), LocalTime.of(12, 30), "47.0122, 10.2920", "Beginner Snowboarding", 40.0, Level.BEGINNER, null),
                    new SkiLesson(null, LocalTime.of(11, 0), LocalTime.of(13, 30), "46.9696, 11.0106", "Advanced Freestyle Skiing", 25.0, Level.ADVANCED, null),
                    new SkiLesson(null, LocalTime.of(12, 0), LocalTime.of(14, 30), "47.3256, 12.7945", "Intermediate Cross-Country", 50.0, Level.INTERMEDIATE, null),
                    new SkiLesson(null, LocalTime.of(13, 0), LocalTime.of(15, 30), "47.3925, 12.6412", "Beginner Skiing Basics", 45.0, Level.BEGINNER, null),
                    new SkiLesson(null, LocalTime.of(14, 0), LocalTime.of(16, 30), "47.2510, 13.5567", "Advanced Mogul Skiing", 55.0, Level.ADVANCED, null),
                    new SkiLesson(null, LocalTime.of(15, 0), LocalTime.of(17, 30), "47.1167, 13.1333", "Intermediate Snowshoeing", 60.0, Level.INTERMEDIATE, null),
                    new SkiLesson(null, LocalTime.of(16, 0), LocalTime.of(18, 30), "47.3930, 13.6893", "Beginner Slope Navigation", 20.0, Level.BEGINNER, null),
                    new SkiLesson(null, LocalTime.of(17, 0), LocalTime.of(19, 30), "47.3306, 11.1876", "Advanced Backcountry Skiing", 70.0, Level.ADVANCED, null),
                    new SkiLesson(null, LocalTime.of(18, 0), LocalTime.of(20, 30), "47.2137, 11.0231", "Intermediate Ski Touring", 65.0, Level.INTERMEDIATE, null),
                    new SkiLesson(null, LocalTime.of(19, 0), LocalTime.of(21, 30), "47.2087, 10.1419", "Beginner Snowshoe Hike", 75.0, Level.BEGINNER, null)
            );

            int skiLessonIndex = 0;
            for (String[] i : instructorData) {
                Instructor instructor = new Instructor();
                instructor.setFirstName(i[0]);
                instructor.setLastName(i[1]);
                instructor.setEmail(i[2]);
                instructor.setPhone(i[3]);
                instructor.setYearsOfExp(Integer.parseInt(i[4]));

                Set<SkiLesson> instructorSkiLessons = new HashSet<>();
                for (int j = 0; j < 3; j++) {
                    SkiLesson skiLesson = allSkiLessons.get(skiLessonIndex++);
                    skiLesson.setInstructor(instructor);
                    instructorSkiLessons.add(skiLesson);
                }

                instructor.setSkiLessons(instructorSkiLessons);
                em.persist(instructor);
            }

            tx.commit();
            System.out.println("4 instructors and 12 unique ski lessons populated successfully.");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
}