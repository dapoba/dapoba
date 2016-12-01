
<%@ page language="java" contentType="text/html; charset=EUC-KR"

    pageEncoding="EUC-KR"%>

<%@page import= "java.sql.*"%>



<!DOCTYPE html>

<html lang="en">

  <head>

  <meta charset="utf-8">



    <meta http-equiv="X-UA-Compatible" content="IE=edge">



    <meta name="viewport" content="width=device-width, initial-scale=1">



    <meta name="description" content="">



    <meta name="author" content="KimHyunAh">



    <link rel="icon" href="../../favicon.ico">

    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">

  </head>

  <body>

  <!--<jsp:include page="header.jsp" flush="true"/>-->

<div class="container">

      <div class="row">

        <div class="col-md-10 col-md-offset-2">

        <% 

        	String cab_number=(String) request.getParameter("cabinet_num");

        	out.print("<h4 class=\"margin-bottom\">사물함"+cab_number+"</h4>");

        %>

        </div>

      </div>

 </div>

<br>

<div class ="row">



 <div class="modal-dialog">

<div class="modal" id="myModal">

        <div class="modal-content">



          <div class="modal-header">



            <h4 class="modal-title">인증번호 </h4>

            <%

            String driver = "com.mysql.jdbc.Driver"; 

        	String url = "jdbc:mysql://localhost:3306/?user=root"; 

        	String userId = "root"; 

        	String passwd = "1234"; 

        	String loc=(String)application.getAttribute("cabinet_loc");

        	Connection conn; 

        	Statement stmt;

        	ResultSet rs; 

        	try

    		{ 

    			Class.forName(driver); // Driver Loading 

    			conn = DriverManager.getConnection(url, userId, passwd);// Connection 

    			String sql = ""; // SQL 작성 

    			stmt = conn.createStatement(); 	// Statement 

    			sql="select cabinet_password from dapoba_db.cabinet where (cabinet_number = '"+cab_number+"') and (cabinet_loc ='"+loc+"');";

    			System.out.println(sql);

    			//sql = "insert into dapoba_db.cabinet(cabinet_number, cabinet_state, cabinet_password, cabinet_loc) values(\"1\",\"1\",\"1250\",\"1\");";

    			rs = stmt.executeQuery(sql);// ResultSet 

    			while(rs.next()) 

    			{

    			//캐비넷이 비어있는 경우 
				out.print("<form action=\"cabinet\" method=\"get\" enctype=\"multipart/form-data\">");
    			if(rs.getString("cabinet_password").equals("0"))

    				{

    					out.print("<input type=\"text\" name=\"verfiNum\" >");

    					out.print("<a href=\"#\" class=\"btn1 btn-custom btn-big\" onclick=\"dataAdd()\"> 확인 </a>");

    	    			out.print("<form>");

    				}
    			else 

    				{

    					out.print(rs.getString("cabinet_password"));

    					out.print("<a href=\"#\" class=\"btn1 btn-custom btn-big\" onclick=\"dataAdd()\"> 비우기 </a>");

    				}

    			}

    			stmt.close(); 

    			conn.close(); // close

    		 } 

    		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }

            %>

          </div>



        </div>



      </div>

</div>

</div>



<!-- Bootstrap core JavaScript

    ================================================== -->

    <!-- Placed at the end of the document so the pages load faster -->

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

    <script>window.jQuery || document.write('<script src="js/jquery.min.js"><\/script>')</script>

    <script src="js/bootstrap.min.js"></script>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->

    <script src="js/ie10-viewport-bug-workaround.js"></script>

<script>function dataAdd(){
       $('#myModal').modal('show');

     }

</script>
  </body>

</html>

</body>

</html>