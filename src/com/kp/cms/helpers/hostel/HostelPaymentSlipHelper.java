package com.kp.cms.helpers.hostel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDamage;
import com.kp.cms.bo.admin.HlFeePayment;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.forms.hostel.HostelPaymentSlipForm;
import com.kp.cms.to.hostel.HostelFeeDetailsTO;
import com.kp.cms.to.hostel.HostelPaymentSlipTO;
import com.kp.cms.transactions.hostel.IHostelDamageTransaction;
import com.kp.cms.transactions.hostel.IHostelPaymentSlipTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelDamageTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelPaymentSlipTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelPaymentSlipHelper {

	/**
	 * Singleton object of HostelPaymentSlipHelper
	 */
	private static volatile HostelPaymentSlipHelper hostelPaymentSlipHelper = null;
	private static final Log log = LogFactory
			.getLog(HostelPaymentSlipHelper.class);

	private HostelPaymentSlipHelper() {

	}

	/**
	 * return singleton object of HostelPaymentSlipHelper.
	 * 
	 * @return
	 */
	public static HostelPaymentSlipHelper getInstance() {
		if (hostelPaymentSlipHelper == null) {
			hostelPaymentSlipHelper = new HostelPaymentSlipHelper();
		}
		return hostelPaymentSlipHelper;
	}

	public String getSearchQuery(HostelPaymentSlipForm hostelPaymentSlipForm)
			throws Exception {
		String searchQuery = "select h from HlApplicationForm h join h.hlRoomTypeByHlApprovedRoomTypeId.hlFeeses fee"
				+ " where h.hlStatus.id in(2,7,8) and fee.isActive=1 and  h.isActive=1 and h.hlHostelByHlApprovedHostelId.id="
				+ hostelPaymentSlipForm.getHostelId();
		if (hostelPaymentSlipForm.getApplicationNo() != null
				&& !hostelPaymentSlipForm.getApplicationNo().isEmpty()) {
			searchQuery = searchQuery + " and h.admAppln.applnNo="
					+ hostelPaymentSlipForm.getApplicationNo()+" group by h.id";
		}
		if (hostelPaymentSlipForm.getRegisterNo() != null
				&& !hostelPaymentSlipForm.getRegisterNo().isEmpty()) {
			searchQuery = "select h from HlApplicationForm h join h.hlRoomTypeByHlApprovedRoomTypeId.hlFeeses fee  join h.admAppln.students s where fee.isActive=1 and h.hlHostelByHlApprovedHostelId.id="
					+ hostelPaymentSlipForm.getHostelId()
					+ " and s.registerNo= '"
					+ hostelPaymentSlipForm.getRegisterNo() + "' group by h.id";
		}
		if (hostelPaymentSlipForm.getRollNo() != null
				&& !hostelPaymentSlipForm.getRollNo().isEmpty()) {
			searchQuery = "select h from HlApplicationForm h join h.hlRoomTypeByHlApprovedRoomTypeId.hlFeeses fee join h.admAppln.students s where fee.isActive=1 and h.hlHostelByHlApprovedHostelId.id="
					+ hostelPaymentSlipForm.getHostelId()
					+ " and s.rollNo= '"
					+ hostelPaymentSlipForm.getRollNo() + "' group by h.id";
		}
		if ((hostelPaymentSlipForm.getApplicationNo() != null && !hostelPaymentSlipForm
				.getApplicationNo().isEmpty())
				&& (hostelPaymentSlipForm.getRegisterNo() != null && !hostelPaymentSlipForm
						.getRegisterNo().isEmpty())) {
			searchQuery = "select h from HlApplicationForm h join h.hlRoomTypeByHlApprovedRoomTypeId.hlFeeses fee join h.admAppln.students s where fee.isActive=1 and h.hlHostelByHlApprovedHostelId.id="
					+ hostelPaymentSlipForm.getHostelId()
					+ " and s.registerNo= '"
					+ hostelPaymentSlipForm.getRegisterNo()
					+ "'"
					+ " and h.admAppln.applnNo="
					+ hostelPaymentSlipForm.getApplicationNo() +" group by h.id";
		}
		if ((hostelPaymentSlipForm.getApplicationNo() != null && !hostelPaymentSlipForm
				.getApplicationNo().isEmpty())
				&& (hostelPaymentSlipForm.getRegisterNo() != null && !hostelPaymentSlipForm
						.getRegisterNo().isEmpty())
				&& (hostelPaymentSlipForm.getRollNo() != null && !hostelPaymentSlipForm
						.getRollNo().isEmpty())) {
			searchQuery = "select h from HlApplicationForm h join h.hlRoomTypeByHlApprovedRoomTypeId.hlFeeses fee join h.admAppln.students s where fee.isActive=1 and h.hlHostelByHlApprovedHostelId.id="
					+ hostelPaymentSlipForm.getHostelId()
					+ " and s.registerNo= '"
					+ hostelPaymentSlipForm.getRegisterNo()
					+ "'"
					+ " and s.rollNo= '"
					+ hostelPaymentSlipForm.getRollNo()
					+ "'"
					+ " and h.admAppln.applnNo="
					+ hostelPaymentSlipForm.getApplicationNo() +" group by h.id";
		}
		if (hostelPaymentSlipForm.getStaffId() != null
				&& !hostelPaymentSlipForm.getStaffId().isEmpty()) {
			searchQuery = searchQuery + " and h.employee.code='"+ hostelPaymentSlipForm.getStaffId()+"'  group by h.id";
		}
		return searchQuery;
	}

	IHostelDamageTransaction transaction = new HostelDamageTransactionImpl();

	/**
	 *converting the Bo to To
	 */
	public HostelPaymentSlipTO convertBOtoTO(HlApplicationForm hlApplicationForm,HostelPaymentSlipForm hostelPaymentSlipForm) throws Exception{
		HostelPaymentSlipTO hostelPaymentSlipTO=new HostelPaymentSlipTO();
		int hlAppId=hlApplicationForm.getId();
		int hostelId=0;
		hostelPaymentSlipTO.setHlApplicationFormId(hlAppId);
		if(hlApplicationForm.getHlHostelByHlApprovedHostelId()!=null){
			if(hlApplicationForm.getHlHostelByHlApprovedHostelId().getId()>0){
				hostelPaymentSlipTO.setHostelName(hlApplicationForm.getHlHostelByHlApprovedHostelId().getName());
			}
		}
		 if(hlApplicationForm.getHlHostelByHlApprovedHostelId().getName()!=null){
				hostelId=hlApplicationForm.getHlHostelByHlApprovedHostelId().getId();
				hostelPaymentSlipTO.setHostelId(hostelId);
		
		}
		if(hlApplicationForm.getRequisitionNo()!=null){
		   hostelPaymentSlipTO.setRequisitionNo(hlApplicationForm.getRequisitionNo()+"");	
		}
		IHostelPaymentSlipTransaction transaction = new HostelPaymentSlipTransactionImpl();
		Integer maxbillNo=transaction.getMaxBillNoFromHlApplicationForm();
		
		if(hlApplicationForm.getBillNo()==null || hlApplicationForm.getBillNo()==0){
			DecimalFormat formatter = new DecimalFormat("");  
			String billNo="";                                       // for generating bill no as five digits 
			if(maxbillNo!=null){                                    // for auto incrementing next bill no base on existing bill no.
			   billNo=formatter.format(maxbillNo+1);
			}  
			else
			{   maxbillNo=0;
				billNo=formatter.format(maxbillNo+1);	
			}
			
			hostelPaymentSlipTO.setSlipNo(billNo);
			hostelPaymentSlipForm.setBillNo(billNo);
			
		}
		else
		{
			hostelPaymentSlipTO.setSlipNo(String.valueOf(hlApplicationForm.getBillNo()));
			hostelPaymentSlipForm.setBillNo(hlApplicationForm.getBillNo()+"");
		}
		HlRoomType roomType=hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId();
		List<HostelFeeDetailsTO> hostelFeeDetailsTOList=new ArrayList<HostelFeeDetailsTO>();
		if(hostelPaymentSlipForm.getSearchBy().equalsIgnoreCase("1")){	
			double totalAmount=0.0;
			double feeAmount=0.0;
			if(roomType!=null && roomType.getHlFeeses()!=null){
				for(HlFees fees: roomType.getHlFeeses()){
					HostelFeeDetailsTO feeDetailsTO=new HostelFeeDetailsTO();
					if(fees.getFeeAmount()!=null){
						feeAmount=fees.getFeeAmount().doubleValue();
						totalAmount=totalAmount+feeAmount;
					}
					// for getting fees
					if(fees.getHlFeeType()!=null){
					  feeDetailsTO.setFeeTypeName(fees.getHlFeeType().getName());
					}
					if(fees.getFeeAmount()!=null){
						feeDetailsTO.setFeeAmount(fees.getFeeAmount().doubleValue()+"");
					}
					
					hostelPaymentSlipTO.setFeeTotalAmount(totalAmount+"");
					hostelFeeDetailsTOList.add(feeDetailsTO);
					hostelPaymentSlipTO.setFeeDetailsTO(hostelFeeDetailsTOList);
				}
			}
		}
		else{
		IHostelPaymentSlipTransaction hostelPaymentSlipTransaction = HostelPaymentSlipTransactionImpl.getInstance();	
		/*if(hostelPaymentSlipForm.getSearchBy().equalsIgnoreCase("2")){*/	
			List<HlDamage> hlDamages=hostelPaymentSlipTransaction.getHostelDamageByHlApplicationFormId(hlApplicationForm.getId(),hlApplicationForm.getHlHostelByHlApprovedHostelId().getId());
			double fineTotalAmount=0.0;
			for(HlDamage hlDamage:hlDamages){
				HostelFeeDetailsTO feeDetailsTO=new HostelFeeDetailsTO(); // for getting fine
				feeDetailsTO.setFineAmount(hlDamage.getAmount()+"");
				if(hlDamage.getDate()!=null){
					String date=CommonUtil.getStringDate(hlDamage.getDate());
					feeDetailsTO.setFineDate(date);
					if(hlDamage.getAmount()!=null){
						double fineAmount=hlDamage.getAmount().doubleValue();
						fineTotalAmount=fineTotalAmount+fineAmount; 
						hostelPaymentSlipTO.setFeeTotalAmount(fineTotalAmount+"");
					}
					if(!StringUtils.isBlank(hlDamage.getDescription())){
						feeDetailsTO.setFineDescription(hlDamage.getDescription());	
						
					}
				}
				hostelFeeDetailsTOList.add(feeDetailsTO);
				hostelPaymentSlipTO.setFeeDetailsTO(hostelFeeDetailsTOList);
			}
		}
		// information needed in printed slip
				
		String studentName="";
		String staffName="";
		AdmAppln admAppln=hlApplicationForm.getAdmAppln();
		if(admAppln!=null){
			PersonalData personalData=admAppln.getPersonalData();
			if(personalData!=null)
			{
				if(personalData.getMiddleName() == null && personalData.getLastName() ==null){  
					studentName=personalData.getFirstName();                       // for getting studentName
				}else if(personalData.getLastName() ==null){
					studentName=personalData.getFirstName() +" "+ personalData.getMiddleName();
				}else if(personalData.getMiddleName() ==null){
					studentName=personalData.getFirstName() +" "+ personalData.getLastName();
				}
				else
				{
					studentName=personalData.getFirstName() +" "+ personalData.getMiddleName()+" "+personalData.getLastName();
				}
			}
		}
		if(hlApplicationForm.getEmployee()!=null)
		{
			/*if(hlApplicationForm.getEmployee().getMiddleName() == null && hlApplicationForm.getEmployee().getLastName() ==null){  
				staffName=hlApplicationForm.getEmployee().getFirstName();                       // for getting staffName
			}else if(hlApplicationForm.getEmployee().getLastName() ==null){
				staffName=hlApplicationForm.getEmployee().getFirstName() +" "+ hlApplicationForm.getEmployee().getMiddleName();
			}else if(hlApplicationForm.getEmployee().getMiddleName() ==null){
				staffName=hlApplicationForm.getEmployee().getFirstName() +" "+ hlApplicationForm.getEmployee().getLastName();
			}*/
			
				staffName=hlApplicationForm.getEmployee().getFirstName() ;
			
		}
		
		hostelPaymentSlipForm.setDate(CommonUtil.getStringDate(new Date()));
		if(!hostelPaymentSlipForm.getApplicationNo().trim().isEmpty()||!hostelPaymentSlipForm.getRegisterNo().trim().isEmpty()||!hostelPaymentSlipForm.getRollNo().trim().isEmpty()){
			if(hlApplicationForm.getAdmAppln()!=null){
				hostelPaymentSlipForm.setStudentOrStaffId(hlApplicationForm.getAdmAppln().getApplnNo()+"");
				hostelPaymentSlipForm.setStudentOrStaffName(studentName);
			}
		}
		if(!hostelPaymentSlipForm.getStaffId().trim().isEmpty()){
			if(hlApplicationForm.getEmployee()!=null){	
				hostelPaymentSlipForm.setStudentOrStaffId(hlApplicationForm.getEmployee().getId()+"");
				hostelPaymentSlipForm.setStudentOrStaffName(staffName);
			}
		}
		
		return hostelPaymentSlipTO;
	}
	
	public HlFeePayment getHlFeePayment(HostelPaymentSlipForm hostelPaymentSlipForm){
		
	  HlFeePayment hlFeePayment=new HlFeePayment();
	  //if(hostelPaymentSlipForm.getSearchBy().equalsIgnoreCase("1"))
	  hlFeePayment.setAmount(new BigDecimal(hostelPaymentSlipForm.getHostelPaymentSlipTO().getFeeTotalAmount()));
	  HlApplicationForm hlApplicationForm=new HlApplicationForm();
	  hlApplicationForm.setId(hostelPaymentSlipForm.getHostelPaymentSlipTO().getHlApplicationFormId());
	  hlFeePayment.setHlApplicationForm(hlApplicationForm);
	  hlFeePayment.setIsActive(true);
	  hlFeePayment.setCreatedBy(hostelPaymentSlipForm.getUserId());
	  hlFeePayment.setModifiedBy(hostelPaymentSlipForm.getUserId());
	  hlFeePayment.setCreatedDate(new Date());
	  hlFeePayment.setLastModifiedDate(new Date());
	  return hlFeePayment;
	  
		
	}

	public List<HostelPaymentSlipTO> getBillNumbersTo(List<HlApplicationForm> hlApplicationForm) 
	{
		List<HostelPaymentSlipTO> billNoList=new ArrayList<HostelPaymentSlipTO>();
		HostelPaymentSlipTO paymentSlipTo=null;
		for(HlApplicationForm form:hlApplicationForm)
		{
			paymentSlipTo=new HostelPaymentSlipTO();
			paymentSlipTo.setHlApplicationFormId(form.getId());
			if(form.getBillNo()!=null){
			paymentSlipTo.setSlipNo(String.valueOf(form.getBillNo()));
			billNoList.add(paymentSlipTo);
			}
		}
		
		return billNoList;
	}

	public String getHostelPaymentSlipQuery(HostelPaymentSlipForm hostelPaymentSlipForm) 
	{
		// TODO Auto-generated method stub
		String searchQuery = "select h from HlApplicationForm h join h.hlRoomTypeByHlApprovedRoomTypeId.hlFeeses fee"
			+ " where h.id="+ hostelPaymentSlipForm.getHlApplicationFormId();
		return searchQuery;
	}

	/**
	 * @param hostelPaymentSlipForm
	 * @return
	 * @throws Exception
	 */
	public String getsearchQueryForFine(HostelPaymentSlipForm hostelPaymentSlipForm) throws Exception{
		String query="select d from HlDamage d left join d.hlApplicationForm.admAppln.students s" +
				" where d.isActive=1 and d.isPaid=0 and d.hlApplicationForm.hlStatus.id=2 ";
		if(hostelPaymentSlipForm.getApplicationNo()!=null && !hostelPaymentSlipForm.getApplicationNo().isEmpty()){
			query=query+" and d.hlApplicationForm.admAppln.applnNo="+hostelPaymentSlipForm.getApplicationNo();
		}
		if(hostelPaymentSlipForm.getRegisterNo()!=null && !hostelPaymentSlipForm.getRegisterNo().isEmpty()){
			query=query+" and s.registerNo='"+hostelPaymentSlipForm.getRegisterNo()+"'";
		}
		if(hostelPaymentSlipForm.getRollNo()!=null && !hostelPaymentSlipForm.getRollNo().isEmpty()){
			query=query+" and s.rollNo='"+hostelPaymentSlipForm.getRollNo()+"'";
		}
		if(hostelPaymentSlipForm.getStaffId()!=null && !hostelPaymentSlipForm.getStaffId().isEmpty()){
			query=query+" and d.employee.code='"+hostelPaymentSlipForm.getStaffId()+"'";
		}
		return query;
	}

	/**
	 * @param hlDamage
	 * @return
	 * @throws Exception
	 */
	public List<HostelPaymentSlipTO> getBillNumbersToByFine(List<HlDamage> hlDamage) throws Exception {
		List<HostelPaymentSlipTO> billNoList=new ArrayList<HostelPaymentSlipTO>();
		HostelPaymentSlipTO paymentSlipTo=null;
		for(HlDamage form:hlDamage)
		{
			paymentSlipTo=new HostelPaymentSlipTO();
			paymentSlipTo.setHlApplicationFormId(form.getBillNo());
			if(form.getBillNo()!=null){
			paymentSlipTo.setSlipNo(String.valueOf(form.getBillNo()));
			billNoList.add(paymentSlipTo);
			}
		}
		
		return billNoList;
	}

	/**
	 * @param hostelPaymentSlipForm
	 * @return
	 * @throws Exception
	 */
	public String getHostelPaymentSlipQueryForFine(HostelPaymentSlipForm hostelPaymentSlipForm) throws Exception {
		String query="select d from HlDamage d where d.isActive=1 and d.isPaid=0 and d.billNo="+hostelPaymentSlipForm.getHlApplicationFormId();
		return query;
	}

	/**
	 * @param hlDamages
	 * @param hostelPaymentSlipForm
	 * @return
	 * @throws Exception
	 */
	public HostelPaymentSlipTO convertBotoToForFine(List<HlDamage> hlDamages,
			HostelPaymentSlipForm hostelPaymentSlipForm) throws Exception {
		
		HostelPaymentSlipTO hostelPaymentSlipTO=new HostelPaymentSlipTO();
		Iterator<HlDamage> itr=hlDamages.iterator();
		double fineTotalAmount=0.0;
		List<HostelFeeDetailsTO> hostelFeeDetailsTOList=new ArrayList<HostelFeeDetailsTO>();
		int count=0;
		while (itr.hasNext()) {
			HlDamage bo = (HlDamage) itr.next();
			if(count==0){
			HlApplicationForm hlApplicationForm=bo.getHlApplicationForm();
			if(hlApplicationForm!=null){
				hostelPaymentSlipTO.setHlApplicationFormId(hlApplicationForm.getId());
			}
			if(hlApplicationForm.getHlHostelByHlApprovedHostelId()!=null){
				if(hlApplicationForm.getHlHostelByHlApprovedHostelId().getId()>0){
					hostelPaymentSlipTO.setHostelName(hlApplicationForm.getHlHostelByHlApprovedHostelId().getName());
				}
			}
			 if(hlApplicationForm.getHlHostelByHlApprovedHostelId().getName()!=null){
				 	int hostelId=hlApplicationForm.getHlHostelByHlApprovedHostelId().getId();
					hostelPaymentSlipTO.setHostelId(hostelId);
			
			}
			if(hlApplicationForm.getRequisitionNo()!=null){
			   hostelPaymentSlipTO.setRequisitionNo(hlApplicationForm.getRequisitionNo()+"");	
			}
			hostelPaymentSlipTO.setSlipNo(String.valueOf(bo.getBillNo()));
			hostelPaymentSlipForm.setBillNo(String.valueOf(bo.getBillNo()));
			String studentName="";
			String staffName="";
			AdmAppln admAppln=hlApplicationForm.getAdmAppln();
			if(admAppln!=null){
				PersonalData personalData=admAppln.getPersonalData();
				if(personalData!=null)
				{
					if(personalData.getMiddleName() == null && personalData.getLastName() ==null){  
						studentName=personalData.getFirstName();                       // for getting studentName
					}else if(personalData.getLastName() ==null){
						studentName=personalData.getFirstName() +" "+ personalData.getMiddleName();
					}else if(personalData.getMiddleName() ==null){
						studentName=personalData.getFirstName() +" "+ personalData.getLastName();
					}
					else
					{
						studentName=personalData.getFirstName() +" "+ personalData.getMiddleName()+" "+personalData.getLastName();
					}
				}
			}
			if(hlApplicationForm.getEmployee()!=null)
			{
				
					staffName=hlApplicationForm.getEmployee().getFirstName() ;
				
			}
			
			hostelPaymentSlipForm.setDate(CommonUtil.getStringDate(new Date()));
			if(!hostelPaymentSlipForm.getApplicationNo().trim().isEmpty()||!hostelPaymentSlipForm.getRegisterNo().trim().isEmpty()||!hostelPaymentSlipForm.getRollNo().trim().isEmpty()){
				if(hlApplicationForm.getAdmAppln()!=null){
					hostelPaymentSlipForm.setStudentOrStaffId(hlApplicationForm.getAdmAppln().getApplnNo()+"");
					hostelPaymentSlipForm.setStudentOrStaffName(studentName);
				}
			}
			if(!hostelPaymentSlipForm.getStaffId().trim().isEmpty()){
				if(hlApplicationForm.getEmployee()!=null){	
					hostelPaymentSlipForm.setStudentOrStaffId(hlApplicationForm.getEmployee().getId()+"");
					hostelPaymentSlipForm.setStudentOrStaffName(staffName);
				}
			}
			}
			HostelFeeDetailsTO feeDetailsTO=new HostelFeeDetailsTO(); // for getting fine
			feeDetailsTO.setFineAmount(bo.getAmount()+"");
			if(bo.getDate()!=null){
				String date=CommonUtil.getStringDate(bo.getDate());
				feeDetailsTO.setFineDate(date);
				if(bo.getAmount()!=null){
					double fineAmount=bo.getAmount().doubleValue();
					fineTotalAmount=fineTotalAmount+fineAmount; 
					hostelPaymentSlipTO.setFeeTotalAmount(fineTotalAmount+"");
				}
				if(!StringUtils.isBlank(bo.getDescription())){
					feeDetailsTO.setFineDescription(bo.getDescription());	
					
				}
			}
			count=count+1;
			hostelFeeDetailsTOList.add(feeDetailsTO);
		}
		hostelPaymentSlipTO.setFeeDetailsTO(hostelFeeDetailsTOList);
		return hostelPaymentSlipTO;
	}
}
