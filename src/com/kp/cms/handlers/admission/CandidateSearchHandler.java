package com.kp.cms.handlers.admission;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admin.StudentUploadPhotoAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ConfigReportsColumn;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.CandidateSearchForm;
import com.kp.cms.helpers.admission.CandidateHelper;
import com.kp.cms.to.admission.CandidateSearchTO;
import com.kp.cms.to.reports.ConfigureColumnForReportTO;
import com.kp.cms.transactions.admission.ICandidateSearchTxnImpl;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.reports.IConfigureColumnTransaction;
import com.kp.cms.transactionsimpl.admission.CandidateSearchTxnImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.reports.ConfigureColumnTransactionImpl;

/**
 * @author kalyan.c
 *
 */
public class CandidateSearchHandler {
	private static Log log = LogFactory.getLog(CandidateSearchHandler.class);
	
	public static volatile CandidateSearchHandler self=null;
	/**
	 * @return
	 * This method will return instance of this classes
	 */
	public static CandidateSearchHandler getInstance(){
		if(self==null)
			self= new CandidateSearchHandler();
		return self;
	}
	private CandidateSearchHandler(){
		
	}

	/**
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 * This method will get the search result of the selected criteria
	 */
	public List<CandidateSearchTO> getStudentSearchResults(
			CandidateSearchForm studentSearchForm) throws Exception {

		/*ICandidateSearchTxnImpl studentSearchTransactionImpl = CandidateSearchTxnImpl.getInstance();
		String query = CandidateHelper.getSelectionSearchCriteria(studentSearchForm);
		List<AdmAppln> admapplnBo =	studentSearchTransactionImpl.getStudentSearch(query);
		List<CandidateSearchTO> studentSearchTo = CandidateHelper.convertBoToTo(admapplnBo);*/
		
		// Code Had Rewritten By Balaji
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		String query=CandidateHelper.getSearchQueryForInput(studentSearchForm,"search");
		List<Object[]> list=transaction.getDataForQuery(query);
		return CandidateHelper.convertBoListToToList(list);
	}
	/**
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 * This method will get the search result of the selected criteria
	 */
	public boolean exportTOExcel(
			CandidateSearchForm studentSearchForm,HttpServletRequest request) throws Exception {
		boolean flage = false;
		List originalSelectedColumns = new ArrayList(studentSearchForm.getSelectedColumnsList());
		List<Integer> unSelectedList = new ArrayList<Integer>();
		String[] unSelected = studentSearchForm.getUnselectedColumnsArray();
		if(unSelected!=null){		
		for(int i=0; i<unSelected.length;i++ ){
			unSelectedList.add(Integer.parseInt(unSelected[i]));
		}
		}
		
//		seperate the selected from the unselected
		
		if(studentSearchForm.getFullList()!=null){
			Iterator<ConfigureColumnForReportTO> configList= studentSearchForm.getFullList().iterator();
			int count = 1;
			while (configList.hasNext()) {

				ConfigureColumnForReportTO configureColumnForReportTO = (ConfigureColumnForReportTO) configList.next();					
				if(unSelected!=null && unSelectedList.contains(configureColumnForReportTO.getId())){
					configList.remove();
				}else{	
					configureColumnForReportTO.setPosition(count);
					configureColumnForReportTO.setShowColumn(Boolean.TRUE);
					count++;
				}
								
			}
			
		}
		studentSearchForm.setSelectedColumnsList(studentSearchForm.getFullList());
		ICandidateSearchTxnImpl studentSearchTransactionImpl = CandidateSearchTxnImpl.getInstance();
//		ICandidateSearchTxnImpl studentSearchTransactionImpl = CandidateSearchTxnImpl.getInstance();
		String query = CandidateHelper.getSelectionSearchCriteria(studentSearchForm);
//		String query = CandidateHelper.sqlgetSelectionSearchCriteria(studentSearchForm);
//		Properties prop = new Properties();
//		InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
//        prop.load(in);
        //String path=prop.getProperty("knowledgepro.admin.student.photosource");
/*        String userName =prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");
        String url = prop.getProperty("jdbc.url");
        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
		Connection conn = null;
	    conn = DriverManager.getConnection (url, userName, password);
		List<Object[]> admapplnBo = studentSearchTransactionImpl.executeAdmissionReport(query,conn);
		if (conn != null)
        {
            try
            {
                conn.close ();
            }
            catch (Exception e) {  }
        }
*/		List<AdmAppln> admapplnBo = studentSearchTransactionImpl.getStudentSearch(query);
//		List<Integer> admIdsForSearch=new ArrayList<Integer>();
//		if(admapplnBo!=null && !admapplnBo.isEmpty()){
//		Iterator itr= admapplnBo.iterator();
//		while (itr.hasNext()) {
//			Object[] object = (Object[]) itr.next();
//			AdmAppln admAppln = (AdmAppln)object[0];
//			if(!admIdsForSearch.contains(admAppln.getId()))
//			admIdsForSearch.add(Integer.valueOf(admAppln.getId()));
//		}
//		}
//		List<Integer> admId= studentSearchTransactionImpl.getStudentListWithPhotos(admIdsForSearch);
		List<Integer> admId= new ArrayList<Integer>();
		CandidateSearchTO candidateSearchTO = CandidateHelper.getSelectedColumns(studentSearchForm.getSelectedColumnsList());
		List<CandidateSearchTO> studentSearchTo = CandidateHelper.convertBoToToExcel(admapplnBo, studentSearchForm,admId,candidateSearchTO);
//		List<CandidateSearchTO> studentSearchTo = CandidateHelper.sqlconvertBoToToExcel(admapplnBo);
		
		flage = saveSelectedColumns(studentSearchForm,originalSelectedColumns);
		if(studentSearchForm.getStatus().equals("other")){
			candidateSearchTO.setDispStatus(true);	
		}
		
//		CandidateHelper.exportTOExcel(studentSearchTo,candidateSearchTO, request);
		CandidateHelper.exportTOExcel(studentSearchTo,candidateSearchTO, request);
		
		//flage = saveSelectedColumns(studentSearchForm,originalSelectedColumns);
		return flage;
	}
	
