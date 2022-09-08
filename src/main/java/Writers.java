import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Writers {

    public static String listToJson(List<Employee> staff) {
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(staff, listType);
    }

    public static void writeString(String json, String fileName) {
        try(FileWriter writer = new FileWriter(fileName)) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Employee> jsonToList(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString, new TypeToken<List<Employee>>() {
        }.getType());
    }
}
