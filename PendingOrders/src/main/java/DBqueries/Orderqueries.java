package DBqueries;

public class Orderqueries {
	
	public String select()
	{
		return "SELECT * FROM order_status WHERE symbol = ? AND action = ?";
	}
	public String insert()
	{
		return "INSERT INTO excuted_orders (symbol,price,action,quantity) VALUES (?,?,?,?)";
	}
	public String update()
	{
		return "UPDATE order_status SET status=? WHERE id=?";
	}
	

}
