package me.qenze;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private double r0 = 0;
    private double a = 0;
    private double minDegree = 0;
    private double maxDegree = 0;
    private double step = 1;
    private ChartPanel chart;
    private JPanel valuesPanel = new JPanel();
    private final JTextField rField = new JTextField();
    private final JTextField aField = new JTextField();
    private final JTextField minDegreeField = new JTextField();
    private final JTextField maxDegreeField = new JTextField();
    private final JTextField stepField = new JTextField();


    public MainFrame() {
        createFrame();
    }

    private void createFrame() {
        setBounds(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 20, SCREEN_WIDTH / 2, (int) (SCREEN_HEIGHT / 1.2));
        setLayout(new BorderLayout());
        add(addValuesPanel(), BorderLayout.NORTH);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addChart() {
        if (chart != null) remove(chart);
        chart = new ChartPanel(r0, a, new double[]{minDegree, maxDegree}, step,
                Math.min(getWidth(), getWidth()),
                Math.min(getHeight()-valuesPanel.getHeight(), getHeight()-valuesPanel.getHeight()));
        add(chart, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel addValuesPanel() {
        valuesPanel = new JPanel();
        valuesPanel.setLayout(new GridLayout(10, 0));
        JButton drawGraphB = new JButton("Draw");
        drawGraphB.addActionListener(e -> {
            try {
                r0 = Double.parseDouble(rField.getText());
                a = Double.parseDouble(aField.getText());
                minDegree = Double.parseDouble(minDegreeField.getText());
                maxDegree = Double.parseDouble(maxDegreeField.getText());
                step = Double.parseDouble(stepField.getText());
                if(step<=0 || minDegree<0 || maxDegree>360) throw new NumberFormatException();
                addChart();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input values are incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        valuesPanel.add(new JLabel("X = ρ * cos(φ)"));
        valuesPanel.add(new JLabel("Y = ρ * sin(φ)"));
        valuesPanel.add(new JLabel("ρ = Ro + A * cos(φ * 2π / 120)"));
        valuesPanel.add(new JLabel("φ є [0; 360]"));
        valuesPanel.add(addRo());
        valuesPanel.add(addA());
        valuesPanel.add(addMinDegree());
        valuesPanel.add(addMaxDegree());
        valuesPanel.add(addStep());
        valuesPanel.add(drawGraphB);
        return valuesPanel;
    }

    private JPanel addRo() {
        JPanel rPanel = new JPanel();
        rPanel.setLayout(new GridLayout(0, 3));
        rPanel.add(new JLabel("Ro = "));
        rPanel.add(rField);
        return rPanel;
    }

    private JPanel addA() {
        JPanel aPanel = new JPanel();
        aPanel.setLayout(new GridLayout(0, 3));
        aPanel.add(new JLabel("A = "));
        aPanel.add(aField);
        return aPanel;
    }

    private JPanel addMinDegree() {
        JPanel minDegreePanel = new JPanel();
        minDegreePanel.setLayout(new GridLayout(0, 3));
        minDegreePanel.add(new JLabel("Min degree = "));
        minDegreePanel.add(minDegreeField);
        return minDegreePanel;
    }

    private JPanel addMaxDegree() {
        JPanel maxDegreePanel = new JPanel();
        maxDegreePanel.setLayout(new GridLayout(0, 3));
        maxDegreePanel.add(new JLabel("Max degree = "));
        maxDegreePanel.add(maxDegreeField);
        return maxDegreePanel;
    }

    private JPanel addStep() {
        JPanel stepPanel = new JPanel();
        stepPanel.setLayout(new GridLayout(0, 3));
        stepPanel.add(new JLabel("Step = "));
        stepPanel.add(stepField);
        return stepPanel;
    }


}
