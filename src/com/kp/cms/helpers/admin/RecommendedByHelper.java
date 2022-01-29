package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Recommendor;
import com.kp.cms.bo.admin.State;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.RecommendedByTO;
import com.kp.cms.to.admin.StateTO;

public class RecommendedByHelper {
	
	private static final Log log = LogFactory.getLog(RecommendedByHelper.class);
	private static volatile RecommendedByHelper recommendedByHelper = null;

	private RecommendedByHelper() {
	}

	public static RecommendedByHelper getInstance() {

		if (recommendedByHelper == null) {
			recommendedByHelper = new RecommendedByHelper();
		}
		return recommendedByHelper;
	}

	/**
	 * 
	 * @param Used
	 *            in adding a recommendedBy
	 * @return Populates To properties to a BO
	 */

	public Recommendor populateTOtoBO(RecommendedByTO recommendedByTO) throws Exception{
		Recommendor recommendor = null;
		if (recommendedByTO != null) {
			recommendor = new Recommendor();
			recommendor.setCode(recommendedByTO.getCode());
			recommendor.setName(recommendedByTO.getName());
			if (recommendedByTO.getAddressLine1() != null
					&& !recommendedByTO.getAddressLine1().isEmpty()) {
				recommendor.setAddressLine1(recommendedByTO.getAddressLine1());
			}
			if (recommendedByTO.getAddressLine1() != null
					&& !recommendedByTO.getAddressLine1().isEmpty()) {
				recommendor.setAddressLine2(recommendedByTO.getAddressLine2());
			}
			recommendor.setCity(recommendedByTO.getCity());
			recommendor.setPhone(recommendedByTO.getPhone());
			if (recommendedByTO.getComments() != null
					&& !recommendedByTO.getComments().isEmpty()) {
				recommendor.setComments(recommendedByTO.getComments());
			}
			State state = new State();
			state.setId(recommendedByTO.getStateTO().getId());
			Country country = new Country();
			country.setId(recommendedByTO.getCountryTO().getId());
			recommendor.setState(state);
			recommendor.setCountry(country);
			recommendor.setCreatedBy(recommendedByTO.getCreatedBy());
			recommendor.setModifiedBy(recommendedByTO.getModifiedBy());
			recommendor.setCreatedDate(new Date());
			recommendor.setLastModifiedDate(new Date());
			recommendor.setIsActive(true);
		}
		log.info("Leaving from populateTOtoBO of RecommendedByHelper");
		return recommendor;

	}

	/**
	 * 
	 * @param Used
	 *            getting all the recommendedBy where isActive=1
	 * @return Also populates Bo object to To
	 */

	public List<RecommendedByTO> pupulateRecommendorBOtoTO(List<Recommendor> recommendList)throws Exception {
		RecommendedByTO recommendedByTO = null;
		StateTO stateTO = null;
		CountryTO countryTO = null;

		List<RecommendedByTO> newRecommList = new ArrayList<RecommendedByTO>();
		if (recommendList != null && !recommendList.isEmpty()) {
			Iterator<Recommendor> iterator = recommendList.iterator();
			while (iterator.hasNext()) {
				Recommendor recommendor = iterator.next();
				recommendedByTO = new RecommendedByTO();
				if (recommendor.getId() != 0 && recommendor.getCode() != null
						&& recommendor.getName() != null
						&& recommendor.getCity() != null
						&& recommendor.getPhone() != null
						&& recommendor.getState().getId() != 0
						&& recommendor.getState().getName() != null
						&& recommendor.getCountry().getId() != 0
						&& recommendor.getCountry().getName() != null) {
					recommendedByTO.setId(recommendor.getId());
					recommendedByTO.setCode(splitStringSize(recommendor.getCode(),10));
					recommendedByTO.setName(splitStringSize(recommendor.getName(),10));
					/**
					 * Check for the textfield properties and set only 15
					 * letters in a row
					 */
					if (recommendor.getAddressLine1() != null && !recommendor.getAddressLine1().isEmpty()) {
						recommendedByTO.setAddressLine1(splitStringSize(recommendor.getAddressLine1(), 10));
					}

					/**
					 * Check for the address properties and set only 15 letters
					 * in a row
					 */

					if (recommendor.getAddressLine2() != null && !recommendor.getAddressLine2().isEmpty()) {
						recommendedByTO.setAddressLine2(splitStringSize(recommendor.getAddressLine2(), 10));

					}
					recommendedByTO.setCity(splitStringSize(recommendor.getCity(),10));
					recommendedByTO.setPhone(recommendor.getPhone());
					if (recommendor.getComments() != null && !recommendor.getComments().isEmpty()) {
						recommendedByTO.setComments(splitStringSize(recommendor.getComments(), 10));
					}
					stateTO = new StateTO();
					stateTO.setId(recommendor.getState().getId());
					stateTO.setName(splitStringSize(recommendor.getState().getName(),10));
					recommendedByTO.setStateTO(stateTO);
					countryTO = new CountryTO();
					countryTO.setId(recommendor.getCountry().getId());
					countryTO.setName(splitStringSize(recommendor.getCountry().getName(),10));
					recommendedByTO.setCountryTO(countryTO);
					newRecommList.add(recommendedByTO);
				}
			}
		}
		log.info("Leaving from pupulateRecommendorBOtoTO of RecommendedByHelper");
		return newRecommList;
	}

