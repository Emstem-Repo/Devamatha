package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.GenerateProcessForm;
import com.kp.cms.helpers.admission.GenerateProcessHelper;
import com.kp.cms.to.admission.AdmissionStatusTO;
import com.kp.cms.transactions.admission.IGenerateProcessTransaction;
import com.kp.cms.transactions.exam.IConsolidatedMarksCardTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.admission.GenerateProcessTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ConsolidatedMarksCardTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;

public class GenerateProcessHandler {
	/**
	 * Singleton object of GenerateProcessHandler
	 */
	private static volatile GenerateProcessHandler generateProcessHandler = null;
	private static final Log log = LogFactory.getLog(GenerateProcessHandler.class);
	private GenerateProcessHandler() {
		
	}
	/**
	 * return singleton object of GenerateProcessHandler.
	 * @return
	 */
	public static GenerateProcessHandler getInstance() {
		if (generateProcessHandler == null) {
			generateProcessHandler = new GenerateProcessHandler();
		}
		return generateProcessHandler;
	}
	/**
	 * @param generateProcessForm
	 * @return
	 * @throws Exception
	 */
	public List<AdmissionStatusTO> getCandidates(GenerateProcessForm generateProcessForm,HttpServletRequest request) throws Exception {
		String query=" select admAppln "+GenerateProcessHelper.getInstance().getSearchCriteria(generateProcessForm);
		IGenerateProcessTransaction transaction=GenerateProcessTransactionImpl.getInstance();
		List<AdmAppln> list=transaction.getCandidates(query); 
		int courseId=0;
		int programId=0;
		if(generateProcessForm.getCourseId()!=null && !generateProcessForm.getCourseId().isEmpty()){
			courseId=Integer.parseInt(generateProcessForm.getCourseId());
		}
		if(generateProcessForm.getProgramId()!=null && !generateProcessForm.getProgramId().isEmpty()){
			programId=Integer.parseInt(generateProcessForm.getProgramId());
		}
		return GenerateProcessHelper.getInstance().convertBoToTO(list,courseId,programId,request);
	}
	/**
	 * @param selectedCandidatesList
	 * @return
	 * @throws Exception
	 */
	public boolean saveDataToDatabase(List<AdmissionStatusTO> selectedCandidatesList,String user) throws Exception {
		IGenerateProcessTransaction transaction=GenerateProcessTransactionImpl.getInstance();
		return transaction.saveDataToDatabase(selectedCandidatesList,user);
	}
	
	/**
	 * 
	 * @param Passing application no. and gets the exactly matching records from AdmAppln table 
	 * @return
	 */
	public AdmissionStatusTO getInterviewResult(String applicationNo, int appliedYear,AdmissionStatusTO statusTO,AdmAppln admAppln) throws Exception
	{
		log.info("Entering into getInterviewResult of AdmissionStatusHandler");
//		IAdmissionStatusTransaction admissionStatusTransaction=new AdmissionStatusTransactionImpl();
//		Calendar cal= Calendar.getInstance();
//		AdmAppln admAppln=admissionStatusTransaction.getInterviewResult(applicationNo,appliedYear/*cal.get(Calendar.YEAR)*/);
		
		AdmissionStatusTO admissionStatusTO = GenerateProcessHelper.getInstance().getInterviewStatus(admAppln,statusTO);
		log.info("Leaving from getInterviewResult of AdmissionStatusHandler");
		return admissionStatusTO;
	}
	/**
	 * @param applicationNo
	 * @return
	 * @throws Exception
	 */
	public List<AdmissionStatusTO> getDetailsAdmAppln(String applicationNo) throws Exception{
		log.info("Entering into getDetailsAdmAppln of AdmissionStatusHandler");
		IGenerateProcessTransaction transaction=GenerateProcessTransactionImpl.getInstance();
		List<AdmissionStatusTO> newList=transaction.getDetailsAdmAppln(applicationNo);
		log.info("Leaving into getDetailsAdmAppln of AdmissionStatusHandler");
		return newList;
	}
	
	
	
	// New Code Starts from Here for Reducing time load
	
