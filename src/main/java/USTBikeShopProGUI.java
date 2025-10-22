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
}