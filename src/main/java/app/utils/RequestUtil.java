package app.utils;

import spark.Request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Vicky on 04/07/2016.
 */
public class RequestUtil {
    public static Result validateFields(Map<String, String> formData) {

        for (String key: formData.keySet()) {
            String value = formData.get(key);
            System.out.format("Validating field %s --> %s%n", key, value);
            if (value == null || value.equals("")) {
                return new Result(false, null, String.format("Error validando el campo: %s",value));
            }
        }

        return new Result(true, null, "Validacion correcta de campos");
    }

}
