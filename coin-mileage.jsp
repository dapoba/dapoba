<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import = "java.util.*, java.sql.*"%>
    <% 
String driver = "com.mysql.jdbc.Driver"; 
String url = "jdbc:mysql://localhost:3306/?user=root";
String userId = "root";
String passwd = "1234";
Connection conn;
Statement stmt;
ResultSet rs;
String Milege = null;
String Coin = null;
Vector <String> date = new Vector<String>();
Vector <String> coin = new Vector<String>();
Vector <String> milege = new Vector<String>();
Vector <String> file = new Vector<String>();
try{
	Class.forName(driver); // Driver Loading
   conn = DriverManager.getConnection(url, userId, passwd); // Connection
   		   
   stmt = conn.createStatement();
	String sql = "USE Dapoba_db";
	
	stmt.execute(sql);
	
	sql = "select *from history where Account_ID = 'test1';";
	rs = stmt.executeQuery(sql);
	while(rs.next()){
		date.add(rs.getString("date"));
		coin.add(rs.getString("Coin_history"));
		milege.add(rs.getString("Mileage_history"));
		file.add(rs.getString("File_name"));
	}
	
	
	sql = "select * from Mileage_Coin where ID = 'test1'";
	rs = stmt.executeQuery(sql);
	rs.next();
	Milege = rs.getString("Mileage");
	Coin = rs.getString("Coin");
	
	stmt.close(); 
	conn.close(); // close
}
catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang = "en">
 <jsp:include page="header.jsp" flush = "true"/>

    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>코인/마일리지</h4>
        </div>
        <div class="col-md-6 col-md-offset-3 coin_box0">
          코인과 마일리지 내역을 확인하실 수 있습니다.
        </div>
        <div class="col-md-6 col-md-offset-3 coin_box1">
          잔여 마일리지 :　<span><%= Milege %></span>P　잔여 코인 :　<span><%= Coin %></span>coin　　
          <button class="btn1 btn-default btn-sm" id ="custom_btn" onclick = "location = 'charge-coin.jsp'" >충전</button>
        </div>
        <div class="col-md-6 col-md-offset-3">
          <div class="row">
            <div class="col-md-3 coin_box2">
              날짜
            </div>
            <div class="col-md-3 coin_box2">
              코인
            </div>
            <div class="col-md-3 coin_box2">
              마일리지
            </div>
	    <div class="col-md-3 coin_box2">
              파일명
            </div>
          </div>
          <div class="row">
            <div class="col-md-3 coin_box2">
            <%
            for(int i = 0; i <date.size(); i++){ %>
            	<%= date.get(i) %><br>
            <% } %>

            </div>
            <div class="col-md-3 coin_box2">
			<%
            for(int i = 0; i <coin.size(); i++){ %>
            	<%= coin.get(i) %><br><br>
            <% } %>
            </div>
            <div class="col-md-3 coin_box2">
			<%
            for(int i = 0; i <milege.size(); i++){ %>
            	<%= milege.get(i) %><br><br>
            <% } %>
            </div>
	    <div class="col-md-3 coin_box2">
			<%
            for(int i = 0; i <file.size(); i++){ %>
            	<%= file.get(i) %><br><br>
            <% } %>
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