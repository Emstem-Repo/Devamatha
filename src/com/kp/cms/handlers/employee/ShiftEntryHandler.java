package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.ShiftEntry;
import com.kp.cms.forms.employee.EmpTypeForm;
import com.kp.cms.forms.employee.ShiftEntryForm;
import com.kp.cms.helpers.employee.EmpTypeHelper;
import com.kp.cms.helpers.employee.ShiftEntryHelper;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.to.employee.ShiftEntryTo;
import com.kp.cms.transactions.employee.IEmpTypeTransaction;
import com.kp.cms.transactionsimpl.employee.EmpTypeTransaction;

public class ShiftEntryHandler {
	
	private static volatile ShiftEntryHandler instance=null;
//	IEmpTypeTransaction empTypeTransaction=EmpTypeTransaction.getInstance();
	IEmpTypeTransaction empTypeTransaction=EmpTypeTransaction.getInstance();
	
	/**
	 * 
	 */
	private ShiftEntryHandler(){
		
	}
	
	/**
	 * @return
	 */
	public static ShiftEntryHandler getInstance(){
		if(instance==null){
			instance=new ShiftEntryHandler();
		}
		return instance;
	}



	/**
	 * @param shiftEntryForm
	 * @return
	 */
	public Map<Integer, String> getEmployeeList() throws Exception {
		Map<Integer, String> map =  empTypeTransaction.getEmployeeMap();
		return map;
	}

	public List<ShiftEntryTo> getShiftEntrys()throws Exception {
		List<ShiftEntryTo> tos =new ArrayList<ShiftEntryTo>();
		ShiftEntryTo to = new ShiftEntryTo();
		to.setDay("Sunday");
		to.setTimeIn("00");
		to.setTimeInMin("00");
		to.setTimeOut("00");
		to.setTimeOutMin("00");
		tos.add(to);
		ShiftEntryTo to1 = new ShiftEntryTo();
		to1.setDay("Monday");
		to1.setTimeIn("00");
		to1.setTimeOut("00");
		to1.setTimeInMin("00");
		to1.setTimeOutMin("00");
		tos.add(to1);
		ShiftEntryTo to2 = new ShiftEntryTo();
		to2.setDay("Tuesday");
		to2.setTimeIn("00");
		to2.setTimeOut("00");
		to2.setTimeInMin("00");
		to2.setTimeOutMin("00");
		tos.add(to2);
		ShiftEntryTo to3 = new ShiftEntryTo();
		to3.setDay("Wednesday");
		to3.setTimeIn("00");
		to3.setTimeOut("00");
		to3.setTimeInMin("00");
		to3.setTimeOutMin("00");
		tos.add(to3);
		ShiftEntryTo to4 = new ShiftEntryTo();
		to4.setDay("ThrusDay");
		to4.setTimeIn("00");
		to4.setTimeOut("00");
		to4.setTimeInMin("00");
		to4.setTimeOutMin("00");
		tos.add(to4);
		ShiftEntryTo to5 = new ShiftEntryTo();
		to5.setDay("Friday");
		to5.setTimeIn("00");
		to5.setTimeOut("00");
		to5.setTimeInMin("00");
		to5.setTimeOutMin("00");
		tos.add(to5);
		ShiftEntryTo to6 = new ShiftEntryTo();
		to6.setDay("Saturday");
		to6.setTimeIn("00");
		to6.setTimeOut("00");
		to6.setTimeInMin("00");
		to6.setTimeOutMin("00");
		tos.add(to6);
		return tos;
	}

	/**
	 * @param shiftEntryForm
	 * @param mode 
	 */
	public Boolean saveShiftEntry(ShiftEntryForm shiftEntryForm, String mode) throws Exception{
		if(mode.equalsIgnoreCase("reActivate")){
			List<ShiftEntry> list = empTypeTransaction.getEmpShiftEntryList(shiftEntryForm.getEmployeeId(),"reActivate");
			List<ShiftEntry> entryBos = new ArrayList<ShiftEntry>();
			Iterator<ShiftEntry> iterator = list.iterator();
			while (iterator.hasNext()) {
				ShiftEntry shiftEntry = (ShiftEntry) iterator.next();
				shiftEntry.setIsActive(true);
				entryBos.add(shiftEntry);
			}
			boolean save = empTypeTransaction.saveShiftEntry(entryBos,"reActivate");
			return save;
		}else if(mode.equalsIgnoreCase("update")){
			List<ShiftEntryTo> entryTos = shiftEntryForm.getEntryTos();
			List<ShiftEntry> entryBos= ShiftEntryHelper.getInstance().convertTOToBo(entryTos,shiftEntryForm);
			boolean save = empTypeTransaction.saveShiftEntry(entryBos,"update");
			return save;
		}else{
			List<ShiftEntryTo> entryTos = shiftEntryForm.getEntryTos();
			List<ShiftEntry> entryBos= ShiftEntryHelper.getInstance().convertTOToBo(entryTos,shiftEntryForm);
			boolean save = empTypeTransaction.saveShiftEntry(entryBos,"add");
			return save;
		}
	}

	public List<EmployeeTO> getEmpEntryList() throws Exception{
		List<ShiftEntry> list = empTypeTransaction.getEmpEntryList();
		List<EmployeeTO> tos = ShiftEntryHelper.getInstance().convetBoToTO(list);
		return tos;
	}

	/**
	 * @param shiftEntryForm
	 * @throws Exception 
	 */
	public void editShiftEntry(ShiftEntryForm shiftEntryForm) throws Exception {
		
		List<ShiftEntry> list = empTypeTransaction.getEmpShiftEntryList(shiftEntryForm.getEmployeeId(),"edit");
		ShiftEntryHelper.getInstance().convetBoToForm(list, shiftEntryForm);
	}

	/**
	 * @param shiftEntryForm
	 * @return
	 */
	public Boolean deleteShiftEntry(ShiftEntryForm shiftEntryForm)throws Exception {
		List<ShiftEntry> list = empTypeTransaction.getEmpShiftEntryList(shiftEntryForm.getEmployeeId(),"delete");
		List<ShiftEntry> entryList = new ArrayList<ShiftEntry>();
		if(list != null){
			Iterator<ShiftEntry> iterator = list.iterator();
			while (iterator.hasNext()) {
				ShiftEntry shiftEntry = (ShiftEntry) iterator.next();
				shiftEntry.setIsActive(false);
				entryList.add(shiftEntry);
			}
		}
		boolean delete = empTypeTransaction.saveShiftEntry(entryList,"delete");
		return delete;
	}

}
