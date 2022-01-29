package com.kp.cms.actions.inventory;

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
import com.kp.cms.to.inventory.StockReportTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * Stock report generation
 *
 */
public class StockReportDetailAction extends BaseDispatchAction {

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
	public ActionForward initStockReportDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StockReportForm reportForm= (StockReportForm)form;
		setUserId(request, reportForm);
		try {
			reportForm.setDate(null);
			reportForm.setLocationId(null);
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
		return mapping.findForward(CMSConstants.INIT_STOCK_REPORT_DETAIL);
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
	public ActionForward submitStockReportDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StockReportForm reportForm= (StockReportForm)form;
		setUserId(request, reportForm);
		//validation if needed
		ActionMessages errors=reportForm.validate(mapping, request);
		if(errors==null)
			errors= new ActionMessages();
		try {
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
			String date=getCurrentDate();
			
			//else fetch all from inv_tx Table on date
			List<StockReportTO> itemList=StockReportHandler.getInstance().getStockReportDetailOnDate(date,Integer.parseInt(reportForm.getLocationId()));
			HttpSession session = request.getSession();
			if(itemList!=null && !itemList.isEmpty()){
//				reportForm.setTxnList(itemList);
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
		return mapping.findForward(CMSConstants.STOCK_REPORT_DETAILS);
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