	/**
	 * 
	 * @param Used
	 *            while updating a recommendedBy
	 * @return Converts To properties to BO
	 */
	public Recommendor populateTotoBoUpdate(RecommendedByTO byTO) throws Exception{
		Country country = null;
		State state = null;
		Recommendor recommendor;
		if (byTO != null) {
			recommendor = new Recommendor();
			recommendor.setId(byTO.getId());
			recommendor.setCode(byTO.getCode());
			recommendor.setName(byTO.getName());
			recommendor.setAddressLine1(byTO.getAddressLine1());
			if (byTO.getAddressLine2() != null && !byTO.getAddressLine2().isEmpty()) {
				recommendor.setAddressLine2(byTO.getAddressLine2());
			}
			if (byTO.getComments() != null && !byTO.getComments().isEmpty()) {
				recommendor.setComments(byTO.getComments());
			}
			recommendor.setCity(byTO.getCity());
			recommendor.setPhone(byTO.getPhone());
			country = new Country();
			country.setId(byTO.getCountryTO().getId());
			recommendor.setCountry(country);
			state = new State();
			state.setId(byTO.getStateTO().getId());
			recommendor.setState(state);
			recommendor.setIsActive(true);
			recommendor.setLastModifiedDate(new Date());
			recommendor.setModifiedBy(byTO.getModifiedBy());
		}else 
			recommendor = null;
		log.info("Leaving from populateTotoBoUpdate of RecommendedByHelper");
		return recommendor;
	}

	/**
	 * Used for edit Converts BO to TO
	 */

	public RecommendedByTO populateBotoToEdit(Recommendor recommendor)throws Exception {
		RecommendedByTO recommendedByTO = new RecommendedByTO();
		if (recommendor != null) {
			recommendedByTO.setId(recommendor.getId());
			recommendedByTO.setCode(recommendor.getCode());
			recommendedByTO.setName(recommendor.getName());
			recommendedByTO.setAddressLine1(recommendor.getAddressLine1());
			if (recommendor.getAddressLine2() != null && !recommendor.getAddressLine2().isEmpty()) {
				recommendedByTO.setAddressLine2(recommendor.getAddressLine2());
			}
			recommendedByTO.setCity(recommendor.getCity());
			recommendedByTO.setPhone(recommendor.getPhone());
			if (recommendor.getComments() != null && !recommendor.getComments().isEmpty()) {
				recommendedByTO.setComments(recommendor.getComments());
			}
			CountryTO countryTO = new CountryTO();
			if (recommendor.getCountry().getName() != null	&& !recommendor.getCountry().getName().isEmpty()
				&& recommendor.getCountry().getId() != 0) {
				countryTO.setName(recommendor.getCountry().getName());
				countryTO.setId(recommendor.getCountry().getId());
			}
			StateTO stateTO = new StateTO();
			if (recommendor.getState().getName() != null
				&& !recommendor.getState().getName().isEmpty()
				&& recommendor.getCountry().getId() != 0) {
				stateTO.setName(recommendor.getState().getName());
				stateTO.setId(recommendor.getState().getId());
			}
			recommendedByTO.setStateTO(stateTO);
			recommendedByTO.setCountryTO(countryTO);
		}
		log.info("Leaving from populateBotoToEdit of RecommendedByHelper");
		return recommendedByTO;
	}

	/**
	 * 
	 * @param This
	 *            method is used to split the string if it is long (For keeping
	 *            10 aplhabets)
	 * @return
	 */
	public static String splitString(String value, int limit)throws Exception {
		String appendedvalue = "";
		StringBuffer buffer = new StringBuffer();
		String[] temp = value.split(" ");

		//int begindex = 0, endindex = limit;

		int count = 0;
		while (true) {

			int length = temp[count].length();
			int begindex = 0;
			int endindex = limit;
			if (endindex < length) {
				while (true) {

					if (endindex < temp[count].length()) {
						String tempstring = temp[count].substring(begindex,
								endindex);
						begindex = begindex + limit;
						endindex = endindex + limit;
						if (endindex + 4 < temp[count].length()){
							appendedvalue = buffer.append(tempstring).append("\n").toString();
						}else{
							appendedvalue = buffer.append(tempstring).toString();
						}
					} else {
						String tempstring = temp[count].substring(begindex,
								temp[count].length());
						appendedvalue = buffer.append(tempstring).toString();
						break;
					}
				}

			} else {
				appendedvalue = buffer.append(" ").append(temp[count]).toString();

			}
			count++;
			if (count == temp.length)
				break;
		}
		log.info("End of splitString of RecommendedByHelper");
		return appendedvalue;
	}

	/**
	 * 
	 * @param value
	 * @param limit
	 * @return after splitting the String
	 * @throws Exception
	 */

	public String splitStringSize(String value, int limit) throws Exception{
		StringBuffer appendedvalue = new StringBuffer();
		int length = value.length();
		String[] temp = new String[length];
		int begindex = 0, endindex = limit;
		int count = 0;
		while (true) {
			if (endindex < length) {
				temp[count] = value.substring(begindex, endindex);
				begindex = begindex + limit;
				endindex = endindex + limit;
				appendedvalue.append(temp[count]).append(" ");
			} else {
				if (count == 0)
				temp[count] = value.substring(0, length);
				temp[count] = value.substring(begindex, length);
				appendedvalue.append(temp[count]);
				break;
			}
			count++;
		}
		log.info("End of splitStringSize of RecommendedByHelper");
		return appendedvalue.toString();
	}
}
