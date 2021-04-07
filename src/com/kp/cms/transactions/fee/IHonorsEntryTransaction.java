package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.HonorsEntryBo;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.fee.HonorsEntryForm;

public interface IHonorsEntryTransaction 
{

	public HonorsEntryBo checkDuplicate(HonorsEntryForm entryForm, Student student)throws Exception;

	public boolean updateHonorsEntry(HonorsEntryBo entryBo)throws Exception;

	public List<Program> getHonorPrograms()throws Exception;

	public HonorsEntryBo getHonorsDetails(HonorsEntryForm entryForm, Student student) throws Exception;

	public boolean deleteHonorsEntry(HonorsEntryBo entryBo) throws Exception;

	public boolean reActivateHonorsEntry(int id, String userId) throws Exception;

	public Student getStudent(HonorsEntryForm entryForm)throws Exception;
	
}
