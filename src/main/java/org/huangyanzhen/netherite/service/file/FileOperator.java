package org.huangyanzhen.netherite.service.file;

import org.huangyanzhen.netherite.service.model.metadata.MediaMetadata;

import java.io.File;
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

    public static void readAllFiles(File root) {

        FileProcessor fp = new FileProcessor(mediaMetadata -> System.out.println(mediaMetadata));
        traverseDirTree(root, (file, depth) -> {
//            MediaMetadata metadata = FileProcessor.pro(file);
            fp.processFile(file);

            System.out.println(file.getAbsolutePath());
            return true;
        }, 0);
    }

}
