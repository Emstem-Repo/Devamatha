package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.forms.hostel.HostelTransactionForm;
import com.kp.cms.handlers.hostel.HostelTransactionHandler;
import com.kp.cms.transactions.hostel.IHostelTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class HostelTransactionImpl implements IHostelTransactions{
	private static final Log log=LogFactory.getLog(HostelTransactionImpl.class);
	public static volatile HostelTransactionImpl hostelTransactionImpl=null;
	public static HostelTransactionImpl getInstance()
	{
		if(hostelTransactionImpl==null)
		{
			hostelTransactionImpl=new HostelTransactionImpl();
			return hostelTransactionImpl;
		}
		return hostelTransactionImpl;
	}
	
	public List<HlAdmissionBo> getStudentList(HostelTransactionForm  form) throws Exception {
		Session session = null;
		List<HlAdmissionBo> List = null;
		try {
			session = HibernateUtil.getSession();
			String str="";
			if( (form.getStudentName()!=null&& !form.getStudentName().isEmpty()) || (form.getRegno()!=null && !form.getRegno().isEmpty()) ||(form.getHostelName()!=null && !form.getHostelName().isEmpty()) ){
				str=" select ha from HlAdmissionBo ha where academicYear="+form.getYear()+" and ha.isCheckedIn=1 and ( ha.checkOut=0 or ha.checkOut=null)";
				if(form.getRegno()!=null && !form.getRegno().isEmpty()){
						str=str+" and ha.studentId.registerNo="+"'"+form.getRegno()+"'";
				}else if(form.getStudentName()!=null&& !form.getStudentName().isEmpty()){
					str=str+" and ha.studentId.admAppln.personalData.firstName="+"'"+form.getStudentName()+"'";
				}else if((form.getHostelName()!=null && !form.getHostelName().isEmpty())){
					str=str+"and ha.hostelId="+"'"+form.getHostelName()+"'";
				}
				Query query=session.createQuery(str);
				   List = query.list();
			}
			
			return List;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

}
