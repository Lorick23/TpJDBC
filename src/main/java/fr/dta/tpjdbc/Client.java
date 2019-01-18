package fr.dta.tpjdbc;


public class Client {
	
	private Integer id;
	private String lastname;
	private String firstname;
	private Gender gender;
	private Integer favBook;


	public Client(String lastname, String firstname, Gender gender) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.gender = gender;
	}

	public Client(String lastname, String firstname, Gender gender, Integer favBook) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.gender = gender;
		this.favBook = favBook;
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

	public Integer getFavBook() {
		return favBook;
	}

	public void setFavBook(Integer favBook) {
		this.favBook = favBook;
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
				+ ", fav_book=" + favBook + "]";
	}
	
}
