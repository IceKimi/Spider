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
			pstmt = connection.prepareStatement("insert into taoBaoItems (itemid,cid,keyword,title,totalSoldQuantity,price,props,brandName,outerId,location,sellerNick,shopType,shopTitle,createtime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public synchronized void add2DB(JSONObject jsonObj,String keywords)
	{
		if(connection==null || pstmt==null)
			initDB();
		long itemId = jsonObj.getLong("Itemid");
		long cid = jsonObj.getLong("CategoryId");
		String title = jsonObj.getString("Title");
		int soldQuantitly = jsonObj.getInt("SoldQuantity");
		double price = jsonObj.getDouble("Price");
		String brandName = jsonObj.getString("brandName");
		String outerId = jsonObj.getString("outerId");
		String props = jsonObj.get("Props").toString();
		String location = jsonObj.getString("Location");
		String sellerNick = jsonObj.getString("SellerNick");
		String shopTitle = jsonObj.getString("ShopTitle");
		String shopType = jsonObj.getString("ShopType");
		try {
			pstmt.setLong(1,itemId);
			pstmt.setLong(2, cid);
			pstmt.setString(3, keywords);
			pstmt.setString(4, title);
			pstmt.setInt(5,soldQuantitly );
			pstmt.setDouble(6, price);
			pstmt.setString(7, props);
			pstmt.setString(8, brandName);
			pstmt.setString(9, outerId);
			pstmt.setString(10, location);
			pstmt.setString(11, sellerNick);
			pstmt.setString(12, shopType);
			pstmt.setString(13, shopTitle);
			pstmt.setLong(14, DateTimeUtil.GetCurrentUnixDate());
			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public synchronized void add2DB(JSONObject jsonObj)
	{
		if(connection==null || pstmt==null)
			initDB();
		long itemId = jsonObj.getLong("Itemid");
		long cid = jsonObj.getLong("CategoryId");
		String title = jsonObj.getString("Title");
		int soldQuantitly = jsonObj.getInt("SoldQuantity");
		double price = jsonObj.getDouble("Price");
		String props = jsonObj.getString("Props");
		String brandName = jsonObj.getString("brandName");
		String outerId = jsonObj.getString("outerId");
		String location = jsonObj.getString("Location");
		String sellerNick = jsonObj.getString("SellerNick");
		String shopTitle = jsonObj.getString("ShopTitle");
		String shopType = jsonObj.getString("ShopType");
		try {
			pstmt.setLong(1,itemId);
			pstmt.setLong(2, cid);
			pstmt.setString(3, "");
			pstmt.setString(4, title);
			pstmt.setInt(5,soldQuantitly );
			pstmt.setDouble(6, price);
			pstmt.setString(7, props);
			pstmt.setString(8, brandName);
			pstmt.setString(9, outerId);
			pstmt.setString(10, location);
			pstmt.setString(11, sellerNick);
			pstmt.setString(12, shopType);
			pstmt.setString(13, shopTitle);
			pstmt.setLong(14, DateTimeUtil.GetCurrentUnixDate());
			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
