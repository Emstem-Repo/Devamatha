package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.SMSTemplateForm;
import com.kp.cms.helpers.admin.SMSTemplateHelper;
import com.kp.cms.to.admission.SMSTemplateTo;
import com.kp.cms.transactions.admin.ISMSTemplateTransaction;
import com.kp.cms.transactionsimpl.admin.SMSTemplateTransactionImpl;

public class SMSTemplateHandler {

	private static final Log log = LogFactory.getLog(SMSTemplateHandler.class);
	private static volatile SMSTemplateHandler tmplateHandler= null;
	public static SMSTemplateHandler getInstance() {
	      if(tmplateHandler == null) {
	    	  tmplateHandler = new SMSTemplateHandler();
	    	  return tmplateHandler;
	      }
	      return tmplateHandler;
	}
	
	/**
	 * 
	 * @param templateForm
	 * @return save the template to database.
	 * @throws Exception
	 */
	public boolean saveSMSTemplate(SMSTemplateForm templateForm) throws Exception {
		SMSTemplate groupTemplate = SMSTemplateHelper.getInstance().getSMSTemplateObject(templateForm,CMSConstants.ADD);
		ISMSTemplateTransaction ISMSTemplateTransaction = SMSTemplateTransactionImpl.getInstance();
		ISMSTemplateTransaction.saveSMSTemplate(groupTemplate);
		log.debug("Leaving createTemplate ");
		return true;
	}
	
	/** 
	 * @param templateForm
	 * @return this will get invoked when actually update of template required.
	 * @throws Exception
	 */
	public boolean updateSMSTemplate(SMSTemplateForm templateForm) throws Exception {
		SMSTemplate groupTemplate = SMSTemplateHelper.getInstance().getSMSTemplateObject(templateForm,CMSConstants.UPDATE);
		ISMSTemplateTransaction ISMSTemplateTransaction = SMSTemplateTransactionImpl.getInstance();
		groupTemplate.setId(templateForm.getTemplateId());
		ISMSTemplateTransaction.saveSMSTemplate(groupTemplate);
		
		log.debug("Leaving createTemplate ");
		return true;
	}
	
	/** 
	 * @param templateForm
	 * @return this will get invoked when actually update of template required.
	 * @throws Exception
	 */
	public boolean deleteSMSTemplate(int id) throws Exception {
		SMSTemplate groupTemplate = new SMSTemplate();
		ISMSTemplateTransaction iTemplatePassword = SMSTemplateTransactionImpl.getInstance();
		groupTemplate.setId(id);
		iTemplatePassword.deleteSMSTemplate(groupTemplate);
		
		log.debug("Leaving createTemplate ");
		return true;
	}
	
	/**
	 * 
	 * @param templateForm
	 * @throws Exception
	 * This will returns the list of templates.
	 */
	public List<SMSTemplateTo> getSMSTemplateList(int id) throws Exception {
		ISMSTemplateTransaction ISMSTemplateTransaction = SMSTemplateTransactionImpl.getInstance();
		List<SMSTemplate> list= ISMSTemplateTransaction.getSMSTemplates(id);
		log.debug("Leaving getTemplateList ");
		
		return SMSTemplateHelper.getInstance().convertBOtoTO(list);
	}
	
	public List<SMSTemplate> getDuplicateCheckList(int courseId, String templateName) throws Exception {
		ISMSTemplateTransaction ISMSTemplateTransaction = SMSTemplateTransactionImpl.getInstance();
		log.debug("Leaving getTemplateList ");
		
		return ISMSTemplateTransaction.getDuplicateCheckList(courseId, templateName);
	}
	public List<SMSTemplate> checkDuplicate(int courseId, String templateName, int programId) throws Exception {
		ISMSTemplateTransaction ISMSTemplateTransaction = SMSTemplateTransactionImpl.getInstance();
		log.debug("Leaving getTemplateList ");
		
		return ISMSTemplateTransaction.checkDuplicate(courseId, templateName, programId);
	}	
	public List<SMSTemplate> getDuplicateCheckList(String templateName) throws Exception {
		ISMSTemplateTransaction iSMSTemplateTransaction = SMSTemplateTransactionImpl.getInstance();
		log.debug("Leaving getTemplateList ");
		
		return iSMSTemplateTransaction.getDuplicateCheckList(templateName);
	}
}
