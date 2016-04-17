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
 * Date: Feb 15, 2012
 * Time: 1:59:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class VersionDTO implements Serializable {

    private String version;
    private String urlwin;
    private String urlosx;
    private String urlother;
    private String dlURLSize;

    public String getDlURLSize() {
        return dlURLSize;
    }

    public void setDlURLSize(String dlURLSize) {
        this.dlURLSize = dlURLSize;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrlwin() {
        return urlwin;
    }

    public void setUrlwin(String urlwin) {
        this.urlwin = urlwin;
    }

    public String getUrlosx() {
        return urlosx;
    }

    public void setUrlosx(String urlosx) {
        this.urlosx = urlosx;
    }

    public String getUrlother() {
        return urlother;
    }

    public void setUrlother(String urlother) {
        this.urlother = urlother;
    }
}
