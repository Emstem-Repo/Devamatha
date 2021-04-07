<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<SCRIPT type="text/javascript">
    function exportExcel(blockId) {  
    	document.location.href = "StudentLoginAction.do?method=marksCardClearanceCertificate&blockId="+blockId;
    }
</SCRIPT>
</head>
<body>
<%
	String reason = ""; 
	if(session.getAttribute("marksCardBlockReason")!= null){
		reason = session.getAttribute("marksCardBlockReason").toString();
	}
	
	String id="";
	if(session.getAttribute("marksCardBlockId")!= null){
		id = session.getAttribute("marksCardBlockId").toString();
	}
%>
<font color="red"> Your Marks Card is blocked - <%=reason%></font>
<br/>
<a href="#" onclick="exportExcel(<%=id %>)" >Click Here to Get Clearance Certificate</a>

</body>
</html>										