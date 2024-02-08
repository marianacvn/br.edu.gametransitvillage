package br.com.ihm.marianacvn.view;

import br.com.ihm.marianacvn.model.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

@Getter
@Setter
public class InventoryPanel extends BasePanel {

    private ImageIcon cnhImage, alertImage, coinImage;
    private Personagem personagem;
    private Logica logica;
    private Fase faseAtual;
    private Missao missaoAtual;

    public InventoryPanel(String key, int x, int y, int width, int height) {
        super(key, x, y, width, height);
        setLayout(null);

        alertImage = new ImageIcon(Objects.requireNonNull(InventoryPanel.class.getResource("/assets/images/icons/alert-icon.png")));
        coinImage = new ImageIcon(Objects.requireNonNull(InventoryPanel.class.getResource("/assets/images/icons/coin-icon.png")));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(Color.BLACK);
        g2.drawImage(cnhImage.getImage(), 10, 10, null);
        exibirDadosFicticios(g2);
        drawPoints(g2);
        drawCoins(g2);
        drawMissionDetails(g2);
    }

    private void exibirDadosFicticios(Graphics2D g2) {
        DadosPersonagem personagem = this.personagem.getDadosPersonagem();
        g2.drawString("Nome: " + personagem.getNomePersonagem(), 23, 30);
        g2.drawString("CPF: " + personagem.getCpf(), 196, 58);
        g2.drawString("RG: " + personagem.getRg(), 196, 82);
        g2.drawString(personagem.getDataNascimento(), 310, 82);
        g2.drawString(personagem.getCategoriaHabilitacao(), 335, 175);
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(196, 163, 60, 12);
        g2.fillRect(268, 163, 45, 12);
        g2.setColor(Color.BLACK);
        g2.drawString("Nro. Registro: " + personagem.getNroRegistro(), 23, 199);
        g2.drawString("Validade: " + personagem.getValidade(), 203, 199);
    }

    private void drawPoints(Graphics2D g2) {
        g2.setFont(BaseFrame.DEFAULT_FONT.deriveFont(Font.BOLD, 16));
        g2.drawImage(alertImage.getImage(), 15, 240, null);
        g2.drawString("Pontuação: ", 45, 260);
        g2.drawString(String.valueOf(personagem.getPontuacao()), 220, 260);
    }

    private void drawCoins(Graphics2D g2) {
        g2.drawImage(coinImage.getImage(), 15, 280, null);
        g2.drawString("Moedas: ", 45, 300);
        g2.drawString(String.valueOf(personagem.getMoedas()), 220, 300);
    }

    private void drawMissionDetails(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString(missaoAtual.getTitulo(), 45, 340);

        g2.setFont(new Font("Arial", Font.BOLD, 13));

        g2.drawString("Recompensa: ", 40, 360);
        g2.drawString(missaoAtual.getRecompensa() + " Moedas", 150, 360);

        // Quebra a descrição em várias linhas
        String descricao = missaoAtual.getDescricao();
        int width = this.getWidth() - 100;
        FontMetrics fm = g2.getFontMetrics();
        int lineHeight = fm.getHeight();
        int curX = 45;
        int curY = 380;

        String[] words = descricao.split(" ");
        String line = words[0];

        curY = getWordsLine(g2, width, fm, lineHeight, curX, curY, words, line);

        g2.drawString("Regras: ", 40, curY);
        // Desenha as regras em várias linhas;
        curY += lineHeight;

        for (String regra : missaoAtual.getRegras()) {
            words = regra.split(" ");
            line = words[0];

            curY = getWordsLine(g2, width, fm, lineHeight, curX, curY, words, line);
        }
    }

    private int getWordsLine(Graphics2D g2, int width, FontMetrics fm, int lineHeight, int curX, int curY, String[] words, String line) {
        StringBuilder lineBuilder = new StringBuilder(line);
        for (int i = 1; i < words.length; i++) {
            if (fm.stringWidth(lineBuilder + words[i]) < width) {
                lineBuilder.append(" ").append(words[i]);
            } else {
                g2.drawString(lineBuilder.toString(), curX, curY);
                lineBuilder = new StringBuilder(words[i]);
                curY += lineHeight;
            }
        }
        line = lineBuilder.toString();
        if (!line.isEmpty()) {
            g2.drawString(line, curX, curY);
            curY += lineHeight;
        }
        return curY;
    }

    public void setPersonagem(Personagem personagem) {

        this.personagem = personagem;
        if (personagem.getCurrentPlayer().equals("male-player")) {
            cnhImage = new ImageIcon(Objects.requireNonNull(InventoryPanel.class.getResource("/assets/images/cnh-male.png")));
            personagem.setDadosPersonagem(new DadosPersonagem("Felipe Silva", "123.456.789-00", "1234567", "1990", "AB", "123456789", "01/01/2025"));
        } else {
            cnhImage = new ImageIcon(Objects.requireNonNull(InventoryPanel.class.getResource("/assets/images/cnh-female.png")));
            personagem.setDadosPersonagem(new DadosPersonagem("Maria Silva", "123.456.789-00", "1234567", "1990", "AB", "123456789", "01/01/2025"));
        }

    }

    public void setLogica(Logica logica) {
        this.logica = logica;
        faseAtual = logica.getFases().get(0);
        missaoAtual = faseAtual.getMissoes().get(0);
    }
}
