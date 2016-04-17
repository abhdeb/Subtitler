/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

/**
 * Created by IntelliJ IDEA.
 * User: abhdeb
 * Date: Feb 10, 2012
 * Time: 12:42:52 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class SubtitlerWebservice implements SubtitlerConstants {


    private String token = null;
    private XmlRpcClient client = null;
    private HashMap idSubMap = null;
    private HashMap movieSubMap = null;
    private final HashMap langMap = Language.getLanguageMap();
    private final ArrayList alreadyDownloaedList = new ArrayList();

    public HashMap getIdSubMap() {
        return idSubMap;
    }

    public HashMap getMovieSubMap() {
        return movieSubMap;
    }

    public String getToken() {
        return token;
    }

    private void setToken(String token) {
        this.token = token;
    }

    public void logout() throws IOException, XmlRpcException {
        if (null != this.token && null != this.client) {
            Vector params = new Vector();
            params.addElement(this.token);
            client.execute("LogOut", params);
            //System.out.println("Logged Out.");
        }
    }

    public void main(String[] args) throws Exception {
        searchSubs(null, null);
        getSubLanguages();
    }

    public HashMap getSubLanguages() throws XmlRpcException {
        HashMap hashMap = new HashMap();
        Vector params = new Vector();
        params.addElement(this.token);
        HashMap hashtable = (HashMap) client.execute("GetSubLanguages", params);
        Vector vector = (Vector) hashtable.get("data");
        if (null != vector) {
            for (int i = 0; i < vector.size(); i++) {
                HashMap hashtableV = (HashMap) vector.get(i);
                String SubLanguageID = (String) hashtableV.get("SubLanguageID");
                String LanguageName = (String) hashtableV.get("LanguageName");
                hashMap.put(SubLanguageID, LanguageName);
                //System.out.println(LanguageName);
            }
        }

        return hashMap;

    }


    private void connectXMLRpc() throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(XML_RPC));
        this.client = new XmlRpcClient();
        this.client.setConfig(config);
    }

    private void setUserAgent() {
        if (null != this.client) {
            String tokenTmp = null;
            Vector params = new Vector();
            HashMap result = null;
            try {
                params.addElement(SubtitlerUtil.getDecrypt(IMDB_USER_NAME));
                params.addElement(SubtitlerUtil.getDecrypt(IMDB_PASSWORD));
                params.addElement("ENG");
                params.addElement(SubtitlerUtil.getDecrypt(IMDB_USERAGENT_1));
                result = (HashMap) this.client.execute(OSDB_WS_LOGIN_PARAM, params);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    params = new Vector();
                    params.addElement(SubtitlerUtil.getDecrypt(IMDB_USER_NAME));
                    params.addElement(SubtitlerUtil.getDecrypt(IMDB_PASSWORD));
                    params.addElement("ENG");
                    params.addElement(SubtitlerUtil.getDecrypt(IMDB_USERAGENT_2));
                    result = (HashMap) this.client.execute(OSDB_WS_LOGIN_PARAM, params);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    try {
                        params.addElement(SubtitlerUtil.getDecrypt(IMDB_USER_NAME));
                        params.addElement(SubtitlerUtil.getDecrypt(IMDB_PASSWORD));
                        params.addElement("ENG");
                        params.addElement(SubtitlerUtil.getDecrypt(IMDB_USERAGENT_3));
                        result = (HashMap) this.client.execute(OSDB_WS_LOGIN_PARAM, params);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        try {
                            params.addElement(SubtitlerUtil.getDecrypt(IMDB_USER_NAME));
                            params.addElement(SubtitlerUtil.getDecrypt(IMDB_PASSWORD));
                            params.addElement("ENG");
                            params.addElement(SubtitlerUtil.getDecrypt(IMDB_USERAGENT_1));
                            result = (HashMap) this.client.execute(OSDB_WS_LOGIN_PARAM, params);
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                }
            }
            if (null != result && null != result.get(OSDB_WS_TOKEN_PARAM)) {
                tokenTmp = (String) result.get(OSDB_WS_TOKEN_PARAM);
                setToken(tokenTmp);
            }
        }
    }

    private boolean checkMovieByHashFunction(File file, String lang)
            throws IOException, XmlRpcException {
        String s = OpenSubtitlesHasher.computeHash(file);
        HashMap hash = new HashMap();
        hash.put(OSDB_WS_MOVIE_HASH_PARAM, s);
        Vector params = new Vector();
        params.addElement(this.token);
        params.addElement(hash);
        if (null != this.token && null != this.client) {
            HashMap result = (HashMap) client.execute(OSDB_WS_CHECK_HASH_FUNCTION_1, params);
            if (null != result &&
                    null != result.get("status") &&
                    HTTP_200.equalsIgnoreCase(result.get("status").toString())) {
                if (null != result.get("data") && result.get("data") instanceof HashMap) {
                    HashMap data = (HashMap) result.get("data");
                    if (null != data && !data.isEmpty() && data.get(s) instanceof HashMap) {
                        HashMap movieData = (HashMap) data.get(s);
                        String movieName = (String) movieData.get("MovieName");
                        String movieYear = (String) movieData.get("MovieYear");
                        String movieImdbNumber = (String) movieData.get("MovieImdbID");
                        String movieHash = (String) movieData.get("MovieHash");
                        SubtitlerImdbDTO subtitlerImdbDTO = new SubtitlerImdbDTO();
                        subtitlerImdbDTO = populateIMDB(movieImdbNumber, subtitlerImdbDTO);
                        boolean out = populateSubByHash(movieImdbNumber, lang, file, subtitlerImdbDTO);
                        if (!out) {
                            out = populateSubByIMDBID(movieImdbNumber, lang, file, subtitlerImdbDTO);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkMovieByHashFunction2(File file, String lang)
            throws IOException, XmlRpcException {
        String s = OpenSubtitlesHasher.computeHash(file);
        HashMap hash = new HashMap();
        hash.put(OSDB_WS_MOVIE_HASH_PARAM, s);
        Vector params = new Vector();
        params.addElement(this.token);
        params.addElement(hash);
        if (null != this.token && null != this.client) {
            HashMap result = (HashMap) client.execute(OSDB_WS_CHECK_HASH_FUNCTION_2, params);
            if (null != result &&
                    null != result.get("status") &&
                    HTTP_200.equalsIgnoreCase(result.get("status").toString())) {
                if (null != result.get("data") && result.get("data") instanceof HashMap) {
                    HashMap data = (HashMap) result.get("data");
                    if (null != data && !data.isEmpty() && data.get(s) instanceof HashMap) {
                        HashMap movieData = (HashMap) data.get(s);
                        String movieName = (String) movieData.get("MovieName");
                        String movieYear = (String) movieData.get("MovieYear");
                        String movieImdbNumber = (String) movieData.get("MovieImdbID");
                        String movieHash = (String) movieData.get("MovieHash");
                        SubtitlerImdbDTO subtitlerImdbDTO = new SubtitlerImdbDTO();
                        subtitlerImdbDTO = populateIMDB(movieImdbNumber, subtitlerImdbDTO);
                        boolean out = populateSubByHash(movieImdbNumber, lang, file, subtitlerImdbDTO);
                        if (!out) {
                            out = populateSubByIMDBID(movieImdbNumber, lang, file, subtitlerImdbDTO);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkMovieByScraping(File file, String lang) throws IOException, XmlRpcException {
        String fName = file.getName();
        if (fName.indexOf(".") != -1) {
            fName = fName.substring(0, fName.indexOf("."));
            String imdbID = SubtitlerUtil.getIMDBIDScraping(fName);
            if (null != imdbID && !"".equals(imdbID)) {
                SubtitlerImdbDTO subtitlerImdbDTO = new SubtitlerImdbDTO();
                subtitlerImdbDTO = populateIMDB(imdbID, subtitlerImdbDTO);
                boolean out = populateSubByHash(imdbID, lang, file, subtitlerImdbDTO);
                if (!out) {
                    out = populateSubByIMDBID(imdbID, lang, file, subtitlerImdbDTO);
                }
                return true;
            }
        }
        return false;
    }

    private String getIMDBIDByScraping(File file) throws IOException, XmlRpcException {
        String imdbID = null;
        if (null != file && file.isFile() && file.exists()) {
            String fName = file.getName();
            if (fName.indexOf(".") != -1) {
                fName = fName.substring(0, fName.indexOf("."));
                imdbID = SubtitlerUtil.getIMDBIDScraping(fName);
            }
        }
        return imdbID;
    }

    private boolean checkMovieByNFO(File file, String lang)
            throws IOException, XmlRpcException {

        String imdbID = null;
        if (null != file && file.isFile()) {
            File parent = file.getParentFile();
            if (null != parent && parent.exists()) {
                imdbID = SubtitlerUtil.getIMDBFromParentNonRecursive(parent);
                if (null != imdbID && !"".equals(imdbID) && imdbID.length() > 0) {
                    String secondaryIMDBID = getIMDBIDByScraping(file);
                    if (null != secondaryIMDBID && !"".equals(secondaryIMDBID) && secondaryIMDBID.length() > 0) {
                        if (Integer.valueOf(imdbID).intValue() == Integer.valueOf(secondaryIMDBID).intValue()) {
                            SubtitlerImdbDTO subtitlerImdbDTO = new SubtitlerImdbDTO();
                            subtitlerImdbDTO = populateIMDB(imdbID, subtitlerImdbDTO);
                            boolean out = populateSubByHash(imdbID, lang, file, subtitlerImdbDTO);
                            if (!out) {
                                out = populateSubByIMDBID(imdbID, lang, file, subtitlerImdbDTO);
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void searchSubs(ArrayList fileList, String lang) throws IOException, XmlRpcException {
        idSubMap = new HashMap();
        movieSubMap = new HashMap();

        if (null == this.client) {
            connectXMLRpc();
        }
        if (null == this.token) {
            setUserAgent();
        }
        if (null != fileList && !fileList.isEmpty() && null != this.token && null != this.client) {
            for (int j = 0; j < fileList.size(); j++) {
                File file = (File) fileList.get(j);
                if ((file.length() / (1024 * 1024)) > 50 && !alreadyDownloaedList.contains(file.getPath())) {

                    // check by Hash Function
                    boolean out = checkMovieByHashFunction(file, lang);
                    if (!out) {
                        // check by Hash Function 2
                        out = checkMovieByHashFunction2(file, lang);
                        if (!out) {
                            // check by NFO Function
                            out = checkMovieByNFO(file, lang);
                            if (!out) {
                                // check by Scraping Function
                                out = checkMovieByScraping(file, lang);
                            }
                        }
                    }


                }
            }
        }
    }

    private SubtitlerImdbDTO populateIMDB(String movieImdbNumber, SubtitlerImdbDTO subtitlerImdbDTO) throws XmlRpcException {
        if (null != movieImdbNumber) {
            Vector param1 = new Vector();
            param1.addElement(this.token);
            param1.addElement(movieImdbNumber);
            HashMap imdbDetails = (HashMap) client.execute("GetIMDBMovieDetails", param1);
            HashMap data = (HashMap) imdbDetails.get("data");

            if (null != data) {
                Object[] var = (Object[]) data.get("aka");
                if (null != var && var.length > 0) {
                    subtitlerImdbDTO.setAka(SubtitlerUtil.getObjArrToStr(var));
                } else {
                    subtitlerImdbDTO.setAka("");
                }
                var = (Object[]) data.get("genres");
                if (null != var && var.length > 0) {
                    subtitlerImdbDTO.setGenres(SubtitlerUtil.getObjArrToStr(var));
                } else {
                    subtitlerImdbDTO.setGenres("");
                }
                subtitlerImdbDTO.setVotes(data.get("votes") != null ? data.get("votes").toString() : "");
                subtitlerImdbDTO.setKind(data.get("kind") != null ? data.get("kind").toString() : "");
                var = (Object[]) data.get("country");
                if (null != var && var.length > 0) {
                    subtitlerImdbDTO.setCountry(SubtitlerUtil.getObjArrToStr(var));
                } else {
                    subtitlerImdbDTO.setCountry("");
                }
                subtitlerImdbDTO.setPlot(data.get("plot") != null ? data.get("plot").toString() : "");
                subtitlerImdbDTO.setId(data.get("id") != null ? data.get("id").toString() : "");
                subtitlerImdbDTO.setCover(data.get("cover") != null ? data.get("cover").toString() : "");
                subtitlerImdbDTO.setTitle(data.get("title") != null ? data.get("title").toString() : "");

                if (null != data.get("cast")) {
                    HashMap cast = (HashMap) data.get("cast");
                    ArrayList castList = new ArrayList();
                    if (null != cast && !cast.isEmpty()) {
                        Set set = cast.entrySet();
                        Iterator iterator = set.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            castList.add(entry.getValue());
                        }
                    }
                    subtitlerImdbDTO.setCast(castList);
                }

                subtitlerImdbDTO.setYear(data.get("year") != null ? data.get("year").toString() : "");

                if (null != data.get("writers")) {
                    HashMap writers = (HashMap) data.get("writers");
                    ArrayList writerList = new ArrayList();
                    if (null != writers && !writers.isEmpty()) {
                        Set set = writers.entrySet();
                        Iterator iterator = set.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            writerList.add(entry.getValue());
                        }
                    }
                    subtitlerImdbDTO.setWriters(writerList);
                }
                subtitlerImdbDTO.setRating(data.get("rating") != null ? data.get("rating").toString() : "");

                if (null != data.get("directors")) {
                    HashMap directors = (HashMap) data.get("directors");
                    ArrayList directorList = new ArrayList();
                    if (null != directors && !directors.isEmpty()) {
                        Set set = directors.entrySet();
                        Iterator iterator = set.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            directorList.add(entry.getValue());
                        }
                    }
                    subtitlerImdbDTO.setDirectors(directorList);
                }
            }
        }
        return subtitlerImdbDTO;
    }

    private boolean populateSubByHash(String movieImdbNumber, String lang, File file, SubtitlerImdbDTO subtitlerImdbDTO)
            throws XmlRpcException, IOException {

        String s = OpenSubtitlesHasher.computeHash(file);
        HashMap para = new HashMap();
        para.put("moviehash", s);
        para.put("moviebytesize", file.length() + "");
        if (lang != null) {
            para.put("sublanguageid", langMap.get(lang).toString());
        } else {
            para.put("sublanguageid", "eng");
        }

        HashMap para2 = new HashMap();
        para2.put("data", para);

        Vector params = new Vector();
        params.addElement(this.token);
        params.addElement(para2);

        HashMap result2 = (HashMap) client.execute("SearchSubtitles", params);

        if (null != result2 && HTTP_200.equals(result2.get("status"))) {
            if (result2.get("data") instanceof Object[]) {
                Object[] reHashtable = (Object[]) result2.get("data");
                //System.out.println("");

                //System.out.println(reHashtable);
                ArrayList finalResult = new ArrayList();
                SubtitlerDTO subtitlerDTO = null;
                for (int i = 0; i < reHashtable.length; i++) {
                    HashMap s1 = (HashMap) reHashtable[i];
                    subtitlerDTO = new SubtitlerDTO();
                    subtitlerDTO.setSubLanguageID(s1.get("SubLanguageID").toString());
                    subtitlerDTO.setSubDownloadLink(s1.get("SubDownloadLink").toString());
                    subtitlerDTO.setMovieTimeMS(s1.get("MovieTimeMS").toString());
                    subtitlerDTO.setSubBad(s1.get("SubBad").toString());
                    subtitlerDTO.setMovieName(s1.get("MovieName").toString());
                    subtitlerDTO.setSubFormat(s1.get("SubFormat").toString());
                    subtitlerDTO.setIDMovieImdb(s1.get("IDMovieImdb").toString());
                    subtitlerDTO.setIDMovie(s1.get("IDMovie").toString());
                    subtitlerDTO.setSubSize(s1.get("SubSize").toString());
                    subtitlerDTO.setMovieHash(s1.get("MovieHash").toString());
                    subtitlerDTO.setMovieByteSize(s1.get("MovieByteSize").toString());
                    subtitlerDTO.setUserNickName(s1.get("UserNickName").toString());
                    subtitlerDTO.setZipDownloadLink(s1.get("ZipDownloadLink").toString());
                    subtitlerDTO.setMatchedBy(s1.get("MatchedBy").toString());
                    subtitlerDTO.setSubAddDate(s1.get("SubAddDate").toString());
                    subtitlerDTO.setSubAuthorComment(s1.get("SubAuthorComment").toString());
                    subtitlerDTO.setSubFileName(s1.get("SubFileName").toString());
                    subtitlerDTO.setSubSumCD(s1.get("SubSumCD").toString());
                    subtitlerDTO.setIDSubMovieFile(s1.get("IDSubMovieFile").toString());
                    subtitlerDTO.setSubDownloadsCnt(s1.get("SubDownloadsCnt").toString());
                    subtitlerDTO.setMovieReleaseName(s1.get("MovieReleaseName").toString());
                    subtitlerDTO.setMovieYear(s1.get("MovieYear").toString());
                    subtitlerDTO.setSubActualCD(s1.get("SubActualCD").toString());
                    subtitlerDTO.setLanguageName(s1.get("LanguageName").toString());
                    subtitlerDTO.setISO639(s1.get("ISO639").toString());
                    subtitlerDTO.setUserID(s1.get("UserID").toString());
                    subtitlerDTO.setSubHash(s1.get("SubHash").toString());
                    subtitlerDTO.setMovieNameEng(s1.get("MovieNameEng").toString());
                    subtitlerDTO.setIDSubtitle(s1.get("IDSubtitle").toString());
                    subtitlerDTO.setSubRating(s1.get("SubRating").toString());
                    subtitlerDTO.setMovieImdbRating(s1.get("MovieImdbRating").toString());
                    subtitlerDTO.setIDSubtitleFile(s1.get("IDSubtitleFile").toString());
                    subtitlerDTO.setSubtitlesLink(s1.get("SubtitlesLink").toString());
                    subtitlerDTO.setSubComments(s1.get("SubComments").toString());
                    subtitlerDTO.setFile(file);
                    subtitlerDTO.setMovieImdbNumber(movieImdbNumber);
                    finalResult.add(subtitlerDTO);
                    idSubMap.put(subtitlerDTO.getIDSubtitleFile(), subtitlerDTO);
                }

                alreadyDownloaedList.add(file.getPath());
                movieSubMap.put(file, finalResult);
                movieSubMap.put(movieImdbNumber, subtitlerImdbDTO);
                return true;
            }
        }
        return false;
    }

    private boolean populateSubByIMDBID(String movieImdbNumber, String lang, File file, SubtitlerImdbDTO subtitlerImdbDTO)
            throws XmlRpcException, IOException {

        String s = OpenSubtitlesHasher.computeHash(file);
        HashMap para = new HashMap();
        para.put("imdbid", movieImdbNumber);
        if (lang != null) {
            para.put("sublanguageid", langMap.get(lang).toString());
        } else {
            para.put("sublanguageid", "eng");
        }

        HashMap para2 = new HashMap();
        para2.put("data", para);

        Vector params = new Vector();
        params.addElement(this.token);
        params.addElement(para2);

        HashMap result2 = (HashMap) client.execute("SearchSubtitles", params);

        if (null != result2 && HTTP_200.equals(result2.get("status"))) {
            if (result2.get("data") instanceof Object[]) {
                Object[] reHashtable = (Object[]) result2.get("data");
                //System.out.println("");

                //System.out.println(reHashtable);
                ArrayList finalResult = new ArrayList();
                SubtitlerDTO subtitlerDTO = null;
                for (int i = 0; i < reHashtable.length; i++) {
                    HashMap s1 = (HashMap) reHashtable[i];
                    subtitlerDTO = new SubtitlerDTO();
                    subtitlerDTO.setSubLanguageID(s1.get("SubLanguageID").toString());
                    subtitlerDTO.setSubDownloadLink(s1.get("SubDownloadLink").toString());
                    subtitlerDTO.setMovieTimeMS(s1.get("MovieTimeMS").toString());
                    subtitlerDTO.setSubBad(s1.get("SubBad").toString());
                    subtitlerDTO.setMovieName(s1.get("MovieName").toString());
                    subtitlerDTO.setSubFormat(s1.get("SubFormat").toString());
                    subtitlerDTO.setIDMovieImdb(s1.get("IDMovieImdb").toString());
                    subtitlerDTO.setIDMovie(s1.get("IDMovie").toString());
                    subtitlerDTO.setSubSize(s1.get("SubSize").toString());
                    subtitlerDTO.setMovieHash(s1.get("MovieHash").toString());
                    subtitlerDTO.setMovieByteSize(s1.get("MovieByteSize").toString());
                    subtitlerDTO.setUserNickName(s1.get("UserNickName").toString());
                    subtitlerDTO.setZipDownloadLink(s1.get("ZipDownloadLink").toString());
                    subtitlerDTO.setMatchedBy(s1.get("MatchedBy").toString());
                    subtitlerDTO.setSubAddDate(s1.get("SubAddDate").toString());
                    subtitlerDTO.setSubAuthorComment(s1.get("SubAuthorComment").toString());
                    subtitlerDTO.setSubFileName(s1.get("SubFileName").toString());
                    subtitlerDTO.setSubSumCD(s1.get("SubSumCD").toString());
                    subtitlerDTO.setIDSubMovieFile(s1.get("IDSubMovieFile").toString());
                    subtitlerDTO.setSubDownloadsCnt(s1.get("SubDownloadsCnt").toString());
                    subtitlerDTO.setMovieReleaseName(s1.get("MovieReleaseName").toString());
                    subtitlerDTO.setMovieYear(s1.get("MovieYear").toString());
                    subtitlerDTO.setSubActualCD(s1.get("SubActualCD").toString());
                    subtitlerDTO.setLanguageName(s1.get("LanguageName").toString());
                    subtitlerDTO.setISO639(s1.get("ISO639").toString());
                    subtitlerDTO.setUserID(s1.get("UserID").toString());
                    subtitlerDTO.setSubHash(s1.get("SubHash").toString());
                    subtitlerDTO.setMovieNameEng(s1.get("MovieNameEng").toString());
                    subtitlerDTO.setIDSubtitle(s1.get("IDSubtitle").toString());
                    subtitlerDTO.setSubRating(s1.get("SubRating").toString());
                    subtitlerDTO.setMovieImdbRating(s1.get("MovieImdbRating").toString());
                    subtitlerDTO.setIDSubtitleFile(s1.get("IDSubtitleFile").toString());
                    subtitlerDTO.setSubtitlesLink(s1.get("SubtitlesLink").toString());
                    subtitlerDTO.setSubComments(s1.get("SubComments").toString());
                    subtitlerDTO.setFile(file);
                    subtitlerDTO.setMovieImdbNumber(movieImdbNumber);
                    finalResult.add(subtitlerDTO);
                    idSubMap.put(subtitlerDTO.getIDSubtitleFile(), subtitlerDTO);
                }

                alreadyDownloaedList.add(file.getPath());
                movieSubMap.put(file, finalResult);
                movieSubMap.put(movieImdbNumber, subtitlerImdbDTO);
                return true;
            }
        }
        return false;
    }

    public void downloadSub(ArrayList finalResult) {
        if (!finalResult.isEmpty()) {
            for (int i = 0; i < finalResult.size(); i++) {
                SubtitlerDTO subtitlerDTO1 = (SubtitlerDTO) finalResult.get(i);
                String zipDownloadLink = subtitlerDTO1.getZipDownloadLink();
                String subFileName = subtitlerDTO1.getSubFileName();
                try {
                    SubtitlerUtil.downloadSubZip(zipDownloadLink, subtitlerDTO1.getFile().getParent(), subFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}