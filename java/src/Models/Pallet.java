package Models;

public class Pallet {

	public int id;
	public String productName;
	public String location;
	public String inTime;
	public String outTime;
	public String reciever;

	public Pallet() {}

	public Pallet(int id, String product, String location, String inTime, String outTime, String reciever) {
		this.id = id;
		this.productName = product;
		this. location = location;
		this.inTime = inTime;
		this.outTime = outTime;
		this.reciever = reciever;
	}

}
