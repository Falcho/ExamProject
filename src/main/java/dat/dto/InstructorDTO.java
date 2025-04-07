// src/main/java/dat/dto/InstructorDTO.java
package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.entities.Instructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InstructorDTO
{
    @JsonIgnore
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int yearsOfExp;

    public InstructorDTO(Instructor instructor)
    {
        this.id = instructor.getId();
        this.firstName = instructor.getFirstName();
        this.lastName = instructor.getLastName();
        this.email = instructor.getEmail();
        this.phone = instructor.getPhone();
        this.yearsOfExp = instructor.getYearsOfExp();
    }
}