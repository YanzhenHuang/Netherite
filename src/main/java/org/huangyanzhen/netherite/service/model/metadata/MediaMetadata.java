package org.huangyanzhen.netherite.service.model.metadata;

import org.huangyanzhen.netherite.service.model.metadata.directory.strategy.EXIFData;
import org.huangyanzhen.netherite.service.model.metadata.directory.strategy.GeoLocationData;
import org.huangyanzhen.netherite.service.model.metadata.directory.strategy.Mp4Data;
import org.huangyanzhen.netherite.util.log.obj2json.ObjectString;

public class MediaMetadata {

    public final EXIFData exifData;
    public final GeoLocationData geoLocationData;
    public final Mp4Data mp4Data;

    public MediaMetadata(Builder builder) {
        this.exifData = builder.exifData;
        this.geoLocationData = builder.geoLocationData;
        this.mp4Data = builder.mp4Data;
    }

    public static class Builder {

        private EXIFData exifData;
        private GeoLocationData geoLocationData;
        private Mp4Data mp4Data;

        public Builder exifData(EXIFData exifData) {
            this.exifData = exifData;
            return this;
        }

        public Builder geoLocationData(GeoLocationData geoLocationData) {
            this.geoLocationData = geoLocationData;
            return this;
        }

        public Builder mp4Data(Mp4Data mp4Data) {
            this.mp4Data = mp4Data;
            return this;
        }

        public MediaMetadata build() {
            return new MediaMetadata(this);
        }
    }

    @Override
    public String toString() {
        ObjectString obs = new ObjectString(1);
        obs.put("exifData", exifData)
                .put("geoLocationData", geoLocationData)
                .put("mp4Data", mp4Data);
        return obs.toString();

//        return sb.toString();
    }
}

