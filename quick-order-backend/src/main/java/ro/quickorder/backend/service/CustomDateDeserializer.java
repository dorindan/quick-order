package ro.quickorder.backend.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.quickorder.backend.exception.NotAcceptableException;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Timestamp> {
    private static final Logger LOG = LoggerFactory.getLogger(CustomDateDeserializer.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("dd+MM+yyyy+HH:mm");

    @Override
    public Timestamp deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext) throws IOException {
        try {
            Date parsedDate = DATE_FORMAT.parse(jsonParser.getText());
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            LOG.error("Could not parse " + jsonParser.getText(), e);
            throw new RuntimeException(e);
        }
    }

    public static Timestamp deserialize(String timestampAsString) {
        try {
            Date parsedDate = DATE_FORMAT2.parse(timestampAsString);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            LOG.error("Could not deserialize string as timestamp: " + timestampAsString, e);
            throw new NotAcceptableException("Could not deserialize string as timestamp");
        }
    }
}
