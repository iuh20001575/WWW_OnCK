package vn.edu.iuh.fit;

import com.thedeanda.lorem.LoremIpsum;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.models.Person;
import vn.edu.iuh.fit.repositories.PersonRepository;

import java.time.LocalDate;

@SpringBootApplication
@AllArgsConstructor
public class OnTapCkMem02Application {
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(OnTapCkMem02Application.class, args);
    }

    @Bean
    public CommandLineRunner insert() {
        return args -> {
            for (int i = 0; i < 10; i++) {
                Person person = new Person(LoremIpsum.getInstance().getName(), LoremIpsum.getInstance().getEmail(), LocalDate.now());

                personRepository.save(person);
            }
        };
    }
}
