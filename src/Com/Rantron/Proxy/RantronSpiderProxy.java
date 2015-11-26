package Com.Rantron.Proxy;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.xyy.bean.PairLong;



public class RantronSpiderProxy {
	
	private static class LazyHolder
	{
		private static final RantronSpiderProxy INSTANCE = new RantronSpiderProxy();
	}
	private RantronSpiderProxy(){};
	public static final RantronSpiderProxy getInstance()
	{
		return LazyHolder.INSTANCE;
	}
	
	
	private  List<PairLong> pairs;
	public   void setProxys(String ips) {
		pairs = new ArrayList<PairLong>();
		for (String ip : ips.split(";")) {
			pairs.add(new PairLong(ip, System.currentTimeMillis()-1000));
		}
		
	}

	public  synchronized String getProxy() {
		for (;;) {
			Collections.sort(pairs, new Comparator<PairLong>() {
				@Override
				public int compare(PairLong o1, PairLong o2) {
					return (int) (o1.value - o2.value);
				}
			});
			if (pairs.get(0).value > System.currentTimeMillis()-3000) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				pairs.get(0).value = System.currentTimeMillis();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return pairs.get(0).key;
			}
		}
	}

}
