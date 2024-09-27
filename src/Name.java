
public class Name {
	private String last;
	private String first;

	// No-Arg Constructor
	public Name() {
		System.out.println("Name: Default Constructor running");
		first = "";
		last = "";
	}

	// Constructor with Parameters
	public Name(String firstName, String lastName) {
		System.out.println("Name: 2-parameter name Constructor running");
		first = firstName;
		last = lastName;
	}
	
	//copy constructor 
	public Name(Name n) {
		this.first = n.getFirst();
		this.last = n.getLast();
	}

	// setters
	private void setLast(String str) {
		last = str;
	}

	private void setFirst(String str) {
		first = str;
	}

	// getters
	public String getLast() {
		return last;
	}

	public String getFirst() {
		return first;
	}
}
