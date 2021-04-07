package com.kp.cms.transactions.fee;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Fee;
import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.SemesterWiseCourseBO;
import com.kp.cms.forms.fee.FeeAssignmentForm;
import com.kp.cms.forms.fee.FeePaymentForm;
import com.kp.cms.forms.fee.SemesterWiseCourseEntryForm;


/**
 * 
 * @date 19/Jan/2009
 * This is an interface for FeeAccountTransaction.
 */
public interface IFeeTransaction {
	
	public boolean addFeeAssignment(Fee fee) throws ConstraintViolationException,Exception;
	public List<Fee> getAllFees() throws Exception;
	public boolean deleteFeeAssignment(Fee fee) throws Exception;
	public boolean activateFeeAssignment(Fee fee) throws Exception;
	public Fee getFeeAssignmentById(int feeId) throws Exception;
	public List<FeeAccountAssignment> getFeesAssignAccounts(int feeId,Set<Integer> accountSet,Set<Integer> applicableSet) throws Exception;
	public boolean updateFeeAssignment(Fee fee) throws Exception;
	public List<Fee> getFeesPaymentDetailsForApplicationNo(Set<Integer>courseSet,String year,Set<Integer> semSet, boolean isAidedStudent, FeePaymentForm feePaymentForm) throws Exception ;
	public Fee getFeeByCompositeKeys(Fee feeOld, int feeCategoryId) throws Exception;
	public Map<Integer,String> getFeesGroupDetailsForCourse(Set<Integer>courseSet,String year) throws Exception;
	public List<FeeGroup> getFeeGroup()throws Exception;
	public Map<Integer, String> getCourse() throws Exception;
	public Fee getFeeData(SemesterWiseCourseEntryForm semesterWiseCourseEntryForm) throws Exception;
	public Course getObject(String courseId) throws Exception;
	public boolean saveDetails(SemesterWiseCourseBO bo2) throws Exception;
	public SemesterWiseCourseBO isSemesterWise(FeeAssignmentForm feeAssignmentForm) throws Exception;
	public SemesterWiseCourseBO getCourseObject(SemesterWiseCourseEntryForm semesterWiseCourseEntryForm) throws Exception;
	public Fee getFeeByCompositeKeysSemesterWise(Fee duplicateFee, int parseInt) throws Exception;
	public Fee getExistingObject(FeeAssignmentForm feeAssignmentForm) throws Exception;
	
}
