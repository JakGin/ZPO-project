package com.example.client;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom Gson TypeAdapter for serializing and deserializing LocalDateTime objects.
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    /**
     * Default constructor.
     * <p>
     * This constructor is used to create an instance of the {@code LocalDateTimeAdapter} class.
     * </p>
     */
    public LocalDateTimeAdapter() {
        // Initialize any necessary components or perform setup if needed.
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    /**
     * Writes a LocalDateTime object to JSON.
     *
     * @param out   The JsonWriter to write to.
     * @param value The LocalDateTime object to be serialized.
     * @throws IOException If an I/O error occurs during writing.
     */
    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(formatter.format(value));
    }
    /**
     * Reads a LocalDateTime object from JSON.
     *
     * @param in The JsonReader to read from.
     * @return The deserialized LocalDateTime object.
     * @throws IOException If an I/O error occurs during reading.
     */
    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return LocalDateTime.parse(in.nextString(), formatter);
    }
}
