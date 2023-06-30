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
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.stream.IntStream;

public class Menu extends javax.swing.JFrame {
    private javax.swing.JComboBox<String> jComboBox1; // Combo box for selecting chapters
    private javax.swing.JButton checkAvailabilityButton; // Button for checking chapter availability
    private int initialChapterCount = 0; // Initial number of chapters
    private String progressFileName = "chapter_progress.txt"; // File name for storing chapter progress
    private LocalDate startDate = LocalDate.of(2021, 5, 4); // Start date for counting Tuesdays

    public Menu() {
        initComponents(); // Initialize the GUI components
        updateChapterComboBox(); // Update the chapter selection combo box
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true)); // Create and show the menu GUI
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

        jPanel1.setBackground(new java.awt.Color(255, 204, 204)); // Set the background color of the panel

        // Generate chapter numbers as strings and populate the combo box
        String[] chapters = generateChapterNumbers(initialChapterCount);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(chapters)); // Set the chapter combo box model

        jComboBox1.addActionListener(this::imageDownload); // Add action listener for chapter selection

        jButton1.setText("Read"); // Set the text for the "Read" button
        jButton1.addActionListener(this::NewWindow); // Add action listener for opening a new window

        checkAvailabilityButton = new JButton("Check Availability"); // Create a button for checking chapter availability
        checkAvailabilityButton.addActionListener(this::addChapterIfTuesday); // Add action listener for checking availability on Tuesday

        jLabel1.setFont(new java.awt.Font("TJC 82 Marker", Font.PLAIN, 24)); // Set the font for jLabel1
        jLabel1.setText("Manga Reader Dandadan"); // Set the text for jLabel1

        jLabel2.setText("Choose chapter"); // Set the text for jLabel2

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

        Loading.deleteFolder(new File("C:\\Users\\" + username + "\\Pictures\\55")); // Delete the existing folder
        new File("C:\\Users\\" + username + "\\Pictures\\55").mkdirs(); // Create a new folder

        Loading.download(chapterNumber); // Download the chapter images
    }

    private void NewWindow(java.awt.event.ActionEvent evt) {
        ConstructionWorkButton.createFrame(); // Open a new window
    }

    private void addChapterIfTuesday(ActionEvent evt) {
        LocalDate currentDate = LocalDate.now();
        int tuesdaysPassed = countTuesdaysPassed(startDate, currentDate);
        int currentChapterCount = initialChapterCount + tuesdaysPassed; // Calculate the current chapter count
        setCurrentChapterCount(currentChapterCount); // Update the current chapter count
        updateChapterComboBox(); // Update the chapter selection combo box
        System.out.println("Chapter added. Current count of chapters: " + currentChapterCount);
        // Print the message with the updated chapter count
    }

    private int countTuesdaysPassed(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.WEEKS.between(startDate, endDate.with(DayOfWeek.TUESDAY));
        // Count the number of Tuesdays between the start and end date
    }

    private void setCurrentChapterCount(int currentChapterCount) {
        try {
            Path filePath = Path.of(progressFileName);
            Files.writeString(filePath, String.valueOf(currentChapterCount), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateChapterComboBox() {
        String[] chapters = generateChapterNumbers(getCurrentChapterCount());
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(chapters));
    }

    private int getCurrentChapterCount() {
        int currentChapterCount = initialChapterCount;
        try {
            Path filePath = Path.of(progressFileName);
            if (Files.exists(filePath)) {
                String content = Files.readString(filePath);
                currentChapterCount = Integer.parseInt(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentChapterCount;
    }

    private String[] generateChapterNumbers(int chapterCount) {
        return IntStream.rangeClosed(1, chapterCount)
                .mapToObj(i -> String.format("%02d", i))
                .toArray(String[]::new);
    }
}
