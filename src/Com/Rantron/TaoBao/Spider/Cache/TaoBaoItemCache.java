package Com.Rantron.TaoBao.Spider.Cache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TaoBaoItemCache implements SpiderCache{

	private Set<String> itemIdSet = null;
	private BufferedWriter bufferedWriter;
	@Override
	public void setCacheFile(String path) {
		// TODO Auto-generated method stub
		File file = new File(path);
		itemIdSet = new HashSet<String>();
		if(file.exists())
		{
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				String line;
				while(( line = bufferedReader.readLine())!=null)
				{
					itemIdSet.add(line.trim());
				}
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file,true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public synchronized boolean contain(String itemid) {
		// TODO Auto-generated method stub
		return itemIdSet.contains(itemid);
	}

	@Override
	public synchronized void add(String itemid) {
		// TODO Auto-generated method stub
		try {
			itemIdSet.add(itemid);
			bufferedWriter.append(itemid);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try 
			{
				bufferedWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
}
