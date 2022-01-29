package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ConsolidateMarksCardSiNo;
import com.kp.cms.forms.exam.ConsolidateMarksCardSiNoForm;


public interface IConsolidateMarksCardSiNoTransaction {

	boolean save(ConsolidateMarksCardSiNo bo)throws Exception;

	List<ConsolidateMarksCardSiNo> getData()throws Exception;

	boolean getDataAvailable(ConsolidateMarksCardSiNoForm consolidateMarksCardSiNoForm)throws Exception;

}
