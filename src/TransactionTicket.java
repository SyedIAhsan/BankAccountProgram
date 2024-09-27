import java.util.Calendar;

public class TransactionTicket {
	private int acctNum;
	private Calendar dateTransaction;
	private String typeTransaction;
	private double amtTransaction;
	private Calendar termCD;

	// Default constructor
	public TransactionTicket() {
		acctNum = 0;
		dateTransaction = Calendar.getInstance();
		typeTransaction = "";
		amtTransaction = 0.0;
		termCD = null;
	}

	// Constructor
	public TransactionTicket(int num, Calendar date, String str, double dub, Calendar date2) {
		acctNum = num;
		dateTransaction = date;
		typeTransaction = str;
		amtTransaction = dub;
		termCD = date2;
	}
	
	//Copy constructor 
	public TransactionTicket(TransactionTicket t) {
		this.acctNum = t.getAcctNum();
		this.dateTransaction = t.getDateTransaction();
		this.typeTransaction = t.getTypeTransaction();
		this.amtTransaction = t.getAmtTransaction();
		this.termCD = t.getTermCD();
	}

	// Getters
	public int getAcctNum() {
		return acctNum;
	}

	public Calendar getDateTransaction() {
		return dateTransaction;
	}

	public String getTypeTransaction() {
		return typeTransaction;
	}

	public double getAmtTransaction() {
		return amtTransaction;
	}

	public Calendar getTermCD() {
		return termCD;
	}

	// Setters
	private void setAcctNum(int num) {
		acctNum = num;
	}

	private void setDateTransaction(Calendar date) {
		dateTransaction = date;
	}

	private void setTypeTransaction(String str) {
		typeTransaction = str;
	}

	private void setAmtTransaction(double dub) {
		amtTransaction = dub;
	}

	private void setTermCD(Calendar date) {
		termCD = date;
	}
}