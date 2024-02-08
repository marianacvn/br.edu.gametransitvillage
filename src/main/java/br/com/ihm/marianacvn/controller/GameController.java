package br.com.ihm.marianacvn.controller;

import br.com.ihm.marianacvn.model.*;
import br.com.ihm.marianacvn.utils.Config;
import br.com.ihm.marianacvn.utils.ErrorHandler;
import br.com.ihm.marianacvn.utils.MusicPlayer;
import br.com.ihm.marianacvn.view.*;
import br.com.ihm.marianacvn.view.components.GameButton;
import br.com.ihm.marianacvn.view.components.GameComandoLabel;
import br.com.ihm.marianacvn.view.components.GameFaseLabel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameController extends KeyAdapter implements ActionListener {
    private MainFrame mainFrame;
    private MapPanel mapPanel;
    private MinimapPanel minimapPanel;
    private ComandosPanel comandosPanel;
    private InventoryPanel inventoryPanel;
    private List<GameComandoLabel> comandos;
    private List<GameComandoLabel> filaComandos;
    private Logica logica;
    private Personagem personagem;
    private Veiculo veiculo;
    private MusicPlayer introGamePlayer;
    private static final GraphicsDevice DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0]; // TODO : Mudar para monitor 0
    private static final int TARGET_FPS = 60;
    public static List<Rectangle> colisao;
    private boolean musicStatus;
    private boolean isRunning;

    public GameController() {

        // Inicia a reprodução da música em uma thread separada
        Thread musicThread = new Thread(() -> {
            // Carrega as configurações do jogo (volume, idioma, etc)
            try {
                Config.load();
            } catch (IOException e) {
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
            });
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
        // Cria a lógica do jogo
        logica = new Logica();
        inventoryPanel.setLogica(logica);
        // Carrega o mapa e insere as classes necessárias
        mapPanel = (MapPanel) mainFrame.getPanelByKey("map");
        mapPanel.setLogica(logica);
        mapPanel.setPersonagem(personagem);
        mapPanel.setVeiculo(veiculo);
        // Carrega o inventário e o numimap
//        inventoryPanel = (InventoryPanel) mainFrame.getPanelByKey("inventory");
        minimapPanel = (MinimapPanel) mainFrame.getPanelByKey("minimap");
        // Cria as coliões
        colisao = logica.getCamada("colision").montarColisao();
        // Inicia o game loop
        isRunning = true;
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
            int veiculoSize = veiculoName.equals("pizza") ? 64 : 128;
            veiculo = new Veiculo(8, veiculoSize, veiculoSize, 6, 4, 40, 384, "/assets/images/sprite/" + veiculoName + "-sprite.png");
            personagem = new Personagem(8, 64, 64, 13, 21, 30, 500, "/assets/images/sprite/" + (player.equals("male-player") ? "character-male_universal" : "character-female_universal") + ".png", player, veiculo);

            inventoryPanel = (InventoryPanel) mainFrame.getPanelByKey("inventory");
            inventoryPanel.setPersonagem(personagem);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
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
                    mainFrame.getPanelByKey("minimap").setVisible(true);
                    mainFrame.getPanelByKey("inventory").setVisible(true);
                }
            };

            worker.execute();
        }
//		// Menu Buttons
        if (mainFrame.getButtonByKey("novo") == e.getSource()) {
            mainFrame.disableMenuComponents("new-game");
            mainFrame.getButtonByKey("jogar").setVisible(true);
            mainFrame.getButtonByKey("jogar").changePosition(BaseFrame.CENTER_DEFAULT_X_BUTTON, 850);
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
            mainFrame.getButtonByKey("jogar").setVisible(false);
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
            mainFrame.getButtonByKey("jogar").setVisible(false);
            musicButtonStatus();
        }

        if (comandosPanel != null && comandosPanel.getPlayButton() == e.getSource() && !filaComandos.isEmpty()) {
            veiculo.setFilaComandos(filaComandos);
            veiculo.movimento();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (isRunning) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (personagem.getBounds().intersects(veiculo.getBounds())) {
                    personagem.setAtivo(false);

                    comandosPanel = (ComandosPanel) mainFrame.getPanelByKey("comandos");
                    veiculo.setComandosPanel(comandosPanel);
                    filaComandos = new ArrayList<>();
                    comandosPanel.setVisible(true);
                    comandos = comandosPanel.comandos;

                    for (GameComandoLabel comando : comandos) {
                        MouseHandler mouseHandler = new MouseHandler(filaComandos, comandosPanel);
                        comando.addMouseListener(mouseHandler);
                        comando.addMouseMotionListener(mouseHandler);
                    }

                    comandosPanel.getPlayButton().addActionListener(this);

                    mapPanel.setGameController(this);
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.out.println("Pause game");
            }

            if (personagem.isAtivo()) {

                if (e.getKeyCode() == KeyEvent.VK_W) personagem.setCima(true);
                if (e.getKeyCode() == KeyEvent.VK_S) personagem.setBaixo(true);
                if (e.getKeyCode() == KeyEvent.VK_A) personagem.setEsquerda(true);
                if (e.getKeyCode() == KeyEvent.VK_D) personagem.setDireita(true);

                personagem.movimento();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W && personagem.getY() < (veiculo.getY() - 128)) {
            JOptionPane.showMessageDialog(null, "Você não pode seguir o percurso sem estar no veículo!!", "Atenção", JOptionPane.ERROR_MESSAGE);
        } else {
            personagem.setCima(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) personagem.setBaixo(false);
        if (e.getKeyCode() == KeyEvent.VK_A) personagem.setEsquerda(false);
        if (e.getKeyCode() == KeyEvent.VK_D) personagem.setDireita(false);
    }

    public void resetGame() {
        resetEnterActions();
        personagem.resetLocale();
        veiculo.resetLocale();
        logica.getFaseAtual().setMissaoAtual(logica.getFaseAtual().getMissoes().get(0));
        inventoryPanel.setMissaoAtual(logica.getFaseAtual().getMissaoAtual());
        personagem.setMoedas(0);
        personagem.setPontos(0);
        inventoryPanel.repaint();
        personagem.setAtivo(true);
//        comandosPanel.setVisible(false);
    }

    public void resetEnterActions() {
        // Tornar o personagem ativo novamente
        personagem.setAtivo(true);

        // Limpar a fila de comandos do veículo
        veiculo.setFilaComandos(new ArrayList<>());

        // Remover os listeners dos comandos
        for (GameComandoLabel comando : comandos) {
            for (MouseListener listener : comando.getMouseListeners()) {
                comando.removeMouseListener(listener);
            }
            for (MouseMotionListener listener : comando.getMouseMotionListeners()) {
                comando.removeMouseMotionListener(listener);
            }
        }

        // Desassociar o GameController do MapPanel
        mapPanel.setGameController(null);

        // Tornar o painel de comandos invisível
        comandosPanel.setVisible(false);
    }
}
