package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import Com.Rantron.TaoBao.Spider.RantronSpider;
import Com.Rantron.TaoBao.Spider.TaoBaoItemCommentSpider;
import Com.Rantron.TaoBao.Spider.TaoBaoItemDetailSpider;
import Com.Rantron.TaoBao.Spider.TaoBaoSearchPageSpider;
import Com.Rantron.TaoBao.Spider.Cache.SpiderCache;
import Com.Rantron.TaoBao.Spider.Cache.ItemCache;
import Com.Rantron.TaoBao.Spider.DB.ItemComments2DB;
import Com.Rantron.TaoBao.Spider.DB.TaoBaoItemDetailBase2DB;

public class RunSpiderLinux {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		// final RantronSpiderProxy proxy = RantronSpiderProxy.getInstance();
		// proxy.setProxys("123.59.87.193;120.132.93.186;120.132.93.131;120.132.93.183");

		TaoBaoSearchPageSpider searchPageSpider = new TaoBaoSearchPageSpider();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		final SpiderCache spiderCache = new ItemCache();
		final TaoBaoItemDetailBase2DB baoItemDetailBase2DB = new TaoBaoItemDetailBase2DB();
		final ItemComments2DB itemComments2DB = new ItemComments2DB();
		spiderCache.setCacheFile(args[0]);
		String[] keywords = args[1].split(";");
		for (final String keyword : keywords) {
			for (int i = 0; i < 100; i++) {
				final int b = i;
				final List<String> itemidlist = searchPageSpider.getSearchPageItemIdListBySearchWords(keyword, b);

				Runnable runner = new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							System.out.println(Thread.currentThread().getName() + "\tStart\t" + "task  " + keyword+"page:"+(b + 1));
							for (String itemid : itemidlist) {
								TaoBaoItemDetailSpider detailSpider = new TaoBaoItemDetailSpider();
								TaoBaoItemCommentSpider commentSpider = new TaoBaoItemCommentSpider();
								// detailSpider.setProxy(proxy);
								detailSpider.setAccessWay(RantronSpider.AccessWay.MOBILE);
								if (!spiderCache.contain(itemid)) {
									spiderCache.add(itemid);
									JSONObject jsonObj = (JSONObject) detailSpider.getItemDetailByItemid(itemid);
									baoItemDetailBase2DB.add2DB(jsonObj, keyword);
									List<String> itemComments = null;
									if (jsonObj.getString("ShopType").equals("C")) {
										itemComments = (List<String>) commentSpider.getItemComments(itemid, "", 1,
												RantronSpider.ECOMMERCE.TAOBAO);
										if (itemComments.size() > 0)
											itemComments2DB.add2DB(itemComments, Long.parseLong(itemid), keyword, 1, "C",
													1);
										itemComments = (List<String>) commentSpider.getItemComments(itemid, "", 0,
												RantronSpider.ECOMMERCE.TAOBAO);
										if (itemComments.size() > 0)
											itemComments2DB.add2DB(itemComments, Long.parseLong(itemid), keyword, 0, "C",
													1);
										itemComments = (List<String>) commentSpider.getItemComments(itemid, "", -1,
												RantronSpider.ECOMMERCE.TAOBAO);
										if (itemComments.size() > 0)
											itemComments2DB.add2DB(itemComments, Long.parseLong(itemid), keyword, -1,
													"C", 1);
									} else if (jsonObj.getString("ShopType").equals("B")) {
										itemComments = (List<String>) commentSpider.getItemComments(itemid,
												jsonObj.getString("SellerId"), 1, RantronSpider.ECOMMERCE.TMALL);
										if (itemComments.size() > 0)
											itemComments2DB.add2DB(itemComments, Long.parseLong(itemid), keyword, 99999,
													"B", 1);
									}
								}
								Thread.sleep(100);
							}
							System.out.println("task  " + keyword+"page:"+(b + 1) + "\tfinished");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				};
				pool.execute(runner);
				System.out.println("task  " + keyword+"page:"+(b + 1) + "\t submit");

			}
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
