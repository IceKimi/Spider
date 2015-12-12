package Com.Rantron.TaoBao.Spider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Parser.TaoBaoItemCommentParser;
import Com.Rantron.TaoBao.Parser.TaoBaoMobileItemDetailParser;
import Com.Rantron.TaoBao.Parser.TaoBaoSearchPageParser;
import Com.Rantron.TaoBao.Parser.TmallItemCommentParser;

public class TaoBaoSpider extends SpiderBase {

	private static final String ItemDetail_Mobile_URL_TAMPLATE = "http://hws.m.taobao.com/cache/wdetail/5.0/?id=[$ITEMID]";
	private static final String ItemDetail_URL_TAMPLATE = "";

	private static final String TAOBAOITEMCOMMENTSURL = "https://rate.taobao.com/feedRateList.htm?auctionNumId=[$ITEMID]&currentPageNum=[$CURRENTPAGE]&rateType=[$RATETYPE]";
	private static final String TMALLITEMCOMMENTSURL = "https://rate.tmall.com/list_detail_rate.htm?itemId=[$ITEMID]&sellerId=[$SELLERID]&currentPage=[$CURRENTPAGE]";

	private static final String Search_URL_TAMPLATE = "https://s.taobao.com/search?q=[$SEARCHWORDS]&bcoffset=[$NTOFFSET]&ntoffset=[$NTOFFSET]&s=[$OFFSET]";

	/**
	 * 获取淘宝商品的详情 返回json格式
	 * 
	 * @param itemid
	 * @return
	 */
	public Object getItemDetailByItemid(String itemid) {
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		JSONObject jsonObject = null;

		url = ItemDetail_Mobile_URL_TAMPLATE.replace("[$ITEMID]", itemid);
		params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://s.taobao.com/");
		params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
		String htmlcontent = CatchHtml(url, params, proxy);
		if(TaoBaoMobileItemDetailParser.blocked(htmlcontent))
		{
			System.out.println(url+"\t has been blocked");
			return null;
		}
		jsonObject =  TaoBaoMobileItemDetailParser.getJsonData(htmlcontent);
		return jsonObject;
	}

	/**
	 * 得到商品的评论信息
	 * 
	 * @param itemid
	 * @param sellerid
	 *            天猫需要
	 * @param rateType
	 *            1,0,-1(好评，中评，差评)
	 * @param ecommerce(电商类别)
	 * @return
	 */

