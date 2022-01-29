package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.List;

import javassist.bytecode.Descriptor.Iterator;

import com.kp.cms.handlers.hostel.ListOfOccupancyRegisterHandler;
import com.kp.cms.to.hostel.ListOfOccupancyRegisterTO;
import common.Logger;

/**
 * 
 * @author kolli.ramamohan
 *
 */
public class ListOfOccupancyRegisterHelper {
	private static final Logger log = Logger.getLogger(ListOfOccupancyRegisterHandler.class);
	private static volatile ListOfOccupancyRegisterHelper listOfOccupancyRegisterHelper = null;
	
	/**
	 * 
	 * @return listOfOccupancyRegisterHelper
	 */
	public static ListOfOccupancyRegisterHelper getInstance() {
		if (listOfOccupancyRegisterHelper == null) {
			listOfOccupancyRegisterHelper = new ListOfOccupancyRegisterHelper();
		}
		return listOfOccupancyRegisterHelper;
	}
	
	/**
	 * 
	 * @param listOfOccupancyRegisterList
	 * @return
	 * @throws Exception
	 */
	public List<ListOfOccupancyRegisterTO> getHostelListOfOccupancyRegisterList(List<Object> listOfOccupancyRegisterList) throws Exception{
		List<ListOfOccupancyRegisterTO> occRegList=new ArrayList<ListOfOccupancyRegisterTO>();
		ListOfOccupancyRegisterTO listOfOccupancyRegisterTO=null;
		java.util.Iterator<Object> iter=listOfOccupancyRegisterList.iterator();
		while(iter.hasNext()){
			Object[] obj=(Object[]) iter.next();
			listOfOccupancyRegisterTO=new ListOfOccupancyRegisterTO();
			listOfOccupancyRegisterTO.setStudentName(String.valueOf(obj[0]));
			listOfOccupancyRegisterTO.setFloorNo(String.valueOf(obj[2]));
			listOfOccupancyRegisterTO.setRoomNo(String.valueOf(obj[1]));
			listOfOccupancyRegisterTO.setNoOfOccupants(String.valueOf(obj[4]));
			occRegList.add(listOfOccupancyRegisterTO);			
		}
		return occRegList;		
	}
}
