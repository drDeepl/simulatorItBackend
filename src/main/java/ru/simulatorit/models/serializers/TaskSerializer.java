package ru.simbirgo.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.simbirgo.models.Answer;
import ru.simbirgo.models.Task;

import java.io.IOException;

public class TaskSerializer extends StdSerializer<Task> {

    public TaskSerializer(){
        this(null);
    }
    public TaskSerializer(Class<Task> r){
        super(r);
    }

    @Override
    public void serialize(Task task, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("taskId", task.getId());
        jsonGenerator.writeNumberField("professionId", task.getProfession().getId());
        jsonGenerator.writeStringField("description", task.getDescription());
        jsonGenerator.writeEndObject();
    }
}
