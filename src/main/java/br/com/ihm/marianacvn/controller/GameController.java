package br.com.ihm.marianacvn.controller;

import br.com.ihm.marianacvn.model.*;
import br.com.ihm.marianacvn.utils.Config;
import br.com.ihm.marianacvn.utils.ErrorHandler;
import br.com.ihm.marianacvn.utils.MusicPlayer;
import br.com.ihm.marianacvn.view.*;
import br.com.ihm.marianacvn.view.components.GameButton;
import br.com.ihm.marianacvn.view.components.GameFaseLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GameController extends KeyAdapter implements ActionListener {
    private MainFrame mainFrame;
    private MapPanel mapPanel;
    private Logica logica;
    private Personagem personagem;
    private Personagem veiculo;
    private MusicPlayer introGamePlayer;
    private static final GraphicsDevice DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[1]; // TODO : Mudar para monitor 0
    private static final int TARGET_FPS = 60;
    public static java.util.List<Rectangle> colisao;
    private boolean cima, baixo, direita, esquerda;
    private int up, down, left, right;
    private boolean  musicStatus;

    public GameController() throws IOException {

        // Inicia a reprodução da música em uma thread separada
        Thread musicThread = new Thread(() -> {
            // Carrega as configurações do jogo (volume, idioma, etc)
            try {
                Config.load();
            } catch (IOException  e) {
                ErrorHandler.logAndExit(e);
            }
            introGamePlayer = new MusicPlayer("/assets/audios/kygo-stranger-things.mp3");
        });
        musicThread.start();

        // Inicia a interface gráfica em outra thread
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (IOException e) {
                ErrorHandler.logAndExit(e);
            }
        });

        // Aguarde a conclusão da reprodução da música
        try {
            musicThread.join();
        } catch (InterruptedException e) {
            ErrorHandler.logAndExit(e);
        }
    }

    private void createAndShowGUI() throws IOException {
        mainFrame = new MainFrame();

        musicButtonStatus();

        DEVICE.setFullScreenWindow(mainFrame);

        for (GameButton button : mainFrame.buttons) {
            button.addActionListener(this);
        }
        for (GameFaseLabel fase : ((NewGamePanel) mainFrame.getPanelByKey("new-game")).getFases()) {
            fase.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (!fase.isBlocked()) {
                        fase.setSelected(true);
                        fase.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    }
                }
            } );
        }

        ((NewGamePanel) mainFrame.getPanelByKey("new-game")).getRightButton().addActionListener(this);
        ((NewGamePanel) mainFrame.getPanelByKey("new-game")).getLeftButton().addActionListener(this);

        mainFrame.addKeyListener(this);

        mainFrame.setFocusable(true);

    }

    private void musicButtonStatus() {
        musicStatus = (boolean) Config.getConfigValue("music");
        if (musicStatus) {
            mainFrame.getButtonByKey("play-music").setVisible(true);
            mainFrame.getButtonByKey("stop-music").setVisible(false);
        } else {
            mainFrame.getButtonByKey("play-music").setVisible(false);
            mainFrame.getButtonByKey("stop-music").setVisible(true);
        }
    }

    private void iniciarJogo() {
        logica = new Logica();
        mapPanel = (MapPanel) mainFrame.getPanelByKey("map");
        mapPanel.setLogica(logica);
        mapPanel.setPersonagem(personagem);
        mapPanel.setVeiculo(veiculo);
        colisao = logica.getCamada("colision").montarColisao(veiculo);
        run();
    }

    public void montarMapa() {
        for (Camada camada : logica.getCamadas()) {
            camada.montarMapa();
        }
    }

    public void run() {
        GameLoop gameLoop = new GameLoop(TARGET_FPS, this);
        gameLoop.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

		// Game
        if (mainFrame.getButtonByKey("jogar") == e.getSource() && mainFrame.getCurrentPanel().getKey().equals("new-game")) {
            String player = ((NewGamePanel) mainFrame.getCurrentPanel()).getCurrentPlayer();
            // TODO: Colocar uma validaçao para verificar se o jogador selecionou uma fase, se não deve  exibir uma mensagem de alerta, ainda não sei o design do alerta.
            String veiculoName = ((NewGamePanel) mainFrame.getPanelByKey("new-game")).getFaseAtiva().getSpriteByFase();
            int veiculoSize = veiculoName.equals("pizza") ? 64 :  128;
            personagem = new Personagem(8, 64, 64, 13, 21, 30, 500, "/assets/images/sprite/" + (player.equals("male-player") ? "character-male_universal" :  "character-female_universal") + ".png");
            veiculo = new Personagem(8, veiculoSize, veiculoSize, 6, 4, 30, 350, "/assets/images/sprite/" + veiculoName + "-sprite.png");

            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    mainFrame.getButtonByKey("jogar").setVisible(false);
                    mainFrame.getButtonByKey("voltar").setVisible(false);
                    mainFrame.getPanelByKey("new-game").setVisible(false);
                    mainFrame.getPanelByKey("start").setVisible(false);
                    mainFrame.getPanelByKey("loading").setVisible(true);
                    iniciarJogo();
                    Thread.sleep(8600);
                    return null;
                }

                @Override
                protected void done() {
                    mainFrame.getPanelByKey("loading").setVisible(false);
                    mainFrame.getPanelByKey("map").setVisible(true);
                }
            };

            worker.execute();
        }
