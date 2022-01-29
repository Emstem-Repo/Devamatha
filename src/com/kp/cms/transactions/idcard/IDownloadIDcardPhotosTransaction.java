package com.kp.cms.transactions.idcard;

import java.util.List;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.employee.EmpImages;


public interface IDownloadIDcardPhotosTransaction {

	public String getprogramNameById(String pgmId) throws Exception;
	public int getTotalImageCount(int year, String classId,String applnNo)throws Exception;
	public List<ApplnDoc> getImages(int year, int page, int pagesize,String pgmId,String applnNo)
	throws Exception;
	
	public int getTotalImageCount1()throws Exception;
	public List<EmpImages> getImages1(int page, int PAGESIZE) throws Exception;
	

}
