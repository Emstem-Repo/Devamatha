package com.kp.cms.handlers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.to.inventory.InvTxTO;
import com.kp.cms.to.inventory.StockReportTO;
import com.kp.cms.transactions.inventory.IStocksReceiptTransaction;
import com.kp.cms.transactionsimpl.inventory.StocksReceiptTransactionImpl;

public class StockReportHandler {
	private static final Log log = LogFactory.getLog(StockReportHandler.class);

	public static volatile StockReportHandler self = null;

	public static StockReportHandler getInstance() {
		if (self == null) {
			self = new StockReportHandler();
		}
		return self;
	}

	private StockReportHandler() {

	}

	/**
	 * @param txDate
	 * @param locationId
	 * @return
	 * @throws Exception
	 */
	public List<InvTxTO> getItemTransactionsOnDate(String txDate, int locationId,String endDate)
			throws Exception {
		IStocksReceiptTransaction txn = new StocksReceiptTransactionImpl();
		List<InvTx> txnBos = txn.getItemTransactionsOnDate(txDate, locationId,endDate);
		List<InvTxTO> txnTos = this.convertTxnBosToTos(txnBos);
		return txnTos;
	}

	/**
	 * @param txnBos
	 * @return
	 * @throws Exception
	 */
	private List<InvTxTO> convertTxnBosToTos(List<InvTx> txnBos)
			throws Exception {
		List<InvTxTO> toList = new ArrayList<InvTxTO>();
		if (txnBos != null) {
			Iterator<InvTx> txItr = txnBos.iterator();
			while (txItr.hasNext()) {
				InvTx invTx = (InvTx) txItr.next();
				InvTxTO txTo = new InvTxTO();
				txTo.setId(invTx.getId());
				txTo.setInvItem(invTx.getInvItem());
				txTo.setInvLocation(invTx.getInvLocation());
				if (invTx.getClosingBalance() != null)
					txTo.setClosingBalance(invTx.getClosingBalance()
							.doubleValue());
				if (invTx.getOpeningBalance() != null)
					txTo.setOpeningBalance(invTx.getOpeningBalance()
							.doubleValue());
				txTo.setTxDisplayDate(invTx.getTxDate().toString());
				txTo.setTxDate(invTx.getTxDate());
				txTo.setTxType(invTx.getTxType());
				if (invTx.getQuantity() != null)
					txTo.setQuantity(invTx.getQuantity().doubleValue());
				toList.add(txTo);
			}
		}
		return toList;
	}

	/**
	 * @param selDt
	 * @param txnId
	 * @param selLoc
	 * @return
	 */
	public List<InvTxTO> getItemTransactionsDetails(String selDt, int txnId,
			int selLoc) throws Exception {
		IStocksReceiptTransaction txn = new StocksReceiptTransactionImpl();
		List<InvTx> txnBos = txn.getItemTransactionsOnDetail(selDt, txnId,
				selLoc);
		List<InvTxTO> txnTos = this.convertTxnBosToTos(txnBos);
		return txnTos;
	}

	public List<StockReportTO> getStockReportDetailOnDate(String date, int locationId)
			throws Exception {
		IStocksReceiptTransaction txn = new StocksReceiptTransactionImpl();

		List<StockReportTO> txnTos = convertBoTOTo(txn.getStockdetails(date,
				locationId));
		return txnTos;
	}

	private List<StockReportTO> convertBoTOTo(List stockdetails) {
		Iterator itr = stockdetails.iterator();
		ArrayList<StockReportTO> list = new ArrayList<StockReportTO>();
		StockReportTO objTo;
		while (itr.hasNext()) {
			Object[] object = (Object[]) itr.next();
			objTo = new StockReportTO();
			if (object[1] != null) {
				objTo.setName(object[1].toString());
			}
			
			if (object[2] != null) {
				objTo.setOpeningBalance(Double.parseDouble(object[2].toString()));
			}
			if (object[3] != null) {
				objTo.setClosingBalance(Double.parseDouble(object[3].toString()));
			}
			if (object[4] != null) {
				
				objTo.setOperation(getoperation(object[4].toString()));
			}
			list.add(objTo);

		}

		return list;
	}

