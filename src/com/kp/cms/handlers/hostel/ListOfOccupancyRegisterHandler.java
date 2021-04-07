package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.ListOfOccupancyRegisterForm;
import com.kp.cms.helpers.hostel.ListOfOccupancyRegisterHelper;
import com.kp.cms.to.hostel.ListOfOccupancyRegisterTO;
import com.kp.cms.transactions.hostel.IListOfOccupancyRegisterTransaction;
import com.kp.cms.transactionsimpl.hostel.ListOfOccupancyRegisterTransactionImpl;
import common.Logger;

/**
 * 
 * @author kolli.ramamohan
 *
 */
public class ListOfOccupancyRegisterHandler {
	private static final Logger log = Logger.getLogger(ListOfOccupancyRegisterHandler.class);
	private static volatile ListOfOccupancyRegisterHandler listOfOccupancyRegisterHandler = null;

	IListOfOccupancyRegisterTransaction iOccupancyRegisterTransaction = new ListOfOccupancyRegisterTransactionImpl();

	@SuppressWarnings("unused")
	private ListOfOccupancyRegisterHandler() {
	
	}
	/**
	 * 
	 * @return listOfOccupancyRegisterHandler
	 */
	public static ListOfOccupancyRegisterHandler getInstance() {
		if (listOfOccupancyRegisterHandler == null) {
			listOfOccupancyRegisterHandler = new ListOfOccupancyRegisterHandler();
		}
		return listOfOccupancyRegisterHandler;
	}
	
	/**
	 * 
	 * @param listRegForm
	 * @return
	 * @throws Exception
	 */
	public List<ListOfOccupancyRegisterTO> getHostelListOfOccupancyRegisterList(ListOfOccupancyRegisterForm listRegForm) throws Exception{
		log.info("Start of getHostelListOfOccupancyRegisterList of ListOfOccupancyRegisterHandler");
		
		List<ListOfOccupancyRegisterTO> listOfOccupancyRegisterList = new ArrayList<ListOfOccupancyRegisterTO>();
		try {
			if(iOccupancyRegisterTransaction!=null){
				List<Object> listOfObject=iOccupancyRegisterTransaction.getHostelListOfOccupancyRegisterList(listRegForm);
				ListOfOccupancyRegisterHelper listOfOccupancyRegisterHelper=ListOfOccupancyRegisterHelper.getInstance();
				listOfOccupancyRegisterList=listOfOccupancyRegisterHelper.getHostelListOfOccupancyRegisterList(listOfObject);
			}
		} catch (Exception e) {
			log.error("Exception occured in getHostelListOfOccupancyRegisterList in ListOfOccupancyRegisterHandler : "+ e);
			throw new ApplicationException(e);
		} 
		log.info("End of getHostelListOfOccupancyRegisterList of ListOfOccupancyRegisterTransactionImpl");
		return listOfOccupancyRegisterList;
	}
}
