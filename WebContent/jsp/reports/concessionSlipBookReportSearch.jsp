<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html:form action="/ConcSlipBookReport">
	<html:hidden property="method" styleId="method" value = "displayConcessionSlipBooks"/>
	<html:hidden property="formName" value="concessionSlipBookReportForm" />
	<html:hidden property="pageType" value="1" />

	<table width="99%" border="0">
	  
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.fee"/> &gt;&gt; <bean:message key="knowledgepro.fee.concession.slip.books"/> &gt;&gt;</span></span></td>
	
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.fee.concession.slip.books"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	
	       <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" class="news">
			<div align="right">
				<FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
				</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                <tr >
	                  <td height="25" class="row-odd" width="15%"><div align="right"><bean:message key="knowledgepro.inventory.counter.master.type.col"/></div></td>
	                  <td class="row-even" width="20%"><html:select
										property="type" styleClass="TextBox" styleId="type">
										<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
									<html:option value='<%=CMSConstants.CONCESSION_TYPE %>'><bean:message key="knowledgepro.fee.concession.slip.book.conc.type"/></html:option>
									<html:option value='<%=CMSConstants.INSTALLMENT_TYPE %>'><bean:message key="knowledgepro.fee.concession.slip.installment.type"/></html:option>
									<html:option value='<%=CMSConstants.SCHOLARSHIP_TYPE %>'><bean:message key="knowledgepro.fee.concession.slip.scholarship.type"/></html:option>
									</html:select></td>
	                  </tr>
	              </table>
	             </td>
	
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td height="35" align="right">
						<html:submit property="" styleClass="formbutton" value="Search">
						</html:submit>
					</td>
				<td width="3" height="35" align="center">&nbsp;</td>
	              <td width="49%">
	              <html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button></td>
	          </tr>
	
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	        </table>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	
	        <td width="0" background="images/TcenterD.gif"></td>
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>
</html:form>	
<script type="text/javascript">
	var print = "<c:out value='${concessionSlipBookReportForm.print}'/>";
	if(print.length != 0 && print == "true") {
		var url ="ConcSlipBookReport.do?method=displayPage";
		myRef = window.open(url,"concession_slip_book_report","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
</script>

