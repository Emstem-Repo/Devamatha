package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentDetails;
import com.kp.cms.forms.examallotment.RoomAllotmentStatusForm;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;
import com.kp.cms.utilities.CommonUtil;

public class ExamRoomAllotmentStatusHelper {
	private static volatile ExamRoomAllotmentStatusHelper helper = null;
	public static ExamRoomAllotmentStatusHelper getInstance(){
		if(helper == null){
			helper = new ExamRoomAllotmentStatusHelper();
		}
		return helper;
	}
	private ExamRoomAllotmentStatusHelper(){
		
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String getRoomsAllotedForCycleQuery( RoomAllotmentStatusForm objForm) throws Exception{
		StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("select count(allotmentDetails.id) as allotedRoomCapacity,roomAllotment.room.blockId.blockName as blockName," +
						" roomAllotment.room.floorName as floorName,roomAllotment.room.roomNo as roomName," +
						" roomAllotment.room.midSemCapacity as midSemCapacity,roomAllotment.room.endSemCapacity as endSemCapcity " +
						" from ExamRoomAllotment roomAllotment  "+
						" join roomAllotment.roomAllotmentDetails allotmentDetails "+
						" where roomAllotment.isActive=1 and" +
						" roomAllotment.examDefinition.id='"+objForm.getExamid()+"'" +
						" and roomAllotment.midOrEndSem='"+objForm.getMidEndSem()+"'" +
						" and roomAllotment.room.blockId.locationId='"+objForm.getCampusName()+"'") ;
		if(objForm.getCycleId()!=null && !objForm.getCycleId().isEmpty()){
			hqlQuery.append(" and roomAllotment.cycle.id="+Integer.parseInt(objForm.getCycleId()));
		}
		if(objForm.getAllottedDate()!=null && !objForm.getAllottedDate().isEmpty()){
			hqlQuery.append(" and roomAllotment.date='"+CommonUtil.ConvertStringToSQLDate(objForm.getAllottedDate())+"'");
		}
		if(objForm.getSessionId()!=null && !objForm.getSessionId().isEmpty()){
			hqlQuery.append(" and roomAllotment.examinationSessions.id="+Integer.parseInt(objForm.getSessionId()));
		}
		hqlQuery.append(" and allotmentDetails.isActive=1 " +
						" group by roomAllotment.room.id" +
						" order by roomAllotment.room.blockId.blockOrder,roomAllotment.room.floor,roomAllotment.room.roomNo");
		return hqlQuery.toString();
	}
	/**
	 * @param objForm 
	 * @param examRoomAllotmentBOList
	 * @return
	 * @throws Exception
	 */
	public List<RoomAllotmentStatusDetailsTo> convertBoListTOToList( List<Object[]> examRoomAllotmentBoList, RoomAllotmentStatusForm objForm) throws Exception{
		List<RoomAllotmentStatusDetailsTo> allotmentStatusTos = new ArrayList<RoomAllotmentStatusDetailsTo>();
		if(examRoomAllotmentBoList!=null && !examRoomAllotmentBoList.isEmpty()){
			Iterator<Object[]> iterator = examRoomAllotmentBoList.iterator();
			while (iterator.hasNext()) {
				Object[] examRoomAllotment = (Object[]) iterator .next();
				RoomAllotmentStatusDetailsTo detailsTo = new RoomAllotmentStatusDetailsTo();
				int allotedCapacity = 0;
				if(examRoomAllotment[1]!=null && examRoomAllotment[2]!=null 
						&& examRoomAllotment[3]!=null){
					
					detailsTo.setBlockNo(examRoomAllotment[1].toString());
					detailsTo.setFloorNo(examRoomAllotment[2].toString());
					detailsTo.setRoomNo(examRoomAllotment[3].toString());
				}
				if(objForm.getMidEndSem().equalsIgnoreCase("M")){
					allotedCapacity = Integer.parseInt(examRoomAllotment[0].toString());
					int midSemCapacity = Integer.parseInt(examRoomAllotment[4].toString());
					if(allotedCapacity<midSemCapacity){
						detailsTo.setRoomCapacity("seatsAvailable");
					}else{
						detailsTo.setRoomCapacity("seatsNotAvailable");
					}
					detailsTo.setTotalRoomCapacity(String.valueOf(midSemCapacity));
					detailsTo.setAllotedRoomCapacity(String.valueOf(allotedCapacity));
				}else if(objForm.getMidEndSem().equalsIgnoreCase("E")){
					allotedCapacity = Integer.parseInt(examRoomAllotment[0].toString());
					int midSemCapacity = Integer.parseInt(examRoomAllotment[5].toString());
					if(allotedCapacity<midSemCapacity){
						detailsTo.setRoomCapacity("seatsAvailable");
					}else{
						detailsTo.setRoomCapacity("seatsNotAvailable");
					}
					detailsTo.setTotalRoomCapacity(String.valueOf(midSemCapacity));
					detailsTo.setAllotedRoomCapacity(String.valueOf(allotedCapacity));
				}
				allotmentStatusTos.add(detailsTo);
			}
		}
		return allotmentStatusTos;
	}
}
