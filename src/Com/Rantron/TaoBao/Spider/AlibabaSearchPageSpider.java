package Com.Rantron.TaoBao.Spider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Com.Rantron.TaoBao.Parser.AlibabaSearchPageParser;

public class AlibabaSearchPageSpider extends RantronSpider {


	private static final String ItemDetail_Mobile_URL_TAMPLATE = "http://hws.m.taobao.com/cache/wdetail/5.0/?id=[$ITEMID]";
	private static final String Search_URL_TAMPLATE = "http://s.1688.com/selloffer/rpc_async_render.jsonp?n=y&uniqfield=pic_tag_id&rpcflag=new&_serviceId_=marketOfferResultViewService&maxPage=100&keywords=[@SEARCHWORDS]&startIndex=[$START]&_template_=controls%2Fnew_template%2Fproducts%2Fmarketoffersearch%2Fofferresult%2Fpkg-a%2Fviews%2Fofferresult.vm&enableAsync=true&asyncCount=20&showMySearchUrl=true&priceEnd=3.4028235E38&offset=2&async=true&beginPage=[$BEGINPAGE]&token=23423&callback=jQuery183028966005193069577_1448425419567";


	public List<String> getSearchPageItemIdListBySearchWords(String SearchWords, int pageIndex) {
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		List<String> itemidList = new ArrayList<String>();
		try {
			if (accessWay == AccessWay.PC) {
				for (int index = 0; index <= 40; index += 20) {

					url = Search_URL_TAMPLATE.replace("[@SEARCHWORDS]", URLEncoder.encode(SearchWords, "GBK"))
							.replace("[$BEGINPAGE]",  URLEncoder.encode(String.valueOf(pageIndex), "GBK"))
							.replace("[$START]", URLEncoder.encode(String.valueOf(index), "GBK"));

					params.put(CatchParamEnum.HEADER_REFERER.getName(), "http://s.1688.com/");
					params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
					String htmlcontent = CatchHtml(url, params, proxy);
					itemidList.addAll(AlibabaSearchPageParser.getItemIdListByHtml(htmlcontent));
				}

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
