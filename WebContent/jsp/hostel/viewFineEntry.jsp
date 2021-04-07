<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	
	function cancelHome(){
		document.location.href = "fineEntry.do?method=initFineEntry";
	}
	function printDetails(){
		var url = "fineEntry.do?method=printFineEntry";
		myRef = window.open(url, "printHallTicket",
						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
	}
</script>

<html:form action="/fineEntry" focus="name">
	<html:hidden property="formName" value="fineEntryForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="aknowledgepro.hostel.fineEntry.display" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="aknowledgepro.hostel.fineEntry.display" /></strong></td>

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
					<div id="notValid" style="color: red"><FONT color="red"></FONT></div>
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
							
								<tr >
				  					<td class="row-odd" width="25%" height="25">
										<div align="right"><bean:message key="knowledgepro.admin.year"/>:</div>
									</td>
									<td class="row-even"  width="25%" height="25">
										<bean:write name="fineEntryForm" property="academicYear"/>
										</td>	
									<td class="row-odd"  width="25%" height="25"><div align="right"><bean:message key="knowledgepro.exam.reJoin.registerNo"/></div></td>
                  					<td class="row-even"  width="25%" height="25">
									<bean:write name="fineEntryForm" property="regNo"/>
									</td>
	                  			</tr>
	                  			<tr>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.admin.name"/>:</td>
				  					<td class="row-even" height="25">
				  						<bean:write name="fineEntryForm" property="studentName"/>
				  					</td>	
				  					<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.admin.course"/>:</td>
                  					<td class="row-even" height="25">
                  						<bean:write name="fineEntryForm" property="studentCourse"/>
                  					</td>	
	                  			</tr>
	                  			<tr>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel"/>:</td>
                  					<td class="row-even" height="25">
                  						<bean:write name="fineEntryForm" property="studentHostel"/>
									</td>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.blocks"/>:</td>
                  					<td class="row-even" height="25">
                  						<bean:write name="fineEntryForm" property="studentBlock"/>
									</td>
	                  			</tr>
	                  			<tr>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.units"/>:</td>
				  					<td class="row-even" height="25">
				  						<bean:write name="fineEntryForm" property="studentUnit"/>
				  					</td>
	                  				<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.roomno"/>:</td>
                  					<td class="row-even" height="25">
                  						<bean:write name="fineEntryForm" property="studentRoom"/>
                  					</td>	
	                  			</tr>
								<tr>
									<td class="row-odd" align="right" height="25"><bean:message key="knowledgepro.hostel.bedno"/>:</td>
                  					<td class="row-even" height="25">
                  						<bean:write name="fineEntryForm" property="studentBed"/>
                  					</td>
									<td class="row-odd" width="10%" >
									<div align="right"> <bean:message key="aknowledgepro.hostel.fineCategory.name"/>:</div>
									</td>
									<td class="row-even" width="20%"> 
									
						   			<bean:write name='fineEntryForm' property='categoryName'/>
                					</td>
								</tr>
								<tr>
									<td class="row-odd" width="10%">
										<div align="right">
											<bean:message key="aknowledgepro.hostel.fineCategory.amount"/>
										</div>
									</td>
									<td  class="row-even" width="20%">
									<bean:write name='fineEntryForm' property='amount'/>
									</td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>Remarks:</div>
									</td>
									<td width="25%" class="row-even"> 
										<bean:write name='fineEntryForm' property='remarks'/>
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
							<td  height="35" align="center" width="40%">
									<input name="Submit" type="reset" class="formbutton" value="Print" onclick="printDetails()"/>&nbsp;&nbsp;&nbsp;&nbsp;
									<input name="Submit" type="reset" class="formbutton" value="Close" onclick="cancelHome()"/>
							</td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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

