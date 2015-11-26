package Com.Rantron.TaoBao.Spider.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Spider.Util.DateTimeUtil;

public class TaoBaoItemDetailBase2DB {

	private Connection connection = null;
	private PreparedStatement pstmt = null;
	private void initDB()
	{
		try {
			connection = JDBCUtils.getConnection("jdbc:mysql://192.168.0.33:3307/rantron_spider?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8");
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement("insert into items (itemid,cid,keyword,title,totalSoldQuantity,price,props,location,sellerNick,shopTitle,createtime) values (?,?,?,?,?,?,?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void add2DB(JSONObject jsonObj)
	{
		if(connection==null || pstmt==null)
			initDB();
		long itemId = jsonObj.getLong("Itemid");
		long cid = jsonObj.getLong("CategoryId");
		String title = jsonObj.getString("Title");
		int soldQuantitly = jsonObj.getInt("SoldQuantity");
		double price = jsonObj.getDouble("Price");
		String props = jsonObj.getString("Props");
		String location = jsonObj.getString("Location");
		String sellerNick = jsonObj.getString("SellerNick");
		String shopTitle = jsonObj.getString("ShopTitle");
		try {
			pstmt.setLong(1,itemId);
			pstmt.setLong(2, cid);
			pstmt.setString(4, title);
			pstmt.setInt(5,soldQuantitly );
			pstmt.setDouble(6, price);
			pstmt.setString(7, props);
			pstmt.setString(8, location);
			pstmt.setString(9, sellerNick);
			pstmt.setString(10, shopTitle);
			pstmt.setLong(11, DateTimeUtil.GetCurrentUnixDate());
			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
