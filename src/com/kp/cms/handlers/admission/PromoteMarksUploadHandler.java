package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.PettyCashCollection;
import com.kp.cms.bo.admission.PromoteMarks;
import com.kp.cms.bo.admission.PromoteSupliMarks;
import com.kp.cms.forms.admission.PromoteMarksUploadForm;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadsForm;
import com.kp.cms.handlers.pettycash.PettyCashAccountHeadsHandler;
import com.kp.cms.helpers.admission.PromoteMarksUploadHelper;
import com.kp.cms.helpers.pettycash.PettyCashAccountHeadsHelper;
import com.kp.cms.to.admission.PromoteMarksUploadTo;
import com.kp.cms.to.exam.PromoteSupliMarksTo;
import com.kp.cms.to.pettycash.PettyCashCollectionTo;
import com.kp.cms.transactions.admission.IPromoteMarksUploadTransaction;
import com.kp.cms.transactionsimpl.admission.PromoteMarksUploadTxnImpl;

public class PromoteMarksUploadHandler {
	private static volatile PromoteMarksUploadHandler marksUploadHandler = null;
	public static PromoteMarksUploadHandler getInstance(){
		if(marksUploadHandler == null){
			marksUploadHandler = new PromoteMarksUploadHandler();
			return marksUploadHandler;
		}
		return marksUploadHandler;
	}
	/**
	 * @param promoteMarksList
	 * @return
	 * @throws Exception
	 */
	public boolean uploadPromoteMarks( List<PromoteMarksUploadTo> promoteMarksList,PromoteMarksUploadForm marksUploadForm)throws Exception {
		IPromoteMarksUploadTransaction transaction = PromoteMarksUploadTxnImpl.getInstance();
		List<PromoteMarks> promoteMarks = PromoteMarksUploadHelper.getInstance().convertToTOBo(promoteMarksList);
		List<String> regNos = transaction.getPromoteMarksRegNos(Integer.parseInt(marksUploadForm.getAcademicYear()));
		return transaction.uploadPromoteMarks(promoteMarks,regNos,marksUploadForm);
	}
	/**
	 * @param marksUploadForm
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public List<PromoteMarksUploadTo> getPromoteMarks(PromoteMarksUploadForm marksUploadForm,HttpSession session)throws Exception{
		PromoteMarksUploadHelper.getInstance().setSubjectNamesToForm(marksUploadForm,session);
		List<PromoteMarks> promoteMarks=PromoteMarksUploadTxnImpl.getInstance().getPromoteMarks(marksUploadForm);
		List<PromoteMarksUploadTo> marksToList=PromoteMarksUploadHelper.getInstance().convertMarksBoToTO(promoteMarks);
		return marksToList;
	}
	public List<PromoteSupliMarksTo> getPromoteSupliMarks(PromoteMarksUploadForm marksUploadForm,HttpSession session)throws Exception{
		PromoteMarksUploadHelper.getInstance().setSupliSubjectNamesToForm(marksUploadForm,session);
		List<PromoteSupliMarks> promoteSupliMarks=PromoteMarksUploadTxnImpl.getInstance().getPromoteSupliMarks(marksUploadForm);
		List<PromoteSupliMarksTo> supliMarksToList=PromoteMarksUploadHelper.getInstance().convertSupliMarksBoToTO(promoteSupliMarks);
		return supliMarksToList;
	}
	public Map<String,String> getCourses(String mode)throws Exception{
		return PromoteMarksUploadTxnImpl.getInstance().getCourses(mode);
	}
	
}
