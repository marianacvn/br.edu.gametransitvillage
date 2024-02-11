package br.com.ihm.marianacvn.controller;

import br.com.ihm.marianacvn.view.ComandosPanel;
import br.com.ihm.marianacvn.view.components.GameComandoLabel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseHandler extends MouseAdapter {
    private GameComandoLabel draggedCommand = null;
    private Point initialLocation = null;
    private Point initialMouseLocation = null;
    private final List<GameComandoLabel> filaComandos;
    private final ComandosPanel comandosPanel;

    public MouseHandler(List<GameComandoLabel> filaComandos, ComandosPanel comandosPanel) {
        this.filaComandos = filaComandos;
        this.comandosPanel = comandosPanel;
    }

    /**
     * Este método é chamado quando um botão do mouse é pressionado.
     *
     * @param e Evento de mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {

        // TODO: Ajustar lógica para que os novos comandos passem por cima dos comandos já existentes na fila

        // Verifica se o componente que disparou o evento é uma instância de GameComandoLabel
        if (e.getSource() instanceof GameComandoLabel clickedCommand  && filaComandos.size() < 23) {

            // Cria um novo comando com as mesmas propriedades do comando clicado
            draggedCommand = new GameComandoLabel(clickedCommand.getDirecao(), clickedCommand.getX(), clickedCommand.getY(), clickedCommand.getWidth(), clickedCommand.getHeight());
            // Define a localização inicial do comando
            initialLocation = clickedCommand.getLocation();
            // Define a localização inicial do mouse
            initialMouseLocation = e.getLocationOnScreen();

            // Adiciona o comando criado ao painel e as ações de mouse
            clickedCommand.getParent().add(draggedCommand);
            draggedCommand.addMouseListener(this);
            draggedCommand.addMouseMotionListener(this);

        }

    }

    /**
     * Este método é chamado quando o mouse é arrastado.
     *
     * @param e Evento de mouse
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        // Verifica se há um comando sendo arrastado
        if (draggedCommand != null  && filaComandos.size() < 23) {
            // Calcula a diferença entre a posição inicial do mouse e a posição atual do mouse
            Point delta = new Point(e.getLocationOnScreen().x - initialMouseLocation.x, e.getLocationOnScreen().y - initialMouseLocation.y);

            //  Atualiza a posição do comando para a posição inicial do comando mais a diferença calculada
            draggedCommand.setLocation(initialLocation.x + delta.x, initialLocation.y + delta.y);

        }

    }

    /**
     * Este método é chamado quando um botão do mouse é solto.
     *
     * @param e Evento de mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        // Verifica se há um comando sendo arrastado
        if (draggedCommand != null && filaComandos.size() < 23) {

            // Verifica se o comando está dentro da área demarcada
            if (isWithinDemarcatedArea(draggedCommand)) {

                // Calcula a posição onde o comando deve ser colocado na fila
                int queueX;
                if (!filaComandos.isEmpty()) {
                    queueX = filaComandos.get(filaComandos.size() - 1).getX() + draggedCommand.getWidth() + 10;
                } else {
                    queueX = ComandosPanel.COMAND_AREA_X;
                }
                int queueY = ComandosPanel.COMAND_AREA_Y;

                // Insere a posição do comando arrastado na fila para a posição calculada
                draggedCommand.setLocation(queueX, queueY);

                // Adiciona o comando arrastado à fila
                filaComandos.add(draggedCommand);
            } else {
                // Se não estiver dentro da área demarcada, mova o comando de volta para sua localização inicial
                draggedCommand.setLocation(initialLocation);
                comandosPanel.remove(draggedCommand);
            }

        }

        draggedCommand = null;
        initialLocation = null;

    }

    /**
     * Verifica se o comando está dentro da área demarcada.
     *
     * @param command O comando a ser verificado
     * @return true se o comando estiver dentro da área demarcada, caso contrário, false
     */
    private boolean isWithinDemarcatedArea(GameComandoLabel command) {
        // Define os limites da área demarcada
        int areaX = ComandosPanel.COMAND_AREA_X;
        int areaY = ComandosPanel.COMAND_AREA_Y;
        int areaWidth = ComandosPanel.COMAND_AREA_WIDTH - 32;
        int areaHeight = ComandosPanel.COMAND_AREA_HEIGHT;

        // Pega o retângulo que representa o comando
        Rectangle commandBounds = command.getBounds();

        //  Cria um retângulo que representa a área demarcada
        Rectangle areaBounds = new Rectangle(areaX, areaY, areaWidth, areaHeight);

        // Verifica se o retângulo do comando está contido no retângulo da área demarcada
        return areaBounds.contains(commandBounds);

    }

}
