package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import Com.Rantron.TaoBao.Spider.AlibabaItemDetailSpider;
import Com.Rantron.TaoBao.Spider.AlibabaSearchPageSpider;
import Com.Rantron.TaoBao.Spider.RantronSpider;
import Com.Rantron.TaoBao.Spider.TaoBaoItemCommentSpider;
import Com.Rantron.TaoBao.Spider.Cache.SpiderCache;
import Com.Rantron.TaoBao.Spider.Cache.TaoBaoItemCache;
import Com.Rantron.TaoBao.Spider.DB.AlibabaItemDetailBase2DB;
import Com.Rantron.TaoBao.Spider.DB.TaoBaoItemDetailBase2DB;

public class SpiderExecutor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AlibabaSearchPageSpider searchPageSpider = new AlibabaSearchPageSpider();
		final SpiderCache spiderCache = new TaoBaoItemCache();
		final AlibabaItemDetailBase2DB alibabaItemDetailBase2DB = new AlibabaItemDetailBase2DB();
		spiderCache.setCacheFile("D:/workspace/RantronSpider/CacheFile/Alibabaitemid");
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for (int i = 1; i < 100; i++) {
			final List<String> itemidlist = searchPageSpider.getSearchPageItemIdListBySearchWords("海康", i);
			final int b = i;
			Runnable runner = new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						System.out.println(Thread.currentThread().getName() + "\tStart\t" + "task" + (b + 1));
						for (String itemid : itemidlist) {
							AlibabaItemDetailSpider detailSpider = new AlibabaItemDetailSpider();
							detailSpider.setAccessWay(RantronSpider.AccessWay.PC);
							if (!spiderCache.contain(itemid)) {
								spiderCache.add(itemid);
								JSONObject jsonObj = (JSONObject) detailSpider.getItemDetailByItemid(itemid);
								alibabaItemDetailBase2DB.add2DB(jsonObj, "海康");
								
							}
							Thread.sleep(1000);
						}
						System.out.println("task" + (b + 1) + "\tfinished");

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
