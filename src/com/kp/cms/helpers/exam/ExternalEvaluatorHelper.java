package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.admin.State;
import com.kp.cms.helpers.exam.ExternalEvaluatorHelper;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.exam.ExternalEvaluatorTO;
import com.kp.cms.to.admin.StateTO;

public class ExternalEvaluatorHelper {
	
	private static final Log log = LogFactory.getLog(ExternalEvaluatorHelper.class);
	private static volatile ExternalEvaluatorHelper externalEvaluatorHelper = null;

	private ExternalEvaluatorHelper() {
	}
	
	public static ExternalEvaluatorHelper getInstance() {

		if (externalEvaluatorHelper == null) {
			externalEvaluatorHelper = new ExternalEvaluatorHelper();
		}
		return externalEvaluatorHelper;
	}
	
	public ExamValuators populateTOtoBO(ExternalEvaluatorTO externalEvaluatorTO) throws Exception{
		log.info("Inside populateTOtoBO of ExternalEvaluatorHelper");
		ExamValuators examValuators = null;
		
		if (externalEvaluatorTO != null) {
			examValuators = new ExamValuators();
			if(externalEvaluatorTO.getId()>0)
			{
				examValuators.setId(externalEvaluatorTO.getId());
			}
			if(externalEvaluatorTO.getName()!=null)
			examValuators.setName(externalEvaluatorTO.getName());
			if (externalEvaluatorTO.getAddressLine1() != null
					&& !externalEvaluatorTO.getAddressLine1().isEmpty()) {
				examValuators.setAddressLine1(externalEvaluatorTO.getAddressLine1());
			}
			if (externalEvaluatorTO.getAddressLine1() != null
					&& !externalEvaluatorTO.getAddressLine1().isEmpty()) {
				examValuators.setAddressLine2(externalEvaluatorTO.getAddressLine2());
			}
			if (externalEvaluatorTO.getCity() != null && !externalEvaluatorTO.getCity().isEmpty()) {
			examValuators.setCity(externalEvaluatorTO.getCity());
			}
			if (externalEvaluatorTO.getPin() != null && !externalEvaluatorTO.getPin().isEmpty()) {
			examValuators.setPin(externalEvaluatorTO.getPin());
			}
			if (externalEvaluatorTO.getEmail() != null && !externalEvaluatorTO.getEmail().isEmpty()) {
			examValuators.setEmail(externalEvaluatorTO.getEmail());
			}
			if (externalEvaluatorTO.getMobile() != null && !externalEvaluatorTO.getMobile().isEmpty()) {
			examValuators.setMobile(externalEvaluatorTO.getMobile());
			}
			if (externalEvaluatorTO.getPan() != null && !externalEvaluatorTO.getPan().isEmpty()) {
			examValuators.setPan(externalEvaluatorTO.getPan());
			}
			if (externalEvaluatorTO.getStateTO() != null) {
			State state = new State();
			state.setId(externalEvaluatorTO.getStateTO().getId());
			examValuators.setState(state);
			}
			if (externalEvaluatorTO.getCountryTO() != null) {
			Country country = new Country();
			country.setId(externalEvaluatorTO.getCountryTO().getId());
			examValuators.setCountry(country);
			}
			if (externalEvaluatorTO.getDepartment() != null && !externalEvaluatorTO.getDepartment().isEmpty()) {
			examValuators.setDepartment(externalEvaluatorTO.getDepartment());
			}
			if (externalEvaluatorTO.getBankAccNo() != null && !externalEvaluatorTO.getBankAccNo().isEmpty()) {
				examValuators.setBankAccNo(externalEvaluatorTO.getBankAccNo());
			}
			if (externalEvaluatorTO.getBankName() != null && !externalEvaluatorTO.getBankName().isEmpty()) {
				examValuators.setBankName(externalEvaluatorTO.getBankName());
			}
			if (externalEvaluatorTO.getBankBranch() != null && !externalEvaluatorTO.getBankBranch().isEmpty()) {
				examValuators.setBankBranch(externalEvaluatorTO.getBankBranch());
			}
			if (externalEvaluatorTO.getBankIfscCode() != null && !externalEvaluatorTO.getBankIfscCode().isEmpty()) {
				examValuators.setBankIfscCode(externalEvaluatorTO.getBankIfscCode());
			}
			if (externalEvaluatorTO.getCreatedBy() != null && !externalEvaluatorTO.getCreatedBy().isEmpty()) {
			examValuators.setCreatedBy(externalEvaluatorTO.getCreatedBy());
			}
			if (externalEvaluatorTO.getModifiedBy() != null && !externalEvaluatorTO.getModifiedBy().isEmpty()) {
			examValuators.setModifiedBy(externalEvaluatorTO.getModifiedBy());
			}
			examValuators.setCreatedDate(new Date());
			examValuators.setLastModifiedDate(new Date());
			examValuators.setIsActive(true);
		}
		log.info("Leaving from populateTOtoBO of ExternalEvaluatorHelper");
		return examValuators;
	}
	
