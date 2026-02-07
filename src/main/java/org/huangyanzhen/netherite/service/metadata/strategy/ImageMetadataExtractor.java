package org.huangyanzhen.netherite.service.metadata.strategy;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import org.huangyanzhen.netherite.service.metadata.MediaMetadataExtractor;
import org.huangyanzhen.netherite.service.model.metadata.MediaMetadata;
import org.huangyanzhen.netherite.service.model.metadata.directory.strategy.EXIFData;
import org.huangyanzhen.netherite.service.model.metadata.directory.strategy.GeoLocationData;
import org.huangyanzhen.netherite.util.FileTypeUtil;
import org.huangyanzhen.netherite.util.MediaType;

import java.io.File;
import java.io.IOException;

public class ImageMetadataExtractor extends MediaMetadataExtractor {

    @Override
    public Metadata readMetadata(File file) throws ImageProcessingException, IOException {
        return ImageMetadataReader.readMetadata(file);
    }

    @Override
    public boolean supports(File file) {
        return FileTypeUtil.getMediaType(file).equals(MediaType.IMAGE);
    }

    @Override
    public MediaMetadata.Builder buildMetadata(Metadata metadata) {
        return new MediaMetadata.Builder()
                .exifData(new EXIFData(metadata))
                .geoLocationData(new GeoLocationData(metadata));
    }

}
