package org.huangyanzhen.netherite.util;


import org.huangyanzhen.netherite.util.types.PlainMediaFileType;
import org.huangyanzhen.netherite.util.types.RichMediaFileType;

import java.io.File;
import java.util.Arrays;


public class FileTypeUtil {

    private static final String[] IMAGE_EXTS = {
            "jpg", "jpeg", "png", "bmp", "gif", "tiff", "webp"
    };

    private static final String[] VIDEO_EXTS = {
            "mp4", "mov", "avi", "mkv", "flv", "wmv", "mpg", "mpeg"
    };

    private static String getExtension(File file) {
        String name = file.getName();
        int i = name.lastIndexOf('.');

        if (i <= 0 || i >= name.length() - 1)
            return "";

        return name.substring(i + 1);
    }

    public static MediaType getMediaType(File file) {
        String ext = getExtension(file).toLowerCase();

        if (Arrays.asList(IMAGE_EXTS).contains(ext))
            return MediaType.IMAGE;

        if (Arrays.asList(VIDEO_EXTS).contains(ext))
            return MediaType.VIDEO;

        return MediaType.UNSUPPORTED;
    }

    public static boolean isRich(File file) {
        String ext = getExtension(file).toLowerCase();
        return Arrays.asList(RichMediaFileType.list()).contains(ext);
    }

    public static boolean isPlain(File file) {
        String ext = getExtension(file).toLowerCase();
        return Arrays.asList(PlainMediaFileType.list()).contains(ext);
    }

    public static boolean isSupported(File file) {
        return getMediaType(file) != MediaType.UNSUPPORTED;
    }


}
