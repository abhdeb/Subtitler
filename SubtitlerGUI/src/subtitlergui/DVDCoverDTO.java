/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: abhdeb
 * Date: Feb 13, 2012
 * Time: 4:05:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class DVDCoverDTO implements Serializable {
    private String movieName;
    private String imgUrl;
    private String localMainFilePath;
    private String localPreviewFilePath;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLocalMainFilePath() {
        return localMainFilePath;
    }

    public void setLocalMainFilePath(String localMainFilePath) {
        this.localMainFilePath = localMainFilePath;
    }

    public String getLocalPreviewFilePath() {
        return localPreviewFilePath;
    }

    public void setLocalPreviewFilePath(String localPreviewFilePath) {
        this.localPreviewFilePath = localPreviewFilePath;
    }
}
