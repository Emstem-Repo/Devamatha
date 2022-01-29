package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.forms.admission.CheckListForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.to.admission.DocTO;

/**
 *  Helper class from Checklist entry.
 */
public class CheckListHelper {

	private static volatile CheckListHelper checkListHelper = null;

	private CheckListHelper() {

	}

	public static CheckListHelper getInstance() {
		if (checkListHelper == null) {
			checkListHelper = new CheckListHelper();
		}
		return checkListHelper;
	}
	
	/**
	 * 
	 * @param list
	 * @return List<DocTO
	 * @throws Exception
	 * will returns the checklist TO's construted from form.
	 */
	public List<DocTO> getCheckListDocFromDocType(List<DocType> list) throws Exception{
		List<DocTO> checkList = new ArrayList<DocTO>();
		Iterator<DocType> it = list.iterator();
		DocType docType;
		DocTO docTO;
		while (it.hasNext()) {
			docType = it.next();
			docTO = new DocTO();
			docTO.setDocTypeId(docType.getId());
			docTO.setName(docType.getName());
			docTO.setNeedToProduce("false");
			docTO.setNeedToProduceSemwiseMc("false");
			docTO.setIsMarksCard("false");
			docTO.setIsConsolidatedMarks("false");
			docTO.setSemesterWise("false");
			docTO.setPreviousExam("false");
			docTO.setIsIncludeLanguage("false");
			docTO.setIsExamRequired("false");
			docTO.setSelect("off");
			//Null check Given by Manu
			if(docType.getIsEducationalInfo()!=null && docType.getIsEducationalInfo()){
				docTO.setIsEducationalInfo("Yes");
			}else{
				docTO.setIsEducationalInfo("No");
			}
			//}
			//listTO.setDocTO(docTO);
			checkList.add(docTO);
		}
		return checkList;
	}

	
	/**
	 * 
	 * @param checklist
	 * @return List<DocTO>
	 * @throws Exception
	 * 		   convert from BO objects to TO objects.
	 */
	public List<DocTO> convertBOstoTOs(List<DocChecklist> checklist) throws Exception{
		List<DocTO> docToList = new ArrayList<DocTO>();

		DocTO docTO;
		Iterator<DocChecklist> iter = checklist.iterator();
//		CheckListTO checkTO;
		DocChecklist checkList;
		while (iter.hasNext()) {
			docTO = new DocTO();
			checkList = iter.next();
		
			docTO.setId(checkList.getId());
			docTO.setDocTypeId(checkList.getDocType().getId());
			docTO.setName(checkList.getDocType().getName());
			docTO.setIsMarksCard(checkList.getIsMarksCard().toString());
			docTO.setIsConsolidatedMarks(checkList.getIsConsolidatedMarks().toString());
			docTO.setNeedToProduce(checkList.getNeedToProduce().toString());
			docTO.setSemesterWise(checkList.getIsSemesterWise().toString());
			docTO.setPreviousExam(checkList.getIsPreviousExam().toString());
			docTO.setIsIncludeLanguage(checkList.getIsIncludeLanguage().toString());
			docTO.setIsExamRequired(checkList.getIsExamRequired().toString());
			docTO.setSelect("off");
			docTO.setTempSelect("on");
			docTO.setCreatedBy(checkList.getCreatedBy());
			if(checkList.getNeedToProduceSemwiseMc()!=null){
				docTO.setNeedToProduceSemwiseMc(checkList.getNeedToProduceSemwiseMc().toString());
			}else{
				docTO.setNeedToProduceSemwiseMc("false");
			}
			// code added by chandra
			if(checkList.getDocType()!=null && !checkList.getDocType().toString().isEmpty()){
				if(checkList.getDocType().getIsEducationalInfo()){
					docTO.setIsEducationalInfo("Yes");
				}else{
					docTO.setIsEducationalInfo("No");
				}
			}
			
			//
			docToList.add(docTO);
		}
		return docToList;
	}
	
