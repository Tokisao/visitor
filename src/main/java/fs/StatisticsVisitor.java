package fs;

public final class StatisticsVisitor implements Visitor {
    private long fileCount;
    private long directoryCount;
    private int maxDepth;

    @Override
    public void visit(FileNode file, String parentPath) {
        fileCount++;
        int depth = depthOf(parentPath) + 1; 
        maxDepth = Math.max(maxDepth, depth);
    }

    @Override
    public void visit(DirectoryNode directory, String parentPath) {
        directoryCount++;
        int depth = depthOf(parentPath);
        maxDepth = Math.max(maxDepth, depth);
    }

    @Override
    public void reset() {
        fileCount = 0L;
        directoryCount = 0L;
        maxDepth = 0;
    }

    public long getFileCount()      { return fileCount; }
    public long getDirectoryCount() { return directoryCount; }
    public int  getMaxDepth()       { return maxDepth; }

    private static int depthOf(String path) {
        if (path.isEmpty()) return 0;
        int slashes = 0;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '/') slashes++;
        }
        return slashes + 1;
    }
}