package com.kp.cms.utilities;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.forms.hostel.UploadHostelStudentsForm;

public class HostelCSVUpdater {
	private static volatile HostelCSVUpdater hostelCSVUpdater = null;
	private static final Log log = LogFactory.getLog(HostelCSVUpdater.class);
	private HostelCSVUpdater() {
		
	}
	/**
	 * return singleton object of HostelCSVUpdater.
	 * @return
	 */
	public static HostelCSVUpdater getInstance() {
		if (hostelCSVUpdater == null) {
			hostelCSVUpdater = new HostelCSVUpdater();
		}
		return hostelCSVUpdater;
	}
	public List<HlRoomTransaction> parseFile(FileInputStream stream1,
			Map<String, Integer> pdMap, Map<Integer, Integer> maxCountMap,
			Map<String, Integer> rTypeMap,UploadHostelStudentsForm uploadHostelStudentsForm) throws Exception {
		Map<Integer,Integer> countMap=new HashMap<Integer, Integer>();
		List<HlRoomTransaction> roomTransactions=new ArrayList<HlRoomTransaction>();
		LabeledCSVParser parser = new LabeledCSVParser(new CSVParser(stream1));
		String hostelId=uploadHostelStudentsForm.getHostelId();
		while(parser.getLine()!=null){
			HlRoomTransaction h=new HlRoomTransaction();
			HlApplicationForm hlApplicationForm=new HlApplicationForm();
			HlHostel h1=new HlHostel();
			h1.setId(Integer.parseInt(uploadHostelStudentsForm.getHostelId()));
			HlRoomType hlRoomType=null;
			Employee e=null;
			AdmAppln admAppln=null;
			String roomNo="";
			String floorNo="";
			if(parser.getValueByLabel("RoomType")!=null && !parser.getValueByLabel("RoomType").isEmpty()){
				if(rTypeMap.containsKey(parser.getValueByLabel("RoomType"))){
					hlRoomType=new HlRoomType();
					hlRoomType.setId(rTypeMap.get(parser.getValueByLabel("RoomType")));
				}
			}
			if(parser.getValueByLabel("AppRegRollCode")!=null && !parser.getValueByLabel("AppRegRollCode").isEmpty()){
				if(pdMap.containsKey(parser.getValueByLabel("AppRegRollCode"))){
					if(uploadHostelStudentsForm.getApplnRegno().equals(4)){
						e=new Employee();
						e.setId(pdMap.get(parser.getValueByLabel("AppRegRollCode")));
						hlApplicationForm.setIsStaff(true);
						hlApplicationForm.setEmployee(e);
						h.setEmployee(e);
					}else{
						admAppln=new AdmAppln();
						admAppln.setId(pdMap.get(parser.getValueByLabel("AppRegRollCode")));
						hlApplicationForm.setIsStaff(false);
						hlApplicationForm.setAdmAppln(admAppln);
						h.setAdmAppln(admAppln);
					}
				}
			}
			if(hlRoomType!=null && hlRoomType.getId()>0 && ((admAppln!=null && admAppln.getId()>0) || (e!=null && e.getId()>0))){
				hlApplicationForm.setIsActive(true);
				HlStatus hlStatus=new HlStatus();
				hlStatus.setId(2);
				hlApplicationForm.setHlHostelByHlAppliedHostelId(h1);
				hlApplicationForm.setHlHostelByHlApprovedHostelId(h1);
				hlApplicationForm.setHlRoomTypeByHlAppliedRoomTypeId(hlRoomType);
				hlApplicationForm.setHlRoomTypeByHlApprovedRoomTypeId(hlRoomType);
				hlApplicationForm.setHlStatus(hlStatus);
				hlApplicationForm.setIsEnteredByAdmin(true);
				h.setHlStatus(hlStatus);
				h.setIsActive(true);
				h.setCreatedBy(uploadHostelStudentsForm.getUserId());
				h.setCreatedDate(new Date());
				h.setModifiedBy(uploadHostelStudentsForm.getUserId());
				h.setLastModifiedDate(new Date());
				hlApplicationForm.setCreatedBy(uploadHostelStudentsForm.getUserId());
				hlApplicationForm.setCreatedDate(new Date());
				hlApplicationForm.setModifiedBy(uploadHostelStudentsForm.getUserId());
				hlApplicationForm.setLastModifiedDate(new Date());
				h.setTxnDate(new Date());
				if(parser.getValueByLabel("IsVeg")!=null && !parser.getValueByLabel("IsVeg").isEmpty()){
					if(parser.getValueByLabel("IsVeg").equals("yes")){
						hlApplicationForm.setIsVeg(true);
					}else{
						hlApplicationForm.setIsVeg(false);
					}
				}	
				if(parser.getValueByLabel("FingerPrintId")!=null && !parser.getValueByLabel("FingerPrintId").isEmpty())
					hlApplicationForm.setFingerPrintId(parser.getValueByLabel("FingerPrintId"));
				if(parser.getValueByLabel("Applied_Date")!=null && CommonUtil.isValidDate(parser.getValueByLabel("Applied_Date")))
					hlApplicationForm.setAppliedDate(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("Applied_Date")));
				if(parser.getValueByLabel("ClinicalRemarks")!=null && !parser.getValueByLabel("ClinicalRemarks").isEmpty())
					hlApplicationForm.setClinicalRemarks(parser.getValueByLabel("ClinicalRemarks"));
				if(parser.getValueByLabel("SickNessRelatedInfo")!=null && !parser.getValueByLabel("SickNessRelatedInfo").isEmpty())
					hlApplicationForm.setSicknessRelatedInfo(parser.getValueByLabel("SickNessRelatedInfo"));
				if(parser.getValueByLabel("RoomNo")!=null && !parser.getValueByLabel("RoomNo").isEmpty())
					roomNo=parser.getValueByLabel("RoomNo");
				if(parser.getValueByLabel("floorNo")!=null && !parser.getValueByLabel("floorNo").isEmpty())
					floorNo=parser.getValueByLabel("floorNo");
				if(parser.getValueByLabel("bedNo")!=null && !parser.getValueByLabel("bedNo").isEmpty())
					h.setBedNo(Integer.parseInt(parser.getValueByLabel("bedNo")));
				if(!floorNo.isEmpty() && !roomNo.isEmpty()){
					int id=getRoomId(floorNo, roomNo,hostelId);
					if(id>0){
						HlRoom hlRoom=new HlRoom();
						hlRoom.setId(id);
						h.setHlRoom(hlRoom);
						h.setHlApplicationForm(hlApplicationForm);
						if(countMap.containsKey(id)){
							List<Object[]> countList=getCount(id);
							Iterator<Object[]> itr=countList.iterator();
							int cc=0;
							int cr=0;
							if(itr.hasNext()) {
								Object[] objects = (Object[]) itr.next();
								if(objects[0]!=null){
									cc=Integer.parseInt(objects[0].toString());
								}
								if(objects[1]!=null){
									cr=Integer.parseInt(objects[1].toString());
								}
							}
							cc=countMap.remove(id);
							if(maxCountMap.containsKey(hlRoomType.getId())){
								int maxCount=maxCountMap.get(hlRoomType.getId());
								if((cc+cr)<maxCount){
									h.setCurrentOccupantsCount(cc+1);
									h.setCurrentReservationCount(cr);
									countMap.put(id,cc+1);
									roomTransactions.add(h);
								}
							}
						}else{
							List<Object[]> countList=getCount(id);
							Iterator<Object[]> itr=countList.iterator();
							int cc=0;
							int cr=0;
							if(itr.hasNext()) {
								Object[] objects = (Object[]) itr.next();
								if(objects[0]!=null){
									cc=Integer.parseInt(objects[0].toString());
								}
								if(objects[1]!=null){
									cr=Integer.parseInt(objects[1].toString());
								}
								
							}
							if(maxCountMap.containsKey(hlRoomType.getId())){
								int maxCount=maxCountMap.get(hlRoomType.getId());
								if((cc+cr)<maxCount){
									h.setCurrentOccupantsCount(cc+1);
									h.setCurrentReservationCount(cr);
									countMap.put(id,cc+1);
									roomTransactions.add(h);
								}
							}
						}
					}
				}
			}
		}
		return roomTransactions;
	}
	
	/**
	 * gets income ID for CSV Matching DATA
	 * @param data
	 * @return
	 */
	public static int getRoomId(String floorNo, String roomNo,String hostelId)throws Exception{
		int result= -1;
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="select h.id from HlRoom h where h.isActive=1 and h.floorNo="+floorNo+" and h.name="+roomNo+" and h.hlHostel.id="+hostelId;
			 Query qr = session.createQuery(query);
			 Integer obj=(Integer)qr.uniqueResult();
			 if(obj!=null && obj.intValue()>0){
				 result=obj.intValue();
			 } 
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return result;
	}
	/**
	 * gets income ID for CSV Matching DATA
	 * @param data
	 * @return
	 */
	public static List<Object[]> getCount(int roomId)throws Exception{
		int result= -1;
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="select h.currentOccupantsCount," +
			 		"h.currentReservationCount " +
			 		"from HlRoomTransaction h where h.isActive=1 and h.hlRoom.id="+roomId+" and h.txnDate=(select max(h1.txnDate) from HlRoomTransaction h1 " +
			 				" where h1.isActive=1 and h1.hlRoom.id="+roomId+")";
			 Query qr = session.createQuery(query);
			 return qr.list();
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
}
