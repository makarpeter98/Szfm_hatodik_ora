package com.example.springjavafx.springApp;

import com.example.springjavafx.springApp.model.Person;
import com.example.springjavafx.springApp.model.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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


    @Test
    @Rollback
    public void testSavePerson()
    {
        personRepository.save(TestAntal);

        Optional<Person> person = personRepository.findById(TestAntal.getId());

        assertThat(person).isPresent();
        assertThat(person.get().getName()).isEqualTo("Trab Antal");
        assertThat(person.get().getAge()).isEqualTo(99);
    }

//    @Test
//    @Rollback
//
//    public void testUpdate()
//    {
//        personRepository.save(TestAgnes)
//    }
}