	public void getColumnsReportList(CandidateSearchForm candidateSearchForm) throws Exception {
		ICandidateSearchTxnImpl studentSearchTransactionImpl = CandidateSearchTxnImpl.getInstance();
		List<ConfigReportsColumn> list = studentSearchTransactionImpl.getColumnsReportList();
		CandidateHelper.convertColumnsBOtoTO(list,candidateSearchForm);
	}
	
	/**
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 * This method will get the search result of the selected criteria
	 */
	/*public boolean exportTOCSV(			
			CandidateSearchForm studentSearchForm,HttpServletRequest request) throws Exception {
		boolean flage = false;
		List originalSelectedColumns = new ArrayList(studentSearchForm.getSelectedColumnsList());
		String[] unSelected = studentSearchForm.getUnselectedColumnsArray();		
		List<Integer> unSelectedList = new ArrayList<Integer>();
		if(unSelected!=null){	
			for(int i=0; i<unSelected.length;i++ ){
				unSelectedList.add(Integer.parseInt(unSelected[i]));
			}
		}
		if(studentSearchForm.getFullList()!=null){
			Iterator<ConfigureColumnForReportTO> configList= studentSearchForm.getFullList().iterator();
			int count = 0;
			while (configList.hasNext()) {

				ConfigureColumnForReportTO configureColumnForReportTO = (ConfigureColumnForReportTO) configList.next();					
				if(unSelected!=null && unSelectedList.contains(configureColumnForReportTO.getId())){
					configList.remove();
				}else{	
					configureColumnForReportTO.setPosition(count);
					configureColumnForReportTO.setShowColumn(Boolean.TRUE);
					count++;
				}
								
			}
			
		}
		studentSearchForm.setSelectedColumnsList(studentSearchForm.getFullList());
		ICandidateSearchTxnImpl studentSearchTransactionImpl = CandidateSearchTxnImpl.getInstance();
		String query = CandidateHelper.getSelectionSearchCriteria(studentSearchForm);
		Properties prop = new Properties();
		InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        prop.load(in);
        //String path=prop.getProperty("knowledgepro.admin.student.photosource");
        String userName =prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");
        String url = prop.getProperty("jdbc.url");
        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
		Connection conn = null;
	    conn = DriverManager.getConnection (url, userName, password);
		List<Object[]> admapplnBo = studentSearchTransactionImpl.executeAdmissionReport(query,conn);
		if (conn != null)
        {
            try
            {
                conn.close ();
            }
            catch (Exception e) {  ignore close errors  }
        }
//		List<CandidateSearchTO> studentSearchTo = CandidateHelper.sqlconvertBoToToExcel(admapplnBo);
		//List<CandidateSearchTO> studentSearchTo = CandidateHelper.convertBoToToExcel(admapplnBo);
		
		CandidateSearchTO candidateSearchTO = CandidateHelper.getSelectedColumns(studentSearchForm.getSelectedColumnsList());
		CandidateHelper.exportTOCSV(studentSearchTo,candidateSearchTO, request);
		flage = saveSelectedColumns(studentSearchForm,originalSelectedColumns);
		return flage;
	}*/
	
	
	/**
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 * This method will get the search result of the selected criteria
	 */
	
//	modifications done by priyatham
	public boolean exportTOCSV(			
			CandidateSearchForm studentSearchForm,HttpServletRequest request) throws Exception {
		boolean flage = false;
		List originalSelectedColumns = new ArrayList(studentSearchForm.getSelectedColumnsList());
		String[] unSelected = studentSearchForm.getUnselectedColumnsArray();		
		List<Integer> unSelectedList = new ArrayList<Integer>();
		if(unSelected!=null){	
			for(int i=0; i<unSelected.length;i++ ){
				unSelectedList.add(Integer.parseInt(unSelected[i]));
			}
		}
		if(studentSearchForm.getFullList()!=null){
			Iterator<ConfigureColumnForReportTO> configList= studentSearchForm.getFullList().iterator();
			int count = 0;
			while (configList.hasNext()) {

				ConfigureColumnForReportTO configureColumnForReportTO = (ConfigureColumnForReportTO) configList.next();					
				if(unSelected!=null && unSelectedList.contains(configureColumnForReportTO.getId())){
					configList.remove();
				}else{	
					configureColumnForReportTO.setPosition(count);
					configureColumnForReportTO.setShowColumn(Boolean.TRUE);
					count++;
				}
								
			}
			
		}
		studentSearchForm.setSelectedColumnsList(studentSearchForm.getFullList());
		ICandidateSearchTxnImpl studentSearchTransactionImpl = CandidateSearchTxnImpl.getInstance();
		String query = CandidateHelper.getSelectionSearchCriteria(studentSearchForm);
		List<AdmAppln> admapplnBo = studentSearchTransactionImpl.getStudentSearch(query);
		//added for photo available display
		List<Integer> admIdsForSearch=new ArrayList<Integer>();
		Iterator itr= admapplnBo.iterator();
		if(admapplnBo!=null && !admapplnBo.isEmpty()){
		
		while (itr.hasNext()) {
			Object[] object = (Object[]) itr.next();
			AdmAppln admAppln = (AdmAppln)object[0];
			admIdsForSearch.add(admAppln.getId());
		}
		}
		List<Integer> admId= studentSearchTransactionImpl.getStudentListWithPhotos(admIdsForSearch);
		CandidateSearchTO candidateSearchTO = CandidateHelper.getSelectedColumns(studentSearchForm.getSelectedColumnsList());
		List<CandidateSearchTO> studentSearchTo = CandidateHelper.convertBoToToExcel(admapplnBo, studentSearchForm,admId,candidateSearchTO);
		
		CandidateHelper.exportTOCSV(studentSearchTo,candidateSearchTO, request);
		flage = saveSelectedColumns(studentSearchForm,originalSelectedColumns);
		return flage;
	}	
	
