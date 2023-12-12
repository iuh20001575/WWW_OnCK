package vn.edu.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
