package ru.simbirgo.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.simbirgo.models.Character;
import ru.simbirgo.models.Profession;

import java.io.IOException;

public class CharacterSerializer extends StdSerializer<Character> {

    public CharacterSerializer(){
        this(null);
    }

    public CharacterSerializer(Class<Character> r){
        super(r);
    }

    @Override
    public void serialize(Character character, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", character.getId());
        jsonGenerator.writeStringField("name", character.getName());
        jsonGenerator.writeNumberField("professionId", character.getProfession().getId());
        jsonGenerator.writeEndObject();
    }


}
