package Models;

public class Pallet {

	public int id;
	public int orderId;
	public String productName;
	public String location;
	public String inTime;
	public String outTime;

	public Pallet() {}

	public Pallet(int id, int orderId, String product, String location, String inTime, String outTime, String reciever) {
		this.id = id;
		this.orderId = orderId;
		this.productName = product;
		this. location = location;
		this.inTime = inTime;
		this.outTime = outTime;
	}

}
