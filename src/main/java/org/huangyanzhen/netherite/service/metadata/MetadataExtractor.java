package org.huangyanzhen.netherite.service.metadata;

import org.huangyanzhen.netherite.service.model.metadata.MediaMetadata;

import java.io.File;
import java.util.Optional;

public interface MetadataExtractor {
    boolean supports(File file);
    Optional<MediaMetadata> extract(File file);
}
