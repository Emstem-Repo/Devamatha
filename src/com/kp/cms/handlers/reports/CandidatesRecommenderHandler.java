package com.kp.cms.handlers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.reports.RecommendedCandidatesForm;
import com.kp.cms.to.reports.CandidatesRecommendorTO;
import com.kp.cms.transactions.reports.ICandidatesRecommendorTransaction;
import com.kp.cms.transactionsimpl.reports.CandidatesRecommendorTxnImpl;

public class CandidatesRecommenderHandler {
	
	private static final Log log = LogFactory.getLog(CandidatesRecommenderHandler.class);

	public static volatile CandidatesRecommenderHandler recommenderHandler = null;
		
		/**
		 * This method is used to create a single instance of CandidatesRecommenderHandler.
		 * @return unique instance of CandidatesRecommenderHandler class.
		 */
		
		public static CandidatesRecommenderHandler getInstance() {
			if (recommenderHandler == null) {
				recommenderHandler = new CandidatesRecommenderHandler();
				return recommenderHandler;
			}
			return recommenderHandler;
		}

		
		public String buildQuery(RecommendedCandidatesForm candidatesForm) throws Exception{
			log.info("entering of buildQuery method in CandidatesRecommenderHandler class.. ");
			String query = "";
			
			if(candidatesForm.getProgramId() != null && candidatesForm.getProgramId().length() >0){
				query = query +" where apc.admAppln.courseBySelectedCourseId.program.id = "+ candidatesForm.getProgramId().trim();
			}
			if(candidatesForm.getRecommendedBy() != null && candidatesForm.getRecommendedBy().length() != 0){
				query = query +" and apc.recommendor.id = "+ candidatesForm.getRecommendedBy().trim();
			}
			query = query + "order by apc.admAppln.id";
			log.info("exit of buildQuery method in CandidatesRecommenderHandler class.. ");
			return query;
		}
		
		public List<CandidatesRecommendorTO> getValuesBasedonQuery(RecommendedCandidatesForm candidatesForm) throws Exception{
			log.info("entering of getValuesBasedonQuery method in CandidatesRecommenderHandler class.. ");
			ICandidatesRecommendorTransaction recommendorTransaction = CandidatesRecommendorTxnImpl.getInstance();

			String query = buildQuery(candidatesForm);
			String finalQuery = "select apc.recommendor.name," +
					" apc.admAppln.applnNo," +
					" apc.admAppln.personalData.firstName," +
					" apc.admAppln.personalData.middleName," +
					" apc.admAppln.personalData.lastName" +
					" from ApplicantRecommendor apc" +
					query;
			
			List<Object[]> list = recommendorTransaction.getAppRecommendorDetails(finalQuery);
			
			List<CandidatesRecommendorTO> recomList = convertBOtoTO(list);
			log.info("exit of getValuesBasedonQuery method in CandidatesRecommenderHandler class.. ");
			return recomList;
		}


		private List<CandidatesRecommendorTO> convertBOtoTO(List<Object[]> list) {
			log.info("entering of convertBOtoTO method in CandidatesRecommenderHandler class.. ");
			List<CandidatesRecommendorTO> candRecomList = new ArrayList<CandidatesRecommendorTO>();
			CandidatesRecommendorTO recommendorTO = null;
			if(list != null && list.size() != 0){
			Iterator<Object[]> iterator = list.iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator
						.next();
				recommendorTO = new CandidatesRecommendorTO();
				if(obj[0]!= null ){
					recommendorTO.setRecommendorName((String)obj[0]);
				}
				if(obj[1]!= null ){
					recommendorTO.setAppno((Integer)obj[1]);
				}
				String studentName = "";
				if(obj[2] != null){
					studentName = studentName+ (String)obj[2]+" ";
				}
				if(obj[3] != null){
					studentName = studentName+ (String)obj[3]+" ";				
				}
				if(obj[4] != null){
					studentName = studentName+ (String)obj[4];
				}
				recommendorTO.setStudentName(studentName);
			}
			candRecomList.add(recommendorTO);
			}
			log.info("exit of convertBOtoTO method in CandidatesRecommenderHandler class.. ");
			return candRecomList;
		}
}