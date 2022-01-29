package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.to.admission.PublishAdmitCardTO;

public class PublishAdmitCardHelper {

	public static List<PublishAdmitCardTO> convertBoToTo(List<AdmAppln> adminApplnBo) {
		List <PublishAdmitCardTO>  publishAdmitCardTO  = new ArrayList<PublishAdmitCardTO>();
		
		if(adminApplnBo !=null) {
			Iterator<AdmAppln> iterator = adminApplnBo.iterator();
			while (iterator.hasNext()) {
				PublishAdmitCardTO publishCardTO = new PublishAdmitCardTO();
				AdmAppln admAppln = (AdmAppln) iterator.next();
				publishCardTO.setApplicantName(admAppln.getPersonalData().getFirstName());
				publishCardTO.setApplicationNumber(String.valueOf(admAppln.getApplnNo()));
				publishAdmitCardTO.add(publishCardTO);
			}
			
		}
		
		return publishAdmitCardTO;
		
	}
}
