package ru.simbirgo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirgo.exceptions.AppException;
import ru.simbirgo.models.Character;
import ru.simbirgo.models.Dialogue;
import ru.simbirgo.models.DialogueText;
import ru.simbirgo.models.Profession;
import ru.simbirgo.payloads.NewDialogueRequest;
import ru.simbirgo.payloads.NewDialogueTextRequest;
import ru.simbirgo.services.CharacterService;
import ru.simbirgo.services.DialogueService;

import java.util.List;

@Tag(name="DialogueController")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dialogue")
public class DialogueController {
    private final Logger LOGGER = LoggerFactory.getLogger(DialogueController.class);

    @Autowired
    DialogueService dialogueService;

    @Autowired
    CharacterService characterService;


    @Operation(summary="создание диалога для персонажа")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Dialogue.class))})
    @PostMapping("/new/{characterName}")
    public ResponseEntity<?> createDialogueForCharacter(@PathVariable("characterName") String characterName, @RequestBody NewDialogueRequest newDialogueRequest){
        LOGGER.info("CREATE DIALOGUE FOR CHARACTER");
        Character character = characterService.getCharacterByName(characterName);
        if(character != null){
            Dialogue createdDialogue = dialogueService.newDialogue(newDialogueRequest, character);
            return new ResponseEntity<>(createdDialogue, HttpStatus.OK);
        }
        return new ResponseEntity<>(new AppException(HttpStatus.NOT_FOUND.value(), "персонаж не найден"), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "добавление фраз к диалогу")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = DialogueText.class))})
    @PostMapping("/dialogue_text/new/{dialogueId}")
    public ResponseEntity<?> addTextToDialogueById(@PathVariable("dialogueId") Long dialogueId, @RequestBody NewDialogueTextRequest newDialogueTextRequest){
        LOGGER.info("ADD TEXT TO DIALOGUE BY ID");
        Dialogue dialogue = dialogueService.getDialogueById(dialogueId);
        if(dialogue != null){
            DialogueText createdDialogueText = dialogueService.addTextToDialogueById(dialogue, newDialogueTextRequest);
            return new ResponseEntity<>(createdDialogueText, HttpStatus.OK);
        }
        return new ResponseEntity<>(new AppException(HttpStatus.NOT_FOUND.value(), "диалог не найден"), HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "получение всех фраз диалога по id")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = DialogueText.class))})
    @GetMapping("/dialogue_text/{dialogueId}")
    public ResponseEntity<?> getTextByDialogueId(@PathVariable("dialogueId") Long dialogueId){
        LOGGER.info("GET TEXT BY DIALOGUE ID");
        try {
            List<DialogueText> texts = dialogueService.getTextsByDialogueId(dialogueId);
            return new ResponseEntity<>(texts, HttpStatus.OK);
        }
        catch (RuntimeException e){
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(new AppException(HttpStatus.FORBIDDEN.value(), "что-то пошло не так"), HttpStatus.FORBIDDEN);
        }
    }



    @Operation(summary = "удаление диалога")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Dialogue.class))})
    @DeleteMapping("/{dialogueId}")
    public ResponseEntity<?> deleteDialogue(@PathVariable("dialogueId") Long dialogueId){
        LOGGER.info("DELETE DIALOGUE");
        dialogueService.deleteDialogueById(dialogueId);
        return ResponseEntity.ok("диалог удален");
    }

}
