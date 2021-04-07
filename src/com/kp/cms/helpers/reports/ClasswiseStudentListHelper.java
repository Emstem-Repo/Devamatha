package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.ClasswiseStudentListForm;
import com.kp.cms.to.reports.ClassStudentListTO;
import com.kp.cms.to.reports.ClasswithStudentTO;

public class ClasswiseStudentListHelper {
	private static final Log log = LogFactory.getLog(ClasswiseStudentListHelper.class);
	public static volatile ClasswiseStudentListHelper classwiseStudentListHelper = null;
	public static ClasswiseStudentListHelper getInstance(){
		if(classwiseStudentListHelper == null){
			classwiseStudentListHelper = new ClasswiseStudentListHelper();  
		}
		return classwiseStudentListHelper;
	}

	/**
	 * creating query string
	 * @param stForm
	 * @return
	 */
	public String getSearchCriteria(ClasswiseStudentListForm stForm){
		String selClassId[] = stForm.getSelClassids();
		String selStCat[] = stForm.getSelStCategoryIds();
		StringBuffer clasid = new StringBuffer("");
		StringBuffer resId = new StringBuffer("");
		
		if(selClassId!= null && selClassId.length > 0){
			for(int i = 0; i < selClassId.length; i++){
				clasid.append("'");
				clasid.append(selClassId[i]);
				clasid.append("'");
				if(i < selClassId.length - 1){
					clasid.append(",");
				}
			}
		}
		if(selStCat!= null && selStCat.length > 0){
			for(int i = 0; i < selStCat.length; i++){
				resId.append("'");
				resId.append(selStCat[i]);
				resId.append("'");
				if(i < selStCat.length - 1){
					resId.append(",");
				}
			}	
		}		

		StringBuffer sqlString = new StringBuffer("from Student st" +
							 " where st.classSchemewise.classes.id in (");
		sqlString.append(clasid.toString());
		sqlString.append(")");
		
		if(selStCat!= null && selStCat.length > 0){
			sqlString.append(" and st.admAppln.personalData.residentCategory.id in (");
			sqlString.append(resId.toString());
			sqlString.append(")");
		}
		if(stForm.getLanguage()!= null && !stForm.getLanguage().trim().isEmpty()){
			sqlString.append(" and st.admAppln.personalData.secondLanguage = '");
			sqlString.append(stForm.getLanguage());
			sqlString.append("'");
		}
		if(stForm.getReligionId()!= null && !stForm.getReligionId().trim().isEmpty()){
			sqlString.append(" and st.admAppln.personalData.religion.id = '");
			sqlString.append(stForm.getReligionId());
			sqlString.append("'");
		}
		if(stForm.getSubRelId()!= null && !stForm.getSubRelId().trim().isEmpty()){
			sqlString.append(" and st.admAppln.personalData.religionSection.id = '");
			sqlString.append(stForm.getSubRelId());
			sqlString.append("'");
		}
		if(stForm.getCasteId()!= null && !stForm.getCasteId().trim().isEmpty()){
			sqlString.append(" and st.admAppln.personalData.caste.id = '");
			sqlString.append(stForm.getCasteId());
			sqlString.append("'");
		}
		
		sqlString.append(" and st.admAppln.isCancelled = 0 and st.isActive = 1");
		sqlString.append(" order by  st.classSchemewise.classes.id, st.rollNo,  st.registerNo");
		
		return sqlString.toString();
	}
	
