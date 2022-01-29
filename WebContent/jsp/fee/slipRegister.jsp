
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar_us.js">
</script>
<script language="JavaScript">
function resetAttReport()	{
	document.location.href = "slipregister.do?method=initSlipRegister";
	
	resetErrMsgs();
}

</script>


<html:form action="slipregister" focus="programType">
	<html:hidden property="method" styleId="method"	value="slipRegisterRecords" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="slipRegisterForm" />
	<html:hidden property="programTypeName" styleId="programTypeName"
		value="" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.fee" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.fee.slipRegister" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.fee.slipRegister" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									 <td width="22%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.program.type" /></div></td>
				     <td width="28%"  class="row-even" >
                    <html:select property="programTypeId"  styleId="programtype" styleClass="combo" onchange="getPrograms(this.value)">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    			<html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId"/>
	     			</html:select> 
                </td>
                
                <td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.fee.academicyear.col" /></div>
									</td>
									<td height="25" colspan="4" class="row-even"><input
										type="hidden" id="tempyear" name="tempyear"
										value="<bean:write name="slipRegisterForm" property="academicYear"/>" />
									<html:select property="academicYear" styleId="academicYear"
										styleClass="combo">
										<html:option value="">- Select -</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>
                
                
               
                            						</tr>


								<tr>
								 
               <td width="13%" class="row-odd"><div align="right"><span
												class="Mandatory">*</span><bean:message key="knowledgepro.fee.division"/><span class="star"></span>:</div></td>
                              <td width="19%"  class="row-even"><span
												class="star">
                                <html:select property="divisionid"
												styleClass="body" styleId="accountid" multiple="multiple" size="5">
                                    <html:optionsCollection name="feeDivisionList" label="name"
													value="id"  />
                                </html:select>
                              </span></td>
                              
                              <td height="25" class="row-odd" width="15%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.counter.master.type.col"/></div></td>
	                      <td class="row-even" >
             <html:select property="feetype"  styleId="feetype">
          		   		<html:option value="CONCESSION">Concession</html:option>
        			    <html:option value="INSTALLMENT">Installment</html:option>
        			    <html:option value="Scholarship">Scholarship</html:option>
	           		</html:select>	
				</td>
									
								</tr>

							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property="" styleClass="formbutton" onclick="resetAttReport()">
								<bean:message key="knowledgepro.admin.reset" />
							</html:button></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
