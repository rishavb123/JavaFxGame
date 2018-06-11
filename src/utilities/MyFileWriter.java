package utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFileWriter {
	
	private static FileWriter fw;
	private static PrintWriter pw;
	
	public static void writelines(String path, String[] writelines)
	{
		try {
			fw = new FileWriter(path);
			pw = new PrintWriter(fw);
			
			for(String s: writelines)
				pw.println(s);
			
			fw.close();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void write(String path, String text)
	{
		try {
			fw = new FileWriter(path);
			pw = new PrintWriter(fw);
			
			pw.print(text);
			
			fw.close();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
