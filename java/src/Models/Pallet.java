package Models;

public class Pallet {

	public int id;
	public int order_id;
	public String productName;
	public String location;
	public String inTime;
	public String outTime;

	public Pallet() {}

	public Pallet(int id, int order_id, String product, String location, String inTime, String outTime, String reciever) {
		this.id = id;
		this.order_id = order_id;
		this.productName = product;
		this. location = location;
		this.inTime = inTime;
		this.outTime = outTime;
	}

}
