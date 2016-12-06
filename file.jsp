<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" %>
    <%@page import= "java.sql.*"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun_KimHyunAh">
    <link rel="icon" href="../../favicon.ico">

    <title>문서함</title>

    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="fileupload.css" rel="stylesheet">
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>
  </head>

  <body>
    <jsp:include page="header.jsp" flush="true"/>

    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4 class="margin-option">문서함</h4>
        </div>
        <div class="col-md-6 col-md-offset-2 select-box">
        <%
        String driver = "com.mysql.jdbc.Driver"; 
    	String url = "jdbc:mysql://localhost:3306/?user=root"; 
    	String userId = "root"; 
    	String passwd = "1234"; 
    	Connection conn; 
    	Statement stmt;
    	ResultSet rs; 
    	try
		{ 
			Class.forName(driver); // Driver Loading 
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = ""; // SQL 작성 
			String file_list="파일이 존재하지 않습니다.";//파일이 존재하지 않을때 출력하기 위해서 file_list의 디폴트를 경고문구로 저장한다.
			stmt = conn.createStatement(); 	// Statement 
			sql = "select Filename, File_extension from dapoba_db.file where Account_Id='test1'";
			rs = stmt.executeQuery(sql);// ResultSet 
			out.print("<form action=\"FileManage\" method=\"post\" enctype=\"multipart/form-data\">");
			while(rs.next()) 
			{ 
				file_list=rs.getString("Filename")+"."+rs.getString("file_extension");
				out.print("<input type=\"checkbox\" name=\"file_select\" value= \""+file_list+"\"> <a href='FileManage?file_name="+file_list+"'>"+file_list+"</a><br>");
			} 
			out.print("<button class=\"btn btn-success upload-btn\" name=\"select_button\" value=\"print\">출력</button>");
			out.print("</form>");
			stmt.close(); 
			conn.close(); // close
		} 
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
        %>
        <div id="result"></div>
        </div>
        <div class="col-md-3 upload-box">
          <form action="FileManage" method="post" enctype="multipart/form-data">

              <h5>파일 업로드</h5>
              <input type="file" name="filename1" class="upload-hidden"><br>
			  <input type="file" name="filename2" class="upload-hidden"><br>
			  <input type="file" name="filename3" class="upload-hidden"><br>
			  <input type="file" name="filename4" class="upload-hidden"><br>
			  <input type="file" name="filename5" class="upload-hidden"><br>
            <br>
            <button class="btn btn-primary upload-btn" name="select_button" value="upload" >업로드</button>
          </form>
        </div>
      </div>
    </div> <!-- /container -->

  </body>
</html>
