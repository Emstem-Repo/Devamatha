package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.employee.IGuestFacultyExcelReportTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class GuestFacultyExcelReportTransImpl implements IGuestFacultyExcelReportTransaction{
	private static volatile GuestFacultyExcelReportTransImpl guestFacultyExcelReportTransImpl = null;
	public static GuestFacultyExcelReportTransImpl getInstance(){
		if(guestFacultyExcelReportTransImpl == null){
			guestFacultyExcelReportTransImpl = new GuestFacultyExcelReportTransImpl();
		return guestFacultyExcelReportTransImpl;
		}
		return guestFacultyExcelReportTransImpl;
	}
	@Override
	public List<GuestFaculty> getSearchedEmployee(StringBuffer query)
			throws Exception {
		List<GuestFaculty> guestFaculties;
		Session session = null;
		try{
			session=HibernateUtil.getSession();
			Query query1= session.createQuery(query.toString());
			guestFaculties = query1.list();
			}
		catch (Exception e) {
			throw new ApplicationException(e);
			}
		return guestFaculties;
		}
}
