<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@page import= "java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">
    <title>������</title>
    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="cabinet.css" rel="stylesheet">
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>
  </head>

  <body>
    <nav class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="admin.jsp">DAPOBA</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="admin.html">������ ������</a></li>
            <li><a href="cabinet.html">�繰�� ����</a></li>
            <li><a href="manage-payment.html">�Ա� ����</a></li>
            <!--li><a href="qna-board.html">������</a></li-->
            <!--li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li-->
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<body>
<div class="container">
      <div class="row">
        <div class="col-md-4 col-md-offset-2">
<%
String driver = "com.mysql.jdbc.Driver"; 

String url = "jdbc:mysql://localhost:3306/?user=root"; 

String userId = "root"; 

String passwd = "1234"; 

String loc=(String)application.getAttribute("cabinet_loc");
String cabinet_num=(String)application.getAttribute("cab_number");
Connection conn; 

Statement stmt;

ResultSet rs; 

try

{ 

	Class.forName(driver); // Driver Loading 

	conn = DriverManager.getConnection(url, userId, passwd);// Connection 

	String sql = ""; // SQL �ۼ� 
	String veri_num=request.getParameter("verfiNum");
	out.print(request.getParameter("verfiNum"));
	
	if(veri_num==null)
	{
		out.println("<script>");
		out.println("alert('������ȣ�� ��ȿ���� �ʽ��ϴ�.');");
		out.println("</script>");
		response.sendRedirect("cabinet_confirm.jsp");
	}
	else 
	{
		stmt = conn.createStatement(); 	// Statement 
		sql="update dapoba_db.cabinet set cabinet_password='"+veri_num+"', cabinet_state='1' where (cabinet_number = '"+cabinet_num+"') and (cabinet_loc ='"+loc+"'); ";
		stmt.execute(sql);
		System.out.println(sql);
		
		out.print("<h6 class=\"margin-bottom\">�����Ǿ����ϴ�.</h6><br>");
		out.println("<button class=\"btn1 btn-default btn-custom\" ><a href=\"cabinet.jsp\" >ĳ������� </a> ");
	}
}
catch(Exception e)
{
	
}

%>
</div></div></div>
</body>
</html>