package com.cube.friend.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtility {

    public static <T> List<T> type(List<?> list) {
        List<T> newList = new ArrayList<>();
        for(Object object: list) {
            newList.add((T) object);
        }
        return newList;
    }

}
