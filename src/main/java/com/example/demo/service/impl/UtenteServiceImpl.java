package com.example.demo.service.impl;

import com.example.demo.entity.Utente;
import com.example.demo.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UtenteJpaRepository;

import java.util.List;

@Service
public class UtenteServiceImpl implements UtenteService {
    private UtenteJpaRepository repository;

    @Autowired
    public UtenteServiceImpl(UtenteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Utente AddUtente(Utente utente) {
        return repository.save(utente);
    }

    @Override
    public List<Utente> getAll() {
        return repository.findAll();
    }

    @Override
    public Utente getById(Long id) {
        return repository.getUtenteById(id);
    }

    @Override
    public List<Utente> filter(String nome, String cognome) {
        return repository.findUtentiByNomeOrCognomeOrAll(nome,cognome);
    }

    @Override
    public void deleteUtente(Long id) {
         repository.deleteById(id);
    }


}
