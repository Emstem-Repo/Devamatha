package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamMarksVerificationEntryBO;
import com.kp.cms.forms.exam.ExamMarksVerificationCorrectionForm;
import com.kp.cms.helpers.exam.ExamMarksVerificationCorrectionHelper;
import com.kp.cms.to.exam.ExamEvaluatorTypeMarksTo;
import com.kp.cms.to.exam.ExamMarksVerificationCorrectionTo;
import com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction;
import com.kp.cms.transactionsimpl.exam.ExamMarksVerificationCorrectionTransactionImpl;


public class ExamMarksVerificationCorrectionHandler {
	private static volatile ExamMarksVerificationCorrectionHandler examMarksVerificationCorrectionHandler = null;
	private static final Log log = LogFactory.getLog(ExamMarksVerificationCorrectionHandler.class);
	
	/**
	 * @return
	 */
	public static ExamMarksVerificationCorrectionHandler getInstance() {
		if (examMarksVerificationCorrectionHandler == null) {
			examMarksVerificationCorrectionHandler = new ExamMarksVerificationCorrectionHandler();
		}
		return examMarksVerificationCorrectionHandler;
	}
	
	/**
	 * @param examMarksVerificationCorrectionForm
	 * @return
	 * @throws Exception
	 */
	//get studentId based on student register number
	public boolean checkValidStudentRegNo(ExamMarksVerificationCorrectionForm examMarksVerificationCorrectionForm) throws Exception{
		IExamMarksVerificationCorrectionTransaction transaction1=ExamMarksVerificationCorrectionTransactionImpl.getInstance();
		Integer studentId=transaction1.getStudentId(examMarksVerificationCorrectionForm.getRegisterNo());
		boolean isValidStudent=false;
		if(studentId!=null && studentId>0){
			isValidStudent=true;
			examMarksVerificationCorrectionForm.setStudentId(studentId);
			String className=transaction1.getClassName(studentId);
			String studentName=transaction1.getStudentName(studentId);
			examMarksVerificationCorrectionForm.setClassName(className);
			examMarksVerificationCorrectionForm.setStudentName(studentName);
		}
		return isValidStudent;
	}
	
