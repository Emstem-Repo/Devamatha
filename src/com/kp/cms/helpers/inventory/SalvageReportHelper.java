package com.kp.cms.helpers.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvSalvage;
import com.kp.cms.forms.reports.InvSalvageReportForm;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.to.inventory.InvSalvageTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.utilities.CommonUtil;

public class SalvageReportHelper {

private static final Log log = LogFactory.getLog(SalvageReportHelper.class);
	
	public static volatile SalvageReportHelper self=null;
	public static SalvageReportHelper getInstance(){
		if(self==null){
			self= new SalvageReportHelper();
		}
		return self;
	}
	
	/**
	 * @param salvageReportForm
	 * @return
	 * This method will build dynamic query
	 */

	private static String commonSearch(InvSalvageReportForm salvageReportForm) {
		log.info("entered commonSearch method in SalvageReportHelper class");
		
		String query = "";
		if(salvageReportForm.getInvLocationId() != null && salvageReportForm.getInvLocationId().trim().length() > 0 
				&& salvageReportForm.getItemId() != null && salvageReportForm.getSearchItem() != null){
			String invLocItem = " insalvage.invLocation.id = " + salvageReportForm.getInvLocationId()
			+" and invsal.invItem.id = "+ salvageReportForm.getItemId(); 
			
			query = query + invLocItem;
		}else if (salvageReportForm.getInvLocationId() != null && salvageReportForm.getInvLocationId().trim().length() > 0) {
			String invLoc = " insalvage.invLocation.id = "
					+ salvageReportForm.getInvLocationId();
			query = query + invLoc;
		}else{
			String itemId = " invsal.invItem.id = " + salvageReportForm.getItemId();

			query = query + itemId;
		}
		log.info("exit of commonSearch method in SalvageReportHelper class");
		return query;
	}
	
	
	/**
	 * @param salvageReportForm
	 * @return value of type string.
	 * This method will give final query
	 */
	public static String getSelectionSearchCriteria(InvSalvageReportForm salvageReportForm) {
		log.info("entered getSelectionSearchCriteria method in SalvageReportHelper class");
		String statusCriteria = commonSearch(salvageReportForm);
		
		String searchCriteria= "";
		searchCriteria = "select insalvage.id," +
				" insalvage.invLocation.id," +
				" invsal.invItem.id," +
				" insalvage.date," +
				" insalvage.remarks," +
				" insalvage.invLocation.name," +
				" invsal.invItem.name," +
				" invsal.invItem.code," +
				" invsal.quantity" +
				" from InvSalvage insalvage" +
				" join insalvage.invSalvageItems invsal"
				+ " where " + statusCriteria +" order by insalvage.invLocation.id";					
		log.info("exit of getSelectionSearchCriteria method in LeaveReportHelper class");
		return searchCriteria;

	}
	
	public static List<InvSalvageTO> getSalvageDetails(List<InvSalvage> salvageList) {
		log.info("entering in getSalvageDetails in SalvageReportHelper class..");
		List<InvSalvageTO> list = new ArrayList<InvSalvageTO>(); 
		Map<Integer, InvSalvageTO> salvageMap = new LinkedHashMap<Integer, InvSalvageTO>();
		if(salvageList != null && salvageList.size() != 0){
			InvSalvageTO invSalvageTO = null;
			InvLocationTO invLocationTO = null;
			ItemTO itemTO = null;
			Iterator iterator = salvageList.iterator();
			while (iterator.hasNext()) {
				Object[] invSalvage = (Object[]) iterator.next();
				
							if(salvageMap.containsKey((Integer)invSalvage[1])){
								invSalvageTO = salvageMap.get((Integer)invSalvage[1]);
								
								itemTO.setId(String.valueOf((Integer)invSalvage[2]));
								itemTO.setNameWithCode(invSalvage[6].toString()+"("+invSalvage[7].toString()+")");
								invSalvageTO.setItemTO(itemTO);
								
								invLocationTO.setId((Integer)invSalvage[1]);
								invLocationTO.setName(invSalvage[5].toString());
								invSalvageTO.setInvLocationTO(invLocationTO);
								
								invSalvageTO.setId((Integer)invSalvage[0]);
								invSalvageTO.setSalvageDate(CommonUtil.ConvertStringToDateFormat(invSalvage[3].toString(), "yyyy-MM-dd", "dd/MM/yyyy"));
								invSalvageTO.setQuatity(String.valueOf(invSalvage[8].toString()));
								invSalvageTO.setRemarks(invSalvage[4].toString());
								
							}else{
								invSalvageTO = new InvSalvageTO();
								invLocationTO = new InvLocationTO();
								itemTO = new ItemTO();
								
								itemTO.setId(String.valueOf((Integer)invSalvage[2]));
								itemTO.setNameWithCode(invSalvage[6].toString()+"("+invSalvage[7].toString()+")");
								invSalvageTO.setItemTO(itemTO);
								
								invLocationTO.setId((Integer)invSalvage[1]);
								invLocationTO.setName(invSalvage[5].toString());
								invSalvageTO.setInvLocationTO(invLocationTO);
								
								invSalvageTO.setId((Integer)invSalvage[0]);
								invSalvageTO.setSalvageDate(CommonUtil.ConvertStringToDateFormat(invSalvage[3].toString(), "yyyy-MM-dd", "dd/MM/yyyy"));
								invSalvageTO.setQuatity(String.valueOf(invSalvage[8].toString()));
								invSalvageTO.setRemarks(invSalvage[4].toString());
							
						}
							salvageMap.put((Integer)invSalvage[0], invSalvageTO);
			}
			list.addAll(salvageMap.values());
		}
		
		log.info("exit of getSalvageDetails in SalvageReportHelper class..");
		return list;
		
	}
}