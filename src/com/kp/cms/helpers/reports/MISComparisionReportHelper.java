package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.BankMis;
import com.kp.cms.to.reports.MISComparisionReportTO;

public class MISComparisionReportHelper {
	
	private static final Log log = LogFactory.getLog(MISComparisionReportHelper.class);
	
	/**
	 * 
	 * @param detailsFromApplication
	 * @param detailsFromBank
	 * @return
	 */
	public List<MISComparisionReportTO> getDiscrepancyList(List<AdmAppln> detailsFromApplication, List<BankMis> detailsFromBank) {
			
		log.info("entered getDiscrepancyList..");
		List<MISComparisionReportTO> discrepancyList = new ArrayList<MISComparisionReportTO>();
		Map<String, MISComparisionReportTO> discrepancyMap = new LinkedHashMap<String, MISComparisionReportTO>();
		Map<String, BankMis> bankMisMap = new LinkedHashMap<String, BankMis>();
		
		if(detailsFromBank != null && detailsFromBank.size()>0){
			Iterator<BankMis> bankDetailsItr = detailsFromBank.iterator();
			
			while (bankDetailsItr.hasNext()) {
				BankMis bankDetails = (BankMis) bankDetailsItr.next();
				
				bankMisMap.put(bankDetails.getRefNo(), bankDetails);
			}
		}
		
		if(detailsFromApplication !=null){
			Iterator<AdmAppln> applnDetailsItr = detailsFromApplication.iterator();
			
			while (applnDetailsItr.hasNext()) {

				AdmAppln applicantDetails = (AdmAppln) applnDetailsItr.next();
				
				if(detailsFromBank != null && detailsFromBank.size()>0){
					
					if(bankMisMap.containsKey(applicantDetails.getChallanRefNo())){
						BankMis bankDetails = bankMisMap.get(applicantDetails.getChallanRefNo());
						
						if((!bankDetails.getJournalNo().equals(applicantDetails.getJournalNo())) || (!bankDetails.getRefNo().equals(applicantDetails.getChallanRefNo())) || (!bankDetails.getTxnBranch().equals(applicantDetails.getBankBranch())) || (bankDetails.getTxnDate().compareTo(applicantDetails.getDate())!= 0)){
							setDiscrepancyToMap(applicantDetails, discrepancyMap);							
						}
					}else{
						setDiscrepancyToMap(applicantDetails, discrepancyMap);
					}
				}else{
					setDiscrepancyToMap(applicantDetails, discrepancyMap);
				}
			}
		}
		discrepancyList.addAll(discrepancyMap.values());
		log.info("exit getDiscrepancyList..");
		return discrepancyList;
	}
	
