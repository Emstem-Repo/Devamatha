package com.kp.cms.handlers.admission;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.ExamCenterForm;
import com.kp.cms.helpers.admission.ExamCenterHelper;
import com.kp.cms.to.admission.ExamCenterTO;
import com.kp.cms.transactions.admission.IExamCenterTransactions;
import com.kp.cms.transactionsimpl.admission.ExamCenterTransactionImpl;

	public class ExamCenterHandler {
		private static final Logger log = Logger.getLogger(ExamCenterHandler.class);	
		public static volatile ExamCenterHandler examCenterHandler = null;
		IExamCenterTransactions iTransactions=new ExamCenterTransactionImpl();
		
		public static ExamCenterHandler getInstance(){
			if(examCenterHandler == null){
				examCenterHandler = new ExamCenterHandler();
				return examCenterHandler;
			}
			return examCenterHandler;
		}
		
		/**
		 * @return
		 * @throws Exception
		 */
		public List<ExamCenterTO> getExamCenterDetails() throws Exception{
			
			List<ExamCenter> examCenterBO=iTransactions.getExamCenterDetails();
			List<ExamCenterTO> examCenterTOs=ExamCenterHelper.getInstance().convertBOsToTOs(examCenterBO);
			return examCenterTOs;
			
		}
		
		
		
		public boolean getExamCenterDefinedInProgram(int pgmId) throws Exception
		{
			
			boolean isExamCentreRequired=iTransactions.getExamCenterDefineInProgram(pgmId);
			return isExamCentreRequired;
			
		}

		/**
		 * @param examCenterForm
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		public boolean addExamCenter(ExamCenterForm examCenterForm,String mode)throws Exception {
			Boolean originalCenterNotChanged = false;

			String center = "";
			String origCenter = "";
			int origProgId = 0;
			int progId = 0;
	
			
			if(examCenterForm.getOrigProgId()!= null && !examCenterForm.getOrigProgId().isEmpty())
			{
				origProgId = Integer.parseInt(examCenterForm.getOrigProgId());
			}
			

			if(examCenterForm.getProgramId()!= null && !examCenterForm.getProgramId().isEmpty())
			{
				progId = Integer.parseInt(examCenterForm.getProgramId());
			}
			
			if(examCenterForm.getCenter()!= null && !examCenterForm.getCenter().isEmpty())
			{
				center = examCenterForm.getCenter().trim();
			}
			
			if(examCenterForm.getOrigCenter()!= null && !examCenterForm.getOrigCenter().equals("")){
				origCenter = examCenterForm.getOrigCenter().trim();
			}
			
			if ((center.equalsIgnoreCase(origCenter)) && (progId == origProgId)) {
				originalCenterNotChanged = true;
			}
			if (mode.equalsIgnoreCase("Add")) {
				originalCenterNotChanged = false;
			}
			if(!originalCenterNotChanged){
				ExamCenter dupExamCenter=iTransactions.checkDuplicate(examCenterForm);
				if(dupExamCenter!=null){
					if(dupExamCenter.getIsActive()){
						throw  new DuplicateException();
					}else
						if(dupExamCenter.getId()>0){
							examCenterForm.setDupId(dupExamCenter.getId());
						}
						throw new ReActivateException();
				}
			}
			ExamCenter examCenterBO=ExamCenterHelper.getInstance().populateFormToBO(examCenterForm);
			if (mode.equals("Add")) {
				examCenterBO.setCreatedDate(new Date());
				examCenterBO.setLastModifiedDate(new Date());
				examCenterBO.setCreatedBy(examCenterForm.getUserId());
				examCenterBO.setModifiedBy(examCenterForm.getUserId());
			} else // edit
			{
				examCenterBO.setLastModifiedDate(new Date());
				examCenterBO.setModifiedBy(examCenterForm.getUserId());
				examCenterBO.setCreatedBy(examCenterForm.getOrigCreatedBy());
				examCenterBO.setCreatedDate(examCenterForm.getOrigCreatedDate());
			}
			
			return iTransactions.addExamCenter(examCenterBO,mode);
		}

		/**
		 * @param examCenterForm
		 * @param id
		 * @throws Exception
		 */
		public void getExamCenterDetailsToEdit(ExamCenterForm examCenterForm,int id) throws Exception {
			ExamCenter examCenterBO=iTransactions.getExamCenterDetailsToEdit(id);
			if(examCenterBO!=null)
				ExamCenterHelper.getInstance().setBOsToTOs(examCenterForm,examCenterBO);
		}

		/**
		 * @param centerId
		 * @param activate
		 * @param examCenterForm
		 * @return
		 * @throws Exception
		 */
		public boolean deleteExamCenter(int centerId, boolean activate,ExamCenterForm examCenterForm) throws Exception {
			log.info("Start of deleteCourse of CourseHandler");
			boolean result = iTransactions.deleteExamCenter(centerId, activate, examCenterForm);
			log.info("End of deleteCourse of CourseHandler");
			return result;
		}
}
