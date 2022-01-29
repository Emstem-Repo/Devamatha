package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamMarksVerificationEntryBO;
import com.kp.cms.forms.exam.ExamMarksVerificationCorrectionForm;
import com.kp.cms.to.exam.ExamEvaluatorTypeMarksTo;
import com.kp.cms.to.exam.ExamMarksVerificationCorrectionTo;
import com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction;
import com.kp.cms.transactionsimpl.exam.ExamMarksVerificationCorrectionTransactionImpl;


public class ExamMarksVerificationCorrectionHelper {
	private static volatile ExamMarksVerificationCorrectionHelper examMarksVerificationCorrectionHelper = null;
	private static final Log log = LogFactory.getLog(ExamMarksVerificationCorrectionHelper.class);
	
	/**
	 * @return
	 */
	public static ExamMarksVerificationCorrectionHelper getInstance() {
		if (examMarksVerificationCorrectionHelper == null) {
			examMarksVerificationCorrectionHelper = new ExamMarksVerificationCorrectionHelper();
		}
		return examMarksVerificationCorrectionHelper;
	}
		
	/**
	 * @param verificationList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,ExamEvaluatorTypeMarksTo> convertBotoMap(List<ExamMarksVerificationEntryBO> verificationList,ExamMarksVerificationCorrectionForm form) throws Exception{
		Map<Integer,ExamEvaluatorTypeMarksTo> map=new HashMap<Integer,ExamEvaluatorTypeMarksTo>();
		IExamMarksVerificationCorrectionTransaction transaction1=ExamMarksVerificationCorrectionTransactionImpl.getInstance();
		if(verificationList!=null && !verificationList.isEmpty()){
			Iterator<ExamMarksVerificationEntryBO> itr=verificationList.iterator();
			while (itr.hasNext()) {
				ExamMarksVerificationEntryBO bo = itr.next();
				if(bo.getSubjectId()!=null ){
				String subjectName=transaction1.getSubjectName(bo.getSubjectId());
				if(map.containsKey(bo.getSubjectId())){
					ExamEvaluatorTypeMarksTo evaluatorTypeMarksTo = map.get(bo.getSubjectId());
					if(bo.getEvaluatorTypeId()!=null){
						if(bo.getEvaluatorTypeId().equals(1)){
							evaluatorTypeMarksTo.setEvaluatorMarks1(String.valueOf(bo.getVmarks()));
							evaluatorTypeMarksTo.setOldEvaluatorMarks1(String.valueOf(bo.getVmarks()));
							evaluatorTypeMarksTo.setId1(bo.getId());
							evaluatorTypeMarksTo.setAnswerScriptTypeId(bo.getAnswerScriptTypeId());
							evaluatorTypeMarksTo.setPacketNo(bo.getPacketNo());
							evaluatorTypeMarksTo.setUserId(bo.getUserId());
						}else if(bo.getEvaluatorTypeId().equals(2)){
							evaluatorTypeMarksTo.setEvaluatorMarks2(String.valueOf(bo.getVmarks()));
							evaluatorTypeMarksTo.setOldEvaluatorMarks2(String.valueOf(bo.getVmarks()));
							evaluatorTypeMarksTo.setId2(bo.getId());
							evaluatorTypeMarksTo.setAnswerScriptTypeId(bo.getAnswerScriptTypeId());
							evaluatorTypeMarksTo.setPacketNo(bo.getPacketNo());
							evaluatorTypeMarksTo.setUserId(bo.getUserId());
						}
					}else{
						if( evaluatorTypeMarksTo.getEvaluatorMarks2()==null || evaluatorTypeMarksTo.getEvaluatorMarks2().isEmpty()){
								evaluatorTypeMarksTo.setEvaluatorMarks2("");
							}
							if(evaluatorTypeMarksTo.getEvaluatorMarks1()==null || evaluatorTypeMarksTo.getEvaluatorMarks1().isEmpty()){
								evaluatorTypeMarksTo.setEvaluatorMarks1("");
								}
								evaluatorTypeMarksTo.setMarks(bo.getVmarks());
								evaluatorTypeMarksTo.setOldMarks(bo.getVmarks());
								evaluatorTypeMarksTo.setId3(bo.getId());
								evaluatorTypeMarksTo.setAnswerScriptTypeId(bo.getAnswerScriptTypeId());
								evaluatorTypeMarksTo.setPacketNo(bo.getPacketNo());
								evaluatorTypeMarksTo.setUserId(bo.getUserId());
					}
					map.put(bo.getSubjectId(), evaluatorTypeMarksTo);
				}else{
					ExamEvaluatorTypeMarksTo correctionTo = new ExamEvaluatorTypeMarksTo();
					if(bo.getEvaluatorTypeId()!=null){
						if(bo.getEvaluatorTypeId().equals(1)){
							correctionTo.setEvaluatorMarks1(String.valueOf(bo.getVmarks()));
							correctionTo.setOldEvaluatorMarks1(String.valueOf(bo.getVmarks()));
							correctionTo.setId1(bo.getId());
							correctionTo.setAnswerScriptTypeId(bo.getAnswerScriptTypeId());
							correctionTo.setPacketNo(bo.getPacketNo());
							correctionTo.setUserId(bo.getUserId());
						}else if(bo.getEvaluatorTypeId().equals(2)){
							correctionTo.setEvaluatorMarks2(String.valueOf(bo.getVmarks()));
							correctionTo.setOldEvaluatorMarks2(String.valueOf(bo.getVmarks()));
							correctionTo.setId2(bo.getId());
							correctionTo.setAnswerScriptTypeId(bo.getAnswerScriptTypeId());
							correctionTo.setPacketNo(bo.getPacketNo());
							correctionTo.setUserId(bo.getUserId());
							}
					}else{
						correctionTo.setEvaluatorMarks1("");
						correctionTo.setEvaluatorMarks2("");
						correctionTo.setMarks(bo.getVmarks());
						correctionTo.setOldMarks(bo.getVmarks());
						correctionTo.setId3(bo.getId());
						correctionTo.setAnswerScriptTypeId(bo.getAnswerScriptTypeId());
						correctionTo.setPacketNo(bo.getPacketNo());
						correctionTo.setUserId(bo.getUserId());
					}
					correctionTo.setSubjectId(bo.getSubjectId());
					correctionTo.setSubjectName(subjectName);
					map.put(bo.getSubjectId(), correctionTo);
					}
				 }
			   }
			}
		
		return map;
	}
	
	/**
	 * @param map
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<ExamMarksVerificationEntryBO>  convertTotoBo(List<ExamMarksVerificationCorrectionTo> list,ExamMarksVerificationCorrectionForm form) throws Exception{
		List<ExamMarksVerificationEntryBO> boList=new ArrayList<ExamMarksVerificationEntryBO>();
		Iterator<ExamMarksVerificationCorrectionTo> iterator = list.iterator();
		while (iterator.hasNext()) {
				ExamMarksVerificationCorrectionTo to=(ExamMarksVerificationCorrectionTo)iterator.next();;
			if(to.getEvaluatorMarks1()!=null && to.getEvaluatorMarks1()!=0){
				ExamMarksVerificationEntryBO bo=new ExamMarksVerificationEntryBO();
				bo.setExamId(Integer.valueOf(form.getExamId()));
				bo.setStudentId(form.getStudentId());
				bo.setSubjectId(to.getSaveSubjectId());
				bo.setVmarks((to.getEvaluatorMarks1()).toString());
				bo.setModifiedBy(form.getUserId());
				bo.setLastModifiedDate(new Date());
				bo.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
				bo.setPacketNo(to.getPacketNo());
				bo.setUserId(to.getUserId());
				bo.setEvaluatorTypeId(1);
				bo.setId(to.getId());
				boList.add(bo);
				}
			if(to.getEvaluatorMarks2()!=null && to.getEvaluatorMarks2()!=0){
				ExamMarksVerificationEntryBO bo=new ExamMarksVerificationEntryBO();
				bo.setExamId(Integer.valueOf(form.getExamId()));
				bo.setStudentId(form.getStudentId());
				bo.setSubjectId(to.getSaveSubjectId());
				bo.setVmarks((to.getEvaluatorMarks2()).toString());
				bo.setModifiedBy(form.getUserId());
				bo.setLastModifiedDate(new Date());
				bo.setId(to.getId());
				bo.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
				bo.setPacketNo(to.getPacketNo());
				bo.setUserId(to.getUserId());
				bo.setEvaluatorTypeId(2);
				boList.add(bo);
				}
			if(to.getMarks()!=null && to.getMarks()!=0){
				ExamMarksVerificationEntryBO bo=new ExamMarksVerificationEntryBO();
				bo.setExamId(Integer.valueOf(form.getExamId()));
				bo.setStudentId(form.getStudentId());
				bo.setSubjectId(to.getSaveSubjectId());
				bo.setVmarks((to.getMarks()).toString());
				bo.setModifiedBy(form.getUserId());
				bo.setLastModifiedDate(new Date());
				bo.setId(to.getId());
				bo.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
				bo.setPacketNo(to.getPacketNo());
				bo.setUserId(to.getUserId());
				boList.add(bo);
				
			}
			}
	return boList;
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ExamEvaluatorTypeMarksTo> convertMaptoList(Map<Integer,ExamEvaluatorTypeMarksTo> map) throws Exception{
		 List<ExamEvaluatorTypeMarksTo> verificationList=new ArrayList<ExamEvaluatorTypeMarksTo>();
		Iterator<Entry<Integer, ExamEvaluatorTypeMarksTo>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, ExamEvaluatorTypeMarksTo> entry = (Map.Entry<Integer, ExamEvaluatorTypeMarksTo>) iterator.next();
			ExamEvaluatorTypeMarksTo to=entry.getValue();
			verificationList.add(to);
			}
		return verificationList;
		}
}
	

