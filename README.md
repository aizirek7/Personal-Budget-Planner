# Budget Planner README

## Overview

The **Budget Planner** is a personal finance management application that allows users to manage their finances efficiently. The application allows users to create, view, update, and delete user profiles, track their income, expenses, and savings goals, and generate detailed reports. The tool also provides a feature to calculate the duration needed for users to reach their savings goals based on their current financial situation.

The data is stored in a CSV file, which allows for easy reading and writing of user information. The application maintains an activity log for tracking user actions.

---

## Features

1. **Create User**: Add a new user with their name, email, income, expenses, and savings goal.
2. **View Users**: Display a list of all users with their financial details.
3. **Update User**: Modify the income, expenses, and savings goal of an existing user.
4. **Delete User**: Remove a user from the system.
5. **Generate Report**: Generate a report that lists the total number of users and an activity log.
6. **Calculate Savings Duration**: Calculate how many months it will take for a user to reach their savings goal based on their income and expenses.
7. **Exit**: Exit the application.

---

## Prerequisites

Before running the application, make sure you have the following:

- **Java Development Kit (JDK)** version 8 or higher installed.
- A CSV file named `users.csv` located in the root directory of the project. The CSV file stores user data and is automatically read and written by the program.

---

## Running the Application

To run the application:

1. Clone or download the project files to your local machine.
2. Navigate to the project folder in your terminal or command prompt.
3. Compile the Java files using the following command:

   ```bash
   javac BudgetPlanner.java
   ```

4. Run the application using the following command:

   ```bash
   java BudgetPlanner
   ```

This will launch the Budget Planner in the terminal, where you can interact with the application.

---

## Functions and Methods

### 1. `createUser()`
   - Creates a new user after validating their name, email, income, expenses, and savings goal.
   - Ensures that the email is valid and not already taken.

### 2. `viewUsers()`
   - Displays all existing users and their financial details.

### 3. `updateUser()`
   - Allows you to update the income, expenses, and savings goal of an existing user by specifying their email.

### 4. `deleteUser()`
   - Deletes a user by their email address.

### 5. `generateReport()`
   - Generates a report with the total number of users and an activity log.

### 6. `calculateSavingsDuration()`
   - Calculates and displays the number of months needed for a user to reach their savings goal based on their income and expenses.

### 7. `exitApp()`
   - Exits the application and logs the activity.

---

## File Format

The user data is stored in a CSV file named **`users.csv`**, with each user entry having the following format:

```
name,email,income,expenses,savingsGoal
```

### Example:

```
John Doe,john.doe@example.com,5000,2000,10000
Jane Smith,jane.smith@example.com,6000,2500,12000
```

---

## Error Handling

- The application performs input validation for the email format and checks for existing email addresses.
- If an invalid number is entered for income, expenses, or savings goal, the user will be prompted to enter a valid positive number.
- If an operation (e.g., viewing or updating users) fails due to missing or incorrect data, appropriate error messages are displayed.

---

## Activity Log

Every action taken by the user is logged and can be viewed in the activity log. For example:

- User creation
- User update
- User deletion
- Report generation
- Calculation of savings duration
- Application exit

The activity log is saved in the memory and displayed when generating a report.

## Outputs and test cases

✅ **Test Case 1: Create User with Valid Data**

**Input:**
```
1  
Aizirek  
aizirek@example.com  
6000  
2500  
15000  
```

**Expected Output:**
```
Enter name: Aizirek  
Enter email: aizirek@example.com  
Enter income: 6000  
Enter expenses: 2500  
Enter savings goal: 15000  
```

**Result:** User Aizirek is successfully added to `users.csv`.  
Activity log includes:  
`User created: aizirek@example.com`

---

❌ **Test Case 2: Create User with Invalid Email**

**Input:**
```
1  
Aizirek  
aizirekexample.com  
aizirek@domain.com  
5000  
2200  
10000  
```

**Expected Output:**
```
Enter name: Aizirek  
Enter email: aizirekexample.com  
Invalid email format.  
Enter email: aizirek@domain.com  
Enter income: 5000  
Enter expenses: 2200  
Enter savings goal: 10000  
```

**Result:** User Aizirek is added after providing a valid email.

---

✅ **Test Case 3: View Users**

**Input:**
```
2  
```

**Expected Output:**
```
Aizirek | aizirek@example.com | Income: 6000.0, Expenses: 2500.0, Savings Goal: 15000.0  
```

**Result:** All current users are displayed, including Aizirek.

---

✅ **Test Case 4: Update User**

**Input:**
```
3  
aizirek@example.com  
7000  
2800  
18000  
```

**Expected Output:**
```
Enter email of user to update: aizirek@example.com  
New income: 7000  
New expenses: 2800  
New savings goal: 18000  
```

**Result:** Aizirek's details are updated in the file.

---

✅ **Test Case 5: Delete User**

**Input:**
```
4  
aizirek@example.com  
```

**Expected Output:**
```
Enter email of user to delete: aizirek@example.com  
```

**Result:** Aizirek is removed from the list and activity is logged.

---

✅ **Test Case 6: Calculate Savings Duration (Positive Savings)**

**Input:**
```
6  
aizirek@example.com  
```

**Expected Output:**
```
Enter email of user to calculate for: aizirek@example.com  
It will take approximately 5 month(s) to reach the savings goal.  
```

**Reasoning:**  
Income = 7000, Expenses = 2800 → Monthly Savings = 4200  
Goal = 18000 → Months = 18000 / 4200 = ~4.28 → **5 months**

---

❌ **Test Case 7: Calculate Savings Duration (Negative Savings)**

**Input:**
```
6  
aizirek@example.com  
(Assume income < expenses)  
```

**Expected Output:**
```
This user cannot save money with current income and expenses.  
```

---

✅ **Test Case 8: Generate Report**

**Input:**
```
5  
```

**Expected Output:**
```
Total users: 1  
Activity log:  
- User created: aizirek@example.com  
- Viewed users  
- Updated user: aizirek@example.com  
- Deleted user: aizirek@example.com  
- Calculated savings duration for: aizirek@example.com  
- Generated report  
```



## 📊 Presentation

You can view the project presentation on Canva here:  
👉 [Savings Planner Presentation](https://www.canva.com/design/DAGko4cSADI/MrWvkOnhBOdMHENFk4wjEg/edit?utm_content=DAGko4cSADI&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)


---
