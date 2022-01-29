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

</SCRIPT>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<link rel="stylesheet" href="calendar.css">

<link href="css/styles.css" rel="stylesheet" type="text/css">

</head>

<body>
<html:form action="/manageWorkingDays.do">
	<html:hidden property="pageType" styleId="pageType" value="1" />
	<html:hidden property="formName" value="ManageWorkingDaysForm" />
	<html:hidden property="method" styleId="method" value="add" />

	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">&gt;&gt;
			Manage Working Days &gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">
					Manage Working Days</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
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
									<div align="right">Academic Year :</div>
									</td>

									<td width="20%" class="row-even">
									<html:select property="academicYr" styleId="academicYr" styleClass="combo">
										<cms:renderYear></cms:renderYear>
									</html:select></td>
									<td width="20%" class="row-odd">
									<div align="right">Program Type :</div>
									</td>
									<td width="20%" class="row-even"><select name="select"
										id="select" class="combo">
										<option selected="selected">Select</option>
									</select></td>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Course :</div>
									</td>
									<td colspan="3" class="row-even"><select name="select3"
										size="3" multiple id="select3">
										<option>BCA</option>
										<option>BCom</option>
										<option>BSc</option>

									</select></td>
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
									<td width="91" height="25" class="row-odd">&nbsp;</td>

									<td class="row-odd">Start Time</td>
									<td class="row-odd">End Time</td>
									<td class="row-odd">&nbsp;</td>
								</tr>
								<tr>
									<td height="25" align="left" class="row-even">
									<div><input name="checkbox2" type="checkbox"
										value="checkbox" checked> Monday</div>
									</td>

									<td width="176" class="row-even"><select name="select5"
										id="select5" class="combo">
										<option selected="selected">Select</option>
										<option>9:00</option>
										<option>9:15</option>
										<option>9:30</option>
										<option>9:45</option>

										<option>10:15</option>
										<option>10:30</option>
										<option>10:45</option>
										<option>11:00</option>
									</select></td>
									<td width="212" class="row-even"><select name="select6"
										id="select6" class="combo">
										<option selected="selected">Select</option>

										<option>9:00</option>
										<option>9:15</option>
										<option>9:30</option>
										<option>9:45</option>
										<option>10:15</option>
										<option>10:30</option>

										<option>10:45</option>
										<option>11:00</option>
									</select></td>
									<td width="203" class="row-even"><input name="Submit22"
										type="button" class="formbutton" value="Add Break"
										onClick="window.open('break.html')" /></td>
								</tr>
								<tr>
									<td height="25" align="left" class="row-white">
									<div><input name="checkbox3" type="checkbox"
										value="checkbox" checked> Tuesday</div>
									</td>
									<td class="row-white"><select name="select7" id="select7"
										class="combo">
										<option selected="selected">Select</option>
										<option>9:00</option>
										<option>9:15</option>
										<option>9:30</option>

										<option>9:45</option>
										<option>10:15</option>
										<option>10:30</option>
										<option>10:45</option>
										<option>11:00</option>
									</select></td>
									<td class="row-white"><select name="select8" id="select8"
										class="combo">
										<option selected="selected">Select</option>
										<option>9:00</option>
										<option>9:15</option>
										<option>9:30</option>

										<option>9:45</option>
										<option>10:15</option>
										<option>10:30</option>
										<option>10:45</option>
										<option>11:00</option>
									</select></td>
									<td class="row-white"><span class="row-even"> <input
										name="Submit222" type="button" class="formbutton"
										value="Add Break" /> </span></td>
								</tr>
								<tr>
									<td height="25" align="left" class="row-even">
									<div><input name="checkbox32" type="checkbox"
										value="checkbox" checked> Wednesday</div>
									</td>

									<td class="row-even"><span class="row-even"> <select
										name="select9" id="select9" class="combo">
										<option selected="selected">Select</option>
										<option>9:00</option>
										<option>9:15</option>
										<option>9:30</option>
										<option>9:45</option>

										<option>10:15</option>
										<option>10:30</option>
										<option>10:45</option>
										<option>11:00</option>
									</select> </span></td>
									<td class="row-even"><span class="row-even"> <select
										name="select10" id="select10" class="combo">
										<option selected="selected">Select</option>
										<option>9:00</option>
										<option>9:15</option>
										<option>9:30</option>
										<option>9:45</option>

										<option>10:15</option>
										<option>10:30</option>
										<option>10:45</option>
										<option>11:00</option>
									</select> </span></td>
									<td class="row-even"><span class="row-even"> <input
										name="Submit223" type="button" class="formbutton"
										value="Add Break" /> </span></td>
								</tr>
								<tr>
									<td height="25" align="left" class="row-white">
									<div><input name="checkbox33" type="checkbox"
										value="checkbox" checked> Thursday</div>
									</td>
									<td class="row-white"><span class="row-even"> <select
										name="select11" id="select11" class="combo">
										<option selected="selected">Select</option>
										<option>9:00</option>
										<option>9:15</option>
										<option>9:30</option>
										<option>9:45</option>

										<option>10:15</option>
										<option>10:30</option>
										<option>10:45</option>
										<option>11:00</option>
									</select> </span></td>
									<td class="row-white"><select name="select12"
										id="select12" class="combo">
										<option selected="selected">Select</option>
										<option>9:00</option>
										<option>9:15</option>
										<option>9:30</option>
										<option>9:45</option>

										<option>10:15</option>
										<option>10:30</option>
										<option>10:45</option>
										<option>11:00</option>
									</select></td>
									<td class="row-white"><span class="row-even"> <input
										name="Submit224" type="button" class="formbutton"
										value="Add Break" /> </span></td>
								</tr>
								<tr>
									<td height="25" align="left" class="row-even">
									<div><input name="checkbox34" type="checkbox"
										value="checkbox" checked> Friday</div>
									</td>
									<td class="row-even"><span class="row-even"> <select
										name="select13" id="select13" class="combo">
										<option selected="selected">Select</option>
										<option>9:00</option>
										<option>9:15</option>
										<option>9:30</option>
										<option>9:45</option>

										<option>10:15</option>
										<option>10:30</option>
										<option>10:45</option>
										<option>11:00</option>
									</select> </span></td>
									<td class="row-even"><span class="row-even"> <select
										name="select14" id="select14" class="combo">
										<option selected="selected">Select</option>
										<option>9:00</option>
										<option>9:15</option>
										<option>9:30</option>
										<option>9:45</option>

										<option>10:15</option>
										<option>10:30</option>
										<option>10:45</option>
										<option>11:00</option>
									</select> </span></td>
									<td class="row-even"><span class="row-even"> <input
										name="Submit225" type="button" class="formbutton"
										value="Add Break" /> </span></td>
								</tr>
								<tr>
									<td height="25" align="left" class="row-white">
									<div><input type="checkbox" name="checkbox35"
										value="checkbox"> Saturday</div>
									</td>
									<td class="row-white">&nbsp;</td>

									<td class="row-white">&nbsp;</td>
									<td class="row-white">&nbsp;</td>
								</tr>
								<tr>
									<td height="25" align="left" class="row-even">
									<div><input type="checkbox" name="checkbox36"
										value="checkbox"> Sunday</div>
									</td>
									<td class="row-even">&nbsp;</td>

									<td class="row-even">&nbsp;</td>
									<td class="row-even">&nbsp;</td>
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
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" align="right"><input name="Submit2"
								type="button" class="formbutton" value="Submit" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit2" type="submit" class="formbutton" value="Reset" /></td>
						</tr>

					</table>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
									<td class="row-odd">Program Type</td>
									<td class="row-odd">Course</td>

									<td width="43" class="row-odd">Edit</td>
									<td width="46" class="row-odd">Delete</td>
								</tr>
								<tr>
									<td height="25" class="row-even">
									<div align="center">1</div>
									</td>
									<td width="535" class="row-even">&nbsp;</td>

									<td width="535" class="row-even">&nbsp;</td>
									<td width="535" class="row-even">&nbsp;</td>
									<td class="row-even">
									<div align="center"><img src="images/edit_icon.gif"
										alt="CMS" width="16" height="16"></div>
									</td>
									<td class="row-even">
									<div align="center"><img src="images/delete_icon.gif"
										alt="CMS" width="16" height="16"></div>
									</td>
								</tr>
							
							</table>


							</td>
						</tr>
					</table>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
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

