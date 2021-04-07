package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.forms.exam.UploadBlockListForHallticketOrMarkscardForm;
import com.kp.cms.to.exam.UploadBlockListForHallticketOrMarkscardTo;
import com.kp.cms.transactions.exam.IUploadBlockListForHallticketOrMarkscardTransaction;
import com.kp.cms.transactionsimpl.exam.UploadBlockListForHallticketOrMarkscardTransactionsImpl;

public class UploadBlockListForHallticketOrMarkscardHelper {
	private static volatile UploadBlockListForHallticketOrMarkscardHelper uploadBlockListForHallticketOrMarkscardHelper = null;
	private static final Log log = LogFactory.getLog(UploadBlockListForHallticketOrMarkscardHelper.class);
	/**
	 * @return
	 */
	public static UploadBlockListForHallticketOrMarkscardHelper getInstance() {
		if (uploadBlockListForHallticketOrMarkscardHelper == null) {
			uploadBlockListForHallticketOrMarkscardHelper = new UploadBlockListForHallticketOrMarkscardHelper();
		}
		return uploadBlockListForHallticketOrMarkscardHelper;
	}
	IUploadBlockListForHallticketOrMarkscardTransaction uploadTransaction = UploadBlockListForHallticketOrMarkscardTransactionsImpl.getInstance();
	
	/**
	 * @param results
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,ExamBlockUnblockHallTicketBO> convertToToBo(List<UploadBlockListForHallticketOrMarkscardTo> results,UploadBlockListForHallticketOrMarkscardForm objform,Map<Integer,String> classMap)throws Exception{
		IUploadBlockListForHallticketOrMarkscardTransaction uploadTransaction = UploadBlockListForHallticketOrMarkscardTransactionsImpl.getInstance();
		
		ExamBlockUnblockHallTicketBO bo=null;
		StringBuffer str=new StringBuffer("The following register numbers are not applicable for the exam selected: ");
		StringBuffer str2=new StringBuffer("The following register numbers are not applicable for the class selected: ");
		Map<Integer, ExamBlockUnblockHallTicketBO> map =new HashMap<Integer, ExamBlockUnblockHallTicketBO>();
		Iterator<UploadBlockListForHallticketOrMarkscardTo> itr=results.iterator();
		while(itr.hasNext()) {
			bo=new ExamBlockUnblockHallTicketBO();
			UploadBlockListForHallticketOrMarkscardTo to=itr.next();
			boolean checkingStudentByclass=uploadTransaction.getStudentIdMapByClassId(to.getClassId(),to.getStudentId());
			if(classMap.containsKey(to.getClassId())&& (checkingStudentByclass)){
				if(!map.containsKey(to.getStudentId()) ){
					bo.setClassId(to.getClassId());
					bo.setExamId(Integer.parseInt(to.getExamId()));
					bo.setStudentId(to.getStudentId());
					bo.setBlockReason(to.getBlockReason());
					bo.setHallTktOrMarksCard(to.getHallTktOrMarksCard());
					map.put(to.getStudentId(), bo);
				}
			}else{
				
				int regNo=uploadTransaction.getStudentRegisterNo(to.getStudentId());
				if(!classMap.containsKey(to.getClassId())){
					str =str.append(regNo+",");
					objform.setFlag4(true);
				}else if(!checkingStudentByclass){
					str2 =str2.append(regNo+",");
					objform.setFlag5(true);
				}
			}
		}
		if(objform.isFlag4()){
			String str1=str.toString();
			if(str1.toString()!=null && !str1.toString().isEmpty()){
				str1 = str1.substring(0, (str1.length() - 1));
				}
			objform.setUnMatchClasses(str1);
		}
		if(objform.isFlag5()){
			String str3=str2.toString();
			if(str3.toString()!=null && !str3.toString().isEmpty()){
				str3 = str3.substring(0, (str3.length() - 1));
				}
			objform.setUnMatchRegByClass(str3);
		}
		
		return map;
	}
	
	public ExamBlockUnblockHallTicketBO convertToToBoGetExamBo(UploadBlockListForHallticketOrMarkscardTo results,UploadBlockListForHallticketOrMarkscardForm objform )throws Exception{
		ExamBlockUnblockHallTicketBO bo=new ExamBlockUnblockHallTicketBO();
				bo.setClassId(results.getClassId());
				bo.setExamId(Integer.parseInt(objform.getExamName()));
				bo.setHallTktOrMarksCard(objform.getType());
				bo.setStudentId(results.getStudentId());
		return bo;
	}
	
	
	
	/**
	 * @param fileName
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains("."))
	{
	return fileName.substring(0, fileName.lastIndexOf("."));
	}
	return fileName;
	}
}
