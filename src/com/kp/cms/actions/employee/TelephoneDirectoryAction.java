package com.kp.cms.actions.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.TelephoneDirectoryForm;
import com.kp.cms.handlers.employee.TelephoneDirectoryHandler;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.EmployeeTO;

public class TelephoneDirectoryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(TelephoneDirectoryAction.class);
	private static final String PHOTOBYTES = "PhotoBytes";
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTelephoneDirectory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TelephoneDirectoryForm objForm = (TelephoneDirectoryForm)form;
		objForm.setId(0);
		objForm.setFingerPrintId(null);
		objForm.setEmployeeName(null);
		objForm.setDepartmentId(null);
		objForm.setEmpList(null);
		objForm.setExtNo(null);
		//cleanupEditSessionData(request);
		List<DepartmentEntryTO> newList = TelephoneDirectoryHandler.getInstance().getDepartmentList();
		objForm.setDeptListTO(newList);
 		return mapping.findForward(CMSConstants.INIT_TELEPHONE_DIRECTORY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TelephoneDirectoryForm objForm = (TelephoneDirectoryForm)form;
		//cleanupEditSessionData(request);
		ActionErrors errors = new ActionErrors();
		try{
			boolean select=true;
			if(objForm.getDepartmentId()==null || objForm.getDepartmentId().isEmpty()){
				if(objForm.getEmployeeName()==null || objForm.getEmployeeName().isEmpty()){
					if(objForm.getFingerPrintId()==null || objForm.getFingerPrintId().isEmpty()){
						if(objForm.getExtNo()==null || objForm.getExtNo().isEmpty()){
							select = false;
						}
					}
				}
			}
			if(!select){
				errors.add("error", new ActionError("knowledgepro.select.anyone"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_TELEPHONE_DIRECTORY);
			}else{
				List<EmployeeTO> newToList = TelephoneDirectoryHandler.getInstance().getSearchDetails(objForm,request);
				if(newToList!=null && !newToList.isEmpty()){
					objForm.setEmpList(newToList);
				}else{
					errors.add("error", new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
					objForm.setEmpList(null);
				}
			}
			/*if(objForm.getDepartmentId()==null || objForm.getDepartmentId().isEmpty()|| objForm.getEmployeeName()==null ||
					objForm.getEmployeeName().isEmpty() || objForm.getFingerPrintId()==null || objForm.getFingerPrintId().isEmpty() ||
					objForm.getExtNo()==null || objForm.getExtNo().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.select.anyone"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_TELEPHONE_DIRECTORY);
				
			}else{
			List<EmployeeTO> newToList = TelephoneDirectoryHandler.getInstance().getSearchDetails(objForm,request);
			if(newToList!=null && !newToList.isEmpty()){
				objForm.setEmpList(newToList);
			}else{
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
				objForm.setEmpList(null);
			}
			}*/
		}catch (Exception e) {
		String msg = super.handleApplicationException(e);
		objForm.setErrorMessage(msg);
		objForm.setErrorStack(e.getMessage());}
		return mapping.findForward(CMSConstants.INIT_TELEPHONE_DIRECTORY);
	}
	
	/**
	 * @param mapping
	 * @param form for restricted users
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTelephoneDirectoryRestricted(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TelephoneDirectoryForm objForm = (TelephoneDirectoryForm)form;
		objForm.setId(0);
		objForm.setFingerPrintId(null);
		objForm.setEmployeeName(null);
		objForm.setDepartmentId(null);
		objForm.setEmpList(null);
		objForm.setExtNo(null);
		//cleanupEditSessionData(request);
		List<DepartmentEntryTO> newList = TelephoneDirectoryHandler.getInstance().getDepartmentList();
		objForm.setDeptListTO(newList);
 		return mapping.findForward(CMSConstants.INIT_TELEPHONE_DIRECTORY_RESTRICTED);
	}
	
	/**
	 * @param mapping
	 * @param form for restricted users
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchDetailsRestricted(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TelephoneDirectoryForm objForm = (TelephoneDirectoryForm)form;
		//cleanupEditSessionData(request);
		ActionErrors errors = new ActionErrors();
		try{
			boolean select=true;
			if(objForm.getDepartmentId()==null || objForm.getDepartmentId().isEmpty()){
				if(objForm.getEmployeeName()==null || objForm.getEmployeeName().isEmpty()){
					if(objForm.getFingerPrintId()==null || objForm.getFingerPrintId().isEmpty()){
						if(objForm.getExtNo()==null || objForm.getExtNo().isEmpty()){
							select = false;
						}
					}
				}
			}
			if(!select){
				errors.add("error", new ActionError("knowledgepro.select.anyone"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_TELEPHONE_DIRECTORY_RESTRICTED);
			}else{
				List<EmployeeTO> newToList = TelephoneDirectoryHandler.getInstance().getSearchDetails(objForm,request);
			if(newToList!=null && !newToList.isEmpty()){
				objForm.setEmpList(newToList);
			}else{
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
				objForm.setEmpList(null);
			}
			}
			/*List<EmployeeTO> newToList = TelephoneDirectoryHandler.getInstance().getSearchDetails(objForm,request);
			if(newToList!=null && !newToList.isEmpty()){
				objForm.setEmpList(newToList);
			}else{
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
				objForm.setEmpList(null);
			}*/
		}catch (Exception e) {
		String msg = super.handleApplicationException(e);
		objForm.setErrorMessage(msg);
		objForm.setErrorStack(e.getMessage());}
		return mapping.findForward(CMSConstants.INIT_TELEPHONE_DIRECTORY_RESTRICTED);
	}
	/*private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			if (session.getAttribute(TelephoneDirectoryAction.PHOTOBYTES) != null)
				session.removeAttribute(TelephoneDirectoryAction.PHOTOBYTES);
		}
	}*/
	/**
	 * for extension numbers display by giri
	 */
	public ActionForward getExtensionNumbers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		TelephoneDirectoryForm objForm = (TelephoneDirectoryForm)form;
		TelephoneDirectoryHandler.getInstance().getExtensionNumbers(objForm);
		return mapping.findForward(CMSConstants.VIEW_EXTENDION_NUMBERS);
	}
}
