import java.util.ArrayList;
import java.util.stream.IntStream;

public class Paper {
	
	String ID;
	String title;
	int quartile;
	ArrayList<String> authors;
	ArrayList<String> coauthors;
	int[] scores;
	
	
	
	public Paper(String iD_, String title_, int quart_, ArrayList<String> auth_, ArrayList<String> coauth_, int[] scores_) {
		super();
		ID = iD_;
		this.title = title_;
		this.quartile = quart_;
		this.authors = auth_;
		this.coauthors = coauth_;
		this.scores = scores_;
	}



	public String getID() {
		return ID;
	}



	public void setID(String iD) {
		ID = iD;
	}
	


	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public ArrayList<String> getAuthors() {
		return authors;
	}



	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}



	public ArrayList<String> getCoauthors() {
		return coauthors;
	}



	public void setCoauthors(ArrayList<String> coauthors) {
		this.coauthors = coauthors;
	}



	public int[] getScores() {
		return scores;
	}

	public double getAvg() {
		
		double sum = IntStream.of(this.scores).sum();
		return sum/this.scores.length;
	}

	public int getREF()
	{
		int ref = 0;
		if (this.getAvg() >= 0 && this.getAvg() <1) {
			ref = 0;
		}
		if (this.getAvg() >= 1 && this.getAvg() <4) {
			ref = 1;
		}
		if (this.getAvg() >= 4 && this.getAvg() <7) {
			ref = 2;
		}
		if (this.getAvg() >= 7 && this.getAvg() <10) {
			ref =  3;
		}
		if (this.getAvg() >= 10 && this.getAvg() <=12) {
			ref = 4;
		}
		return ref;
	}

	public void setScores(int[] scores) {
		this.scores = scores;
	}



	public int getQuartile() {
		return quartile;
	}



	public void setQuartile(int quartile) {
		this.quartile = quartile;
	}

	
	
	

}
