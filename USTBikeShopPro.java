import java.io.*;
import java.util.Scanner;

public class USTBikeShopPro {
    private static final int MAX_INVENTORY_CAPACITY = 100;
    private static final String INVENTORY_FILE = "bike_inventory.dat";
    
    private static final Bike[] basicBikes = new Bike[MAX_INVENTORY_CAPACITY];
    private static final Bike[] mountainBikes = new Bike[MAX_INVENTORY_CAPACITY];
    private static final Bike[] roadBikes = new Bike[MAX_INVENTORY_CAPACITY];
    private static final Bike[] eBikes = new Bike[MAX_INVENTORY_CAPACITY];
    private static final Bike[] roadEBikes = new Bike[MAX_INVENTORY_CAPACITY];
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> displayInventory("Basic Bike", basicBikes);
                case 2 -> displayInventory("Mountain Bike", mountainBikes);
                case 3 -> displayInventory("Road Bike", roadBikes);
                case 4 -> displayInventory("E-Bike", eBikes);
                case 5 -> displayInventory("Road E-Bike", roadEBikes);
                case 6 -> displayAllInventories();
                case 7 -> addBike(basicBikes, "Basic");
                case 8 -> addBike(mountainBikes, "Mountain");
                case 9 -> addBike(roadBikes, "Road");
                case 10 -> addBike(eBikes, "E-Bike");
                case 11 -> addBike(roadEBikes, "Road E-Bike");
                case 12 -> modifyBike(basicBikes, "Basic");
                case 13 -> modifyBike(mountainBikes, "Mountain");
                case 14 -> modifyBike(roadBikes, "Road");
                case 15 -> modifyBike(eBikes, "E-Bike");
                case 16 -> modifyBike(roadEBikes, "Road E-Bike");
                case 17 -> removeBike(basicBikes, "Basic");
                case 18 -> removeBike(mountainBikes, "Mountain");
                case 19 -> removeBike(roadBikes, "Road");
                case 20 -> removeBike(eBikes, "E-Bike");
                case 21 -> removeBike(roadEBikes, "Road E-Bike");
                case 22 -> saveToFile();
                case 23 -> loadFromFile();
                case 24 -> {
                    if (confirmExit()) {
                        scanner.close();
                        System.exit(0);
                    }
                }
                default -> System.out.println("Invalid option - please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
                
                Enter the action number to perform.  Your options are:

                 1 - display Basic bike Stock      2 - display Mountain bike Stock   3 - display Road bike Stock
                 4 - display E bike Stock          5 - display Road E bike Stock     6 - display ALL inventory
                --------------------------------------------------------------------------------
                 7 - add Basic Bike Stock          8 - add Mountain Bike Stock       9 - add Road Bike Stock
                10 - add E Bike Stock             11 - add Road E Bike Stock
                --------------------------------------------------------------------------------
                12 - modify Basic Bike Stock      13 - modify Mountain Bike Stock   14 - modify Road Bike Stock
                15 - modify E Bike Stock          16 - modify Road E Bike Stock
                --------------------------------------------------------------------------------
                17 - delete Basic Bike Stock      18 - delete Mountain Bike Stock   19 - delete Road Bike Stock
                20 - delete E Bike Stock          21 - delete Road E Bike Stock
                --------------------------------------------------------------------------------
                22 - save Inventory to file       23 - load Inventory from file      24 - EXIT
                --------------------------------------------------------------------------------
                """);
    }

    private static void displayInventory(String title, Bike[] inventory) {
        System.out.println("\n--- " + title + " Inventory ---");
        boolean empty = true;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.println("\nStock ID: " + title.replace(" ", "_") + "_" + (i + 1));
                inventory[i].showDetails();
                empty = false;
            }
        }
        if (empty) {
            System.out.println("(Empty)");
        }
        System.out.println("---------------------------------------------");
    }

    private static void displayAllInventories() {
        displayInventory("Basic Bike", basicBikes);
        displayInventory("Mountain Bike", mountainBikes);
        displayInventory("Road Bike", roadBikes);
        displayInventory("E-Bike", eBikes);
        displayInventory("Road E-Bike", roadEBikes);
    }

    private static void addBike(Bike[] inventory, String type) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                System.out.println("\nAdding new " + type + " bike:");
                
                int maxSpeed = getIntInput("Max speed: ");
                int gears = getIntInput("No. of gears: ");
                int wheels = getIntInput("No. of wheels: ");
                String paintColor = getStringInput("Paint color: ");
                String safetyFeatures = getStringInput("Safety features: ");
                
                switch (type) {
                    case "Basic" -> 
                        inventory[i] = new BasicBike(maxSpeed, gears, wheels, paintColor, safetyFeatures);
                    
                    case "Mountain" -> {
                        int seatHeight = getIntInput("Seat height (cm): ");
                        boolean fullSuspension = getYesNoInput("Full suspension (y/n): ");
                        boolean flatProofTires = getYesNoInput("Flat proof tires (y/n): ");
                        inventory[i] = new MountainBike(maxSpeed, gears, wheels, paintColor, safetyFeatures,
                                                      seatHeight, fullSuspension, flatProofTires);
                    }
                    
                    case "Road" -> {
                        boolean flatHandlebars = getYesNoInput("Flat handlebars (y/n): ");
                        inventory[i] = new RoadBike(maxSpeed, gears, wheels, paintColor, safetyFeatures,
                                                  flatHandlebars);
                    }
                    
                    case "E-Bike" -> {
                        int seatHeight = getIntInput("Seat height (cm): ");
                        boolean fullSuspension = getYesNoInput("Full suspension (y/n): ");
                        boolean flatProofTires = getYesNoInput("Flat proof tires (y/n): ");
                        String batteryType = getStringInput("Battery type: ");
                        int batterySize = getIntInput("Battery size (Ah): ");
                        int batteryVoltage = getIntInput("Battery voltage (V): ");
                        int rangeMiles = getIntInput("Range (miles): ");
                        int motorPower = getIntInput("Motor power (W): ");
                        inventory[i] = new EBike(maxSpeed, gears, wheels, paintColor, safetyFeatures,
                                               seatHeight, fullSuspension, flatProofTires,
                                               batteryType, batterySize, batteryVoltage,
                                               rangeMiles, motorPower);
                    }
                    
                    case "Road E-Bike" -> {
                        boolean flatHandlebars = getYesNoInput("Flat handlebars (y/n): ");
                        String batteryType = getStringInput("Battery type: ");
                        int batterySize = getIntInput("Battery size (Ah): ");
                        int batteryVoltage = getIntInput("Battery voltage (V): ");
                        int rangeMiles = getIntInput("Range (miles): ");
                        int motorPower = getIntInput("Motor power (W): ");
                        boolean gpsTracking = getYesNoInput("GPS tracking enabled (y/n): ");
                        inventory[i] = new RoadEBike(maxSpeed, gears, wheels, paintColor, safetyFeatures,
                                                   flatHandlebars, batteryType, batterySize, batteryVoltage,
                                                   rangeMiles, motorPower, gpsTracking);
                    }
                }
                
                System.out.println(type + " bike added successfully!");
                return;
            }
        }
        System.out.println("Inventory full - cannot add more " + type + " bikes.");
    }

    private static void modifyBike(Bike[] inventory, String type) {
        int stockNumber = getIntInput("Enter the stock number for the bike to modify: ");
        int slot = stockNumber - 1;
        
        if (slot < 0 || slot >= inventory.length || inventory[slot] == null) {
            System.out.println("Invalid stock number or empty slot.");
            return;
        }
        
        System.out.println("Modifying " + type + " bike stock number " + stockNumber);
        String confirm = getStringInput("Confirm Y/N? : ");
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Modification cancelled.");
            return;
        }
        
        Bike current = inventory[slot];
        
        // Common attributes for all bikes
        System.out.println("\nCurrent max speed value is: " + current.getMaxSpeed());
        int maxSpeed = getIntInput("Enter the new max speed value: ", current.getMaxSpeed());
        
        System.out.println("\nCurrent no of gears value is: " + current.getGears());
        int gears = getIntInput("Enter the new no of gears value: ", current.getGears());
        
        System.out.println("\nCurrent no of wheels value is: " + current.getWheels());
        int wheels = getIntInput("Enter the new no of wheels value: ", current.getWheels());
        
        System.out.println("\nCurrent paint color value is: " + 
                         (current.getPaintColor() != null ? current.getPaintColor() : "null"));
        String paintColor = getStringInput("Enter the new paint color value: ", current.getPaintColor());
        
        System.out.println("\nCurrent safety features value is: " + 
                         (current.getSafetyFeatures() != null ? current.getSafetyFeatures() : "null"));
        String safetyFeatures = getStringInput("Enter the new safety features value: ", current.getSafetyFeatures());
        
        // Type-specific attributes
        switch (type) {
            case "Mountain" -> {
                MountainBike mtb = (MountainBike)current;
                System.out.println("\nCurrent seat height: " + mtb.getSeatHeight() + " cm");
                int seatHeight = getIntInput("Enter new seat height (cm): ", mtb.getSeatHeight());
                
                System.out.println("\nCurrent full suspension: " + (mtb.hasFullSuspension() ? "Yes" : "No"));
                boolean fullSuspension = getYesNoInput("Full suspension (y/n): ");
                
                System.out.println("\nCurrent flat proof tires: " + (mtb.hasFlatProofTires() ? "Yes" : "No"));
                boolean flatProofTires = getYesNoInput("Flat proof tires (y/n): ");
                
                inventory[slot] = new MountainBike(maxSpeed, gears, wheels, paintColor, safetyFeatures,
                                                 seatHeight, fullSuspension, flatProofTires);
            }
            
            case "Road" -> {
                RoadBike rb = (RoadBike)current;
                System.out.println("\nCurrent flat handlebars: " + (rb.hasFlatHandlebars() ? "Yes" : "No"));
                boolean flatHandlebars = getYesNoInput("Flat handlebars (y/n): ");
                
                inventory[slot] = new RoadBike(maxSpeed, gears, wheels, paintColor, safetyFeatures,
                                             flatHandlebars);
            }
            
            case "E-Bike" -> {
                EBike eb = (EBike)current;
                System.out.println("\nCurrent seat height: " + eb.getSeatHeight() + " cm");
                int seatHeight = getIntInput("Enter new seat height (cm): ", eb.getSeatHeight());
                
                System.out.println("\nCurrent full suspension: " + (eb.hasFullSuspension() ? "Yes" : "No"));
                boolean fullSuspension = getYesNoInput("Full suspension (y/n): ");
                
                System.out.println("\nCurrent flat proof tires: " + (eb.hasFlatProofTires() ? "Yes" : "No"));
                boolean flatProofTires = getYesNoInput("Flat proof tires (y/n): ");
                
                System.out.println("\nCurrent battery type: " + eb.getBatteryType());
                String batteryType = getStringInput("Enter new battery type: ", eb.getBatteryType());
                
                System.out.println("\nCurrent battery size: " + eb.getBatterySize() + "Ah");
                int batterySize = getIntInput("Enter new battery size (Ah): ", eb.getBatterySize());
                
                System.out.println("\nCurrent battery voltage: " + eb.getBatteryVoltage() + "V");
                int batteryVoltage = getIntInput("Enter new battery voltage (V): ", eb.getBatteryVoltage());
                
                System.out.println("\nCurrent range: " + eb.getRangeMiles() + " miles");
                int rangeMiles = getIntInput("Enter new range (miles): ", eb.getRangeMiles());
                
                System.out.println("\nCurrent motor power: " + eb.getMotorPower() + "W");
                int motorPower = getIntInput("Enter new motor power (W): ", eb.getMotorPower());
                
                inventory[slot] = new EBike(maxSpeed, gears, wheels, paintColor, safetyFeatures,
                                          seatHeight, fullSuspension, flatProofTires,
                                          batteryType, batterySize, batteryVoltage,
                                          rangeMiles, motorPower);
            }
            
            case "Road E-Bike" -> {
                RoadEBike reb = (RoadEBike)current;
                System.out.println("\nCurrent flat handlebars: " + (reb.hasFlatHandlebars() ? "Yes" : "No"));
                boolean flatHandlebars = getYesNoInput("Flat handlebars (y/n): ");
                
                System.out.println("\nCurrent battery type: " + reb.getBatteryType());
                String batteryType = getStringInput("Enter new battery type: ", reb.getBatteryType());
                
                System.out.println("\nCurrent battery size: " + reb.getBatterySize() + "Ah");
                int batterySize = getIntInput("Enter new battery size (Ah): ", reb.getBatterySize());
                
                System.out.println("\nCurrent battery voltage: " + reb.getBatteryVoltage() + "V");
                int batteryVoltage = getIntInput("Enter new battery voltage (V): ", reb.getBatteryVoltage());
                
                System.out.println("\nCurrent range: " + reb.getRangeMiles() + " miles");
                int rangeMiles = getIntInput("Enter new range (miles): ", reb.getRangeMiles());
                
                System.out.println("\nCurrent motor power: " + reb.getMotorPower() + "W");
                int motorPower = getIntInput("Enter new motor power (W): ", reb.getMotorPower());
                
                System.out.println("\nCurrent GPS tracking: " + (reb.isGpsTrackingEnabled() ? "Enabled" : "Disabled"));
                boolean gpsTracking = getYesNoInput("GPS tracking enabled (y/n): ");
                
                inventory[slot] = new RoadEBike(maxSpeed, gears, wheels, paintColor, safetyFeatures,
                                              flatHandlebars, batteryType, batterySize, batteryVoltage,
                                              rangeMiles, motorPower, gpsTracking);
            }
            
            default -> // Basic Bike
                inventory[slot] = new BasicBike(maxSpeed, gears, wheels, paintColor, safetyFeatures);
        }
        
        System.out.println("\nSuccessfully modified " + type + " bike stock " + stockNumber + " from the inventory");
    }

    private static void removeBike(Bike[] inventory, String type) {
        int stockNumber = getIntInput("Enter stock number to remove: ");
        int slot = stockNumber - 1;
        
        if (slot >= 0 && slot < inventory.length && inventory[slot] != null) {
            inventory[slot] = null;
            System.out.println(type + " bike #" + stockNumber + " removed successfully!");
        } else {
            System.out.println("Invalid stock number or empty slot.");
        }
    }

    private static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(INVENTORY_FILE))) {
            
            oos.writeObject(basicBikes);
            oos.writeObject(mountainBikes);
            oos.writeObject(roadBikes);
            oos.writeObject(eBikes);
            oos.writeObject(roadEBikes);
            
            System.out.println("Inventory saved successfully to " + INVENTORY_FILE);
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) {
            System.out.println("No saved inventory found.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(INVENTORY_FILE))) {
            
            Bike[] loadedBasic = (Bike[]) ois.readObject();
            System.arraycopy(loadedBasic, 0, basicBikes, 0, loadedBasic.length);
            
            Bike[] loadedMountain = (Bike[]) ois.readObject();
            System.arraycopy(loadedMountain, 0, mountainBikes, 0, loadedMountain.length);
            
            Bike[] loadedRoad = (Bike[]) ois.readObject();
            System.arraycopy(loadedRoad, 0, roadBikes, 0, loadedRoad.length);
            
            Bike[] loadedEBike = (Bike[]) ois.readObject();
            System.arraycopy(loadedEBike, 0, eBikes, 0, loadedEBike.length);
            
            Bike[] loadedRoadEBike = (Bike[]) ois.readObject();
            System.arraycopy(loadedRoadEBike, 0, roadEBikes, 0, loadedRoadEBike.length);
            
            System.out.println("Inventory loaded successfully from " + INVENTORY_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    private static boolean confirmExit() {
        while (true) {
            System.out.print("Are you sure you want to exit? (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("y")) {
                System.out.println("Exiting program. Goodbye!");
                return true;
            } else if (input.equals("n")) {
                System.out.println("Returning to main menu.");
                return false;
            }
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static int getIntInput(String prompt, int currentValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                return currentValue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static String getStringInput(String prompt, String currentValue) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        return input.isEmpty() ? currentValue : input;
    }

    private static boolean getYesNoInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
            System.out.println("Please enter 'y' or 'n'.");
        }
    }
}

abstract class Bike implements Serializable {
    protected static final long serialVersionUID = 1L;
    protected int maxSpeed;
    protected int gears;
    protected int wheels;
    protected String paintColor;
    protected String safetyFeatures;
    
    public Bike(int maxSpeed, int gears, int wheels, String paintColor, String safetyFeatures) {
        this.maxSpeed = maxSpeed;
        this.gears = gears;
        this.wheels = wheels;
        this.paintColor = paintColor;
        this.safetyFeatures = safetyFeatures;
    }
    
    public int getMaxSpeed() { return maxSpeed; }
    public int getGears() { return gears; }
    public int getWheels() { return wheels; }
    public String getPaintColor() { return paintColor; }
    public String getSafetyFeatures() { return safetyFeatures; }
    
    public abstract void showDetails();
}

class BasicBike extends Bike {
    private static final long serialVersionUID = 1L;

    public BasicBike() {
        super(0, 0, 0, null, null);
    }
    
    public BasicBike(int maxSpeed, int gears, int wheels, String paintColor, String safetyFeatures) {
        super(maxSpeed, gears, wheels, paintColor, safetyFeatures);
    }
    
    @Override
    public void showDetails() {
        System.out.println("Max speed: " + getMaxSpeed());
        System.out.println("No. of gears: " + getGears());
        System.out.println("No. of wheels: " + getWheels());
        System.out.println("Paint color: " + (getPaintColor() != null ? getPaintColor() : "null"));
        System.out.println("Safety features: " + (getSafetyFeatures() != null ? getSafetyFeatures() : "null"));
    }
}

class MountainBike extends Bike {
    private static final long serialVersionUID = 1L;
    private int seatHeight;
    private boolean fullSuspension;
    private boolean flatProofTires;

    public MountainBike(int maxSpeed, int gears, int wheels, String paintColor,
                      String safetyFeatures, int seatHeight, boolean fullSuspension,
                      boolean flatProofTires) {
        super(maxSpeed, gears, wheels, paintColor, safetyFeatures);
        this.seatHeight = seatHeight;
        this.fullSuspension = fullSuspension;
        this.flatProofTires = flatProofTires;
    }
    
    public int getSeatHeight() { return seatHeight; }
    public boolean hasFullSuspension() { return fullSuspension; }
    public boolean hasFlatProofTires() { return flatProofTires; }
    
    @Override
    public void showDetails() {
        System.out.println("Max speed: " + getMaxSpeed());
        System.out.println("No. of gears: " + getGears());
        System.out.println("No. of wheels: " + getWheels());
        System.out.println("Paint color: " + (getPaintColor() != null ? getPaintColor() : "null"));
        System.out.println("Safety features: " + (getSafetyFeatures() != null ? getSafetyFeatures() : "null"));
        System.out.println("Seat height: " + getSeatHeight() + " cm");
        System.out.println("Full suspension: " + (hasFullSuspension() ? "Yes" : "No"));
        System.out.println("Flat proof tires: " + (hasFlatProofTires() ? "Yes" : "No"));
    }
}

class RoadBike extends Bike {
    private static final long serialVersionUID = 1L;
    private boolean flatHandlebars;

    public RoadBike(int maxSpeed, int gears, int wheels, String paintColor,
                  String safetyFeatures, boolean flatHandlebars) {
        super(maxSpeed, gears, wheels, paintColor, safetyFeatures);
        this.flatHandlebars = flatHandlebars;
    }
    
    public boolean hasFlatHandlebars() { return flatHandlebars; }
    
    @Override
    public void showDetails() {
        System.out.println("Max speed: " + getMaxSpeed());
        System.out.println("No. of gears: " + getGears());
        System.out.println("No. of wheels: " + getWheels());
        System.out.println("Paint color: " + (getPaintColor() != null ? getPaintColor() : "null"));
        System.out.println("Safety features: " + (getSafetyFeatures() != null ? getSafetyFeatures() : "null"));
        System.out.println("Flat handlebars: " + (hasFlatHandlebars() ? "Yes" : "No"));
    }
}

class EBike extends Bike {
    private static final long serialVersionUID = 1L;
    private int seatHeight;
    private boolean fullSuspension;
    private boolean flatProofTires;
    private String batteryType;
    private int batterySize;
    private int batteryVoltage;
    private int rangeMiles;
    private int motorPower;

    public EBike(int maxSpeed, int gears, int wheels, String paintColor,
               String safetyFeatures, int seatHeight, boolean fullSuspension,
               boolean flatProofTires, String batteryType, int batterySize,
               int batteryVoltage, int rangeMiles, int motorPower) {
        super(maxSpeed, gears, wheels, paintColor, safetyFeatures);
        this.seatHeight = seatHeight;
        this.fullSuspension = fullSuspension;
        this.flatProofTires = flatProofTires;
        this.batteryType = batteryType;
        this.batterySize = batterySize;
        this.batteryVoltage = batteryVoltage;
        this.rangeMiles = rangeMiles;
        this.motorPower = motorPower;
    }
    
    public int getSeatHeight() { return seatHeight; }
    public boolean hasFullSuspension() { return fullSuspension; }
    public boolean hasFlatProofTires() { return flatProofTires; }
    public String getBatteryType() { return batteryType; }
    public int getBatterySize() { return batterySize; }
    public int getBatteryVoltage() { return batteryVoltage; }
    public int getRangeMiles() { return rangeMiles; }
    public int getMotorPower() { return motorPower; }
    
    @Override
    public void showDetails() {
        System.out.println("Max speed: " + getMaxSpeed());
        System.out.println("No. of gears: " + getGears());
        System.out.println("No. of wheels: " + getWheels());
        System.out.println("Paint color: " + (getPaintColor() != null ? getPaintColor() : "null"));
        System.out.println("Safety features: " + (getSafetyFeatures() != null ? getSafetyFeatures() : "null"));
        System.out.println("Seat height: " + getSeatHeight() + " cm");
        System.out.println("Full suspension: " + (hasFullSuspension() ? "Yes" : "No"));
        System.out.println("Flat proof tires: " + (hasFlatProofTires() ? "Yes" : "No"));
        System.out.println("Battery type: " + getBatteryType());
        System.out.println("Battery size: " + getBatterySize() + "Ah");
        System.out.println("Battery voltage: " + getBatteryVoltage() + "V");
        System.out.println("Range: " + getRangeMiles() + " miles");
        System.out.println("Motor power: " + getMotorPower() + "W");
    }
}

class RoadEBike extends Bike {
    private static final long serialVersionUID = 1L;
    private boolean flatHandlebars;
    private String batteryType;
    private int batterySize;
    private int batteryVoltage;
    private int rangeMiles;
    private int motorPower;
    private boolean gpsTrackingEnabled;

    public RoadEBike(int maxSpeed, int gears, int wheels, String paintColor,
                   String safetyFeatures, boolean flatHandlebars, String batteryType,
                   int batterySize, int batteryVoltage, int rangeMiles, int motorPower,
                   boolean gpsTrackingEnabled) {
        super(maxSpeed, gears, wheels, paintColor, safetyFeatures);
        this.flatHandlebars = flatHandlebars;
        this.batteryType = batteryType;
        this.batterySize = batterySize;
        this.batteryVoltage = batteryVoltage;
        this.rangeMiles = rangeMiles;
        this.motorPower = motorPower;
        this.gpsTrackingEnabled = gpsTrackingEnabled;
    }
    
    public boolean hasFlatHandlebars() { return flatHandlebars; }
    public String getBatteryType() { return batteryType; }
    public int getBatterySize() { return batterySize; }
    public int getBatteryVoltage() { return batteryVoltage; }
    public int getRangeMiles() { return rangeMiles; }
    public int getMotorPower() { return motorPower; }
    public boolean isGpsTrackingEnabled() { return gpsTrackingEnabled; }
    
    @Override
    public void showDetails() {
        System.out.println("Max speed: " + getMaxSpeed());
        System.out.println("No. of gears: " + getGears());
        System.out.println("No. of wheels: " + getWheels());
        System.out.println("Paint color: " + (getPaintColor() != null ? getPaintColor() : "null"));
        System.out.println("Safety features: " + (getSafetyFeatures() != null ? getSafetyFeatures() : "null"));
        System.out.println("Flat handlebars: " + (hasFlatHandlebars() ? "Yes" : "No"));
        System.out.println("Battery type: " + getBatteryType());
        System.out.println("Battery size: " + getBatterySize() + "Ah");
        System.out.println("Battery voltage: " + getBatteryVoltage() + "V");
        System.out.println("Range: " + getRangeMiles() + " miles");
        System.out.println("Motor power: " + getMotorPower() + "W");
        System.out.println("GPS tracking: " + (isGpsTrackingEnabled() ? "Enabled" : "Disabled"));
    }
}
