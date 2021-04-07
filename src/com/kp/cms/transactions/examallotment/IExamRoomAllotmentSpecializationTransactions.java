package com.kp.cms.transactions.examallotment;

import java.util.List;

import com.kp.cms.bo.examallotment.ExamRoomAllotmentSpecialization;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentSpecializationForm;

public interface IExamRoomAllotmentSpecializationTransactions {

	public boolean saveOrUpdateBOList(List<ExamRoomAllotmentSpecialization> boList)throws Exception;

	public List<ExamRoomAllotmentSpecialization> getAlreadyExistedBoList(  String midEndSem, String schemeNo)throws Exception;

	public boolean deleteSpecializationDetails( List<ExamRoomAllotmentSpecialization> existedBOList,ExamRoomAllotmentSpecializationForm objForm)throws Exception;

}
