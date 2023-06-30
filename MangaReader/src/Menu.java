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
    private JComboBox<String> jComboBox1;

    private final int initialChapterCount = 0;
    private final String progressFileName = "chapter_progress.txt";
    private final LocalDate startDate = LocalDate.of(2021, 5, 4);

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
        String username = System.getProperty("user.name");
        int chapterIndex = jComboBox1.getSelectedIndex();
        String chapterNumber = String.format("%02d", chapterIndex + 1);

        Loading.deleteFolder(new File("C:\\Users\\" + username + "\\Pictures\\55"));
        new File("C:\\Users\\" + username + "\\Pictures\\55").mkdirs();

        Loading.download(chapterNumber);
    }

    private void NewWindow(ActionEvent evt) {
        ConstructionWorkButton.createFrame();
    }

    private void addChapterIfTuesday(ActionEvent evt) {
        LocalDate currentDate = LocalDate.now();
        int tuesdaysPassed = countTuesdaysPassed(startDate, currentDate);
        int currentChapterCount = initialChapterCount + tuesdaysPassed;
        setCurrentChapterCount(currentChapterCount);
        updateChapterComboBox();
        System.out.println("Chapter added. Current count of chapters: " + currentChapterCount);
    }

    private int countTuesdaysPassed(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.WEEKS.between(startDate, endDate.with(DayOfWeek.TUESDAY));
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
        jComboBox1.setModel(new DefaultComboBoxModel<>(chapters));
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
