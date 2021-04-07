package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ConductCertificateDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.forms.admission.ConductCertificateForm;
import com.kp.cms.forms.admission.TransferCertificateForm;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.transactions.admission.IConductCertificateTransaction;
import com.kp.cms.transactionsimpl.admission.ConductCertificateTransactionImpl;
import com.kp.cms.helpers.admission.ConductCertificateHelper;
import com.kp.cms.helpers.admission.TransferCertificateHelper;

public class ConductCertificateHandler {
	private static final Log log = LogFactory.getLog(ConductCertificateHandler.class);
	public static volatile ConductCertificateHandler certificateHandler=null;
	
	public static ConductCertificateHandler getInstance()
	{
		if(certificateHandler==null)
			certificateHandler=new ConductCertificateHandler();
		return certificateHandler;
	}
	IConductCertificateTransaction certificateTransaction = new ConductCertificateTransactionImpl();	
	public List<PrintTcDetailsTo> getStudentsByClass(HttpServletRequest request,ConductCertificateForm form)throws Exception 
	{
		List<PrintTcDetailsTo> studentList=new ArrayList<PrintTcDetailsTo>();
		List<ConductCertificateDetails>  studentBoList=certificateTransaction.getStudentList(form.getClasses(),form.getFromUsn(),form.getToUsn(),form.getStudentId());
		int ptype=certificateTransaction.getPtype(form.getClasses());
		studentList=ConductCertificateHelper.getInstance().convertStudentBoToTo(studentBoList,request,form,ptype);
		return studentList;
	}
	public List<TCDetailsTO> getStudentsByName(String name,HttpServletRequest request, ConductCertificateForm certificateForm) throws Exception
	{
		List<StudentTCDetails> studentBo=certificateTransaction.getStudentsByName(name,certificateForm.getClasses());
		List<TCDetailsTO>studentList=ConductCertificateHelper.getInstance().convertStudentTcDetailsBoToToForReprintOnlyTc(studentBo,request,certificateForm);
		return studentList;
	}
}
