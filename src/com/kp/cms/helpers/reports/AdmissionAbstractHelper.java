package com.kp.cms.helpers.reports;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.to.reports.AdmAbstractCatgMapTO;
import com.kp.cms.to.reports.AdmissionAbstractTO;

public class AdmissionAbstractHelper {

	public static final Log log = LogFactory.getLog(AdmissionAbstractHelper.class);
	public static volatile AdmissionAbstractHelper admissionAbstractHelper = null;
	public static AdmissionAbstractHelper getInstance() {
		if (admissionAbstractHelper == null) {
			admissionAbstractHelper = new AdmissionAbstractHelper();
			return admissionAbstractHelper;
		}
		return admissionAbstractHelper;
	}
	
	/**
	 * 
	 * @param admList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, AdmissionAbstractTO> copyBosToTO(List<Student> admList) throws Exception {
		log.debug("inside copyBosToTO");
		Iterator admItr = admList.iterator();
		AdmissionAbstractTO admAbstractTO;
		Map<Integer, AdmissionAbstractTO> admAbstractMap = new HashMap<Integer, AdmissionAbstractTO>();
		Map<Integer, AdmAbstractCatgMapTO> admAbstractCatgMap = new HashMap<Integer, AdmAbstractCatgMapTO>();
		AdmAbstractCatgMapTO admAbstractCatgMapTO;
			
		int boysCount = 0; 
		int girlsCount = 0;
		int karnatakaCount = 0;
		int nonKarnatakaCount = 0;
		int OtherThanIdia = 0;
		int total = 0;
		//calculating totals
		while(admItr.hasNext()){
			Object[] admAbstractDetails = (Object[]) admItr.next();
			admAbstractTO = new AdmissionAbstractTO();
			admAbstractTO.setCourseCode(admAbstractDetails[6].toString());
			admAbstractTO.setProgramCode(admAbstractDetails[7].toString());
			girlsCount = 0;
			boysCount = 0;
			karnatakaCount = 0;
			nonKarnatakaCount = 0;
			OtherThanIdia = 0;
			if (admAbstractMap.containsKey((Integer) admAbstractDetails[0])) {
				admAbstractTO = admAbstractMap.get((Integer)admAbstractDetails[0] );
				if(admAbstractDetails[5].toString().equalsIgnoreCase("male")){
					boysCount = admAbstractTO.getBoysCount();
					boysCount++;
					admAbstractTO.setBoysCount(boysCount);
				}
				else
				{
					girlsCount = admAbstractTO.getGirlsCount();
					girlsCount++;
					admAbstractTO.setGirlsCount(girlsCount);
				}
				total = admAbstractTO.getTotal();
				total++;
				admAbstractTO.setTotal(total);
				if(admAbstractDetails[4]!= null && admAbstractDetails[4].toString().equalsIgnoreCase("karnataka")
					&& (Integer)admAbstractDetails[10] == CMSConstants.KP_DEFAULT_NATIONALITY_ID){
					karnatakaCount = admAbstractTO.getKarStudents();
					karnatakaCount++;
					admAbstractTO.setKarStudents(karnatakaCount);
				}
				
				if(admAbstractDetails[4]!= null && !admAbstractDetails[4].toString().equalsIgnoreCase("karnataka")
					&& (Integer)admAbstractDetails[10] == CMSConstants.KP_DEFAULT_NATIONALITY_ID){
					nonKarnatakaCount = admAbstractTO.getOtherThanKar();
					nonKarnatakaCount++;
					admAbstractTO.setOtherThanKar(nonKarnatakaCount);
				}
				
				
				if(admAbstractDetails[10]!= null && !((Integer)admAbstractDetails[10] ==CMSConstants.KP_DEFAULT_NATIONALITY_ID)){
					OtherThanIdia = admAbstractTO.getOtherThanInd();
					OtherThanIdia ++;
					admAbstractTO.setOtherThanInd(OtherThanIdia);
				}
				int count = 0;
				if (admAbstractCatgMap.containsKey((Integer) admAbstractDetails[1])) {
					admAbstractCatgMapTO = admAbstractCatgMap.get((Integer)admAbstractDetails[1]);
						count = admAbstractCatgMapTO.getNoOfStudents();
						count++;
						admAbstractCatgMapTO.setNoOfStudents(count);
						admAbstractCatgMapTO.setCategoryName(admAbstractCatgMapTO.getCategoryName());
						admAbstractCatgMap.put((Integer)admAbstractDetails[1], admAbstractCatgMapTO);
						admAbstractTO.setCategoryMap(admAbstractCatgMap);
				}
				else
				{
					admAbstractCatgMapTO = new AdmAbstractCatgMapTO();
					admAbstractCatgMapTO.setCategoryName(admAbstractDetails[2].toString());
					admAbstractCatgMapTO.setNoOfStudents(1);
					admAbstractCatgMap.put((Integer)admAbstractDetails[1], admAbstractCatgMapTO);
					admAbstractTO.setCategoryMap(admAbstractCatgMap);
				}
				
			}
			else
			{
				admAbstractTO = new AdmissionAbstractTO();
				admAbstractTO.setCourseCode(admAbstractDetails[6].toString());
				
				admAbstractTO.setProgramCode(admAbstractDetails[7].toString());
				if(admAbstractDetails[5].toString().equalsIgnoreCase("male")){
					admAbstractTO.setBoysCount(1);
				}
				else
				{
					admAbstractTO.setGirlsCount(1);
				}
				if(admAbstractDetails[4]!= null && admAbstractDetails[4].toString().equalsIgnoreCase("karnataka")
						&& (Integer)admAbstractDetails[10] == CMSConstants.KP_DEFAULT_NATIONALITY_ID){
						admAbstractTO.setKarStudents(1);
				}
				if(admAbstractDetails[4]!= null && !admAbstractDetails[4].toString().equalsIgnoreCase("karnataka")
						&& (Integer)admAbstractDetails[10] == CMSConstants.KP_DEFAULT_NATIONALITY_ID){
						admAbstractTO.setOtherThanKar(1);
				}
				
				if(admAbstractDetails[10]!= null && !((Integer)admAbstractDetails[10] == CMSConstants.KP_DEFAULT_NATIONALITY_ID)){
					admAbstractTO.setOtherThanInd(1);
				}
				
				if(admAbstractDetails[2]!= null){
					admAbstractCatgMapTO = new AdmAbstractCatgMapTO();
					admAbstractCatgMap = new HashMap<Integer, AdmAbstractCatgMapTO>();
					admAbstractCatgMapTO.setCategoryName(admAbstractDetails[2].toString());
					admAbstractCatgMapTO.setNoOfStudents(1);
					admAbstractCatgMap.put((Integer)admAbstractDetails[1], admAbstractCatgMapTO);
					admAbstractTO.setCategoryMap(admAbstractCatgMap);
				}
				admAbstractTO.setTotal(1);
				
				admAbstractMap.put((Integer)admAbstractDetails[0], admAbstractTO);
			}
			
		}
		
		log.debug("exit copyBosToTO");
		return admAbstractMap;
	}
	
	
}
