package org.huangyanzhen.netherite.service.model.metadata.directory;

import com.drew.metadata.Directory;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DirectoryData {
    protected DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");

    /**
     * 检查整个字段是否为空
     *
     * @return 字段是否为空
     */
    public abstract boolean isEmpty();

    /**
     * 解析时间字符串
     *
     * @param timeStr 时间字符串
     * @return LocalDateTime对象
     */
    protected LocalDateTime parseTime(String timeStr) {
        return LocalDateTime.parse(timeStr, FORMATTER);
    }

    /**
     * 检查字段目录是否为空
     *
     * @param d 目录
     * @return 字段是否为空
     */
    public boolean isDirectoryEmpty(Directory d) {
        return d == null || d.isEmpty();
    }

    /**
     * 检查tagType是否存在于d中
     *
     * @param d       目录对象
     * @param tagType 字段
     * @return 字段是否在目录中
     */
    protected boolean check(Directory d, int tagType) {
        return d != null && d.containsTag(tagType);
    }

    /**
     * 获取tagType的字段
     *
     * @param d       目录对象
     * @param tagType 字段
     * @return 字段值
     */
    protected Optional<String> get(Directory d, int tagType) {
        if (d == null) return Optional.empty();
        return Optional.ofNullable(d.getString(tagType));
    }

    /**
     * 获取tagType1和tagType2的字段，
     * 当且仅当二者都存在时才返回有效值。
     *
     * @param d        目录对象
     * @param tagType1 字段1
     * @param tagType2 字段2
     * @return 字段值
     */
    protected Optional<Pair<String, String>> getBothOrNone(
            Directory d, int tagType1, int tagType2) {
        if (d == null || !d.containsTag(tagType1) || !d.containsTag(tagType2))
            return Optional.empty();
        return Optional.of(new Pair<>(
                d.getString(tagType1),
                d.getString(tagType2)
        ));
    }

    /**
     * 获取优先顺序队列中第一个存在tagType的字段
     *
     * @param items 优先顺序队列
     * @return 字段值
     */
    protected Optional<String> getBestExist(
            List<Pair<? extends Directory, Integer>> items) {
        if (items == null || items.isEmpty()) return Optional.empty();

        return items.stream()
                .filter(item -> check(item.getKey(), item.getValue()))
                .findFirst().flatMap(item -> get(item.getKey(), item.getValue()));
    }
}
