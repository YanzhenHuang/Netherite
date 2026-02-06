package org.huangyanzhen.netherite.service.model.metadata.media;

import org.huangyanzhen.netherite.service.model.metadata.MediaMetadata;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class Media {
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
     * @return 媒体文件名
     */
    public String getFilename() {
        return file.getName();
    }

    /**
     * 获取媒体文件大小（KB）
     * @return 媒体文件大小
     */
    public String getFileSizeKB() {
        return String.format("%.2f KB", file.length() / 1024.0);
    }


    /**
     * 获取媒体文件系统时间
     * @return 媒体文件系统时间
     */
    public LocalDateTime getFileSystemTime() {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(file.lastModified()),
                ZoneId.systemDefault()
        );
    }

    public MediaMetadata getMediaMetaData() {
        return mediaMetaData;
    }
}
