package com.example.demo.controller;

import com.example.demo.dto.UtenteDTO;
import com.example.demo.entity.Utente;
import com.example.demo.service.UtenteService;
import com.example.demo.mappers.UtenteMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("utente")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;
    private final String fileType = "text/csv";
    private UtenteMapper utenteMapper = new UtenteMapper();
    @PostMapping("/new")
    public ResponseEntity<UtenteDTO> salva (@RequestBody UtenteDTO utente){
        UtenteDTO utenteResponse =utenteMapper.fromEntityToDto(utenteService.AddUtente(utenteMapper.fromDtoToEntity(utente)));
        return new ResponseEntity<>(utenteResponse, HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<UtenteDTO>> getByNomeAndCognome (@RequestParam String nome , @RequestParam String cognome){
        return new ResponseEntity<>(utenteMapper.convertListEntityToListDTO(utenteService.filter(nome,cognome)), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UtenteDTO>> getAll (){
        return new ResponseEntity<>(utenteMapper.convertListEntityToListDTO(utenteService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtenteDTO> getById (@PathVariable("id") Long id){
        UtenteDTO utenteResponse = utenteMapper.fromEntityToDto(utenteService.getById(id));
        return new ResponseEntity<>(utenteResponse, HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<List<Utente>> saveUtentiFromCsv(@RequestParam("file") MultipartFile file) throws IOException {
        List<Utente> utentiResponse;
        if (file.getContentType().equalsIgnoreCase(fileType)){
            BufferedReader fileReader = new BufferedReader(new
                    InputStreamReader(file.getInputStream(), "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
            List<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                    Utente utenteDaSalvare = utenteMapper.convertToUtente(csvRecord);
                    if(utenteDaSalvare==null)
                        continue;
                    utenteService.AddUtente(utenteDaSalvare);

            }
           utentiResponse = utenteService.getAll();
        }
        else{
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(utentiResponse, HttpStatus.OK);
    }


}
