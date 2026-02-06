package org.huangyanzhen.netherite.service.model.metadata.subdirectory;

public record GeoLocationData (
        double latitude,
        double longitude,
        double altitude
) {
    public static class Builder {
        double latitude;
        double longitude;
        double altitude;

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder altitude(double altitude) {
            this.altitude = altitude;
            return this;
        }

        public GeoLocationData build() {
            return new GeoLocationData(latitude, longitude, altitude);
        }
    }
}
