package com.kp.cms.helpers.hostel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UploadHostelStudentsHelper {
	/**
	 * Singleton object of uploadHostelStudentsHelper
	 */
	private static volatile UploadHostelStudentsHelper uploadHostelStudentsHelper = null;
	private static final Log log = LogFactory.getLog(UploadHostelStudentsHelper.class);
	private UploadHostelStudentsHelper() {
		
	}
	/**
	 * return singleton object of uploadHostelStudentsHelper.
	 * @return
	 */
	public static UploadHostelStudentsHelper getInstance() {
		if (uploadHostelStudentsHelper == null) {
			uploadHostelStudentsHelper = new UploadHostelStudentsHelper();
		}
		return uploadHostelStudentsHelper;
	}
	/**
	 * @param applicationYear
	 * @param applnReg
	 * @return
	 * @throws Exception
	 */
	public String getAdmQuery(int applicationYear, String applnReg) throws Exception {
		String query="";
		if(applnReg.equals("1")){
			query=" select a.applnNo," +
					"a.id from AdmAppln a join a.students s " +
					"where a.appliedYear="+applicationYear+
					" and a.isCancelled=0 " +
					" and s.isAdmitted=1 " +
					" and s.isActive=1 " +
					"group by a.id";
		}else if(applnReg.equals("2")){
			query="select s.registerNo,s.admAppln.id from Student s where s.isActive=1 " +
					" and s.isAdmitted=1 " +
					" and s.admAppln.appliedYear="+applicationYear+" and s.registerNo <> null and s.registerNo<>''";
		}else if(applnReg.equals("3")){
			query="select s.rollNo,s.admAppln.id " +
					"from Student s where s.isActive=1 " +
					" and s.isAdmitted=1 " +
					"and s.admAppln.appliedYear="+applicationYear+" and s.rollNo <> null and s.rollNo<>''";
		}else{
			query="select e.code,e.id from Employee e " +
					"where e.isActive=1 and e.code<>null";
		}
		return query;
	}
	/**
	 * @param hostelId
	 * @return
	 * @throws Exception
	 */
	public String getMaxCountForRoomType(String hostelId) throws Exception {
		String query="select h.id,h.noOfOccupants " +
				"from HlRoomType h where h.isActive=1 and h.hlHostel.id="+hostelId;
		return query;
	}
	/**
	 * @param hostelId
	 * @return
	 * @throws Exception
	 */
	public String getRoomTypeForHostel(String hostelId) throws Exception{
		String query="select h.name,h.id " +
				"from HlRoomType h where h.isActive=1 and h.hlHostel.id="+hostelId;
		// TODO Auto-generated method stub
		return query;
	}
	/**
	 * @param hostelId
	 * @return
	 * @throws Exception
	 */
	public String getRoomQuery(String hostelId) throws Exception{
		String query="from HlRoom h where h.isActive=1 and h.hlHostel.id="+hostelId;
		return query;
	}
}
