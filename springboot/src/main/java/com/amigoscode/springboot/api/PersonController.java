package com.amigoscode.springboot.api;

import java.util.List;

import com.amigoscode.springboot.model.Person;
import com.amigoscode.springboot.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // take content from request body and put it inside Person class
    @PostMapping("/person")
    public void addPerson(@RequestBody Person person) {
        personService.addPerson(person);
    }

    @GetMapping("/people")
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }
}