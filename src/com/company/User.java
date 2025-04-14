package com.company;

class User {
    String name;
    String email;
    double income;
    double expenses;
    double savingsGoal;

    public User(String name, String email, double income, double expenses, double savingsGoal) {
        this.name = name;
        this.email = email;
        this.income = income;
        this.expenses = expenses;
        this.savingsGoal = savingsGoal;
    }

    public String toCSV() {
        return name + "," + email + "," + income + "," + expenses + "," + savingsGoal;
    }

    public static User fromCSV(String csv) {
        String[] parts = csv.split(",");
        return new User(parts[0], parts[1], Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]), Double.parseDouble(parts[4]));
    }
}