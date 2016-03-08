package validator;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by lupena on 2/5/2016.
 */

public class LongTypeAdapter extends TypeAdapter<Long> {
    @Override
    public Long read(JsonReader reader) throws IOException {
        if(reader.peek() == JsonToken.NULL){
            reader.nextNull();
            return null;
        }
        try{
            String stringValue = reader.nextString();
            System.out.println("tried");
            Long value = Long.valueOf(stringValue);
            return value;
        }catch(NumberFormatException e){
            return null;
        }
    }
    @Override
    public void write(JsonWriter writer, Long value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}