package fs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DispatchTest {
    static final class RecordingVisitor implements Visitor {
        int fileHits, dirHits;
        @Override public void visit(FileNode file, String parentPath) { fileHits++; }
        @Override public void visit(DirectoryNode directory, String parentPath) { dirHits++; }
        @Override public void reset() { fileHits = 0; dirHits = 0; }
    }

    @Test
    void fileNodeDispatchesToFileOverload() {
        RecordingVisitor v = new RecordingVisitor();
        new FileNode("a.txt", 10).accept(v, "");
        assertEquals(1, v.fileHits);
        assertEquals(0, v.dirHits);
    }

    @Test
    void directoryNodeDispatchesToDirectoryOverload() {
        RecordingVisitor v = new RecordingVisitor();
        new DirectoryNode("d").accept(v, "");
        assertEquals(0, v.fileHits);
        assertEquals(1, v.dirHits);
    }
}