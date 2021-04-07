<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">
function cancel(){
	document.location.href = "assignSapKey.do?method=initAssignSapKey";
	
}
</script>

<html:form action="/assignSapKey" focus="name">
<html:hidden property="formName" value="assignSapKeyForm" />
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> SAP<span class="Bredcrumbs">&gt;&gt;
			Assign SAP Keys &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Assign SAP Keys</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td  width="4%"  height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="15%"  height="5%" class="row-odd" ><div align="center">Class</div></td>
									<td width="15%"  height="5%" class="row-odd" ><div align="center">Register No</div></td>
                    				<td width="12%"  height="5%" class="row-odd" ><div align="center">Student Name</div></td>
                    				<td width="5%" class="row-odd"><div align="center">EMail</div></td>
                    				<td width="10%" class="row-odd"><div align="center">Key</div></td>
                 				</tr>
                 				<logic:notEmpty name="assignSapKeyForm" property="sapKeysTos">
								<nested:iterate id="CME" name="assignSapKeyForm" property="sapKeysTos" indexId="count">
                					<c:choose>
                   						<c:when test="${temp == 0}">
                   						
                   				<tr>
                   					<td  height="25" class="row-even" align="center"><c:out value="${count + 1}" /></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="className"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="regNo"/></td>
                   					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="name"/></td>
                   					<td  height="25" class="row-even" align="left"><nested:write name="CME" property="universityMail"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="key"/></td>
                   				</tr>
                    				<c:set var="temp" value="1"/>
                   						</c:when>
                    				<c:otherwise>
		            			<tr>
		            			
		            				<td  height="25" class="row-white" align="center"><c:out value="${count + 1}" /></td>
		            				<td  height="25" class="row-white" align="center"><nested:write name="CME" property="className"/></td>
		            				<td  height="25" class="row-white" align="center"><nested:write name="CME" property="regNo"/></td>
               						<td  height="25" class="row-white" align="left"><nested:write name="CME" property="name"/></td>
               						<td  height="25" class="row-white" align="left"><nested:write name="CME" property="universityMail"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="key"/></td>
               					</tr>
                    					<c:set var="temp" value="0"/>
				  					</c:otherwise>
                  				</c:choose>
                				</nested:iterate>
								</logic:notEmpty>
								
								
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
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35" align="center"></td>
							<td width="55%" height="35" align="left">
									<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
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

