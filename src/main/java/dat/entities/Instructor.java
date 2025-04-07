// src/main/java/dat/entities/Instructor.java
package dat.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dat.dto.InstructorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Instructor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int yearsOfExp;

    @OneToMany(mappedBy = "instructor", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonManagedReference
    @ToString.Exclude
    private Set<SkiLesson> skiLessons;

    public Instructor(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Instructor(InstructorDTO instructorDTO)
    {
        this.id = instructorDTO.getId();
        this.firstName = instructorDTO.getFirstName();
        this.lastName = instructorDTO.getLastName();
        this.email = instructorDTO.getEmail();
        this.phone = instructorDTO.getPhone();
        this.yearsOfExp = instructorDTO.getYearsOfExp();
    }


}