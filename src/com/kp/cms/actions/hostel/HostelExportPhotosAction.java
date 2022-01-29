package com.kp.cms.actions.hostel;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelExportPhotosForm;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;

public class HostelExportPhotosAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelExportPhotos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelExportPhotosForm objForm = (HostelExportPhotosForm)form;
		objForm.reset();
		try{
				setRequiredDataToFrom(objForm);
		}catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_EXPORT_PHOTOS);
	}

	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setRequiredDataToFrom(HostelExportPhotosForm objForm)throws Exception {
		Map<String,String> hostelMap = AvailableSeatsHandler.getInstance().getHostelMap();
		/*Map<Integer,String> blockMap = HostelEntryHandler.getInstance().getBlocks(objForm.getHostelId());
		Map<Integer,String> unitMap = HostelEntryHandler.getInstance().getUnits(objForm.getUnitId());*/
		objForm.setHostelMap(hostelMap);
		/*objForm.setBlockMap(blockMap);
		objForm.setUnitMap(unitMap);*/
	}
}
