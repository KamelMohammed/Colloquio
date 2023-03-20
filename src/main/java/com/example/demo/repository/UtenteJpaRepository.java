package com.example.demo.repository;

import com.example.demo.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteJpaRepository extends JpaRepository<Utente, Long> {

    @Query("Select  u from Utente u where u.id = :id")
    Utente getUtenteById(@Param("id") Long id);

    @Query("select u from Utente u " +
            "where (lower(u.nome) like concat('%', lower(?1),'%') " +
            "and lower(u.cognome) like concat('%', lower(?2),'%'))")
    List<Utente> findUtentiByNomeOrCognomeOrAll(String nome, String Cognome);
}
