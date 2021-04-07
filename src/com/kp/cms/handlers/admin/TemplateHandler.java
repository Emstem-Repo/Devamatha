package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.TemplateForm;
import com.kp.cms.helpers.admin.TemplateHelper;
import com.kp.cms.to.admin.GroupTemplateTO;
import com.kp.cms.transactions.admin.ITemplatePassword;
import com.kp.cms.transactionsimpl.admin.TemplateImpl;

/**
 * 
 * Handler class for the email template creation.
 *
 */
public class TemplateHandler {

	private static final Log log = LogFactory.getLog(TemplateHandler.class);
	private static volatile TemplateHandler tmplateHandler= null;
	public static TemplateHandler getInstance() {
	      if(tmplateHandler == null) {
	    	  tmplateHandler = new TemplateHandler();
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
	public boolean saveGroupTemplate(TemplateForm templateForm) throws Exception {
		GroupTemplate groupTemplate = TemplateHelper.getInstance().getGroupTemplateObject(templateForm,CMSConstants.ADD);
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		ITemplatePassword.saveGroupTemplate(groupTemplate);
		log.debug("Leaving createTemplate ");
		return true;
	}
	
	/** 
	 * @param templateForm
	 * @return this will get invoked when actually update of template required.
	 * @throws Exception
	 */
	public boolean updateGroupTemplate(TemplateForm templateForm) throws Exception {
		GroupTemplate groupTemplate = TemplateHelper.getInstance().getGroupTemplateObject(templateForm,CMSConstants.UPDATE);
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		groupTemplate.setId(templateForm.getTemplateId());
		ITemplatePassword.saveGroupTemplate(groupTemplate);
		
		log.debug("Leaving createTemplate ");
		return true;
	}
	
	/** 
	 * @param templateForm
	 * @return this will get invoked when actually update of template required.
	 * @throws Exception
	 */
	public boolean deleteGroupTemplate(int id) throws Exception {
		GroupTemplate groupTemplate = new GroupTemplate();
		ITemplatePassword iTemplatePassword = TemplateImpl.getInstance();
		groupTemplate.setId(id);
		iTemplatePassword.deleteGroupTemplate(groupTemplate);
		
		log.debug("Leaving createTemplate ");
		return true;
	}
	
	/**
	 * 
	 * @param templateForm
	 * @throws Exception
	 * This will returns the list of templates.
	 */
	public List<GroupTemplateTO> getGroupTemplateList(int id,String programTypeId,String programId,String templateName) throws Exception {
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		List<GroupTemplate> list= ITemplatePassword.getGroupTemplates(id,programTypeId,programId,templateName);
		log.debug("Leaving getTemplateList ");
		
		return TemplateHelper.getInstance().convertBOtoTO(list);
	}
	
	public List<GroupTemplate> getDuplicateCheckList(int courseId, String templateName) throws Exception {
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		log.debug("Leaving getTemplateList ");
		
		return ITemplatePassword.getDuplicateCheckList(courseId, templateName);
	}
	public List<GroupTemplate> checkDuplicate(int courseId, String templateName, int programId) throws Exception {
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		log.debug("Leaving getTemplateList ");
		
		return ITemplatePassword.checkDuplicate(courseId, templateName, programId);
	}
//	public List<CertificateDetailsTemplate> checkDuplicateCertificateTemplate(int certificateId, String certName) throws Exception {
//		log.debug("Entering getTemplateList ");
//		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
//		log.debug("Leaving getTemplateList ");
//		
//		return ITemplatePassword.checkDuplicateCertificateTemplate(certificateId,certName);
//	}
	public List<GroupTemplate> getDuplicateCheckList(String templateName) throws Exception {
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		log.debug("Leaving getTemplateList ");
		
		return ITemplatePassword.getDuplicateCheckList(templateName);
	}

	/**
	 * @param templateName
	 * @return
	 * @throws Exception
	 */
	public List<GroupTemplate> getTemplateForNRI(String templateName) throws Exception{
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		log.debug("Leaving getTemplateForNRI");
		return ITemplatePassword.getTemplateForNRI(templateName);
	}
}