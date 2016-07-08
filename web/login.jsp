<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>登&nbsp;&nbsp;录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%=request.getContextPath()%>/css/login.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <style>
        .logo {
            width: 247px;
            margin: 0 auto;
            margin-top:60px;
            padding: 15px;
            text-align: center;
        }
        *{margin:0;padding:0;list-style-type:none;}
        a,img{border:0;}
        body{font:12px/180% Arial, Helvetica, sans-serif, "新宋体";}
        .visual{height:470px;border-top:1px solid #d7d7d7;overflow:hidden; margin-left:0px}
        .banner{width:800px;overflow:hidden; height:470px; float:left; position:relative}
        .container{ margin-left:30px;width:350px;height:422px; float:left; background:#FFF}
        .banList{height:422px;overflow:hidden;position:relative; margin-left:0px}
        .banList ul{width:9999px;height:422px;overflow:hidden;position:absolute;top:0;left:0}
        .banList li{float:left;width:0;height:422px;}
        .banList li span{display:block;width:100%;height:422px}
        .banList li a{display:block;width:100%;height:422px}
        .banList li.active{opacity:1;transform:scale(1);z-index:2;}
        .fomW{position:absolute;bottom:20px;left:50%;height:20px;z-index:9;width:1000px;margin-left:-500px}
        .jsNav{text-align:center;}
        .jsNav a{display:inline-block;background:#fff;width:15px;height:15px;border-radius:50%;margin:0 5px;}
        .jsNav a.current{background:#fc8f0f;cursor:pointer}
        h1{ auto 50px auto;text-align:center;color:#00F;margin-left:-25px;font-size:35px;font-weight: bold;text-shadow: 0px 1px 1px #555;}
        h2{margin:40px auto;text-align:center;color: #FFF;font-size:18px;font-weight: bold;text-shadow: 0px 1px 1px #555; margin-top:1px}
        h1 sup{
            font-size: 18px;
            font-style: normal;
            position: absolute;
            margin-left: 10px;}
    </style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/qrcode.js"></script>
</head>
<body style="background-color: #444444" align="center">
<div style="width:1190px;margin-left: auto;margin-right: auto;">
    <h1>企盟科技业务中心<sup>2016</sup></h1>
    <h2>Work Center Of qimeng Technology</h2>
</div>
<div class="visual" style="width:1190px;margin-left: auto;margin-right: auto;">
    <div class="banner">
        <ul class="banList">
            <li class="active"><a href="#"><img src="img\img_main_1.jpg"/></a></li>
            <li><a href="#"><img src="img\img_main_2.jpg"/></a></li>
            <li><a href="#"><img src="img\img_main_3.jpg"/></a></li>
            <li><a href="#"><img src="img\img_main_4.jpg"/></a></li>
        </ul>
        <div class="fomW">
            <div class="jsNav">
                <a href="#" class="current"></a>
                <a href="#" class=""></a>
                <a href="#" class=""></a>
                <a href="#" class=""></a>
            </div>
        </div>
    </div>
    <div class="container" id="container">
            <div class="login page page-0">
        <div class="logo">
            <img src="img/qmlogo.png" width="200" alt="" />
        </div>
            <form class="form-signin form-inline" action="<%=request.getContextPath()%>/User!Login" method="post" >
                <div id="myTabContent" class="tab-content">
                    <div id="home" class="tab-pane in active">
                        <div class="form-group">
                            <label for="uname">账号</label>
                            <input type="text" class="form-control" id="uname" name="uname" placeholder="账号">
                        </div>
                        <div class="form-group">
                            <label for="upwd">密码</label>
                            <input type="password" class="form-control" id="upwd" name="upwd" placeholder="密码">
                        </div>
                        <div class="form-inline">
                            <label for="upwd">角色</label>
                            <select name="loginrole"  class="form-control" >
                                <option value="1"　selected>默认</option>
                                <option value="2">微信商户</option>
                                <option value="3">京东商户</option>
                            </select>
                                <button type="submit" class="btn btn-primary" >登 录</button>
                        </div>
                        <div style="color:#ff0000;font-size: 12px;">
                           ${loginErrorMessage}
                        </div>
                    </div>
                    <div id="wx" class="tab-pane"><img
                            src="http://www.runoob.com/wp-content/themes/w3cschool.cc/assets/img/qrcode.jpg"
                            alt="使用微信扫一扫">
                    </div>
                </div>
            </form>
                <p>Copyright©2016企盟科技 All Rights Reserved Powered </p>
            </div>
        </div>
</div>
</body>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/banner.js"></script>
<script>
$(function(){
$(".banner").swBanner();
});
    function login(){
        $.ajax({
            type: 'post',
            url: 'User!Login',
            dataType:"json",
            data:$("form").serialize(),
            success: function (data) {
                var json =  eval("(" + data + ")");
                if ( json.resultCode =="Failed")
                alert ("登录失败，请检查账号密码，验证码");
                else if (json.resultCode =="Succeed"){
                    alert(json.resultCode)
                    window.location.href ="mainpage.jsp";

                }
            }
        })
    }
    function iniqcode() {;
                var qr = qrcode(10, 'Q');
                qr.addData(json.code_url);
                qr.make();
                $("#wx").innerHTML = qr.createImgTag();
                $("#QRCode")[0].appendChild(dom);
    }
</script>
</html>