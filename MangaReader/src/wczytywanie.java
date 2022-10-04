
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class wczytywanie {

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

    public static void Pobiez(String numerRozdzialu) {


        BufferedImage image = null;

        boolean done = true;
        int i = 0;
        String username = System.getProperty("user.name");

        while (done) {
            try {
                String numerStrony = String.format("%02d", i + 1);
                String link = "https://scans-hot.leanbox.us/manga/Dandadan/00" + numerRozdzialu + "-0" + numerStrony + ".png";
                URL url = new URL(link);
                image = ImageIO.read(url);

                ImageIO.write(image, "png", new File("C:\\Users\\" + username + "\\Pictures\\55\\" + numerStrony + ".png"));
                i++;

            } catch (IOException e) {
                e.printStackTrace();
                done = false;
            }
        }

    }
}