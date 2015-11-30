package Com.Rantron.TaoBao.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class AlibabaItemDetailParser {

	
	public static long getItemIdByHtml(String content)
	{
		String itemidStr="";
		Matcher m = Pattern.compile("offer_id:\"([\\s\\S]*?)\"").matcher(content);
		if(m.find())
		{
			itemidStr = m.group(1);
		}
		return Long.parseLong(itemidStr);
	}
	
	public static String getItemTitleByHtml(String content)
	{
		String title = "";
		Matcher m = Pattern.compile("<meta property=\"og:title\" content=\"([\\s\\S]*?)\"/>").matcher(content);
		if(m.find())
			title = m.group(1);
		return title;
	}
	
	public static int getItemDealRecordsByHtml(String content)
	{

		int dealRecord = 0;
		Matcher m = Pattern.compile("\"title\":\"成交<em>([\\s\\S]*?)</em>").matcher(content);
		if(m.find())
			try {
				dealRecord = Integer.parseInt(m.group(1));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		return dealRecord;
	}
	
	public static String getItemSellerNickByHtml(String content)
	{
		String sellerNick = "";
		Matcher m = Pattern.compile("<meta property=\"og:product:nick\" content=\"name=([\\s\\S]*?); url").matcher(content);
		if(m.find())
			sellerNick = m.group(1);
		return sellerNick;
	
	}
	
	public static String getItemDealLevelByHtml(String content)
	{
		String dealLevel = "";
		Matcher m = Pattern.compile("<div class=\"common-tips\"><div class=\"tips-content\">([\\s\\S]*?)</div><i class=\"arrow arrow-down\"></i></div>").matcher(content);
		if(m.find())
			dealLevel = m.group(1);
		return dealLevel;
		
	}
	
	public static String getItemBizTypeByHtml(String content)
	{
		String bizType = "";
		Matcher m = Pattern.compile("<span class=\"biz-type-model\">([\\s\\S]*?)</span>").matcher(content);
		if(m.find())
			bizType = m.group(1);
		bizType = bizType.replace("<span class=\"already-checked-mem\">", "");
		return bizType;
	}
	
	public static double getItemPriceByHtml(String content)
	{
		double price = 0.0;
		String priceStr = "";
		Matcher m = Pattern.compile("<meta property=\"og:product:price\" content=\"([\\s\\S]*?)\"/>").matcher(content);
		if(m.find())
		{
			priceStr = m.group(1);
			try
			{
				price = Double.valueOf(priceStr);
			}
			catch(java.lang.NumberFormatException e)
			{
				price = 0.0;
			}
		}
		return price;
	}
	
	public static double getItemOrgPriceByHtml(String content)
	{
		double price = 0.0;
		String priceStr = "";
		Matcher m = Pattern.compile("<meta property=\"og:product:orgprice\" content=\"([\\s\\S]*?)\"/>").matcher(content);
		if(m.find())
		{
			priceStr = m.group(1);
			try
			{
				price = Double.valueOf(priceStr);
			}
			catch(java.lang.NumberFormatException e)
			{
				price = 0.0;
			}
		}
		return price;
	}
	
	public static String getItemBrandNameByHtml(String content)
	{
		String brandName = "";
		Matcher m = Pattern.compile("<td class=\"de-feature\">品牌</td>([\\s\\S]*?)</td>").matcher(content);
		if(m.find())
			brandName = m.group(1);
		brandName = brandName.replace("<td class=\"de-value\">", "").trim();
		return brandName;
	}
	
	public static String getItemOuterIdByHtml(String content)
	{
		String outerId = "";
		Matcher m = Pattern.compile("<td class=\"de-feature\">型号</td>([\\s\\S]*?)</td>").matcher(content);
		if(m.find())
			outerId = m.group(1);
		outerId = outerId.replace("<td class=\"de-value\">", "").trim();
		return outerId;
	}
	
	public static String getItemProvinceByHtml(String content)
	{
		String province = "";
		Matcher m = Pattern.compile("province=([\\s\\S]*?);").matcher(content);
		if(m.find())
			province = m.group(1);
		return province;
	}
	
	public static String getItemCityByHtml(String content)
	{
		String city = "";
		Matcher m = Pattern.compile("city=([\\s\\S]*?)\"").matcher(content);
		if(m.find())
			city = m.group(1);
		return city;
	}
	
	public static String[] getItemPropsByHtml(String content)
	{
		
		Matcher m1 = Pattern.compile("<td class=\"de-feature\">([\\s\\S]*?)</td>").matcher(content);
		Matcher m2 = Pattern.compile("<td class=\"de-value\">([\\s\\S]*?)</td>").matcher(content);
		ArrayList<String> props = new ArrayList<String>();
		while(m1.find()&&m2.find())
			props.add(m1.group(1)+":"+m2.group(1));
		return props.toArray(new String[props.size()]);
	}
	

	public static Object getJsonData(String htmlContent) {
		// TODO Auto-generated method stub
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("ItemId", getItemIdByHtml(htmlContent));
		jsonMap.put("Title", getItemTitleByHtml(htmlContent));
		jsonMap.put("DealRecords", getItemDealRecordsByHtml(htmlContent));
		jsonMap.put("SellerNick", getItemSellerNickByHtml(htmlContent));
		jsonMap.put("DealLevel", getItemDealLevelByHtml(htmlContent));
		jsonMap.put("BizType", getItemBizTypeByHtml(htmlContent));
		jsonMap.put("Price", getItemPriceByHtml(htmlContent));
		jsonMap.put("OrgPrice", getItemOrgPriceByHtml(htmlContent));
		jsonMap.put("BrandName", getItemBrandNameByHtml(htmlContent));
		jsonMap.put("Props", getItemPropsByHtml(htmlContent));
		jsonMap.put("OuterId", getItemOuterIdByHtml(htmlContent));
		jsonMap.put("Province", getItemProvinceByHtml(htmlContent));
		jsonMap.put("City", getItemCityByHtml(htmlContent));
		JSONObject jsonObject = new JSONObject(jsonMap);
		return jsonObject;
	}
}
