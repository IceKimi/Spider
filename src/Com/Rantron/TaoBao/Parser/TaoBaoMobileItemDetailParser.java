package Com.Rantron.TaoBao.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Com.Rantron.Spider.bean.TaoBaoItemBase;

public class TaoBaoMobileItemDetailParser {

	public static String getItemTitleByHtml(String htmlContent)
	{
		JSONArray array = new JSONArray("["+htmlContent+"]");
		String title = array.getJSONObject(0).getJSONObject("data").getJSONObject("itemInfoModel").getString("title");
		return title;
	}
	
	public static long getItemCategoryIdByHtml(String htmlContent)
	{
		JSONArray array = new JSONArray("["+htmlContent+"]");
		String categoryIdStr = array.getJSONObject(0).getJSONObject("data").getJSONObject("itemInfoModel").getString("categoryId");
		return Long.parseLong(categoryIdStr);
	}
	
	public static long getItemIdByHtml(String htmlContent)
	{
		JSONArray array = new JSONArray("["+htmlContent+"]");
		String itemIdStr = array.getJSONObject(0).getJSONObject("data").getJSONObject("itemInfoModel").getString("itemId");
		return Long.parseLong(itemIdStr);
	}
	
	public static int getItemFavCountByHtml(String htmlContent)
	{
		JSONArray array = new JSONArray("["+htmlContent+"]");
		String favcountStr = array.getJSONObject(0).getJSONObject("data").getJSONObject("itemInfoModel").getString("favcount");
		return Integer.parseInt(favcountStr);
	}
	
	public static int getItemSoldQuantityByHtml(String htmlContent)
	{
		int totalSoldQuantity = 0;
		String quantityStr = "";
		Matcher m = Pattern.compile("totalSoldQuantity([\\s\\S]*?),").matcher(htmlContent);
	
		if(m.find())
		{
			quantityStr = m.group(1);
			quantityStr = quantityStr.replace("\\\"", "");
			quantityStr = quantityStr.replace(":", "");
		}
		totalSoldQuantity = Integer.parseInt(quantityStr);
		return totalSoldQuantity;
	}
	
	public static double getItemPriceByHtml(String htmlContent)
	{
		double price = 0;
		String priceStr = "";
		JSONArray array = new JSONArray("["+htmlContent+"]");
		
		String tmp = array.getJSONObject(0).getJSONObject("data").getJSONArray("apiStack").getJSONObject(0).get("value").toString();
		tmp = tmp.replace("\\\"", "");
		Matcher m = Pattern.compile("price\":\"([\\s\\S]*?)\",").matcher(tmp);
	
		if(m.find())
		{
			priceStr = m.group(1);
			priceStr = priceStr.replace("\\\"", "");
			priceStr = priceStr.replace(":", "");
			priceStr = priceStr.split("-")[0];
		}
		price = Double.parseDouble(priceStr);
		return price;
	}
	
	
	public static String[] getItemPropsByHtml(String htmlContent)
	{
		String []props=null;
		JSONArray array = new JSONArray("["+htmlContent+"]");
		try {
			array = array.getJSONObject(0).getJSONObject("data").getJSONArray("props");
			int len = array.length();
			props = new String[len];
			for(int i=0;i<len;i++)
			{
				String propName = array.getJSONObject(i).get("name").toString().trim();
				String propValue = array.getJSONObject(i).get("value").toString().trim();
				props[i]=propName+":"+propValue;
			}
		} catch (JSONException e) {
			// TODO: handle exception
			props = new String[1];
		}
		
		return props;
	}
	
	public static String getItemLocationByHtml(String htmlContent)
	{
		JSONArray array = new JSONArray("["+htmlContent+"]");
		String location = array.getJSONObject(0).getJSONObject("data").getJSONObject("itemInfoModel").getString("location");
		return location;
	}
	
	public static String getItemSellerNickByHtml(String htmlContent)
	{
		JSONArray array = new JSONArray("["+htmlContent+"]");
		String SellerNick = array.getJSONObject(0).getJSONObject("data").getJSONObject("seller").getString("nick");
		return SellerNick;
	}
	
	public static String getItemShopTitleByHtml(String htmlContent)
	{
		JSONArray array = new JSONArray("["+htmlContent+"]");
		String shopTitle = array.getJSONObject(0).getJSONObject("data").getJSONObject("seller").getString("shopTitle");
		return shopTitle;
	}
	
	public static String getItemShopTypeByHtml(String htmlContent)
	{
		JSONArray array = new JSONArray("["+htmlContent+"]");
		String shopType = array.getJSONObject(0).getJSONObject("data").getJSONObject("seller").getString("type");
		return shopType;
	}
	
	public static TaoBaoItemBase getTaoBaoItemBase(String htmlContent)
	{
		TaoBaoItemBase taoBaoItemBase = new TaoBaoItemBase();
		taoBaoItemBase.setTitle(getItemTitleByHtml(htmlContent));
		taoBaoItemBase.setCategoryId(getItemCategoryIdByHtml(htmlContent));
		taoBaoItemBase.setFavCount(getItemFavCountByHtml(htmlContent));
		taoBaoItemBase.setTotalSoldQuantity(getItemSoldQuantityByHtml(htmlContent));
		taoBaoItemBase.setPrice(getItemPriceByHtml(htmlContent));
		taoBaoItemBase.setProps(getItemPropsByHtml(htmlContent));
		taoBaoItemBase.setLocation(getItemLocationByHtml(htmlContent));
		taoBaoItemBase.setSellerNick(getItemSellerNickByHtml(htmlContent));
		taoBaoItemBase.setShopTitle(getItemShopTitleByHtml(htmlContent));
		taoBaoItemBase.setShopType(getItemShopTypeByHtml(htmlContent));
		return taoBaoItemBase;
	}

	public static Object getJsonData(String htmlContent) {
		// TODO Auto-generated method stub
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("Itemid", getItemIdByHtml(htmlContent));
		jsonMap.put("Title", getItemTitleByHtml(htmlContent));
		jsonMap.put("CategoryId", getItemCategoryIdByHtml(htmlContent));
		jsonMap.put("FavCount", getItemFavCountByHtml(htmlContent));
		jsonMap.put("SoldQuantity", getItemSoldQuantityByHtml(htmlContent));
		jsonMap.put("Price", getItemPriceByHtml(htmlContent));
		jsonMap.put("Props", getItemPropsByHtml(htmlContent));
		jsonMap.put("Location", getItemLocationByHtml(htmlContent));
		jsonMap.put("SellerNick", getItemSellerNickByHtml(htmlContent));
		jsonMap.put("ShopTitle", getItemShopTitleByHtml(htmlContent));
		jsonMap.put("ShopType", getItemShopTypeByHtml(htmlContent));
		JSONObject jsonObject = new JSONObject(jsonMap);
		return jsonObject;
	}
	
	
	
	
}
