package Com.Rantron.TaoBao.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class TmallItemCommentParser {

	public static List<String> getItemComments(String htmlContent)
	{
		JSONArray array = new JSONArray("[{"+htmlContent+"}]");
		JSONArray commentArray= array.getJSONObject(0).getJSONObject("rateDetail").getJSONArray("rateList");
		int len = commentArray.length();
		List<String> comments = new ArrayList<String>();
		for(int i=0;i<len;i++)
		{
			comments.add(commentArray.getJSONObject(i).getString("rateContent"));
		}
		return comments;
	}
	
	public static int getMaxCommentsPageNum(String htmlContent)
	{
		JSONArray array = new JSONArray("[{"+htmlContent+"}]");
		try
		{
			JSONObject jsonObj= array.getJSONObject(0).getJSONObject("rateDetail").getJSONObject("paginator");
			return Integer.parseInt(jsonObj.get("lastPage").toString());
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	public static void main(String []args)
	{
//		
//		Map<String, String> params = new HashMap<String, String>();
//		params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://rate.tmall.com/");
//		params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "5000");
//		params.put(CatchParamEnum.COOKIE.getName(), "thw=cn; cna=LKGwDthWlEsCAXPBq+2UFX4j; miid=7188889168917676727; cn_781594128355fac54e97_dplus=%7B%22distinct_id%22%3A%20%22150ffbd6ec69e8-0d1cc7fd7-681c107a-1fa400-150ffbd6ec7277%22%2C%22tbNick%22%3A%20%22fcjzj0105%22%7D; ali_ab=115.193.171.237.1445823893053.6; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; lzstat_uv=29428154233237827645|2857556; _cc_=U%2BGCWk%2F7og%3D%3D; tg=0; uc3=nk2=&id2=&lg2=; tracknick=; mt=ci=0_0&cyk=0_0; _m_h5_tk=440222fe1f0ac767d5bf3ca4eabf5d53_1448618248703; _m_h5_tk_enc=775c30d8422dbb1386565ecc0a056d5b; v=0; uc1=cookie14=UoWzUaLJftE2Rw%3D%3D; cookie2=1c37c6c7b536bcc9fb099367e03f55da; t=f55bb9e90b245e482386ff924697f7e5; isg=D5D88E5D3B13F7C4A3C6D38FF3213EF1");
//		RantronSpider rs = new RantronSpider();
//		String url ="https://rate.tmall.com/list_detail_rate.htm?itemId=41464129793&sellerId=1652490016&currentPage=1";
//		String content = rs.CatchHtml(url, params, null).trim();
	
		
	}
}