	public List<String> getItemComments(String itemid, String sellerid, int rateType, ECOMMERCE ecommerce) {
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		List<String> comments = new ArrayList<String>();
		if (ecommerce.equals(ECOMMERCE.TAOBAO)) {
			int currentPage = 1;
			int maxPage = -1;
			do {
				url = TAOBAOITEMCOMMENTSURL.replace("[$ITEMID]", itemid)
						.replace("[$CURRENTPAGE]", String.valueOf(currentPage))
						.replace("[$RATETYPE]", String.valueOf(rateType));
				params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://s.taobao.com/");
				params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
				params.put(CatchParamEnum.COOKIE.getName(),
						"thw=cn; cna=LKGwDthWlEsCAXPBq+2UFX4j; miid=7188889168917676727;ali_ab=115.193.171.237.1445823893053.6; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; lzstat_uv=29428154233237827645|2857556; _m_h5_tk=440222fe1f0ac767d5bf3ca4eabf5d53_1448618248703; _m_h5_tk_enc=775c30d8422dbb1386565ecc0a056d5b; _cc_=UIHiLt3xSw%3D%3D; tg=0; uc3=nk2=&id2=&lg2=; tracknick=; mt=ci=0_0; v=0; cookie2=1cf75261ed8fbe266b209e4d35372629; t=e29d0a794860754bb06443ecc2ac30f0; _tb_token_=8a3Qfbys8DPjL4; isg=C854907405FB5B6565C677A924960762; uc1=cookie14=UoWzUGXM6Jppeg%3D%3D; l=AnR0pzqI2571SFzrvsVmDzDyxDzmFpg9");
				String htmlcontent = CatchHtml(url, params, proxy);
				maxPage = TaoBaoItemCommentParser.getMaxCommentsPageNum(htmlcontent);
				comments.addAll(TaoBaoItemCommentParser.getItemComments(htmlcontent));
				currentPage++;
				try {
					// Thread.sleep(SleepTimeRand.getSleepTime());
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (currentPage <= maxPage);
		} else if (ecommerce.equals(ECOMMERCE.TMALL)) {
			int currentPage = 1;
			int maxPage = -1;
			do {
				url = TMALLITEMCOMMENTSURL.replace("[$ITEMID]", itemid)
						.replace("[$CURRENTPAGE]", String.valueOf(currentPage)).replace("[$SELLERID]", sellerid);
				params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://rate.tmall.com/");
				params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
				String htmlcontent = CatchHtml(url, params, proxy);
				maxPage = TmallItemCommentParser.getMaxCommentsPageNum(htmlcontent);
				comments.addAll(TmallItemCommentParser.getItemComments(htmlcontent));
				currentPage++;
				try {
					// Thread.sleep(SleepTimeRand.getSleepTime());
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (currentPage <= maxPage);
		}
		// JSONArray jsonArray = new JSONArray(comments);
		return comments;

		// return jsonArray;

	}

	/**
	 * 得到淘宝搜索页的搜索结果商品ID
	 * 
	 * @param SearchWords
	 * @param pageIndex
	 * @param sortType
	 * @return
	 */
	public List<String> getSearchPageItemIdListBySearchWords(String SearchWords, int pageIndex, SORTTYPE sortType) {
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		List<String> itemidList = new ArrayList<String>();
		try {
			url = Search_URL_TAMPLATE.replace("[$SEARCHWORDS]", URLEncoder.encode(SearchWords, "GBK"))
					.replace("[$OFFSET]", URLEncoder.encode(String.valueOf(pageIndex * 44), "GBK"))
					.replace("[$NTOFFSET]", URLEncoder.encode(String.valueOf(5 - 3 * pageIndex), "GBK"));
			if (sortType == SORTTYPE.SALEDESC)
				url += "&sort=sale-desc";
			else if (sortType == SORTTYPE.RENQIDESC)
				url += "&sort=renqi-desc";
			else if (sortType == SORTTYPE.CREDITDESC)
				url += "&sort=credit-desc";
			params.put(CatchParamEnum.HEADER_REFERER.getName(), "http://s.taobao.com/");
			params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
			params.put(CatchParamEnum.COOKIE.getName(),
					"thw=cn; cna=LKGwDthWlEsCAXPBq+2UFX4j; miid=7188889168917676727; cn_781594128355fac54e97_dplus=%7B%22distinct_id%22%3A%20%22150ffbd6ec69e8-0d1cc7fd7-681c107a-1fa400-150ffbd6ec7277%22%2C%22tbNick%22%3A%20%22fcjzj0105%22%7D; ali_ab=115.193.171.237.1445823893053.6; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; lzstat_uv=29428154233237827645|2857556; linezing_session=geaIFJL9uKpCLNZq5JRN17jP_1449641210967kEOk_2; alitrackid=login.taobao.com; _m_user_unitinfo_=unit|unit; _m_unitapi_v_=1446197822170; _tb_token_=5e7ee4351385b; v=0; uc3=nk2=BdNqucg6lfGD&id2=UoCIQeIGqKK3&vt3=F8dAScPjp3o9yxshiY0%3D&lg2=W5iHLLyFOGW7aA%3D%3D; existShop=MTQ0OTY1MzIyOA%3D%3D; lgc=fcjzj0105; tracknick=fcjzj0105; sg=599; cookie2=1ce1ec71ade655c2806fc912a3e1105e; cookie1=VvaNkjQuPCKZGcGpzXgRtMAIiLdxnaH%2BSzuuIgR0bME%3D; unb=111798879; skt=6902ee874ffeaa99; t=a5c09c5a69387cbd968e9d43279cc87e; publishItemObj=Ng%3D%3D; _cc_=WqG3DMC9EA%3D%3D; tg=0; _l_g_=Ug%3D%3D; _nk_=fcjzj0105; cookie17=UoCIQeIGqKK3; lastalitrackid=login.taobao.com; uc1=cookie14=UoWzUGIMrGbQfg%3D%3D&cookie16=WqG3DMC9UpAPBHGz5QBErFxlCA%3D%3D&existShop=false&cookie21=W5iHLLyFeYTE&tag=7&cookie15=W5iHLLyFOGW7aA%3D%3D&pas=0; mt=ci=8_1; _m_h5_tk=c3ee19ae883cfc0c5f66d34486f475b4_1449658271228; _m_h5_tk_enc=813c283a5f0edc8b3df647693804912d; JSESSIONID=5F3B03B94436CCEC2FEEE297B8A37B32; Hm_lvt_73a389ead45490aaf952f750657e830f=1449651677,1449652762,1449652776,1449653231; Hm_lpvt_73a389ead45490aaf952f750657e830f=1449653754; l=ApqaN2qd/Ue4FQKZPKWget7qak69pB6l; isg=ED67C05B0B18084DB24B4BDF641A2BA2");

			String htmlcontent = CatchHtml(url, params, proxy);
			itemidList.addAll(TaoBaoSearchPageParser.getItemIdListByHtml(htmlcontent));

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemidList;

	}

}
