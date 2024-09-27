import java.util.Calendar;

public class TransactionRec {
	private TransactionTicket ticket;
	private boolean successIndicatorFlag;
	private String reasonForFailure;
	private String accountType;
	private double preTransactionBalance;
	private double postTransactionBalance;
	private Calendar postTransactionMaturityDate;

	// Default constructor
	public TransactionRec() {
		ticket = new TransactionTicket();
		successIndicatorFlag = false;
		reasonForFailure = "";
		accountType = "";
		preTransactionBalance = 0;
		postTransactionBalance = 0;
		postTransactionMaturityDate = null;
	}

	// Parameterized constructor
	public TransactionRec(TransactionTicket tick, boolean bool, String str, String str2, double dub, double dub2,
			Calendar date) {
		ticket = tick;
		successIndicatorFlag = bool;
		reasonForFailure = str;
		accountType = str2;
		preTransactionBalance = dub;
		postTransactionBalance = dub2;
		postTransactionMaturityDate = date;
	}

	public TransactionRec(TransactionRec t) {
		this.ticket = t.getTicket();
		this.successIndicatorFlag = t.getSuccessIndicatorFlag();
		this.reasonForFailure = t.getReasonForFailure();
		this.accountType = t.getAccountType();
		this.preTransactionBalance = t.getPreTransactionBalance();
		this.postTransactionBalance = t.getPostTransactionBalance();
		this.postTransactionMaturityDate = t.getPostTransactionMaturityDate();
		System.out.println("COPY CONSTRUCTOR RUNNING");
	}

	// Getters
	public TransactionTicket getTicket() {
		return ticket;
	}

	public boolean getSuccessIndicatorFlag() {
		return successIndicatorFlag;
	}

	public String getReasonForFailure() {
		return reasonForFailure;
	}

	public String getAccountType() {
		return accountType;
	}

	public double getPreTransactionBalance() {
		return preTransactionBalance;
	}

	public double getPostTransactionBalance() {
		return postTransactionBalance;
	}

	public Calendar getPostTransactionMaturityDate() {
		return postTransactionMaturityDate;
	}

	// Setters
	private void setTicket(TransactionTicket tick) {
		ticket = tick;
	}

	private void setSuccessIndicatorFlag(boolean bool) {
		successIndicatorFlag = bool;
	}

	private void setReasonForFailure(String str) {
		reasonForFailure = str;
	}

	private void setAccountType(String str2) {
		accountType = str2;
	}

	private void setPreTransactionBalance(double dub) {
		preTransactionBalance = dub;
	}

	private void setPostTransactionBalance(double dub2) {
		postTransactionBalance = dub2;
	}

	private void setPostTransactionMaturityDate(Calendar date) {
		postTransactionMaturityDate = date;
	}

