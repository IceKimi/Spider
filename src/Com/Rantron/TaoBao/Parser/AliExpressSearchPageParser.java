package Com.Rantron.TaoBao.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AliExpressSearchPageParser {

	
	///http://www.aliexpress.com/w/wholesale-hikvision.html?spm=2114.01020208.0.335.cKxwtl&site=glo&shipCountry=all&SortType=default&SearchText=hikvision&g=y
	public static List<String> getItemUrl(String htmlContent)
	{
		List<String> urls = new ArrayList<String>();
		Matcher m = Pattern.compile("<a class=\"picRind history-item \" href=\"([\\s\\S]*?)\\?").matcher(htmlContent);
		while(m.find())
		{
			urls.add(m.group(1));
		}
		return urls;
	}
}
