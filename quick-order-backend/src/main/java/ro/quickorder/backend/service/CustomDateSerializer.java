package ro.quickorder.backend.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CustomDateSerializer extends JsonSerializer<Timestamp> {
    private static final Logger LOG = LoggerFactory.getLogger(CustomDateSerializer.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String formattedDate = DATE_FORMAT.format(value);
        gen.writeString(formattedDate);
    }
}
