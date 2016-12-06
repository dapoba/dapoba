<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="YoonJiHyun">
<link rel="icon" href="../../favicon.ico">
<title>캐비넷</title>
<link href="ie10-viewport-bug-workaround.css" rel="stylesheet">

<link href="cabinet.css" rel="stylesheet">

<title>보관함</title>

</head>

<body>




	<div class="container">

		<div class="row">

			<div class="col-md-10 col-md-offset-2">

				<%
					String cab_number = (String) request.getParameter("cabinet_num");
					out.print("<form action=\"cabinet_assgin.jsp\">");
					out.print("<h4 class=\"margin-bottom\">사물함 " + cab_number + "</h4>");
					application.setAttribute("cab_number", cab_number);
					out.print("</form>");
					String driver = "com.mysql.jdbc.Driver";

					String url = "jdbc:mysql://localhost:3306/?user=root";

					String userId = "root";

					String passwd = "1234";

					String loc = (String) application.getAttribute("cabinet_loc");

					Connection conn;

					Statement stmt;

					ResultSet rs;
					String password = "";
					try {
						Class.forName(driver); // Driver Loading 

						conn = DriverManager.getConnection(url, userId, passwd);// Connection 

						String sql = ""; // SQL 작성 

						stmt = conn.createStatement(); // Statement 

						sql = "select cabinet_password from dapoba_db.cabinet where (cabinet_number = '" + cab_number
								+ "') and (cabinet_loc ='1');";
						System.out.println(sql);
						rs = stmt.executeQuery(sql);// ResultSet 
						while (rs.next()) {
							//캐비넷이 비어있는 경우 
							password = rs.getString("cabinet_password");
						}
						stmt.close();

						conn.close(); // close
					} catch (Exception e) {

					}
				%>

			</div>

		</div>

	</div>

	<br>

	<div class="modal" id="myModal">
		<div class="modal-content">
			<div class="modal-header">

				<%
					System.out.println(password);
					if (password.equals("0"))

					{

						System.out.print("Dddd");
						out.print("<div  id=\"box\">");
						out.print("<h4 class=\"modal-title\">인증번호 </h4>");
						out.print("<form action=\"cabinet_assign.jsp\" method=\"get\" enctype=\"multipart/form-data\">");
						out.print("<input type=\"text\" name=\"verfiNum\" ></input>");

						out.print("<button name=\"okbutton\" class=\"btn1 btn-default btn-custom\" value=\"ok\"> 확인</button>");

						out.print("</form>");
						out.print("</div>");
					} else

					{
						out.print("<div>");
						out.print("<fieldset>");
						out.print("<h4 class=\"modal-title\">인증번호 </h4>");
						out.print("<form action=\"cabinet_confirm.jsp\">");

						out.print("<h6 class=\"margin-bottom\">" + password + "</h6><br>");

						out.print(
								"<button name=\"okbutton\" class=\"btn1 btn-default btn-custom\" value=\"empty\">비우기</button>");
						out.print("</form>");
						out.print("</fieldset>");
						out.print("</div>");
					}
				%>

			</div>
		</div>


	<!-- Bootstrap core JavaScript

    ================================================== -->

	<!-- Placed at the end of the document so the pages load faster -->

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

	<script>
		window.jQuery
				|| document.write('<script src="js/jquery.min.js"><\/script>')
	</script>

	<script src="js/bootstrap.min.js"></script>

	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->

	<script src="js/ie10-viewport-bug-workaround.js"></script>

	<script>
		function dataAdd() {
			$('#myModal').modal('show');

		}
	</script>
</body>

</html>