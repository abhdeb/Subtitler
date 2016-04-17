/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.tools.ant.taskdefs.Untar;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.misc.BASE64Decoder;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SubtitlerUtil implements SubtitlerConstants {

    private static final void copyInputStream(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);

        in.close();
        out.close();
    }


    private static void listContentsOfZipFile(String tmpZipFile, String subName, String parent) {

        try {
            ZipFile zipFile = new ZipFile(tmpZipFile);

            Enumeration zipEntries = zipFile.entries();

            while (zipEntries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) zipEntries.nextElement();
                String zipContent = entry.getName();
                if (zipContent.equalsIgnoreCase(subName)) {
                    copyInputStream(zipFile.getInputStream(entry),
                            new BufferedOutputStream(new FileOutputStream(parent + "/" + zipContent)));

                }
            }
            zipFile.close();
            File f = new File(tmpZipFile);
            f.delete();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void downloadSubZip(String subUrl, String parent, String subName) throws Exception {
        URLConnection con = null;
        URL url = new URL(subUrl);
        con = url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("content-type", "binary/data");
        InputStream in = con.getInputStream();

        FileOutputStream fout = new FileOutputStream(parent + "/tmp.zip");
        int count;
        byte[] buffer = new byte[1024];
        while ((count = in.read(buffer)) > 0) {
            fout.write(buffer, 0, count);
        }
        fout.close();
        in.close();

        listContentsOfZipFile(parent + "/tmp.zip", subName, parent);
    }

    public static void unGZip(byte[] theBytes, String tarFileName, String filePath, String parent) throws Exception {

        InputStream in = new ByteArrayInputStream(theBytes);
        FileOutputStream fout = new FileOutputStream(filePath);
        int count;
        byte[] buffer = new byte[1024];
        while ((count = in.read(buffer)) > 0) {
            fout.write(buffer, 0, count);
        }
        fout.close();
        in.close();


        GZIPInputStream in2 = new GZIPInputStream(new FileInputStream(filePath));
        OutputStream out = new FileOutputStream(tarFileName);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in2.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in2.close();
        out.close();

        Untar untar = new Untar();
        untar.setSrc(new File(parent + tarFileName));
        untar.setDest(new File(parent));
        untar.execute();
    }

    public static byte[] inputStreamToBytes(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);

        in.close();
        out.close();
        return out.toByteArray();
    }

    public static String getIMDBFromNFO(File nfoFile) throws IOException {
        String imdbID = null;
        Pattern pattern = Pattern.compile(IMDB_EXP, Pattern.CASE_INSENSITIVE);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(nfoFile)));
        String line = null;
        Matcher matcher = null;
        while ((line = bufferedReader.readLine()) != null) {
            matcher = pattern.matcher(line);
            boolean matched = matcher.find();
            if (matched) {
                pattern = Pattern.compile("tt(\\d+)", Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(line);
                matched = matcher.find();
                if (matched) {
                    int start = matcher.start();
                    int end = matcher.end();
                    imdbID = line.substring(start + 2, end);
                    //System.out.println(imdbID);
                    return imdbID;
                }
            }
        }
        return imdbID;
    }

    public static String getImdbDetails(SubtitlerImdbDTO subtitlerImdbDTO) {
        String baseHtml = "<div><img src=\"" + subtitlerImdbDTO.getCover() + "\" width=91 " +
                "height=140></div><div style=\"font:11pt Thoma\"><table width=100%><tr><td><b>AKA</b></td><td>" + subtitlerImdbDTO.getAka() + "</td></tr><tr>" +
                "<td><b>Kind</b></td>" + subtitlerImdbDTO.getKind() + "<td></td></tr><tr><td><b>Cast</b></td>" +
                "<td>" + subtitlerImdbDTO.getCast() + "</td></tr><tr><td><b>Writers</b></td>" + subtitlerImdbDTO.getWriters() + "<td></td></tr>" +
                "<tr><td><b>Directors</b></td><td>" + subtitlerImdbDTO.getDirectors() + "</td></tr><tr><td><b>IMDB Rating</b></td>" +
                "<td>" + subtitlerImdbDTO.getRating() + "</td></tr><tr><td><b>Genre</b></td><td>" + subtitlerImdbDTO.getGenres() + "</td></tr>" +
                "<tr><td><b>Vote</b></td><td>" + subtitlerImdbDTO.getVotes() + "</td></tr>" +
                "<tr><td><b>Rating</b></td><td>" + subtitlerImdbDTO.getRating() + "</td></tr><tr><td><b>Year</b></td>" +
                "<td>" + subtitlerImdbDTO.getYear() + "</td></tr>" +
                "<tr><td><b>Country</b></td><td>" + subtitlerImdbDTO.getCountry() + "</td></tr>" +
                "<tr><td><b>Plot</b></td><td>" + subtitlerImdbDTO.getPlot() + "</td></tr>" +
                "<tr><td><b>Title</b></td><td>" + subtitlerImdbDTO.getTitle() + "</td></tr></table></div>";
        return baseHtml;
    }

    public static String getDVDCoverHTML(LinkedHashMap hashMap, String title, String year) {
        String baseHtml = "<table><tr><td style=\"font:11pt Times, serif\"><b><u>" + title + " (" + year + ")</u></b></td></tr>";
        if (null != hashMap && !hashMap.isEmpty()) {
            Set set = hashMap.entrySet();
            Iterator iterator = set.iterator();
            Map.Entry entry = null;
            File f = null;
            ArrayList list = new ArrayList();
            while (iterator.hasNext()) {
                entry = (Map.Entry) iterator.next();
                DVDCoverDTO dvdCoverDTO = (DVDCoverDTO) entry.getValue();
                list.add(dvdCoverDTO);
            }
            if (null != list && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    DVDCoverDTO dvdCoverDTO = (DVDCoverDTO) list.get(i);
                    f = new File(dvdCoverDTO.getLocalPreviewFilePath());
                    if (i != 0 && i % 4 == 0) {
                        baseHtml += "</tr>";
                    }
                    if (i % 4 == 0) {
                        baseHtml += "<tr>";
                    }

                    baseHtml += "<td><table><tr><td> <img src=\"" + f.toURI().toString() + "\" width=91 height=140> </td></tr>" +
                            "<tr><td style=\"font:11pt Times, serif\"> <a href=\"" + f.toURI().toString() + "\" style=\"font:11pt Times, serif\">" +
                            "preview</a> </td></tr></table></td>";

                }
            }
            baseHtml += "</tr>";
        }
        baseHtml += "</table>";
        return baseHtml;
    }

    public static String getObjArrToStr(Object obj[]) {
        String str = null;
        if (null != obj && obj.length > 0) {
            for (int i = 0; i < obj.length; i++) {
                if (i == 0) {
                    str = obj[i].toString();
                } else {
                    str += ", " + obj[i].toString();
                }
            }
        }
        return str;
    }

    public static String getIMDBFromParentNonRecursive(File parent) throws IOException {
        String imdbID = null;
        String[] ext = {"nfo"};
        Collection files = FileUtils.listFiles(
                parent,
                ext,
                false
        );
        //System.out.println("");
        if (null != files && !files.isEmpty()) {
            LinkedList linkedList = (LinkedList) files;
            for (int i = 0; i < linkedList.size(); i++) {
                File file = (File) linkedList.get(i);
                if (file.exists() && file.isFile()) {
                    imdbID = SubtitlerUtil.getIMDBFromNFO(file);
                    if (null != imdbID && !"".equals(imdbID) && imdbID.length() > 0) {
                        return imdbID;
                    }
                    //System.out.println("");
                }
            }
        }
        return imdbID;
    }

    public static String getIMDBFromParentRecursive(File parent) throws IOException {
        String imdbID = null;
        Collection files = FileUtils.listFiles(
                parent,
                new RegexFileFilter("^(.*?nfo)"),
                DirectoryFileFilter.DIRECTORY
        );
        //System.out.println("");
        if (null != files && !files.isEmpty()) {
            LinkedList linkedList = (LinkedList) files;
            for (int i = 0; i < linkedList.size(); i++) {
                File file = (File) linkedList.get(i);
                if (file.exists() && file.isFile()) {
                    imdbID = SubtitlerUtil.getIMDBFromNFO(file);
                    if (null != imdbID && !"".equals(imdbID) && imdbID.length() > 0) {
                        return imdbID;
                    }
                    //System.out.println("");
                }
            }
        }
        return imdbID;
    }

    public static String getIMDBIDScraping(String title) throws IOException {
        String imdbID = null;
        if (null != title) {
            Pattern pattern = Pattern.compile(FILE_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(title);
            boolean matched = matcher.find();
            if (matched) {
                title = matcher.replaceAll(" ");
                if (title.indexOf(".") != -1) {
                    title = title.substring(0, title.lastIndexOf("."));
                    if (null != title) {
                        title = title.trim();
                        if (title.indexOf(".") != -1) {
                            title = title.replace(".", " ");
                        }
                    }
                }
            }
            if (null != title && !"".equals(title) && title.length() > 0) {
                String params = URLEncoder.encode(title, "UTF-8");
                URL scrapingURL = new URL(IMDB_SCRAPING_URL + params);

                URLConnection connnection = scrapingURL.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connnection.getInputStream(), "UTF-8"));

                String thisLine;
                StringBuffer allSource = new StringBuffer("");

                while ((thisLine = in.readLine()) != null) {
                    allSource.append(thisLine).append(" ");
                }
                in.close();

                if (null != allSource && allSource.length() > 0) {
                    pattern = Pattern.compile(IMDB_SCRAPING_EXP, Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(allSource.toString());
                    matched = matcher.find();
                    if (matched) {
                        int start = matcher.start();
                        int end = matcher.end();
                        imdbID = allSource.toString().substring(start + 3, end);
                        //System.out.println(imdbID);
                    }
                }
            }
        }
        return imdbID;
    }

    public static VersionDTO readLocalVersionInfo(File versionFile) {
        VersionDTO versionDTO = null;
        if (null != versionFile && versionFile.exists()) {
            try {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(versionFile);
                doc.getDocumentElement().normalize();
                NodeList subtitler = doc.getElementsByTagName(SUBTITLER);
                int childCount = subtitler.getLength();
                String version = null;
                String dlURLWin = null;
                String dlURLOsx = null;
                String dlURLOther = null;
                String dlURLSize = null;
                for (int s = 0; s < childCount; s++) {
                    Node firstNode = subtitler.item(s);
                    if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element subtitlerElement = (Element) firstNode;

                        NodeList versionList = subtitlerElement.getElementsByTagName(VERSION);
                        Element versionElement = (Element) versionList.item(0);
                        NodeList textVersionList = versionElement.getChildNodes();
                        version = ((Node) textVersionList.item(0)).getNodeValue().trim();
                        //System.out.println("Current Version : " + version);

                        NodeList urlList = subtitlerElement.getElementsByTagName(DL_URL_WIN);
                        Element urlElement = (Element) urlList.item(0);
                        NodeList textURLList = urlElement.getChildNodes();
                        dlURLWin = ((Node) textURLList.item(0)).getNodeValue().trim();
                        //System.out.println("Current URL WIN : " + dlURLWin);

                        NodeList urlOSXList = subtitlerElement.getElementsByTagName(DL_URL_OSX);
                        Element urlOSXElement = (Element) urlOSXList.item(0);
                        NodeList textURLOSXList = urlOSXElement.getChildNodes();
                        dlURLOsx = ((Node) textURLOSXList.item(0)).getNodeValue().trim();
                        //System.out.println("Current URL OSX : " + dlURLOsx);

                        NodeList urlOtherList = subtitlerElement.getElementsByTagName(DL_URL_OTHER);
                        Element urlOtherElement = (Element) urlOtherList.item(0);
                        NodeList textURLOtherList = urlOtherElement.getChildNodes();
                        dlURLOther = ((Node) textURLOtherList.item(0)).getNodeValue().trim();
                        //System.out.println("Current URL Other : " + dlURLOther);

                        NodeList urlSizeList = subtitlerElement.getElementsByTagName(DL_SIZE);
                        Element urlSizeElement = (Element) urlSizeList.item(0);
                        NodeList textURLSizeList = urlSizeElement.getChildNodes();
                        dlURLSize = ((Node) textURLSizeList.item(0)).getNodeValue().trim();
                        //System.out.println("Current URL Other : " + dlURLSize);

                        versionDTO = new VersionDTO();
                        versionDTO.setVersion(version);
                        versionDTO.setUrlwin(dlURLWin);
                        versionDTO.setUrlosx(dlURLOsx);
                        versionDTO.setDlURLSize(dlURLSize);
                        versionDTO.setUrlother(dlURLOther);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return versionDTO;
    }

    public static VersionDTO readVersionInfo(String uri) {
        VersionDTO versionDTO = null;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            URL url = new URL(uri);
            URLConnection connnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connnection;
            httpURLConnection.setInstanceFollowRedirects(false);

            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            while ((responseCode / 100) == 3) {
                String newLocationHeader = httpURLConnection.getHeaderField(HTTP_LOCATION);
                //System.out.println(newLocationHeader);
                url = new URL(newLocationHeader);
                connnection = url.openConnection();
                httpURLConnection = (HttpURLConnection) connnection;
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                Document doc = docBuilder.parse(inputStream);
                doc.getDocumentElement().normalize();
                NodeList subtitler = doc.getElementsByTagName(SUBTITLER);
                int childCount = subtitler.getLength();
                String version = null;
                String dlURLWin = null;
                String dlURLOsx = null;
                String dlURLOther = null;
                String dlURLSize = null;
                for (int s = 0; s < childCount; s++) {
                    Node firstNode = subtitler.item(s);
                    if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element subtitlerElement = (Element) firstNode;

                        NodeList versionList = subtitlerElement.getElementsByTagName(VERSION);
                        Element versionElement = (Element) versionList.item(0);
                        NodeList textVersionList = versionElement.getChildNodes();
                        version = ((Node) textVersionList.item(0)).getNodeValue().trim();
                        //System.out.println("Current Version : " + version);

                        NodeList urlList = subtitlerElement.getElementsByTagName(DL_URL_WIN);
                        Element urlElement = (Element) urlList.item(0);
                        NodeList textURLList = urlElement.getChildNodes();
                        dlURLWin = ((Node) textURLList.item(0)).getNodeValue().trim();
                        //System.out.println("Current URL WIN : " + dlURLWin);

                        NodeList urlOSXList = subtitlerElement.getElementsByTagName(DL_URL_OSX);
                        Element urlOSXElement = (Element) urlOSXList.item(0);
                        NodeList textURLOSXList = urlOSXElement.getChildNodes();
                        dlURLOsx = ((Node) textURLOSXList.item(0)).getNodeValue().trim();
                        //System.out.println("Current URL OSX : " + dlURLOsx);

                        NodeList urlOtherList = subtitlerElement.getElementsByTagName(DL_URL_OTHER);
                        Element urlOtherElement = (Element) urlOtherList.item(0);
                        NodeList textURLOtherList = urlOtherElement.getChildNodes();
                        dlURLOther = ((Node) textURLOtherList.item(0)).getNodeValue().trim();
                        //System.out.println("Current URL Other : " + dlURLOther);

                        NodeList urlSizeList = subtitlerElement.getElementsByTagName(DL_SIZE);
                        Element urlSizeElement = (Element) urlSizeList.item(0);
                        NodeList textURLSizeList = urlSizeElement.getChildNodes();
                        dlURLSize = ((Node) textURLSizeList.item(0)).getNodeValue().trim();
                        //System.out.println("Current URL Other : " + dlURLSize);

                        versionDTO = new VersionDTO();
                        versionDTO.setVersion(version);
                        versionDTO.setUrlwin(dlURLWin);
                        versionDTO.setUrlosx(dlURLOsx);
                        versionDTO.setDlURLSize(dlURLSize);
                        versionDTO.setUrlother(dlURLOther);
                    }
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionDTO;
    }

    public static void downloadLatestInstaller(String uri, String path, int size) throws IOException {
        URL url = new URL(uri);
        URLConnection connnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connnection;
        httpURLConnection.setInstanceFollowRedirects(false);

        httpURLConnection.connect();

        int responseCode = httpURLConnection.getResponseCode();
        while ((responseCode / 100) == 3) {
            String newLocationHeader = httpURLConnection.getHeaderField(HTTP_LOCATION);
            url = new URL(newLocationHeader);
            connnection = url.openConnection();
            httpURLConnection = (HttpURLConnection) connnection;
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            File f = null;
            String os = System.getProperty(OS_NAME);
            if (os.toLowerCase().indexOf("windows") != -1) {
                f = new File(path + "\\" + getFileName(newLocationHeader));
            } else if (os.toLowerCase().indexOf("mac") != -1) {
                f = new File(path + "/" + getFileName(newLocationHeader));
            } else {
                f = new File(path + "/" + getFileName(newLocationHeader));
            }
            if (null != f) {
                System.out.println(f.getPath());
                FileOutputStream fout = new FileOutputStream(f);
                int count;
                byte[] buffer = new byte[1024];
                int downloaded = 0;
                int per = 0;
                while ((count = inputStream.read(buffer)) > 0) {
                    downloaded = downloaded + count;
                    per = ((downloaded * 100) / (size * 1000));

                    if (null != updateChecker) {
                        updateChecker.updateProgress(per);
                    }
                    fout.write(buffer, 0, count);
                }
                if (null != updateChecker) {
                    updateChecker.updateProgress(100);
                }
                fout.close();
                inputStream.close();

                responseCode = httpURLConnection.getResponseCode();
            }
            //System.out.println(responseCode);
            break;

        }
    }

    private static UpdateChecker updateChecker = null;

    public static void setUpdateChecker(UpdateChecker updateCheck) {
        updateChecker = updateCheck;
    }

    public static void setCenterScreen(JFrame jFrame, int thisH, int thisW) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int screenH = dim.height;
        int screenW = dim.width;
        int tmpH = (screenH - thisH) / 2;
        int tmpW = (screenW - thisW) / 2;
        int x = screenW - thisW - tmpW;
        int y = screenH - thisH - tmpH;
        jFrame.setLocation(x, y);
    }

    public static final String deocdeURL(String uri) throws UnsupportedEncodingException {
        return URLDecoder.decode(uri, "UTF-8");
    }

    public static String getDecrypt(String s) {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] b = new byte[0];
        try {
            b = base64Decoder.decodeBuffer(s);
            s = new String(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static String getFileName(String uri) throws UnsupportedEncodingException {
        if (null != uri) {
            if (uri.indexOf("/") != -1) {
                uri = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
            }
            uri = URLDecoder.decode(uri, "UTF-8");
            System.out.println(uri);
        }
        return uri;
    }

    public static void main(String[] args) throws IOException {
        /*File f = new File("C:\\Development\\subtitler test\\Super 8 2011 1080p BDRip H264 AAC - IceBane (Kingdom Release)");
        getIMDBFromParentNonRecursive(f);*/
        /*String id = getIMDBIDScraping("Final Destination 5");
        //System.out.println(id);*/

        //downloadLatestInstaller("https://downloads.sourceforge.net/project/renaissannce/latest/Windows/Renaissance%20Setup.exe?r=&ts=1329350673&use_mirror=iweb", "C:\\Development\\SubtitlerGUI\\SubtitlerGUI", 2200);
        /*FileUtils.copyURLToFile(new URL("https://downloads.sourceforge.net/project/renaissannce/latest/Windows/Renaissance%20Setup.exe?r=&ts=1329346129&use_mirror=iweb"),
                new File("C:\\Development\\SubtitlerGUI\\SubtitlerGUI\\in.exe"),100000,100000) ;*/
        ////System.out.println("");
        //String s = getIMDBIDScraping("Where Eagles Dare 1968 720p BRRip.mkv");
        //System.out.println(s);
        downloadLatestInstaller("https://downloads.sourceforge.net/project/renaissannce/Renaissannce/Subtitler%20Renaissance%20Setup.dmg?r=https%3A%2F%2Fsourceforge.net%2Fprojects%2Frenaissannce%2F&amp;ts=1329556521&amp;use_mirror=cdnetworks-us-2", "", 1);

        /*String s = "http://iweb.dl.sourceforge.net/project/renaissannce/Renaissannce/Subtitler%20Renaissance%20Setup.dmg";
        if(s.indexOf("/") != -1) {
            s = s.substring(s.lastIndexOf("/")+1,s.length());
        }
        String s1 = URLDecoder.decode(s,"UTF-8");
        System.out.println(s1);*/


    }
}

