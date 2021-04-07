<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function searchAmcDetails() {
		document.getElementById("itemCategoryName").value = document
				.getElementById("itemCategoryId").options[document
				.getElementById("itemCategoryId").selectedIndex].text;
		document.amcDetailsForm.submit();
	}
</script>
<html:form action="/AmcDetails">
	<html:hidden property="method" styleId="method" value = "submitAmcDetails"/>
	<html:hidden property="formName" value="amcDetailsForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="itemCategoryName" styleId="itemCategoryName"/>
	<table width="99%" border="0">
	  
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/> &gt;&gt; <bean:message key="knowledgepro.inventory.amc.details.entry"/> &gt;&gt;</span></span></td>
	
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.inventory.amc.details.entry"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="news">
			<div align="right">
				<FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
				</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif"></td>
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
	
	                  <td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.inventory.amc.details.item.category"/></div></td>
	                  <td width="25%" class="row-even" >
 						<html:select property="itemCategoryId" styleClass="comboLarge" styleId="itemCategoryId">
							<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="itemCategoryList"
									label="name" value="id" />
							</html:select> 
						</td>
	                  <td width="25%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.update.warranty.sl.no"/>:</div></td>
	                  <td width="25%" class="row-even" ><html:text property="itemNo" styleClass="TextBox"
									styleId="itemNo" size="20" maxlength="9" name="amcDetailsForm" /></td>
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
	            <td height="35" align="right"><html:button property=""
				styleClass="formbutton" value="Submit" onclick="searchAmcDetails()"></html:button></td>
				<td width="3" height="35" align="center">&nbsp;</td>
				<td height="35" align="left"><html:button property=""
					styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"/></td>
	
	          </tr>
	        </table></td>
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