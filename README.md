Bank Account Application

Table of Contents
- Introduction
- Features
- Architecture

Introduction
The Bank Account Application is a Java-based command-line program designed to simulate basic banking operations. It allows users to create and manage bank accounts, perform transactions such as deposits and withdrawals, view account details, and maintain transaction histories. This application demonstrates object-oriented programming principles and best practices in Java development.

Features
Create New Accounts: Open Checking, Savings, or CD accounts with unique account numbers.
View Account Details: Retrieve detailed information about specific accounts, including balance, account holder's name, and account type.
Deposit Funds: Add money to existing accounts.
Withdraw Funds: Remove money from accounts with overdraft protection.
Transaction History: Maintain and view a history of all transactions performed on an account.
Account Management: Close, reopen, or delete accounts as needed.
Check Clearing: Handle check transactions for checking accounts.
Data Persistence: Read initial account data from input.txt and write updates to output.txt.

Architecture
The application is structured using object-oriented principles with the following key classes:

Main: Handles user interaction, menu navigation, and orchestrates operations by invoking methods from other classes.
Bank: Manages a collection of Account objects, handles operations like adding, deleting, depositing, and withdrawing funds, and maintains total amounts across different account types.
Account: Base class representing a generic bank account. Subclasses include:
CheckingAccount
SavingsAccount
CDAccount
Depositor: Represents the account holder with personal information.
TransactionRec & TransactionTicket: Handle transaction records and tickets for tracking operations.
Check: Represents a check transaction for checking accounts.

GitHub: SyedIAhsan
