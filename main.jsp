<%@ page language="java" contentType="text/html; charset=EUC-KR" import="java.sql.*"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 <%
 	Class.forName("com.mysql.jdbc.Driver");
 
	String driver = "com.mysql.jdbc.Driver"; 
	String url = "jdbc:mysql://localhost:3306/?user=root"; 
	String userId = "root"; 
	String passwd = "1234"; 
	String table = "dapoba_db.account";
	Connection conn; 
	Statement stmt; 
	ResultSet rs=null;
	StringBuilder sb = new StringBuilder();
	try{
		conn=DriverManager.getConnection(url, userId, passwd);
		stmt=conn.createStatement();
		String id =(String)application.getAttribute("userid");
		System.out.print(id);
		String name=null;
		String coin=null;
		String mileage=null;
		String sql = sb.append("SELECT id from dapoba_db.account where (id like '" + id + "');" ).toString();
		
		rs = stmt.executeQuery(sql);
		while(rs.next()){
			name=rs.getString("id");
		}
		
		sql = "select *from dapoba_db.mileage_coin where id = '" + id + "'";
		//sql = sb.append("SELECT *from dapoba_db.mileage_coin where (id like '" + id + "');" ).toString();
		rs = stmt.executeQuery(sql);
		while(rs.next()){
			coin = rs.getString("coin");
			mileage = rs.getString("mileage");
		}


 %>


<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">
	<title> DAPOBA</title>
    <link href="bootstrap.css" rel="stylesheet">
    <link href="main.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
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
          <a class="navbar-brand" href="main.jsp">DAPOBA</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="main.jsp">홈</a></li>
            <li><a href="file.jsp">문서함</a></li>
            <li><a href="option_select.jsp">출력</a></li>
            <li><a href="coin-milege.jsp">코인/마일리지</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li class="user_name"><a>
            <% out.print((String)application.getAttribute("userid"));%>
            	님</a></li>
            <li class=""><a href="mypage.jsp">마이페이지 <span class="sr-only">(current)</span></a></li>
            <li><a href="./">로그아웃</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    <div class="container">
      <div class="row">
          <div class="col-md-offset-2 col-md-8 my_info_box">
            <div class="my_info_contents">
             
                <h4 class="logo">출력물 예약 사이트 DAPOBA</h4><br>
              <span><% out.print(name);%></span>님 반갑습니다.
           
               
              <div>
                <br>마일리지 <span><%=mileage%></span> P / 코인 <span><%=coin%></span> P　<a href="charge-coin.jsp" class="btn1 btn-default1">충전</a> <br><br>
              </div>
              <div>
                <a href="mypage.jsp" class="btn1 btn-default1">마이페이지</a>
              </div>
            </div>
          </div>
      </div>
      <div class="row">
        <div class="col-md-offset-2 col-md-8 menu_box">
          <p>
            <a href="file.jsp" class="btn1 btn-default1 menu_box_btn additional_custom" id="custom_btn1">　문서함 <img src="img/icon/file-1.png" class="main-logo" width="65px" height="65px"></a>
            <a href="#" class="btn1 btn-info1 menu_box_btn additional_custom2" id="custom_btn1">　출력하기 <img src="img/icon/printer-1.png" class="main-logo" width="65px" height="65px"></a>
          </p>
          <p>
            <a href="coin-mileage.jsp" class="btn1 btn-warning1 menu_box_btn additional_custom3" id="custom_btn2">코인/마일리지 <img src="img/icon/coin.png" class="main-logo" width="70px" height="65px"></a>
            <span class="btn1 btn-success1 btn-d menu_box_btn" id="custom_btn3"><img src="img/icon/party.png" class="main-logo" width="33px" height="33px">　OPEN EVENT　<img src="img/icon/party.png" class="main-logo" width="30px" height="30px"><br> 이벤트 참여시 1,000P 증정!</span>
          </p>
        </div>
      </div>
    </div> <!-- /container -->
    
    
<%
//rs.close();
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