	/**
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 * This method will get the search result of the selected criteria
	 */
	public List<ConfigReportsColumn> getSelectedColumns () throws Exception {

		ICandidateSearchTxnImpl studentSearchTransactionImpl = CandidateSearchTxnImpl.getInstance();

			
			List<ConfigReportsColumn> selectedColumns =studentSearchTransactionImpl.getSelectedColumns();
	
		return selectedColumns;
	}
	/**
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 * This method will get the search result of the selected criteria
	 */
	public boolean saveSelectedColumns (CandidateSearchForm studentSearchForm,List originalSelectedList) throws Exception {
//		if(studentSearchForm.getUnselectedColumnsList()!=null){
//			int count = 0;
//			Iterator<ConfigureColumnForReportTO> configList= studentSearchForm.getUnselectedColumnsList().iterator();
//			
//			if(studentSearchForm.getSelectedColumnsList()!=null){
//				count = studentSearchForm.getSelectedColumnsList().size() + 1;
//			}
//			while (configList.hasNext()) {
//				ConfigureColumnForReportTO configureColumnForReportTO = (ConfigureColumnForReportTO) configList.next();
//				configureColumnForReportTO.setPosition(count);
//				configureColumnForReportTO.setShowColumn(Boolean.FALSE);
//				count++;				
//			}
//		}

		List<ConfigReportsColumn> configReportColumn =  CandidateHelper.convertListToBo(studentSearchForm,originalSelectedList);
		IConfigureColumnTransaction transaction = ConfigureColumnTransactionImpl.getInstance();
		boolean flag = transaction.updateConfigReport(configReportColumn);
		return flag;
	}

