package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.TemplateForm;
import com.kp.cms.to.admin.GroupTemplateTO;

/**
 * 
 * Helper class for the template create.
 */
public class TemplateHelper {
	
	private static final Log log = LogFactory.getLog(TemplateHelper.class);
	private static volatile TemplateHelper templateHelper= null;
	public static TemplateHelper getInstance() {
	      if(templateHelper == null) {
	    	  templateHelper = new TemplateHelper();
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
	public GroupTemplate getGroupTemplateObject(TemplateForm templateForm,String mode) throws Exception {
		GroupTemplate groupTemplate = new GroupTemplate();
		ProgramType programType = new ProgramType();
		if(templateForm.getProgramTypeId() != null && !templateForm.getProgramTypeId().trim().isEmpty()){
			programType.setId(Integer.valueOf(templateForm.getProgramTypeId()));
			groupTemplate.setProgramType(programType);
		}
		Program program = new Program();
		if(templateForm.getProgramId() != null && !templateForm.getProgramId().trim().isEmpty()){
			program.setId(Integer.valueOf(templateForm.getProgramId()));
			groupTemplate.setProgram(program);
		}
		Course course = new Course();
		if(templateForm.getCourseId() != null && !templateForm.getCourseId().trim().isEmpty() ){
			course.setId(Integer.valueOf(templateForm.getCourseId()));
			groupTemplate.setCourse(course);
		}
		
		groupTemplate.setTemplateName(templateForm.getTemplateName());
		groupTemplate.setTemplateDescription(templateForm.getTemplateDescription());
		if(mode.equalsIgnoreCase(CMSConstants.ADD)) {
			groupTemplate.setCreatedBy(templateForm.getUserId());
			groupTemplate.setCreatedDate(new Date());
		}
		groupTemplate.setLastModifiedDate(new Date());
		groupTemplate.setModifiedBy(templateForm.getUserId());
		log.debug("leaving the getTemplateObject");
		return groupTemplate;
	}
	
	/**
	 * This method creates the EmailPasswordTemplate object and returns.
	 * @param templateForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<GroupTemplateTO> convertBOtoTO(List<GroupTemplate> groupTemplateList) throws Exception {
		
		List<GroupTemplateTO> groupTemplateTOList = new ArrayList<GroupTemplateTO>();
		Iterator<GroupTemplate> groupTemplateItr = groupTemplateList.iterator();
		
		while (groupTemplateItr.hasNext()) {
			GroupTemplate groupTemplate = (GroupTemplate) groupTemplateItr.next();
			
			GroupTemplateTO groupTemplateTO = new GroupTemplateTO();
			
			if(groupTemplate.getProgramType() != null){
				groupTemplateTO.setProgramTypeId(String.valueOf(groupTemplate.getProgramType().getId()));
				groupTemplateTO.setProgramTypeName(groupTemplate.getProgramType().getName());
			}else{
				groupTemplateTO.setProgramTypeName(CMSConstants.NA);
			}
			
			if(groupTemplate.getProgram() != null){
				groupTemplateTO.setProgramId(String.valueOf(groupTemplate.getProgram().getId()));
				groupTemplateTO.setProgramName(groupTemplate.getProgram().getName());
			}else{
				groupTemplateTO.setProgramName(CMSConstants.NA);
			}

			if(groupTemplate.getCourse() != null){
				groupTemplateTO.setCourseId(String.valueOf(groupTemplate.getCourse().getId()));
				groupTemplateTO.setCourseName(groupTemplate.getCourse().getName());
			}else{
				groupTemplateTO.setCourseName(CMSConstants.NA);
			}
			
			groupTemplateTO.setId(groupTemplate.getId());
			groupTemplateTO.setTemplateName(groupTemplate.getTemplateName());
			groupTemplateTO.setTemplateDescription(groupTemplate.getTemplateDescription());
			
			groupTemplateTOList.add(groupTemplateTO);
		}
		
		log.debug("leaving the getTemplateObject");
		return groupTemplateTOList;
	}
}