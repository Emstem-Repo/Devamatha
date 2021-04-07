package com.kp.cms.helpers.admission;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admission.AdmSelectionSettings;
import com.kp.cms.bo.admission.AdmSelectionSettingsDetails;
import com.kp.cms.forms.admission.AdmSelectionProcessCJCForm;
import com.kp.cms.transactions.admission.IAdmSelectProcessCJCTransaction;
import com.kp.cms.transactionsimpl.admission.AdmSelectProcessCJCImpl;

public class AdmSelectionProcessCJCHelper {
	
	 public static volatile AdmSelectionProcessCJCHelper admSelectionProcessHelper = null;
	 
	 public static AdmSelectionProcessCJCHelper getInstance(){
		 if(admSelectionProcessHelper == null){
			 admSelectionProcessHelper = new AdmSelectionProcessCJCHelper();
			 return admSelectionProcessHelper;
		 }
		 return admSelectionProcessHelper;
	 }
	 
	 IAdmSelectProcessCJCTransaction transaction = AdmSelectProcessCJCImpl.getInstance();
	 
	 public List<AdmAppln> convertBOsToTOs(List<AdmAppln> applnList,AdmSelectionProcessCJCForm admSelProcessCJCForm) throws Exception {
		 List<AdmAppln> applnBo= new ArrayList<AdmAppln>();	
		 List<AdmSelectionSettings> admSettingsList=transaction.getAdmSelectionSettings(admSelProcessCJCForm);
		 
		 if(admSettingsList!=null && !admSettingsList.isEmpty())
			{
				Iterator<AdmSelectionSettings> itrSet=admSettingsList.iterator();
				while (itrSet.hasNext()) {
					AdmSelectionSettings admSet= (AdmSelectionSettings) itrSet.next();
					Iterator<AdmSelectionSettingsDetails> itrSetDetails=admSet.getAdmSelectionSettingsDetails().iterator();
					while (itrSetDetails.hasNext()) {
						AdmSelectionSettingsDetails admSetDetails= (AdmSelectionSettingsDetails) itrSetDetails.next();
						if(admSetDetails.getCutOffPercentage()!=null && !admSetDetails.getCutOffPercentage().equals(0)){
							Map<Integer, String> map=new HashMap<Integer, String>();
						
							Iterator<AdmAppln> itr=applnList.iterator();
							while (itr.hasNext()) {
								AdmAppln appln= (AdmAppln) itr.next();
								/*if(!map.containsKey(appln.getId())){
								map.put(appln.getId(),String.valueOf(appln.getApplnNo()));*/
								BigDecimal percentage=null;
								AdmAppln app=new AdmAppln();
								if(appln!= null){
									Set<EdnQualification> ednQualificationSet = appln.getPersonalData().getEdnQualifications();
									Iterator<EdnQualification> eduItr = ednQualificationSet.iterator();
									while (eduItr.hasNext()) {
										EdnQualification ednQualification = (EdnQualification) eduItr.next();
										BigDecimal MarksObtained=new BigDecimal(ednQualification.getMarksObtained().intValue());
										BigDecimal TotalObtained=new BigDecimal(ednQualification.getTotalMarks().intValue());
										percentage=MarksObtained.divide(TotalObtained);
									}
									if(admSetDetails.getCutOffPercentage()==percentage){
										app.setIsBypassed(true);
										app.setIsSelected(true);
										app.setIsFinalMeritApproved(true);
										app.setFinalMeritListApproveDate(new Date());
										app.setAdmStatus(null);
										app.setCreatedBy(admSelProcessCJCForm.getUserId());
										app.setCreatedDate(new Date());
										app.setLastModifiedDate(new Date());
										app.setModifiedBy(admSelProcessCJCForm.getUserId());
										
									Set<AdmapplnAdditionalInfo> addInfo=new HashSet<AdmapplnAdditionalInfo>();
										Iterator<AdmAppln> itrr=applnList.iterator();
									  while (itrr.hasNext()) {
										  AdmAppln objt= (AdmAppln) itrr.next();
										 if(appln.getId()==objt.getId()){
										   AdmapplnAdditionalInfo details = new AdmapplnAdditionalInfo();
										   	  details.setAdmissionScheduledDate(admSet.getDate());
										   	  details.setAdmissionScheduledTime(admSet.getTime());
											  details.setListName(admSelProcessCJCForm.getListName());
											  details.setCreatedBy(admSelProcessCJCForm.getUserId());
											  details.setCreatedDate(new Date());
											  details.setLastModifiedDate(new Date());
											  details.setModifiedBy(admSelProcessCJCForm.getUserId());
											  addInfo.add(details);
										  }
										}
									  app.setAdmapplnAdditionalInfo(addInfo);
									  applnBo.add(app);
									 }
									}
						    	}
							}
					}
			}
			}
				return applnBo;
		 }
	 
	 public boolean checkFoDelete(List<AdmAppln> previousData) {
			boolean flag=false;
			if(previousData!=null && !previousData.isEmpty()){
				Iterator<AdmAppln> itrr=previousData.iterator();
				while (itrr.hasNext()) {
					AdmAppln appln=(AdmAppln) itrr.next();
					Iterator<AdmapplnAdditionalInfo> itr=appln.getAdmapplnAdditionalInfo().iterator();
					while (itr.hasNext()) {
						AdmapplnAdditionalInfo add = (AdmapplnAdditionalInfo) itr.next();
						/*if(add.getgetIsApplied()!=null && add.getIsApplied()){
							flag=true;
						}*/
					}
				}
			}
			return flag;
		}
}
