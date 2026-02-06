package org.huangyanzhen.netherite.service.model.media;

import org.huangyanzhen.netherite.service.model.metadata.MediaMetadata;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Media {
    private final File file;
    private final MediaMetadata mediaMetaData;

    public Media(File file, MediaMetadata mediaMetaData) {
        if (file == null)
            throw new IllegalArgumentException("File should not be null!");

        this.file = file;
        this.mediaMetaData = mediaMetaData;
    }

    /**
     * 获取媒体文件名
     *
     * @return 媒体文件名
     */
    public String getFilename() {
        return file.getName();
    }

    /**
     * 获取媒体文件路径
     *
     * @return 媒体文件路径
     */
    public String getFilePath() {
        return file.getAbsolutePath();
    }

    /**
     * 获取媒体文件大小（KB）
     *
     * @return 媒体文件大小
     */
    public String getFileSizeKB() {
        return String.format("%.2f KB", file.length() / 1024.0);
    }


    /**
     * 获取媒体文件系统时间
     *
     * @return 媒体文件系统时间
     */
    public LocalDateTime getFileSystemTime() {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(file.lastModified()),
                ZoneId.systemDefault()
        );
    }

    /**
     * 获取媒体元数据
     *
     * @return 媒体元数据
     */
    public MediaMetadata getMediaMetaData() {
        return mediaMetaData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("======== Media ========")
                .append("\nPath:").append(getFilePath())
                .append("\nName:").append(getFilename())
                .append("\nSize:").append(getFileSizeKB()).append("KB")
                .append("\n").append(getMediaMetaData());

        return sb.toString();
    }
}
