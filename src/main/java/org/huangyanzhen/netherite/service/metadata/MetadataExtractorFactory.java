package org.huangyanzhen.netherite.service.metadata;

import org.huangyanzhen.netherite.service.metadata.strategy.ImageMetadataExtractor;
import org.huangyanzhen.netherite.service.metadata.strategy.Mp4MetadataExtractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MetadataExtractorFactory {
    private final List<MetadataExtractor> extractors;

    public MetadataExtractorFactory() {
        extractors = new ArrayList<>();
        extractors.add(new ImageMetadataExtractor());
        extractors.add(new Mp4MetadataExtractor());
    }

    public Optional<MetadataExtractor> getExtractorForFile(File file) {
        for (MetadataExtractor extractor : extractors) {
            if (extractor.supports(file))
                return Optional.of(extractor);
        }

        return Optional.empty();
    }
}
