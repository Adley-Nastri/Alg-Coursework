import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readInp("C:\\\\Users\\\\CANastri\\\\eclipse-workspace-3\\\\Alg Coursework\\\\src\\\\Input.txt");
	}
	
	private static void readInp (String input) {
	try(BufferedReader inp = new BufferedReader(new FileReader(input)))
		{
		int lines = 0;
		String str = null;
		
		ArrayList<String> auth = new ArrayList<String>();
		auth.add(inp.readLine());
		System.out.println(auth);
		while ((str = inp.readLine()) != null) {
			
			lines++;
			String[] tokens = str.split(",\n");
			
			for (String str_ : tokens) {
				if (str.contains("Paper"))
		        {
		        	System.out.println("\nNew paper");
		        }
		        System.out.println(str_ + " (Line : "+lines+")");
		        if (lines == 1)
		        {
		        	
		        	
		        }
		        
		        
		      }
	        
		}
		}
	catch (Exception ex) 
	{
		
	}
	
	
	}
	
	

}
