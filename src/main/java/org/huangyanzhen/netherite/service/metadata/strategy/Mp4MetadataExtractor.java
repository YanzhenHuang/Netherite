package org.huangyanzhen.netherite.service.metadata.strategy;

import com.drew.imaging.mp4.Mp4MetadataReader;
import com.drew.metadata.Metadata;
import org.huangyanzhen.netherite.service.metadata.MediaMetadataExtractor;
import org.huangyanzhen.netherite.service.model.metadata.MediaMetadata;
import org.huangyanzhen.netherite.service.model.metadata.directory.strategy.EXIFData;
import org.huangyanzhen.netherite.service.model.metadata.directory.strategy.GeoLocationData;
import org.huangyanzhen.netherite.service.model.metadata.directory.strategy.Mp4Data;
import org.huangyanzhen.netherite.util.FileTypeUtil;
import org.huangyanzhen.netherite.util.types.VideoFileTypes;

import java.io.File;
import java.io.IOException;

public class Mp4MetadataExtractor extends MediaMetadataExtractor {

    @Override
    public Metadata readMetadata(File file) throws IOException {
        return Mp4MetadataReader.readMetadata(file);
    }

    @Override
    public boolean supports(File file) {
        return FileTypeUtil.getExtension(file).equals(VideoFileTypes.MP4);
    }

    @Override
    public MediaMetadata.Builder buildMetadata(Metadata metadata) {
        return new MediaMetadata.Builder()
                .exifData(new EXIFData(metadata))
                .geoLocationData(new GeoLocationData(metadata))
                .mp4Data(new Mp4Data(metadata));
    }

}
