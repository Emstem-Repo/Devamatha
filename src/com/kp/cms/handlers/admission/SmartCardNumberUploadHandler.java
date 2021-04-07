	package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;

import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.ISmartCardNumberUploadTransaction;
import com.kp.cms.transactionsimpl.admission.SmartCardNumberTransactionImpl;
	
	public class SmartCardNumberUploadHandler {
		private static final Log log = LogFactory.getLog(SmartCardNumberUploadHandler.class);
		private static volatile SmartCardNumberUploadHandler smartCardNumberUploadHandler=null;
		public static SmartCardNumberUploadHandler getInstance(){
			if(smartCardNumberUploadHandler == null){
				smartCardNumberUploadHandler = new SmartCardNumberUploadHandler();
				return smartCardNumberUploadHandler;
			}
			return smartCardNumberUploadHandler;
		}
		
		/**
		 * @return
		 * @throws Exception
		 */
		public Map<String, Student> getRegisterNumber()throws Exception {
			ISmartCardNumberUploadTransaction uploadTransaction = SmartCardNumberTransactionImpl.getInstance();
			return uploadTransaction.getRegisterNumber();
		}
		/**
		 * @param results
		 * @param user
		 * @return
		 * @throws Exception 
		 */
		public boolean addUploadSmartCard(List<Student> results, String user) throws Exception {
			log.info("call of addUploadSmartCard method in SmartCardNumberUploadHandler class.");
			boolean isAdd = false;
			ISmartCardNumberUploadTransaction uploadTransaction=SmartCardNumberTransactionImpl.getInstance();
			isAdd=uploadTransaction.addSmartCardNumber(results,user);
			log.info("end of addUploadSmartCard method in SmartCardNumberUploadHandler class.");
			return isAdd;
		}

		public boolean addUploadSmartCardNew(List<StudentTO> list, String user) throws Exception{
			boolean flag=false;
			List<String> regNos=new ArrayList<String>();
			Iterator<StudentTO> iterator=list.iterator();
			while (iterator.hasNext()) {
				StudentTO studentTO = (StudentTO) iterator.next();
				regNos.add(studentTO.getRegisterNo());
			}
			ISmartCardNumberUploadTransaction transaction = SmartCardNumberTransactionImpl.getInstance();
			Map<String,Student> map=transaction.getStudents(regNos);
			List<Student> students=convertToBos(map,list,user);
			if(students!=null && !students.isEmpty()){
				flag=transaction.update(students);
			}
			return flag;
		}

		private List<Student> convertToBos(Map<String, Student> map,
				List<StudentTO> list, String user) throws Exception{
			List<Student> students=new ArrayList<Student>();
			Student student=null;
			Iterator<StudentTO> iterator=list.iterator();
			while (iterator.hasNext()) {
				StudentTO studentTO = (StudentTO) iterator.next();
				if(map.containsKey(studentTO.getRegisterNo())){
					student=map.get(studentTO.getRegisterNo());
					student.setModifiedBy(user);
					student.setLastModifiedDate(new Date());
					student.setBankAccNo(studentTO.getBankAccNo());
					student.setSmartCardNo(studentTO.getSmartCardNo());
					student.setIsSCDataDelivered(true);
					students.add(student);
				}
			}
			return students;
		}
	}