	/**
	 * 
	 * @param studentList
	 * @return
	 * @throws Exception
	 */
	public List<ClassStudentListTO> copyBosToTO(List<Student> studentList, ClasswiseStudentListForm stForm) throws Exception {
		log.debug("inside copyBosToTO");
		ClassStudentListTO classStudentListTO;
		Student student;
		Iterator<Student> itr = studentList.iterator();
		StringBuffer studentName = new StringBuffer();
		Map<String, List<ClassStudentListTO>> classStudentListTOMap = new HashMap<String, List<ClassStudentListTO>>();
		String className;
		while (itr.hasNext()){
			classStudentListTO = new ClassStudentListTO(); 
			student = itr.next();
			className = student.getClassSchemewise().getClasses().getName();
			
			List<ClassStudentListTO> classStudentToList = null;
			if(classStudentListTOMap.containsKey(className)){
				classStudentToList = classStudentListTOMap.get(className.trim());
			}else
			{
				classStudentToList = new ArrayList<ClassStudentListTO>();
			}
			classStudentListTO.setClassName(className);
			classStudentListTO.setSlNo(classStudentToList.size() + 1);
			studentName = new StringBuffer();
			studentName.append(student.getAdmAppln().getPersonalData().getFirstName());
			if(student.getAdmAppln().getPersonalData().getMiddleName()!= null && !student.getAdmAppln().getPersonalData().getMiddleName().trim().isEmpty()){
				studentName.append(" ");
				studentName.append(student.getAdmAppln().getPersonalData().getMiddleName());
			}
			if(student.getAdmAppln().getPersonalData().getLastName()!= null && !student.getAdmAppln().getPersonalData().getLastName().trim().isEmpty()){
				studentName.append(" ");
				studentName.append(student.getAdmAppln().getPersonalData().getLastName());
			}
			classStudentListTO.setStudentName(studentName.toString());
			
			
			StringBuffer rollNoRegNo = new StringBuffer();
			if(student.getRollNo()!= null &&  !student.getRollNo().trim().isEmpty() && 
				student.getRegisterNo()!= null && !student.getRegisterNo().trim().isEmpty()	){
				rollNoRegNo.append(student.getRollNo());
				rollNoRegNo.append("/");
				rollNoRegNo.append(student.getRegisterNo());
						
			}
			else if (student.getRollNo()!= null &&  !student.getRollNo().trim().isEmpty()){
				rollNoRegNo.append(student.getRollNo());
			}
			else if (student.getRegisterNo()!= null && !student.getRegisterNo().trim().isEmpty()){
				rollNoRegNo.append(student.getRegisterNo());
			}
			classStudentListTO.setRollRegNo(rollNoRegNo.toString());
			
			if(student.getAdmAppln().getPersonalData().getSecondLanguage()!= null){
				classStudentListTO.setLanguage(student.getAdmAppln().getPersonalData().getSecondLanguage());
			}
			
			if(student.getAdmAppln().getPersonalData().getResidentCategory()!= null && student.getAdmAppln().getPersonalData().getResidentCategory().getName()!= null){
				classStudentListTO.setStudentCategory(student.getAdmAppln().getPersonalData().getResidentCategory().getName());
			}
			
			if(student.getAdmAppln().getPersonalData().getReligion()!= null && student.getAdmAppln().getPersonalData().getReligion().getName()!= null){
				classStudentListTO.setReligion(student.getAdmAppln().getPersonalData().getReligion().getName());
			}
			if(student.getAdmAppln().getPersonalData().getResidentCategory()!= null && student.getAdmAppln().getPersonalData().getResidentCategory().getName()!= null){
				classStudentListTO.setStudentCategory(student.getAdmAppln().getPersonalData().getResidentCategory().getName());
			}

			if(student.getAdmAppln().getPersonalData().getReligionSectionOthers()!= null ){
				classStudentListTO.setCasteCategory(student.getAdmAppln().getPersonalData().getReligionSectionOthers());
			}
			else
			{
			if(student.getAdmAppln().getPersonalData().getReligionSection()!= null && student.getAdmAppln().getPersonalData().getReligionSection().getName()!= null){
				classStudentListTO.setCasteCategory(student.getAdmAppln().getPersonalData().getReligionSection().getName());
				
				}
			}
			
			classStudentToList.add(classStudentListTO);
			classStudentListTOMap.put(className.trim(), classStudentToList);
		}
		
		Iterator<String> keyIterator = classStudentListTOMap.keySet().iterator();
		ClasswithStudentTO classStTO; 
		List<ClasswithStudentTO> classWithStudList = new ArrayList<ClasswithStudentTO>();
		List<ClassStudentListTO> classwiseTotalStudentList = new ArrayList<ClassStudentListTO>();
		while (keyIterator.hasNext()) {
			String string = (String) keyIterator.next();
			classwiseTotalStudentList.addAll(classStudentListTOMap.get(string));
			classStTO= new ClasswithStudentTO();
			classStTO.setClassName(string);
			classStTO.setStudentList(classStudentListTOMap.get(string));
			classWithStudList.add(classStTO);
		
		}		
		stForm.setClassWithStudentToList(classWithStudList);
		return classwiseTotalStudentList;
	}
}
