package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Spider.TaoBaoItemDetailSpider;
import Com.Rantron.TaoBao.Spider.TaoBaoSearchPageSpider;
import Com.Rantron.TaoBao.Spider.Cache.SpiderCache;
import Com.Rantron.TaoBao.Spider.Cache.TaoBaoItemCache;
import Com.Rantron.TaoBao.Spider.DB.TaoBaoItemDetailBase2DB;
import Com.Rantron.TaoBao.Spider.RantronSpider;


public class RunSpider {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		//final RantronSpiderProxy proxy = RantronSpiderProxy.getInstance();
		//proxy.setProxys("123.59.87.193;120.132.93.186;120.132.93.131;120.132.93.183");
		
		
		
		TaoBaoSearchPageSpider searchPageSpider = new TaoBaoSearchPageSpider();
		ExecutorService pool = Executors.newFixedThreadPool(5);
		final SpiderCache spiderCache = new TaoBaoItemCache();
		final TaoBaoItemDetailBase2DB baoItemDetailBase2DB = new TaoBaoItemDetailBase2DB();
		spiderCache.setCacheFile("D:/workspace/RantronSpider/CacheFile/itemid");
		for (int i = 0; i < 100; i++) {
			final int b = i;
			final List<String> itemidlist = searchPageSpider.getSearchPageItemIdListBySearchWords("海康", b);
			
			Runnable runner = new  Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						System.out.println(Thread.currentThread().getName()+"\tStart\t"+"task"+(b+1));
						for (String itemid : itemidlist) {
							TaoBaoItemDetailSpider detailSpider = new TaoBaoItemDetailSpider();
							//detailSpider.setProxy(proxy);
							detailSpider.setAccessWay(RantronSpider.AccessWay.MOBILE);
							if(!spiderCache.contain(itemid))
							{
								spiderCache.add(itemid);
								JSONObject jsonObj = (JSONObject)detailSpider.getItemDetailByItemid(itemid);
								baoItemDetailBase2DB.add2DB(jsonObj, "海康");
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
