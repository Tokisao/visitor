package fs;

public interface Visitor {
    void visit(FileNode file, String parentPath);
    void visit(DirectoryNode directory, String parentPath);

    default void reset() {}
    
    default void run(Element root) {
        reset();
        root.accept(this, "");
    }
}