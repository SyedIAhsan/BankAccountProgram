import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class CDAccount extends Account {
	// No-args constructor
	public CDAccount() {
		acctNum = 0;
		acctBal = 0.0;
		acctType = "";
		acctDepositor = new Depositor();
		maturityDate = null;
		isOpen = true; // By default, account is open
		transactionHistory = new ArrayList<>();
	}

	// Parameters constructor
	public CDAccount(int n, double d, String s, Depositor depo, Calendar mD, boolean isO,
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
	public CDAccount(Account acct) {
		this.acctNum = acct.getAcctNum();
		this.acctBal = acct.getAcctBal();
		this.acctType = acct.getAcctType();
		this.acctDepositor = acct.getAcctUserInfo();
		this.maturityDate = acct.getMaturityDate();
		this.isOpen = acct.getIsOpen();
		this.transactionHistory = acct.getTransactionHistory();
	}

	// Method to deposit account balance
	public TransactionRec depositBalance(double amount, TransactionTicket ticket, Account accounts, Scanner sc) {
		TransactionRec transRec = new TransactionRec();

		int year = accounts.getMaturityDate().get(Calendar.YEAR);
		int month = accounts.getMaturityDate().get(Calendar.MONTH) + 1;
		int day = accounts.getMaturityDate().get(Calendar.DAY_OF_MONTH);
		String dateString = year + "-" + month + "-" + day;

		if (Calendar.getInstance().before(accounts.getMaturityDate())) {
			transRec = new TransactionRec(ticket, false,
					"Deposit not allowed before CD account maturity date: " + dateString, accounts.getAcctType(),
					accounts.getAcctBal(), accounts.getAcctBal(), accounts.getMaturityDate());
			return transRec;
		} else {
			if (amount > 0) {
				accounts.setAcctBal(accounts.getAcctBal() + amount);

				// Setting new maturity date if the account is a cd type and the transaction was
				// successful
				int newMatDateInt = 0;
				boolean isValidInputD = false;

				while (!isValidInputD) {
					System.out.println("Please select a new maturity date for the CD account: 6, 12, 18 or 24 months");
					newMatDateInt = sc.nextInt();
					if (newMatDateInt == 6 || newMatDateInt == 12 || newMatDateInt == 18 || newMatDateInt == 24) {
						isValidInputD = true;
					} else {
						System.out.println("Invalid choice. Please enter 6, 12, 18, or 24.");
					}
				}
				Calendar currentDate = Calendar.getInstance();
				currentDate.add(Calendar.MONTH, newMatDateInt);
				accounts.setMaturityDate(currentDate);
				transRec = new TransactionRec(ticket, true, " ", accounts.getAcctType(), accounts.getAcctBal() - amount,
						accounts.getAcctBal(), currentDate);
				return transRec;
			} else {
				transRec = new TransactionRec(ticket, false, "Invalid Deposit amount", accounts.getAcctType(),
						accounts.getAcctBal(), accounts.getAcctBal(), null);
				return transRec;
			}
		}
	}

	public TransactionRec withdrawBalance(double amount, TransactionTicket ticket, Account accounts, Scanner sc) {
		TransactionRec transRec = new TransactionRec();
		
		int year = accounts.getMaturityDate().get(Calendar.YEAR);
		int month = accounts.getMaturityDate().get(Calendar.MONTH) + 1;
		int day = accounts.getMaturityDate().get(Calendar.DAY_OF_MONTH);
		String dateString = year + "-" + month + "-" + day;

		if (Calendar.getInstance().before(accounts.getMaturityDate())) {
			transRec = new TransactionRec(ticket, false,
					"withdrawal not allowed before CD account maturity date " + dateString, accounts.getAcctType(),
					accounts.getAcctBal(), accounts.getAcctBal(), accounts.getMaturityDate());
			return transRec;
		} else {
			accounts.setAcctBal(accounts.getAcctBal() - amount);
			int newMatDateInt = 0;
			boolean isValidInputD = false;

			while (!isValidInputD) {
				System.out.println("Please select a new maturity date for the CD account: 6, 12, 18 or 24 months");
				newMatDateInt = sc.nextInt();
				if (newMatDateInt == 6 || newMatDateInt == 12 || newMatDateInt == 18 || newMatDateInt == 24) {
					isValidInputD = true;
				} else {
					System.out.println("Invalid choice. Please enter 6, 12, 18, or 24.");
				}
			}
			Calendar currentDate = Calendar.getInstance();
			currentDate.add(Calendar.MONTH, newMatDateInt);
			accounts.setMaturityDate(currentDate);
			transRec = new TransactionRec(ticket, true, " ", accounts.getAcctType(), accounts.getAcctBal() + amount,
					accounts.getAcctBal(), currentDate);
			return transRec;

		}
	}

}
