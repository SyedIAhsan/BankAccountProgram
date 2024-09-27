import java.util.Calendar;

public class Check {
    // Data fields
    private int acctNum;
    private double checkAmt;
    private Calendar dateCheck;

    // Constructor
    public Check(int num, double dub, Calendar DOC) {
        acctNum = num;
        checkAmt = dub;
        dateCheck = DOC;
    }
    
    //Copy Constructor
    public Check(Check c) {
        this.acctNum = c.getAcctNumCheck();
        this.checkAmt = c.getCheckAmt();
        this.dateCheck = c.getDateCheck();
    }

    public Check() {
	}

	// Getters and setters
    public int getAcctNumCheck() {
		return acctNum;
    }
    
    public double getCheckAmt() {
		return checkAmt;
    }
    
    public Calendar getDateCheck() {
		return dateCheck;
    }
    
    private void setAcctNumCheck(int num) {
        acctNum = num;
    }

    private void setCheckAmt(double dub) {
        checkAmt = dub;
    }

    private void setDateCheck(Calendar DOC) {
        dateCheck = DOC;
    }
}
