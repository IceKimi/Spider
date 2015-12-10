package Com.Rantron.TaoBao.Spider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Parser.AlibabaItemDetailParser;
import Com.Rantron.TaoBao.Parser.AlibabaMobileItemDetailParser;
import Com.Rantron.TaoBao.Parser.AlibabaSearchPageParser;

public class AliBaBaSpider extends SpiderBase{

	private static final String ItemDetail_Mobile_URL_TAMPLATE = "http://m.1688.com/offer/[$ITEMID].html";
	private static final String ItemDetail_URL_TAMPLATE = "http://detail.1688.com/offer/[$ITEMID].html ";
	private static final String Search_URL_TAMPLATE = "http://s.1688.com/selloffer/rpc_async_render.jsonp?n=y&uniqfield=pic_tag_id&rpcflag=new&_serviceId_=marketOfferResultViewService&maxPage=100&keywords=[@SEARCHWORDS]&startIndex=[$START]&_template_=controls%2Fnew_template%2Fproducts%2Fmarketoffersearch%2Fofferresult%2Fpkg-a%2Fviews%2Fofferresult.vm&enableAsync=true&asyncCount=20&showMySearchUrl=true&priceEnd=3.4028235E38&offset=2&async=true&beginPage=[$BEGINPAGE]&token=23423&callback=jQuery183028966005193069577_1448425419567";

	
	/**
	 * 得到1688上的商品详情信息
	 * @param itemid
	 * @return
	 */
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
	
	
	/**
	 * 得到1688搜索页的商品ID
	 * @param SearchWords
	 * @param pageIndex
	 * @param sortType
	 * @return
	 */

	public List<String> getSearchPageItemIdListBySearchWords(String SearchWords, int pageIndex,SORTTYPE sortType) {
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		List<String> itemidList = new ArrayList<String>();
		try {
			if (accessWay == AccessWay.PC) {
				for (int index = 0; index <= 40; index += 20) {

					url = Search_URL_TAMPLATE.replace("[@SEARCHWORDS]", URLEncoder.encode(SearchWords, "GBK"))
							.replace("[$BEGINPAGE]",  URLEncoder.encode(String.valueOf(pageIndex), "GBK"))
							.replace("[$START]", URLEncoder.encode(String.valueOf(index), "GBK"));
					if(sortType == SORTTYPE.SALEDESC)
						url +="&sortType=booked";
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
