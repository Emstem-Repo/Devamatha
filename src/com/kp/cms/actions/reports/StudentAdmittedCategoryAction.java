package com.kp.cms.actions.reports;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.StudentAdmittedCategoryForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.StudentAdmittedCategoryHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.utilities.CommonUtil;

public class StudentAdmittedCategoryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StudentAdmittedCategoryAction.class);
	
	/**
	 * initializes student report screen
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentAdmittedCategoryForm studentForm= (StudentAdmittedCategoryForm)form;
		
		setUserId(request, studentForm);
		try {
			studentForm.setProgramTypeId(null);
			studentForm.setProgramId(null);
			studentForm.setCastCategory(null);
			List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
			studentForm.setCasteList(castelist); 
		}
		catch (Exception e) {
			log.error("error in initStudentReport...",e);
			throw e;
			
		}
		return mapping.findForward(CMSConstants.INIT_STUDENT_CATEGORY_REPORT);
	}
	
	
	/**
	 * gets all students
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadStudentReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentAdmittedCategoryForm studentForm= (StudentAdmittedCategoryForm)form;
		//validation if needed
		ActionMessages errors=studentForm.validate(mapping, request);
		if(errors==null)
			errors= new ActionMessages();
		try {
			//validate
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_STUDENT_CATEGORY_REPORT);
			}
			studentForm.setReportdate(CommonUtil.formatDate(new Date(), "dd/MM/yyyy"));
			if(studentForm.getProgramTypeId()!=null && !StringUtils.isEmpty(studentForm.getProgramTypeId()) && StringUtils.isNumeric(studentForm.getProgramTypeId()))
			{
				List<ProgramTypeTO> programtypelist=ProgramTypeHandler.getInstance().getProgramType();
				if(programtypelist!=null){
					Iterator<ProgramTypeTO> prgItr=programtypelist.iterator();
					while (prgItr.hasNext()) {
						ProgramTypeTO programTypeTO = (ProgramTypeTO) prgItr.next();
						if(programTypeTO.getProgramTypeId()!=0 && 
								programTypeTO.getProgramTypeId()== Integer.parseInt(studentForm.getProgramTypeId())){
							studentForm.setProgramTypeName(programTypeTO.getProgramTypeName());
						}
					}
				}
			}
			
			if(studentForm.getProgramId()!=null && !StringUtils.isEmpty(studentForm.getProgramId()) && StringUtils.isNumeric(studentForm.getProgramId()))
			{
				List<ProgramTO> programlist=ProgramHandler.getInstance().getProgram();
				if(programlist!=null){
					Iterator<ProgramTO> prgItr=programlist.iterator();
					while (prgItr.hasNext()) {
						ProgramTO programTO = (ProgramTO) prgItr.next();
						if(programTO.getId()!=0 && 
								programTO.getId()== Integer.parseInt(studentForm.getProgramId())){
							studentForm.setProgramName(programTO.getName());
						}
					}
				}
			}
			
			if(studentForm.getCastCategory()!=null && !StringUtils.isEmpty(studentForm.getCastCategory()) && StringUtils.isNumeric(studentForm.getCastCategory()))
			{
				List<CasteTO> castelist=studentForm.getCasteList();
				if(castelist!=null){
					Iterator<CasteTO> cstItr=castelist.iterator();
					while (cstItr.hasNext()) {
						CasteTO cstTO = (CasteTO) cstItr.next();
						if(cstTO.getCasteId()!=0 && 
								cstTO.getCasteId()== Integer.parseInt(studentForm.getCastCategory())){
							studentForm.setCasteName(cstTO.getCasteName());
						}
					}
				}
			}
			
			studentForm.setSearchedStudents(StudentAdmittedCategoryHandler.getInstance().searchStudents(studentForm.getProgramId(),studentForm.getCastCategory()));
			if(studentForm.getSearchedStudents()==null || studentForm.getSearchedStudents().isEmpty())
			{
				//no records found
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
					message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add("messages", message);
					saveMessages(request, messages);
				return mapping.findForward(CMSConstants.INIT_STUDENT_CATEGORY_REPORT);
			}else{
				HttpSession session = request.getSession(false);
				if(session!=null)
					session.setAttribute("studentList",studentForm.getSearchedStudents());
				//data page
				return mapping.findForward(CMSConstants.STUDENT_CATEGORY_REPORT);
			}
			
		}
		catch (Exception e) {
			log.error("error in initStudentReport...",e);
			throw e;
			
		}
		
	}
}
