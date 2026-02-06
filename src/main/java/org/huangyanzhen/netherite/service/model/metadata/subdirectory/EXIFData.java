package org.huangyanzhen.netherite.service.model.metadata.subdirectory;

import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifThumbnailDirectory;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EXIFData {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");

    /**
     * 三个主要的EXIF Directory
     */
    private final ExifIFD0Directory d0Dir;
    private final ExifSubIFDDirectory subIfDir;
    private final ExifThumbnailDirectory thumbnailDir;

    public EXIFData(Metadata metadata) {
        this.d0Dir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        this.subIfDir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        this.thumbnailDir = metadata.getFirstDirectoryOfType(ExifThumbnailDirectory.class);
    }

    private LocalDateTime parseTime(String timeStr) {
        return LocalDateTime.parse(timeStr, FORMATTER);
    }

    /**
     * 判断EXIF是否为空
     *
     * @return EXIF完全为空
     */
    public boolean isEmpty() {
        return d0Dir == null && subIfDir == null && thumbnailDir == null;
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

        // 文件写入时间，非拍摄时间
        if (d0Dir.containsTag(ExifIFD0Directory.TAG_DATETIME))
            return Optional.of(parseTime(d0Dir.getString(ExifIFD0Directory.TAG_DATETIME)));

        return Optional.empty();
    }

    /**
     * EXIF字段：设备制造商
     *
     * @return 设备制造商 （可选）
     */
    public Optional<String> getDeviceManufacturer() {
        if (d0Dir == null) return Optional.empty();
        return Optional.ofNullable(d0Dir.getString(ExifIFD0Directory.TAG_MAKE));
    }

    /**
     * EXIF字段：设备型号
     *
     * @return 设备型号 （可选）
     */
    public Optional<String> getDeviceModel() {
        if (d0Dir == null) return Optional.empty();
        return Optional.ofNullable(d0Dir.getString(ExifIFD0Directory.TAG_MODEL));
    }

    /**
     * EXIF字段：光圈值
     *
     * @return 光圈值 （可选）
     */
    public Optional<String> getFNumber() {
        if (subIfDir == null) return Optional.empty();
        return Optional.ofNullable(subIfDir.getString(ExifSubIFDDirectory.TAG_FNUMBER));
    }

    /**
     * EXIF字段：曝光时间
     *
     * @return 曝光时间 （可选）
     */
    public Optional<String> getExposureTime() {
        if (subIfDir == null) return Optional.empty();
        return Optional.ofNullable(subIfDir.getString(ExifSubIFDDirectory.TAG_EXPOSURE_TIME));
    }

    /**
     * EXIF字段：ISO
     *
     * @return ISO （可选）
     */
    public Optional<String> getISO() {
        if (subIfDir == null) return Optional.empty();
        return Optional.ofNullable(subIfDir.getString(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
    }

    /**
     * EXIF字段：闪光灯
     *
     * @return 闪光灯 （可选）
     */
    public Optional<String> getFlash() {
        if (subIfDir == null) return Optional.empty();
        return Optional.ofNullable(subIfDir.getString(ExifSubIFDDirectory.TAG_FLASH));
    }

    /**
     * EXIF字段：白平衡
     *
     * @return 白平衡 （可选）
     */
    public Optional<String> getWhiteBalance() {
        if (subIfDir == null) return Optional.empty();
        return Optional.ofNullable(subIfDir.getString(ExifSubIFDDirectory.TAG_WHITE_BALANCE));
    }

    /**
     * EXIF字段：镜头制造商
     * @return 镜头制造商 （可选）
     */
    public Optional<String> getLensManufacturer() {
        if (subIfDir == null) return Optional.empty();
        return Optional.ofNullable(subIfDir.getString(ExifSubIFDDirectory.TAG_LENS_MAKE));
    }

    /**
     * EXIF字段：镜头型号
     * @return 镜头型号 （可选）
     */
    public Optional<String> getLensModel() {
        if (subIfDir == null) return Optional.empty();
        return Optional.ofNullable(subIfDir.getString(ExifSubIFDDirectory.TAG_LENS_MODEL));
    }

    /**
     * EXIF字段：颜色空间
     * @return 颜色空间 （可选）
     */
    public Optional<String> getColorSpace() {
        if (d0Dir == null) return Optional.empty();
        return Optional.ofNullable(d0Dir.getString(ExifIFD0Directory.TAG_COLOR_SPACE));
    }

    /**
     * EXIF字段：压缩方式
     * @return 压缩方式 （可选）
     */
    public Optional<String> getCompression() {
        if (d0Dir == null) return Optional.empty();
        return Optional.ofNullable(d0Dir.getString(ExifIFD0Directory.TAG_COMPRESSION));
    }

    /**
     * EXIF字段：图片方向
     * @return 图片方向 （可选）
     */
    public Optional<String> getOrientation() {
        if (d0Dir == null) return Optional.empty();
        return Optional.ofNullable(d0Dir.getString(ExifIFD0Directory.TAG_ORIENTATION));
    }

    /**
     * EXIF字段：分辨率, X-Y
     * @return 分辨率（可选）
     */
    public Optional<Pair<String, String>> getResolution() {
        if (d0Dir == null ||
                !d0Dir.containsTag(ExifIFD0Directory.TAG_X_RESOLUTION) ||
                !d0Dir.containsTag(ExifSubIFDDirectory.TAG_Y_RESOLUTION))
            return Optional.empty();
        return Optional.of(new Pair<String, String>(
                d0Dir.getString(ExifIFD0Directory.TAG_X_RESOLUTION),
                d0Dir.getString(ExifIFD0Directory.TAG_Y_RESOLUTION)
        ));
    }

    @Override
    public String toString() {
        if (isEmpty()) return "<Empty>";

        StringBuilder sb = new StringBuilder();
        sb.append("{\n\t\tRecommendedDateTime: ").append(getRecommendedExifTime())
                .append(", \n\t\tManufacturer: ").append(getDeviceManufacturer())
                .append(", \n\t\tDeviceModel: ").append(getDeviceModel())
                .append(", \n\t\tFNumber: ").append(getFNumber())
                .append(", \n\t\tExposureTime: ").append(getExposureTime())
                .append(", \n\t\tISO: ").append(getISO())
                .append(", \n\t\tFlash: ").append(getFlash())
                .append(", \n\t\tWhiteBalance: ").append(getWhiteBalance())
                .append(", \n\t\tLensManufacturer: ").append(getLensManufacturer())
                .append(", \n\t\tLensModel: ").append(getLensModel())
                .append(", \n\t\tColorSpace: ").append(getColorSpace())
                .append(", \n\t\tCompression: ").append(getCompression())
                .append(", \n\t\tOrientation: ").append(getOrientation())
                .append(", \n\t\tResolution: ").append(getResolution())
                .append(" \n\t}");
        return sb.toString();
    }
}
