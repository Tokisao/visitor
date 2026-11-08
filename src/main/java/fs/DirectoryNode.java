package fs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class DirectoryNode implements Element {
    private final String name;
    private final Map<String, Element> children = new LinkedHashMap<>();

    public DirectoryNode(String name) {
        this.name = requireValidName(name);
    }

    @Override
    public String getName() { return name; }

    public void add(Element child) {
        Objects.requireNonNull(child, "child");
        String childName = child.getName();
        if (children.containsKey(childName)) {
            throw new IllegalArgumentException("Duplicate sibling name in '" + name + "': " + childName);
        }
        if (child == this) {
            throw new IllegalArgumentException("A directory cannot contain itself (cycle)");
        }
        children.put(childName, child);
    }

    public boolean remove(String childName) {
        return children.remove(childName) != null;
    }

    public List<Element> getChildren() {
        return Collections.unmodifiableList(new ArrayList<>(children.values()));
    }

    public int childCount() {
        return children.size();
    }

    @Override
    public void accept(Visitor visitor, String parentPath) {
        Objects.requireNonNull(visitor, "visitor");
        
        String myPath = childPath(parentPath, name);
        
        // Передаем визитору свой правильный путь
        visitor.visit(this, myPath);
        
        // Детям передаем свой путь как родительский
        for (Element child : getChildren()) {
            child.accept(visitor, myPath);
        }
    }

    public static String childPath(String parentPath, String name) {
        // Игнорируем имя корневой папки ("root"), чтобы пути были root-relative
        if (parentPath.isEmpty() && name.equals("root")) {
            return "";
        }
        return parentPath.isEmpty() ? name : parentPath + "/" + name;
    }

    private static String requireValidName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Directory name must be non-blank");
        }
        if (name.contains("/")) {
            throw new IllegalArgumentException("Directory name must not contain '/'");
        }
        return name;
    }
}