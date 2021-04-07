package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CasteForm;
import com.kp.cms.helpers.admin.CasteHelper;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.transactions.admin.ICasteTransaction;
import com.kp.cms.transactionsimpl.admin.CasteTransactionImpl;

/**
 * Manages the operations of add, edit, delete of Caste.
 * @author
 * 
 */
public class CasteHandler {
	private static volatile CasteHandler casteHandler = null;
    private CasteHandler(){
    	
    }
	public static CasteHandler getInstance() {
		if (casteHandler == null) {
			casteHandler = new CasteHandler();
		}
		return casteHandler;
	}

	/**
	 * Add Caste to the database.
	 * @param casteName - Caste Name to be added.
	 * @return - true for successful addition, false otherwise.
	 */
	public boolean addCaste(CasteForm casteForm,HttpServletRequest request) throws Exception{
		ICasteTransaction casteImpl = new CasteTransactionImpl();
		Caste caste1=CasteHelper.convertTOtoBO(casteForm,"Add");
		Caste caste=CasteHelper.convertTOtoBO(casteForm,"Add");
		caste = casteImpl.isCastDuplcated(caste);
		boolean isCasteAdded = false;
		if (caste != null && caste.getIsActive()) {
			throw new DuplicateException();
		} else if (caste != null && !caste.getIsActive()) {
			request.getSession().setAttribute("Caste",caste);
			casteForm.setReactivateid(caste.getId());
			throw new ReActivateException();
		}else if(casteImpl != null) {
			isCasteAdded = casteImpl.addCaste(caste1);
		}
		return isCasteAdded;
	}

	/**
	 * Updates the Caste.
	 * @param casteId - Caste Id to be edited.
	 * @param casteName - Caste Name to be edited.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean updateCaste(CasteForm casteForm,HttpServletRequest request) throws Exception{
		ICasteTransaction casteImpl = new CasteTransactionImpl();
		boolean isCasteEdited = false;
		Caste caste1=CasteHelper.convertTOtoBO(casteForm,"Update");
		Caste caste=CasteHelper.convertTOtoBO(casteForm,"Update");
		caste = casteImpl.isCastDuplcated(caste);
		if(!casteForm.getCasteName().equals(casteForm.getOrigCasteName().trim()) || !casteForm.getReligionId().equals(casteForm.getOrgreligionId())){
		if (caste != null && caste.getIsActive()) {
			throw new DuplicateException();
		}
		}
		if (caste != null && !caste.getIsActive()) {
			request.getSession().setAttribute("Caste",caste);
			casteForm.setReactivateid(caste.getId());
			throw new ReActivateException();
		}else if (casteImpl != null) {
			isCasteEdited = casteImpl.updateCaste(caste1);
		}
		return isCasteEdited;
	}

	/**
	 * Deletes the Caste from the database.
	 * @param casteId - Caste Id to be deleted.
	 * @return - true, if the merit set is deleted successfully, false otherwise.
	 */
	public boolean deleteCaste(int casteId,String userId) throws Exception{
		ICasteTransaction casteImpl = new CasteTransactionImpl();
		boolean isCasteDeleted = false;
		if (casteImpl != null) {
			isCasteDeleted = casteImpl.deleteCaste(casteId,userId);
		}
		return isCasteDeleted;
	}

	/**
	 * Get all Caste list from the database.
	 * @return List - Caste transaction List object
	 */
	public List<CasteTO> getCastes(){
		ICasteTransaction transaction = new CasteTransactionImpl();
		List<Caste> casteBOList = transaction.getCastes();
		List<CasteTO> casteList = CasteHelper.convertBOsToTos(casteBOList);
		return casteList;
	}

	public boolean reActivateCaste(Caste caste,String userId) throws Exception{
		ICasteTransaction transaction = new CasteTransactionImpl();
		boolean isCasteReActivate=transaction.reActivateCaste(caste,userId);
		return isCasteReActivate;
	}
}
