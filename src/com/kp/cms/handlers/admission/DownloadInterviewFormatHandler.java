package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.admission.DownloadInterviewFormatForm;
import com.kp.cms.helpers.admission.DownloadInterviewFormatHelper;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.transactions.admission.IDownloadInterviewFormat;
import com.kp.cms.transactionsimpl.admission.DownloadInterviewFormatTransactionImpl;

public class DownloadInterviewFormatHandler {
	/**
	 * Singleton object of DownloadInterviewFormatHandler
	 */
	private static volatile DownloadInterviewFormatHandler downloadInterviewFormatHandler = null;
	private static final Log log = LogFactory.getLog(DownloadInterviewFormatHandler.class);
	private DownloadInterviewFormatHandler() {
		
	}
	/**
	 * return singleton object of DownloadInterviewFormatHandler.
	 * @return
	 */
	public static DownloadInterviewFormatHandler getInstance() {
		if (downloadInterviewFormatHandler == null) {
			downloadInterviewFormatHandler = new DownloadInterviewFormatHandler();
		}
		return downloadInterviewFormatHandler;
	}
	/**
	 * @param ids
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Integer> getInterviewPerPanel(String[] ids, String type) throws Exception {
		String query=DownloadInterviewFormatHelper.getInstance().getQuery(ids,type);
		IDownloadInterviewFormat transaction=DownloadInterviewFormatTransactionImpl.getInstance();
		return transaction.getInterviewPerPanelMap(query);
	}
	/**
	 * @param downloadInterviewFormatForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<InterviewResultTO> getCandidates(DownloadInterviewFormatForm downloadInterviewFormatForm,HttpServletRequest request) throws Exception {
		String query=DownloadInterviewFormatHelper.getInstance().getsearchQuery(downloadInterviewFormatForm,request);
		IDownloadInterviewFormat transaction=DownloadInterviewFormatTransactionImpl.getInstance();
		List list=transaction.getCandidates(query);
		return DownloadInterviewFormatHelper.convertBotoTo(list);
	}
}
