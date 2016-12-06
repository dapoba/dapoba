<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import= "java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">
    <title>������</title>
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="cabinet.css" rel="stylesheet">
    <link href="bootstrap.css" rel="stylesheet">
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
            <li class="active"><a href="admin.jsp">������ ������</a></li>
            <li><a href="cabinet.jsp">�繰�� ����</a></li>
            <li><a href="manage-payment.jsp">�Ա� ����</a></li>
           
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
  		String cabinet_loc="1";//�󿡼� �޾ƿÿ���
  		
  		String manager_id=(String)application.getAttribute("userid");
  		try{
  			Class.forName(driver); // Driver Loading
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = ""; //SQL�� 
			int count=1;
			stmt = conn.createStatement(); 	// Statement 
			sql="select name from dapoba_db.account where ID='"+ manager_id+"';";//��ġ��
			rs = stmt.executeQuery(sql);// ResultSet //
			while(rs.next())
			{
				//cabinet_loc=rs.getString("name");
			}
  		}
  		catch(Exception e)
  		{
  			
  		}
  		//ĳ��� ��ġ�� ��� ��ġ�� �繰������ ǥ��
  		application.setAttribute("cabinet_loc",cabinet_loc);
  		if(cabinet_loc.equals("1"))
  	  		out.print("<h4 class=\"margin-bottom\">�繰�� �Ű��а�</h4>");
  		else if(cabinet_loc.equals("2"))
  			out.print("<h4 class=\"margin-bottom\">�繰�� ������</h4>");
  		else if(cabinet_loc.equals("3"))
  			out.print("<h4 class=\"margin-bottom\">�繰�� �����</h4>");
  		else if(cabinet_loc.equals("4"))
  			out.print("<h4 class=\"margin-bottom\">�繰�� �߾ӵ�����</h4>");
  		out.print("</div>");
  		out.print("<div class=\"col-md-3 search_box\">");
  		out.print("<form>");
  	    out.print("<input type=\"text\" name=\"keyword\"></input>");
  	    out.print("<input type=\"submit\" name=\"input\" value=\"�Է�\" class=\"btn1 btn-default btn-custom\"></input>");
  	    out.print("</form>");
  	    out.print("</div>");
  		out.print("</div>");
  		try
		{ 
			Class.forName(driver); // Driver Loading
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = ""; //SQL�� 
			int count=1;
			String cabinetState="";
			String cabinetNumber="";
			String cabinetPassword=(String) request.getParameter("keyword");
			stmt = conn.createStatement(); 	// Statement 
			sql="select cabinet_number from dapoba_db.cabinet where cabinet_password= '"+cabinetPassword+"' and cabinet_loc = '"+cabinet_loc+"';";//��ġ��
			rs = stmt.executeQuery(sql);// ResultSet //
			if(rs.next())
				{
					out.println("<script type='text/javascript'>");
					out.println("alert('"+(String)rs.getString("cabinet_number")+"�� �繰�Կ� �����Ǿ��ֽ��ϴ�');");
					out.println("</script>");
				}
			else 
			{
				if(request.getParameter("input")!=null)
				{
				out.println("<script type='text/javascript'>");
				out.println("alert('������ �繰���� �����ϴ�.');");
				out.println("</script>");
				}
			}
			
			out.print("<form action=\"cabinet_verinum.jsp\" method=\"get\" enctype=\"multipart/form-data\">");
			for(count=1;count<=50;count++)
			{
				sql = "select cabinet_state, cabinet_password from dapoba_db.cabinet where cabinet_number='"+count+"' and cabinet_loc = '1';";
				//sql = "select cabinet_state from dapoba_db.cabinet where cabinet_number='"+count+"' and cabinet_loc = '"+Account.getName()+"'";
				rs = stmt.executeQuery(sql);// ResultSet 
				
				/*�繰���� á���� ��á���� �˼� ����. 
				�繰���� ����ִٸ� �������� �� �ֵ��� ������ȣ�� �޴� jsp�� �Ѿ��
				�繰���� ���ִٸ� �� �繰�Կ� �ο��� ������ȣ(ĳ��ݿ��� ������ȣ�� ��й�ȣ������ �Ѵ�. �� ������ȣ�� cabinet_password�̴�)�� �����. */
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
    <script>
     function dataAdd(){
       $('#myModal').modal('show');
     }
    </script>
  </body>
</html>