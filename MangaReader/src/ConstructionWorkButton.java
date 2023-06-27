import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConstructionWorkButton {

    public ConstructionWorkButton() {
        // Constructor
    }

    public static void createFrame() {
        EventQueue.invokeLater(() -> {
            final int width = 784;
            final int height = 1145;
            String username = System.getProperty("user.name");

            // Create the main frame
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            frame.getContentPane().setBackground(Color.BLACK);

            // Create a panel to hold the images
            JPanel panel = new JPanel();
            panel.setBackground(Color.BLACK);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Create a scroll pane for vertical scrolling
            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.getVerticalScrollBar().setUnitIncrement(80);

            BufferedImage img = null;
            // Load and display images
            for (int i = 0; i < 70; i++) {
                String page = String.format("%02d", i + 1);
                try {
                    String path = "C:\\Users\\" + username + "\\Pictures\\55\\" + page + ".png";
                    img = ImageIO.read(new File(path));
                    Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    ImageIcon image = new ImageIcon(scaledImg);
                    JLabel label = new JLabel(image);
                    label.setAlignmentX(Component.CENTER_ALIGNMENT);
                    panel.add(label);

                    if (i < 69) {
                        panel.add(Box.createRigidArea(new Dimension(0, 20)));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }

            // Add a mouse wheel listener for scrolling
            panel.addMouseWheelListener(e -> {
                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                int notches = e.getWheelRotation();
                int scrollAmount = verticalScrollBar.getUnitIncrement() * notches;
                verticalScrollBar.setValue(verticalScrollBar.getValue() + scrollAmount);
            });

            panel.setOpaque(true);
            panel.setBackground(Color.BLACK);

            // Set the layout and add the scroll pane to the frame
            frame.setLayout(new BorderLayout());
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        createFrame();
    }
}
