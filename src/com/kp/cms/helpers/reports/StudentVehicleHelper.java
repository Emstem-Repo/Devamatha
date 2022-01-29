package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.StringUtil;

import com.kp.cms.forms.reports.StudentVehicleForm;
import com.kp.cms.to.reports.StudentVehicleTO;

public class StudentVehicleHelper {
	private static volatile StudentVehicleHelper studentVehicleHelper = null;
	private static final Log log = LogFactory.getLog(StudentVehicleHelper.class);
	private StudentVehicleHelper() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static StudentVehicleHelper getInstance() {
		if (studentVehicleHelper == null) {
			studentVehicleHelper = new StudentVehicleHelper();
		}
		return studentVehicleHelper;
	}
	/**
	 * making query
	 * @param studentVehicleForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQuery(StudentVehicleForm studentVehicleForm) throws Exception {
		String SearchCriteria="select stud.registerNo," +
				"stud.rollNo,stud.classSchemewise.classes.name," +
				"stud.admAppln.personalData.firstName," +
				"stud.admAppln.personalData.middleName," +
				"stud.admAppln.personalData.lastName," +
				"stud.admAppln.personalData.gender," +
				"stvd.vehicleNo,stvd.vehicleType" +
				" from StudentVehicleDetails stvd" +
				" join stvd.admAppln.students stud " +
				" where stvd.admAppln.id = stud.admAppln.id";
				
				
		if(studentVehicleForm.getProgramTypeId()!=null && !StringUtils.isEmpty(studentVehicleForm.getProgramTypeId())){
			SearchCriteria=SearchCriteria+" and stvd.admAppln.courseBySelectedCourseId.program.programType.id ="+ studentVehicleForm.getProgramTypeId();
		}
		if(studentVehicleForm.getVehicleType()!=null && !StringUtils.isEmpty(studentVehicleForm.getVehicleType())){
			SearchCriteria=SearchCriteria+"  and stvd.vehicleType like '"+studentVehicleForm.getVehicleType()+"%'";
		}
		SearchCriteria=SearchCriteria+" order by stud.registerNo";
		return SearchCriteria;
	}
	/**
	 * converting the list of object into List of TO's
	 * @param listofStudents
	 * @return
	 * @throws Exception
	 */
	public List<StudentVehicleTO> convertBotoTo(List listofStudents) throws Exception {
		List<StudentVehicleTO> applicantDetailsList = new ArrayList<StudentVehicleTO>();
		
		if (listofStudents != null) {
			Iterator listofStudentsItr = listofStudents.iterator();
			while (listofStudentsItr.hasNext()) {
				Object[] searchResults = (Object[]) listofStudentsItr.next();
				StudentVehicleTO studentVehicleTO = new StudentVehicleTO();
				if(searchResults[0]!=null){
					studentVehicleTO.setRegisterNo(searchResults[0].toString());
				}
				if(searchResults[1]!=null){
					studentVehicleTO.setRollNo(searchResults[1].toString());
				}
				if(searchResults[2]!=null){
					studentVehicleTO.setClassType(searchResults[2].toString());
				}
				if(searchResults[4] == null && searchResults[5] ==null){
					studentVehicleTO.setApplicantName(searchResults[3].toString());
				}else if(searchResults[4] == null){
					studentVehicleTO.setApplicantName(searchResults[3].toString() +" "+ searchResults[5].toString());
				}else if(searchResults[5] == null){
					studentVehicleTO.setApplicantName(searchResults[3].toString() +" "+ searchResults[4].toString());
				}
				else{
					studentVehicleTO.setApplicantName(searchResults[3].toString() +" "+ searchResults[4].toString() +" "+ searchResults[5].toString());
				}
				if(searchResults[6]!=null){
					studentVehicleTO.setGender(searchResults[6].toString());
				}
				if(searchResults[7]!=null){
					studentVehicleTO.setVehicleNo(searchResults[7].toString());
				}
				if(searchResults[8]!=null){
					studentVehicleTO.setVehicleType(searchResults[8].toString());
				}
				applicantDetailsList.add(studentVehicleTO);
			}
		}	
		return applicantDetailsList;
	}
}
