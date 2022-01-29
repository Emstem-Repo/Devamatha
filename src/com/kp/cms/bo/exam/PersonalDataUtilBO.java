package com.kp.cms.bo.exam;

public class PersonalDataUtilBO {

	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private Integer secondLanguageId;
	private String secondLanguage;

	private ExamSecondLanguageMasterBO examSecondLanguageMasterBO;

	public PersonalDataUtilBO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		if (lastName != null && lastName.length() > 0)
			return firstName + " " + lastName;
		return firstName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setExamSecondLanguageMasterBO(
			ExamSecondLanguageMasterBO examSecondLanguageMasterBO) {
		this.examSecondLanguageMasterBO = examSecondLanguageMasterBO;
	}

	public ExamSecondLanguageMasterBO getExamSecondLanguageMasterBO() {
		return examSecondLanguageMasterBO;
	}

	public void setSecondLanguageId(Integer secondLanguageId) {
		this.secondLanguageId = secondLanguageId;
	}

	public Integer getSecondLanguageId() {
		return secondLanguageId;
	}

	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public String getSecondLanguage() {
		return secondLanguage;
	}

}
