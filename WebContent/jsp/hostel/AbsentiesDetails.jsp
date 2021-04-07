<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">
function exportToExcel(){
	document.location.href = "downloadAbsenteesList.do";
	myRef = window.open(url, "Absentees Details", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
function sendEmail(){
	document.getElementById("method").value="sendMail";
	var name=document.getElementById("mail").value;
	document.getElementById("methodName").value=name;
	document.absentiesListForm.submit();
}
function sendSms(){ 
	document.getElementById("method").value="sendMail";
	var name=document.getElementById("sms").value;
	document.getElementById("methodName").value=name;
	document.absentiesListForm.submit();
}
function cancel(){
	document.location.href = "absentiesList.do?method=initAbsentiesList";
	
}
function unCheckSelectAll() {
	 var inputs = document.getElementsByTagName("input");
	 var inputObj;
	 var checkBoxOthersSelectedCount = 0;
	 var checkBoxOthersCount = 0;
	 for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox' && inputObj.id != "checkAll") {
	                  checkBoxOthersCount++;
	                  if(inputObj.checked) {
	                        checkBoxOthersSelectedCount++;
	                        inputObj.value="on";
	                  }else{
	                	  inputObj.value="off";	
	                      }   
	            }
	    }
	    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
	      document.getElementById("checkAll").checked = false;
	    } else {
	      document.getElementById("checkAll").checked = true;
	    }
	}
function selectAll(obj) {
	    var value = obj.checked;
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox') {
	                  inputObj.checked = value;
	                  inputObj.value="on";
	            }
	    }
	}
</script>

<html:form action="/absentiesList" focus="name">
<html:hidden property="formName" value="absentiesListForm" />
<html:hidden property="method" styleId="method" value="getAbsentiesList"/>
<html:hidden property="methodName" styleId="methodName" value=""/>
<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.absenties.list" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.hostel.absenties.list" /></strong></td>

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
									<td width="2%"  height="5%" class="row-odd" ><div align="center">
										All<input type="checkbox" id="checkAll" onclick="selectAll(this)"/></div>
									</td>
									<td width="20%"  height="5%" class="row-odd" ><div align="center">Hostel</div></td>
                    				<td width="8%"  height="5%" class="row-odd" ><div align="center">Register No</div></td>
                    				<td width="12%"  height="5%" class="row-odd" ><div align="center">Student Name</div></td>
                    				<td width="5%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.block"/></td>
                    				<td width="5%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.unit"/></td>
                    				<td width="5%" height="25" class="row-odd" align="center" >Room</td>
                    				<td width="5%" class="row-odd"><div align="center">Bed</div></td>
                    				<td width="8%" class="row-odd"><div align="center">Mobile No</div></td>
                    				<td width="8%" class="row-odd"><div align="center">Parent Mobile No</div></td>
                    				<td width="5%" class="row-odd"><div align="center">Date</div></td>
                    				<td width="10%" class="row-odd"><div align="center">Session</div></td>
                 				</tr>
                 				<logic:notEmpty name="absentiesListForm" property="absentiesListTosList">
								<nested:iterate id="CME" name="absentiesListForm" property="absentiesListTosList" indexId="count">
                					<c:choose>
                   						<c:when test="${temp == 0}">
                   						
                   				<tr>
                   					<td  height="25" class="row-even" align="center"><c:out value="${count + 1}" /></td>
                   					<td  height="25" class="row-even" align="center">
                   					<nested:checkbox property="checked" styleId="checkId" onclick="unCheckSelectAll()"> </nested:checkbox>
                   					</td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="hostelName"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="regNo"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="studentName"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="block"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="unit"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="room"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="bed"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="contactNo"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="parentContactNo"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="date"/></td>
                   					<td  height="25" class="row-even" align="center"><nested:write name="CME" property="session"/></td>
                   				</tr>
                    				<c:set var="temp" value="1"/>
                   						</c:when>
                    				<c:otherwise>
		            			<tr>
		            			
		            				<td  height="25" class="row-white" align="center"><c:out value="${count + 1}" /></td>
		            				<td  height="25" class="row-white" align="center"><nested:checkbox property="checked" styleId="checkId" onclick="unCheckSelectAll()"> </nested:checkbox>
		            				</td>
		            				<td  height="25" class="row-white" align="center"><nested:write name="CME" property="hostelName"/></td>
               						<td  height="25" class="row-white" align="center"><nested:write name="CME" property="regNo"/></td>
               						<td  height="25" class="row-white" align="center"><nested:write name="CME" property="studentName"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="block"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="unit"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="room"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="bed"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="contactNo"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="parentContactNo"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="date"/></td>
                   					<td  height="25" class="row-white" align="center"><nested:write name="CME" property="session"/></td>
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
							<td>
								<br></br>
							</td>
						</tr>
						<tr>
							<td class="row-odd" width="50%" align="right">
										<span class="Mandatory"></span><bean:message key="knowledgepro.admin.hostel.absent.student.select.studentorparent" /> :
							</td>
							<td class="row-even" align="left" width="50%">
								<input type="radio" name="mailFor" value="parent" ></input>Parent<input type="radio" name="mailFor" value="student" checked="checked"></input>Student
							</td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35" align="center"></td>
							<td width="5%" height="35" align="left">
									<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
							</td>
							<td width="5%" height="35" align="left">
									<html:button property="" value="Send Mail" styleId="mail" styleClass="formbutton"  onclick="sendEmail()"></html:button>
							</td>
							<td width="5%" height="35" align="left">
									<html:button property="" value="Send Sms" styleId="sms" styleClass="formbutton" onclick="sendSms()"></html:button>
							</td>
							<td width="39%" >
								<html:button property="" styleClass="formbutton" onclick="exportToExcel()">	Export To Excel	</html:button>
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

