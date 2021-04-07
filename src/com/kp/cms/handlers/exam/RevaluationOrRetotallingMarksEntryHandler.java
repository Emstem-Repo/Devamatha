package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.ChangeSubjectForm;
import com.kp.cms.forms.exam.ExamMarksVerificationCorrectionForm;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.helpers.exam.RevaluationOrRetotallingMarksEntryHelper;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.exam.RevaluationOrRetotallingMarksEntryTo;
import com.kp.cms.transactions.attandance.IChangeSubject;
import com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.IRevaluationOrRetotallingMarksEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.ChangeSubjectTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamMarksVerificationCorrectionTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.RevaluationOrRetotallingMarksEntryTransactionImpl;

public class RevaluationOrRetotallingMarksEntryHandler {
	private static volatile RevaluationOrRetotallingMarksEntryHandler revaluationOrRetotallingMarksEntryHandler = null;
	private static final Log log = LogFactory.getLog(RevaluationOrRetotallingMarksEntryHandler.class);
	
	/**
	 * @return
	 */
	public static RevaluationOrRetotallingMarksEntryHandler getInstance() {
		if (revaluationOrRetotallingMarksEntryHandler == null) {
			revaluationOrRetotallingMarksEntryHandler = new RevaluationOrRetotallingMarksEntryHandler();
		}
		return revaluationOrRetotallingMarksEntryHandler;
	}
	/**
	 * @param Form
	 * @return
	 * @throws Exception
	 */
	public boolean checkValidStudentRegNo(RevaluationOrRetotallingMarksEntryForm Form) throws Exception{
		IExamMarksVerificationCorrectionTransaction transaction1=ExamMarksVerificationCorrectionTransactionImpl.getInstance();
		Integer studentId=transaction1.getStudentId(Form.getRegisterNo());
		boolean isValidStudent=false;
		if(studentId!=null && studentId>0){
			isValidStudent=true;
			Form.setStudentId(studentId);
		}
		return isValidStudent;
	}
	
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<RevaluationOrRetotallingMarksEntryTo> getStudentDetails(RevaluationOrRetotallingMarksEntryForm form) throws Exception{
		IRevaluationOrRetotallingMarksEntryTransaction transaction=RevaluationOrRetotallingMarksEntryTransactionImpl.getInstance();
		List<ExamRevaluationApplication> boList=transaction.getStudentDetailsList(form);
		List<RevaluationOrRetotallingMarksEntryTo> toList=RevaluationOrRetotallingMarksEntryHelper.getInstance().convertBotoTo(boList,form);
		return toList;
	}
	
	public boolean saveSelectedSubjectData(RevaluationOrRetotallingMarksEntryForm form ) throws Exception{
		boolean isSaved=false;
		IRevaluationOrRetotallingMarksEntryTransaction transaction=RevaluationOrRetotallingMarksEntryTransactionImpl.getInstance();
			List<RevaluationOrRetotallingMarksEntryTo> toList=new ArrayList<RevaluationOrRetotallingMarksEntryTo>();
			List<RevaluationOrRetotallingMarksEntryTo> toList1=new ArrayList<RevaluationOrRetotallingMarksEntryTo>();
		String date1="";
		int flag =0;
	    Iterator itr=form.getStudentDetailsList().iterator();
		  while(itr.hasNext()){
			  RevaluationOrRetotallingMarksEntryTo to=(RevaluationOrRetotallingMarksEntryTo)itr.next();
				if(to.getChecked1()!=null && to.getChecked1().equalsIgnoreCase("on") ){
					to.setChecked1(null);
					
					//new
					if(to.isEvaluatorType3()==true){
						if((to.getMarks1()!=null && !to.getMarks1().isEmpty()) && (to.getMarks2()!=null && !to.getMarks2().isEmpty())){
							if(to.getOldMarks1()==null || to.getOldMarks1().isEmpty()){
									if(to.getMarks1()!=null && !to.getMarks1().isEmpty()){
										toList.add(to);
									}
							}else{
									if(!to.getOldMarks1().equalsIgnoreCase(to.getMarks1())){
												toList.add(to);
									}
								}
							if(to.getOldMarks2()==null || to.getOldMarks2().isEmpty()){
									if(to.getMarks2()!=null && !to.getMarks2().isEmpty()){
										toList.add(to);
									}
							}else{
									if(!to.getOldMarks2().equalsIgnoreCase(to.getMarks2())){
												toList.add(to);
									}
								 }
							if(to.getOldMarks3()==null || to.getOldMarks3().isEmpty()){
								if(to.getMarks3()!=null && !to.getMarks3().isEmpty()){
									toList.add(to);
								}
							}else{
								if(!to.getOldMarks3().equalsIgnoreCase(to.getMarks3())){
											toList.add(to);
								}
							 }
							}else{
								
								 flag =1;
							}
						}
					
					
					else if(to.isEvaluatorType2()==true){
						if((to.getMarks1()!=null && !to.getMarks1().isEmpty()) && (to.getMarks2()!=null && !to.getMarks2().isEmpty())){
							if(to.getOldMarks1()==null || to.getOldMarks1().isEmpty()){
									if(to.getMarks1()!=null && !to.getMarks1().isEmpty()){
										toList.add(to);
									}
							}else{
									if(!to.getOldMarks1().equalsIgnoreCase(to.getMarks1())){
												toList.add(to);
									}
								}
							if(to.getOldMarks2()==null || to.getOldMarks2().isEmpty()){
									if(to.getMarks2()!=null && !to.getMarks2().isEmpty()){
										toList.add(to);
									}
							}else{
									if(!to.getOldMarks2().equalsIgnoreCase(to.getMarks2())){
												toList.add(to);
									}
								 }
							
							}else{
								
								 flag =1;
							}
						}else if(to.isEvaluatorType1()==true){
							if(to.getOldMarks1()==null || to.getOldMarks1().isEmpty()){
									if(to.getMarks1()!=null && !to.getMarks1().isEmpty()){
										toList.add(to);
									}
							}else{
									if(!to.getOldMarks1().equalsIgnoreCase(to.getMarks1())){
												toList.add(to);
									}
							}
						}
						//eva over
					//normal marks start
						else{
							
							if(to.getOldMarks()==null || to.getOldMarks().isEmpty()){
								if(to.getMarks()!=null && !to.getMarks().isEmpty()){
									toList.add(to);
								}
						}else{
								if(!to.getOldMarks().equalsIgnoreCase(to.getMarks())){
											toList.add(to);
								}
						}
							
						}
				}
				toList1.add(to);
		  }
		  form.setStudentDetailsList(toList1);
		  if(flag==1){
			  throw new BusinessException();
		  }else{
		  isSaved=transaction.saveRevaluationOrRetotalingStudentRecords(toList,form);
		  }
		return isSaved;
	}
	public Double getMaxMarkOfSubject(RevaluationOrRetotallingMarksEntryForm form ,int id) throws Exception {
		IRevaluationOrRetotallingMarksEntryTransaction transaction=RevaluationOrRetotallingMarksEntryTransactionImpl.getInstance();
			return transaction.getMaxMarkOfSubject(form,id);
	}
}
