package org.huangyanzhen.netherite;

import junit.framework.TestCase;
import org.huangyanzhen.netherite.service.file.FileOperator;

import java.io.File;

public class FileOperatorTest extends TestCase {

    public void testReadAllFiles() {
        File testDir = new File("D:\\Storage\\Test\\PhotoCollections");
        FileOperator.readAllFiles(testDir);
    }

}
