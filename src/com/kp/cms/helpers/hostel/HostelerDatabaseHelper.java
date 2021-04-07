package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelerDatabaseForm;
import com.kp.cms.handlers.hostel.HostelerDatabaseHandler;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.hostel.HostelerDataBaseResultsTO;
import com.kp.cms.to.hostel.HostelerDatabaseTO;
import com.kp.cms.utilities.CommonUtil;

public class HostelerDatabaseHelper {
	private static final Log log = LogFactory.getLog(HostelerDatabaseHandler.class);
	public static volatile HostelerDatabaseHelper hostelerDatabaseHelper = null;
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private HostelerDatabaseHelper(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static HostelerDatabaseHelper getInstance() {
		if (hostelerDatabaseHelper == null) {
			hostelerDatabaseHelper = new HostelerDatabaseHelper();
		}
		return hostelerDatabaseHelper;
	}
	
	/**
	 * Used to prepare the query
	 */
	public String searchCritera(HostelerDatabaseForm databaseForm, int checkedInStatusId)throws Exception{
		String commonSearch = HostelerDatabaseHelper.commonSearch(checkedInStatusId);
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(commonSearch);
		
		if(databaseForm.getHostelId().length() > 0){
			String hostel = " and form.hlHostelByHlApprovedHostelId.isActive = 1 and form.hlHostelByHlApprovedHostelId.id = " 
			+ Integer.parseInt(databaseForm.getHostelId().trim());
			buffer.append(hostel);
		}
		if(databaseForm.getSearchBy().length() > 0 && databaseForm.getTextToSearch().length() > 0){
			if(databaseForm.getSearchBy().equals(CMSConstants.HOSTEL_HOSTLER_DATABASE_ROOMTYPE)){
				String roomType= " and form.hlRoomTypeByHlApprovedRoomTypeId.isActive = 1 and " +
				"form.hlRoomTypeByHlApprovedRoomTypeId.name like '"+ 
				databaseForm.getTextToSearch().trim() +"%'";
				buffer.append(roomType);
			}
			else if(databaseForm.getSearchBy().equals(CMSConstants.HOSTEL_HOSTLER_DATABASE_REGNO)){
				String regNo = " and student.registerNo ='" + databaseForm.getTextToSearch().trim() +"'" 
				+ " and student.isActive = 1";
				buffer.append(regNo);
			}
			else if(databaseForm.getSearchBy().equals(CMSConstants.HOSTEL_HOSTLER_DATABASE_ROOMNO)){
				String roomNo = " and room.name ='" + databaseForm.getTextToSearch().trim() +"'"
				+ " and room.isActive = 1";
				buffer.append(roomNo);
			}
			else if(databaseForm.getSearchBy().equals(CMSConstants.HOSTEL_HOSTLER_DATABASE_STAFFID)){
				String roomNo = " and form.employee.id ='" + databaseForm.getTextToSearch().trim() +"'"
				+ " and room.isActive = 1";
				buffer.append(roomNo);
			}
			/*else if(databaseForm.getSearchBy().equals(CMSConstants.HOSTEL_HOSTLER_DATABASE_NAME)){
				String name = " and form.admAppln.personalData.firstName ='" + databaseForm.getTextToSearch().trim() +"'";
				buffer.append(name);
			}*/
		}	
		return buffer.toString();
		
	}
	
	public static String commonSearch(int checkedInStatusId)throws Exception{
		
		String search = "select form.hlHostelByHlApprovedHostelId.id," +
		" form.hlHostelByHlApprovedHostelId.name," +
		" form.admAppln.personalData.firstName," +
		" form.admAppln.personalData.middleName," +
		" form.admAppln.personalData.lastName," +
		" student.id," +
		" student.registerNo," +
		" room.name," +
		" form.hlRoomTypeByHlApprovedRoomTypeId.id," +
		" room.floorNo," +
		" form.isStaff," +
		"form.employee.firstName," +
		"form.employee.middleName," +
		"form.employee.lastName,form.id,form.employee.code  " +
		" from HlApplicationForm form" +
		" join form.hlRoomTypeByHlApprovedRoomTypeId.hlRooms room" +
		" join form.admAppln.students student" +
		" where form.isActive = 1" +
		" and form.hlStatus.isActive = 1" +
		" and form.hlStatus.id = "+checkedInStatusId ;
				return search;
	}
	
	/**
	 * Used to covert HlApplicationForm BO to TO
	 * Prepares the BO as per the input selected by user in the 1st screen
	 */
	public List<HostelerDatabaseTO> convertAppFormBOToTO(List<HlRoomTransaction> appBOList)throws Exception{
		List<HostelerDatabaseTO> studentList = new ArrayList<HostelerDatabaseTO>();
		Set<Integer> idSet=new HashSet<Integer>();
		HostelerDatabaseTO databaseTO = null;
			if(appBOList != null && !appBOList.isEmpty()){
				Iterator<HlRoomTransaction> iterator = appBOList.iterator();
				while (iterator.hasNext()) {
					HlRoomTransaction object =  iterator.next();
					HlApplicationForm hlApplicationForm=object.getHlApplicationForm();
					if(!idSet.contains(hlApplicationForm.getId())){
					databaseTO =  new HostelerDatabaseTO();
					String name="";
					if(hlApplicationForm.getIsStaff()==null || !hlApplicationForm.getIsStaff()){
						if(hlApplicationForm.getAdmAppln()!=null){
							if(hlApplicationForm.getAdmAppln().getPersonalData().getFirstName()!=null){
								name=name+hlApplicationForm.getAdmAppln().getPersonalData().getFirstName();
							}
							if(hlApplicationForm.getAdmAppln().getPersonalData().getMiddleName()!=null){
								name=name+hlApplicationForm.getAdmAppln().getPersonalData().getMiddleName();
							}
							if(hlApplicationForm.getAdmAppln().getPersonalData().getLastName()!=null){
								name=name+hlApplicationForm.getAdmAppln().getPersonalData().getLastName();
							}
							databaseTO.setStudentName(name);
							Set<Student> stuSet=hlApplicationForm.getAdmAppln().getStudents();
							if(stuSet!=null && !stuSet.isEmpty()){
								Iterator<Student> itr=stuSet.iterator();
								while (itr.hasNext()) {
									Student student = (Student) itr.next();
									databaseTO.setStudentId(student.getId());
									databaseTO.setRegNo(student.getRegisterNo());
								}
							}
						}
					}else{
						if(hlApplicationForm.getEmployee()!=null){
							databaseTO.setRegNo(hlApplicationForm.getEmployee().getCode());
							if(hlApplicationForm.getEmployee().getFirstName()!=null){
								name=name+hlApplicationForm.getEmployee().getFirstName();
							}
							/*if(hlApplicationForm.getEmployee().getMiddleName()!=null){
								name=name+hlApplicationForm.getEmployee().getMiddleName();
							}
							if(hlApplicationForm.getEmployee().getLastName()!=null){
								name=name+hlApplicationForm.getEmployee().getLastName();
							}*/
							databaseTO.setStudentName(name);
						}
					}
					if(hlApplicationForm.getHlHostelByHlApprovedHostelId()!=null){
						databaseTO.setHostelId(hlApplicationForm.getHlHostelByHlApprovedHostelId().getId());
						databaseTO.setHostelName(hlApplicationForm.getHlHostelByHlApprovedHostelId().getName());
					}
					if(object.getHlRoom()!=null){
					databaseTO.setRoomNo(object.getHlRoom().getName());
					databaseTO.setFloorNo(object.getHlRoom().getFloorNo());
					databaseTO.setRoomTypeName(object.getHlRoom().getHlRoomType().getName());
					}
					databaseTO.setHlformId(hlApplicationForm.getId());
					
						idSet.add(hlApplicationForm.getId());
						studentList.add(databaseTO);
					}
				}				
			}
		return studentList;
	}

	public static String getQuery(String hlAppId) {	 
			String searchCriteria = "";
			searchCriteria = "select hl.hlHostelByHlAppliedHostelId.name," +
					"hl.requisitionNo," +
					"hl.hlRoomTypeByHlAppliedRoomTypeId.name," +
					"hl.admAppln.personalData.religion.name," +
					"hl.admAppln.personalData.dateOfBirth," +
					"hl.admAppln.personalData.mobileNo1," +
					"hl.admAppln.courseBySelectedCourseId.program.name," +
					"hl.admAppln,"+
					"hl.admAppln.personalData.email," +
					"hl.admAppln.personalData.parentAddressLine1," +
					"hl.admAppln.personalData.parentAddressLine2," +
					"hl.admAppln.personalData.parentAddressLine3," +
					"hl.admAppln.personalData.permanentAddressZipCode," +
					"hl.admAppln.personalData.parentPh1," +
					"hl.admAppln.personalData.guardianAddressLine1," +
					"hl.admAppln.personalData.guardianAddressLine2," +
					"hl.admAppln.personalData.guardianAddressLine3," +
					"hl.admAppln.personalData.guardianPh1," +
					"hl.admAppln.personalData.guardianAddressZipCode," +
					"hl.admAppln.personalData.cityByParentAddressCityId,"+
					"hl.admAppln.personalData.stateByParentAddressStateId.name,"+
					"hl.admAppln.personalData.countryByParentAddressCountryId.name,"+
					"hl.admAppln.personalData.cityByGuardianAddressCityId,"+
					"hl.hlHostelByHlAppliedHostelId.name,"+
					"hl.hlRoomTypeByHlAppliedRoomTypeId.name,"+
					"hl.id ,hl.isStaff,hl.employee.dob,hl.employee.mobile,hl.employee.email," +
					"hl.employee.permanentAddressLine1," +
					"hl.employee.permanentAddressLine2," +
					"hl.employee.permanentAddressCity," +
					"hl.employee.stateByPermanentAddressStateId.name," +
					"hl.employee.permanentAddressStateOthers," +
					"hl.employee.countryByPermanentAddressCountryId.name," +
					"hl.employee.permanentAddressZip," +
					"hl.employee.communicationAddressLine1," +
					"hl.employee.communicationAddressLine2," +
					"hl.employee.communicationAddressCity," +
					"hl.employee.stateByCommunicationAddressStateId.name," +
					"hl.employee.communicationAddressStateOthers," +
					"hl.employee.countryByCommunicationAddressCountryId.name," +
					"hl.employee.communicationAddressZip," +
					"hl.employee.permanentAddressMobile1," +
					"hl.employee.permanentAddressMobile2," +
					"hl.employee.permanentAddressMobile3," +
					"hl.employee.firstName," +
					"hl.employee.middleName," +
					"hl.employee.lastName,hl.employee.code,student.registerNo " +
					"from HlApplicationForm hl join hl.admAppln.students student " +
					" where hl.id='"+hlAppId+"'"+" and hl.isActive=1";
					return searchCriteria;
		}

	public static HostelerDataBaseResultsTO copyRequisitionBOToTO(List<HlApplicationForm> hostelerBO, HttpServletRequest req) {
		HostelerDataBaseResultsTO hostelerTo=null; 
		
		if(hostelerBO!=null && !hostelerBO.isEmpty()){	
			Iterator<HlApplicationForm> requisitionsIterator = hostelerBO.iterator();
				while (requisitionsIterator.hasNext()) {
					HlApplicationForm hlApplicationForm = (HlApplicationForm) requisitionsIterator.next();
					hostelerTo = new HostelerDataBaseResultsTO();
					boolean isStaff=false;
					String name="";
					if(hlApplicationForm.getHlHostelByHlApprovedHostelId() != null){
						hostelerTo.setHostelName(hlApplicationForm.getHlHostelByHlApprovedHostelId().getName());
					}
					if(hlApplicationForm.getRequisitionNo()!=null){
						hostelerTo.setRequisitionNol(hlApplicationForm.getRequisitionNo().toString());
					}
					if(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId() != null){
						hostelerTo.setRoomType(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId().getName());
					}
					if(hlApplicationForm.getHlHostelByHlAppliedHostelId()!= null){
						hostelerTo.setPreferredHostel(hlApplicationForm.getHlHostelByHlAppliedHostelId().getName());
						}
					if(hlApplicationForm.getHlRoomTypeByHlAppliedRoomTypeId()!= null){
						hostelerTo.setPreferredRoomType(hlApplicationForm.getHlRoomTypeByHlAppliedRoomTypeId().getName());
						}
					if(hlApplicationForm.getIsStaff()==null || !hlApplicationForm.getIsStaff()){
						if(hlApplicationForm.getAdmAppln()!=null){
							AdmAppln admAppln=hlApplicationForm.getAdmAppln();
							PersonalData personalData=admAppln.getPersonalData();
							if(personalData.getFirstName()!=null){
								name=name+personalData.getFirstName();
							}
							if(personalData.getMiddleName()!=null){
								name=name+personalData.getMiddleName();
							}
							if(personalData.getLastName()!=null){
								name=name+personalData.getLastName();
							}
							hostelerTo.setStudentName(name);
							if(personalData.getReligion() != null){
								hostelerTo.setReligion(personalData.getReligion().getName());
							}
							if(personalData.getDateOfBirth() != null){
								hostelerTo.setDateofBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getDateOfBirth()), HostelerDatabaseHelper.SQL_DATEFORMAT,HostelerDatabaseHelper.FROM_DATEFORMAT));
							}
							if(personalData.getMobileNo2()!= null){
								hostelerTo.setMobileNo(personalData.getMobileNo2());
							}
							hostelerTo.setApplNoStaffID(Integer.toString(admAppln.getApplnNo()));
							Set<Student> student=admAppln.getStudents();
							String className=null;
							if(student!=null && !student.isEmpty()){
								Iterator<Student> docItr=student.iterator();
								while (docItr.hasNext()) {
									Student studentdet = (Student) docItr.next();
									studentdet.getRegisterNo();
									studentdet.getRollNo();
									if(studentdet.getClassSchemewise()!=null)
									className=studentdet.getClassSchemewise().getClasses().getName();
								}
							}
							Set<ApplnDoc> appDocs=admAppln.getApplnDocs();
							if(appDocs!=null && !appDocs.isEmpty()){
								Iterator<ApplnDoc> docItr=appDocs.iterator();
								while (docItr.hasNext()) {
								ApplnDoc applnDoc = (ApplnDoc) docItr.next();
									if(applnDoc.getIsPhoto()){
										if(applnDoc.getDocument()!=null){
											hostelerTo.setPhotoBytes(applnDoc.getDocument());
										}
									}
								}
							}
							Course course=admAppln.getCourseBySelectedCourseId();
							hostelerTo.setCourseName(course.getName());
							hostelerTo.setProgram(course.getProgram().getName());
							hostelerTo.setClassName(className);
							if(personalData.getEmail() != null){
								hostelerTo.setEmail(personalData.getEmail());
							}
							if(personalData.getPermanentAddressLine1() != null){
								hostelerTo.setPaddressLine1(personalData.getPermanentAddressLine1());
								}
							if(personalData.getPermanentAddressLine2() != null){
								hostelerTo.setPaddressLine2(personalData.getPermanentAddressLine2());
								}
							
							if(personalData.getPermanentAddressZipCode() != null){
								hostelerTo.setZip(personalData.getPermanentAddressZipCode());
								}
							String phone="";
							if(personalData.getPhNo1()!= null){
								phone=phone+personalData.getPhNo1();
								}
							if(personalData.getPhNo2()!= null){
								phone=phone+personalData.getPhNo2();
								}
							if(personalData.getPhNo3()!= null){
								phone=phone+personalData.getPhNo3();
								}
							hostelerTo.setPhone(phone);
							
							if(personalData.getGuardianAddressLine1()!= null){
								hostelerTo.setGaddressLine1(personalData.getGuardianAddressLine1());
								}
							
							if(personalData.getGuardianAddressLine2() != null){
								hostelerTo.setGaddressLine2(personalData.getGuardianAddressLine2());
								}
							phone="";
							if(personalData.getGuardianPh1() != null){
								phone=phone+personalData.getGuardianPh1();
								}
							if(personalData.getGuardianPh2() != null){
								phone=phone+personalData.getGuardianPh2();
								}
							if(personalData.getGuardianPh3() != null){
								phone=phone+personalData.getGuardianPh3();
								}
							hostelerTo.setGphone(phone);
							
							if(personalData.getGuardianAddressZipCode() != null){
								hostelerTo.setGzip(personalData.getGuardianAddressZipCode());
								}
							if(personalData.getCityByParentAddressCityId()!= null){
								hostelerTo.setCity(personalData.getCityByPermanentAddressCityId());
								}
							if(personalData.getStateByPermanentAddressStateId()!= null){
								hostelerTo.setState(personalData.getStateByPermanentAddressStateId().getName());
								}else{
									hostelerTo.setState(personalData.getPermanentAddressStateOthers());
								}
							if(personalData.getCountryByPermanentAddressCountryId()!= null){
								hostelerTo.setCountry(personalData.getCountryByPermanentAddressCountryId().getName());
								}else{
									hostelerTo.setCountry(personalData.getPermanentAddressCountryOthers());
								}
							if(personalData.getCityByGuardianAddressCityId() != null){
								hostelerTo.setGcity(personalData.getCityByGuardianAddressCityId());
								}
							if(personalData.getStateByGuardianAddressStateId()!= null){
								hostelerTo.setGstate(personalData.getStateByGuardianAddressStateId().getName());
								}else{
									hostelerTo.setGstate(personalData.getGuardianAddressStateOthers());
								}
							if(personalData.getCountryByGuardianAddressCountryId()!= null){
								hostelerTo.setGcountry(personalData.getCountryByGuardianAddressCountryId().getName());
								}else{
									hostelerTo.setGcountry(personalData.getGuardianAddressCountryOthers());
								}
						}
					}else{
						if(hlApplicationForm.getEmployee()!=null){
							Employee employee=hlApplicationForm.getEmployee();
							if(employee.getFirstName()!=null){
								name=name+employee.getFirstName();
							}
						/*	if(employee.getMiddleName()!=null){
								name=name+employee.getMiddleName();
							}
							if(employee.getLastName()!=null){
								name=name+employee.getLastName();
							}*/
							hostelerTo.setStudentName(name);
							if(employee.getDob() != null){
								hostelerTo.setDateofBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(employee.getDob()), HostelerDatabaseHelper.SQL_DATEFORMAT,HostelerDatabaseHelper.FROM_DATEFORMAT));
								}
						/*	if(employee.getMobile()!= null){
								hostelerTo.setMobileNo(employee.getMobile().toString());
								}*/
							if(employee.getEmail() != null){
								hostelerTo.setEmail(employee.getEmail());
								}
							if(employee.getPermanentAddressLine1()!= null){
								hostelerTo.setPaddressLine1(employee.getPermanentAddressLine1());
								}
							if(employee.getPermanentAddressLine2()!= null){
								hostelerTo.setPaddressLine2(employee.getPermanentAddressLine2());
								}
							if(employee.getPermanentAddressCity() != null){
								hostelerTo.setCity(employee.getPermanentAddressCity());
								}
							if(employee.getStateByPermanentAddressStateId()!= null){
								hostelerTo.setState(employee.getStateByPermanentAddressStateId().getName());
								}else{
									if(employee.getPermanentAddressStateOthers() != null){
										hostelerTo.setState(employee.getPermanentAddressStateOthers());
										}
								}
							if(employee.getCountryByPermanentAddressCountryId()!= null){
								hostelerTo.setCountry(employee.getCountryByPermanentAddressCountryId().getName());
								}
							if(employee.getPermanentAddressZip()!= null){
								hostelerTo.setZip(employee.getPermanentAddressZip());
								}
							if(employee.getCommunicationAddressLine1()!= null){
								hostelerTo.setGaddressLine1(employee.getCommunicationAddressLine1());
								}
							if(employee.getCommunicationAddressLine2()!= null){
								hostelerTo.setGaddressLine2(employee.getCommunicationAddressLine2());
								}
							if(employee.getCommunicationAddressCity()!= null){
								hostelerTo.setGcity(employee.getCommunicationAddressCity());
								}
							if(employee.getStateByCommunicationAddressStateId()!= null){
								hostelerTo.setGstate(employee.getStateByCommunicationAddressStateId().getName());
								}else{
									if(employee.getCommunicationAddressStateOthers()!= null){
										hostelerTo.setGstate(employee.getCommunicationAddressStateOthers());
										}
								}
							if(employee.getCountryByCommunicationAddressCountryId()!= null){
								hostelerTo.setGcountry(employee.getCountryByCommunicationAddressCountryId().getName());
								}
							if(employee.getCommunicationAddressZip() != null){
								hostelerTo.setGzip(employee.getCommunicationAddressZip());
								}
							String phone="";
							/*if(employee.getPhone1()!= null){
								phone=phone+employee.getPhone1();
								}
							if(employee.getPhone2()!= null){
								phone=phone+employee.getPhone2();
								}*/
							hostelerTo.setGphone(phone);
								hostelerTo.setApplNoStaffID(employee.getCode());
						}
					}
				}
		}
	return hostelerTo;
}
	
	
	/**
	 * written by balaji
	 * @param checkedInStatusId
	 * @return
	 * @throws Exception
	 */
	public static String commonSearch1(int checkedInStatusId)throws Exception{
		
		String search ="select h from HlRoomTransaction h ";
				return search;
	}
	
	/**
	 * Used to prepare the query
	 */
	public String searchCriteria(HostelerDatabaseForm databaseForm, int checkedInStatusId)throws Exception{
		String commonSearch = HostelerDatabaseHelper.commonSearch1(checkedInStatusId);
		StringBuffer buffer = new StringBuffer();
//		buffer.append(commonSearch);
		String join="";
		if(databaseForm.getHostelId().length() > 0){
			String hostel = " and h.hlApplicationForm.hlHostelByHlApprovedHostelId.id="+Integer.parseInt(databaseForm.getHostelId().trim());
			buffer.append(hostel);
		}
		if(databaseForm.getSearchBy().length() > 0 && databaseForm.getTextToSearch().length() > 0){
			if(databaseForm.getSearchBy().equals(CMSConstants.HOSTEL_HOSTLER_DATABASE_ROOMTYPE)){
				String roomType= " and h.hlRoom.hlRoomType.name like '%" +databaseForm.getTextToSearch().trim() +"%'";
				buffer.append(roomType);
			}
			else if(databaseForm.getSearchBy().equals(CMSConstants.HOSTEL_HOSTLER_DATABASE_REGNO)){
				join="join  h.admAppln.students s ";
				String regNo = " and s.registerNo='" + databaseForm.getTextToSearch().trim() +"' and s.isActive = 1";
				buffer.append(regNo);
			}
			else if(databaseForm.getSearchBy().equals(CMSConstants.HOSTEL_HOSTLER_DATABASE_ROOMNO)){
				String roomNo = " and h.hlRoom.name='" + databaseForm.getTextToSearch().trim() +"' and h.hlRoom.isActive = 1";
				buffer.append(roomNo);
			}
			else if(databaseForm.getSearchBy().equals(CMSConstants.HOSTEL_HOSTLER_DATABASE_STAFFID)){
				String emp = " and h.hlApplicationForm.employee.code = '" + databaseForm.getTextToSearch().trim() +"'";
				buffer.append(emp);
			}
		}
		
		String common=" where h.hlApplicationForm.isActive=1 " +
				" and h.hlApplicationForm.hlStatus.id="+checkedInStatusId +
				" and h.isActive=1 and h.hlApplicationForm.hlStatus.id="+checkedInStatusId; 
		return commonSearch+join+common+buffer.toString();
	}
	
	/**
	 * @param hlAppId
	 * @return
	 */
	public static String getQuery1(String hlAppId) {	 
		String searchCriteria = "from HlApplicationForm hl " +
				" where hl.id='"+hlAppId+"'"+" and hl.isActive=1";
				return searchCriteria;
	}
}
