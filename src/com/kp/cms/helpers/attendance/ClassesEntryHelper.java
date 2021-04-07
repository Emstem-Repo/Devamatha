package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.forms.attendance.ClassEntryForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.attendance.ClassesTO;

public class ClassesEntryHelper {

	private static volatile ClassesEntryHelper classsesHelper= null;
	private static final Log log = LogFactory.getLog(ClassesEntryHelper.class);
	
	/**
	 * Return the single instance of classentry helper.   
	 * @return
	 */
	public static ClassesEntryHelper getInstance() {
		if(classsesHelper == null) {
	      classsesHelper = new ClassesEntryHelper();
		}
	    return classsesHelper;
	}
	
	/**
	 * This method copy Bo's to TO's
	 * @param classes
	 * @return
	 * @throws Exception
	 */
	public List<ClassesTO> copyBosToTos(List<ClassSchemewise> classes) throws Exception {
		log.debug("Hepler : Entering the copyBosToTos");
		List<ClassesTO> ClassesList = new ArrayList<ClassesTO>();
		Iterator<ClassSchemewise> itr = classes.iterator();
		CourseTO courseTo;
		ClassSchemewise tempClasses ;
		ClassesTO classesTo;
		
		while(itr.hasNext()) {
			tempClasses = itr.next();
			classesTo = new ClassesTO();
			courseTo = new CourseTO();
			
			courseTo.setId(tempClasses.getClasses().getCourse().getId());
			courseTo.setName(tempClasses.getClasses().getCourse().getName());
			courseTo.setCode(tempClasses.getClasses().getCourse().getCode());
			
			classesTo.setCourseTo(courseTo);
			classesTo.setId(tempClasses.getClasses().getId());
			classesTo.setClassName(tempClasses.getClasses().getName());
			classesTo.setSectionName(tempClasses.getClasses().getSectionName());
			classesTo.setTermNo(tempClasses.getCurriculumSchemeDuration().getSemesterYearNo());
			classesTo.setYear(tempClasses.getCurriculumSchemeDuration().getAcademicYear());
			// Added for change as per new UC
			classesTo.setCourseGroupCodeId(tempClasses.getClasses().getCourseGroupCodeId());
			
			ClassesList.add(classesTo);
		}
		log.debug("Hepler : Leaving the copyBosToTos");
	return ClassesList;	
	}
	
	/**
	 *  This method copies the data from form to Bo's.
	 * @param classesForm
	 * @return
	 * @throws Exception
	 */
	public Classes copyFormDataToBo(ClassEntryForm classesForm) throws Exception {
		Classes classes = new Classes();
		ClassSchemewise classSchemewise = new ClassSchemewise();
		
		// sets the id to 0 while adding otherwise id while updating.
		if(classesForm.getId().equals("0") || classesForm.getId().length() == 0){
			classes.setId(0);
		} else {
			classes.setId(Integer.parseInt(classesForm.getId()));
		}
		
		if(classesForm.getClassSchemewiseId().equals("0") || classesForm.getClassSchemewiseId().length() == 0){
			classSchemewise.setId(0);
		} else {
			classSchemewise.setId(Integer.parseInt(classesForm.getClassSchemewiseId()));
		}
		
		Course course = new Course();
		course.setId(Integer.parseInt(classesForm.getCourseId()));
		
		CurriculumSchemeDuration curriculumSchemeDuration = new CurriculumSchemeDuration();
		curriculumSchemeDuration.setId(Integer.valueOf(classesForm.getTermNo()));
		
		classSchemewise.setCurriculumSchemeDuration(curriculumSchemeDuration);
		
		Set<ClassSchemewise> classSchemewiseSet = new HashSet<ClassSchemewise>();
		classSchemewiseSet.add(classSchemewise);
		
		classes.setCourse(course);
		classes.setSectionName(classesForm.getSectionName());
		classes.setIsActive(true);
		classes.setCreatedBy(classesForm.getUserId());
		classes.setModifiedBy(classesForm.getUserId());
		classes.setCreatedDate(new Date());
		classes.setLastModifiedDate(new Date());
		classes.setName(classesForm.getClassName());
		classes.setTermNumber(Integer.valueOf(classesForm.getSemesterNo().trim()));
		classes.setClassSchemewises(classSchemewiseSet);
		// Added for change as per new UC
		classes.setCourseGroupCodeId(classesForm.getCourseGroupCodeId());
		
		return classes;	
	}

	public String getQuery(ClassEntryForm classEntryForm) throws Exception{
		String query=" from ClassSchemewise c " +
				"where c.classes.name ='"+classEntryForm.getClassName()+"' and c.classes.course.id ="+classEntryForm.getCourseId()+
						" and c.classes.termNumber ="+classEntryForm.getSemesterNo()+" and c.curriculumSchemeDuration.academicYear = '"+classEntryForm.getYear()+"'";
		if(classEntryForm.getSectionName()!=null && !classEntryForm.getSectionName().isEmpty()){
			query=query+" and c.classes.sectionName='"+classEntryForm.getSectionName()+"'";
		}else{
			query=query+" and c.classes.sectionName = ''";
		}
		
		return query;
	}
}
