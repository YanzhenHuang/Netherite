package org.huangyanzhen.netherite.service.metadata.strategy;

import com.drew.imaging.mp4.Mp4MetadataReader;
import com.drew.metadata.Metadata;
import org.huangyanzhen.netherite.util.FileTypeUtil;
import org.huangyanzhen.netherite.util.types.VideoFileTypes;

import java.io.File;
import java.io.IOException;

public class RichMp4MetadataExtractor extends RichFileMetadataExtractor {

    @Override
    public Metadata readMetadata(File file) throws IOException {
        return Mp4MetadataReader.readMetadata(file);
    }

    @Override
    public boolean supports(File file) {
        return FileTypeUtil.getExtension(file).equals(VideoFileTypes.MP4);
    }

}
