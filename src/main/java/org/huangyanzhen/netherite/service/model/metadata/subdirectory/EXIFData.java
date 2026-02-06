package org.huangyanzhen.netherite.service.model.metadata.subdirectory;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifThumbnailDirectory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

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

    private LocalDateTime parseTime(String timeStr){
        return LocalDateTime.parse(timeStr, FORMATTER);
    }

    /**
     * 根据优先级，从EXIF中获取最合适的时间。
     *
     * @return Optional
     */
    public Optional<LocalDateTime> getRecommendedExifTime() {

        if (subIfDir == null) return Optional.empty();

        // Original Date Time
        if (subIfDir.containsTag(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL))
            return Optional.of(parseTime(subIfDir.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)));

        // Date Time
        if (subIfDir.containsTag(ExifSubIFDDirectory.TAG_DATETIME))
            return Optional.of(parseTime(subIfDir.getString(ExifSubIFDDirectory.TAG_DATETIME)));

        if (d0Dir == null) return Optional.empty();

        // D0Dir Tag Datetime
        if (d0Dir.containsTag(ExifIFD0Directory.TAG_DATETIME))
            return Optional.of(parseTime(d0Dir.getString(ExifIFD0Directory.TAG_DATETIME)));

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("{RecommendedDateTime: %s, ", getRecommendedExifTime()))
                .append(String.format("Manufacturer: %s, ", getDeviceManufacturer()))
                .append(String.format("DeviceModel: %s}", getDeviceModel()));

        return sb.toString();
    }
}
