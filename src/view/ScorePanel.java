package view;

import model.GameModel;
import model.ModelListener;

import javax.swing.*;

public class ScorePanel extends JPanel implements ModelListener {
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private GameModel model;

    public ScorePanel(GameModel model) {
        this.model = model;
        model.addModelListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        scoreLabel = new JLabel("Score: 0");
        levelLabel = new JLabel("Level: 1");

        add(scoreLabel);
        add(levelLabel);
    }

    @Override
    public void onModelChanged() {
        updateLabels();
    }

    public void updateLabels() {
        scoreLabel.setText("Score: " + model.getScore());
        levelLabel.setText("Level: " + model.getLevel());
    }
}
