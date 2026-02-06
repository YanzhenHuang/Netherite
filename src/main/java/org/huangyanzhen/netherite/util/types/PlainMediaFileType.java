package org.huangyanzhen.netherite.util.types;

public class PlainMediaFileType implements FileType {
    public static final String BMP = "bmp";
    public static final String GIF = "gif";
    public static final String TIFF = "tiff";

    public static final String AVI = "avi";
    public static final String FLV = "flv";
    public static final String WMV = "wmv";
    public static final String MPG = "mpg";
    public static final String MPEG = "mpeg";

    public static String[] list() {
        return new String[]{BMP, GIF, TIFF, AVI, FLV, WMV, MPG, MPEG};
    }
}
