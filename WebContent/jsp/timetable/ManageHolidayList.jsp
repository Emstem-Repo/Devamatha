<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<html>
<head>
<title>:: CMS ::</title>
<script type="text/javascript">
	function editDetails(id) {
		document.location.href = "manageHolidayList.do?method=edit&id=" + id;
	}
	function deleteDetails(id) {
		if(confirm("Are You Sure To Delete This Entry?"))
		document.location.href = "manageHolidayList.do?method=delete&id=" + id;
	}
</SCRIPT>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<link rel="stylesheet" href="calendar.css">

<link href="css/styles.css" rel="stylesheet" type="text/css">

</head>

<body>
<html:form action="/manageHolidayList.do">
	<html:hidden property="pageType" styleId="pageType" value="1" />
	<html:hidden property="formName" value="manageHolidayListForm" />


	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">&gt;&gt;
			Manage Holidays List&gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">
					Manage Holiday List</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<td width="17%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Academic
									Year :</div>
									</td>

									<td width="20%" class="row-even"><html:select
										property="academicYr">
										<cms:renderYear />
									</html:select></td>
									<td width="20%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Holiday
									:</div>
									</td>
									<td width="20%" class="row-even"><html:text
										property="holidayName" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>From
									Date :</div>
									</td>

									<td class="row-even">
									<table width="82" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="60"><html:text property="startDate"
												styleId="startDate" /></td>
											<td width="40"><script language="JavaScript">
	new tcal( {
		// form name
		'formname' : 'manageHolidayListForm',
		// input name
		'controlname' : 'startDate'

	});
</script></td>
										</tr>
									</table>
									</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>To
									Date :</div>
									</td>
									<td class="row-even">
									<table width="82" border="0" cellspacing="0" cellpadding="0">

										<tr>
											<td width="60"><html:text property="endDate"
												styleId="endDate" /></td>
											<td width="40"><script language="JavaScript">
	new tcal( {
		// form name
		'formname' : 'manageHolidayListForm',
		// input name
		'controlname' : 'endDate'

	});
</script></td>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" align="right"><c:choose>
								<c:when test="${edit=='edit'}">
									<input name="Submit2" type="submit" class="formbutton"
										value="Update" id="submit" />
									<html:hidden property="method" styleId="method" value="update" />
								</c:when>
								<c:otherwise>
									<input name="Submit2" type="submit" class="formbutton"
										value="Submit" id="submit" />
									<html:hidden property="method" styleId="method" value="add" />
								</c:otherwise>
							</c:choose></td>

							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit2" type="reset" class="formbutton" value="Reset" /></td>
						</tr>
					</table>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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

									<td width="58" height="25" class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Academic Year</td>
									<td class="row-odd">Holiday</td>
									<td class="row-odd">From Date</td>
									<td class="row-odd">To Date</td>
									<td width="43" class="row-odd">Edit</td>

									<td width="46" class="row-odd">Delete</td>
								</tr>
								<nested:iterate property="bottomGrid" indexId="count">
									<tr>
										<td height="25" class="row-even">
										<div align="center"><c:out value="${count+1}"></c:out></div>
										</td>
										<td width="535" class="row-even"><nested:write
											property="academicYear" /></td>
										<td width="535" class="row-even"><nested:write
											property="holidayName" /></td>
										<td width="535" class="row-even"><nested:write
											property="startDate" /></td>

										<td width="535" class="row-even"><nested:write
											property="endDate" /></td>
										<td class="row-even">
										<div align="center"><img src="images/edit_icon.gif"
											alt="CMS" width="16" height="16"
											onclick="editDetails('<nested:write property="id"/>')"></div>
										</td>
										<td class="row-even">
										<div align="center"><img src="images/delete_icon.gif"
											alt="CMS" width="16" height="16"
											onclick="deleteDetails('<nested:write property="id"/>')"></div>
										</td>
									</tr>
								</nested:iterate>
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


					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
</body>
</html>

