<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="com.kp.cms.constants.KPPropertiesConfiguration"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link rel='stylesheet'  href="css/auditorium/start/jquery-ui.css" />
<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>

 <!-- for cache controling with html code-->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
 
 <!-- for cache controling with jsp code-->
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>

<%-- 
<script src="js/admission/admissionButtons.js" type="text/javascript"></script>
--%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</head>
 
<body>

<html:form action="/onlineApplicationSubmit" method="post" enctype="multipart/form-data">

<!-- catch exception in jsp page -->
<c:catch var ="catchException">

<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" styleId="onlineApplicationForm" value="onlineApplicationForm" />
<html:hidden property="currentPageNo" styleId="currentPageNo"/>
<html:hidden property="displayPage" styleId="displayPage"/>
<html:hidden property="singlePageAppln" name="onlineApplicationForm" value="true"/>
<html:hidden property="onlineApply" name="onlineApplicationForm" value="true"/>
	<html:hidden property="detailsView" name="onlineApplicationForm" value="false" />
	<html:hidden property="isInterviewSelectionSchedule" styleId="isInterviewSelectionSchedule" name="onlineApplicationForm"/>
	<html:hidden property="reviewed" styleId="reviewed" name="onlineApplicationForm"/>
	<html:hidden property="programYear" styleId="programYear" name="onlineApplicationForm"/>
	<html:hidden property="semesterMarkPage" styleId="semesterMarkPage" name="onlineApplicationForm"/>
	<html:hidden property="detailMarkPage" styleId="detailMarkPage" name="onlineApplicationForm"/>

<%--  
<logic:notEmpty property="displayPage" name="onlineApplicationForm">
	   <logic:equal value="basic" property="displayPage" name="onlineApplicationForm">
	  	 <html:hidden property="pageType" value="26" />
		</logic:equal>
		<logic:equal value="guidelines" property="displayPage" name="onlineApplicationForm">
	  	 <html:hidden property="pageType" value="12" />
		</logic:equal>
		<logic:equal value="terms" property="displayPage" name="onlineApplicationForm">
	  	 <html:hidden property="pageType" value="12" />
		</logic:equal>
		<logic:equal value="details" property="displayPage" name="onlineApplicationForm">
	  	 <html:hidden property="pageType" value="18" />
		</logic:equal>
		<logic:equal value="payment" property="displayPage" name="onlineApplicationForm">
	  	 <html:hidden property="pageType" value="1" />
		</logic:equal>
</logic:notEmpty>

--%>

<div>

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td>

<logic:notEmpty property="displayPage" name="onlineApplicationForm">
	  
	  
	   <logic:equal value="basic" property="displayPage" name="onlineApplicationForm">
		    
		    <tr id="basic_page">
		    	<td>
				    <jsp:include page="/jsp/admission/OnlineDetailsBasicInfo.jsp"/>
		    	</td>
		    </tr>
		    
	    </logic:equal>
	    
	   
	   <logic:equal value="payment" property="displayPage" name="onlineApplicationForm">
		  <tr id="payment_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/OnlineDetailsPaymentInfo.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
	    </logic:equal>
	    
	    <logic:equal value="paymentsuccess" property="displayPage" name="onlineApplicationForm">
		  <tr id="paymentsuccess_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/OnlineDetailsPaymentSuccessInfo.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
	    </logic:equal>
	   
	    <logic:equal value="preferences" property="displayPage" name="onlineApplicationForm">
		  <tr id="preferences_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/OnlineDetailsCoursePreferenceInfo.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
	    </logic:equal>
	    
	    
	   <logic:equal value="personaldetail" property="displayPage" name="onlineApplicationForm">
		  <tr id="personaldetail_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/OnlineDetailsPersonalInfo.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
	    </logic:equal>
	    
	   
	    <logic:equal value="educationaldetail" property="displayPage" name="onlineApplicationForm">
		  <tr id="educationaldetail_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/OnlineDetailsEducationalInfo.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
	    </logic:equal>
	    
	    	
		 <logic:equal value="detailMarkPage" property="displayPage" name="onlineApplicationForm">
		  <tr id="details_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/onlineDetailsMarks.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
		</logic:equal>
		
		<logic:equal value="detailMarkPage12" property="displayPage" name="onlineApplicationForm">
		  <tr id="details_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/onlineDetailsMarks12.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
		</logic:equal>
	
		<logic:equal value="detailMarkPageDegree" property="displayPage" name="onlineApplicationForm">
		  <tr id="details_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/onlineDetailsMarksDegree.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
		</logic:equal>
		
	   
	     <logic:equal value="semesterMarkPage" property="displayPage" name="onlineApplicationForm">
		  <tr id="details_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/OnlineAppSemesterMarks.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
	    </logic:equal>
	    
	    
	    <logic:equal value="detailMarkPageView" property="displayPage" name="onlineApplicationForm">
		  <tr id="details_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/onlineDetailsMarksView.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
		</logic:equal>
		
		<logic:equal value="detailMarkPage12View" property="displayPage" name="onlineApplicationForm">
		  <tr id="details_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/onlineDetailsMarks12View.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
		</logic:equal>
	
		<logic:equal value="detailMarkPageDegreeView" property="displayPage" name="onlineApplicationForm">
		  <tr id="details_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/onlineDetailsMarksDegreeView.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
		</logic:equal>
		
	   
	    
	    
		
		 <logic:equal value="onlineLateralEntry" property="displayPage" name="onlineApplicationForm">
		  <tr id="details_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/OnlineLateralDetailsConfirm.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
	    </logic:equal>
	    
	    
	     <logic:equal value="onlineTransferEntry" property="displayPage" name="onlineApplicationForm">
		  <tr id="details_page">
		    	<td>
		    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    		<jsp:include page="/jsp/admission/OnlineTransferDetailsConfirm.jsp"/>
		    	</logic:equal>
		    	</td>
		    </tr>
	    </logic:equal>
	    
		
		
		 <logic:equal value="attachment" property="displayPage" name="onlineApplicationForm">
		    <tr id="attachment_page">
		    	<td>
		    		<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
		    			<jsp:include page="/jsp/admission/OnlineDetailsDocumentAttachmentInfo.jsp"/>
					</logic:equal>
		    	</td>
		    </tr>
	   </logic:equal>
	  
	   
		
	 
		    
		    <logic:equal value="confirmPage" property="displayPage" name="onlineApplicationForm">
			  <tr id="confirm_Page">
			    	<td>
			    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
			    		<jsp:include page="/jsp/admission/OnlineDetailsConfirmPageInfo.jsp"/>
			    	</logic:equal>
			    	</td>
			    </tr>
			 </logic:equal>
			 
			 <logic:equal value="previewpage" property="displayPage" name="onlineApplicationForm">
			  <tr id="confirm_Page">
			    	<td>
			    	<logic:equal property="onlineApply" value="true" name="onlineApplicationForm">
			    		<jsp:include page="/jsp/admission/OnlineDetailsPreview.jsp"/>
			    	</logic:equal>
			    	</td>
			    </tr>
			 </logic:equal>
			 
			 
</logic:notEmpty>


 </td>
 </tr>
</table>
</div>
</c:catch>

<!-- if  exception are there in jsp page logout out application-->
<c:if test = "${catchException != null}">

   <%
   session.setAttribute("errorinJSP","errorinJSP");
   System.out.println("************************ error details in online admission in jsp page*************************");
	
   %>
   
   <jsp:forward page="/uniqueIdRegistration.do?method=initOnlineApplicationLogin"/>
   
</c:if>

</html:form>
</body>


