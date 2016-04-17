/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

import org.jdesktop.swingx.border.DropShadowBorder;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: abhdeb
 * Date: Feb 10, 2012
 * Time: 11:00:56 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SubtitlerConstants {
    //public static final DropShadowBorder DSB = new DropShadowBorder(UIManager.getColor("Control"), 0, 8, 0.5F, 12, false, true, true, true);
    public static final DropShadowBorder DSB = new DropShadowBorder();
    public static final Integer LOOK_AND_FEEL_NIMBUS = 0;
    public static final Integer LOOK_AND_FEEL_METAL = 1;
    public static final Integer LOOK_AND_FEEL_MOTIF = 2;
    public static final Integer LOOK_AND_FEEL_SYSTEM = 3;
    public static final Color DARK_GRAY = new Color(51, 51, 51);
    public static final Color DARK_GRAY_1 = new Color(102, 102, 102);
    public static final Color DARK_GREEN = new Color(22, 27, 20);
    public static final Color DARK_BLUE = new Color(20, 20, 27);
    public static final Color DARK_RED = new Color(51, 0, 0);

    public static final String VIDEO_FILES = "Video Files ...";
    public static final String ADD_VIDEO = "Add Video";
    public static final String REMOVE = "Remove";
    public static final String LOADING_IMDB = "Loading IMDB information...";
    public static final String HTTP_200 = "200 OK";

    public static final String XML_RPC = "http://api.opensubtitles.org/xml-rpc";
    public static final String IMDB_EXP = "imdb\\.[^\\/]+\\/title\\/tt(\\d+)";
    public static final String GOOGLE_IMAGE = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=";
    public static final String GOOGLE_IMAGE_SITE = "site:covershut.com";
    public static final String GOOGLE_TOKENIZER = "GsearchResultClass";
    public static final String GOOGLE_URL = "unescapedUrl";
    public static final String IMDB_SCRAPING_URL = "http://www.imdbapi.com/?t=";
    public static final String IMDB_SCRAPING_EXP = "\\\"tt(\\d+)";

    public static final String IMDB_USER_NAME = "YWJoZGVi";
    public static final String IMDB_PASSWORD = "YWdhcnRhbGExMjM=";
    public static final String IMDB_USERAGENT_1 = "UmVuYWlzc2FuY2UgU3VidGl0bGVyIDEuMA==";
    public static final String IMDB_USERAGENT_2 = "U29sRW9sIDEuMy4z";
    public static final String IMDB_USERAGENT_3 = "T1MgVGVzdCBVc2VyIEFnZW50";

    public static final String UPDATE_URL = "https://downloads.sourceforge.net/project/renaissannce/1/System.xml?r=&ts=1329352697&use_mirror=master";
    public static final String INSTALLED_APP_VERSION_FILE = "System.xml";

    public static final String VERSION = "version";
    public static final String DL_URL_WIN = "urlwin";
    public static final String DL_URL_OSX = "urlosx";
    public static final String DL_URL_OTHER = "urlother";
    public static final String SUBTITLER = "subtitler";
    public static final String HTTP_LOCATION = "Location";
    public static final String SETUP_FILE_NAME = "Subtitler_Setup.exe";
    public static final String DL_SIZE = "size";
    public static final String OS_NAME = "os.name";

    public static final String OSDB_WS_LOGIN_PARAM = "LogIn";
    public static final String OSDB_WS_TOKEN_PARAM = "token";
    public static final String OSDB_WS_MOVIE_HASH_PARAM = "MovieHash";
    public static final String OSDB_WS_CHECK_HASH_FUNCTION_1 = "CheckMovieHash";
    public static final String OSDB_WS_CHECK_HASH_FUNCTION_2 = "CheckMovieHash2";

    public static final String FILE_REGEX = "-|_|3Li|BRRip|Extended Cut|BluRay|Dual Audio|XviD|playXD|BDRip|480p|720p|1080p|x264|h264|AAC";
    public static final double APP_VERSION = 1.0;


}
