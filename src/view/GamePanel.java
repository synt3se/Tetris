package view;

import controller.GameController;
import model.GameModel;
import model.ModelListener;
import model.tetromino.Tetromino;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel implements ModelListener {
    public static final int BLOCK_SIZE = 30;
    private GameModel model;
    private GameController controller;
    private JPanel gameOverPanel;
    private JLabel scoreLabel;

    public GamePanel(GameModel model, GameController controller) {
        this.model = model;
        this.controller = controller;
        model.addModelListener(this);
        setPreferredSize(new Dimension(model.WIDTH * BLOCK_SIZE, model.HEIGHT * BLOCK_SIZE));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        initGameOverPanel();
    }

    private void initGameOverPanel() {
        gameOverPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 200));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gameOverPanel.setOpaque(false);
        gameOverPanel.setVisible(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 20, 10, 20);

        JLabel titleLabel = new JLabel("GAME OVER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.RED);
        gameOverPanel.add(titleLabel, gbc);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.WHITE);
        gameOverPanel.add(scoreLabel, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JButton restartButton = new JButton("Restart");
        styleButton(restartButton,
                new Color(46, 139, 87), // Цвет морской волны
                Color.WHITE
        );
        JButton exitButton = new JButton("Exit");
        styleButton(exitButton,
                new Color(178, 34, 34), // Кирпичный цвет
                Color.WHITE
        );

        restartButton.addActionListener(e -> restartGame());
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        gbc.fill = GridBagConstraints.CENTER;
        gameOverPanel.add(buttonPanel, gbc);

        this.add(gameOverPanel, BorderLayout.CENTER);
    }

    public void restartGame() {
        gameOverPanel.setVisible(false);
        controller.newGame();
        repaint();
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawFallenBlocks(g);
        drawCurrentPiece(g);
        drawGhost(g);
        if (model.isGameOver()) {
            gameOverPanel.setVisible(true);
            gameOverPanel.repaint();
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(new Color(50, 50, 50));
        for (int x = 0; x <= model.WIDTH; x++) {
            g.drawLine(x * BLOCK_SIZE, 0, x * BLOCK_SIZE, model.HEIGHT * BLOCK_SIZE);
        }
        for (int y = 0; y <= model.HEIGHT; y++) {
            g.drawLine(0, y * BLOCK_SIZE, model.WIDTH * BLOCK_SIZE, y * BLOCK_SIZE);
        }
    }

    private void drawFallenBlocks(Graphics g) {
        int[][] grid = model.getGrid();
        for (int y = 0; y < model.HEIGHT; y++) {
            for (int x = 0; x < model.WIDTH; x++) {
                if (grid[y][x] != 0) {
                    drawBlock(g, x, y, Color.GRAY);
                }
            }
        }
    }

    private void drawCurrentPiece(Graphics g) {
        Tetromino piece = model.getCurrTetromino();
        Color color = piece.getColor();
        int x0 = piece.getPosX();
        int y0 = piece.getPosY();
        int[][] repr = piece.getRepr();
        for (int y = 0; y < repr.length; ++y) {
            for (int x = 0; x < repr[0].length; ++x) {
                if (y0 + y >= 0 && repr[y][x] == 1) {
                    drawBlock(g, x0 + x, y0 + y, color);
                }
            }
        }
    }

    private void drawGhost(Graphics g) {
        Tetromino ghost = model.getGhostTetromino();
        Color color = ghost.getColor();
        int x0 = ghost.getPosX();
        int y0 = ghost.getPosY();
        int[][] repr = ghost.getRepr();
        for (int y = 0; y < repr.length; ++y) {
            for (int x = 0; x < repr[0].length; ++x) {
                if (y0 + y >= 0 && repr[y][x] == 1) {
                    drawGhostBlock(g, x0 + x, y0 + y, color);
                }
            }
        }
    }

    private void drawGhostBlock(Graphics g, int x, int y, Color baseColor) {
        Color translucentColor = new Color(
                baseColor.getRed(),
                baseColor.getGreen(),
                baseColor.getBlue(),
                50
        );

        g.setColor(translucentColor);
        g.fillRect(
                x * BLOCK_SIZE + 1,
                y * BLOCK_SIZE + 1,
                BLOCK_SIZE - 2,
                BLOCK_SIZE - 2
        );

        g.setColor(baseColor);
        g.drawRect(
                x * BLOCK_SIZE,
                y * BLOCK_SIZE,
                BLOCK_SIZE - 1,
                BLOCK_SIZE - 1
        );

        Color brighterTranslucent = new Color(
                baseColor.getRed(),
                baseColor.getGreen(),
                baseColor.getBlue(),
                120
        );

        g.setColor(brighterTranslucent);
        g.drawLine(
                x * BLOCK_SIZE,
                y * BLOCK_SIZE,
                (x + 1) * BLOCK_SIZE - 1,
                y * BLOCK_SIZE
        );
    }

    private void drawBlock(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(
                x * BLOCK_SIZE + 1,
                y * BLOCK_SIZE + 1,
                BLOCK_SIZE - 2,
                BLOCK_SIZE - 2
        );

        // Add 3D-effect
        g.setColor(color.brighter());
        g.drawLine(
                x * BLOCK_SIZE,
                y * BLOCK_SIZE,
                (x + 1) * BLOCK_SIZE - 1,
                y * BLOCK_SIZE
        );
        g.drawLine(
                x * BLOCK_SIZE,
                y * BLOCK_SIZE,
                x * BLOCK_SIZE,
                (y + 1) * BLOCK_SIZE - 1
        );
    }

    private void styleButton(JButton button, Color bgColor, Color textColor) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false); // Убираем рамку фокуса
        button.setContentAreaFilled(false); // Делаем фон прозрачным
        button.setOpaque(true); // Разрешаем отрисовку фона
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        button.setBackground(bgColor);
        button.setForeground(textColor);

        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, bgColor.brighter(),
                        0, c.getHeight(), bgColor.darker()
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);

                super.paint(g2, c);
                g2.dispose();
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    @Override
    public void onModelChanged() {
        if (model.isGameOver()) {
            scoreLabel.setText("Score: " + model.getScore());
        }
        repaint();
    }
}
