<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
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
            <h6><b>������ ���ϸ��� �� ������ �������ּ���.(10coin/p ����)</b></h6><br>
            ����� ���� <span><%= (String)request.getAttribute("page") %></span>�� (<span><%= (String)request.getAttribute("total") %></span> coin/point) ��<br><br>
            <div class="form-group has-success">
            <form method = "POST" action= "Milege_Coin">
              <label class="control-label a" for="inputUsingCoin">mileage</label>
              <input name = "useMilege" type="text" class="form-control" id="inputUsingMilege">P
              <label class="control-label" for="inputUsingCoin">coin</label>
              <input name = "useCoin" type="text" class="form-control" id="inputUsingCoin">coin,��
            </div>
            (�ܿ� ���ϸ���: <span><%= (String)request.getAttribute("milege") %></span>P, �ܿ� ����: <span><%= (String)request.getAttribute("coin") %></span>coin) <br><br>
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