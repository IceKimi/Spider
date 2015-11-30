package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Com.Rantron.Proxy.RantronSpiderProxy;
import Com.Rantron.TaoBao.Spider.TaoBaoSearchPageSpider;

public class KeywordsRankExecutor {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		final RantronSpiderProxy proxy = RantronSpiderProxy.getInstance();
		// proxy.setProxys("123.59.87.193;120.132.93.186;120.132.93.131;120.132.93.183");
		proxy.setProxys("192.168.0.33;192.168.0.46;192.168.0.47");
		final String targerId = "524119206813";
		String[] searchWords = { "狐狸毛超大毛领羽绒服","鹅绒羽绒服中长款女","大毛领鹅绒羽绒服中长款","大牌鹅绒羽绒服中长款女"};

		ExecutorService pool = Executors.newFixedThreadPool(2);
		for (final String keywords : searchWords) {

			Runnable runner = new Runnable() {
				@Override
				public void run() {
					TaoBaoSearchPageSpider searchPageSpider = new TaoBaoSearchPageSpider();
					// searchPageSpider.setProxy(proxy);
					// TODO Auto-generated method stub
					for (int i = 0; i < 100; i++) {

						final List<String> itemidlist = searchPageSpider.getSearchPageItemIdListBySearchWords(keywords,
								i);
						for (int index = 0; index < itemidlist.size(); index++) {
							if (itemidlist.get(index).compareTo(targerId)==0)
								System.out.println("page:" + (i + 1) + "\t" + "index:" + index+"\tkeywords:"+keywords);
						}
						try {
							Thread.sleep(1000);
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
