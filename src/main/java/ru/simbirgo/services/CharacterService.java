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
    private ModelMapper modelMapper;


    public List<Character> getCharacters(){
        LOGGER.info("GET CHARACTER");
        return characterRepository.findAll();
    }

    public Character getCharacterById(Long characterId){
        LOGGER.info("GET CHARACTER BY ID");
        return characterRepository.findById(characterId).get();
    }

    public Character getCharacterByName(String name){
        LOGGER.info("GET CHARACTER BY NAME");
        return characterRepository.findByName(name.toLowerCase()).get();
    }

    public List<Character> getCharactersByProfessionId(Long professionId){
        LOGGER.info("GET CHARACTERS BY ID");
        return characterRepository.findAllByProfessionId(professionId);
    }

    public void setNullWithProfessionId(Long professionId){
        List<Character> characters = getCharactersByProfessionId(professionId);
        for(int i = 0; i<characters.size(); i++){
            characters.get(i).setProfession(null);
        }
        characterRepository.saveAll(characters);
    }

    public Character createCharacter(NewCharacterRequest newCharacterRequest, Profession profession) {
        LOGGER.info("CREATE CHARACTER");
        Character newCharacter = new Character();
        newCharacter.setProfession(profession);
        newCharacter.setName(newCharacterRequest.getName().toLowerCase());
        return characterRepository.save(newCharacter);
    }

    public Character updateCharacter(Long id, NewCharacterRequest newCharacterRequest, Profession profession){
        LOGGER.info("UPDATE CHARACTER");
        Character characterToUpdate = characterRepository.findById(id).get();
        if(newCharacterRequest.getProfessionId() != null){
            if(!characterToUpdate.getProfession().getId().equals(newCharacterRequest.getProfessionId())){
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

    public boolean isExistsCharacterById (Long id){
        LOGGER.info("IS EXISTS CHARACTER BY ID");
        return characterRepository.existsById(id);
    }
}
