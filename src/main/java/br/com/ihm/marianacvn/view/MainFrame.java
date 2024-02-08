package br.com.ihm.marianacvn.view;

import br.com.ihm.marianacvn.utils.ErrorHandler;
import br.com.ihm.marianacvn.view.components.GameButton;
import br.com.ihm.marianacvn.view.components.GameComandoLabel;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.io.IOException;

public class MainFrame extends BaseFrame {


    public MainFrame() throws IOException {
        super();

        // Carrega o Tema FlatIntelliJLaf
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            ErrorHandler.logAndExit(e);
        }

        panels.add(new MenuPanel("menu", "/assets/images/background/menu-game.png"));
        panels.add(new StartPanel("start", "/assets/images/background/telas-game.png"));
        panels.add(new NewGamePanel("new-game", "/assets/images/background/telas-game.png"));
        panels.add(new HelpPanel("help", "/assets/images/background/telas-game.png"));
        panels.add(new LoadingPanel("loading", "/assets/images/background/telas-game.png"));

        MapPanel mapPanel = new MapPanel("map", 389, 0, BaseFrame.DEFAULT_WIDTH - 389, 1000);
        panels.add(mapPanel);

        panels.add(new MinimapPanel("minimap", 5, 5, 384, 270, mapPanel));
        panels.add(new InventoryPanel("inventory", 5, 270, 384, 720));
        panels.add(new ComandosPanel("comandos", 0, 1000, BaseFrame.DEFAULT_WIDTH, 80));

        buttons.add(new GameButton("novo", 50, -400, 0, 0));
        GameButton jogarButton = new GameButton("jogar", 0, 0, 0, 0);
        jogarButton.changePosition(BaseFrame.CENTER_DEFAULT_X_BUTTON, 850);
        jogarButton.setVisible(false);
        buttons.add(jogarButton);
        buttons.add(new GameButton("tutorial", 50, -400 + buttons.get(0).getX() + 5, 0, 0));
        buttons.add(new GameButton("sair", 50, -400 + buttons.get(2).getX() + 5, 0, 0));
        GameButton voltarButton = new GameButton("voltar", -400, -864, 52, 52);
        voltarButton.setBorderPainted(false);
        buttons.add(voltarButton);
        ;
        buttons.add(new GameButton("play-music", -400, 878, 52, 52));
        buttons.add(new GameButton("stop-music", -400, 878, 52, 52));

        getButtonByKey("voltar").setVisible(false);
        getButtonByKey("stop-music").setVisible(false);

        for (GameButton button : buttons) {
            add(button);
        }

        for (BasePanel panel : panels) {
            add(panel);
        }

        setIgnoreRepaint(true);
        setVisible(true);

    }

}
