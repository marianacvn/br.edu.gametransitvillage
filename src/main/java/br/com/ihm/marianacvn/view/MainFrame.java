package br.com.ihm.marianacvn.view;

import br.com.ihm.marianacvn.view.components.GameButton;

import java.io.IOException;

public class MainFrame extends BaseFrame {

	public MainFrame() throws IOException {
		super();

		panels.add(new MenuPanel("menu", "/assets/images/background/menu-game.png"));
		panels.add(new StartPanel("start", "/assets/images/background/telas-game.png"));
		panels.add(new NewGamePanel("new-game", "/assets/images/background/telas-game.png"));
		panels.add(new HelpPanel("help", "/assets/images/background/telas-game.png"));
		panels.add(new CreditPanel("credit", "/assets/images/background/telas-game.png"));
		panels.add(new MapPanel("map"));

		buttons.add(new GameButton("novo", 50, -530, 0,0));
		buttons.add(new GameButton("jogar", 50, -530+buttons.get(0).getX()+70, 0,0));
		buttons.add(new GameButton("tutorial", 50, -530+buttons.get(1).getX()+70, 0,0));
		buttons.add(new GameButton("sair", 50, -530+buttons.get(2).getX()+70, 0,0));
		buttons.add(new GameButton("voltar", -400, -864, 52,52));;
		getButtonByKey("voltar").setVisible(false);

		for (GameButton button : buttons) {
			add(button);
		}

		for(BasePanel panel : panels) {
			add(panel);
		}

		setVisible(true);
		
	}

}
