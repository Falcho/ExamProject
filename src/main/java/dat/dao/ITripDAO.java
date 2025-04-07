package dat.dao;

import dat.entities.Instructor;
import dat.entities.SkiLesson;
import dat.enums.Level;

import java.util.List;

public interface ITripDAO
{
    SkiLesson getSkiLessonsById(Long id);
    SkiLesson addInstructor(SkiLesson skiLesson, Instructor instructor);
    SkiLesson removeInstructor(SkiLesson skiLesson, Instructor instructor);
    Instructor getInstructorForSkiLesson(SkiLesson skiLesson);
    public List<SkiLesson> filterByLevel(Level level);
}
