package com.company;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;



public class BudgetPlanner {
    static final String FILE_NAME = "users.csv";
    static final Scanner scanner = new Scanner(System.in);
    static final List<String> activityLog = new ArrayList<String>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Personal Budget Planner ---");
            System.out.println("1. Create User\n2. View Users\n3. Update User\n4. Delete User\n5. Generate Report\n6. Calculate Savings Duration\n7. Exit");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            if ("1".equals(option)) {
                createUser();
            } else if ("2".equals(option)) {
                viewUsers();
            } else if ("3".equals(option)) {
                updateUser();
            } else if ("4".equals(option)) {
                deleteUser();
            } else if ("5".equals(option)) {
                generateReport();
            } else if ("6".equals(option)) {
                calculateSavingsDuration();
            } else if ("7".equals(option)) {
                exitApp();
                return;
            }
            else {
                System.out.println("Invalid option.");
            }
        }
    }

    static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", email);
    }

    static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                users.add(User.fromCSV(line));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return users;
    }

    static void saveUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                pw.println(user.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    static void createUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }
        System.out.print("Enter income: ");
        double income = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter expenses: ");
        double expenses = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter savings goal: ");
        double savingsGoal = Double.parseDouble(scanner.nextLine());

        List<User> users = readUsers();
        users.add(new User(name, email, income, expenses, savingsGoal));
        saveUsers(users);
        activityLog.add("User created: " + email);
    }

    static void viewUsers() {
        List<User> users = readUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : users) {
                System.out.println(user.name + " | " + user.email + " | Income: " + user.income + ", Expenses: " + user.expenses + ", Savings Goal: " + user.savingsGoal);
            }
        }
        activityLog.add("Viewed users");
    }

    static void updateUser() {
        List<User> users = readUsers();
        System.out.print("Enter email of user to update: ");
        String email = scanner.nextLine();
        for (User user : users) {
            if (user.email.equals(email)) {
                System.out.print("New income: ");
                user.income = Double.parseDouble(scanner.nextLine());
                System.out.print("New expenses: ");
                user.expenses = Double.parseDouble(scanner.nextLine());
                System.out.print("New savings goal: ");
                user.savingsGoal = Double.parseDouble(scanner.nextLine());
                saveUsers(users);
                activityLog.add("Updated user: " + email);
                return;
            }
        }
        System.out.println("User not found.");
    }

    static void deleteUser() {
        List<User> users = readUsers();
        System.out.print("Enter email of user to delete: ");
        String email = scanner.nextLine();
        boolean removed = users.removeIf(user -> user.email.equals(email));
        if (removed) {
            saveUsers(users);
            activityLog.add("Deleted user: " + email);
        } else {
            System.out.println("User not found.");
        }
    }

    static void generateReport() {
        List<User> users = readUsers();
        System.out.println("Total users: " + users.size());
        System.out.println("Activity log:");
        for (String activity : activityLog) {
            System.out.println("- " + activity);
        }
        activityLog.add("Generated report");
    }

    static void calculateSavingsDuration() {
        List<User> users = readUsers();
        System.out.print("Enter email of user to calculate for: ");
        String email = scanner.nextLine();

        for (User user : users) {
            if (user.email.equals(email)) {
                double monthlySavings = user.income - user.expenses;
                if (monthlySavings <= 0) {
                    System.out.println("This user cannot save money with current income and expenses.");
                    return;
                }
                int months = (int) Math.ceil(user.savingsGoal / monthlySavings);
                System.out.println("It will take approximately " + months + " month(s) to reach the savings goal.");
                activityLog.add("Calculated savings duration for: " + email);
                return;
            }
        }
        System.out.println("User not found.");
    }


    static void exitApp() {
        System.out.println("Exiting application. Goodbye!");
        activityLog.add("Exited application");
    }
}

