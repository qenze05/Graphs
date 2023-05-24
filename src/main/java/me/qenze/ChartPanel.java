package me.qenze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChartPanel extends JPanel {

    private final double r0;
    private final double a;
    private final double[] range;
    private final double step;
    private final int CENTER_X;
    private final int CENTER_Y;

    public ChartPanel(double r0, double a, double[] range, double step, int chartWidth, int chartHeight) {
        this.r0 = r0;
        this.a = a;
        this.range = range;
        this.step = step;
        this.CENTER_X = chartWidth / 2;
        this.CENTER_Y = chartHeight / 2;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Line2D line;
        double[] xy;

        BufferedImage image = new BufferedImage(CENTER_X * 2, CENTER_Y * 2, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, CENTER_X * 2, CENTER_Y * 2);

        g2d.setColor(Color.BLACK);
        g2d.drawString("X = ρ * cos(φ), " +
                "Y = ρ * sin(φ), ", 20, 40);
        g2d.drawString("ρ = Ro + A * cos(φ * 2π / 120)", 20, 60);
        g2d.drawString("φ є [" + range[0] + "; " + range[1] + "]", 20, 80);
        g2d.drawString("Ro = " + r0, 20, 100);
        g2d.drawString("A = " + a, 20, 120);

        g2d.drawLine(0, CENTER_Y, getWidth(), CENTER_Y);
        g2d.drawLine(CENTER_X, 0, CENTER_X, getHeight());

        g2.drawLine(0, CENTER_Y, getWidth(), CENTER_Y);
        g2.drawLine(CENTER_X, 0, CENTER_X, getHeight());

        g2d.drawString("0", CENTER_X - 10, CENTER_Y + 15);
        g2.drawString("0", CENTER_X - 10, CENTER_Y + 15);

        g2d.drawString("^", CENTER_X - 3, 7);
        g2d.drawString("y", CENTER_X - 15, 10);
        g2d.drawString(">", getWidth() - 10, CENTER_Y + 4);
        g2d.drawString("x", getWidth() - 15, CENTER_Y + 15);

        g2.drawString("^", CENTER_X - 3, 7);
        g2.drawString("y", CENTER_X - 15, 10);
        g2.drawString(">", getWidth() - 10, CENTER_Y + 4);
        g2.drawString("x", getWidth() - 15, CENTER_Y + 15);

        for (int i = 100; i >= -100; i -= 10) {
            if (i == 0) continue;
            drawNumbers(g2d, i);

            drawNumbers(g2, i);
        }

        g2d.setColor(Color.RED);
        g2.setColor(Color.RED);

        for (double degree = 0; degree < 360; degree += step) {
            if (degree >= range[0] && degree <= range[1]) {
                xy = getXY(degree);
                line = new Line2D.Double(CENTER_X + xy[0], CENTER_Y + xy[1],
                        CENTER_X + xy[0] + step / 10, CENTER_Y + xy[1] + step / 10);
                g2.draw(line);

                g2d.drawLine((int) (CENTER_X + xy[0]), (int) (CENTER_Y + xy[1]),
                        (int) (CENTER_X + xy[0] + step), (int) (CENTER_Y + xy[1] + step));
                g2d.draw(line);
            }
        }

        try {
            ImageIO.write(image, "png", new File("src/main/resources/image.png"));
            System.out.println("Image saved successfully to " + "src/main/resources/image.png");
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

    private void drawNumbers(Graphics2D g2d, int i) {
        g2d.drawString(String.valueOf(i), CENTER_X + i * 3 - 10, CENTER_Y + 20);
        g2d.drawLine(CENTER_X - i * 3, CENTER_Y + 5, CENTER_X - i * 3, CENTER_Y - 5);

        g2d.drawString(String.valueOf(i), CENTER_X - 40, CENTER_Y - i * 3 + 5);
        g2d.drawLine(CENTER_X - 5, CENTER_Y + i * 3, CENTER_X + 5, CENTER_Y + i * 3);
    }

    private double[] getXY(double degree) {
        double p = getP(degree);
        return new double[]{p * Math.cos(degree) * 3, p * Math.sin(degree) * 3};
    }

    private double getP(double degree) {
        return r0 + a * Math.cos(degree * 3);
    }
}
