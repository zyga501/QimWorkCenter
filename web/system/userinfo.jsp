<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html><head>
</head>
<body class="gray-bg">
<div class="ibox ">
    <div class="ibox-content">
        <div class="tab-content">
            <div id="contact-1" class="tab-pane active">
                <div class="text-center">
                    <h2>${userInfo.unick}</h2>
                    <h3>
                        编号:${userInfo.uname}
                    </h3>
                </div>
                <div class="row  m-b-md">
                    <div class="col-sm-4">
                        <input class="btn btn-primary" type="button" value="名下商户" onclick="merchantlist(${userInfo.id})">
                    </div>
                    <div  class="col-sm-4">
                        <input class="btn btn-danger" type="button" value="关联商户" onclick="linkmerchant(${userInfo.id})">
                    </div>
                    <div  class="col-sm-4">
                        <input class="btn btn-danger" type="button" value="初始化密码" onclick="initpwd(${userInfo.uname})">
                    </div>
                </div>
                <div id="pagecountdiv" class="ibox-content"></div>
                <div id="contentdiv" class="ibox-content"></div>
            </div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/content.min.js"></script>
<script src="<%=request.getContextPath()%>/js/laypage.js"></script>
<script src="<%=request.getContextPath()%>/js/layer.js"></script>
<script>
    function initpwd(param) {
        $.ajax({
            type: 'post',
            url: "<%=request.getContextPath()%>/User!updateUserInfoPwd",
            data: "salemanname=" + param,
            success: function (data) {
                var json = eval("(" + data + ")");

            }
        })
    }

    function merchantlist(param){
        $("#pagecountdiv").html("");
        $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
        $.ajax({
            type: 'post',
            url: "<%=request.getContextPath()%>/weixin/wxmch!FetchSubmerchant",
            data: "salemanid="+param,
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.errorMessage != null) {
                    $("#contentdiv").html(json.errorMessage);
                }
                else {
                    $("#pagecountdiv").html("<span class='label label-success'>" +
                            "总共数据<span class='badge'>" + json[0]["totalcount"] + " </span></span>");
                    var htmlStr = "<table class='table table-striped table-bordered table-hover'>" + "<thead>";
                    for (var key in json[1]) {
                        htmlStr += "<th>" + key + "</th>";
                    }
                    htmlStr += "</thead><tbody>";
                    for (var i = 1, l = json.length; i < l; i++) {
                        htmlStr += "<tr>";
                        for (var key in json[i]) {
                            htmlStr += "<td>" + json[i][key] + "</td>";
                        }
                        htmlStr += "</tr>";
                    }
                    htmlStr += "</tobdy></table>";
                    $("#contentdiv").html(htmlStr);
                }
            }
        })
    }

    function linkmerchant(param){
        var index = parent.layer.open({
            type: 2,
            title: "关联商户",
            area: ['390px', '90%'],
            closeBtn: 1,
            skin: 'layui-layer-rim', //加上边框
            content: "./linkmerchant.jsp?sid="+param
        });
    }
</script>
</body>

</html>

