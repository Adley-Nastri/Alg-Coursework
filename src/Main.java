import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;
import java.util.List;

public class Main {

	private static Scanner input;
	
	
	
	public static boolean linearIn(String[] outer, String o) {

		   return Arrays.asList(outer).contains(o); //Return whether array contains specified string.
		}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException{
		 input = new Scanner(new File("\\\\dl-stud1\\users\\D35\\u1803005\\Desktop\\Alg-Coursework-master\\Alg-Coursework-master\\src\\Input.txt"));
		 input.useDelimiter(","); //Each new object is separated by a comma
		 
		 ArrayList<Paper> Papers = new ArrayList<Paper>(); //ArrayList of Paper Objects
		 String[] auth = input.nextLine().split(","); //Main authors defined in the first line of the file (AK, PF, AM), split by a comma
		 
		 System.out.println(Arrays.toString(auth));
		 System.out.println("");
		 while(input.hasNext()) {
			 ArrayList<String> p_auth = new ArrayList<String>(); //Authors to be applied to paper object. 
			 ArrayList<String> coauth = new ArrayList<String>(); //Co-authors to be applied
			 
			 String id = input.next(); //
			 System.out.println(id);
			 String title = input.next();
			 System.out.println(title);
			 
			 String quart = input.nextLine().replace(",", "");
			 System.out.println(quart);
			 System.out.println("");
			 int quartile = Integer.parseInt(quart);
			 
			 String[] coauth_tmp = input.nextLine().split(","); // Read next line and place into a temp array
			 
			for (int ca = 0; ca < coauth_tmp.length ; ca ++)
			{ 
				if (linearIn(auth, coauth_tmp[ca])) {  //If the current co-author is in the main Author array, then add Author to Paper object
					System.out.println(coauth_tmp[ca]+ " is in Authors");
					p_auth.add(coauth_tmp[ca]);
					
				}
				if (!linearIn(auth, coauth_tmp[ca])) {//If the current co-author is NOT in the main Author array, then add Co-author to Paper object
					System.out.println(coauth_tmp[ca]+ " is not in Authors");
					coauth.add(coauth_tmp[ca]);
					
				}
				
			}
			
			System.out.println("Auth "+p_auth);
			System.out.println("Co-auth "+coauth);
			
			
			if (p_auth.size() == 0 && coauth.size() > 0) { //If the size of the Paper's author arraylist is 0 and the paper only has co-authors then
			    //They must have left the institution 
				System.out.println(coauth + " Left Instiution"); 
				
				//DO NOT CONTRIBUTE TO FTE HEAD COUNT
			}
			
			
			 
			 String[] scores = input.nextLine().split(",");
			 System.out.println(Arrays.toString(scores));
			 System.out.println("");
			 
			
			 int[] scorearr = Arrays.stream(scores).mapToInt(Integer::parseInt).toArray(); //Convert String of scores into int format
			
	
			Paper pap = new Paper(id, title, quartile, p_auth, coauth, scorearr); //New Paper object
			
			
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
			 System.out.printf("Avg %.2f ",Papers.get(i).getAvg());
			 System.out.println("");
			 System.out.println("REF : "+Papers.get(i).getREF());
			 System.out.println("");
			 System.out.println("");
		 }
		 
		 
		 Collections.sort(Papers);
		 for (int k = 0 ; k < Papers.size(); k++)
		 {
			 if (k+1 < Papers.size()) {
				 
			 
				 if (Papers.get(k).getAvg() == Papers.get(k+1).getAvg()) {
					 
					 //System.out.println("MATCH " +Papers.get(k).getTitle() + " "+ Papers.get(k+1).getTitle());
					 
					 if (Papers.get(k).getQuartile() > Papers.get(k+1).getQuartile())
					 {
						Collections.swap(Papers, k, k+1);
					 }
					 
				 
				 }
			 }
			 
			 
			 System.out.println("Title = " + Papers.get(k).getTitle() + " | AVG = " +Papers.get(k).getAvg()+ " | REF = " +Papers.get(k).getREF() + " | Quartile = " +Papers.get(k).getQuartile());
		 }
		 System.out.println("GPA of "+Papers.size()+" Papers : "+GPA(Papers));
	}
	
	
	public static double GPA(ArrayList<Paper> Papers_)
	{
		double gpa = 0;
		double sum = 0;
		for(int j = 0; j < Papers_.size(); j++)
		{
			sum += Papers_.get(j).getREF();
		}
		gpa = sum/Papers_.size();
		return gpa;
		
	}
	
	
}
