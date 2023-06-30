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
	String executed = "executed";
	String bidstatus, askstatus;
	String symbol;
	int id, quantity, Price;

	public String pendingOrders(OrdersPojo I, Connection con) throws SQLException {
		if (I.getAskPrice() == 0 && I.getBidPrice() > 0) {
			String str = bidOrders(I, con);
			return str;
		} else if (I.getAskPrice() > 0 && I.getBidPrice() == 0) {
			String str = askPriceOrders(I, con);
			return str;
		} else if (I.getAskPrice() == 0 || I.getBidPrice() == 0 && I.getQuantity() == 0 || I.getSymbol().equals(null)) {
			return "enter correct values";

		} else if (I.getAskPrice() > 0 && I.getBidPrice() > 0) {
			return "enter a single price that is bid price or askprice";
		} else {
			return "enter valid credentials";
		}
	}

	// SELLING THE STOCK METHOD
	public String bidOrders(OrdersPojo I, Connection con) throws SQLException {
		PreparedStatement SelectQuery = con.prepareStatement(queries.select());
		SelectQuery.setString(1, I.getSymbol());
		SelectQuery.setString(2, sell);
		ResultSet rs = SelectQuery.executeQuery();
		if (rs.next()) {
			symbol = rs.getString("symbol");
			id = rs.getInt("id");
			Price = rs.getInt("symbolprice");
			quantity = rs.getInt("Quantity");
			bidstatus = rs.getString("status");
			System.out.println(bidstatus + "Bid status is excuted");

		}
		if (I.getSymbol() == symbol || I.getSymbol().equals(symbol)) {
			if (I.getBidPrice() > Price) {
				if (I.getQuantity() <= quantity) {
					if (bidstatus.equals("pending")) {
						PreparedStatement UpdateQuery = con.prepareStatement(queries.update());
						PreparedStatement InsertQuery = con.prepareStatement(queries.insert());
						UpdateQuery.setString(1, executed);
						UpdateQuery.setInt(2, id);
						UpdateQuery.execute();
						InsertQuery.setString(1, I.getSymbol());
						InsertQuery.setInt(2, I.getBidPrice());
						InsertQuery.setString(3, sell);
						InsertQuery.setInt(4, I.getQuantity());
						InsertQuery.execute();
						return "ordered sucessfully";
					} else {
						return "Already Excuted";
					}
				} else
					return "sufficient quantity is not present";
			} else {
				return " Bidprice is less than the price field in the row from the order_status table for that symbol";
			}
		} else {
			return "incorrect symbol given";
		}
	}

//BUYING THE STOCK METHOD
	public String askPriceOrders(OrdersPojo I, Connection con) throws SQLException {
		PreparedStatement SelectQuery = con.prepareStatement(queries.select());
		SelectQuery.setString(1, I.getSymbol());
		SelectQuery.setString(2, buy);
		ResultSet rs = SelectQuery.executeQuery();
		if (rs.next()) {
			symbol = rs.getString("symbol");
			id = rs.getInt("id");
			quantity = rs.getInt("Quantity");
			Price = rs.getInt("symbolprice");
			askstatus = rs.getString("status");
			System.out.println(askstatus);

		}
		if (I.getSymbol().equals(symbol)) {
			if (I.getAskPrice() < Price) {
				if (I.getQuantity() <= quantity) {
					if (askstatus.equals("pending")) {
						PreparedStatement UpdateQuery = con.prepareStatement(queries.update());
						PreparedStatement InsertQuery = con.prepareStatement(queries.insert());
						UpdateQuery.setString(1, executed);
						UpdateQuery.setInt(2, id);
						UpdateQuery.execute();
						InsertQuery.setString(1, I.getSymbol());
						InsertQuery.setInt(2, I.getAskPrice());
						InsertQuery.setString(3, buy);
						InsertQuery.setInt(4, I.getQuantity());
						InsertQuery.execute();
						return "ordered sucessfully";
					} else {
						return "Already excuted ";
					}
				} else {
					return "sufficient quantity is not present";
				}
			} else {
				return "Askprice is greater than the price field in the row from the order_status table for that symbol";
			}
		} else {
			return "incorrect symbol given";
		}

	}
}