package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.GeneratePasswordForm;
import com.kp.cms.transactions.admin.StudentLoginTO;

import com.kp.cms.utilities.EncryptUtil;
import com.kp.cms.utilities.PasswordGenerator;


/**
 * Helper for generate password handler
 *
 */
public class GeneratePasswordHelper {
private static final Log log = LogFactory.getLog(GeneratePasswordHelper.class);
	
	public static volatile GeneratePasswordHelper self=null;
	public static  GeneratePasswordHelper getInstance(){
		
			if(self==null)
					self= new GeneratePasswordHelper();
			return self;
		}
		
	private GeneratePasswordHelper(){
		
	}
	/**
	 * @param studentList
	 * @param gnForm 
	 * @param usernames 
	 * @return
	 */
	public List<StudentLogin> prepareStudentLogins(List<Student> studentList, GeneratePasswordForm gnForm, List<String> usernames) throws Exception{
		List<StudentLogin> studentlogins= new ArrayList<StudentLogin>();
		if(usernames==null)
			usernames=new ArrayList<String>();
		List<StudentLoginTO> studentloginTos= new ArrayList<StudentLoginTO>();
		List<Student> rejectList= new ArrayList<Student>();
		if(studentList!=null){
			Iterator<Student> stItr=studentList.iterator();
			while (stItr.hasNext()) {
				Student student = (Student) stItr.next();
				//if it is mailid to be username
				if (student.getId()==4100) {
					System.out.println(student.getId());
				}
				if(gnForm.isStudentMailid() && student.getAdmAppln().getPersonalData()!=null && student.getAdmAppln().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(student.getAdmAppln().getPersonalData().getEmail().trim())){
					StudentLogin login= new StudentLogin();
					StudentLoginTO loginTo= new StudentLoginTO();
					login.setStudent(student);
					login.setUserName(student.getAdmAppln().getPersonalData().getEmail());
					
					String randPass=PasswordGenerator.getPassword();
					EncryptUtil encUtil=EncryptUtil.getInstance();
					String encpass=null;
					if(!StringUtils.isEmpty(randPass.trim())){
						encpass=encUtil.encryptDES(randPass);
					}
					login.setPassword(encpass);
					login.setIserverPassword(randPass.substring(0, 3)+randPass);
					login.setIsTempPassword(false);
					//student role
					Roles roles= new Roles();
					roles.setId(CMSConstants.STUDENT_ROLE_ID);
					login.setRoles(roles);
					login.setIsActive(true);
					login.setIsStudent(true);
					login.setCreatedBy(gnForm.getUserId());
					login.setCreatedDate(new Date());
					if(!usernames.contains(login.getUserName())){
						usernames.add(login.getUserName());
						studentlogins.add(login);
						setParentLoginDetails(student, studentlogins, loginTo, login, randPass,gnForm,studentloginTos,usernames,rejectList);
					}else{
						rejectList.add(student);
					}
					//if rollno
				}else if(gnForm.isStudentRollNo() && student.getRollNo()!=null && !StringUtils.isEmpty(student.getRollNo().trim())){
					StudentLogin login= new StudentLogin();
					StudentLoginTO loginTo= new StudentLoginTO();
					login.setStudent(student);
					login.setUserName(student.getRollNo());
					String randPass=PasswordGenerator.getPassword();
					EncryptUtil encUtil=EncryptUtil.getInstance();
					String encpass=null;
					if(!StringUtils.isEmpty(randPass.trim())){
						encpass=encUtil.encryptDES(randPass);
					}
					login.setPassword(encpass);
					login.setIserverPassword(randPass.substring(0, 3)+randPass);
					login.setIsTempPassword(false);
					//student role
					Roles roles= new Roles();
					roles.setId(CMSConstants.STUDENT_ROLE_ID);
					login.setRoles(roles);
					login.setIsActive(true);
					login.setIsStudent(true);
					login.setCreatedBy(gnForm.getUserId());
					login.setCreatedDate(new Date());
					
					if(!usernames.contains(login.getUserName())){
						usernames.add(login.getUserName());
						studentlogins.add(login);
						setParentLoginDetails(student, studentlogins, loginTo, login, randPass,gnForm,studentloginTos,usernames,rejectList);
					}else{
						rejectList.add(student);
					}
					//if reg.no
				}else if(gnForm.isStudentRegNo() && student.getRegisterNo()!=null && !StringUtils.isEmpty(student.getRegisterNo().trim())){
					StudentLogin login= new StudentLogin();
					StudentLoginTO loginTo= new StudentLoginTO();
					login.setStudent(student);
					login.setUserName(student.getRegisterNo());
					String randPass=PasswordGenerator.getPassword();
					EncryptUtil encUtil=EncryptUtil.getInstance();
					String encpass=null;
					if(!StringUtils.isEmpty(randPass.trim())){
						encpass=encUtil.encryptDES(randPass);
					}
					login.setPassword(encpass);
					login.setIserverPassword(randPass.substring(0, 3)+randPass);
					login.setIsTempPassword(false);
					//student role
					Roles roles= new Roles();
					roles.setId(CMSConstants.STUDENT_ROLE_ID);
					login.setRoles(roles);
					login.setIsActive(true);
					login.setIsStudent(true);
					login.setCreatedBy(gnForm.getUserId());
					login.setCreatedDate(new Date());
					if(!usernames.contains(login.getUserName())){
						usernames.add(login.getUserName());
						studentlogins.add(login);
						setParentLoginDetails(student, studentlogins, loginTo, login, randPass,gnForm,studentloginTos,usernames,rejectList);
					}else{
						rejectList.add(student);
					}
					
				}
				else
				{
					rejectList.add(student);
				}
			}
		}
		gnForm.setRejectedList(rejectList);
		gnForm.setSuccessList(studentloginTos);
		log.info("Exit prepareStudentLogins");
		return studentlogins;
	}
	
