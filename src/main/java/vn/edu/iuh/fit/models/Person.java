package vn.edu.iuh.fit.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullname;
    private String email;
    private LocalDate dob;

    public Person(String fullname, String email, LocalDate dob) {
        this.fullname = fullname;
        this.email = email;
        this.dob = dob;
    }
}
