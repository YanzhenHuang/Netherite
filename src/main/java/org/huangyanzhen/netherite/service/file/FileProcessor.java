package org.huangyanzhen.netherite.service.file;

import org.huangyanzhen.netherite.service.model.metadata.MediaMetadata;
import org.huangyanzhen.netherite.service.metadata.MetadataExtractorFactory;
import org.huangyanzhen.netherite.util.FileTypeUtil;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

public class FileProcessor {
    private final MetadataExtractorFactory metadataExtractorFactory;
    private final Consumer<MediaMetadata> fileHandler;

    public FileProcessor(Consumer<MediaMetadata> fileHandler) {
        this.metadataExtractorFactory = new MetadataExtractorFactory();
        this.fileHandler = fileHandler;
    }

    public void processFile(File file) {
        if (!file.isFile() || !FileTypeUtil.isSupported( file))
            return;

        metadataExtractorFactory.getExtractorForFile(file)
                .ifPresent(extractor -> {
                    Optional<MediaMetadata> metadata = extractor.extract(file);
                    metadata.ifPresentOrElse(
                            fileHandler,
                            () -> handleUnsupportedFormat(file)
                    );
                });
    }

    public void handleUnsupportedFormat(File file) {
        // TODO: Use Log4j
        System.out.printf("%s not supported.\n", file.getName());
    }
}
