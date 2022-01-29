package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HostelApplicationNumber;
import com.kp.cms.forms.hostel.HlApplicationNumberForm;
import com.kp.cms.to.hostel.HlApplicationFeeTO;
import com.kp.cms.transactions.hostel.IHlApplicationNumberTransaction;
import com.kp.cms.transactionsimpl.hostel.HlApplicationNumberTransactionImpl;

public class HlApplicationNumberHandler {
	
	public static volatile HlApplicationNumberHandler handler = null;
	IHlApplicationNumberTransaction transaction=HlApplicationNumberTransactionImpl.getInstance();
	public static HlApplicationNumberHandler getInstance() {
		if (handler == null) {
			handler = new HlApplicationNumberHandler();
			return handler;
		}
		return handler;
	}
	public Map<Integer, String> getHosteList() throws Exception{
		return transaction.getHostelList();
	}
	/**
	 * @param hlApplicationNumberForm
	 * @param mode 
	 * @return
	 * @throws Exception
	 */
	public boolean save(HlApplicationNumberForm hlApplicationNumberForm, String mode) throws Exception{
		HostelApplicationNumber number = new HostelApplicationNumber();
		if(mode.equalsIgnoreCase("UPDATE"))
			number.setId(hlApplicationNumberForm.getId());
		number.setAcademicYear(Integer.parseInt(hlApplicationNumberForm.getAcademicYear()));
		number.setCreatedBy(hlApplicationNumberForm.getUserId());
		number.setCreatedDate(new Date());
		HlHostel hlHostel = new HlHostel();
		hlHostel.setId(Integer.parseInt(hlApplicationNumberForm.getHostelId()));
		number.setHlHostel(hlHostel);
		number.setIsActive(true);
		number.setLastModifiedDate(new Date());
		number.setModifiedBy(hlApplicationNumberForm.getUserId());
		number.setPrefix(hlApplicationNumberForm.getPreFix());
		number.setStartingNumber(Integer.parseInt(hlApplicationNumberForm.getStartNumber()));
		return transaction.save(number, mode);
	}
	public boolean delete(int id) throws Exception{
		return transaction.delete(id);
	}
	public List<HlApplicationFeeTO> getApplicationNoList() throws Exception{
		List<HlApplicationFeeTO> applicationNoList = new ArrayList<HlApplicationFeeTO>();
		List<HostelApplicationNumber> numbers = transaction.getHostelApplnNumbers();
		if(numbers != null){
			for (HostelApplicationNumber hostelApplicationNumber : numbers) {
				HlApplicationFeeTO to = new HlApplicationFeeTO();
				to.setId(hostelApplicationNumber.getId());
				to.setHostelName(hostelApplicationNumber.getHlHostel().getName());
				to.setHosteId(String.valueOf(hostelApplicationNumber.getHlHostel().getId()));
				to.setPrefix(hostelApplicationNumber.getPrefix());
				to.setStartNumber(String.valueOf(hostelApplicationNumber.getStartingNumber()));
				to.setAcademicyear(String.valueOf(hostelApplicationNumber.getAcademicYear()));
				applicationNoList.add(to);
			}
		}
		return applicationNoList;
	}
	
}
