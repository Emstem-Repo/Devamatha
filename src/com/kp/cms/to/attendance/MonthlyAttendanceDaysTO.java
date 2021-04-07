package com.kp.cms.to.attendance;

public class MonthlyAttendanceDaysTO {
	
	private int dayId;
	
	private String hoursHeld;
	
	private String hoursHeldOld;
	
	private int tabOrder;


	public String getHoursHeld() {
		return hoursHeld;
	}

	public int getDayId() {
		return dayId;
	}

	public void setDayId(int dayId) {
		this.dayId = dayId;
	}

	public void setHoursHeld(String hoursHeld) {
		this.hoursHeld = hoursHeld;
	}

	public String getHoursHeldOld() {
		return hoursHeldOld;
	}

	public void setHoursHeldOld(String hoursHeldOld) {
		this.hoursHeldOld = hoursHeldOld;
	}

	public int getTabOrder() {
		return tabOrder;
	}

	public void setTabOrder(int tabOrder) {
		this.tabOrder = tabOrder;
	}

}
