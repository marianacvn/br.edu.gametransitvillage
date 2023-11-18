package br.com.ihm.marianacvn.statics;

import br.com.ihm.marianacvn.model.InventoryInfo;
import br.com.ihm.marianacvn.model.Personagem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryInfos {
    static List<InventoryInfo> infos = new ArrayList<>();
    private static BufferedImage iconeCoracao;
    private static BufferedImage iconeEstrela;

    static {
        try {
            iconeCoracao = ImageIO.read(InventoryInfos.class.getResourceAsStream("/res/iconeCoracao.png"));
            iconeEstrela = ImageIO.read(InventoryInfos.class.getResourceAsStream("/res/iconeEstrela.png"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar imagens do inventário");
        }
    }

    public static List<InventoryInfo> getSingleInventoryInfos(List<Personagem> personagens) throws IOException {

        infos.add(new InventoryInfo(InventoryInfo.Type.IMAGE, 530, 27, iconeCoracao));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 553, 40, "HP: " + personagens.get(0).getVida()));
        infos.add(new InventoryInfo(InventoryInfo.Type.IMAGE, 530, 47, iconeEstrela));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 553, 58, "Pontos: " + personagens.get(0).getPontos()));
        infos.add(new InventoryInfo(InventoryInfo.Type.FONT, "Arial", Font.BOLD, 15));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 530, 240, "Status: "));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 530, 260, personagens.get(0).getStatus(60, 0)));

        return infos;
    }

    public static List<InventoryInfo> getMultiInventoryInfos(List<Personagem> personagens) throws IOException {
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 530, 33, "Player 1"));
        infos.add(new InventoryInfo(InventoryInfo.Type.IMAGE, 530, 40, iconeCoracao));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 553, 52, "HP: " + personagens.get(0).getVida()));
        infos.add(new InventoryInfo(InventoryInfo.Type.IMAGE, 530, 57, iconeEstrela));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 553, 69, "Pontos: " + personagens.get(0).getPontos()));
        infos.add(new InventoryInfo(InventoryInfo.Type.FONT, "Arial", Font.BOLD, 15));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 530, 150, "Status: "));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 530, 170, personagens.get(0).getStatus(60, 0)));
        infos.add(new InventoryInfo(InventoryInfo.Type.FONT, "Arial", Font.BOLD, 12));

        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 530, 275, "Player 2"));
        infos.add(new InventoryInfo(InventoryInfo.Type.IMAGE, 530, 282, iconeCoracao));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 553, 294, "HP: " + personagens.get(0).getVida()));
        infos.add(new InventoryInfo(InventoryInfo.Type.IMAGE, 530, 299, iconeEstrela));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 553, 311, "Pontos: " + personagens.get(0).getPontos()));
        infos.add(new InventoryInfo(InventoryInfo.Type.FONT, "Arial", Font.BOLD, 15));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 530, 380, "Status: "));
        infos.add(new InventoryInfo(InventoryInfo.Type.STRING, 530, 400, personagens.get(0).getStatus(60, 0)));

        return infos;
    }

}
