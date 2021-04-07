package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.forms.hostel.ListOfOccupancyRegisterForm;
/**
 * 
 * @author kolli.ramamohan
 *
 */

public interface IListOfOccupancyRegisterTransaction {
	/**
	 * 
	 * @param listRegForm
	 * @return
	 * @throws Exception
	 */
	public List<Object> getHostelListOfOccupancyRegisterList(ListOfOccupancyRegisterForm listRegForm) throws Exception;
}
