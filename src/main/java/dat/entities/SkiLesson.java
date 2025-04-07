package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import dat.dto.SkiLessonDTO;
import dat.enums.Level;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SkiLesson
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private String startPosition;
    private String name;
    private Double price;
    private Level level;

    @ManyToOne
    @JsonBackReference
    @ToString.Exclude
    private Instructor instructor;

    public SkiLesson(String name)
    {
        this.name = name;
    }

    public SkiLesson(String name, String startPosition)
    {
        this.name = name;
        this.startPosition = startPosition;
    }

    public SkiLesson(SkiLessonDTO skiLessonDTO)
    {
        this.name = skiLessonDTO.getName();
        this.price = skiLessonDTO.getPrice();
        this.startPosition = skiLessonDTO.getStartPosition();
        this.startTime = skiLessonDTO.getStartTime();
        this.endTime = skiLessonDTO.getEndTime();
        this.level = skiLessonDTO.getLevel();
    }

    public Instructor addInstructor(Instructor instructor)
    {
        return  this.instructor = instructor;
    }

    public Instructor removeInstructor(Instructor instructor)
    {
        this.instructor = null;
        if (instructor != null)
        {
            instructor.getSkiLessons().remove(this);
        }
        return instructor;
    }

}
