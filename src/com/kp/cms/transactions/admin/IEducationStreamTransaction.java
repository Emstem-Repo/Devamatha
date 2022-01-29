package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.ApplicationFee;
import com.kp.cms.bo.admin.EducationStream;

public interface IEducationStreamTransaction 
{
	
public List<EducationStream> getEducStreams();
	
	public EducationStream isEducStreamDuplicated(EducationStream oldceducstream) throws Exception;
	public boolean addEducStream(EducationStream educstream) throws Exception;
	public boolean updateEducStream(EducationStream educstream) throws Exception;
	public boolean deleteEducStream(int educstreamId,String userId) throws Exception;
	public boolean reActivateEducStream(EducationStream educstream,String userId) throws Exception;
	public EducationStream getEducStreamList(int educstreamId);

}
