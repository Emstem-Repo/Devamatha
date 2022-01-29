package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamInvigilationDutyBO;
import com.kp.cms.forms.exam.ExamAssignExaminerForm;
import com.kp.cms.helpers.exam.ExamAssignExaminerHelper;
import com.kp.cms.to.exam.InvDutyDetailsTO;
import com.kp.cms.to.exam.InvDutyMainTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamAssignExaminerImpl;
/* NO LONGER USED*/




//public class ExamAssignExaminerHandler extends ExamGenHandler {
//	ExamAssignExaminerHelper helper = new ExamAssignExaminerHelper();
//	ExamAssignExaminerImpl impl = new ExamAssignExaminerImpl();
//
//	public List<KeyValueTO> getInvigilatorList() {
//		List<ExamInvigilationDutyBO> lBO = new ArrayList(impl
//				.select_ActiveOnly(ExamInvigilationDutyBO.class));
//		return helper.convertInvDuty(lBO);
//	}
//
//	public ExamAssignExaminerForm getDetails(ExamAssignExaminerForm form) {
//		List<InvDutyMainTO> list = new ArrayList<InvDutyMainTO>();
//		InvDutyMainTO m = new InvDutyMainTO();
//
//		//	
//		KeyValueTO k;
//		List<KeyValueTO> invDutyTypeList = new ArrayList<KeyValueTO>();
//		k = new KeyValueTO(1, "Invigilator");
//
//		invDutyTypeList.add(k);
//		k = new KeyValueTO(2, "Reliver");
//
//		invDutyTypeList.add(k);
//		k = new KeyValueTO(3, "xyz");
//
//		invDutyTypeList.add(k);
//
//		//
//
//		List<InvDutyDetailsTO> invDutyDetailsList = new ArrayList<InvDutyDetailsTO>();
//		InvDutyDetailsTO dd;
//
//		dd = new InvDutyDetailsTO(1, "Shwetha", true, "very naughty", "101");
//		invDutyDetailsList.add(dd);
//		dd = new InvDutyDetailsTO(2, "Pooja", true, "very good", "102");
//		invDutyDetailsList.add(dd);
//		dd = new InvDutyDetailsTO(3, "Debi", false, "very boring- Ha ha ha- Puja", "103");
//		invDutyDetailsList.add(dd);
//		dd = new InvDutyDetailsTO(4, "xyz", true, "very zzzzzzzzzz", "104");
//		invDutyDetailsList.add(dd);
//		//
//		m.setInvDutyTypeList(invDutyTypeList);
//		m.setInvDutyDetailsList(invDutyDetailsList);
//		list.add(m);
//		form.setInvDutyListMain(list);
//		form.setInvDutyDetailsListSize(invDutyDetailsList.size());
//
//		return form;
//	}


