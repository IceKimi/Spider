package Com.Rantron.TaoBao.Spider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Parser.AliExpressItemDetailParser;
import Com.Rantron.TaoBao.Parser.AliExpressSearchPageParser;

public class AliExpressSpider extends SpiderBase{

	private static final String aliExpressSearchUrl = "http://www.aliexpress.com/w/wholesale-hikvision.html?shipCountry=all&SortType=default&SearchText=[$KEYWORD]&g=y&page=[$PAGE]";

	/**
	 * 得到aliexpress 上的商品详情
	 * @param url
	 * @return
	 */
	public Object getItemDetailByItemUrl(String url)
	{
		Map<String, String> params = new HashMap<String, String>();
		JSONObject jsonObject=null;
		params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
		String htmlcontent = CatchHtml(url, params, proxy);
		jsonObject = (JSONObject)AliExpressItemDetailParser.getJsonData(htmlcontent);
		return jsonObject;
	
	}
	
	/**
	 * 得到aliexpress 搜索页面上的商品url
	 * @param SearchWords
	 * @param pageIndex
	 * @param sortType
	 * @return
	 */
	public List<String> getSearchPageItemUrlBySearchWords(String SearchWords, int pageIndex,SORTTYPE sortType) {
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		List<String> itemUrls = new ArrayList<String>();
		try {
				for (int index = 0; index <= 40; index += 20) {

					url = aliExpressSearchUrl.replace("[$KEYWORD]", URLEncoder.encode(SearchWords, "GBK"))
							.replace("[$PAGE]", String.valueOf(pageIndex));
					if(sortType == SORTTYPE.SALEDESC)
						url+="&SortType=total_tranpro_desc";
					params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
					String htmlcontent = CatchHtml(url, params, proxy);
					itemUrls.addAll(AliExpressSearchPageParser.getItemUrl(htmlcontent));
				}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemUrls;

	}
}
