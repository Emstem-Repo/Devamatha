<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function getInstallMentPaymentDetails(){
	document.getElementById("method").value="initInstallmentPaymentDetails";	
	document.installmentPaymentForm.submit();	
}
</script>
<html:form action="/installmentPayment" method="post">
<html:hidden property="method" styleId="method" value="initInstallmentPaymentDetails"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="installmentPaymentForm" />
<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.fee"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.fee.installment.payment"/>
			  &gt;&gt;</span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">  <bean:message key="knowledgepro.fee.installment.payment"/></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatorymark"/> </span></FONT></div>
					<div id="errorMessage"><FONT color="green"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.installment.payment.appno/reno/rollno.col"/> </div>
									</td>
								  <td width="44%" height="25" class="row-even"><span
										class="star"> 
										<html:text property="searchByValue" maxlength="9" size="9" styleClass="TextBox"></html:text>								  
								 <html:radio styleId="appNo"
					property="searchBy" value="1"><bean:message key="knowledgepro.interview.ApplicationNo"/> </html:radio>
				<html:radio styleId="regNo"
					property="searchBy" value="2"><bean:message key="knowledgepro.attendance.regno"/> </html:radio>
				<html:radio
					property="searchBy" value="3" styleId="rollNo"><bean:message key="knowledgepro.attendance.rollno"/> </html:radio>							  
								  </td>
                                  </tr>
                                  <tr>
                                  <td width="17%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col"/> </div></td>
                  <td width="19%" colspan="5" class="row-even" >
                  <html:select property="academicYear" styleClass="combo"
								styleId="year">
								<html:option value="">
								<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<cms:renderYear>
								</cms:renderYear>
							</html:select>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="center">
						<html:submit styleClass="formbutton" >
							<bean:message key="knowledgepro.submit" /></html:submit>								
							</div>
							</td>							
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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