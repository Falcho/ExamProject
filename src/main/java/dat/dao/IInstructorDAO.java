package dat.dao;

import dat.entities.Instructor;
import dat.entities.SkiLesson;

public interface IInstructorDAO
{
    Instructor getInstructorForSkiLesson(SkiLesson skiLesson);

}
