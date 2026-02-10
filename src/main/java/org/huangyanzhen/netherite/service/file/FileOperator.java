package org.huangyanzhen.netherite.service.file;

import org.huangyanzhen.netherite.service.model.media.Media;
import org.huangyanzhen.netherite.util.FileTypeUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class FileOperator {

    /**
     * 递归遍历目录树，寻找合适的文件。
     *
     * @param dir   根目录
     * @param fn    文件处理函数
     * @param depth 递归深度
     */
    private static void traverseDirTree(File dir, BiFunction<File, Integer, Boolean> fn, int depth, int maxDepth) {
        if (!dir.isDirectory() || depth > maxDepth) {
            fn.apply(dir, depth);
            return;
        }

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if (f == null || !f.exists()) {
                continue;
            }

            if (f.isDirectory() && Objects.requireNonNull(f.listFiles()).length != 0) {
                traverseDirTree(f, fn, depth + 1, maxDepth);
            }

            fn.apply(f, depth);
        }
    }

    /**
     * 以某个目录为根路径，递归遍历所有内部文件。
     *
     * @param root 根目录
     */
    public static void readAllFiles(File root) {
        List<File> batchedFiles = new ArrayList<>();
        FileProcessor fp = new FileProcessor(System.out::println);

        traverseDirTree(
                root,
                (file, depth) -> {
                    if (!FileTypeUtil.isSupported(file)) {
                        return true;
                    }
                    batchedFiles.add(file);
                    if (batchedFiles.size() >= 20) {
                        fp.processBatchedFiles(batchedFiles);
                        batchedFiles.clear();
                    }
                    return true;
                }, 0, 20);

        if (!batchedFiles.isEmpty()) {
            fp.processBatchedFiles(batchedFiles);
        }
        batchedFiles.clear();
    }

    /**
     * 复制文件
     *
     * @param src  源文件
     * @param dest 新文件
     * @return 是否成功
     */
    public static boolean copyFile(File src, File dest) {
        if (!src.exists() || src.isDirectory() || dest.exists())
            return false;

        try {
            Path srcPath = src.toPath();
            Path destPath = dest.toPath();
            Files.copy(srcPath, destPath);
            return true;
        } catch (IOException e) {
            return false;
        }

    }
}
