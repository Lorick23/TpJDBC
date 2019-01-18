package fr.dta.tpjdbc;

public enum Gender {
	M("MALE"), F("FEMALE");

	private String genderDesc;

	Gender(String genderDesc) {
		this.setGender(genderDesc);
	}

	public String getGender() {
		return genderDesc;
	}

	public void setGender(String genderDesc) {
		this.genderDesc = genderDesc;
	}
}