	/**
	 * 
	 * @param checklist
	 * @return
	 * @throws Exception
	 *         Convert the BO list to TO list.
	 */
	public List<CheckListTO> convertBOstoCheckListTOs(List<DocChecklist> checklist) throws Exception{
		List<CheckListTO> chlist = new ArrayList<CheckListTO>();

		CourseTO courseTo;
		ProgramTypeTO programTypeTo;
		ProgramTO programTo;

		Iterator<DocChecklist> iter = checklist.iterator();
		CheckListTO checkTO;
		while (iter.hasNext()) {
			courseTo = new CourseTO();
			programTypeTo = new ProgramTypeTO();
			programTo = new ProgramTO();

			DocChecklist check = (DocChecklist) iter.next();
			checkTO = new CheckListTO();
			checkTO.setDocChecklistId(check.getId());

			String year1 = String.valueOf(check.getYear() + 1);
			checkTO.setCombinedYear(String.valueOf(check.getYear())+"-"+year1);
            checkTO.setYear(String.valueOf(check.getYear()));
	
			// setting to course, program, programType names to CheckListTO instance which are collecting from DocCheckList instance..
			programTypeTo.setProgramTypeName(check.getCourse().getProgram().getProgramType().getName());
			programTypeTo.setProgramTypeId(check.getCourse().getProgram().getProgramType().getId());
			programTo.setId(check.getCourse().getProgram().getId());
			programTo.setName(check.getCourse().getProgram().getName());
			programTo.setProgramTypeTo(programTypeTo);
			courseTo.setId(check.getCourse().getId());
			courseTo.setCode(check.getCourse().getName());
			courseTo.setProgramTo(programTo);
			checkTO.setCourseTo(courseTo);
			
			DocType type = new DocType();
			check.setDocType(type);
			type = check.getDocType();
			
			if (type != null) {
				checkTO.setDocTypeId(type.getId());
				checkTO.setName(type.getName());
			}

			DocTO docTO = new DocTO();
			docTO.setId(checkTO.getDocTypeId());
			docTO.setName(checkTO.getName());
			docTO.setIsMarksCard(checkTO.getIsMarks_card());
			docTO.setIsConsolidatedMarks(checkTO.getConsolidated_Marks());
			docTO.setNeedToProduce(checkTO.getNeed_To_Produce());
			docTO.setSemesterWise(checkTO.getSemesterWise());
			docTO.setPreviousExam(checkTO.getPreviousExam());
			docTO.setIsIncludeLanguage(checkTO.getIsIncludeLanguage());
			docTO.setIsExamRequired(checkTO.isExamRequired());
			checkTO.setDocTO(docTO);
			checkTO.setIsMarks_card(convertBoolValue(check.getIsMarksCard()));
			checkTO.setConsolidated_Marks(convertBoolValue(check.getIsConsolidatedMarks()));
			checkTO.setNeed_To_Produce(convertBoolValue(check.getNeedToProduce()));
			checkTO.setSemesterWise(convertBoolValue(check.getIsSemesterWise()));
			checkTO.setPreviousExam(convertBoolValue(check.getIsPreviousExam()));
			checkTO.setIsIncludeLanguage(convertBoolValue(check.getIsIncludeLanguage()));
			if(check.getIsExamRequired()!=null)
			checkTO.setExamRequired(convertBoolValue(check.getIsExamRequired()));
			else{
				checkTO.setExamRequired("No");	
			}
			checkTO.setCreatedBy(check.getCreatedBy());
			checkTO.setCreatedDate(check.getCreatedDate());
			//setting to CheckListTO instance and adding the TO instance to list..
			chlist.add(checkTO);
		}
		return chlist;
	}
	
	/**
	 * 
	 * @param value
	 * @return yes/no based on boolean values.
	 */
	private String convertBoolValue(Boolean value) {
		if (value.booleanValue() == false) 
			return "No";
		 else 
			return "Yes";
	}

	/**
	 * 	
	 * @param docList
	 * @param checkListTO
	 * @return List<DocChecklist>
	 * @throws Exception
	 * 			Convert from TO's to BO's.
	 */
	public List<DocChecklist> convertTOstoBOs(List<DocTO> docList, CheckListTO checkListTO,CheckListForm checkListForm) throws Exception{

		
		DocChecklist checklist;
		DocType docType ;
		Iterator<DocTO> iter=docList.iterator();
		List<DocChecklist>  docCheckList = new ArrayList<DocChecklist>();
		DocTO docTO;
		Course course;
		Program program;
		while (iter.hasNext()) {
			docTO = (DocTO) iter.next();
			checklist = new DocChecklist();
			if(docTO.getId() != 0)
				checklist.setId(docTO.getId());
			
			checklist.setIsMarksCard(convertStringValue(docTO.getIsMarksCard()));
			checklist.setNeedToProduce(convertStringValue(docTO.getNeedToProduce()));
			if(docTO.getNeedToProduce().equals("false")){
				checklist.setNeedToProduceSemwiseMc(false);
			}else{
				if(docTO.getNeedToProduceSemwiseMc()!=null && docTO.getNeedToProduceSemwiseMc().equals("true")){
					checklist.setNeedToProduceSemwiseMc(true);
				}else{
					checklist.setNeedToProduceSemwiseMc(false);
				}
			}
			
			if(docTO.getIsMarksCard().equals("false")) {
				checklist.setIsConsolidatedMarks(false);
				checklist.setIsSemesterWise(false);
				checklist.setIsPreviousExam(false);
				checklist.setIsIncludeLanguage(false);
				checklist.setIsExamRequired(false);	
			} else {
				checklist.setIsConsolidatedMarks(convertStringValue(docTO.getIsConsolidatedMarks()));
				if(docTO.getIsConsolidatedMarks().equals("true")){
					checklist.setIsSemesterWise(false);
				} else {
					checklist.setIsSemesterWise(convertStringValue(docTO.getSemesterWise()));
				}
				if(checklist.getIsSemesterWise() == false){
					checklist.setIsIncludeLanguage(false);
				} else {
					checklist.setIsIncludeLanguage(convertStringValue(docTO.getIsIncludeLanguage()));
				}
				checklist.setIsPreviousExam(convertStringValue(docTO.getPreviousExam()));
				if(docTO.getIsExamRequired()!=null)
				checklist.setIsExamRequired(convertStringValue(docTO.getIsExamRequired()));
				else
					checklist.setIsExamRequired(false);	
			}
			checklist.setIsDocSelected(true);
			checklist.setIsActive(docTO.isActive());
			
			docType = new DocType();
			docType.setId(docTO.getDocTypeId());
			checklist.setDocType(docType);
			
			course = new Course();
			course.setId(Integer.parseInt(checkListForm.getCourse()));
			checklist.setCourse(course);

			program = new Program();
			program.setId(Integer.parseInt(checkListForm.getProgram()));
			checklist.setProgram(program);
			
			checklist.setYear(Integer.parseInt(checkListForm.getYear()));
			checklist.setCreatedDate(new Date());
			checklist.setCreatedBy(docTO.getCreatedBy());
			checklist.setLastModifiedDate(new Date());
			checklist.setModifiedBy(checkListForm.getUserId());
			docCheckList.add(checklist);
		}
		return docCheckList;
	}
	
