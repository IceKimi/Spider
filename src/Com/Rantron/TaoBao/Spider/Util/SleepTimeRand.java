package Com.Rantron.TaoBao.Spider.Util;

import java.util.Random;

public class SleepTimeRand {

	static long[] sleepTimes={500,500,1000,1000,1000,1500,1500,1500,2000,2000,2000,2000};
	static Random random = new Random();
	public synchronized static long getSleepTime()
	{
		return sleepTimes[random.nextInt(12)];
	}
}
