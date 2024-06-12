package com.yuanno.blockclover.util;

import java.util.HashMap;

public class BeRegistry {

    private static HashMap<String, String> langMap = new HashMap<String, String>();

    public static HashMap<String, String> getLangMap()
    {
        return langMap;
    }
}