	/**
	 * 
	 * @param doclist
	 * @return DocTO's list
	 * @throws Exception
	 * 		   creates the list To objects.
	 */
	public List<DocTO> getTOFromForm(List<DocChecklist> doclist) throws Exception{
		CheckListTO checkListTO=new CheckListTO();
		List<DocTO> list=new ArrayList<DocTO>();
		Iterator<DocChecklist> itr=doclist.iterator();
		CourseTO courseTo;
		ProgramTypeTO programTypeTo;
		ProgramTO programTo;
		
		while (itr.hasNext()) {
			courseTo = new CourseTO();
			programTypeTo = new ProgramTypeTO();
			programTo = new ProgramTO();
			
			DocChecklist checklist = itr.next();
			checkListTO.setConsolidated_Marks(convertBoolValue(checklist.getIsConsolidatedMarks()));
			checkListTO.setIsMarks_card(convertBoolValue(checklist.getIsMarksCard()));
			checkListTO.setNeed_To_Produce(convertBoolValue(checklist.getNeedToProduce()));
			checkListTO.setSemesterWise(convertBoolValue(checklist.getIsSemesterWise()));
			checkListTO.setPreviousExam(convertBoolValue(checklist.getIsPreviousExam()));
			checkListTO.setYear(String.valueOf(checklist.getYear()));
			checkListTO.setIsIncludeLanguage(String.valueOf(checklist.getIsIncludeLanguage()));
			checkListTO.setExamRequired(String.valueOf(checklist.getIsExamRequired()));
			programTypeTo.setProgramTypeId(checklist.getCourse().getProgram().getProgramType().getId());
			programTo.setId(checklist.getCourse().getProgram().getId());
			programTo.setProgramTypeTo(programTypeTo);
			courseTo.setId(checklist.getCourse().getId());
			checkListTO.setCourseTo(courseTo);
			
			DocType type = checklist.getDocType();
			if(checklist.getIsDocSelected()==false)
				checkListTO.setSelect(false);
			else
				checkListTO.setSelect(true);
			if (type != null) {
				checkListTO.setDocTypeId(type.getId());
				checkListTO.setName(type.getName());
			}
			checklist.setDocType(type);
			Set<DocChecklist> set=type.getDocChecklists();
			type.setDocChecklists(set);
			DocTO docTO = new DocTO();
			docTO.setCourseTo(courseTo);
			docTO.setYear(String.valueOf(checklist.getYear()));
			docTO.setId(checkListTO.getDocTypeId());
			docTO.setName(checkListTO.getName());
			docTO.setIsMarksCard(checkListTO.getIsMarks_card());
			docTO.setIsConsolidatedMarks(checkListTO.getConsolidated_Marks());
			docTO.setNeedToProduce(checkListTO.getNeed_To_Produce());
			docTO.setSemesterWise(checkListTO.getSemesterWise());
			docTO.setPreviousExam(checkListTO.getPreviousExam());
			docTO.setIsIncludeLanguage(checkListTO.getIsIncludeLanguage());
			docTO.setIsExamRequired(checkListTO.isExamRequired());
			docTO.setSelect(Boolean.valueOf(checkListTO.isSelect()).toString());
			checkListTO.setDocTO(docTO);
			
			list.add(docTO);
		}
		return list;
	}
	/**
	 * Converts String value to Boolean value of String type.
	 * 
	 * @param value
	 *            - Represents the String type.
	 * @return Boolean.
	 */
	
	private Boolean convertStringValue(String value) {
		if(value.equals("false")) 
			return Boolean.valueOf(false);
		 else 
			return Boolean.valueOf(true);
	}
}