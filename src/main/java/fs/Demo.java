package fs;

public final class Demo {
    public static void main(String[] args) {
        DirectoryNode root = new DirectoryNode("root");

        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode images = new DirectoryNode("images");
        DirectoryNode nested = new DirectoryNode("nested");

        docs.add(new FileNode("report.pdf", 1_024));
        docs.add(new FileNode("notes.txt", 200));

        images.add(new FileNode("photo.png", 5_000));
        images.add(new FileNode("diagram.png", 800));

        nested.add(new FileNode("deep.txt", 50));

        root.add(docs);
        root.add(images);
        root.add(new FileNode("readme.md", 120));
        docs.add(nested);

        TotalSizeVisitor size = new TotalSizeVisitor();
        size.run(root);
        System.out.println("Total size: " + size.getTotalBytes() + " bytes");

        SearchVisitor search = new SearchVisitor(FileNameCriterion.containsIgnoreCase(".txt"), 40);
        search.run(root);
        System.out.println("Search (.txt, >40 bytes): " + search.getMatches());

        StatisticsVisitor stats = new StatisticsVisitor();
        stats.run(root);
        System.out.printf("Stats -> files=%d dirs=%d maxDepth=%d%n",
                stats.getFileCount(), stats.getDirectoryCount(), stats.getMaxDepth());
    }
}