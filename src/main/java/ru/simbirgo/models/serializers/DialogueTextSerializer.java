package ru.simbirgo.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.simbirgo.models.Character;
import ru.simbirgo.models.DialogueText;

import java.io.IOException;

public class DialogueTextSerializer extends StdSerializer<DialogueText> {
    public DialogueTextSerializer(){
        this(null);
    }

    public DialogueTextSerializer(Class<DialogueText> r ){
        super(r);
    }

    @Override
    public void serialize(DialogueText dialogueText, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", dialogueText.getId());
        jsonGenerator.writeStringField("text", dialogueText.getText());
        jsonGenerator.writeNumberField("dialogueId", dialogueText.getDialogue().getId());
        jsonGenerator.writeEndObject();
    }


}
