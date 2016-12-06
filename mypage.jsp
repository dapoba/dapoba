<%@ page language="java" contentType="text/html; charset=EUC-KR" import="java.sql.*"
    pageEncoding="EUC-KR"%>
 <%Class.forName("com.mysql.jdbc.Driver");
 
	String driver = "com.mysql.jdbc.Driver"; 
	String url = "jdbc:mysql://127.0.0.1:3306/dapoba_db?autoReconnect=true&useSSL=false"; 
	String userId = "root"; 
	String passwd = "1234"; 
	String table = "account";
	Connection conn; 
	Statement stmt; 
	ResultSet rs=null;
	StringBuilder sb = new StringBuilder();
	try{
		conn=DriverManager.getConnection(url, userId, passwd);
		stmt=conn.createStatement();
		String id =("test1");

		
		String sql = sb.append("SELECT ID, password, name, birth, email, Phone_number from account where (id like '" + id + "')" ).toString();
		System.out.println(sql);
		rs = stmt.executeQuery(sql);

 %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="header.jsp" flush = "true"></jsp:include>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">

    <title>마이페이지</title>

    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="mypage.css" rel="stylesheet">
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>
  </head>

  <body>
    <!-- Static navbar -->

    <div class="container">
      <form class="form-horizontal col-md-6 col-md-offset-3 well form-box">
        <fieldset>
          <legend>마이페이지</legend>
          회원님의 정보를 확인하실 수 있습니다.<br><br>
          <% while(rs.next()){%>
          <div class="form-group">
            <label for="ID" class="col-md-2 control-label"><b>아이디</b></label>
            <div class="col-md-8 underline">
              <%=rs.getString("id")%>
    
            </div>
          </div>
          <div class="form-group">
            <label for="password" class="col-md-2 control-label"><b>비밀번호</b></label>
            <div class="col-md-8 underline">
               <%=rs.getString("password") %>
            </div>
          </div>
          <div class="form-group">
            <label for="name" class="col-md-2 control-label"><b>이름</b></label>
            <div class="col-md-8 underline">
              <%=rs.getString("name") %>
            </div>
          </div>
          <div class="form-group">
            <label for="birth" class="col-md-2 control-label"><b>생년월일</b></label>
            <div class="col-md-8 underline">
              <%=rs.getString("birth") %>
            </div>
          </div>
          <div class="form-group">
            <label for="email" class="col-md-2 control-label"><b>이메일</b></label>
            <div class="col-md-8 underline">
              <%=rs.getString("email") %>
            </div>
          </div>
          <div class="form-group">
            <label for="phone" class="col-md-2 control-label"><b>전화번호</b></label>
            <div class="col-md-8 underline">
              <%=rs.getString("Phone_number") %>
            </div>
          </div>
          <div class="form-group">
            <div class="col-md-8 col-md-offset-2">
              <a href="modify.jsp" class="btn-sm btn1 btn-custom">정보수정</a>
            </div>
          </div>
        </fieldset>
      </form>
    </div> <!-- /container -->

<%
}
rs.close();
stmt.close();
conn.close();
	}catch(SQLException e){
		out.println("err:"+e.toString());
	}
            		%>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/jquery.min.js"><\/script>')</script>
    <script src="js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>