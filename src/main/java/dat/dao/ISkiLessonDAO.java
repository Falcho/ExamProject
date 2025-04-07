package dat.dao;

import dat.entities.Instructor;
import dat.entities.SkiLesson;
import dat.enums.Level;

import java.util.List;

public interface ISkiLessonDAO
{
    SkiLesson getSkiLessonsById(Long id);
    SkiLesson addInstructor(SkiLesson skiLesson, Instructor instructor);
    SkiLesson removeInstructor(SkiLesson skiLesson, Instructor instructor);
    public List<SkiLesson> filterByLevel(Level level);
}
