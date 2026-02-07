package org.huangyanzhen.netherite.util;


import org.huangyanzhen.netherite.util.types.ImageFileTypes;
import org.huangyanzhen.netherite.util.types.VideoFileTypes;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;


public class FileTypeUtil {

    /**
     * 获取文件拓展名
     * @param file 文件对象
     * @return 文件拓展名
     */
    public static String getExtension(File file) {
        if (file == null || !file.isFile())
            return "";

        String name = file.getName();
        int i = name.lastIndexOf('.');

        if (i <= 0 || i >= name.length() - 1)
            return "";

        return name.substring(i + 1);
    }

    /**
     * 获取媒体类型
     * @param file 文件对象
     * @return 媒体类型枚举
     */
    public static MediaType getMediaType(File file) {
        String ext = getExtension(file).toLowerCase();

        if (Arrays.asList(ImageFileTypes.list()).contains(ext))
            return MediaType.IMAGE;

        if (Arrays.asList(VideoFileTypes.list()).contains(ext))
            return MediaType.VIDEO;

        return MediaType.UNSUPPORTED;
    }

    public static boolean isSupported(File file) {
        return getMediaType(file) != MediaType.UNSUPPORTED;
    }


}
