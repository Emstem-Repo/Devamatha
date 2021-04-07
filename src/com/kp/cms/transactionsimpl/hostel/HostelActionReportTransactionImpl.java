package com.kp.cms.transactionsimpl.hostel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.to.hostel.HostelActionReportTO;
import com.kp.cms.transactions.hostel.IHostelActionReportTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class HostelActionReportTransactionImpl implements
		IHostelActionReportTransaction {
	private static final Log log = LogFactory.getLog(HostelActionReportTransactionImpl.class);
	public List<Object> getHostelActionReportDetails(
			HostelActionReportTO actionReportTO) throws Exception {
		String strQuery="";
		List<Object> objList = new ArrayList<Object>();	
		Session session = null;
		Query query = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			/*String strQuery="select distinct hostel.name, disciDet.hlDisciplinaryType.name, leaves.hlLeaveType," +
					" stud.registerNo, happForm.employee.id, pdata.firstName, pdata.middleName, pdata.lastName," +
					" leaves.endDate, leaves.startDate, leaves.approvedDate" +
					" from HlApplicationForm happForm " +
					" join happForm.hlHostelByHlApprovedHostelId hostel " +
					" join happForm.admAppln.personalData pdata " +
					" join happForm.hlDisciplinaryDetailses disciDet " +
					" join happForm.hlHostelByHlApprovedHostelId.hlLeaves leaves " +
					" join happForm.admAppln.students stud ";*/
			
			/*String strQuery="SELECT hostel.name as 'HOSTELNAME', disciType.name as 'DISCIPLINETYPENAME', leavType.name as 'LEAVETYPENAME', stud.register_no, hlappForm.employee_id," +
				" pdata.first_name, pdata.middle_name, pdata.last_name,leave1.end_date, leave1.start_date, leave1.approved_date" +
				" FROM (((((( " +
				" hl_application_form hlappForm " +
				" INNER JOIN hl_hostel hostel ON (hlappForm.hl_applied_hostel_id = hostel.id) AND(hlappForm.hl_approved_hostel_id = hostel.id)" +
				" INNER JOIN hl_disciplinary_details disciDet ON (disciDet.hl_hostel_id = hostel.id) AND (disciDet.hl_application_form_id = hlappForm.id))" +
				" INNER JOIN hl_leave leave1 ON (leave1.hl_hostel_id = hostel.id) AND (leave1.hl_application_form_id = hlappForm.id))" +
				" INNER JOIN hl_disciplinary_type disciType ON (disciDet.hl_disciplinary_type_id = disciType.id))" +
				" INNER JOIN adm_appln admApp ON (hlappForm.adm_appln_id = admApp.id))" +
				" INNER JOIN personal_data pdata  ON (admApp.personal_data_id = pdata.id))" +
				" INNER JOIN student stud ON (stud.adm_appln_id = admApp.id))" +
				" INNER JOIN hl_leave_type leavType ON (leave1.hl_leave_type_id = leavType.id)";*/
			
			if(actionReportTO.getTypeId()!=null && !actionReportTO.getTypeId().equals("")){
				if(actionReportTO.getTypeId().equals("0")){			
					strQuery="SELECT hostel.name as 'HOSTELNAME', leavType.name as 'LEAVETYPENAME', stud.register_no, hlappForm.employee_id," +
								" pdata.first_name, pdata.middle_name, pdata.last_name,leave1.end_date, leave1.start_date, leave1.approved_date" +
								" FROM (((((( " +
								" hl_application_form hlappForm " +
								" INNER JOIN hl_hostel hostel ON (hlappForm.hl_applied_hostel_id = hostel.id) AND(hlappForm.hl_approved_hostel_id = hostel.id)" +
								" INNER JOIN hl_disciplinary_details disciDet ON (disciDet.hl_hostel_id = hostel.id) AND (disciDet.hl_application_form_id = hlappForm.id))" +
								" INNER JOIN hl_leave leave1 ON (leave1.hl_hostel_id = hostel.id) AND (leave1.hl_application_form_id = hlappForm.id))" +
								" INNER JOIN hl_disciplinary_type disciType ON (disciDet.hl_disciplinary_type_id = disciType.id))" +
								" INNER JOIN adm_appln admApp ON (hlappForm.adm_appln_id = admApp.id))" +
								" INNER JOIN personal_data pdata  ON (admApp.personal_data_id = pdata.id))" +
								" INNER JOIN student stud ON (stud.adm_appln_id = admApp.id))" +
								" INNER JOIN hl_leave_type leavType ON (leave1.hl_leave_type_id = leavType.id)";
				}else if(actionReportTO.getTypeId().equals("1")){
					strQuery="SELECT hostel.name as 'HOSTELNAME', disciType.name as 'DISCIPLINETYPENAME', stud.register_no, hlappForm.employee_id," +
								" pdata.first_name, pdata.middle_name, pdata.last_name,leave1.end_date, leave1.start_date, leave1.approved_date" +
								" FROM (((((( " +
								" hl_application_form hlappForm " +
								" INNER JOIN hl_hostel hostel ON (hlappForm.hl_applied_hostel_id = hostel.id) AND(hlappForm.hl_approved_hostel_id = hostel.id)" +
								" INNER JOIN hl_disciplinary_details disciDet ON (disciDet.hl_hostel_id = hostel.id) AND (disciDet.hl_application_form_id = hlappForm.id))" +
								" INNER JOIN hl_leave leave1 ON (leave1.hl_hostel_id = hostel.id) AND (leave1.hl_application_form_id = hlappForm.id))" +
								" INNER JOIN hl_disciplinary_type disciType ON (disciDet.hl_disciplinary_type_id = disciType.id))" +
								" INNER JOIN adm_appln admApp ON (hlappForm.adm_appln_id = admApp.id))" +
								" INNER JOIN personal_data pdata  ON (admApp.personal_data_id = pdata.id))" +
								" INNER JOIN student stud ON (stud.adm_appln_id = admApp.id))" +
								" INNER JOIN hl_leave_type leavType ON (leave1.hl_leave_type_id = leavType.id)";
				}
			}
				
			StringBuffer sbf = new StringBuffer(strQuery);
			
			if(actionReportTO.getHostelId()!=null && !actionReportTO.getHostelId().equals("")){
				sbf.append(" where hostel.id="+Integer.parseInt(actionReportTO.getHostelId()));
			}
			
			//DateFormat sdformat=new SimpleDateFormat("yyyy-MM-dd");
			if(actionReportTO.getStartDate()!=null && !actionReportTO.getStartDate().equals("")){				
				try{					
					sbf.append(" and leave1.start_date="+"'"+CommonUtil.ConvertStringToSQLExactDate1(actionReportTO.getStartDate().trim())+"'");
				}catch(Exception pe){
				}
			}
			if(actionReportTO.getEndDate()!=null && !actionReportTO.getEndDate().equals("")){				
				try{					
					sbf.append(" and leave1.end_date="+"'"+CommonUtil.ConvertStringToSQLExactDate1(actionReportTO.getEndDate().trim())+"'");
				}catch(Exception pe){
				}
			}
					
			//query = session.createQuery(sbf.toString());
			query = session.createSQLQuery(sbf.toString());
			objList=(List<Object>)query.list();
		} catch (Exception e) {
				log.debug("error occured in qyery");
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return objList;
	}
}
