package Models;

public class Pallet {

	public int id;
	public String productName;
	public int loadingId;
	public String location;
	public String inTime;
	public String outTime;
	public String reciever;

	public Pallet() {}

	public Pallet(int id, String product, int loading, String location, String inTime, String outTime, String reciever) {
		this.id = id;
		this.productName = product;
		this.loadingId = loading;
		this. location = location;
		this.inTime = inTime;
		this.outTime = outTime;
		this.reciever = reciever;
	}

}
