import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class loading {

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


    public static void Download(String chapterNumber) {

        BufferedImage image;

        try {
            int chapter = Integer.parseInt(chapterNumber);
            boolean done = true;
            int i = 0;
            String username = System.getProperty("user.name");

            while (done) {
                try {
                    String.format(chapterNumber, "%02d");
                    String pageNumber = String.format("%02d", i + 1);
                    if (chapter < 100 & chapter <= 23) {
                        String link = "https://official.lowee.us/manga/Dandadan/00" + chapterNumber + "-0" + pageNumber + ".png";

                        URL url = new URL(link);
                        image = ImageIO.read(url);

                        ImageIO.write(image, "png", new File("C:\\Users\\" + username + "\\Pictures\\55\\" + pageNumber + ".png"));
                        i++;

                    } else if (chapter < 100) {
                        String link = "https://scans-hot.leanbox.us/manga/Dandadan/00" + chapterNumber + "-0" + pageNumber + ".png";

                        URL url = new URL(link);
                        image = ImageIO.read(url);

                        ImageIO.write(image, "png", new File("C:\\Users\\" + username + "\\Pictures\\55\\" + pageNumber + ".png"));
                        i++;
                    } else {
                        String link = "https://scans-hot.leanbox.us/manga/Dandadan/0" + chapterNumber + "-0" + pageNumber + ".png";

                        URL url = new URL(link);
                        image = ImageIO.read(url);

                        ImageIO.write(image, "png", new File("C:\\Users\\" + username + "\\Pictures\\55\\" + pageNumber + ".png"));
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    done = false;
                }
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}
