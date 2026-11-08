package fs;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SearchVisitorTest {
    private DirectoryNode sampleTree() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode sub = new DirectoryNode("sub");
        docs.add(new FileNode("alpha.txt", 500));
        docs.add(new FileNode("beta.txt", 10));
        sub.add(new FileNode("gamma.txt", 900));
        sub.add(new FileNode("delta.log", 900));
        docs.add(sub);
        root.add(docs);
        root.add(new FileNode("top.txt", 100));
        return root;
    }

    @Test
    void combinesNameAndSizeFilters() {
        SearchVisitor v = new SearchVisitor(FileNameCriterion.containsIgnoreCase(".txt"), 100);
        v.run(sampleTree());
        assertEquals(List.of("docs/alpha.txt", "docs/sub/gamma.txt"), v.getMatches());
    }

    @Test
    void producesSortedRootRelativePathsWithSlashSeparator() {
        SearchVisitor v = new SearchVisitor(FileNameCriterion.any(), 0);
        v.run(sampleTree());
        List<String> m = v.getMatches();
        assertEquals(List.of(
                "docs/alpha.txt", "docs/beta.txt", "docs/sub/delta.log",
                "docs/sub/gamma.txt", "top.txt"), m);
    }
}