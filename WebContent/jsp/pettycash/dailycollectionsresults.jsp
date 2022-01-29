<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title></title>
</head>

<SCRIPT type="text/javascript">

	    function cancelAction() {
	    	document.location.href = "collectionsDay.do?method=initcollectionsReport";
	    }                        
	    function printAction(){
	  var url = "collectionsDay.do?method=printCollectinsResult";
	 myRef = window.open(url,"collectionsDay","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	 
	   }

	 
	    
	    
	    
	    
	</SCRIPT>

<body>
<html:form action="/collectionsDay">

	<html:hidden property="method" styleId="method"
		value="initcollectionsReport" />
	<html:hidden property="formName" value="collectionsReportForm" />
	<html:hidden property="pageType" value="3" />

<table width="100%" border="0">
			<tr>
		<td><span class="Bredcrumbs"><bean:message
			key="knowledgepro.pettycash" /> <span class="Bredcrumbs">&gt;&gt; <bean:message
			key="knowledgepro.petticash.collections" /> &gt;&gt;</span></span></td>
	</tr>
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="9"><img src="images/Tright_03_01.gif" width="9"
					height="29"></td>
				<td background="images/Tcenter.gif" class="body"><strong
					class="boxheader"> <bean:message
					key="knowledgepro.petticash.collections" /></strong></td>

				<td width="10"><img src="images/Tright_1_01.gif" width="9"
					height="29"></td>
			</tr>
			<tr>
				<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

				<td valign="top" class="news">
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td><img src="images/01.gif" width="5" height="5" /></td>
						<td width="914" background="images/02.gif"></td>
						<td><img src="images/03.gif" width="5" height="5" /></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td valign="top">

						<table width="100%">

							<tr align="center">
								<td colspan="8" align="center">
								<div align="center" class="header"><bean:write property="organizationName" name="collectionsReportForm"/><br>
								COLLECTIONS FOR THE DAY 
										<bean:write name='collectionsReportForm' property='startDate' /> &nbsp; to &nbsp;<bean:write name='collectionsReportForm' property='endDate' />
									
									</div>
								</td>
							</tr>
							<tr>
								<td height="5%" class="row-odd"><div align="center">ReceiptNumber</div></td>
								<td height="5%" class="row-odd"><div align="center">Appl.No</div></td>
								<td height="5%" class="row-odd"><div align="center">Register No</div></td>
								<td  height="5%" class="row-odd"><div align="center">Name</div></td>
								<td  height="5%" class="row-odd"><div align="center">Time</div></td>
								<td height="5%" class="row-odd"><div align="center">A/c Name</div></td>
								<td height="5%" class="row-odd"><div align="center">Class Name</div></td>
								
								<logic:notEmpty
									name="collectionsReportForm" property="totalAccountList">
									<logic:iterate id="dailycollections1"
										name="collectionsReportForm" property="totalAccountList">
									  <td class="row-odd" align="center">
										<bean:write name="dailycollections1"  property="accName"/>
										</td>
									</logic:iterate>
								</logic:notEmpty>
								
								<td  class="row-odd" ><div align="center">Total Amount</div></td>
							</tr>
							<logic:notEmpty name="dailycollections">
								<logic:iterate id="dailycollections1" name="dailycollections">
									<tr>
										<td height="5%" class="row-even"><div align="center"><bean:write
											name='dailycollections1' property='receiptOrStudentnum' /></div></td>
										<td  height="5%" class="row-even">
										<div align="center"><bean:write
											name='dailycollections1' property='applicationNo' />
											</div>
										</td>
										  <td  height="5%" class="row-even">
										  <div align="center"><bean:write
											name='dailycollections1' property='regNo' />
											</div>
										</td>
										<td height="5%" class="row-left"><bean:write
											name='dailycollections1' property='name' /></td>
										<td   height="5%" class="row-even">
										<div align="center"><bean:write
											name='dailycollections1' property='time' />
											</div>
									    </td>
										<td   height="5%" class="row-even"><bean:write
											name='dailycollections1' property='accountName' /></td>
							<c:choose>
										<c:when test="${dailycollections1.classname!=null}">
											<td  height="5%" class="row-even"><bean:write
											name='dailycollections1' property='classname' /></td>
										</c:when>
										<c:otherwise>
											<td  height="5%" class="row-even"><bean:write
											name='dailycollections1' property='coursecode' /></td>
										</c:otherwise>
						</c:choose>
								
										<!-- <td  height="5%" class="row-even"><bean:write
											name='dailycollections1' property='coursecode' /></td>-->
										
												<logic:iterate id="tempid" name="dailycollections1" property="accountHeadList" indexId="count">
												<td  class="row-right" >
												<bean:write name='tempid' property='amount' /> </td> 
												</logic:iterate>
											
										<td width="5%" class="row-right">
											<logic:iterate id="tempid1" name="dailycollections1" property="accountHeadList" indexId="count">
											<bean:write property='amount' name='tempid1'/>
											</logic:iterate>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
							<tr><td  colspan="4" height="5%" class="row-even"></td>
								<td colspan="3" height="5%" class="bold-fontAmount"> Total Amount</td>
								
										<logic:iterate id="amountId" name="collectionsReportForm" property="totalAmountList" indexId="count">
										<td class="bold-fontAmount" >
										<bean:write name='amountId'/> </td> 
										</logic:iterate>
									
								<td  height="5%" class="bold-fontAmount">
								<bean:write name="collectionsReportForm" property="totamount"/>
								</td>
							</tr>
						</table>
						</td>
						<td width="5" height="29" background="images/right.gif"></td>
					</tr>
					<tr>
						<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
						<td background="images/05.gif"></td>
						<td><img src="images/06.gif" /></td>
					</tr>
				</table>
				</td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
				<td valign="top" class="news"><div align="center">
	            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="45%" height="35"><div align="right">
                  </div></td>
                  <td  style="padding-right: 5px;"><html:button property="print" onclick="printAction()" styleClass="formbutton"  styleId="printme" ><bean:message key="knowledgepro.print" /></html:button></td>
                  <td ><html:button property="cancel" onclick="cancelAction()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button></td>
                  <td width="44%"></td>
                </tr>
              </table>
	        </div></td>
				<td valign="top" class="news">&nbsp;</td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
				<td width="0" background="images/TcenterD.gif"></td>
				<td><img src="images/Tright_02.gif" width="9" height="29"></td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
</html:form>
</body>