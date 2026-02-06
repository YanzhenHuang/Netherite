package org.huangyanzhen.netherite.service.model.metadata.subdirectory;

import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

import javax.swing.text.html.Option;
import java.util.Optional;

public class GeoLocationData {
    private final GpsDirectory gpsDirectory;

    public GeoLocationData(Metadata metadata) {
        this.gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
    }

    /**
     * 判断GeoLocation是否为空
     *
     * @return GeoLocation完全为空
     */
    public boolean isEmpty() {
        return gpsDirectory == null;
    }

    /**
     * 获取纬度
     * @return 纬度
     */
    public Optional<String> getLatitude() {
        if (gpsDirectory == null) return Optional.empty();
        return Optional.of(gpsDirectory.getString(GpsDirectory.TAG_LATITUDE));
    }

    /**
     * 获取经度
     * @return 经度
     */
    public Optional<String> getLongitude() {
        if (gpsDirectory == null) return Optional.empty();
        return Optional.of(gpsDirectory.getString(GpsDirectory.TAG_LONGITUDE));
    }

    /**
     * 获取高度
     * @return 高度
     */
    public Optional<String> getAltitude() {
        if (gpsDirectory == null) return Optional.empty();
        return Optional.of(gpsDirectory.getString(GpsDirectory.TAG_ALTITUDE));
    }

    /**
     * 获取GPS时间戳
     * @return GPS时间戳
     */
    public Optional<String> getGPSTimestamp() {
        if (gpsDirectory == null) return Optional.empty();
        return Optional.of(gpsDirectory.getString(GpsDirectory.TAG_TIME_STAMP));
    }

    /**
     * 获取电量
     * @return 电量
     */
    public Optional<String> getBattery(){
        if (gpsDirectory == null) return Optional.empty();
        return Optional.of(gpsDirectory.getString(GpsDirectory.TAG_BATTERY_LEVEL));
    }

    @Override
    public String toString() {
        if (isEmpty()) return "<Empty>";

        StringBuilder sb = new StringBuilder();
        sb.append("{\n\t\tLatitude: ").append(getLatitude())
                .append(", \n\t\tLongitude: ").append(getLongitude())
                .append(", \n\t\tAltitude: ").append(getAltitude())
                .append(", \n\t\tGPSTimestamp: ").append(getGPSTimestamp())
                .append(", \n\t\tBattery: ").append(getBattery())
                .append(" \n\t}");
        return sb.toString();
    }
}
