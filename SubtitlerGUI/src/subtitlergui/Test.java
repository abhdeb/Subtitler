/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by IntelliJ IDEA.
 * User: abhdeb
 * Date: Feb 17, 2012
 * Time: 12:50:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        /*URL freecoversURL = new URL("http://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=Transit+cd+OR+dvd+site:covershut.com");
        System.out.println(freecoversURL.getPath());
        URLConnection connnection = freecoversURL.openConnection();
        System.out.println(freecoversURL.getFile());

        BufferedReader in = new BufferedReader(new InputStreamReader(connnection.getInputStream(), "UTF-8"));

        String thisLine;
        StringBuffer allSource = new StringBuffer("");

        while ((thisLine = in.readLine()) != null) {
            allSource.append(thisLine).append(" ");
        }
        in.close();*/
        //String s = URLDecoder.decode("C:/Development/SubtitlerGUI/SubtitlerGUI/out/production/SubtitlerGUI/tmpImages/Final%20Destination%205_7.jpg", "UTF-8");
        //System.out.println(s);
        /*BASE64Encoder base64Encoder = new BASE64Encoder();
        String d = "OS Test User Agent";
        String s = base64Encoder.encode(d.getBytes());
        System.out.println(s);

        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] b = base64Decoder.decodeBuffer(s);
        System.out.println(new String(b));*/
        System.getProperties();
    }
}
