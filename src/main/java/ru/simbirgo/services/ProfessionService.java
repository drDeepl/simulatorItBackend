package ru.simbirgo.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirgo.exceptions.ProfessionNotExists;
import ru.simbirgo.models.Character;
import ru.simbirgo.models.Profession;
import ru.simbirgo.payloads.NewProfessionRequest;
import ru.simbirgo.payloads.ProfessionRequest;
import ru.simbirgo.repositories.CharacterRepository;
import ru.simbirgo.repositories.ProfessionRepository;

import java.util.List;

@Service
public class ProfessionService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProfessionService.class);

    @Autowired
    private ProfessionRepository professionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CharacterRepository characterRepository;





    public Profession createProfession(NewProfessionRequest newProfessionRequest){
        LOGGER.info("CREATE PROFESSION");
        Profession profession = modelMapper.map(newProfessionRequest, Profession.class);
        return professionRepository.save(profession);
    }

    public List<Profession> getProfessions(){
        LOGGER.info("GET PROFESSIONS");
        return professionRepository.findAll();
    }



    public Profession getProfessionById(Long id){
        return professionRepository.findById(id).get();
    }

    public List<Profession> gerProfessions(){
        LOGGER.info("GET PROFESSIONS");
        return professionRepository.findAll();
    }

    public Profession getProfessionByName(String name){
        LOGGER.info("GER PROFESSION BY NAME");
        return professionRepository.findByName(name).get();
    }

    public Profession updateProfession(Long professionId, ProfessionRequest professionRequest){
        LOGGER.info("UPDATE PROFESSION");
//        Profession updateProfession = modelMapper.map(professionRequest, Profession.class);
        Profession updateProfession = professionRepository.findById(professionId).get();
        updateProfession.setName(professionRequest.getName());
        updateProfession.setDescription(professionRequest.getDescription());
        return professionRepository.save(updateProfession);
    }

    public void deleteProfessionById(Long id){
        LOGGER.info("DELETE PROFESSION BY ID");
        professionRepository.deleteById(id);
    }


}
