<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
<script type="text/javascript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
<!--<script type="text/javascript" src="js/leftSwitchMenu.js"></script>-->
<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
</script>
<html:form action="/studentWiseAttendanceSummary">
	<html:hidden property="formName"
		value="studentWiseAttendanceSummaryForm" />
	<html:hidden property="pageType" value="1" />
<!--<head>
	<style>
table { 
    table-layout:fixed;
     overflow: hidden; 
    text-overflow: ellipsis; 
    word-wrap: break-word;
}
td { 
    overflow: hidden; 
    text-overflow: ellipsis; 
    word-wrap: break-word;
}
	
	</style>
	
	
	</head>
--><div class="pageheader">
 
 </div>

 <div class="panel-body">
	<div class="panel panel-primary">
	<div class="panel-heading">
	<h1 class="panel-title">Absence Details</h1>
	</div>

	</div>
	<div class="table-responsive">
                  <table id="basicTable" class="table table-bordered table-striped" width="100%" >
	
		<logic:empty name="studentWiseAttendanceSummaryForm"
			property="attList">
									 	No Abscent Records
									 </logic:empty>
		<logic:notEmpty name="studentWiseAttendanceSummaryForm"
			property="attList">
			<tr>
				<th><div align="center">Date</div></th>
				<th>
				<div align="center">Day</div>
				</th>
				<logic:iterate id="periodName"
					name="studentWiseAttendanceSummaryForm" property="periodNameList">
					<th>
					<div align="center"><bean:write
						name="periodName" /></div>
					</th>
				</logic:iterate>
				<th><div align="center">Total</div></th>
			</tr>
			<logic:notEmpty name="studentWiseAttendanceSummaryForm"
				property="attList">
				<logic:iterate id="to" name="studentWiseAttendanceSummaryForm"
					property="attList">
					<tr>
						<td><div align="center"><bean:write name="to" property="date" /></div></td>
						<td><div align="center"><bean:write name="to" property="day" /></div></td>
						<logic:iterate id="pto" name="to" property="periodList">
							<td>
							<div align="center"><logic:equal
								value="true" name="pto" property="coLeave">
								<font color="green"><bean:write name="pto"
									property="periodName" /></font>
							</logic:equal> <logic:equal value="false" name="pto" property="coLeave">
								<bean:write name="pto" property="periodName" />
							</logic:equal></div>
							</td>
						</logic:iterate>
						<td><div align="center"><bean:write name="to" property="hoursHeldByDay" /></div></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
		</logic:notEmpty>
	</table>
	</div>
	<div class="form-group">
<div class="col-sm-12">
		
			<h4>Periods Marked In Green Are Cocurricular Leave</h4>
		</div>
		<div class="col-sm-2"> 
			NO OF PERIODS ABSENT</div>
			<div class="col-sm-2"> <bean:write
				name="studentWiseAttendanceSummaryForm" property="total" /></div>
			<div class="col-sm-2"> TOTAL COCURRICULAR</div>
			<div class="col-sm-2"> <bean:write
				name="studentWiseAttendanceSummaryForm" property="totalCoLeave" /></div>
			<div class="col-sm-2"> TOTAL PERIODS ABSENT</div>
			<div class="col-sm-2"> <bean:write
				name="studentWiseAttendanceSummaryForm" property="abscent" /></div>
	
	</div>

		<logic:iterate id="sub" name="studentWiseAttendanceSummaryForm"
			property="subMap">
			<div class="form-group">
				<div class="col-sm-6">

				<bean:write name="sub" property="key" />
				</div>
				
				
				<div class="col-sm-4">
				<bean:write name="sub" property="value" />
			</div>
			</div>
		</logic:iterate>
	

	<div class="col-sm-12 text-center">
	<div class="btn btn-danger btn-lg"><span class="form-controler"onclick=cancelAction();>Cancel</span></div>
	</div>
</div>
</html:form>