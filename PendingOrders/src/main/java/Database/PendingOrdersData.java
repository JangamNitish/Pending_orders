package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBqueries.Orderqueries;
import OrdersPojo.OrdersPojo;

public class PendingOrdersData {
	Orderqueries queries = new Orderqueries();
	
	String buy = "buy";
	String sell = "sell";
	String excuted = "excuted";
	String symbol;
	int id,quantity,Price;

	
	public String pendingOrders(OrdersPojo I, Connection con) throws SQLException {
//		System.out.println(I.getAskPrice());
//		System.out.println(I.getBidPrice());
//		System.out.println(I.getQuantity());
//		System.out.println(I.getSymbol());
		askPriceOrders(I, con);		
		if(I.getAskPrice()==0 && I.getBidPrice()>0) {
			String str=bidOrders(I, con);
			return str;
		}
		else if(I.getAskPrice()>0 && I.getBidPrice()==0) {
			String str=askPriceOrders(I, con);
			return str;
		}
		else if(I.getAskPrice()==0 && I.getBidPrice()==0 && I.getQuantity()==0 || I.getSymbol().equals(null)) {
			return "enter correct parameters";
		}
		else if(I.getAskPrice()>0 && I.getBidPrice()>0) {
			return "enter a single price that is bid price or askprice";
		}
		else {	
			return "enter valid cradentials";
		}
	}

	public String bidOrders(OrdersPojo I,Connection con) throws SQLException {
		PreparedStatement SelectQuery=con.prepareStatement(queries.select());
		SelectQuery.setString(1, I.getSymbol());
		SelectQuery.setString(2,sell);
		ResultSet rs=SelectQuery.executeQuery();
		if(rs.next()) {
			symbol=rs.getString("symbol");
			id=rs.getInt("id");
			Price=rs.getInt("symbolprice");
			quantity=rs.getInt("Quantity");
		}
		System.out.println(id);
		if(I.getSymbol().equals(symbol)) {
			if(I.getBidPrice()>Price) {
				if(I.getQuantity()<=quantity) {
				PreparedStatement UpdateQuery=con.prepareStatement(queries.update());
				PreparedStatement InsertQuery=con.prepareStatement(queries.insert());
				UpdateQuery.setString(1,excuted);
				UpdateQuery.setInt(2, id);
				UpdateQuery.execute();
				InsertQuery.setString(1,I.getSymbol() );
				InsertQuery.setInt(2, I.getBidPrice());
				InsertQuery.setString(3,sell);
				InsertQuery.setInt(4, I.getQuantity());
				InsertQuery.execute();
				return "ordered sucessfully";
			}
				else {
					return "sufficient quantity is not present";
				}
			}
			else {
				return "give bidprice is less than price";
			}
		}
		else {
			return "incorrect symbol given";
		}
	}
public String askPriceOrders(OrdersPojo I,Connection con) throws SQLException {
	PreparedStatement SelectQuery=con.prepareStatement(queries.select());
	SelectQuery.setString(1, I.getSymbol());
	SelectQuery.setString(2,buy);
	ResultSet rs=SelectQuery.executeQuery();
	while(rs.next()) {
		symbol=rs.getString("symbol");
		id=rs.getInt("id");
		quantity=rs.getInt("Quantity");
		Price=rs.getInt("symbolprice");
		System.out.println();
	}

	if(I.getSymbol().equals(symbol)) {
		if(I.getAskPrice()<Price) {
			if(I.getQuantity()<=quantity) {
			PreparedStatement UpdateQuery=con.prepareStatement(queries.update());
			PreparedStatement InsertQuery=con.prepareStatement(queries.insert());
			UpdateQuery.setString(1,excuted);
			UpdateQuery.setInt(2, id);
			UpdateQuery.execute();
			InsertQuery.setString(1,I.getSymbol() );
			InsertQuery.setInt(2, I.getAskPrice());
			InsertQuery.setString(3,buy);
			InsertQuery.setInt(4, I.getQuantity());
			InsertQuery.execute();
			return "ordered sucessfully";
		}
			else {
				return "sufficient quantity is not present";
			}
		}
		else {
			return "give askprice is less than price";
		}
	}
	else {
		return "incorrect symbol given";
	}
}
}