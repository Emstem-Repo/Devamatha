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
import com.kp.cms.forms.admin.ReGeneratePasswordForm;
import com.kp.cms.transactions.admin.IReGeneratePasswordTransaction;
import com.kp.cms.transactionsimpl.admin.ReGeneratePasswordTransactionImpl;
import com.kp.cms.utilities.EncryptUtil;
import com.kp.cms.utilities.PasswordGenerator;

public class ReGeneratePasswordHelper {
	/**
	 * Singleton object of ReGeneratePasswordHelper
	 */
	private static volatile ReGeneratePasswordHelper reGeneratePasswordHelper = null;
	private ReGeneratePasswordHelper() {
		
	}
	/**
	 * return singleton object of ReGeneratePasswordHelper.
	 * @return
	 */
	public static ReGeneratePasswordHelper getInstance() {
		if (reGeneratePasswordHelper == null) {
			reGeneratePasswordHelper = new ReGeneratePasswordHelper();
		}
		return reGeneratePasswordHelper;
	}
	/**
	 * @param reGeneratePasswordForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentLogin> getFinalStudentLogins(List<StudentLogin> studentLogins,ReGeneratePasswordForm reGeneratePasswordForm) throws Exception{
		List<StudentLogin> finalList=new ArrayList<StudentLogin>();
		List<String> userNameList=new ArrayList<String>();
		IReGeneratePasswordTransaction transaction=new ReGeneratePasswordTransactionImpl();
		if(studentLogins!=null && !studentLogins.isEmpty()){
			Iterator<StudentLogin> itr=studentLogins.iterator();
			while (itr.hasNext()) {
				StudentLogin login = (StudentLogin) itr.next();
				Student student=login.getStudent();
				if(reGeneratePasswordForm.isStudentMailid() && student.getAdmAppln().getPersonalData()!=null && student.getAdmAppln().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(student.getAdmAppln().getPersonalData().getEmail().trim())){
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
					//if rollno
				}else if(reGeneratePasswordForm.isStudentRollNo() && student.getRollNo()!=null && !StringUtils.isEmpty(student.getRollNo().trim())){
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
					//if reg.no
				}else if(reGeneratePasswordForm.isStudentRegNo() && student.getRegisterNo()!=null && !StringUtils.isEmpty(student.getRegisterNo().trim())){
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
				}
				login.setIsTempPassword(false);
				//student role
				Roles roles= new Roles();
				roles.setId(CMSConstants.STUDENT_ROLE_ID);
				login.setRoles(roles);
				login.setIsActive(true);
				login.setIsStudent(true);
				login.setModifiedBy(reGeneratePasswordForm.getUserId());
				login.setLastModifiedDate(new Date());
				
				if(transaction.checkDuplicateRegisterNo(login) && !userNameList.contains(login.getUserName())){
					userNameList.add(login.getUserName());
					finalList.add(login);
				}
			}
		}
		return finalList;
	}
}
