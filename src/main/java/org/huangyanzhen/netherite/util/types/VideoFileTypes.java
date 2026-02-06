package org.huangyanzhen.netherite.util.types;

public class VideoFileTypes implements FileType{

    // Rich
    public static final String MP4 = "mp4";
    public static final String MOV = "mov";
    public static final String MKV = "mkv";

    // Plain
    public static final String AVI = "avi";
    public static final String FLV = "flv";
    public static final String WMV = "wmv";
    public static final String MPG = "mpg";
    public static final String MPEG = "mpeg";

    public static String[] list() {
        return new String[]{MP4, MOV, MKV, AVI, FLV, WMV, MPG, MPEG};
    }

    public static String[] listRich() {
        return new String[]{MP4, MOV, MKV};
    }

    public static String[] listPlain() {
        return new String[]{AVI, FLV, WMV, MPG, MPEG};
    }
}
