package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.ListOfOccupancyRegisterForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.ListOfOccupancyRegisterHandler;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.ListOfOccupancyRegisterTO;

/**
 * 
 * @author kolli.ramamohan
 *
 */

public class ListOfOccupancyRegisterAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(ListOfOccupancyRegisterAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward initListOfOccupancyRegister(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		log.info("Entering into initListOfOccupancyRegister of ListOfOccupancyRegisterAction");
		ListOfOccupancyRegisterForm listRegForm = (ListOfOccupancyRegisterForm)form;		
		try {
			listRegForm.clear();
			setHostelEntryDetailsToRequest(request);
		} catch (Exception e) {
			log.error("Error occured in initListOfOccupancyRegister of ListOfOccupancyRegisterAction", e);
			String msg = super.handleApplicationException(e);
			listRegForm.setErrorMessage(msg);
			listRegForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initListOfOccupancyRegister of ListOfOccupancyRegisterAction");
		return mapping.findForward(CMSConstants.HOSTEL_INIT_LIST_OF_OCCUPANCY_REGISTER);		
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
	public ActionForward getHostelOccupancyRegisterPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into getHostelOccupancyRegisterPage of ListOfOccupancyRegisterAction");
		ListOfOccupancyRegisterForm listRegForm = (ListOfOccupancyRegisterForm)form;
		ActionErrors errors=null;
		errors=listRegForm.validate(mapping, request);		
		if(errors.isEmpty()){
			try {			
				assignListOfOccupancyRegisterListToForm(listRegForm);
				listRegForm.clear();
			} catch (Exception e) {
				log.error("Error occured in initListOfOccupancyRegister of ListOfOccupancyRegisterAction", e);
				String msg = super.handleApplicationException(e);
				listRegForm.setErrorMessage(msg);
				listRegForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.HOSTEL_OCCUPANCY_REGISTER_SEARCH);
		}else{
			try {
				setHostelEntryDetailsToRequest(request);
			} catch (Exception e) {
				
			}
			addErrors(request,errors);
			return mapping.findForward(CMSConstants.HOSTEL_INIT_LIST_OF_OCCUPANCY_REGISTER);
		}		
	}
	
	/**
	 * 
	 * @param request
	 * @throws Exception 
	 */
	public void setHostelEntryDetailsToRequest(HttpServletRequest request) throws Exception{
		log.debug("start setHostelEntryDetailsToRequest");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("exit setHostelEntryDetailsToRequest");
	}
	
	/**
	 * setting floor map to request
	 * @param request
	 * @param roomForm
	 */
	public void setFloorMapToRequest(HttpServletRequest request,  ListOfOccupancyRegisterForm listRegForm) {
		if (listRegForm.getHostelId() != null && !(listRegForm.getHostelId().isEmpty())) {
			Map<Integer, String> floorMap = CommonAjaxHandler.getInstance().getFloorsByHostel(Integer.parseInt(listRegForm.getHostelId()));
			request.setAttribute("floorMap", floorMap);
		}
	}
	
	/**
	 * 
	 * @param listRegForm
	 * @throws Exception
	 */	
	public void assignListOfOccupancyRegisterListToForm(ListOfOccupancyRegisterForm listRegForm) throws Exception{
		ListOfOccupancyRegisterHandler listRegHandler=ListOfOccupancyRegisterHandler.getInstance();
		try {
			List<ListOfOccupancyRegisterTO> listOfOccupancyRegisterList=listRegHandler.getHostelListOfOccupancyRegisterList(listRegForm);			
			listRegForm.setListOfOccupancyRegisterList(listOfOccupancyRegisterList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
