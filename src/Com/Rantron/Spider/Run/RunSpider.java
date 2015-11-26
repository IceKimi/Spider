package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Com.Rantron.TaoBao.Spider.TaoBaoItemDetailSpider;
import Com.Rantron.TaoBao.Spider.TaoBaoSearchPageSpider;
import Com.Rantron.Proxy.RantronSpiderProxy;
import Com.Rantron.TaoBao.Spider.RantronSpider;


public class RunSpider {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		//final RantronSpiderProxy proxy = RantronSpiderProxy.getInstance();
		//proxy.setProxys("123.59.87.193;120.132.93.186;120.132.93.131;120.132.93.183");
		TaoBaoSearchPageSpider searchPageSpider = new TaoBaoSearchPageSpider();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 100; i++) {
			long t = System.currentTimeMillis();
			final int b = i;
			final List<String> itemidlist = searchPageSpider.getSearchPageItemIdListBySearchWords("海康", b);
			
			Runnable runner = new  Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						System.out.println(Thread.currentThread().getName()+"\tStart\t"+b);
						for (String itemid : itemidlist) {
							TaoBaoItemDetailSpider detailSpider = new TaoBaoItemDetailSpider();
							//detailSpider.setProxy(proxy);
							detailSpider.setAccessWay(RantronSpider.AccessWay.MOBILE);
							detailSpider.getItemDetailByItemid(itemid);
						
							Thread.sleep(1);
						}
						System.out.println(b+"\tfinished");
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};
			pool.execute(runner);

			System.out.println(System.currentTimeMillis() - t);

		}
	}

}
