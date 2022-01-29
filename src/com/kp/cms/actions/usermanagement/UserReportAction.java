package com.kp.cms.actions.usermanagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.UserReportForm;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.handlers.usermanagement.UserReportHandler;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.UserInfoTO;

public class UserReportAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(UserReportAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUserReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("inside initUserReport");
		UserReportForm userReportForm = (UserReportForm) form;
		HttpSession session = request.getSession(false);
		Map<Integer, String> department = UserInfoHandler.getInstance().getDepartment();
		userReportForm.setDepartment(department);
		session.removeAttribute("userinfoList");
		session.removeAttribute("privilegeList");
		log.debug("leaving initUserReport");
		return mapping.findForward(CMSConstants.USER_REPORT);

	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return list of users based on the search criteria
	 * @throws Exception
	 */
	public ActionForward searchUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
									throws Exception {
		log.debug("inside searchUsers");
		UserReportForm userReportForm = (UserReportForm) form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("userinfoList")==null){
	 		List<UserInfoTO> userInfoTOList  = UserReportHandler.getInstance().getUserDetails(userReportForm.getSearchDob(), userReportForm.getFirstsearchName(),userReportForm.getMiddlesearchName(), userReportForm.getLastsearchName(), userReportForm.getSearchDepartment());
			session.setAttribute("userinfoList",userInfoTOList);
		}	
		log.debug("leaving searchUsers");
		return mapping.findForward(CMSConstants.SUBMIT_USER_REPORT);
	}
	/**
	 * setting privilege list display while clicking on the role
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward dispPrivilages(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)	throws Exception {
		log.debug("inside dispPrivilages");
		HttpSession session = request.getSession();
		UserReportForm userReportForm = (UserReportForm)form;
		List<AssignPrivilegeTO> assignPrivilegeList = UserReportHandler.getInstance().getUserPrivilegesByRole(userReportForm.getRoleId());
		session.setAttribute("privilegeList",assignPrivilegeList);
		log.debug("leaving dispPrivilages");
		return mapping.findForward(CMSConstants.SHOW_PRIVILEGE);
	}
}
