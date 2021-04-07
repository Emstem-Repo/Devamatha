<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
	<script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
	 $('#submit').click(function(e){
		 e.preventDefault();
		 var regNo = document.getElementById("regNo").value;
		 if(regNo!=""){
			var url = "studentTranscriptPrint.do?method=studentTranscriptPrintSearch&regNo="+ regNo;
			myRef = window.open(url,"SMCP","left=20,top=20,width=800,height=730,toolbar=2,resizable=0,scrollbars=1");
		 }else{
			 $('#errorMessage').slideDown().html("<span>Please enter Register No.</span>");
			 return false;
		 }
	 });
	});

	function resetMessages(){
		document.getElementById("regNo").value = "";
		resetFieldAndErrMsgs();
	}
	
	</script>
	
<html:form action="/studentTranscriptPrint" method="post">
<html:hidden property="formName" value="StudentTranscriptPrintForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method" styleId="method" value=""/>
<table width="100%" border="0">
<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;
			Print Student Transcript</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Print Student Transcript </strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<font color="red" size="2"><div id="errorMessage">
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages></FONT></div></font>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif">
					<div id="errorMessage"></div>
					</td>
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
									<td width="50%" height="25" class="row-odd" align="right">
									<span class="Mandatory">*</span>&nbsp;
                                    <bean:message key="knowledgepro.exam.blockUnblock.regNo"/>:</td>
                                    <td width="50%" class="row-even"><div align="left"> <span class="star">
                                    <html:text property="regNo" styleId="regNo" size="20" />
                                    </span></div></td>									
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
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
							<td width="49%" height="35">
							<div align="right">
							<html:submit property="" styleClass="formbutton" value="Print Student Transcript"
										 styleId="submit"></html:submit>
									
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetMessages()"></html:button>
						</tr>
					
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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