import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class CheckingAccount extends Account {
	// No-args constructor
	public CheckingAccount() {
		acctNum = 0;
		acctBal = 0.0;
		acctType = "";
		acctDepositor = new Depositor();
		isOpen = true; // By default, account is open
		transactionHistory = new ArrayList<>();
	}

	// Parameters constructor
	public CheckingAccount(int n, double d, String s, Depositor depo, boolean isO, ArrayList<TransactionRec> hist) {
		acctNum = n;
		acctBal = d;
		acctType = s;
		acctDepositor = depo;
		isOpen = isO;
		transactionHistory = hist;
	}

	// Copy constructor
	public CheckingAccount(Account acct) {
		this.acctNum = acct.getAcctNum();
		this.acctBal = acct.getAcctBal();
		this.acctType = acct.getAcctType();
		this.acctDepositor = acct.getAcctUserInfo();
		this.isOpen = acct.getIsOpen();
		this.transactionHistory = acct.getTransactionHistory();
	}

	// Method to clear check balance from account balance
	public TransactionRec clearCheck(Check check, TransactionTicket ticket, Account accounts) {
		System.out.println("TES TEST TEST TEST TEST TEST");

		TransactionRec transRec = new TransactionRec();
		Calendar sixMonthsAgo = Calendar.getInstance();
		sixMonthsAgo.add(Calendar.MONTH, -6);
		int year = check.getDateCheck().get(Calendar.YEAR);
		int month = check.getDateCheck().get(Calendar.MONTH) + 1;
		int day = check.getDateCheck().get(Calendar.DAY_OF_MONTH);
		String dateString = year + "-" + month + "-" + day;

		if (accounts.getAcctBal() > check.getCheckAmt()) {
			if (check.getDateCheck().after(sixMonthsAgo)) {
				if (accounts.getAcctBal() < 2500) {
					accounts.setAcctBal(accounts.getAcctBal() - check.getCheckAmt());
					accounts.setAcctBal(accounts.getAcctBal() - 1.5);
					transRec = new TransactionRec(ticket, true, "Fee of 1.5 charged", accounts.getAcctType(), accounts.getAcctBal(),
							accounts.getAcctBal() - check.getCheckAmt(), null);
					return transRec;
				}
				accounts.setAcctBal(accounts.getAcctBal() - check.getCheckAmt());
				transRec = new TransactionRec(ticket, true, " ", accounts.getAcctType(), accounts.getAcctBal(),
						accounts.getAcctBal() - check.getCheckAmt(), null);
				return transRec;
			} else {
				transRec = new TransactionRec(ticket, false,
						"the Check is dated past 6 months and can not be cashed in at this time: " + dateString,
						accounts.getAcctType(), accounts.getAcctBal(), accounts.getAcctBal(), null);
				return transRec;
			}
		} else {
			transRec = new TransactionRec(ticket, false,
					"The account does not have enough balance to clear the check and has been charged a bounce fee of $2.50",
					accounts.getAcctType(), accounts.getAcctBal(), accounts.getAcctBal() - 2.5, null);
			accounts.setAcctBal(accounts.getAcctBal() - 2.5);
			return transRec;
		}
	}

}
