/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

import java.io.IOException;
import java.util.*;

public class Language {

    public static HashMap getLanguageMap() {
        HashMap hashMap = new HashMap();
        Properties properties = new Properties();
        try {
            properties.load(Language.class.getClassLoader().getResourceAsStream("subtitlergui/Languages.properties"));
            Set set = properties.entrySet();
            Map.Entry entry = null;
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                entry = (Map.Entry) iterator.next();
                hashMap.put(entry.getKey().toString(), entry.getValue().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public static ArrayList getLanguageNames() {

        ArrayList arrayList = new ArrayList();
        Properties properties = new Properties();
        try {
            properties.load(Language.class.getClassLoader().getResourceAsStream("subtitlergui/Languages.properties"));
            Set set = properties.entrySet();
            Map.Entry entry = null;
            Iterator iterator = set.iterator();
            String s = null;
            while (iterator.hasNext()) {
                entry = (Map.Entry) iterator.next();
                arrayList.add(entry.getKey().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
