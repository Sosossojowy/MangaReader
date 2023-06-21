import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConstructionWorkButton {

    public ConstructionWorkButton() {
    }


    public static void createFrame() {

        EventQueue.invokeLater(() -> {
            final int width = 1200;
            final int height = 1000;

            String username = System.getProperty("user.name");
            JFrame frame = new JFrame();

            JPanel panel = new JPanel();

            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBounds(0, 0, width, height);
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(width, height));
            contentPane.add(scrollPane);

            BufferedImage img = null;


            for (int i = 0; i < 70; i++) {
                String page = String.format("%02d", i + 1);
                try {
                    String path = "C:\\Users\\" + username + "\\Pictures\\55\\" + page + ".png";
                    img = ImageIO.read(new File(path));
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

                JLabel label = new JLabel();
                label.setSize(width, height);

                Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(),
                        Image.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(dimg);

                label.setIcon(image);

                panel.add(label);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
            }
            //todo
            /**
             * fix scroll panel
             * fix filling content
             * optimise loading
             * fix multiple windows
             * set size
             */

            panel.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent evt) {
                    Component c = (Component) evt.getSource();
                    Dimension newSize = c.getSize();
                    scrollPane.setSize(newSize.width,scrollPane.getHeight());
                    contentPane.setSize(newSize.width,scrollPane.getHeight());
                }
            });

            panel.setBackground(Color.black);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setContentPane(contentPane);
            frame.pack();
            frame.setVisible(true);
        });
    }
}