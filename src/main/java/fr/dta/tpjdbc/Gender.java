package fr.dta.tpjdbc;

public enum Gender {
	M("MALE"), F("FEMALE");

	private String genderDesc;

	Gender(String genderDesc) {
		this.genderDesc = genderDesc;
	}

	public String getGender() {
		return genderDesc;
	}
}
