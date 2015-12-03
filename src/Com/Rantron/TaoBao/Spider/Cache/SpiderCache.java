package Com.Rantron.TaoBao.Spider.Cache;

public interface SpiderCache {

	public void setCacheFile(String path);
	
	public boolean contain(String obj);
	
	public void add(String id);
	
	
}
