package fs;

public interface Element {
    String getName();
    void accept(Visitor visitor, String parentPath);
}