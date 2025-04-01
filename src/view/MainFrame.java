package view;

import controller.GameController;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    GamePanel gamePanel;
    GameModel model;
    private NextPiecePanel nextPiecePanel;
    public ScorePanel scorePanel;
    public HighScoreTable highScoreTable;

    public MainFrame(GameModel model, GameController controller) {
        this.model = model;
        initMenu();
        initGameComponents(model, controller);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                gamePanel.requestFocusInWindow();
            }
        });
        setupWindow();
    }

    private void initGameComponents(GameModel model, GameController controller) {
        JPanel gameContainer = new JPanel(new BorderLayout(10, 10));

        gamePanel = new GamePanel(model, controller);
        gameContainer.add(gamePanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        scorePanel = new ScorePanel(model);
        nextPiecePanel = new NextPiecePanel(model);
        highScoreTable = new HighScoreTable(model);

        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(scorePanel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(nextPiecePanel);
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(highScoreTable);
        gameContainer.add(rightPanel, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(gameContainer, BorderLayout.CENTER);
    }

    private void setupWindow() {
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        styleMenuBar(menuBar);

        JMenu gameMenu = createStyledMenu("Game", Color.ORANGE);
        addMenuItem(gameMenu, "New Game", KeyEvent.VK_N, "icons/new_game.png", e -> gamePanel.restartGame());
        gameMenu.addSeparator();
        addMenuItem(gameMenu, "Exit", KeyEvent.VK_Q, "icons/exit.png", e -> System.exit(0));

        JMenu helpMenu = createStyledMenu("Help", Color.GREEN);
        addMenuItem(helpMenu, "Controls", KeyEvent.VK_C, "icons/controls.png", e -> showControls());
        addMenuItem(helpMenu, "About", KeyEvent.VK_A, "icons/about.png", e -> showAbout());

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void styleMenuBar(JMenuBar menuBar) {
        menuBar.setBackground(new Color(30, 30, 30));
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private JMenu createStyledMenu(String title, Color color) {
        JMenu menu = new JMenu(title);
        menu.setForeground(color);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menu.setOpaque(true);
        menu.setBackground(new Color(45, 45, 45));
        menu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        menu.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menu.setBackground(new Color(60, 60, 60));
            }
            public void mouseExited(MouseEvent e) {
                menu.setBackground(new Color(45, 45, 45));
            }
        });
        return menu;
    }

    private void addMenuItem(JMenu menu, String text, int keyCode, String iconPath, ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.setIcon(new ImageIcon(iconPath));
        item.setAccelerator(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK));
        item.addActionListener(action);
        styleMenuItem(item);
        menu.add(item);
    }

    private void styleMenuItem(JMenuItem item) {
        item.setForeground(Color.WHITE);
        item.setBackground(new Color(60, 60, 60));
        item.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        item.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));

        item.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                item.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(MouseEvent e) {
                item.setBackground(new Color(60, 60, 60));
            }
        });
    }

    private void showControls() {
        String controls = """
            Controls:
            ← → - Move left/right
            ↑ - Rotate
            ↓ - Soft drop
            Space - Hard drop
            """;
        JOptionPane.showMessageDialog(this, controls, "Game Controls", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        String about = """
            Classic Tetris Clone
            Version 1.0
            Developed by Arsenii Taskaev
            © 2024 All rights not reserved
            """;
        JOptionPane.showMessageDialog(this, about, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public JComponent getGamePanel() {
        return gamePanel;
    }
}
