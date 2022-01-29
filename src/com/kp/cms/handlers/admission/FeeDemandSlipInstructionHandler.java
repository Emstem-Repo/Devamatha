package com.kp.cms.handlers.admission;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admission.DemandSlipInstruction;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.FeeDemandSlipInstructionForm;
import com.kp.cms.helpers.admission.FeeDemandSlipInstructionHelper;
import com.kp.cms.to.admission.DemandSlipInstructionTO;
import com.kp.cms.transactions.admission.IFeeDemandSlipInstructionTransaction;
import com.kp.cms.transactionsimpl.admission.FeeDemandSlipInstructionTxnImpl;

	public class FeeDemandSlipInstructionHandler {
		private static final Log log = LogFactory.getLog(FeeDemandSlipInstructionHandler.class);
		private static volatile FeeDemandSlipInstructionHandler feSlipInstructionHandler = null;
		IFeeDemandSlipInstructionTransaction iTransaction = new FeeDemandSlipInstructionTxnImpl();
	
		public static FeeDemandSlipInstructionHandler getInstance() {
			if (feSlipInstructionHandler == null) {
				feSlipInstructionHandler = new FeeDemandSlipInstructionHandler();
			}
			return feSlipInstructionHandler;
		}

		/**
		 * @param feSlipInstructionForm
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		public boolean addSlipInstruction(
				FeeDemandSlipInstructionForm feSlipInstructionForm,String mode) throws Exception {
			int oldCourseId=0;
			int oldSchemeNo=0;
			int courseId = 0;
			int schemeNo = 0;
			if(feSlipInstructionForm.getOldCourseId()!=null && feSlipInstructionForm.getOldSchemeNo()!=null){
				 oldCourseId = Integer.parseInt(feSlipInstructionForm.getOldCourseId());
				 oldSchemeNo = Integer.parseInt(feSlipInstructionForm.getOldSchemeNo());
			}
			if(feSlipInstructionForm.getCourseId()!= null && !feSlipInstructionForm.getCourseId().isEmpty()){
				courseId = Integer.parseInt(feSlipInstructionForm.getCourseId());
			}
			if(feSlipInstructionForm.getSchemeNo()!=null && !feSlipInstructionForm.getSchemeNo().isEmpty()){
				schemeNo = Integer.parseInt(feSlipInstructionForm.getSchemeNo());
			}
			DemandSlipInstruction deSlipInstructionBO = null;
			if(feSlipInstructionForm.getCourseId()== null || feSlipInstructionForm.getCourseId().isEmpty()
				|| feSlipInstructionForm.getSchemeNo()==null || feSlipInstructionForm.getSchemeNo().isEmpty()){
					deSlipInstructionBO =iTransaction.checkDuplicate(courseId,schemeNo);
					if(deSlipInstructionBO!=null && deSlipInstructionBO.getId()!= feSlipInstructionForm.getId()){
						if(deSlipInstructionBO.getIsActive())
							throw new DuplicateException();
						else{
							feSlipInstructionForm.setId(deSlipInstructionBO.getId());
							throw new ReActivateException();
						}
					
					}
				}
			if(feSlipInstructionForm.getCourseId()!= null && !feSlipInstructionForm.getCourseId().isEmpty()
				&& feSlipInstructionForm.getSchemeNo()!=null && !feSlipInstructionForm.getSchemeNo().isEmpty()){
				if(courseId != oldCourseId || schemeNo != oldSchemeNo){
					deSlipInstructionBO =iTransaction.checkDuplicate(courseId,schemeNo);
					if(deSlipInstructionBO!=null){
						if(deSlipInstructionBO.getIsActive())
							throw new DuplicateException();
						else{
							feSlipInstructionForm.setId(deSlipInstructionBO.getId());
							throw new ReActivateException();
						}
					}
				}
			}
			deSlipInstructionBO=FeeDemandSlipInstructionHelper.getInstance().populateFormToBO(feSlipInstructionForm);
			if(mode.equalsIgnoreCase("Add")){
				deSlipInstructionBO.setCreatedBy(feSlipInstructionForm.getUserId());
				deSlipInstructionBO.setCreatedDate(new Date());
				deSlipInstructionBO.setModifiedBy(feSlipInstructionForm.getUserId());
				deSlipInstructionBO.setLastModifiedDate(new Date());
			}else{
				deSlipInstructionBO.setModifiedBy(feSlipInstructionForm.getUserId());
				deSlipInstructionBO.setLastModifiedDate(new Date());
				deSlipInstructionBO.setCreatedBy(feSlipInstructionForm.getOldCreatedBy());
				deSlipInstructionBO.setCreatedDate(feSlipInstructionForm.getOldCreatedDate());
			}
			
				
			return iTransaction.addSlipInstruction(deSlipInstructionBO);
		}

		/**
		 * @param feForm
		 * @param id
		 * @throws Exception
		 */
		public void getDetailsToEdit(FeeDemandSlipInstructionForm feForm,int id) throws Exception{
			DemandSlipInstruction deSlipInstruction = iTransaction.getDetailsToEdit(id);
			FeeDemandSlipInstructionHelper.getInstance().convertBOToForm(feForm,deSlipInstruction);
		}
		
		/**
		 * @return
		 * @throws Exception
		 */
		public List<DemandSlipInstructionTO> getDetailsToDisplay() throws Exception{
			List<DemandSlipInstruction> deList=iTransaction.getDetailsToDisplay();
			List<DemandSlipInstructionTO> deSlipInstructionTOs=FeeDemandSlipInstructionHelper.getInstance().convertTOToBO(deList);
			return deSlipInstructionTOs;
		}

		/**
		 * @param id
		 * @param activate
		 * @param userId
		 * @return
		 * @throws Exception
		 */
		public boolean deleteSlipInstruction(int id,Boolean activate,String userId) throws Exception {
			log.info("inside deleteSlipInstruction of FeeDemandSlipInstructionHandler");
			if(iTransaction != null)
			{
				return iTransaction.deleteSlipInstruction(id,activate,userId);
			}
			log.info("existing deleteSlipInstruction of FeeDemandSlipInstructionHandler");
			return false;
		}
	
	}
