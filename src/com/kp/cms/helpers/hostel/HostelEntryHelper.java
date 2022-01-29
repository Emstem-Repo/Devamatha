package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.State;
import com.kp.cms.forms.hostel.HostelEntryForm;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.hostel.HostelTO;

public class HostelEntryHelper {
	public static final Log log = LogFactory.getLog(HostelEntryHelper.class);
	public static HostelEntryHelper hostelEntryHelper;
	public static HostelEntryHelper getInstance(){
		if(hostelEntryHelper == null){
			HostelEntryHelper hostelEntryHelper = new HostelEntryHelper();
			return hostelEntryHelper; 
		}
		return hostelEntryHelper; 
	}
	
	/**
	 * copying BO values to TO
	 * @param hostelTOList
	 * @return
	 */
	public List<HostelTO> copyHostelBosToTos(List<HlHostel> hostelList) {
		log.debug("inside copyHostelBosToTos");
		List<HostelTO> hostelTOList = new ArrayList<HostelTO>();
		Iterator<HlHostel> iterator = hostelList.iterator();
		HlHostel hlHostel;
		HostelTO hostelTO;
		CountryTO countryTO;
		StateTO stateTO;
		while (iterator.hasNext()) {
			hostelTO = new HostelTO();
			countryTO = new CountryTO();
			stateTO = new StateTO();
			hlHostel = (HlHostel) iterator.next();
			hostelTO.setName(hlHostel.getName());
			hostelTO.setAddressLine1(hlHostel.getAddressLine1());
			if(hlHostel.getAddressLine2()!= null){
				hostelTO.setAddressLine2(hlHostel.getAddressLine2());
			}
			if(hlHostel.getCity()!= null){
				hostelTO.setCity(hlHostel.getCity());
			}
			if(hlHostel.getCountry()!= null){
				countryTO.setId(hlHostel.getCountry().getId());
				if(hlHostel.getCountry().getName()!= null){
					countryTO.setName(hlHostel.getCountry().getName());
				}
				hostelTO.setCountryTO(countryTO);
			}
			if(hlHostel.getEmail()!= null){
				hostelTO.setEmail(hlHostel.getEmail());
			}
			if(hlHostel.getFaxNo()!= null){
				hostelTO.setFaxNo(hlHostel.getFaxNo());
			}
			hostelTO.setId(hlHostel.getId());
			if(hlHostel.getGender()!= null){
				hostelTO.setGender(hlHostel.getGender());
			}
			if(hlHostel.getNoOfFloors()!= null){
				hostelTO.setNoOfFloors(hlHostel.getNoOfFloors());
			}
			if(hlHostel.getPhone()!= null){
				hostelTO.setPhone(hlHostel.getPhone());
			}
			if(hlHostel.getPrimaryContactDesignation()!= null){
				hostelTO.setPrimaryContactDesignation(hlHostel.getPrimaryContactDesignation());
			}
			if(hlHostel.getPrimaryContactEmail()!= null){
				hostelTO.setPrimaryContactEmail(hlHostel.getPrimaryContactEmail());
			}
			if(hlHostel.getPrimaryContactMobile()!= null){
				hostelTO.setPrimaryContactMobile(hlHostel.getPrimaryContactMobile());
			}
			if(hlHostel.getPrimaryContactName()!= null){
				hostelTO.setPrimaryContactName(hlHostel.getPrimaryContactName());
			}
			if(hlHostel.getPrimaryContactPhone()!= null){
				hostelTO.setPrimaryContactPhone(hlHostel.getPrimaryContactPhone());
			}
			if(hlHostel.getSecondaryContactDesignation()!= null){
				hostelTO.setSecContactDesignation(hlHostel.getSecondaryContactDesignation());
			}
			if(hlHostel.getSecondaryContactEmail()!= null){
				hostelTO.setSecContactEmail(hlHostel.getSecondaryContactEmail());
			}
			if(hlHostel.getSecondaryContactMobile()!=null){
				hostelTO.setSecContactMobile(hlHostel.getSecondaryContactMobile());
			}
			if(hlHostel.getSecondaryContactName()!= null){
				hostelTO.setSecContactName(hlHostel.getSecondaryContactName());
			}
			if(hlHostel.getSecondaryContactPhone()!= null){
				hostelTO.setSecContactPhone(hlHostel.getSecondaryContactPhone());
			}
			if(hlHostel.getState()!= null ){
				stateTO.setId(hlHostel.getState().getId());
				if(hlHostel.getState().getName()!= null){
					stateTO.setName(hlHostel.getState().getName());
				}
				hostelTO.setStateTO(stateTO);
			}
			if(hlHostel.getStateOthers()!= null){
				hostelTO.setStateOthers(hlHostel.getStateOthers());
			}
			if(hlHostel.getZipCode()!= null){
				hostelTO.setZipCode(hlHostel.getZipCode());
			}
			if(hlHostel.getGender()!= null){
				hostelTO.setGender(hlHostel.getGender());
			}
			if(hlHostel.getNoOfFloors()!= null){
				hostelTO.setNoOfFloors(hlHostel.getNoOfFloors());
			}
			
			hostelTO.setFileName(hlHostel.getFileName());
			hostelTO.setContentType(hlHostel.getContentType());
			hostelTOList.add(hostelTO);
		}
		log.debug("leaving copyDisciplinaryTypeBosToTos");
		return hostelTOList;
	}
	
