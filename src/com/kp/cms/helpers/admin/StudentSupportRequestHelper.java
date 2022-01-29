package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentSupportRequestBo;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.supportrequest.CategoryBo;
import com.kp.cms.forms.admin.StudentSupportRequestForm;
import com.kp.cms.to.admin.StudentSupportRequestTo;
import com.kp.cms.utilities.CommonUtil;


public class StudentSupportRequestHelper {
	public static volatile StudentSupportRequestHelper studentSupportRequestHelper = null;
	private StudentSupportRequestHelper(){
		
	}
	public static StudentSupportRequestHelper getInstance() {
		if (studentSupportRequestHelper == null) {
			studentSupportRequestHelper = new StudentSupportRequestHelper();
			return studentSupportRequestHelper;
		}
		return studentSupportRequestHelper;
	}
	/**
	 * convert form to Bo for student
	 * @param studentSupportRequestForm
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public StudentSupportRequestBo convertFormToBo(
			StudentSupportRequestForm studentSupportRequestForm,
			String studentId) throws Exception{
		StudentSupportRequestBo studentSupportRequestBo=new StudentSupportRequestBo();
		CategoryBo categoryBo=new CategoryBo();
		categoryBo.setId(Integer.parseInt(studentSupportRequestForm.getCategoryId()));
		studentSupportRequestBo.setCategoryId(categoryBo);
		Student student=new Student();
		student.setId(Integer.parseInt(studentId));
		studentSupportRequestBo.setStudentId(student);
		studentSupportRequestBo.setDescription(studentSupportRequestForm.getDescription());
		studentSupportRequestBo.setStatus("Pending");
		studentSupportRequestBo.setDateOfSubmission(new Date());
		studentSupportRequestBo.setLastModifiedDate(new Date());
		studentSupportRequestBo.setModifiedBy(studentSupportRequestForm.getUserId());
		studentSupportRequestBo.setCreatedDate(new Date());
		studentSupportRequestBo.setCreatedBy(studentSupportRequestForm.getUserId());
		studentSupportRequestBo.setIsActive(true);
		return studentSupportRequestBo;
	}
/**
 * convert Bo to Tos
 * @param list
 * @param string 
 * @return
 * @throws Exception
 */
	public List<StudentSupportRequestTo> convertBotoTos(
			List<StudentSupportRequestBo> list, String string) throws Exception{
		List<StudentSupportRequestTo> studentSupportRequestTos=new ArrayList<StudentSupportRequestTo>();
		if(list!=null){
			Iterator<StudentSupportRequestBo> iterator=list.iterator();
			StudentSupportRequestTo studentSupportRequestTo=null;
			while (iterator.hasNext()) {
				StudentSupportRequestBo studentSupportRequestBo = (StudentSupportRequestBo) iterator.next();
				studentSupportRequestTo=new StudentSupportRequestTo();
				studentSupportRequestTo.setId(studentSupportRequestBo.getId());
				studentSupportRequestTo.setCategoryName(studentSupportRequestBo.getCategoryId().getName());
				studentSupportRequestTo.setDateOfSubmssion(CommonUtil.formatDates(studentSupportRequestBo.getDateOfSubmission()));
				studentSupportRequestTo.setDescription(studentSupportRequestBo.getDescription());
				studentSupportRequestTo.setStatus(studentSupportRequestBo.getStatus());
				if(string.equalsIgnoreCase("student")){
					studentSupportRequestTo.setRequestId("ST"+studentSupportRequestBo.getId());
				}else if(string.equalsIgnoreCase("admin")){
					studentSupportRequestTo.setRequestId("S"+studentSupportRequestBo.getId());
				}
				
				if(studentSupportRequestBo.getRemarks()!=null){
					studentSupportRequestTo.setRemarks(studentSupportRequestBo.getRemarks());
				}
				studentSupportRequestTos.add(studentSupportRequestTo);
			}
		}
		return studentSupportRequestTos;
	}
/**
 * convert Form to Bo for Admin
 * @param studentSupportRequestForm
 * @return
 */
	public StudentSupportRequestBo convertFormToBoForAdmin(
			StudentSupportRequestForm studentSupportRequestForm) {
		StudentSupportRequestBo studentSupportRequestBo=new StudentSupportRequestBo();
		CategoryBo categoryBo=new CategoryBo();
		categoryBo.setId(Integer.parseInt(studentSupportRequestForm.getCategoryId()));
		studentSupportRequestBo.setCategoryId(categoryBo);
		Users users=new Users();
		users.setId(Integer.parseInt(studentSupportRequestForm.getUserId()));
		studentSupportRequestBo.setUserId(users);
		studentSupportRequestBo.setDescription(studentSupportRequestForm.getDescription());
		studentSupportRequestBo.setStatus("Pending");
		studentSupportRequestBo.setDateOfSubmission(new Date());
		studentSupportRequestBo.setLastModifiedDate(new Date());
		studentSupportRequestBo.setModifiedBy(studentSupportRequestForm.getUserId());
		studentSupportRequestBo.setCreatedDate(new Date());
		studentSupportRequestBo.setCreatedBy(studentSupportRequestForm.getUserId());
		studentSupportRequestBo.setIsActive(true);
		return studentSupportRequestBo;
	}
public List<StudentSupportRequestTo> convertSupportBoToTos(
		List<StudentSupportRequestBo> list, Map<Integer, String> map) throws Exception{
	List<StudentSupportRequestTo> studentSupportRequestTos=new ArrayList<StudentSupportRequestTo>();
	try{
		Map<String,String> statusMap=new HashMap<String, String>();
		statusMap.put("Closed", "Closed");
		statusMap.put("In Progress", "In Progress");
		statusMap.put("Invalid Request", "Invalid Request");
		StudentSupportRequestTo studentSupportRequestTo=null;
		Iterator<StudentSupportRequestBo> iterator=list.iterator();
		while (iterator.hasNext()) {
			StudentSupportRequestBo studentSupportRequestBo = (StudentSupportRequestBo) iterator.next();
			studentSupportRequestTo=new StudentSupportRequestTo();
			studentSupportRequestTo.setId(studentSupportRequestBo.getId());
			studentSupportRequestTo.setCatgryId(studentSupportRequestBo.getCategoryId().getId());
			studentSupportRequestTo.setStatusMap(statusMap);
			studentSupportRequestTo.setCategoryMap(map);
			studentSupportRequestTo.setStatus(studentSupportRequestBo.getStatus());
			studentSupportRequestTo.setDateOfSubmssion(CommonUtil.formatDates(studentSupportRequestBo.getDateOfSubmission()));
			studentSupportRequestTo.setDescription(studentSupportRequestBo.getDescription());
			if(studentSupportRequestBo.getStudentId()!=null){
				studentSupportRequestTo.setIssueRaisedBy(studentSupportRequestBo.getStudentId().getRegisterNo());
				studentSupportRequestTo.setRequestId("ST"+studentSupportRequestBo.getId());
				if(studentSupportRequestBo.getStudentId().getAdmAppln()!=null){
					if(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId()!=null){
						if(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getWorkLocation()!=null){
							if(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getWorkLocation().getName()!=null){
								studentSupportRequestTo.setCampus(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getWorkLocation().getName());
							}
						}
					}
				}
			}
			if(studentSupportRequestBo.getUserId()!=null){
				studentSupportRequestTo.setRequestId("S"+studentSupportRequestBo.getId());
				if(studentSupportRequestBo.getUserId().getEmployee()!=null){
					if(studentSupportRequestBo.getUserId().getEmployee().getFingerPrintId()!=null){
						studentSupportRequestTo.setIssueRaisedBy(studentSupportRequestBo.getUserId().getEmployee().getFingerPrintId());
					}
				}
				if(studentSupportRequestBo.getUserId().getEmployee()!=null){
					if(studentSupportRequestBo.getUserId().getEmployee().getWorkLocationId()!=null){
						if(studentSupportRequestBo.getUserId().getEmployee().getWorkLocationId().getName()!=null){
							studentSupportRequestTo.setCampus(studentSupportRequestBo.getUserId().getEmployee().getWorkLocationId().getName());
						}
					}
				}
			}
			if(studentSupportRequestBo.getRemarks()!=null){
				studentSupportRequestTo.setRemarks(studentSupportRequestBo.getRemarks());
			}
			studentSupportRequestTos.add(studentSupportRequestTo);
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
	return studentSupportRequestTos;
}
public StudentSupportRequestTo convertBotoTo(
		StudentSupportRequestBo studentSupportRequestBo) throws Exception{
	StudentSupportRequestTo studentSupportRequestTo=new StudentSupportRequestTo();
	if(studentSupportRequestBo.getUserId()!=null){
		studentSupportRequestTo.setRequestId("S"+studentSupportRequestBo.getId());
		if(studentSupportRequestBo.getUserId().getEmployee()!=null){
			if(studentSupportRequestBo.getUserId().getEmployee().getWorkEmail()!=null){
				studentSupportRequestTo.setEmployeeId(String.valueOf(studentSupportRequestBo.getUserId().getEmployee().getFingerPrintId()));
				studentSupportRequestTo.setEmail(studentSupportRequestBo.getUserId().getEmployee().getWorkEmail());
			}
			if(studentSupportRequestBo.getUserId().getEmployee().getCurrentAddressMobile1()!=null){
				studentSupportRequestTo.setMobileNo(studentSupportRequestBo.getUserId().getEmployee().getCurrentAddressMobile1());
			}
		}else if(studentSupportRequestBo.getUserId().getGuest()!=null){
			if(studentSupportRequestBo.getUserId().getGuest().getWorkEmail()!=null){
				studentSupportRequestTo.setEmail(studentSupportRequestBo.getUserId().getGuest().getWorkEmail());
			}
			if(studentSupportRequestBo.getUserId().getGuest().getCurrentAddressMobile1()!=null){
				studentSupportRequestTo.setMobileNo(studentSupportRequestBo.getUserId().getGuest().getCurrentAddressMobile1());
			}
		}
	}
	if(studentSupportRequestBo.getStudentId()!=null){
		studentSupportRequestTo.setRequestId("ST"+studentSupportRequestBo.getId());
		if(studentSupportRequestBo.getStudentId().getAdmAppln().getPersonalData().getUniversityEmail()!=null){
				studentSupportRequestTo.setEmail(studentSupportRequestBo.getStudentId().getAdmAppln().getPersonalData().getUniversityEmail());
		}
		if(studentSupportRequestBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null){
			studentSupportRequestTo.setMobileNo(studentSupportRequestBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2());
		}
		if(studentSupportRequestBo.getStudentId().getRegisterNo()!=null){
			studentSupportRequestTo.setRegNo(studentSupportRequestBo.getStudentId().getRegisterNo());
		}
	}
	studentSupportRequestTo.setCategoryName(studentSupportRequestBo.getCategoryId().getName());
	studentSupportRequestTo.setDateOfSubmssion(CommonUtil.formatDates(studentSupportRequestBo.getDateOfSubmission()));
	studentSupportRequestTo.setStatus(studentSupportRequestBo.getStatus());
	if(studentSupportRequestBo.getCategoryId().getEmail()!=null && !studentSupportRequestBo.getCategoryId().getEmail().isEmpty()){
		studentSupportRequestTo.setCategoryEMail(studentSupportRequestBo.getCategoryId().getEmail());
	}
	if(studentSupportRequestBo.getDescription()!=null && !studentSupportRequestBo.getDescription().isEmpty()){
		studentSupportRequestTo.setDescription(studentSupportRequestBo.getDescription());
	}
	return studentSupportRequestTo;
}
public String queryForSearchList(
		StudentSupportRequestForm studentSupportRequestForm) throws Exception{
	Date date=new Date();
	Date date1=null;
	if(studentSupportRequestForm.getNoOfDays()!=null && !studentSupportRequestForm.getNoOfDays().isEmpty()){
		date1=getPreviousDate(date, Integer.parseInt(studentSupportRequestForm.getNoOfDays()));
	}else{
		date1=getPreviousDate(date,2);
	}
	java.sql.Date noOfDaysDate=CommonUtil.ConvertStringToSQLDate(CommonUtil.formatDates(date1));
	java.sql.Date currentDate=CommonUtil.ConvertStringToSQLDate(CommonUtil.formatDates(date));
	String query="from StudentSupportRequestBo s where s.isActive=1";
	if(studentSupportRequestForm.getStatus()!=null && !studentSupportRequestForm.getStatus().isEmpty() && !studentSupportRequestForm.getDeptId().isEmpty() && studentSupportRequestForm.getDeptId()!=null){
		query=query+" and s.status='"+studentSupportRequestForm.getStatus()+"' and s.categoryId.departmentId.id="+Integer.parseInt(studentSupportRequestForm.getDeptId())+
		" and date(s.dateOfSubmission) <= '"+noOfDaysDate+"'";
	}else if(studentSupportRequestForm.getStatus()!=null && !studentSupportRequestForm.getStatus().isEmpty()){
		query=query+" and s.status='"+studentSupportRequestForm.getStatus()+"' and date(s.dateOfSubmission) <= '"+noOfDaysDate+"'";
	}else if(studentSupportRequestForm.getDeptId()!=null && !studentSupportRequestForm.getDeptId().isEmpty()){
		query=query+" and s.categoryId.departmentId.id="+Integer.parseInt(studentSupportRequestForm.getDeptId())+
		" and date(s.dateOfSubmission) between '"+noOfDaysDate+"' and '"+currentDate+"'";
	}
	query=query+" order by s.dateOfSubmission";
	return query;
}
/**
 * getting the absenties list
 * @param absentiesListForm
 * @throws Exception
 */
public Date getPreviousDate(Date date,int days)throws Exception{
	Calendar c = Calendar.getInstance(); 
	c.setTime(date); 
	c.add(Calendar.DATE, -days);
	return c.getTime();
}
public List<StudentSupportRequestTo> convertBostoTos(
		List<StudentSupportRequestBo> list, StudentSupportRequestForm studentSupportRequestForm) throws Exception{
	List<StudentSupportRequestTo> studentSupportRequestTos=new ArrayList<StudentSupportRequestTo>();
	try{
		StudentSupportRequestTo studentSupportRequestTo=null;
		Iterator<StudentSupportRequestBo> iterator=list.iterator();
		while (iterator.hasNext()) {
			StudentSupportRequestBo studentSupportRequestBo = (StudentSupportRequestBo) iterator.next();
			studentSupportRequestTo=new StudentSupportRequestTo();
			studentSupportRequestTo.setId(studentSupportRequestBo.getId());
			studentSupportRequestTo.setCategoryName(studentSupportRequestBo.getCategoryId().getName());
			studentSupportRequestTo.setStatus(studentSupportRequestBo.getStatus());
			studentSupportRequestTo.setDateOfSubmssion(CommonUtil.formatDates(studentSupportRequestBo.getDateOfSubmission()));
			studentSupportRequestTo.setDescription(studentSupportRequestBo.getDescription());
			if(studentSupportRequestBo.getStudentId()!=null){
				studentSupportRequestTo.setIssueRaisedBy(studentSupportRequestBo.getStudentId().getRegisterNo());
				studentSupportRequestTo.setRequestId("ST"+studentSupportRequestBo.getId());
				if(studentSupportRequestBo.getStudentId().getAdmAppln()!=null){
					if(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId()!=null){
						if(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getWorkLocation()!=null){
							if(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getWorkLocation().getName()!=null){
								studentSupportRequestTo.setCampus(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getWorkLocation().getName());
							}
						}
					}
					StringBuilder stringBuilder=new StringBuilder();
					if(studentSupportRequestBo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null){
						stringBuilder.append(studentSupportRequestBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
					}
					if(studentSupportRequestBo.getStudentId().getAdmAppln().getPersonalData().getMiddleName()!=null){
						stringBuilder.append(studentSupportRequestBo.getStudentId().getAdmAppln().getPersonalData().getMiddleName());
					}
					studentSupportRequestForm.setPreviousName(stringBuilder.toString());
				}
				if(studentSupportRequestBo.getStudentId().getClassSchemewise()!=null){
					if(studentSupportRequestBo.getStudentId().getClassSchemewise().getClass()!=null){
						if(studentSupportRequestBo.getStudentId().getClassSchemewise().getClasses().getName()!=null){
							studentSupportRequestForm.setClassOrDepartment(studentSupportRequestBo.getStudentId().getClassSchemewise().getClasses().getName());
						}
					}
				}
			}
			if(studentSupportRequestBo.getUserId()!=null){
				studentSupportRequestTo.setRequestId("S"+studentSupportRequestBo.getId());
				if(studentSupportRequestBo.getUserId().getEmployee()!=null){
					if(studentSupportRequestBo.getUserId().getEmployee().getFingerPrintId()!=null){
						studentSupportRequestTo.setIssueRaisedBy(studentSupportRequestBo.getUserId().getEmployee().getFingerPrintId());
					}
				}
				if(studentSupportRequestBo.getUserId().getEmployee()!=null){
					if(studentSupportRequestBo.getUserId().getEmployee().getWorkLocationId()!=null){
						if(studentSupportRequestBo.getUserId().getEmployee().getWorkLocationId().getName()!=null){
							studentSupportRequestTo.setCampus(studentSupportRequestBo.getUserId().getEmployee().getWorkLocationId().getName());
						}
					}
					if(studentSupportRequestBo.getUserId().getEmployee().getDepartment()!=null){
						if(studentSupportRequestBo.getUserId().getEmployee().getDepartment().getName()!=null)
						studentSupportRequestForm.setClassOrDepartment(studentSupportRequestBo.getUserId().getEmployee().getDepartment().getName());
					}
					StringBuilder stringBuilder=new StringBuilder();
					if(studentSupportRequestBo.getUserId().getEmployee().getFirstName()!=null){
						stringBuilder.append(studentSupportRequestBo.getUserId().getEmployee().getFirstName());
					}
					if(studentSupportRequestBo.getUserId().getEmployee().getMiddleName()!=null){
						stringBuilder.append(studentSupportRequestBo.getUserId().getEmployee().getMiddleName());
					}
					studentSupportRequestForm.setPreviousName(stringBuilder.toString());
				}
			}
			if(studentSupportRequestBo.getRemarks()!=null){
				studentSupportRequestTo.setRemarks(studentSupportRequestBo.getRemarks());
			}
			studentSupportRequestTos.add(studentSupportRequestTo);
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
	return studentSupportRequestTos;
}
}
