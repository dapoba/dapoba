<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import = "java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang = "en">
 <jsp:include page="header.jsp" flush = "true"/>

    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>����/���ϸ���</h4>
        </div>
        <div class="col-md-6 col-md-offset-3 coin_box0">
          ���ΰ� ���ϸ��� ������ Ȯ���Ͻ� �� �ֽ��ϴ�.
        </div>
        <div class="col-md-6 col-md-offset-3 coin_box1">
          �ܿ� ���ϸ��� :��<span><%= (String)request.getAttribute("milege") %></span>P���ܿ� ���� :��<span><%= (String)request.getAttribute("coin") %></span>coin����
          <button class="btn1 btn-default btn-sm" id ="custom_btn" onclick = "location = 'charge-coin.jsp'" >����</button>
        </div>
        <div class="col-md-6 col-md-offset-3">
          <div class="row">
            <div class="col-md-3 coin_box2">
              ��¥
            </div>
            <div class="col-md-3 coin_box2">
              ����
            </div>
            <div class="col-md-3 coin_box2">
              ���ϸ���
            </div>
	    <div class="col-md-3 coin_box2">
              ���ϸ�
            </div>
          </div>
          <div class="row">
            <div class="col-md-3 coin_box2">
            <%
            Vector date = (Vector)request.getAttribute("date");
            for(int i = 0; i <date.size(); i++){ %>
            	<%= date.get(i) %><br>
            <% } %>

            </div>
            <div class="col-md-3 coin_box2">
			<%
            Vector coin = (Vector)request.getAttribute("coin");
            for(int i = 0; i <coin.size(); i++){ %>
            	<%= coin.get(i) %><br><br>
            <% } %>
            </div>
            <div class="col-md-3 coin_box2">
			<%
            Vector mileage = (Vector)request.getAttribute("milege");
            for(int i = 0; i <mileage.size(); i++){ %>
            	<%= mileage.get(i) %><br><br>
            <% } %>
            </div>
	    <div class="col-md-3 coin_box2">
			<%
            Vector file = (Vector)request.getAttribute("file");
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