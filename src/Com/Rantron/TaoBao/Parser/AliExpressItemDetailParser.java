package Com.Rantron.TaoBao.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;


public class AliExpressItemDetailParser {

	
	public static long getItemIdByHtml(String htmlContent)
	{
		String reg = "window.runParams.productId=\"([\\s\\S]*?)\"";
		Matcher m = Pattern.compile(reg).matcher(htmlContent);
		long itemid = 0l;
		if(m.find())
		{
			try {
				itemid = Long.parseLong(m.group(1).trim());
			} catch (NumberFormatException e) {
				//log4j output
				// TODO: handle exception
			}
		}
		return itemid;
	}
	public static String getItemTitleByHtml(String htmlContent)
	{
		String reg = "<h1 class=\"product-name\" itemprop=\"name\">([\\s\\S]*?)</h1>";
		Matcher m = Pattern.compile(reg).matcher(htmlContent);
		String title="";
		if(m.find())
		{
			title = m.group(1).trim();
		}
		return title;
	}
	
	public static double getItemPriceByHtml(String htmlContent)
	{
		String reg = "<span id=\"sku-price\" itemprop=\"price\">([\\s\\S]*?)</span>";
		Matcher m = Pattern.compile(reg).matcher(htmlContent);
		double price = 0d;
		if(m.find())
		{
			try
			{
				price = Double.parseDouble(m.group(1).trim());
			}
			catch(Exception e)
			{
				price = 0d;
			}
		}
		return price;
		
	}
	
	public static int getItemTotalSoldByHtml(String htmlContent)
	{
		String reg = "<b>(\\d+)</b> order";
		Matcher m = Pattern.compile(reg).matcher(htmlContent);
		int soldOut = 0;
		if(m.find())
		{
			try
			{
				soldOut = Integer.parseInt(m.group(1).trim());
			}
			catch(NumberFormatException e)
			{
				//log4j output
			}
		}
		return soldOut;
	}
	
	public static String getItemBrandNameByHtml(String htmlContent)
	{
		String reg = "<dt><span class=\"brand\">Brand Name:</span></dt>\n <dd title=\"([\\s\\S]*?)\">";
		Matcher m = Pattern.compile(reg).matcher(htmlContent);
		String brandName="";
		if(m.find())
		{
			brandName = m.group(1).trim();
		}
		return brandName;
	}
	
	public static String getItemOuterIdByHtml(String htmlContent)
	{
		String reg = "Model Number:</dt>\n <dd title=\"([\\s\\S]*?)\">";
		Matcher m = Pattern.compile(reg).matcher(htmlContent);
		String outerId = "";
		if(m.find())
		{
			outerId = m.group(1).trim();
		}
		return outerId;
	}
	
	public static String[] getItemPropsByHtml(String htmlContent)
	{
		String reg = "<dt id=.*?>([\\s\\S]*?)</dt>\n <dd title=\"([\\s\\S]*?)\">";
		Matcher m = Pattern.compile(reg).matcher(htmlContent);
		List<String> props = new ArrayList<String>();
		while(m.find())
		{
			props.add(m.group(1).trim()+m.group(2).trim());
		}
		String[] propArray =new String[props.size()];
		return props.toArray(propArray);
	}
	
	public static String getItemCompanyNameByHtml(String htmlContent)
	{
		String reg = "<a class=\"store-lnk\".*?>([\\s\\S]*?)</a>";
		Matcher m = Pattern.compile(reg).matcher(htmlContent);
		String companyName = "";
		if(m.find())
		{
			companyName = m.group(1).trim();
		}
		return companyName;
	}
	
	public static String getItemAddressByHtml(String htmlContent)
	{
		String reg = "<address>\n([\\s\\S]*?)</address>";
		Matcher m = Pattern.compile(reg).matcher(htmlContent);
		String address = "";
		if(m.find())
		{
			address = m.group(1).trim();
		}
		return address;
	}

	public static Object getJsonData(String htmlContent) {
		// TODO Auto-generated method stub
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("Itemid", getItemIdByHtml(htmlContent));
		jsonMap.put("Title", getItemTitleByHtml(htmlContent));
		jsonMap.put("SoldQuantity", getItemTotalSoldByHtml(htmlContent));
		jsonMap.put("Price", getItemPriceByHtml(htmlContent));
		jsonMap.put("Props", getItemPropsByHtml(htmlContent));
		jsonMap.put("brandName", getItemBrandNameByHtml(htmlContent));
		jsonMap.put("outerId", getItemOuterIdByHtml(htmlContent));
		jsonMap.put("Address", getItemAddressByHtml(htmlContent));
		jsonMap.put("ShopTitle", getItemCompanyNameByHtml(htmlContent));
		JSONObject jsonObject = new JSONObject(jsonMap);
		return jsonObject;
	}
	
//	public static void main(String []args)
//	{
//		Map<String, String> params = new HashMap<String, String>();
//		params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
//		RantronSpider rs = new RantronSpider();
//		String url ="http://www.aliexpress.com/item/IP-Camera-Hikvision-DS-2CD3132F-IWS-3MP-HD1080P-Webcam-Wireless-Camera-Security-camera-cctv-cam-support/32548797035.html";
//		String content = rs.CatchHtml(url, params, null).trim();
//		System.out.println(getItemIdByHtml(content));
//	}
}
