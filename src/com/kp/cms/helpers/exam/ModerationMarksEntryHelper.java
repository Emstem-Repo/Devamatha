package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.bo.exam.ModerationMarksEntryBo;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ModerationMarksEntryForm;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;
import com.kp.cms.forms.exam.NewStudentMarksCorrectionForm;
import com.kp.cms.handlers.exam.ModerationMarksEntryHandler;
import com.kp.cms.to.exam.ModerationMarksEntryTo;
import com.kp.cms.to.exam.NewStudentMarkCorrectionTo;
import com.kp.cms.to.exam.SubjectMarksTO;

public class ModerationMarksEntryHelper {
	
	public static volatile ModerationMarksEntryHelper helper = null;
	
	public static ModerationMarksEntryHelper getInstance() {
		if (helper == null) {
			helper = new ModerationMarksEntryHelper();
		}
		return helper;
	}

	public void convertBotoTo(ModerationMarksEntryForm mForm,Map<Integer, ModerationMarksEntryBo> moderationMap,
			                                                      List<StudentFinalMarkDetails> finalMarkList) {
		List<ModerationMarksEntryTo> toList = new LinkedList<ModerationMarksEntryTo>();
		Integer courseid = 0;
		Iterator<StudentFinalMarkDetails> itr = finalMarkList.iterator();
		while (itr.hasNext()) {
			ModerationMarksEntryTo to = new ModerationMarksEntryTo();
			ModerationMarksEntryBo mBo = new ModerationMarksEntryBo();
			StudentFinalMarkDetails fmBo = (StudentFinalMarkDetails) itr.next();
			mForm.setStudentId(fmBo.getStudent().getId());
			mForm.setClassId(String.valueOf(fmBo.getClasses().getId()));
			// Setting existing  data from finalmark table
			courseid = fmBo.getClasses().getCourse().getId();
			if(fmBo.getExam().getId() > 0)
				to.setExamId(fmBo.getExam().getId());
			if(fmBo.getClasses().getId()>0){
				to.setClassId(fmBo.getClasses().getId());
				mForm.setClassName(fmBo.getClasses().getName());
			}
				
			if(fmBo.getSubject().getId() > 0)
				to.setSubjectId(fmBo.getSubject().getId());
			if(fmBo.getSubject().getName()!=null)
				to.setSubjectName(fmBo.getSubject().getName());
			if(fmBo.getSubject().getCode()!=null)
				to.setSubjectCode(fmBo.getSubject().getCode());
			if(fmBo.getSubject().getIsTheoryPractical()!=null)
				to.setTheoryPractical(fmBo.getSubject().getIsTheoryPractical());
			if(fmBo.getStudent().getId()>0){
				to.setStudentId(fmBo.getStudent().getId());
				mForm.setStudentName(fmBo.getStudent().getAdmAppln().getPersonalData().getFirstName());
			}
				
			if(fmBo.getStudentPracticalMarks()!=null && !StringUtils.isEmpty(fmBo.getStudentPracticalMarks()))
				to.setCurrentPracticalMarks(fmBo.getStudentPracticalMarks());
			    
			if(fmBo.getStudentTheoryMarks()!=null && !StringUtils.isEmpty(fmBo.getStudentTheoryMarks()))
				to.setCurrentTheoryMarks(fmBo.getStudentTheoryMarks());
			    
			
			// setting data from moderation table
			
			   if(moderationMap != null && moderationMap.size()!=0 && moderationMap.containsKey(fmBo.getSubject().getId())){
				   mBo =  moderationMap.get(fmBo.getSubject().getId());
				   
				   if(mBo.getComments()!=null)
					   to.setComments(mBo.getComments());
				   if(mBo.getId()>0)
					   to.setModerationId(mBo.getId());
				   
					   to.setModeration(mBo.getModeration());
					   to.setRevaluation(mBo.getRevaluation());
					
				    if(mBo.getPreviousPracticalMarks()!=null && !StringUtils.isEmpty(mBo.getPreviousPracticalMarks()))
				    	to.setPreviousPracticalMarks(mBo.getPreviousPracticalMarks());
				    
				    if(mBo.getPreviousTheoryMarks()!=null && !StringUtils.isEmpty(mBo.getPreviousTheoryMarks()))
				    	to.setPrevoiusTheoryMarks(mBo.getPreviousTheoryMarks());
				    
				    
				    if(mBo.getPracticalMarks()!=null && !StringUtils.isEmpty(mBo.getPracticalMarks())){
				    	to.setPracticalMarks(mBo.getPracticalMarks());
				        to.setOldPracticalMarks(mBo.getPracticalMarks());
				    }
				    if(mBo.getTheoryMarks()!=null && !StringUtils.isEmpty(mBo.getTheoryMarks())){
				    	to.setTheoryMarks(mBo.getTheoryMarks());
				        to.setOldTheoryMarks(mBo.getTheoryMarks());
				    }
			   }else{
				   if(mForm.getEntryType()!=null && mForm.getEntryType().equalsIgnoreCase("R")){
					   to.setModeration(false);
					   to.setRevaluation(true);
				   }
				   else  if(mForm.getEntryType()!=null && mForm.getEntryType().equalsIgnoreCase("M")) {
					   to.setModeration(true);
					   to.setRevaluation(false); 
				   }
			   }
			
			   
			   toList.add(to);
		}
		
		mForm.setCourseId(courseid.toString());
		mForm.setMarksList(toList);
		
	}

