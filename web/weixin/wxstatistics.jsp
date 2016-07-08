<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/danlanlaydate.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<center>
    <div class="btn-group form-inline col-lg-12">
        <button type="button" class="btn btn-warning btn-xs" onclick="setdate('today')">今天</button>
        <button type="button" class="btn btn-default btn-xs" onclick="setdate('yestoday')">昨天</button>
        <button type="button" class="btn btn-default btn-xs" onclick="setdate('thisweek')">本周</button>
        <button type="button" class="btn btn-default btn-xs" onclick="setdate('preweek')">上周</button>
        <button type="button" class="btn btn-default btn-xs" onclick="setdate('thismonth')">本月</button>
        <button type="button" class="btn btn-default btn-xs" onclick="setdate('premonth')">上月</button>
    </div>
</center>
<form id="searchform">
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <input name="startdate" id="startdate" class="form-control layer-date" placeholder="开始日期"
                       onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"></div>
            <div class="col-md-3">
                <input name="enddate" id="enddate" class="form-control layer-date" placeholder="结束日期"
                       onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"></div>
            <div class="col-md-3"><input name="salename" type="text" class="form-control" placeholder="代理/销售编号"></div>
            <div class="col-md-3"><input name="submchid" type="text" class="form-control" placeholder="商户号"></div>
        </div>
        <div class="row">
            <div class="col-md-10"><input name="storename" type="text" class="form-control" placeholder="商户名称"></div>
            <div class="col-md-1"><input  type="button" class="form-control btn btn-primary"
                                         onclick="searchlist(1)" value="检索"></div>
            <div class="col-md-1"><input  type="reset" class="form-control btn btn-danger" value="重置"></div>
        </div>
    </div>
</form>
<HR align=center width=100% color=#987cb9>
<div style="float:left" id="pagecountdiv"></div>
<div style="float:right;text-align: center" id="navigatediv"></div>
<div id="contentdiv"></div>
</body>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/laydate.js"></script>
<script src="<%=request.getContextPath()%>/js/laypage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dateRangeUtil.js"></script>
<script>
    $().ready(function(){
        setdate('today');
    });
    function setdate(varstr) {
        if (varstr == "today") {
            $("#startdate").val(DateUtil.Format(dateRangeUtil.getCurrentDate()));
            $("#enddate").val(DateUtil.Format(dateRangeUtil.getCurrentDate()));
        } else if (varstr == "yestoday") {
            $("#startdate").val(DateUtil.Format(dateRangeUtil.getPreviousDate()));
            $("#enddate").val(DateUtil.Format(dateRangeUtil.getPreviousDate()));
        } else if (varstr == "thisweek") {
            $("#startdate").val(DateUtil.Format(dateRangeUtil.getCurrentWeek()[0]));
            $("#enddate").val(DateUtil.Format(dateRangeUtil.getCurrentWeek()[1]));
        } else if (varstr == "preweek") {
            $("#startdate").val(DateUtil.Format(dateRangeUtil.getPreviousWeek()[0]));
            $("#enddate").val(DateUtil.Format(dateRangeUtil.getPreviousWeek()[1]));
        } else if (varstr == "thismonth") {
            $("#startdate").val(DateUtil.Format(dateRangeUtil.getCurrentMonth()[0]));
            $("#enddate").val(DateUtil.Format(dateRangeUtil.getCurrentMonth()[1]));
        } else if (varstr == "premonth") {
            $("#startdate").val(DateUtil.Format(dateRangeUtil.getPreviousMonth()[0]));
            $("#enddate").val(DateUtil.Format(dateRangeUtil.getPreviousMonth()[1]));
        }
    }
    function searchlist(curr) {
        $("#pagecountdiv").html("");
        $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
        $.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/weixin/wx!Fetchstatistics',
            dataType: "json",
            data: $("#searchform").serialize() + "&currpagenum=" + curr,
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.errorMessage != null) {
                    $("#navigatediv").html("");
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
                                $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
                            $.ajax({
                                type: 'post',
                                url: '<%=request.getContextPath()%>/weixin/wx!Fetchstatistics',
                                dataType: "json",
                                data: $("#searchform").serialize() + "&currpagenum=" + obj.curr,
                                success: function (data) {
                                    var json = eval("(" + data + ")");
                                    if (json.errorMessage != null) {
                                        $("#navigatediv").html("");
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
</script>
</html>