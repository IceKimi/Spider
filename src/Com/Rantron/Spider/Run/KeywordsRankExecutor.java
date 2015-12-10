package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Com.Rantron.Proxy.RantronSpiderProxy;
import Com.Rantron.TaoBao.Spider.SpiderBase;
import Com.Rantron.TaoBao.Spider.TaoBaoSpider;

public class KeywordsRankExecutor {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		final RantronSpiderProxy proxy = RantronSpiderProxy.getInstance();
		// proxy.setProxys("123.59.87.193;120.132.93.186;120.132.93.131;120.132.93.183");
		proxy.setProxys("192.168.0.33;192.168.0.46;192.168.0.47");
		final String targerId = "524393794968";
		String[] searchWords = {"老佛爷羽绒服"};

		ExecutorService pool = Executors.newFixedThreadPool(1);
		for (final String keywords : searchWords) {
			for (int i = 0; i < 100; i++) {
				final int j=i;
			Runnable runner = new Runnable() {
				@Override
				public void run() {
					TaoBaoSpider searchPageSpider = new TaoBaoSpider();
					// searchPageSpider.setProxy(proxy);
					// TODO Auto-generated method stub
					
						System.out.println(j);
						final List<String> itemidlist = searchPageSpider.getSearchPageItemIdListBySearchWords(keywords,j,SpiderBase.SORTTYPE.DEFAULT);
						for (int index = 0; index < itemidlist.size(); index++) {
							if (itemidlist.get(index).compareTo(targerId)==0)
								System.out.println("page:" + (j + 1) + "\t" + "index:" + index+"\tkeywords:"+keywords);
						}
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				

			};
			pool.execute(runner);
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
