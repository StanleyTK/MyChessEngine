package com.stanleykim.chess;

import javax.swing.*;
import java.awt.*;

public class ProgressPanel extends JPanel {
    private JLabel statusLabel;
    private BaseBoardPanel baseBoardPanel;

    public ProgressPanel(BaseBoardPanel baseBoardPanel) {
        this.baseBoardPanel = baseBoardPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(40, 44, 52));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Color labelColor = Color.WHITE;

        statusLabel = createStyledLabel("",labelFont, labelColor);
        add(statusLabel);

        add(Box.createVerticalStrut(10));

//        bestMoveLabel = createStyledLabel("Best Move: ", labelFont, labelColor);
//        add(bestMoveLabel);


    }

    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }


    public void setStatusText(String s) {
        System.out.println("Setting status text to: " + s);
        statusLabel.setText(s);
    }

}
