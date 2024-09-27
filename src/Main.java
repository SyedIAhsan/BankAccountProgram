import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Calendar;

public class Main {

	public static void main(String[] args) throws IOException {
		// Variables

		char choice;
		boolean notDone = true;

		Bank bank = new Bank();

		// file pathing for output and scanner
		File initialCasesFile = new File("input.txt");
		Scanner fileSC = new Scanner(initialCasesFile);

		File testFile3 = new File("testCasesFile.txt");
		Scanner sc = new Scanner(testFile3);

		File outputFile = new File("output.txt");
		PrintWriter outFile = new PrintWriter(outputFile);

		readAccts(bank, fileSC);
		printAccts(bank, outFile);

		do {
			showMenu();
			choice = sc.next().charAt(0);
			switch (choice) {
			case 'q':
			case 'Q':
				notDone = false;
				printAccts(bank, outFile);
				break;
			case 'b':
			case 'B':
				checkBalance(sc, outFile, bank);
				break;
			case 'd':
			case 'D':
				deposit(sc, outFile, bank);
				break;
			case 'w':
			case 'W':
				withdraw(sc, outFile, bank);
				break;
			case 'c':
			case 'C':
				clearCheck(sc, outFile, bank);
				System.out.println("Back in the do loop");
				break;
			case 'n':
			case 'N':
				newAcct(sc, outFile, bank);
				break;
			case 'x':
			case 'X':
				deleteAcct(sc, outFile, bank);
				break;
			case 'h':
			case 'H':
				infoandhistory(sc, outFile, bank);
				break;
			case 'i':
			case 'I':
				info(sc, outFile, bank);
				break;
			case 's':
			case 'S':
				closeAcct(sc, outFile, bank);
				break;
			case 'r':
			case 'R':
				openAcct(sc, outFile, bank);
				break;
			default:
				outFile.println("Error: " + choice + " is an invalid selection -  try again");
				outFile.println();
				outFile.flush();
				break;
			}
		} while (notDone);
		{
			System.out.println("The program is terminating");
			System.out.println(bank.getArraySize());
			notDone = false;
		}
		outFile.close();
	}

	// Method to show the menu
	public static void showMenu() {
		System.out.println();
		System.out.println("Select one of the following transactions:");
		System.out.println("\t****************************");
		System.out.println("\t    List of Choices         ");
		System.out.println("\t****************************");
		System.out.println("\t     I -- Account Information");
		System.out.println("\t     H -- Account Information and History");
		System.out.println("\t     W -- Withdrawal");
		System.out.println("\t     D -- Deposit");
		System.out.println("\t     N -- New Account");
		System.out.println("\t     B -- Balance Inquiry");
		System.out.println("\t     S -- Close Account");
		System.out.println("\t     R -- Reopen a closed account");
		System.out.println("\t     X -- Delete Account");
		System.out.println("\t     Q -- Quit");
		System.out.println();
		System.out.print("\tEnter your selection: ");
		System.out.println();
	}

	// Method to read in accounts
	public static void readAccts(Bank bank, Scanner fileSC) {

		int count = 0;
		int maxAccts = bank.getMAX_ACCTS();
		String tempStr;
		String line;

		while (fileSC.hasNext() && count < maxAccts) {

			line = fileSC.nextLine();
			String[] tokens = line.split(" ");

			Name readName = new Name(tokens[0], tokens[1]);
			Depositor readDepositor = new Depositor(tokens[2], readName);
			int acctNum = Integer.parseInt(tokens[3]);
			double acctBal = Double.parseDouble(tokens[5]);
			String acctType = tokens[4];
			boolean isOpen = true;

			if (acctType.equals("CD")) {
				String[] dateTokens = tokens[6].split("/");
				int day = Integer.parseInt(dateTokens[0]);
				int month = Integer.parseInt(dateTokens[1]);
				int year = Integer.parseInt(dateTokens[2]);
				Calendar maturityDate = Calendar.getInstance();
				maturityDate.set(year, month - 1, day);

				CDAccount newAccount = new CDAccount(acctNum, acctBal, acctType, readDepositor, maturityDate, isOpen,
						new ArrayList<>());
				bank.addAccount(newAccount);
			} else if (acctType.equals("Savings")) {
				SavingsAccount newAccount = new SavingsAccount(acctNum, acctBal, acctType, readDepositor, isOpen,
						new ArrayList<>());
				bank.addAccount(newAccount);
			} else if (acctType.equals("Checking")) {
				CheckingAccount newAccount = new CheckingAccount(acctNum, acctBal, acctType, readDepositor, isOpen,
						new ArrayList<>());
				bank.addAccount(newAccount);
			}

			count++;
		}
	}

