package com.kp.cms.helpers.employee;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeReportForm;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.employee.EmployeeReportTO;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeReportHelper {
	private static final Log log = LogFactory.getLog(EmployeeReportHelper.class);
private static volatile EmployeeReportHelper employeeReportHelper = null;
private static final String DISPLAY = "display";
public static EmployeeReportHelper getInstance(){
	if(employeeReportHelper == null){
		employeeReportHelper = new EmployeeReportHelper();
		return employeeReportHelper;
	}
	return employeeReportHelper;
}

/**
 * @param employeeReportForm
 * @param streamId
 * @param departmentId
 * @param designationId
 * @param workLocationId
 * @return
 */
public StringBuffer getSelectionSearchCriteria(
		EmployeeReportForm employeeReportForm, int streamId, int departmentId,
		int designationId, int workLocationId) {
	StringBuffer stringBuffer = new StringBuffer("from Employee e "
			+" where e.isActive = 1 ");
	if(streamId > 0){
		stringBuffer = stringBuffer .append(" and e.streamId.id='" 
				+ streamId+"'");
	}
	if(departmentId > 0){
		stringBuffer = stringBuffer .append(" and e.department.id='"
				+ departmentId+"'");
	}
	if(designationId > 0){
		stringBuffer = stringBuffer .append(" and e.designation.id='"
				+ designationId+"'");
	}
	if(workLocationId > 0){
		stringBuffer = stringBuffer.append(" and e.workLocationId.id='"
				+ workLocationId+"'");
	}
	if(employeeReportForm.getActive() !=null)
	{
		if (employeeReportForm.getActive().equals("1")) {
			
			stringBuffer = stringBuffer.append(" and e.active= 1 ");
		}
		else if (employeeReportForm.getActive().equals("0")) {
			
			stringBuffer = stringBuffer.append(" and e.active= 0 ");
		}
	}
	if(employeeReportForm.getTeachingStaff()!=null)
	{
		if (employeeReportForm.getTeachingStaff().equals("1")) {
			
			stringBuffer = stringBuffer.append(" and e.teachingStaff= 1 ");
		}
		else if (employeeReportForm.getTeachingStaff().equals("0")) {
			
			stringBuffer = stringBuffer.append(" and e.teachingStaff= 0 ");
		}
		else if (employeeReportForm.getTeachingStaff().equals("2")) {
		//	query = query.append(" and e.teachingStaff is not null");
			stringBuffer = stringBuffer.append(" and (e.teachingStaff= 0 OR e.teachingStaff= 1)");
		}
	}
	return stringBuffer;
}

/**
 * @param employeeTOs
 * @return
 */
public List<EmployeeTO> convertEmployeeBoTOTo(List<Employee> employee) {
	List<EmployeeTO> employeeTOs = new ArrayList<EmployeeTO>();
	if(employee!=null && !employee.isEmpty()){
		Iterator<Employee> iterator=employee.iterator();
		while (iterator.hasNext()) {
			Employee emp = (Employee) iterator.next();
			EmployeeTO employeeTO = new EmployeeTO();
			if(emp.getFingerPrintId()!=null){
				if(!emp.getFingerPrintId().isEmpty()){
					employeeTO.setFingerPrintId(emp.getFingerPrintId());
				}
			}
			if(emp.getFirstName()!=null){
				if( !emp.getFirstName().isEmpty()){
					employeeTO.setFirstName(emp.getFirstName());
				}
			}
			if(emp.getDob()!=null){
				employeeTO.setDob(emp.getDob().toString());
			}
			if(emp.getGender()!=null){
				if(!emp.getGender().isEmpty()){
					employeeTO.setGender(emp.getGender());
				}
				
			}
			if(emp.getDepartment()!=null){
				if(emp.getDepartment().getId()!=0){
					employeeTO.setDepartmentId(String.valueOf(emp.getDepartment().getId()));
				}
				if(emp.getDepartment().getName()!=null){
					if(!emp.getDepartment().getName().isEmpty()){
						employeeTO.setDepartmentName(emp.getDepartment().getName());
					}
				}
			}
			if(emp.getWorkLocationId()!=null){
				if(emp.getWorkLocationId().getId()!=0){
					employeeTO.setWorkLocationId(String.valueOf(emp.getWorkLocationId().getId()));
				}
				if(emp.getWorkLocationId().getName()!=null){
					if(!emp.getWorkLocationId().getName().isEmpty()){
						employeeTO.setWorkLocationName(emp.getWorkLocationId().getName());
					}
				}
			}
			employeeTOs.add(employeeTO);
		}
	}
	return employeeTOs;
}

/**
 * @param employeelist
 * @return
 */
	public List<EmployeeTO> convertBOToExcel(List<Employee> employeelist) throws Exception{
		List<EmployeeTO> list = new ArrayList<EmployeeTO>();
		if (employeelist != null) {
			Iterator<Employee> iterator = employeelist.iterator();
			while (iterator.hasNext()) {
				Employee employee = (Employee) iterator.next();
				EmployeeTO employeeTO = new EmployeeTO();
				if (employee.getFingerPrintId() != null) {
					if (!employee.getFingerPrintId().isEmpty()) {
						employeeTO
								.setFingerPrintId(employee.getFingerPrintId());
					}
				}

				if (employee.getFirstName() != null) {
					if (!employee.getFirstName().isEmpty()) {
						employeeTO.setFirstName(employee.getFirstName());
					}
				}
				if (employee.getBankAccNo() != null) {
					if (!employee.getBankAccNo().isEmpty()) {
						employeeTO.setBankAccNo(employee.getBankAccNo());
					}
				}
				if (employee.getBloodGroup() != null) {
					if (!employee.getBloodGroup().isEmpty()) {
						employeeTO.setBloodGroup(employee.getBloodGroup());
					}
				}
				if (employee.getCode() != null) {
					if (!employee.getCode().isEmpty()) {
						employeeTO.setCode(employee.getCode());
					}
				}
				if (employee.getUid() != null) {
					if (!employee.getUid().isEmpty()) {
						employeeTO.setUid(employee.getUid());
					}
				}
				if (employee.getMaritalStatus() != null) {
					if (!employee.getMaritalStatus().isEmpty()) {
						employeeTO
								.setMaritalStatus(employee.getMaritalStatus());
					}
				}
				if (employee.getNationality() != null) {
					if (employee.getNationality().getName() != null) {
						if (!employee.getNationality().getName().isEmpty()) {
							employeeTO.setNationalityName(employee
									.getNationality().getName());
						}
					}
				}
				if (employee.getDepartment() != null) {
					if (employee.getDepartment().getName() != null) {
						if (!employee.getDepartment().getName().isEmpty()) {
							employeeTO.setDepartmentName(employee
									.getDepartment().getName());
						}
					}
				}
				if (employee.getDesignation() != null) {
					if (employee.getDesignation().getName() != null) {
						if (!employee.getDesignation().getName().isEmpty()) {
							employeeTO.setDesignationName(employee
									.getDesignation().getName());
						}
					}
				}
				if (employee.getGender() != null) {
					if (!employee.getGender().isEmpty()) {
						employeeTO.setGender(employee.getGender());
					}
				}
				if (employee.getDob() != null) {
					String dob = employee.getDob().toString();
					employeeTO.setDob(dob);
					String age = AgeCalculation(employee.getDob());
					employeeTO.setAge(age);
				}
				if (employee.getReligionId() != null) {
					if (employee.getReligionId().getName() != null) {
						if (!employee.getReligionId().getName().isEmpty()) {
							employeeTO.setReligion(employee.getReligionId()
									.getName());
						}
					}
				}
				if (employee.getPanNo() != null) {
					if (!employee.getPanNo().isEmpty()) {
						employeeTO.setPanNo(employee.getPanNo());
					}
				}
				if (employee.getWorkEmail() != null) {
					if (!employee.getWorkEmail().isEmpty()) {
						employeeTO.setWorkEmail(employee.getWorkEmail());
					}
				}
				if (employee.getEmail() != null) {
					if (!employee.getEmail().isEmpty()) {
						employeeTO.setEmail(employee.getEmail());
					}
				}
				if (employee.getReservationCategory() != null) {
					if (!employee.getReservationCategory().isEmpty()) {
						employeeTO.setReservationCatagory(employee
								.getReservationCategory());
					}
				}
				if (employee.getSmartCardNo() != null) {
					if (!employee.getSmartCardNo().isEmpty()) {
						employeeTO.setSmartCardNo(employee.getSmartCardNo());
					}
				}
				if (employee.getPfNo() != null) {
					if (!employee.getPfNo().isEmpty()) {
						employeeTO.setPbankCCode(employee.getPfNo());
					}
				}
				if (employee.getFourWheelerNo() != null) {
					if (!employee.getFourWheelerNo().isEmpty()) {
						employeeTO
								.setFourWheelerNo(employee.getFourWheelerNo());
					}
				}
				if (employee.getTwoWheelerNo() != null) {
					if (!employee.getTwoWheelerNo().isEmpty()) {
						employeeTO.setTwoWheelerNo(employee.getTwoWheelerNo());
					}
				}
				if (employee.getCurrentAddressMobile1() != null) {
					if (!employee.getCurrentAddressMobile1().isEmpty()) {
						employeeTO.setCurrentAddressMobile1(employee
								.getCurrentAddressMobile1());
					}
				}
				String str = "";
				if (employee.getCurrentAddressHomeTelephone1() != null) {
					if (!employee.getCurrentAddressHomeTelephone1().isEmpty()) {
						String str1 = employee
								.getCurrentAddressHomeTelephone1();
						str = str + str1;
					}
				}
				if (employee.getCurrentAddressHomeTelephone2() != null) {
					if (!employee.getCurrentAddressHomeTelephone2().isEmpty()) {
						String str2 = employee
								.getCurrentAddressHomeTelephone2();
						str = str + str2;
					}
				}
				if (employee.getCurrentAddressHomeTelephone3() != null) {
					if (!employee.getCurrentAddressHomeTelephone3().isEmpty()) {
						String str3 = employee
								.getCurrentAddressHomeTelephone3();
						str = str + str3;
					}
				}
				employeeTO.setHomeTelephone(str);
				String str4 = "";
				if (employee.getCurrentAddressWorkTelephone1()!= null) {
					if (!employee.getCurrentAddressWorkTelephone1().isEmpty()) {
						String str1 = employee.getCurrentAddressWorkTelephone1();
						str4 = str4 + str1;
					}
				}
				if (employee.getCurrentAddressWorkTelephone2()!= null) {
					if (!employee.getCurrentAddressWorkTelephone2().isEmpty()) {
						String str2 = employee.getCurrentAddressWorkTelephone2();
						str4 = str4 + str2;
					}
				}
				if (employee.getCurrentAddressWorkTelephone3() != null) {
					if (!employee.getCurrentAddressWorkTelephone3().isEmpty()) {
						String str3 = employee.getCurrentAddressWorkTelephone3();
						str4 = str4 + str3;
					}
				}
				employeeTO.setWorkTelephone(str4);

				if (employee.getCommunicationAddressLine1() != null) {
					if (!employee.getCommunicationAddressLine1().isEmpty()) {
						employeeTO.setCommunicationAddressLine1(employee
								.getCommunicationAddressLine1());
					}
				}
				if (employee.getCommunicationAddressLine2() != null) {
					if (!employee.getCommunicationAddressLine2().isEmpty()) {
						employeeTO.setCommunicationAddressLine2(employee
								.getCommunicationAddressLine2());
					}
				}
				if (employee.getCommunicationAddressCity() != null) {
					if (!employee.getCommunicationAddressCity().isEmpty()) {
						employeeTO.setCommunicationAddressCity(employee
								.getCommunicationAddressCity());
					}
				}
				if (employee.getCommunicationAddressZip() != null) {
					if (!employee.getCommunicationAddressZip().isEmpty()) {
						employeeTO.setCommunicationAddressZip(employee
								.getCommunicationAddressZip());
					}
				}
				if (employee.getCountryByCommunicationAddressCountryId() != null) {
					if (employee.getCountryByCommunicationAddressCountryId()
							.getName() != null) {
						if (!employee .getCountryByCommunicationAddressCountryId() .getName().isEmpty()) {
							CountryTO country = new CountryTO();
							country .setName(employee .getCountryByCommunicationAddressCountryId() .getName());
							employeeTO.setCountryByCommunicationAddressCountryId(country);
						}
					}
				}
				if (employee.getStateByCommunicationAddressStateId() != null) {
					if (employee.getStateByCommunicationAddressStateId()
							.getName() != null) {
						if (!employee.getStateByCommunicationAddressStateId()
								.getName().isEmpty()) {
							StateTO state = new StateTO();
							state.setName(employee
									.getStateByCommunicationAddressStateId()
									.getName());
							employeeTO
									.setStateByCommunicationAddressStateId(state);
						}
					}
				}
				if (employee.getPermanentAddressLine1() != null) {
					if (!employee.getPermanentAddressLine1().isEmpty()) {
						employeeTO.setPermanentAddressLine1(employee
								.getPermanentAddressLine1());
					}
				}
				if (employee.getPermanentAddressLine2() != null) {
					if (!employee.getPermanentAddressLine2().isEmpty()) {
						employeeTO.setPermanentAddressLine2(employee
								.getPermanentAddressLine2());
					}
				}
				if (employee.getPermanentAddressCity() != null) {
					if (!employee.getPermanentAddressCity().isEmpty()) {
						employeeTO.setPermanentAddressCity(employee
								.getPermanentAddressCity());
					}
				}
				if (employee.getPermanentAddressZip() != null) {
					if (!employee.getPermanentAddressZip().isEmpty()) {
						employeeTO.setPermanentAddressZip(employee
								.getPermanentAddressZip());
					}
				}
				if (employee.getCountryByPermanentAddressCountryId() != null) {
					if (employee.getCountryByPermanentAddressCountryId()
							.getName() != null) {
						if (!employee.getCountryByPermanentAddressCountryId()
								.getName().isEmpty()) {
							CountryTO country = new CountryTO();
							country.setName(employee
									.getCountryByPermanentAddressCountryId()
									.getName());
							employeeTO
									.setCountryByPermanentAddressCountryId(country);
						}
					}
				}
				if (employee.getStateByPermanentAddressStateId() != null) {
					if (employee.getStateByPermanentAddressStateId().getName() != null) {
						if (!employee.getStateByPermanentAddressStateId()
								.getName().isEmpty()) {
							StateTO stateTO = new StateTO();
							stateTO.setName(employee
									.getStateByPermanentAddressStateId()
									.getName());
							employeeTO
									.setStateByPermanentAddressStateId(stateTO);
						}
					}
				}
				if (employee.getEmptype() != null) {
					if (employee.getEmptype().getName() != null) {
						if (!employee.getEmptype().getName().isEmpty()) {
							employeeTO.setEmployeeType(employee.getEmptype()
									.getName());
						}
					}
				}
				if (employee.getDoj() != null) {
					if (!employee.getDoj().toString().isEmpty()) {
						employeeTO.setDateOfJoining(employee.getDoj()
								.toString());
					}
				}
				if (employee.getRejoinDate() != null) {
					if (!employee.getRejoinDate().toString().isEmpty()) {
						employeeTO.setRejoinDate(employee.getRejoinDate()
								.toString());
					}
				}
				if (employee.getRetirementDate() != null) {
					if (!employee.getRetirementDate().toString().isEmpty()) {
						employeeTO.setRetirementDate(employee
								.getRetirementDate().toString());
					}
				}
				if (employee.getActive()) {
					employeeTO.setActive("Yes");
				} else {
					employeeTO.setActive("No");
				}
				if (employee.getStreamId() != null) {
					if (employee.getStreamId().getName() != null) {
						if (!employee.getStreamId().getName().isEmpty()) {
							employeeTO.setStream(employee.getStreamId()
									.getName());
						}
					}
				}
				if (employee.getWorkLocationId() != null) {
					if (!employee.getWorkLocationId().getName().isEmpty()) {
						if (!employee.getWorkLocationId().getName().isEmpty()) {
							employeeTO.setWorkLocationName(employee
									.getWorkLocationId().getName());
						}
					}
				}
			/*	if (employee.getGrade() != null) {
					if (!employee.getGrade().isEmpty()) {
						employeeTO.setGrade(employee.getGrade());
					}
				}*/
				if (employee.getTitleId() != null) {
					if (employee.getTitleId().getTitle() != null) {
						if (!employee.getTitleId().getTitle().isEmpty()) {
							employeeTO.setTitle(employee.getTitleId()
									.getTitle());
						}
					}
				}
				if (employee.getEmployeeByReportToId() != null) {
					if (employee.getEmployeeByReportToId() != null) {
						if (!employee.getEmployeeByReportToId().toString()
								.isEmpty()) {
							employeeTO.setReportToName(employee
									.getEmployeeByReportToId().toString());
						}
					}
				}
				if (employee.getTotalExpYear() != null) {
					if (!employee.getTotalExpYear().isEmpty()) {
						employeeTO.setTotalExpYears(employee.getTotalExpYear());
					}
				}
				if (employee.getTotalExpMonths() != null) {
					if (!employee.getTotalExpMonths().isEmpty()) {
						employeeTO.setTotalExpMonths(employee
								.getTotalExpMonths());
					}
				}
				if (employee.getRelevantExpYears() != null) {
					if (!employee.getRelevantExpYears().isEmpty()) {
						employeeTO.setRelevantExpYears(employee
								.getRelevantExpYears());
					}
				}
				if (employee.getRelevantExpMonths() != null) {
					if (!employee.getRelevantExpMonths().isEmpty()) {
						employeeTO.setRelevantExpMonths(employee
								.getRelevantExpMonths());
					}
				}
				if (employee.getEmpSubjectArea() != null) {
					if (employee.getEmpSubjectArea().getName() != null) {
						if (!employee.getEmpSubjectArea().getName().isEmpty()) {
							employeeTO.setSubjectArea(employee
									.getEmpSubjectArea().getName());
						}
					}
				}
				if (employee.getEmpQualificationLevel() != null) {
					if (employee.getEmpQualificationLevel().getName() != null) {
						if (!employee.getEmpQualificationLevel().getName()
								.isEmpty()) {
							employeeTO.setQualificationLevel(employee
									.getEmpQualificationLevel().getName());
						}
					}
				}
				if (employee.getHighQualifForAlbum() != null) {
					if (!employee.getHighQualifForAlbum().isEmpty()) {
						employeeTO.setHighestQualification(employee
								.getHighQualifForAlbum());
					}
				}
				if (employee.getScale() != null) {
					if (!employee.getScale().isEmpty()) {
						employeeTO.setScale(employee.getScale());
					}
				}
				if (employee.getEmpImmigration() != null) {
					if (employee.getEmpImmigration().getPassportNo() != null) {
						if (!employee.getEmpImmigration().getPassportNo()
								.isEmpty()) {
							employeeTO.setPassPortNo(employee
									.getEmpImmigration().getPassportNo());
						}
					}
					if (employee.getEmpImmigration().getPassportIssueDate() != null) {
						if (!employee.getEmpImmigration()
								.getPassportIssueDate().toString().isEmpty()) {
							employeeTO.setPassPortIssuedDate(employee
									.getEmpImmigration().getPassportIssueDate()
									.toString());
						}
					}
					if (employee.getEmpImmigration().getPassportStatus() != null) {
						if (!employee.getEmpImmigration().getPassportStatus()
								.isEmpty()) {
							employeeTO.setPassPortStatus(employee
									.getEmpImmigration().getPassportStatus());
						}
					}
					if (employee.getEmpImmigration().getPassportDateOfExpiry() != null) {
						if (!employee.getEmpImmigration()
								.getPassportDateOfExpiry().toString().isEmpty()) {
							employeeTO.setPassPortDOE(employee
									.getEmpImmigration()
									.getPassportDateOfExpiry().toString());
						}
					}
					if (employee.getEmpImmigration().getPassportReviewStatus() != null) {
						if (!employee.getEmpImmigration()
								.getPassportReviewStatus().isEmpty()) {
							employeeTO.setPassPortReviewComments(employee
									.getEmpImmigration()
									.getPassportReviewStatus());
						}
					}
					if (employee.getEmpImmigration().getPassportComments() != null) {
						if (!employee.getEmpImmigration().getPassportComments()
								.isEmpty()) {
							employeeTO.setPassPortComments(employee
									.getEmpImmigration().getPassportComments());
						}
					}
					if (employee.getEmpImmigration().getVisaNo() != null) {
						if (!employee.getEmpImmigration().getVisaNo().isEmpty()) {
							employeeTO.setVisaNo(employee.getEmpImmigration()
									.getVisaNo());
						}
					}
					if (employee.getEmpImmigration().getVisaIssueDate() != null) {
						if (!employee.getEmpImmigration().getVisaIssueDate()
								.toString().isEmpty()) {
							employeeTO.setVisaIssuedDate(employee
									.getEmpImmigration().getVisaIssueDate()
									.toString());
						}
					}
					if (employee.getEmpImmigration().getVisaReviewStatus() != null) {
						if (!employee.getEmpImmigration().getVisaReviewStatus()
								.isEmpty()) {
							employeeTO.setVisaReviewComments(employee
									.getEmpImmigration().getVisaReviewStatus());
						}
					}
					if (employee.getEmpImmigration().getVisaDateOfExpiry() != null) {
						if (!employee.getEmpImmigration().getVisaDateOfExpiry()
								.toString().isEmpty()) {
							employeeTO.setVisaDoe(employee.getEmpImmigration()
									.getVisaDateOfExpiry().toString());
						}
					}
					if (employee.getEmpImmigration().getVisaComments() != null) {
						if (!employee.getEmpImmigration().getVisaComments()
								.isEmpty()) {
							employeeTO.setVisaComments(employee
									.getEmpImmigration().getVisaComments());
						}
					}
					if (employee.getEmpImmigration().getVisaStatus() != null) {
						if (!employee.getEmpImmigration().getVisaStatus() .isEmpty()) {
							employeeTO.setVisaStatus(employee .getEmpImmigration().getVisaStatus());
						}
					}
					if(employee.getEmpImmigration().getCountryByPassportCountryId()!=null){
						if(employee.getEmpImmigration().getCountryByPassportCountryId().getName()!=null){
							if(!employee.getEmpImmigration().getCountryByPassportCountryId().getName().isEmpty()){
								employeeTO.setPassPortCItizenShip(employee.getEmpImmigration().getCountryByPassportCountryId().getName());
							}
						}
					}
					if(employee.getEmpImmigration().getCountryByVisaCountryId()!=null){
						if(employee.getEmpImmigration().getCountryByVisaCountryId().getName()!=null){
							if(!employee.getEmpImmigration().getCountryByVisaCountryId().getName().isEmpty()){
								employeeTO.setVisaCitizenShip(employee.getEmpImmigration().getCountryByVisaCountryId().getName());
							}
						}
					}
				}
				if (employee.getEmergencyContName() != null) {
					if (!employee.getEmergencyContName().isEmpty()) {
						employeeTO.setEmergencyContName(employee
								.getEmergencyContName());
					}
				}
				if (employee.getRelationship() != null) {
					if (!employee.getRelationship().isEmpty()) {
						employeeTO.setRelationship(employee.getRelationship());
					}
				}
				if (employee.getEmergencyHomeTelephone() != null) {
					if (!employee.getEmergencyHomeTelephone().isEmpty()) {
						employeeTO.setEmergencyHomeTelephone(employee
								.getEmergencyHomeTelephone());
					}
				}
				if (employee.getEmergencyMobile() != null) {
					if (!employee.getEmergencyMobile().isEmpty()) {
						employeeTO.setEmergencyMobile(employee
								.getEmergencyMobile());
					}
				}
				if (employee.getEmergencyWorkTelephone() != null) {
					if (!employee.getEmergencyWorkTelephone().isEmpty()) {
						employeeTO.setEmergencyWorkTelephone(employee
								.getEmergencyWorkTelephone());
					}
				}
				if (employee.getDateOfLeaving() != null) {
					if (!employee.getDateOfLeaving().toString().isEmpty()) {
						employeeTO.setDateOfLeaving(employee.getDateOfLeaving()
								.toString());
					}
				}
				if (employee.getDateOfResignation() != null) {
					if (!employee.getDateOfResignation().toString().isEmpty()) {
						employeeTO.setDateOfResignation(employee
								.getDateOfResignation().toString());
					}
				}
				if (employee.getReasonOfLeaving() != null) {
					if (!employee.getReasonOfLeaving().isEmpty()) {
						employeeTO.setReasonOfLeaving(employee
								.getReasonOfLeaving());
					}
				}
				if (employee.getBooks() != null) {
					if (!employee.getBooks().isEmpty()) {
						employeeTO.setBooks(employee.getBooks());
					}
				}
				if (employee.getNoOfPublicationsNotRefered() != null) {
					if (!employee.getNoOfPublicationsNotRefered().isEmpty()) {
						employeeTO.setNoOfPublicationsNotRefered(employee.getNoOfPublicationsNotRefered());
					}
				}
				if (employee.getNoOfPublicationsRefered() != null) {
					if (!employee.getNoOfPublicationsRefered().isEmpty()) {
						employeeTO.setNoOfPublicationsRefered(employee.getNoOfPublicationsRefered());
					}
				}
				if (employee.getTeachingStaff()) {
					employeeTO.setTeachingStaff("Yes");
				} else {
					employeeTO.setTeachingStaff("No");
				}
				if(employee.getEmpImages()!=null){
					Set<EmpImages> empImages = employee.getEmpImages();
					Iterator<EmpImages> iterator2 = empImages.iterator();
					while (iterator2.hasNext()) {
						EmpImages empImages2 = (EmpImages) iterator2.next();
						if(empImages2.getEmpPhoto()!=null){
							employeeTO.setPhoto("Yes");
						}else{
							employeeTO.setPhoto("No");
						}
					}
				}
				if(employee.getUserses()!=null){
					Set<Users> users = employee.getUserses();
					Iterator<Users> iterator3 = users.iterator();
					while (iterator3.hasNext()) {
						Users usersBo = (Users) iterator3.next();
						if(usersBo.getUserName()!=null){
							if(!usersBo.getUserName().isEmpty()){
								employeeTO.setUserName(usersBo.getUserName());
							}
						}
//						if(usersBo.getPwd()!=null){
//							if(!usersBo.getPwd().isEmpty()){
//								employeeTO.setPassword(EncryptUtil.getInstance().decryptDES(usersBo.getPwd()));
//							}
//						}
					}
				}if (employee.getPayScaleId() != null) {
					if (employee.getPayScaleId().getPayScale() != null) {
						if (!employee.getPayScaleId().getPayScale().isEmpty()) {
							employeeTO.setPayScaleId(employee
									.getPayScaleId().getPayScale());
						}
					}
				}
				/*----------------------------------- code added by sudhir---------------------------------------*/
				/* calculating Experience in cjc (or) cu from joining Date to present day*/
				Date joiningDate = null;
				String fromDateDay = null;
				String fromDateMonth = null;
				if(employeeTO.getDateOfJoining()!=null && !employeeTO.getDateOfJoining().isEmpty()){
					String joinDate = CommonUtil.ConvertStringToDateFormat( employeeTO.getDateOfJoining(), "yyyy-mm-dd", "dd/mm/yyyy");
					employeeTO.setDateOfJoining(joinDate);
					 joiningDate = CommonUtil.ConvertStringToDate(joinDate);
					 fromDateDay = employeeTO.getDateOfJoining().substring(0, 2);
					 fromDateMonth = employeeTO.getDateOfJoining().substring(3, 5);
				}
				String todaysDate = CommonUtil.getTodayDate();
				String toDateDay = todaysDate.substring(0, 2);
				String toDateMonth = todaysDate.substring(3, 5);
				Date toDate = CommonUtil.ConvertStringToDate(todaysDate);
				int yy = 0;
				int mm =0;
				if(joiningDate!=null && toDate!=null){
					double msPerGregorianYear = 365.25 * 86400 * 1000;
				 	double years1 =(toDate.getTime() - joiningDate.getTime()) / msPerGregorianYear;
				 	 yy = (int) years1;
			         mm = (int) ((years1 - yy) * 12);
			        if(fromDateDay.equals(toDateDay)){
			        	if(fromDateMonth.equals(toDateMonth)){
			        		mm=0;
			        		yy = (int) Math.round(years1);
			        	}
			        }
			        if(fromDateDay.equals(toDateDay)){
			        	if(!fromDateMonth.equals(toDateMonth)){
			        		mm = (int) Math.round(((years1 - yy) * 12));
			        	}
			        }
			        employeeTO.setExperienceInYearsAndMonths(String.valueOf(yy)+"Years"+"   "+String.valueOf(mm)+"Months");
				}
//				calculating totalCurrentExperience 
				int releventExpMonths = 0;
				int releventExpYears = 0;
				String totalCurrentExpYearsAndMonths = "";
				if(employeeTO.getRelevantExpMonths()!=null && !employeeTO.getRelevantExpMonths().isEmpty()){
					releventExpMonths = Integer.parseInt(employeeTO.getRelevantExpMonths());
				}
				if(employeeTO.getRelevantExpYears()!=null && !employeeTO.getRelevantExpYears().isEmpty()){
					releventExpYears= Integer.parseInt(employeeTO.getRelevantExpYears());
				}
				int totalYearsOfRelevantAndExperienceIn = releventExpYears + yy;
				int totalMonthsOfRecognisedAndExperienceIn = releventExpMonths + mm;
				if(totalMonthsOfRecognisedAndExperienceIn>=12){
					int months = totalMonthsOfRecognisedAndExperienceIn % 12;
					int years = totalMonthsOfRecognisedAndExperienceIn / 12 ;
					if(releventExpYears!=0){
						releventExpYears = releventExpYears + years;
					}else {
						releventExpYears = years;
					}
					totalCurrentExpYearsAndMonths = String.valueOf(releventExpYears)+"Years"+"   "+String.valueOf(months)+"Months";
				}else{
					totalCurrentExpYearsAndMonths = String.valueOf(totalYearsOfRelevantAndExperienceIn)+"Years"+"   "+String.valueOf(totalMonthsOfRecognisedAndExperienceIn)+"Months";
				}
				if(totalCurrentExpYearsAndMonths!=null && !totalCurrentExpYearsAndMonths.isEmpty()){
					employeeTO.setTotalCurrentExpYearsAndMonths(totalCurrentExpYearsAndMonths);
				}
				/*-------------------------------------------------------------*/
				if (employee.getExtensionNumber() != null) {
					if (!employee.getExtensionNumber().isEmpty()) {
						employeeTO.setExtensionNumber(employee.getExtensionNumber());
					}
				}
				if(employee.getDeputationToDepartmentId()!=null){
					employeeTO.setDeputaionToDepartment(employee.getDeputationToDepartmentId().getName());
				}
				if(employee.getAppointmentLetterDate()!=null){
					employeeTO.setAppointmentLetterDate(CommonUtil.ConvertStringToDateFormat(employee.getAppointmentLetterDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(employee.getReferenceNumberForAppointment()!=null){
					employeeTO.setReferenceNumberForAppointment(employee.getReferenceNumberForAppointment());
				}
				if(employee.getReleavingOrderDate()!=null){
					employeeTO.setReleavingOrderDate(CommonUtil.ConvertStringToDateFormat(employee.getReleavingOrderDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(employee.getReferenceNubmerforReleaving()!=null){
					employeeTO.setReferenceNumberForReleaving(employee.getReferenceNubmerforReleaving());
				}
				list.add(employeeTO);
			}
		}
		return list;
}

/**
 * @param employeeTOs
 * @param empReportTo 
 * @param request 
 * @return
 * @throws Exception
 */
public boolean convertToExcel(List<EmployeeTO> employeeTOs, EmployeeReportTO empReportTo, HttpServletRequest request) throws Exception {
	boolean isUpdated=false;
	Properties prop = new Properties();
	try {
		InputStream inputStream = CommonUtil.class.getClassLoader()
				.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(inputStream);
	} 
	catch (IOException e) {
		log.error("Error occured at exportTOExcel of EmployeeReportHelper ",e);
		throw new IOException(e);
	}
	String fileName=prop.getProperty(CMSConstants.EMP_EXCEL_REPORT);
	File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);
	if(excelFile.exists()){
		excelFile.delete();
	}
	FileOutputStream fos = null;
	ByteArrayOutputStream bos=null;
	XSSFWorkbook wb=null;
	XSSFSheet sheet=null;
	XSSFRow row=null;
	XSSFCell cell=null;
	
	if(employeeTOs!=null){
		int count = 0;
		Iterator<EmployeeTO> iterator = employeeTOs.iterator();
		try{
			wb=new XSSFWorkbook();
			XSSFCellStyle cellStyle=wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
			sheet = wb.createSheet("Employee Report");
			row = sheet.createRow(count);
			count = sheet.getFirstRowNum();
			// Create cells in the row and put some data in it.
			if(empReportTo.getEmpIdDisplay()!=null && empReportTo.getEmpIdDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getEmpIdPoistion()).setCellValue("EmpId");
			}
			if(empReportTo.getNameDisplay()!=null && empReportTo.getNameDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getNamePoistion()).setCellValue("Name");
			}
			if(empReportTo.getDepartmentDisplay()!=null && empReportTo.getDepartmentDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getDepartmentPoistion()).setCellValue("Department");
			}
			if(empReportTo.getDesignationDisplay()!=null && empReportTo.getDesignationDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getDesignationPoistion()).setCellValue("Designation");
			}
			if(empReportTo.getUidDisplay()!=null && empReportTo.getUidDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getUidPosition()).setCellValue("UID");
			}
			if(empReportTo.getNationalityDisplay()!=null && empReportTo.getNationalityDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getNationalityPosition()).setCellValue("Nationality");
			}
			if(empReportTo.getGenderDisplay()!=null && empReportTo.getGenderDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getGenderPosition()).setCellValue("Gender");
			}
			if(empReportTo.getMatrialStatusDisplay()!=null && empReportTo.getMatrialStatusDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getMatrialStatusPosition()).setCellValue("Marital Status");
			}
			if(empReportTo.getDobDiaplay()!=null && empReportTo.getDobDiaplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getDobPosition()).setCellValue("Date of birth");
			}
			if(empReportTo.getAgeDis()!=null && empReportTo.getAgeDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getAgePos()).setCellValue("Age");
			}
			if(empReportTo.getBloodGroupDisplay()!=null && empReportTo.getBloodGroupDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getBloodGrouopPosition()).setCellValue("Blood Group");
			}
			if(empReportTo.getReligionDisplay()!=null && empReportTo.getReligionDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getReligionPosition()).setCellValue("Religion");
			}
			if(empReportTo.getPanNoDisplay()!=null && empReportTo.getPanNoDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPanNoPosition()).setCellValue("PanCard No");
			}
			if(empReportTo.getOfficialEmailDisplay()!=null && empReportTo.getOfficialEmailDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getOfficialEmailPosition()).setCellValue("Official Email Id");
			}
			if(empReportTo.getEmailDisplay()!=null && empReportTo.getEmailDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getEmailPosition()).setCellValue("Personal Email Id");
			}
			if(empReportTo.getResCatDisplay()!=null && empReportTo.getResCatDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getResCatPosition()).setCellValue("Reservation Category");
			}
			if(empReportTo.getMobileDisplay()!=null && empReportTo.getMobileDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getMobilePosition()).setCellValue("Mobile");
			}
			if(empReportTo.getBankAccNoDisplay()!=null && empReportTo.getBankAccNoDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getBankAcccNoPosition()).setCellValue("BankAccount No");
			}
			if(empReportTo.getSmartCardDisplay()!=null && empReportTo.getSmartCardDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getSmartCardPosition()).setCellValue("SmartCard No");
			}
			if(empReportTo.getPfNoDisplay()!=null && empReportTo.getPfNoDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPfNoPosition()).setCellValue("Pf Account No");
			}
			if(empReportTo.getFourWheelerDisplay()!=null && empReportTo.getFourWheelerDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getFourWheelerPosition()).setCellValue("FourWheeler No ");
			}
			if(empReportTo.getTwoWheelerDisplay()!=null && empReportTo.getTwoWheelerDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getTwoWheelerPosition()).setCellValue("TwoWheeler No");
			}
			if(empReportTo.getHomeTelephoneDisplay()!=null && empReportTo.getHomeTelephoneDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getHomeTelephonePosition()).setCellValue("Home Telephone");
			}
			if(empReportTo.getWorkTelephoneDisplay()!=null && empReportTo.getWorkTelephoneDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getWorkTelephonePosition()).setCellValue("Work TelePhone");
			}
			if(empReportTo.getCurrentAddLine1Display()!=null && empReportTo.getCurrentAddLine1Display().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getCurrentAddLine1Position()).setCellValue("Current Address Line1");
			}
			if(empReportTo.getCurrentAddLine2Display()!=null && empReportTo.getCurrentAddLine2Display().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getCurrentAddLine2Position()).setCellValue("Current Address Line2");
			}
			if(empReportTo.getCurrentCountrydis()!=null && empReportTo.getCurrentCountrydis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getCurrentCounPos()).setCellValue("Current Country");
			}
			if(empReportTo.getCurrentCityDis()!=null && empReportTo.getCurrentCityDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getCurentCitypos()).setCellValue("Current City");
			}
			if(empReportTo.getCurrentPincodeDis()!=null && empReportTo.getCurrentPincodeDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getCurentPinCodePos()).setCellValue("Current PinCode");
			}
			if(empReportTo.getCurrentStateDis()!=null && empReportTo.getCurrentStateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getCurrentStatePos()).setCellValue("Current State");
			}
			if(empReportTo.getPerAddLine1Dis()!=null && empReportTo.getPerAddLine1Dis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPerAddLine1Pos()).setCellValue("Permanent Address Line1");
			}
			if(empReportTo.getPermAddLine2Dis()!=null && empReportTo.getPermAddLine2Dis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPermAddLine2Pos()).setCellValue("Permanent Address line2");
			}
			if(empReportTo.getPermContryDis()!=null && empReportTo.getPermContryDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPermCountryPos()).setCellValue("Permanent Country");
			}
			if(empReportTo.getPermCityDis()!=null && empReportTo.getPermCityDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPermCityPos()).setCellValue("Permanent City");
			}
			if(empReportTo.getPermPinCodeDis()!=null && empReportTo.getPermPinCodeDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPermPinCodePos()).setCellValue("Permanent PinCode");
			}
			if(empReportTo.getPermStateDis()!=null && empReportTo.getPermStateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPermStatePos()).setCellValue("Permanent State");
			}
			if(empReportTo.getEmpTypeDis()!=null && empReportTo.getEmpTypeDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getEmpTypePos()).setCellValue("Employee Type");
			}
			if(empReportTo.getDojDis()!=null && empReportTo.getDojDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getDojPos()).setCellValue("Date Of Joining");
			}
			if(empReportTo.getRejoinDis()!=null && empReportTo.getRejoinDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getRejoinPos()).setCellValue("Rejoin Date");
			}
			if(empReportTo.getDateOfResignationDis()!=null && empReportTo.getDateOfResignationDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getDateOfResignationPos()).setCellValue("Date of Retirement");
			}
			if(empReportTo.getAppointmentLetterDateDis()!=null && empReportTo.getAppointmentLetterDateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getAppointmentLetterDatePos()).setCellValue("Appointment Letter Date");
			}
			if(empReportTo.getReferenceNumberForAppointmentDis()!=null && empReportTo.getReferenceNumberForAppointmentDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getReferenceNumberForAppointmentPos()).setCellValue("Reference Number For Appointment");
			}
			if(empReportTo.getActiveDis()!=null && empReportTo.getActiveDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getActivePOs()).setCellValue("Active");
			}
			if(empReportTo.getStrDetailsDis()!=null && empReportTo.getStrDetailsDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getStrDetailsPos()).setCellValue("Stream Details");
			}
			if(empReportTo.getWorkLocdis()!=null && empReportTo.getWorkLocdis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getWorkLocPos()).setCellValue("Work Location");
			}
			if(empReportTo.getDeputationToDepartmentDis()!=null && empReportTo.getDeputationToDepartmentDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getDeputationToDepartmentPos()).setCellValue("Deputation To Department");
			}
			if(empReportTo.getReportTODis()!=null && empReportTo.getReportTODis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getReportTOPos()).setCellValue("Report To");
			}
			if(empReportTo.getTitledis()!=null && empReportTo.getTitledis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getTitlePos()).setCellValue("Title");
			}
			if(empReportTo.getTotExpYearsDis()!=null && empReportTo.getTotExpYearsDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getTotExpYearsPos()).setCellValue("Total Experience Years");
			}
			if(empReportTo.getTotExpMonDis()!=null && empReportTo.getTotExpMonDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getTotExpMonPos()).setCellValue("Total Experience Months");
			}
			if(empReportTo.getRelExpYearsDis()!=null && empReportTo.getRelExpYearsDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getRelExpYearsPos()).setCellValue("Recognised Experience Years");
			}
			if(empReportTo.getRelExpMonDis()!=null && empReportTo.getRelExpMonDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getRelExpMonPos()).setCellValue("Recognised Experience Months");
			}
			if(empReportTo.getQuaLevelDis()!=null && empReportTo.getQuaLevelDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getGuaLevelPos()).setCellValue("Qualification Level");
			}
			if(empReportTo.getHighestQuaDis()!=null && empReportTo.getHighestQuaDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getHighestQuaPos()).setCellValue("Highest Qualification");
			}
			if(empReportTo.getSubjectAreaDis()!=null && empReportTo.getSubjectAreaDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getSubjectAreaPOs()).setCellValue("Subject Area");
			}
			if(empReportTo.getScaleDis()!=null && empReportTo.getScaleDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getScalePos()).setCellValue("Scale");
			}
			if(empReportTo.getEmerNameDis()!=null && empReportTo.getEmerNameDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getEmerNamePos()).setCellValue("Emergency Contact Name");
			}
			if(empReportTo.getEmerReldis()!=null && empReportTo.getEmerReldis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getEmerRelPos()).setCellValue("RelationShip");
			}
			if(empReportTo.getEmergHomeTelDis()!=null && empReportTo.getEmergHomeTelDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getEmergHomeTelPos()).setCellValue("Emergency HomeTelephone");
			}
			if(empReportTo.getEmergMobileDis()!=null && empReportTo.getEmergMobileDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getEmergMobilePos()).setCellValue("Emergency Mobile No");
			}
			if(empReportTo.getEmergWorkTelDis()!=null && empReportTo.getEmergWorkTelDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getEmergWorkTelPos()).setCellValue("Emergency Work Telephone");
			}
			if(empReportTo.getPassPortNoDis()!=null && empReportTo.getPassPortNoDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPassPortNoPos()).setCellValue("PassPort Number");
			}
			if(empReportTo.getPassPortIssueDateDis()!=null && empReportTo.getPassPortIssueDateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPassPortIssueDatePos()).setCellValue("PassPort Issued Date");
			}
			if(empReportTo.getPassPortStatusDis()!=null && empReportTo.getPassPortStatusDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPassPortStatusPos()).setCellValue("PassPort Status");
			}
			if(empReportTo.getPassPortDOEDis()!=null && empReportTo.getPassPortDOEDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPassPortDOEPos()).setCellValue("PassPort Date of Expiry");
			}
			if(empReportTo.getPassPortReviewStatusDis()!=null && empReportTo.getPassPortReviewStatusDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPassPortReviewStatusPos()).setCellValue("PassPort Review Status");
			}
			if(empReportTo.getPassPortCommentsDis()!=null && empReportTo.getPassPortCommentsDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPassPortCommentsPos()).setCellValue("PassPort Comments");
			}
			if(empReportTo.getPassPortCountryDis()!=null && empReportTo.getPassPortCountryDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPassPortCountryPos()).setCellValue("PassPort Country");
			}
			if(empReportTo.getVisaNoDis()!=null && empReportTo.getVisaNoDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getVisaNoPos()).setCellValue("Visa Number");
			}
			if(empReportTo.getVisaIssueDateDis()!=null && empReportTo.getVisaIssueDateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getVisaIssueDatePos()).setCellValue("Visa Issued Date");
			}
			if(empReportTo.getVisaStatusDis()!=null && empReportTo.getVisaStatusDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getVisaStatusPos()).setCellValue("Visa Status");
			}
			if(empReportTo.getVisaDOEDis()!=null && empReportTo.getVisaDOEDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getVisaDOEPos()).setCellValue("Visa Date of Expiry");
			}
			if(empReportTo.getVisaCommentsDis()!=null && empReportTo.getVisaCommentsDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getVisaCommentsPos()).setCellValue("Visa Comments");
			}
			if(empReportTo.getVisaCountryDis()!=null && empReportTo.getVisaCountryDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getVisaCountryPos()).setCellValue("Visa Country");
			}
			if(empReportTo.getVisaReviewStatusDis()!=null && empReportTo.getVisaReviewStatusDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getVisaReviewStatusPos()).setCellValue("Visa Review Status");
			}
			if(empReportTo.getReasonOfLeavingDis()!=null && empReportTo.getReasonOfLeavingDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getReasonOfLeavingPos()).setCellValue("Reason Of Leaving");
			}
			if(empReportTo.getDateOfLeavingDis()!=null && empReportTo.getDateOfLeavingDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getDateOfLeavingPos()).setCellValue("Date of Leaving");
			}
			if(empReportTo.getDateOfResignationDis()!=null && empReportTo.getDateOfResignationDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getDateOfResignationPos()).setCellValue("Date Of Resignatioin");
			}
			if(empReportTo.getReleavingOrderDateDis()!=null && empReportTo.getReleavingOrderDateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getReleavingOrderDatePos()).setCellValue("Releaving Order Date");
			}
			if(empReportTo.getReferenceNumberForReleavingDis()!=null && empReportTo.getReferenceNumberForReleavingDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getReferenceNumberForReleavingPos()).setCellValue("Reference Number For Releaving");
			}
			if(empReportTo.getTeachingStaffDis()!=null && empReportTo.getTeachingStaffDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getTeachingStaffPos()).setCellValue("Teaching Staff");
			}
			if(empReportTo.getEmpPhotoDis()!=null && empReportTo.getEmpPhotoDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getEmpPhotoPos()).setCellValue("Photo");
			}
			if(empReportTo.getUserNameDis()!=null && empReportTo.getUserNameDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getUserNamePos()).setCellValue("UserName");
			}
			if(empReportTo.getBooksDis()!=null && empReportTo.getBooksDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getBooksPos()).setCellValue("No. of Books with ISBN");
			}
			if(empReportTo.getNoOfPublicationsNotReferedDis()!=null && empReportTo.getNoOfPublicationsNotReferedDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getNoOfPublicationsNotReferedPos()).setCellValue("Publications Non Refereed");
			}
			if(empReportTo.getNoOfPublicationsReferedDis()!=null && empReportTo.getNoOfPublicationsReferedDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getNoOfPublicationsReferedPos()).setCellValue("No. of Publications Refereed");
			}
			if(empReportTo.getPayScaleIdDis()!=null && empReportTo.getPayScaleIdDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getPayScaleIdPos()).setCellValue("Grade");
			}
