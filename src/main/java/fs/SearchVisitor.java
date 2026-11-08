package fs;

import java.util.ArrayList;
import java.util.List;

public final class SearchVisitor implements Visitor {
    private final FileNameCriterion criterion;
    private final long minSizeExclusive;
    private final List<String> matches = new ArrayList<>();

    public SearchVisitor(FileNameCriterion criterion, long minSizeExclusive) {
        if (criterion == null) {
            throw new IllegalArgumentException("criterion must not be null");
        }
        this.criterion = criterion;
        this.minSizeExclusive = minSizeExclusive;
    }

    @Override
    public void visit(FileNode file, String parentPath) {
        if (file.getSizeInBytes() > minSizeExclusive && criterion.matches(file.getName())) {
            matches.add(DirectoryNode.childPath(parentPath, file.getName()));
        }
    }

    @Override
    public void visit(DirectoryNode directory, String parentPath) {}

    @Override
    public void reset() {
        matches.clear();
    }

    public List<String> getMatches() {
        List<String> copy = new ArrayList<>(matches);
        copy.sort(String::compareTo);
        return copy;
    }
}