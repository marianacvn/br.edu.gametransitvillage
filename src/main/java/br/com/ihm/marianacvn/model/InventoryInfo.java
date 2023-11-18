package br.com.ihm.marianacvn.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InventoryInfo {

    private Type type;

    private int posX;
    private int posY;
    private String valueString;
    private BufferedImage valueImage;
    private Font valueFont;

    public InventoryInfo(Type type, int posX, int posY, String valueString) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.valueString = valueString;
    }

    public InventoryInfo(Type type, int posX, int posY, BufferedImage valueImage) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.valueImage = valueImage;
    }

    public InventoryInfo(Type type, String nameFont, int styleFont, int sizeFont) {
        this.type = type;
        this.valueFont = new Font(nameFont, styleFont, sizeFont);
    }

    public void draw(Graphics g) {
        switch (type) {
            case STRING:
                g.drawString(valueString, posX, posY);
                break;
            case IMAGE:
                g.drawImage(valueImage, posX, posY, null);
                break;
            case FONT:
                g.setFont(valueFont);
                break;
        }
    }

    public enum Type {
        STRING("STR"), IMAGE("IMG"), FONT("FNT");

        private String value;
        Type(String str) {
            this.value = str;
        }

        public String getValue() {
            return value;
        }

        public static Type fromString(String text) {
            for (Type b : Type.values()) {
                if (b.value.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }
    }
}
