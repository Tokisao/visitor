package fs;

@FunctionalInterface
public interface FileNameCriterion {
    boolean matches(String fileName);

    static FileNameCriterion exact(String expected) {
        return name -> name.equals(expected);
    }

    static FileNameCriterion containsIgnoreCase(String fragment) {
        return name -> name.toLowerCase().contains(fragment.toLowerCase());
    }

    static FileNameCriterion any() {
        return name -> true;
    }
}