package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel  extends JPanel {

    private int previousX;
    private int previousY;
    private final List<Line2D.Double> lines = new ArrayList<>();
    private final UdpConnection connection;

    public DrawingPanel(UdpConnection connection) {

        this.connection = connection;
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

                addLine(previousX, previousY, currentX, currentY);

                String message = previousX + " " + previousY
                        + " " + currentX + " " + currentY;
                try {
                    connection.send(message);
                } catch (IOException ex) {
                    System.out.println("Unable to send!");
                }

                previousX = currentX;
                previousY = currentY;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Loop through lines and draw each one using g2.draw(...)
        for(Line2D line : lines){
            g2.draw(line);
        }
    }

    public void addLine(int x1, int y1, int x2, int y2) {
        lines.add(new Line2D.Double(x1, y1, x2, y2));
        repaint();
    }
}
