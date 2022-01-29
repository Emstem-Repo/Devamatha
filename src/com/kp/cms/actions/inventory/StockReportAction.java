package com.kp.cms.actions.inventory;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.StockReportForm;
import com.kp.cms.handlers.inventory.StocksReceiptHandler;
import com.kp.cms.handlers.reports.StockReportHandler;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.to.inventory.InvTxTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * Stock report generation
 *
 */
public class StockReportAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(StockReportAction.class);
	
	/**
	 * initializes stock report screen
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStockReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StockReportForm reportForm= (StockReportForm)form;
		setUserId(request, reportForm);
		try {
			reportForm.resetFields();
			
			HttpSession session = request.getSession(false);
			if(session!=null && session.getAttribute("itemList")!=null)
			session.removeAttribute("itemList");
			
			reportForm.setLocations(StocksReceiptHandler.getInstance().getAllInventoryLocation());
			reportForm.setTxnList(null);
		} 
		catch (Exception e) {
			log.error("error in initStockReport...",e);
			throw e;
			
		}
	
	    log.info("exit initStockReport page...");
		return mapping.findForward(CMSConstants.INIT_STOCK_REPORT_PAGE);
	}
	
	/**
	 * submits stock report view
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitStockReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StockReportForm reportForm= (StockReportForm)form;
		setUserId(request, reportForm);
		//validation if needed
		ActionMessages errors=reportForm.validate(mapping, request);
		if(errors==null)
			errors= new ActionMessages();
		try {
			validateStockReport(reportForm,errors);
			
			//validate
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_STOCK_REPORT_PAGE);
			}
			//set locationName
			// set vendor name to form
			if(reportForm.getLocationId()!=null && !StringUtils.isEmpty(reportForm.getLocationId())
					&& StringUtils.isNumeric(reportForm.getLocationId()) && reportForm.getLocations()!=null){
				Iterator<InvLocationTO> vItr=reportForm.getLocations().iterator();
				while (vItr.hasNext()) {
					InvLocationTO locTO = (InvLocationTO) vItr.next();
					if(locTO.getId()==Integer.parseInt(reportForm.getLocationId())){
						reportForm.setLocationName(locTO.getName());
					}
					
				}
				
			}
			
			//else fetch all from inv_tx Table on date
			List<InvTxTO> itemList=StockReportHandler.getInstance().getItemTransactionsOnDate(reportForm.getDate(),Integer.parseInt(reportForm.getLocationId()),reportForm.getEndDate());
			HttpSession session = request.getSession();
			if(itemList!=null && !itemList.isEmpty()){
				session.setAttribute("itemList",itemList);
			}else{
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage("knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		} 
		catch (Exception e) {
			log.error("error in submitStockReport...",e);
			throw e;
			
		}
	
	log.info("exit submitStockReport page...");
		return mapping.findForward(CMSConstants.STOCK_REPORT_PAGE);
	}
	
	
	private void validateStockReport(StockReportForm reportForm,
			ActionMessages errors) {
		
		// date validation
		if(reportForm.getDate()!=null && !StringUtils.isEmpty(reportForm.getDate())&& !CommonUtil.isValidDate(reportForm.getDate())){
			if (errors.get(CMSConstants.STOCK_REPORT_DATE_INVALID)!=null && !errors.get(CMSConstants.STOCK_REPORT_DATE_INVALID).hasNext()) {
				errors.add(CMSConstants.STOCK_REPORT_DATE_INVALID, new ActionError(CMSConstants.STOCK_REPORT_DATE_INVALID));
			}
		}
		// location id validation
		if(reportForm.getLocationId()!=null && !StringUtils.isEmpty(reportForm.getLocationId())&& !StringUtils.isNumeric(reportForm.getLocationId())){
			if (errors.get(CMSConstants.STOCK_REPORT_LOCATION_INVALID)!=null && !errors.get(CMSConstants.STOCK_REPORT_LOCATION_INVALID).hasNext()) {
				errors.add(CMSConstants.STOCK_REPORT_LOCATION_INVALID, new ActionError(CMSConstants.STOCK_REPORT_LOCATION_INVALID));
			}
		}
		if(CommonUtil.checkForEmpty(reportForm.getDate()) && CommonUtil.checkForEmpty(reportForm.getEndDate()) && CommonUtil.isValidDate(reportForm.getDate()) && CommonUtil.isValidDate(reportForm.getEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(reportForm.getDate());
			Date endDate = CommonUtil.ConvertStringToDate(reportForm.getEndDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError("knowledgepro.attendance.activityattendence.greaterenddate"));
			}
		}
	}

	public String getCurrentDate() {
		Date a=new Date();
		int month=a.getMonth()+1;
		int day=a.getDate();
		int year=a.getYear()+1900;
		String sMonth="",sday="";
		if(month<10){
			sMonth=Integer.toString(month);
			sMonth="0"+sMonth;
		}else{
			sMonth=Integer.toString(month);	
		}
		if(day<10){
			sday=Integer.toString(day);
			sday="0"+sday;
		}else{
			sday=Integer.toString(day);
		}
		return Integer.toString(year)+"-"+sMonth+"-"+sday;
	}

	/**
	 * get detailed transaction
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StockReportForm reportForm= (StockReportForm)form;
		setUserId(request, reportForm);
		try {
			String txnId=reportForm.getTxnId();
			String selDt=reportForm.getSelectedDate();
			String selLoc=reportForm.getSelectedLocation();
			reportForm.setTxnList(StockReportHandler.getInstance().getItemTransactionsDetails(selDt,Integer.parseInt(txnId),Integer.parseInt(selLoc)));
		} 
		catch (Exception e) {
			log.error("error in getDetails...",e);
			throw e;
			
		}
	
	log.info("exit getDetails page...");
		return mapping.findForward(CMSConstants.STOCK_DETAIL_TXN__PAGE);
	}
}
