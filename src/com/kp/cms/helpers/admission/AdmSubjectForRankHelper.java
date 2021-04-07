package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.AdmSubjectForRank;
import com.kp.cms.forms.admission.AdmSubjectForRankForm;
import com.kp.cms.to.admission.AdmSubjectForRankTo;

public class AdmSubjectForRankHelper {

	public static List<AdmSubjectForRankTo> convertBotoTo
	                                  (List<AdmSubjectForRank> subjectlist) {

		List<AdmSubjectForRankTo> subjecttolist = new ArrayList<AdmSubjectForRankTo>();
		Iterator<AdmSubjectForRank> iterator = subjectlist.iterator();
		if(subjectlist != null){
		while(iterator.hasNext()){
			AdmSubjectForRankTo subjectto = new AdmSubjectForRankTo();
			AdmSubjectForRank subject = iterator.next();
			subjectto.setGroupname(subject.getGroupName());
			subjectto.setStream(subject.getStream());
			subjectto.setSubjectname(subject.getName());
			subjectto.setId(subject.getId());
			subjecttolist.add(subjectto);
			}
		
           }
		return subjecttolist;
	}

	public static AdmSubjectForRank convertTotoBo(
			AdmSubjectForRankForm admsbjctfrm, String mode) {
		
		AdmSubjectForRank admsbjctbo = new AdmSubjectForRank();
		   
		   admsbjctbo.setId(admsbjctfrm.getId());
		   admsbjctbo.setName(admsbjctfrm.getSubjectname());
		   admsbjctbo.setGroupName(admsbjctfrm.getGroupname());
		   admsbjctbo.setStream(admsbjctfrm.getStream());
		   admsbjctbo.setIsActive(true);
		   
		  
		  if (mode.equals("ADD")) {
			  
			  admsbjctbo.setCreatedBy(admsbjctfrm.getUserId());
			  admsbjctbo.setCreatedDate(new Date());
			  }
		  else if(mode.equals("EDIT")) {
			  admsbjctbo.setModifiedBy(admsbjctfrm.getUserId());
			  admsbjctbo.setLastModifiedDate(new Date());
			
		}
		return admsbjctbo;
		
	
	}
	
	

}
