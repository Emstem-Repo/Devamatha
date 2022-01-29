package com.kp.cms.helpers.sap;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.sap.UploadSAPMarksBo;
import com.kp.cms.forms.sap.UploadSAPMarksForm;
import com.kp.cms.to.sap.UploadSAPMarksTo;
import com.kp.cms.transactions.exam.IUploadBlockListForHallticketOrMarkscardTransaction;
import com.kp.cms.transactionsimpl.exam.UploadBlockListForHallticketOrMarkscardTransactionsImpl;
import com.kp.cms.utilities.CommonUtil;

public class UploadSAPMarksHelper {

	private static volatile UploadSAPMarksHelper uploadSAPMarksHelper = null;
	private static final Log log = LogFactory.getLog(UploadSAPMarksHelper.class);
	public static UploadSAPMarksHelper getInstance() {
		if (uploadSAPMarksHelper == null) {
			uploadSAPMarksHelper = new UploadSAPMarksHelper();
		}
		return uploadSAPMarksHelper;
	}
	
	
	
	public Map<Integer,UploadSAPMarksBo> covertToToBo(List<UploadSAPMarksTo> results,UploadSAPMarksForm objform)throws Exception{
		UploadSAPMarksBo bo=null;
		Map<Integer, UploadSAPMarksBo> map =new HashMap<Integer, UploadSAPMarksBo>();
		Iterator<UploadSAPMarksTo> itr=results.iterator();
		while(itr.hasNext()) {
			bo=new UploadSAPMarksBo();
			UploadSAPMarksTo to=itr.next();
				if(!map.containsKey(to.getStudentId()) ){
					Student student=new Student();
					student.setId(to.getStudentId());
					bo.setStudentId(student);
					bo.setMarks(to.getMarks());
					bo.setStatus(to.getStatus());
					bo.setDate(CommonUtil.ConvertStringToSQLDate(objform.getExamDate()));
					bo.setCreatedBy(objform.getUserId());
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(objform.getUserId());
					bo.setLastModifiedDate(new Date());
					bo.setIsActive(true);
					map.put(to.getStudentId(), bo);
				}
		}
		return map;
	}
	
	
}
	




