package org.huangyanzhen.netherite.service.model.metadata;

import org.huangyanzhen.netherite.service.model.metadata.subdirectory.EXIFData;
import org.huangyanzhen.netherite.service.model.metadata.subdirectory.GeoLocationData;

import java.time.LocalDateTime;

public record MediaMetadata(
        LocalDateTime fileSystemDateTime,
        String filename,
        boolean isValid,
        EXIFData exifData,
        GeoLocationData geoLocationData
) {
    public static class Builder {
        private LocalDateTime fileSystemDateTime;
        private String filename;
        private boolean isValid;

        private EXIFData exifData;
        private GeoLocationData geoLocationData;

        public Builder fileSystemDateTime(LocalDateTime fileSystemDateTime) {
            this.fileSystemDateTime = fileSystemDateTime;
            return this;
        }

        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder isValid(boolean isValid) {
            this.isValid = isValid;
            return this;
        }

        public Builder exifData(EXIFData exifData) {
            this.exifData = exifData;
            return this;
        }

        public Builder geoLocationData(GeoLocationData geoLocationData) {
            this.geoLocationData = geoLocationData;
            return this;
        }

        public MediaMetadata build() {
            return new MediaMetadata(
                    fileSystemDateTime,
                    filename,
                    isValid,
                    exifData,
                    geoLocationData
            );
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MediaMetadata {")
                .append("\n\tfileSystemDateTime: ").append(fileSystemDateTime)
                .append("\n\tfilename: ").append(filename)
                .append("\n\texifData: ").append(exifData)
                .append("\n}");

        return sb.toString();
    }
}

