package Com.Rantron.TaoBao.Parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class TaoBaoItemCommentParser {

//	public static String[] getItemComments(String htmlContent)
//	{
//		JSONArray array = new JSONArray("["+htmlContent.replace("(", "").replace(")", "")+"]");
//		JSONArray commentsArray = array.getJSONObject(0).getJSONArray("comments");
//		int len = commentsArray.length();
//		String []commtens = new String[len];
//		for(int i=0;i<len;i++)
//		{
//			commtens[i] = commentsArray.getJSONObject(i).get("content").toString();
//		}
//		return commtens;
//		
//	}
	public static List<String> getItemComments(String htmlContent)
	{
		JSONArray array = new JSONArray("["+htmlContent.replace("(", "").replace(")", "")+"]");
		JSONArray commentsArray = array.getJSONObject(0).getJSONArray("comments");
		int len = commentsArray.length();
		List<String> commtents = new ArrayList<String>();
		for(int i=0;i<len;i++)
		{
			commtents.add(commentsArray.getJSONObject(i).get("content").toString());
		}
		return commtents;
		
	}
	
	public static int getMaxCommentsPageNum(String htmlContent)
	{
		JSONArray array = new JSONArray("["+htmlContent.replace("(", "").replace(")", "")+"]");
		try
		{
			return Integer.parseInt(array.getJSONObject(0).get("maxPage").toString());
		}
		catch(Exception e)
		{
			return 0;
		}
	}

}