	/**
	 * sets parent login details
	 * @param gnForm 
	 * @param studentloginTos 
	 * @param rejectList 
	 * @param usernames 
	 */
	private void setParentLoginDetails(Student student,List<StudentLogin> studentlogins,StudentLoginTO loginTo,StudentLogin login,String normalPassword, GeneratePasswordForm gnForm, List<StudentLoginTO> studentloginTos, List<String> usernames, List<Student> rejectList) throws Exception{

		//parent logindetails
		StudentLogin parentlogin=null;
		String parentPass="";
		if(student.getAdmAppln().getPersonalData().getFatherEmail()!=null && !StringUtils.isEmpty(student.getAdmAppln().getPersonalData().getFatherEmail().trim())){
		// if parent userid,password diff
			if(!gnForm.isSameUseridPassword()){
			
				parentlogin=new StudentLogin();
				parentlogin.setStudent(student);
				parentlogin.setUserName(student.getAdmAppln().getPersonalData().getFatherEmail());
				parentPass=PasswordGenerator.getPassword();
				String encParpass=null;
				EncryptUtil encUtil=EncryptUtil.getInstance();
				if(!StringUtils.isEmpty(parentPass.trim())){
					encParpass=encUtil.encryptDES(parentPass);
				}
				parentlogin.setPassword(encParpass);
				parentlogin.setIserverPassword(parentPass.substring(0, 3)+parentPass);
				parentlogin.setIsTempPassword(false);
				//student role
				Roles parentRole= new Roles();
				parentRole.setId(CMSConstants.STUDENT_ROLE_ID);
				parentlogin.setRoles(parentRole);
				parentlogin.setIsActive(true);
				parentlogin.setIsStudent(false);
				parentlogin.setCreatedBy(gnForm.getUserId());
				parentlogin.setCreatedDate(new Date());
				if(!usernames.contains(parentlogin.getUserName())){
					usernames.add(parentlogin.getUserName());
					studentlogins.add(parentlogin);
				}else{
					rejectList.add(student);
				}
				
			}
		}
		
		// to get original password for student to mail, set student object and normal password
			loginTo.setOriginalStudent(student);
			if(student.getAdmAppln()!=null && student.getAdmAppln().getPersonalData()!=null && student.getAdmAppln().getPersonalData().getFirstName()!=null){
			loginTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
			}
			if(student.getAdmAppln()!=null && student.getAdmAppln().getPersonalData()!=null && student.getAdmAppln().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(student.getAdmAppln().getPersonalData().getEmail().trim())){
				loginTo.setStudentMail(student.getAdmAppln().getPersonalData().getEmail());
			}
			if(student.getAdmAppln()!=null && student.getAdmAppln().getPersonalData()!=null && student.getAdmAppln().getPersonalData().getFatherEmail()!=null && !StringUtils.isEmpty(student.getAdmAppln().getPersonalData().getFatherEmail().trim())){
				loginTo.setParentMail(student.getAdmAppln().getPersonalData().getFatherEmail());
			}
			loginTo.setOriginalPassword(normalPassword);
			log.info("Student password:::----"+normalPassword);
			loginTo.setStudentUsername(login.getUserName());
			log.info("Student Username:::----"+login.getUserName());
		if(!gnForm.isSameUseridPassword() && parentlogin!=null && !StringUtils.isEmpty(parentPass)){
			loginTo.setParentUsername(parentlogin.getUserName());
			log.info("Parent Username:::----"+parentlogin.getUserName());
			loginTo.setParentPassword(parentPass);
			log.info("Parent Username:::----"+parentPass);
					
		}
		studentloginTos.add(loginTo);
	}
	
	
}
