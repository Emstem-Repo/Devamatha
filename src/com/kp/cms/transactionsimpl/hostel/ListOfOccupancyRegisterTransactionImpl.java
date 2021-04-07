package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.ListOfOccupancyRegisterForm;
import com.kp.cms.transactions.hostel.IListOfOccupancyRegisterTransaction;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * 
 * @author kolli.ramamohan
 *
 */
public class ListOfOccupancyRegisterTransactionImpl implements IListOfOccupancyRegisterTransaction {
	private static final Logger log = Logger.getLogger(ListOfOccupancyRegisterTransactionImpl.class);
	
	/**
	 * 
	 */
	public List<Object> getHostelListOfOccupancyRegisterList(ListOfOccupancyRegisterForm listRegForm) throws Exception{
		log.info("Start of getHostelListOfOccupancyRegisterList of ListOfOccupancyRegisterTransactionImpl");
		Session session = null;
		List<Object> listOfOccupancyRegisterList = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			String sqlQuery="SELECT distinct pdata.first_name, room.name, room.floor_no," +
					" room.hl_room_type_id, roomtype.no_of_occupants, hostel.name FROM (  (   (   (" +
					" hl_room_type roomtype INNER JOIN hl_hostel hostel ON (roomtype.hl_hostel_id = hostel.id))" +
					" INNER JOIN hl_application_form appForm ON (appForm.hl_approved_hostel_id = hostel.id)" +
					" AND (appForm.hl_applied_room_type_id = roomtype.id) AND (appForm.hl_applied_hostel_id = hostel.id)" +
					" AND (appForm.hl_approved_room_type_id = roomtype.id)) INNER JOIN hl_room room " +
					" ON (room.hl_hostel_id = hostel.id) AND (room.hl_room_type_id = roomtype.id))" +
					" INNER JOIN adm_appln admapp ON (appForm.adm_appln_id = admapp.id))" +
					" INNER JOIN personal_data pdata ON (admapp.personal_data_id = pdata.id)";
			StringBuffer sb=new StringBuffer(sqlQuery);
			
			if(listRegForm.getHostelId()!=null && !listRegForm.getHostelId().equals("")){
				sb.append(" where hostel.id="+Integer.parseInt(listRegForm.getHostelId()));
			}
			if(listRegForm.getFloorNo()!=null && !listRegForm.getFloorNo().equals("")){
				sb.append(" and room.floor_no="+"'"+listRegForm.getFloorNo()+"'");
			}
			if(listRegForm.getRoomNo()!=null && !listRegForm.getRoomNo().equals("")){
				sb.append(" and room.name="+"'"+listRegForm.getRoomNo()+"'");
			}
			listOfOccupancyRegisterList = session.createSQLQuery(sb.toString()).list();
			
			//add this      where hostel.id=1 and room.floor_no='1' and room.name='s1'
			
			
		} catch (Exception e) {
			log.error("Exception occured in getFeeFinancialYearDetails in ListOfOccupancyRegisterTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("End of getFeeFinancialYearDetails of ListOfOccupancyRegisterTransactionImpl");
		return listOfOccupancyRegisterList;
	}
}