	public List<ExternalEvaluatorTO> pupulateExamValuatorsBOtoTO(List<ExamValuators> externalList)throws Exception {
		log.info("Entering into pupulateExamValuatorsBOtoTO of ExternalEvaluatorHelper");
		ExternalEvaluatorTO externalEvaluatorTO = null;
		StateTO stateTO = null;
		CountryTO countryTO = null;

		List<ExternalEvaluatorTO> newExternList = new ArrayList<ExternalEvaluatorTO>();
		if (externalList != null && !externalList.isEmpty()) {
			Iterator<ExamValuators> iterator = externalList.iterator();
			while (iterator.hasNext()) {
				ExamValuators examValuators = iterator.next();
				externalEvaluatorTO = new ExternalEvaluatorTO();
				if (examValuators.getId() != 0
						&& examValuators.getName() != null) {
					externalEvaluatorTO.setId(examValuators.getId());
					externalEvaluatorTO.setName(examValuators.getName());
					externalEvaluatorTO.setAddressLine1(examValuators.getAddressLine1());
					externalEvaluatorTO.setAddressLine2(examValuators.getAddressLine2());
					externalEvaluatorTO.setCity(examValuators.getCity());
					externalEvaluatorTO.setPin(examValuators.getPin());
					externalEvaluatorTO.setEmail(examValuators.getEmail());
					externalEvaluatorTO.setMobile(examValuators.getMobile());
					externalEvaluatorTO.setPan(examValuators.getPan());
					externalEvaluatorTO.setDepartment(examValuators.getDepartment());
					externalEvaluatorTO.setBankAccNo(examValuators.getBankAccNo());
					externalEvaluatorTO.setBankName(examValuators.getBankName());
					externalEvaluatorTO.setBankBranch(examValuators.getBankBranch());
					externalEvaluatorTO.setBankIfscCode(examValuators.getBankIfscCode());

					stateTO = new StateTO();
					if (examValuators.getState() != null) {
					stateTO.setId(examValuators.getState().getId());
					stateTO.setName(examValuators.getState().getName());
					}
					externalEvaluatorTO.setStateTO(stateTO);
					countryTO = new CountryTO();
					if (examValuators.getCountry() != null) {
					countryTO.setId(examValuators.getCountry().getId());
					countryTO.setName(examValuators.getCountry().getName());
					}
					externalEvaluatorTO.setCountryTO(countryTO);					
					
					newExternList.add(externalEvaluatorTO);
				}
			}
		}
		log.info("Leaving from pupulateExamValuatorsBOtoTO of ExternalEvaluatorHelper");
		return newExternList;
	}
	
	public ExamValuators populateTotoBoUpdate(ExternalEvaluatorTO byTO) throws Exception{
		log.info("Entering into populateTotoBoUpdate of ExternalEvaluatorHelper");
		Country country = null;
		State state = null;
		if (byTO != null) {
			ExamValuators examValuators = new ExamValuators();
			examValuators.setId(byTO.getId());
			examValuators.setName(byTO.getName());
			if (byTO.getAddressLine1() != null && !byTO.getAddressLine1().isEmpty()) {
				examValuators.setAddressLine1(byTO.getAddressLine1());
			}
			if (byTO.getAddressLine2() != null && !byTO.getAddressLine2().isEmpty()) {
				examValuators.setAddressLine2(byTO.getAddressLine2());
			}
			if (byTO.getCity() != null && !byTO.getCity().isEmpty()) {
			examValuators.setCity(byTO.getCity());
			}
			if (byTO.getPin() != null && !byTO.getPin().isEmpty()) {
			examValuators.setPin(byTO.getPin());
			}
			if (byTO.getEmail() != null && !byTO.getEmail().isEmpty()) {
			examValuators.setEmail(byTO.getEmail());
			}
			if (byTO.getMobile() != null && !byTO.getMobile().isEmpty()) {
			examValuators.setMobile(byTO.getMobile());
			}
			if (byTO.getPan() != null && !byTO.getPan().isEmpty()) {
			examValuators.setPan(byTO.getPan());
			}
			if (byTO.getDepartment() != null && !byTO.getDepartment().isEmpty()) {
			examValuators.setDepartment(byTO.getDepartment());
			}
			if (byTO.getBankAccNo() != null && !byTO.getBankAccNo().isEmpty()) {
				examValuators.setBankAccNo(byTO.getBankAccNo());
			}
			if (byTO.getBankName() != null && !byTO.getBankName().isEmpty()) {
				examValuators.setBankName(byTO.getBankName());
			}
			if (byTO.getBankBranch() != null && !byTO.getBankBranch().isEmpty()) {
				examValuators.setBankBranch(byTO.getBankBranch());
			}
			if (byTO.getBankIfscCode() != null && !byTO.getBankIfscCode().isEmpty()) {
				examValuators.setBankIfscCode(byTO.getBankIfscCode());
			}
			if (byTO.getCountryTO() != null) {
			country = new Country();
			country.setId(byTO.getCountryTO().getId());
			examValuators.setCountry(country);
			}
			if (byTO.getStateTO() != null) {
			state = new State();
			state.setId(byTO.getStateTO().getId());
			examValuators.setState(state);
			}
			
			examValuators.setIsActive(true);
			examValuators.setLastModifiedDate(new Date());
			examValuators.setModifiedBy(byTO.getModifiedBy());
			if (examValuators != null) {
				return examValuators;
			}
		}
		log.info("Leaving from populateTotoBoUpdate of ExternalEvaluatorHelper");
		return null;
	}
	
