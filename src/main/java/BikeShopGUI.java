import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple example GUI for a bike shop.
 * - Shows a table of bikes (id, model, price)
 * - Add / Remove buttons with simple dialogs
 *
 * Feel free to replace or extend this with your full implementation.
 */
public class BikeShopGUI extends JFrame {
    private BikeTableModel tableModel;
    private JTable table;

    public BikeShopGUI() {
        super("Bike Shop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        tableModel = new BikeTableModel();
        table = new JTable(tableModel);

        // sample data
        tableModel.addBike(new Bike(1, "Roadster 2000", 799.99));
        tableModel.addBike(new Bike(2, "City Cruiser", 349.50));
        tableModel.addBike(new Bike(3, "Mountain Pro", 1299.00));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton removeBtn = new JButton("Remove");
        JButton editBtn = new JButton("Edit");

        addBtn.addActionListener(e -> onAdd());
        removeBtn.addActionListener(e -> onRemove());
        editBtn.addActionListener(e -> onEdit());

        controls.add(addBtn);
        controls.add(editBtn);
        controls.add(removeBtn);

        add(controls, BorderLayout.SOUTH);
    }

    private void onAdd() {
        BikeForm form = new BikeForm(this, "Add Bike", null);
        Bike b = form.showDialog();
        if (b != null) tableModel.addBike(b);
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a bike to edit.", "No selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Bike existing = tableModel.getBikeAt(row);
        BikeForm form = new BikeForm(this, "Edit Bike", existing);
        Bike updated = form.showDialog();
        if (updated != null) {
            tableModel.updateBike(row, updated);
        }
    }

    private void onRemove() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a bike to remove.", "No selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Remove selected bike?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeBike(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BikeShopGUI gui = new BikeShopGUI();
            gui.setVisible(true);
        });
    }

    // --- Model classes ---

    static class Bike {
        private int id;
        private String model;
        private double price;

        public Bike(int id, String model, double price) {
            this.id = id;
            this.model = model;
            this.price = price;
        }

        public int getId() { return id; }
        public String getModel() { return model; }
        public double getPrice() { return price; }

        public void setId(int id) { this.id = id; }
        public void setModel(String model) { this.model = model; }
        public void setPrice(double price) { this.price = price; }
    }

    static class BikeTableModel extends AbstractTableModel {
        private final String[] cols = {"ID", "Model", "Price"};
        private final List<Bike> bikes = new ArrayList<>();

        public void addBike(Bike b) {
            bikes.add(b);
            fireTableRowsInserted(bikes.size()-1, bikes.size()-1);
        }

        public Bike getBikeAt(int row) { return bikes.get(row); }

        public void updateBike(int row, Bike b) {
            bikes.set(row, b);
            fireTableRowsUpdated(row, row);
        }

        public void removeBike(int row) {
            bikes.remove(row);
            fireTableRowsDeleted(row, row);
        }

        @Override
        public int getRowCount() { return bikes.size(); }

        @Override
        public int getColumnCount() { return cols.length; }

        @Override
        public String getColumnName(int column) { return cols[column]; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Bike b = bikes.get(rowIndex);
            switch (columnIndex) {
                case 0: return b.getId();
                case 1: return b.getModel();
                case 2: return String.format("%.2f", b.getPrice());
                default: return "";
            }
        }
    }

    // Simple dialog to add/edit bikes
    static class BikeForm extends JDialog {
        private final JTextField idField = new JTextField(10);
        private final JTextField modelField = new JTextField(20);
        private final JTextField priceField = new JTextField(10);
        private Bike result = null;

        public BikeForm(Frame owner, String title, Bike existing) {
            super(owner, title, true);
            setLayout(new BorderLayout());
            JPanel form = new JPanel(new GridLayout(3,2,5,5));
            form.add(new JLabel("ID:"));
            form.add(idField);
            form.add(new JLabel("Model:"));
            form.add(modelField);
            form.add(new JLabel("Price:"));
            form.add(priceField);

            if (existing != null) {
                idField.setText(String.valueOf(existing.getId()));
                modelField.setText(existing.getModel());
                priceField.setText(String.valueOf(existing.getPrice()));
            }

            add(form, BorderLayout.CENTER);

            JPanel buttons = new JPanel();
            JButton ok = new JButton("OK");
            JButton cancel = new JButton("Cancel");
            ok.addActionListener(e -> onOk());
            cancel.addActionListener(e -> onCancel());
            buttons.add(ok);
            buttons.add(cancel);
            add(buttons, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(owner);
        }

        private void onOk() {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String model = modelField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                if (model.isEmpty()) throw new IllegalArgumentException("Model required");
                result = new Bike(id, model, price);
                setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void onCancel() {
            result = null;
            setVisible(false);
        }

        public Bike showDialog() {
            setVisible(true);
            return result;
        }
    }