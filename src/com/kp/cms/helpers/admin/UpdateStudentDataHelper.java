package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.forms.admin.UpdateStudentDataForm;
import com.kp.cms.to.admin.UpdateStudentDataTO;
import com.kp.cms.transactions.admin.IUpdateStudentDataTransaction;
import com.kp.cms.transactionsimpl.admin.UpdateStudentDataTransactionImpl;

public class UpdateStudentDataHelper {
	private static volatile UpdateStudentDataHelper updateStudentDataHelper = null;
	private static final Log log = LogFactory.getLog(UpdateStudentDataHelper.class);
	private UpdateStudentDataHelper() {
		
	}
	/**
	 * return singleton object of NewUpdateProccessHelper.
	 * @return
	 */
	public static UpdateStudentDataHelper getInstance() {
		if (updateStudentDataHelper == null) {
			updateStudentDataHelper = new UpdateStudentDataHelper();
		}
		return updateStudentDataHelper;
	}
	public List<UpdateStudentDataTO> convertBoToTO(List stuList) throws Exception {
		List<UpdateStudentDataTO> list = new ArrayList<UpdateStudentDataTO>();
		if(stuList != null && !stuList.isEmpty()){
			Iterator<Student> iterator = stuList.iterator();
			while (iterator.hasNext()) {
				Student bo = iterator.next();
				UpdateStudentDataTO to = new UpdateStudentDataTO();
				to.setRegNo(bo.getRegisterNo());
				to.setStudentId(bo.getId());
				to.setStudentName(bo.getAdmAppln().getPersonalData().getFirstName());
				to.setClassName(bo.getClassSchemewise().getClasses().getName());
				if(bo.getAdmAppln().getPersonalData().getParentMob2() != null && !bo.getAdmAppln().getPersonalData().getParentMob2().isEmpty()){
					to.setParentMobNo(bo.getAdmAppln().getPersonalData().getParentMob2());
				}
				list.add(to);
			}
		}
		return list;
	}
	public List<UpdateStudentDataTO> convertBOToTO1(List prevStudentList) throws Exception {
		List<UpdateStudentDataTO> list = new ArrayList<UpdateStudentDataTO>();
		if(prevStudentList != null && !prevStudentList.isEmpty()){
			Iterator<StudentUtilBO> iterator = prevStudentList.iterator();
			IUpdateStudentDataTransaction txn = UpdateStudentDataTransactionImpl.getInstance();
			while (iterator.hasNext()) {
				StudentUtilBO bo = iterator.next();
				UpdateStudentDataTO to = new UpdateStudentDataTO();
				to.setRegNo(bo.getRegisterNo());
				to.setStudentId(bo.getId());
				to.setStudentName(bo.getAdmApplnUtilBO().getPersonalDataUtilBO().getFirstName());
				to.setClassName(bo.getClassSchemewiseUtilBO().getClassUtilBO().getName());
				String mobileNo = txn.getParentMob(to.getStudentId());
				if(mobileNo != null && !mobileNo.isEmpty()){
					to.setParentMobNo(mobileNo);
				}
				list.add(to);
			}
		}
		return list;
	}
	public List<PersonalData> convertTOtoBO(List<UpdateStudentDataTO> list,UpdateStudentDataForm updateStudentDataForm) throws Exception {
		List<PersonalData> liPersonalDatas = new ArrayList<PersonalData>();
		Iterator<UpdateStudentDataTO> iterator = list.iterator();
		IUpdateStudentDataTransaction txn = UpdateStudentDataTransactionImpl.getInstance();
		while(iterator.hasNext()){
			UpdateStudentDataTO to = iterator.next();
			if(to.getParentMobNo() != null && !to.getParentMobNo().isEmpty()){
			PersonalData data = txn.getData(to.getStudentId());
			if(to.getParentMobNo() != null && !to.getParentMobNo().isEmpty()){
				data.setParentMob2(to.getParentMobNo());
			}
			data.setModifiedBy(updateStudentDataForm.getUserId());
			data.setLastModifiedDate(new Date());
			liPersonalDatas.add(data);
			}
		}
		return liPersonalDatas;
	}
}
