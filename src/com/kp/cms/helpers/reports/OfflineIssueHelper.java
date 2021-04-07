package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.forms.reports.OfflineIssueForm;
import com.kp.cms.to.admission.OfflineDetailsTO;
import com.kp.cms.to.reports.OfflineIssueTO;
import com.kp.cms.utilities.CommonUtil;

public class OfflineIssueHelper {
	private static final Log log = LogFactory.getLog(OfflineIssueHelper.class);
	public static volatile OfflineIssueHelper offlineIssueHelper = null;
	private OfflineIssueHelper()
	{
	}
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */

	public static OfflineIssueHelper getInstance() {
		if (offlineIssueHelper == null) {
			offlineIssueHelper = new OfflineIssueHelper();
			return offlineIssueHelper;
		}
		return offlineIssueHelper;
	}
	/**
	 * creating query
	 * @param offlineIssueForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQuery(OfflineIssueForm offlineIssueForm) throws Exception{
		String searchCriteria="from OfflineDetails offlineDetails";
		if(offlineIssueForm.getYear()!=null&& !StringUtils.isEmpty(offlineIssueForm.getYear())){
			searchCriteria=searchCriteria+" where offlineDetails.isActive=1 and offlineDetails.year="+offlineIssueForm.getYear();
		}
		if(offlineIssueForm.getCourseId()!=null&& !StringUtils.isEmpty(offlineIssueForm.getCourseId())){
			searchCriteria=searchCriteria+"  and offlineDetails.course.id="+offlineIssueForm.getCourseId();
		}
		return searchCriteria;
	}
	/**
	 * Used in view
	 * Populates all offline details Bo to TO
	 */
	public List<OfflineIssueTO> populateOfflineDetailsBOtoTO(List<OfflineDetails> offlineDetailsBOList)throws Exception
	{
		log.info("Entering into OfflineDetailsHelper of populateOfflineDetailsBOtoTO");
		OfflineDetails offlineDetails;
		OfflineIssueTO offlineIssueTO;
		List<OfflineIssueTO> offlineTOList=new ArrayList<OfflineIssueTO>();
		Iterator<OfflineDetails>iterator= offlineDetailsBOList.iterator();
		while (iterator.hasNext()) {
			offlineDetails = iterator.next();
			offlineIssueTO = new OfflineIssueTO();
			offlineIssueTO.setId(offlineDetails.getId());
			if(offlineDetails.getCourse().getId()!=0){
				offlineIssueTO.setCourseId(offlineDetails.getCourse().getId());
				offlineIssueTO.setCourseName(offlineDetails.getCourse().getName());
			}
			offlineIssueTO.setApplicationNo(String.valueOf(offlineDetails.getApplnNo()));
			offlineIssueTO.setReceiptNo(String.valueOf(offlineDetails.getReceiptNo()));
			offlineIssueTO.setAcademicYear(offlineDetails.getYear());
			offlineIssueTO.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(offlineDetails.getDate()), "dd-MMM-yyyy","dd/MM/yyyy"));
			offlineIssueTO.setAmount(String.valueOf(offlineDetails.getAmount()));
				offlineTOList.add(offlineIssueTO);
		}
		log.info("Leaving from OfflineDetailsHelper of populateOfflineDetailsBOtoTO");
		return offlineTOList;		
	}
}
