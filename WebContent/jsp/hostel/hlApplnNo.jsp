<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
var histelName=null;
var prefixName=null;
var startNumber=null;
var academicYear=null;
function editNumberEntry(id, startNum, frefix,hosteId,year) {
	document.getElementById("method").value = "updateNumber";
	document.getElementById("id").value = id;
	document.getElementById("startNumber").value = startNum;
	document.getElementById("preFix").value = frefix;
	document.getElementById("hostelId").value = hosteId;
	document.getElementById("academicYr").value = year;
	histelName=hosteId;
	prefixName=frefix;
	startNumber=startNum;
	academicYear=year;
	document.getElementById("submitbutton").value = "Update";
	resetErrMsgs();
}
	function cancel1(){
		document.location.href = "HlApplnNo.do?method=initNumberEntry";
	}
	function resetMessages() {
		document.getElementById("startNumber").value = "";
		document.getElementById("preFix").value = "";
		document.getElementById("hostelId").value = "";
		document.getElementById("academicYr").value = "";
		if(document.getElementById("method").value=="updateNumber"){
			document.getElementById("startNumber").value = startNumber;
			document.getElementById("preFix").value = prefixName;
			document.getElementById("hostelId").value = histelName;
			document.getElementById("academicYr").value = academicYear;
		}
	}
	function deleteDetails(id){
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "HlApplnNo.do?method=deleteNumber&id="+id;
		}
	}	
</script>

<html:form action="/HlApplnNo" focus="name">

	<html:hidden property="id" styleId="id" name="hlApplicationNumberForm" />
	<html:hidden property="formName" value="hlApplicationNumberForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="saveNumber"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			Hostel Application Number &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Hostel Application Number</strong></td>

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
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.year"/>:</div>
									</td>
									<td class="row-even"  width="25%" height="25">
										<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="hlApplicationNumberForm" property="academicYear"/>"/>
											<html:select property="academicYear" name="hlApplicationNumberForm"  styleClass="combo" styleId="academicYr">
											<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
										</html:select></td>	
									<td class="row-odd"  width="25%" height="25"><div align="right" ><span class="Mandatory">* </span>Hostel:</div></td>
                  					<td class="row-even"  width="25%" height="25">
                  						<html:select property="hostelId"
												styleClass="comboLarge" styleId="hostelId">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
											<logic:notEmpty name="hlApplicationNumberForm" property="hostelMap">
												<html:optionsCollection name="hlApplicationNumberForm" property="hostelMap" label="value" value="key" />
											</logic:notEmpty>
										</html:select>
									</td>
	                  			</tr>
	                  			<tr>
	                  				<td class="row-odd"  width="25%" height="25"><div align="right" ><span class="Mandatory">* </span>Prefix:</div></td>
                  					<td class="row-even"  width="25%" height="25">
                  						<html:text property="preFix" styleId="preFix" />
									</td>
									<td class="row-odd"  width="25%" height="25"><div align="right" ><span class="Mandatory">* </span>Start Number:</div></td>
                  					<td class="row-even"  width="25%" height="25">
                  						<html:text property="startNumber" styleId="startNumber" />
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
							<td  height="35" align="right" width="50%">
									<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
							</td>
							<td   align="left" width="4%">
								<html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages()"></html:button>
							</td>
							<td  height="35" align="left" width="40%">
							</td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
								<tr >
                    				<td width="5"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    				<td width="5"  height="5%" class="row-odd" ><div align="center">Hostel</div></td>
                    				<td width="15" height="30%" class="row-odd" align="center" >Prefix</td>
                    				<td width="15" height="30%" class="row-odd" align="center" >Start Number</td>
                    				<td width="15" height="30%" class="row-odd" align="center" >Academic Year</td>
                    				<td width="5" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    				<td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                    			<logic:notEmpty name="hlApplicationNumberForm" property="applicationNoList">
                 				<logic:iterate id="list" name="hlApplicationNumberForm" property="applicationNoList" indexId="count">
                					<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
	                   					<td  height="25"  ><div align="center"><c:out value="${count + 1}"/></div></td>
	                   					<td  height="25" align="center"><bean:write name="list" property="hostelName"/></td>
	                   					<td  height="25" align="center"><bean:write name="list" property="prefix"/></td>
	                   					<td  height="25" align="center"><bean:write name="list" property="startNumber"/></td>
	                   					<td  height="25" align="center"><bean:write name="list" property="academicyear"/></td>
	                   					<td  height="25"  align="center"> <div align="center"><img src="images/edit_icon.gif"
							 					height="18" style="cursor:pointer" onclick="editNumberEntry('<bean:write name="list" property="id"/>','<bean:write name="list" property="startNumber"/>','<bean:write name="list" property="prefix"/>','<bean:write name="list" property="hosteId"/>','<bean:write name="list" property="academicyear"/>')"> </div> </td>
	                   					<td  height="25" ><div align="center">
	                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="list" property="id"/>')"></div></td>
                				</logic:iterate>
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
