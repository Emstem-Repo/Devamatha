package com.kp.cms.helpers.exam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TempHallTicketOrIDCardHelper {
	private static final Log log = LogFactory.getLog(TempHallTicketOrIDCardHelper.class);
	public static volatile TempHallTicketOrIDCardHelper tempHallTicketOrIDCardHelper=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static TempHallTicketOrIDCardHelper getInstance(){
		if(tempHallTicketOrIDCardHelper==null){
			tempHallTicketOrIDCardHelper= new TempHallTicketOrIDCardHelper();}
		return tempHallTicketOrIDCardHelper;
	}


}
