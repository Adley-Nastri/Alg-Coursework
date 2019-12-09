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
		 input = new Scanner(new File("C:\\Users\\CANastri\\Desktop\\Github Alg\\Alg-Coursework\\src\\Input.txt"));
		 input.useDelimiter(","); //Each new object is separated by a comma
		 
		 ArrayList<Paper> Papers = new ArrayList<Paper>(); //ArrayList of Paper Objects
		 ArrayList<Author> auth_al = new ArrayList<Author>();
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
				System.out.println(coauth + " Left Institution"); 
				
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
		 
		 
		 PapSort(Papers,Papers.size());
		 System.out.println("GPA of "+Papers.size()+" Papers : "+GPA(Papers, Papers.size()));
		 System.out.println("");
		 
		 
		 for (int auth_int = 0; auth_int < auth.length; auth_int++ )
		 {
			 ArrayList<Paper> auth_pap = new ArrayList<Paper>();
			 System.out.println("~~~~~~~~~~~~~~~~~~~~");
			 System.out.println(auth[auth_int]);
			 System.out.println("~~~~~~~~~~~~~~~~~~~~");
			 for(Paper pap_ : Papers){
			        if(pap_.getAuthors() != null && pap_.getAuthors().contains(auth[auth_int])){
			        	
			        	//System.out.println("\nPaper ID = "+pap_.getID()+" | REF = "+pap_.getREF()+ " | AVG = "+pap_.getAvg());
			        	
			        	auth_pap.add(pap_);

			        	
			        	
			        }
			        
			       
			           
			    }
			 Author auth_obj = new Author(auth[auth_int], auth_pap);
	        	
	        auth_al.add(auth_obj);
//			System.out.println("\nSIZE "+auth_al.get(auth_int).auth_papers.size());
//			
//			
//			System.out.println("GPA = "+GPA(auth_al.get(auth_int).auth_papers,auth_al.get(auth_int).auth_papers.size())+"\n");
	        int xN = 5;
	        PapSort(auth_al.get(auth_int).auth_papers,xN);
	        //System.out.println("GPA = "+GPA(auth_al.get(auth_int).auth_papers,xN));
			 
		 }
		 
		 int M = auth_al.size();
		 int N = (int) Math.ceil(M * 2.5);
		 System.out.println("\nN = "+N);
		 
		 int papsort_ = PapSort(Papers,N);
		 Double gpa_ = GPA(Papers,papsort_);
		 System.out.println("\nGPA of top "+N+" papers = "+gpa_);
		 
		
	}

	public static int PapSort(ArrayList<Paper> PapsToSort, int N){
		Collections.sort(PapsToSort);
		if (N <= PapsToSort.size()) {
		 for (int k = 0 ; k < N; k++)
		 {
			 if (k+1 < N) {
				 
			 
				 if (PapsToSort.get(k).getAvg() == PapsToSort.get(k+1).getAvg()) {
					 
					 
					 if (PapsToSort.get(k).getQuartile() > PapsToSort.get(k+1).getQuartile())
					 {
						Collections.swap(PapsToSort, k, k+1);
					 }
					 
				 
				 }
			 }
			 
			 DecimalFormat df = new DecimalFormat();
			 df.setMaximumFractionDigits(2);
			 System.out.println("Title = " + PapsToSort.get(k).getTitle() + " | AVG = " +df.format(PapsToSort.get(k).getAvg())+ " | REF = " +PapsToSort.get(k).getREF() + " | Quartile = " +PapsToSort.get(k).getQuartile());
		 }
		}
		if (N > PapsToSort.size())
		{
			PapSort(PapsToSort,PapsToSort.size());
			N = PapsToSort.size();
		}
		return N;
		  
	 }
	
	
	
	public static double GPA(ArrayList<Paper> Papers_, int N)
	{
		double gpa = 0;
		double sum = 0;
		if (N <= Papers_.size()) {
		
			for(int j = 0; j < N; j++)
			{
				sum += Papers_.get(j).getREF();
			}
			gpa = sum/N;
		}
		if (N > Papers_.size()) {
			
			
			for(int j = 0; j < Papers_.size(); j++)
			{
				sum += Papers_.get(j).getREF();
			}
			//System.out.println();
			gpa = sum/Papers_.size();
		}
		
		return gpa;
		
	}
	
	
}
