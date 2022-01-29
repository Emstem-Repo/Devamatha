package com.kp.cms.transactions.admin;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentSupportRequestBo;
import com.kp.cms.bo.supportrequest.CategoryBo;
import com.kp.cms.forms.admin.StudentSupportRequestForm;

public interface IStudentSupportRequestTransaction {

	List<CategoryBo> getCategoryForStudent()throws Exception;

	boolean saveSupportRequest(StudentSupportRequestBo studentSupportRequestBo, HttpServletRequest request)throws Exception;

	List<StudentSupportRequestBo> getStudentSupportRequest(String query)throws Exception;

	List<StudentSupportRequestBo> getAdminSupportRequest(String userId)throws Exception;

	List<CategoryBo> getCategoryForAdmin()throws Exception;

	List<StudentSupportRequestBo> getPendingSupportReq(String userId)throws Exception;

	List<CategoryBo> getSupportCategory()throws Exception;

	boolean updateCategory(int id, String categoryId, String userId)throws Exception;

	boolean updateStatusAndRemarks(int id, String status, String remarks, String userId)throws Exception;

	StudentSupportRequestBo getStudentSupportRequestBoById(int id)throws Exception;

	List<StudentSupportRequestBo> getSearchList(String query)throws Exception;

	List<StudentSupportRequestBo> getNoOfIssuesInOpen(Date sqlDate)throws Exception;

	Object[] getAdminSupportRequestId(StringBuilder stringBuilder)throws Exception;

	List<StudentSupportRequestBo> getPreviousSupportRequests(String query)throws Exception;

	Student  checkRegisterNoAvailable(String regNo)throws Exception;

}
