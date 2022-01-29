package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.kp.cms.forms.hostel.HostelActionReportForm;
import com.kp.cms.handlers.hostel.HostelActionReportHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.to.hostel.HostelActionReportTO;
import com.kp.cms.to.hostel.HostelTO;

/**
 * 
 * @author kolli.ramamohan
 * 
 */
public class HostelActionReportAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(HostelReservationAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelActionReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("start initHostelActionReport");
		HostelActionReportForm hostelActionReportForm = (HostelActionReportForm) form;
		try {
			setHostelEntryDetailsToRequest(request, hostelActionReportForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("end initHostelActionReport");
		return mapping.findForward(CMSConstants.HOSTEL_ACTION_REPORT_PAGE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHostelActionReportDetailsPage(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("start getHostelActionReportDetailsPage");
		List<HostelActionReportTO> actionReportTOList = new ArrayList<HostelActionReportTO>();
		HostelActionReportForm hostelActionReportForm = (HostelActionReportForm) form;
		HostelActionReportHandler actionReportHandler = HostelActionReportHandler
				.getInstance();
		try {
			actionReportTOList = actionReportHandler
					.getHostelActionReportDetails(hostelActionReportForm);
		} catch (Exception e) {
			log.debug("erros in theis page");
		}
		HttpSession sess = request.getSession();
		sess.setAttribute("theList", actionReportTOList);
		hostelActionReportForm.setHostelActionReportList(actionReportTOList);
		log.debug("end getHostelActionReportDetailsPage");
		return mapping
				.findForward(CMSConstants.HOSTEL_ACTION_REPORT_DETIALS_PAGE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward printHostelActionReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("start printHostelActionReport");
		HostelActionReportForm hostelActionReportForm = (HostelActionReportForm) form;
		List<HostelActionReportTO> repTOPrintList = new ArrayList<HostelActionReportTO>();
		HostelActionReportTO repTO = null;
		String checks = request.getParameter("checks").trim();
		String ch[] = checks.split(" ");

		HttpSession sess1 = request.getSession();
		List<HostelActionReportTO> actionReportTOList = (List<HostelActionReportTO>) sess1
				.getAttribute("theList");
		Iterator iter = actionReportTOList.iterator();
		while (iter.hasNext()) {
			repTO = (HostelActionReportTO) iter.next();
			for (int k = 0; k < ch.length; k++) {
				if (String.valueOf(repTO.getSrlNo()).equals(
						String.valueOf(ch[k]))) {
					repTOPrintList.add(repTO);
				}
			}
		}
		hostelActionReportForm.setHostelPrintList(repTOPrintList);
		
		log.debug("end printHostelActionReport");
		return mapping
				.findForward(CMSConstants.HOSTEL_ACTION_REPORT_DETIALS_PRINT_PAGE);
	}
	
	public ActionForward displayHostelActionReportMainPage(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("start printHostelActionReport");
		log.debug("end printHostelActionReport");
		return mapping.findForward(CMSConstants.HOSTEL_ACTION_REPORT_PAGE);
	}

	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void setHostelEntryDetailsToRequest(HttpServletRequest request,
			HostelActionReportForm hostelActionReportForm) throws Exception {
		log.debug("start setHostelEntryDetailsToRequest");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance()
				.getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		hostelActionReportForm.setHostelList(hostelList);
		log.debug("exit setHostelEntryDetailsToRequest");
	}
}
