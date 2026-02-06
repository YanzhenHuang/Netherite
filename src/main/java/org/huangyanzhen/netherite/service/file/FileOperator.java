package org.huangyanzhen.netherite.service.file;

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
}