	// Overloaded toString methods
	public String toString() {
		String myString = " ";

		if (successIndicatorFlag) {

			if (ticket.getTypeTransaction().equalsIgnoreCase("Deposit")) {
				if (accountType.equals("CD")) {
					Calendar maturityDate = postTransactionMaturityDate;
					System.out.println("JERE UJERE");
					String maturityDateSTRING = maturityDate.get(Calendar.DAY_OF_MONTH) + "/"
							+ (maturityDate.get(Calendar.MONTH) + 1) + "/" + maturityDate.get(Calendar.YEAR);

					myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
							+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n Pre-Transaction Balance: $"
							+ String.format("%.2f", preTransactionBalance) + "\n Post-Transaction Balance: $"
							+ String.format("%.2f", postTransactionBalance) + "\n Amount to be deposited: $"
							+ String.format("%.2f", ticket.getAmtTransaction())
							+ "\n New Maturity Date for CD account: " + maturityDateSTRING;

				} else {
					myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
							+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n Pre-Transaction Balance: $"
							+ String.format("%.2f", preTransactionBalance) + "\n Post-Transaction Balance: $"
							+ String.format("%.2f", postTransactionBalance) + "\n Amount to be deposited: $"
							+ String.format("%.2f", ticket.getAmtTransaction());
				}
			} else if (ticket.getTypeTransaction().equalsIgnoreCase("Withdrawal")) {
				if (accountType.equals("CD")) {
					Calendar maturityDate = postTransactionMaturityDate;
					String maturityDateSTRING = maturityDate.get(Calendar.DAY_OF_MONTH) + "/"
							+ (maturityDate.get(Calendar.MONTH) + 1) + "/" + maturityDate.get(Calendar.YEAR);

					myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
							+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n Pre-Transaction Balance: $"
							+ String.format("%.2f", preTransactionBalance) + "\n Post-Transaction Balance: $"
							+ String.format("%.2f", postTransactionBalance) + "\n Amount to be withdrawn: $"
							+ String.format("%.2f", ticket.getAmtTransaction())
							+ "\n New Maturity Date for CD account: " + maturityDateSTRING;

				} else {
					myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
							+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n Pre-Transaction Balance: $"
							+ String.format("%.2f", preTransactionBalance) + "\n Post-Transaction Balance: $"
							+ String.format("%.2f", postTransactionBalance) + "\n Amount to be withdrawn: $"
							+ String.format("%.2f", ticket.getAmtTransaction());
				}
			} else if (ticket.getTypeTransaction().equalsIgnoreCase("Check Clear")) {
				myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
						+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n Pre-Transaction Balance: $"
						+ String.format("%.2f", preTransactionBalance) + "\n Post-Transaction Balance: $"
						+ String.format("%.2f", postTransactionBalance) + "\n Amount of check: $"
						+ String.format("%.2f", ticket.getAmtTransaction());
			} else if (ticket.getTypeTransaction().equalsIgnoreCase("Account Deletion")) {
				myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
						+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n Account Balance: $"
						+ String.format("%.2f", postTransactionBalance);
			} else if (ticket.getTypeTransaction().equalsIgnoreCase("Account Closure")) {
				myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
						+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n Account Balance: $"
						+ String.format("%.2f", postTransactionBalance) + "\n Success";
			} else if (ticket.getTypeTransaction().equalsIgnoreCase("Account Re-Open")) {
				myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
						+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n Account Balance: $"
						+ String.format("%.2f", postTransactionBalance) + "\n Success";
			} else if (ticket.getTypeTransaction().equalsIgnoreCase("Balance Inquiry")) {
				myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
						+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n Pre-Transaction Balance: $"
						+ String.format("%.2f", preTransactionBalance) + "\n Post-Transaction Balance: $"
						+ String.format("%.2f", postTransactionBalance);
			}
		} else {
			myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Failed: " + reasonForFailure
					+ "\n Account Number: " + ticket.getAcctNum() + "\n Old Account Balance: $"
					+ String.format("%.2f", preTransactionBalance) + "\n Amount of Transaction: $"
					+ String.format("%.2f", ticket.getAmtTransaction()) + "\n New Account Balance: $"
					+ String.format("%.2f", postTransactionBalance);
		}
		return myString;
	}

	// toString for new account
	public String toString(String firstName, String lastName, String newSSN) {
		String myString = " ";

		if (accountType.equals("CD")) {
			String maturityDateSTRING = "";
			maturityDateSTRING = postTransactionMaturityDate.get(Calendar.DAY_OF_MONTH) + "/"
					+ (postTransactionMaturityDate.get(Calendar.MONTH) + 1) + "/"
					+ postTransactionMaturityDate.get(Calendar.YEAR);

			myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
					+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n New Account Balance: $"
					+ String.format("%.2f", postTransactionBalance) + "\n New Account Info: \n" + ticket.getAcctNum()
					+ " " + firstName + " " + lastName + " " + newSSN + " " + accountType + " $"
					+ String.format("%.2f", postTransactionBalance) + " Maturity Date: " + maturityDateSTRING;

		} else {
			myString = "Transaction Requested: " + ticket.getTypeTransaction() + "\n Account Number: "
					+ ticket.getAcctNum() + "\n Account Type: " + accountType + "\n New Account Balance: $"
					+ String.format("%.2f", postTransactionBalance) + "\n New Account Info: \n" + ticket.getAcctNum()
					+ " " + firstName + " " + lastName + " " + newSSN + " " + accountType + " $"
					+ String.format("%.2f", postTransactionBalance);
		}

		return myString;
	}

	// history toString
	public String toString(String h, String str) {

		String transactionDate = ticket.getDateTransaction().get(Calendar.DAY_OF_MONTH) + "/"
				+ (ticket.getDateTransaction().get(Calendar.MONTH) + 1) + "/"
				+ ticket.getDateTransaction().get(Calendar.YEAR);

		String myString = (String.format("%-18s %-20s $%-11.2f %-15s $%-17.2f %s", transactionDate,
				ticket.getTypeTransaction(), ticket.getAmtTransaction(), str, postTransactionBalance,
				reasonForFailure));

		return myString;
	}
}