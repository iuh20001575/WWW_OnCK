package vn.edu.iuh.fit.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.models.Person;
import vn.edu.iuh.fit.repositories.PersonRepository;

import java.util.Optional;
import java.util.stream.IntStream;

@AllArgsConstructor
@Controller
@RequestMapping("/persons")
public class PersonController {
    private PersonRepository personRepository;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String index(@RequestParam("page")Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model) {
        int pageNum = page.orElse(1);
        int sizeNum = size.orElse(5);

        PageRequest pageRequest = PageRequest.of(pageNum - 1, sizeNum);

        Page<Person> personPage = personRepository.findAll(pageRequest);

        model.addAttribute("persons", personPage);
        model.addAttribute("pages", IntStream.range(1, personPage.getTotalPages() + 1).boxed().toList());

        return "persons/index";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        personRepository.deleteById(id);

        return "redirect:/persons";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String add(Model model) {
        Person person = new Person();

        model.addAttribute(person);

        return "persons/add";
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String addPost(@ModelAttribute("person") Person person) {
        personRepository.save(person);

        return "redirect:/persons";
    }

    @GetMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(Model model, @PathVariable("id") Long id) {
        Person person = personRepository.findById(id).get();

        model.addAttribute(person);

        return "persons/update";
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updatePost(@ModelAttribute("person") Person person, @PathVariable("id") Long id) {
        person.setId(id);

        personRepository.save(person);

        return "redirect:/persons";
    }
}
