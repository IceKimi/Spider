package Com.Rantron.TaoBao.Spider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Com.Rantron.TaoBao.Parser.TaoBaoSearchPageParser;

public class TaoBaoSearchPageSpider extends RantronSpider {


	private static final String ItemDetail_Mobile_URL_TAMPLATE = "http://hws.m.taobao.com/cache/wdetail/5.0/?id=[$ITEMID]";
	private static final String Search_URL_TAMPLATE = "https://s.taobao.com/search?q=[$SEARCHWORDS]&bcoffset=[$NTOFFSET]&ntoffset=[$NTOFFSET]&s=[$OFFSET]";

	private AccessWay accessWay = AccessWay.PC;

	public List<String> getSearchPageItemIdListBySearchWords(String SearchWords, int pageIndex) {
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		List<String> itemidList = new ArrayList<String>();
		try {
			if (accessWay == AccessWay.PC) {

				url = Search_URL_TAMPLATE.replace("[$SEARCHWORDS]", URLEncoder.encode(SearchWords, "GBK"))
						.replace("[$OFFSET]", URLEncoder.encode(String.valueOf(pageIndex*44), "GBK")).replace("[$NTOFFSET]", URLEncoder.encode(String.valueOf(5-3*pageIndex),"GBK"));

				params.put(CatchParamEnum.HEADER_REFERER.getName(), "http://s.taobao.com/");
				params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
				String htmlcontent = CatchHtml(url, params, proxy);
				itemidList.addAll(TaoBaoSearchPageParser.getItemIdListByHtml(htmlcontent));

			} else if (accessWay == AccessWay.MOBILE) {

				params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://items.taobao.com/");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemidList;

	}
	

}
