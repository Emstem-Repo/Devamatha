package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.AdmLoanLetterDetails;
import com.kp.cms.forms.admission.AdmLoanLetterForm;
import com.kp.cms.to.admission.AdmLoanLetterDetailsTO;
import com.kp.cms.utilities.CommonUtil;

public class AdmLoanLetterHelper {
	
	private static volatile AdmLoanLetterHelper admLoanLetterHelper = null;
	private static final Log log = LogFactory.getLog(AdmLoanLetterHelper.class);
	
	private AdmLoanLetterHelper() {
		
	}
	
	public static AdmLoanLetterHelper getInstance() {
		if (admLoanLetterHelper == null) {
			admLoanLetterHelper = new AdmLoanLetterHelper();
		}
		return admLoanLetterHelper;
	}
	
	/**
	 * returning the query to get the loanLetter generated student list for input selected
	 * @param letterForm
	 * @return
	 * @throws Exception
	 */
	public String getQuery(AdmLoanLetterForm letterForm) throws Exception
	{
		Date dateinSQlFormat=CommonUtil.ConvertStringToSQLDate(letterForm.getAdmittedDate());
		String query=  "from Student s where s.isActive=1 and s.isAdmitted=1 and (s.isHide=0 or s.isHide=null) and s.admAppln.isCancelled=0" ;
                  
		if(letterForm.getAdmittedDate()!=null && !letterForm.getAdmittedDate().trim().isEmpty())
			query = query + " and s.admAppln.admissionDate='" +dateinSQlFormat+ "' ";
		
		if(letterForm.getApplicationNo()!=null && !letterForm.getApplicationNo().trim().isEmpty())
			query = query + " and s.admAppln.applnNo=" +letterForm.getApplicationNo();
		
		if(letterForm.getRegisterNo()!=null && !letterForm.getRegisterNo().trim().isEmpty())
			query = query + " and  s.registerNo='" +letterForm.getRegisterNo()+"'";
		
		return query;
	}
	
	public List<AdmLoanLetterDetailsTO> convertBostoTO(List<Student> applnList) throws Exception {
		List<AdmLoanLetterDetailsTO> chTolist=null;
		if(applnList!=null && !applnList.isEmpty()){
			Iterator<Student> itr=applnList.iterator();
			chTolist= new ArrayList<AdmLoanLetterDetailsTO>();
			while (itr.hasNext()) {
				Student std = (Student) itr.next();
				AdmLoanLetterDetailsTO chTo=new AdmLoanLetterDetailsTO();
				
				chTo.setStudentName(std.getAdmAppln().getPersonalData().getFirstName());
				chTo.setCourseName(std.getAdmAppln().getCourse().getName());
				if(std.getAdmAppln().getAdmissionDate()!=null){
					String dateString = std.getAdmAppln().getAdmissionDate().toString().substring(0, 10);
					String inputDateFormat = "yyyy-mm-dd";
					String outPutdateFormat = "dd/mm/yyyy";
					chTo.setAdmittedDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));
				}
					chTo.setRegisterNo(std.getRegisterNo());
					chTo.setAdmApplnId(std.getAdmAppln().getId());
				if(std.getAdmAppln().getApplnNo()>0){
					chTo.setApplicationNo(std.getAdmAppln().getApplnNo());
				}
				if(std.getAdmAppln().getAdmLoanLetterDetails()!=null && !std.getAdmAppln().getAdmLoanLetterDetails().isEmpty())
				{
					Iterator<AdmLoanLetterDetails> itr1=std.getAdmAppln().getAdmLoanLetterDetails().iterator();
					while (itr1.hasNext()) {
						AdmLoanLetterDetails loan = (AdmLoanLetterDetails) itr1.next();
						if(loan.getAdmApplnId().getId()==std.getAdmAppln().getId()){
							if(loan.getId()>0){
								chTo.setId(loan.getId());
							}
							
							chTo.setChecked1(String.valueOf(loan.getIsLoanLetterIssued()));
							chTo.setTempChecked(loan.getIsLoanLetterIssued());
						}
							
						}
				}
				chTolist.add(chTo);
				
				}
			}
		return chTolist;
	}

}
