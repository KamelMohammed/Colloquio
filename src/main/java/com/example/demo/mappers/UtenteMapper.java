package com.example.demo.mappers;

import com.example.demo.dto.UtenteDTO;
import com.example.demo.entity.Utente;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.Validate;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class UtenteMapper {
    ModelMapper modelMapper = new ModelMapper();

    public Utente fromDtoToEntity(final UtenteDTO dto){
        Validate.notNull(dto);
        Validate.matchesPattern(dto.getNome(), "^\\s*[a-zA-Z]{3,20}$", "Nome not match");
        Validate.matchesPattern(dto.getCognome(), "^\\s*[a-zA-Z]{3,20}$", "Cognome not match");
        Validate.matchesPattern(dto.getEmial(), "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", "Email dose not match");
        return modelMapper.map(dto, Utente.class);
    }
    public UtenteDTO fromEntityToDto(final Utente entity){
        return modelMapper.map(entity, UtenteDTO.class);
    }

    public List<UtenteDTO> convertListEntityToListDTO(List<Utente> utenti){
        List<UtenteDTO> utentiResponse = new ArrayList<>();
        for(Utente utente : utenti){
            UtenteDTO  dto = fromEntityToDto(utente);
            utentiResponse.add(dto);
        }
        return  utentiResponse;
    }
    public Utente convertToUtente(CSVRecord record){
        List<String> dati = new ArrayList<>();
        for( int i =0 ; i<record.size(); i++){
            dati.add(record.get(i));
        }

        Utente utente = new Utente();
        if(dati.size()==3) {
            utente.setNome(dati.get(0));
            utente.setCognome(dati.get(1));
            utente.setEmial(dati.get(2));
            return utente;
        }
        else return null;
    }
}
