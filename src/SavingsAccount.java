import java.util.ArrayList;
import java.util.Scanner;

public class SavingsAccount extends Account {

	// No-args constructor
	public SavingsAccount() {
		acctNum = 0;
		acctBal = 0.0;
		acctType = "";
		acctDepositor = new Depositor();
		isOpen = true; // By default, account is open
		transactionHistory = new ArrayList<>();
	}

	// Parameters constructor
	public SavingsAccount(int n, double d, String s, Depositor depo, boolean isO, ArrayList<TransactionRec> hist) {
		acctNum = n;
		acctBal = d;
		acctType = s;
		acctDepositor = depo;
		isOpen = isO;
		transactionHistory = hist;
	}

	// Copy constructor
	public SavingsAccount(Account acct) {
		this.acctNum = acct.getAcctNum();
		this.acctBal = acct.getAcctBal();
		this.acctType = acct.getAcctType();
		this.acctDepositor = acct.getAcctUserInfo();
		this.isOpen = acct.getIsOpen();
		this.transactionHistory = acct.getTransactionHistory();
	}

}
