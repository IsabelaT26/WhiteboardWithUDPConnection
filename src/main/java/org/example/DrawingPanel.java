package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel  extends JPanel {

    private int previousX;
    private int previousY;
    private final List<Line2D.Double> lines = new ArrayList<>();

    public DrawingPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 400));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + ", " + e.getY());
                previousX = e.getX();
                previousY = e.getY();
            }
        }

        );
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int currentX = e.getX();
                int currentY = e.getY();

                System.out.println(
                        "From " + previousX + ", " + previousY
                                + " to " + currentX + ", " + currentY
                );

                lines.add(new Line2D.Double(
                        previousX, previousY, currentX, currentY
                ));

                previousX = currentX;
                previousY = currentY;
            }
        });
    }

}
