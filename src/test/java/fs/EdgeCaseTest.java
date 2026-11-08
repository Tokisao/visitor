package fs;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class EdgeCaseTest {
    @Test
    void emptyRootHasZeroSizeAndNoMatches() {
        DirectoryNode root = new DirectoryNode("root");
        TotalSizeVisitor size = new TotalSizeVisitor();
        size.run(root);
        assertEquals(0, size.getTotalBytes());

        SearchVisitor search = new SearchVisitor(FileNameCriterion.any(), 0);
        search.run(root);
        assertEquals(List.of(), search.getMatches());
    }

    @Test
    void noMatchSearchReturnsEmptyList() {
        DirectoryNode root = new DirectoryNode("root");
        root.add(new FileNode("a.txt", 5));
        SearchVisitor search = new SearchVisitor(FileNameCriterion.exact("missing.bin"), 0);
        search.run(root);
        assertTrue(search.getMatches().isEmpty());
    }

    @Test
    void deeplyNestedAndEmptyDirectoriesAreTraversed() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode a = new DirectoryNode("a");
        DirectoryNode empty = new DirectoryNode("empty");
        a.add(empty);
        root.add(a);
        root.add(new FileNode("f", 7));

        StatisticsVisitor stats = new StatisticsVisitor();
        stats.run(root);
        assertEquals(1, stats.getFileCount());
        assertEquals(3, stats.getDirectoryCount());
        assertEquals(2, stats.getMaxDepth());
    }
}