	// Method to print accounts to output file
	public static void printAccts(Bank bank, PrintWriter outFile) {

		Name myName;
		Depositor myDepositor;

		outFile.println();
		outFile.println("\t\tDatabase of Bank Accounts");
		outFile.println();
		outFile.println(
				"Account Number  First Name   Last Name   SSN         Account Type       Balance     Status   Maturity Date");
		System.out
				.println("Account Number  First Name   Last Name   SSN          Account Type   Balance  Maturity Date");

		for (int index = 0; index < bank.getArraySize(); index++) {
			Account account = bank.getSingleAccount(index);
			if (account != null) {
				System.out.println("printing");
				outFile.print(account.toString());
				// System.out.printf("%-15d %-12s %-11s %-12s %-14s $%10.2f %-15s%n", acctNum,
				// firstName, lastName, SSN,
				// acctType, balance, maturityDateSTRING);
			}
		}
		outFile.println();
		outFile.printf("Total funds in all accounts: $%.2f%n", Bank.getTotalAmountInAllAccts());
		outFile.printf("Savings accounts: $%.2f%n", Bank.getTotalAmountInSavingsAccts());
		outFile.printf("CD accounts: $%.2f%n", Bank.getTotalAmountInCDAccts());
		outFile.printf("Checking accounts: $%.2f%n", Bank.getTotalAmountInCheckingAccts());
		outFile.println();
	}

	// Method to Check Bal
	public static void checkBalance(Scanner sc, PrintWriter outFile, Bank bank) {
		System.out.println("Please enter the account number for BALANCE CHECK");
		int acctNum = sc.nextInt();
		TransactionRec transRec = new TransactionRec();
		Calendar currentDate = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket(acctNum, currentDate, "Balance Inquiry", 0, null);
		transRec = bank.checkBal(ticket, sc, outFile);
		if (transRec.getSuccessIndicatorFlag()) {
			outFile.println(transRec.toString());
			outFile.println();
		} else {
			outFile.println(transRec.toString());
			outFile.println();
		}
	}

	// Method to deposit balance
	public static void deposit(Scanner sc, PrintWriter outFile, Bank bank) {
		System.out.println("Please enter the account number for Deposit");
		int acctNum = sc.nextInt();
		System.out.println("Please enter amount to be deposited");
		double dAmt = sc.nextDouble();

		boolean isCD;
		isCD = bank.isCD(acctNum);
		TransactionTicket ticket;
		TransactionRec transRec = new TransactionRec();
		Calendar currentDate = Calendar.getInstance();

		if (isCD) {
			Calendar cdDate = bank.date2find(acctNum);
			ticket = new TransactionTicket(acctNum, currentDate, "Deposit", dAmt, cdDate);
			transRec = bank.deposit(ticket, outFile, sc, dAmt);
			if (transRec.getSuccessIndicatorFlag()) {
				outFile.println(transRec.toString());
				outFile.println();
			} else {
				outFile.println(transRec.toString());
				outFile.println();
			}
		} else {
			ticket = new TransactionTicket(acctNum, currentDate, "Deposit", dAmt, null);
			transRec = bank.deposit(ticket, outFile, sc, dAmt);
			if (transRec.getSuccessIndicatorFlag()) {
				outFile.println(transRec.toString());
				outFile.println();
			} else {
				outFile.println(transRec.toString());
				outFile.println();
			}
		}
	}

