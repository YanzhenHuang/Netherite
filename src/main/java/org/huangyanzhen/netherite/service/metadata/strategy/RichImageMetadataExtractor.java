package org.huangyanzhen.netherite.service.metadata.strategy;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import org.huangyanzhen.netherite.util.FileTypeUtil;
import org.huangyanzhen.netherite.util.MediaType;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class RichImageMetadataExtractor extends RichFileMetadataExtractor {

    @Override
    public Metadata readMetadata(File file) throws ImageProcessingException, IOException {
        return ImageMetadataReader.readMetadata(file);
    }

    @Override
    public boolean supports(File file) {
        return FileTypeUtil.getMediaType(file) == MediaType.IMAGE;
    }

}
