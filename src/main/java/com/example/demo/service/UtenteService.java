package com.example.demo.service;

import com.example.demo.entity.Utente;

import java.util.List;

public interface UtenteService {


        public Utente AddUtente(Utente utente);

        public List<Utente> getAll();

        public Utente getById(Long id);

        public List<Utente> filter (String nome, String cognome);

        public void deleteUtente(Long id);

}


