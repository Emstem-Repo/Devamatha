package com.kp.cms.to.examallotment;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.kp.cms.to.studentfeedback.RoomEndMidSemRowsTo;

public class ExamRoomAllotmentDetailsTO implements Serializable {
	
		private int id;
	 	private int blockId;
	    private String blockName;
	    private String locationId;
	    private String locationName;
	    private String floor;
	    private String roomNo;
	    private String totalCapacity;
	    private String endSemCapacity;
	    private String endSemTotalColumn;
	    private String endSemSeatInDesk;
	    private String midSemCapacity;
	    private String midSemTotalColumn;
	    private String midSemSeatInDesk;	
	    private List<RoomEndMidSemRowsTo> columnWiseRows;
	    private int totalColumns;
	    
	    
	    private int studentId;
	    private int classId;
	    private int examSessionId;
	    private String examSessionName;
	    private String examDate;
	    
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getBlockId() {
			return blockId;
		}
		public void setBlockId(int blockId) {
			this.blockId = blockId;
		}
		public String getBlockName() {
			return blockName;
		}
		public void setBlockName(String blockName) {
			this.blockName = blockName;
		}
		public String getLocationId() {
			return locationId;
		}
		public void setLocationId(String locationId) {
			this.locationId = locationId;
		}
		public String getLocationName() {
			return locationName;
		}
		public void setLocationName(String locationName) {
			this.locationName = locationName;
		}
		public String getFloor() {
			return floor;
		}
		public void setFloor(String floor) {
			this.floor = floor;
		}
		public String getRoomNo() {
			return roomNo;
		}
		public void setRoomNo(String roomNo) {
			this.roomNo = roomNo;
		}
		public String getTotalCapacity() {
			return totalCapacity;
		}
		public void setTotalCapacity(String totalCapacity) {
			this.totalCapacity = totalCapacity;
		}
		public String getEndSemCapacity() {
			return endSemCapacity;
		}
		public void setEndSemCapacity(String endSemCapacity) {
			this.endSemCapacity = endSemCapacity;
		}
		public String getEndSemTotalColumn() {
			return endSemTotalColumn;
		}
		public void setEndSemTotalColumn(String endSemTotalColumn) {
			this.endSemTotalColumn = endSemTotalColumn;
		}
		public String getEndSemSeatInDesk() {
			return endSemSeatInDesk;
		}
		public void setEndSemSeatInDesk(String endSemSeatInDesk) {
			this.endSemSeatInDesk = endSemSeatInDesk;
		}
		public String getMidSemCapacity() {
			return midSemCapacity;
		}
		public void setMidSemCapacity(String midSemCapacity) {
			this.midSemCapacity = midSemCapacity;
		}
		public String getMidSemTotalColumn() {
			return midSemTotalColumn;
		}
		public void setMidSemTotalColumn(String midSemTotalColumn) {
			this.midSemTotalColumn = midSemTotalColumn;
		}
		public String getMidSemSeatInDesk() {
			return midSemSeatInDesk;
		}
		public void setMidSemSeatInDesk(String midSemSeatInDesk) {
			this.midSemSeatInDesk = midSemSeatInDesk;
		}
		public List<RoomEndMidSemRowsTo> getColumnWiseRows() {
			return columnWiseRows;
		}
		public void setColumnWiseRows(List<RoomEndMidSemRowsTo> columnWiseRows) {
			this.columnWiseRows = columnWiseRows;
		}
		public int getStudentId() {
			return studentId;
		}
		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}
		public int getClassId() {
			return classId;
		}
		public void setClassId(int classId) {
			this.classId = classId;
		}
		public int getTotalColumns() {
			return totalColumns;
		}
		public void setTotalColumns(int totalColumns) {
			this.totalColumns = totalColumns;
		}
		public int getExamSessionId() {
			return examSessionId;
		}
		public void setExamSessionId(int examSessionId) {
			this.examSessionId = examSessionId;
		}
		public String getExamSessionName() {
			return examSessionName;
		}
		public void setExamSessionName(String examSessionName) {
			this.examSessionName = examSessionName;
		}
		public String getExamDate() {
			return examDate;
		}
		public void setExamDate(String examDate) {
			this.examDate = examDate;
		}
	    
	    
	    
	    	    
	
}