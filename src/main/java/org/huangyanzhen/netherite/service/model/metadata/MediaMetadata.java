package org.huangyanzhen.netherite.service.model.metadata;

import org.huangyanzhen.netherite.service.model.metadata.subdirectory.EXIFData;
import org.huangyanzhen.netherite.service.model.metadata.subdirectory.GeoLocationData;

public record MediaMetadata(
        EXIFData exifData,
        GeoLocationData geoLocationData
) {
    public static class Builder {

        private EXIFData exifData;
        private GeoLocationData geoLocationData;

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
                    exifData,
                    geoLocationData
            );
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MediaMetadata {")
                .append("\n\texifData: ").append(exifData)
                .append("\n\tgeoLocationData: ").append(geoLocationData)
                .append("\n}");

        return sb.toString();
    }
}

