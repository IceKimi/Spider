package Com.Rantron.TaoBao.Spider.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Com.Rantron.Spider.bean.TaoBaoItemBase;
import Com.Rantron.TaoBao.Spider.Util.DateTimeUtil;


public class DBTaoBaoItem {

	private static Connection connection = null;
	private static PreparedStatement pstmt = null;
	private static void initDB()
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
	
	public static synchronized void add2DB(TaoBaoItemBase taoBaoItem)
	{
		if(connection==null || pstmt==null)
			initDB();
		long itemId = taoBaoItem.getItemId();
		long cid = taoBaoItem.getCategoryId();
		String keywords = taoBaoItem.getKeywords();
		String title = taoBaoItem.getTitle();
		int soldQuantitly = taoBaoItem.getTotalSoldQuantity();
		double price = taoBaoItem.getPrice();
		String props = taoBaoItem.getPropsStr();
		String location = taoBaoItem.getLocation();
		String sellerNick = taoBaoItem.getSellerNick();
		String shopTitle = taoBaoItem.getShopTitle();
		try {
			pstmt.setLong(1,itemId);
			pstmt.setLong(2, cid);
			pstmt.setString(3, keywords);
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
