package org.huangyanzhen.netherite.util;


import org.huangyanzhen.netherite.util.types.ImageFileTypes;
import org.huangyanzhen.netherite.util.types.VideoFileTypes;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;


public class FileTypeUtil {

    public static String getExtension(File file) {
        String name = file.getName();
        int i = name.lastIndexOf('.');

        if (i <= 0 || i >= name.length() - 1)
            return "";

        return name.substring(i + 1);
    }

    public static MediaType getMediaType(File file) {
        String ext = getExtension(file).toLowerCase();

        if (Arrays.asList(ImageFileTypes.list()).contains(ext))
            return MediaType.IMAGE;

        if (Arrays.asList(VideoFileTypes.list()).contains(ext))
            return MediaType.VIDEO;

        return MediaType.UNSUPPORTED;
    }

    public static String[] listRichTypes() {
        String[] richImageTypes = ImageFileTypes.listRich();
        String[] richVideoTypes = VideoFileTypes.listRich();
        return Stream.concat(
                Arrays.stream(richImageTypes),
                Arrays.stream(richVideoTypes)
        ).toArray(String[]::new);
    }

    public static String[] listPlainTypes() {
        String[] plainImageTypes = ImageFileTypes.listPlain();
        String[] plainVideoTypes = VideoFileTypes.listPlain();
        return Stream.concat(
                Arrays.stream(plainImageTypes),
                Arrays.stream(plainVideoTypes)
        ).toArray(String[]::new);
    }

    public static boolean isRich(File file) {
        String ext = getExtension(file).toLowerCase();
        return Arrays.asList(listRichTypes()).contains(ext);
    }

    public static boolean isPlain(File file) {
        String ext = getExtension(file).toLowerCase();
        return Arrays.asList(listPlainTypes()).contains(ext);
    }

    public static boolean isSupported(File file) {
        return getMediaType(file) != MediaType.UNSUPPORTED;
    }


}
