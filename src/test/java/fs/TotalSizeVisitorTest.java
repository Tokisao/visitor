package fs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class TotalSizeVisitorTest {
    @Test
    void sumsAcrossMultipleNestingLevels() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode a = new DirectoryNode("a");
        DirectoryNode b = new DirectoryNode("b");
        root.add(new FileNode("r.txt", 100));
        a.add(new FileNode("a.txt", 200));
        b.add(new FileNode("b.txt", 300));
        a.add(b);
        root.add(a);

        TotalSizeVisitor v = new TotalSizeVisitor();
        v.run(root);
        assertEquals(600, v.getTotalBytes());
    }

    @Test
    void repeatedRunsDoNotAccumulate() {
        DirectoryNode root = new DirectoryNode("root");
        root.add(new FileNode("x", 42));

        TotalSizeVisitor v = new TotalSizeVisitor();
        v.run(root);
        v.run(root);
        v.run(root);
        assertEquals(42, v.getTotalBytes());
    }
}