	/**
	 * 
	 * @param applicantDetails
	 * @param discrepancyMap
	 */
	public void setDiscrepancyToMap(AdmAppln applicantDetails, Map<String, MISComparisionReportTO> discrepancyMap){
		
		MISComparisionReportTO comparisionTO = new MISComparisionReportTO();
		
		if(applicantDetails.getPersonalData()!=null){
			if(applicantDetails.getPersonalData().getFirstName()!=null && applicantDetails.getPersonalData().getMiddleName()!=null && applicantDetails.getPersonalData().getLastName()!=null && !applicantDetails.getPersonalData().getFirstName().isEmpty() && !applicantDetails.getPersonalData().getMiddleName().isEmpty() && !applicantDetails.getPersonalData().getLastName().isEmpty()){
				comparisionTO.setApplicantName(applicantDetails.getPersonalData().getFirstName()+" "+applicantDetails.getPersonalData().getMiddleName()+" "+applicantDetails.getPersonalData().getLastName());
			}else if(applicantDetails.getPersonalData().getFirstName()!=null && applicantDetails.getPersonalData().getMiddleName()!=null && !applicantDetails.getPersonalData().getFirstName().isEmpty() && !applicantDetails.getPersonalData().getMiddleName().isEmpty()){
				comparisionTO.setApplicantName(applicantDetails.getPersonalData().getFirstName()+" "+applicantDetails.getPersonalData().getMiddleName());
			}else if(applicantDetails.getPersonalData().getFirstName()!=null && !applicantDetails.getPersonalData().getFirstName().isEmpty()){
				comparisionTO.setApplicantName(applicantDetails.getPersonalData().getFirstName());
			}
			if(applicantDetails.getPersonalData().getEmail()!=null && !applicantDetails.getPersonalData().getEmail().isEmpty()){
				comparisionTO.setEmail(applicantDetails.getPersonalData().getEmail());
			}
			if(applicantDetails.getPersonalData().getPhNo1()!=null && applicantDetails.getPersonalData().getPhNo2()!=null && applicantDetails.getPersonalData().getPhNo3()!=null && !applicantDetails.getPersonalData().getPhNo1().isEmpty() && !applicantDetails.getPersonalData().getPhNo2().isEmpty() && !applicantDetails.getPersonalData().getPhNo3().isEmpty()){
				comparisionTO.setTelephoneNo(applicantDetails.getPersonalData().getPhNo1()+" "+applicantDetails.getPersonalData().getPhNo2()+" "+applicantDetails.getPersonalData().getPhNo3());
			}else if(applicantDetails.getPersonalData().getPhNo1()!=null && applicantDetails.getPersonalData().getPhNo2()!=null && !applicantDetails.getPersonalData().getPhNo1().isEmpty() && !applicantDetails.getPersonalData().getPhNo2().isEmpty()){
				comparisionTO.setTelephoneNo(applicantDetails.getPersonalData().getPhNo1()+" "+applicantDetails.getPersonalData().getPhNo2());
			}else if(applicantDetails.getPersonalData().getPhNo1()!=null && !applicantDetails.getPersonalData().getPhNo1().isEmpty()){
				comparisionTO.setTelephoneNo(applicantDetails.getPersonalData().getPhNo1());
			}
			if(applicantDetails.getPersonalData().getMobileNo1()!=null && applicantDetails.getPersonalData().getMobileNo2()!=null && applicantDetails.getPersonalData().getMobileNo3()!=null && !applicantDetails.getPersonalData().getMobileNo1().isEmpty() && !applicantDetails.getPersonalData().getMobileNo2().isEmpty() && !applicantDetails.getPersonalData().getMobileNo3().isEmpty()){
				comparisionTO.setMobileNo(applicantDetails.getPersonalData().getMobileNo1()+" "+applicantDetails.getPersonalData().getMobileNo2()+" "+applicantDetails.getPersonalData().getMobileNo3());
			}else if(applicantDetails.getPersonalData().getMobileNo1()!=null && applicantDetails.getPersonalData().getMobileNo2()!=null && !applicantDetails.getPersonalData().getMobileNo1().isEmpty() && !applicantDetails.getPersonalData().getMobileNo2().isEmpty()){
				comparisionTO.setMobileNo(applicantDetails.getPersonalData().getMobileNo1()+" "+applicantDetails.getPersonalData().getMobileNo2());
			}else if(applicantDetails.getPersonalData().getMobileNo1()!=null && !applicantDetails.getPersonalData().getMobileNo1().isEmpty()){
				comparisionTO.setMobileNo(applicantDetails.getPersonalData().getMobileNo1());
			}
		}
		comparisionTO.setApplicationNo(String.valueOf(applicantDetails.getApplnNo()));
		if(applicantDetails.getCourseBySelectedCourseId()!=null && applicantDetails.getCourseBySelectedCourseId().getProgram()!=null){
			comparisionTO.setProgram(applicantDetails.getCourseBySelectedCourseId().getProgram().getName());
		}
		discrepancyMap.put(String.valueOf(applicantDetails.getApplnNo()), comparisionTO);
	}
}