	// method to withdraw balance
	public static void withdraw(Scanner sc, PrintWriter outFile, Bank bank) {
		System.out.println("Please enter the account number for Withdrawal");
		int acctNum = sc.nextInt();
		System.out.println("Please enter amount to be withdrawn");
		double wAmt = sc.nextDouble();

		boolean isCD;
		isCD = bank.isCD(acctNum);
		TransactionTicket ticket;
		TransactionRec transRec = new TransactionRec();
		Calendar currentDate = Calendar.getInstance();

		if (isCD) {
			Calendar cdDate = bank.date2find(acctNum);
			ticket = new TransactionTicket(acctNum, currentDate, "Withdrawal", wAmt, cdDate);
			transRec = bank.withdrawal(ticket, outFile, sc, wAmt);
			if (transRec.getSuccessIndicatorFlag()) {
				outFile.println(transRec.toString());
				outFile.println();
			} else {
				outFile.println(transRec.toString());
				outFile.println();
			}
		} else {
			ticket = new TransactionTicket(acctNum, currentDate, "withdrawal", wAmt, null);
			transRec = bank.withdrawal(ticket, outFile, sc, wAmt);
			if (transRec.getSuccessIndicatorFlag()) {
				outFile.println(transRec.toString());
				outFile.println();
			} else {
				outFile.println(transRec.toString());
				outFile.println();
			}
		}
	}

	// method to ClearCheck
	public static void clearCheck(Scanner sc, PrintWriter outFile, Bank bank) {
		System.out.println("Please enter the account number of the check writer(must be a checking account)");
		int newCheckWriter = sc.nextInt();
		System.out.println("Please enter the check amount");
		double newCheckAmt = sc.nextDouble();

		System.out.println("Please enter the date of check (dd/mm/yyyy)");
		String[] checkDateString = sc.next().split("/");

		int day = Integer.parseInt(checkDateString[0]);
		int month = Integer.parseInt(checkDateString[1]);
		int year = Integer.parseInt(checkDateString[2]);
		Calendar newCheckDate = Calendar.getInstance();
		newCheckDate.set(year, month - 1, day);

		Check newCheck = new Check(newCheckWriter, newCheckAmt, newCheckDate);
		TransactionRec transRec = new TransactionRec();

		boolean isChecking = bank.isChecking(newCheckWriter);
		Calendar currentDate = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket();
		ticket = new TransactionTicket(newCheckWriter, currentDate, "Check Clear", newCheckAmt, null);

		transRec = bank.clearCheck(newCheck, ticket);

		if (transRec.getSuccessIndicatorFlag()) {
			outFile.println(transRec.toString());
			outFile.println();
		} else {
			outFile.println(transRec.toString());
			outFile.println();
		}
	}

	// Merthod to get acct history and info from SSN
	public static void infoandhistory(Scanner sc, PrintWriter outFile, Bank bank) {
		outFile.println("Account information and history inquiry");
		System.out.print("Enter the SSN to search for account information: ");
		String ssn = sc.next();

		outFile.println("Social Security Number provided: " + ssn);
		int numberAccts = 0;
		boolean found = false;
		int foundAcctsCounter = 0;

		for (int i = 0; i < bank.getArraySize(); i++) {
			found = bank.acctInfoAndHistoryExist(ssn, i);

			if (found) {
				foundAcctsCounter++;
				Account account = bank.acctInfoAndHistory(ssn, i);
				outFile.println(
						"Account Number  First Name   Last Name   SSN         Account Type       Balance     Status   Maturity Date");
				outFile.println(account.toString());

				outFile.println();
				outFile.println("***** Account Transactions ***** ");
				outFile.println(
						"Date               Transaction          Amount      Status          Balance             Reason For Failure");

				if (account.getTransactionHistory().size() <= 0) {
					outFile.println("No transactions on this account");
				}
				for (int index = 0; index < account.getTransactionHistory().size(); index++) {
					TransactionRec history = account.getTransactionHistory().get(index);
					String str = "Done";
					if (history.getSuccessIndicatorFlag()) {
						str = "Done";
					} else {
						str = "Failed";
					}
					outFile.println(history.toString("h", str));

				}
				outFile.println();
			}
		}

		if (foundAcctsCounter >= 1) {
			outFile.println();
			outFile.println(foundAcctsCounter + " accounts were found.");
			outFile.println();
		} else {
			outFile.println("ERROR!: No account found for the provided SSN.");
			outFile.println();
		}

	}

