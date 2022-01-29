package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.AssignSecondLanguageForm;
import com.kp.cms.to.admission.AssignSecondLanguageTo;

public class AssignSecondLanguageHelper {
	/**
	 * Singleton object of assignSecondLanguageHelper
	 */
	private static volatile AssignSecondLanguageHelper assignSecondLanguageHelper = null;
	private AssignSecondLanguageHelper() {
		
	}
	/**
	 * return singleton object of assignSecondLanguageHelper.
	 * @return
	 */
	public static AssignSecondLanguageHelper getInstance() {
		if (assignSecondLanguageHelper == null) {
			assignSecondLanguageHelper = new AssignSecondLanguageHelper();
		}
		return assignSecondLanguageHelper;
	}
	/**
	 * @param assignSecondLanguageForm
	 * @return
	 */
	public String getQuery(AssignSecondLanguageForm assignSecondLanguageForm) {
		String query = "from Student s where s.isActive=1 and s.isAdmitted=1 " +
				"and s.admAppln.isCancelled=0 and s.admAppln.isSelected=1 and s.admAppln.courseBySelectedCourseId.id="+assignSecondLanguageForm.getCourseId()+
				" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+assignSecondLanguageForm.getSchemeNo()+" and s.admAppln.appliedYear="+assignSecondLanguageForm.getYear();
		if(assignSecondLanguageForm.getSection()!=null && !assignSecondLanguageForm.getSection().isEmpty()){
			query=query+" and s.classSchemewise.classes.sectionName= '"+assignSecondLanguageForm.getSection()+"'";
		}
		if(assignSecondLanguageForm.getSecondLanguageId()!=null && !assignSecondLanguageForm.getSecondLanguageId().isEmpty()){
			query=query+" and s.admAppln.personalData.secondLanguage= '"+assignSecondLanguageForm.getSecondLanguageId()+"'";
		}
		return query;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<AssignSecondLanguageTo> convertBotoToList(List<Student> list) throws Exception {
		List<AssignSecondLanguageTo> studnList=new ArrayList<AssignSecondLanguageTo>();
		if(list!=null && !list.isEmpty()){
			Iterator<Student> iterator=list.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				AssignSecondLanguageTo assignSecondLanguageTo=new AssignSecondLanguageTo();
				StringBuffer  name=new StringBuffer();
				if(student.getAdmAppln()!=null){
					PersonalData data=student.getAdmAppln().getPersonalData();
					if(data!=null){
						name=name.append(data.getFirstName());
						if(data.getMiddleName()!=null){
							name=name.append(" "+data.getMiddleName());
						}
						if(data.getLastName()!=null){
							name=name.append(" "+data.getLastName());
						}
						assignSecondLanguageTo.setName(name.toString());
						if(data.getSecondLanguage()!=null){
							assignSecondLanguageTo.setSecondLanguage(data.getSecondLanguage());
						}
						if(student.getAdmAppln().getApplnNo()>0){
							assignSecondLanguageTo.setApplnNo(Integer.toString(student.getAdmAppln().getApplnNo()));
						}
						assignSecondLanguageTo.setRegisterNo(student.getRegisterNo());
						assignSecondLanguageTo.setRollNo(student.getRollNo());
						assignSecondLanguageTo.setId(data.getId());
						studnList.add(assignSecondLanguageTo);
					}
				}
			}
		}
		return studnList;
	}
}