	/**
	 * Used for edit Converts BO to TO
	 */

	public ExternalEvaluatorTO populateBotoToEdit(ExamValuators examValuators)throws Exception {
		log.info("Entering into populateBotoToEdit of ExternalEvaluatorHelper");
		ExternalEvaluatorTO externalEvaluatorTO = new ExternalEvaluatorTO();
		if (examValuators != null) {
			externalEvaluatorTO.setId(examValuators.getId());
			if (examValuators.getName() != null && !examValuators.getName().isEmpty()) {
			externalEvaluatorTO.setName(examValuators.getName());
			}
			if (examValuators.getAddressLine1() != null && !examValuators.getAddressLine1().isEmpty()) {
			externalEvaluatorTO.setAddressLine1(examValuators.getAddressLine1());
			}
			if (examValuators.getAddressLine2() != null && !examValuators.getAddressLine2().isEmpty()) {
				externalEvaluatorTO.setAddressLine2(examValuators.getAddressLine2());
			}
			if (examValuators.getCity() != null && !examValuators.getCity().isEmpty()) {
			externalEvaluatorTO.setCity(examValuators.getCity());
			}
			if (examValuators.getPin() != null && !examValuators.getPin().isEmpty()) {
			externalEvaluatorTO.setPin(examValuators.getPin());
			}
			if (examValuators.getEmail() != null && !examValuators.getEmail().isEmpty()) {
			externalEvaluatorTO.setEmail(examValuators.getEmail());
			}
			if (examValuators.getMobile() != null && !examValuators.getMobile().isEmpty()) {
			externalEvaluatorTO.setMobile(examValuators.getMobile());
			}
			if (examValuators.getPan() != null && !examValuators.getPan().isEmpty()) {
			externalEvaluatorTO.setPan(examValuators.getPan());
			}
			if (examValuators.getDepartment() != null && !examValuators.getDepartment().isEmpty()) {
			externalEvaluatorTO.setDepartment(examValuators.getDepartment());
			}
			if (examValuators.getBankAccNo() != null && !examValuators.getBankAccNo().isEmpty()) {
				externalEvaluatorTO.setBankAccNo(examValuators.getBankAccNo());
			}
			if (examValuators.getBankName() != null && !examValuators.getBankName().isEmpty()) {
				externalEvaluatorTO.setBankName(examValuators.getBankName());
			}
			if (examValuators.getBankBranch() != null && !examValuators.getBankBranch().isEmpty()) {
				externalEvaluatorTO.setBankBranch(examValuators.getBankBranch());
			}
			if (examValuators.getBankIfscCode() != null && !examValuators.getBankIfscCode().isEmpty()) {
				externalEvaluatorTO.setBankIfscCode(examValuators.getBankIfscCode());
			}

			CountryTO countryTO = new CountryTO();
			if(examValuators.getCountry()!=null){
			if (examValuators.getCountry().getName() != null	&& !examValuators.getCountry().getName().isEmpty()
				&& examValuators.getCountry().getId() != 0) {
				countryTO.setName(examValuators.getCountry().getName());
				countryTO.setId(examValuators.getCountry().getId());
				externalEvaluatorTO.setCountryTO(countryTO);
			}
			}
			StateTO stateTO = new StateTO();
			if(examValuators.getState()!=null){
			if (examValuators.getState().getName() != null
				&& !examValuators.getState().getName().isEmpty()
				&& examValuators.getCountry().getId() != 0) {
				stateTO.setName(examValuators.getState().getName());
				stateTO.setId(examValuators.getState().getId());
				externalEvaluatorTO.setStateTO(stateTO);
			}
			}
			
			
		}
		if (externalEvaluatorTO != null) {
			return externalEvaluatorTO;
		}
		log.info("Leaving from populateBotoToEdit of ExternalEvaluatorHelper");
		return null;
	}
	
	/*public String splitStringSize(String value, int limit) throws Exception{
		log.info("Start of splitStringSize of ExternalEvaluatorHelper");
		StringBuffer buffer = new StringBuffer();
		String appendedvalue = "";
		int length = value.length();
		String[] temp = new String[length];
		int begindex = 0, endindex = limit;
		int count = 0;
		while (true) {
			if (endindex < length) {
				temp[count] = value.substring(begindex, endindex);
				begindex = begindex + limit;
				endindex = endindex + limit;
				appendedvalue = buffer.append(temp[count]).append(" ").toString();
			} else {
				if (count == 0)
				temp[count] = value.substring(0, length);
				temp[count] = value.substring(begindex, length);
				appendedvalue = buffer.append(temp[count]).toString();
				break;
			}
			count++;
		}
		log.info("End of splitStringSize of ExternalEvaluatorHelper");
		return appendedvalue;
	}*/

}
