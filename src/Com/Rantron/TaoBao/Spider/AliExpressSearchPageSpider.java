package Com.Rantron.TaoBao.Spider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Com.Rantron.TaoBao.Parser.AliExpressSearchPageParser;
public class AliExpressSearchPageSpider extends RantronSpider{

	private static final String aliExpressSearchUrl = "http://www.aliexpress.com/w/wholesale-hikvision.html?shipCountry=all&SortType=default&SearchText=[$KEYWORD]&g=y&page=[$PAGE]";
	
	public List<String> getSearchPageItemUrlBySearchWords(String SearchWords, int pageIndex) {
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		List<String> itemUrls = new ArrayList<String>();
		try {
				for (int index = 0; index <= 40; index += 20) {

					url = aliExpressSearchUrl.replace("[$KEYWORD]", URLEncoder.encode(SearchWords, "GBK"))
							.replace("[$PAGE]", String.valueOf(pageIndex));

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
