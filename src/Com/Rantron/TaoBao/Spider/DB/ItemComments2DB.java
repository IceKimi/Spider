package Com.Rantron.TaoBao.Spider.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Spider.Util.DateTimeUtil;

public class ItemComments2DB {

	private Connection connection = null;
	private PreparedStatement pstmt = null;
	private void initDB()
	{
		try {
			connection = JDBCUtils.getConnection("jdbc:mysql://192.168.0.33:3307/rantron_spider?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8");
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement("insert into itemComments (itemid,keywords,brandId,rateType,shopType,comments,createtime) values (?,?,?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public synchronized void add2DB(List<String> comments,long itemid,String keywords,int rateType,String shopType,int brandid)
	{
		if(connection==null || pstmt==null)
			initDB();
		try {
			for(String comment:comments)
			{
				pstmt.setLong(1,itemid);
				pstmt.setString(2, keywords);
				pstmt.setInt(3, brandid);
				pstmt.setInt(4, rateType);
				pstmt.setString(5, shopType);
				pstmt.setString(6, comment);
				pstmt.setLong(7, DateTimeUtil.GetCurrentUnixDate());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			connection.commit();
			pstmt.clearBatch();
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
			pstmt.setString(8, location);
			pstmt.setString(9, sellerNick);
			pstmt.setString(10, shopType);
			pstmt.setString(11, shopTitle);
			pstmt.setLong(12, DateTimeUtil.GetCurrentUnixDate());
			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
