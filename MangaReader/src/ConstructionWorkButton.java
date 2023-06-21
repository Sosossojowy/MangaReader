import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConstructionWorkButton {

    public ConstructionWorkButton() {
    }

    public static void createFrame() {
        EventQueue.invokeLater(() -> {
            final int width = 784;
            final int height = 1145;
            String username = System.getProperty("user.name");

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set fullscreen mode
            frame.getContentPane().setBackground(Color.BLACK);

            JPanel panel = new JPanel();
            panel.setBackground(Color.BLACK);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.getVerticalScrollBar().setUnitIncrement(80); // Increase mouse wheel scroll by 80 pixels

            BufferedImage img = null;
            for (int i = 0; i < 70; i++) {
                String page = String.format("%02d", i + 1);
                try {
                    String path = "C:\\Users\\" + username + "\\Pictures\\55\\" + page + ".png";
                    img = ImageIO.read(new File(path));
                    Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    ImageIcon image = new ImageIcon(scaledImg);
                    JLabel label = new JLabel(image);
                    label.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label content
                    panel.add(label);

                    // Add empty panel as a separator
                    if (i < 69) {
                        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Gap between images (20 pixels)
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }

            // Add mouse wheel listener
            panel.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                    int notches = e.getWheelRotation();
                    int scrollAmount = verticalScrollBar.getUnitIncrement() * notches;
                    verticalScrollBar.setValue(verticalScrollBar.getValue() + scrollAmount);
                }
            });

            // Set black background for the panel
            panel.setOpaque(true);
            panel.setBackground(Color.BLACK);

            frame.setLayout(new BorderLayout());
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        createFrame();
    }
}
