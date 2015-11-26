package Com.Rantron.TaoBao.Spider.Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

	
	public static synchronized void append2File(String filePath,String content)
	{
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
