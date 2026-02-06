package org.huangyanzhen.netherite.service.metadata.strategy;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
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
import java.util.Collection;
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
                    .exifData(new EXIFData(metadata))
                    .isValid(true);

            return Optional.of(metadataBuilder.build());
        } catch (ImageProcessingException | IOException ignored) {
        }

        return Optional.empty();
    }
}
