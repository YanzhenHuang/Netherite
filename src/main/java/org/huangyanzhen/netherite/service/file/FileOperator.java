package org.huangyanzhen.netherite.service.file;

import org.huangyanzhen.netherite.service.model.media.Media;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiFunction;

public class FileOperator {

    private static void traverseDirTree(File dir, BiFunction<File, Integer, Boolean> fn, int depth) {
        if (!dir.isDirectory()) {
            fn.apply(dir, depth);
            return;
        }

        for (File f: Objects.requireNonNull(dir.listFiles())) {
            if (f == null || !f.exists()) {
                continue;
            }

            if (f.isDirectory() && Objects.requireNonNull(f.listFiles()).length != 0) {
                traverseDirTree(f, fn, depth + 1);
            }

            fn.apply(f, depth);
        }
    }

    /**
     * 以某个目录为根路径，递归遍历所有内部文件。
     * @param root 根目录
     */
    public static void readAllFiles(File root) {

        ArrayList<Media> mediaList = new ArrayList<>();
        FileProcessor fp = new FileProcessor(mediaList::add);

        traverseDirTree(root, (file, depth) -> {
            fp.processFile(file); // 如果类型不受支持自动忽略。否则将媒体推入ArrayList
            return true;
        }, 0);

        for (Media media: mediaList) {
            System.out.println(media);
        }
    }

}
