package com.khajana.setting.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer extends JsonDeserializer<Date> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String dateAsString = parser.getText();

        try {
            DATE_FORMAT.setLenient(false); // To enforce strict date parsing
            return DATE_FORMAT.parse(dateAsString);
        } catch (ParseException e) {
            throw new InvalidFormatException(
                    parser,
                    "Invalid date format. Date should be in yyyy-MM-dd format.",
                    dateAsString,
                    Date.class
            );
        }
    }
}
