package examples;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Example working with JSON data. The data consists of an array with two objects in it.
 * Each object has two key-value pairs in it.
 */
public class JSONDemo {
    /**
     * A first example of working with JSON data.
     * @param args not used
     */
    public static void main(String[] args) {
        String jsonData = "[{\"key1\" : \"string1a\", \"key2\":21}," +
                           "{\"key1\" : \"string1b\", \"key2\":22}]";
        JSONArray jsonArray = new JSONArray(jsonData);
        System.out.println(jsonArray);
        System.out.println(jsonArray.length());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonObject);
        System.out.println(jsonObject.getString("key1"));
        System.out.println(jsonObject.getInt("key2"));
    }
}
