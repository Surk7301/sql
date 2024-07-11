import java.util.*;

class InsuranceApplication {
    private static final double BASE_PREMIUM_4_WHEELER = 4500;
    private static final double BASE_PREMIUM_2_WHEELER = 2500;
    private Map<Integer, Underwriter> underwriters = new HashMap<>();
    private Map<String, List<PremiumRecord>> premiumHistory = new HashMap<>();
    private Map<String, String> adminCredentials = new HashMap<>();
    private Map<String, String> underwriterCredentials = new HashMap<>();

    public static void main(String[] args) {
        InsuranceApplication app = new InsuranceApplication();
        app.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        // Adding a default admin for testing
        adminCredentials.put("admin", "password123");
        while (true) {
            try {
                System.out.println("1. Admin Login");
                System.out.println("2. Underwriter Login");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        if (adminLogin(scanner)) {
                            adminMenu(scanner);
                        }
                        break;
                    case 2:
                        if (underwriterLogin(scanner)) {
                            underwriterMenu(scanner);
                        }
                        break;
                    case 3:
                        System.out.println("Exiting application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
                scanner.nextLine(); // Consume newline
            }
        }
    }

    private boolean adminLogin(Scanner scanner) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password)) {
            System.out.println("Admin login successful.");
            return true;
        } else {
            System.out.println("Invalid admin credentials.");
            return false;
        }
    }

    private boolean underwriterLogin(Scanner scanner) {
        System.out.print("Enter Underwriter ID: ");
        String underwriterId = scanner.nextLine();
        System.out.print("Enter Underwriter Password: ");
        String password = scanner.nextLine();

        if (underwriterCredentials.containsKey(underwriterId) && underwriterCredentials.get(underwriterId).equals(password)) {
            System.out.println("Underwriter login successful.");
            return true;
        } else {
            System.out.println("Invalid underwriter credentials.");
            return false;
        }
    }

    private void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Register Underwriter");
            System.out.println("2. View All Underwriters");
            System.out.println("3. Exit to Main Menu");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUnderwriter(scanner);
                    break;
                case 2:
                    viewAllUnderwriters();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void registerUnderwriter(Scanner scanner) {
        System.out.print("Enter Underwriter ID: ");
        int underwriterId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter DOB: ");
        String dob = scanner.nextLine();

        System.out.print("Enter Joining Date: ");
        String joiningDate = scanner.nextLine();

        System.out.print("Enter Default Password: ");
        String defaultPassword = scanner.nextLine();

        Underwriter underwriter = new Underwriter(underwriterId, name, dob, joiningDate, defaultPassword);
        underwriters.put(underwriterId, underwriter);
        underwriterCredentials.put(String.valueOf(underwriterId), defaultPassword);
        System.out.println("Underwriter registered successfully.");
    }

    private void viewAllUnderwriters() {
        for (Underwriter underwriter : underwriters.values()) {
            System.out.println(underwriter);
        }
    }

    private void underwriterMenu(Scanner scanner) {
        while (true) {
            System.out.println("Underwriter Menu:");
            System.out.println("1. Calculate Premium");
            System.out.println("2. View Premium History");
            System.out.println("3. Exit to Main Menu");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    calculatePremium(scanner);
                    break;
                case 2:
                    viewPremiumHistory(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void calculatePremium(Scanner scanner) {
        System.out.print("Enter Vehicle Type (2-wheeler/4-wheeler): ");
        String vehicleType = scanner.nextLine();

        System.out.print("Enter Vehicle Value: ");
        double vehicleValue = scanner.nextDouble();

        System.out.print("Enter Vehicle State (New/Old): ");
        String vehicleState = scanner.next();

        boolean claimStatus = false;
        double ncb = 0;

        if (vehicleState.equals("Old")) {
            System.out.print("Enter Claim Status (true/false): ");
            claimStatus = scanner.nextBoolean();

            if (!claimStatus) {
                System.out.print("Enter NCB: ");
                ncb = scanner.nextDouble();
            }
        }

        double premiumAmount = calculatePremiumAmount(vehicleType, vehicleValue, vehicleState, claimStatus, ncb);
        System.out.println("Calculated Premium Amount: " + premiumAmount);
    }

    private double calculatePremiumAmount(String vehicleType, double vehicleValue, String vehicleState, boolean claimStatus, double ncb) {
        double basePremium = vehicleType.equals("4-wheeler") ? BASE_PREMIUM_4_WHEELER : BASE_PREMIUM_2_WHEELER;
        double premiumAmount = 0;

        if (vehicleType.equals("2-wheeler")) {
            if (vehicleState.equals("New")) {
                premiumAmount = vehicleValue * 0.10;
            } else {
                premiumAmount = (vehicleValue * 0.10) - ncb;
            }
        } else if (vehicleType.equals("4-wheeler")) {
            if (vehicleState.equals("New")) {
                premiumAmount = vehicleValue * 0.15;
            } else {
                premiumAmount = (vehicleValue * 0.15) - ncb;
            }
        }

        return basePremium + premiumAmount;
    }

    private void viewPremiumHistory(Scanner scanner) {
        System.out.print("Enter Vehicle Number: ");
        String vehicleNo = scanner.nextLine();
        List<PremiumRecord> records = premiumHistory.get(vehicleNo);
        if (records != null) {
            for (PremiumRecord record : records) {
                System.out.println("Year: " + record.getYear() + ", Premium Amount Paid: " + record.getAmount());
            }
        } else {
            System.out.println("No records found for vehicle number: " + vehicleNo);
        }
    }

    private void addPremiumRecord(String vehicleNo, int year, double amount) {
        premiumHistory.computeIfAbsent(vehicleNo, k -> new ArrayList<>()).add(new PremiumRecord(year, amount));
    }
}

class Underwriter {
    private int underwriterId;
    private String name;
    private String dob;
    private String joiningDate;
    private String defaultPassword;

    public Underwriter(int underwriterId, String name, String dob, String joiningDate, String defaultPassword) {
        this.underwriterId = underwriterId;
        this.name = name;
        this.dob = dob;
        this.joiningDate = joiningDate;
        this.defaultPassword = defaultPassword;
    }

    public int getUnderwriterId() {
        return underwriterId;
    }

    @Override
    public String toString() {
        return "Underwriter ID: " + underwriterId + ", Name: " + name + ", DOB: " + dob + ", Joining Date: " + joiningDate;
    }
}

class PremiumRecord {
    private int year;
    private double amount;

    public PremiumRecord(int year, double amount) {
        this.year = year;
        this.amount = amount;
    }

    public int getYear() {
        return year;
    }

    public double getAmount() {
        return amount;
    }
}
