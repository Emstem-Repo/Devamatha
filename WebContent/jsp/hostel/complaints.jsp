<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<script>
	function complaintSubmit() {
		document.getElementById("method").value="submitComplaints";
	}

	function deleteComplaints(id){
		deleteConfirm = confirm("Are you sure want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "complaints.do?method=deleteComplaintsDetails&id="+id;
	}
		}
</script>

<html:form action="/complaints" method="POST">
<html:hidden property="formName" value="complaintsForm" />
<html:hidden property="method" styleId="method" value="submitComplaints"/>
<html:hidden property="pageType" value="1" />
<html:hidden property="id" styleId="id" />
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading">Hostel<span class="Bredcrumbs">&gt;&gt;  Complaints &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > Complaints</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="40" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top">
              <table width="100%" cellspacing="1" cellpadding="2">
              	<tr >
                    <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.adminmessage.hostelname"/></div></td>
                    <td height="25" class="row-even" >
                    <html:select name="complaintsForm" property="hostelName" styleClass="comboLarge" styleId="hostelId">
						<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							<logic:notEmpty name="complaintsForm" property="hostelList">		
								<html:optionsCollection name="complaintsForm" property="hostelList" label="name" value="id" />
							</logic:notEmpty>
						</html:select>
                    </td>
                  </tr>
              <tr>
               <td width="50%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.reqno.view.status"/> </div></td>
               <td width="50%" height="25" class="row-even" align="left">
					<html:text
						property="requisitionNo" styleId="requisitionno" 
						maxlength="16" />
			   </td>
			</tr>
                  <tr >
                    <td width="18%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.date"/></div></td>
                    <td width="32%" height="25" class="row-even" >
                    <bean:write name="complaintsForm" property="logDate"/>
                    </td>
                    </tr>
                  <tr >
                    <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.complaints.complaintType"/></div></td>
                    <td height="25" class="row-even" >
                    <html:select name="complaintsForm" property="complaintType" styleClass="comboLarge" styleId="complaintId">
						<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							<logic:notEmpty name="complaintsForm" property="complaintsList">		
								<html:optionsCollection name="complaintsForm" property="complaintsList" label="type" value="id" />
							</logic:notEmpty>
						</html:select>
                    </td>
                  </tr>
                  <tr >
                    <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.complaints.title"/></div></td>
                    <td height="25" class="row-even" ><span class="star">
                      <html:text property="title" name="complaintsForm" styleClass="textbox" styleId="title" size="25" maxlength="20"/>
                    </span></td>
                    </tr>
                  <tr >
                    <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.desc.label"/></div></td>
                    <td height="25" class="row-even" >
                    	<html:textarea property="description" name="complaintsForm" styleId="descrip" cols="22" rows="2" style="width:200px;"/>
                    </td>
                    </tr>
              </table></td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="38" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="35"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="48%" height="35"><div align="right">
                        <html:submit property="submit" styleClass="formbutton" value="Submit" onclick="complaintSubmit()"/>
                    </div></td>
                    <td width="2%"></td>
                    <td width="48%" height="35"><div align="left">
                    <html:button  property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()">
						<bean:message  key="knowledgepro.cancel"/>
					</html:button></div>
					</td>
                  </tr>
              </table></td>
            </tr>
            <tr>
							<td height="45" colspan="4">
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
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.hostel.name"/></td>
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.hostel.reqno.view.status"/></td>
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.exam.assignExaminer.date"/></td>
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.hostel.complaints.complaintType"/></td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<c:set var="temp" value="0" />
										<logic:notEmpty name="complaintsForm"
											property="complaintsListTo">
											<logic:iterate id="complaints" name="complaintsForm"
												property="complaintsListTo" indexId="count">
												
														<tr>
															<td width="6%" height="25" class="row-even">
															<div align="center"><c:out value="${count + 1}" /></div>
															</td>
															<td width="31%" height="25" class="row-even"
																align="center"><bean:write name="complaints" property="hostelName" /></td>
															<td width="21%" class="row-even" align="center"><bean:write
																name="complaints" property="requisitionNo" /></td>
															<td width="41%" class="row-even" align="center"><bean:write
																name="complaints" property="logDate" /></td>
															<td width="41%" class="row-even" align="center"><bean:write
																name="complaints" property="complaintTypeName" /></td>	
															
															<td width="6%" height="25" class="row-even">
															<div align="center"><img
																src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
																onclick="deleteComplaints('<bean:write name="complaints" property="id"/>')"></div>
															</td>
														</tr>
														<c:set var="temp" value="1" />
											</logic:iterate>
										</logic:notEmpty>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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