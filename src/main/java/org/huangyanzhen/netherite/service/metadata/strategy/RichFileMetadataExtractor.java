package org.huangyanzhen.netherite.service.metadata.strategy;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import org.huangyanzhen.netherite.service.metadata.MetadataExtractor;
import org.huangyanzhen.netherite.service.model.metadata.MediaMetadata;
import org.huangyanzhen.netherite.service.model.metadata.subdirectory.EXIFData;
import org.huangyanzhen.netherite.service.model.metadata.subdirectory.GeoLocationData;
import org.huangyanzhen.netherite.util.FileTypeUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public abstract class RichFileMetadataExtractor implements MetadataExtractor {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");

    public abstract Metadata readMetadata(File file) throws IOException, ImageProcessingException;

    @Override
    public boolean supports(File file) {
        return FileTypeUtil.isRich(file);
    }

    @Override
    public Optional<MediaMetadata> extract(File file) {
        try {

            /*
             * 先透过MetadataReader获取图片的全部元数据。
             */
            Metadata metadata = readMetadata(file);
            Directory exifDir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            Directory gpsDir = metadata.getFirstDirectoryOfType(GpsDirectory.class);


            /*
             * 获取到数据后构建可以构建的MediaMetadata对象部分。
             */
            MediaMetadata.Builder metadataBuilder = new MediaMetadata.Builder()
                    .fileSystemDateTime(
                            Files.getLastModifiedTime(file.toPath())
                                    .toInstant().atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                    )
                    .filename(file.getName())
                    .isValid(true);

            // 图片没有EXIF字段，构建停止。
            if (exifDir != null) {

                EXIFData.Builder exifDataBuilder = new EXIFData.Builder();

                /*
                 * 尝试获取可用的EXIF字段并加入构建。
                 */
                if (exifDir.containsTag(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)) {
                    String _dateTime = exifDir.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                    LocalDateTime dateTime = LocalDateTime.parse(_dateTime, FORMATTER);
                    exifDataBuilder.datetimeOriginal(dateTime);
                }

                if (exifDir.containsTag(ExifSubIFDDirectory.TAG_DATETIME)) {
                    String _dateTime = exifDir.getString(ExifSubIFDDirectory.TAG_DATETIME);
                    LocalDateTime dateTime = LocalDateTime.parse(_dateTime, FORMATTER);
                    exifDataBuilder.datetime(dateTime);
                }

                if (exifDir.containsTag(ExifSubIFDDirectory.TAG_MAKE)) {
                    exifDataBuilder.make(exifDir.getString(ExifSubIFDDirectory.TAG_MAKE));
                }

                if (exifDir.containsTag(ExifSubIFDDirectory.TAG_MODEL)) {
                    exifDataBuilder.model(exifDir.getString(ExifSubIFDDirectory.TAG_MODEL));
                }

                metadataBuilder.exifData(exifDataBuilder.build());
            }

            if (gpsDir != null) {
                GeoLocationData.Builder geoLocationDataBuilder = new GeoLocationData.Builder();
                if (gpsDir.containsTag(GpsDirectory.TAG_LONGITUDE))
                    geoLocationDataBuilder.longitude(gpsDir.getLong(GpsDirectory.TAG_LONGITUDE));

                if (gpsDir.containsTag(GpsDirectory.TAG_LATITUDE))
                    geoLocationDataBuilder.latitude(gpsDir.getLong(GpsDirectory.TAG_LATITUDE));

                if (gpsDir.containsTag(GpsDirectory.TAG_ALTITUDE))
                    geoLocationDataBuilder.altitude(gpsDir.getDouble(GpsDirectory.TAG_ALTITUDE));

                metadataBuilder.geoLocationData(geoLocationDataBuilder.build());
            }

            return Optional.of(metadataBuilder.build());
        } catch (ImageProcessingException | IOException | MetadataException ignored) {
        }

        return Optional.empty();
    }
}
