package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.reports.ViewInternalMarksForm;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;


public class ViewInternalMarkHelper {
	private static final Log log = LogFactory.getLog(ViewInternalMarkHelper.class);
	
	public static volatile ViewInternalMarkHelper self=null;
	/**
	 * @return
	 */
	public static ViewInternalMarkHelper getInstance(){
		if(self==null){
			self= new ViewInternalMarkHelper();
		}
		return self;
	}
	private ViewInternalMarkHelper(){}

	
	/**
	 * @param examMarksEntryDetailsBOList
	 * @return
	 */
	public List<ExamMarksEntryDetailsTO> convertMarkList(List<MarksEntryDetails> examMarksEntryDetailsBOList,List<Integer> intExamIds, ViewInternalMarksForm viewInternalMarksForm){
		log.info("Entering into convertMarkListToMap of StudentAttendanceSummaryHelper");
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList = new ArrayList<ExamMarksEntryDetailsTO>();
		String subjectName=null;
		String className=null;
		if(examMarksEntryDetailsBOList!=null && !examMarksEntryDetailsBOList.isEmpty()){
			Map<Integer,Map<Integer,ExamMarksEntryDetailsTO>> mainMap=new HashMap<Integer, Map<Integer,ExamMarksEntryDetailsTO>>();
			Map<Integer,ExamMarksEntryDetailsTO> stuMap=new HashMap<Integer, ExamMarksEntryDetailsTO>();
			ExamMarksEntryDetailsTO examMarksEntryDetailsTO;
			Iterator<MarksEntryDetails> markBoItr = examMarksEntryDetailsBOList.iterator();
			while (markBoItr.hasNext()) {
				MarksEntryDetails bo =markBoItr.next();
				if(mainMap.containsKey(bo.getMarksEntry().getStudent().getId()))
					stuMap=mainMap.remove(bo.getMarksEntry().getStudent().getId());
				else
					stuMap=new HashMap<Integer, ExamMarksEntryDetailsTO>();
				
				if(stuMap.containsKey(bo.getMarksEntry().getExam().getId()))
					examMarksEntryDetailsTO=stuMap.remove(bo.getMarksEntry().getExam().getId());
				else{
					Iterator<Integer> itr=intExamIds.iterator();
					while (itr.hasNext()) {
						Integer examId= (Integer) itr.next();
						examMarksEntryDetailsTO=new ExamMarksEntryDetailsTO();
						examMarksEntryDetailsTO.setId(examId);
						stuMap.put(examId, new ExamMarksEntryDetailsTO());
					}
					examMarksEntryDetailsTO=stuMap.remove(bo.getMarksEntry().getExam().getId());
				}
				if(bo.getMarksEntry().getExam().getName()!=null && !bo.getMarksEntry().getExam().getName().isEmpty())
					examMarksEntryDetailsTO.setExamName(bo.getMarksEntry().getExam().getName());
				if(bo.getMarksEntry().getClasses().getName()!=null && !bo.getMarksEntry().getClasses().getName().isEmpty())
					examMarksEntryDetailsTO.setClassName(bo.getMarksEntry().getClasses().getName());
				if(bo.getSubject().getName()!=null && !bo.getSubject().getName().isEmpty())
					examMarksEntryDetailsTO.setSubjectName(bo.getSubject().getName());
				if(bo.getTheoryMarks()!=null && !bo.getTheoryMarks().isEmpty())
					examMarksEntryDetailsTO.setTheoryMarks(bo.getTheoryMarks());
				if(bo.getPracticalMarks()!=null && !bo.getPracticalMarks().isEmpty())
					examMarksEntryDetailsTO.setPracticalMarks(bo.getPracticalMarks());
				if(bo.getMarksEntry().getStudent().getRegisterNo()!=null && !bo.getMarksEntry().getStudent().getRegisterNo().isEmpty())
					examMarksEntryDetailsTO.setRegNo(bo.getMarksEntry().getStudent().getRegisterNo());
				if(bo.getMarksEntry().getStudent().getAdmAppln().getPersonalData().getFirstName()!=null && !bo.getMarksEntry().getStudent().getAdmAppln().getPersonalData().getFirstName().isEmpty())
					examMarksEntryDetailsTO.setStudentName(bo.getMarksEntry().getStudent().getAdmAppln().getPersonalData().getFirstName());

				stuMap.put(bo.getMarksEntry().getExam().getId(),examMarksEntryDetailsTO);
				mainMap.put(bo.getMarksEntry().getStudent().getId(), stuMap);
//				examMarksEntryDetailsTOList.add(examMarksEntryDetailsTO);
			}
			if(!mainMap.isEmpty()){
				Iterator<Map.Entry<Integer,Map<Integer,ExamMarksEntryDetailsTO>>> entrys=mainMap.entrySet().iterator();
				while (entrys.hasNext()) {
					examMarksEntryDetailsTO =new ExamMarksEntryDetailsTO();
					List<ExamMarksEntryDetailsTO> tos = new ArrayList<ExamMarksEntryDetailsTO>();
					int count=0;
					Map.Entry<Integer, Map<Integer, ExamMarksEntryDetailsTO>> entry = entrys.next();
					Iterator<Map.Entry<Integer,ExamMarksEntryDetailsTO>> entrys1=entry.getValue().entrySet().iterator();
					while (entrys1.hasNext()) {
						Map.Entry<Integer, ExamMarksEntryDetailsTO> entry2 =  entrys1 .next();
						if(count==0){
							examMarksEntryDetailsTO.setRegNo(entry2.getValue().getRegNo());
							examMarksEntryDetailsTO.setStudentName(entry2.getValue().getStudentName());
							examMarksEntryDetailsTO.setSubjectName(entry2.getValue().getSubjectName());
							examMarksEntryDetailsTO.setClassName(entry2.getValue().getClassName());
							
						} 
						tos.add(entry2.getValue());
					}
					
				//	Collections.sort(tos,new ExamRegNoComparator());
					viewInternalMarksForm.setSubjectName(examMarksEntryDetailsTO.getSubjectName());
					viewInternalMarksForm.setClassName(examMarksEntryDetailsTO.getClassName());
					examMarksEntryDetailsTO.setTos(tos);
					examMarksEntryDetailsTOList.add(examMarksEntryDetailsTO);
				}
			}
		}
		
		
		return examMarksEntryDetailsTOList;
	}
	/**
	 * @param viewInternalMarksForm
	 * @return
	 */
	public String getInternalExamIdsQuery( ViewInternalMarksForm viewInternalMarksForm) throws Exception{
		String query="select md.marksEntry.exam.id from MarksEntryDetails md where md.marksEntry.exam.examTypeID in (4,5) and  md.marksEntry.classes.id="+viewInternalMarksForm.getClassId()+" and md.subject.id="+viewInternalMarksForm.getSubjectId()+" group by md.marksEntry.exam.id";
		return query;
	}
}
