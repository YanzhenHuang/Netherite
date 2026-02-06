package org.huangyanzhen.netherite.util.types;

public class ImageFileTypes implements FileType {
    // Rich
    public static final String JPG = "jpg";
    public static final String JPEG = "jpeg";
    public static final String PNG = "png";
    public static final String WEBP = "webp";

    // Plain
    public static final String BMP = "bmp";
    public static final String GIF = "gif";
    public static final String TIFF = "tiff";

    public static String[] list() {
        return new String[]{JPG, JPEG, PNG, WEBP, BMP, GIF, TIFF};
    }

    public static String[] listRich() {
        return new String[]{JPG, JPEG, PNG, WEBP};
    }

    public static String[] listPlain() {
        return new String[]{BMP, GIF, TIFF};
    }
}
