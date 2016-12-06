<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import = "java.util.*, java.sql.*, java.util.Date, java.text.SimpleDateFormat"%>
 <% 
String driver = "com.mysql.jdbc.Driver"; 
String url = "jdbc:mysql://localhost:3306/?user=root";
String userId = "root";
String passwd = "1234";
Connection conn;
Statement stmt;
ResultSet rs;
Vector <String> id = new Vector<String>();
Vector <String> name = new Vector<String>();
Vector <String> deposit = new Vector<String>();
Vector <String> money = new Vector<String>();
try{
	Class.forName(driver); // Driver Loading
   conn = DriverManager.getConnection(url, userId, passwd); // Connection
   		   
   stmt = conn.createStatement();
	String sql = "USE Dapoba_db";
	
	stmt.execute(sql);
	
	sql = "select *from cashList where ID = 'test1'";
	rs = stmt.executeQuery(sql);
	while(rs.next()){
		id.add(rs.getString("ID"));
		name.add(rs.getString("Name"));
		deposit.add(rs.getString("deposit"));
		money.add(rs.getString("money"));
	}	
	stmt.close(); 
	conn.close(); // close
}
catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang = "en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">
    <title>입금 관리</title>
    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="manage-payment.css" rel="stylesheet">
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
          <a class="navbar-brand" href="main.html">DAPOBA</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="admin.html">관리자 페이지</a></li>
            <li><a href="cabinet.html">사물함 관리</a></li>
            <li><a href="manage-payment.html">입금 관리</a></li>
            <!--li><a href="qna-board.html">고객센터</a></li-->
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
    <script>
function chk(){
	<% 
	try{
		Class.forName(driver); // Driver Loading
	   conn = DriverManager.getConnection(url, userId, passwd); // Connection
	   		   
	   stmt = conn.createStatement();
		String sql = "USE Dapoba_db";
		stmt.execute(sql);
		
		sql = "update cashList set deposit ='0' Where ID = 'test1'";
		stmt.executeUpdate(sql);
		
		sql = "select *from cashList Where ID = 'test1'";
		rs = stmt.executeQuery(sql);
		rs.next();
		int pay = rs.getInt("money");
		pay = pay/2;
		
		sql = "select *from Mileage_Coin where ID = 'test1'";
		rs = stmt.executeQuery(sql);
		rs.next();
		int coin = rs.getInt("Coin");
		int result = coin + pay;
		
		sql = "update Mileage_Coin set Coin = '" + String.valueOf(result) + "' Where ID = 'test1'";
		stmt.executeUpdate(sql);

		stmt.close(); 
		conn.close(); // close
	}
	catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }%>
	alert('입금 확인을 완료하였습니다.');
	location.href='manage-payment.jsp';
}
</script>
    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>입금 관리</h4>
        </div>
        <div class="col-md-6 col-md-offset-3 payment_box0">
          <h5>무통장 입금 현황 명단</h5>
        </div>
        <div class="col-md-6 col-md-offset-3">
          <div class="row">
            <div class="col-md-3 payment_box2">
              아이디
            </div>
            <div class="col-md-3 payment_box2">
              회원명
            </div>
            <div class="col-md-3 payment_box2">
              금액
            </div>
            <div class="col-md-3 payment_box2">
              입금 여부
            </div>
          </div>
          <div class="row">
            <div class="col-md-3 payment_box2">
              <div class="list">
               <%
            for(int i = 0; i <id.size(); i++){ %>
            	<%= id.get(i) %><br>
            <% } %>
              </div>
            </div>
            <div class="col-md-3 payment_box2">
              <div class="list">
          <%
            for(int i = 0; i <name.size(); i++){ %>
            	<%= name.get(i) %><br>
            <% } %>
              </div>
            </div>
            <div class="col-md-3 payment_box2">
              <div class="list">
            <%
            for(int i = 0; i <money.size(); i++){ %>
            	<%= money.get(i) %><br>
            <% } %>
              </div>
            </div>

            <div class="col-md-3 payment_box2">
            <div class="list">
             <%
            for(int i = 0; i <deposit.size(); i++){ 
            if(deposit.get(i).equals("1")){ %>
              <button  name = "confirmPayment"class="btn1 btn-default btn-sm" id ="custom_btn" onclick = "chk()" >확인</button><br>
    			<%}
            else if(deposit.get(i).equals("0")){%>
            완료<br>
            <%} %>
            <% } %>
      		</div>
            </div>
           
          </div>
        </div>
      </div>
      <div class="row">

      </div>
      </div>
    </div> <!-- /container -->

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

</html>