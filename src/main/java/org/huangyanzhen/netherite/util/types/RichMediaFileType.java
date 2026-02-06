package org.huangyanzhen.netherite.util.types;

public class RichMediaFileType implements FileType {
    // Image
    public static final String JPG = "jpg";
    public static final String JPEG = "jpeg";
    public static final String PNG = "png";
    public static final String WEBP = "webp";

    // Video
    public static final String MP4 = "mp4";
    public static final String MOV = "mov";
    public static final String MKV = "mkv";

    public static String[] list() {
        return new String[] {JPG, JPEG, PNG, WEBP, MP4, MOV, MKV};
    }
}
