package Com.Rantron.TaoBao.Spider;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Parser.AliExpressItemDetailParser;

public class AliExpressItemDetailSpider extends RantronSpider{

	public Object getItemDetailByItemUrl(String url)
	{
		Map<String, String> params = new HashMap<String, String>();
		JSONObject jsonObject=null;
		params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
		String htmlcontent = CatchHtml(url, params, proxy);
		jsonObject = (JSONObject)AliExpressItemDetailParser.getJsonData(htmlcontent);
		return jsonObject;
	}
}
