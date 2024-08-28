package com.khajana.setting.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailDeserializer extends JsonDeserializer<String> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String email = parser.getText();
        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if (matcher.matches()) {
            return email;
        } else {
            throw new IllegalArgumentException(parser.getCurrentName() + " - Invalid email format");
        }
    }

}