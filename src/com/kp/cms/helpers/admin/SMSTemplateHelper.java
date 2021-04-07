package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.SMSTemplateForm;
import com.kp.cms.to.admission.SMSTemplateTo;

public class SMSTemplateHelper {
	
	private static final Log log = LogFactory.getLog(TemplateHelper.class);
	private static volatile SMSTemplateHelper templateHelper= null;
	public static SMSTemplateHelper getInstance() {
	      if(templateHelper == null) {
	    	  templateHelper = new SMSTemplateHelper();
	      }
	      return templateHelper;
	}
	
	/**
	 * This method creates the EmailPasswordTemplate object and returns.
	 * @param templateForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public SMSTemplate getSMSTemplateObject(SMSTemplateForm templateForm,String mode) throws Exception {
		SMSTemplate smsTemplate = new SMSTemplate();
		ProgramType programType = new ProgramType();
		if(templateForm.getProgramTypeId() != null && !templateForm.getProgramTypeId().trim().isEmpty()){
			programType.setId(Integer.valueOf(templateForm.getProgramTypeId()));
			smsTemplate.setProgramType(programType);
		}
		Program program = new Program();
		if(templateForm.getProgramId() != null && !templateForm.getProgramId().trim().isEmpty()){
			program.setId(Integer.valueOf(templateForm.getProgramId()));
			smsTemplate.setProgram(program);
		}
		Course course = new Course();
		if(templateForm.getCourseId() != null && !templateForm.getCourseId().trim().isEmpty() ){
			course.setId(Integer.valueOf(templateForm.getCourseId()));
			smsTemplate.setCourse(course);
		}
		
		smsTemplate.setTemplateName(templateForm.getTemplateName());
		smsTemplate.setTemplateDescription(templateForm.getTemplateDescription());
		if(mode.equalsIgnoreCase(CMSConstants.ADD)) {
			smsTemplate.setCreatedBy(templateForm.getUserId());
			smsTemplate.setCreatedDate(new Date());
		}
		smsTemplate.setLastModifiedDate(new Date());
		smsTemplate.setModifiedBy(templateForm.getUserId());
		log.debug("leaving the getTemplateObject");
		return smsTemplate;
	}
	
	/**
	 * This method creates the EmailPasswordTemplate object and returns.
	 * @param templateForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<SMSTemplateTo> convertBOtoTO(List<SMSTemplate> smsTemplateList) throws Exception {
		
		List<SMSTemplateTo> smsTemplateTOList = new ArrayList<SMSTemplateTo>();
		Iterator<SMSTemplate> smsTemplateItr = smsTemplateList.iterator();
		
		while (smsTemplateItr.hasNext()) {
			SMSTemplate smsTemplate = (SMSTemplate) smsTemplateItr.next();
			
			SMSTemplateTo smsTemplateTO = new SMSTemplateTo();
			
			if(smsTemplate.getProgramType() != null){
				smsTemplateTO.setProgramTypeId(String.valueOf(smsTemplate.getProgramType().getId()));
				smsTemplateTO.setProgramTypeName(smsTemplate.getProgramType().getName());
			}else{
				smsTemplateTO.setProgramTypeName(CMSConstants.NA);
			}
			
			if(smsTemplate.getProgram() != null){
				smsTemplateTO.setProgramId(String.valueOf(smsTemplate.getProgram().getId()));
				smsTemplateTO.setProgramName(smsTemplate.getProgram().getName());
			}else{
				smsTemplateTO.setProgramName(CMSConstants.NA);
			}

			if(smsTemplate.getCourse() != null){
				smsTemplateTO.setCourseId(String.valueOf(smsTemplate.getCourse().getId()));
				smsTemplateTO.setCourseName(smsTemplate.getCourse().getName());
			}else{
				smsTemplateTO.setCourseName(CMSConstants.NA);
			}
			
			smsTemplateTO.setId(smsTemplate.getId());
			smsTemplateTO.setTemplateName(smsTemplate.getTemplateName());
			smsTemplateTO.setTemplateDescription(smsTemplate.getTemplateDescription());
			
			smsTemplateTOList.add(smsTemplateTO);
		}
		
		log.debug("leaving the getTemplateObject");
		return smsTemplateTOList;
	}
}
