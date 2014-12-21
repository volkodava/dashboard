package com.infusion.ui.converter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ExtJsConverter {

    public static final String DATA_FIELD = "data";
    public static final String SUCCESS_FLAG_FIELD = "success";
    public static final String TOTAL_COLLECTION_SIZE_FIELD = "total";

    public <T> Map<String, Object> transformSuccess(Collection<T> items) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put(TOTAL_COLLECTION_SIZE_FIELD, items.size());
        modelMap.put(DATA_FIELD, items);
        modelMap.put(SUCCESS_FLAG_FIELD, true);

        return modelMap;
    }

    public <T> Map<String, Object> transformSuccess(T item) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put(DATA_FIELD, item);
        modelMap.put(SUCCESS_FLAG_FIELD, true);

        return modelMap;
    }

    public <T> Map<String, Object> transformError(String message, Throwable t) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("message", message);
        modelMap.put("success", false);

        return modelMap;
    }
}