	// Merthod to get acct info from SSN
	public static void info(Scanner sc, PrintWriter outFile, Bank bank) {
		outFile.println("Account information inquiry");
		System.out.print("Enter the SSN to search for account information: ");
		String ssn = sc.next();

		outFile.println("Social Security Number provided: " + ssn);
		int numberAccts = 0;
		boolean found = false;
		int foundAcctsCounter = 0;

		for (int i = 0; i < bank.getArraySize(); i++) {
			found = bank.acctInfoAndHistoryExist(ssn, i);

			if (found) {
				foundAcctsCounter++;
				Account account = bank.acctInfoAndHistory(ssn, i);
				String str2;
				if (account.getIsOpen()) {
					str2 = "open";
				} else {
					str2 = "closed";
				}
				outFile.println(
						"Account Number  First Name   Last Name   SSN         Account Type       Balance     Status   Maturity Date");
				outFile.println(account.toString());
				outFile.println();
			}
		}

		if (foundAcctsCounter >= 1) {
			outFile.println(foundAcctsCounter + " accounts were found.");
			outFile.println();
		} else {
			outFile.println("ERROR!: No account found for the provided SSN.");
			outFile.println();
		}

	}

	// Method to create new acct
	public static void newAcct(Scanner sc, PrintWriter outFile, Bank bank) {

		System.out.println("Please enter the account number to be CREATED");
		int acctNum = sc.nextInt();
		System.out.println("Please enter your first name");
		String firstName = sc.next();
		System.out.println("Please enter your last name");
		String lastName = sc.next();
		System.out.println("Please enter your social security number(without dashes)");
		String newSSN = sc.next();
		System.out.println("Please enter the amount of initial deposit ($)");
		double newAcctBal = sc.nextDouble();
		System.out.println("Please choose an account type: Checking, Saving, CD");
		String newAcctType = sc.next();

		Calendar newMaturityDate = Calendar.getInstance();

		if (newAcctType.equals("CD")) {
			System.out.println("Please enter the maturity date of the CD account in the DD/MM/YYYY format");
			String[] dateTokens = sc.next().split("/");
			int day = Integer.parseInt(dateTokens[0]);
			int month = Integer.parseInt(dateTokens[1]);
			int year = Integer.parseInt(dateTokens[2]);
			newMaturityDate.set(year, month - 1, day);
		}

		TransactionRec transRec = bank.newAcct(acctNum, firstName, lastName, newSSN, newAcctBal, newAcctType,
				newMaturityDate);

		if (transRec.getSuccessIndicatorFlag()) {
			outFile.println(transRec.toString(firstName, lastName, newSSN));
			outFile.println();
		} else {
			outFile.println(transRec.toString());
		}
	}

	// method to delete acct
	public static void deleteAcct(Scanner sc, PrintWriter outFile, Bank bank) {

		System.out.println("Please enter the account number to be CREATED");
		int acctNum = sc.nextInt();

		TransactionRec transRec = bank.deleteAcct(acctNum);

		if (transRec.getSuccessIndicatorFlag()) {
			outFile.println(transRec.toString());
			outFile.println(" ");
		} else {
			outFile.println(transRec.toString());
			outFile.println(" ");
		}
	}

	// Method to Close an open Account
	public static void closeAcct(Scanner sc, PrintWriter outFile, Bank bank) {
		System.out.println(
				"Please enter the account number you would like to close (please remember no transactions will be allowed)");
		int acctNum = sc.nextInt();
		TransactionRec transRec = new TransactionRec();
		Calendar currentDate = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket(acctNum, currentDate, "Account Closure", 0, null);
		transRec = bank.closeAcct(ticket);
		if (transRec.getSuccessIndicatorFlag()) {
			outFile.println(transRec.toString());
			outFile.println();
		} else {
			outFile.println(transRec.toString());
			outFile.println();
		}

	}

	// Method to re-open an existing closed Account
	public static void openAcct(Scanner sc, PrintWriter outFile, Bank bank) {
		System.out.println(
				"Please enter the account number you would like to re-open(must be existing account currently closed)");
		int acctNum = sc.nextInt();
		TransactionRec transRec = new TransactionRec();
		Calendar currentDate = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket(acctNum, currentDate, "Account Re-Open", 0, null);
		transRec = bank.openAcct(ticket);
		if (transRec.getSuccessIndicatorFlag()) {
			outFile.println(transRec.toString());
			outFile.println();
		} else {
			outFile.println(transRec.toString());
			outFile.println();
		}
	}

}
