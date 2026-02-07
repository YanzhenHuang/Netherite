package org.huangyanzhen.netherite.service.model.metadata.directory.strategy;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.mp4.media.Mp4SoundDirectory;
import com.drew.metadata.mp4.media.Mp4UuidBoxDirectory;
import com.drew.metadata.mp4.media.Mp4VideoDirectory;
import javafx.util.Pair;
import org.huangyanzhen.netherite.service.model.metadata.directory.DirectoryData;
import org.huangyanzhen.netherite.util.log.obj2json.ObjectString;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public class Mp4Data extends DirectoryData {

    private final Mp4VideoDirectory videoDir;
    private final Mp4SoundDirectory soundDir;
    private final Mp4UuidBoxDirectory uuidBoxDir;

    public Mp4Data(Metadata metadata) {
        this.videoDir = metadata.getFirstDirectoryOfType(Mp4VideoDirectory.class);
        this.soundDir = metadata.getFirstDirectoryOfType(Mp4SoundDirectory.class);
        this.uuidBoxDir = metadata.getFirstDirectoryOfType(Mp4UuidBoxDirectory.class);
    }

    @Override
    public boolean isEmpty() {
        return isDirectoryEmpty(videoDir)
                || isDirectoryEmpty(soundDir)
                || isDirectoryEmpty(uuidBoxDir);
    }

    public Optional<LocalDateTime> getRecommendedMp4Time() {
        return getBestExist(Stream.of(
                        new Pair<Directory, Integer>(videoDir, Mp4VideoDirectory.TAG_CREATION_TIME),
                        new Pair<>(videoDir, Mp4VideoDirectory.TAG_MODIFICATION_TIME),
                        new Pair<>(soundDir, Mp4SoundDirectory.TAG_CREATION_TIME),
                        new Pair<>(soundDir, Mp4SoundDirectory.TAG_MODIFICATION_TIME),
                        new Pair<>(uuidBoxDir, Mp4UuidBoxDirectory.TAG_CREATION_TIME)
                ).toList()
        ).map(this::parseTime);
    }

    @Override
    public String toString() {
        ObjectString obs = new ObjectString(2);
        obs.put("video", videoDir)
                .put("sound", soundDir)
                .put("uuidBox", uuidBoxDir);
                return obs.toString();
    }

//
//    public void test() {
//        return Mp4VideoDirectory
//    }


}
