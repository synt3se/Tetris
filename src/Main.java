import controller.GameController;
import model.GameModel;
import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel();
            GameController controller = new GameController();
            MainFrame view = new MainFrame(model, controller);
            controller.init(model, view);

            view.setVisible(true);
        });
    }
}
