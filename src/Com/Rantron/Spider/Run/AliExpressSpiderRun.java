package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Spider.AliExpressItemDetailSpider;
import Com.Rantron.TaoBao.Spider.AliExpressSearchPageSpider;
import Com.Rantron.TaoBao.Spider.Cache.ItemCache;
import Com.Rantron.TaoBao.Spider.Cache.SpiderCache;
import Com.Rantron.TaoBao.Spider.DB.AliExpressItemDetail2DB;

public class AliExpressSpiderRun {

	public static void main(String []args)
	{
		final String keyword = "hikvision";
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		AliExpressSearchPageSpider searchPageSpider = new AliExpressSearchPageSpider();
		final SpiderCache cache = new ItemCache();
		final AliExpressItemDetailSpider itemDetailSpider = new AliExpressItemDetailSpider();
		final AliExpressItemDetail2DB aliexpressItemDetail2DB = new AliExpressItemDetail2DB();
		cache.setCacheFile("D:\\workspace\\RantronSpider\\CacheFile\\AliExpressItemid");
		for (int i = 0; i < 100; i++) 
		{
			final List<String> itemUrls = searchPageSpider.getSearchPageItemUrlBySearchWords(keyword, i);
			Runnable runner = new Runnable() {
				@Override
				public void run() {
					for(String url:itemUrls)
					{
						if(cache.contain(url))
							continue;
						JSONObject jsonObject = (JSONObject)itemDetailSpider.getItemDetailByItemUrl(url);
						aliexpressItemDetail2DB.add2DB(jsonObject, keyword);
						cache.add(url);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}

			};
			pool.execute(runner);
		
		}
		
		
		pool.shutdown();
		while (true) {
			if (pool.isTerminated()) {
				System.out.println("任务结束");
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
