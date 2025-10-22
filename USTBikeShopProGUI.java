import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class USTBikeShopProGUI {
    // Constants for bike limits
    private static final int MAX_TOTAL_BIKES = 500;
    private static final int MAX_BIKES_PER_TYPE = 100;
    
    // Bike details structure
    class BikeDetails {
        // Common fields
        String name;
        String type;
        String description;
        double price;
        int quantity;
        String stockID;
        int maxSpeed;
        int numberOfGears;
        int numberOfWheels;
        String paintColor;
        String safetyFeatures;
        
        // Basic/Road fields
        boolean flatHandlebars;
        
        // Mountain bike fields
        int seatHeight; // in cm
        boolean fullSuspension;
        boolean flatProofTires;
        
        // Electric bike fields
        String batteryType;
        String batterySize;
        String batteryVoltage;
        String range;
        String motorPower;
        boolean gpsTrackingEnabled;
        
        // Constructor for Basic/Road bikes
        BikeDetails(String name, String type, String description, double price, 
                   int quantity, String stockID, int maxSpeed, int numberOfGears,
                   int numberOfWheels, String paintColor, String safetyFeatures,
                   boolean flatHandlebars) {
            this.name = name;
            this.type = type;
            this.description = description;
            this.price = price;
            this.quantity = quantity;
            this.stockID = stockID;
            this.maxSpeed = maxSpeed;
            this.numberOfGears = numberOfGears;
            this.numberOfWheels = numberOfWheels;
            this.paintColor = paintColor;
            this.safetyFeatures = safetyFeatures;
            this.flatHandlebars = flatHandlebars;
        }
        
        // Constructor for Mountain bikes
        BikeDetails(String name, String type, String description, double price, 
                   int quantity, String stockID, int maxSpeed, int numberOfGears,
                   int numberOfWheels, String paintColor, String safetyFeatures,
                   int seatHeight, boolean fullSuspension, boolean flatProofTires) {
            this(name, type, description, price, quantity, stockID, maxSpeed, 
                numberOfGears, numberOfWheels, paintColor, safetyFeatures, false);
            this.seatHeight = seatHeight;
            this.fullSuspension = fullSuspension;
            this.flatProofTires = flatProofTires;
        }
        
        // Constructor for Electric/RoadElectric bikes
        BikeDetails(String name, String type, String description, double price, 
                   int quantity, String stockID, int maxSpeed, int numberOfGears,
                   int numberOfWheels, String paintColor, String safetyFeatures,
                   boolean flatHandlebars, String batteryType, String batterySize,
                   String batteryVoltage, String range, String motorPower, 
                   boolean gpsTrackingEnabled) {
            this(name, type, description, price, quantity, stockID, maxSpeed, 
                numberOfGears, numberOfWheels, paintColor, safetyFeatures, flatHandlebars);
            this.batteryType = batteryType;
            this.batterySize = batterySize;
            this.batteryVoltage = batteryVoltage;
            this.range = range;
            this.motorPower = motorPower;
            this.gpsTrackingEnabled = gpsTrackingEnabled;
        }
    }

    // Inventory and UI components
    private Map<String, BikeDetails[]> bikeInventory = new HashMap<>();
    private int currentBikeIndex = 0;
    private String currentCategory = "";
    
    private JTextArea textArea;
    private JLabel nameField;
    private JLabel imageLabel;
    private JLabel detailsLabel;
    private JButton buttonSave, buttonLoad, buttonPrevious, buttonNext, buttonFirst, buttonLast;
    private JButton buttonBAB, buttonMTB, buttonRDB, buttonECB, buttonREB;
    private JButton buttonSaveBike, buttonExit, buttonEdit, buttonAdd, buttonDelete;
    private JPanel mainPanel, northPanel, southPanel, eastPanel, westPanel;
    private JPanel centerPanel, centerPanelRow1, centerPanelRow2;
    private JFrame jF;

    public USTBikeShopProGUI() {
        initializeInventory();
        createGUIComponents();
        setupLayout();
        addEventListeners();
    }

    private void initializeInventory() {
        // Basic Bikes
        bikeInventory.put("Basic", new BikeDetails[]{
            new BikeDetails("Basic Bike A", "Basic", "Comfortable city bike", 
                199.99, 5, "BSC_001", 25, 7, 2, "Red", "reflectors", false),
            new BikeDetails("Basic Bike B", "Basic", "Standard hybrid bike", 
                249.99, 3, "BSC_002", 30, 21, 2, "Blue", "reflectors, bell", true)
        });

        // Mountain Bikes
        bikeInventory.put("Mountain", new BikeDetails[]{
            new BikeDetails("MTB X", "Mountain", "Professional mountain bike", 
                899.99, 4, "MTB_001", 30, 27, 2, "Black", "reflectors, bell",
                75, true, true),
            new BikeDetails("MTB Y", "Mountain", "Trail mountain bike", 
                649.99, 2, "MTB_002", 25, 24, 2, "Green", "reflectors",
                70, false, true)
        });

        // Road Bikes
        bikeInventory.put("Road", new BikeDetails[]{
            new BikeDetails("Road Bike 1", "Road", "Performance road bike", 
                1299.99, 3, "RDB_001", 35, 22, 2, "White", "reflectors", false),
            new BikeDetails("Road Bike 2", "Road", "Endurance road bike", 
                999.99, 4, "RDB_002", 30, 18, 2, "Silver", "reflectors, bell", true)
        });

        // Electric Bikes
        bikeInventory.put("Electric", new BikeDetails[]{
            new BikeDetails("E-Bike 7", "Electric", "City e-bike", 
                1999.99, 2, "ELC_001", 28, 7, 2, "Black", "lights, reflectors",
                true, "Lithium-ion", "10Ah", "36V", "40 miles", "250W", true),
            new BikeDetails("E-Bike 8", "Electric", "Mountain e-bike", 
                2499.99, 1, "ELC_002", 25, 9, 2, "Blue", "lights, reflectors, bell",
                false, "Lithium-ion", "14Ah", "48V", "50 miles", "500W", false)
        });

        // Road Electric Bikes
        bikeInventory.put("RoadElectric", new BikeDetails[]{
            new BikeDetails("Road E-Bike Z", "RoadElectric", "High-performance e-road bike", 
                3499.99, 1, "REL_001", 28, 11, 2, "Red", "LED lights, reflectors",
                true, "Lithium-ion", "12Ah", "36V", "60 miles", "350W", true)
        });
    }

    private void createGUIComponents() {
        textArea = new JTextArea(15, 40);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setText("Welcome to the UST Bike Shop Pro GUI!\n\nSelect a bike category to begin.");
        
        nameField = new JLabel("Hello, BikeLord");
        nameField.setFont(new Font("Arial", Font.BOLD, 16));

        buttonFirst = new JButton("<<First");
        buttonLast = new JButton("Last>>");
        buttonPrevious = new JButton("<Previous");
        buttonNext = new JButton("Next>");
        buttonLoad = new JButton("Load Data");
        buttonSave = new JButton("Save Data");
        buttonSaveBike = new JButton("Save Bike");
        buttonExit = new JButton("Exit");
        buttonEdit = new JButton("Edit Bike");
        buttonAdd = new JButton("Add Bike");
        buttonDelete = new JButton("Delete Bike");

        buttonBAB = new JButton("Basic Bikes");
        buttonMTB = new JButton("Mountain Bikes");
        buttonRDB = new JButton("Road Bikes");
        buttonECB = new JButton("E-Bikes");
        buttonREB = new JButton("Road E-Bikes");

        imageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (currentCategory != null && !currentCategory.isEmpty()) {
                    drawBike(g2d, getWidth(), getHeight());
                }
            }
        };
        imageLabel.setPreferredSize(new Dimension(300, 200));
        imageLabel.setBorder(BorderFactory.createTitledBorder("Bike Image"));

        detailsLabel = new JLabel("Select a bike to view details");
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        detailsLabel.setBorder(BorderFactory.createTitledBorder("Bike Details"));
    }

    private void drawBike(Graphics2D g2d, int width, int height) {
        // Set bike color based on current bike's paintColor
        BikeDetails currentBike = bikeInventory.get(currentCategory)[currentBikeIndex];
        Color bikeColor;
        try {
            bikeColor = (Color)Color.class.getField(currentBike.paintColor.toLowerCase()).get(null);
        } catch (Exception e) {
            bikeColor = Color.BLUE; // default color
        }
        
        // Scale factors for the bike drawing
        double scale = Math.min(width, height) / 300.0;
        g2d.scale(scale, scale);
        
        // Bike frame points (common for all bike types)
        int[] frameX = {50, 100, 150, 100};
        int[] frameY = {150, 100, 150, 200};
        
        // Wheels
        g2d.setColor(Color.BLACK);
        g2d.fillOval(30, 130, 40, 40);  // Rear wheel
        g2d.fillOval(130, 130, 40, 40); // Front wheel
        
        // Frame
        g2d.setColor(bikeColor);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawPolyline(frameX, frameY, 4);
        g2d.drawLine(100, 100, 150, 100); // Top tube
        g2d.drawLine(100, 200, 150, 150); // Seat stay
        
        // Handlebar and stem
        if (currentBike.flatHandlebars) {
            // Flat handlebars
            g2d.drawLine(150, 100, 180, 100);
            g2d.drawLine(180, 100, 180, 90);
            g2d.drawLine(180, 100, 180, 110);
        } else {
            // Drop handlebars
            g2d.drawLine(150, 100, 180, 100);
            g2d.drawLine(180, 100, 175, 80);
            g2d.drawLine(180, 100, 185, 80);
        }
        
        // Saddle
        g2d.setColor(Color.BLACK);
        g2d.fillOval(45, 145, 15, 10);
        
        // Type-specific features
        switch(currentCategory) {
            case "Mountain":
                // Suspension
                if (currentBike.fullSuspension) {
                    g2d.setColor(Color.GRAY);
                    // Rear suspension
                    g2d.drawLine(70, 150, 90, 170);
                    g2d.drawLine(90, 170, 110, 170);
                    // Front suspension
                    g2d.drawLine(150, 100, 150, 130);
                    g2d.drawLine(140, 130, 160, 130);
                }
                
                // Knobby tires
                g2d.setColor(Color.DARK_GRAY);
                for (int i = 0; i < 8; i++) {
                    double angle = i * Math.PI / 4;
                    int x1 = (int)(50 + 20 * Math.cos(angle));
                    int y1 = (int)(150 + 20 * Math.sin(angle));
                    int x2 = (int)(50 + 25 * Math.cos(angle));
                    int y2 = (int)(150 + 25 * Math.sin(angle));
                    g2d.drawLine(x1, y1, x2, y2);
                    
                    x1 = (int)(150 + 20 * Math.cos(angle));
                    y1 = (int)(150 + 20 * Math.sin(angle));
                    x2 = (int)(150 + 25 * Math.cos(angle));
                    y2 = (int)(150 + 25 * Math.sin(angle));
                    g2d.drawLine(x1, y1, x2, y2);
                }
                break;
                
            case "Road":
                // Thin tires
                g2d.setColor(Color.BLACK);
                g2d.fillOval(35, 135, 30, 30);
                g2d.fillOval(135, 135, 30, 30);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(38, 138, 24, 24);
                g2d.fillOval(138, 138, 24, 24);
                break;
                
            case "Electric":
            case "RoadElectric":
                // Battery and motor
                g2d.setColor(Color.GRAY);
                g2d.fillRect(70, 120, 30, 20); // Battery
                g2d.fillRect(110, 140, 15, 10); // Motor
                
                // Wires
                g2d.setColor(Color.RED);
                g2d.drawLine(100, 130, 110, 140);
                g2d.setColor(Color.BLACK);
                g2d.drawLine(110, 145, 130, 145);
                
                if (currentBike.gpsTrackingEnabled) {
                    // GPS device
                    g2d.setColor(Color.GREEN);
                    g2d.fillOval(60, 110, 10, 10);
                }
                break;
        }
        
        // Chain and gears
        g2d.setColor(Color.BLACK);
        g2d.fillOval(90, 140, 20, 20); // Rear gear
        g2d.fillOval(140, 140, 20, 20); // Front gear
        
        // Chain
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4;
            int x = (int)(100 + 40 * Math.cos(angle));
            int y = (int)(150 + 40 * Math.sin(angle));
            g2d.fillOval(x-2, y-2, 4, 4);
        }
        
        // Pedals
        g2d.setColor(Color.BLACK);
        g2d.fillOval(140, 140, 10, 10);
        g2d.drawLine(145, 145, 155, 155);
        g2d.drawLine(145, 155, 155, 145);
    }

    private void setupLayout() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        northPanel = new JPanel();
        southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        eastPanel = new JPanel();
        westPanel = new JPanel();
        centerPanel = new JPanel(new BorderLayout());
        centerPanelRow1 = new JPanel();
        centerPanelRow2 = new JPanel();

        // North Panel
        northPanel.add(nameField);

        // West Panel (Category Buttons)
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.add(buttonBAB);
        westPanel.add(buttonMTB);
        westPanel.add(buttonRDB);
        westPanel.add(buttonECB);
        westPanel.add(buttonREB);

        // Center Panel (Image and Details)
        JScrollPane textScroll = new JScrollPane(textArea);
        centerPanelRow1.add(imageLabel);
        centerPanelRow2.add(detailsLabel);
        centerPanel.add(centerPanelRow1, BorderLayout.NORTH);
        centerPanel.add(textScroll, BorderLayout.CENTER);
        centerPanel.add(centerPanelRow2, BorderLayout.SOUTH);

        // East Panel (Navigation Buttons)
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.add(buttonFirst);
        eastPanel.add(Box.createVerticalStrut(10));
        eastPanel.add(buttonPrevious);
        eastPanel.add(Box.createVerticalStrut(10));
        eastPanel.add(buttonNext);
        eastPanel.add(Box.createVerticalStrut(10));
        eastPanel.add(buttonLast);

        // South Panel (Utility Buttons)
        southPanel.add(buttonSave);
        southPanel.add(buttonLoad);
        southPanel.add(buttonAdd);
        southPanel.add(buttonEdit);
        southPanel.add(buttonDelete);
        southPanel.add(buttonSaveBike);
        southPanel.add(buttonExit);

        // Assemble Main Panel
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(westPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(eastPanel, BorderLayout.EAST);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void addEventListeners() {
        buttonBAB.addActionListener(e -> showCategory("Basic"));
        buttonMTB.addActionListener(e -> showCategory("Mountain"));
        buttonRDB.addActionListener(e -> showCategory("Road"));
        buttonECB.addActionListener(e -> showCategory("Electric"));
        buttonREB.addActionListener(e -> showCategory("RoadElectric"));

        buttonFirst.addActionListener(e -> showBike(0));
        buttonLast.addActionListener(e -> {
            if (!currentCategory.isEmpty()) {
                showBike(bikeInventory.get(currentCategory).length - 1);
            }
        });
        buttonPrevious.addActionListener(e -> {
            if (!currentCategory.isEmpty()) {
                showBike(Math.max(0, currentBikeIndex - 1));
            }
        });
        buttonNext.addActionListener(e -> {
            if (!currentCategory.isEmpty()) {
                int maxIndex = bikeInventory.get(currentCategory).length - 1;
                showBike(Math.min(maxIndex, currentBikeIndex + 1));
            }
        });

        buttonEdit.addActionListener(e -> {
            if (!currentCategory.isEmpty()) {
                showEditDialog(bikeInventory.get(currentCategory)[currentBikeIndex]);
            }
        });

        buttonAdd.addActionListener(e -> showAddBikeDialog());
        
        buttonDelete.addActionListener(e -> {
            if (!currentCategory.isEmpty()) {
                deleteCurrentBike();
            }
        });

        buttonSaveBike.addActionListener(e -> {
            JOptionPane.showMessageDialog(jF, "✅ Bike details saved successfully!", "Save Bike", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(jF, "Are you sure you want to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                jF.dispose();
            }
        });
    }

    private void showCategory(String category) {
        currentCategory = category;
        currentBikeIndex = 0;
        updateInventoryDisplay();
        showBike(0);
    }

    private void showBike(int index) {
        if (currentCategory.isEmpty()) return;
        
        BikeDetails[] bikes = bikeInventory.get(currentCategory);
        if (bikes == null || index < 0 || index >= bikes.length) return;
        
        currentBikeIndex = index;
        updateBikeDisplay();
    }

    private void updateBikeDisplay() {
        BikeDetails bike = bikeInventory.get(currentCategory)[currentBikeIndex];
        
        String details;
        switch(bike.type) {
            case "Basic":
                details = String.format(
                    "<html><b>Name:</b> %s<br><b>Type:</b> %s<br>" +
                    "<b>Description:</b> %s<br><b>Price:</b> $%.2f<br>" +
                    "<b>In Stock:</b> %d<br><b>Stock ID:</b> %s<br>" +
                    "<b>Max Speed:</b> %d mph<br><b>Gears:</b> %d-speed<br>" +
                    "<b>Wheels:</b> %d<br><b>Color:</b> %s<br>" +
                    "<b>Safety Features:</b> %s<br>" +
                    "<b>Flat Handlebars:</b> %s</html>",
                    bike.name, bike.type, bike.description, bike.price, bike.quantity,
                    bike.stockID, bike.maxSpeed, bike.numberOfGears, bike.numberOfWheels,
                    bike.paintColor, bike.safetyFeatures,
                    bike.flatHandlebars ? "Yes" : "No"
                );
                break;
                
            case "Mountain":
                details = String.format(
                    "<html><b>Name:</b> %s<br><b>Type:</b> %s<br>" +
                    "<b>Description:</b> %s<br><b>Price:</b> $%.2f<br>" +
                    "<b>In Stock:</b> %d<br><b>Stock ID:</b> %s<br>" +
                    "<b>Max Speed:</b> %d mph<br><b>Gears:</b> %d-speed<br>" +
                    "<b>Wheels:</b> %d<br><b>Color:</b> %s<br>" +
                    "<b>Safety Features:</b> %s<br>" +
                    "<b>Seat Height:</b> %d cm<br>" +
                    "<b>Full Suspension:</b> %s<br>" +
                    "<b>Flat Proof Tires:</b> %s</html>",
                    bike.name, bike.type, bike.description, bike.price, bike.quantity,
                    bike.stockID, bike.maxSpeed, bike.numberOfGears, bike.numberOfWheels,
                    bike.paintColor, bike.safetyFeatures, bike.seatHeight,
                    bike.fullSuspension ? "Yes" : "No",
                    bike.flatProofTires ? "Yes" : "No"
                );
                break;
                
            case "Road":
                details = String.format(
                    "<html><b>Name:</b> %s<br><b>Type:</b> %s<br>" +
                    "<b>Description:</b> %s<br><b>Price:</b> $%.2f<br>" +
                    "<b>In Stock:</b> %d<br><b>Stock ID:</b> %s<br>" +
                    "<b>Max Speed:</b> %d mph<br><b>Gears:</b> %d-speed<br>" +
                    "<b>Wheels:</b> %d<br><b>Color:</b> %s<br>" +
                    "<b>Safety Features:</b> %s<br>" +
                    "<b>Flat Handlebars:</b> %s</html>",
                    bike.name, bike.type, bike.description, bike.price, bike.quantity,
                    bike.stockID, bike.maxSpeed, bike.numberOfGears, bike.numberOfWheels,
                    bike.paintColor, bike.safetyFeatures,
                    bike.flatHandlebars ? "Yes" : "No"
                );
                break;
                
            case "Electric":
            case "RoadElectric":
                details = String.format(
                    "<html><b>Name:</b> %s<br><b>Type:</b> %s<br>" +
                    "<b>Description:</b> %s<br><b>Price:</b> $%.2f<br>" +
                    "<b>In Stock:</b> %d<br><b>Stock ID:</b> %s<br>" +
                    "<b>Max Speed:</b> %d mph<br><b>Gears:</b> %d-speed<br>" +
                    "<b>Wheels:</b> %d<br><b>Color:</b> %s<br>" +
                    "<b>Safety Features:</b> %s<br>" +
                    "<b>Flat Handlebars:</b> %s<br>" +
                    "<b>Battery Type:</b> %s<br><b>Battery Size:</b> %s<br>" +
                    "<b>Battery Voltage:</b> %s<br><b>Range:</b> %s miles<br>" +
                    "<b>Motor Power:</b> %s<br><b>GPS Tracking:</b> %s</html>",
                    bike.name, bike.type, bike.description, bike.price, bike.quantity,
                    bike.stockID, bike.maxSpeed, bike.numberOfGears, bike.numberOfWheels,
                    bike.paintColor, bike.safetyFeatures,
                    bike.flatHandlebars ? "Yes" : "No",
                    bike.batteryType, bike.batterySize, bike.batteryVoltage,
                    bike.range, bike.motorPower,
                    bike.gpsTrackingEnabled ? "Enabled" : "Disabled"
                );
                break;
                
            default:
                details = "Select a bike to view details";
        }
        
        detailsLabel.setText(details);
        imageLabel.repaint();
        updateInventoryDisplay();
    }

    private void updateInventoryDisplay() {
        if (currentCategory.isEmpty()) return;
        
        BikeDetails[] bikes = bikeInventory.get(currentCategory);
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("=== %s Bikes Inventory ===\n\n", currentCategory));
        sb.append(String.format("Showing %d of %d bikes\n\n", currentBikeIndex + 1, bikes.length));
        
        for (int i = 0; i < bikes.length; i++) {
            BikeDetails bike = bikes[i];
            String indicator = (i == currentBikeIndex) ? ">> " : "   ";
            sb.append(String.format("%s%d. %-20s $%-8.2f (Qty: %d)\n", 
                indicator, i+1, bike.name, bike.price, bike.quantity));
        }
        
        textArea.setText(sb.toString());
    }

    private void showEditDialog(BikeDetails bike) {
        JDialog editDialog = new JDialog(jF, "Edit Bike: " + bike.name, true);
        editDialog.setLayout(new GridLayout(0, 2, 10, 10));
        
        // Common fields for all bikes
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(bike.price, 0, 10000, 0.01));
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(bike.quantity, 0, 100, 1));
        JTextField colorField = new JTextField(bike.paintColor);
        JTextField safetyField = new JTextField(bike.safetyFeatures);
        
        // Add common fields
        editDialog.add(new JLabel("Price:"));
        editDialog.add(priceSpinner);
        editDialog.add(new JLabel("Quantity:"));
        editDialog.add(quantitySpinner);
        editDialog.add(new JLabel("Color:"));
        editDialog.add(colorField);
        editDialog.add(new JLabel("Safety Features:"));
        editDialog.add(safetyField);
        
        // Type-specific fields
        switch(bike.type) {
            case "Basic":
            case "Road":
                JCheckBox flatHandlebarsCheck = new JCheckBox("", bike.flatHandlebars);
                editDialog.add(new JLabel("Flat Handlebars:"));
                editDialog.add(flatHandlebarsCheck);
                break;
                
            case "Mountain":
                JSpinner seatHeightSpinner = new JSpinner(new SpinnerNumberModel(bike.seatHeight, 50, 100, 1));
                JCheckBox suspensionCheck = new JCheckBox("", bike.fullSuspension);
                JCheckBox flatProofCheck = new JCheckBox("", bike.flatProofTires);
                
                editDialog.add(new JLabel("Seat Height (cm):"));
                editDialog.add(seatHeightSpinner);
                editDialog.add(new JLabel("Full Suspension:"));
                editDialog.add(suspensionCheck);
                editDialog.add(new JLabel("Flat Proof Tires:"));
                editDialog.add(flatProofCheck);
                break;
                
            case "Electric":
            case "RoadElectric":
                JCheckBox eFlatHandlebarsCheck = new JCheckBox("", bike.flatHandlebars);
                JTextField batteryTypeField = new JTextField(bike.batteryType);
                JTextField batterySizeField = new JTextField(bike.batterySize);
                JTextField batteryVoltageField = new JTextField(bike.batteryVoltage);
                JTextField rangeField = new JTextField(bike.range);
                JTextField motorPowerField = new JTextField(bike.motorPower);
                JCheckBox gpsCheck = new JCheckBox("", bike.gpsTrackingEnabled);
                
                editDialog.add(new JLabel("Flat Handlebars:"));
                editDialog.add(eFlatHandlebarsCheck);
                editDialog.add(new JLabel("Battery Type:"));
                editDialog.add(batteryTypeField);
                editDialog.add(new JLabel("Battery Size:"));
                editDialog.add(batterySizeField);
                editDialog.add(new JLabel("Battery Voltage:"));
                editDialog.add(batteryVoltageField);
                editDialog.add(new JLabel("Range (miles):"));
                editDialog.add(rangeField);
                editDialog.add(new JLabel("Motor Power:"));
                editDialog.add(motorPowerField);
                editDialog.add(new JLabel("GPS Tracking:"));
                editDialog.add(gpsCheck);
                break;
        }
        
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            bike.price = (Double)priceSpinner.getValue();
            bike.quantity = (Integer)quantitySpinner.getValue();
            bike.paintColor = colorField.getText();
            bike.safetyFeatures = safetyField.getText();
            
            switch(bike.type) {
                case "Basic":
                case "Road":
                    bike.flatHandlebars = ((JCheckBox)editDialog.getContentPane().getComponent(9)).isSelected();
                    break;
                    
                case "Mountain":
                    bike.seatHeight = (Integer)((JSpinner)editDialog.getContentPane().getComponent(8)).getValue();
                    bike.fullSuspension = ((JCheckBox)editDialog.getContentPane().getComponent(10)).isSelected();
                    bike.flatProofTires = ((JCheckBox)editDialog.getContentPane().getComponent(12)).isSelected();
                    break;
                    
                case "Electric":
                case "RoadElectric":
                    bike.flatHandlebars = ((JCheckBox)editDialog.getContentPane().getComponent(8)).isSelected();
                    bike.batteryType = ((JTextField)editDialog.getContentPane().getComponent(10)).getText();
                    bike.batterySize = ((JTextField)editDialog.getContentPane().getComponent(12)).getText();
                    bike.batteryVoltage = ((JTextField)editDialog.getContentPane().getComponent(14)).getText();
                    bike.range = ((JTextField)editDialog.getContentPane().getComponent(16)).getText();
                    bike.motorPower = ((JTextField)editDialog.getContentPane().getComponent(18)).getText();
                    bike.gpsTrackingEnabled = ((JCheckBox)editDialog.getContentPane().getComponent(20)).isSelected();
                    break;
            }
            
            updateBikeDisplay();
            editDialog.dispose();
        });
        
        editDialog.add(saveButton);
        editDialog.pack();
        editDialog.setLocationRelativeTo(jF);
        editDialog.setVisible(true);
    }

    private void showAddBikeDialog() {
        if (currentCategory.isEmpty()) {
            JOptionPane.showMessageDialog(jF, "Please select a bike category first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if we've reached the maximum bikes per type
        if (bikeInventory.get(currentCategory).length >= MAX_BIKES_PER_TYPE) {
            JOptionPane.showMessageDialog(jF, 
                String.format("Cannot add more bikes to this category. Maximum of %d bikes per type reached.", MAX_BIKES_PER_TYPE),
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if we've reached the total maximum bikes
        int totalBikes = 0;
        for (BikeDetails[] bikes : bikeInventory.values()) {
            totalBikes += bikes.length;
        }
        if (totalBikes >= MAX_TOTAL_BIKES) {
            JOptionPane.showMessageDialog(jF, 
                String.format("Cannot add more bikes. Maximum of %d total bikes reached.", MAX_TOTAL_BIKES),
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JDialog addDialog = new JDialog(jF, "Add New Bike to " + currentCategory, true);
        addDialog.setLayout(new GridLayout(0, 2, 10, 10));
        
        // Common fields for all bikes
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0, 10000, 0.01));
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JTextField stockIDField = new JTextField();
        JSpinner speedSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JSpinner gearsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 30, 1));
        JSpinner wheelsSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 3, 1));
        JTextField colorField = new JTextField();
        JTextField safetyField = new JTextField();
        
        // Add common fields
        addDialog.add(new JLabel("Name:"));
        addDialog.add(nameField);
        addDialog.add(new JLabel("Description:"));
        addDialog.add(descField);
        addDialog.add(new JLabel("Price:"));
        addDialog.add(priceSpinner);
        addDialog.add(new JLabel("Quantity:"));
        addDialog.add(quantitySpinner);
        addDialog.add(new JLabel("Stock ID:"));
        addDialog.add(stockIDField);
        addDialog.add(new JLabel("Max Speed (mph):"));
        addDialog.add(speedSpinner);
        addDialog.add(new JLabel("Number of Gears:"));
        addDialog.add(gearsSpinner);
        addDialog.add(new JLabel("Number of Wheels:"));
        addDialog.add(wheelsSpinner);
        addDialog.add(new JLabel("Color:"));
        addDialog.add(colorField);
        addDialog.add(new JLabel("Safety Features:"));
        addDialog.add(safetyField);
        
        // Type-specific fields
        switch(currentCategory) {
            case "Basic":
            case "Road":
                JCheckBox flatHandlebarsCheck = new JCheckBox("");
                addDialog.add(new JLabel("Flat Handlebars:"));
                addDialog.add(flatHandlebarsCheck);
                break;
                
            case "Mountain":
                JSpinner seatHeightSpinner = new JSpinner(new SpinnerNumberModel(70, 50, 100, 1));
                JCheckBox suspensionCheck = new JCheckBox("");
                JCheckBox flatProofCheck = new JCheckBox("");
                
                addDialog.add(new JLabel("Seat Height (cm):"));
                addDialog.add(seatHeightSpinner);
                addDialog.add(new JLabel("Full Suspension:"));
                addDialog.add(suspensionCheck);
                addDialog.add(new JLabel("Flat Proof Tires:"));
                addDialog.add(flatProofCheck);
                break;
                
            case "Electric":
            case "RoadElectric":
                JCheckBox eFlatHandlebarsCheck = new JCheckBox("");
                JTextField batteryTypeField = new JTextField("Lithium-ion");
                JTextField batterySizeField = new JTextField("10Ah");
                JTextField batteryVoltageField = new JTextField("36V");
                JTextField rangeField = new JTextField("40 miles");
                JTextField motorPowerField = new JTextField("250W");
                JCheckBox gpsCheck = new JCheckBox("");
                
                addDialog.add(new JLabel("Flat Handlebars:"));
                addDialog.add(eFlatHandlebarsCheck);
                addDialog.add(new JLabel("Battery Type:"));
                addDialog.add(batteryTypeField);
                addDialog.add(new JLabel("Battery Size:"));
                addDialog.add(batterySizeField);
                addDialog.add(new JLabel("Battery Voltage:"));
                addDialog.add(batteryVoltageField);
                addDialog.add(new JLabel("Range (miles):"));
                addDialog.add(rangeField);
                addDialog.add(new JLabel("Motor Power:"));
                addDialog.add(motorPowerField);
                addDialog.add(new JLabel("GPS Tracking:"));
                addDialog.add(gpsCheck);
                break;
        }
        
        JButton addButton = new JButton("Add Bike");
        addButton.addActionListener(e -> {
            // Create the new bike based on type
            BikeDetails newBike;
            String name = nameField.getText().trim();
            String description = descField.getText().trim();
            double price = (Double)priceSpinner.getValue();
            int quantity = (Integer)quantitySpinner.getValue();
            String stockID = stockIDField.getText().trim();
            int maxSpeed = (Integer)speedSpinner.getValue();
            int numberOfGears = (Integer)gearsSpinner.getValue();
            int numberOfWheels = (Integer)wheelsSpinner.getValue();
            String paintColor = colorField.getText().trim();
            String safetyFeatures = safetyField.getText().trim();
            
            if (name.isEmpty() || stockID.isEmpty()) {
                JOptionPane.showMessageDialog(addDialog, "Name and Stock ID are required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            switch(currentCategory) {
                case "Basic":
                case "Road":
                    boolean flatHandlebars = ((JCheckBox)addDialog.getContentPane().getComponent(21)).isSelected();
                    newBike = new BikeDetails(name, currentCategory, description, price, quantity, stockID, 
                        maxSpeed, numberOfGears, numberOfWheels, paintColor, safetyFeatures, flatHandlebars);
                    break;
                    
                case "Mountain":
                    int seatHeight = (Integer)((JSpinner)addDialog.getContentPane().getComponent(20)).getValue();
                    boolean fullSuspension = ((JCheckBox)addDialog.getContentPane().getComponent(22)).isSelected();
                    boolean flatProofTires = ((JCheckBox)addDialog.getContentPane().getComponent(24)).isSelected();
                    newBike = new BikeDetails(name, currentCategory, description, price, quantity, stockID, 
                        maxSpeed, numberOfGears, numberOfWheels, paintColor, safetyFeatures,
                        seatHeight, fullSuspension, flatProofTires);
                    break;
                    
                case "Electric":
                case "RoadElectric":
                    boolean eFlatHandlebars = ((JCheckBox)addDialog.getContentPane().getComponent(21)).isSelected();
                    String batteryType = ((JTextField)addDialog.getContentPane().getComponent(23)).getText();
                    String batterySize = ((JTextField)addDialog.getContentPane().getComponent(25)).getText();
                    String batteryVoltage = ((JTextField)addDialog.getContentPane().getComponent(27)).getText();
                    String range = ((JTextField)addDialog.getContentPane().getComponent(29)).getText();
                    String motorPower = ((JTextField)addDialog.getContentPane().getComponent(31)).getText();
                    boolean gpsTracking = ((JCheckBox)addDialog.getContentPane().getComponent(33)).isSelected();
                    newBike = new BikeDetails(name, currentCategory, description, price, quantity, stockID, 
                        maxSpeed, numberOfGears, numberOfWheels, paintColor, safetyFeatures,
                        eFlatHandlebars, batteryType, batterySize, batteryVoltage, range, motorPower, gpsTracking);
                    break;
                    
                default:
                    return;
            }
            
            // Add the new bike to inventory
            BikeDetails[] currentBikes = bikeInventory.get(currentCategory);
            BikeDetails[] newBikes = Arrays.copyOf(currentBikes, currentBikes.length + 1);
            newBikes[newBikes.length - 1] = newBike;
            bikeInventory.put(currentCategory, newBikes);
            
            // Show the new bike
            currentBikeIndex = newBikes.length - 1;
            updateBikeDisplay();
            
            addDialog.dispose();
            JOptionPane.showMessageDialog(jF, "✅ Bike added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        
        addDialog.add(addButton);
        addDialog.pack();
        addDialog.setLocationRelativeTo(jF);
        addDialog.setVisible(true);
    }

    private void deleteCurrentBike() {
        if (currentCategory.isEmpty()) {
            JOptionPane.showMessageDialog(jF, "No bike selected to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        BikeDetails[] bikes = bikeInventory.get(currentCategory);
        if (bikes == null || bikes.length == 0) {
            JOptionPane.showMessageDialog(jF, "No bikes in this category to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(jF, 
            "Are you sure you want to delete this bike?\n" + bikes[currentBikeIndex].name,
            "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Create a new array without the deleted bike
            BikeDetails[] newBikes = new BikeDetails[bikes.length - 1];
            System.arraycopy(bikes, 0, newBikes, 0, currentBikeIndex);
            System.arraycopy(bikes, currentBikeIndex + 1, newBikes, currentBikeIndex, bikes.length - currentBikeIndex - 1);
            
            bikeInventory.put(currentCategory, newBikes);
            
            // Adjust current index if needed
            if (currentBikeIndex >= newBikes.length && newBikes.length > 0) {
                currentBikeIndex = newBikes.length - 1;
            } else if (newBikes.length == 0) {
                currentBikeIndex = 0;
            }
            
            updateBikeDisplay();
            JOptionPane.showMessageDialog(jF, "✅ Bike deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void show() {
        jF = new JFrame("UST Bike Shop Pro System");
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jF.setSize(1000, 700);
        jF.setMinimumSize(new Dimension(800, 600));
        jF.add(mainPanel);
        jF.setLocationRelativeTo(null);
        jF.setVisible(true);
    }

    public static void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login - UST Bike Shop Pro");
        loginFrame.setSize(350, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridLayout(4, 1));

        JPanel userPanel = new JPanel();
        JLabel userLabel = new JLabel("Username: ");
        JTextField userField = new JTextField(15);
        userPanel.add(userLabel);
        userPanel.add(userField);

        JPanel passPanel = new JPanel();
        JLabel passLabel = new JLabel("Password: ");
        JPasswordField passField = new JPasswordField(15);
        passPanel.add(passLabel);
        passPanel.add(passField);

        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.equals("BikeLord") && password.equals("SpiderOne")) {
                loginFrame.dispose();
                USTBikeShopProGUI gui = new USTBikeShopProGUI();
                gui.show();
            } else {
                statusLabel.setText("❌ Invalid login. Try again.");
            }
        });

        loginFrame.add(userPanel);
        loginFrame.add(passPanel);
        loginFrame.add(loginButton);
        loginFrame.add(statusLabel);

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showLoginScreen();
        });
    }
}
