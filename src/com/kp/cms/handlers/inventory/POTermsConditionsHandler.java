package com.kp.cms.handlers.inventory;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvPOTermsAndConditions;
import com.kp.cms.forms.inventory.POTermsConditionsForm;
import com.kp.cms.transactions.inventory.IPOTermsConditionsTransaction;
import com.kp.cms.transactionsimpl.inventory.POTermsConditionsTransactionimpl;

public class POTermsConditionsHandler {

	/**
	 * Singleton object of POTermsConditionsHandler
	 */
	private static volatile POTermsConditionsHandler poTermsConditionsHandler = null;
	private static final Log log = LogFactory.getLog(POTermsConditionsHandler.class);
	private POTermsConditionsHandler() {
		
	}
	/**
	 * return singleton object of POTermsConditionsHandler.
	 * @return
	 */
	public static POTermsConditionsHandler getInstance() {
		if (poTermsConditionsHandler == null) {
			poTermsConditionsHandler = new POTermsConditionsHandler();
		}
		return poTermsConditionsHandler;
	}
	/**
	 * creating the object of TransactionImpl class
	 */
	IPOTermsConditionsTransaction txn=POTermsConditionsTransactionimpl.getInstance();
	/**
	 * sets if T&C available to form
	 * @param poTermsConditionsForm
	 */
	public void setTCtoForm(POTermsConditionsForm poTermsConditionsForm) {
		try {
			InvPOTermsAndConditions bo=txn.getTCBo();
			if(bo!=null){
				poTermsConditionsForm.setTcDescExists(true);
				if(bo.getIsActive()){
					poTermsConditionsForm.setId(String.valueOf(bo.getId()));
					poTermsConditionsForm.setSavedTcDescription(bo.getTcDescription());
				}
				else {
					poTermsConditionsForm.setId(String.valueOf(bo.getId()));
					poTermsConditionsForm.setSavedTcDescription(null);
					poTermsConditionsForm.setTcDescInActive(true);
				}
			}
			else{
				poTermsConditionsForm.setId(null);
				poTermsConditionsForm.setSavedTcDescription(null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * adds terms and conditions into the database
	 * @param poTermsConditionsForm
	 * @return
	 * @throws Exception
	 */
	public boolean addTermsConditions(POTermsConditionsForm poTermsConditionsForm,boolean editMode) throws Exception {
		boolean added=false;
		//replacing the enter key with <br> (new line) to set in database and display the same in print PO jsp
		if (poTermsConditionsForm.getTcDescription() != null
				&& !StringUtils.isEmpty(poTermsConditionsForm.getTcDescription())){
			String[] args=poTermsConditionsForm.getTcDescription().split("\r\n");
			StringBuilder strTc=new StringBuilder();
			for (String string : args) {
				strTc.append(string);
				strTc.append("<br>");
			}
			if(strTc!=null && !strTc.toString().isEmpty())
			poTermsConditionsForm.setTcDescription(strTc.toString());
		}
		InvPOTermsAndConditions bo=new InvPOTermsAndConditions();
		if(editMode){
			if(poTermsConditionsForm.getId()!=null && !poTermsConditionsForm.getId().isEmpty())
			bo.setId(Integer.parseInt(poTermsConditionsForm.getId()));	
		}
		bo.setTcDescription(poTermsConditionsForm.getTcDescription());
		if(!editMode){
			bo.setCreatedBy(poTermsConditionsForm.getUserId());
			bo.setCreatedDate(new Date());
		}
		bo.setLastModifiedDate(new Date());
		bo.setModifiedBy(poTermsConditionsForm.getUserId());
		bo.setIsActive(true);
		added=txn.addTermsAndConditions(bo,poTermsConditionsForm);
		return added;
	}
	/**
	 * deletes the selected Terms and Conditions
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTermsConditions(int id,boolean activate,String userId) throws Exception {
		return txn.deleteTermsConditions(id,activate,userId);

	}
}
