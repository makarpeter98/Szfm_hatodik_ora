
package hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.controller;

import hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.model.Person;
import hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.model.PersonRepository;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Összes személy lekérdezése
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // Személy keresése azonosító alapján
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    // Új személy létrehozása vagy meglévő módosítása
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    // Személy törlése
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
