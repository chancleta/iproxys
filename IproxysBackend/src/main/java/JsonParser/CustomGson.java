package JsonParser;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by lupena on 2/5/2016.
 */
public class CustomGson {

    private static Gson Gson;
    public static Gson Gson(){
        if(Gson == null){

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Long.class,new LongTypeAdapter());
            Gson = gsonBuilder.create();

        }
        return Gson;
    }
}

class LongTypeAdapter extends TypeAdapter<Long>{
    @Override
    public Long read(JsonReader reader) throws IOException {
        if(reader.peek() == JsonToken.NULL){
            reader.nextNull();
            return null;
        }
        try{
            String stringValue = reader.nextString();
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