import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Loading {
    private static final int MAX_THREADS = 10;
    private static final String SAVE_DIRECTORY = "C:\\Users\\{username}\\Pictures\\55\\";

    // Delete a folder and its contents recursively
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    // Download images for a given chapter number
    public static void download(String chapterNumber) {
        try {
            int chapter = Integer.parseInt(chapterNumber);
            int i = 0;
            String username = System.getProperty("user.name");

            ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

            while (i < 70) {
                // Format chapter and page numbers with leading zeros
                String.format(chapterNumber, "%02d");
                String pageNumber = String.format("%02d", i + 1);
                String link;

                // Determine the image link based on chapter and page numbers
                if (chapter < 100 & chapter <= 23) {
                    link = "https://official.lowee.us/manga/Dandadan/00" + chapterNumber + "-0" + pageNumber + ".png";
                } else if (chapter < 100) {
                    link = "https://scans-hot.leanbox.us/manga/Dandadan/00" + chapterNumber + "-0" + pageNumber + ".png";
                } else {
                    link = "https://scans-hot.leanbox.us/manga/Dandadan/0" + chapterNumber + "-0" + pageNumber + ".png";
                }

                // Asynchronously download and save the image
                CompletableFuture.runAsync(() -> {
                    try {
                        URL url = new URL(link);
                        BufferedImage image = ImageIO.read(url);
                        ImageIO.write(image, "png", new File(SAVE_DIRECTORY.replace("{username}", username) + pageNumber + ".png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, executor);

                i++;
            }

            executor.shutdown();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}
