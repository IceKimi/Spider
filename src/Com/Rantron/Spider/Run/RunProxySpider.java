package Com.Rantron.Spider.Run;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import com.bg.entity.cluster.SusongData;

import Com.Rantron.Proxy.RantronSpiderProxy;
import Com.Rantron.TaoBao.Spider.SpiderBase.CatchParamEnum;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.RequestConfig;



public class RunProxySpider {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String url = "http://58.55.195.85:18001/taobaoproxy/servlet/item/";
		final String paramsUrl = "getPageItemInfoByWordAndCat.do?word=hikvision&page=[$pages]&sort=renqi-desc";
		ExecutorService pool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 100; i++) {
			final int b = i;
			
			
			Runnable runner = new  Runnable() {
				@Override
				public void run() {
					Map<String, String> params = new HashMap<String, String>();
					//params.put(CatchParamEnum.HEADER_REFERER.getName(), "https://s.taobao.com/");
					params.put(CatchParamEnum.TARGET_TIMEOUT.getName(), "50000");
					String Tmpparams = paramsUrl.replace("[$pages]",String.valueOf(b));
					final long t = System.currentTimeMillis();
					System.out.println(CatchHtml(url+Tmpparams,params,null));
					System.out.println(System.currentTimeMillis() - t);
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
	
	public static String CatchHtml(String url,Map<String, String> params,RantronSpiderProxy rantronSpiderProxy)
	{
		RequestConfig config = new RequestConfig();
		config.setTimeoutForConnect(5000);
		if(params!=null && params.containsKey(CatchParamEnum.HEADER_REFERER.getName()))
			config.addHeader(CatchParamEnum.HEADER_REFERER.getName(), params.get(CatchParamEnum.HEADER_REFERER.getName()));
		if (params != null && params.containsKey(CatchParamEnum.COOKIE.getName())) {
			config.setCookie(params.get(CatchParamEnum.COOKIE.getName()));
		}
		
		if(rantronSpiderProxy!=null)
		{
			String ipAddress = rantronSpiderProxy.getProxy();
			InetSocketAddress addr = new InetSocketAddress(ipAddress, 18002);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
			config.setProxy(proxy);
		}
		
		try {
			HttpRequest request = new HttpRequest(url, config);
			return request.getResponse().getHtmlByCharsetDetect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}
