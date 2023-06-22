import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.IntStream;

public class Menu extends javax.swing.JFrame {
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JButton checkAvailabilityButton;
    private int initialChapterCount = 111;
    private String progressFileName = "chapter_progress.txt";

    public Menu() {
        initComponents();
        updateChapterComboBox();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
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

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        // Creating the combo box model with chapter numbers
        String[] chapters = IntStream.rangeClosed(1, initialChapterCount)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(chapters));

        // Adding action listener to the combo box for image download
        jComboBox1.addActionListener(this::imageDownload);

        jButton1.setText("Read");
        jButton1.addActionListener(this::NewWindow);

        checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.addActionListener(this::addChapterIfTuesday);

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
                                .addGap(29, 29, 29)
                                .addComponent(checkAvailabilityButton)
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
                                        .addComponent(jButton1)
                                        .addComponent(checkAvailabilityButton))
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

    private void imageDownload(ActionEvent evt) {
        String username = System.getProperty("user.name");
        int chapterIndex = jComboBox1.getSelectedIndex();
        String chapterNumber = String.format("%02d", chapterIndex + 1);

        // Delete existing folder and create a new one
        Loading.deleteFolder(new File("C:\\Users\\" + username + "\\Pictures\\55"));
        new File("C:\\Users\\" + username + "\\Pictures\\55").mkdirs();

        // Perform the image download process
        Loading.download(chapterNumber);
    }

    private void NewWindow(java.awt.event.ActionEvent evt) {
        // Open a new window when the "Read" button is clicked
        ConstructionWorkButton.createFrame();
    }

    private void addChapterIfTuesday(ActionEvent evt) {
        if (isTuesday()) {
            // Check if it is Tuesday
            int currentChapterCount = getCurrentChapterCount();
            currentChapterCount++;
            setCurrentChapterCount(currentChapterCount);
            updateChapterComboBox();
            System.out.println("Chapter added. Current chapter count: " + currentChapterCount);
        } else {
            System.out.println("It's not Tuesday. Chapter not added.");
        }
    }

    private boolean isTuesday() {
        // Check if the current day is Tuesday
        return LocalDate.now().getDayOfWeek() == DayOfWeek.TUESDAY;
    }

    private void updateChapterComboBox() {
        // Update the chapter combo box with the current chapter count
        int currentChapterCount = getCurrentChapterCount();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (int i = 1; i <= currentChapterCount; i++) {
            model.addElement("Chapter " + i);
        }
        jComboBox1.setModel(model);
    }

    private int getCurrentChapterCount() {
        // Get the current chapter count from the progress file
        int progress = initialChapterCount;
        try {
            Path progressFile = Path.of(progressFileName);
            if (Files.exists(progressFile)) {
                String content = Files.readString(progressFile);
                progress = Integer.parseInt(content.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return progress;
    }

    private void setCurrentChapterCount(int currentChapterCount) {
        // Set the current chapter count in the progress file
        try {
            String content = String.valueOf(currentChapterCount);
            Files.writeString(Path.of(progressFileName), content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
