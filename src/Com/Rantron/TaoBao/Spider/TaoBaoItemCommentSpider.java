package Com.Rantron.TaoBao.Spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


import Com.Rantron.TaoBao.Parser.TaoBaoItemCommentParser;

public class TaoBaoItemCommentSpider extends RantronSpider {

	private static final String TAOBAOITEMCOMMENTSURL = "https://rate.taobao.com/feedRateList.htm?auctionNumId=[$ITEMID]&currentPageNum=[$CURRENTPAGE]";
	public Object getItemComments(String itemid, ECOMMERCE ecommerce) {
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
				params.put(CatchParamEnum.COOKIE.getName(), "thw=cn; cna=LKGwDthWlEsCAXPBq+2UFX4j; miid=7188889168917676727; cn_781594128355fac54e97_dplus=%7B%22distinct_id%22%3A%20%22150ffbd6ec69e8-0d1cc7fd7-681c107a-1fa400-150ffbd6ec7277%22%2C%22tbNick%22%3A%20%22fcjzj0105%22%7D; ali_ab=115.193.171.237.1445823893053.6; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; lzstat_uv=29428154233237827645|2857556; _m_h5_tk=440222fe1f0ac767d5bf3ca4eabf5d53_1448618248703; _m_h5_tk_enc=775c30d8422dbb1386565ecc0a056d5b; v=0; linezing_session=OKvAia0msT8487h1eEvkbgm6_144886341475562gn_1; uc3=nk2=BdNqucg6lfGD&id2=UoCIQeIGqKK3&vt3=F8dAScAbVwrjJkSRz8k%3D&lg2=Vq8l%2BKCLz3%2F65A%3D%3D; existShop=MTQ0ODg3MTQ2MQ%3D%3D; lgc=fcjzj0105; tracknick=fcjzj0105; sg=599; cookie2=1c9f112a78b4ff347e1e9fa912e60535; cookie1=VvaNkjQuPCKZGcGpzXgRtMAIiLdxnaH%2BSzuuIgR0bME%3D; unb=111798879; skt=903fbd9472d04605; t=a0d831f94e3170c935f663de23eb3982; publishItemObj=Ng%3D%3D; _cc_=VT5L2FSpdA%3D%3D; tg=0; _l_g_=Ug%3D%3D; _nk_=fcjzj0105; cookie17=UoCIQeIGqKK3; mt=ci=8_1&cyk=0_0; uc1=cookie14=UoWzUaLIX6UCpA%3D%3D&existShop=false&cookie16=Vq8l%2BKCLySLZMFWHxqs8fwqnEw%3D%3D&cookie21=WqG3DMC9Fbxq&tag=7&cookie15=W5iHLLyFOGW7aA%3D%3D&pas=0; _tb_token_=tvp77j4DajvMd57; isg=FBB83EF01BF7913B0E3EBD0AABE32580; l=AhkZPeW4zlof4CEcq37TrCVkqQvyxg0P");
				String htmlcontent = CatchHtml(url, params, proxy);
				maxPage = TaoBaoItemCommentParser.getMaxCommentsPageNum(htmlcontent);
				comments.addAll(TaoBaoItemCommentParser.getItemComments(htmlcontent));
				currentPage++;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (currentPage<=maxPage);
		}
		JSONArray jsonArray = new JSONArray(comments);
		

		return jsonArray;

	}
}
