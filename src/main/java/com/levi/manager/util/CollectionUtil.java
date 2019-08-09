package com.levi.manager.util;

import java.util.Collection;

public class CollectionUtil {

    public static boolean isNotEmpty(Collection<Object> collection) {
        return collection != null && !collection.isEmpty();
    }

}
