package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.PrintShortageAttendanceForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.to.admission.PrintShortageAttendanceTo;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;

public class PrintShortageAttendanceHelper {
	/**
	 * Singleton object of printShortageAttendanceHelper
	 */
	private static volatile PrintShortageAttendanceHelper printShortageAttendanceHelper = null;
	private static final Log log = LogFactory.getLog(PrintShortageAttendanceHelper.class);
	private PrintShortageAttendanceHelper() {
		
	}
	/**
	 * return singleton object of printShortageAttendanceHelper.
	 * @return
	 */
	public static PrintShortageAttendanceHelper getInstance() {
		if (printShortageAttendanceHelper == null) {
			printShortageAttendanceHelper = new PrintShortageAttendanceHelper();
		}
		return printShortageAttendanceHelper;
	}
	/**
	 * @param printShortageAttendanceForm
	 * @return
	 * @throws Exception
	 */
	public String getListOfStudentQuery(PrintShortageAttendanceForm printShortageAttendanceForm) throws Exception{
		String query="select a.student,";
			query=query+commonSearch(printShortageAttendanceForm)+" group by a.student";
		return query;
	}
	/**
	 * @param printShortageAttendanceForm
	 * @return
	 * @throws Exception
	 */
	private String commonSearch(PrintShortageAttendanceForm printShortageAttendanceForm) throws Exception{
		String commonQuery="sum(a.attendance.hoursHeld),a.student.registerNo" +
				" from AttendanceStudent a " +
				" join a.attendance.attendanceClasses ac " +
				" join a.student.classSchemewise c " +
				" where a.student.isActive=1 " +
				" and (a.student.isHide = 0 or a.student.isHide is null)" +
				" and a.attendance.isCanceled=0"+
				" and a.student.admAppln.courseBySelectedCourseId.program.id="+printShortageAttendanceForm.getProgramId()+
				" and a.student.admAppln.appliedYear='"+printShortageAttendanceForm.getYear()+"'"+
				" and a.student.admAppln.courseBySelectedCourseId.program.programType.id="+printShortageAttendanceForm.getProgramTypeId()+" and a.attendance.attendanceDate between '"+CommonUtil.ConvertStringToSQLDate(printShortageAttendanceForm.getStartDate())+"' and '"+CommonUtil.ConvertStringToSQLDate(printShortageAttendanceForm.getEndDate())+"' and c.id=ac.classSchemewise.id " +
				" and a.student.admAppln.isCancelled=0";
		if(printShortageAttendanceForm.getCourseId()!=null && !printShortageAttendanceForm.getCourseId().isEmpty())
			commonQuery+=" and a.student.admAppln.courseBySelectedCourseId.id=" +printShortageAttendanceForm.getCourseId();
			
		return commonQuery;
	}
	/**
	 * @param printShortageAttendanceForm
	 * @return
	 * @throws Exception
	 */
	public String getMapOfStudentQuery(
			PrintShortageAttendanceForm printShortageAttendanceForm) throws Exception {
		String query="select a.student.id,";
		query=query+commonSearch(printShortageAttendanceForm)+" and (a.isPresent=1 or a.isCoCurricularLeave=1) group by a.student";
	return query;
	}
	/**
	 * @param listOfAttedance
	 * @param attMap
	 * @return
	 * @throws Exception
	 */
	public static List<PrintShortageAttendanceTo> getFinalList(List<Object[]> listOfAttedance, Map<Integer, Integer> attMap,PrintShortageAttendanceForm printShortageAttendanceForm,List<Integer> detainedStudentList) throws Exception{
		List<PrintShortageAttendanceTo> finalList=new ArrayList<PrintShortageAttendanceTo>();
		float percentageFrom=Float.parseFloat(printShortageAttendanceForm.getPercentageFrom());
		float percentageTo=Float.parseFloat(printShortageAttendanceForm.getPercentageTo());
		if(listOfAttedance!=null && !listOfAttedance.isEmpty()){
			Iterator<Object[]> itr=listOfAttedance.iterator();
			String studentName="";
			String address="";
			
			while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				Student student=(Student)objects[0];
				if(student.getIsHide()!=null){
				if(!detainedStudentList.contains(objects[2]) && student.getIsHide()==false){
				if(objects[1]!=null){
					PrintShortageAttendanceTo printShortageAttendanceTo=new PrintShortageAttendanceTo();
					studentName="";
					address="";
					float totalhours=Integer.parseInt(objects[1].toString());
					float hoursAtt=0;
					if(attMap.containsKey(student.getId())){
						hoursAtt=attMap.get(student.getId());
					}else{
						hoursAtt=0;
					}
					float perc=hoursAtt/totalhours;
					float per=Math.round((perc)*100);
					if(per>=percentageFrom && per<=percentageTo){
						PersonalData personalData=student.getAdmAppln().getPersonalData();
							if (student.getAdmAppln().getAdmapplnAdditionalInfo() != null) {
									Iterator<AdmapplnAdditionalInfo> iterator = student.getAdmAppln().getAdmapplnAdditionalInfo().iterator();
									while (iterator.hasNext()) {
										AdmapplnAdditionalInfo bo=iterator.next();
											if(bo.getTitleFather()!=null && !bo.getTitleFather().isEmpty()){
												printShortageAttendanceTo.setTitleFather(bo.getTitleFather());
											}
											if(bo.getTitleMother()!=null && !bo.getTitleMother().isEmpty()){
												printShortageAttendanceTo.setTitleMother(bo.getTitleMother());
											}
											if(bo.getCommToBeSentTo()!=null && !bo.getCommToBeSentTo().isEmpty()){
												printShortageAttendanceTo.setCommSentTo(bo.getCommToBeSentTo());
											}
										}
							}
						if(personalData.getFirstName()!=null){
							studentName=studentName+personalData.getFirstName();
						}
						if(personalData.getMiddleName()!=null){
							studentName=studentName+" "+personalData.getMiddleName();
						}
						if(personalData.getLastName()!=null){
							studentName=studentName+" "+personalData.getLastName();
						}
						if(personalData.getPermanentAddressLine1()!=null && !personalData.getPermanentAddressLine1().trim().isEmpty()){
							address=address+personalData.getPermanentAddressLine1()+",<BR>";
						}
						if(personalData.getPermanentAddressLine2()!=null && !personalData.getPermanentAddressLine2().trim().isEmpty()){
							address=address+personalData.getPermanentAddressLine2()+",<BR>";
						}
						if(personalData.getCityByPermanentAddressCityId()!=null && !personalData.getCityByPermanentAddressCityId().trim().isEmpty()){
							address=address+personalData.getCityByPermanentAddressCityId()+",<BR>";
						}
						if(personalData.getStateByPermanentAddressStateId()!=null){
							address=address+personalData.getStateByPermanentAddressStateId().getName()+",<BR>";
						}else if(personalData.getPermanentAddressStateOthers()!=null && !personalData.getPermanentAddressStateOthers().trim().isEmpty()){
							address=address+personalData.getPermanentAddressStateOthers()+",<BR>";
						}
						if(personalData.getCountryByPermanentAddressCountryId()!=null){
							address=address+personalData.getCountryByPermanentAddressCountryId().getName()+",<BR>";
						}else if(personalData.getPermanentAddressCountryOthers()!=null && !personalData.getPermanentAddressCountryOthers().trim().isEmpty()){
							address=address+personalData.getPermanentAddressCountryOthers()+",<BR>";
						}
						printShortageAttendanceTo.setStudentName(studentName);
						printShortageAttendanceTo.setParentFullAddress(address);
						printShortageAttendanceTo.setPincode(personalData.getPermanentAddressZipCode()!=null?personalData.getPermanentAddressZipCode():"");
						Set<StudentLogin> studentLogins=student.getStudentLogins();
						if(studentLogins!=null && !studentLogins.isEmpty()){
							Iterator<StudentLogin> stuitr=studentLogins.iterator();
							while (stuitr.hasNext()) {
								StudentLogin studentLogin = (StudentLogin) stuitr.next();
								if(studentLogin.getIsActive() && studentLogin.getIsStudent()){
									printShortageAttendanceTo.setUserName(studentLogin.getUserName());
									printShortageAttendanceTo.setPassword(EncryptUtil.getInstance().decryptDES(studentLogin.getPassword()));
									
								}
							}
						}
				printShortageAttendanceTo.setRegisterNumber(student.getRegisterNo());
				printShortageAttendanceTo.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
		if((printShortageAttendanceTo.getCommSentTo()!=null && !printShortageAttendanceTo.getCommSentTo().isEmpty()) ){
			if(printShortageAttendanceTo.getCommSentTo().equalsIgnoreCase("F"))
				{
					if(printShortageAttendanceTo.getTitleFather()!=null && printShortageAttendanceTo.getTitleFather().equalsIgnoreCase("late")){
							if(printShortageAttendanceTo.getTitleMother()!=null && printShortageAttendanceTo.getTitleMother().equalsIgnoreCase("late"))
							{
								if(personalData.getGuardianName()!=null && !personalData.getGuardianName().isEmpty())		
										{
											
											address="";
											if(personalData.getGuardianAddressLine1()!=null && !personalData.getGuardianAddressLine1().trim().isEmpty()){
												address=address+personalData.getGuardianAddressLine1()+",<BR>";
											}
											if(personalData.getGuardianAddressLine2()!=null && !personalData.getGuardianAddressLine2().trim().isEmpty()){
												address=address+personalData.getGuardianAddressLine2()+",<BR>";
											}
											if(personalData.getGuardianAddressLine3()!=null && !personalData.getGuardianAddressLine3().trim().isEmpty()){
												address=address+personalData.getGuardianAddressLine3()+",<BR>";
											}
											if(personalData.getCityByGuardianAddressCityId()!=null && !personalData.getCityByGuardianAddressCityId().trim().isEmpty()){
												address=address+personalData.getCityByGuardianAddressCityId()+",<BR>";
											}
											if(personalData.getStateByGuardianAddressStateId()!=null){
												address=address+personalData.getStateByGuardianAddressStateId().getName()+",<BR>";
											}else if(personalData.getGuardianAddressStateOthers()!=null && !personalData.getGuardianAddressStateOthers().trim().isEmpty()){
												address=address+personalData.getGuardianAddressStateOthers()+",<BR>";
											}
											if(personalData.getCountryByGuardianAddressCountryId()!=null){
												address=address+personalData.getCountryByGuardianAddressCountryId().getName()+",<BR>";
											}else if(personalData.getGuardianAddressCountryOthers()!=null && !personalData.getGuardianAddressCountryOthers().trim().isEmpty()){
												address=address+personalData.getGuardianAddressCountryOthers()+",<BR>";
											}
											printShortageAttendanceTo.setPincode(personalData.getGuardianAddressZipCode()!=null?personalData.getGuardianAddressZipCode():"");
											if(!address.isEmpty()){
												printShortageAttendanceTo.setParentFullAddress(address);
												printShortageAttendanceTo.setParentName(personalData.getGuardianName());
											}
										}
									}else{
										printShortageAttendanceTo.setParentName(personalData.getMotherName());
									}
								}else{
									printShortageAttendanceTo.setParentName(personalData.getFatherName());
								}
				}
				else if(printShortageAttendanceTo.getCommSentTo().equalsIgnoreCase("M"))
				{
					if(printShortageAttendanceTo.getTitleMother()!=null && printShortageAttendanceTo.getTitleMother().equalsIgnoreCase("late"))
							{
								if(personalData.getGuardianName()!=null && !personalData.getGuardianName().isEmpty())		
										{
											
											address="";
											if(personalData.getGuardianAddressLine1()!=null && !personalData.getGuardianAddressLine1().trim().isEmpty()){
												address=address+personalData.getGuardianAddressLine1()+",<BR>";
											}
											if(personalData.getGuardianAddressLine2()!=null && !personalData.getGuardianAddressLine2().trim().isEmpty()){
												address=address+personalData.getGuardianAddressLine2()+",<BR>";
											}
											if(personalData.getGuardianAddressLine3()!=null && !personalData.getGuardianAddressLine3().trim().isEmpty()){
												address=address+personalData.getGuardianAddressLine3()+",<BR>";
											}
											if(personalData.getCityByGuardianAddressCityId()!=null && !personalData.getCityByGuardianAddressCityId().trim().isEmpty()){
												address=address+personalData.getCityByGuardianAddressCityId()+",<BR>";
											}
											if(personalData.getStateByGuardianAddressStateId()!=null){
												address=address+personalData.getStateByGuardianAddressStateId().getName()+",<BR>";
											}else if(personalData.getGuardianAddressStateOthers()!=null && !personalData.getGuardianAddressStateOthers().trim().isEmpty()){
												address=address+personalData.getGuardianAddressStateOthers()+",<BR>";
											}
											if(personalData.getCountryByGuardianAddressCountryId()!=null){
												address=address+personalData.getCountryByGuardianAddressCountryId().getName()+",<BR>";
											}else if(personalData.getGuardianAddressCountryOthers()!=null && !personalData.getGuardianAddressCountryOthers().trim().isEmpty()){
												address=address+personalData.getGuardianAddressCountryOthers()+",<BR>";
											}
											printShortageAttendanceTo.setPincode(personalData.getGuardianAddressZipCode()!=null?personalData.getGuardianAddressZipCode():"");
											if(!address.isEmpty()){
												printShortageAttendanceTo.setParentFullAddress(address);
												printShortageAttendanceTo.setParentName(personalData.getGuardianName());
											}
										}
									}else{
										printShortageAttendanceTo.setParentName(personalData.getMotherName());
								}
						}else{
							if(personalData.getGuardianName()!=null && !personalData.getGuardianName().isEmpty())		
							{
								
								address="";
								if(personalData.getGuardianAddressLine1()!=null && !personalData.getGuardianAddressLine1().trim().isEmpty()){
									address=address+personalData.getGuardianAddressLine1()+",<BR>";
								}
								if(personalData.getGuardianAddressLine2()!=null && !personalData.getGuardianAddressLine2().trim().isEmpty()){
									address=address+personalData.getGuardianAddressLine2()+",<BR>";
								}
								if(personalData.getGuardianAddressLine3()!=null && !personalData.getGuardianAddressLine3().trim().isEmpty()){
									address=address+personalData.getGuardianAddressLine3()+",<BR>";
								}
								if(personalData.getCityByGuardianAddressCityId()!=null && !personalData.getCityByGuardianAddressCityId().trim().isEmpty()){
									address=address+personalData.getCityByGuardianAddressCityId()+",<BR>";
								}
								if(personalData.getStateByGuardianAddressStateId()!=null){
									address=address+personalData.getStateByGuardianAddressStateId().getName()+",<BR>";
								}else if(personalData.getGuardianAddressStateOthers()!=null && !personalData.getGuardianAddressStateOthers().trim().isEmpty()){
									address=address+personalData.getGuardianAddressStateOthers()+",<BR>";
								}
								if(personalData.getCountryByGuardianAddressCountryId()!=null){
									address=address+personalData.getCountryByGuardianAddressCountryId().getName()+",<BR>";
								}else if(personalData.getGuardianAddressCountryOthers()!=null && !personalData.getGuardianAddressCountryOthers().trim().isEmpty()){
									address=address+personalData.getGuardianAddressCountryOthers()+",<BR>";
								}
								printShortageAttendanceTo.setPincode(personalData.getGuardianAddressZipCode()!=null?personalData.getGuardianAddressZipCode():"");
								if(!address.isEmpty()){
								printShortageAttendanceTo.setParentFullAddress(address);
								printShortageAttendanceTo.setParentName(personalData.getGuardianName());
								}
							}
						}
		}
		else
		{
				if(printShortageAttendanceTo.getTitleFather()!=null && printShortageAttendanceTo.getTitleFather().equalsIgnoreCase("late")){
					if(printShortageAttendanceTo.getTitleMother()!=null && printShortageAttendanceTo.getTitleMother().equalsIgnoreCase("late"))
					{
						if(personalData.getGuardianName()!=null && !personalData.getGuardianName().isEmpty())		
								{
									
									address="";
									if(personalData.getGuardianAddressLine1()!=null && !personalData.getGuardianAddressLine1().trim().isEmpty()){
										address=address+personalData.getGuardianAddressLine1()+",<BR>";
									}
									if(personalData.getGuardianAddressLine2()!=null && !personalData.getGuardianAddressLine2().trim().isEmpty()){
										address=address+personalData.getGuardianAddressLine2()+",<BR>";
									}
									if(personalData.getGuardianAddressLine3()!=null && !personalData.getGuardianAddressLine3().trim().isEmpty()){
										address=address+personalData.getGuardianAddressLine3()+",<BR>";
									}
									if(personalData.getCityByGuardianAddressCityId()!=null && !personalData.getCityByGuardianAddressCityId().trim().isEmpty()){
										address=address+personalData.getCityByGuardianAddressCityId()+",<BR>";
									}
									if(personalData.getStateByGuardianAddressStateId()!=null){
										address=address+personalData.getStateByGuardianAddressStateId().getName()+",<BR>";
									}else if(personalData.getGuardianAddressStateOthers()!=null && !personalData.getGuardianAddressStateOthers().trim().isEmpty()){
										address=address+personalData.getGuardianAddressStateOthers()+",<BR>";
									}
									if(personalData.getCountryByGuardianAddressCountryId()!=null){
										address=address+personalData.getCountryByGuardianAddressCountryId().getName()+",<BR>";
									}else if(personalData.getGuardianAddressCountryOthers()!=null && !personalData.getGuardianAddressCountryOthers().trim().isEmpty()){
										address=address+personalData.getGuardianAddressCountryOthers()+",<BR>";
									}
									printShortageAttendanceTo.setPincode(personalData.getGuardianAddressZipCode()!=null?personalData.getGuardianAddressZipCode():"");
									if(!address.isEmpty()){
										printShortageAttendanceTo.setParentFullAddress(address);
										printShortageAttendanceTo.setParentName(personalData.getGuardianName());
									}
								}
							}else{
								printShortageAttendanceTo.setParentName(personalData.getMotherName());
							}
						}else{
							 printShortageAttendanceTo.setParentName(personalData.getFatherName());
						}	
					}
				if(printShortageAttendanceTo.getParentName()==null || printShortageAttendanceTo.getParentName().isEmpty()){
					if((printShortageAttendanceTo.getCommSentTo()!=null && !printShortageAttendanceTo.getCommSentTo().isEmpty()) && printShortageAttendanceTo.getCommSentTo().equalsIgnoreCase("F"))
					{
						printShortageAttendanceTo.setParentName(personalData.getFatherName());
					}else if((printShortageAttendanceTo.getCommSentTo()!=null && !printShortageAttendanceTo.getCommSentTo().isEmpty()) && printShortageAttendanceTo.getCommSentTo().equalsIgnoreCase("M"))
					{
						printShortageAttendanceTo.setParentName(personalData.getMotherName());
					}
				}
						printShortageAttendanceTo.setPercentage(Float.toString(per));
						finalList.add(printShortageAttendanceTo);
		
			}
					
				}
			}	
				}
				
			}
		}
		return finalList;
	}
	/**
	 * @param finalList
	 * @return
	 * @throws Exception
	 */
	public List<String> getPrintList(List<PrintShortageAttendanceTo> finalList,HttpServletRequest request) throws Exception {
		List<String> printList=new ArrayList<String>();
		String logoPath="";
		byte[] logo = null;
		String signaturePath="";
		signaturePath ="images/att_shortage_signature.jpg";
		String signature = "<img src='"+signaturePath+ "' width='133' height='61' >";
		HttpSession session = request.getSession(false);
		
		Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
		if (organisation != null) {
			logo = organisation.getLogo();
		}
		if (session != null) {
			session.setAttribute("LogoBytes", logo);
		}
		logoPath = request.getContextPath();
		logoPath = "<img src="
				+ logoPath
				+ "/LogoServlet alt='Logo not available' width='210' height='100' >";
		String date = CommonUtil.getStringDate(new Date());
		if(finalList!=null && !finalList.isEmpty()){
			Iterator<PrintShortageAttendanceTo> itr=finalList.iterator();
			TemplateHandler temphandle=TemplateHandler.getInstance();
			List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.ATTENDANCE_SHORTAGE_TEMPLATE);
			if(list != null && !list.isEmpty()) {
				String desc ="";
				if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
					desc = list.get(0).getTemplateDescription();
				}
				while (itr.hasNext()) {
					PrintShortageAttendanceTo printShortageAttendanceTo = (PrintShortageAttendanceTo) itr.next();
					String message = desc;
					if(printShortageAttendanceTo.getParentName()!=null)
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME,printShortageAttendanceTo.getParentName());
					if(printShortageAttendanceTo.getStudentName()!=null)
					message = message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, printShortageAttendanceTo.getStudentName());
					if(printShortageAttendanceTo.getCourseName()!=null)
					message = message.replace(CMSConstants.TEMPLATE_COURSE, printShortageAttendanceTo.getCourseName());
					if(printShortageAttendanceTo.getParentFullAddress()!=null)
					message = message.replace(CMSConstants.TEMPLATE_PERMANENT_ADDRESS, printShortageAttendanceTo.getParentFullAddress());
					if(printShortageAttendanceTo.getPincode()!=null)
					message = message.replace(CMSConstants.TEMPLATE_PINCODE, printShortageAttendanceTo.getPincode());
					if(printShortageAttendanceTo.getUserName()!=null)
					message = message.replace(CMSConstants.TEMPLATE_USERNAME, printShortageAttendanceTo.getUserName());
					if(printShortageAttendanceTo.getPassword()!=null)
					message = message.replace(CMSConstants.TEMPLATE_PASSWORD, printShortageAttendanceTo.getPassword());
					if(printShortageAttendanceTo.getRegisterNumber()!=null)
					message = message.replace(CMSConstants.TEMPLATE_REGISTER_NO, printShortageAttendanceTo.getRegisterNumber());
					if(printShortageAttendanceTo.getPercentage()!=null)
					message = message.replace(CMSConstants.TEMPLATE_PERCENTAGE, printShortageAttendanceTo.getPercentage());
					message = message.replace(CMSConstants.TEMPLATE_DATE, date);
					message = message.replace(CMSConstants.TEMPLATE_LOGO,logoPath);
					message = message.replace(CMSConstants.TEMPLATE_SIGNATURE, signature);
					printList.add(message);
				}
			}
		}
		return printList;
	}
	/**
	 * to short the attendance short list based on regNo
	 * @param finalList
	 * @param printShortageAttendanceForm
	 * @return
	 */
	public static List<PrintShortageAttendanceTo> getFinalShortAttendenceList(List<PrintShortageAttendanceTo> finalList,PrintShortageAttendanceForm printShortageAttendanceForm){
		List<PrintShortageAttendanceTo> attendanceTosList=new ArrayList<PrintShortageAttendanceTo>();
		Iterator<PrintShortageAttendanceTo> iterator=finalList.iterator();
		PrintShortageAttendanceTo printShortageAttendanceTo=null;
		if (printShortageAttendanceForm.getRegNoFrom()!=null && printShortageAttendanceForm.getRegNoTo()!=null
				&& !printShortageAttendanceForm.getRegNoFrom().isEmpty()&& !printShortageAttendanceForm.getRegNoTo().isEmpty()) {
				while (iterator.hasNext()) {
					printShortageAttendanceTo = (PrintShortageAttendanceTo) iterator.next();
					int regNoFrom=Integer.valueOf(printShortageAttendanceForm.getRegNoFrom());
					int regNoTo=Integer.valueOf(printShortageAttendanceForm.getRegNoTo());
					int attendeceRegNo=Integer.valueOf(printShortageAttendanceTo.getRegisterNumber());
					for(int i=regNoFrom;i<=regNoTo;i++){
						if(i==attendeceRegNo){
							attendanceTosList.add(printShortageAttendanceTo);
						}
					}
				}
		}else if(printShortageAttendanceForm.getRegNoFrom()!=null && printShortageAttendanceForm.getRegNoTo().isEmpty() && !printShortageAttendanceForm.getRegNoFrom().isEmpty()){
			while (iterator.hasNext()) {
				printShortageAttendanceTo = (PrintShortageAttendanceTo) iterator.next();
				int regNoFrom=Integer.valueOf(printShortageAttendanceForm.getRegNoFrom());
				int attendeceRegNo=Integer.valueOf(printShortageAttendanceTo.getRegisterNumber());
					if(regNoFrom==attendeceRegNo){
						attendanceTosList.add(printShortageAttendanceTo);
					}
			}
		}else{
			attendanceTosList=finalList;
		}
		if(!attendanceTosList.isEmpty()){
		Collections.sort(attendanceTosList, new Comparator<PrintShortageAttendanceTo>() {
			  public int compare(PrintShortageAttendanceTo c1, PrintShortageAttendanceTo c2) {
				  /*code added by sudhir */
				  try{
					  Integer.valueOf(c1.getRegisterNumber());
					  Integer.valueOf(c2.getRegisterNumber());
				  }catch (Exception e) {
					return -1;
				  }
				  /*code added by sudhir */
				    if (Integer.valueOf(c1.getRegisterNumber()) > Integer.valueOf(c2.getRegisterNumber())) return 1;
				    if (Integer.valueOf(c1.getRegisterNumber()) < Integer.valueOf(c2.getRegisterNumber())) return -1;
				    return 0;
				  }});
		}
		return attendanceTosList;
	}
}