	/**
	 * @param generateProcessForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<AdmissionStatusTO> getCandidatesForInput(GenerateProcessForm generateProcessForm,HttpServletRequest request) throws Exception {
		List<AdmissionStatusTO> mainList=new ArrayList<AdmissionStatusTO>();
		int courseId=0;
		int programId=0;
		if(generateProcessForm.getCourseId()!=null && !generateProcessForm.getCourseId().isEmpty()){
			courseId=Integer.parseInt(generateProcessForm.getCourseId());
		}
		if(generateProcessForm.getProgramId()!=null && !generateProcessForm.getProgramId().isEmpty()){
			programId=Integer.parseInt(generateProcessForm.getProgramId());
		}
		
		IGenerateProcessTransaction txn=GenerateProcessTransactionImpl.getInstance();
		
		Map<Integer,String> admitCardMap=txn.getTemplateDescriptions(CMSConstants.E_ADMITCARD_TEMPLATE,courseId,programId);
		Map<Integer,String> admissionCardMap=txn.getTemplateDescriptions(CMSConstants.E_ADMISSIONCARD_TEMPLATE,courseId,programId);
		
		
		String query=" select admAppln.id "+GenerateProcessHelper.getInstance().getSearchCriteria(generateProcessForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Integer> studentIds=transaction.getDataForQuery(query); 
		if(studentIds!=null && !studentIds.isEmpty()){
			String query1="from AdmAppln a where a.admStatus is not null and a.admStatus <> '' and a.id in (:ids)";
			List<AdmAppln> list=txn.getAdmAppln(query1,studentIds);
			if(list!=null && !list.isEmpty()){
				List<AdmissionStatusTO> toList=GenerateProcessHelper.getInstance().getAdmissionStatusTosFromBo(list,studentIds,true,"",false,null,request,false);
				if(!toList.isEmpty()){
					mainList.addAll(toList);
				}
			}
			if(!studentIds.isEmpty()){
				String query2="from AdmAppln a where a.isSelected=1 and a.isFinalMeritApproved=1 and a.id in (:ids)";
				List<AdmAppln> list1=txn.getAdmAppln(query2,studentIds);
				if(list1!=null && !list1.isEmpty()){
					List<AdmissionStatusTO> toList=GenerateProcessHelper.getInstance().getAdmissionStatusTosFromBo(list1,studentIds,false,CMSConstants.SELECTED_FOR_ADMISSION,true,admissionCardMap,request,false);
					if(!toList.isEmpty()){
						mainList.addAll(toList);
					}
				}
			}
			if(!studentIds.isEmpty()){
				String query3="select a from AdmAppln a where a.id not in ( select i.admAppln.id from InterviewSelected i ) and a.id in (:ids)";
				List<AdmAppln> list1=txn.getAdmAppln(query3,studentIds);
				if(list1!=null && !list1.isEmpty()){
					List<AdmissionStatusTO> toList=GenerateProcessHelper.getInstance().getAdmissionStatusTosFromBo(list1,studentIds,false,null,false,null,request,true);
					if(!toList.isEmpty()){
						mainList.addAll(toList);
					}
				}
			}
			if(!studentIds.isEmpty()){
				String query3="select ic from InterviewCard ic join ic.interview.interview.interviewProgramCourse ipc where ic.admAppln.id in (:ids) and ipc.isActive=1 and ipc.sequence=(select max(ipc1.sequence) from InterviewCard ic1 join ic1.interview.interview.interviewProgramCourse ipc1 where ic1.admAppln.id=ic.admAppln.id and ipc1.isActive=1 ) order by ic.admAppln.id ";
				List<InterviewCard> list1=txn.getAdmAppln(query3,studentIds);
				if(list1!=null && !list1.isEmpty()){
					List<AdmissionStatusTO> toList=GenerateProcessHelper.getInstance().getInterviewAdmitCard(list1,studentIds,request,admitCardMap);
					if(!toList.isEmpty()){
						mainList.addAll(toList);
					}
				}
			}
		}
//		GenerateProcessHelper.getInstance().convertBoToTO(list,courseId,programId,request);
		return mainList;
	}
	
	
	
	
	
}
