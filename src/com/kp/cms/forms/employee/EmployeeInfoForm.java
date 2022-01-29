package com.kp.cms.forms.employee;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpJobAllowanceTO;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.admin.EmployeeInformactionTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.LanguageTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.employee.EmpEducationMasterTO;
import com.kp.cms.to.employee.EmployeeKeyValueTO;
import com.kp.cms.to.employee.EmployeeStreamTO;

public class EmployeeInfoForm extends BaseActionForm {
	private Pattern pat;
	private Matcher mat;
	private static final String epat ="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private boolean adminUser;
	private boolean employeeFound;
	private List<NationalityTO> nationalities;
	private List<CountryTO> countries;
	private List<LanguageTO> languages;
	private EmployeeTO employeeDetail;
	private List<StateTO> editStates;
	private List<StateTO> editCurrentStates;
	private List<EmpEducationMasterTO> educationMasters;
	private List<EmpAllowanceTO> allowanceTos;
	private List<EmpLeaveTypeTO> leaveTypeTOs;
	private List<EmpLeaveTO> leaveTOs;
	private List<EmpAcheivementTO> achievementTOs;
	private List<EmpAcheivementTO> newAchievementTOs;
	
	
	private List<EmployeeTO> reportingTos;
	private List<EmployeeStreamTO> streamTO;
	
	List<EmployeeKeyValueTO> listDepartment;
	List<EmployeeKeyValueTO> listDesignation;
	List<EmployeeKeyValueTO> listWorkLocation;
	List<EmployeeKeyValueTO> listEmployeeType;
	
	List<EmployeeKeyValueTO> listRoles;
	private String duplicateIn;
	private String mode;
	private String otherState;


	public List<EmployeeKeyValueTO> getListRoles() {
		return listRoles;
	}


	public void setListRoles(List<EmployeeKeyValueTO> listRoles) {
		this.listRoles = listRoles;
	}


	private int allowanceSize;
	
	private List<EmpJobAllowanceTO> allowances;
	private List<EmployeeInformactionTO> listOfEmployees;
	public EmployeeInfoForm(){
		pat = Pattern.compile(epat);
		this.employeeDetail= new EmployeeTO();
	}
	
	
	public boolean isAdminUser() {
		return adminUser;
	}

	public void setAdminUser(boolean adminUser) {
		this.adminUser = adminUser;
	}

	public List<NationalityTO> getNationalities() {
		return nationalities;
	}

	public void setNationalities(List<NationalityTO> nationalities) {
		this.nationalities = nationalities;
	}

