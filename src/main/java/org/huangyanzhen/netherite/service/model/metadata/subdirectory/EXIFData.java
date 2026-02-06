package org.huangyanzhen.netherite.service.model.metadata.subdirectory;

import java.time.LocalDateTime;

public record EXIFData(
    LocalDateTime datetimeOriginal,
    LocalDateTime datetime,
    String make,
    String model
) {
    public static class Builder {
        LocalDateTime datetimeOriginal;
        LocalDateTime datetime;
        String make;
        String model;

        public Builder datetimeOriginal(LocalDateTime datetimeOriginal) {
            this.datetimeOriginal = datetimeOriginal;
            return this;
        }

        public Builder datetime(LocalDateTime datetime) {
            this.datetime = datetime;
            return this;
        }

        public Builder make(String make) {
            this.make = make;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public EXIFData build() {
            return new EXIFData(datetimeOriginal, datetime, make, model);
        }
    }
}