//			if(empReportTo.getPassWordDis()!=null && empReportTo.getPassWordDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
//				row.createCell((short)empReportTo.getPassWordPos()).setCellValue("PassWord");
//			}
			if(empReportTo.getExperienceInDis()!=null && empReportTo.getExperienceInDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				if(CMSConstants.LINK_FOR_CJC){
					row.createCell((short)empReportTo.getExperienceInPos()).setCellValue("Experience In CJC");
				}else{
					row.createCell((short)empReportTo.getExperienceInPos()).setCellValue("Experience In CU");
				}
			}
			if(empReportTo.getTotalCurrentExperienceDis()!=null && empReportTo.getTotalCurrentExperienceDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getTotalCurrentExperiencePos()).setCellValue("Total Current Experience");
			}
			if(empReportTo.getExtentionNoDis()!=null && empReportTo.getExtentionNoDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)){
				row.createCell((short)empReportTo.getExtentionNoPos()).setCellValue("Extension Number");
			}
			while (iterator.hasNext()) {
				EmployeeTO empTO = (EmployeeTO) iterator.next();
				count = count +1;
				row = sheet.createRow(count);
				if(empReportTo.getEmpIdDisplay()!=null && empReportTo.getEmpIdDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getFingerPrintId()!=null){
					row.createCell((short)empReportTo.getEmpIdPoistion()).setCellValue(empTO.getFingerPrintId());
				}
				if(empReportTo.getNameDisplay()!=null && empReportTo.getNameDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getFirstName()!=null){
					row.createCell((short)empReportTo.getNamePoistion()).setCellValue(empTO.getFirstName());
				}
				if(empReportTo.getDepartmentDisplay()!=null && empReportTo.getDepartmentDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getDepartmentName()!=null){
					row.createCell((short)empReportTo.getDepartmentPoistion()).setCellValue(empTO.getDepartmentName());
				}
				if(empReportTo.getDesignationDisplay()!=null && empReportTo.getDesignationDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getDesignationName()!=null){
					row.createCell((short)empReportTo.getDesignationPoistion()).setCellValue(empTO.getDesignationName());
				}
				if(empReportTo.getUidDisplay()!=null && empReportTo.getUidDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getUid()!=null){
					row.createCell((short)empReportTo.getUidPosition()).setCellValue(empTO.getUid());
				}
				if(empReportTo.getNationalityDisplay()!=null && empReportTo.getNationalityDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getNationalityName()!=null){
					row.createCell((short)empReportTo.getNationalityPosition()).setCellValue(empTO.getNationalityName());
				}
				if(empReportTo.getGenderDisplay()!=null && empReportTo.getGenderDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getGender()!=null){
					row.createCell((short)empReportTo.getGenderPosition()).setCellValue(empTO.getGender());
				}
				if(empReportTo.getBloodGroupDisplay()!=null && empReportTo.getBloodGroupDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) &&empTO.getBloodGroup()!=null){
					row.createCell((short)empReportTo.getBloodGrouopPosition()).setCellValue(empTO.getBloodGroup());
				}
				if(empReportTo.getMatrialStatusDisplay()!=null && empReportTo.getMatrialStatusDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getMaritalStatus()!=null){
					row.createCell((short)empReportTo.getMatrialStatusPosition()).setCellValue(empTO.getMaritalStatus());
				}
				if(empReportTo.getDobDiaplay()!=null && empReportTo.getDobDiaplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getDob()!=null){
					row.createCell((short)empReportTo.getDobPosition()).setCellValue(empTO.getDob());
				}
				if(empReportTo.getAgeDis()!=null && empReportTo.getAgeDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getAge()!=null){
					row.createCell((short)empReportTo.getAgePos()).setCellValue(empTO.getAge());
				}
				if(empReportTo.getReligionDisplay()!=null && empReportTo.getReligionDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getReligion()!=null){
					row.createCell((short)empReportTo.getReligionPosition()).setCellValue(empTO.getReligion());
				}
				if(empReportTo.getPanNoDisplay()!=null && empReportTo.getPanNoDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getPanNo()!=null){
					row.createCell((short)empReportTo.getPanNoPosition()).setCellValue(empTO.getPanNo());
				}
				if(empReportTo.getOfficialEmailDisplay()!=null && empReportTo.getOfficialEmailDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getWorkEmail()!=null){
					row.createCell((short)empReportTo.getOfficialEmailPosition()).setCellValue(empTO.getWorkEmail());
				}
				if(empReportTo.getEmailDisplay()!=null && empReportTo.getEmailDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getEmail()!=null){
					row.createCell((short)empReportTo.getEmailPosition()).setCellValue(empTO.getEmail());
				}
				if(empReportTo.getResCatDisplay()!=null && empReportTo.getResCatDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getReservationCatagory()!=null){
					row.createCell((short)empReportTo.getResCatPosition()).setCellValue(empTO.getReservationCatagory());
				}
				if(empReportTo.getMobileDisplay()!=null && empReportTo.getMobileDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getCurrentAddressMobile1()!=null){
					row.createCell((short)empReportTo.getMobilePosition()).setCellValue(empTO.getCurrentAddressMobile1());
				}
				if(empReportTo.getBankAccNoDisplay()!=null && empReportTo.getBankAccNoDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getBankAccNo()!=null){
					row.createCell((short)empReportTo.getBankAcccNoPosition()).setCellValue(empTO.getBankAccNo());
				}
				if(empReportTo.getPfNoDisplay()!=null && empReportTo.getPfNoDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getPbankCCode()!=null){
					row.createCell((short)empReportTo.getPfNoPosition()).setCellValue(empTO.getPbankCCode());
				}
				if(empReportTo.getFourWheelerDisplay()!=null && empReportTo.getFourWheelerDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getFourWheelerNo()!=null){
					row.createCell((short)empReportTo.getFourWheelerPosition()).setCellValue(empTO.getFourWheelerNo());
				}
				if(empReportTo.getTwoWheelerDisplay()!=null && empReportTo.getTwoWheelerDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getTwoWheelerNo()!=null){
					row.createCell((short)empReportTo.getTwoWheelerPosition()).setCellValue(empTO.getTwoWheelerNo());
				}
				if(empReportTo.getSmartCardDisplay()!=null && empReportTo.getSmartCardDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getSmartCardNo()!=null){
					row.createCell((short)empReportTo.getSmartCardPosition()).setCellValue(empTO.getSmartCardNo());
				}
				if(empReportTo.getHomeTelephoneDisplay()!=null && empReportTo.getHomeTelephoneDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getHomeTelephone()!=null){
					row.createCell((short)empReportTo.getHomeTelephonePosition()).setCellValue(empTO.getHomeTelephone());
				}
				if(empReportTo.getWorkTelephoneDisplay()!=null && empReportTo.getWorkTelephoneDisplay().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getWorkTelephone()!=null){
					row.createCell((short)empReportTo.getWorkTelephonePosition()).setCellValue(empTO.getWorkTelephone());
				}
				if(empReportTo.getCurrentAddLine1Display()!=null && empReportTo.getCurrentAddLine1Display().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getCommunicationAddressLine1()!=null){
					row.createCell((short)empReportTo.getCurrentAddLine1Position()).setCellValue(empTO.getCommunicationAddressLine1());
				}
				if(empReportTo.getCurrentAddLine2Display()!=null && empReportTo.getCurrentAddLine2Display().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getCommunicationAddressLine2()!=null){
					row.createCell((short)empReportTo.getCurrentAddLine2Position()).setCellValue(empTO.getCommunicationAddressLine2());
				}
				if(empReportTo.getCurrentCityDis()!=null && empReportTo.getCurrentCityDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getCommunicationAddressCity()!=null){
					row.createCell((short)empReportTo.getCurentCitypos()).setCellValue(empTO.getCommunicationAddressCity());
				}
				if(empReportTo.getCurrentPincodeDis()!=null && empReportTo.getCurrentPincodeDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getCommunicationAddressZip()!=null){
					row.createCell((short)empReportTo.getCurentPinCodePos()).setCellValue(empTO.getCommunicationAddressZip());
				}
				if(empReportTo.getCurrentCountrydis()!=null && empReportTo.getCurrentCountrydis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getCountryByCommunicationAddressCountryId().getName()!=null){
					row.createCell((short)empReportTo.getCurrentCounPos()).setCellValue(empTO.getCountryByCommunicationAddressCountryId().getName());
				}
				if(empReportTo.getCurrentStateDis()!=null && empReportTo.getCurrentStateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getStateByCommunicationAddressStateId().getName()!=null){
					row.createCell((short)empReportTo.getCurrentStatePos()).setCellValue(empTO.getStateByCommunicationAddressStateId().getName());
				}
				if(empReportTo.getPerAddLine1Dis()!=null && empReportTo.getPerAddLine1Dis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getPermanentAddressLine1()!=null){
					row.createCell((short)empReportTo.getPerAddLine1Pos()).setCellValue(empTO.getPermanentAddressLine1());
				}
				if(empReportTo.getPermAddLine2Dis()!=null && empReportTo.getPermAddLine2Dis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getPermanentAddressLine2()!=null){
					row.createCell((short)empReportTo.getPermAddLine2Pos()).setCellValue(empTO.getPermanentAddressLine2());
				}
				if(empReportTo.getPermCityDis()!=null && empReportTo.getPermCityDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getPermanentAddressCity()!=null){
					row.createCell((short)empReportTo.getPermCityPos()).setCellValue(empTO.getPermanentAddressCity());
				}
				if(empReportTo.getPermContryDis()!=null && empReportTo.getPermContryDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getCountryByPermanentAddressCountryId()!=null){
					row.createCell((short)empReportTo.getPermCountryPos()).setCellValue(empTO.getCountryByPermanentAddressCountryId().getName());
				}
				if(empReportTo.getPermPinCodeDis()!=null && empReportTo.getPermPinCodeDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getPermanentAddressZip()!=null){
					row.createCell((short)empReportTo.getPermPinCodePos()).setCellValue(empTO.getPermanentAddressZip());
				}
				if(empReportTo.getPermStateDis()!=null && empReportTo.getPermStateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getStateByPermanentAddressStateId()!=null){
					row.createCell((short)empReportTo.getPermStatePos()).setCellValue(empTO.getStateByPermanentAddressStateId().getName());
				}
				if(empReportTo.getEmpTypeDis()!=null && empReportTo.getEmpTypeDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getEmployeeType()!=null){
					row.createCell((short)empReportTo.getEmpTypePos()).setCellValue(empTO.getEmployeeType());
				}
				if(empReportTo.getDojDis()!=null && empReportTo.getDojDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getDateOfJoining()!=null){
					row.createCell((short)empReportTo.getDojPos()).setCellValue(empTO.getDateOfJoining());
				}
				if(empReportTo.getRejoinDis()!=null && empReportTo.getRejoinDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getRejoinDate()!=null){
					row.createCell((short)empReportTo.getRejoinPos()).setCellValue(empTO.getRejoinDate());
				}
				if(empReportTo.getDateOfRetDis()!=null && empReportTo.getDateOfRetDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getRetirementDate()!=null){
					row.createCell((short)empReportTo.getDateOfRetPos()).setCellValue(empTO.getRetirementDate());
				}
				if(empReportTo.getAppointmentLetterDateDis()!=null && empReportTo.getAppointmentLetterDateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getAppointmentLetterDate()!=null){
					row.createCell((short)empReportTo.getAppointmentLetterDatePos()).setCellValue(empTO.getAppointmentLetterDate());
				}
				if(empReportTo.getReferenceNumberForAppointmentDis()!=null && empReportTo.getReferenceNumberForAppointmentDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getReferenceNumberForAppointment()!=null){
					row.createCell((short)empReportTo.getReferenceNumberForAppointmentPos()).setCellValue(empTO.getReferenceNumberForAppointment());
				}
				if(empReportTo.getStrDetailsDis()!=null && empReportTo.getStrDetailsDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getStream()!=null){
					row.createCell((short)empReportTo.getStrDetailsPos()).setCellValue(empTO.getStream());
				}
				if(empReportTo.getActiveDis()!=null && empReportTo.getActiveDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getActive()!=null){
					row.createCell((short)empReportTo.getActivePOs()).setCellValue(empTO.getActive());
				}
				if(empReportTo.getWorkLocdis()!=null && empReportTo.getWorkLocdis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getWorkLocationName()!=null){
					row.createCell((short)empReportTo.getWorkLocPos()).setCellValue(empTO.getWorkLocationName());
				}
				if(empReportTo.getDeputationToDepartmentDis()!=null && empReportTo.getDeputationToDepartmentDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getDeputaionToDepartment()!=null){
					row.createCell((short)empReportTo.getDeputationToDepartmentPos()).setCellValue(empTO.getDeputaionToDepartment());
				}
				if(empReportTo.getReportTODis()!=null && empReportTo.getReportTODis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getReportToName()!=null){
					row.createCell((short)empReportTo.getReportTOPos()).setCellValue(empTO.getReportToName());
				}
				if(empReportTo.getTitledis()!=null && empReportTo.getTitledis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getTitle()!=null){
					row.createCell((short)empReportTo.getTitlePos()).setCellValue(empTO.getTitle());
				}
				if(empReportTo.getTotExpYearsDis()!=null && empReportTo.getTotExpYearsDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getTotalExpYears()!=null){
					row.createCell((short)empReportTo.getTotExpYearsPos()).setCellValue(empTO.getTotalExpYears());
				}
				if(empReportTo.getTotExpMonDis()!=null && empReportTo.getTotExpMonDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getTotalExpMonths()!=null){
					row.createCell((short)empReportTo.getTotExpMonPos()).setCellValue(empTO.getTotalExpMonths());
				}
				if(empReportTo.getRelExpYearsDis()!=null && empReportTo.getRelExpYearsDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getRelevantExpYears()!=null){
					row.createCell((short)empReportTo.getRelExpYearsPos()).setCellValue(empTO.getRelevantExpYears());
				}
				if(empReportTo.getRelExpMonDis()!=null && empReportTo.getRelExpMonDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getRelevantExpMonths()!=null){
					row.createCell((short)empReportTo.getRelExpMonPos()).setCellValue(empTO.getRelevantExpMonths());
				}
				if(empReportTo.getSubjectAreaDis()!=null && empReportTo.getSubjectAreaDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getSubjectArea()!=null){
					row.createCell((short)empReportTo.getSubjectAreaPOs()).setCellValue(empTO.getSubjectArea());
				}
				if(empReportTo.getQuaLevelDis()!=null && empReportTo.getQuaLevelDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getQualificationLevel()!=null){
					row.createCell((short)empReportTo.getGuaLevelPos()).setCellValue(empTO.getQualificationLevel());
				}
				if(empReportTo.getHighestQuaDis()!=null && empReportTo.getHighestQuaDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getHighestQualification()!=null){
					row.createCell((short)empReportTo.getHighestQuaPos()).setCellValue(empTO.getHighestQualification());
				}
				if(empReportTo.getScaleDis()!=null && empReportTo.getScaleDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getScale()!=null){
					row.createCell((short)empReportTo.getScalePos()).setCellValue(empTO.getScale());
				}
				if(empReportTo.getEmerNameDis()!=null && empReportTo.getEmerNameDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getEmergencyContName()!=null){
					row.createCell((short)empReportTo.getEmerNamePos()).setCellValue(empTO.getEmergencyContName());
				}
				if(empReportTo.getEmerReldis()!=null && empReportTo.getEmerReldis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getRelationship()!=null){
					row.createCell((short)empReportTo.getEmerRelPos()).setCellValue(empTO.getRelationship());
				}
				if(empReportTo.getEmergHomeTelDis()!=null && empReportTo.getEmergHomeTelDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getEmergencyHomeTelephone()!=null){
					row.createCell((short)empReportTo.getEmergHomeTelPos()).setCellValue(empTO.getEmergencyHomeTelephone());
				}
				if(empReportTo.getEmergMobileDis()!=null && empReportTo.getEmergMobileDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getEmergencyMobile()!=null){
					row.createCell((short)empReportTo.getEmergMobilePos()).setCellValue(empTO.getEmergencyMobile());
				}
				if(empReportTo.getEmergWorkTelDis()!=null && empReportTo.getEmergWorkTelDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getEmergencyWorkTelephone()!=null){
					row.createCell((short)empReportTo.getEmergWorkTelPos()).setCellValue(empTO.getEmergencyWorkTelephone());
				}
				if(empReportTo.getPassPortNoDis()!=null && empReportTo.getPassPortNoDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getPassPortNo()!=null){
					row.createCell((short)empReportTo.getPassPortNoPos()).setCellValue(empTO.getPassPortNo());
				}
				if(empReportTo.getPassPortIssueDateDis()!=null && empReportTo.getPassPortIssueDateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getPassPortIssuedDate()!=null){
					row.createCell((short)empReportTo.getPassPortIssueDatePos()).setCellValue(empTO.getPassPortIssuedDate());
				}
				if(empReportTo.getPassPortStatusDis()!=null && empReportTo.getPassPortStatusDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getPassPortStatus()!=null){
					row.createCell((short)empReportTo.getPassPortStatusPos()).setCellValue(empTO.getPassPortStatus());
				}
				if(empReportTo.getPassPortDOEDis()!=null && empReportTo.getPassPortDOEDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getPassPortDOE()!=null){
					row.createCell((short)empReportTo.getPassPortDOEPos()).setCellValue(empTO.getPassPortDOE());
				}
				if(empReportTo.getPassPortReviewStatusDis()!=null && empReportTo.getPassPortReviewStatusDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getPassPortDOE()!=null){
					row.createCell((short)empReportTo.getPassPortReviewStatusPos()).setCellValue(empTO.getPassPortReviewComments());
				}
				if(empReportTo.getPassPortCommentsDis()!=null && empReportTo.getPassPortCommentsDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getPassPortComments()!=null){
					row.createCell((short)empReportTo.getPassPortCommentsPos()).setCellValue(empTO.getPassPortComments());
				}
				if(empReportTo.getPassPortCountryDis()!=null && empReportTo.getPassPortCountryDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getPassPortCItizenShip()!=null){
					row.createCell((short)empReportTo.getPassPortCountryPos()).setCellValue(empTO.getPassPortCItizenShip());
				}
				if(empReportTo.getVisaNoDis()!=null && empReportTo.getVisaNoDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getVisaNo()!=null){
					row.createCell((short)empReportTo.getVisaNoPos()).setCellValue(empTO.getVisaNo());
				}
				if(empReportTo.getVisaIssueDateDis()!=null && empReportTo.getVisaIssueDateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getVisaIssuedDate()!=null){
					row.createCell((short)empReportTo.getVisaIssueDatePos()).setCellValue(empTO.getVisaIssuedDate());
				}
				if(empReportTo.getVisaStatusDis()!=null && empReportTo.getVisaStatusDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getVisaStatus()!=null){
					row.createCell((short)empReportTo.getVisaStatusPos()).setCellValue(empTO.getVisaStatus());
				}
				if(empReportTo.getVisaDOEDis()!=null && empReportTo.getVisaDOEDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getVisaDoe()!=null){
					row.createCell((short)empReportTo.getVisaDOEPos()).setCellValue(empTO.getVisaDoe());
				}
				if(empReportTo.getVisaReviewStatusDis()!=null && empReportTo.getVisaReviewStatusDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getVisaReviewComments()!=null){
					row.createCell((short)empReportTo.getVisaReviewStatusPos()).setCellValue(empTO.getVisaReviewComments());
				}
				if(empReportTo.getVisaCommentsDis()!=null && empReportTo.getVisaCommentsDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getVisaComments()!=null){
					row.createCell((short)empReportTo.getVisaCommentsPos()).setCellValue(empTO.getVisaComments());
				}
				if(empReportTo.getVisaCountryDis()!=null && empReportTo.getVisaCountryDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getVisaCitizenShip()!=null){
					row.createCell((short)empReportTo.getVisaCountryPos()).setCellValue(empTO.getVisaCitizenShip());
				}
				if(empReportTo.getDateOfResignationDis()!=null && empReportTo.getDateOfResignationDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getDateOfResignation()!=null){
					row.createCell((short)empReportTo.getDateOfResignationPos()).setCellValue(empTO.getDateOfResignation());
				}
				if(empReportTo.getReasonOfLeavingDis()!=null && empReportTo.getReasonOfLeavingDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getReasonOfLeaving()!=null){
					row.createCell((short)empReportTo.getReasonOfLeavingPos()).setCellValue(empTO.getReasonOfLeaving());
				}
				if(empReportTo.getDateOfLeavingDis()!=null && empReportTo.getDateOfLeavingDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getDateOfLeaving()!=null){
					row.createCell((short)empReportTo.getDateOfLeavingPos()).setCellValue(empTO.getDateOfLeaving());
				}
				if(empReportTo.getReleavingOrderDateDis()!=null && empReportTo.getReleavingOrderDateDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getReleavingOrderDate()!=null){
					row.createCell((short)empReportTo.getReleavingOrderDatePos()).setCellValue(empTO.getReleavingOrderDate());
				}
				if(empReportTo.getReferenceNumberForReleavingDis()!=null && empReportTo.getReferenceNumberForReleavingDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getReferenceNumberForReleaving()!=null){
					row.createCell((short)empReportTo.getReferenceNumberForReleavingPos()).setCellValue(empTO.getReferenceNumberForReleaving());
				}
				if(empReportTo.getTeachingStaffDis()!=null && empReportTo.getTeachingStaffDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getTeachingStaff()!=null){
					row.createCell((short)empReportTo.getTeachingStaffPos()).setCellValue(empTO.getTeachingStaff());
				}
				if(empReportTo.getEmpPhotoDis()!=null && empReportTo.getEmpPhotoDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY)&& empTO.getPhoto()!=null){
					row.createCell((short)empReportTo.getEmpPhotoPos()).setCellValue(empTO.getPhoto());
				}
				if(empReportTo.getUserNameDis()!=null && empReportTo.getUserNameDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getUserName()!=null){
					row.createCell((short)empReportTo.getUserNamePos()).setCellValue(empTO.getUserName());
				}
				if(empReportTo.getBooksDis()!=null && empReportTo.getBooksDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getBooks()!=null){
					row.createCell((short)empReportTo.getBooksPos()).setCellValue(empTO.getBooks());
				}
				if(empReportTo.getNoOfPublicationsNotReferedDis()!=null && empReportTo.getNoOfPublicationsNotReferedDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getNoOfPublicationsNotRefered()!=null){
					row.createCell((short)empReportTo.getNoOfPublicationsNotReferedPos()).setCellValue(empTO.getNoOfPublicationsNotRefered());
				}
				if(empReportTo.getNoOfPublicationsReferedDis()!=null && empReportTo.getNoOfPublicationsReferedDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getNoOfPublicationsRefered()!=null){
					row.createCell((short)empReportTo.getNoOfPublicationsReferedPos()).setCellValue(empTO.getNoOfPublicationsRefered());
				}
				if(empReportTo.getPayScaleIdDis()!=null && empReportTo.getPayScaleIdDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getPayScaleId()!=null){
					row.createCell((short)empReportTo.getPayScaleIdPos()).setCellValue(empTO.getPayScaleId());
				}
