package Com.Rantron.TaoBao.Spider;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;


import Com.Rantron.Proxy.RantronSpiderProxy;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.RequestConfig;

public class SpiderBase {

	
	
	public enum SORTTYPE
	{
		DEFAULT,
		SALEDESC,
		RENQIDESC,
		CREDITDESC;
		
	}
	public enum AccessWay {
		PC,
		MOBILE;
	}
	
	public enum ECOMMERCE
	{
		TAOBAO,
		TMALL,
		JD,
		AMAZON;
	}
	public enum CatchParamEnum {
		HEADER_USER_AGENT("User-Agent"), HEADER_HOST("host"), HEADER_ACCEPT("Accept"), HEADER_ACCEPT_LANGUAGE("Accept-Language"), HEADER_ACCEPT_ENCODING(
				"Accept-Encoding"), HEADER_REFERER("Referer"), HEADER_CONNECTION("Connection"), HEADER_CONTENT_TYPE("Content-Type"), HEADER_X_FORWARDED_FOR(
				"X-Forwarded-For"), TARGET_URL("_targetUrl"), TARGET_ENCODING("_targetEncoding"), TARGET_TIMEOUT("_targetTimeout"), PARAM_ENCODING(
				"_paramEncoding"), SUBMIT_METHOD("_submitMethod"), CONTENT_REGEX("_contentRegex"), RANDOM_IP("_randomIp"), NO_CACHE(
				"_no_cache"), // createRandomIp
		COOKIE("Cookie");

		private String name;

		CatchParamEnum(String name) {
			this.name = name;
		}

		public static CatchParamEnum getEnum(String name) throws RuntimeException {
			CatchParamEnum[] pesEnum = CatchParamEnum.values();
			for (int i = 0; i < pesEnum.length; i++) {
				if (pesEnum[i].getName().equals(name)) {
					return pesEnum[i];
				}
			}
			return null;
		}

		public static boolean contains(String name) throws RuntimeException {
			CatchParamEnum[] pesEnum = CatchParamEnum.values();
			for (int i = 0; i < pesEnum.length; i++) {
				if (pesEnum[i].getName().equals(name)) {
					return true;
				}
			}
			return false;
		}

		public String getName() {
			return name;
		}
	}
	
	public RantronSpiderProxy proxy = null;
	public RequestConfig requestConfig = new RequestConfig();
	
	protected AccessWay accessWay;
	
	public String CatchHtml(String url,Map<String, String> params,RantronSpiderProxy rantronSpiderProxy)
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
	
	public  void setProxy(RantronSpiderProxy proxy)
	{
		this.proxy  = proxy;
	}
	
	public void setAccessWay(AccessWay accessWay)
	{
		this.accessWay = accessWay;
	}
}
