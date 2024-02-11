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

        MapPanel mapPanel = new MapPanel("map", 389, 0, BaseFrame.DEFAULT_WIDTH - 389, 670);
        panels.add(mapPanel);

        panels.add(new MinimapPanel("minimap", 5, 5, 384, 270, mapPanel));
        panels.add(new InventoryPanel("inventory", 5, 275, 384, 395));
        panels.add(new ComandosPanel("comandos", 0, 670, BaseFrame.DEFAULT_WIDTH, 50));

        buttons.add(new GameButton("novo", 50, -230, 0, 0));
        GameButton jogarButton = new GameButton("jogar", 0, 0, 0, 0);
        jogarButton.setVisible(false);
        buttons.add(jogarButton);
        buttons.add(new GameButton("tutorial", 50, -230+ buttons.get(0).getX() - 120, 0, 0));
        buttons.add(new GameButton("sair", 50, -230 + buttons.get(2).getX()  - 120, 0, 0));
        GameButton voltarButton = new GameButton("voltar", -320, -600, 18, 18);
        voltarButton.setBorderPainted(false);
        buttons.add(voltarButton);
        ;
        buttons.add(new GameButton("play-music", -330, 580, 18, 18));
        buttons.add(new GameButton("stop-music", -330, 580, 18, 18));

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
