package Com.Rantron.TaoBao.Parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class TaoBaoSearchPageParser {

	public static List<String> getItemIdListByHtml(String content)
	{
		List<String> itemidList = new ArrayList<String>();
		Matcher m = Pattern.compile("\"nid\":\"([\\s\\S]*?)\",").matcher(content);
		while(m.find())
		{
			itemidList.add(m.group(1));
		}
		return itemidList;
	}
	
	public static Queue<String> getItemIdQueueByHtml(String content)
	{
		Queue<String> itemIdQueue = new LinkedList<String>();
		Matcher m = Pattern.compile("\"nid\":\"([\\s\\S]*?)\",").matcher(content);
		while(m.find())
		{
			itemIdQueue.add(m.group(1));
		}
		return itemIdQueue;
		
	}
	
	public static Object getItemidJsonByHtml(String content)
	{
		List<String> itemidList = new ArrayList<String>();
		Matcher m = Pattern.compile("\"nid\":\"([\\s\\S]*?)\",").matcher(content);
		while(m.find())
		{
			itemidList.add(m.group(1));
		}
		JSONObject jsonObject = new JSONObject(itemidList);
		return jsonObject;
	}
}
