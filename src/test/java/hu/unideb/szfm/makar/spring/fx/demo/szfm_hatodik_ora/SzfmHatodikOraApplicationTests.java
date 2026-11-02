package hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora;

import hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.controller.PersonController;
import hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.model.Person;
import hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.model.PersonRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:person-test;DB_CLOSE_DELAY=-1",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.jpa.hibernate.ddl-auto=create-drop"
})

class SzfmHatodikOraApplicationTests {

	@Autowired
	private PersonController personController;

	@Autowired
	private PersonRepository personRepository;

	@BeforeEach
	void setUp() {
		personRepository.deleteAll();
	}

	// Tesztadat létrehozása
	private Person createPerson() {
		Person person = new Person();

		person.setFirstName("Teszt");
		person.setLastName("Elek");
		person.setAge(25);

		return person;
	}

	// Spring alkalmazáskörnyezet indulásának ellenőrzése
	@Test
	void contextLoads() {
		assertNotNull(personController);
		assertNotNull(personRepository);
	}

	// Új személy mentése
	@Test
	void testSavePerson() {
		Person person = createPerson();

		Person saved = personController.savePerson(person);

		assertNotNull(saved.getId());
		assertEquals("Teszt", saved.getFirstName());
		assertEquals("Elek", saved.getLastName());
		assertEquals(25, saved.getAge());
	}

	// Személy lekérdezése azonosító alapján
	@Test
	void testGetPersonById() {
		Person saved = personController.savePerson(createPerson());

		Optional<Person> result =
				personController.getPersonById(saved.getId());

		assertTrue(result.isPresent());
		assertEquals(saved.getId(), result.get().getId());
		assertEquals("Teszt", result.get().getFirstName());
	}

	// Összes személy lekérdezése
	@Test
	void testGetAllPersons() {
		personController.savePerson(createPerson());
		personController.savePerson(createPerson());

		List<Person> persons = personController.getAllPersons();

		assertEquals(2, persons.size());
	}

	// Személy adatainak módosítása
	@Test
	void testUpdatePerson() {
		Person saved = personController.savePerson(createPerson());

		saved.setFirstName("Modositott");
		saved.setAge(30);

		personController.savePerson(saved);

		Optional<Person> result =
				personController.getPersonById(saved.getId());

		assertTrue(result.isPresent());
		assertEquals("Modositott", result.get().getFirstName());
		assertEquals(30, result.get().getAge());
	}

	// Személy törlése
	@Test
	void testDeletePerson() {
		Person saved = personController.savePerson(createPerson());

		personController.deletePerson(saved.getId());

		Optional<Person> result =
				personController.getPersonById(saved.getId());

		assertFalse(result.isPresent());
	}

	// Nem létező személy keresése
	@Test
	void testGetNonExistingPerson() {
		Optional<Person> result =
				personController.getPersonById(-1L);

		assertFalse(result.isPresent());
	}
}
