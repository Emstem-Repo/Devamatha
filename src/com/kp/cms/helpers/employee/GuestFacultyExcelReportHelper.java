package com.kp.cms.helpers.employee;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.employee.GuestPreviousChristWorkDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.GuestFacultyExcelReportForm;
import com.kp.cms.to.employee.EmployeeReportTO;
import com.kp.cms.to.employee.GuestFacultyTO;
import com.kp.cms.to.employee.GuestPreviousChristWorkDetailsTO;
import com.kp.cms.transactions.admission.ICandidateSearchTxnImpl;
import com.kp.cms.transactionsimpl.admission.CandidateSearchTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class GuestFacultyExcelReportHelper {
	private static volatile GuestFacultyExcelReportHelper guestFacultyExcelReportHelper = null;
	private static final String DISPLAY = "display";
	public static GuestFacultyExcelReportHelper getInstance(){
		if(guestFacultyExcelReportHelper == null){
			guestFacultyExcelReportHelper = new GuestFacultyExcelReportHelper();
			return guestFacultyExcelReportHelper;
		}
		return guestFacultyExcelReportHelper;
	}
	public StringBuffer getSelectionSearchCriteria(GuestFacultyExcelReportForm guestFacultyExcelReportForm,
			int streamId, int departmentId, int designationId,int workLocationId) throws Exception{
		StringBuffer stringBuffer = new StringBuffer("from GuestFaculty e  where e.isActive = 1 ");
		if(streamId > 0){
			stringBuffer = stringBuffer .append(" and e.streamId.id='"+ streamId+"'");
		}
		if(departmentId > 0){
			stringBuffer = stringBuffer .append(" and e.department.id='"+ departmentId+"'");
		}
		if(designationId > 0){
			stringBuffer = stringBuffer .append(" and e.designation.id='"+ designationId+"'");
		}
		if(workLocationId > 0){
			stringBuffer = stringBuffer.append(" and e.workLocationId.id='"+ workLocationId+"'");
		}
		if(guestFacultyExcelReportForm.getActive() !=null)
		{
			if (guestFacultyExcelReportForm.getActive().equals("1")) {
				stringBuffer = stringBuffer.append(" and e.active= 1 ");
			}else if (guestFacultyExcelReportForm.getActive().equals("0")) {
				stringBuffer = stringBuffer.append(" and e.active= 0 ");
			}
		}
		return stringBuffer;
}
	public List<GuestFacultyTO> convertEmployeeBoTOTo(List<GuestFaculty> guestFaculties) throws Exception{
		List<GuestFacultyTO> guestFacultyTOs = new ArrayList<GuestFacultyTO>();
		if(guestFaculties!=null && !guestFaculties.isEmpty()){
			Iterator<GuestFaculty> iterator=guestFaculties.iterator();
			while (iterator.hasNext()) {
				GuestFaculty guestFaculty = (GuestFaculty) iterator.next();
				GuestFacultyTO guestFacultyTO = new GuestFacultyTO();
				guestFacultyTO.setId(guestFaculty.getId());
				if(guestFaculty.getFirstName()!=null &&  !guestFaculty.getFirstName().isEmpty()){
						guestFacultyTO.setFirstName(guestFaculty.getFirstName());
				}
				if(guestFaculty.getDob()!=null){
					guestFacultyTO.setDob(guestFaculty.getDob().toString());
				}
				if(guestFaculty.getGender()!=null && !guestFaculty.getGender().isEmpty()){
						guestFacultyTO.setGender(guestFaculty.getGender());
					
				}
				if(guestFaculty.getDepartment()!=null){
					if(guestFaculty.getDepartment().getId()!=0){
						guestFacultyTO.setDepartmentId(String.valueOf(guestFaculty.getDepartment().getId()));
					}
					if(guestFaculty.getDepartment().getName()!=null){
						if(!guestFaculty.getDepartment().getName().isEmpty()){
							guestFacultyTO.setDepartmentName(guestFaculty.getDepartment().getName());
						}
					}
				}
				if(guestFaculty.getWorkLocationId()!=null){
					if(guestFaculty.getWorkLocationId().getId()!=0){
						guestFacultyTO.setWorkLocationId(String.valueOf(guestFaculty.getWorkLocationId().getId()));
					}
					if(guestFaculty.getWorkLocationId().getName()!=null){
						if(!guestFaculty.getWorkLocationId().getName().isEmpty()){
							guestFacultyTO.setWorkLocationName(guestFaculty.getWorkLocationId().getName());
						}
					}
				}
				guestFacultyTOs.add(guestFacultyTO);
			}
		}
		return guestFacultyTOs;
}
	public List<GuestFacultyTO> convertBOToExcel(List<GuestFaculty> guestFaculties, GuestFacultyExcelReportForm guestFacultyExcelReportForm)throws Exception {
		int maxListSize=0;
		List<GuestFacultyTO> list=new ArrayList<GuestFacultyTO>();
		if(guestFaculties!=null && !guestFaculties.isEmpty()){
			Iterator<GuestFaculty> iterator=guestFaculties.iterator();
			while (iterator.hasNext()) {
				GuestFaculty guestFaculty = (GuestFaculty) iterator.next();
				GuestFacultyTO guestFacultyTO=new GuestFacultyTO();
				
				if(guestFaculty.getTeachingStaff()!=null){
					if(guestFaculty.getTeachingStaff()){
						guestFacultyTO.setTeachingStaff("Yes");
					}else{
						guestFacultyTO.setTeachingStaff("No");
					}
				}
				//start personal data
				if(guestFaculty.getFirstName()!=null && !guestFaculty.getFirstName().isEmpty()){
					guestFacultyTO.setFirstName(guestFaculty.getFirstName());
				}
				if(guestFaculty.getUid()!=null && guestFaculty.getUid().isEmpty()){
					guestFacultyTO.setUid(guestFaculty.getUid());
				}
				if(guestFaculty.getStaffId()!=null && !guestFaculty.getStaffId().isEmpty()){
					guestFacultyTO.setStaffId(guestFaculty.getStaffId());
				}
				if(guestFaculty.getGender()!=null && !guestFaculty.getGender().isEmpty()){
					guestFacultyTO.setGender(guestFaculty.getGender());
				}
				if(guestFaculty.getNationality()!=null && guestFaculty.getNationality().getName()!=null && !guestFaculty.getNationality().getName().isEmpty()){
					guestFacultyTO.setNationalityName(guestFaculty.getNationality().getName());
				}
				if(guestFaculty.getMaritalStatus()!=null && !guestFaculty.getMaritalStatus().isEmpty()){
					guestFacultyTO.setMaritalStatus(guestFaculty.getMaritalStatus());
				}
				if(guestFaculty.getDob()!=null){
					guestFacultyTO.setDob(CommonUtil.formatDates(guestFaculty.getDob()));
				}
				if(guestFaculty.getBloodGroup()!=null && !guestFaculty.getBloodGroup().isEmpty()){
					guestFacultyTO.setBloodGroup(guestFaculty.getBloodGroup());
				}
				if(guestFaculty.getReligionId()!=null && guestFaculty.getReligionId().getName()!=null && !guestFaculty.getReligionId().getName().isEmpty()){
					guestFacultyTO.setReligion(guestFaculty.getReligionId().getName());
				}
				if(guestFaculty.getPanNo()!=null && !guestFaculty.getPanNo().isEmpty()){
					guestFacultyTO.setPanNo(guestFaculty.getPanNo());
				}
				if(guestFaculty.getWorkEmail()!=null && !guestFaculty.getWorkEmail().isEmpty()){
					guestFacultyTO.setWorkEmail(guestFaculty.getWorkEmail());
				}
				if(guestFaculty.getEmail()!=null && !guestFaculty.getEmail().isEmpty()){
					guestFacultyTO.setEmail(guestFaculty.getEmail());
				}
				if(guestFaculty.getReservationCategory()!=null && !guestFaculty.getReservationCategory().isEmpty()){
					guestFacultyTO.setReservationCatagory(guestFaculty.getReservationCategory());
				}
				if(guestFaculty.getCurrentAddressMobile1()!=null && !guestFaculty.getCurrentAddressMobile1().isEmpty()){
					guestFacultyTO.setCurrentAddressMobile1(guestFaculty.getCurrentAddressMobile1());
				}
				if(guestFaculty.getBankAccNo()!=null && !guestFaculty.getBankAccNo().isEmpty()){
					guestFacultyTO.setBankAccNo(guestFaculty.getBankAccNo());
				}
				if(guestFaculty.getPfNo()!=null && !guestFaculty.getPfNo().isEmpty()){
					guestFacultyTO.setPbankCCode(guestFaculty.getPfNo());
				}
				if(guestFaculty.getEmergencyContName()!=null && !guestFaculty.getEmergencyContName().isEmpty()){
					guestFacultyTO.setEmergencyContName(guestFaculty.getEmergencyContName());
				}
				if(guestFaculty.getEmContactRelationship()!=null && !guestFaculty.getEmContactRelationship().isEmpty()){
					guestFacultyTO.setRelationship(guestFaculty.getEmContactRelationship());
				}
				if(guestFaculty.getEmergencyMobile()!=null && !guestFaculty.getEmergencyMobile().isEmpty()){
					guestFacultyTO.setEmergencyMobile(guestFaculty.getEmergencyMobile());
				}
				if(guestFaculty.getEmergencyHomeTelephone()!=null && !guestFaculty.getEmergencyHomeTelephone().isEmpty()){
					guestFacultyTO.setEmergencyHomeTelephone(guestFaculty.getEmergencyHomeTelephone());
				}
				if(guestFaculty.getEmergencyWorkTelephone()!=null && !guestFaculty.getEmergencyWorkTelephone().isEmpty()){
					guestFacultyTO.setEmergencyWorkTelephone(guestFaculty.getEmergencyWorkTelephone());
				}
				if(guestFaculty.getFourWheelerNo()!=null && !guestFaculty.getFourWheelerNo().isEmpty()){
					guestFacultyTO.setFourWheelerNo(guestFaculty.getFourWheelerNo());
				}
				if(guestFaculty.getTwoWheelerNo()!=null && !guestFaculty.getTwoWheelerNo().isEmpty()){
					guestFacultyTO.setTwoWheelerNo(guestFaculty.getTwoWheelerNo());
				}
				//end personal data
				//start current address
				if(guestFaculty.getCommunicationAddressLine1()!=null && !guestFaculty.getCommunicationAddressLine1().isEmpty()){
					guestFacultyTO.setCommunicationAddressLine1(guestFaculty.getCommunicationAddressLine1());
				}
				if(guestFaculty.getCommunicationAddressLine2()!=null && !guestFaculty.getCommunicationAddressLine2().isEmpty()){
					guestFacultyTO.setCommunicationAddressLine2(guestFaculty.getCommunicationAddressLine2());
				}
				if(guestFaculty.getCommunicationAddressCity()!=null && !guestFaculty.getCommunicationAddressCity().isEmpty()){
					guestFacultyTO.setCommunicationAddressCity(guestFaculty.getCommunicationAddressCity());
				}
				if(guestFaculty.getCommunicationAddressZip()!=null && !guestFaculty.getCommunicationAddressZip().isEmpty()){
					guestFacultyTO.setCommunicationAddressZip(guestFaculty.getCommunicationAddressZip());
				}
				if(guestFaculty.getCountryByCommunicationAddressCountryId()!=null && guestFaculty.getCountryByCommunicationAddressCountryId().getName()!=null && !guestFaculty.getCountryByCommunicationAddressCountryId().getName().isEmpty()){
					guestFacultyTO.setCommuContryName(guestFaculty.getCountryByCommunicationAddressCountryId().getName());
				}
				if(guestFaculty.getStateByCommunicationAddressStateId()!=null && guestFaculty.getStateByCommunicationAddressStateId().getName()!=null && !guestFaculty.getStateByCommunicationAddressStateId().getName().isEmpty()){
					guestFacultyTO.setCommuStateName(guestFaculty.getStateByCommunicationAddressStateId().getName());
				}
				//end current address
				//start permanent address
				if(guestFaculty.getPermanentAddressLine1()!=null &&  !guestFaculty.getPermanentAddressLine1().isEmpty()){
					guestFacultyTO.setPermanentAddressLine1(guestFaculty.getPermanentAddressLine1());
				}
				if(guestFaculty.getPermanentAddressLine2()!=null &&  !guestFaculty.getPermanentAddressLine2().isEmpty()){
					guestFacultyTO.setPermanentAddressLine2(guestFaculty.getPermanentAddressLine2());
				}
				if(guestFaculty.getPermanentAddressCity()!=null &&  !guestFaculty.getPermanentAddressCity().isEmpty()){
					guestFacultyTO.setPermanentAddressCity(guestFaculty.getPermanentAddressCity());
				}
				if(guestFaculty.getPermanentAddressZip()!=null &&  !guestFaculty.getPermanentAddressZip().isEmpty()){
					guestFacultyTO.setPermanentAddressZip(guestFaculty.getPermanentAddressZip());
				}
				if(guestFaculty.getCountryByPermanentAddressCountryId()!=null && guestFaculty.getCountryByPermanentAddressCountryId().getName()!=null && !guestFaculty.getCountryByPermanentAddressCountryId().getName().isEmpty()){
					guestFacultyTO.setPermanentCountryName(guestFaculty.getCountryByPermanentAddressCountryId().getName());
				}
				if(guestFaculty.getStateByPermanentAddressStateId()!=null && guestFaculty.getStateByPermanentAddressStateId().getName()!=null && !guestFaculty.getStateByPermanentAddressStateId().getName().isEmpty()){
					guestFacultyTO.setPermanentStateName(guestFaculty.getStateByPermanentAddressStateId().getName());
				}
				//end permanent address
				//start job
				if(guestFaculty.getActive()){
					guestFacultyTO.setActive("Yes");
				}else{
					guestFacultyTO.setActive("No");
				}
				if(guestFaculty.getStreamId()!=null && guestFaculty.getStreamId().getName()!=null && ! guestFaculty.getStreamId().getName().isEmpty()){
					guestFacultyTO.setStream( guestFaculty.getStreamId().getName());
				}
				if(guestFaculty.getWorkLocationId()!=null && guestFaculty.getWorkLocationId().getName()!=null && ! guestFaculty.getWorkLocationId().getName().isEmpty()){
					guestFacultyTO.setWorkLocationName( guestFaculty.getWorkLocationId().getName());
				}
				if(guestFaculty.getDesignation()!=null && guestFaculty.getDesignation().getName()!=null && ! guestFaculty.getDesignation().getName().isEmpty()){
					guestFacultyTO.setDesignationName( guestFaculty.getDesignation().getName());
				}
				if(guestFaculty.getDepartment()!=null && guestFaculty.getDepartment().getName()!=null && ! guestFaculty.getDepartment().getName().isEmpty()){
					guestFacultyTO.setDepartmentName( guestFaculty.getDepartment().getName());
				}
				if(guestFaculty.getSubjectSpecilization()!=null && !guestFaculty.getSubjectSpecilization().isEmpty()){
					guestFacultyTO.setSubjectSpecilization(guestFaculty.getSubjectSpecilization());
				}
				if(guestFaculty.getReferredBy()!=null && !guestFaculty.getReferredBy().isEmpty()){
					guestFacultyTO.setReferredBy(guestFaculty.getReferredBy());
				}
				if(guestFaculty.getWorkingHoursPerWeek()!=null && !guestFaculty.getWorkingHoursPerWeek().isEmpty()){
					guestFacultyTO.setWorkingHoursPerWeek(guestFaculty.getWorkingHoursPerWeek());
				}
				if(guestFaculty.getHonorariumPerHours()!=null && !guestFaculty.getHonorariumPerHours().isEmpty()){
					guestFacultyTO.setHonorariumPerHours(guestFaculty.getHonorariumPerHours());
				}
				//end job
				//start professional exp
				if(guestFaculty.getTotalExpMonths()!=null && !guestFaculty.getTotalExpMonths().isEmpty()){
					guestFacultyTO.setTotalExpMonths(guestFaculty.getTotalExpMonths());
				}
				if(guestFaculty.getTotalExpYear()!=null && !guestFaculty.getTotalExpYear().isEmpty()){
					guestFacultyTO.setTotalExpYears(guestFaculty.getTotalExpYear());
				}
				if(guestFaculty.getRelevantExpMonths()!=null && !guestFaculty.getRelevantExpMonths().isEmpty()){
					guestFacultyTO.setRelevantExpMonths(guestFaculty.getRelevantExpMonths());
				}
				if(guestFaculty.getRelevantExpYears()!=null && !guestFaculty.getRelevantExpYears().isEmpty()){
					guestFacultyTO.setRelevantExpYears(guestFaculty.getRelevantExpYears());
				}
				if(guestFaculty.getEligibilityTest()!=null && !guestFaculty.getEligibilityTest().isEmpty() && guestFaculty.getEligibilityTest().equalsIgnoreCase("OTHER")){
					guestFacultyTO.setEligibilityTest(guestFaculty.getEligibilityTest());
					if(guestFaculty.getEligibilityTestOther()!=null && !guestFaculty.getEligibilityTestOther().isEmpty()){
						guestFacultyTO.setEligibilityTestForOther(guestFaculty.getEligibilityTestOther());
					}
				}else{
					guestFacultyTO.setEligibilityTest(guestFaculty.getEligibilityTest());
				}
				//end professional exp
				//start educational details
				if(guestFaculty.getEmpQualificationLevel()!=null && guestFaculty.getEmpQualificationLevel().getName()!=null && !guestFaculty.getEmpQualificationLevel().getName().isEmpty()){
					guestFacultyTO.setQualificationLevel(guestFaculty.getEmpQualificationLevel().getName());
				}
				if(guestFaculty.getHighQualifForAlbum()!=null && !guestFaculty.getHighQualifForAlbum().isEmpty()){
					guestFacultyTO.setHighestQualification(guestFaculty.getHighQualifForAlbum());
				}
				if(guestFaculty.getNoOfPublicationsNotRefered()!=null && !guestFaculty.getNoOfPublicationsNotRefered().isEmpty()){
					guestFacultyTO.setNoOfPubNotReffered(guestFaculty.getNoOfPublicationsNotRefered());
				}
				if(guestFaculty.getNoOfPublicationsRefered()!=null && !guestFaculty.getNoOfPublicationsRefered().isEmpty()){
					guestFacultyTO.setNoOfPubReffered(guestFaculty.getNoOfPublicationsRefered());
				}
				if(guestFaculty.getBooks()!=null && !guestFaculty.getBooks().isEmpty()){
					guestFacultyTO.setBooks(guestFaculty.getBooks());
				}
				//end educational details
				//start additional job information
				
				if(guestFaculty.getPreviousChristDetails()!=null && !guestFaculty.getPreviousChristDetails().isEmpty()){
					Set<GuestPreviousChristWorkDetails> set=guestFaculty.getPreviousChristDetails();
					if(set.size()>maxListSize){
						maxListSize=set.size();
					}
					List<GuestPreviousChristWorkDetailsTO> guestPreviousChristWorkDetailsTOs=new ArrayList<GuestPreviousChristWorkDetailsTO>();
					Iterator<GuestPreviousChristWorkDetails> iterator2=set.iterator();
					while (iterator2.hasNext()) {
						GuestPreviousChristWorkDetails guestPreviousChristWorkDetails = (GuestPreviousChristWorkDetails) iterator2.next();
						GuestPreviousChristWorkDetailsTO guestPreviousChristWorkDetailsTO=new GuestPreviousChristWorkDetailsTO();
						if(guestPreviousChristWorkDetails!=null){
							if(guestPreviousChristWorkDetails.getStartDate()!=null){
								guestPreviousChristWorkDetailsTO.setStartDate(CommonUtil.formatDates(guestPreviousChristWorkDetails.getStartDate()));
							}
							if(guestPreviousChristWorkDetails.getEndDate()!=null){
								guestPreviousChristWorkDetailsTO.setEndDate(CommonUtil.formatDates(guestPreviousChristWorkDetails.getEndDate()));
							}
							if(guestPreviousChristWorkDetails.getSemester()!=null && !guestPreviousChristWorkDetails.getSemester().isEmpty()){
								guestPreviousChristWorkDetailsTO.setSemester(guestPreviousChristWorkDetails.getSemester());
							}
							if(guestPreviousChristWorkDetails.getStrmId()!=null && guestPreviousChristWorkDetails.getStrmId().getName()!=null
									&& !guestPreviousChristWorkDetails.getStrmId().getName().isEmpty()){
								guestPreviousChristWorkDetailsTO.setStrmId(guestPreviousChristWorkDetails.getStrmId().getName());
							}
							if(guestPreviousChristWorkDetails.getWorklLocId()!=null && guestPreviousChristWorkDetails.getWorklLocId().getName()!=null
									&& !guestPreviousChristWorkDetails.getWorklLocId().getName().isEmpty()){
								guestPreviousChristWorkDetailsTO.setWorkLocId(guestPreviousChristWorkDetails.getWorklLocId().getName());
							}
							if(guestPreviousChristWorkDetails.getDeptId()!=null && guestPreviousChristWorkDetails.getDeptId().getName()!=null
									&& !guestPreviousChristWorkDetails.getDeptId().getName().isEmpty()){
								guestPreviousChristWorkDetailsTO.setDeptId(guestPreviousChristWorkDetails.getDeptId().getName());
							}
							if(guestPreviousChristWorkDetails.getWorkHoursPerWeek()!=null && !guestPreviousChristWorkDetails.getWorkHoursPerWeek().isEmpty()){
								guestPreviousChristWorkDetailsTO.setWorkHoursPerWeek(guestPreviousChristWorkDetails.getWorkHoursPerWeek());
							}
							if(guestPreviousChristWorkDetails.getHonorarium()!=null && !guestPreviousChristWorkDetails.getHonorarium().isEmpty()){
								guestPreviousChristWorkDetailsTO.setHonorarium(guestPreviousChristWorkDetails.getHonorarium());
							}
						}
						guestPreviousChristWorkDetailsTOs.add(guestPreviousChristWorkDetailsTO);
					}
					guestFacultyTO.setGuestPreviousChristWorkDetailsTOs(guestPreviousChristWorkDetailsTOs);
				}
				//end additional job information
				list.add(guestFacultyTO);
			}
		}
		guestFacultyExcelReportForm.setMaxListSize(maxListSize);
		return list;
	}
	public EmployeeReportTO getSelectedColumns(GuestFacultyExcelReportForm guestFacultyExcelReportForm)throws Exception {
		EmployeeReportTO reportTO = new EmployeeReportTO();

		List<String> selectedColumnsList = new ArrayList<String>();
		String[] selected = guestFacultyExcelReportForm.getSelectedColumnsArray();
		for (int i = 0; i < selected.length; i++) {
			selectedColumnsList.add(selected[i]);
		}
		if (selectedColumnsList != null) {
			Iterator<String> iterator = selectedColumnsList.iterator();
			int count = 0;
			while (iterator.hasNext()) {

				String columnName = iterator.next();
				if (columnName.equalsIgnoreCase("Teaching")) {
					reportTO.setTeachingStaffDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setTeachingStaffPos((short) count++);
				}
				
				//start personal details
				if(columnName.equalsIgnoreCase("Name")){
					reportTO.setUserNameDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setUserNamePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("UID")) {
					reportTO.setUidDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setUidPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Staff Id")) {
					reportTO.setStaffIdDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setStaffIdPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Gender")) {
					reportTO.setGenderDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setGenderPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Nationality")) {
					reportTO .setNationalityDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setNationalityPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Marital Status")) {
					reportTO .setMatrialStatusDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setMatrialStatusPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Date of birth")) {
					reportTO.setDobDiaplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setDobPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Blood Group")) {
					reportTO.setBloodGroupDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setBloodGrouopPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Religion")) {
					reportTO.setReligionDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setReligionPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Pan No")) {
					reportTO.setPanNoDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setPanNoPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Official Email Id")) {
					reportTO .setOfficialEmailDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setOfficialEmailPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Personal Email Id")) {
					reportTO.setEmailDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setEmailPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Reservation Category")) {
					reportTO.setResCatDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setResCatPosition((short) count++);
				}
				//end personal data
				//start current address 
				if (columnName.equalsIgnoreCase("Current Address Line1")) {
					reportTO
							.setCurrentAddLine1Display(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setCurrentAddLine1Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current Address Line2")) {
					reportTO
							.setCurrentAddLine2Display(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setCurrentAddLine2Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current City")) {
					reportTO.setCurrentCityDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setCurentCitypos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current PinCode")) {
					reportTO.setCurrentPincodeDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setCurentPinCodePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current Country")) {
					reportTO.setCurrentCountrydis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setCurrentCounPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current State")) {
					reportTO.setCurrentStateDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setCurrentStatePos((short) count++);
				}
				//end current address
				//start permanent address
				if (columnName.equalsIgnoreCase("Permanent Address Line1")) {
					reportTO.setPerAddLine1Dis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setPerAddLine1Pos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent Address Line2")) {
					reportTO.setPermAddLine2Dis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setPermAddLine2Pos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent City")) {
					reportTO.setPermCityDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setPermCityPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent PinCode")) {
					reportTO.setPermPinCodeDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setPermPinCodePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent Country")) {
					reportTO.setPermContryDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setPermCountryPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent State")) {
					reportTO.setPermStateDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setPermStatePos((short) count++);
				}
				//end permanent address
				//start job details
				if (columnName.equalsIgnoreCase("Active")) {
					reportTO.setActiveDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setActivePOs((short) count++);
				}
				if (columnName.equalsIgnoreCase("Stream")) {
					reportTO.setStrDetailsDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setStrDetailsPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Work Location")) {
					reportTO.setWorkLocdis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setWorkLocPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Designation")) {
					reportTO .setDesignationDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setDesignationPoistion((short) count++);
				}
				if (columnName.equalsIgnoreCase("Department")) {
					reportTO.setDepartmentDisplay(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setDepartmentPoistion((short) count++);
				}
				if (columnName.equalsIgnoreCase("Title")) {
					reportTO.setTitledis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setTitlePos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Reffered By")) {
					reportTO.setRefferedByDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setRefferedByPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Subject/Specialization")) {
					reportTO.setSubOrSpecilizationDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setSubOrSpecilizationPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Working Hours Per Week")) {
					reportTO.setWorkHoursPerWeekDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setWorkHoursPerWeekPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Honorarium")) {
					reportTO.setHonororiamDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setHonororiamPos((short) count++);
				}
				//end job details
				//start professional exp
				if (columnName.equalsIgnoreCase("Total Experience Years")) {
					reportTO.setTotExpYearsDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setTotExpYearsPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Total Experience Months")) {
					reportTO.setTotExpMonDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setTotExpMonPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Recognised Experience Years")) {
					reportTO.setRelExpYearsDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setRelExpYearsPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Recognised Experience Months")) {
					reportTO.setRelExpMonDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setRelExpMonPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Eligibility Test")) {
					reportTO.setEligibilityTestDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setEligibilityTestPos((short) count++);
					reportTO.setEligibilityTestForOtherDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setEligibilityTestForOtherPos((short) count++);
				}
				//end professional exp
				//start educational details
				if (columnName.equalsIgnoreCase("Qualification Level")) {
					reportTO.setQuaLevelDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setGuaLevelPos((short) count++);
				}
				if (columnName.equalsIgnoreCase("Highest Qualification")) {
					reportTO.setHighestQuaDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setHighestQuaPos((short) count++);
				}
				if(columnName.equalsIgnoreCase("No. of Books with ISBN")){
					reportTO.setBooksDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setBooksPos((short) count++);
				}
				if(columnName.equalsIgnoreCase("Publications Non Refereed")){
					reportTO.setNoOfPublicationsNotReferedDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setNoOfPublicationsNotReferedPos((short) count++);
				}
				if(columnName.equalsIgnoreCase("No. of Publications Refereed")){
					reportTO.setNoOfPublicationsReferedDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setNoOfPublicationsReferedPos((short) count++);
				}
				//end educational details
				//start additional job information
				if(columnName.equalsIgnoreCase("Additional Job Information")){
					reportTO.setAddJobInfoDis(GuestFacultyExcelReportHelper.DISPLAY);
					reportTO.setAddJobInfoPos((short) count++);
				}
				//end additional job information
			
			}
		}
		return reportTO;
	}
	public boolean convertToExcel(List<GuestFacultyTO> guestFacultyTOs,
			EmployeeReportTO empReportTo, HttpServletRequest request, GuestFacultyExcelReportForm guestFacultyExcelReportForm) throws Exception {
		boolean isUpdated=false;
		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			throw new IOException(e);
		}
		String fileName=prop.getProperty(CMSConstants.EMP_GUEST_EXCEL_REPORT);
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
		if(guestFacultyTOs!=null){
			int count = 0;
			Iterator<GuestFacultyTO> iterator = guestFacultyTOs.iterator();
			try{
				wb=new XSSFWorkbook();
				XSSFCellStyle cellStyle=wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				sheet = wb.createSheet("GuestFacultyReport");
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				short counter = 0;
				// Create cells in the row and put some data in it.
				if(empReportTo.getTeachingStaffDis()!=null && empReportTo.getTeachingStaffDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getTeachingStaffPos()).setCellValue("Teaching");
					counter++;
				}
				//start personal data
				if(empReportTo.getUserNameDis()!=null && empReportTo.getUserNameDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getUserNamePos()).setCellValue("Name");
					counter++;
				}
				if(empReportTo.getUidDisplay()!=null && empReportTo.getUidDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getUidPosition()).setCellValue("UID");
					counter++;
				}
				if(empReportTo.getStaffIdDis()!=null && empReportTo.getStaffIdDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getStaffIdPos()).setCellValue("Staff Id");
					counter++;
				}
				if(empReportTo.getGenderDisplay()!=null && empReportTo.getGenderDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getGenderPosition()).setCellValue("Gender");
					counter++;
				}
				if(empReportTo.getNationalityDisplay()!=null && empReportTo.getNationalityDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getNationalityPosition()).setCellValue("Nationality");
					counter++;
				}
				
				if(empReportTo.getMatrialStatusDisplay()!=null && empReportTo.getMatrialStatusDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getMatrialStatusPosition()).setCellValue("Marital Status");
					counter++;
				}
				if(empReportTo.getDobDiaplay()!=null && empReportTo.getDobDiaplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getDobPosition()).setCellValue("Date of birth");
					counter++;
				}
				if(empReportTo.getBloodGroupDisplay()!=null && empReportTo.getBloodGroupDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getBloodGrouopPosition()).setCellValue("Blood Group");
					counter++;
				}
				if(empReportTo.getReligionDisplay()!=null && empReportTo.getReligionDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getReligionPosition()).setCellValue("Religion");
					counter++;
				}
				if(empReportTo.getPanNoDisplay()!=null && empReportTo.getPanNoDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getPanNoPosition()).setCellValue("PanCard No");
					counter++;
				}
				if(empReportTo.getOfficialEmailDisplay()!=null && empReportTo.getOfficialEmailDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getOfficialEmailPosition()).setCellValue("Official Email Id");
					counter++;
				}
				if(empReportTo.getEmailDisplay()!=null && empReportTo.getEmailDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getEmailPosition()).setCellValue("Personal Email Id");
					counter++;
				}
				if(empReportTo.getResCatDisplay()!=null && empReportTo.getResCatDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getResCatPosition()).setCellValue("Reservation Category");
					counter++;
				}
				if(empReportTo.getMobileDisplay()!=null && empReportTo.getMobileDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getMobilePosition()).setCellValue("Mobile");
					counter++;
				}
				if(empReportTo.getBankAccNoDisplay()!=null && empReportTo.getBankAccNoDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getBankAcccNoPosition()).setCellValue("BankAccount No");
					counter++;
				}
				if(empReportTo.getPfNoDisplay()!=null && empReportTo.getPfNoDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getPfNoPosition()).setCellValue("Pf Account No");
					counter++;
				}
				if(empReportTo.getEmerNameDis()!=null && empReportTo.getEmerNameDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getEmerNamePos()).setCellValue("Emergency Contact Name");
					counter++;
				}
				if(empReportTo.getEmerReldis()!=null && empReportTo.getEmerReldis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getEmerRelPos()).setCellValue("RelationShip");
					counter++;
				}
				if(empReportTo.getEmergHomeTelDis()!=null && empReportTo.getEmergHomeTelDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getEmergHomeTelPos()).setCellValue("Emergency HomeTelephone");
					counter++;
				}
				if(empReportTo.getEmergMobileDis()!=null && empReportTo.getEmergMobileDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getEmergMobilePos()).setCellValue("Emergency Mobile No");
					counter++;
				}
				if(empReportTo.getEmergWorkTelDis()!=null && empReportTo.getEmergWorkTelDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getEmergWorkTelPos()).setCellValue("Emergency Work Telephone");
					counter++;
				}
				if(empReportTo.getFourWheelerDisplay()!=null && empReportTo.getFourWheelerDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getFourWheelerPosition()).setCellValue("FourWheeler No ");
					counter++;
				}
				if(empReportTo.getTwoWheelerDisplay()!=null && empReportTo.getTwoWheelerDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getTwoWheelerPosition()).setCellValue("TwoWheeler No");
					counter++;
				}
				//end personal data
				//start current address
				if(empReportTo.getCurrentAddLine1Display()!=null && empReportTo.getCurrentAddLine1Display().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getCurrentAddLine1Position()).setCellValue("Current Address Line1");
					counter++;
				}
				if(empReportTo.getCurrentAddLine2Display()!=null && empReportTo.getCurrentAddLine2Display().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getCurrentAddLine2Position()).setCellValue("Current Address Line2");
					counter++;
				}
				if(empReportTo.getCurrentCountrydis()!=null && empReportTo.getCurrentCountrydis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getCurrentCounPos()).setCellValue("Current Country");
					counter++;
				}
				if(empReportTo.getCurrentCityDis()!=null && empReportTo.getCurrentCityDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getCurentCitypos()).setCellValue("Current City");
					counter++;
				}
				if(empReportTo.getCurrentPincodeDis()!=null && empReportTo.getCurrentPincodeDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getCurentPinCodePos()).setCellValue("Current PinCode");
					counter++;
				}
				if(empReportTo.getCurrentStateDis()!=null && empReportTo.getCurrentStateDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getCurrentStatePos()).setCellValue("Current State");
					counter++;
				}
				//end current address
				//start permanent address
				if(empReportTo.getPerAddLine1Dis()!=null && empReportTo.getPerAddLine1Dis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getPerAddLine1Pos()).setCellValue("Permanent Address Line1");
					counter++;
				}
				if(empReportTo.getPermAddLine2Dis()!=null && empReportTo.getPermAddLine2Dis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getPermAddLine2Pos()).setCellValue("Permanent Address line2");
					counter++;
				}
				if(empReportTo.getPermContryDis()!=null && empReportTo.getPermContryDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getPermCountryPos()).setCellValue("Permanent Country");
					counter++;
				}
				if(empReportTo.getPermCityDis()!=null && empReportTo.getPermCityDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getPermCityPos()).setCellValue("Permanent City");
					counter++;
				}
				if(empReportTo.getPermPinCodeDis()!=null && empReportTo.getPermPinCodeDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getPermPinCodePos()).setCellValue("Permanent PinCode");
					counter++;
				}
				if(empReportTo.getPermStateDis()!=null && empReportTo.getPermStateDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getPermStatePos()).setCellValue("Permanent State");
					counter++;
				}
				//end permanent address
				//start job details
				if(empReportTo.getActiveDis()!=null && empReportTo.getActiveDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getActivePOs()).setCellValue("Active");
					counter++;
				}
				if(empReportTo.getStrDetailsDis()!=null && empReportTo.getStrDetailsDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getStrDetailsPos()).setCellValue("Stream Details");
					counter++;
				}
				if(empReportTo.getWorkLocdis()!=null && empReportTo.getWorkLocdis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getWorkLocPos()).setCellValue("Work Location");
					counter++;
				}
				if(empReportTo.getDepartmentDisplay()!=null && empReportTo.getDepartmentDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getDepartmentPoistion()).setCellValue("Department");
					counter++;
				}
				if(empReportTo.getDesignationDisplay()!=null && empReportTo.getDesignationDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getDesignationPoistion()).setCellValue("Designation");
					counter++;
				}
				if(empReportTo.getTitledis()!=null && empReportTo.getTitledis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getTitlePos()).setCellValue("Title");
					counter++;
				}
				if(empReportTo.getSubOrSpecilizationDis()!=null && empReportTo.getSubOrSpecilizationDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getSubOrSpecilizationPos()).setCellValue("Subject/Specialization");
					counter++;
				}
				if(empReportTo.getRefferedByDis()!=null && empReportTo.getRefferedByDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getRefferedByPos()).setCellValue("Reffered By");
					counter++;
				}
				if(empReportTo.getWorkHoursPerWeekDis()!=null && empReportTo.getWorkHoursPerWeekDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getWorkHoursPerWeekPos()).setCellValue("Working Hours Per Week");
					counter++;
				}
				if(empReportTo.getHonororiamDis()!=null && empReportTo.getHonororiamDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getHonororiamPos()).setCellValue("Honorarium");
					counter++;
				}
				//end job details
				//start professional exp
				if(empReportTo.getTotExpYearsDis()!=null && empReportTo.getTotExpYearsDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getTotExpYearsPos()).setCellValue("Total Experience Years");
					counter++;
				}
				if(empReportTo.getTotExpMonDis()!=null && empReportTo.getTotExpMonDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getTotExpMonPos()).setCellValue("Total Experience Months");
					counter++;
				}
				if(empReportTo.getRelExpYearsDis()!=null && empReportTo.getRelExpYearsDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getRelExpYearsPos()).setCellValue("Recognised Experience Years");
					counter++;
				}
				if(empReportTo.getRelExpMonDis()!=null && empReportTo.getRelExpMonDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getRelExpMonPos()).setCellValue("Recognised Experience Months");
					counter++;
				}
				if(empReportTo.getEligibilityTestDis()!=null && empReportTo.getEligibilityTestDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getEligibilityTestPos()).setCellValue("Eligibility Test");
					counter++;
				}
				if(empReportTo.getEligibilityTestForOtherDis()!=null && empReportTo.getEligibilityTestForOtherDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getEligibilityTestForOtherPos()).setCellValue("Eligibility Test For Other");
					counter++;
				}
				//end professional exp
				//start educational details
				if(empReportTo.getQuaLevelDis()!=null && empReportTo.getQuaLevelDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getGuaLevelPos()).setCellValue("Qualification Level");
					counter++;
				}
				if(empReportTo.getHighestQuaDis()!=null && empReportTo.getHighestQuaDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getHighestQuaPos()).setCellValue("Highest Qualification");
					counter++;
				}
				if(empReportTo.getNoOfPublicationsNotReferedDis()!=null && empReportTo.getNoOfPublicationsNotReferedDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getNoOfPublicationsNotReferedPos()).setCellValue("Publications Non Refereed");
					counter++;
				}
				if(empReportTo.getNoOfPublicationsReferedDis()!=null && empReportTo.getNoOfPublicationsReferedDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getNoOfPublicationsReferedPos()).setCellValue("No. of Publications Refereed");
					counter++;
				}
				if(empReportTo.getBooksDis()!=null && empReportTo.getBooksDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					row.createCell((short)empReportTo.getBooksPos()).setCellValue("No. of Books with ISBN");
					counter++;
				}
				//end educational details
				//start additional job info
				if(empReportTo.getAddJobInfoDis()!=null && empReportTo.getAddJobInfoDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
					if(guestFacultyExcelReportForm.getMaxListSize()>0){
						for(int i=1;i<=guestFacultyExcelReportForm.getMaxListSize();i++){
							row.createCell((short) (counter) ).setCellValue("startDate"+i);
							counter = (short) (counter +1);
							row.createCell((short) (counter) ).setCellValue("endDate"+i);
							counter = (short) (counter + 1);
							row.createCell((short) (counter) ).setCellValue("Semester"+i);
							counter = (short) (counter + 1);
							row.createCell((short) (counter) ).setCellValue("Additional Department"+i);
							counter = (short) (counter + 1);
							row.createCell((short) (counter) ).setCellValue("Additional Stream"+i);
							counter = (short) (counter + 1);
							row.createCell((short) (counter) ).setCellValue("Additional Work Location"+i);
							counter = (short) (counter + 1);
							row.createCell((short) (counter) ).setCellValue("Additional Work Hours/Week"+i);
							counter = (short) (counter + 1);
							row.createCell((short) (counter) ).setCellValue("Additional Honorarium"+i);
							counter = (short) (counter + 1);
						}
					}
					
				}
				//end additional job info
				
				while (iterator.hasNext()) {
					short cellcount=0;
					GuestFacultyTO empTO = (GuestFacultyTO) iterator.next();
					count = count +1;
					row = sheet.createRow(count);
					if(empReportTo.getTeachingStaffDis()!=null && empReportTo.getTeachingStaffDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getTeachingStaff()!=null){
							row.createCell((short)empReportTo.getTeachingStaffPos()).setCellValue(empTO.getTeachingStaff());
						}
					}
					//start personal data
					if(empReportTo.getUserNameDis()!=null && empReportTo.getUserNameDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getFirstName()!=null){
							row.createCell((short)empReportTo.getUserNamePos()).setCellValue(empTO.getFirstName());
						}
					}
					if(empReportTo.getUidDisplay()!=null && empReportTo.getUidDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getUid()!=null){
							row.createCell((short)empReportTo.getUidPosition()).setCellValue(empTO.getUid());
						}
					}
					if(empReportTo.getStaffIdDis()!=null && empReportTo.getStaffIdDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getStaffId()!=null){
							row.createCell((short)empReportTo.getStaffIdPos()).setCellValue(empTO.getStaffId());
						}
					}
					if(empReportTo.getGenderDisplay()!=null && empReportTo.getGenderDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getGender()!=null){
							row.createCell((short)empReportTo.getGenderPosition()).setCellValue(empTO.getGender());
						}
					}
					if(empReportTo.getNationalityDisplay()!=null && empReportTo.getNationalityDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getNationalityName()!=null){
							row.createCell((short)empReportTo.getNationalityPosition()).setCellValue(empTO.getNationalityName());
						}
					}
					
					if(empReportTo.getMatrialStatusDisplay()!=null && empReportTo.getMatrialStatusDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getMaritalStatus()!=null){
							row.createCell((short)empReportTo.getMatrialStatusPosition()).setCellValue(empTO.getMaritalStatus());
						}
					}
					if(empReportTo.getDobDiaplay()!=null && empReportTo.getDobDiaplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getDob()!=null){
							row.createCell((short)empReportTo.getDobPosition()).setCellValue(empTO.getDob());
						}
					}
					if(empReportTo.getBloodGroupDisplay()!=null && empReportTo.getBloodGroupDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getBloodGroup()!=null){
							row.createCell((short)empReportTo.getBloodGrouopPosition()).setCellValue(empTO.getBloodGroup());
						}
					}
					if(empReportTo.getReligionDisplay()!=null && empReportTo.getReligionDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getReligion()!=null){
							row.createCell((short)empReportTo.getReligionPosition()).setCellValue(empTO.getReligion());
						}
					}
					if(empReportTo.getPanNoDisplay()!=null && empReportTo.getPanNoDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getPanNo()!=null){
							row.createCell((short)empReportTo.getPanNoPosition()).setCellValue(empTO.getPanNo());
						}
					}
					if(empReportTo.getOfficialEmailDisplay()!=null && empReportTo.getOfficialEmailDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getWorkEmail()!=null){
							row.createCell((short)empReportTo.getOfficialEmailPosition()).setCellValue(empTO.getWorkEmail());
						}
					}
					if(empReportTo.getEmailDisplay()!=null && empReportTo.getEmailDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getEmail()!=null){
							row.createCell((short)empReportTo.getEmailPosition()).setCellValue(empTO.getEmail());
						}
					}
					if(empReportTo.getResCatDisplay()!=null && empReportTo.getResCatDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getReservationCatagory()!=null){
							row.createCell((short)empReportTo.getResCatPosition()).setCellValue(empTO.getReservationCatagory());
						}
					}
					if(empReportTo.getMobileDisplay()!=null && empReportTo.getMobileDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getMobile()!=null){
							row.createCell((short)empReportTo.getMobilePosition()).setCellValue(empTO.getMobile());
						}
					}
					if(empReportTo.getBankAccNoDisplay()!=null && empReportTo.getBankAccNoDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getBankAccNo()!=null){
							row.createCell((short)empReportTo.getBankAcccNoPosition()).setCellValue(empTO.getBankAccNo());
						}
					}
					if(empReportTo.getPfNoDisplay()!=null && empReportTo.getPfNoDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getPbankCCode()!=null){
							row.createCell((short)empReportTo.getPfNoPosition()).setCellValue(empTO.getPbankCCode());
						}
					}
					if(empReportTo.getEmerNameDis()!=null && empReportTo.getEmerNameDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getEmergencyContName()!=null){
							row.createCell((short)empReportTo.getEmerNamePos()).setCellValue(empTO.getEmergencyContName());
						}
					}
					if(empReportTo.getEmerReldis()!=null && empReportTo.getEmerReldis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getRelationship()!=null){
							row.createCell((short)empReportTo.getEmerRelPos()).setCellValue(empTO.getRelationship());
						}
					}
					if(empReportTo.getEmergHomeTelDis()!=null && empReportTo.getEmergHomeTelDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getEmergencyHomeTelephone()!=null){
							row.createCell((short)empReportTo.getEmergHomeTelPos()).setCellValue(empTO.getEmergencyHomeTelephone());
						}
						
					}
					if(empReportTo.getEmergMobileDis()!=null && empReportTo.getEmergMobileDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getEmergencyMobile()!=null){
							row.createCell((short)empReportTo.getEmergMobilePos()).setCellValue(empTO.getEmergencyMobile());
						}
					}
					if(empReportTo.getEmergWorkTelDis()!=null && empReportTo.getEmergWorkTelDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getEmergencyWorkTelephone()!=null){
							row.createCell((short)empReportTo.getEmergWorkTelPos()).setCellValue(empTO.getEmergencyWorkTelephone());
						}
					}
					if(empReportTo.getFourWheelerDisplay()!=null && empReportTo.getFourWheelerDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getFourWheelerNo()!=null){
							row.createCell((short)empReportTo.getFourWheelerPosition()).setCellValue(empTO.getFourWheelerNo());
						}
					}
					if(empReportTo.getTwoWheelerDisplay()!=null && empReportTo.getTwoWheelerDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getTwoWheelerNo()!=null){
							row.createCell((short)empReportTo.getTwoWheelerPosition()).setCellValue(empTO.getTwoWheelerNo());
						}
					}
					//end personal data
					//start current address
					if(empReportTo.getCurrentAddLine1Display()!=null && empReportTo.getCurrentAddLine1Display().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getCommunicationAddressLine1()!=null){
							row.createCell((short)empReportTo.getCurrentAddLine1Position()).setCellValue(empTO.getCommunicationAddressLine1());
						}
					}
					if(empReportTo.getCurrentAddLine2Display()!=null && empReportTo.getCurrentAddLine2Display().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getCommunicationAddressLine2()!=null){
							row.createCell((short)empReportTo.getCurrentAddLine2Position()).setCellValue(empTO.getCommunicationAddressLine2());
						}
					}
					if(empReportTo.getCurrentCountrydis()!=null && empReportTo.getCurrentCountrydis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getCommuContryName()!=null){
							row.createCell((short)empReportTo.getCurrentCounPos()).setCellValue(empTO.getCommuContryName());
						}
					}
					if(empReportTo.getCurrentCityDis()!=null && empReportTo.getCurrentCityDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getCommunicationAddressCity()!=null){
							row.createCell((short)empReportTo.getCurentCitypos()).setCellValue(empTO.getCommunicationAddressCity());
						}
					}
					if(empReportTo.getCurrentPincodeDis()!=null && empReportTo.getCurrentPincodeDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getCommunicationAddressZip()!=null){
							row.createCell((short)empReportTo.getCurentPinCodePos()).setCellValue(empTO.getCommunicationAddressZip());
						}
					}
					if(empReportTo.getCurrentStateDis()!=null && empReportTo.getCurrentStateDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getCommuStateName()!=null){
							row.createCell((short)empReportTo.getCurrentStatePos()).setCellValue(empTO.getCommuStateName());
						}
					}
					//end current address
					//start permanent address
					if(empReportTo.getPerAddLine1Dis()!=null && empReportTo.getPerAddLine1Dis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getPermanentAddressLine1()!=null){
							row.createCell((short)empReportTo.getPerAddLine1Pos()).setCellValue(empTO.getPermanentAddressLine1());
						}
					}
					if(empReportTo.getPermAddLine2Dis()!=null && empReportTo.getPermAddLine2Dis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getPermanentAddressLine2()!=null){
							row.createCell((short)empReportTo.getPermAddLine2Pos()).setCellValue(empTO.getPermanentAddressLine2());
						}
					}
					if(empReportTo.getPermContryDis()!=null && empReportTo.getPermContryDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getPermanentCountryName()!=null){
							row.createCell((short)empReportTo.getPermCountryPos()).setCellValue(empTO.getPermanentCountryName());
						}
					}
					if(empReportTo.getPermCityDis()!=null && empReportTo.getPermCityDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getPermanentAddressCity()!=null){
							row.createCell((short)empReportTo.getPermCityPos()).setCellValue(empTO.getPermanentAddressCity());
						}
					}
					if(empReportTo.getPermPinCodeDis()!=null && empReportTo.getPermPinCodeDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getPermanentAddressZip()!=null){
							row.createCell((short)empReportTo.getPermPinCodePos()).setCellValue(empTO.getPermanentAddressZip());
						}
					}
					if(empReportTo.getPermStateDis()!=null && empReportTo.getPermStateDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getPermanentStateName()!=null){
							row.createCell((short)empReportTo.getPermStatePos()).setCellValue(empTO.getPermanentStateName());
						}
					}
					//end permanent address
					//start job details
					if(empReportTo.getActiveDis()!=null && empReportTo.getActiveDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getActive()!=null){
							row.createCell((short)empReportTo.getActivePOs()).setCellValue(empTO.getActive());
						}
					}
					if(empReportTo.getStrDetailsDis()!=null && empReportTo.getStrDetailsDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getStream()!=null){
							row.createCell((short)empReportTo.getStrDetailsPos()).setCellValue(empTO.getStream());
						}
					}
					if(empReportTo.getWorkLocdis()!=null && empReportTo.getWorkLocdis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getWorkLocationName()!=null){
							row.createCell((short)empReportTo.getWorkLocPos()).setCellValue(empTO.getWorkLocationName());
						}
					}
					if(empReportTo.getDepartmentDisplay()!=null && empReportTo.getDepartmentDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getDepartmentName()!=null){
							row.createCell((short)empReportTo.getDepartmentPoistion()).setCellValue(empTO.getDepartmentName());
						}
					}
					if(empReportTo.getDesignationDisplay()!=null && empReportTo.getDesignationDisplay().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getDesignationName()!=null){
							row.createCell((short)empReportTo.getDesignationPoistion()).setCellValue(empTO.getDesignationName());
						}
					}
					if(empReportTo.getTitledis()!=null && empReportTo.getTitledis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getTitle()!=null){
							row.createCell((short)empReportTo.getTitlePos()).setCellValue(empTO.getTitle());
						}
					}
					if(empReportTo.getSubOrSpecilizationDis()!=null && empReportTo.getSubOrSpecilizationDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getSubjectSpecilization()!=null){
							row.createCell((short)empReportTo.getSubOrSpecilizationPos()).setCellValue(empTO.getSubjectSpecilization());
						}
					}
					if(empReportTo.getRefferedByDis()!=null && empReportTo.getRefferedByDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getReferredBy()!=null){
							row.createCell((short)empReportTo.getRefferedByPos()).setCellValue(empTO.getReferredBy());
						}
					}
					if(empReportTo.getWorkHoursPerWeekDis()!=null && empReportTo.getWorkHoursPerWeekDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getWorkingHoursPerWeek()!=null){
							row.createCell((short)empReportTo.getWorkHoursPerWeekPos()).setCellValue(empTO.getWorkingHoursPerWeek());
						}
					}
					if(empReportTo.getHonororiamDis()!=null && empReportTo.getHonororiamDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getHonorariumPerHours()!=null){
							row.createCell((short)empReportTo.getHonororiamPos()).setCellValue(empTO.getHonorariumPerHours());
						}
					}
					//end job details
					//start professional exp
					if(empReportTo.getTotExpYearsDis()!=null && empReportTo.getTotExpYearsDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getTotalExpYears()!=null){
							row.createCell((short)empReportTo.getTotExpYearsPos()).setCellValue(empTO.getTotalExpYears());
						}
					}
					if(empReportTo.getTotExpMonDis()!=null && empReportTo.getTotExpMonDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getTotalExpMonths()!=null){
							row.createCell((short)empReportTo.getTotExpMonPos()).setCellValue(empTO.getTotalExpMonths());
						}
					}
					if(empReportTo.getRelExpYearsDis()!=null && empReportTo.getRelExpYearsDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getRelevantExpYears()!=null){
							row.createCell((short)empReportTo.getRelExpYearsPos()).setCellValue(empTO.getRelevantExpYears());
						}
					}
					if(empReportTo.getRelExpMonDis()!=null && empReportTo.getRelExpMonDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getRelevantExpMonths()!=null){
							row.createCell((short)empReportTo.getRelExpMonPos()).setCellValue(empTO.getRelevantExpMonths());
						}
					}
					if(empReportTo.getEligibilityTestDis()!=null && empReportTo.getEligibilityTestDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getEligibilityTest()!=null){
							row.createCell((short)empReportTo.getEligibilityTestPos()).setCellValue(empTO.getEligibilityTest());
						}
					}
					if(empReportTo.getEligibilityTestForOtherDis()!=null && empReportTo.getEligibilityTestForOtherDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY)){
						cellcount++;
						if(empTO.getEligibilityTestForOther()!=null){
							row.createCell((short)empReportTo.getEligibilityTestForOtherPos()).setCellValue(empTO.getEligibilityTestForOther());
						}
					}
					//end professional exp
					//start educational details
					if(empReportTo.getQuaLevelDis()!=null && empReportTo.getQuaLevelDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getQualificationLevel()!=null){
							row.createCell((short)empReportTo.getGuaLevelPos()).setCellValue(empTO.getQualificationLevel());
						}
					}
					if(empReportTo.getHighestQuaDis()!=null && empReportTo.getHighestQuaDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getHighestQualification()!=null){
							row.createCell((short)empReportTo.getHighestQuaPos()).setCellValue(empTO.getHighestQualification());
						}
					}
					if(empReportTo.getNoOfPublicationsNotReferedDis()!=null && empReportTo.getNoOfPublicationsNotReferedDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getNoOfPubNotReffered()!=null){
							row.createCell((short)empReportTo.getNoOfPublicationsNotReferedPos()).setCellValue(empTO.getNoOfPubNotReffered());
						}
					}
					if(empReportTo.getNoOfPublicationsReferedDis()!=null && empReportTo.getNoOfPublicationsReferedDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getNoOfPubReffered()!=null){
							row.createCell((short)empReportTo.getNoOfPublicationsReferedPos()).setCellValue(empTO.getNoOfPubReffered());
						}
					}
					if(empReportTo.getBooksDis()!=null && empReportTo.getBooksDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) ){
						cellcount++;
						if(empTO.getBooks()!=null){
							row.createCell((short)empReportTo.getBooksPos()).setCellValue(empTO.getBooks());
						}
					}
					//end educational details
					//start additional job info
					if(empReportTo.getAddJobInfoDis()!=null && empReportTo.getAddJobInfoDis().equalsIgnoreCase(GuestFacultyExcelReportHelper.DISPLAY) && empTO.getGuestPreviousChristWorkDetailsTOs()!=null && !empTO.getGuestPreviousChristWorkDetailsTOs().isEmpty()){
						Iterator<GuestPreviousChristWorkDetailsTO> iterator2=empTO.getGuestPreviousChristWorkDetailsTOs().iterator();
						while (iterator2.hasNext()) {
							GuestPreviousChristWorkDetailsTO guestPreviousChristWorkDetailsTO = (GuestPreviousChristWorkDetailsTO) iterator2.next();
							if(guestPreviousChristWorkDetailsTO.getStartDate()!=null){
								row.createCell((short) (cellcount) ).setCellValue(guestPreviousChristWorkDetailsTO.getStartDate());
							}
							cellcount = (short) (cellcount +1);
							if(guestPreviousChristWorkDetailsTO.getEndDate()!=null){
								row.createCell((short) (cellcount) ).setCellValue(guestPreviousChristWorkDetailsTO.getEndDate());
							}
							cellcount = (short) (cellcount + 1);
							if(guestPreviousChristWorkDetailsTO.getSemester()!=null && !guestPreviousChristWorkDetailsTO.getSemester().isEmpty()){
								row.createCell((short) (cellcount) ).setCellValue(guestPreviousChristWorkDetailsTO.getSemester());
							}
							cellcount = (short) (cellcount + 1);
							if(guestPreviousChristWorkDetailsTO.getDeptId()!=null && !guestPreviousChristWorkDetailsTO.getDeptId().isEmpty()){
								row.createCell((short) (cellcount) ).setCellValue(guestPreviousChristWorkDetailsTO.getDeptId());
							}
							cellcount = (short) (cellcount + 1);
							if(guestPreviousChristWorkDetailsTO.getStrmId()!=null && !guestPreviousChristWorkDetailsTO.getStrmId().isEmpty()){
								row.createCell((short) (cellcount) ).setCellValue(guestPreviousChristWorkDetailsTO.getStrmId());
							}
							cellcount = (short) (cellcount + 1);
							if(guestPreviousChristWorkDetailsTO.getWorkLocId()!=null && !guestPreviousChristWorkDetailsTO.getWorkLocId().isEmpty()){
								row.createCell((short) (cellcount) ).setCellValue(guestPreviousChristWorkDetailsTO.getWorkLocId());
							}
							cellcount = (short) (cellcount + 1);
							if(guestPreviousChristWorkDetailsTO.getWorkHoursPerWeek()!=null && !guestPreviousChristWorkDetailsTO.getWorkHoursPerWeek().isEmpty()){
								row.createCell((short) (cellcount) ).setCellValue(guestPreviousChristWorkDetailsTO.getWorkHoursPerWeek());
							}
							cellcount = (short) (cellcount + 1);
							if(guestPreviousChristWorkDetailsTO.getHonorarium()!=null && !guestPreviousChristWorkDetailsTO.getHonorarium().isEmpty()){
								row.createCell((short) (cellcount) ).setCellValue(guestPreviousChristWorkDetailsTO.getHonorarium());
							}
							cellcount = (short) (cellcount + 1);
						}
					}
					//end additional job info
					
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
}
