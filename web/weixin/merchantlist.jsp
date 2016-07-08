<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.ext.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/danlanlaydate.css" rel="stylesheet" type="text/css"/>
    <script src="<%=request.getContextPath()%>/js/laypage.js"></script>
</head>
<body>
<form id="searchform">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="input-group">
                    <input type="text" name="item" placeholder="查找用户" class="input form-control">
                            <span class="input-group-btn">
                                        <button type="button" class="btn btn btn-primary" onclick="searchlist(1)"> <i class="fa fa-search"></i> 搜索</button>
                                </span>
                </div>
            </div>
        </div>
    </div>
    </div>
</form>
<HR align=center width=100% color=#987cb9>
<div style="float:left" id="pagecountdiv"></div>
<div style="float:right;text-align: center" id="navigatediv"></div>
<center><div id="contentdiv"></div></center>
</body>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dateRangeUtil.js"></script>
<script>
    function searchlist(curr) {
        $("#pagecountdiv").html("");
        $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
        $.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/weixin/wxmch!FetchSubmerchant',
            dataType: "json",
            data: $("#searchform").serialize() + "&currpagenum=" + curr,
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.errorMessage != null) {
                    $("#contentdiv").html(json.errorMessage);
                }
                else {
                    laypage({
                        cont: 'navigatediv',
                        pages: json[0].pagecount,
                        skip: true,
                        skin: 'yahei',
                        jump: function (obj, first) {
                            if(!first){
                                $("#pagecountdiv").html("");
                                $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
                                $.ajax({
                                    type: 'post',
                                    url: '<%=request.getContextPath()%>/weixin/wxmch!FetchSubmerchant',
                                    dataType: "json",
                                    data: $("#searchform").serialize() + "&currpagenum=" + obj.curr,
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
                                                htmlStr += "<tr shh='" + json[i]['商户号'] + "' sh='" + json[i]['商户'] + "'>";
                                                for (var key in json[i]) {
                                                    htmlStr += "<td>" + json[i][key] + "</td>";
                                                }
                                                htmlStr += "</tr>";
                                            }
                                            htmlStr += "</tobdy></table>";
                                            $("#contentdiv").html(htmlStr);
                                            $("table > tbody > tr").click(function () {
                                                showinfo($(this).attr("shh"),$(this).attr("sh"));
                                            });
                                        }
                                    }
                                })}
                        }
                    })
                    $("#pagecountdiv").html("<span class='label label-success'>" +
                            "总共数据<span class='badge'>" + json[0]["totalcount"] + " </span></span>");
                    var htmlStr = "<table class='table table-striped table-bordered table-hover'>" + "<thead>";
                    for (var key in json[1]) {
                        htmlStr += "<th>" + key + "</th>";
                    }
                    htmlStr += "</thead><tbody>";
                    for (var i = 1, l = json.length; i < l; i++) {
                        htmlStr += "<tr shh='" + json[i]['商户号'] + "' sh='" + json[i]['商户'] + "'>";
                        for (var key in json[i]) {
                            htmlStr += "<td>" + json[i][key] + "</td>";
                        }
                        htmlStr += "</tr>";
                    }
                    htmlStr += "</tobdy></table>";
                    $("#contentdiv").html(htmlStr);
                    $("table > tbody > tr").click(function () {
                        showinfo($(this).attr("shh"),$(this).attr("sh"));
                    });
                }
            }
        })
        function  showinfo(usernum,username){
//            layer.open({
//                type: 1,
//                title:"警告",
//                skin: 'layui-layer-demo', //样式类名
//                closeBtn: 1, //不显示关闭按钮
//                shift: 2,
//                shadeClose: true, //开启遮罩关闭
//                content: "<center>确认初始化【"+usernum+"】商户密？<br><input type='button' class='btn btn-primary' value='确定'></center>"
//            });

            layer.confirm("确认初始化【"+username+"】商户密码？", {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type: 'post',
                    url: '<%=request.getContextPath()%>/weixin/wxmch!InitSubmerchantPwd',
                    dataType: "json",
                    data:"id="+usernum,
                    success:function(data){
                        var json = eval("(" + data + ")");
                        if (json.resultCode=='Succeed')
                            layer.msg('操作成功', {icon: 1});
                        else
                            layer.msg('操作失败', {icon: 2});
                    }
                })
            }, function(){
            });
        }
    }

</script>
</html>