package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;


import Com.Rantron.TaoBao.Spider.TaoBaoSpider;
import Com.Rantron.TaoBao.Spider.Cache.SpiderCache;
import Com.Rantron.TaoBao.Spider.Cache.ItemCache;
import Com.Rantron.TaoBao.Spider.SpiderBase;


public class RunSpider {

	public static void main(String[] args) {

		final TaoBaoSpider taoBaoSpider = new TaoBaoSpider();
		ExecutorService pool = Executors.newFixedThreadPool(1);
		final SpiderCache spiderCache = new ItemCache();
		spiderCache.setCacheFile("D:/workspace/RantronSpider/CacheFile/itemid");
		for (int i = 0; i < 100; i++) {
			final int b = i;
			final List<String> itemidlist = taoBaoSpider.getSearchPageItemIdListBySearchWords("海康威视", b,SpiderBase.SORTTYPE.SALEDESC);
			Runnable runner = new  Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						System.out.println(Thread.currentThread().getName()+"\tStart\t"+"task"+(b+1));
						for (String itemid : itemidlist) {
							//detailSpider.setProxy(proxy);
							if(!spiderCache.contain(itemid))
							{
								spiderCache.add(itemid);
								JSONObject jsonObj = (JSONObject)taoBaoSpider.getItemDetailByItemid(itemid);
								System.out.println(jsonObj);
							}
							Thread.sleep(100);
						}
						System.out.println("task"+(b+1)+"\tfinished");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};
			pool.execute(runner);
			System.out.println("task"+(b+1)+"\t submit");

		}
		pool.shutdown();
         while(true){
             if(pool.isTerminated()){
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
