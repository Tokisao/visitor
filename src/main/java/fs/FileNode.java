package fs;

import java.util.Objects;

public final class FileNode implements Element {
    private final String name;
    private final long sizeInBytes;

    public FileNode(String name, long sizeInBytes) {
        this.name = requireValidName(name);
        if (sizeInBytes < 0) {
            throw new IllegalArgumentException("File size must be non-negative, got " + sizeInBytes);
        }
        this.sizeInBytes = sizeInBytes;
    }

    @Override
    public String getName() { return name; }

    public long getSizeInBytes() { return sizeInBytes; }

    @Override
    public void accept(Visitor visitor, String parentPath) {
        Objects.requireNonNull(visitor, "visitor");
        visitor.visit(this, parentPath);
    }

    private static String requireValidName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("File name must be non-blank");
        }
        if (name.contains("/")) {
            throw new IllegalArgumentException("File name must not contain '/'");
        }
        return name;
    }
}