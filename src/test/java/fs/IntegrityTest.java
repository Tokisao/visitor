package fs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class IntegrityTest {
    @Test
    void rejectsNegativeFileSize() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode("bad", -1));
    }

    @Test
    void rejectsDuplicateSiblingNames() {
        DirectoryNode d = new DirectoryNode("d");
        d.add(new FileNode("same", 1));
        assertThrows(IllegalArgumentException.class, () -> d.add(new FileNode("same", 2)));
    }

    @Test
    void rejectsSelfContainmentCycle() {
        DirectoryNode d = new DirectoryNode("d");
        assertThrows(IllegalArgumentException.class, () -> d.add(d));
    }

    @Test
    void validNestingAndChildCollectionIsUnmodifiable() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode child = new DirectoryNode("child");
        root.add(child);
        child.add(new FileNode("f", 1));

        assertThrows(UnsupportedOperationException.class, () -> root.getChildren().add(new FileNode("x", 1)));
        assertEquals(1, root.childCount());
    }
}