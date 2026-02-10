package org.huangyanzhen.netherite.service.file;

import org.huangyanzhen.netherite.service.model.media.Media;
import org.huangyanzhen.netherite.service.model.metadata.MediaMetadata;
import org.huangyanzhen.netherite.service.metadata.MetadataExtractorFactory;
import org.huangyanzhen.netherite.util.FileTypeUtil;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public class FileProcessor {
    private final MetadataExtractorFactory metadataExtractorFactory;
    private final Consumer<Media> mediaHandler;

    public FileProcessor(Consumer<Media> mediaHandler) {
        this.metadataExtractorFactory = new MetadataExtractorFactory();
        this.mediaHandler = mediaHandler;
    }

    public void processFile(File file) {
        if (!FileTypeUtil.isSupported(file))
            return;

        metadataExtractorFactory.getExtractorForFile(file)
                // Extractor Exists
                .ifPresent(extractor -> {
                    // Obtain file, extract metadata, and wrap file and metadata into media.
                    Optional<MediaMetadata> metadata = extractor.extract(file);
                    metadata.ifPresentOrElse(
                            (mediaMetaData) -> {
                                // let media as the callback input
                                mediaHandler.accept(new Media(file, mediaMetaData));
                            },
                            () -> handleUnsupportedFormat(file)
                    );
                });
    }

    public void processBatchedFiles(Collection<File> files) {
        for (File file: files) {
            processFile(file);
        }
    }

    public void handleUnsupportedFormat(File file) {
        // TODO: Use Log4j
        System.out.printf("%s not supported.\n", file.getName());
    }
}
