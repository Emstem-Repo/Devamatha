package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.forms.hostel.HostelStudentDetailsForm;
import com.kp.cms.transactions.hostel.IHostelStudentDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HostelStudentDetailsTxnImpl implements IHostelStudentDetailsTransaction {

	
	
	@Override
	public boolean checkHostelStudentIsCanceled(HostelStudentDetailsForm detailsForm) throws Exception {
		Session session = null;
		String regNoOrRollNo = detailsForm.getRegNoOrRollNo();
		boolean singlefld = false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			StringBuffer query = new StringBuffer(
			" from HlAdmissionBo hostel where hostel.isCancelled=1 and hostel.academicYear="+detailsForm.getYear()+" and hostel.hostelId.id="+detailsForm.getHostelId());
			if (regNoOrRollNo != null && !StringUtils.isEmpty(regNoOrRollNo.trim()))
			{
			query = query.append(" and ( hostel.studentId.registerNo= '"+ regNoOrRollNo+"' or hostel.studentId.rollNo='"+regNoOrRollNo+"')");
			
			Query query1= session.createQuery(query.toString());
			List<HlAdmissionBo> list=  query1.list();
			if(list!=null && !list.isEmpty()){
				singlefld=true;
				Iterator<HlAdmissionBo> iterator = list.iterator();
				while (iterator.hasNext()) {
			 HlAdmissionBo admissionBo=iterator.next();
			detailsForm.setRemarks(admissionBo.getCancelReason());
		}
	}
	}
} catch (Exception e) {
	throw new ApplicationException(e);
}
return singlefld;
}	

public StringBuffer getSerchedHostelStudentsQuery(HostelStudentDetailsForm detailsForm)throws Exception {
	String regNoOrRollNo=detailsForm.getRegNoOrRollNo();
	String firstName=detailsForm.getStudentName();
	StringBuffer query = new StringBuffer("from HlAdmissionBo hostel where hostel.isCancelled=0 and hostel.isActive=1 and hostel.academicYear="+detailsForm.getYear()+" and hostel.hostelId.id="+detailsForm.getHostelId());
	if (regNoOrRollNo != null && !StringUtils.isEmpty(regNoOrRollNo.trim())) {
		query = query.append(" and ( hostel.studentId.registerNo= '"+ regNoOrRollNo+"' or hostel.studentId.rollNo='"+regNoOrRollNo+"')" );
	}
	
	if (firstName != null && !StringUtils.isEmpty(firstName.trim())) {
		query = query.append(" and hostel.studentId.admAppln.personalData.firstName like '"
				+firstName+"%'");
	}
	
				
	query.append(" order by hostel.studentId.admAppln.personalData.firstName");
return query;
}

@Override
public List<HlAdmissionBo> getSerchedHostelStudents(StringBuffer querry) throws Exception {
	Session session = null;
	List<HlAdmissionBo> hlAdmissionBoList;
	try {
		session = HibernateUtil.getSession();
		Query queri = session.createQuery(querry.toString());
		hlAdmissionBoList = queri.list();
	} catch (Exception e) {
		throw new ApplicationException(e);
	}
	return hlAdmissionBoList;
}

public List<ExamStudentDetentionRejoinDetails> getExamHostelStudentDetentionRejoinDetails() {
	List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetailslist=new ArrayList<ExamStudentDetentionRejoinDetails>();
	Session session=null;
	try{
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		session=sessionFactory.openSession();
		String query="from ExamStudentDetentionRejoinDetails es"
					+" where (es.detain=1 or es.discontinued=1) and (es.rejoin=0 or es.rejoin is null)";
		Query queri=session.createQuery(query);
	examStudentDetentionRejoinDetailslist=queri.list();
	}catch (Exception exception) {
		if(session!=null){
			session.flush();
			session.close();
		}
		
	}
	
	return examStudentDetentionRejoinDetailslist;
}

@Override
public List<Integer> getSerchedHostelStudentsPhotoList(
		HostelStudentDetailsForm studentDetailsForm) throws Exception {
	Session session = null;
	String regNoOrRollNo=studentDetailsForm.getRegNoOrRollNo();
	String firstName=studentDetailsForm.getStudentName();
	List<Integer> stList;
	try {
		StringBuffer query = new StringBuffer(
				"select st.id from ApplnDoc a"
				+ " join a.admAppln.students st  where  a.isPhoto=1 and  (a.document <> '' or a.document <> null) and st.isAdmitted=1 and st.isActive = 1 and st.admAppln.isSelected=1 and st.admAppln.isCancelled=0  and st.admAppln.isApproved=1 ");
		if (regNoOrRollNo != null && !StringUtils.isEmpty(regNoOrRollNo.trim())) {
			query = query.append(" and ( st.registerNo= '"+ regNoOrRollNo+"' or st.rollNo='"+regNoOrRollNo+"')");
		}
		
		if (firstName != null && !StringUtils.isEmpty(firstName.trim())) {
			query = query.append(" and a.admAppln.personalData.firstName like '"
					+ firstName+"%'");
		}
		query = query.append(" order by a.admAppln.personalData.firstName");
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		// session =sessionFactory.openSession();
		session = HibernateUtil.getSession();
		Query queri = session.createQuery(query.toString());
		stList = queri.list();
		
	} catch (Exception e) {
		throw new ApplicationException(e);
	}
	return stList;
}
	
}