	public List<CountryTO> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryTO> countries) {
		this.countries = countries;
	}

	public List<LanguageTO> getLanguages() {
		return languages;
	}

	public void setLanguages(List<LanguageTO> languages) {
		this.languages = languages;
	}

	public EmployeeTO getEmployeeDetail() {
		return employeeDetail;
	}

	public void setEmployeeDetail(EmployeeTO employeeDetail) {
		this.employeeDetail = employeeDetail;
	}


	public boolean isEmployeeFound() {
		return employeeFound;
	}


	public void setEmployeeFound(boolean employeeFound) {
		this.employeeFound = employeeFound;
	}
	public List<StateTO> getEditStates() {
		return editStates;
	}


	public void setEditStates(List<StateTO> editStates) {
		this.editStates = editStates;
	}


	public List<EmpEducationMasterTO> getEducationMasters() {
		return educationMasters;
	}


	public void setEducationMasters(List<EmpEducationMasterTO> educationMasters) {
		this.educationMasters = educationMasters;
	}


	public List<EmpAllowanceTO> getAllowanceTos() {
		return allowanceTos;
	}


	public void setAllowanceTos(List<EmpAllowanceTO> allowanceTos) {
		this.allowanceTos = allowanceTos;
	}

	public List<EmpJobAllowanceTO> getAllowances() {
		return allowances;
	}
	public void setAllowances(List<EmpJobAllowanceTO> allowances) {
		this.allowances = allowances;
	}

	public int getAllowanceSize() {
		return allowanceSize;
	}


	public void setAllowanceSize(int allowanceSize) {
		this.allowanceSize = allowanceSize;
	}


	public List<EmpLeaveTypeTO> getLeaveTypeTOs() {
		return leaveTypeTOs;
	}


	public void setLeaveTypeTOs(List<EmpLeaveTypeTO> leaveTypeTOs) {
		this.leaveTypeTOs = leaveTypeTOs;
	}


	public List<EmpLeaveTO> getLeaveTOs() {
		return leaveTOs;
	}


	public void setLeaveTOs(List<EmpLeaveTO> leaveTOs) {
		this.leaveTOs = leaveTOs;
	}


	public List<EmpAcheivementTO> getAchievementTOs() {
		return achievementTOs;
	}


	public void setAchievementTOs(List<EmpAcheivementTO> achievementTOs) {
		this.achievementTOs = achievementTOs;
	}


	public List<EmpAcheivementTO> getNewAchievementTOs() {
		return newAchievementTOs;
	}


	public void setNewAchievementTOs(List<EmpAcheivementTO> newAchievementTOs) {
		this.newAchievementTOs = newAchievementTOs;
	}


	public List<EmployeeTO> getReportingTos() {
		return reportingTos;
	}


	public void setReportingTos(List<EmployeeTO> reportingTos) {
		this.reportingTos = reportingTos;
	}


	/* (non-Javadoc)
	 * validation call
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}


	public void setListOfEmployees(List<EmployeeInformactionTO> listOfEmployees) {
		this.listOfEmployees = listOfEmployees;
	}


	public List<EmployeeInformactionTO> getListOfEmployees() {
		return listOfEmployees;
	}


	public void setStreamTO(List<EmployeeStreamTO> streamTO) {
		this.streamTO = streamTO;
	}


	public List<EmployeeStreamTO> getStreamTO() {
		return streamTO;
	}


	public List<EmployeeKeyValueTO> getListDepartment() {
		return listDepartment;
	}


	public void setListDepartment(List<EmployeeKeyValueTO> listDepartment) {
		this.listDepartment = listDepartment;
	}


	public List<EmployeeKeyValueTO> getListDesignation() {
		return listDesignation;
	}


	public void setListDesignation(List<EmployeeKeyValueTO> listDesignation) {
		this.listDesignation = listDesignation;
	}


	public List<EmployeeKeyValueTO> getListWorkLocation() {
		return listWorkLocation;
	}


	public void setListWorkLocation(List<EmployeeKeyValueTO> listWorkLocation) {
		this.listWorkLocation = listWorkLocation;
	}
	public List<EmployeeKeyValueTO> getListEmployeeType() {
		return listEmployeeType;
	}


	public void setListEmployeeType(List<EmployeeKeyValueTO> listEmployeeType) {
		this.listEmployeeType = listEmployeeType;
	}


	public String getDuplicateIn() {
		return duplicateIn;
	}


	public void setDuplicateIn(String duplicateIn) {
		this.duplicateIn = duplicateIn;
	}

	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public String getOtherState() {
		return otherState;
	}


	public void setOtherState(String otherState) {
		this.otherState = otherState;
	}


	public List<StateTO> getEditCurrentStates() {
		return editCurrentStates;
	}


	public void setEditCurrentStates(List<StateTO> editCurrentStates) {
		this.editCurrentStates = editCurrentStates;
	}


	public void clearFields(){
		this.employeeDetail.setEmployeeType(null);
		this.employeeDetail.setEmergencyHomeTelephone(null);
		this.employeeDetail.setEmergencyHomeTelephone1(null);
		this.employeeDetail.setEmergencyHomeTelephone2(null);
		this.employeeDetail.setEmergencyMobile(null);
		this.employeeDetail.setEmergencyMobile1(null);
		this.employeeDetail.setEmergencyMobile2(null);
		this.employeeDetail.setEmergencyWorkTelephone(null);
		this.employeeDetail.setEmergencyWorkTelephone1(null);
		this.employeeDetail.setEmergencyWorkTelephone2(null);
		this.mode=null;
		this.employeeDetail.setCommunicationAddressStateOthers(null);
		this.employeeDetail.setPermanentAddressStateOthers(null);
	}
	
	  public boolean validateEmail(final String hex){

		  mat = pat.matcher(hex);
		  return mat.matches();

	  }

	
}
