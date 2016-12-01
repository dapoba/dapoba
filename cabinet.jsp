<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import= "java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<body>
<jsp:include page="header.jsp" flush="true"/>
    <div class="container">
      <div class="row">
        <div class="col-md-4 col-md-offset-2">
      <%
     	String driver = "com.mysql.jdbc.Driver"; 
  		String url = "jdbc:mysql://localhost:3306/?user=root"; 
  		String userId = "root"; 
  		String passwd = "1234"; 
  		Connection conn; 
  		Statement stmt;
  		ResultSet rs; 
  		String cabinet_loc="1";//빈에서 받아올예정
  		//캐비넷 위치에 어느 위치의 사물함인지 표시
  		application.setAttribute("cabinet_loc",cabinet_loc);
  		if(cabinet_loc.equals("1"))
  	  		out.print("<h4 class=\"margin-bottom\">사물함 신공학관</h4>");
  		else if(cabinet_loc.equals("2"))
  			out.print("<h4 class=\"margin-bottom\">사물함 명진관</h4>");
  		else if(cabinet_loc.equals("3"))
  			out.print("<h4 class=\"margin-bottom\">사물함 원흥관</h4>");
  		else if(cabinet_loc.equals("4"))
  			out.print("<h4 class=\"margin-bottom\">사물함 중앙도서관</h4>");
  		out.print("</div>");
  		out.print("<div class=\"col-md-3 search_box\">");
  		out.print("<form>");
  	    out.print("<input type=\"text\" name=\"keyword\"></input>");
  	    out.print("<input type=\"submit\" value=\"입력\" class=\"btn1 btn-default btn-custom\"></input>");
  	    out.print("</form>");
  	    out.print("</div>");
  		out.print("</div>");
  		try
		{ 
			Class.forName(driver); // Driver Loading
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = ""; //SQL문 
			int count=1;
			String cabinetState="";
			String cabinetNumber="";
			String cabinetPassword=(String) request.getParameter("keyword");
			stmt = conn.createStatement(); 	// Statement 
			sql="select cabinet_number from dapoba_db.cabinet where cabinet_password= '"+cabinetPassword+"' and cabinet_loc = '1';";//고치기
			rs = stmt.executeQuery(sql);// ResultSet //
			if(rs.next())
				{
					out.println("<script type='text/javascript'>");
					out.println("alert('"+(String)rs.getString("cabinet_number")+"번 사물함에 배정되어있습니다');");
					out.println("</script>");
				}
			else 
			{
				out.println("<script type='text/javascript'>");
				out.println("alert('배정된 사물함이 없습니다.');");
				out.println("</script>");
			}
			out.print("<form action=\"cabinet\" method=\"get\" enctype=\"multipart/form-data\">");
			for(count=1;count<=50;count++)
			{
				sql = "select cabinet_state, cabinet_password from dapoba_db.cabinet where cabinet_number='"+count+"' and cabinet_loc = '1';";
				//sql = "select cabinet_state from dapoba_db.cabinet where cabinet_number='"+count+"' and cabinet_loc = '"+Account.getName()+"'";
				rs = stmt.executeQuery(sql);// ResultSet 
				
				/*사물함이 찼는지 안찼는지 알수 있음. 
				사물함이 비어있다면 배정받을 수 있도록 인증번호를 받는 jsp로 넘어가고
				사물함이 차있다면 이 사물함에 부여한 인증번호(캐비넷에선 인증번호가 비밀번호역할을 한다. 즉 인증번호는 cabinet_password이다)를 출력함. */
				while(rs.next()) 
				{ 
					cabinetState=rs.getString("cabinet_state");
					cabinetPassword=rs.getString("cabinet_password");
					
						if(count%10==1)
						{
							out.print("<div class=\"row\">");
							out.print(" <button class=\"col-md-1 col-md-offset-2 cabinet-box\" name=\"cabinet_num\" onclick=\"location.href='cabinet_verinum.jsp'\" value=\""+count+"\">"+count+"</button>");
						}
						else if(count%10==2)
							out.print("<button class=\"col-md-1 cabinet-box\" name=\"cabinet_num\" onclick=\"location.href='cabinet_verinum.jsp'\"value=\""+count+"\"><span>"+count+"</span></button>");
						else if(count%10==0)
							{
								out.print("<button class=\"col-md-1 cabinet-box\"  name=\"cabinet_num\" onclick=\"location.href='cabinet_verinum.jsp'\"value=\""+count+"\">"+count+"</button>");
								out.print("</div>");	
							}
						else
							out.print("<button class=\"col-md-1 cabinet-box\" name=\"cabinet_num\" onclick=\"location.href='cabinet_verinum.jsp'\"value=\""+count+"\">"+count+"</button>");
				} 
			}
			stmt.close(); 
			conn.close(); // close
		} 
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
      %>
      </form>
      </div>
    </div> <!-- /container -->
    </div>
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