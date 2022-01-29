package com.kp.cms.handlers.phd;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.phd.PhdQualificationLevelBo;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.QualificationLevelForm;
import com.kp.cms.forms.phd.PhdQualificationLevelForm;
import com.kp.cms.helpers.employee.QualificationLevelHelper;
import com.kp.cms.helpers.phd.PhdQualificationLevelHelper;
import com.kp.cms.to.employee.QualificationLevelTO;
import com.kp.cms.to.phd.PhdQualificationLevelTo;
import com.kp.cms.transactions.employee.IQualificationLevelTransaction;
import com.kp.cms.transactions.phd.IPhdQualificationLevel;
import com.kp.cms.transactionsimpl.employee.QualificationLevelTransactionImpl;
import com.kp.cms.transactionsimpl.phd.PhdQualificationLevelImpl;

public class PhdQualificationLevelHandler {
	private static final Log log = LogFactory.getLog(PhdQualificationLevelHandler.class);
	IPhdQualificationLevel iTransction = PhdQualificationLevelImpl.getInstance();
	public static volatile PhdQualificationLevelHandler phdQualificationLevelHandler=null;
	
	public static PhdQualificationLevelHandler getInstance(){
		if(phdQualificationLevelHandler == null){
			phdQualificationLevelHandler = new PhdQualificationLevelHandler();
			return phdQualificationLevelHandler;
		}
		return phdQualificationLevelHandler;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<PhdQualificationLevelTo> getQualificationList() throws Exception{
		List<PhdQualificationLevelBo> levelBOs=iTransction.getQualificationLevel();
		List<PhdQualificationLevelTo> qualificationLevelTOs=PhdQualificationLevelHelper.getInstance().copyQualificationLevelBOsToTOs(levelBOs);;
		return qualificationLevelTOs;
	}
	/**
	 * @param qualificationLevelForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addQualificationLevel(PhdQualificationLevelForm phdQualificationLevelForm, String mode) throws Exception{
		boolean isAdded=false;
		PhdQualificationLevelBo phdQualificationLevelBo=null;
		PhdQualificationLevelBo levelBO=null;
		PhdQualificationLevelBo reactivated=null;
		if(mode.equalsIgnoreCase("Add")){
			phdQualificationLevelBo=iTransction.isDuplicated(phdQualificationLevelForm);
			 levelBO=iTransction.isDuplicateDisplayOrder(phdQualificationLevelForm);
			 reactivated=iTransction.isReactivate(phdQualificationLevelForm);
		}
		
		if (phdQualificationLevelBo != null && phdQualificationLevelBo.getIsActive()) {
			throw new DuplicateException();
		}
		
		if(levelBO!=null && levelBO.getIsActive()){
			throw new DuplicateException();
		}
		if(reactivated !=null && reactivated.getIsActive()){
			throw new DuplicateException();
		}
		else if(reactivated !=null && !reactivated.getIsActive()){
			phdQualificationLevelForm.setDuplId(reactivated.getId());
			throw new ReActivateException();
		}
		phdQualificationLevelBo=PhdQualificationLevelHelper.getInstance().copyDataFromFormToBO(phdQualificationLevelForm, mode);
		isAdded=iTransction.addQualificationLevel(phdQualificationLevelBo, mode);
		return isAdded;
	}
	/**
	 * @param id
	 * @param activate
	 * @param qualificationLevelForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteQualificationLevel(int id, boolean activate,PhdQualificationLevelForm phdQualificationLevelForm) throws Exception {
		boolean isDeleted=false;
		isDeleted=iTransction.deleteQualificationLevel(id,activate,phdQualificationLevelForm);
		return isDeleted;
	}
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PhdQualificationLevelTo editQualificationLevel(int id) throws Exception {
		PhdQualificationLevelBo bos=iTransction.editQualificationLevel(id);
		PhdQualificationLevelTo tos=PhdQualificationLevelHelper.getInstance().copyQualificationLevelBOsToTOs1(bos);
		return tos;
	}
	/**
	 * @param qualificationLevelForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean updateQualificationLevel(PhdQualificationLevelForm phdQualificationLevelForm, String mode)throws Exception {
		boolean isUpdated=false;
		PhdQualificationLevelBo qualificationLevelBO=null;
		PhdQualificationLevelBo levelBO=null;
		if(mode.equalsIgnoreCase("Edit")){
			 qualificationLevelBO=iTransction.isDuplicated(phdQualificationLevelForm);
			 levelBO=iTransction.isDuplicateDisplayOrder(phdQualificationLevelForm);
		}
		if(qualificationLevelBO!=null){
			if(phdQualificationLevelForm.getId() != qualificationLevelBO.getId()){
				throw new DuplicateException();
			}
		}
		if(levelBO!=null){
			if(phdQualificationLevelForm.getId() != levelBO.getId()){
				throw new DuplicateException();
			}
		}
		PhdQualificationLevelBo bo=PhdQualificationLevelHelper.getInstance().copyDataFromFormToBO(phdQualificationLevelForm, mode);
		isUpdated=iTransction.addQualificationLevel(bo, mode);
		return isUpdated;
	}

}
