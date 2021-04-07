package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.StudentsListReportForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.reports.StudentsListReportTO;

public class StudentsListReportHelper {

	private static final Log log = LogFactory.getLog(StudentsListReportHelper.class);
	
	/**
	 * @param leaveReportForm
	 * @return
	 * This method will build dynamic query
	 */

	private static String commonSearch(StudentsListReportForm listReportForm) {
		log.info("entered commonSearch method in StudentsListReportHelper class");
		
		String query = "";
		
		if (listReportForm.getProgramTypeId() != null && listReportForm.getProgramTypeId().trim().length() > 0 && !StringUtils.isEmpty(listReportForm.getProgramTypeId())) {
			String programType = " stu.admAppln.courseBySelectedCourseId.program.programType = "+ listReportForm.getProgramTypeId();
				query = query + programType;
		}
		if(listReportForm.getProgramId() != null && listReportForm.getProgramId().trim().length() > 0 && !StringUtils.isEmpty(listReportForm.getProgramId())){
			String program = " and stu.admAppln.courseBySelectedCourseId.program.id = "+ listReportForm.getProgramId();	
				query = query + program;
		}	
		if(listReportForm.getSemister() != null && listReportForm.getSemister().trim().length() > 0 && !StringUtils.isEmpty(listReportForm.getSemister())){
			String semister = " and stu.classSchemewise.classes.termNumber = "+ listReportForm.getSemister();
				query = query + semister;
		}
		query = query + " and stu.isAdmitted = 1  " 
						+ " and stu.admAppln.personalData.gender <> null " 
						+ " and stu.admAppln.personalData.gender <> '' "  
						+ " order by stu.admAppln.courseBySelectedCourseId.id ";
		
		log.info("exit of commonSearch method in StudentsListReportHelper class");
		return query;
	}

	/**
	 * @param leaveReportForm
	 * @return value of type string.
	 * This method will give final query
	 */
	public static String getSelectionSearchCriteria(StudentsListReportForm listReportForm) {
		log.info("entered getSelectionSearchCriteria method in StudentsListReportHelper class");
		String statusCriteria = commonSearch(listReportForm);
		
		String searchCriteria = "select stu.admAppln.courseBySelectedCourseId.id ,"+
						" stu.admAppln.courseBySelectedCourseId.name ," + " stu.admAppln.courseBySelectedCourseId.maxIntake," +
						" stu.admAppln.personalData.gender " + " from Student stu"	+
						" where " + statusCriteria;					
		log.info("exit of getSelectionSearchCriteria method in StudentsListReportHelper class");
		return searchCriteria;

	}
	
	/**
	 * This method is used to convert BOs to TOs from list
	 * @param list
	 * @param listReportForm
	 * @return
	 */
	
	public static List<StudentsListReportTO> convertBOstoTOs(List<Student> studentsList, StudentsListReportForm listReportForm) {
		log.info("entered convertBOstoTOs method in StudentsListReportHelper class");
		
		Map<Integer, StudentsListReportTO> map = new HashMap<Integer, StudentsListReportTO>();
		List<StudentsListReportTO> studentsReportList = new ArrayList<StudentsListReportTO>(); 
		StudentsListReportTO listReportTO = null;
		CourseTO courseTO;
		
		if(listReportForm.getSemister() != null && !listReportForm.getSemister().isEmpty()){
			if(Integer.parseInt(listReportForm.getSemister().trim()) == 1){
				listReportForm.setSemister("1st");
			}else if(Integer.parseInt(listReportForm.getSemister().trim()) == 2){
				listReportForm.setSemister("2nd");
			}else if(Integer.parseInt(listReportForm.getSemister().trim()) == 3){
				listReportForm.setSemister("3rd");
			}else if(Integer.parseInt(listReportForm.getSemister().trim()) == 4){
				listReportForm.setSemister("4th");
			}else if(Integer.parseInt(listReportForm.getSemister().trim()) == 5){
				listReportForm.setSemister("5th");
			}else if(Integer.parseInt(listReportForm.getSemister().trim()) == 6){
				listReportForm.setSemister("6th");
			}else if(Integer.parseInt(listReportForm.getSemister().trim()) == 7){
				listReportForm.setSemister("7th");
			}else if(Integer.parseInt(listReportForm.getSemister().trim()) == 8){
				listReportForm.setSemister("8th");
			}else if(Integer.parseInt(listReportForm.getSemister().trim()) == 9){ 
				listReportForm.setSemister("9th");
			}else{
				listReportForm.setSemister("10th");
			}	
		}
		
		if(studentsList != null && !studentsList.isEmpty()){
			Iterator iterator = studentsList.iterator();
			while (iterator.hasNext()) {
				Object[] object = (Object[]) iterator.next();
				if(map.containsKey((Integer)(object[0]))){
					int maleCount = Integer.parseInt(listReportTO.getNoOfMale());
					int femaleCount = Integer.parseInt(listReportTO.getNoOfFemale());
					int total = Integer.parseInt(listReportTO.getTotal());
					if("male".equalsIgnoreCase(object[3].toString())){
						maleCount++;
					}else{
						femaleCount++;
					}
					total = maleCount + femaleCount;
					listReportTO.setNoOfMale(Integer.toString(maleCount));
					listReportTO.setNoOfFemale(Integer.toString(femaleCount));
					listReportTO.setTotal(Integer.toString(total));
					
				}else{
					listReportTO = new StudentsListReportTO();
					int maleCount = 0;
					int femaleCount = 0;
					int total;
					courseTO = new CourseTO();
					courseTO.setId((Integer)object[0]);
					courseTO.setName(object[1].toString());
					listReportTO.setCourseTO(courseTO);
					listReportTO.setMaxIntake(object[2].toString());
					if("male".equalsIgnoreCase(object[3].toString())){
						maleCount++;
					}else{
						femaleCount++;
					}
					total = maleCount + femaleCount;
					listReportTO.setNoOfMale(Integer.toString(maleCount));
					listReportTO.setNoOfFemale(Integer.toString(femaleCount));
					listReportTO.setTotal(Integer.toString(total));
				}
				map.put((Integer)(object[0]), listReportTO);
			}
			
			studentsReportList.addAll(map.values());
		}
		log.info("exit of convertBOstoTOs method in StudentsListReportHelper class.");
		return studentsReportList;
	}
}