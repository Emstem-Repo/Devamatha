package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admission.AdmissionSubject;
import com.kp.cms.to.admission.AdmissionSubjectTo;

public class AdmissionSubjectHelper {

	private static volatile AdmissionSubjectHelper admissionSubjectHelper = null;
	public static AdmissionSubjectHelper getInstance(){
		if(admissionSubjectHelper == null){
			admissionSubjectHelper = new AdmissionSubjectHelper();
			return admissionSubjectHelper;
		}
		return admissionSubjectHelper;
	}
	public List<AdmissionSubject> convertToTOBo(List<AdmissionSubjectTo> admissionSubjectTo) {
		List<AdmissionSubject> admList=new ArrayList<AdmissionSubject>();
		if(admissionSubjectTo!=null){
			Iterator<AdmissionSubjectTo> iterator = admissionSubjectTo.iterator();
			while (iterator.hasNext()) {
				AdmissionSubjectTo admsubjectTo = (AdmissionSubjectTo) iterator .next();
				AdmissionSubject admsubBo = new AdmissionSubject();
				
				if(admsubjectTo.getClasses()!=null && !admsubjectTo.getClasses().isEmpty()){
					admsubBo.setClasses(admsubjectTo.getClasses());
				}
				if(admsubjectTo.getSubject1()!=null && !admsubjectTo.getSubject1().isEmpty()){
					admsubBo.setSubject1(admsubjectTo.getSubject1());
				}
				if(admsubjectTo.getSubcode1()!= null && !admsubjectTo.getSubcode1().isEmpty()){
					admsubBo.setSubcode1(admsubjectTo.getSubcode1());
				}
				if(admsubjectTo.getSubject2()!=null && !admsubjectTo.getSubject2().isEmpty()){
					admsubBo.setSubject2(admsubjectTo.getSubject2());
				}
				if(admsubjectTo.getSubcode2()!=null && !admsubjectTo.getSubcode2().isEmpty()){
					admsubBo.setSubcode2(admsubjectTo.getSubcode2());
				}
				if(admsubjectTo.getSubject3()!=null && !admsubjectTo.getSubject3().isEmpty()){
					admsubBo.setSubject3(admsubjectTo.getSubject3());
				}
				if(admsubjectTo.getSubcode3()!=null && !admsubjectTo.getSubcode3().isEmpty()){
					admsubBo.setSubcode3(admsubjectTo.getSubcode3());
				}
				if(admsubjectTo.getSubject4()!=null &&!admsubjectTo.getSubject4().isEmpty()){
					admsubBo.setSubject4(admsubjectTo.getSubject4());
				}
				if(admsubjectTo.getSubcode4()!=null && !admsubjectTo.getSubcode4().isEmpty()){
					admsubBo.setSubcode4(admsubjectTo.getSubcode4());
				}
				if(admsubjectTo.getSubject5()!=null && !admsubjectTo.getSubject5().isEmpty()){
					admsubBo.setSubject5(admsubjectTo.getSubject5());
				}
				if(admsubjectTo.getSubcode5()!=null && !admsubjectTo.getSubcode5().isEmpty()){
					admsubBo.setSubcode5(admsubjectTo.getSubcode5());
				}
				if(admsubjectTo.getAcademicYear()!=null && !admsubjectTo.getAcademicYear().isEmpty()){
					admsubBo.setAcademicYear(admsubjectTo.getAcademicYear());
				}
				admList.add(admsubBo);
			}
		}
		return admList;
	}
	}
