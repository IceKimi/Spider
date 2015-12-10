package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import Com.Rantron.TaoBao.Spider.SpiderBase;
import Com.Rantron.TaoBao.Spider.TaoBaoSpider;

public class CommentsExecutor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// proxy.setProxys("123.59.87.193;120.132.93.186;120.132.93.131;120.132.93.183");
//		proxy.setProxys("192.168.0.33;192.168.0.46;192.168.0.47");

		ExecutorService pool = Executors.newFixedThreadPool(1);
		final String itemid = "524479715477";
		final String sellerid ="2228361831";
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				TaoBaoSpider Spider = new TaoBaoSpider();
				List<String> jsonArray = (List<String>) Spider.getItemComments(itemid,sellerid, 0,SpiderBase.ECOMMERCE.TAOBAO);
				System.out.println(jsonArray);

			}

		};
		pool.execute(runner);
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
