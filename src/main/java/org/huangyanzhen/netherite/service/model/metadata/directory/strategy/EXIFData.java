package org.huangyanzhen.netherite.service.model.metadata.directory.strategy;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifThumbnailDirectory;
import javafx.util.Pair;
import org.huangyanzhen.netherite.service.model.metadata.directory.DirectoryData;
import org.huangyanzhen.netherite.util.log.obj2json.ObjectString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;

public class EXIFData extends DirectoryData {

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

    /**
     * 判断EXIF是否为空
     *
     * @return EXIF完全为空
     */
    @Override
    public boolean isEmpty() {
        return isDirectoryEmpty(d0Dir)
                || isDirectoryEmpty(subIfDir)
                || isDirectoryEmpty(thumbnailDir);
    }

    /**
     * 根据优先级，从EXIF中获取最合适的时间。
     *
     * @return Optional
     */
    public Optional<LocalDateTime> getRecommendedExifTime() {

        return getBestExist(Stream.of(
                new Pair<Directory, Integer>(subIfDir, ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL),
                new Pair<>(subIfDir, ExifSubIFDDirectory.TAG_DATETIME),
                new Pair<>(d0Dir, ExifIFD0Directory.TAG_DATETIME)).toList()
        ).map(this::parseTime);
    }

    /**
     * EXIF字段：设备制造商
     *
     * @return 设备制造商 （可选）
     */
    public Optional<String> getDeviceManufacturer() {
        return get(d0Dir, ExifIFD0Directory.TAG_MAKE);
    }

    /**
     * EXIF字段：设备型号
     *
     * @return 设备型号 （可选）
     */
    public Optional<String> getDeviceModel() {
        return get(d0Dir, ExifIFD0Directory.TAG_MODEL);
    }

    /**
     * EXIF字段：光圈值
     *
     * @return 光圈值 （可选）
     */
    public Optional<String> getFNumber() {
        return get(subIfDir, ExifSubIFDDirectory.TAG_FNUMBER);
    }

    /**
     * EXIF字段：曝光时间
     *
     * @return 曝光时间 （可选）
     */
    public Optional<String> getExposureTime() {
        return get(subIfDir, ExifSubIFDDirectory.TAG_EXPOSURE_TIME);
    }

    /**
     * EXIF字段：ISO
     *
     * @return ISO （可选）
     */
    public Optional<String> getISO() {
        return get(subIfDir, ExifSubIFDDirectory.TAG_ISO_EQUIVALENT);
    }

    /**
     * EXIF字段：闪光灯
     *
     * @return 闪光灯 （可选）
     */
    public Optional<String> getFlash() {
        return get(subIfDir, ExifSubIFDDirectory.TAG_FLASH);
    }

    /**
     * EXIF字段：白平衡
     *
     * @return 白平衡 （可选）
     */
    public Optional<String> getWhiteBalance() {
        return get(subIfDir, ExifSubIFDDirectory.TAG_WHITE_BALANCE);
    }

    /**
     * EXIF字段：镜头制造商
     *
     * @return 镜头制造商 （可选）
     */
    public Optional<String> getLensManufacturer() {
        return get(subIfDir, ExifSubIFDDirectory.TAG_LENS_MAKE);
    }

    /**
     * EXIF字段：镜头型号
     *
     * @return 镜头型号 （可选）
     */
    public Optional<String> getLensModel() {
        return get(subIfDir, ExifSubIFDDirectory.TAG_LENS_MODEL);
    }

    /**
     * EXIF字段：颜色空间
     *
     * @return 颜色空间 （可选）
     */
    public Optional<String> getColorSpace() {
        return get(subIfDir, ExifSubIFDDirectory.TAG_COLOR_SPACE);
    }

    /**
     * EXIF字段：压缩方式
     *
     * @return 压缩方式 （可选）
     */
    public Optional<String> getCompression() {
        return get(subIfDir, ExifSubIFDDirectory.TAG_COMPRESSION);
    }

    /**
     * EXIF字段：图片方向
     *
     * @return 图片方向 （可选）
     */
    public Optional<String> getOrientation() {
        return get(subIfDir, ExifSubIFDDirectory.TAG_ORIENTATION);
    }

    /**
     * EXIF字段：分辨率, X-Y
     *
     * @return 分辨率（可选）
     */
    public Optional<Pair<String, String>> getResolution() {
        return getBothOrNone(subIfDir,
                ExifSubIFDDirectory.TAG_X_RESOLUTION,
                ExifSubIFDDirectory.TAG_Y_RESOLUTION);
    }

    @Override
    public String toString() {
        if (isEmpty()) return "{}";
        ObjectString obs = new ObjectString(2);
        obs.put("RecommendedDateTime", getRecommendedExifTime().toString())
                .put("Manufacturer", getDeviceManufacturer().toString())
                .put("DeviceModel", getDeviceModel().toString())
                .put("FNumber", getFNumber().toString())
                .put("ExposureTime", getExposureTime().toString())
                .put("ISO", getISO().toString())
                .put("Flash", getFlash().toString())
                .put("WhiteBalance", getWhiteBalance().toString())
                .put("LensManufacturer", getLensManufacturer().toString())
                .put("LensModel", getLensModel().toString())
                .put("ColorSpace", getColorSpace().toString())
                .put("Compression", getCompression().toString())
                .put("Orientation", getOrientation().toString())
                .put("Resolution", getResolution().toString());
        return obs.toString();
    }
}
