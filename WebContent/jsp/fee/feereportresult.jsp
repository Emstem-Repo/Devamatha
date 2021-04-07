<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>

<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">
<html:html>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>
<SCRIPT type="text/javascript">
    function cancelAction() {
    	document.location.href = "feeReport.do?method=initFeeReport";
    }
</SCRIPT>
<body>

<html:form action="feeReport" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.fee"/> <span class="Bredcrumbs">&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.feereport.feereport.feereportsearch" /></strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top">

	<c:set var="temp" value="0" />    
		<display:table export="true" uid="studentid" name="sessionScope.studentSearch" requestURI="" defaultorder="descending" pagesize="10">
		<display:setProperty name="export.excel.filename" value="Feereport.xls"/>
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv.filename" value="Feereport.csv"/>
		<c:choose>
		<c:when test="${temp == 0}">											
			<display:column style="padding-left: 60px;" property="applicationNo" sortable="true" title="Application No." class="row-even" headerClass="row-odd"/>
			<display:column style="padding-left: 80px;" property="registrationNo" sortable="true" title="Registration No." class="row-even" headerClass="row-odd"/>
			<display:column style="padding-left: 40px;" property="status" sortable="true" title="Status" class="row-even" headerClass="row-odd"/>
			<display:column style="padding-left: 55px;" property="feeAccountList"  sortable="true" title="Fee Account" class="row-even" headerClass="row-odd"/>
			<display:column style="padding-left: 50px;" property="feeAccountTotalAmountList"   sortable="true" title="Fee Total Amount" class="row-even" headerClass="row-odd"/>

			<display:column style="padding-left: 50px;" property="concessionAmount"   sortable="true" title="Concession Amount" class="row-even" headerClass="row-odd"/>
			<display:column style="padding-left: 50px;" property="instalmentAmount"   sortable="true" title="Instalment Amount" class="row-even" headerClass="row-odd"/>
			<display:column style="padding-left: 50px;" property="amountPaid"   sortable="true" title="Amount Paid" class="row-even" headerClass="row-odd"/>
			<display:column style="padding-left: 60px;" property="amountBalance"   sortable="true" title="Amount Balance" class="row-even" headerClass="row-odd"/>
			<display:column style="padding-left: 30px;" property="excessShortAmount" media="csv excel xml pdf" title="Excess Short Amount"/>

			<display:column style="padding-left: 30px;" property="billNo" media="csv excel xml pdf" title="BillNo"/>
			<display:column style="padding-left: 40px;" property="totalAmount" media="csv excel xml pdf" title="Total Amount"/>	
			<display:column style="padding-left: 30px;" property="totalFeePaid" media="csv excel xml pdf" title="Total Fee Paid"/>
			<display:column style="padding-left: 35px;" property="isFeePaid" media="csv excel xml pdf" title="Fee Paid"/>
			<display:column style="padding-left: 70px;" property="feePaidDate" media="csv excel xml pdf" title="Fee Paid Date"/>
			<display:column style="padding-left: 30px;" property="paymentMode" media="csv excel xml pdf" title="Payment Mode"/>

			<display:column style="padding-left: 30px;" property="challenNo" media="csv excel xml pdf" title="Challen No."/>
			<display:column style="padding-left: 30px;" property="isChallenPrinted" media="csv excel xml pdf" title="Challen Printed"/>
			<display:column style="padding-left: 50px;" property="challenPrintedDate" media="csv excel xml pdf" title="Challen Printed Date"/>
			<display:column style="padding-left: 30px;" property="isCompletlyPaid" media="csv excel xml pdf" title="Completly Paid"/>
			<display:column style="padding-left: 40px;" property="consessionReferenceNo" media="csv excel xml pdf" title="Consession Reference No."/>
			<display:column style="padding-left: 30px;" property="installmentReferenceNo" media="csv excel xml pdf" title="Installment Reference No."/>
			<display:column style="padding-left: 30px;" property="installmentDate" media="csv excel xml pdf" title="Installment Date"/>
			<display:column style="padding-left: 50px;" property="totalConcessionAmount" media="csv excel xml pdf" title="Total Concession Amount"/>
			<display:column style="padding-left: 30px;" property="totalInstallmentAmount" media="csv excel xml pdf" title="Total Installment Amount"/>
			<display:column style="padding-left: 50px;" property="totalBalanceAmount" media="csv excel xml pdf" title="Total Balance Amount"/>

				<c:set var="temp" value="1" />
		</c:when>
		<c:otherwise>
			<display:column style="padding-left: 60px;" property="applicationNo" sortable="true" title="Application No." class="row-white" headerClass="row-odd"/>
			<display:column style="padding-left: 80px;" property="registrationNo" sortable="true" title="Registration No." class="row-white" headerClass="row-odd"/>
			<display:column style="padding-left: 40px;" property="status" sortable="true" title="Status" class="row-white" headerClass="row-odd"/>
			<display:column style="padding-left: 55px;" property="feeAccountList" sortable="true" title="Fee Account" class="row-white" headerClass="row-odd"/>
			<display:column style="padding-left: 50px;" property="feeAccountTotalAmountList"  sortable="true" title="Fee Total Amount" class="row-white" headerClass="row-odd"/>

			<display:column style="padding-left: 50px;" property="concessionAmount"  sortable="true" title="Concession Amount" class="row-white" headerClass="row-odd"/>
			<display:column style="padding-left: 50px;" property="instalmentAmount"  sortable="true" title="Instalment Amount" class="row-white" headerClass="row-odd"/>
			<display:column style="padding-left: 50px;" property="amountPaid"  sortable="true" title="Amount Paid" class="row-white" headerClass="row-odd"/>
			<display:column style="padding-left: 60px;" property="amountBalance"  sortable="true" title="Amount Balance" class="row-white" headerClass="row-odd"/>
			<display:column style="padding-left: 30px;" property="excessShortAmount" media="csv excel xml pdf" title="Excess Short Amount"/>

			<display:column style="padding-left: 30px;" property="billNo" media="csv excel xml pdf" title="BillNo"/>
			<display:column style="padding-left: 40px;" property="totalAmount" media="csv excel xml pdf" title="Total Amount"/>	
			<display:column style="padding-left: 30px;" property="totalFeePaid" media="csv excel xml pdf" title="Total Fee Paid"/>
			<display:column style="padding-left: 35px;" property="isFeePaid" media="csv excel xml pdf" title="Fee Paid"/>
			<display:column style="padding-left: 70px;" property="feePaidDate" media="csv excel xml pdf" title="Fee Paid Date"/>
			<display:column style="padding-left: 30px;" property="paymentMode" media="csv excel xml pdf" title="Payment Mode"/>

			<display:column style="padding-left: 30px;" property="challenNo" media="csv excel xml pdf" title="Challen No."/>
			<display:column style="padding-left: 30px;" property="isChallenPrinted" media="csv excel xml pdf" title="Challen Printed"/>
			<display:column style="padding-left: 50px;" property="challenPrintedDate" media="csv excel xml pdf" title="Challen Printed Date"/>
			<display:column style="padding-left: 30px;" property="isCompletlyPaid" media="csv excel xml pdf" title="Completly Paid"/>
			<display:column style="padding-left: 40px;" property="consessionReferenceNo" media="csv excel xml pdf" title="Consession Reference No."/>
			<display:column style="padding-left: 30px;" property="installmentReferenceNo" media="csv excel xml pdf" title="Installment Reference No."/>
			<display:column style="padding-left: 30px;" property="installmentDate" media="csv excel xml pdf" title="Installment Date"/>
			<display:column style="padding-left: 50px;" property="totalConcessionAmount" media="csv excel xml pdf" title="Total Concession Amount"/>
			<display:column style="padding-left: 30px;" property="totalInstallmentAmount" media="csv excel xml pdf" title="Total Installment Amount"/>
			<display:column style="padding-left: 50px;" property="totalBalanceAmount" media="csv excel xml pdf" title="Total Balance Amount"/>

				<c:set var="temp" value="0" />
			</c:otherwise>
		</c:choose>										
		</display:table>


















             </td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="61" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
          
              <table width="100%" height="48"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="25"><div align="center">                  
					<html:button property="cancel" onclick="cancelAction()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button>
                  </div></td>
                </tr>
              </table>
            
        </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>
