package com.kp.cms.bo.admin;

import java.util.Date;

public class EmpAcademicHolidaysDates {
	
		private int id;
		private EmpAcademicHolidays empAcademicHolidays;
		private Date date;
		
		public EmpAcademicHolidaysDates(){
			
		}
		
		public EmpAcademicHolidaysDates(int id, EmpAcademicHolidays empAcademicHolidays,
				Date date) {
			super();
			this.id = id;
			this.empAcademicHolidays = empAcademicHolidays;
			this.date = date;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}

		public EmpAcademicHolidays getEmpAcademicHolidays() {
			return empAcademicHolidays;
		}

		public void setEmpAcademicHolidays(EmpAcademicHolidays empAcademicHolidays) {
			this.empAcademicHolidays = empAcademicHolidays;
		}
}
