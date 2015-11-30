package Com.Rantron.TaoBao.Spider;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Parser.AlibabaItemDetailParser;
import Com.Rantron.TaoBao.Parser.AlibabaMobileItemDetailParser;

public class AlibabaItemDetailSpider extends RantronSpider{


	
	private static final String ItemDetail_Mobile_URL_TAMPLATE = "http://m.1688.com/offer/[$ITEMID].html";
	private static final String ItemDetail_URL_TAMPLATE = "http://detail.1688.com/offer/[$ITEMID].html ";
	
	private AccessWay accessWay = AccessWay.PC;
	public Object getItemDetailByItemid(String itemid)
	{
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		JSONObject jsonObject=null;
		if(accessWay == AccessWay.MOBILE)
		{
			url = ItemDetail_Mobile_URL_TAMPLATE.replace("[$ITEMID]", itemid);
			params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://m.1688.com/");
			params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
			String htmlcontent = CatchHtml(url, params, proxy);
			jsonObject = (JSONObject)AlibabaMobileItemDetailParser.getJsonData(htmlcontent);
//			TaoBaoItemBase taoBaoItemBase = TaoBaoMobileItemDetailParser.getTaoBaoItemBase(htmlcontent);
			
		}
		else if(accessWay == AccessWay.PC)
		{
			url = ItemDetail_URL_TAMPLATE.replace("[$ITEMID]", itemid);
			params.put(CatchParamEnum.HEADER_REFERER.getName(), "http://detail.1688.com/");
			String htmlcontent = CatchHtml(url, params, proxy);
			jsonObject = (JSONObject)AlibabaItemDetailParser.getJsonData(htmlcontent);
			
		}
		return jsonObject;
	
		
		
		
		
	}
	

	
}
