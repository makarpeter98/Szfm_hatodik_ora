package com.example.springjavafx.springApp;

import com.example.springjavafx.springApp.model.Person;
import com.example.springjavafx.springApp.model.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SpringTestDemo {

    @Autowired
    PersonRepository personRepository;

    Person TestAntal = new Person();
    Person TestAgnes = new Person();

    public SpringTestDemo() {
        TestAgnes.setName("Elektrom Agnes");
        TestAgnes.setAge(101);
        TestAntal.setName("Trab Antal");
        TestAntal.setAge(99);
    }
}
