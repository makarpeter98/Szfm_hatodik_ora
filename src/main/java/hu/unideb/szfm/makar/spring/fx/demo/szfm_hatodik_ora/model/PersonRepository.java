package hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}