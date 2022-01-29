package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentApplicantDetails;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.bo.admin.FeePaymentDetailFeegroup;
import com.kp.cms.bo.admin.FeePaymentOptionalFeeGroup;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.fee.BulkChallanPrintForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.fee.FeePaymentDetailFeeGroupTO;
import com.kp.cms.to.fee.FeePaymentTO;
import com.kp.cms.to.fee.PrintChalanTO;
import com.kp.cms.utilities.CommonUtil;

public class BulkChallanPrintHelper {
	/**
	 * Singleton object of BulkChallanPrintHelper
	 */
	/*private static volatile BulkChallanPrintHelper bulkChallanPrintHelper = null;
	private static final Log log = LogFactory.getLog(BulkChallanPrintHelper.class);
	private BulkChallanPrintHelper() {
		
	}
	*//**
	 * return singleton object of BulkChallanPrintHelper.
	 * @return
	 *//*
	public static BulkChallanPrintHelper getInstance() {
		if (bulkChallanPrintHelper == null) {
			bulkChallanPrintHelper = new BulkChallanPrintHelper();
		}
		return bulkChallanPrintHelper;
	}
	*//**
	 * converting the FeePaymentBO and setting to the form to print challan
	 * @param feePaymentBoList
	 * @return
	 * @throws Exception
	 *//*
	public boolean convertBOtoTO(List<FeePayment> feePaymentBoList,BulkChallanPrintForm bulkChallanPrintForm,HttpServletRequest request) throws Exception {
		boolean dataAvailable=false;
		List<FeePaymentTO> feeToList=null;
		if(feePaymentBoList!=null && !feePaymentBoList.isEmpty()){
			feeToList=new ArrayList<FeePaymentTO>();
			Iterator<FeePayment> itrFee= feePaymentBoList.iterator();
			while (itrFee.hasNext()) {
				FeePayment feePayment = (FeePayment) itrFee.next();
				FeePaymentTO feePaymentTo= new FeePaymentTO();
				
				if(feePayment.getStudent().getAdmAppln() != null){
					feePaymentTo.setApplicationNo(String.valueOf(feePayment.getStudent().getAdmAppln().getApplnNo()));
				}
				if(feePayment.getStudent().getRegisterNo()!=null && !feePayment.getStudent().getRegisterNo().isEmpty()){
					feePaymentTo.setApplicationNo(String.valueOf(feePayment.getStudent().getRegisterNo()));
				}
				feePaymentTo.setBillNo(feePayment.getBillNo());
				feePaymentTo.setChallenPrintedDate(CommonUtil.getStringDate(feePayment.getChallenPrintedDate()));
				//setting the verified by to form
				if(feePayment.getCreatedBy()!=null)
					feePaymentTo.setVerifiedBy(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(feePayment.getCreatedBy()),"Users",true,"userName"));
			
			//	feePaymentForm.setStudentName(feePayment.getStudentName());
				boolean isFirstYear=false;
				if(feePayment.getStudent()!=null){
					Student student = feePayment.getStudent();
					StringBuffer studentName = new StringBuffer(); 
					if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getFirstName() != null) {
						studentName.append(student.getAdmAppln().getPersonalData().getFirstName());
						studentName.append(" ");
					} 
					if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getMiddleName() != null) {
						studentName.append(student.getAdmAppln().getPersonalData().getMiddleName());
						studentName.append(" ");
					}
					if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getLastName() != null) {
						studentName.append(student.getAdmAppln().getPersonalData().getLastName());
						studentName.append(" ");
					}
					feePaymentTo.setStudentName(studentName.toString());
					Set<FeePaymentApplicantDetails>details= feePayment.getFeePaymentApplicantDetailses();
						Iterator<FeePaymentApplicantDetails>iterator=details.iterator();
						while(iterator.hasNext())
						{
							FeePaymentApplicantDetails applicantDetails=iterator.next();
							if(applicantDetails.getSemesterNo()==1)
							{
								isFirstYear=true;
								break;
							}	
						}
				}
				feePaymentTo.setInstallmentReferenceNo(feePayment.getInstallmentVoucherNo());
				feePaymentTo.setConcessionReferenceNo(feePayment.getConcessionVoucherNo());
				feePaymentTo.setScholarshipReferenceNo(feePayment.getSchlorshipVoucherNo());
				if(feePayment.getCurrency()!= null && feePayment.getCurrency().getCurrencyCode()!= null){
					feePaymentTo.setCurrencyCode(feePayment.getCurrency().getCurrencyCode());
				}
				
				if(feePayment.getStudent()!= null && feePayment.getStudent().getAdmAppln()!= null && feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId()!= null &&
						feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCode()!= null){
					String className="";
					if(isFirstYear){
						className="I ";
					}
					className=className+feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCode();
					feePaymentTo.setClassName(className);
				}
				if(feePayment.getStudent().getClassSchemewise()!=null && feePayment.getStudent().getClassSchemewise().getClasses()!=null)
				{
					feePaymentTo.setClassName(feePayment.getStudent().getClassSchemewise().getClasses().getName());
					if(feePaymentForm.getPucFeePayment())
					{
						String className=getClassName(feePayment);
						feePaymentForm.setClassName(className);
					}
				}
				
				if(feePayment.getChallanCreatedTime()!= null){
					feePaymentTo.setChalanCreatedTime(feePayment.getChallanCreatedTime().toString());
				}
				FeePaymentDetailFeeGroupTO feeDetailFeeGroupTO;
				FeePaymentDetailFeegroup feePaymentDetailFeegroup;
				
				HttpSession session = request.getSession(false);
				if(feePayment.getFeePaymentMode()!= null && feePayment.getFeePaymentMode().getName()!= null){
					feePaymentTo.setPaymentMode(feePayment.getFeePaymentMode().getName());
				}
				Set<Integer> sems = new HashSet<Integer>();
				String schemeNo = "";
				StringBuffer schemeBuf = new StringBuffer();
				if(feePayment.getFeePaymentApplicantDetailses().size() != 0) {
					Iterator<FeePaymentApplicantDetails> itr2 = feePayment.getFeePaymentApplicantDetailses().iterator();
					FeePaymentApplicantDetails applicantDetails;
					while(itr2.hasNext()) {
						 applicantDetails = itr2.next();
						 if(!sems.contains(applicantDetails.getSemesterNo())){
							 sems.add(applicantDetails.getSemesterNo());
							 schemeBuf.append(applicantDetails.getSemesterNo()).append('/');
						 }
					}
				}
				else if (feePayment.getFeePaymentOptionalFeeGroups().size() != 0){
					Iterator<FeePaymentOptionalFeeGroup> itr2 = feePayment.getFeePaymentOptionalFeeGroups().iterator();
					FeePaymentOptionalFeeGroup applicantDetails;
					while(itr2.hasNext()) {
						 applicantDetails = itr2.next();
						 if(!sems.contains(applicantDetails.getSemesterNo())){
							 sems.add(applicantDetails.getSemesterNo());
							 schemeBuf.append(applicantDetails.getSemesterNo()).append('/');
						 }
					}
				}
				
				int length = schemeBuf.length();
				schemeNo = schemeBuf.substring(0, length-1);
				feePaymentTo.setSemester(schemeNo);
				
				Set<FeePaymentDetail> feePaymentList = feePayment.getFeePaymentDetails();
				Iterator<FeePaymentDetail> itr = feePaymentList.iterator();
				Map<Integer,Double> accountWiseNonOptionalAmount = new HashMap<Integer,Double>();
				Map<Integer,Double> accountWiseOptionalAmount = new HashMap<Integer,Double>();
				Map<Integer,Double> accountWiseTotalAmount = new HashMap<Integer,Double>();
				Map<Integer,Double> accountWiseConcessionAmount = new HashMap<Integer,Double>();
				Map<Integer,String> allFeeAccountMap = new HashMap<Integer,String>();
				List<PrintChalanTO> printChalanList = new ArrayList<PrintChalanTO>();
				PrintChalanTO printChalanTO;
				List<String> descList;
				List<String> bankInfoList;
				int totalAmt = 0;
				FeePaymentDetail feePaymentDetail;
				int count = 1;
				StringBuffer accwiseTotalString = new StringBuffer();
				Double grandTotal = 0.00;
				List<Integer> currList = new ArrayList<Integer>();
				while(itr.hasNext()) {
					totalAmt = 0;
					feePaymentDetail = itr.next();
					if(feePaymentDetail.getIsInstallmentPayment()!= null && feePaymentDetail.getIsInstallmentPayment()){
						continue;
					}
					
					allFeeAccountMap.put(feePaymentDetail.getFeeAccount().getId(), (feePaymentDetail.getFeeAccount().getName()+"("+feePaymentDetail.getFeeAccount().getCode()+")"));
					if(feePaymentDetail.getTotalNonAdditionalAmount() != null) {
						accountWiseNonOptionalAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getTotalNonAdditionalAmount().doubleValue());
					}	
					if(feePaymentDetail.getTotalAdditionalAmount() != null) {
						accountWiseOptionalAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getTotalAdditionalAmount().doubleValue());
					}	
					accountWiseTotalAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getTotalAmount().doubleValue());
					accountWiseConcessionAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getConcessionAmount().doubleValue());

					printChalanTO = new PrintChalanTO();
					printChalanTO.setPrintAccountName(feePaymentDetail.getFeeAccount().getPrintAccountName());
					if(feePaymentDetail.getFeeAccount().getCode().equalsIgnoreCase("993"))
						printChalanTO.setAccountName("Christ Educational Society");
						else printChalanTO.setAccountName("Christ University");
					printChalanTO.setAccId(feePaymentDetail.getFeeAccount().getId());
					if(feePaymentDetail.getTotalNonAdditionalAmount() != null) {
						printChalanTO.setNonAdditionalAmount(Integer.toString(feePaymentDetail.getTotalNonAdditionalAmount().intValue()));
						totalAmt = feePaymentDetail.getTotalNonAdditionalAmount().intValue(); 
					}
					if(feePaymentDetail.getTotalAdditionalAmount() != null) {
						printChalanTO.setAdditionalAMount(Integer.toString(feePaymentDetail.getTotalAdditionalAmount().intValue()));
						totalAmt = totalAmt + feePaymentDetail.getTotalAdditionalAmount().intValue(); 
					}
					if(feePaymentDetail.getTotalAmount()!= null) {
						printChalanTO.setTotalAmount(Integer.toString(feePaymentDetail.getTotalAmount().intValue()));
					}
					if(feePaymentDetail.getFeeAccount()!= null && feePaymentDetail.getFeeAccount().getBankInformation()!= null){
						printChalanTO.setBankInfo(feePaymentDetail.getFeeAccount().getBankInformation());
					}
					
					if(feePaymentDetail.getCurrency()!= null){
						printChalanTO.setCurrencyCode(feePaymentDetail.getCurrency().getCurrencyCode());
					}
					//------
					Iterator<FeePaymentDetailFeegroup> addItr = feePaymentDetail.getFeePaymentDetailFeegroups().iterator();
					List<FeePaymentDetailFeeGroupTO> additionalList = new ArrayList<FeePaymentDetailFeeGroupTO>();
					while(addItr.hasNext()){
						feePaymentDetailFeegroup = addItr.next();
						if(!feePaymentDetailFeegroup.getIsOptional()){
							continue;
						}
						feeDetailFeeGroupTO = new FeePaymentDetailFeeGroupTO();
						feeDetailFeeGroupTO.setFeeGroupId(feePaymentDetailFeegroup.getFeeGroup().getId());
						feeDetailFeeGroupTO.setFeeGroupName(feePaymentDetailFeegroup.getFeeGroup().getName());
						feeDetailFeeGroupTO.setAmount(Integer.toString(feePaymentDetailFeegroup.getAmount().intValue()));
						additionalList.add(feeDetailFeeGroupTO);
					}
					//-----------
					Collections.sort(additionalList);
					printChalanTO.setAdditionalList(additionalList);
					
					String verified = "";
					if(feePaymentDetail.getFeeAccount()!= null && feePaymentDetail.getFeeAccount().getVerifiedBy()!= null){
						printChalanTO.setVerified(feePaymentDetail.getFeeAccount().getVerifiedBy());
						verified = feePaymentDetail.getFeeAccount().getVerifiedBy();
						StringTokenizer token = new StringTokenizer(verified, " "); 
						List<String> verifiedList = new ArrayList<String>();
						while(token.hasMoreTokens()) {
							String val = token.nextToken(); 
							verifiedList.add(val);
						}
						printChalanTO.setVerifiedList(verifiedList);
					}
					
					
					int deducationAmt = 0; 

					if(feePaymentDetail.getConcessionAmount()!= null && feePaymentDetail.getConcessionAmount().doubleValue() > 0){
						printChalanTO.setConcessionAmt(Integer.toString(feePaymentDetail.getConcessionAmount().intValue()));
						deducationAmt = feePaymentDetail.getConcessionAmount().intValue(); 
						printChalanTO.setIsConcession(true);
					}
					if(feePaymentDetail.getInstallmentAmount()!= null && feePaymentDetail.getInstallmentAmount().doubleValue() > 0){
						printChalanTO.setInstallmentAmt(Integer.toString(feePaymentDetail.getInstallmentAmount().intValue()));
						deducationAmt = deducationAmt + feePaymentDetail.getInstallmentAmount().intValue();
						printChalanTO.setIsInstallment(true);
					}
					if(feePaymentDetail.getSchlorshipAmount()!= null && feePaymentDetail.getSchlorshipAmount().doubleValue() > 0){
						printChalanTO.setScholarShipAmt(Integer.toString(feePaymentDetail.getSchlorshipAmount().intValue()));
						deducationAmt = deducationAmt + feePaymentDetail.getSchlorshipAmount().intValue();
						printChalanTO.setIsScholarShipAmt(true);
					}
					
					
					
					int netAmt = totalAmt - deducationAmt;
					printChalanTO.setNetAmount(Integer.toString(netAmt));
					String amtInWord = CommonUtil.numberToWord(netAmt) + " Only ";
					
					if(feePaymentDetail.getFeePayment().getCurrency()!= null && feePaymentDetail.getFeePayment().getCurrency().getCurrencyCode()!= null){
							amtInWord = amtInWord + "(IN " + feePaymentDetail.getCurrency().getCurrencyCode() + ")";	
						if(feePaymentDetail.getCurrency()!= null && !currList.contains(feePaymentDetail.getCurrency().getId())){
							currList.add(feePaymentDetail.getCurrency().getId());
						}
					}
					printChalanTO.setAmountInWord(amtInWord);
					printChalanTO.setLogoBytes(feePaymentDetail.getFeeAccount().getLogo());
					printChalanTO.setCount(count);


					if(netAmt!= 0){
						if(!accwiseTotalString.toString().trim().isEmpty()){
							accwiseTotalString.append(" + ");
						}
						accwiseTotalString.append(Integer.toString(netAmt));
					}
					grandTotal = grandTotal + netAmt;
					descList = new ArrayList<String>();
					bankInfoList = new ArrayList<String>();
				    char slashN='\n';
				    char slashR='\r';
					if(feePaymentDetail.getFeeAccount()!= null && feePaymentDetail.getFeeAccount().getDescription1()!= null){
						printChalanTO.setFeeDesc(feePaymentDetail.getFeeAccount().getDescription1());
						String desc = String.valueOf(feePaymentDetail.getFeeAccount().getDescription1());
						char[] descCharArray = desc.toCharArray();
					    	
					    StringBuffer descString = new StringBuffer();  
					    if(descCharArray!= null){
					    	for(int i = 0; i< descCharArray.length;i++){
					    		char descChar = descCharArray[i];
					    		if(descChar == slashR){
					    			descString.append("$");
					    		}
					    		else if(descChar != slashN){
					    			descString.append(descCharArray[i]);
					    		}
					    	}
					    }
					    if(descString!= null){
						 	StringTokenizer st = new StringTokenizer(descString.toString(), "$"); 
							
							while(st.hasMoreTokens()) {
								String val = st.nextToken(); 
								descList.add(val);
							}
					    }
					    printChalanTO.setDescList(descList);
					}
					descList = new ArrayList<String>();
					if(feePaymentDetail.getFeeAccount()!= null && feePaymentDetail.getFeeAccount().getDescription2()!= null){
						printChalanTO.setFeeDesc(feePaymentDetail.getFeeAccount().getDescription2());
						String desc = String.valueOf(feePaymentDetail.getFeeAccount().getDescription2());
						char[] descCharArray = desc.toCharArray();
					    	
					    StringBuffer descString = new StringBuffer();  
					    if(descCharArray!= null){
					    	for(int i = 0; i< descCharArray.length;i++){
					    		char descChar = descCharArray[i];
					    		if(descChar == slashR){
					    			descString.append("$");
					    		}
					    		else if(descChar != slashN){
					    			descString.append(descCharArray[i]);
					    		}
					    	}
					    }
					    if(descString!= null){
						 	StringTokenizer st = new StringTokenizer(descString.toString(), "$"); 
							
							while(st.hasMoreTokens()) {
								String val = st.nextToken(); 
								descList.add(val);
							}
					    }
					    printChalanTO.setDesc2List(descList);
					}
					
					if(feePaymentDetail.getFeeAccount()!= null && feePaymentDetail.getFeeAccount().getBankInformation()!= null){
						String bankInfo = String.valueOf(feePaymentDetail.getFeeAccount().getBankInformation());
						char[] bankCharArray = bankInfo.toCharArray();
					    	
					    StringBuffer bankInfoString = new StringBuffer();  
					    if(bankCharArray.length>0){
					    	for(int i = 0; i< bankCharArray.length;i++){
					    		char bankCharChar = bankCharArray[i];
					    		if(bankCharChar == slashR){
					    			bankInfoString.append("$");
					    		}
					    		else if(bankCharChar != slashN){
					    			bankInfoString.append(bankCharArray[i]);
					    		}
					    	}
					    }
					    if(!bankInfoString.toString().isEmpty()){
						 	StringTokenizer st = new StringTokenizer(bankInfoString.toString(), "$"); 
							
							while(st.hasMoreTokens()) {
								String bankVal = st.nextToken(); 
								bankInfoList.add(bankVal);
							}
					    }
					    printChalanTO.setBankInfoList(bankInfoList);
						
					}
					session.setAttribute("image_" + count, printChalanTO);
					count++;
					
				    printChalanList.add(printChalanTO);
				}
				if(currList.size() <= 1){
					feePaymentTo.setAccwiseTotalPrintString(accwiseTotalString.toString() + " = " + Integer.toString(grandTotal.intValue()));
				}
				feePaymentTo.setAccountWiseNonOptionalAmount(accountWiseNonOptionalAmount);
				feePaymentTo.setAccountWiseOptionalAmount(accountWiseOptionalAmount);
				feePaymentTo.setAllFeeAccountMap(allFeeAccountMap);
				feePaymentTo.setFullAccountWiseTotal(accountWiseTotalAmount);
				feePaymentTo.setPrintChalanList(printChalanList);
				if(printChalanList!= null ){
					if(printChalanList.size() == 1){
						feePaymentTo.setSinglePrint(true);
					}
					else
					{
						feePaymentTo.setSinglePrint(false);
					}
						
				}
				Collections.sort(printChalanList);
				if(printChalanList!=null){
					feePaymentTo.setLastNo(printChalanList.size()-1);
				} 
				feeToList.add(feePaymentTo);
			}
			bulkChallanPrintForm.setFeeToList(feeToList);
			dataAvailable=true;
		}
		return dataAvailable;
	}*/
}