//		// Menu Buttons
        if (mainFrame.getButtonByKey("novo") == e.getSource()) {
            mainFrame.disableMenuComponents("new-game");
            mainFrame.getButtonByKey("jogar").setVisible(true);
            mainFrame.getButtonByKey("jogar").changePosition(BaseFrame.CENTER_DEFAULT_X_BUTTON,  850);
        }
        if (mainFrame.getButtonByKey("jogar") == e.getSource()) {
            mainFrame.disableMenuComponents("start");
        }
        if (mainFrame.getButtonByKey("tutorial") == e.getSource()) {
            mainFrame.disableMenuComponents("help");
        }
        if (mainFrame.getButtonByKey("play-music") == e.getSource()) {
            introGamePlayer.stop();
            mainFrame.getButtonByKey("play-music").setVisible(false);
            mainFrame.getButtonByKey("stop-music").setVisible(true);
        }
        if (mainFrame.getButtonByKey("stop-music") == e.getSource()) {
            introGamePlayer.playInLoop();
            mainFrame.getButtonByKey("play-music").setVisible(true);
            mainFrame.getButtonByKey("stop-music").setVisible(false);
        }
        if (mainFrame.getButtonByKey("sair") == e.getSource()) {
            System.exit(0);
        }
//		// Voltar
        if (mainFrame.getButtonByKey("voltar") == e.getSource() && mainFrame.getCurrentPanel().getKey().equals("start")) {
            mainFrame.enableMenuComponents("start");
            musicButtonStatus();
        }
        if (mainFrame.getButtonByKey("voltar") == e.getSource() && mainFrame.getCurrentPanel().getKey().equals("new-game")) {
            mainFrame.getButtonByKey("jogar").resetPosition();
            mainFrame.enableMenuComponents("new-game");
            musicButtonStatus();
        }
        if (mainFrame.getCurrentPanel().getKey().equals("new-game") && ((NewGamePanel) mainFrame.getCurrentPanel()).getRightButton() == e.getSource()) {
            ((NewGamePanel) mainFrame.getCurrentPanel()).setPlayerImage();
        }
        if (mainFrame.getCurrentPanel().getKey().equals("new-game") && ((NewGamePanel) mainFrame.getCurrentPanel()).getLeftButton() == e.getSource()) {
            ((NewGamePanel) mainFrame.getCurrentPanel()).setPlayerImage();
        }
        if (mainFrame.getButtonByKey("voltar") == e.getSource() && mainFrame.getCurrentPanel().getKey().equals("help")) {
            mainFrame.enableMenuComponents("help");
            musicButtonStatus();
        }


    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Pause game");
        }

        if (e.getKeyCode() == KeyEvent.VK_W) cima = true;
        if (e.getKeyCode() == KeyEvent.VK_S) baixo = true;
        if (e.getKeyCode() == KeyEvent.VK_A) esquerda = true;
        if (e.getKeyCode() == KeyEvent.VK_D) direita = true;

        movimento();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) cima = false;
        if (e.getKeyCode() == KeyEvent.VK_S) baixo = false;
        if (e.getKeyCode() == KeyEvent.VK_A) esquerda = false;
        if (e.getKeyCode() == KeyEvent.VK_D) direita = false;
    }

    public void movimento() {
        if (esquerda) {
            int xL = personagem.getX();
            int yL = personagem.getY();
            if (xL > Personagem.DIFF_COLISAO) {
                personagem.setX(xL - Personagem.VELOCIDADE);
                personagem.setY(yL);
                switch (left) {
                    case 0:
                        personagem.setAparencia(9);
                        break;
                    case 1:
                        personagem.setAparencia(30);
                        break;
                    case 2:
                        personagem.setAparencia(51);
                        break;
                    case 3:
                        personagem.setAparencia(72);
                        break;
                    case 4:
                        personagem.setAparencia(93);
                        break;
                    case 5:
                        personagem.setAparencia(114);
                        break;
                    case 6:
                        personagem.setAparencia(135);
                        break;
                    case 7:
                        personagem.setAparencia(156);
                        break;
                    case 8:
                        personagem.setAparencia(177);
                        break;
                    case 9:
                        personagem.setAparencia(198);
                        break;
                    case 10:
                        personagem.setAparencia(219);
                        break;
                }
                if (left == 10) {
                    left = 0;
                } else {
                    left++;
                }

            }
        }
        if (cima) {
            int xL = personagem.getX();
            int yL = personagem.getY();
            if (yL > Personagem.DIFF_COLISAO) {
                personagem.setX(xL);
                personagem.setY(yL - Personagem.VELOCIDADE);
                switch (up) {
                    case 0:
                        personagem.setAparencia(8);
                        break;
                    case 1:
                        personagem.setAparencia(29);
                        break;
                    case 2:
                        personagem.setAparencia(50);
                        break;
                    case 3:
                        personagem.setAparencia(71);
                        break;
                    case 4:
                        personagem.setAparencia(92);
                        break;
                    case 5:
                        personagem.setAparencia(113);
                        break;
                    case 6:
                        personagem.setAparencia(134);
                        break;
                    case 7:
                        personagem.setAparencia(155);
                        break;
                    case 8:
                        personagem.setAparencia(176);
                        break;
                    case 9:
                        personagem.setAparencia(197);
                        break;
                    case 10:
                        personagem.setAparencia(218);
                        break;
                }
                if (up == 10) {
                    up = 0;
                } else {
                    up++;
                }
            }

        }
        if (direita) {
            int xL = personagem.getX();
            int yL = personagem.getY();
            if (xL < 1856 - Personagem.DIFF_COLISAO) {
                personagem.setX(xL + Personagem.VELOCIDADE);
                personagem.setY(yL);
                switch (right) {
                    case 0:
                        personagem.setAparencia(11);
                        break;
                    case 1:
                        personagem.setAparencia(32);
                        break;
                    case 2:
                        personagem.setAparencia(53);
                        break;
                    case 3:
                        personagem.setAparencia(74);
                        break;
                    case 4:
                        personagem.setAparencia(95);
                        break;
                    case 5:
                        personagem.setAparencia(116);
                        break;
                    case 6:
                        personagem.setAparencia(137);
                        break;
                    case 7:
                        personagem.setAparencia(158);
                        break;
                    case 8:
                        personagem.setAparencia(179);
                        break;
                    case 9:
                        personagem.setAparencia(200);
                        break;
                    case 10:
                        personagem.setAparencia(221);
                        break;
                }
                if (right == 10) {
                    right = 0;
                } else {
                    right++;
                }
            }
        }
        if (baixo) {
            int xL = personagem.getX();
            int yL = personagem.getY();
            if (yL < 1016 - Personagem.DIFF_COLISAO) {
                personagem.setX(xL);
                personagem.setY(yL + Personagem.VELOCIDADE);
                switch (down) {
                    case 0:
                        personagem.setAparencia(10);
                        break;
                    case 1:
                        personagem.setAparencia(31);
                        break;
                    case 2:
                        personagem.setAparencia(52);
                        break;
                    case 3:
                        personagem.setAparencia(73);
                        break;
                    case 4:
                        personagem.setAparencia(94);
                        break;
                    case 5:
                        personagem.setAparencia(115);
                        break;
                    case 6:
                        personagem.setAparencia(136);
                        break;
                    case 7:
                        personagem.setAparencia(157);
                        break;
                    case 8:
                        personagem.setAparencia(178);
                        break;
                    case 9:
                        personagem.setAparencia(199);
                        break;
                    case 10:
                        personagem.setAparencia(220);
                        break;
                }
                if (down == 10) {
                    down = 0;
                } else {
                    down++;
                }

            }
        }
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public MusicPlayer getIntroGamePlayer() {
        return introGamePlayer;
    }

    public Logica getLogica() {
        return logica;
    }

}
