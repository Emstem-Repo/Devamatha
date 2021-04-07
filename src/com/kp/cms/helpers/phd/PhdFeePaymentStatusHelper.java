package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.phd.PhdFeePaymentStatusTO;
import com.kp.cms.transactions.phd.IPhdFeePaymentStatus;
import com.kp.cms.transactionsimpl.phd.PhdFeePaymentStatusTransactionImpl;

public class PhdFeePaymentStatusHelper {
	private static final Log log = LogFactory .getLog(PhdFeePaymentStatusHelper.class);
 private static volatile PhdFeePaymentStatusHelper subjectGroupHistoryHelper = null;
 public static PhdFeePaymentStatusHelper getInstance(){
	 if(subjectGroupHistoryHelper == null){
		 subjectGroupHistoryHelper = new PhdFeePaymentStatusHelper();
		 return subjectGroupHistoryHelper;
	 }
	 return subjectGroupHistoryHelper;
 }
 
 IPhdFeePaymentStatus transactions= PhdFeePaymentStatusTransactionImpl.getInstance();
 
public List<PhdFeePaymentStatusTO> setDataBotoTo(List<Object[]> documentBo) {
	List<PhdFeePaymentStatusTO> documentlist=new ArrayList<PhdFeePaymentStatusTO>();
	String check="";
	int ck=0;
	if(documentBo!=null){
		Iterator<Object[]> itr = documentBo.iterator();
	 while (itr.hasNext()) {
		Object[] object = (Object[]) itr.next();
		PhdFeePaymentStatusTO documentTo= new PhdFeePaymentStatusTO();
		
		if(object[9]!=null){
			if(Integer.parseInt(object[9].toString())!=ck){
				check=transactions.getcurrency(Integer.parseInt(object[9].toString()));
				ck=Integer.parseInt(object[9].toString());
			}
		}
		if(object[0]!=null){
			documentTo.setCourseName(object[0].toString());
		}if(object[1]!=null){
			documentTo.setRegisterNo(object[1].toString());
		}if(object[2]!=null && object[3]!=null){
			documentTo.setStudentName(object[2].toString()+""+object[3].toString());
		}else{ 
			documentTo.setStudentName(object[2].toString());
		}if(object[4]!=null){
			documentTo.seteMail(object[4].toString());
		}if(object[5]!=null){
			documentTo.setBillNo(object[5].toString());
		}if(object[6]!=null){
			documentTo.setBillDate(object[6].toString());
		}if(object[7]!=null){
			if(!check.equalsIgnoreCase("INR") && check!=null && !check.isEmpty()){
				documentTo.setPaidAmout(object[7].toString()+" ("+check+")");
			}else{
				documentTo.setPaidAmout(object[7].toString());
			}
		}if(object[8]!=null){
			documentTo.setFeePaidOn(object[8].toString());
		}if(object[10]!=null && Double.parseDouble(object[10].toString())>0){
			documentTo.setConcession(object[10].toString());
		}
		if(object[11]!=null && Double.parseDouble(object[11].toString())>0){
			documentTo.setTotalAmount(object[11].toString());
		}
		if(object[12]!=null && object[12].equals(true)){
			documentTo.setPaid("Yes");
		}else{
			documentTo.setPaid("No");
		}
		documentlist.add(documentTo);
	}
	}
	return documentlist;
}

/**
 * @param course
 * @return
 */
public List<CourseTO> SetCourses(List<Object[]> course) {
	List<CourseTO> courseList=new ArrayList<CourseTO>();
	Iterator<Object[]> itr=course.iterator();
	while (itr.hasNext()) {
		CourseTO courseto=new CourseTO();
		Object[] courses = (Object[]) itr.next();
		courseto.setName(courses[2].toString()+"-"+courses[0].toString());
		courseto.setId(Integer.parseInt(courses[1].toString()));
		courseList.add(courseto);
	}
	return courseList;
}


}
