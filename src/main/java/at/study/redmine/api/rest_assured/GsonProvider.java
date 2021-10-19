package at.study.redmine.api.rest_assured;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * первым в скобке registerTypeAdapter идет
 * класс который преобразуется-LocalDateTime.class
 * <p>
 * вторым в скобке идет сериализатор, соответственно для второго
 * registerTypeAdapter в скобке идет десериализатор
 */
public class GsonProvider {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())          //десериализатор -
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())       // сериализатор
            .create();

    // тут описываем ДеСериализатор - делаем LocalDateTime из Json
    public static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext)
                throws JsonParseException {
            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
    }

    //ту описываем Сериализатор - делаем Json из LocalDateTime
    public static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(src.format(DateTimeFormatter.ISO_DATE_TIME));
        }
    }

}