	private String getoperation(String opType) {
		String value="";
		if (opType.contains("R")) {
			value = "RECEIVED";
			// objTO.setOperation("RECEIVED");
		} else if (opType.contains("I")) {
			value = "ISSUED";
			// objTO.setOperation("ISSUED");
		} else if (opType.contains("PR")) {
			value = "PURCHASE RETURNED";
			// objTO.setOperation("PURCHASE RETURNED");
		} else if (opType.contains("SA")) {
			value = "TRANSFER ISSUED";
			// objTO.setOperation("TRANSFER ISSUED");
		} else if (opType.contains("SI")) {
			value = "TRANSFER RECEIVED";
			// objTO.setOperation("TRANSFER RECEIVED");
		} else {
			value = "SALVAGED";
			// objTO.setOperation("SALVAGED");
		}
		return value;
	}

	private List<InvTxTO> convertBosToTos(List<InvTx> txnBos) throws Exception {
		List<InvTxTO> toList = new ArrayList<InvTxTO>();
		if (txnBos != null) {
			Iterator<InvTx> txItr = txnBos.iterator();
			while (txItr.hasNext()) {
				InvTx invTx = (InvTx) txItr.next();
				InvTxTO txTo = new InvTxTO();
				txTo.setId(invTx.getId());
				txTo.setInvItem(invTx.getInvItem());
				txTo.setInvLocation(invTx.getInvLocation());
				if (invTx.getClosingBalance() != null)
					txTo.setClosingBalance(invTx.getClosingBalance()
							.doubleValue());
				if (invTx.getOpeningBalance() != null)
					txTo.setOpeningBalance(invTx.getOpeningBalance()
							.doubleValue());
				txTo.setTxDisplayDate(invTx.getTxDate().toString());
				txTo.setTxDate(invTx.getTxDate());
				txTo.setTxType(invTx.getTxType());
				if (invTx.getQuantity() != null)
					txTo.setQuantity(invTx.getQuantity().doubleValue());
				//				
				// txTo.setListInvTo(getItemTransactionDetails(invTx.getTxDate()
				// .toString(), invTx.getId(), invTx.getInvLocation()
				// .getId()));
				//				
				// Set<InvTx> setInvTx =invTx.getInvLocation().getId()get
				txTo.setOperation(getItemTransactionDetails(invTx.getTxDate()
						.toString(), invTx.getId(), invTx.getInvLocation()
						.getId()));
				toList.add(txTo);
			}
		}
		return toList;
	}

	private String getItemTransactionDetails(String date, int txnId, int selLoc)
			throws Exception {
		IStocksReceiptTransaction txn = new StocksReceiptTransactionImpl();
		List<InvTx> txnBos = txn.getItemTransactionsOnDetail(date, txnId,
				selLoc);
		return convertTxnBosToTo(txnBos);
	}

	private String convertTxnBosToTo(List<InvTx> txnBos) {
		String value = null;
		//List<StockReportTO> toList = new ArrayList<StockReportTO>();
		if (txnBos != null) {
			Iterator<InvTx> txItr = txnBos.iterator();
			while (txItr.hasNext()) {
				InvTx invTx = (InvTx) txItr.next();
				// StockReportTO objTO = new StockReportTO();
				//
				// if (invTx.getClosingBalance() != null)
				// objTO.setClosingBalance(invTx.getClosingBalance()
				// .doubleValue());
				// if (invTx.getOpeningBalance() != null)
				// objTO.setOpeningBalance(invTx.getOpeningBalance()
				// .doubleValue());
				// objTO.setName(invTx.getInvItem().getName());

				String opType = invTx.getTxType();
				if (opType.contains("R")) {
					value = "RECEIVED";
					// objTO.setOperation("RECEIVED");
				} else if (opType.contains("I")) {
					value = "ISSUED";
					// objTO.setOperation("ISSUED");
				} else if (opType.contains("PR")) {
					value = "PURCHASE RETURNED";
					// objTO.setOperation("PURCHASE RETURNED");
				} else if (opType.contains("SA")) {
					value = "TRANSFER ISSUED";
					// objTO.setOperation("TRANSFER ISSUED");
				} else if (opType.contains("SI")) {
					value = "TRANSFER RECEIVED";
					// objTO.setOperation("TRANSFER RECEIVED");
				} else {
					value = "SALVAGED";
					// objTO.setOperation("SALVAGED");
				}

				// toList.add(objTO);
			}
		}
		return value;

	}

}