//				if(empReportTo.getPassWordDis()!=null && empReportTo.getPassWordDis().equalsIgnoreCase(EmployeeReportHelper.DISPLAY) && empTO.getPassword()!=null){
//					row.createCell((short)empReportTo.getPassWordPos()).setCellValue(empTO.getPassword());
//				}
				if(empReportTo.getExperienceInDis()!=null && empReportTo.getExperienceInDis().equals(EmployeeReportHelper.DISPLAY) && empTO.getExperienceInYearsAndMonths()!=null){
					row.createCell((short)empReportTo.getExperienceInPos()).setCellValue(empTO.getExperienceInYearsAndMonths());
				}
				if(empReportTo.getTotalCurrentExperienceDis()!=null && empReportTo.getTotalCurrentExperienceDis().equals(EmployeeReportHelper.DISPLAY) && empTO.getTotalCurrentExpYearsAndMonths()!=null){
					row.createCell((short)empReportTo.getTotalCurrentExperiencePos()).setCellValue(empTO.getTotalCurrentExpYearsAndMonths());
				}
				if(empReportTo.getExtentionNoDis()!=null && empReportTo.getExtentionNoDis().equals(EmployeeReportHelper.DISPLAY) && empTO.getExtensionNumber()!=null){
					row.createCell((short)empReportTo.getExtentionNoPos()).setCellValue(empTO.getExtensionNumber());
				}
			}
			bos=new ByteArrayOutputStream();
			wb.write(bos);
			HttpSession session = request.getSession();
			session.setAttribute(CMSConstants.EXCEL_BYTES,bos.toByteArray());
			isUpdated=true;
			fos.flush();
			fos.close();
			
		}catch (Exception e) {
			//throw new ApplicationException();
			// TODO: handle exception
			
		}
	}
	return isUpdated;
}

