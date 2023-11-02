package ru.simbirgo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirgo.models.Character;
import ru.simbirgo.models.Dialogue;
import ru.simbirgo.models.DialogueText;
import ru.simbirgo.payloads.NewDialogueRequest;
import ru.simbirgo.payloads.NewDialogueTextRequest;
import ru.simbirgo.repositories.DialogueRepository;
import ru.simbirgo.repositories.DialogueTextRepository;

import java.util.List;

@Service
public class DialogueService {
    private final Logger LOGGER = LoggerFactory.getLogger(DialogueService.class);

    @Autowired
    DialogueRepository dialogueRepository;

    @Autowired
    DialogueTextRepository dialogueTextRepository;

    public Dialogue newDialogue(NewDialogueRequest newDialogueRequest, Character character){
        LOGGER.info("NEW DIALOGUE");
        Dialogue newDialogue = new Dialogue();
        newDialogue.setName(newDialogueRequest.getName());
        newDialogue.setCharacter(character);
        return dialogueRepository.save(newDialogue);
    }

    public Dialogue getDialogueById(Long dialogueId){
        LOGGER.info("GET DIAlOGUE BY ID");
        return dialogueRepository.findById(dialogueId).get();
    }

    public DialogueText addTextToDialogueById(Dialogue dialogue, NewDialogueTextRequest newDialogueTextRequest){
        LOGGER.info("ADD TEXT TO DIALOGUE BY ID");
        DialogueText newDialogueText = new DialogueText();
        newDialogueText.setText(newDialogueTextRequest.getText());
        newDialogueText.setDialogue(dialogue);
        return dialogueTextRepository.save(newDialogueText);

    }

    public List<DialogueText> getTextsByDialogueId(Long dialogueId){
        LOGGER.info("GET TEXTS BY DIALOGUE ID");
        return dialogueTextRepository.findAllByDialogueId(dialogueId);
    }

    public void deleteDialogueById(Long id){
        LOGGER.info("DELETE DIALOGUE BY ID");
        dialogueRepository.deleteById(id);
    }


}
