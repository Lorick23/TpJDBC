package fr.dta.TpJDBC;

public enum Gender {
	M("MALE"), F("FEMALE");

	private String gender;

	// Constructeur
	Gender(String gender) {
		this.setGender(gender);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
