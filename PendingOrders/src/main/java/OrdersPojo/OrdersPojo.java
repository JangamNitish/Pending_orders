package OrdersPojo;

public class OrdersPojo {
	int Quantity,askPrice,bidPrice;
	String symbol;
	public int getQuantity() {
		return Quantity;
	}
	public int getAskPrice() {
		return askPrice;
	}
	public int getBidPrice() {
		return bidPrice;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public void setAskPrice(int askPrice) {
		this.askPrice = askPrice;
	}
	public void setBidPrice(int bidPrice) {
		this.bidPrice = bidPrice;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public OrdersPojo(int quantity, int askPrice, int bidPrice, String symbol) {
		super();
		Quantity = quantity;
		this.askPrice = askPrice;
		this.bidPrice = bidPrice;
		this.symbol = symbol;
	}
	
	
}