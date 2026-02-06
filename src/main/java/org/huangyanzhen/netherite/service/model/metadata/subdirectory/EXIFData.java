package org.huangyanzhen.netherite.service.model.metadata.subdirectory;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifThumbnailDirectory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeMap;

public class EXIFData {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");

    private final ExifIFD0Directory d0Dir;
    private final ExifSubIFDDirectory subIfDir;
    private final ExifThumbnailDirectory thumbnailDir;

    private final ArrayList<Directory> dirs = new ArrayList<>();

    public EXIFData(Metadata metadata) {
        this.d0Dir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        this.subIfDir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        this.thumbnailDir = metadata.getFirstDirectoryOfType(ExifThumbnailDirectory.class);
    }

    /**
     * 根据优先级，从EXIF中获取最合适的时间。
     * @return Optional
     */
    public Optional<LocalDateTime> getRecommendedExifTime() {

        if (subIfDir == null) return Optional.empty();

        if (subIfDir.containsTag(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL))
            return Optional.of(
                    LocalDateTime.parse(subIfDir.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL), EXIFData.FORMATTER)
            );

        if (subIfDir.containsTag(ExifSubIFDDirectory.TAG_DATETIME))
            return Optional.of(
                    LocalDateTime.parse(subIfDir.getString(ExifSubIFDDirectory.TAG_DATETIME), EXIFData.FORMATTER)
            );

        if (d0Dir == null) return Optional.empty();

        if (d0Dir.containsTag(ExifIFD0Directory.TAG_DATETIME))
            return Optional.of(
                    LocalDateTime.parse(d0Dir.getString(ExifIFD0Directory.TAG_DATETIME), EXIFData.FORMATTER)
            );

        return Optional.empty();
    }

    public Optional<String> getDeviceManufacturer() {
        if (d0Dir == null) return Optional.empty();
        return Optional.ofNullable(d0Dir.getString(ExifIFD0Directory.TAG_MAKE));
    }

    public Optional<String> getDeviceModel() {
        if (d0Dir == null) return Optional.empty();
        return Optional.ofNullable(d0Dir.getString(ExifIFD0Directory.TAG_MODEL));
    }
}
