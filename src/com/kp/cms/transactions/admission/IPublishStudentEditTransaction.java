package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.PublishStudentEdit;
import com.kp.cms.bo.admission.PublishStudentEditClasswise;
import com.kp.cms.forms.admission.PublishStudentEditForm;

public interface IPublishStudentEditTransaction {

	List<Student> getStudentList(PublishStudentEditForm publishStudentEditForm)throws Exception;

	boolean updateStudentlist(List<PublishStudentEdit> students)throws Exception;

	boolean updateClasswiseList(List<PublishStudentEditClasswise> classwises)throws Exception;

	List<PublishStudentEditClasswise> getClasswiseList(PublishStudentEditForm publishStudentEditForm)throws Exception;

	Map<Integer, PublishStudentEdit> getPublishStudentEdit(PublishStudentEditForm publishStudentEditForm)throws Exception;

	Map<Integer, PublishStudentEditClasswise> getpublishStudentEditClass(PublishStudentEditForm publishStudentEditForm)throws Exception;

	boolean deletePublishEdit(PublishStudentEditForm publishStudentEditForm)throws Exception;

	PublishStudentEditClasswise getDupentry(PublishStudentEditClasswise classwise)throws Exception;

	boolean reactiveClassId(int dupId, boolean activate,PublishStudentEditForm publishStudentEditForm)throws Exception;

	PublishStudentEditClasswise getEditDetails(PublishStudentEditForm publishStudentEditForm)throws Exception;

	boolean updateClass(PublishStudentEditClasswise editClasswise)throws Exception;

}
