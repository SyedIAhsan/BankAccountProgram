
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Account {
	protected int acctNum;
	protected double acctBal;
	protected String acctType;
	protected Depositor acctDepositor;
	protected Calendar maturityDate;
	protected boolean isOpen;
	protected ArrayList<TransactionRec> transactionHistory;

	// No-args constructor
	public Account() {
		acctNum = 0;
		acctBal = 0.0;
		acctType = "";
		acctDepositor = new Depositor();
		maturityDate = null;
		isOpen = true; // By default, account is open
		transactionHistory = new ArrayList<>();
	}

	// Parameters constructor
	public Account(int n, double d, String s, Depositor depo, Calendar mD, boolean isO,
			ArrayList<TransactionRec> hist) {
		acctNum = n;
		acctBal = d;
		acctType = s;
		acctDepositor = depo;
		maturityDate = mD;
		isOpen = isO;
		transactionHistory = hist;
	}

	// Copy constructor
	public Account(Account acct) {
		this.acctNum = acct.getAcctNum();
		this.acctBal = acct.getAcctBal();
		this.acctType = acct.getAcctType();
		this.acctDepositor = acct.getAcctUserInfo();
		this.maturityDate = acct.getMaturityDate();
		this.isOpen = acct.getIsOpen();
		this.transactionHistory = acct.getTransactionHistory();
	}

	// setters
	private void setAcctNum(int num) {
		acctNum = num;
	}

	protected void setAcctBal(double num2) {
		acctBal = num2;
	}

	private void setAcctType(String s) {
		acctType = s;
	}

	private void setDepositor(Depositor d) {
		acctDepositor = d;
	}

	protected void setMaturityDate(Calendar mD) {
		maturityDate = mD;
	}

	private void setOpen(boolean status) {
		isOpen = status;
	}

	public void setTransactionHistory(ArrayList<TransactionRec> history) {
		transactionHistory = history;
	}

	// getters
	public int getAcctNum() {
		return acctNum;
	}

	public double getAcctBal() {
		return acctBal;
	}

	public String getAcctType() {
		return acctType;
	}

	public Depositor getAcctUserInfo() {
		return acctDepositor;
	}

	public Calendar getMaturityDate() {
		return maturityDate;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public ArrayList<TransactionRec> getTransactionHistory() {
		return transactionHistory;
	}

	// Methods of the Account class
	// Method to check the balance
	public TransactionRec checkBalance(TransactionTicket ticket, Account account) {
		TransactionRec transRec = new TransactionRec(ticket, true, " ", account.getAcctType(), account.getAcctBal(),
				account.getAcctBal(), null);
		return transRec;
	}

	// Method to deposit account balance
	public TransactionRec depositBalance(double amount, TransactionTicket ticket, Account accounts, Scanner sc) {
		TransactionRec transRec = new TransactionRec();

		if (amount > 0) {
			accounts.setAcctBal(accounts.getAcctBal() + amount);
			transRec = new TransactionRec(ticket, true, " ", accounts.getAcctType(), accounts.getAcctBal() - amount,
					accounts.getAcctBal(), null);
			return transRec;
		} else {
			transRec = new TransactionRec(ticket, false, "Invalid Deposit amount", accounts.getAcctType(),
					accounts.getAcctBal(), accounts.getAcctBal(), null);
			return transRec;
		}
	}

	public TransactionRec withdrawBalance(double amount, TransactionTicket ticket, Account accounts, Scanner sc) {
		TransactionRec transRec = new TransactionRec();
		if (amount > 0) {
			if (accounts.getAcctBal() >= amount) {

				accounts.setAcctBal(accounts.getAcctBal() - amount);
				transRec = new TransactionRec(ticket, true, " ", accounts.getAcctType(), accounts.getAcctBal() + amount,
						accounts.getAcctBal(), null);
				return transRec;

			} else {
				transRec = new TransactionRec(ticket, false, "Insuffecient funds were detected. Ouch.",
						accounts.getAcctType(), accounts.getAcctBal(), accounts.getAcctBal(), null);
				return transRec;
			}
		} else {
			transRec = new TransactionRec(ticket, false, "Invalid Withdrawal amount", accounts.getAcctType(),
					accounts.getAcctBal(), accounts.getAcctBal(), null);
			return transRec;
		}
	}

	// Method to add transaction receipt to history of account
	public void addHistory(TransactionRec transRec) {
		transactionHistory.add(transRec);
	}

	// account closure and open methods only valid for ecxisting accts
	public TransactionRec closeAcct(TransactionTicket ticket, Account account) {
		account.setOpen(false);
		TransactionRec transRec = new TransactionRec(ticket, true, " ", account.getAcctType(), account.getAcctBal(),
				account.getAcctBal(), null);
		return transRec;
	}

	public TransactionRec openAcct(TransactionTicket ticket, Account account) {
		account.setOpen(true);
		TransactionRec transRec = new TransactionRec(ticket, true, " ", account.getAcctType(), account.getAcctBal(),
				account.getAcctBal(), null);
		return transRec;
	}

	// Overloaded toStribg method

	public String toString() {
		String maturityDateSTRING = " ";
		String status = " ";
		if (isOpen) {
			status = "Open";
		} else {
			status = "Closed";
		}

		if (acctType.equals("CD")) {
			maturityDateSTRING = maturityDate.get(Calendar.DAY_OF_MONTH) + "/" + (maturityDate.get(Calendar.MONTH) + 1)
					+ "/" + maturityDate.get(Calendar.YEAR);
		}

		String myString = String.format("%-15s %-12s %-11s %-12s %-14s $%10.2f     %-6s  %-12s%n", acctNum,
				acctDepositor.getName().getFirst(), acctDepositor.getName().getLast(), acctDepositor.getSSN(), acctType,
				acctBal, status, maturityDateSTRING);
		return myString;
	}

	// Overloaded equals method
	public boolean equals(int num) {
		if (acctNum == num) {
			return true;
		} else {
			return false;
		}
	}

	public boolean equals(String str) {
		return acctType.equalsIgnoreCase(str);
	}

	public TransactionRec clearCheck(Check check, TransactionTicket ticket, Account account) {
		return null;
	}
}
