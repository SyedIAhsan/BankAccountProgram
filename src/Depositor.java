
public class Depositor {
	private String SSN;
	private Name acctName;

	// Constructor no-args
	public Depositor() {
		SSN = "";
		acctName = new Name();
	}

	// Constructor parametized
	public Depositor(String s, Name name) {
		SSN = s;
		acctName = name;
	}
	
	//copy constructor
	public Depositor(Depositor d) {
		this.SSN = d.getSSN();
		this.acctName = d.getName();
	}

	// setters
	private void setName(Name n) {
		acctName = n;
	}

	private void setSSN(String s) {
		SSN = s;
	}

	// getters
	public Name getName() {
		return acctName;
	}

	public String getSSN() {
		return SSN;
	}
}
