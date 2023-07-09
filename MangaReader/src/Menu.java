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

public class Menu extends JFrame {
    // Initial chapter count for the manga
    private final int initialChapterCount = 0;
    // File name to store the progress
    private final String progressFileName = "chapter_progress.txt";
    // Start date of the manga
    private final LocalDate startDate = LocalDate.of(2021, 5, 4);
    private JComboBox<String> jComboBox1;

    public Menu() {
        initComponents();
        updateChapterComboBox();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Menu().setVisible(true));
    }

    private void initComponents() {
        JButton checkAvailabilityButton;
        JPanel jPanel1 = new JPanel();
        jComboBox1 = new JComboBox<>();
        JButton jButton1 = new JButton();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setBackground(new Color(255, 204, 204));

        // Generate chapter numbers and populate the combo box
        String[] chapters = generateChapterNumbers(initialChapterCount);
        jComboBox1.setModel(new DefaultComboBoxModel<>(chapters));
        jComboBox1.addActionListener(this::imageDownload);

        jButton1.setText("Read");
        jButton1.addActionListener(this::NewWindow);

        checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.addActionListener(this::addChapterIfTuesday);

        jLabel1.setFont(new Font("TJC 82 Marker", Font.PLAIN, 24));
        jLabel1.setText("Manga Reader Dandadan");

        jLabel2.setText("Choose chapter");

        jLabel3.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/title.png"))));
        jLabel3.setText("jLabel3");

        // Set up the layout using GroupLayout
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(29, 29, 29)
                                .addComponent(checkAvailabilityButton)
                                .addGap(29, 29, 29))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel2))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(73, 73, 73)
                                                .addComponent(jLabel1)))
                                .addContainerGap(76, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1)
                                        .addComponent(checkAvailabilityButton))
                                .addGap(43, 43, 43))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pack();
    }

    private void imageDownload(ActionEvent evt) {
        // Get the username of the current user
        String username = System.getProperty("user.name");
        // Get the selected chapter index from the combo box
        int chapterIndex = jComboBox1.getSelectedIndex();
        // Format the chapter number with leading zeros
        String chapterNumber = String.format("%02d", chapterIndex + 1);

        // Delete the existing folder and create a new one for image download
        Loading.deleteFolder(new File("C:\\Users\\" + username + "\\Pictures\\55"));
        new File("C:\\Users\\" + username + "\\Pictures\\55").mkdirs();

        // Call the download method from the Loading class
        Loading.download(chapterNumber);
    }

    private void NewWindow(ActionEvent evt) {
        // Open a new frame using the ConstructionWorkButton class
        ConstructionWorkButton.createFrame();
    }

    private void addChapterIfTuesday(ActionEvent evt) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        // Count the number of Tuesdays passed since the start date
        int tuesdaysPassed = countTuesdaysPassed(startDate, currentDate);
        // Calculate the current chapter count based on the initial count and Tuesdays passed
        int currentChapterCount = initialChapterCount + tuesdaysPassed;
        // Set the current chapter count and update the combo box
        setCurrentChapterCount(currentChapterCount);
        updateChapterComboBox();
        System.out.println("Chapter added. Current count of chapters: " + currentChapterCount);
    }

    private int countTuesdaysPassed(LocalDate startDate, LocalDate endDate) {
        // Count the number of weeks between start and end date, considering Tuesdays
        return (int) ChronoUnit.WEEKS.between(startDate, endDate.with(DayOfWeek.TUESDAY));
    }

    private void updateChapterComboBox() {
        // Generate chapter numbers based on the current chapter count and update the combo box
        String[] chapters = generateChapterNumbers(getCurrentChapterCount());
        jComboBox1.setModel(new DefaultComboBoxModel<>(chapters));
    }

    private int getCurrentChapterCount() {
        int currentChapterCount = initialChapterCount;
        try {
            // Read the progress file to get the current chapter count
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

    private void setCurrentChapterCount(int currentChapterCount) {
        try {
            // Write the current chapter count to the progress file
            Path filePath = Path.of(progressFileName);
            Files.writeString(filePath, String.valueOf(currentChapterCount), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] generateChapterNumbers(int chapterCount) {
        // Generate an array of chapter numbers as strings
        return IntStream.rangeClosed(1, chapterCount)
                .mapToObj(i -> String.format("%02d", i))
                .toArray(String[]::new);
    }
}
