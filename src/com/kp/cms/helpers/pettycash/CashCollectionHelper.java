package com.kp.cms.helpers.pettycash;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.pettycash.CashCollectionForm;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.CashCollectionTO;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class CashCollectionHelper {
	private static Log log = LogFactory.getLog(CashCollectionHelper.class);
	private static Map<Integer, String> timeMap = null;
	
	static {
		timeMap = new HashMap<Integer, String>();
		timeMap.put(0, "12");
		timeMap.put(1, "13");
		timeMap.put(2, "14");
		timeMap.put(3, "15");
		timeMap.put(4, "16");
		timeMap.put(5, "17");
		timeMap.put(6, "18");
		timeMap.put(7, "19");
		timeMap.put(8, "20");
		timeMap.put(9, "21");
		timeMap.put(10, "22");
		timeMap.put(11, "23");
	}
	
	
	public static volatile CashCollectionHelper cashCollectionHelper = null;
	
	public static CashCollectionHelper getInstance()
	{
		if(cashCollectionHelper==null)
		{
		cashCollectionHelper = new CashCollectionHelper();
		return cashCollectionHelper;
		}
		return cashCollectionHelper;
	}
	
	public List<FinancialYearTO>getFinancialYear(List<PcFinancialYear> finanicalYearBo)
	{
	log.info("inside getFincanicalYear method of CashCollectionHelper class" );
	List<FinancialYearTO> singleFieldList = new ArrayList<FinancialYearTO>() ;
	//= new ArrayList<String>();
	Iterator yearIteratorIt = finanicalYearBo.iterator();
	FinancialYearTO finanicalYearTo=null;
	while(yearIteratorIt.hasNext())
	{
		finanicalYearTo = new FinancialYearTO();
		PcFinancialYear finYearBo = new PcFinancialYear();
		finYearBo.setFinancialYear((String)yearIteratorIt.next());
		log.info("trying to store into the to from bo in helper");
		log.info("first year in the Bo after conversion from bo"+finYearBo.getFinancialYear());
		if(finYearBo.getFinancialYear()!= null)
		finanicalYearTo.setFinancialYear(finYearBo.getFinancialYear());
		singleFieldList.add(finanicalYearTo) ;
	}
	log.info("leaving getFincanicalYear method of CashCollectionHelper class" );
	return singleFieldList;	
	}
	
	public List<CashCollectionTO> convertAccDetailsToTo(List<PcAccountHead> accDetilasBoList, CashCollectionForm cashCollectionform) throws Exception
	{
		Map<Integer, CashCollectionTO> accountMap = new HashMap<Integer, CashCollectionTO>();
		List<CashCollectionTO> accNameWithCodeToList = new ArrayList<CashCollectionTO>();
		if(accDetilasBoList!=null && !accDetilasBoList.isEmpty())
		{
			CashCollectionTO cashCollectionTo=null;
			Iterator<PcAccountHead> accIt = accDetilasBoList.iterator();
			while (accIt.hasNext()) {
				PcAccountHead pcAccountHead = (PcAccountHead) accIt.next();
				cashCollectionTo = new CashCollectionTO();
				if(pcAccountHead.getAccCode()!= null)
				cashCollectionTo.setAccountCode(pcAccountHead.getAccCode());
				if(pcAccountHead.getAccName()!=null)
				cashCollectionTo.setAccName(pcAccountHead.getAccName());
				if(pcAccountHead.getAmount()!=null)
				cashCollectionTo.setAmount(String.valueOf(pcAccountHead.getAmount()));
				cashCollectionTo.setId(pcAccountHead.getId());
				cashCollectionTo.setNameWithCode(pcAccountHead.getAccName()+"("+pcAccountHead.getAccCode()+")");
				accNameWithCodeToList.add(cashCollectionTo);
				accountMap.put(pcAccountHead.getId(), cashCollectionTo);
			}

		}
		cashCollectionform.setAccountMap(accountMap);
		
		return accNameWithCodeToList;
	}
	
	public Integer getLastReceiptNumber(String lastReceiptNumber) throws Exception
	{
		 Integer newReceiptNumber =null;
		
		Calendar calendar = new GregorianCalendar();
	    
	    Integer year = calendar.get(Calendar.YEAR);
	    ICashCollectionTransaction cashCollectiontransaction = CashCollectionTransactionImpl.getInstance();
		
		//List<PcReceipts> cashCollectionBolist =cashCollectiontransaction.getLastReceiptNumber();
	
	    List<Object[]> getStartandEndDate = cashCollectiontransaction.getStartAndEndDate(year);
	    Iterator<Object[]> it = getStartandEndDate.iterator();
		Object[] temp =null;
		String stratingNo=null;
		String endingNo=null;
		
	    while(it.hasNext())
	    {
	    	temp = it.next();
	    	
	    }
	    if(temp!=null && temp.length!=0)
	    {
	    	stratingNo = (String)temp[0];
	    	endingNo = (String)temp[1];
	    	
		    if(lastReceiptNumber!= null && StringUtils.isNumeric(lastReceiptNumber)&&stratingNo!= null && StringUtils.isNumeric(stratingNo)&&endingNo!= null && StringUtils.isNumeric(endingNo)&& Integer.valueOf(lastReceiptNumber)>=Integer.valueOf(stratingNo) && Integer.valueOf(lastReceiptNumber)<=Integer.valueOf(endingNo))
		    {
		    	return newReceiptNumber = Integer.valueOf(lastReceiptNumber)+1;
		    }
		     return newReceiptNumber;
	    }
	    else 
	    	return newReceiptNumber;
	
	}

public AccountHeadTO convertAmountsListToTo(List amountDetilasList) throws Exception
	{
		String amount=null;
		//List<CashCollectionTO> amountDetailsTOList = new ArrayList<CashCollectionTO>();
		AccountHeadTO accTo =new AccountHeadTO();
		if(amountDetilasList!=null && !amountDetilasList.isEmpty())
		{
			
			log.info("entered into  getDetails..");
		
			Iterator boit= amountDetilasList.iterator();
			
			while(boit.hasNext())
			{
				Object[] object =(Object[]) boit.next();
				
					if (object[0]!=null) {
						accTo.setAmount(object[0].toString());
					}
					if (object[1]!=null) {
						accTo.setIsFixed(object[1].toString());
					}
				
				
			}
				
				
		}
			
			return accTo;
			
		
	}

/*
 * This method retrives all details about partiular fine from the combine feild
 */

public CashCollectionTO getAccnameAndCode(CashCollectionForm cashCollectionForm) throws Exception
{
	
	//List<CashCollectionTO> amountDetailsTOList = new ArrayList<CashCollectionTO>();
	//implements the logic to seperate account code and accName from the cashCollectionTo.getAccoCodeWithName
	CashCollectionTO collectionTo = new CashCollectionTO();
	if(cashCollectionForm!=null)
	{
		
		collectionTo.setAccId(cashCollectionForm.getAccId());
		
		
		
	}
return collectionTo;
}

	public  String commonSearch(CashCollectionForm cashCollectionForm) {
	log.info("entered commonSearch method ");
	String query=null;
	String number=cashCollectionForm.getAppNo();
	
	if(number.equals("1"))
	{
		query="from Student st where st.admAppln.appliedYear ="+cashCollectionForm.getFinYearId()+" and st.admAppln.applnNo ="+Integer.parseInt(cashCollectionForm.getAppRegRollno());
		return query;
	}
	else if(number.equals("3"))
	{
		query="from Student st where st.registerNo='"+cashCollectionForm.getAppRegRollno().trim()+"'";	
		return query;
	}
	else if(number.equals("2"))
	{
		query="from Student st where st.rollNo='"+cashCollectionForm.getAppRegRollno().trim()+"'";	
		return query;
	}
	
	return query;
}


public String convertStudentnameBoToTo(List<PersonalData> personneldataBo)
{
Iterator<PersonalData> boit =personneldataBo.iterator();
//PersonalDataTO personnelTo = new PersonalDataTO();
PersonalData data=null;
String studentName =null;
String studentId;
while(boit.hasNext())
{
	data=boit.next();
	
 studentName = data.getFirstName()+data.getMiddleName()+data.getLastName();
	//studentId = data.

}
return studentName;

}


public boolean getUserPrivilage(String userId) throws Exception
{
	boolean canEdit=false;
	ICashCollectionTransaction cashCollectionTrxImpl = new CashCollectionTransactionImpl();
	List<Object[]> userDetails = cashCollectionTrxImpl.getUserPrivilage(userId) ;
	Iterator<Object[]> listIt = userDetails.iterator();
	String roleName=null;
	String roleId =null;
	String userName =null;
	while(listIt.hasNext())
	{
		Object[] users = listIt.next();
		/*if(users!=null && (users.length>0 && users.length <=1))
		{*/
		 roleName = (String)users[0];
		roleId = users[1].toString();
		userName =(String)users[2];
		//}
	}
	if(roleName.equals("Developer")||roleName.equals("Administrator")||roleName.equals("General"));
	{
		canEdit = true;	
	}
	return canEdit;
}


public CashCollectionTO getCurrentTimenadDate(CashCollectionForm collectionForm) throws Exception
{
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	java.util.Date dates = new java.util.Date();

	//collectionsReportForm.setToday(dateFormat.format(dates));


	
	Calendar calendar = new GregorianCalendar();
   
    String paidDate = dateFormat.format(dates);
    Integer hour = calendar.get(Calendar.HOUR);
    Integer minutes = calendar.get(Calendar.MINUTE);
    int am_pm = calendar.get(Calendar.AM_PM);
    CashCollectionTO collectionTo = new CashCollectionTO();
    collectionTo.setPaidDate(paidDate);
    collectionTo.setHour(hour.toString());
    if(am_pm == 0){
    	collectionTo.setMinutes(minutes.toString().concat(" ").concat("AM"));
    }else{
    	collectionTo.setMinutes(minutes.toString().concat(" ").concat("PM"));
    	collectionTo.setHour(timeMap.get(hour));
    }
    return collectionTo;
	
}



public List<PcReceipts> prepareBoObjectsToStore(CashCollectionForm collectionForm,PcFinancialYear financialYearBo,Student student,Users user) throws Exception
{
	List<CashCollectionTO> toList = collectionForm.getAccountList();
	List<PcAccountHead> accountHeadlist = new ArrayList<PcAccountHead>();
//	String hour = collectionForm.getHour();
//	String minutes = collectionForm.getMinutes().substring(0,2).trim();
//	String date = collectionForm.getPaidDate();
//	int last_ofM = collectionForm.getMinutes().lastIndexOf("M");
//	String am_pm = collectionForm.getMinutes().substring(last_ofM - 1, last_ofM + 1);
//	Calendar cal = Calendar.getInstance();
//	cal.setTime(CommonUtil.ConvertStringToSQLDate(date));
//	cal.set(Calendar.HOUR,Integer.valueOf(hour));
//	cal.set(Calendar.MINUTE, Integer.valueOf(minutes));
//	cal.set(Calendar.SECOND, 0);
//	if(am_pm.equalsIgnoreCase("AM"))
//		cal.set(Calendar.AM_PM,0);
//	else if(am_pm.equalsIgnoreCase("PM"))
//		cal.set(Calendar.AM_PM,1);
//	java.util.Date paidDate = cal.getTime();
	
	Calendar cal = Calendar.getInstance();
	String finalTime =collectionForm.getPaidDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);

	java.util.Date paidDate = CommonUtil.ConvertStringToSQLDateTime(finalTime);
	
	List<PcReceipts> collectionsBoList = new ArrayList<PcReceipts>();
	if((toList!=null && !toList.isEmpty()) && financialYearBo!=null)
	{
		Iterator<CashCollectionTO> toIt = toList.iterator();
		while(toIt.hasNext())
		{
			
			PcAccountHead pcAccountHead = new PcAccountHead();
			CashCollectionTO collectionTo = toIt.next();
			if(collectionTo.getId()!= null)
			pcAccountHead.setId(collectionTo.getId());
			if(collectionTo.getAccountCode()!=null)
			pcAccountHead.setAccCode(collectionTo.getAccountCode());
			if(collectionTo.getAccName()!= null)
			pcAccountHead.setAccName(collectionTo.getAccName());
			if(collectionTo.getAmount()!=null)
			pcAccountHead.setAmount(BigDecimal.valueOf(Double.parseDouble(collectionTo.getAmount())));
			pcAccountHead.setDetails(collectionTo.getDetails());
			accountHeadlist.add(pcAccountHead);
			
		}
	}
		if(!accountHeadlist.isEmpty()){
		Iterator<PcAccountHead> accountIt = accountHeadlist.iterator();
		
		while(accountIt.hasNext())
		{
			PcAccountHead accountHead = accountIt.next();
			
			PcReceipts pcReceipts = new PcReceipts();
			
			//getting the value
//			pcReceipts.setNumber(Integer.parseInt(collectionForm.getNumber()));
			pcReceipts.setPaidDate(paidDate);
			if(collectionForm.getAppNo().equals("1"))
			{
				pcReceipts.setRefType("appNo");
			}
			else if(collectionForm.getAppNo().equals("2"))
			{
				pcReceipts.setRefType("rollNo");
			}
			else if(collectionForm.getList()!=null && !collectionForm.getList().isEmpty())
			{
				pcReceipts.setRefType("offLineApplNo");
			}else
				pcReceipts.setRefType("regNo");
			
			pcReceipts.setRefNo(collectionForm.getAppRegRollno());
			pcReceipts.setApplnNo(collectionForm.getAppNo());
			pcReceipts.setRegNo(collectionForm.getRegNo());
			if(collectionForm.getName().trim()!=null && !collectionForm.getName().trim().isEmpty()){
			pcReceipts.setName(collectionForm.getName().trim());
			}
			pcReceipts.setPcAccountHead(accountHead);
			pcReceipts.setAcademicYear(Integer.parseInt(collectionForm.getFinYearId()));
			pcReceipts.setStudent(student);
			pcReceipts.setUsers(user);
			pcReceipts.setName(collectionForm.getName());
			pcReceipts.setPcFinancialYear(financialYearBo);
	
			pcReceipts.setCreatedDate(new java.util.Date());
			pcReceipts.setLastModifiedDate(new java.util.Date());
			pcReceipts.setCreatedBy(collectionForm.getUserId());
			pcReceipts.setModifiedBy(collectionForm.getUserId());
			
			pcReceipts.setAmount(accountHead.getAmount());
			pcReceipts.setDetails(accountHead.getDetails());
			pcReceipts.setIsCancelled(false);
			pcReceipts.setCancelComments("");
			pcReceipts.setIsActive(true);
			if(collectionForm.getList()!=null && !collectionForm.getList().isEmpty())
			{
				pcReceipts.setOffLineApplication(true);
			}else{
				pcReceipts.setOffLineApplication(false);
			}
			
			collectionsBoList.add(pcReceipts);
		}
	}
	

	return collectionsBoList;
}
//if user does not enter any appl/roll/reg no and only eneters name this method will be called.
public List<PcReceipts> prepareBoObjectsToStore(CashCollectionForm collectionForm,PcFinancialYear financialYearBo,String studentName,Users user) throws Exception
{
	boolean canEdit=false;
	
	
	List<CashCollectionTO> toList = collectionForm.getAccountList();
	
	
	List<PcAccountHead> accountHeadlist = new ArrayList<PcAccountHead>();
	
//	String hour = collectionForm.getHour();
//	String minutes = collectionForm.getMinutes().substring(0,2).trim();
//	String date = collectionForm.getPaidDate();
//	int last_ofM = collectionForm.getMinutes().lastIndexOf("M");
//	String am_pm = collectionForm.getMinutes().substring(last_ofM - 1, last_ofM + 1);
//	Calendar cal = Calendar.getInstance();
//	cal.setTime(CommonUtil.ConvertStringToSQLDateTime(date));
//	cal.set(Calendar.HOUR,Integer.valueOf(hour));
//	cal.set(Calendar.MINUTE, Integer.valueOf(minutes));
//	cal.set(Calendar.SECOND, 0);
//	if(am_pm.equalsIgnoreCase("AM"))
//		cal.set(Calendar.AM_PM,0);
//	else if(am_pm.equalsIgnoreCase("PM"))
//		cal.set(Calendar.AM_PM,1);
	
	Calendar cal = Calendar.getInstance();
	String finalTime =collectionForm.getPaidDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);

	java.util.Date paidDate = CommonUtil.ConvertStringToSQLDateTime(finalTime);
	List<PcReceipts> collectionsBoList = new ArrayList<PcReceipts>();
	if((toList!=null && !toList.isEmpty()) && financialYearBo!=null)
	{
		Iterator<CashCollectionTO> toIt = toList.iterator();
		while(toIt.hasNext())
		{
			
			PcAccountHead pcAccountHead = new PcAccountHead();
			CashCollectionTO collectionTo = toIt.next();
			pcAccountHead.setId(collectionTo.getId());
			pcAccountHead.setAccCode(collectionTo.getAccountCode());
			pcAccountHead.setAccName(collectionTo.getAccName());
			if(CommonUtil.isValidDecimal(collectionTo.getAmount()))
			pcAccountHead.setAmount(BigDecimal.valueOf(Double.parseDouble(collectionTo.getAmount())));
			pcAccountHead.setDetails(collectionTo.getDetails());
			accountHeadlist.add(pcAccountHead);
			
		}
	}
		if(!accountHeadlist.isEmpty()){
		Iterator<PcAccountHead> accountIt = accountHeadlist.iterator();
		
		while(accountIt.hasNext())
		{
			PcAccountHead accountHead = accountIt.next();
			
			PcReceipts pcReceipts = new PcReceipts();
			//getting the value
			if(collectionForm.getNumber()!=null)
			pcReceipts.setNumber(Integer.parseInt(collectionForm.getNumber()));
			if(collectionForm.getPaidDate()!=null)
			pcReceipts.setPaidDate(paidDate);
			if(collectionForm.getAppNo()!=null && !collectionForm.getAppNo().isEmpty() )
			{
				if(collectionForm.getAppNo().equals("1"))
			{
				pcReceipts.setRefType("appNo");
			}
			else if(collectionForm.getAppNo().equals("2"))
			{
				pcReceipts.setRefType("rollNo");
			}
			else if(collectionForm.getAppNo().equals("3"))
			{
				pcReceipts.setRefType("regNo");
			}
			/*else
				pcReceipts.setRefType("");*/
			}
			/*else
			{
				pcReceipts.setRefType("");	
			}*/
			if(collectionForm.getAppNo()!=null && !collectionForm.getAppNo().isEmpty())
			{
			pcReceipts.setRefNo(collectionForm.getAppRegRollno());
			}
			if(collectionForm.getRegNo()!=null && !collectionForm.getRegNo().isEmpty())
			{
				pcReceipts.setRegNo(collectionForm.getRegNo());
				}
			/*else
			{
				pcReceipts.setRefNo("");	
			}*/
			if(pcReceipts.getName()!=null)
			pcReceipts.setName(collectionForm.getName().trim());
			if(accountHead!=null)
			pcReceipts.setPcAccountHead(accountHead);
			if(collectionForm.getFinYearId()!=null)
			pcReceipts.setAcademicYear(Integer.parseInt(collectionForm.getFinYearId()));
			if(user!=null)
			pcReceipts.setUsers(user);
			if(studentName!=null)
			pcReceipts.setName(studentName);
			if(financialYearBo!=null)
			pcReceipts.setPcFinancialYear(financialYearBo);
			//pcReceipts.set
			pcReceipts.setCreatedDate(new java.util.Date());
			pcReceipts.setLastModifiedDate(new java.util.Date());
			if(collectionForm.getUserId()!=null)
			pcReceipts.setCreatedBy(collectionForm.getUserId());
			if(collectionForm.getUserId()!=null)
			pcReceipts.setModifiedBy(collectionForm.getUserId());
			pcReceipts.setAmount(accountHead.getAmount());
			pcReceipts.setDetails(accountHead.getDetails());
			pcReceipts.setIsCancelled(false);
			pcReceipts.setCancelComments("");
			pcReceipts.setIsActive(true);
			
			collectionsBoList.add(pcReceipts);
		}
	}
	

	return collectionsBoList;
}
//printing of receipts related
public List<FinancialYearTO> convertToListToBo(List<PcFinancialYear> finanicalYearListBo) throws Exception
{
	log.info("Start of convertToListToBo of CashCollectionHelper");
	List<FinancialYearTO> financialYearTolist = new ArrayList<FinancialYearTO>();
	if(finanicalYearListBo!=null && !finanicalYearListBo.isEmpty() )
	{	
	Iterator<PcFinancialYear> it = finanicalYearListBo.iterator();
		while(it.hasNext())
		{
		PcFinancialYear pcfinancailYear = it.next();
		FinancialYearTO yearTo = new FinancialYearTO();
		yearTo.setId(pcfinancailYear.getId());
		yearTo.setFinancialYear(pcfinancailYear.getFinancialYear());
		financialYearTolist.add(yearTo);
		}
	}
	log.info("Exiting convertToListToBo of CashCollectionHelper");
	return financialYearTolist;
}

	public void setReceiptNumberDatatoForm(List<PcReceipts> pcReceiptList,CashCollectionForm cashCollectionForm) throws Exception
	{
		
		log.info("Start of setReceiptNumberDatatoForm of CashCollectionHelper");
		List<CashCollectionTO> cashCollectionToList = new ArrayList<CashCollectionTO>();
		double total=0.00;
		String time="";
		int size=0;
		Iterator<PcReceipts> it = pcReceiptList.iterator();
			while(it.hasNext())	
			{
				PcReceipts pcReceipts=it.next();
				CashCollectionTO collectionTo = new CashCollectionTO();
				collectionTo.setId(pcReceipts.getId());
				cashCollectionForm.setId(Integer.toString(pcReceipts.getId()));
				
				collectionTo.setNumber(String.valueOf(pcReceipts.getNumber()));
				cashCollectionForm.setNumber(String.valueOf(pcReceipts.getNumber()));
				cashCollectionForm.setOldRecNo(String.valueOf(pcReceipts.getNumber()));
				collectionTo.setName(pcReceipts.getName());
				cashCollectionForm.setNameOfStudent(pcReceipts.getName());
				collectionTo.setPaidDate(String.valueOf(pcReceipts.getPaidDate()));
				//cashCollectionForm.setPaidDate(String.valueOf(pcReceipts.getPaidDate()));
				if(pcReceipts.getPaidDate()!=null){
					time = String.valueOf(pcReceipts.getPaidDate());
				}
				//collectionTo.setPaidDate(pcReceipts.getPaidDate().toString());
				//cashCollectionForm.setPaidDate(pcReceipts.getPaidDate().toString());
				//time to be conerted to hour and minute
				collectionTo.setRefNo(pcReceipts.getRefNo());
				cashCollectionForm.setRefNo(pcReceipts.getRefNo());
				
				collectionTo.setReftype(pcReceipts.getRefType());
				cashCollectionForm.setRefType(pcReceipts.getRefType());
				
				if(pcReceipts.getPcAccountHead()!=null && pcReceipts.getPcAccountHead().getAccName()!=null){
				collectionTo.setAccName(pcReceipts.getPcAccountHead().getAccName());
				cashCollectionForm.setAccName(pcReceipts.getPcAccountHead().getAccName());
				}
				if(pcReceipts.getPcAccountHead()!=null && pcReceipts.getPcAccountHead().getAccCode()!=null){
					collectionTo.setAccountCode(pcReceipts.getPcAccountHead().getAccCode());
					cashCollectionForm.setAccName(pcReceipts.getPcAccountHead().getAccName());
				}
				if(pcReceipts.getAmount()!=null){
					collectionTo.setAmount(pcReceipts.getAmount().toString());
					total = total + Double.valueOf(pcReceipts.getAmount().doubleValue());
				}
				//cashCollectionForm.setAmount(pcReceipts.getAmount().toString());
				
				if(pcReceipts.getUsers()!=null && pcReceipts.getUsers().getUserName()!=null){
					collectionTo.setUserName(pcReceipts.getUsers().getUserName());
					cashCollectionForm.setUsername(pcReceipts.getUsers().getUserName());
					}
				if(pcReceipts.getPcAccountHead().getPcBankAccNumber()!=null){
					if(size==0 && pcReceipts.getPcAccountHead().getPcBankAccNumber().getLogo()!=null){
						cashCollectionForm.setLogo(pcReceipts.getPcAccountHead().getPcBankAccNumber().getLogo());
						size=1;
					}
				}
				collectionTo.setDetails(pcReceipts.getDetails());
				cashCollectionToList.add(collectionTo);
				
			}
			if(!time.isEmpty()){
			String dateString = time.substring(0, 10);
			String inputDateFormat = "yyyy-mm-dd";
			String outPutdateFormat = "dd/mm/yyyy";
			cashCollectionForm.setPaidDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));
			String hour = time.substring(11, 13);
			String minute = time.substring(14, 16);
			cashCollectionForm.setTime(hour+":"+minute);
			}
		cashCollectionForm.setTotal(String.valueOf(total)+"0");
		cashCollectionForm.setRupeesInWords(CommonUtil.numberToWord(Double.valueOf(total).intValue()));
		cashCollectionForm.setAccountListforPrint(cashCollectionToList);
		
		log.info("Existing setReceiptNumberDatatoForm of CashCollectionHelper");
		
	}
}
