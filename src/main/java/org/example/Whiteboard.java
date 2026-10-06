package org.example;

import javax.swing.*;
import java.awt.*;

public class Whiteboard extends JPanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Whiteboard");

           DrawingPanel panel = new DrawingPanel();

            window.add(panel);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }

}
