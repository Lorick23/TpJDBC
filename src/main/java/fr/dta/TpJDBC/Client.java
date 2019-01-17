package fr.dta.TpJDBC;


public class Client {
	
	private Integer id;
	private String lastname;
	private String firstname;
	private Gender gender;
	private Integer fav_book;


	public Client(String lastname, String firstname, Gender gender) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.gender = gender;
		//Client.clients.add(this);
	}

	public Client(String lastname, String firstname, Gender gender, Integer fav_book) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.gender = gender;
		this.fav_book = fav_book;
		//Client.clients.add(this);
	}


	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getFav_book() {
		return fav_book;
	}

	public void setFav_book(Integer fav_book) {
		this.fav_book = fav_book;
	}

	public int getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", gender=" + gender
				+ ", fav_book=" + fav_book + "]";
	}
	
}
