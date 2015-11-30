package Com.Rantron.TaoBao.Spider;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Parser.TaoBaoMobileItemDetailParser;

public class TaoBaoItemDetailSpider extends RantronSpider {

	
	
	


	
	
	private static final String ItemDetail_Mobile_URL_TAMPLATE = "http://hws.m.taobao.com/cache/wdetail/5.0/?id=[$ITEMID]";
	private static final String ItemDetail_URL_TAMPLATE = "";
	
	private AccessWay accessWay = AccessWay.MOBILE;
	
	public Object getItemDetailByItemid(String itemid)
	{
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		JSONObject jsonObject=null;
		if(accessWay == AccessWay.MOBILE)
		{
			url = ItemDetail_Mobile_URL_TAMPLATE.replace("[$ITEMID]", itemid);
			params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://s.taobao.com/");
			params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
			String htmlcontent = CatchHtml(url, params, proxy);
			jsonObject = (JSONObject)TaoBaoMobileItemDetailParser.getJsonData(htmlcontent);
			return jsonObject;
			
			
		}
		else if(accessWay == AccessWay.PC)
		{
			url = ItemDetail_URL_TAMPLATE.replace("[$ITEMID]", itemid);
			params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://items.taobao.com/");
		}
		return jsonObject;
		
		
		
		
	}
	
	

	
	
	
}

	
