package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Com.Rantron.TaoBao.Spider.AlibabaItemDetailSpider;
import Com.Rantron.TaoBao.Spider.AlibabaSearchPageSpider;
import Com.Rantron.TaoBao.Spider.RantronSpider;

public class SpiderExecutor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// RantronSpiderProxy proxy = RantronSpiderProxy.getInstance();
		// proxy.setProxys("192.168.0.33;192.168.0.46;192.168.0.47");
		AlibabaSearchPageSpider searchPageSpider = new AlibabaSearchPageSpider();
		// searchPageSpider.setProxy(proxy);
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for (int i = 1; i < 100; i++) {
			long t = System.currentTimeMillis();
			final List<String> itemidlist = searchPageSpider.getSearchPageItemIdListBySearchWords("海康", i);
			final int b = i;
			pool.submit(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						for (String itemid : itemidlist) {
							AlibabaItemDetailSpider detailSpider = new AlibabaItemDetailSpider();
							detailSpider.setAccessWay(RantronSpider.AccessWay.PC);
							// detailSpider.setProxy(proxy);
							detailSpider.getItemDetalByItemid(itemid);
							System.out.println(Thread.currentThread().getName());
							System.out.println(b);
							Thread.sleep(100);
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});

			System.out.println(System.currentTimeMillis() - t);

		}

	}

}
