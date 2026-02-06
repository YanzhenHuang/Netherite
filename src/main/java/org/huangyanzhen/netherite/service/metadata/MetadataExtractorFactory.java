package org.huangyanzhen.netherite.service.metadata;

import org.huangyanzhen.netherite.service.metadata.strategy.RichImageMetadataExtractor;
import org.huangyanzhen.netherite.service.metadata.strategy.RichMp4MetadataExtractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MetadataExtractorFactory {
    private final List<MetadataExtractor> extractors;

    public MetadataExtractorFactory() {
        extractors = new ArrayList<>();
        extractors.add(new RichImageMetadataExtractor());
        extractors.add(new RichMp4MetadataExtractor());
    }

    public Optional<MetadataExtractor> getExtractorForFile(File file) {
        for (MetadataExtractor extractor : extractors) {
            if (extractor.supports(file))
                return Optional.of(extractor);
        }

        return Optional.empty();
    }
}
