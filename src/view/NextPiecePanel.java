package view;

import model.GameModel;
import model.ModelListener;
import model.tetromino.Tetromino;

import javax.swing.*;
import java.awt.*;

public class NextPiecePanel extends JPanel implements ModelListener {
    private static final int PREVIEW_SIZE = 120;
    private static final int BLOCK_SIZE = 20;
    private GameModel model;

    public NextPiecePanel(GameModel model) {
        this.model = model;
        model.addModelListener(this);
        setPreferredSize(new Dimension(PREVIEW_SIZE, PREVIEW_SIZE));
        setBackground(new Color(30, 30, 30));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Заголовок
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g.getFontMetrics();
        String title = "Next Piece";
        g.drawString(title, (getWidth() - fm.stringWidth(title)) / 2, 20);

        Tetromino next = model.getNextTetromino().copy();
        if (next != null) {
            int[][] shape = next.getRepr();
            Color color = next.getColor();

            int startX = (getWidth() - (4 * BLOCK_SIZE)) / 2;
            int startY = 40;

            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if (shape[y][x] == 1) {
                        drawPreviewBlock(g,
                                startX + x * BLOCK_SIZE,
                                startY + y * BLOCK_SIZE,
                                color
                        );
                    }
                }
            }
        }
    }

    private void drawPreviewBlock(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x + 1, y + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y, x + BLOCK_SIZE - 1, y);
        g.drawLine(x, y, x, y + BLOCK_SIZE - 1);

        g.setColor(color.darker());
        g.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
        g.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
    }

    @Override
    public void onModelChanged() {
        repaint();
    }
}
