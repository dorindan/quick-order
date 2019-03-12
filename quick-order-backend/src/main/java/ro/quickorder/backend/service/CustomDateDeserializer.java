package ro.quickorder.backend.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Timestamp> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    @Override
    public Timestamp deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationContext) throws IOException {
        try {
            Date parsedDate = DATE_FORMAT.parse(jsonParser.getText());
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

}