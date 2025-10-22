// Simple Swing-based bike shop UI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BikeShopGUI {
    private JFrame frame;
    private JTextArea textArea;

    public BikeShopGUI() {
        frame = new JFrame("Bike Shop");
        textArea = new JTextArea();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add functionality
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Edit functionality
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Remove functionality
            }
        });

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(editButton);
        panel.add(removeButton);

        frame.add(panel, BorderLayout.SOUTH);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BikeShopGUI();
            }
        });
    }
}