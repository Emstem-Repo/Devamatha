package com.kp.cms.helpers.admin;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentFeedBack;
import com.kp.cms.forms.admin.StudentFeedBackForm;

public class StudentFeedBackHelper {
	
	private static final Log log = LogFactory.getLog(StudentFeedBackHelper.class);
	public static volatile StudentFeedBackHelper studentFeedBackHelper = null;
	   public static StudentFeedBackHelper getInstance() {
		      if(studentFeedBackHelper == null) {
		    	  studentFeedBackHelper = new StudentFeedBackHelper();
		    	  return studentFeedBackHelper;
		      }
		      return studentFeedBackHelper;
	   }
	   
	   
	   /**
	 * @param studentFeedBackForm
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public StudentFeedBack convertFormToBo(StudentFeedBackForm studentFeedBackForm,String studentId) throws Exception
	{
	    StudentFeedBack studentFeedBack  = new StudentFeedBack();
		studentFeedBack.setFeedback(studentFeedBackForm.getFeedBack());
		Student s=new Student();
		s.setId(Integer.parseInt(studentId));
		studentFeedBack.setStudent(s);
		studentFeedBack.setIsActive(true);
		studentFeedBack.setCreatedDate(new Date());
		studentFeedBack.setLastModifiedDate(new Date());
		studentFeedBack.setDate(new Date());
		studentFeedBack.setEmail(studentFeedBackForm.getEmail());
		studentFeedBack.setMobileNo(studentFeedBackForm.getMobileNo());
	    return studentFeedBack;
	}

}
