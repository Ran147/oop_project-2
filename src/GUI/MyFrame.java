package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame {

    public MyFrame() {
        // Set the title of the frame
        setTitle("My First JFrame");

        // Set the size of the frame
        setSize(300, 200);

        // Set the layout manager of the frame
        setLayout(new FlowLayout());

        // Create a button
        JButton button = new JButton("Click Me!");

        // Add an action listener to the button
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MyFrame.this, "Button Clicked!");
            }
        });

        // Add the button to the frame
        add(button);

        // Set the default close operation of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the frame visible
        setVisible(true);
    }
}
