package controller;

import model.GameModel;
import view.MainFrame;

import javax.swing.*;

import java.awt.event.ActionEvent;

import static java.lang.Math.max;

public class GameController {
    private static final int INITIAL_DELAY = 1000;
    private Timer gameTimer;
    private int currentSpeed = INITIAL_DELAY;
    private GameModel model;
    private MainFrame view;

    public GameController() {}

    public void init(GameModel model, MainFrame view) {
        this.model = model;
        this.view = view;
        initControls();
        initGameTimer();
        newGame();
    }

    public void newGame() {
        gameTimer.stop();
        model.initModel();
        currentSpeed = INITIAL_DELAY;
        gameTimer.setInitialDelay(currentSpeed);
        gameTimer.start();
    }

    private void updateCurrentSpeed() {
        currentSpeed = max(100, INITIAL_DELAY - model.getLevel() * 80);
        gameTimer.setDelay(currentSpeed);
    }

    private void initGameTimer() {
        gameTimer = new Timer(currentSpeed, e -> {
            if (model.isGameOver()) {
                gameTimer.stop();
            }
            model.moveDown();
            model.checkGameOver();
            updateCurrentSpeed();
            view.scorePanel.updateLabels();
        });
    }

    private void initControls() {
        InputMap inputMap = view.getGamePanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = view.getGamePanel().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "softDrop");
        actionMap.put("softDrop", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameTimer.stop();
                model.moveDown();
                gameTimer.start();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.moveRight();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.moveLeft();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("UP"), "rotate");
        actionMap.put("rotate", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.rotate();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "hardDrop");
        actionMap.put("hardDrop", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameTimer.stop();
                model.drop();
                gameTimer.start();
            }
        });
    }
}
