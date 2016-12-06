<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import = "java.sql.*"%>
    <% 
String driver = "com.mysql.jdbc.Driver"; 
String url = "jdbc:mysql://localhost:3306/?user=root";
String userId = "root";
String passwd = "1234";
Connection conn;
Statement stmt;
ResultSet rs;
String mileage = null;
String coin = null;
int color = 0;
int seperate = 0;
int count = 0;
int totalPay = 0;
try{
	Class.forName(driver); // Driver Loading
   conn = DriverManager.getConnection(url, userId, passwd); // Connection
   		   
   stmt = conn.createStatement();
	String sql = "USE Dapoba_db";
	
	stmt.execute(sql);
	
	sql = "select *from Mileage_Coin where ID = 'test1';";
	rs = stmt.executeQuery(sql);
	rs.next();
	mileage = rs.getString("Mileage");
	coin = rs.getString("Coin");
	
	sql = "select * from File where Account_ID = 'execute'";
	rs = stmt.executeQuery(sql);
	while(rs.next()){
		count += rs.getInt("File_page_no");
	}
	
	sql = "select *from Option_select where ID = 'test1'";
	rs = stmt.executeQuery(sql);
	rs.next();
	color = rs.getInt("Color");
	seperate = rs.getInt("Seperate");
	
	totalPay = count * 10;
	if(color == 2) totalPay *= 5;
	totalPay /= seperate;
	stmt.close(); 
	conn.close(); // close
}
catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <jsp:include page="header.jsp" flush = "true"/>

    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>����</h4>
        </div>
      </div>
        <div class="row">
          <div class="col-md-6 col-md-offset-3 select-box optional-border center-align">
            <h6><b>������ ���ϸ��� �� ������ �������ּ���.</b></h6><br>
            ����� ���� <span><%= count %></span>�� (<span><%= totalPay %></span> coin/point) ��<br><br>
            <div class="form-group has-success">
            <form method = "POST" action= "Milege_Coin">
              <label class="control-label a" for="inputUsingCoin">mileage</label>
              <input name = "useMilege" type="text" class="form-control" id="inputUsingMilege">P
              <label class="control-label" for="inputUsingCoin">coin</label>
              <input name = "useCoin" type="text" class="form-control" id="inputUsingCoin">coin,��
            </div>
            (�ܿ� ���ϸ���: <span><%= mileage %></span>P, �ܿ� ����: <span><%=coin %></span>coin) <br><br>
            ������ ��� ���� : <lable class="radio-inline" id="radio-design">
              <input name = "Bill" type="radio" name="option_bill" id="yesBill" value="yes" checked="checked"> ��
            </lable>
            <lable class="radio-inline" id="radio-design">
              <input name = "Bill" type="radio" name="option_bill" id="noBill" value="no" onclick="rbclick(this)"> �ƴϿ�
            </lable>
            <input type="submit" class="btn1" id="power-center" value="���� �Ϸ�"></input>
            </form>
          </div>
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