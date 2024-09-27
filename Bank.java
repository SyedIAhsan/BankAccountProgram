import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Bank {
	final int MAX_NUM = 50;
	private ArrayList<Account> accounts;

	// Static variables
	private static double totalAmountInSavingsAccts =0;
	private static double totalAmountInCDAccts =0;
	private static double totalAmountInCheckingAccts =0;
	private static double totalAmountInAllAccts =0;

	// Constructor
	public Bank() {
		accounts = new ArrayList<>();
	}

	// setters
	private void setAccounts(ArrayList<Account> accts) {
		accounts = new ArrayList<>(accts);
	}

	// Getters for static variables
	public static double getTotalAmountInSavingsAccts() {
		return totalAmountInSavingsAccts;
	}

	public static double getTotalAmountInCheckingAccts() {
		return totalAmountInCheckingAccts;
	}

	public static double getTotalAmountInCDAccts() {
		return totalAmountInCDAccts;
	}

	public static double getTotalAmountInAllAccts() {
		return totalAmountInAllAccts;
	}

	// getters
	public int getMAX_ACCTS() {
		return MAX_NUM;
	}

	// methods of the Bank Class

	// add Account
	public void addAccount(Account account) {
		if (accounts.size() < MAX_NUM) {
			accounts.add(account);
			if (account.equals("CD")) {
				addCD(account.getAcctBal());
			} else if (account.equals("Checking")) { 
				addChecking(account.getAcctBal());
			} else if (account.equals("Savings")){
				addSavings(account.getAcctBal());
			}
			addTotalAccts();
		} else {
			System.out.println("Error: bank is full");
		}
	}

	// Method to find existing accounts
	private int findAcct(int accountNum) {
		for (int index = 0; index < accounts.size(); index++) {
			if (accounts.get(index) != null && accounts.get(index).equals(accountNum) ) {
				return index;
			}
		}
		return -1;
	}

	// Method to check balance
	public TransactionRec checkBal(TransactionTicket ticket, Scanner sc, PrintWriter outFile) {
		int acctNum = ticket.getAcctNum();
		int index = findAcct(acctNum);
		if (!(index == -1)) {
			if (accounts.get(index).getIsOpen()) {
				TransactionRec transRec = new TransactionRec();
				transRec = accounts.get(index).checkBalance(ticket, accounts.get(index));
				accounts.get(index).addHistory(transRec);
				return transRec;
			} else {
				TransactionRec transRec = new TransactionRec(ticket, false,
						"Account is closed, no transactions allowed", accounts.get(index).getAcctType(),
						accounts.get(index).getAcctBal(), accounts.get(index).getAcctBal(), null);
				accounts.get(index).addHistory(transRec);
				return transRec;
			}
		} else {
			TransactionRec transRec = new TransactionRec(ticket, false, "Account Number does not exist", null, 0, 0,
					null);
			return transRec;
		}
	}

	// Method to withdraw balance
	public TransactionRec withdrawal(TransactionTicket ticket, PrintWriter outFile, Scanner sc, double wAmt) {
		int acctNum = ticket.getAcctNum();
		int index = findAcct(acctNum);
		double amount = wAmt;
		if (index != -1) {
			if (accounts.get(index).getIsOpen()) {
				TransactionRec transRec = new TransactionRec();
				transRec = accounts.get(index).withdrawBalance(amount, ticket, accounts.get(index), sc);
				accounts.get(index).addHistory(transRec);
				
				if (transRec.getSuccessIndicatorFlag()) {
					if (accounts.get(index).equals("CD")) {
						subCD(amount);
					} else if (accounts.get(index).equals("Checking")) { 
						subChecking(amount);
					} else if (accounts.get(index).equals("Savings")){
						subSavings(amount);
					}
					addTotalAccts();
				}
				
				return transRec;
			} else {
				TransactionRec transRec = new TransactionRec(ticket, false,
						"Account is closed, no transactions allowed", accounts.get(index).getAcctType(),
						accounts.get(index).getAcctBal(), accounts.get(index).getAcctBal(), null);
				accounts.get(index).addHistory(transRec);
				return transRec;
			}
		} else {
			TransactionRec transRec = new TransactionRec(ticket, false, "Account Number does not exist", null, 0, 0,
					null);
			return transRec;
		}
	}

	// Method to deposit balance
	public TransactionRec deposit(TransactionTicket ticket, PrintWriter outFile, Scanner sc, Double dAmt) {
		int acctNum = ticket.getAcctNum();
		int index = findAcct(acctNum);
		double depAmt = dAmt;
		if (index != -1) {
			if (accounts.get(index).getIsOpen()) {
				TransactionRec transRec = new TransactionRec();
				transRec = accounts.get(index).depositBalance(depAmt, ticket, accounts.get(index), sc);
				accounts.get(index).addHistory(transRec);
				
				if (transRec.getSuccessIndicatorFlag()) {
					if (accounts.get(index).equals("CD")) {
						addCD(depAmt);
					} else if (accounts.get(index).equals("Checking")) { 
						addChecking(depAmt);
					} else if (accounts.get(index).equals("Savings")){
						addSavings(depAmt);
					}
					addTotalAccts();
				}
				
				return transRec;
			} else {
				TransactionRec transRec = new TransactionRec(ticket, false,
						"Account is closed, no transactions allowed", accounts.get(index).getAcctType(),
						accounts.get(index).getAcctBal(), accounts.get(index).getAcctBal(), null);
				accounts.get(index).addHistory(transRec);
				return transRec;
			}
		} else {
			TransactionRec transRec = new TransactionRec(ticket, false, "Account Number does not exist", null, 0, 0,
					null);
			return transRec;
		}
	}

	// Method to create new account
	public TransactionRec newAcct(int acctNum, String firstName, String lastName, String newSSN, double newAcctBal,
			String newAcctType, Calendar newMatDate) {

		Calendar currentDate = Calendar.getInstance();
		TransactionRec transRec = new TransactionRec();
		if (acctNum >= 100000 && acctNum <= 999999) {
			int valid = findAcct(acctNum);
			if (valid != -1) {
				TransactionTicket ticket = new TransactionTicket(acctNum, currentDate, "New Account Creation", 0.0,
						null);
				transRec = new TransactionRec(ticket, false,
						"New account could not be created due to number already existing!", " ", 0, 0, null);
				accounts.get(valid).addHistory(transRec);
				return transRec;
			} else {
				if (newAcctType.equalsIgnoreCase("CD")) {
					Name newName = new Name(firstName, lastName);
					Depositor newDepositor = new Depositor(newSSN, newName);
					CDAccount newAccount = new CDAccount(acctNum, newAcctBal, newAcctType, newDepositor, newMatDate, true,
							new ArrayList<>());
					accounts.add(newAccount);

					TransactionTicket ticket = new TransactionTicket(acctNum, currentDate, "New Account Creation", 0.0,
							null);
					transRec = new TransactionRec(ticket, true, " ", newAcctType, 0, newAcctBal, newMatDate);
					int lastIndex = accounts.size() - 1;
					accounts.get(lastIndex).addHistory(transRec);
				
				} else if (newAcctType.equalsIgnoreCase("Savings")){
					Name newName = new Name(firstName, lastName);
					Depositor newDepositor = new Depositor(newSSN, newName);
					SavingsAccount newAccount = new SavingsAccount(acctNum, newAcctBal, newAcctType, newDepositor, true,
							new ArrayList<>());
					accounts.add(newAccount);

					TransactionTicket ticket = new TransactionTicket(acctNum, currentDate, "New Account Creation", 0.0,
							null);
					transRec = new TransactionRec(ticket, true, " ", newAcctType, 0, newAcctBal, null);
					int lastIndex = accounts.size() - 1;
					accounts.get(lastIndex).addHistory(transRec);
				} else if (newAcctType.equalsIgnoreCase("Checking")){
					Name newName = new Name(firstName, lastName);
					Depositor newDepositor = new Depositor(newSSN, newName);
					CheckingAccount newAccount = new CheckingAccount(acctNum, newAcctBal, newAcctType, newDepositor, true,
							new ArrayList<>());
					accounts.add(newAccount);
					TransactionTicket ticket = new TransactionTicket(acctNum, currentDate, "New Account Creation", 0.0,
							null);
					transRec = new TransactionRec(ticket, true, " ", newAcctType, 0, newAcctBal, null);
					int lastIndex = accounts.size() - 1;
					accounts.get(lastIndex).addHistory(transRec);
				}
				
				int lastIndex = accounts.size() - 1;
				if (transRec.getSuccessIndicatorFlag()) {
					if (accounts.get(lastIndex).equals("CD")) {
						addCD(newAcctBal);
					} else if (accounts.get(lastIndex).equals("Checking")) { 
						addChecking(newAcctBal);
					} else if (accounts.get(lastIndex).equals("Savings")) {
						addSavings(newAcctBal);
					}
					addTotalAccts();
				}
				return transRec;
			}
		} else {
			TransactionTicket ticket = new TransactionTicket(acctNum, currentDate, "New Account Creation", 0.0, null);
			transRec = new TransactionRec(ticket, false, "Invalid Input for account number", " ", 0, 0, null);
			return transRec;
		}
	}

	// Method to delete accounts
	public TransactionRec deleteAcct(int acctNum) {
		Calendar currentDate = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket(acctNum, currentDate, "Account Deletion", 0, null);
		TransactionRec transRec = new TransactionRec();

		int index = findAcct(acctNum);
		if (index != -1) {
			if (accounts.get(index).getAcctBal() > 0) {
				transRec = new TransactionRec(ticket, false,
						"Account still have a positive balance please withdraw all funds before deleting account ",
						accounts.get(index).getAcctType(), accounts.get(index).getAcctBal(),
						accounts.get(index).getAcctBal(), null);
				accounts.get(index).addHistory(transRec);
				return transRec;
			} else {
				transRec = new TransactionRec(ticket, true, " ", accounts.get(index).getAcctType(), 0, 0, null);
				
				if (transRec.getSuccessIndicatorFlag()) {
					if (accounts.get(index).equals("CD")) {
						subCD(accounts.get(index).getAcctBal());
					} else if (accounts.get(index).equals("Checking")) { 
						subChecking(accounts.get(index).getAcctBal());
					} else if (accounts.get(index).equals("Savings")){
						subSavings(accounts.get(index).getAcctBal());
					}
					addTotalAccts();
				}
				
				accounts.remove(index);
				return transRec;
			}
		} else {
			transRec = new TransactionRec(ticket, false, "Invalid Input or account does not exist", " ", 0, 0, null);
			return transRec;
		}
	}

	// Method to get info on account
	public Account acctInfoAndHistory(String ssn, int i) {
		boolean found = false;
		Account account = new Account();
		if (accounts.get(i).getAcctUserInfo().getSSN().equals(ssn)) {
			found = true;
			account = accounts.get(i);
		}
		return account;
	}

	// Helper method
	public boolean acctInfoAndHistoryExist(String ssn, int i) {

		if (accounts.get(i).getAcctUserInfo().getSSN().equals(ssn)) {
			return true;
		} else {
			return false;
		}
	}

	// MEthod to clear a check
	public TransactionRec clearCheck(Check check, TransactionTicket ticket) {
		int index = findAcct(check.getAcctNumCheck());

		if (!(index == -1)) {
			if (accounts.get(index).getIsOpen()) {
				if(accounts.get(index) instanceof CheckingAccount) {
					TransactionRec transRec = new TransactionRec();
					transRec = accounts.get(index).clearCheck(check, ticket, accounts.get(index));
					accounts.get(index).addHistory(transRec);
					
					if (transRec.getSuccessIndicatorFlag()) {
						if (accounts.get(index).equals("CD")) {
							subCD(check.getCheckAmt());
						} else if (accounts.get(index).equals("Checking")) { 
							subChecking(check.getCheckAmt());
						} else if (accounts.get(index).equals("Savings")){
							subSavings(check.getCheckAmt());
						}
						addTotalAccts();
					}
					return transRec;
				} else {
					TransactionRec transRec = new TransactionRec(ticket, false,
							"Account is not a checking account", accounts.get(index).getAcctType(),
							accounts.get(index).getAcctBal(), accounts.get(index).getAcctBal(), null);
					accounts.get(index).addHistory(transRec);
					return transRec;
				}
			} else {
				TransactionRec transRec = new TransactionRec(ticket, false,
						"Account is closed, no transactions allowed", accounts.get(index).getAcctType(),
						accounts.get(index).getAcctBal(), accounts.get(index).getAcctBal(), null);
				accounts.get(index).addHistory(transRec);
				return transRec;
			}
		} else {
			TransactionRec transRec = new TransactionRec(ticket, false, "Account Number does not exist", null, 0, 0,
					null);
			return transRec;
		}
	}

	public boolean isCD(int acctNum) {
		int index = findAcct(acctNum);
		if (!(index == -1)) {
			if (accounts.get(index).equals("CD")) {
				return true;
			}
		}
		return false;
	}

	public Calendar date2find(int acctNum) {
		int index = findAcct(acctNum);
		return accounts.get(index).getMaturityDate();
	}

	public boolean isChecking(int newCheckWriter) {
		int index = findAcct(newCheckWriter);
		if (!(index == -1)) {
			if (accounts.get(index).equals("Checking")) {
				return true;
			}
		}
		return false;
	}

	public TransactionRec closeAcct(TransactionTicket ticket) {
		TransactionRec transRec = new TransactionRec();
		int acctNum = ticket.getAcctNum();
		int index = findAcct(acctNum);
		if (!(index == -1)) {
			transRec = new TransactionRec();
			transRec = accounts.get(index).closeAcct(ticket, accounts.get(index));
			accounts.get(index).addHistory(transRec);
			return transRec;
		} else {
			transRec = new TransactionRec(ticket, false, "Account Number does not exist", null, 0, 0, null);
			return transRec;
		}
	}

	public TransactionRec openAcct(TransactionTicket ticket) {
		TransactionRec transRec = new TransactionRec();
		int acctNum = ticket.getAcctNum();
		int index = findAcct(acctNum);
		if (!(index == -1)) {
			transRec = new TransactionRec();
			transRec = accounts.get(index).openAcct(ticket, accounts.get(index));
			accounts.get(index).addHistory(transRec);
			return transRec;
		} else {
			transRec = new TransactionRec(ticket, false, "Account Number does not exist", null, 0, 0, null);
			return transRec;
		}
	}

	public int getArraySize() {
		return accounts.size();
	}

	public Account getSingleAccount(int index) {
		return accounts.get(index);
	}

	// Methods to update total amounts (static variables)
	public static void addChecking(double add) {
		totalAmountInCheckingAccts += add;
	}
	
	public static void addSavings(double add) {
		totalAmountInSavingsAccts += add;
	}
	
	public static void addCD(double add) {
		totalAmountInCDAccts += add;
	}
	
	public static void addTotalAccts() {
		totalAmountInAllAccts = totalAmountInCDAccts + totalAmountInSavingsAccts + totalAmountInCheckingAccts;
	}
	
	public static void subCD(double sub) {
		totalAmountInCDAccts -= sub;
	}
	
	public static void subChecking(double sub) {
		totalAmountInCheckingAccts -= sub;
	}
	
	public static void subSavings(double sub) {
		totalAmountInSavingsAccts -= sub;
	}
	
}
