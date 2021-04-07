package com.kp.cms.actions.hostel;

import java.util.List;
import java.util.Map;

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
import com.kp.cms.actions.employee.PayScaleDetailsAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelStatusInfoForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelApplicationHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelStatusInfoHandler;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HlFloorTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * 
 * @author kolli.ramamohan
 * 
 */
public class HostelStatusInfoAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(HostelStatusInfoAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelStatusInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HostelStatusInfoForm hostelStatusInfoForm = (HostelStatusInfoForm) form;
		hostelStatusInfoForm.clearFormFields();
		setHostelsToForm(request, hostelStatusInfoForm);
		return mapping.findForward(CMSConstants.HOSTEL_STATUS_INFO_PAGE);
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
	public ActionForward getHostelStatusInfoDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HostelStatusInfoForm hostelStatusInfoForm = (HostelStatusInfoForm) form;
		
		ActionErrors errors=hostelStatusInfoForm.validate(mapping, request);
		if(!errors.isEmpty()){
			setHostelsToForm(request, hostelStatusInfoForm);
			saveErrors(request,errors);
			return mapping.findForward(CMSConstants.HOSTEL_STATUS_INFO_PAGE);
		}else{
			List<HlFloorTo> floorList=HostelStatusInfoHandler.getInstance().getHostelStatusInfo(hostelStatusInfoForm);
			if(floorList.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				setHostelsToForm(request, hostelStatusInfoForm);
				return mapping.findForward(CMSConstants.HOSTEL_STATUS_INFO_PAGE);
			}
			hostelStatusInfoForm.setFloorList(floorList);
		}
		return mapping.findForward("hostelStatusRoomPage");
	}

	/**
	 * 
	 * @param request
	 * @param hostelStatusInfoForm
	 * @throws Exception
	 */
	public void setHostelsToForm(HttpServletRequest request,
			HostelStatusInfoForm hostelStatusInfoForm) throws Exception {
		List<HostelTO> hostelList = HostelApplicationHandler.getInstance().getHostelDetails();
		hostelStatusInfoForm.setHostelList(hostelList);
		request.setAttribute("hostelCollection", hostelList);
		if(hostelStatusInfoForm.getHostelId()!=null && !hostelStatusInfoForm.getHostelId().isEmpty()){
			Map<Integer, String> floorMap = CommonAjaxHandler.getInstance().getFloorsByHostel(Integer.parseInt(hostelStatusInfoForm.getHostelId()));
			request.setAttribute("floorMap",floorMap);
			Map<Integer, String> roomTypeMap = CommonAjaxHandler.getInstance().getRoomTypesByHostel(Integer.parseInt(hostelStatusInfoForm.getHostelId()));
			request.setAttribute("roomTypeMap",roomTypeMap);
		}
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
	public ActionForward goToHostelStatusInfoPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HostelStatusInfoForm hostelForm=new HostelStatusInfoForm();
		hostelForm.clearFormFields();
		return mapping.findForward("hostelStatusPage");
	}
	/**
	 * @author venkat
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward initHostelStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HostelStatusInfoForm hostelForm=(HostelStatusInfoForm)form;
		hostelForm.clearFormFields();
		HttpSession session = request.getSession();
		try{
			List<HostelTO> hostelList = HostelApplicationHandler.getInstance().getHostelDetails();
			session.setAttribute("HostelList", hostelList);
		}catch(Exception exception){
			log.error("Error occured in HostelStatusInfoAction", exception);
			String msg = super.handleApplicationException(exception);
			hostelForm.setErrorMessage(msg);
			hostelForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//setRequiredDataToForm(hostelForm);
		return mapping.findForward("hostelStatus");
	}
	/*public void setRequiredDataToForm(HostelStatusInfoForm hostelForm)throws Exception{
		//Map<Integer,String> hostelMap = HostelApplicationHandler.getInstance().getHostelDetailsMap();
		//hostelForm.setHostelMap(hostelMap);
		List<HostelTO> hostelList = HostelApplicationHandler.getInstance().getHostelDetails();
		hostelForm.setHostelList(hostelList);
	}*/
	/**
	 * @author venkat
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward hostelStatusDisplay(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HostelStatusInfoForm hostelForm=(HostelStatusInfoForm)form;
		ActionErrors errors = new ActionErrors();
		try{
			Map<String, Map<String, Map<Integer,List<HlAdmissionTo>>>> hlAdmissionMap=HostelStatusInfoHandler.getInstance().getHostelDetailByHostelAndYear(hostelForm);
			//session.setAttribute("hlAdmissionMap", hlAdmissionMap);
			if(hlAdmissionMap==null)
			{
				hostelForm.setHlAdmissionMap(null);
				errors.add("error", new ActionError( "knowledgepro.norecords"));
				addErrors(request, errors);
			}
			hostelForm.setHlAdmissionMap(hlAdmissionMap);
			if(hostelForm.getHostelId() != null && !hostelForm.getHostelId().trim().isEmpty()){
				Map<Integer,String> blockMap = HostelEntryHandler.getInstance().getBlocks(hostelForm.getHostelId());
				blockMap = CommonUtil.sortMapByValue(blockMap);
				hostelForm.setBlockMap(blockMap);
			}
			if(hostelForm.getBlockId()!=null && !hostelForm.getBlockId().trim().isEmpty()){
				Map<Integer,String> unitMap = HostelEntryHandler.getInstance().getUnits(hostelForm.getHostelId());
				unitMap = CommonUtil.sortMapByValue(unitMap);
				hostelForm.setUnitMap(unitMap);
			}
		}catch(Exception exception){
			log.error("Error occured in HostelStatusInfoAction", exception);
			String msg = super.handleApplicationException(exception);
			hostelForm.setErrorMessage(msg);
			hostelForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//HttpSession session = request.getSession();
		
		//setRequiredDataToForm(hostelForm);
		return mapping.findForward("hostelStatus");
	}
}
