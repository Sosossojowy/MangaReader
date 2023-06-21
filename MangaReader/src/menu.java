import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;
import java.util.stream.IntStream;


public class menu extends javax.swing.JFrame {


    private javax.swing.JComboBox<String> jComboBox1;


    public menu() {
        initComponents();
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> new menu().setVisible(true));
    }

    private void initComponents() {
        javax.swing.JPanel jPanel1;
        javax.swing.JLabel jLabel3;
        javax.swing.JLabel jLabel2;
        javax.swing.JLabel jLabel1;
        javax.swing.JButton jButton1;

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowsActivated();
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        String[] chapters = IntStream.rangeClosed(1, 110)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(chapters));

        jComboBox1.addActionListener(this::imageDownload);

        jButton1.setText("Read");
        jButton1.addActionListener(this::NewWindow);

        jLabel1.setFont(new java.awt.Font("TJC 82 Marker", Font.PLAIN, 24));
        jLabel1.setText("Manga Reader Dandadan");

        jLabel2.setText("Choose chapter");

        jLabel3.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/title.png"))));
        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(29, 29, 29))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel2))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(73, 73, 73)
                                                .addComponent(jLabel1)))
                                .addContainerGap(76, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1))
                                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pack();
    }

    private void imageDownload(java.awt.event.ActionEvent evt) {
        String username = System.getProperty("user.name");
        int chapterIndex = jComboBox1.getSelectedIndex();
        String chapterNumber = String.format("%02d", chapterIndex + 1);


        loading.deleteFolder(new File("C:\\Users\\" + username + "\\Pictures\\55"));
        new File("C:\\Users\\" + username + "\\Pictures\\55").mkdirs();

        loading.Download(chapterNumber);
    }

    private void NewWindow(java.awt.event.ActionEvent evt) {
        ConstructionWorkButton jbutton1 = new ConstructionWorkButton();

        ConstructionWorkButton.createFrame();

    }
    private void formWindowsActivated() {
        ImageIcon icon = new ImageIcon("src/mr.png");
        setIconImage(icon.getImage());
    }
}
