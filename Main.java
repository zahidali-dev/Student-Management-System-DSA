


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends JFrame {
    private LinkedList studentList;
    private JTable displayTable;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, gradeField, attendanceField;
    private JScrollPane scrollPane;
    private JPanel windowButtonPanel;
    private JPanel tablePanel; // New panel to hold table and controls

    public Main() {
        studentList = new LinkedList();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Student Management System");
        setSize(1100, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);

        // === Background Panel ===
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image bg = new ImageIcon(getClass().getResource("/icon/background.png")).getImage();
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    g.setColor(new Color(25, 45, 85));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        setContentPane(backgroundPanel);

        // === Custom Top Bar ===
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel(" Student Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.YELLOW);
        topBar.add(titleLabel, BorderLayout.WEST);

        // Top right control buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        controlPanel.setOpaque(false);

        JButton minBtn = createIconButton("/icon/minimize.png", e -> setExtendedState(Frame.ICONIFIED));
        JButton maxBtn = createIconButton("/icon/maximize.png", e -> {
            if ((getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH)
                setExtendedState(Frame.NORMAL);
            else
                setExtendedState(Frame.MAXIMIZED_BOTH);
        });
        JButton closeBtn = createIconButton("/icon/exit.png", e -> System.exit(0));

        controlPanel.add(minBtn);
        controlPanel.add(maxBtn);
        controlPanel.add(closeBtn);

        topBar.add(controlPanel, BorderLayout.EAST);
        backgroundPanel.add(topBar, BorderLayout.NORTH);

        // === Left Input Panel ===
        JPanel inputPanel = new RoundedPanel(25, new Color(255, 255, 255, 40));
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.CYAN, 2),
                "Student Details", 0, 0, new Font("Segoe UI", Font.BOLD, 18), Color.WHITE));
        inputPanel.setPreferredSize(new Dimension(360, getHeight()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 14, 12, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(createLabel("ID:"), gbc);
        gbc.gridx = 1; inputPanel.add(idField = createField(), gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(createLabel("Name:"), gbc);
        gbc.gridx = 1; inputPanel.add(nameField = createField(), gbc);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(createLabel("Grade:"), gbc);
        gbc.gridx = 1; inputPanel.add(gradeField = createField(), gbc);
        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(createLabel("Attendance:"), gbc);
        gbc.gridx = 1; inputPanel.add(attendanceField = createField(), gbc);

        backgroundPanel.add(inputPanel, BorderLayout.WEST);

        // === Buttons Panel (with icons) ===
        JPanel buttonPanel = new RoundedPanel(25, new Color(0, 0, 0, 120));
        buttonPanel.setLayout(new GridLayout(6, 1, 15, 15));  // Updated to 6 rows for the new export button
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        java.util.function.Function<String, ImageIcon> loadIcon = (name) -> {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/icon/" + name));
                Image scaled = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } catch (Exception e) {
                return null;
            }
        };

        JButton addBtn = createModernButton("Add", new Color(76, 175, 80));
        addBtn.setIcon(loadIcon.apply("add.png"));
        addBtn.addActionListener(e -> addStudent());
        buttonPanel.add(addBtn);

        JButton updateBtn = createModernButton("Update", new Color(255, 193, 7));
        updateBtn.setIcon(loadIcon.apply("update.png"));
        updateBtn.addActionListener(e -> updateStudent());
        buttonPanel.add(updateBtn);

        JButton deleteBtn = createModernButton("Delete", new Color(244, 67, 54));
        deleteBtn.setIcon(loadIcon.apply("delete.png"));
        deleteBtn.addActionListener(e -> deleteStudent());
        buttonPanel.add(deleteBtn);

        JButton displayBtn = createModernButton("Display", new Color(33, 150, 243));
        displayBtn.setIcon(loadIcon.apply("display.png"));
        displayBtn.addActionListener(e -> displayStudents());
        buttonPanel.add(displayBtn);

        JButton exportBtn = createModernButton("Export CSV", new Color(76, 175, 80));  // New export button
        exportBtn.setIcon(loadIcon.apply("export.png"));  // Add an export icon if available
        exportBtn.addActionListener(e -> exportTableToCSV());
        buttonPanel.add(exportBtn);

        JButton exitBtn = createModernButton("Exit", new Color(158, 158, 158));
        exitBtn.setIcon(loadIcon.apply("exit.png"));
        exitBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitBtn);

        backgroundPanel.add(buttonPanel, BorderLayout.EAST);

        // === Table Panel with Search and Close ===
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setVisible(false); // Initially hidden

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setOpaque(false);
        JLabel searchLabel = new JLabel("Search by ID:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchLabel.setForeground(Color.WHITE);
        JTextField searchField = createField();
        searchField.setPreferredSize(new Dimension(150, 30));
        JButton searchBtn = createModernButton("Search", new Color(33, 150, 243));
        searchBtn.addActionListener(e -> searchStudent(searchField.getText().trim()));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        // Close Button in top right
        JButton closeTableBtn = createIconButton("/icon/close.png", e -> tablePanel.setVisible(false));
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setOpaque(false);
        topRightPanel.add(closeTableBtn);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        tablePanel.add(topPanel, BorderLayout.NORTH);

        // === Table ===
        String[] columns = {"ID", "Name", "Grade", "Attendance"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        displayTable = new JTable(tableModel);
        displayTable.setRowHeight(30);
        displayTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        displayTable.setForeground(Color.WHITE);
        displayTable.setBackground(new Color(20, 20, 20));
        displayTable.setGridColor(new Color(255, 255, 255, 60));
        displayTable.setSelectionBackground(new Color(30, 144, 255, 150));
        displayTable.setSelectionForeground(Color.WHITE);
        displayTable.setShowGrid(true);

        JTableHeader header = displayTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);

        scrollPane = new JScrollPane(displayTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.CYAN, 2),
                "Student Records", 0, 0, new Font("Segoe UI", Font.BOLD, 16), Color.CYAN));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        backgroundPanel.add(tablePanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // === Icon Button (for min, max, close)
    private JButton createIconButton(String path, java.awt.event.ActionListener action) {
        JButton btn = new JButton();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            btn.setText("?");
        }
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        return btn;
    }

    // === UI Helper ===
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private JTextField createField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(new Color(255, 255, 255, 220));
        field.setForeground(Color.BLACK);
        field.setCaretColor(Color.BLACK);
        field.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true));
        field.setPreferredSize(new Dimension(160, 32));
        return field;
    }

    private JButton createModernButton(String text, Color baseColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color color = getModel().isPressed() ? baseColor.darker()
                        : getModel().isRollover() ? baseColor.brighter() : baseColor;
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(10);
        return btn;
    }

    // === Functional Logic (unchanged except for display and search)
    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String grade = gradeField.getText().trim();
            String attendance = attendanceField.getText().trim();
            studentList.Add(new Student(id, name, grade, attendance));
            clearFields();
            JOptionPane.showMessageDialog(this, "Student added successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter numeric ID.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String grade = gradeField.getText().trim();
            String attendance = attendanceField.getText().trim();
            if (studentList.Update(id, name, grade, attendance)) {
                clearFields();
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.");
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            if (studentList.Delete(id)) {
                clearFields();
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.");
        }
    }

    private void displayStudents() {
        tableModel.setRowCount(0);
        for (Student s : studentList.GetAll()) {
            tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getGrade(), s.getAttendance()});
        }
        tablePanel.setVisible(true);
        revalidate();
    }

    private void searchStudent(String idText) {
        try {
            int id = Integer.parseInt(idText);
            Student s = studentList.Search(id);
            if (s != null) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getGrade(), s.getAttendance()});
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.");
        }
    }

    // === New Method: Export Table to CSV ===
    private void exportTableToCSV() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No data to export. Display students first.");
            return;
        }

        // Prompt user to choose save location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save CSV File");
        fileChooser.setSelectedFile(new File("students.csv"));  // Default filename
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                // Write CSV headers
                writer.write("ID,Name,Grade,Attendance\n");

                // Write each row from the table
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    writer.write(tableModel.getValueAt(i, 0) + "," +  // ID
                                 tableModel.getValueAt(i, 1) + "," +  // Name
                                 tableModel.getValueAt(i, 2) + "," +  // Grade
                                 tableModel.getValueAt(i, 3) + "\n"); // Attendance
                }

                JOptionPane.showMessageDialog(this, "Data exported successfully to " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error exporting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        gradeField.setText("");
        attendanceField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginDialog(null, () -> new Main()));
    }
}

// === Rounded Panel ===
class RoundedPanel extends JPanel {
    private final int cornerRadius;
    private final Color backgroundColor;

    public RoundedPanel(int cornerRadius, Color backgroundColor) {
        this.cornerRadius = cornerRadius;
        this.backgroundColor = backgroundColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
    }
}


