package com.xeeshi.nsoft.Utils;

import com.activeandroid.serializer.TypeSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ZEESHAN on 16/08/16.
 */
public class UtilListSerializer extends TypeSerializer {
    @Override
    public Class<?> getDeserializedType() {
        return List.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public String serialize(Object o) {
        if (o == null) {
            return null;
        }

        String str = "";
        for (String s : (List<String>) o) {
            str = str + s + ",";
        }

        return str;
    }

    @Override
    public List<String> deserialize(Object o) {
        if (o == null) {
            return null;
        }

        List<String> strings = new ArrayList<>();
        String str = (String) o;
        Collections.addAll(strings, str.split(","));

        return strings;
    }
}