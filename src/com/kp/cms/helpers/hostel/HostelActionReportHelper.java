package com.kp.cms.helpers.hostel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.to.hostel.HostelActionReportTO;
import com.kp.cms.utilities.CommonUtil;

public class HostelActionReportHelper {
	private static final Log log = LogFactory.getLog(HostelActionReportHelper.class);

	public List<HostelActionReportTO> getHostelActionReportDetails(List<Object> objList) throws Exception{
		Object obj[]=null;
		Date dateEnd=null;
		Date dateStart=null;
		java.util.Iterator<Object> iter=objList.iterator();
		HostelActionReportTO actionReportTO=null;
		List<HostelActionReportTO> actionReportTOList=new ArrayList<HostelActionReportTO>();							
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int k=100;
		while(iter.hasNext()){
			actionReportTO=new HostelActionReportTO();	
			obj=(Object[])iter.next();
			k++;
			actionReportTO.setSrlNo(k);
			actionReportTO.setHostelName(String.valueOf(obj[0]));			
			actionReportTO.setTypeId(String.valueOf(obj[1]));					
			actionReportTO.setRegNo(String.valueOf(obj[2]));
			actionReportTO.setStaffId(String.valueOf(obj[3]));
			actionReportTO.setName(String.valueOf(obj[4])+" "+String.valueOf(obj[5])+" "+String.valueOf(obj[6]));
								
			try {
				dateEnd=dateFormat.parse(String.valueOf(obj[7]));
				dateStart=dateFormat.parse(String.valueOf(obj[8]));
			} catch (ParseException e) {
				log.error(e.getMessage());
			}					
			actionReportTO.setDaysOfAbsent(String.valueOf(CommonUtil.getDaysDiff(dateStart, dateEnd)));
			actionReportTO.setDaysOfApproved(String.valueOf(CommonUtil.getDaysDiff(dateStart, dateEnd)));
			actionReportTO.setSizeOfActionReports(objList.size());
			
			actionReportTOList.add(actionReportTO);	
		}
		return actionReportTOList;
	}
}
