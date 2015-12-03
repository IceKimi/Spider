package Com.Rantron.TaoBao.Spider.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Spider.Util.DateTimeUtil;

public class AliExpressItemDetail2DB {

	private Connection connection = null;
	private PreparedStatement pstmt = null;
	private void initDB()
	{
		try {
			connection = JDBCUtils.getConnection("jdbc:mysql://192.168.0.33:3307/rantron_spider?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8");
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement("insert into aliExpressItems (itemid,keywords,title,totalSoldQuantity,price,props,brandName,outerId,address,shopTitle,createtime) values (?,?,?,?,?,?,?,?,?,?,?)");
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
		String title = jsonObj.getString("Title");
		int soldQuantitly = jsonObj.getInt("SoldQuantity");
		double price = jsonObj.getDouble("Price");
		String props = jsonObj.get("Props").toString();
		String brandName = jsonObj.getString("brandName");
		String outerId = jsonObj.getString("outerId");
		String address = jsonObj.getString("Address");
		String shopTitle = jsonObj.getString("ShopTitle");
		try {
			pstmt.setLong(1,itemId);
			pstmt.setString(2, keywords);
			pstmt.setString(3, title);
			pstmt.setInt(4,soldQuantitly );
			pstmt.setDouble(5, price);
			pstmt.setString(6, props);
			pstmt.setString(7, brandName);
			pstmt.setString(8, outerId);
			pstmt.setString(9, address);
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