	/**
	 * @param examMarksVerificationCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public 	List<ExamEvaluatorTypeMarksTo> getStudentMarks(ExamMarksVerificationCorrectionForm form) throws Exception{
		IExamMarksVerificationCorrectionTransaction transaction=ExamMarksVerificationCorrectionTransactionImpl.getInstance();
		String Query="from ExamMarksVerificationEntryBO s where s.examId="+form.getExamId()+ 
				" and s.studentId="+form.getStudentId();
		List<ExamMarksVerificationEntryBO> verificationList=transaction.getDataForQuery(Query);
		Map<Integer,ExamEvaluatorTypeMarksTo> verificationMap=ExamMarksVerificationCorrectionHelper.getInstance().convertBotoMap(verificationList,form);
		// map is not sending  the values from jsp to form thats why again  converting the map to list
	List<ExamEvaluatorTypeMarksTo> verificationlist=ExamMarksVerificationCorrectionHelper.getInstance().convertMaptoList(verificationMap);
		 return verificationlist;
	}
	/**
	 * @param examMarksVerificationCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean compareIsMarksChangedOrNot(ExamMarksVerificationCorrectionForm form) throws Exception{
		boolean isSaved=false;
		IExamMarksVerificationCorrectionTransaction transaction=ExamMarksVerificationCorrectionTransactionImpl.getInstance();
		List<ExamMarksVerificationCorrectionTo> saveMarList=new ArrayList<ExamMarksVerificationCorrectionTo>();
		List<ExamEvaluatorTypeMarksTo> list=form.getVerificationlist();
		Iterator<ExamEvaluatorTypeMarksTo> iterator = list.iterator();
		while (iterator.hasNext()) {
			ExamEvaluatorTypeMarksTo to=(ExamEvaluatorTypeMarksTo)iterator.next();
			if( (to.getEvaluatorMarks1() !=null && !to.getEvaluatorMarks1().isEmpty()) && ( to.getEvaluatorMarks2()!= null && !to.getEvaluatorMarks2().isEmpty())){
				if(!(to.getEvaluatorMarks1().equalsIgnoreCase(to.getOldEvaluatorMarks1()))&& !(to.getEvaluatorMarks2().equalsIgnoreCase(to.getOldEvaluatorMarks2()))){
				
					if(!(to.getEvaluatorMarks1().equalsIgnoreCase(to.getOldEvaluatorMarks1()))){
						ExamMarksVerificationCorrectionTo newMarks=new ExamMarksVerificationCorrectionTo();
						newMarks.setEvaluatorMarks1(Integer.valueOf(to.getEvaluatorMarks1()));
						newMarks.setSaveSubjectId(to.getSubjectId());
						newMarks.setId(to.getId1());
						newMarks.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
						newMarks.setPacketNo(to.getPacketNo());
						newMarks.setUserId(to.getUserId());
						saveMarList.add(newMarks);
					}
					if(!(to.getEvaluatorMarks2().equalsIgnoreCase(to.getOldEvaluatorMarks2()))){
						ExamMarksVerificationCorrectionTo newMarks=new ExamMarksVerificationCorrectionTo();
						newMarks.setEvaluatorMarks2(Integer.valueOf(to.getEvaluatorMarks2()));
						newMarks.setSaveSubjectId(to.getSubjectId());
						newMarks.setId(to.getId2());
						newMarks.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
						newMarks.setPacketNo(to.getPacketNo());
						newMarks.setUserId(to.getUserId());
						saveMarList.add(newMarks);
					}
			    }else if(!(to.getEvaluatorMarks1().equalsIgnoreCase(to.getOldEvaluatorMarks1())))  {
					ExamMarksVerificationCorrectionTo newMarks=new ExamMarksVerificationCorrectionTo();
					newMarks.setEvaluatorMarks1(Integer.valueOf(to.getEvaluatorMarks1()));
					newMarks.setId(to.getId1());
					newMarks.setSaveSubjectId(to.getSubjectId());
					newMarks.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
					newMarks.setPacketNo(to.getPacketNo());
					newMarks.setUserId(to.getUserId());
					saveMarList.add(newMarks);
				}else if(!(to.getEvaluatorMarks2().equalsIgnoreCase(to.getOldEvaluatorMarks2()))){
					ExamMarksVerificationCorrectionTo newMarks=new ExamMarksVerificationCorrectionTo();
					newMarks.setEvaluatorMarks2(Integer.valueOf(to.getEvaluatorMarks2()));
					newMarks.setId(to.getId2());
					newMarks.setSaveSubjectId(to.getSubjectId());
					newMarks.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
					newMarks.setPacketNo(to.getPacketNo());
					newMarks.setUserId(to.getUserId());
					saveMarList.add(newMarks);
				}
			}else { 
				if((to.getEvaluatorMarks1() !=null && to.getEvaluatorMarks1().isEmpty()) && (to.getEvaluatorMarks2()!=null && to.getEvaluatorMarks2().isEmpty())){
							if(to.getMarks() !=null && !to.getMarks().isEmpty()){
								if(!to.getMarks().equalsIgnoreCase(to.getOldMarks())){
									ExamMarksVerificationCorrectionTo newMarks=new ExamMarksVerificationCorrectionTo();
									newMarks.setMarks(Integer.valueOf(to.getMarks()));
									newMarks.setId(to.getId3());
									newMarks.setSaveSubjectId(to.getSubjectId());
									newMarks.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
									newMarks.setPacketNo(to.getPacketNo());
									newMarks.setUserId(to.getUserId());
									saveMarList.add(newMarks);
								}
							}
				
					}else{
			
							if(to.getEvaluatorMarks1() !=null && !to.getEvaluatorMarks1().isEmpty() ){ 
								if(!(to.getEvaluatorMarks1().equalsIgnoreCase(to.getOldEvaluatorMarks1())))  {
									ExamMarksVerificationCorrectionTo newMarks=new ExamMarksVerificationCorrectionTo();
									newMarks.setEvaluatorMarks1(Integer.valueOf(to.getEvaluatorMarks1()));
									newMarks.setId(to.getId1());
									newMarks.setSaveSubjectId(to.getSubjectId());
									newMarks.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
									newMarks.setPacketNo(to.getPacketNo());
									newMarks.setUserId(to.getUserId());
									saveMarList.add(newMarks);
								}
							}else{
								if(to.getMarks() !=null && !to.getMarks().isEmpty()){
									if(!to.getMarks().equalsIgnoreCase(to.getOldMarks())){
					   			ExamMarksVerificationCorrectionTo newMarks=new ExamMarksVerificationCorrectionTo();
								newMarks.setMarks(Integer.valueOf(to.getMarks()));
								newMarks.setId(to.getId3());
								newMarks.setSaveSubjectId(to.getSubjectId());
								newMarks.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
								newMarks.setPacketNo(to.getPacketNo());
								newMarks.setUserId(to.getUserId());
								saveMarList.add(newMarks);
									}
								}
							}
			
			 
							if( to.getEvaluatorMarks2()!=null && !to.getEvaluatorMarks2().isEmpty()){
								if(!(to.getEvaluatorMarks2().equalsIgnoreCase(to.getOldEvaluatorMarks2()))){
									ExamMarksVerificationCorrectionTo newMarks=new ExamMarksVerificationCorrectionTo();
									newMarks.setId(to.getId2());
									newMarks.setEvaluatorMarks2(Integer.valueOf(to.getEvaluatorMarks2()));
									newMarks.setSaveSubjectId(to.getSubjectId());
									newMarks.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
									newMarks.setPacketNo(to.getPacketNo());
									newMarks.setUserId(to.getUserId());
									saveMarList.add(newMarks);
								}
							}else{
								if(to.getMarks() !=null && !to.getMarks().isEmpty()){
									if(!to.getMarks().equalsIgnoreCase(to.getOldMarks())){
										ExamMarksVerificationCorrectionTo newMarks=new ExamMarksVerificationCorrectionTo();
										newMarks.setMarks(Integer.valueOf(to.getMarks()));
										newMarks.setId(to.getId3());
										newMarks.setSaveSubjectId(to.getSubjectId());
										newMarks.setAnswerScriptTypeId(to.getAnswerScriptTypeId());
										newMarks.setPacketNo(to.getPacketNo());
										newMarks.setUserId(to.getUserId());
										saveMarList.add(newMarks);
									}
								}
							}
						}
					}
				}
		List<ExamMarksVerificationEntryBO> saveMarks=ExamMarksVerificationCorrectionHelper.getInstance().convertTotoBo(saveMarList,form);
		isSaved=transaction.saveVerificationMarks(saveMarks);
		return isSaved;
		} 
	

}
