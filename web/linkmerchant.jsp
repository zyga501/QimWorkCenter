<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/danlanlaydate.css" rel="stylesheet" type="text/css"/>
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
                                        <button type="button" class="btn btn btn-success" onclick="linkitems()"> <i class="fa fa-search"></i> 确定</button>
                                </span>
                </div>
            </div>
        </div>
    </div>
    </div>
</form>
<center><div id="pagecountdiv"></div></center>
<center><div id="contentdiv"></div></center>
</body>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/laypage.js"></script>
<script src="<%=request.getContextPath()%>/js/laydate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dateRangeUtil.js"></script>
<script>
    function searchlist(curr) {
        $("#pagecountdiv").html("");
        $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
        $.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/weixin/wxmch!LinkMerchant',
            dataType: "json",
            data: $("#searchform").serialize() + "&currpagenum=" + curr,
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.errorMessage != null) {
                    $("#contentdiv").html(json.errorMessage);
                }
                else {
                    var htmlStr = "<form id='checkform' ><table class='table table-striped table-bordered table-hover'>" + "<thead>";
//                    for (var key in json[0]) {
//                        htmlStr += "<th>" + key + "</th>";
//                    }
                    htmlStr +="<th></th><th>商户号</th><th>商户名</th><th>销售/机构</th>";
                    htmlStr += "</thead><tbody>";
                    for (var i = 0, l = json.length; i < l; i++) {
                        htmlStr += "<tr><td><input type='checkbox' name='chkbox' value="+json[i]['subid']+"></td>";
                        htmlStr += "<td>" + json[i]['subid'] + "</td>";
                        htmlStr += "<td>" + json[i]['name'] + "</td>";
                        if (json[i]['unick'] ==null)
                            htmlStr += "<td></td>";
                        else
                            htmlStr += "<td>" + json[i]['unick'] + "</td>";
//                        for (var key in json[i]) {
//                            htmlStr += "<td>" + json[i][key] + "</td>";
//                        }
                        htmlStr += "</tr>";
                    }
                    htmlStr += "</tobdy></table></form>";
                    $("#contentdiv").html(htmlStr);
                }
            }
        })
    }

    function linkitems(){
        $.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/weixin/wxmch!LinkItems',
            dataType: "json",
            data: $("#checkform").serialize()+"&salemanid="+getQueryString("sid"),
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.resultCode != null) {
                    $("#contentdiv").html("操作失败");
                }else{
                    searchlist(1);
                }
            }
        })
    }

    $().ready(function(){
        searchlist(1);
    });

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
</script>
</html>