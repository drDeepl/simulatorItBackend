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
import ru.simbirgo.dtos.DialogueTextDTO;
import ru.simbirgo.dtos.MessageDTO;
import ru.simbirgo.exceptions.AppException;
import ru.simbirgo.models.*;
import ru.simbirgo.models.Character;
import ru.simbirgo.payloads.NewAnswerRequest;
import ru.simbirgo.payloads.NewDialogueRequest;
import ru.simbirgo.payloads.NewDialogueTextRequest;
import ru.simbirgo.services.CharacterService;
import ru.simbirgo.services.DialogueService;

import java.util.List;
import java.util.Locale;

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

    @Operation(summary="добавление ответа к фразе по id диалога")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Answer.class))})
    @PostMapping("/dialogue_text/answer/new/{dialogueTextId}")
    public ResponseEntity<?> addAnswerToDialogueById(@PathVariable("dialogueTextId") Long dialogueTextId, @RequestBody  NewAnswerRequest newAnswerRequest){
        LOGGER.info("ADD ANSWER TO DIALOGUE BY ID DIALOGUE");
        try{
            LOGGER.info("ANSWER: " + newAnswerRequest.getAnswer());
            Answer newAnswerForDialogueText =  dialogueService.createAnswerDialogueText((long) dialogueTextId, newAnswerRequest.getAnswer());
            LOGGER.info("created answer");
            return new ResponseEntity<Answer>(newAnswerForDialogueText, HttpStatus.OK);
        }
        catch (NullPointerException NPE){
            LOGGER.error(NPE.getMessage());
            return new ResponseEntity<>(new AppException(HttpStatus.NOT_FOUND.value(), NPE.getMessage()), HttpStatus.NOT_FOUND);
        }
    }



    @Operation(summary = "получение всех фраз диалога по id")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = DialogueTextDTO.class))})
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


    @Operation(summary = "получение ответов к фразе диалога по id фразы(dialogueText) диалога")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array=@ArraySchema(schema=@Schema(implementation = String.class)))})
    @GetMapping("/dialogue_text/answer/{dialogueTextId}")
    public ResponseEntity<?> getAnswerByDialogueId(@PathVariable("dialogueTextId") Long dialogueId){
        try{
            List<String> answers = dialogueService.getAnswersByDialogueTextId(dialogueId);

            return new ResponseEntity<>(answers, HttpStatus.OK);
        }
        catch (RuntimeException E){
            LOGGER.info(E.getMessage());
            return new ResponseEntity<>(new AppException(HttpStatus.FORBIDDEN.value(), "что-то пошло не так"), HttpStatus.FORBIDDEN);
        }
    }


    @Operation(summary="получение всех фраз для всех диалогов")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = DialogueText.class))})
    @GetMapping("/dialogue_text")
    public ResponseEntity<?> getTextsForAllDialogues(){
        LOGGER.info("GET TEXTS  FOR ALL DIALOGUES");
        List<DialogueText> texts = dialogueService.getTexts();
        return new ResponseEntity<List<DialogueText>>(texts, HttpStatus.OK);
    }

    @Operation(summary = "удаление диалога")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Dialogue.class))})
    @DeleteMapping("/{dialogueId}")
    public ResponseEntity<?> deleteDialogue(@PathVariable("dialogueId") Long dialogueId){
        LOGGER.info("DELETE DIALOGUE");
        dialogueService.deleteDialogueById(dialogueId);
        return ResponseEntity.ok("диалог удален");
    }

    @Operation(summary="удаление фразы диалога по её id")
    @DeleteMapping("/{dialogueTextId}")
    public ResponseEntity<MessageDTO> deleteDialogueTextById(@PathVariable("dialogueTextId") Long dialogueTextId){
        LOGGER.info("DELETE DIALOGUE TEXT BY ID");
        boolean isDeleted = dialogueService.deleteDialogueTextById(dialogueTextId);
        if(isDeleted){
            return ResponseEntity.ok(new MessageDTO("удаление прошло успешно"));
        }
        return new ResponseEntity<MessageDTO>(new MessageDTO("во время удаление фразы произошла ошибка"), HttpStatus.FORBIDDEN);
    }

}
