package EmpCURDOperationPackage;

class Demo {
	private int ID;
	private String name;
	
	/*
	 * right click -> source -> generate getter setter
	 * right click -> source -> generate constructor using fields...
	 */
	
	public int getID() {
		return ID;
	}
	public Demo(int iD, String name) {
		super();
		ID = iD;
		this.name = name;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

public class temp {

}
