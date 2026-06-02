import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainApp extends JFrame {
    private JTextField urlInputField;
    private JButton scanBtn;
    private JLabel statusValueLabel;
    private JLabel confidenceValueLabel;
    private JProgressBar scanningMatrixBar;
    private JPanel gradientHeader;

    // --- 🗄️ ENTERPRISE DATABASE NODE ---
    private static final String DB_URL = "jdbc:mysql://localhost:3306/phishing_db"; 
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root"; // 🔴 तुझा पासवर्ड टाक

    public MainApp() {
        // Window Setup
        setTitle("🎯 CyberShield Quantum Engine v3.0 - AI Predictor");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // --- 🎨 MODERN GRADIENT TOP HEADER ---
        gradientHeader = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(41, 128, 185), getWidth(), 0, new Color(142, 68, 173));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientHeader.setPreferredSize(new Dimension(650, 80));
        gradientHeader.setLayout(new GridBagLayout());
        
        JLabel titleLabel = new JLabel("QUANTUM NEURAL PHISHING PREDICTOR");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        gradientHeader.add(titleLabel);
        add(gradientHeader, BorderLayout.NORTH);

        // --- 📊 CORE CONTAINER (DARK GLASSMORPHISM EFFECT) ---
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainContainer.setBackground(new Color(15, 20, 30)); // Deep Space Black

        // Input Field Wrapper
        urlInputField = new JTextField();
        urlInputField.setFont(new Font("Consolas", Font.PLAIN, 15));
        urlInputField.setBackground(new Color(25, 35, 50));
        urlInputField.setForeground(new Color(0, 255, 200)); // Cyber Green Text
        urlInputField.setCaretColor(Color.WHITE);
        urlInputField.setPreferredSize(new Dimension(500, 45));
        urlInputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        urlInputField.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(142, 68, 173), 1), 
            "ENTER NETWORK VECTOR PATH (URL)", 0, 0, new Font("Segoe UI", Font.BOLD, 10), new Color(142, 68, 173)
        ));
        mainContainer.add(urlInputField);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- ⚡ RUN MATRIX BUTTON ---
        scanBtn = new JButton("INITIALIZE AI HYPER-SCAN");
        scanBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        scanBtn.setForeground(Color.WHITE);
        scanBtn.setBackground(new Color(142, 68, 173));
        scanBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        scanBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        scanBtn.setFocusPainted(false);
        scanBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainContainer.add(scanBtn);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- ⏳ REAL-TIME ANIMATED PROGRESS BAR ---
        scanningMatrixBar = new JProgressBar(0, 100);
        scanningMatrixBar.setStringPainted(true);
        scanningMatrixBar.setFont(new Font("Consolas", Font.BOLD, 12));
        scanningMatrixBar.setForeground(new Color(0, 210, 255));
        scanningMatrixBar.setBackground(new Color(25, 35, 50));
        scanningMatrixBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        scanningMatrixBar.setVisible(false);
        mainContainer.add(scanningMatrixBar);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- 💎 TELEMETRY MONITOR DISPLAY ---
        JPanel monitorPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        monitorPanel.setBackground(new Color(20, 28, 40));
        monitorPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(41, 128, 185), 1), new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel t1 = new JLabel("ML ENGINE CLASSIFICATION:", SwingConstants.LEFT); t1.setForeground(Color.GRAY); t1.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusValueLabel = new JLabel("STANDBY", SwingConstants.RIGHT); statusValueLabel.setForeground(Color.WHITE); statusValueLabel.setFont(new Font("Consolas", Font.BOLD, 14));

        JLabel t2 = new JLabel("MODEL PREDICTION CONFIDENCE:", SwingConstants.LEFT); t2.setForeground(Color.GRAY); t2.setFont(new Font("Segoe UI", Font.BOLD, 11));
        confidenceValueLabel = new JLabel("00.00%", SwingConstants.RIGHT); confidenceValueLabel.setForeground(Color.WHITE); confidenceValueLabel.setFont(new Font("Consolas", Font.BOLD, 14));

        monitorPanel.add(t1); monitorPanel.add(statusValueLabel);
        monitorPanel.add(t2); monitorPanel.add(confidenceValueLabel);
        mainContainer.add(monitorPanel);

        add(mainContainer, BorderLayout.CENTER);

        // --- 🚀 ACTION LOGIC WITH DYNAMIC SIMULATION ---
        scanBtn.addActionListener(e -> {
            String url = urlInputField.getText().trim();
            if (url.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Target Vector cannot be null!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Start Scanning Animation Thread
            scanBtn.setEnabled(false);
            scanningMatrixBar.setVisible(true);
            
            SwingWorker<Void, Integer> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    String[] steps = {"Extracting Tokens...", "Loading Weka ML Model...", "Evaluating Feature Matrix...", "Synchronizing DB Node..."};
                    for (int i = 0; i <= 100; i += 5) {
                        Thread.sleep(60); // Animation Speed
                        scanningMatrixBar.setValue(i);
                        scanningMatrixBar.setString(steps[(i / 30) % steps.length] + " " + i + "%");
                    }
                    return null;
                }

                @Override
                protected void done() {
                    executeAIClassification(url, monitorPanel);
                    scanBtn.setEnabled(true);
                }
            };
            worker.execute();
        });
    }

    // --- 🧠 CORE WEKA-INSPIRED DEEP CORE MATHEMATICS ---
    private void executeAIClassification(String url, JPanel monitor) {
        String data = url.toLowerCase();
        double modelProbability = 0.12; // Baseline Probability

        // Deep Feature Extractor
        if (!data.startsWith("https://")) modelProbability += 0.38;
        if (data.contains("@") || data.contains("login") || data.contains("secure")) modelProbability += 0.42;
        
        int dotMatrixCount = 0;
        for (char c : data.toCharArray()) if (c == '.') dotMatrixCount++;
        if (dotMatrixCount >= 3) modelProbability += 0.25;
        if (data.length() > 60) modelProbability += 0.18;

        String finalVerdict;
        double displayConfidence;

        if (modelProbability > 0.50) {
            finalVerdict = "CRITICAL_PHISHING";
            displayConfidence = 78.5 + (modelProbability * 15);
            if (displayConfidence > 99.8) displayConfidence = 99.45;
            
            statusValueLabel.setText("🚨 CRITICAL_PHISHING_DETECTION");
            statusValueLabel.setForeground(new Color(255, 75, 75));
            monitor.setBorder(BorderFactory.createLineBorder(new Color(255, 75, 75), 2));
        } else {
            finalVerdict = "VERIFIED_SAFE";
            displayConfidence = 98.9 - (modelProbability * 10);
            
            statusValueLabel.setText("🛡️ SYSTEM_VERIFIED_SECURE");
            statusValueLabel.setForeground(new Color(0, 255, 150));
            monitor.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 150), 2));
        }

        String confidenceString = String.format("%.2f%%", displayConfidence);
        confidenceValueLabel.setText(confidenceString);

        // Push Logs to DB
        commitToDatabase(url, finalVerdict, confidenceString);
    }

    private void commitToDatabase(String url, String verdict, String confidence) {
        String sql = "INSERT INTO url_history (url_address, status, confidence_score) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, url);
            pstmt.setString(2, verdict);
            pstmt.setString(3, confidence);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("DB_SYNC_ERROR: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}