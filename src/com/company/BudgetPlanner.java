package com.company;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class BudgetPlanner {
    // Constant to define the filename where user data is stored
    static final String FILE_NAME = "users.csv";
    // Scanner to read user input from the console
    static final Scanner scanner = new Scanner(System.in);
    // List to maintain activity log of the app
    static final List<String> activityLog = new ArrayList<String>();

    public static void main(String[] args) {
        // Main loop to interact with the user and perform various actions
        while (true) {
            System.out.println("\n--- Personal Budget Planner ---");
            System.out.println("1. Create User\n2. View Users\n3. Update User\n4. Delete User\n5. Generate Report\n6. Calculate Savings Duration\n7. Exit");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            // Handling the various menu options
            if ("1".equals(option)) {
                createUser();  // Option 1: Create a new user
            } else if ("2".equals(option)) {
                viewUsers();  // Option 2: View existing users
            } else if ("3".equals(option)) {
                updateUser();  // Option 3: Update an existing user's data
            } else if ("4".equals(option)) {
                deleteUser();  // Option 4: Delete a user
            } else if ("5".equals(option)) {
                generateReport();  // Option 5: Generate a report
            } else if ("6".equals(option)) {
                calculateSavingsDuration();  // Option 6: Calculate savings duration for a user
            } else if ("7".equals(option)) {
                exitApp();  // Option 7: Exit the application
                return;  // Exit the loop and terminate the program
            }
            else {
                System.out.println("Invalid option.");  // Handle invalid input
            }
        }
    }

    // Helper method to validate the email format using regular expressions
    static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", email);  // Check if the email follows a valid format
    }

    // Method to read users from the CSV file and return a list of users
    static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            // Read each line from the CSV file and convert it into a User object
            while ((line = br.readLine()) != null) {
                users.add(User.fromCSV(line));  // Convert CSV line to User object
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());  // Handle file read error
        }
        return users;  // Return the list of users
    }

    // Method to save a list of users back to the CSV file
    static void saveUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                pw.println(user.toCSV());  // Write each user as CSV format to the file
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());  // Handle file write error
        }
    }

    // Method to create a new user with input validation
    static void createUser() {
        String name;
        do {
            System.out.print("Enter name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");  // Ensure name is not empty
            }
        } while (name.isEmpty());

        String email;
        List<User> users = readUsers();  // Read existing users from file
        boolean emailExists;

        do {
            System.out.print("Enter email: ");
            email = scanner.nextLine().trim();

            // Validate email format
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format.");
                continue;  // Skip to the next iteration if email is invalid
            }

            // Check if email already exists in the list of users
            String finalEmail = email;
            emailExists = users.stream().anyMatch(user -> user.email.equalsIgnoreCase(finalEmail));
            if (emailExists) {
                System.out.println("This email already exists. Please use a different one.");
                email = null;  // Reset email if it already exists in the system
            }

        } while (email == null);  // Keep asking for email until a valid one is provided

        // Prompt the user for financial details
        double income = readDouble("Enter income: ");
        double expenses = readDouble("Enter expenses: ");
        double savingsGoal = readDouble("Enter savings goal: ");

        // Create a new user object and save it to the list
        users.add(new User(name, email, income, expenses, savingsGoal));
        saveUsers(users);  // Save updated users list to file

        // Log the user creation activity
        activityLog.add("User created: " + email);
    }

    // Helper method to read a valid positive double value from the user
    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Value must be a positive number. Please try again.");
                } else {
                    return value;  // Return the valid positive number
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid positive number.");
            }
        }
    }

    // Method to view all existing users
    static void viewUsers() {
        List<User> users = readUsers();  // Read users from file
        if (users.isEmpty()) {
            System.out.println("No users found.");  // Handle case where there are no users
        } else {
            // Print details of each user
            for (User user : users) {
                System.out.println(user.name + " | " + user.email + " | Income: " + user.income + ", Expenses: " + user.expenses + ", Savings Goal: " + user.savingsGoal);
            }
        }
        // Log the activity of viewing users
        activityLog.add("Viewed users");
    }

    // Method to update an existing user's data
    static void updateUser() {
        List<User> users = readUsers();  // Read users from file
        System.out.print("Enter email of user to update: ");
        String email = scanner.nextLine();
        for (User user : users) {
            // Find the user by email and update their financial details
            if (user.email.equals(email)) {
                System.out.print("New income: ");
                user.income = Double.parseDouble(scanner.nextLine());
                System.out.print("New expenses: ");
                user.expenses = Double.parseDouble(scanner.nextLine());
                System.out.print("New savings goal: ");
                user.savingsGoal = Double.parseDouble(scanner.nextLine());
                saveUsers(users);  // Save the updated users list to file
                activityLog.add("Updated user: " + email);  // Log the update activity
                return;
            }
        }
        System.out.println("User not found.");  // Handle case where user is not found
    }

    // Method to delete an existing user
    static void deleteUser() {
        List<User> users = readUsers();  // Read users from file
        System.out.print("Enter email of user to delete: ");
        String email = scanner.nextLine();
        boolean removed = users.removeIf(user -> user.email.equals(email));  // Remove user if found
        if (removed) {
            saveUsers(users);  // Save the updated list to file
            activityLog.add("Deleted user: " + email);  // Log the deletion activity
        } else {
            System.out.println("User not found.");  // Handle case where user is not found
        }
    }

    // Method to generate and display a report on users and activities
    static void generateReport() {
        List<User> users = readUsers();  // Read users from file
        System.out.println("Total users: " + users.size());  // Display the total number of users
        System.out.println("Activity log:");
        for (String activity : activityLog) {
            System.out.println("- " + activity);  // Print each activity in the log
        }
        activityLog.add("Generated report");  // Log the report generation activity
    }

    // Method to calculate the duration for a user to reach their savings goal
    static void calculateSavingsDuration() {
        List<User> users = readUsers();  // Read users from file
        System.out.print("Enter email of user to calculate for: ");
        String email = scanner.nextLine();

        for (User user : users) {
            if (user.email.equals(email)) {
                double monthlySavings = user.income - user.expenses;  // Calculate monthly savings
                if (monthlySavings <= 0) {
                    System.out.println("This user cannot save money with current income and expenses.");
                    return;
                }
                int months = (int) Math.ceil(user.savingsGoal / monthlySavings);  // Calculate the number of months required to reach the savings goal
                System.out.println("It will take approximately " + months + " month(s) to reach the savings goal.");
                activityLog.add("Calculated savings duration for: " + email);  // Log the savings duration calculation
                return;
            }
        }
        System.out.println("User not found.");  // Handle case where user is not found
    }

    // Method to exit the application
    static void exitApp() {
        System.out.println("Exiting application. Goodbye!");
        activityLog.add("Exited application");  // Log the exit activity
    }
}
