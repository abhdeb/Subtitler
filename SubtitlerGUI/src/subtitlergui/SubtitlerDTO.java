/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

import java.io.File;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: dev
 * Date: Dec 30, 2009
 * Time: 5:10:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class SubtitlerDTO implements Serializable {
    private String SubLanguageID = null;
    private String SubDownloadLink = null;
    private String MovieTimeMS = null;
    private String SubBad = null;
    private String MovieName = null;
    private String SubFormat = null;
    private String IDMovieImdb = null;
    private String IDMovie = null;
    private String SubSize = null;
    private String MovieHash = null;
    private String MovieByteSize = null;
    private String UserNickName = null;
    private String ZipDownloadLink = null;
    private String MatchedBy = null;
    private String SubAddDate = null;
    private String SubAuthorComment = null;
    private String SubFileName = null;
    private String SubSumCD = null;
    private String IDSubMovieFile = null;
    private String SubDownloadsCnt = null;
    private String MovieReleaseName = null;
    private String MovieYear = null;
    private String SubActualCD = null;
    private String LanguageName = null;
    private String ISO639 = null;
    private String UserID = null;
    private String SubHash = null;
    private String MovieNameEng = null;
    private String IDSubtitle = null;
    private String SubRating = null;
    private String MovieImdbRating = null;
    private String IDSubtitleFile = null;
    private String SubtitlesLink = null;
    private String SubComments = null;
    private File file = null;
    private String movieImdbNumber = null;


    public String getMovieImdbNumber() {
        return movieImdbNumber;
    }

    public void setMovieImdbNumber(String movieImdbNumber) {
        this.movieImdbNumber = movieImdbNumber;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getSubLanguageID() {
        return SubLanguageID;
    }

    public void setSubLanguageID(String subLanguageID) {
        SubLanguageID = subLanguageID;
    }

    public String getSubDownloadLink() {
        return SubDownloadLink;
    }

    public void setSubDownloadLink(String subDownloadLink) {
        SubDownloadLink = subDownloadLink;
    }

    public String getMovieTimeMS() {
        return MovieTimeMS;
    }

    public void setMovieTimeMS(String movieTimeMS) {
        MovieTimeMS = movieTimeMS;
    }

    public String getSubBad() {
        return SubBad;
    }

    public void setSubBad(String subBad) {
        SubBad = subBad;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getSubFormat() {
        return SubFormat;
    }

    public void setSubFormat(String subFormat) {
        SubFormat = subFormat;
    }

    public String getIDMovieImdb() {
        return IDMovieImdb;
    }

    public void setIDMovieImdb(String IDMovieImdb) {
        this.IDMovieImdb = IDMovieImdb;
    }

    public String getIDMovie() {
        return IDMovie;
    }

    public void setIDMovie(String IDMovie) {
        this.IDMovie = IDMovie;
    }

    public String getSubSize() {
        return SubSize;
    }

    public void setSubSize(String subSize) {
        SubSize = subSize;
    }

    public String getMovieHash() {
        return MovieHash;
    }

    public void setMovieHash(String movieHash) {
        MovieHash = movieHash;
    }

    public String getMovieByteSize() {
        return MovieByteSize;
    }

    public void setMovieByteSize(String movieByteSize) {
        MovieByteSize = movieByteSize;
    }

    public String getUserNickName() {
        return UserNickName;
    }

    public void setUserNickName(String userNickName) {
        UserNickName = userNickName;
    }

    public String getZipDownloadLink() {
        return ZipDownloadLink;
    }

    public void setZipDownloadLink(String zipDownloadLink) {
        ZipDownloadLink = zipDownloadLink;
    }

    public String getMatchedBy() {
        return MatchedBy;
    }

    public void setMatchedBy(String matchedBy) {
        MatchedBy = matchedBy;
    }

    public String getSubAddDate() {
        return SubAddDate;
    }

    public void setSubAddDate(String subAddDate) {
        SubAddDate = subAddDate;
    }

    public String getSubAuthorComment() {
        return SubAuthorComment;
    }

    public void setSubAuthorComment(String subAuthorComment) {
        SubAuthorComment = subAuthorComment;
    }

    public String getSubFileName() {
        return SubFileName;
    }

    public void setSubFileName(String subFileName) {
        SubFileName = subFileName;
    }

    public String getSubSumCD() {
        return SubSumCD;
    }

    public void setSubSumCD(String subSumCD) {
        SubSumCD = subSumCD;
    }

    public String getIDSubMovieFile() {
        return IDSubMovieFile;
    }

    public void setIDSubMovieFile(String IDSubMovieFile) {
        this.IDSubMovieFile = IDSubMovieFile;
    }

    public String getSubDownloadsCnt() {
        return SubDownloadsCnt;
    }

    public void setSubDownloadsCnt(String subDownloadsCnt) {
        SubDownloadsCnt = subDownloadsCnt;
    }

    public String getMovieReleaseName() {
        return MovieReleaseName;
    }

    public void setMovieReleaseName(String movieReleaseName) {
        MovieReleaseName = movieReleaseName;
    }

    public String getMovieYear() {
        return MovieYear;
    }

    public void setMovieYear(String movieYear) {
        MovieYear = movieYear;
    }

    public String getSubActualCD() {
        return SubActualCD;
    }

    public void setSubActualCD(String subActualCD) {
        SubActualCD = subActualCD;
    }

    public String getLanguageName() {
        return LanguageName;
    }

    public void setLanguageName(String languageName) {
        LanguageName = languageName;
    }

    public String getISO639() {
        return ISO639;
    }

    public void setISO639(String ISO639) {
        this.ISO639 = ISO639;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getSubHash() {
        return SubHash;
    }

    public void setSubHash(String subHash) {
        SubHash = subHash;
    }

    public String getMovieNameEng() {
        return MovieNameEng;
    }

    public void setMovieNameEng(String movieNameEng) {
        MovieNameEng = movieNameEng;
    }

    public String getIDSubtitle() {
        return IDSubtitle;
    }

    public void setIDSubtitle(String IDSubtitle) {
        this.IDSubtitle = IDSubtitle;
    }

    public String getSubRating() {
        return SubRating;
    }

    public void setSubRating(String subRating) {
        SubRating = subRating;
    }

    public String getMovieImdbRating() {
        return MovieImdbRating;
    }

    public void setMovieImdbRating(String movieImdbRating) {
        MovieImdbRating = movieImdbRating;
    }

    public String getIDSubtitleFile() {
        return IDSubtitleFile;
    }

    public void setIDSubtitleFile(String IDSubtitleFile) {
        this.IDSubtitleFile = IDSubtitleFile;
    }

    public String getSubtitlesLink() {
        return SubtitlesLink;
    }

    public void setSubtitlesLink(String subtitlesLink) {
        SubtitlesLink = subtitlesLink;
    }

    public String getSubComments() {
        return SubComments;
    }

    public void setSubComments(String subComments) {
        SubComments = subComments;
    }

    @Override
    public String toString() {
        return "SubtitlerDTO{" +
                "SubFile='" + SubFileName + '\'' +
                ", Release='" + MovieReleaseName + '\'' +
                ", Language='" + LanguageName + '\'' +
                ", Rating='" + SubRating + '\'' +
                '}';
    }

    /*@Override
    public String toString() {
        return "SubtitlerDTO{" +
                "SubLanguageID='" + SubLanguageID + '\'' +
                ", SubDownloadLink='" + SubDownloadLink + '\'' +
                ", MovieTimeMS='" + MovieTimeMS + '\'' +
                ", SubBad='" + SubBad + '\'' +
                ", MovieName='" + MovieName + '\'' +
                ", SubFormat='" + SubFormat + '\'' +
                ", IDMovieImdb='" + IDMovieImdb + '\'' +
                ", IDMovie='" + IDMovie + '\'' +
                ", SubSize='" + SubSize + '\'' +
                ", MovieHash='" + MovieHash + '\'' +
                ", MovieByteSize='" + MovieByteSize + '\'' +
                ", UserNickName='" + UserNickName + '\'' +
                ", ZipDownloadLink='" + ZipDownloadLink + '\'' +
                ", MatchedBy='" + MatchedBy + '\'' +
                ", SubAddDate='" + SubAddDate + '\'' +
                ", SubAuthorComment='" + SubAuthorComment + '\'' +
                ", SubFileName='" + SubFileName + '\'' +
                ", SubSumCD='" + SubSumCD + '\'' +
                ", IDSubMovieFile='" + IDSubMovieFile + '\'' +
                ", SubDownloadsCnt='" + SubDownloadsCnt + '\'' +
                ", MovieReleaseName='" + MovieReleaseName + '\'' +
                ", MovieYear='" + MovieYear + '\'' +
                ", SubActualCD='" + SubActualCD + '\'' +
                ", LanguageName='" + LanguageName + '\'' +
                ", ISO639='" + ISO639 + '\'' +
                ", UserID='" + UserID + '\'' +
                ", SubHash='" + SubHash + '\'' +
                ", MovieNameEng='" + MovieNameEng + '\'' +
                ", IDSubtitle='" + IDSubtitle + '\'' +
                ", SubRating='" + SubRating + '\'' +
                ", MovieImdbRating='" + MovieImdbRating + '\'' +
                ", IDSubtitleFile='" + IDSubtitleFile + '\'' +
                ", SubtitlesLink='" + SubtitlesLink + '\'' +
                ", SubComments='" + SubComments + '\'' +
                '}';
    }*/
}
