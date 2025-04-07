package dat.dao;

import dat.entities.Instructor;
import dat.entities.SkiLesson;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class InstructorDAO extends GenericDAO implements IInstructorDAO
{
    public InstructorDAO(EntityManagerFactory emf)
    {
        super(emf);
    }

    public List<Instructor> getAllInstructors()
    {
        return super.getAll(Instructor.class);
    }

    @Override
    public Instructor getInstructorForSkiLesson(SkiLesson skiLesson)
    {
        return skiLesson.getInstructor();
    }
}
