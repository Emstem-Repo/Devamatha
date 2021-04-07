package com.kp.cms.helpers.hostel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.HlFeeType;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.forms.hostel.HostelFeesForm;
import com.kp.cms.to.hostel.HostelFeesTo;
import com.kp.cms.to.hostel.HostelFeesTypeTo;
import com.kp.cms.transactions.hostel.IHostelFeeTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelFeeTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelFeeHelper {
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static Log log = LogFactory.getLog(HostelFeeHelper.class);
	private static volatile HostelFeeHelper hostelFeeHelper = null;
	IHostelFeeTransactions transaction = HostelFeeTransactionImpl.getInstance();
	
	private HostelFeeHelper() {
	}
	
	public static HostelFeeHelper getInstance() {
		if (hostelFeeHelper == null) {
			hostelFeeHelper = new HostelFeeHelper();
		}
		return hostelFeeHelper;
	}
	
	
	public List<HostelFeesTypeTo> copyFeeBosToTos(List<HlFeeType> feeList) throws Exception{
		log.debug("Entering copyFeeBosToTos in HostelFeeHelper");
		List<HostelFeesTypeTo> listTo = new ArrayList<HostelFeesTypeTo>();
		if(feeList!=null && !feeList.isEmpty())
		{
			Iterator<HlFeeType> boit = feeList.iterator();
			while(boit.hasNext())
			{
				HlFeeType hlFeeType = boit.next();
				HostelFeesTypeTo feeTo = new HostelFeesTypeTo();
				if(hlFeeType.getName()!=null){
					feeTo.setFeesname(hlFeeType.getName());
					feeTo.setId(hlFeeType.getId());
				}
				listTo.add(feeTo);
			}
		}
		log.debug("Exiting copyFeeBosToTos in HostelFeeHelper");
		return listTo;
	}
	
	public List<HostelFeesTo> copyFeeDetailedListBosToTos(List<HlFees> feeList) throws Exception{
		log.debug("Entering copyFeeDetailedListBosToTos in HostelFeeHelper");
		List<HostelFeesTo> listTo = new ArrayList<HostelFeesTo>();
		if(feeList!=null && !feeList.isEmpty())
		{
			Iterator<HlFees> boit = feeList.iterator();
			while(boit.hasNext())
			{
				HlFees hlFees = boit.next();
				HostelFeesTo feeTo = new HostelFeesTo();
				if(String.valueOf(hlFees.getId())!=null && !String.valueOf(hlFees.getId()).isEmpty() ){
					feeTo.setId(hlFees.getId());
				}
				if(hlFees.getHlHostel()!=null){
					feeTo.setHostelId(hlFees.getHlHostel().getId());
				if(hlFees.getHlHostel().getName()!=null && !hlFees.getHlHostel().getName().isEmpty() ){
					feeTo.setHostelName(hlFees.getHlHostel().getName());
				}
				}
				if(hlFees.getHlRoomType()!=null){
					feeTo.setRoomTypeId(hlFees.getHlRoomType().getId());
				if(hlFees.getHlRoomType().getName()!=null && !hlFees.getHlRoomType().getName().isEmpty() ){
					feeTo.setRoomType(hlFees.getHlRoomType().getName())	;
				}
				}
				listTo.add(feeTo);
			}
		}
		log.debug("Exiting copyFeeDetailedListBosToTos in HostelFeeHelper");
		return listTo;
	}
	
	
	public List<HostelFeesTo> copyFeeDetailedListBosToTosToView(List<HlFees> feeList) throws Exception{
		log.debug("Entering copyFeeDetailedListBosToTosToView in HostelFeeHelper");
		double total=0.0;
		List<HostelFeesTo> listTo = new ArrayList<HostelFeesTo>();
		if(feeList!=null && !feeList.isEmpty())
		{
			Iterator<HlFees> boit = feeList.iterator();
			while(boit.hasNext())
			{
				HlFees hlFees = boit.next();
				HostelFeesTo feeTo = new HostelFeesTo();
				if(String.valueOf(hlFees.getId())!=null && !String.valueOf(hlFees.getId()).isEmpty() ){
					feeTo.setId(hlFees.getId());
				}
				if(hlFees.getHlHostel()!=null){
				if(hlFees.getHlHostel().getName()!=null && !hlFees.getHlHostel().getName().isEmpty() ){
					feeTo.setHostelName(hlFees.getHlHostel().getName());
				}
				}
				if(hlFees.getHlRoomType()!=null){
				if(hlFees.getHlRoomType().getName()!=null && !hlFees.getHlRoomType().getName().isEmpty() ){
					feeTo.setRoomType(hlFees.getHlRoomType().getName())	;
				}
				}
				if(hlFees.getHlFeeType()!=null){
					if(hlFees.getHlFeeType().getName()!=null && !hlFees.getHlFeeType().getName().isEmpty()){
						feeTo.setFeeType(hlFees.getHlFeeType().getName());
					}
				}
				if(hlFees.getFeeAmount()!=null && hlFees.getFeeAmount()!=null){
					feeTo.setAmount(hlFees.getFeeAmount().toString());
					total = total + Double.valueOf(hlFees.getFeeAmount().doubleValue());
					feeTo.setTotal(String.valueOf(total));
				}
				listTo.add(feeTo);
			}
		}
		log.debug("Exiting copyFeeDetailedListBosToTosToView in HostelFeeHelper");
		return listTo;
	}
	
	public List<HlFees> prepareBoToSave(HostelFeesForm hostelFeeForm,List<HostelFeesTypeTo> hostelFeesTypeToList) throws Exception
	{
		log.debug("Entering prepareBoToSave in HostelFeeHelper");
		if(hostelFeesTypeToList == null || hostelFeesTypeToList.isEmpty())
		{
			return new  ArrayList<HlFees>();
		}
		else if((hostelFeeForm.getHostelId()== null ||hostelFeeForm.getHostelId().isEmpty()) && (hostelFeeForm.getRoomType()==null ||hostelFeeForm.getRoomType().isEmpty()))
		{
			return new  ArrayList<HlFees>();
		}
		else{
			Calendar calendar = new GregorianCalendar();
		    int year = calendar.get(Calendar.YEAR);
		    
			List<HlFees> hlFeesBoList = new ArrayList<HlFees>();
			Currency currency = new Currency();
			currency.setId(1);
			HlHostel hlHostel = new HlHostel();
			if(hostelFeeForm.getHostelId()!=null&& !hostelFeeForm.getHostelId().isEmpty()){
			hlHostel.setId(Integer.valueOf(hostelFeeForm.getHostelId()));
			}
			HlRoomType hlRoomType = new HlRoomType();
			if(hostelFeeForm.getRoomType()!= null && !hostelFeeForm.getRoomType().isEmpty()){
			hlRoomType.setId(Integer.valueOf(hostelFeeForm.getRoomType()));
			}
			for (HostelFeesTypeTo feesTo : hostelFeesTypeToList) {
				HlFees hlFees = new HlFees();
				hlFees.setCurrency(currency);
				hlFees.setAcademicYr(String.valueOf(year));
				HlFeeType hlFeeType = new HlFeeType();
				if(String.valueOf(feesTo.getId())!=null && !String.valueOf(feesTo.getId()).isEmpty()){
				hlFeeType.setId(feesTo.getId());
				}
				if(hlFeeType!=null)
				hlFees.setHlFeeType(hlFeeType);
				if(hlHostel!=null)
				hlFees.setHlHostel(hlHostel);
				if(hlRoomType!=null)
				hlFees.setHlRoomType(hlRoomType);	
				if(feesTo.getAmount()!=null && !feesTo.getAmount().isEmpty()){
					hlFees.setFeeAmount(new BigDecimal(feesTo.getAmount()));
				}
				hlFees.setIsActive(true);
				hlFees.setCreatedBy(hostelFeeForm.getUserId());
				hlFees.setModifiedBy(hostelFeeForm.getUserId());
				hlFees.setCreatedDate(new Date());
				hlFees.setLastModifiedDate(new Date());
				
				hlFeesBoList.add(hlFees);
			}
			log.debug("Exiting prepareBoToSave in HostelFeeHelper");
			return hlFeesBoList;
		}
		
	}
	
	public List<HlFees> prepareBoListToMakeActive(List<HlFees> boList,String userId) throws Exception
	{
		log.debug("Entering prepareBoListToMakeActive in HostelFeeHelper");
		List<HlFees> boListWithActive = new ArrayList<HlFees>();
		
		for (HlFees hlFees : boList) {
			hlFees.setIsActive(true);
			hlFees.setLastModifiedDate(new Date());
			hlFees.setModifiedBy(userId);
			boListWithActive.add(hlFees);
		}
		log.debug("Exiting prepareBoListToMakeActive in HostelFeeHelper");
		return boListWithActive;
	}

	public List<HostelFeesTypeTo> convertFeesToFeesType(List<HlFees> feeListBo,HostelFeesForm hostelFeeForm,List<HostelFeesTypeTo>listToDisplay)throws Exception 
	{
		List<HostelFeesTypeTo>feesTypeList=new ArrayList<HostelFeesTypeTo>();
		HostelFeesTypeTo feesType=null;
		double total=0;
		HlFees fees=null;
		boolean extraFeeTypeAdded=false;
		String feesName="";
		for(int j=0;j<listToDisplay.size();j++)
		{
			feesName=listToDisplay.get(j).getFeesname();
			for(int i=0;i<feeListBo.size();i++)
			{
				fees=feeListBo.get(i);
				if(fees.getHlFeeType().getName().equals(feesName))
				{
					extraFeeTypeAdded=false;
					feesType=new HostelFeesTypeTo();
					feesType.setFeesname(fees.getHlFeeType().getName());
					if(fees.getFeeAmount()!=null)
					{
						feesType.setAmount(fees.getFeeAmount().toString());
						total = total+fees.getFeeAmount().doubleValue();
					}
					
					else
						feesType.setAmount("");
					
					feesType.setId(fees.getHlFeeType().getId());
					feesType.setFeeId(fees.getId());
					
					feesType.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(fees.getCreatedDate()), HostelFeeHelper.SQL_DATEFORMAT,HostelFeeHelper.FROM_DATEFORMAT));
					feesType.setCreatedBy(fees.getCreatedBy());
					feesType.setCurrencyId(fees.getCurrency().getId());
					feesType.setActive(fees.getIsActive());
					feesType.setAcademicYear(fees.getAcademicYr());
					break;
				}
				else
				{
					extraFeeTypeAdded=true;
				}	
			}	
			
			if(extraFeeTypeAdded)
			{
				feesType=new HostelFeesTypeTo();
				feesType.setId(listToDisplay.get(j).getId());
				feesType.setFeesname(feesName);
				feesType.setAmount("");
				feesType.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getTodayDate(), HostelFeeHelper.SQL_DATEFORMAT,HostelFeeHelper.FROM_DATEFORMAT));
				feesType.setCreatedBy(fees.getCreatedBy());
				feesType.setCurrencyId(fees.getCurrency().getId());
				feesType.setActive(fees.getIsActive());
				feesType.setAcademicYear(fees.getAcademicYr());
			}
			
			feesTypeList.add(feesType);
		}
		hostelFeeForm.setTotal(Double.toString(total));
		return feesTypeList;
	}
	
	public List<HlFees> updateFeeDetailsTosBos(HostelFeesForm hostelFeeForm,List<HostelFeesTypeTo> hostelFeesTypeToList) throws Exception
	{
		
		List<HlFees> hlFeesBoList = new ArrayList<HlFees>();
		HlHostel hlHostel = new HlHostel();
		if(hostelFeeForm.getHostelId()!=null&& !hostelFeeForm.getHostelId().isEmpty()){
		hlHostel.setId(Integer.valueOf(hostelFeeForm.getHostelId()));
		}
		HlRoomType hlRoomType = new HlRoomType();
		if(hostelFeeForm.getRoomType()!= null && !hostelFeeForm.getRoomType().isEmpty()){
		hlRoomType.setId(Integer.valueOf(hostelFeeForm.getRoomType()));
		}
		for (HostelFeesTypeTo feesTo : hostelFeesTypeToList) {
			
			HlFees hlFees = new HlFees();
			HlFeeType hlFeeType = new HlFeeType();
			hlFeeType.setId(feesTo.getId());
			if(String.valueOf(feesTo.getFeeId())!=null && !String.valueOf(feesTo.getFeeId()).isEmpty()){
				hlFees.setId(feesTo.getFeeId());
			}
			if(hlFeeType!=null)
			hlFees.setHlFeeType(hlFeeType);
			if(hlHostel!=null)
			hlFees.setHlHostel(hlHostel);
			if(hlRoomType!=null)
			hlFees.setHlRoomType(hlRoomType);	
			if(feesTo.getAmount()!=null && !feesTo.getAmount().isEmpty()){
				hlFees.setFeeAmount(new BigDecimal(feesTo.getAmount()));
			}
			hlFees.setModifiedBy(hostelFeeForm.getUserId());
			hlFees.setLastModifiedDate(new Date());
			hlFees.setAcademicYr(feesTo.getAcademicYear());
			hlFees.setCreatedBy(feesTo.getCreatedBy());
			hlFees.setCreatedDate(CommonUtil.ConvertStringToSQLDate(feesTo.getCreatedDate()));
			if(feesTo.getCurrencyId() > 0)
			{
				Currency c = new Currency();
				c.setId(feesTo.getCurrencyId());
				hlFees.setCurrency(c);
			}
				
			hlFees.setIsActive(true);
			hlFeesBoList.add(hlFees);
		}
		return hlFeesBoList;
		
	}
}
