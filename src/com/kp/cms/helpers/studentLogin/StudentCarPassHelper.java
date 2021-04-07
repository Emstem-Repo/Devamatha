package com.kp.cms.helpers.studentLogin;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.report.soapengine.api.Data;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.studentLogin.StudentCarPass;
import com.kp.cms.forms.studentLogin.StudentCarPassForm;
import com.kp.cms.to.studentLogin.StudentCarPassTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;


public class StudentCarPassHelper {
	
	private static final Log log = LogFactory.getLog(StudentCarPassHelper.class);	
	public static volatile StudentCarPassHelper studentCarPassHelper = null;
	
	public static StudentCarPassHelper getInstance() {
		if (studentCarPassHelper == null) {
			studentCarPassHelper = new StudentCarPassHelper();
		}
		return studentCarPassHelper;
	}

	/**
	 * @param carPassForm
	 * @return
	 * @throws Exception 
	 */
	public StudentCarPass convertFormToBo(StudentCarPassForm carPassForm) throws Exception{
		StudentCarPass studentCarPass=new StudentCarPass();
		Student student=new Student();
		student.setId(Integer.parseInt(carPassForm.getStudentId()));
		studentCarPass.setEmergencyContactNo(carPassForm.getEmergencyContactNo());
		studentCarPass.setModelOfVehicle(carPassForm.getModelOfVehicle());
		studentCarPass.setVehicleNumber(carPassForm.getVehicleNo());
		studentCarPass.setStudentId(student);
		studentCarPass.setCreatedBy(carPassForm.getUserId());
		studentCarPass.setCreatedDate(new Date());
		studentCarPass.setModifiedBy(carPassForm.getUserId());
		studentCarPass.setLastModifiedDate(new Date());
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		if(year!=0){
			studentCarPass.setAcademicYear(String.valueOf(year));
		}
		studentCarPass.setIsActive(true);
		studentCarPass.setCarPassNo(carPassForm.getRegCarPasses()+1);
		return studentCarPass;
		
	}
	/**
	 * @param carPass
	 * @param carPassForm
	 */
	public void convertBotoTo(StudentCarPass carPass,StudentCarPassForm carPassForm){
		StudentCarPassTo carPassTo=new StudentCarPassTo();
		if(carPass!=null){
		carPassTo.setId(carPass.getId());
		if(carPass.getVehicleNumber()!=null){
		carPassTo.setVehicleNumber(carPass.getVehicleNumber());
		}if(carPass.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null){
			carPassTo.setStudentName(carPass.getStudentId().getAdmAppln().getPersonalData().getFirstName());	
		}if(carPass.getStudentId().getAdmAppln().getPersonalData().getFatherName()!=null){
			carPassTo.setFatherName(carPass.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		}if(carPass.getStudentId().getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null){
			carPassTo.setAddress(carPass.getStudentId().getAdmAppln().getPersonalData().getCurrentAddressLine1());
		}if(carPass.getStudentId().getRegisterNo()!=null){
			carPassTo.setRegisterNo(carPass.getStudentId().getRegisterNo());
		}if(carPass.getModelOfVehicle()!=null){
			carPassTo.setModelOfVehicle(carPass.getModelOfVehicle());
		}if(carPass.getStudentId().getAdmAppln().getPersonalData().getCurrentAddressZipCode()!=null){
			carPassTo.setPinCode(carPass.getStudentId().getAdmAppln().getPersonalData().getCurrentAddressZipCode());
		}if(carPass.getStudentId().getClassSchemewise().getClasses().getCourse().getName()!=null){
			carPassTo.setCourse(carPass.getStudentId().getClassSchemewise().getClasses().getCourse().getName());
		}if(carPass.getStudentId().getAdmAppln().getPersonalData().getDateOfBirth()!=null){
			carPassTo.setDateOfBirth(CommonUtil.formatDates(carPass.getStudentId().getAdmAppln().getPersonalData().getDateOfBirth()));
		}if(carPass.getEmergencyContactNo()!=null){
			carPassTo.setEmergencyContactNo(carPass.getEmergencyContactNo());
		}if(carPass.getCarPassNo()!=0){
			carPassTo.setRegistrationNo(String.valueOf(carPass.getCarPassNo()));
		}
		carPassForm.setStudentcarPassTo(carPassTo);
	}
	}
	
	/**
	 * @param studentCarPass
	 * @param studentCarPassForm
	 */
	public void convertBotoForm(StudentCarPass studentCarPass,StudentCarPassForm studentCarPassForm){
		if(studentCarPass!=null){
		if(studentCarPass.getModelOfVehicle()!=null){
            studentCarPassForm.setModelOfVehicle(studentCarPass.getModelOfVehicle());			
		}if(studentCarPass.getVehicleNumber()!=null){
			studentCarPassForm.setVehicleNo(studentCarPass.getVehicleNumber());
		}if(studentCarPass.getEmergencyContactNo()!=null){
			studentCarPassForm.setEmergencyContactNo(studentCarPass.getEmergencyContactNo());
		}
		studentCarPassForm.setCheckDisableText(true);
		}
	}
}
