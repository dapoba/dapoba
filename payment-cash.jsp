<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <jsp:include page="header.jsp" flush = "true"/>

    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>������ �Ա�</h4>
        </div>
      </div>
        <div class="row">
          <div class="col-md-6 col-md-offset-3 select-box optional-border">
            <h6><b>�Ա��Ͻ� ������� �������ּ���.</b></h6>
            <form method = "POST" action = "Milege_Coin"><br>
              <select name = "cashBank" class="form-control" id="select">
                <option value="shinhan">��������</option>
                <option value="kukmin">��������</option>
                <option value="woori">�츮����</option>
              </select>
            <br><br>
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