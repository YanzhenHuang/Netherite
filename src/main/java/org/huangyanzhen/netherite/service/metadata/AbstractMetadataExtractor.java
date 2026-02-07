package org.huangyanzhen.netherite.service.metadata;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import org.huangyanzhen.netherite.service.model.metadata.MediaMetadata;
import org.huangyanzhen.netherite.service.model.metadata.directory.strategy.EXIFData;
import org.huangyanzhen.netherite.service.model.metadata.directory.strategy.GeoLocationData;
import org.huangyanzhen.netherite.util.FileTypeUtil;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public abstract class AbstractMetadataExtractor implements MetadataExtractor {

    /**
     * 不同的文件类型，需要不同的元数据读取器。
     * @param file 文件对象
     * @return Metadata对象
     * @throws IOException 读取文件时发生IO异常
     * @throws ImageProcessingException 图片处理（读取Metadata）时发生异常
     */
    public abstract Metadata readMetadata(File file)
            throws IOException, ImageProcessingException;

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
            if (metadata == null) {
                return Optional.empty();
            }

            /*
             * 获取到数据后构建可以构建的MediaMetadata对象部分。
             */
            MediaMetadata.Builder metadataBuilder = new MediaMetadata.Builder()
                    .exifData(new EXIFData(metadata))
                    .geoLocationData(new GeoLocationData(metadata));

            return Optional.of(metadataBuilder.build());
        } catch (ImageProcessingException | IOException ignored) {
        }

        return Optional.empty();
    }
}