	/**
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 * This method will get the search result of the selected criteria
	 */
	public List<CandidateSearchTO> sqlgetStudentSearchResults(
			CandidateSearchForm studentSearchForm) throws Exception {

		ICandidateSearchTxnImpl studentSearchTransactionImpl = CandidateSearchTxnImpl.getInstance();
		String query = CandidateHelper.sqlgetSelectionSearchCriteria(studentSearchForm);
		Properties prop = new Properties();
		InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        prop.load(in);
        //String path=prop.getProperty("knowledgepro.admin.student.photosource");
        String userName =prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");
        String url = prop.getProperty("jdbc.url");
        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
		Connection conn = null;
	    conn = DriverManager.getConnection (url, userName, password);
		List<Object[]> recordList =	studentSearchTransactionImpl.executeAdmissionReport(query,conn);
		if (conn != null)
        {
            try
            {
                conn.close ();
            }
            catch (Exception e) { /* ignore close errors */ }
        }
		List<CandidateSearchTO> studentSearchTo = CandidateHelper.sqlconvertBoToTo(recordList);
		
		return studentSearchTo;
	}
	
	
	// Code Rewritten By Balaji
	/**
	 * @param studentSearchForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean exportingTOExcel( CandidateSearchForm studentSearchForm,HttpServletRequest request) throws Exception {
		boolean flage = false;
		List originalSelectedColumns = new ArrayList(studentSearchForm.getSelectedColumnsList());
		List<Integer> unSelectedList = new ArrayList<Integer>();
		String[] unSelected = studentSearchForm.getUnselectedColumnsArray();
		if(unSelected!=null){		
		for(int i=0; i<unSelected.length;i++ ){
			unSelectedList.add(Integer.parseInt(unSelected[i]));
		}
		}
		
//		seperate the selected from the unselected
		
		if(studentSearchForm.getFullList()!=null){
			Iterator<ConfigureColumnForReportTO> configList= studentSearchForm.getFullList().iterator();
			int count = 1;
			while (configList.hasNext()) {

				ConfigureColumnForReportTO configureColumnForReportTO = (ConfigureColumnForReportTO) configList.next();					
				if(unSelected!=null && unSelectedList.contains(configureColumnForReportTO.getId())){
					configList.remove();
				}else{	
					configureColumnForReportTO.setPosition(count);
					configureColumnForReportTO.setShowColumn(Boolean.TRUE);
					count++;
				}
								
			}
			
		}
		studentSearchForm.setSelectedColumnsList(studentSearchForm.getFullList());
		ICandidateSearchTxnImpl studentSearchTransactionImpl = CandidateSearchTxnImpl.getInstance();
		// setting the selected Column Fields in To
		CandidateSearchTO candidateSearchTO = CandidateHelper.getSelectedColumns(studentSearchForm.getSelectedColumnsList());
		
		String query = CandidateHelper.getSearchQueryForInput(studentSearchForm,"export");
		List<AdmAppln> admapplnBo = studentSearchTransactionImpl.getStudentSearch(query);
		List<Integer> admId= new ArrayList<Integer>();
		List<CandidateSearchTO> studentSearchTo = CandidateHelper.convertBoToToExcel(admapplnBo, studentSearchForm,admId,candidateSearchTO);
		flage = saveSelectedColumns(studentSearchForm,originalSelectedColumns);
		if(studentSearchForm.getStatus().equals("other")){
			candidateSearchTO.setDispStatus(true);	
		}
		CandidateHelper.exportTOExcel(studentSearchTo,candidateSearchTO, request);
		return flage;
	}
	
	
}
