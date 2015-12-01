package Com.Rantron.TaoBao.Spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;


import Com.Rantron.TaoBao.Parser.TaoBaoItemCommentParser;
import Com.Rantron.TaoBao.Parser.TmallItemCommentParser;
import Com.Rantron.TaoBao.Spider.Util.SleepTimeRand;

public class TaoBaoItemCommentSpider extends RantronSpider {

	private static final String TAOBAOITEMCOMMENTSURL = "https://rate.taobao.com/feedRateList.htm?auctionNumId=[$ITEMID]&currentPageNum=[$CURRENTPAGE]";
	private static final String TMALLITEMCOMMENTSURL = "https://rate.tmall.com/list_detail_rate.htm?itemId=[$ITEMID]&sellerId=[$SELLERID]&currentPage=[$CURRENTPAGE]";
	
	public Object getItemComments(String itemid,String sellerid, ECOMMERCE ecommerce) {
		Map<String, String> params = new HashMap<String, String>();
		String url = "";
		List<String> comments = new ArrayList<String>();
		if(ecommerce.equals(ECOMMERCE.TAOBAO))
		{
			int currentPage = 1;
			int maxPage = -1;
			do {
				url = TAOBAOITEMCOMMENTSURL.replace("[$ITEMID]", itemid).replace("[$CURRENTPAGE]", String.valueOf(currentPage));
				params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://s.taobao.com/");
				params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
				params.put(CatchParamEnum.COOKIE.getName(), "thw=cn; cna=LKGwDthWlEsCAXPBq+2UFX4j; miid=7188889168917676727; ali_ab=115.193.171.237.1445823893053.6; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; lzstat_uv=29428154233237827645|2857556; _m_h5_tk=440222fe1f0ac767d5bf3ca4eabf5d53_1448618248703; _m_h5_tk_enc=775c30d8422dbb1386565ecc0a056d5b; _cc_=VT5L2FSpdA%3D%3D; tg=0; uc3=nk2=&id2=&lg2=; tracknick=; mt=ci=0_0&cyk=0_0; v=0; _tb_token_=pOw8KlGfZ54jZR9; cookie2=2c00ad017969a0e52ce7ce9bfdd59bf8; t=a0d831f94e3170c935f663de23eb3982; uc1=cookie14=UoWzUaP1%2F%2Bf1Hw%3D%3D; isg=6E0BF739F1D1F0ED43A704FE010E2E14; l=ApqaNcg5/Ue4FUI51IOgQCKRak68Dx6E");
				String htmlcontent = CatchHtml(url, params, proxy);
				maxPage = TaoBaoItemCommentParser.getMaxCommentsPageNum(htmlcontent);
				comments.addAll(TaoBaoItemCommentParser.getItemComments(htmlcontent));
				currentPage++;
				try {
//					Thread.sleep(SleepTimeRand.getSleepTime());
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (currentPage<=maxPage);
		}
		else if(ecommerce.equals(ECOMMERCE.TMALL))
		{
			int currentPage = 1;
			int maxPage = -1;
			do {
				url = TMALLITEMCOMMENTSURL.replace("[$ITEMID]", itemid).replace("[$CURRENTPAGE]", String.valueOf(currentPage)).replace("[$SELLERID]", sellerid);
				params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://rate.tmall.com/");
				params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
				String htmlcontent = CatchHtml(url, params, proxy);
				maxPage = TmallItemCommentParser.getMaxCommentsPageNum(htmlcontent);
				comments.addAll(TmallItemCommentParser.getItemComments(htmlcontent));
				currentPage++;
				try {
//					Thread.sleep(SleepTimeRand.getSleepTime());
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (currentPage<=maxPage);
		}
		//JSONArray jsonArray = new JSONArray(comments);
		return comments;

		//return jsonArray;

	}
}
