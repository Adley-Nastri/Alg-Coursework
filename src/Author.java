import java.util.ArrayList;

public class Author {
	
	String name;
	ArrayList<Paper> auth_papers;
	
	public Author(String name_, ArrayList<Paper> auth_papers_) {
		super();
		this.name = name_;
		this.auth_papers = auth_papers_;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Paper> getAuth_papers() {
		return auth_papers;
	}

	public void setAuth_papers(ArrayList<Paper> auth_papers) {
		this.auth_papers = auth_papers;
	}
	

}
