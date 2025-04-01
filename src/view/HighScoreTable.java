package view;

import model.GameModel;
import model.ModelListener;
import model.ScoreTable;
import utils.ScoreEntry;

import javax.swing.*;
import java.awt.*;

public class HighScoreTable extends JPanel implements ModelListener {
    private JList<ScoreEntry> list;
    private DefaultListModel<ScoreEntry> listModel;

    public HighScoreTable(GameModel model) {
        setBorder(BorderFactory.createTitledBorder("Top 10"));
        setPreferredSize(new Dimension(200, 400));

        model.addModelListener(this);

        listModel = new DefaultListModel<>();
        updateList();

        list = new JList<>(listModel);
        list.setCellRenderer(new ScoreListRenderer());

        add(new JScrollPane(list));
    }

    public void updateList() {
        listModel.clear();
        ScoreTable.getInstance().getHighScores().forEach(listModel::addElement);
    }

    private static class ScoreListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            ScoreEntry entry = (ScoreEntry) value;
            String text = String.format("%2d. %-15s %6d",
                    index + 1,
                    entry.getPlayerName(),
                    entry.getScore());
            return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
        }
    }

    @Override
    public void onModelChanged() {
        updateList();
        repaint();
    }
}
