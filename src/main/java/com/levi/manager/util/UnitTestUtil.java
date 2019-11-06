package com.levi.manager.util;

import java.util.Arrays;
import java.util.List;

public class UnitTestUtil {

    private static final Integer FIRST_PARAMETER = 0;

    public static String transformValuesInRequestParameters(Object... objects) {
        List<Object> objectList = Arrays.asList(objects);
        StringBuilder result = new StringBuilder();
        result.append(objectList.get(FIRST_PARAMETER).toString());
        for(Object object : objectList) {
            result.append(",").append(object.toString());
        }
        return result.toString();
    }

}
