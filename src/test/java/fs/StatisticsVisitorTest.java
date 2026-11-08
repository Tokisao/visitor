package fs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class StatisticsVisitorTest {
    @Test
    void countsFilesDirectoriesAndMaxDepth() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode a = new DirectoryNode("a");
        DirectoryNode b = new DirectoryNode("b");
        root.add(new FileNode("f1", 1));
        a.add(new FileNode("f2", 1));
        b.add(new FileNode("f3", 1));
        a.add(b);
        root.add(a);

        StatisticsVisitor v = new StatisticsVisitor();
        v.run(root);

        assertEquals(3, v.getFileCount());
        assertEquals(3, v.getDirectoryCount());
        assertEquals(3, v.getMaxDepth());
    }

    @Test
    void emptyRootDirectoryIsCountedWithDepthZero() {
        StatisticsVisitor v = new StatisticsVisitor();
        v.run(new DirectoryNode("only"));
        assertEquals(0, v.getFileCount());
        assertEquals(1, v.getDirectoryCount());
        assertEquals(0, v.getMaxDepth());
    }
}