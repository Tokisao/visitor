package fs;

public final class TotalSizeVisitor implements Visitor {
    private long totalBytes;

    @Override
    public void visit(FileNode file, String parentPath) {
        totalBytes += file.getSizeInBytes();
    }

    @Override
    public void visit(DirectoryNode directory, String parentPath) {}

    @Override
    public void reset() {
        totalBytes = 0L;
    }

    public long getTotalBytes() { return totalBytes; }
}