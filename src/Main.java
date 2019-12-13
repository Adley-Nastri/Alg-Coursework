import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;

public class Main {

	private static Scanner input;
	
	
	
	public static boolean linearIn(String[] outer, String o) {

		   return Arrays.asList(outer).contains(o); //Return whether array contains specified string.
		}

	public static void main(String[] args) throws FileNotFoundException{
		 input = new Scanner(new File("Input.txt"));
		 input.useDelimiter(","); //Each new object is separated by a comma
		 
		 ArrayList<Paper> Papers = new ArrayList<Paper>(); //ArrayList of Paper Objects
		 ArrayList<Author> auth_al = new ArrayList<Author>(); //ArrayList of authors
		 String[] auth = input.nextLine().replace(" ", "").split(","); //Main authors defined in the first line of the file (AK, PF, AM), split by a comma
		 
		 System.out.println(Arrays.toString(auth));
		 System.out.println("");
		 while(input.hasNext()) {
			 ArrayList<String> p_auth = new ArrayList<String>(); //Authors to be applied to paper object. 
			 ArrayList<String> coauth = new ArrayList<String>(); //Co-authors to be applied
			 
			 String id = input.next(); 
			 System.out.println(id);
			 String title = input.next();
			 System.out.println(title);
			 
			 String quart = input.nextLine().replace(",", "").replace(" ", "");
			 System.out.println(quart);
			 int quartile = Integer.parseInt(quart);
			 
			 String[] coauth_tmp = input.nextLine().replace(" ", "").split(","); // Read next line and place into a temp array
			 
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
			
			//This indentifies whether the Author is a member of the institution. CURRENTLY UN-USED! Don't worry about this for now
			 
			if (p_auth.size() == 0 && coauth.size() > 0) { //If the size of the Paper's author arraylist is 0 and the paper only has co-authors then
			    //They must have left the institution 
				System.out.println(coauth + " Left Institution"); 
				
				//DO NOT CONTRIBUTE TO FTE HEAD COUNT
			}
			
			
			 
			 String[] scores = input.nextLine().replace(" ", "").split(",");
			 System.out.println(Arrays.toString(scores));
			 System.out.println("");
			 
			
			 int[] scorearr = Arrays.stream(scores).mapToInt(Integer::parseInt).toArray(); //Convert String of scores into int format
			
	
			Paper pap = new Paper(id, title, quartile, p_auth, coauth, scorearr); //New Paper object
			
			
			Papers.add(pap); //Add each paper found to the Papers ArrayList
		
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
		 
		
		 
		 
		 //This block is not required for the requirements, just a proof of concept.
		 //System.out.println("TOTAL PAPERS : "+Papers.size()+ "\n");
		 //PapSort(Papers,Papers.size()); 
		 //System.out.println("GPA of "+Papers.size()+" Papers : "+GPA(Papers, Papers.size()));
		 //System.out.println("");
		
		
		 
		 
		 for (int auth_int = 0; auth_int < auth.length; auth_int++ ) //auth_int is used instead of the traditional 'i' varaible
			 //for the size of the list of authors (first determined from the VERY FIRST LINE of the input file
		 {
			 ArrayList<Paper> auth_pap = new ArrayList<Paper>(); //Create a new ArrayList for each author
			 //to contain the papers associated with each one

			 System.out.println("~~~~~~~~~~~~~~~~~~~~");
			 System.out.println(auth[auth_int]); // Print out the author name using auth_int as the index of the auth array
			 System.out.println("~~~~~~~~~~~~~~~~~~~~");
			 for(Paper pap_ : Papers){ //for every paper object in the Papers ArrayList
			        if(pap_.getAuthors() != null && pap_.getAuthors().contains(auth[auth_int])){ //if current paper's authors are not equal to null
					//and contains the current indexed author
			        	
			        	//System.out.println("\nPaper ID = "+pap_.getID()+" | REF = "+pap_.getREF()+ " | AVG = "+pap_.getAvg());
			        	
			        	auth_pap.add(pap_); //add the paper to the auth_pap ArrayList	
			        }
		       
			    }
			 Author auth_obj = new Author(auth[auth_int], auth_pap); // Create a new Author object with the current indexed name and the papers 
			 //assocaited witht the curent author
	        	
	        auth_al.add(auth_obj); //Add the current Author object to the auth_al ArrayList as defined at the top of the program
			 
//			System.out.println("\nSIZE "+auth_al.get(auth_int).auth_papers.size());
//			
//			
//			System.out.println("GPA = "+GPA(auth_al.get(auth_int).auth_papers,auth_al.get(auth_int).auth_papers.size())+"\n");
	        
			 
	        int xN = 5; //Contraint maximum of 5 papers per author
	        PapSort(auth_al.get(auth_int).auth_papers,xN); //Run PapSort function with the auth_al index of auth_int (current position in for loop), then how many
			 //papers to process. Results in showing the top 5 papers for the current author
			 
			 
		 }
		 
		 int M = auth_al.size();
		 int N = (int) Math.ceil(M * 2.5); // 2.5 Times the amount of authors,  converted to an int, then rounded UP
		 System.out.println("\nN = "+N);//Print new line (\n) then print the value of N
		 
		 int papsort_ = PapSort(Papers,N); //As shown below, PapSort returns an int value, so the result of the function can be stored as a variable
		 Double gpa_ = GPA(Papers,papsort_); //Same with the GPA, but returns a Double. Feed the value of papsort_ into the GPA function
		 System.out.println("\nGPA of top "+N+" papers = "+gpa_);
		 
		
	}

	public static int PapSort(ArrayList<Paper> PapsToSort, int N){
		Collections.sort(PapsToSort); //Sort the Papers parsed in
		if (N <= PapsToSort.size()) { //As long as N is less than or equal to the size of the arraylist. Used to prevent exceptions
		 for (int k = 0 ; k < N; k++) //k is used instead of 'i'
		 {
			 if (k+1 < N) { //If the next position in the ArrayList is less than N
				 
			 
				 if (PapsToSort.get(k).getAvg() == PapsToSort.get(k+1).getAvg()) { //If the current paper's average is equal to the next paper's average
					 //then look at both quartiles  
					 if (PapsToSort.get(k).getQuartile() > PapsToSort.get(k+1).getQuartile())
						 //if the current paper's quartile is mathematically larger than the next paper's quartile
						 //then swap their positions in the ArrayList
						 //As we are showing papers whereby the lower the quartile number, the worse the paper, thus the paper is shown
						 //lower down
					 {
						Collections.swap(PapsToSort, k, k+1); //Put the next position's paper in the place of the current paper
					 }
					 
				 
				 }
			 }
			 
			 DecimalFormat df = new DecimalFormat();
			 df.setMaximumFractionDigits(2); //Used for decimal places
			 System.out.println("Title = " + PapsToSort.get(k).getTitle() + " | AVG = " +df.format(PapsToSort.get(k).getAvg())+ " | REF = " +PapsToSort.get(k).getREF() + " | Quartile = " +PapsToSort.get(k).getQuartile());
		 }
		}
		if (N > PapsToSort.size()) //Exception avoidance
		{
			PapSort(PapsToSort,PapsToSort.size()); //Recursion of the function
			N = PapsToSort.size();
		}
		return N; //N value returned as an int
		  
		
	 }
	
	
	
	public static double GPA(ArrayList<Paper> Papers_, int N)
	{
		double gpa = 0;
		double sum = 0;
		if (N <= Papers_.size()) { //As long as N is smaller or equal than the size of the ArrayList of Papers
		
			for(int j = 0; j < N; j++)
			{
				sum += Papers_.get(j).getREF(); //Add up the REF score of each paper 
			}
			gpa = sum/N; //Mean Average. This is we can choose an N amount of top papers 
		}
		if (N > Papers_.size()) { //If N is larger than the size of the ArrayList
			
			for(int j = 0; j < Papers_.size(); j++) 
			{
				sum += Papers_.get(j).getREF();
			}
			gpa = sum/Papers_.size(); //Divide by the size of the ArrayList
		}
		
		return gpa;//Return Double
		
	}
	
	
}
