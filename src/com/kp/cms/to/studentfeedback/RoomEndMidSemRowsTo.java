package com.kp.cms.to.studentfeedback;

import java.io.Serializable;




public class RoomEndMidSemRowsTo implements Serializable ,Comparable<RoomEndMidSemRowsTo>{

	private int id;
    private String endMidSem;
    private Integer endColumnNumber;
    private Integer midColumnNumber;
    private String endSemNoOfRows;
    private String midSemNoOfRows;
    private String endSemSeatInDesk;
    private String midSemSeatInDesk;
	private int noOfSeats;
	private int noOfColumns;
	private int noOfRows;
	private int seatingPosition;
	private boolean lastColumns;
	private String tempField;
	private int rowNo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEndMidSem() {
		return endMidSem;
	}
	public void setEndMidSem(String endMidSem) {
		this.endMidSem = endMidSem;
	}
	public Integer getEndColumnNumber() {
		return endColumnNumber;
	}
	public void setEndColumnNumber(Integer endColumnNumber) {
		this.endColumnNumber = endColumnNumber;
	}
	public Integer getMidColumnNumber() {
		return midColumnNumber;
	}
	public void setMidColumnNumber(Integer midColumnNumber) {
		this.midColumnNumber = midColumnNumber;
	}
	public String getEndSemNoOfRows() {
		return endSemNoOfRows;
	}
	public void setEndSemNoOfRows(String endSemNoOfRows) {
		this.endSemNoOfRows = endSemNoOfRows;
	}
	public String getMidSemNoOfRows() {
		return midSemNoOfRows;
	}
	public void setMidSemNoOfRows(String midSemNoOfRows) {
		this.midSemNoOfRows = midSemNoOfRows;
	}
	@Override
	public int compareTo(RoomEndMidSemRowsTo arg0) {
		if(this.getEndColumnNumber()!=null)
		{
		  if(arg0 instanceof RoomEndMidSemRowsTo && arg0.getId()>0 ){
				return this.getEndColumnNumber().compareTo(arg0.endColumnNumber);
			}else
				return 0;
		}
		if(this.getMidColumnNumber()!=null)
		{
		if(arg0 instanceof RoomEndMidSemRowsTo && arg0.getId()>0 ){
			return this.getMidColumnNumber().compareTo(arg0.midColumnNumber);
		}
	     else
		    return 0;
		}else
		    return 0;
	}
	
	public String getEndSemSeatInDesk() {
		return endSemSeatInDesk;
	}
	public void setEndSemSeatInDesk(String endSemSeatInDesk) {
		this.endSemSeatInDesk = endSemSeatInDesk;
	}
	public String getMidSemSeatInDesk() {
		return midSemSeatInDesk;
	}
	public void setMidSemSeatInDesk(String midSemSeatInDesk) {
		this.midSemSeatInDesk = midSemSeatInDesk;
	}
	public int getNoOfSeats() {
		return noOfSeats;
	}
	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
	public int getNoOfColumns() {
		return noOfColumns;
	}
	public void setNoOfColumns(int noOfColumns) {
		this.noOfColumns = noOfColumns;
	}
	public int getNoOfRows() {
		return noOfRows;
	}
	public void setNoOfRows(int noOfRows) {
		this.noOfRows = noOfRows;
	}
	public int getSeatingPosition() {
		return seatingPosition;
	}
	public void setSeatingPosition(int seatingPosition) {
		this.seatingPosition = seatingPosition;
	}
	public boolean isLastColumns() {
		return lastColumns;
	}
	public void setLastColumns(boolean lastColumns) {
		this.lastColumns = lastColumns;
	}
	public String getTempField() {
		return tempField;
	}
	public void setTempField(String tempField) {
		this.tempField = tempField;
	}
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

}
