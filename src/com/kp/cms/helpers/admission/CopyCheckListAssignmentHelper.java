package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.forms.admission.CopyCheckListAssignmentForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.to.admission.DocTO;
import com.kp.cms.transactionsimpl.admission.CopyCheckListAssignmentTransImpl;
import com.kp.cms.transactionsimpl.admission.CopyClassesTransactionImpl;

	public class CopyCheckListAssignmentHelper {
		private static final Log log = LogFactory.getLog(CopyCheckListAssignmentHelper.class);
		private static volatile CopyCheckListAssignmentHelper copyCheckListHelper = null;
		
		public static CopyCheckListAssignmentHelper getInstance()
		{
			if(copyCheckListHelper==null){
				copyCheckListHelper=new CopyCheckListAssignmentHelper();
			}
			return copyCheckListHelper;
		}

		public List<CheckListTO> convertBOstoCheckListTOs(List<DocChecklist> docChecklist) throws Exception{
			List<CheckListTO> chlist = new ArrayList<CheckListTO>();

			CourseTO courseTo;
			ProgramTypeTO programTypeTo;
			ProgramTO programTo;

			Iterator<DocChecklist> iter = docChecklist.iterator();
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
				checkTO.setCourse_year(check.getCourse().getId()+"_"+check.getYear());
				
				DocType type = check.getDocType();
				
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
				//setting to CheckListTO instance and adding the TO instance to list..
				chlist.add(checkTO);
			}
			return chlist;
	}
		
		private String convertBoolValue(Boolean value) {
			if (value.booleanValue() == false) 
				return "No";
			 else 
				return "Yes";
	}
		
		
		public List<DocChecklist> convertTOToBO(
				CopyCheckListAssignmentForm copyCheckListAssignmentForm) throws Exception {
			List<DocChecklist> docListBO= new ArrayList<DocChecklist>();
			List<CheckListTO> backUpCheckList = copyCheckListAssignmentForm.getBackupCheckList();
			List<CheckListTO> dispList = copyCheckListAssignmentForm.getDisplayCheckList();
			for(CheckListTO disp:dispList){
				if(disp.isCheckListSel())
				{
					for(CheckListTO bacList:backUpCheckList){
						if(disp.getCourse_year().equalsIgnoreCase(bacList.getCourse_year())){
							
							DocChecklist docList = new DocChecklist();
							Course course = new Course();
							DocType docType = new DocType();
							Program program = new Program();
							int courseId=bacList.getCourseTo().getId();
							int progId=bacList.getCourseTo().getProgramTo().getId();
							int year=Integer.parseInt(copyCheckListAssignmentForm.getToYear());
							String query=" from DocChecklist doc " +
							"where doc.course.id ="+courseId+" and doc.program.id ="+progId+
									" and doc.year="+year+"";
							
							boolean isDup;
							isDup = CopyCheckListAssignmentTransImpl.getInstance().checkDuplicate(query);
							if(!isDup){
							course.setId(courseId);
							docList.setCourse(course);
							
							docType.setId(bacList.getDocTypeId());
							docList.setDocType(docType);
							docList.setYear(year);
							docList.setIsConsolidatedMarks(convertStringValue(bacList.getConsolidated_Marks()));
							docList.setIsActive(true);
							docList.setIsExamRequired(convertStringValue(bacList.isExamRequired()));
							docList.setIsIncludeLanguage(convertStringValue(bacList.getIsIncludeLanguage()));
							docList.setIsDocSelected(bacList.isSelect());
							docList.setNeedToProduce(convertStringValue(bacList.getNeed_To_Produce()));
							docList.setIsPreviousExam(convertStringValue(bacList.getPreviousExam()));
							docList.setIsSemesterWise(convertStringValue(bacList.getSemesterWise()));
							docList.setCreatedBy(copyCheckListAssignmentForm.getUserId());
							docList.setCreatedDate(new Date());
							docList.setModifiedBy(copyCheckListAssignmentForm.getUserId());
							docList.setLastModifiedDate(new Date());
							docList.setIsMarksCard(convertStringValue(bacList.getIsMarks_card()));
							program.setId(progId);	
							docList.setProgram(program);
							docListBO.add(docList);
							}
						}
					}
				}
			}
			return docListBO;
	}
		
		private  Boolean convertStringValue(String value) {
			if (value.equalsIgnoreCase("Yes")) 
				return true;
			 else 
				return false;
		}
}
