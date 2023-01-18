package com.uptbal.calculator.view;

import com.uptbal.calculator.controller.*;
import java.lang.NullPointerException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Cursor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;


public class Window extends JFrame {

    JTextField screen;
    JPanel panelNumbers, panelOperations;
    String ands;

    public Window() {
        super();
        setSize(350, 300);
        setTitle("Calculator IML");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("./assets/logo.png").getImage());

        var panel = (JPanel) this.getContentPane();
        panel.setLayout(new BorderLayout());

        screen = new JTextField("", 20);
        screen.setBorder(new EmptyBorder(4, 4, 4, 4));
        screen.setFont(new Font("Arial", Font.BOLD, 25));
        screen.setHorizontalAlignment(JTextField.RIGHT);
        screen.setBackground(new Color(210, 210, 210));

        screen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    evalText();
                }
            }
        });
        panel.add("North", screen);
        
        var panelCentral = new JPanel();
        panel.add("Center", panelCentral);
        
        panelCentral.setLayout(new GridLayout(1, 2));
        panelCentral.setBorder(new EmptyBorder(4, 4, 4, 4));

        panelNumbers = new JPanel();
        panelNumbers.setLayout(new GridLayout(4, 3));
        panelNumbers.setBorder(new EmptyBorder(4, 4, 4, 5));

        for (int n = 9; n >= 1; n-=3) {
            newNumericButton("" + (n - 2));
            newNumericButton("" + (n - 1));
            newNumericButton("" + n);
        }
        
        newNumericButton("0");
        newNumericButton(".");
        newNumericButton("EXP");
        
        panelCentral.add(panelNumbers);

        panelOperations = new JPanel();
        panelOperations.setLayout(new GridLayout(4, 2));
        panelOperations.setBorder(new EmptyBorder(4, 20, 4, 4));

        String[] operators = {"DEL", "AC", "*", "/", "+", "-", "^", "="};
        
        for (int i = 0; i < 8; i++) {
            newOperationButton(operators[i]);
        }
        
        panelCentral.add(panelOperations);

        validate();
    }

    public static void main(String[] args) {
        var ventana = new Window();
        ventana.setVisible(true);
    }

    private void onClickButton(MouseEvent evt) {
        var btn = (JButton) evt.getSource();
        var text = btn.getText();
        if (text == "EXP") text = "E";

        setScreenText(text);
    }

    private void newNumericButton(String digito) {
        JButton btn = new JButton();
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(200, 200, 200));
        btn.setText(digito);

        btn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent evt) {
                onClickButton(evt);
            }
        });

        panelNumbers.add(btn);
    }
    
    private void setScreenText(String text) {
        var carrilPosici  = screen.getCaretPosition();
        var screenText = screen.getText();
        var lenText = screenText.length();

        // Remove character at carril posicition
        var end = carrilPosici -1 > 0 ? carrilPosici -1 : 0;      
        screen.setText(
            screenText.substring(0, !text.isEmpty() ? carrilPosici : end)
            + text
            + screenText.substring(carrilPosici, lenText)
        );

        screen.requestFocus();
        screen.setCaretPosition(!text.isEmpty() ? carrilPosici + 1 : end);
    }
    
    private void newOperationButton(String operator) {
        JButton btn = new JButton(operator);
        
        btn.setForeground(new Color(100, 40, 44));
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(200, 200, 200));
        btn.setHorizontalAlignment(JButton.CENTER);
            
        if (operator == "DEL" || operator == "AC") {
            btn.setForeground(new Color(200, 200, 200));
            btn.setBackground(new Color(210, 35, 10));
        }
        
        btn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent evt) {
                JButton btn = (JButton) evt.getSource();

                switch (btn.getText()) {
                    case "=": evalText();
                        break;
                    case "DEL": setScreenText("");
                        return;
                    case "AC": screen.setText("");
                        break;
                    default: onClickButton(evt);
                        return;
                }
                
                screen.requestFocus();
                screen.setCaretPosition(0);
            }
        });

        panelOperations.add(btn);
    }

    // JavaC not compile "model" package totally if this method is private
    public void evalText() {
        if (screen.getText().isEmpty()) return;
        
        Evaluator evaluator = new Evaluator(screen.getText());

        try {
            screen.setText(evaluator.evaluate().toString());
        } catch (NullPointerException e) {
            screen.setText("Error");
        }
    }

}
