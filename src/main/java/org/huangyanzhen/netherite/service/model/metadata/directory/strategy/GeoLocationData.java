package org.huangyanzhen.netherite.service.model.metadata.directory.strategy;

import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import javafx.util.Pair;
import org.huangyanzhen.netherite.service.model.metadata.directory.DirectoryData;

import java.util.Optional;

public class GeoLocationData extends DirectoryData {
    private final GpsDirectory gpsDir;

    public GeoLocationData(Metadata metadata) {
        this.gpsDir = metadata.getFirstDirectoryOfType(GpsDirectory.class);
    }

    /**
     * 判断GeoLocation是否为空
     *
     * @return GeoLocation完全为空
     */
    @Override
    public boolean isEmpty() {
        return isDirectoryEmpty(gpsDir);
    }

    /**
     * 获取经纬度坐标
     *
     * @return 经纬度坐标
     */
    public Optional<Pair<String, String>> getCoordinate() {
        return getBothOrNone(gpsDir,
                GpsDirectory.TAG_LATITUDE,
                GpsDirectory.TAG_LONGITUDE);
    }

    /**
     * 获取高度
     *
     * @return 高度
     */
    public Optional<String> getAltitude() {
        return get(gpsDir, GpsDirectory.TAG_ALTITUDE);
    }

    /**
     * 获取GPS时间戳
     *
     * @return GPS时间戳
     */
    public Optional<String> getGPSTimestamp() {
        return get(gpsDir, GpsDirectory.TAG_TIME_STAMP);
    }

    /**
     * 获取电量
     *
     * @return 电量
     */
    public Optional<String> getBattery() {
        return get(gpsDir, GpsDirectory.TAG_BATTERY_LEVEL);
    }

    @Override
    public String toString() {
        if (isEmpty()) return "<Empty>";

        StringBuilder sb = new StringBuilder();
        sb.append("{\n\t\tCoordinate: ").append(getCoordinate())
                .append(", \n\t\tAltitude: ").append(getAltitude())
                .append(", \n\t\tGPSTimestamp: ").append(getGPSTimestamp())
                .append(", \n\t\tBattery: ").append(getBattery())
                .append(" \n\t}");
        return sb.toString();
    }
}
