import java.awt.EventQueue;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.*;
import javax.swing.BorderFactory;


public class JButton {

    public JButton(String new_Frame) {
    }


    public static void createFrame() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final int width = 1570;
                final int height = 1000;

                String username = System.getProperty("user.name");
                JFrame frame = new JFrame();

                JPanel panel = new JPanel();
                for (int i = 0; i < 70; i++) {
                    String numerStrony = String.format("%02d", i + 1);
                    ImageIcon image = new ImageIcon(
                            "C:\\Users\\" + username + "\\Pictures\\55\\" + numerStrony + ".png");
                    JLabel label = new JLabel(image);
                    panel.add(label);
                    label.setBorder(BorderFactory.createMatteBorder(0, 0, 10, 0, Color.black));
                    label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                }
                panel.setBackground(Color.black);
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                JScrollPane scrollPane = new JScrollPane(panel);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setBounds(0, 0, width, height);
                JPanel contentPane = new JPanel(null);
                contentPane.setPreferredSize(new Dimension(width, height));
                contentPane.add(scrollPane);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(contentPane);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}