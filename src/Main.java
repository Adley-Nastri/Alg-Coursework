import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	private static Scanner input;
	
	
	
	public static boolean linearIn(String[] outer, String o) {

		   return Arrays.asList(outer).contains(o);
		}

	public static void main(String[] args) throws FileNotFoundException{
		 input = new Scanner(new File("C:\\\\\\\\Users\\\\\\\\CANastri\\\\\\\\eclipse-workspace-3\\\\\\\\Alg Coursework\\\\\\\\src\\\\\\\\Input.txt"));
		 input.useDelimiter(",");
		 
		 ArrayList<Paper> Papers = new ArrayList<Paper>();
		 String[] auth = input.nextLine().split(",");
		 
		 System.out.println(Arrays.toString(auth));
		 System.out.println("");
		 while(input.hasNext()) {
			 ArrayList<String> p_auth = new ArrayList<String>();
			 ArrayList<String> coauth = new ArrayList<String>();
			 
			 String id = input.next();
			 System.out.println(id);
			 String title = input.next();
			 System.out.println(title);
			 
			 String quart = input.nextLine().replace(",", "");
			 System.out.println(quart);
			 System.out.println("");
			 int quartile = Integer.parseInt(quart);
			 
			 String[] coauth_tmp = input.nextLine().split(",");
			 
			for (int ca = 0; ca < coauth_tmp.length ; ca ++)
			{ 
				if (linearIn(auth, coauth_tmp[ca])) {
					System.out.println(coauth_tmp[ca]+ " is in Authors");
					p_auth.add(coauth_tmp[ca]);
					
				}
				if (!linearIn(auth, coauth_tmp[ca])) {
					System.out.println(coauth_tmp[ca]+ " is not in Authors");
					coauth.add(coauth_tmp[ca]);
					
				}
				
			}
			
			System.out.println("Auth "+p_auth);
			System.out.println("Co-auth "+coauth);
			
			
			if (p_auth.size() == 0 && coauth.size() > 0) {
				System.out.println(coauth + " Left Instiution");
			}
			
			
			 
			 String[] scores = input.nextLine().split(",");
			 System.out.println(Arrays.toString(scores));
			 System.out.println("");
			 
			
			 int[] scorearr = Arrays.stream(scores).mapToInt(Integer::parseInt).toArray();
			
	
			Paper pap = new Paper(id, title, quartile, p_auth, coauth, scorearr);
			
			
			Papers.add(pap);
		
		 }
		 //IF A PAPER DOESNT CONTAIN AT LEAST 1 AUTHOR FROM THE STATED AUTHOR LIST - LEFT INSTITUTION 
		 //BUT NOT SUBJECT TO CONSTRAINTS OF 1 - 5 PAPERS AND DO NOT INCREASE TOTAL HEAD COUNT
		 // ON FTEs
		 //
		 //AUTHOR CAN SUBMIT A MAXIMUM OF 5 PAPERS AS MAIN AUTHOR
		 //BUT CAN BE CO-AUTHOR WHERE OTHER COLLEAGUES ARE MAIN
		 //
		 //IF PAPER IS BY MAIN AUTHOR BUT EXCEEDS THE MAX VALUE OF 5 FOR THAT AUTHOR, THEN SCRAP THE PAPER
		 //ADD THE REST TO BE USED WITHIN GPA CALCULATIONS
		 
		 System.out.println("TOTAL PAPERS : "+Papers.size()+ "\n");
		 for (int i = 0; i  < Papers.size(); i++) {
			 System.out.println("Paper ID : "+Papers.get(i).getID());
			 System.out.println("Paper Title : "+Papers.get(i).getTitle());
			 System.out.println("Quartile : "+Papers.get(i).getQuartile());
			 System.out.println("Authors :" + Papers.get(i).getAuthors());
			 System.out.println("Co-authors :" + Papers.get(i).getCoauthors());
			 System.out.println("Scores : "+Arrays.toString(Papers.get(i).getScores()));
			 System.out.println("");
		 }
		 
	}

}
