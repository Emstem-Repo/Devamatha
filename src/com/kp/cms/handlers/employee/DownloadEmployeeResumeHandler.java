package com.kp.cms.handlers.employee;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admin.StudentUploadPhotoAction;
import com.kp.cms.actions.employee.DownloadEmployeeResumeAction;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmpOnlineResumeUsers;
import com.kp.cms.bo.employee.EmployeeSubject;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.DownloadEmployeeResumeForm;
import com.kp.cms.helpers.employee.DownloadEmployeeResumeHelper;
import com.kp.cms.to.admin.DownloadEmployeeResumeTO;
import com.kp.cms.to.admin.EmpOnlineEducationalDetailsTO;
import com.kp.cms.to.admin.EmpOnlinePreviousExperienceTO;
import com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction;
import com.kp.cms.transactionsimpl.employee.DownloadEmployeeResumeTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PasswordGenerator;
import com.kp.cms.utilities.jms.MailTO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class DownloadEmployeeResumeHandler {
	/**
	 * Singleton object of DownloadEmployeeResumeHandler
	 */
	private static volatile DownloadEmployeeResumeHandler downloadEmployeeResumeHandler = null;
	private static final Log log = LogFactory.getLog(DownloadEmployeeResumeHandler.class);
	
	
	private DownloadEmployeeResumeHandler() {
		
	}
	/**
	 * return singleton object of downloadEmployeeResumeHandler.
	 * @return
	 */
	public static DownloadEmployeeResumeHandler getInstance() {
		if (downloadEmployeeResumeHandler == null) {
			downloadEmployeeResumeHandler = new DownloadEmployeeResumeHandler();
		}
		return downloadEmployeeResumeHandler;
	}
	public void setEmployeeDetailsToForm(DownloadEmployeeResumeForm downloadEmployeeResumeForm) throws Exception{
		IDownloadEmployeeResumeTransaction transaction = DownloadEmployeeResumeTransactionImpl.getInstance();
		List<Department> departmentList = transaction.getDepartmentList();
		List<Designation> designationList = transaction.getDesignationList();
		List<EmpQualificationLevel> qualificationList = transaction.getEmpQualificationList();
		Map<Integer,String> empSubjects = transaction.getEmployeeSubjects();
		downloadEmployeeResumeForm.setDepartmentList(departmentList);
		downloadEmployeeResumeForm.setDesignationList(designationList);
		downloadEmployeeResumeForm.setQualificationList(qualificationList);
		downloadEmployeeResumeForm.setEmpSubjects(empSubjects);
	}
	/**
	 * @param downloadEmployeeResumeForm
	 * @return
	 * @throws Exception
	 */
	public List<DownloadEmployeeResumeTO> getEmpDetails( DownloadEmployeeResumeForm downloadEmployeeResumeForm) throws Exception {
		IDownloadEmployeeResumeTransaction transaction = DownloadEmployeeResumeTransactionImpl.getInstance();
		List<EmpOnlineResume> empOnlineResumes = transaction.getEmployeeDetails(downloadEmployeeResumeForm);
		List<EmpOnlineResumeUsers> empOnlineResumeUsers = transaction.getEmployeeForwardedDetails(downloadEmployeeResumeForm);
		Map<Integer,String> usersMap = DownloadEmployeeResumeHelper.getInstance().populateBOListToMap(empOnlineResumeUsers);
		List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs = DownloadEmployeeResumeHelper.getInstance().convertBoToTo(empOnlineResumes,usersMap);
		return downloadEmployeeResumeTOs;
	}
	/**
	 * @param downloadEmployeeResumeForm
	 * @param request 
	 * @return
	 * @throws Exception
	 */
	public boolean sendMailToAdmin(	DownloadEmployeeResumeForm downloadEmployeeResumeForm,String realPath,HttpSession session, HttpServletRequest request) throws Exception{
		boolean sent=false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
			String adminmail=downloadEmployeeResumeForm.getEmailIds();
			String subject="Online Resume - Careers at Christ University";
			//String username="Christ University";
			String fromName = prop.getProperty(CMSConstants.EMPLOYEE_ONLINE_RESUME_FROMNAME);
			String fromAddress=prop.getProperty(CMSConstants.EMPLOYEE_ONLINE_RESUME_FROM_MAIL);
			Map<String,String> msg = getMessage(downloadEmployeeResumeForm,session,request);
			String[] mailIds = adminmail.split(",");
			for (int i = 0; i < mailIds.length; i++) {
				String toAddress=mailIds[i];
				sent=sendMail(toAddress, subject, msg, fromName,fromAddress,downloadEmployeeResumeForm,realPath,request);
				if(sent && downloadEmployeeResumeForm.getAppNosList()!=null){
					IDownloadEmployeeResumeTransaction transaction = DownloadEmployeeResumeTransactionImpl.getInstance();
					List<String> appNos=downloadEmployeeResumeForm.getAppNosList();
					Iterator itr=appNos.iterator();
					while(itr.hasNext()){
						String appNo = itr.next().toString();
						transaction.setStatus(Integer.parseInt(appNo));
					}
					downloadEmployeeResumeForm.setAppNosList(null);
				}
			}
		return sent;
	}
	/**
	 * @param downloadEmployeeResumeForm
	 * @param request 
	 * @return
	 * @throws Exception 
	 */
	private Map<String,String> getMessage(
			DownloadEmployeeResumeForm downloadEmployeeResumeForm,HttpSession session, HttpServletRequest request) throws Exception {
		
		//List<String> messages=new ArrayList<String>();
		Map<String,String> messages=new HashMap<String, String>();
		String department="";
		List<DownloadEmployeeResumeTO> tos = downloadEmployeeResumeForm.getDownloadEmployeeResumeTOs();
		List<String> appNosList =new ArrayList<String>();
		if(tos != null){
			Iterator<DownloadEmployeeResumeTO> iterator = tos.iterator();
			while (iterator.hasNext()) 
			{
			    DownloadEmployeeResumeTO downloadEmployeeResumeTO = (DownloadEmployeeResumeTO) iterator.next();
				if(downloadEmployeeResumeTO.getChecked() != null && !downloadEmployeeResumeTO.getChecked().isEmpty()){
					String msg = "";
					String appNo="";
					if(downloadEmployeeResumeTO.getChecked().equalsIgnoreCase("on")){
						IDownloadEmployeeResumeTransaction transaction = DownloadEmployeeResumeTransactionImpl.getInstance();
						String empId = String.valueOf(downloadEmployeeResumeTO.getId());
						EmpOnlineResume empOnlineResume = transaction.getDetailsForEmployee(empId);
						//code added by sudhir
						if(empOnlineResume.getEmpPhoto()!=null){
							try{
							byte[] photo = empOnlineResume.getEmpPhoto();  
							String regno = empOnlineResume.getApplicationNo()+ ".jpg";
							String relativePath;
							String s1=request.getRealPath("");
							Properties prop = new Properties();
							InputStream in = DownloadEmployeeResumeAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
					        prop.load(in);
					        String path=prop.getProperty("knowledgepro.admin.downloadResumePhotos.photosource");
					        relativePath=s1+path;
					        BufferedImage imag=ImageIO.read(new ByteArrayInputStream(photo));
					        File f = new File(relativePath);
							ImageIO.write(imag, "jpg",new File(f,regno));
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						}
						//
						List<EmpOnlineEducationalDetails> eduDetails = transaction.getEmployeeEducationDetails(empId);
						List<EmpOnlinePreviousExperience> teachingExperience = transaction.getEmployeeExperienceDetails(empId);
						 List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs = DownloadEmployeeResumeHelper.getInstance().convertBoToToForPrint(empOnlineResume, teachingExperience, downloadEmployeeResumeForm,session);
						downloadEmployeeResumeForm.setTos(downloadEmployeeResumeTOs);
						 List<EmpOnlineEducationalDetailsTO> empEduDetails = DownloadEmployeeResumeHelper.getInstance().convertBoToToForEduDetails(eduDetails,downloadEmployeeResumeForm);
						List<EmpOnlinePreviousExperienceTO> expTos = DownloadEmployeeResumeHelper.getInstance().convertBoToToForExpDetails(teachingExperience,empOnlineResume,downloadEmployeeResumeForm);
						downloadEmployeeResumeForm.setTeachingExperience(expTos);
						downloadEmployeeResumeForm.setEmpEducationalDetails(empEduDetails);
						appNo=empOnlineResume.getApplicationNo();
						appNosList.add(appNo);
						if(empOnlineResume.getDepartment()!=null)
							department=empOnlineResume.getDepartment().getName();
						msg = msg + " <html> <body> <table width='100%' border='0' cellpadding='0' cellspacing='0'>" +
						"<br>" + "</br>" +
						"<tr>" +
						"<td align='center' ><strong>Application Form For " +
				       empOnlineResume.getPostAppliedDesig()+" Staff"+
				        "<div align='right'>Application No:"+empOnlineResume.getApplicationNo()+"</div></strong></td></tr></table>"+
								"<table style='border:1px solid #000000' rules='all' width='100%' border='1'>"+
									"<tr>"+
										"<td width='43%'>Post Applied For</td><td align='left' width='57%'> "+empOnlineResume.getPostAppliedDesig()+"</td>"+
									"</tr>"+
									"<tr>"+
										"<td >Department</td><td align='left' > "+department+" </td>"+
									"</tr>"+
									"<tr>"+
											"<td >Job Code</td><td align='left' > ";
											if(empOnlineResume.getJobCode() != null){
												msg = msg +empOnlineResume.getJobCode();
											}
											msg = msg + "</td>"+
									"</tr>"+
									"<tr>"+
										"<td >Name & Current Address:</td><td align='left' > "+empOnlineResume.getName()+" <BR/> "+empOnlineResume.getCurrentAddressLine1()+"  <BR/> ";
											if(empOnlineResume.getAddressLine2() != null){
												msg = msg + empOnlineResume.getCurrentAddressLine2();
											}
											msg =msg + "  <BR/>  ";
											if(empOnlineResume.getAddressLine3() != null){
												msg = msg + empOnlineResume.getCurrentAddressLine3();
											}
											msg =msg + "</td>"+
									"</tr>"+
									/*"<tr>"+
										"<td>Current Location</td><td align='left'><STRONG> "+ empOnlineResume.getCurrentCity()+" </STRONG></td>"+
									"</tr>"+*/
									"<tr>"+
										"<td >Nationality</td><td align='left' > "+ empOnlineResume.getNationality().getName()+" </td>"+
									"</tr>"+
									"<tr>"+
										"<td >Gender</td><td align='left' > "+empOnlineResume.getGender()+"</td>"+
									"</tr>"+
									"<tr>"+
										"<td >Marital Status</td><td align='left' > "+ empOnlineResume.getMaritalStatus()+" </td>"+
									"</tr>"+
									"<tr>"+
										"<td >Date of Birth</td><td align='left'> "+CommonUtil.getStringDate(empOnlineResume.getDateOfBirth())+" </td>"+
									"</tr>"+
									"<tr>"+
									"<td >Religion</td><td align='left' > ";
									if(empOnlineResume.getReligion() != null && empOnlineResume.getReligion().getName() != null){
										msg = msg + empOnlineResume.getReligion().getName();
									}
									msg = msg +" </td>"+
								"</tr>"+
								"<tr>"+
								"<td > Blood Group </td><td align='left' > ";
									if(empOnlineResume.getBloodGroup()!=null){
										msg = msg + empOnlineResume.getBloodGroup();
									}
									msg = msg + "</td>"+
							    "</tr>"+
									"<tr>"+
									"<td >Email</td><td align='left' > "+empOnlineResume.getEmail()+"</td>"+
									"</tr>"+
									"<tr>"+
										"<td >Reservation Category</td><td align='left' > "+empOnlineResume.getReservationCategory()+" </td>"+
									"</tr>"+
									"<tr>"+
										"<td >Contact Number</td><td align='left' >";
											if(empOnlineResume.getPhNo1() != null){
												msg =msg + " +91"+ empOnlineResume.getPhNo1()+" ";
											}
											msg =msg + "</td>"+
									"</tr>"+
									"<tr>"+
										"<td >Mobile Number</td><td align='left' > +91"+empOnlineResume.getMobileNo1()+"</td>"+
									"</tr>"+
									"<tr>";
										
										if(empOnlineResume.isCurrentlyWorking()){
											msg = msg + "<td >Currently Working</td><td align='left'> Yes </td>" ;
										}else{
											msg = msg + "<td >Currently Working</td><td align='left' > NO </td>" ;
										}
										
									msg = msg + "</tr>"+
									"<tr>"+
										"<td >Designation</td><td align='left' > "; 
									if(empOnlineResume.getDesignation() != null && empOnlineResume.getDesignation().getName() != null){
										msg = msg + empOnlineResume.getDesignation().getName();
									}
									msg = msg +	" </td>"+
									"</tr>"+
									"<tr>"+
										"<td >Permanent Address:</td><td align='left' >  "+empOnlineResume.getAddressLine1()+"  <BR/> ";
									if(empOnlineResume.getAddressLine2() != null){
										msg =msg +empOnlineResume.getAddressLine2();
									}
									msg =msg +"  <BR/>  ";
									if(empOnlineResume.getAddressLine3() !=null){
										msg =msg +empOnlineResume.getAddressLine3();
									}
									msg =msg+"</td></tr>"+
									"<tr>"+
									"<td >Date of Application</td><td align='left' > "+CommonUtil.getStringDate(empOnlineResume.getDateOfSubmission())+"</td>"+
								"</tr>"+
									" </table> <table> ";
									msg = msg +"<tr height='25px'> <td width='43%'><p><strong>Experience Details</strong></p></td></tr></table>";
									msg = msg + "<table style='border:1px solid #000000' rules='all' width='100%' border='1'>"+
												"<tr> <td width='25%'> Teaching Experience </td>";
									String subjectArea="";
									if(downloadEmployeeResumeTOs != null && !downloadEmployeeResumeTOs.isEmpty()){
										Iterator<DownloadEmployeeResumeTO> toIterator = downloadEmployeeResumeTOs.iterator();
										while (toIterator.hasNext()) {
											DownloadEmployeeResumeTO downloadEmployeeResumeTO2 = (DownloadEmployeeResumeTO) toIterator.next();
											
											if(downloadEmployeeResumeTO2.getTotalTeachingExperience() != null && !downloadEmployeeResumeTO2.getTotalTeachingExperience().isEmpty()){
												msg = msg +"<td colspan='3'>"+downloadEmployeeResumeTO2.getTotalTeachingExperience()+"</td>";
												//msg = msg+"<td></td>";
											}
											else
												msg = msg+"<td ></td>";
												//msg = msg+"<td></td>";
										
									
									if(downloadEmployeeResumeTO2.getSubjectArea()!=null && !downloadEmployeeResumeTO2.getSubjectArea().isEmpty()){
										subjectArea=downloadEmployeeResumeTO2.getSubjectArea();
									
									msg = msg + "</tr>" +
											"<tr><td>Functional Area</td>" +
											"<td colspan='3'>"+subjectArea+"</td>" +
											"</tr>" ;
										}	
									}
									}
											List<EmpOnlinePreviousExperienceTO> teachingExpTos = downloadEmployeeResumeForm.getTeachingExperience();
											List<EmpOnlinePreviousExperienceTO> industryExpTos = downloadEmployeeResumeForm.getIndustryExperience();
											
											if(teachingExpTos != null && !teachingExpTos.isEmpty()){
									msg = msg +	"<tr><td>Teaching Experience Details</td><td></td><td></td><td></td></tr><tr> " +
											"<tr><td  align='center' width='25%'><strong>Designation </strong></td>	<td  align='center' width='25%'><strong>Institution</strong></td> " +
											"<td align='center' width='25%'><strong>Experience Year(s)</strong></td>	<td align='center' width='25%'><strong>Experience Month(s)</strong></td></tr>";
									
										Iterator<EmpOnlinePreviousExperienceTO> teachingIterator = teachingExpTos.iterator();
										while (teachingIterator.hasNext()) {
											EmpOnlinePreviousExperienceTO empOnlinePreviousExperienceTO = (EmpOnlinePreviousExperienceTO) teachingIterator.next();
											if(empOnlinePreviousExperienceTO.getEmpDesignation()!=null){
											msg = msg +"<tr>	<td align='center'> "+empOnlinePreviousExperienceTO.getEmpDesignation()+"</td>"+
											"<td align='center'>"+empOnlinePreviousExperienceTO.getEmpOrganization()+" </td>" +
											//code added by sudhir
											"<td align='center'>"+empOnlinePreviousExperienceTO.getTeachingExpYears()+"&nbsp;&nbsp;Year(s) </td> " +
											"<td align='center'>"+empOnlinePreviousExperienceTO.getTeachingExpMonths()+"&nbsp;&nbsp;Months(s) </td> </tr>";
											//
											}
										}
									}
									String indFuncArea="";
									msg = msg + "</table>"+
												"<table style='border:1px solid #000000' rules='all' width='100%' border='1'><tr>"+
												"<td width='25%'> Industry Experience</td>";
									if(downloadEmployeeResumeTOs != null && !downloadEmployeeResumeTOs.isEmpty()){
										Iterator<DownloadEmployeeResumeTO> iterator1 = downloadEmployeeResumeTOs.iterator();
										while (iterator1.hasNext()) {
											DownloadEmployeeResumeTO downloadEmployeeResumeTO2 = (DownloadEmployeeResumeTO) iterator1.next();
											msg = msg +"<td colspan='3'>";
											msg = msg + "</td></tr>";
											if(downloadEmployeeResumeTO2.getIndustryExperience() != null){
												msg = msg + downloadEmployeeResumeTO2.getIndustryExperience();
												if(downloadEmployeeResumeTO2.getIndustryFunctionalArea()!=null && !downloadEmployeeResumeTO2.getIndustryFunctionalArea().isEmpty()){
													indFuncArea=downloadEmployeeResumeTO2.getIndustryFunctionalArea();
													msg = msg + "<tr><td>Functional Area</td>" +
													"<td colspan='3'>" +indFuncArea+"</td></tr>";
												}
												
											}
											
											//msg = msg + "<td></td><td></td>";
										}
									}
									
									if(industryExpTos != null && !industryExpTos.isEmpty()){		
									msg = msg +"<tr> <td >Industry Experience Details</td><td></td><td></td><td></td>"+
												"</tr>	<tr> <td align='center' width='25%'><strong>Designation</strong> </td>"+
												"<td align='center' width='25%'><strong>Institution</strong></td> <td align='center' width='25%'><strong>Experience Year(s)</strong></td> <td  align='center' width='25%'><strong>Experience Month(s)</strong></td></tr>";
									
										Iterator<EmpOnlinePreviousExperienceTO> industryIterator = industryExpTos.iterator();
										while (industryIterator.hasNext()) {
											EmpOnlinePreviousExperienceTO empOnlinePreviousExperienceTO = (EmpOnlinePreviousExperienceTO) industryIterator.next();
											msg = msg +"<tr>	<td align='center' > "+empOnlinePreviousExperienceTO.getDesignation()+"</td>"+
											"<td align='center' >"+empOnlinePreviousExperienceTO.getOrganization()+" </td>	" +
										
											"<td align='center' >"+empOnlinePreviousExperienceTO.getIndustryExpYears()+" &nbsp;&nbsp;Year(s) </td>" +
											"<td align='center' >"+empOnlinePreviousExperienceTO.getIndustryExpMonths()+"&nbsp;&nbsp;Month(s) </td></tr>";
											
										}
									}
									
									msg = msg + "</table><br>	"+
												"<table style='border:1px solid #000000' rules='all' width=100% border='1'>";
									
									if(downloadEmployeeResumeTOs != null && !downloadEmployeeResumeTOs.isEmpty()){
										Iterator<DownloadEmployeeResumeTO> iterator2 = downloadEmployeeResumeTOs.iterator();
										while (iterator2.hasNext()) {
											DownloadEmployeeResumeTO downloadEmployeeResumeTO2 = (DownloadEmployeeResumeTO) iterator2.next();
											msg = msg + "<tr> "+
											"<td width='43%'> Total Experience</td>"+
											"<td > ";
											if(downloadEmployeeResumeTO2.getTotalExp() != null && !downloadEmployeeResumeTO2.getTotalExp().isEmpty()){
												msg = msg + downloadEmployeeResumeTO2.getTotalExp();
											}
											msg = msg + "</td>"+
											"</tr>"+
											"<tr> "+
											"<td > Qualification Level</td>"+
											"<td > ";
											if(downloadEmployeeResumeTO2.getQualificationLevel() != null && !downloadEmployeeResumeTO2.getQualificationLevel().isEmpty()){
												msg = msg + downloadEmployeeResumeTO2.getQualificationLevel();
											}
											msg = msg + "</td>"+
											"</tr>"+
											"<tr> "+
											"<td > Job Type</td>";
											msg = msg +	"<td > ";
											if(empOnlineResume.getEmpJobType() != null && !empOnlineResume.getEmpJobType().isEmpty()){
												msg = msg + empOnlineResume.getEmpJobType() ;
											}
											msg = msg + " </td> </tr>"+
											"<tr> "+
											"<td > Employment Status</td>"+
											"<td > ";
											if(downloadEmployeeResumeTO2.getEmpStatus() != null && !downloadEmployeeResumeTO2.getEmpStatus().isEmpty()){
												msg = msg + downloadEmployeeResumeTO2.getEmpStatus();
											}
											msg = msg + "</td>"+
											"</tr>"+
											"<tr> "+
											"<td > Expected Salary</td>"+
											"<td > ";
											if(downloadEmployeeResumeTO2.getExpectedSalary() != null && !downloadEmployeeResumeTO2.getExpectedSalary().isEmpty()){
												msg =msg + downloadEmployeeResumeTO2.getExpectedSalary();
											}
											msg = msg +"</td>"+
											"</tr>"+
											"<tr> "+
											"<td > Eligibility Test</td>"+
											"<td > ";
											if(downloadEmployeeResumeTO2.getEligibilityTest() != null && !downloadEmployeeResumeTO2.getEligibilityTest().isEmpty()){
												msg = msg + downloadEmployeeResumeTO2.getEligibilityTest();
											}
											msg = msg + "</td>"+
											"</tr>";
										}
									}
									
									msg = msg + "</table>";
												
									Iterator<DownloadEmployeeResumeTO> iterator3 = downloadEmployeeResumeTOs.iterator();
									while (iterator3.hasNext()) {
										DownloadEmployeeResumeTO downloadEmployeeResumeTO2 = (DownloadEmployeeResumeTO) iterator3.next();
										if(downloadEmployeeResumeTO2.getNoOfPublicationsNotRefered()!=null || downloadEmployeeResumeTO2.getNoOfPublicationsRefered()!=null || downloadEmployeeResumeTO2.getBooks()!=null){
										msg = msg + "<table>"+
										            " <tr>	<td><strong><p>No. of Publications</p> </strong></td> </tr> </table>"+
										            "	<table style='border:1px solid #000000' rules='all' width='100%' border='1'><tr>"+
										            "	<td>Refereed</td>	<td>Non-Refereed</td>	<td>Books</td>		</tr> <tr>";
										msg = msg +"<td>";
										if(downloadEmployeeResumeTO2.getNoOfPublicationsRefered() != null){
											msg = msg +downloadEmployeeResumeTO2.getNoOfPublicationsRefered();
										}
										msg =msg + "</td> <td>";
										if(downloadEmployeeResumeTO2.getNoOfPublicationsNotRefered()!= null){
											msg = msg +downloadEmployeeResumeTO2.getNoOfPublicationsNotRefered();
										}
										msg =msg + "</td> <td>";
										if(downloadEmployeeResumeTO2.getBooks() != null){
											msg = msg +downloadEmployeeResumeTO2.getBooks();
										}
										
										msg = msg +	"</td></tr>	</table>";
										}
									}
									msg = msg + " <table> <tr>"+
												"<td><strong><p>Educational Qualification</p> </strong></td></tr></table>"+
												"<table style='border:1px solid #000000' rules='all' width='100%' border='1'>"+
												"<tr><td width='23%'><strong>Course</strong></td>	<td width='20%'><strong>Specialization</strong></td>"+
												"<td><strong width='20%'>Year Of Completion</strong></td>    <td width='17%'><strong>Grade %</strong></td>	<td width='20%'><strong>Institute/University</strong></td>	</tr>";
									
									if(empEduDetails != null && !empEduDetails.isEmpty()){
										Iterator<EmpOnlineEducationalDetailsTO> iterator4 = empEduDetails.iterator();
										while (iterator4.hasNext()) {
											EmpOnlineEducationalDetailsTO empOnlineEducationalDetailsTO = (EmpOnlineEducationalDetailsTO) iterator4.next();
											msg = msg + "<tr>"+
											"<td>";
											if(empOnlineEducationalDetailsTO.getCourse() != null && !empOnlineEducationalDetailsTO.getCourse().isEmpty()){
												msg = msg + empOnlineEducationalDetailsTO.getCourse();
											}
											msg = msg + "</td>"+
											"<td>";
											if(empOnlineEducationalDetailsTO.getSpecialization() != null && !empOnlineEducationalDetailsTO.getSpecialization().isEmpty()){
												msg = msg + empOnlineEducationalDetailsTO.getSpecialization();
											}
											msg = msg + "</td>"+
											"<td>";
											if(empOnlineEducationalDetailsTO.getYearOfCompletion() != 0){
												msg = msg + empOnlineEducationalDetailsTO.getYearOfCompletion();
											}
											msg = msg + "</td>"+
											"<td>";
											if(empOnlineEducationalDetailsTO.getGrade() != null){
												msg = msg + empOnlineEducationalDetailsTO.getGrade();
											}
											msg = msg + "</td>"+
											"<td>";
											if(empOnlineEducationalDetailsTO.getInstitute() != null){
												msg = msg + empOnlineEducationalDetailsTO.getInstitute();
											}
											msg = msg + "</td>"+
											"</tr>";
										}
									}
									msg = msg + "</table>";
									if(downloadEmployeeResumeForm.getAdditionalQualification()!=null && !downloadEmployeeResumeForm.getAdditionalQualification().isEmpty()){
										msg = msg + "<table> <tr><td><strong><p>Additional Qualification</p> </strong></td> </tr> </table>"+
													"<table style='border:1px solid #000000' rules='all' width='100%' border='1'>"+
													"<tr>"+
													"<td ><strong>Course</strong></td>"+
													"<td><strong>Specialization</strong></td>"+
													"<td><strong>Year Of Completion</strong></td>"+
													"<td><strong>Grade %</strong></td>"+
													"<td><strong>Institute/University</strong></td>"+
													"</tr>";
										
										Iterator<EmpOnlineEducationalDetailsTO> iterator5 = downloadEmployeeResumeForm.getAdditionalQualification().iterator();
										while (iterator5.hasNext()) {
											EmpOnlineEducationalDetailsTO empOnlineEducationalDetailsTO = (EmpOnlineEducationalDetailsTO) iterator5.next();
											msg = msg + "<tr>"+
											"<td>";
											if(empOnlineEducationalDetailsTO.getCourse() != null && !empOnlineEducationalDetailsTO.getCourse().isEmpty()){
												msg = msg + empOnlineEducationalDetailsTO.getCourse();
											}
											msg = msg + "</td>"+
											"<td>";
											if(empOnlineEducationalDetailsTO.getSpecialization() != null && !empOnlineEducationalDetailsTO.getSpecialization().isEmpty()){
												msg = msg + empOnlineEducationalDetailsTO.getSpecialization();
											}
											msg = msg + "</td>"+
											"<td>";
											if(empOnlineEducationalDetailsTO.getYearOfCompletion() != 0){
												msg = msg + empOnlineEducationalDetailsTO.getYearOfCompletion();
											}
											msg = msg + "</td>"+
											"<td>";
											if(empOnlineEducationalDetailsTO.getGrade() != null){
												msg = msg + empOnlineEducationalDetailsTO.getGrade();
											}
											msg = msg + "</td>"+
											"<td>";
											if(empOnlineEducationalDetailsTO.getInstitute() != null){
												msg = msg + empOnlineEducationalDetailsTO.getInstitute();
											}
											msg = msg + "</td>"+
											"</tr></table>";
										}
									}
									msg = msg + " <table><tr> <td><strong><p>Other Information</p></strong> </td> </tr> </table>"+
													"<table style='border:1px solid #000000' rules='all' width='100%' border='1'>"+
													"<tr><td> <p>";
									if(empOnlineResume.getOtherInfo() != null && !empOnlineResume.getOtherInfo().isEmpty()){
										msg = msg + empOnlineResume.getOtherInfo();
									}
									msg = msg + "</p> </td>	</tr> </table> <table> <tr> <td height='30px'> ------------------------------------------ </BR></td> </tr> </table></body> </html>";
					}
					messages.put(appNo, msg);
				}
				
			}
		}
		downloadEmployeeResumeForm.setAppNosList(appNosList);
		return messages;
	}
	/**
	 * Common Send mail
	 * @param request 
	 * @param admForm
	 * @return
	 */
	public boolean sendMail(String mailID,String sub,Map<String,String> message, String user,String fromAddress,DownloadEmployeeResumeForm downloadEmployeeResumeForm,String realPath, HttpServletRequest request) {
			boolean sent=false;
			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {	
			log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
				//String fromMail=fromAddress;
				String toAddress=mailID;
				// MAIL TO CONSTRUCTION
				String subject=sub;
				/*String msg="";
				msg = msg + message;*/
				String username=user;
				MailTO mailto=new MailTO();
				mailto.setFromAddress(CMSConstants.MAIL_CARRER_USERID);
				mailto.setToAddress(toAddress);
				mailto.setSubject(subject);
				mailto.setMailBody(downloadEmployeeResumeForm.getMailBody());
				//mailto.setMessage(msg);
				mailto.setMessages(message);
				mailto.setFromName(username);
				//uses JMS 
//				sent=CommonUtil.postMail(mailto);
				sent=CommonUtil.sendMailForPdfAttachment(mailto,realPath,request);
			return sent;
	}
	/**
	 * @param downloadEmployeeResumeForm
	 * @param request 
	 * @throws Exception
	 */
	public void getResumeDetails(DownloadEmployeeResumeForm downloadEmployeeResumeForm,HttpSession session, HttpServletRequest request) throws Exception{
		IDownloadEmployeeResumeTransaction transaction = DownloadEmployeeResumeTransactionImpl.getInstance();
		String empId = downloadEmployeeResumeForm.getEmployeeId();
		EmpOnlineResume empOnlineResume = transaction.getDetailsForEmployee(empId);
		List<EmpOnlineEducationalDetails> eduDetails = transaction.getEmployeeEducationDetails(empId);
		List<EmpOnlinePreviousExperience> teachingExperience = transaction.getEmployeeExperienceDetails(empId);
		List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs = DownloadEmployeeResumeHelper.getInstance().convertBoToToForPrint(empOnlineResume, teachingExperience, downloadEmployeeResumeForm,session);
		downloadEmployeeResumeForm.setTos(downloadEmployeeResumeTOs);
		 List<EmpOnlineEducationalDetailsTO> empEduDetails = DownloadEmployeeResumeHelper.getInstance().convertBoToToForEduDetails(eduDetails,downloadEmployeeResumeForm);
		List<EmpOnlinePreviousExperienceTO> tos = DownloadEmployeeResumeHelper.getInstance().convertBoToToForExpDetails(teachingExperience,empOnlineResume,downloadEmployeeResumeForm);
		downloadEmployeeResumeForm.setTeachingExperience(tos);
		downloadEmployeeResumeForm.setEmpEducationalDetails(empEduDetails);
	}
	
	public DownloadEmployeeResumeTO getEmpDetailsByAppNo(DownloadEmployeeResumeForm downloadEmployeeResumeForm)throws Exception{
		IDownloadEmployeeResumeTransaction transaction = DownloadEmployeeResumeTransactionImpl.getInstance();
		EmpOnlineResume onlineResume = transaction.getEmpDetailsByAppNo(Integer.parseInt(downloadEmployeeResumeForm.getApplicationNo()));
		DownloadEmployeeResumeTO resumeTO=DownloadEmployeeResumeHelper.getInstance().convertOnlineResumeToTO(onlineResume);
			return resumeTO;
	}
	public boolean saveStatus(DownloadEmployeeResumeForm downloadEmployeeResumeForm)throws Exception{
		IDownloadEmployeeResumeTransaction transaction = DownloadEmployeeResumeTransactionImpl.getInstance();
		boolean flag= transaction.saveStatus(downloadEmployeeResumeForm);
		return flag;
	}
	/**
	 * @param downloadEmployeeResumeForm 
	 * @throws Exception
	 */
	public void getUsersDetailsMap(DownloadEmployeeResumeForm downloadEmployeeResumeForm) throws Exception{
		IDownloadEmployeeResumeTransaction transaction = DownloadEmployeeResumeTransactionImpl.getInstance();
		List<Object[]> usersDetailsList = transaction.getUsersList();
		DownloadEmployeeResumeHelper.getInstance().getUsersMap(usersDetailsList,downloadEmployeeResumeForm);
	}
	/**
	 * @param downloadEmployeeResumeForm
	 * @return
	 * @throws Exception
	 */
	public boolean sendMailTOAdmin1( DownloadEmployeeResumeForm downloadEmployeeResumeForm) throws Exception{
		boolean isSent = false;
		String[] userIds = downloadEmployeeResumeForm.getSelectedUserIdsArray();
		Map<Integer,String> usersWithEmailIdsMap = downloadEmployeeResumeForm.getUsersMapWithEmails();
		if(usersWithEmailIdsMap!=null && !usersWithEmailIdsMap.isEmpty()){
			Properties prop = new Properties();
			InputStream inStr = CommonUtil.class.getClassLoader() .getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
			String subject="Online Resume - Careers at Christ University";
			String fromName = prop.getProperty(CMSConstants.EMPLOYEE_ONLINE_RESUME_FROMNAME);
			String fromAddress=prop.getProperty(CMSConstants.EMPLOYEE_ONLINE_RESUME_FROM_MAIL);
		for(int i=0;i<userIds.length;i++){
			String userId= userIds[i];
			if(usersWithEmailIdsMap.containsKey(Integer.parseInt(userId))){
				String mailId = usersWithEmailIdsMap.get(Integer.parseInt(userId));
				if(mailId!=null && !mailId.isEmpty()){
					MailTO mailTO = new MailTO();
					mailTO.setFromName(fromName);
					mailTO.setFromAddress(fromAddress);
					mailTO.setSubject(subject);
					mailTO.setToAddress(mailId);
					mailTO.setMessage(downloadEmployeeResumeForm.getMailBody());
					CommonUtil.sendMail(mailTO);
				}
			}
		}
		}	
		isSent = DownloadEmployeeResumeHelper.getInstance().saveForwardedEmpDetails(downloadEmployeeResumeForm,userIds);
		return isSent;
	}
	
}
