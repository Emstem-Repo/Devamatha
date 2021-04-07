package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.forms.reports.ApplicationReceivedForm;
import com.kp.cms.to.reports.ApplicationReceivedTO;

public class ApplicationReceivedHelper {
	private static final Log log = LogFactory.getLog(ApplicationReceivedHelper.class);
	public static volatile ApplicationReceivedHelper applicationReceivedHelper = null;

	private ApplicationReceivedHelper(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static ApplicationReceivedHelper getInstance() {
		if (applicationReceivedHelper == null) {
			applicationReceivedHelper = new ApplicationReceivedHelper();
		}
		return applicationReceivedHelper;
	}
	
	/**
	 * 
	 * @param receivedForm
	 * @returns the query for getting online/offline application range
	 * @throws Exception
	 */
	public String createSeacrQuery(ApplicationReceivedForm receivedForm)throws Exception{
		log.info("Entering into ApplicationReceivedForm of ApplicationReceivedHelper");
		String searchQuery ="from CourseApplicationNumber a where a.isActive=1 and a.applicationNumber.isActive=1 and a.applicationNumber.year = " + Integer.parseInt(receivedForm.getYear());
		if(receivedForm.getCourseId().length()>0){
			searchQuery = searchQuery + " and a.course.id = " + Integer.parseInt(receivedForm.getCourseId());
		}
		log.info("Leaving into ApplicationReceivedForm of ApplicationReceivedHelper");
		return searchQuery;
	}
	
	/**
	 * 
	 * @param appNoRangeList
	 * @param admApplnList
	 * @returns the list online/offline
	 * Populates TO properties from BO
	 */

	public List<ApplicationReceivedTO> getReceivedApplications(
			List<Integer> appNoRangeList, List<AdmAppln> admApplnList) {
		log.info("Entering into getReceivedApplications of ApplicationReceivedHelper");
		List<ApplicationReceivedTO> finalApplicationList = new ArrayList<ApplicationReceivedTO>();
		ApplicationReceivedTO appReceivedTO = null;
		if(admApplnList!=null && appNoRangeList!=null){
			Iterator<AdmAppln> iterator = admApplnList.iterator();
			while (iterator.hasNext()) {
				AdmAppln admAppln = iterator.next();
				StringBuffer buffer = new StringBuffer();
				//Checks the application in online/offline range
				if(appNoRangeList.contains(admAppln.getApplnNo())){
					appReceivedTO = new ApplicationReceivedTO();
					appReceivedTO.setApplicationNo(String.valueOf(admAppln.getApplnNo()));
					if(admAppln.getPersonalData()!=null){
						if(admAppln.getPersonalData().getFirstName()!=null){
							buffer.append(admAppln.getPersonalData().getFirstName());
						}
						if(admAppln.getPersonalData().getMiddleName()!=null){
							buffer.append(" " + admAppln.getPersonalData().getMiddleName());
						}
						if(admAppln.getPersonalData().getLastName()!=null){
							buffer.append(" " + admAppln.getPersonalData().getLastName());
						}
					}
					appReceivedTO.setName(buffer.toString());
					if(admAppln.getCandidatePreferences()!=null){
						Iterator<CandidatePreference> candIterator = admAppln.getCandidatePreferences().iterator();
						while (candIterator.hasNext()) {
							CandidatePreference candidatePreference = candIterator.next();
							if(candidatePreference.getCourse()!=null){
								if(candidatePreference.getPrefNo()!=null && candidatePreference.getPrefNo()==1){
									appReceivedTO.setFirstPreference(candidatePreference.getCourse().getName());
								}
								if(candidatePreference.getPrefNo()!=null && candidatePreference.getPrefNo()==2){
									appReceivedTO.setSecondPreference(candidatePreference.getCourse().getName());
								}
								if(candidatePreference.getPrefNo()!=null && candidatePreference.getPrefNo()==3){
									appReceivedTO.setThirdPreference(candidatePreference.getCourse().getName());
								}
							}
						}
					}
					finalApplicationList.add(appReceivedTO);
				}
			}
		}
		log.info("Leaving into getReceivedApplications of ApplicationReceivedHelper");
		return finalApplicationList;
	}

	/**
	 * 
	 * @param receivedForm
	 * @returns query for admAppln based on year and course
	 */
	public String createSeacrQueryForAdmAppln(
			ApplicationReceivedForm receivedForm) {
		log.info("Entering into createSeacrQueryForAdmAppln of ApplicationReceivedHelper");
		String searchQuery = "from AdmAppln a where a.appliedYear = " + Integer.valueOf(receivedForm.getYear());
		if(receivedForm.getCourseId().length()>0){
			searchQuery = searchQuery + " and a.course.id = " + Integer.valueOf(receivedForm.getCourseId());
		}
		searchQuery = searchQuery + " order by a.applnNo";
		log.info("Leaving into createSeacrQueryForAdmAppln of ApplicationReceivedHelper");
		return searchQuery;
	}
}