	/**
	 * copying form values to BO
	 * @param hlForm
	 * @return
	 * @throws Exception
	 */
	public HlHostel copyDataFromFormToBO(HostelEntryForm hlForm) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		HlHostel hlHostel = new HlHostel();
		if(hlForm.getId() != 0) {
			hlHostel.setId(hlForm.getId());
		}
		hlHostel.setAddressLine1(hlForm.getAddressLine1());
		hlHostel.setAddressLine2(hlForm.getAddressLine2());
		hlHostel.setCity(hlForm.getCity());
		if(hlForm.getCountryId()!= null && !hlForm.getCountryId().trim().isEmpty() ){
			Country country = new Country();
			country.setId(Integer.parseInt(hlForm.getCountryId()));
			hlHostel.setCountry(country);
		}
		hlHostel.setEmail(hlForm.getEmail());
		hlHostel.setFaxNo(hlForm.getFaxNo());
		if(hlForm.getGender()!= null && !hlForm.getGender().trim().isEmpty()){
			if("MALE".equalsIgnoreCase(hlForm.getGender())){
				hlHostel.setGender("MALE");
			}
			else
			{
				hlHostel.setGender("FEMALE");
			}
		}
		hlHostel.setName(hlForm.getName());
		hlHostel.setPhone(hlForm.getPhone());
		hlHostel.setPrimaryContactDesignation(hlForm.getPrimaryContactDesignation());
		hlHostel.setPrimaryContactEmail(hlForm.getPrimaryContactEmail());
		hlHostel.setPrimaryContactMobile(hlForm.getPrimaryContactMobile());
		hlHostel.setPrimaryContactName(hlForm.getPrimaryContactName());
		hlHostel.setPrimaryContactPhone(hlForm.getPrimaryContactPhone());
		hlHostel.setSecondaryContactDesignation(hlForm.getSecContactDesignation());
		hlHostel.setSecondaryContactEmail(hlForm.getSecContactEmail());
		hlHostel.setSecondaryContactMobile(hlForm.getSecContactMobile());
		hlHostel.setSecondaryContactName(hlForm.getSecContactName());
		hlHostel.setSecondaryContactPhone(hlForm.getSecContactPhone());
		
		if(hlForm.getStateId().equalsIgnoreCase("other")){
			hlHostel.setStateOthers(hlForm.getStateOthers());
		}
		else{
			if(hlForm.getStateId()!=  null){
				State state = new State();
				state.setId(Integer.parseInt(hlForm.getStateId()));
				hlHostel.setState(state);
			}
		}
		if(hlForm.getZipCode()!= null && !hlForm.getZipCode().trim().isEmpty()){
			hlHostel.setZipCode(Integer.parseInt(hlForm.getZipCode()));
		}
		if(hlForm.getNoOfFloors()!= null && !hlForm.getNoOfFloors().trim().isEmpty()){
			hlHostel.setNoOfFloors(Integer.parseInt(hlForm.getNoOfFloors()));
		}
		if(hlForm.getThefile()!=null && hlForm.getThefile().getFileName()!=null){
			hlHostel.setFileName(hlForm.getThefile().getFileName());
		}
		if(hlForm.getThefile()!=null && hlForm.getThefile().getContentType()!=null){
			hlHostel.setContentType(hlForm.getThefile().getContentType());
		}
		
		
		if(hlForm.getThefile()!=null && hlForm.getThefile().getFileData()!=null
				&& hlForm.getThefile().getFileName()!=null && !hlForm.getThefile().getFileName().isEmpty() &&
				hlForm.getThefile().getContentType()!=null && !hlForm.getThefile().getContentType().isEmpty()){
			
			hlHostel.setFileName(hlForm.getThefile().getFileName());
			hlHostel.setContentType(hlForm.getThefile().getContentType());
		}
		else
		{
			hlHostel.setFileName(hlForm.getFileName());
			hlHostel.setContentType(hlForm.getContentType());
		}
		hlHostel.setIsActive(true);
		hlHostel.setCreatedBy(hlForm.getUserId());
		hlHostel.setModifiedBy(hlForm.getUserId());
		hlHostel.setCreatedDate(new Date());
//		hlHostel.setLastModifiedDate(new Date());
		log.debug("leaving copyDataFromFormToBO");
		return hlHostel;
		}
	
}
