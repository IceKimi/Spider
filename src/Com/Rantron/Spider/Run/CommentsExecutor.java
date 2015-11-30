package Com.Rantron.Spider.Run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import Com.Rantron.Proxy.RantronSpiderProxy;
import Com.Rantron.TaoBao.Spider.RantronSpider;
import Com.Rantron.TaoBao.Spider.TaoBaoItemCommentSpider;
import Com.Rantron.TaoBao.Spider.TaoBaoSearchPageSpider;

public class CommentsExecutor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final RantronSpiderProxy proxy = RantronSpiderProxy.getInstance();
		// proxy.setProxys("123.59.87.193;120.132.93.186;120.132.93.131;120.132.93.183");
//		proxy.setProxys("192.168.0.33;192.168.0.46;192.168.0.47");

		ExecutorService pool = Executors.newFixedThreadPool(1);
		final String itemid = "522987774537";

		Runnable runner = new Runnable() {
			@Override
			public void run() {
				TaoBaoItemCommentSpider Spider = new TaoBaoItemCommentSpider();
				JSONArray jsonArray = (JSONArray) Spider.getItemComments(itemid, RantronSpider.ECOMMERCE.TAOBAO);
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
