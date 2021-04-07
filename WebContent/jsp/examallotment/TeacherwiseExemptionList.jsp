<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script src="jquery/development-bundle/jquery-1.7.1.js"></script>
    <link rel='stylesheet' type='text/css' href="css/auditorium/start/jquery-ui-supportRequest.css" />
	<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script type="text/javascript">
function cancel(){
	document.location.href = "teacherwiseExemption.do?method=initTeacherwiseExemption";
	
}
function deleteInvigilator(id){
	 $.confirm({
			'message'	: 'Are you sure, Do you want to delete ?',
			'buttons'	: {
				'Yes'	: {
					'class'	: 'blue',
					'action': function(){
						$.confirm.hide();
						document.location.href = "teacherwiseExemption.do?method=deleteInvigilator&id="+id;
					}
				},
     'No'	:  {
					'class'	: 'blue',
					'action': function(){
						$.confirm.hide();
					}
				}
			}
		});
}
function addMore(){
	document.getElementById("method").value = "addMore";
	document.teacherwiseExemptionForm.submit();
}
function deleteMore(){
	document.getElementById("method").value = "deleteMore";
	document.teacherwiseExemptionForm.submit();
}
function checkDuplicate(count,id,facId){
	document.getElementById("countValue").value = count;
		var session=document.getElementById("session_"+count).value;
		var date=document.getElementById("date_"+count).value;
		checkDuplicateExemption(date,session,id,facId,updateInvigilator);
}
function updateInvigilator(req){
	var count=document.getElementById("countValue").value;
	var date1=document.getElementById("date_"+count).value;
	var session1= document.getElementById("session_"+count).value;
	var session2= document.getElementById("hiddenSession_"+count).value;
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
		if (value[I].firstChild != null) {
			var temp = value[I].firstChild.nodeValue;
			if(temp!=null && temp==1){
				 $.confirm({
						'message'	: "This <font color=blue>"+date1+"</font> and <font color=blue>"+session1+"</font>"+" is already exempted for the teacher",
						'buttons'	: {
			     		'Ok'	:  {
								'class'	: 'blue',
								'action': function(){
									$.confirm.hide();
								}
							}
						}
					});
				 document.getElementById("session_"+count).value= document.getElementById("hiddenSession_"+count).value;
				 document.getElementById("date_"+count).value= document.getElementById("hiddenDate_"+count).value;
				}
 		}
		}
	}
}
</script>
<html:form action="/teacherwiseExemption" method="post">
<html:hidden property="formName" value="teacherwiseExemptionForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="focus" styleId="focus" />
<html:hidden property="countValue" styleId="countValue" />
<html:hidden property="method" styleId="method"	value="addTeacherOrUpdate" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.exam.allotment" /><span class="Bredcrumbs">&gt;&gt;
			Teacherwise Exemption &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Teacherwise Exemption</strong></td>

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
									<td  width="4%"  height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
									<td width="10%"  height="5%" class="row-odd" ><div align="center">Date</div></td>
									<td width="5%"  height="5%" class="row-odd" ><div align="center">Session</div></td>
                    				<td width="5%" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 				</tr>
                 				<logic:notEmpty name="teacherwiseExemptionForm" property="list">
								<nested:iterate id="CME" name="teacherwiseExemptionForm" property="list" indexId="count" type="com.kp.cms.to.examallotment.TeacherwiseExemptionTo">
									<%
                						String dateId="date_"+count;
										String hiddenDate="hiddenDate_"+count;
										String flag="flag_"+count;
										String sessionId="session_"+count;
										String hiddenSession="hiddenSession_"+count;
										String deleteMethod="deleteInvigilator("+CME.getId()+")";
										String method="checkDuplicate("+count+","+CME.getId()+","+CME.getFacId()+")";
                					%>
                					<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
                   				<tr>
                   					<td  height="25" class="row-even" align="center"><c:out value="${count + 1}" /></td>
                   					<td  height="25" class="row-even" align="center">
                   						<nested:hidden property="hiddenDate" styleId="<%=hiddenDate %>" ></nested:hidden>
                   						<nested:text property="date" styleId="<%=dateId %>"  size="9" onchange="<%=method %>"></nested:text>
                   						<script language="JavaScript">
                   						var cnt=<c:out value="${count}" />;
                   						new tcal( {
    										// form name
    										'formname' :'teacherwiseExemptionForm',
    										// input name
    										'controlname' :'date_'+cnt
    									});
                                     				</script>
                   					</td>
                   					<td  height="25" class="row-even" align="center">
                   						<nested:hidden property="hiddenSession" styleId="<%=hiddenSession %>" ></nested:hidden>
                   						<nested:select property="session" styleId="<%=sessionId %>"  onchange="<%=method %>">
                    						<html:option value="">--Select--</html:option>
                    						<nested:notEmpty property="sessionMap" name="CME">
					   							<nested:optionsCollection property="sessionMap" label="value" value="key"/>
					   						</nested:notEmpty>
					   					</nested:select>
                   					</td>
                   					<td  height="25" class="row-even" ><div align="center">
                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="<%=deleteMethod %>"></div>
                   					</td>
                   				</tr>
                				</nested:iterate>
								</logic:notEmpty>
								<logic:notEmpty name="teacherwiseExemptionForm" property="addMorelist">
								<nested:iterate id="CME" name="teacherwiseExemptionForm" property="addMorelist" indexId="cnt" type="com.kp.cms.to.examallotment.TeacherwiseExemptionTo">
									<%
                						String dtId="dt_"+cnt;
                					%>
                					<c:choose>
										<c:when test="${cnt%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
                   				<tr>
                   					<logic:notEmpty name="teacherwiseExemptionForm" property="count">
                   						<td  height="25" class="row-even" align="center"><c:out value="${cnt+1}" /></td>
                   					</logic:notEmpty>
                   					<logic:empty name="teacherwiseExemptionForm" property="count">
                   						<td  height="25" class="row-even" align="center"><c:out value="${cnt+count+2}" /></td>
                   					</logic:empty>
                   					<td  height="25" class="row-even" align="center">
                   						<nested:text property="date" styleId="<%=dtId %>" size="9"></nested:text>
                   						<script language="JavaScript">
                   						var cnt=<c:out value="${cnt}" />;
                   						new tcal( {
    										// form name
    										'formname' :'teacherwiseExemptionForm',
    										// input name
    										'controlname' :'dt_'+cnt
    									});
                                     				</script>
                   					</td>
                   					<td  height="25" class="row-even" align="center">
                   						<nested:select property="session">
                    						<html:option value="">--Select--</html:option>
                    						<nested:notEmpty property="sessionMap">
					   							<nested:optionsCollection property="sessionMap" label="value" value="key"/>
					   						</nested:notEmpty>
					   					</nested:select>
                   					</td>
                   					<td  height="25" class="row-even" >
                   					<div align="center">
                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"></div>
                   					</td>
                   				</tr>
                				</nested:iterate>
								</logic:notEmpty>
								<tr>
									<td  height="25" align="center" colspan="6">
									<table>
										<tr>
											<td width="=49%" align="right">
												<html:button property="" value="Add More" styleClass="formbutton" onclick="addMore()"></html:button>
											</td>
											<td width="1%"></td>
			                   					<logic:equal value="true" property="addMoreFlag" name="teacherwiseExemptionForm">
				                   					<td  align="left" width="45%">
				                   						<html:button property="" value="Remove" styleClass="formbutton" onclick="deleteMore()"></html:button>
				                   					</td>
	                   							</logic:equal>
										</tr>
									</table>
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
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35" align="center"></td>
							<td width="5%" height="35" align="right">
								<html:submit property="" value="Submit" styleClass="formbutton"></html:submit>
							</td>
							<td width="1%" height="35" align="right"></td>
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
<script type="text/javascript">
			var focusField=document.getElementById("focus").value;
		    if(focusField != 'null'){  
			    if(document.getElementById(focusField)!=null)      
		            document.getElementById(focusField).focus();
			}
</script>
