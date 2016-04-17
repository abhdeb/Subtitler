/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: abhdeb
 * Date: Feb 13, 2012
 * Time: 2:12:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class DvdCover implements SubtitlerConstants {
    public static final ArrayList dvdCoverList = new ArrayList();

    public static void main(String[] args) throws IOException {
        new DvdCover().getCoverURL("Inception");
    }

    public static void clearDVDCovers() {
        if (null != dvdCoverList && !dvdCoverList.isEmpty()) {
            dvdCoverList.removeAll(dvdCoverList);
        }
    }

    public ArrayList getCoverURL(String movieName) throws IOException {
        ArrayList coverList = new ArrayList();
        String params = URLEncoder.encode(movieName + " cd OR dvd " + GOOGLE_IMAGE_SITE, "UTF-8");
        URL freecoversURL = new URL(GOOGLE_IMAGE + params);
        //System.out.println(freecoversURL.getPath());
        URLConnection connnection = freecoversURL.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(connnection.getInputStream(), "UTF-8"));

        String thisLine;
        StringBuffer allSource = new StringBuffer("");

        while ((thisLine = in.readLine()) != null) {
            allSource.append(thisLine).append(" ");
        }
        in.close();

        if (null != allSource && allSource.length() > 0) {
            String src = allSource.toString();
            String[] srcArray = null;
            if (src.indexOf(GOOGLE_TOKENIZER) != -1) {
                srcArray = src.split(GOOGLE_TOKENIZER);
                if (null != srcArray && srcArray.length > 0) {
                    String url = null;
                    for (int i = 0; i < srcArray.length; i++) {
                        if (srcArray[i].indexOf(GOOGLE_URL) != -1) {
                            url = srcArray[i].substring(srcArray[i].indexOf(GOOGLE_URL) + GOOGLE_URL.length(), srcArray[i].length());
                            if (url.indexOf(",") != -1) {
                                url = url.substring(0, url.indexOf(","));
                                if (url.indexOf("\"") != -1) {
                                    url = url.replaceAll("\"", "");
                                }
                                if (null != url) {
                                    url = url.substring(1, url.length());
                                    url = url.trim();
                                    if (!coverList.contains(url)) {
                                        String mimeType = new MimetypesFileTypeMap().getContentType(url);
                                        //System.out.println(mimeType);
                                        if (mimeType.indexOf("image") != -1) {
                                            coverList.add(url);
                                        }
                                    }
                                }
                                //System.out.println(url);
                            }
                        }
                    }
                }
            }

        }
        return coverList;
    }

    public LinkedHashMap saveCovers(ArrayList coverList, String movieName) throws IOException {
        LinkedHashMap hashMap = new LinkedHashMap();
        String path1 = System.getProperty("java.io.tmpdir");
        System.out.println("COVER PATH ### "+path1);
        if (null != coverList && !coverList.isEmpty()) {
            String uri = null;

            File file1 = new File(path1+"tmpImages");
                if (!file1.exists()) {
                    file1.mkdir();
                }
            DVDCoverDTO dvdCoverDTO = null;
            System.out.println("COVER PATH ### 11 "+file1.getPath());

            for (int i = 0; i < coverList.size(); i++) {
                uri = coverList.get(i).toString();
                URL url = new URL(uri);
                String path = url.getPath();
                String ext = null;
                if (null != path && !"".equals(path)) {
                    if (path.indexOf(".") != -1) {
                        ext = path.substring(path.indexOf(".") + 1, path.length());
                    }
                }
                if (null != ext && !"".equals(ext)) {
                    InputStream is = url.openStream();
                    OutputStream os = new FileOutputStream(file1.getPath() + "\\" + movieName + "_" + i + "." + ext);
                    byte[] b = new byte[2048];
                    int length;

                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }

                    is.close();
                    os.close();

                    BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
                    if (null != img) {
                        img.createGraphics().drawImage(ImageIO.read(new File(file1.getPath() + "\\" + movieName + "_" + i + "." + ext)).getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null);
                        ImageIO.write(img, ext, new File(file1.getPath() + "\\" + movieName + "_Preview_" + i + "." + ext));

                        dvdCoverDTO = new DVDCoverDTO();
                        dvdCoverDTO.setMovieName(movieName);
                        dvdCoverDTO.setImgUrl(uri);
                        dvdCoverDTO.setLocalMainFilePath(file1.getPath() + "\\" + movieName + "_" + i + "." + ext);
                        dvdCoverDTO.setLocalPreviewFilePath(file1.getPath() + "\\" + movieName + "_Preview_" + i + "." + ext);
                        dvdCoverList.add(dvdCoverDTO);
                        hashMap.put(uri, dvdCoverDTO);
                    }
                }
            }
        }
        return hashMap;
    }
}
