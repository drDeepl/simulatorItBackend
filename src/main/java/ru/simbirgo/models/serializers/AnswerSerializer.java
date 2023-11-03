package ru.simbirgo.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.simbirgo.models.Answer;
import ru.simbirgo.models.DialogueText;

import java.io.IOException;

public class AnswerSerializer extends StdSerializer<Answer> {

    public AnswerSerializer(Class<Answer> r){
        super(r);
    }

    @Override
    public void serialize(Answer answer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", answer.getId());
        jsonGenerator.writeStringField("dialogueText", answer.getDialogueText().getText());
        if(answer.getTextAnswer() == null){
            jsonGenerator.writeNullField("textAnswer");
        }
        else{
        jsonGenerator.writeStringField("textAnswer", answer.getTextAnswer());
        }

        jsonGenerator.writeEndObject();
    }




}
