package ru.simbirgo.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirgo.controllers.AccountController;
import ru.simbirgo.controllers.CharacterController;
import ru.simbirgo.models.Character;
import ru.simbirgo.models.Profession;
import ru.simbirgo.payloads.NewCharacterRequest;
import ru.simbirgo.repositories.CharacterRepository;

import java.util.List;

@Service
public class CharacterService {
    private final Logger LOGGER = LoggerFactory.getLogger(CharacterService.class);

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private ProfessionService professionService;
    @Autowired
    private ModelMapper modelMapper;


    public List<Character> getCharacters(){
        LOGGER.info("GETR CHARACTER");
        return characterRepository.findAll();
    }
    public Character createCharacter(NewCharacterRequest newCharacterRequest) {
        LOGGER.info("CREATE CHARACTER");
        Profession profession = professionService.getProfessionById(newCharacterRequest.getProfessionId());
        Character newCharacter = new Character();
        newCharacter.setProfession(profession);
        newCharacter.setName(newCharacterRequest.getName());
        return characterRepository.save(newCharacter);
    }

    public Character updateCharacter(Long id, NewCharacterRequest newCharacterRequest){
        LOGGER.info("UPDATE CHARACTER");
        Character characterToUpdate = characterRepository.findById(id).get();
        if(newCharacterRequest.getProfessionId() != null){
            if(!characterToUpdate.getProfession().getId().equals(newCharacterRequest.getProfessionId())){
                Profession profession = professionService.getProfessionById(newCharacterRequest.getProfessionId());
                characterToUpdate.setProfession(profession);
            }
        }
        characterToUpdate.setName(newCharacterRequest.getName());
        return characterRepository.save(characterToUpdate);
    }

    public boolean deleteById(Long id){
        LOGGER.info("DELETE BY ID");
        try{
            characterRepository.deleteById(id);
            return true;
        }
        catch (RuntimeException e){
            LOGGER.error(e.getMessage());
            return false;
        }


    }
}
