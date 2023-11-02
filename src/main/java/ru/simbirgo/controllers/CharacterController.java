package ru.simbirgo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirgo.dtos.JwtDTO;
import ru.simbirgo.dtos.MessageDTO;
import ru.simbirgo.exceptions.AppException;
import ru.simbirgo.models.Character;
import ru.simbirgo.models.Profession;
import ru.simbirgo.payloads.NewCharacterRequest;
import ru.simbirgo.repositories.CharacterRepository;
import ru.simbirgo.services.CharacterService;
import ru.simbirgo.services.ProfessionService;

import java.util.List;
import java.util.NoSuchElementException;

@Tag(name="CharacterController")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/character")
public class CharacterController {
    private final Logger LOGGER = LoggerFactory.getLogger(CharacterController.class);

    @Autowired
    private CharacterService characterService;

    @Autowired
    ProfessionService professionService;

    @Operation(summary="получение всех персонажей")
    @GetMapping("")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Character.class))})
    public List<Character> getCharacters(){
        LOGGER.info("GET CHARACTERS");
        return characterService.getCharacters();
    }

    @Operation(summary="создание персонажа")
    @PostMapping("/new")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Character.class))})
    public ResponseEntity<?> newCharacter(@RequestBody NewCharacterRequest newCharacterRequest){
        LOGGER.info("NEW CHARACTER");
        try {
            Profession profession = professionService.getProfessionById(newCharacterRequest.getProfessionId());
            Character newCharacter = characterService.createCharacter(newCharacterRequest, profession);
            return new ResponseEntity<Character>(newCharacter, HttpStatus.OK);
        }
        catch(NoSuchElementException NSE){
            LOGGER.info(NSE.getMessage());
            return new ResponseEntity<>(new AppException(HttpStatus.NOT_FOUND.value(), "выбранная профессия не найдена"), HttpStatus.NOT_FOUND);
        }
        catch (RuntimeException e){
            LOGGER.info(e.getMessage());
            return new ResponseEntity<>(new AppException(HttpStatus.FORBIDDEN.value(), "что-то пошло не так"), HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary="редактирование персонажа")
    @PutMapping("/update/{id}")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Character.class))})
    public ResponseEntity<?> updateCharacter(@PathVariable("id") Long id, @RequestBody NewCharacterRequest newCharacterRequest){
        LOGGER.info("UPDATE CHARACTER");
        try {
            Profession profession = professionService.getProfessionById(newCharacterRequest.getProfessionId());
            Character updatedCharacter = characterService.updateCharacter(id, newCharacterRequest, profession);
            return new ResponseEntity<Character>(updatedCharacter, HttpStatus.OK);
        }
        catch (NoSuchElementException NSE){
            LOGGER.error(NSE.getMessage());
            return new ResponseEntity<>(new AppException(HttpStatus.NOT_FOUND.value(), "персонаж не найден"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary="удаление персонажа")
    @DeleteMapping("/delete/{id}")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = MessageDTO.class))})
    public ResponseEntity<MessageDTO> deleteCharacterById(@PathVariable("id") Long id){
        LOGGER.info("DELETE CHARACTER BY ID");
        boolean isDeleted = characterService.deleteById(id);
        if(isDeleted){
            return new ResponseEntity<MessageDTO>(new MessageDTO("профессия успешно удалена"), HttpStatus.OK);
        }

        return new ResponseEntity<MessageDTO>(new MessageDTO("при удалении произошла ошибка"), HttpStatus.FORBIDDEN);
    }
}
