package com.kp.cms.utilities;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;

public class MySessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		try {
		    HttpSession session=sessionEvent.getSession();
		    String userId=(String) session.getAttribute("uid");
		    if(userId != null && !userId.isEmpty()){
		    	ILoginTransaction loginTransaction = new LoginTransactionImpl();
		    	loginTransaction.getUserAndSetLoggedIn(Integer.parseInt(userId));
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
