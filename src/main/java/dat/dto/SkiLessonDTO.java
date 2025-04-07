package dat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat.entities.SkiLesson;
import dat.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SkiLessonDTO
{
    private Long id;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private String startPosition;
    private String name;
    private Double price;
    private Level level;
    private InstructorDTO instructor;

    public SkiLessonDTO(SkiLesson skiLesson)
    {
        this.id = skiLesson.getId();
        this.name = skiLesson.getName();
        this.price = skiLesson.getPrice();
        this.startPosition = skiLesson.getStartPosition();
        this.startTime = skiLesson.getStartTime();
        this.endTime = skiLesson.getEndTime();
        this.level = skiLesson.getLevel();
        if (skiLesson.getInstructor() != null)
        {
            this.instructor = new InstructorDTO(skiLesson.getInstructor());
        }

    }
}
