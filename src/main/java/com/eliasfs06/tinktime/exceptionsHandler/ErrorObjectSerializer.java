package com.eliasfs06.tinktime.exceptionsHandler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ErrorObjectSerializer extends JsonSerializer<ErrorObject> {
    @Override
    public void serialize(ErrorObject errorObject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("message", errorObject.getMessage());
        jsonGenerator.writeStringField("field", errorObject.getField());
        jsonGenerator.writeObjectField("parameter", errorObject.getParameter());
        jsonGenerator.writeEndObject();
    }
}