/**
 * @param employeeReportForm
 * @return
 */
	public EmployeeReportTO getSelectedColumns(
			EmployeeReportForm employeeReportForm) {
		EmployeeReportTO reportTO = new EmployeeReportTO();

		List<String> selectedColumnsList = new ArrayList<String>();
		String[] selected = employeeReportForm.getSelectedColumnsArray();
		for (int i = 0; i < selected.length; i++) {
			selectedColumnsList.add(selected[i]);
		}
		if (selectedColumnsList != null) {
			Iterator<String> iterator = selectedColumnsList.iterator();
			int count = 0;
			while (iterator.hasNext()) {

				String columnName = iterator.next();
				if (columnName.equalsIgnoreCase("EmpId")) {
					reportTO.setEmpIdDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setEmpIdPoistion((short) count++);
				}
				if (columnName.equalsIgnoreCase("Name")) {
					reportTO.setNameDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setNamePoistion((short) count++);
				}
				if (columnName.equalsIgnoreCase("Designation")) {
					reportTO .setDesignationDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setDesignationPoistion((short) count++);
				}
				if (columnName.equalsIgnoreCase("Department")) {
					reportTO.setDepartmentDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setDepartmentPoistion((short) count++);
				}
				if (columnName.equalsIgnoreCase("UID")) {
					reportTO.setUidDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setUidPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Nationality")) {
					reportTO .setNationalityDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setNationalityPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Gender")) {
					reportTO.setGenderDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setGenderPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Marital Status")) {
					reportTO .setMatrialStatusDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setMatrialStatusPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Date of birth")) {
					reportTO.setDobDiaplay(EmployeeReportHelper.DISPLAY);
					reportTO.setDobPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Age")) {
					reportTO.setAgeDis(EmployeeReportHelper.DISPLAY);
					reportTO.setAgePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Blood Group")) {
					reportTO.setBloodGroupDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setBloodGrouopPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Religion")) {
					reportTO.setReligionDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setReligionPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Pan No")) {
					reportTO.setPanNoDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setPanNoPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Official Email Id")) {
					reportTO .setOfficialEmailDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setOfficialEmailPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Personal Email Id")) {
					reportTO.setEmailDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setEmailPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Reservation Category")) {
					reportTO.setResCatDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setResCatPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("MobileNo")) {
					reportTO.setMobileDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setMobilePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Bank Account No")) {
					reportTO.setBankAccNoDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setBankAcccNoPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("PF Account No")) {
					reportTO.setPfNoDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setPfNoPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Four Wheeler No")) {
					reportTO
							.setFourWheelerDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setFourWheelerPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Two Wheeler No")) {
					reportTO.setTwoWheelerDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setTwoWheelerPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Smart Card No")) {
					reportTO.setSmartCardDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setSmartCardPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Home Telephone")) {
					reportTO
							.setHomeTelephoneDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setHomeTelephonePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Work Telephone")) {
					reportTO
							.setWorkTelephoneDisplay(EmployeeReportHelper.DISPLAY);
					reportTO.setWorkTelephonePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current Address Line1")) {
					reportTO
							.setCurrentAddLine1Display(EmployeeReportHelper.DISPLAY);
					reportTO.setCurrentAddLine1Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current Address Line2")) {
					reportTO
							.setCurrentAddLine2Display(EmployeeReportHelper.DISPLAY);
					reportTO.setCurrentAddLine2Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current City")) {
					reportTO.setCurrentCityDis(EmployeeReportHelper.DISPLAY);
					reportTO.setCurentCitypos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current PinCode")) {
					reportTO.setCurrentPincodeDis(EmployeeReportHelper.DISPLAY);
					reportTO.setCurentPinCodePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current Country")) {
					reportTO.setCurrentCountrydis(EmployeeReportHelper.DISPLAY);
					reportTO.setCurrentCounPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current State")) {
					reportTO.setCurrentStateDis(EmployeeReportHelper.DISPLAY);
					reportTO.setCurrentStatePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent Address Line1")) {
					reportTO.setPerAddLine1Dis(EmployeeReportHelper.DISPLAY);
					reportTO.setPerAddLine1Pos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent Address Line2")) {
					reportTO.setPermAddLine2Dis(EmployeeReportHelper.DISPLAY);
					reportTO.setPermAddLine2Pos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent City")) {
					reportTO.setPermCityDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPermCityPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent PinCode")) {
					reportTO.setPermPinCodeDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPermPinCodePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent Country")) {
					reportTO.setPermContryDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPermCountryPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent State")) {
					reportTO.setPermStateDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPermStatePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Photo")) {
					reportTO.setEmpPhotoDis(EmployeeReportHelper.DISPLAY);
					reportTO.setEmpPhotoPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Employee Type")) {
					reportTO.setEmpTypeDis(EmployeeReportHelper.DISPLAY);
					reportTO.setEmpTypePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Joined Date")) {
					reportTO.setDojDis(EmployeeReportHelper.DISPLAY);
					reportTO.setDojPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Rejoin Date")) {
					reportTO.setRejoinDis(EmployeeReportHelper.DISPLAY);
					reportTO.setRejoinPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Date of Retirement")) {
					reportTO.setDateOfRetDis(EmployeeReportHelper.DISPLAY);
					reportTO.setDateOfRetPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Appointment Letter Date")) {
					reportTO.setAppointmentLetterDateDis(EmployeeReportHelper.DISPLAY);
					reportTO.setAppointmentLetterDatePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Reference Number For Appointment")) {
					reportTO.setReferenceNumberForAppointmentDis(EmployeeReportHelper.DISPLAY);
					reportTO.setReferenceNumberForAppointmentPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Active")) {
					reportTO.setActiveDis(EmployeeReportHelper.DISPLAY);
					reportTO.setActivePOs((short) count++);
				}
				if (columnName.equalsIgnoreCase("Stream Details")) {
					reportTO.setStrDetailsDis(EmployeeReportHelper.DISPLAY);
					reportTO.setStrDetailsPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Work Location")) {
					reportTO.setWorkLocdis(EmployeeReportHelper.DISPLAY);
					reportTO.setWorkLocPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Deputation To Department")) {
					reportTO.setDeputationToDepartmentDis(EmployeeReportHelper.DISPLAY);
					reportTO.setDeputationToDepartmentPos((short) count++);
				}
//				if (columnName.equalsIgnoreCase("Grade")) {
//					reportTO.setGradeDis(EmployeeReportHelper.DISPLAY);
//					reportTO.setGradePos((short) count++);
//				}
				if (columnName.equalsIgnoreCase("Title")) {
					reportTO.setTitledis(EmployeeReportHelper.DISPLAY);
					reportTO.setTitlePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Report To")) {
					reportTO.setReportTODis(EmployeeReportHelper.DISPLAY);
					reportTO.setReportTOPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Total Experience Years")) {
					reportTO.setTotExpYearsDis(EmployeeReportHelper.DISPLAY);
					reportTO.setTotExpYearsPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Total Experience Months")) {
					reportTO.setTotExpMonDis(EmployeeReportHelper.DISPLAY);
					reportTO.setTotExpMonPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Recognised Experience Years")) {
					reportTO.setRelExpYearsDis(EmployeeReportHelper.DISPLAY);
					reportTO.setRelExpYearsPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Recognised Experience Months")) {
					reportTO.setRelExpMonDis(EmployeeReportHelper.DISPLAY);
					reportTO.setRelExpMonPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Subject Area")) {
					reportTO.setSubjectAreaDis(EmployeeReportHelper.DISPLAY);
					reportTO.setSubjectAreaPOs((short) count++);
				}
				if (columnName.equalsIgnoreCase("Qualification Level")) {
					reportTO.setQuaLevelDis(EmployeeReportHelper.DISPLAY);
					reportTO.setGuaLevelPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Highest Qualification")) {
					reportTO.setHighestQuaDis(EmployeeReportHelper.DISPLAY);
					reportTO.setHighestQuaPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Scale")) {
					reportTO.setScaleDis(EmployeeReportHelper.DISPLAY);
					reportTO.setScalePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Teaching Staff")) {
					reportTO.setTeachingStaffDis(EmployeeReportHelper.DISPLAY);
					reportTO.setTeachingStaffPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Emergency Contact Name")) {
					reportTO.setEmerNameDis(EmployeeReportHelper.DISPLAY);
					reportTO.setEmerNamePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("RelationShip")) {
					reportTO.setEmerReldis(EmployeeReportHelper.DISPLAY);
					reportTO.setEmerRelPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Emergency Mobile")) {
					reportTO.setEmergMobileDis(EmployeeReportHelper.DISPLAY);
					reportTO.setEmergMobilePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Emergency HomeTelephone")) {
					reportTO.setEmergHomeTelDis(EmployeeReportHelper.DISPLAY);
					reportTO.setEmergHomeTelPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Emergency WorkTelephone")) {
					reportTO.setEmergWorkTelDis(EmployeeReportHelper.DISPLAY);
					reportTO.setEmergWorkTelPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Passport No")) {
					reportTO.setPassPortNoDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPassPortNoPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Passport Status")) {
					reportTO.setPassPortStatusDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPassPortStatusPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Passport ReviewStatus")) {
					reportTO
							.setPassPortReviewStatusDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPassPortReviewStatusPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Passport Issue Date")) {
					reportTO
							.setPassPortIssueDateDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPassPortIssueDatePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Passport Date_of_expiry")) {
					reportTO.setPassPortDOEDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPassPortDOEPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Passport Comments")) {
					reportTO
							.setPassPortCommentsDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPassPortCommentsPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Passport Country")) {
					reportTO
							.setPassPortCountryDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPassPortCountryPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Visa No")) {
					reportTO.setVisaNoDis(EmployeeReportHelper.DISPLAY);
					reportTO.setVisaNoPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Visa Status")) {
					reportTO.setVisaStatusDis(EmployeeReportHelper.DISPLAY);
					reportTO.setVisaStatusPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Visa ReviewStatus")) {
					reportTO .setVisaReviewStatusDis(EmployeeReportHelper.DISPLAY);
					reportTO.setVisaReviewStatusPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Visa Issue_date")) {
					reportTO.setVisaIssueDateDis(EmployeeReportHelper.DISPLAY);
					reportTO.setVisaIssueDatePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Visa Date_of_expiry")) {
					reportTO.setVisaDOEDis(EmployeeReportHelper.DISPLAY);
					reportTO.setVisaDOEPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Visa Comments")) {
					reportTO.setVisaCommentsDis(EmployeeReportHelper.DISPLAY);
					reportTO.setVisaCommentsPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Visa Country")) {
					reportTO.setVisaCountryDis(EmployeeReportHelper.DISPLAY);
					reportTO.setVisaCountryPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Date of Resignation")) {
					reportTO
							.setDateOfResignationDis(EmployeeReportHelper.DISPLAY);
					reportTO.setDateOfResignationPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Date of Leaving")) {
					reportTO.setDateOfLeavingDis(EmployeeReportHelper.DISPLAY);
					reportTO.setDateOfLeavingPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Reason of Leaving")) {
					reportTO.setReasonOfLeavingDis(EmployeeReportHelper.DISPLAY);
					reportTO.setReasonOfLeavingPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Releaving Order Date")) {
					reportTO.setReleavingOrderDateDis(EmployeeReportHelper.DISPLAY);
					reportTO.setReleavingOrderDatePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Reference Number For Releaving")) {
					reportTO.setReferenceNumberForReleavingDis(EmployeeReportHelper.DISPLAY);
					reportTO.setReferenceNumberForReleavingPos((short) count++);
				}
				if(columnName.equalsIgnoreCase("UserName")){
					reportTO.setUserNameDis(EmployeeReportHelper.DISPLAY);
					reportTO.setUserNamePos((short) count++);
				}
				if(columnName.equalsIgnoreCase("No. of Books with ISBN")){
					reportTO.setBooksDis(EmployeeReportHelper.DISPLAY);
					reportTO.setBooksPos((short) count++);
				}
				if(columnName.equalsIgnoreCase("Publications Non Refereed")){
					reportTO.setNoOfPublicationsNotReferedDis(EmployeeReportHelper.DISPLAY);
					reportTO.setNoOfPublicationsNotReferedPos((short) count++);
				}
				if(columnName.equalsIgnoreCase("No. of Publications Refereed")){
					reportTO.setNoOfPublicationsReferedDis(EmployeeReportHelper.DISPLAY);
					reportTO.setNoOfPublicationsReferedPos((short) count++);
				}
				if(columnName.equalsIgnoreCase("Grade")){
					reportTO.setPayScaleIdDis(EmployeeReportHelper.DISPLAY);
					reportTO.setPayScaleIdPos((short) count++);
				}
//				if(columnName.equalsIgnoreCase("PassWord")){
//					reportTO.setPassWordDis(EmployeeReportHelper.DISPLAY);
//					reportTO.setPassWordPos((short) count++);
//				}
				if(CMSConstants.LINK_FOR_CJC){
					if(columnName.equalsIgnoreCase("Experience in CJC")){
						reportTO.setExperienceInDis(EmployeeReportHelper.DISPLAY);
						reportTO.setExperienceInPos((short) count++);
					}
				}else{
					if(columnName.equalsIgnoreCase("Experience in CU")){
						reportTO.setExperienceInDis(EmployeeReportHelper.DISPLAY);
						reportTO.setExperienceInPos((short) count++);
					}
				}
				if(columnName.equalsIgnoreCase("Total Current Experience")){
					reportTO.setTotalCurrentExperienceDis(EmployeeReportHelper.DISPLAY);
					reportTO.setTotalCurrentExperiencePos((short) count++);
				}
				if(columnName.equalsIgnoreCase("Extension Number")){
					reportTO.setExtentionNoDis(EmployeeReportHelper.DISPLAY);
					reportTO.setExtentionNoPos((short) count++);
				}
			}
		}
		return reportTO;
	}
	public String AgeCalculation(Date dateOfBirth)throws Exception
	 {
			
		  Calendar cal1 = new GregorianCalendar();
	      Calendar cal2 = new GregorianCalendar();
	      String Age="";
	      int age = 0;
	      int factor = 0; 
	      Date today= new Date();
	     if(dateOfBirth!=null)
	     {
	    //  Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(dateOfBirth);
	      
	   //   Date date2 = new SimpleDateFormat("MM-dd-yyyy").parse(String.valueOf(today));
	      
	      cal1.setTime(dateOfBirth);
	      cal2.setTime(today);
	      if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
	            factor = -1; 
	      }
	      age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
	      Age=String.valueOf(age);
	     }
	     return Age;
	 }
}
