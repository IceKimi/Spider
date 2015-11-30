package Com.Rantron.TaoBao.Spider.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Spider.Util.DateTimeUtil;

public class AlibabaItemDetailBase2DB {

	private Connection connection = null;
	private PreparedStatement pstmt = null;

	private void initDB() {
		try {
			connection = JDBCUtils.getConnection(
					"jdbc:mysql://192.168.0.33:3307/rantron_spider?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8");
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(
					"insert into AliBaBaItems (itemid,keywords,title,totalSoldQuantity,sellerNick,dealLevel,bizType,price,orgPrice,brandName,props,outerId,province,city,createTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized void add2DB(JSONObject jsonObj, String keywords) {

		if (connection == null || pstmt == null)
			initDB();
		long itemId = jsonObj.getLong("ItemId");
		String title = jsonObj.getString("Title");
		int soldQuantitly = jsonObj.getInt("DealRecords");
		String sellerNick = jsonObj.getString("SellerNick");
		String dealLevel = jsonObj.getString("DealLevel");
		String bizType = jsonObj.getString("BizType");
		double price = jsonObj.getDouble("Price");
		double orgPrice = jsonObj.getDouble("OrgPrice");
		String brandName = jsonObj.getString("BrandName");
		String props = jsonObj.get("Props").toString();
		String outerId = jsonObj.getString("OuterId");
		String province = jsonObj.getString("Province");
		String city = jsonObj.getString("City");

		try {
			pstmt.setLong(1, itemId);
			pstmt.setString(2, keywords);
			pstmt.setString(3, title);
			pstmt.setInt(4, soldQuantitly);
			pstmt.setString(5, sellerNick);
			pstmt.setString(6, dealLevel);
			pstmt.setString(7, bizType);
			pstmt.setDouble(8, price);
			pstmt.setDouble(9, orgPrice);
			pstmt.setString(10, brandName);
			pstmt.setString(11, props);
			pstmt.setString(12, outerId);
			pstmt.setString(13, province);
			pstmt.setString(14, city);
			pstmt.setLong(15, DateTimeUtil.GetCurrentUnixDate());
			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized void add2DB(JSONObject jsonObj) {
		if (connection == null || pstmt == null)
			initDB();
		long itemId = jsonObj.getLong("ItemId");
		String title = jsonObj.getString("Title");
		int soldQuantitly = jsonObj.getInt("DealRecords");
		String sellerNick = jsonObj.getString("SellerNick");
		String dealLevel = jsonObj.getString("DealLevel");
		String bizType = jsonObj.getString("BizType");
		double price = jsonObj.getDouble("Price");
		double orgPrice = jsonObj.getDouble("OrgPrice");
		String brandName = jsonObj.getString("BrandName");
		String props = jsonObj.get("Props").toString();
		String outerId = jsonObj.getString("OuterId");
		String province = jsonObj.getString("Province");
		String city = jsonObj.getString("City");

		try {
			pstmt.setLong(1, itemId);
			pstmt.setString(2, "");
			pstmt.setString(3, title);
			pstmt.setInt(4, soldQuantitly);
			pstmt.setString(5, sellerNick);
			pstmt.setString(6, dealLevel);
			pstmt.setString(7, bizType);
			pstmt.setDouble(8, price);
			pstmt.setDouble(9, orgPrice);
			pstmt.setString(10, brandName);
			pstmt.setString(11, props);
			pstmt.setString(12, outerId);
			pstmt.setString(13, province);
			pstmt.setString(14, city);
			pstmt.setLong(15, DateTimeUtil.GetCurrentUnixDate());
			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