	public String getMaxMarksQuery(ModerationMarksEntryForm mForm) throws Exception {
		String query=" from SubjectRuleSettings s" +
		" where s.course.id="+mForm.getCourseId()+
		" and s.schemeNo=" +mForm.getSchemeNo();
		if(mForm.getYear()!=null && !mForm.getYear().isEmpty()){
			query=query+" and s.academicYear="+mForm.getAcademicYear();
		}else{
			if(mForm.getExamType().equals("Supplementary")){
				query=query+" and s.academicYear="+ mForm.getAcademicYear();
			}else{
				query=query+" and s.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+mForm.getExamId()+") ";
			}
		}
		query=query+" and s.isActive=1";
		return query;
	}

	public Map<Integer, SubjectMarksTO> convertMaxMark(List list) {
		Map<Integer, SubjectMarksTO> map  = new HashMap<Integer, SubjectMarksTO>();
		Iterator<SubjectRuleSettings> itr = list.iterator();
		while (itr.hasNext()) {
			SubjectRuleSettings bo = (SubjectRuleSettings) itr.next();
			SubjectMarksTO to = new SubjectMarksTO();
			
			if(bo.getPracticalEseEnteredMaxMark()!=null)
			to.setFinalPracticalMarks(bo.getPracticalEseEnteredMaxMark().toString());
			
			if(bo.getTheoryEseEnteredMaxMark()!=null)
			to.setFinalTheoryMarks(bo.getTheoryEseEnteredMaxMark().toString());
			
			map.put(bo.getSubject().getId(), to);
			
		}
		return map;
	}
	
	public List<ModerationMarksEntryBo> convertTotoBoList(ModerationMarksEntryForm mForm) throws Exception {
		List<ModerationMarksEntryTo> toList=mForm.getMarksList();
		List<ModerationMarksEntryBo> boList=new ArrayList<ModerationMarksEntryBo>();
		 
		Iterator<ModerationMarksEntryTo> itr=toList.iterator();
		while (itr.hasNext()) {
			boolean change = false;
			ModerationMarksEntryTo to = (ModerationMarksEntryTo) itr.next();
			ModerationMarksEntryBo bo = new ModerationMarksEntryBo();
			
			if(to.getComments()!=null)
			bo.setComments(to.getComments());
			bo.setCreatedBy(mForm.getUserId());
			bo.setModifiedBy(mForm.getUserId());
			bo.setLastModifiedDate(new Date());
			bo.setCreatedDate(new Date());
			bo.setRevaluation(to.isRevaluation());
			bo.setModeration(to.isModeration());
			if(to.getCurrentPracticalMarks()!=null)
			bo.setPreviousPracticalMarks(to.getCurrentPracticalMarks());
			if(to.getCurrentTheoryMarks()!=null)
			bo.setPreviousTheoryMarks(to.getCurrentTheoryMarks());
			if(to.getStudentId()>0)
				bo.setStudentId(to.getStudentId());
			if(to.getSubjectId()>0)
				bo.setSubjectId(to.getSubjectId());
			if(to.getClassId()>0)
				bo.setClassId(to.getClassId());
			if(to.getExamId()>0)
				bo.setExamId(to.getExamId());
			if(to.getModerationId()>0){
				bo.setId(to.getModerationId());	
				
				if(to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty() && !to.getOldPracticalMarks().trim().equalsIgnoreCase(to.getPracticalMarks().trim())){
					bo.setPracticalMarks(to.getPracticalMarks());
					change = true;
				}
				if(to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty() && !to.getOldTheoryMarks().trim().equalsIgnoreCase(to.getTheoryMarks().trim())){
					bo.setTheoryMarks(to.getTheoryMarks());
					change = true;
				}
			}else{
				if(to.getPracticalMarks()!=null && !StringUtils.isEmpty(to.getPracticalMarks())){
					bo.setPracticalMarks(to.getPracticalMarks());
					change = true;
				}
				if(to.getTheoryMarks()!=null && !StringUtils.isEmpty(to.getTheoryMarks())){
					bo.setTheoryMarks(to.getTheoryMarks());
					change = true;
				}
			}
			if(change)
				boList.add(bo);
			
			
		}
		return boList;
	}
	
	public void convertBotoToForRevaluation(NewExamMarksEntryForm newExamMarksEntryForm,Map<Integer, ModerationMarksEntryBo> moderationMap,
			List<StudentFinalMarkDetails> finalMarkList) {
		List<ModerationMarksEntryTo> toList = new LinkedList<ModerationMarksEntryTo>();
		Integer courseid = 0;
		Iterator<StudentFinalMarkDetails> itr = finalMarkList.iterator();
		while (itr.hasNext()) {
			ModerationMarksEntryTo to = new ModerationMarksEntryTo();
			ModerationMarksEntryBo mBo = new ModerationMarksEntryBo();
			StudentFinalMarkDetails fmBo = (StudentFinalMarkDetails) itr.next();
			// Setting existing  data from finalmark table
			courseid = fmBo.getClasses().getCourse().getId();
			if(fmBo.getExam().getId() > 0)
				to.setExamId(fmBo.getExam().getId());
			if(fmBo.getClasses().getId()>0){
				to.setClassId(fmBo.getClasses().getId());
				newExamMarksEntryForm.setClassName(fmBo.getClasses().getName());
			}
			if(fmBo.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null)
				to.setStudentName(fmBo.getStudent().getAdmAppln().getPersonalData().getFirstName());
			to.setRegNo(fmBo.getStudent().getRegisterNo());
			
			if(fmBo.getSubject().getId() > 0)
				to.setSubjectId(fmBo.getSubject().getId());
			if(fmBo.getSubject().getName()!=null)
				to.setSubjectName(fmBo.getSubject().getName());
			if(fmBo.getSubject().getCode()!=null)
				to.setSubjectCode(fmBo.getSubject().getCode());
			if(fmBo.getSubject().getIsTheoryPractical()!=null)
				to.setTheoryPractical(fmBo.getSubject().getIsTheoryPractical());
			if(fmBo.getStudent().getId()>0){
				to.setStudentId(fmBo.getStudent().getId());
				newExamMarksEntryForm.setStudentName(fmBo.getStudent().getAdmAppln().getPersonalData().getFirstName());
			}

			if(fmBo.getStudentPracticalMarks()!=null && !StringUtils.isEmpty(fmBo.getStudentPracticalMarks()))
				to.setCurrentPracticalMarks(fmBo.getStudentPracticalMarks());

			if(fmBo.getStudentTheoryMarks()!=null && !StringUtils.isEmpty(fmBo.getStudentTheoryMarks()))
				to.setCurrentTheoryMarks(fmBo.getStudentTheoryMarks());


			// setting data from moderation table

			if(moderationMap != null && moderationMap.size()!=0 && moderationMap.containsKey(fmBo.getStudent().getId())){
				mBo =  moderationMap.get(fmBo.getStudent().getId());

				if(mBo.getComments()!=null)
					to.setComments(mBo.getComments());
				if(mBo.getId()>0)
					to.setModerationId(mBo.getId());

				to.setModeration(mBo.getModeration());
				to.setRevaluation(mBo.getRevaluation());

				if(mBo.getPreviousPracticalMarks()!=null && !StringUtils.isEmpty(mBo.getPreviousPracticalMarks()))
					to.setPreviousPracticalMarks(mBo.getPreviousPracticalMarks());

				if(mBo.getPreviousTheoryMarks()!=null && !StringUtils.isEmpty(mBo.getPreviousTheoryMarks()))
					to.setPrevoiusTheoryMarks(mBo.getPreviousTheoryMarks());


				if(mBo.getPracticalMarks()!=null && !StringUtils.isEmpty(mBo.getPracticalMarks())){
					to.setPracticalMarks(mBo.getPracticalMarks());
					to.setPreviousPracticalMarks(mBo.getPracticalMarks());
				}
				if(mBo.getTheoryMarks()!=null && !StringUtils.isEmpty(mBo.getTheoryMarks())){
					to.setTheoryMarks(mBo.getTheoryMarks());
					to.setPrevoiusTheoryMarks(mBo.getTheoryMarks());
				}
		
			}else{
				if(newExamMarksEntryForm.getEntryType()!=null && newExamMarksEntryForm.getEntryType().equalsIgnoreCase("R")){
					to.setModeration(false);
					to.setRevaluation(true);
				}
				else  if(newExamMarksEntryForm.getEntryType()!=null && newExamMarksEntryForm.getEntryType().equalsIgnoreCase("S")) {
					to.setScrutiny(true);
				}
				else  if(newExamMarksEntryForm.getEntryType()!=null && newExamMarksEntryForm.getEntryType().equalsIgnoreCase("CV")) {
					to.setChallengeValuation(true);
				}
				else{
					to.setModeration(true);
					to.setRevaluation(false);
				}
			}


			toList.add(to);
		}

		newExamMarksEntryForm.setCourseId(courseid.toString());
		newExamMarksEntryForm.setMarksList(toList);

}
	
	public List<ModerationMarksEntryBo> convertTotoBoListForRevaluation(List<ModerationMarksEntryTo> toList,String userId,String entryType) throws Exception {
		List<ModerationMarksEntryBo> boList=new ArrayList<ModerationMarksEntryBo>();
		 
		Iterator<ModerationMarksEntryTo> itr=toList.iterator();
		while (itr.hasNext()) {
			boolean change = false;
			ModerationMarksEntryTo to = (ModerationMarksEntryTo) itr.next();
			ModerationMarksEntryBo bo = new ModerationMarksEntryBo();
			
			if(to.getComments()!=null)
			bo.setComments(to.getComments());
			bo.setCreatedBy(userId);
			bo.setModifiedBy(userId);
			bo.setLastModifiedDate(new Date());
			bo.setCreatedDate(new Date());
			bo.setRevaluation(to.isRevaluation());
			bo.setModeration(to.isModeration());
			if(to.getCurrentPracticalMarks()!=null)
			bo.setPreviousPracticalMarks(to.getCurrentPracticalMarks());
			if(to.getCurrentTheoryMarks()!=null)
			bo.setPreviousTheoryMarks(to.getCurrentTheoryMarks());
			if(to.getStudentId()>0)
				bo.setStudentId(to.getStudentId());
			if(to.getSubjectId()>0)
				bo.setSubjectId(to.getSubjectId());
			if(to.getClassId()>0)
				bo.setClassId(to.getClassId());
			if(to.getExamId()>0)
				bo.setExamId(to.getExamId());
			if(to.getModerationId()>0){
				bo.setId(to.getModerationId());	
				
				if(to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty() && !to.getPreviousPracticalMarks().trim().equalsIgnoreCase(to.getPracticalMarks().trim())){
					bo.setPracticalMarks(to.getPracticalMarks());
					change = true;
				}
				if(to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty() && !to.getPrevoiusTheoryMarks().trim().equalsIgnoreCase(to.getTheoryMarks().trim())){
					bo.setTheoryMarks(to.getTheoryMarks());
					change = true;
				}
			}else{	if(to.getPracticalMarks()!=null && !StringUtils.isEmpty(to.getPracticalMarks())){
				bo.setPracticalMarks(to.getPracticalMarks());
				change = true;
			}
			if(to.getTheoryMarks()!=null && !StringUtils.isEmpty(to.getTheoryMarks())){
				bo.setTheoryMarks(to.getTheoryMarks());
				change = true;
			}}
			if("R".equalsIgnoreCase(entryType)){
				bo.setRevaluation(true);
			}
			else if("S".equalsIgnoreCase(entryType)){
				bo.setScrutiny(true);
			}
			else if("CV".equalsIgnoreCase(entryType)){
				bo.setChallengeValuation(true);
			}
			if(change)
				boList.add(bo);
			
			
		}
		return boList;
	}
	
	public String getMaxMarksQueryForRevaluation(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		String query=" from SubjectRuleSettings s" +
		" where s.course.id="+newExamMarksEntryForm.getCourseId()+
		" and s.schemeNo=" +newExamMarksEntryForm.getSchemeNo();
		if(newExamMarksEntryForm.getYear()!=null && !newExamMarksEntryForm.getYear().isEmpty()){
			query=query+" and s.academicYear="+newExamMarksEntryForm.getYear();
		}else{
			if(newExamMarksEntryForm.getExamType().equals("Supplementary")){
				query=query+" and s.academicYear=(select e.examForJoiningBatch from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+") ";
			}else{
				query=query+" and s.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+") ";
			}
		}
		query=query+" and s.isActive=1";
		return query;
	}

	
}
