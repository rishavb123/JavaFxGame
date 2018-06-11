package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MyFileReader {
		
	public static String read(String path)
	{
		File fi = new File(path);
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(fi));
			String text = "";
			String s;
			while((s = reader.readLine())!=null)
			{
				text+=s+"\n";
			}
			reader.close();
			return text.substring(0, text.length() - 1);

		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
}
