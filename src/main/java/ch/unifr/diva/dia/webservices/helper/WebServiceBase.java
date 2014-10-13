package ch.unifr.diva.dia.webservices.helper;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Marcel Würsch on 02.10.2014.
 */
public class WebServiceBase {

    public static JSONArray createJsonArray(List<Map<String, Object>> values) {
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> map : values) {
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                try {
                    jsonObject.put(entry.getKey(), entry.getValue().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}

