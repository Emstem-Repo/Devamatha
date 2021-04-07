package com.kp.cms.handlers.fee;

import java.util.List;

import com.kp.cms.bo.admin.HonorsEntryBo;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.HonorsEntryForm;
import com.kp.cms.helpers.fee.HonorsEntryHelper;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.transactions.fee.IHonorsEntryTransaction;
import com.kp.cms.transactionsimpl.fee.HonorsEntryTransactionImpl;

public class HonorsEntryHandler 
{
	private static volatile HonorsEntryHandler handler=null;
	private HonorsEntryHandler()
	{
		
	}
	
	
	public static HonorsEntryHandler getInstance()
	{
		if(handler==null)
			handler=new HonorsEntryHandler();
		return handler;
	}
	public HonorsEntryHelper helper=HonorsEntryHelper.getInstance();
	public IHonorsEntryTransaction entryTransaction=new HonorsEntryTransactionImpl();
	
	public boolean updateHonorsEntry(HonorsEntryForm entryForm, Student student) throws Exception
	{
		HonorsEntryBo honorsEntryBo=entryTransaction.checkDuplicate(entryForm,student);
		
		if(honorsEntryBo !=null && honorsEntryBo.getIsActive()){
			throw new DuplicateException();
		}
		else if(honorsEntryBo !=null && !honorsEntryBo.getIsActive()){
			int id =honorsEntryBo.getId();
			throw new ReActivateException(id);
		}
		HonorsEntryBo entryBo=helper.convertFormToBo(entryForm,student);
		return entryTransaction.updateHonorsEntry(entryBo);
	}


	public List<ProgramTO> getProgram() throws Exception
	{
		List<Program> programs=entryTransaction.getHonorPrograms();
		List<ProgramTO>programTos=helper.convertPrograms(programs);
		return programTos;
	}


	public boolean deleteHonorsEntry(HonorsEntryForm entryForm,Student student) throws Exception{
		
		HonorsEntryBo honorsEntryBo=entryTransaction.getHonorsDetails(entryForm,student);
		if(honorsEntryBo!=null){
			HonorsEntryBo entryBo=helper.convertBOToBO(honorsEntryBo,entryForm);
			return entryTransaction.deleteHonorsEntry(entryBo);
		}
		return false;
	}


	public boolean reActivateHonorsEntry(int id, String userId) throws Exception{
		
		if(entryTransaction != null)
		{
			return entryTransaction.reActivateHonorsEntry(id, userId);
		}
		return false;
	}


	public Student getStudent(HonorsEntryForm entryForm)throws Exception 
	{
		return entryTransaction.getStudent(entryForm);
	}
	
	
}
