<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户管理</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/animate.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/style.min.css?v=4.1.0" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/danlanlaydate.css" rel="stylesheet" type="text/css"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-8">
            <div class="ibox">
                <div class="ibox-content">
                    <div class="input-group">
                        <input type="text" placeholder="查找用户" class="input form-control">
                            <span class="input-group-btn">
                                        <button type="button" class="btn btn btn-primary"> <i class="fa fa-search"></i> 搜索</button>
                                </span>
                    </div>
                    <div class="clients-list">
                        <ul class="nav nav-tabs">
                            <li class=""><a data-toggle="tab" href="#tab-1" onclick="fetchuserlist(1)"><i class="fa fa-briefcase"></i>机构<span id="icount1"></span></a>
                            </li>
                            <li class=""><a data-toggle="tab" href="#tab-2" onclick="fetchuserlist(2)"><i class="fa fa-user"></i>销售<span id="icount2"></span></a>
                            </li>
                            <li class=""><a data-toggle="tab" href="#tab-3" onclick="fetchuserlist(3)"><i class="fa fa-briefcase"></i>职员<span id="icount3"></span></a>
                            </li>
                            <li class=""><a data-toggle="tab" href="#tab-4"><i class="fa fa-plus"></i>添加</a>
                            </li>
                        </ul>
                        <div class="tab-content" style="overflow:auto">
                            <div id="tab-1" class="tab-pane active">
                                <div class="full-height-scroll">
                                    <div class="table-responsive" id="contentdiv1">
                                    </div>
                                </div>
                            </div>
                            <div id="tab-2" class="tab-pane">
                                <div class="full-height-scroll">
                                    <div class="table-responsive" id="contentdiv2">
                                    </div>
                                </div>
                            </div>
                            <div id="tab-3" class="tab-pane">
                                <div class="full-height-scroll">
                                    <div class="table-responsive" id="contentdiv3">
                                    </div>
                                </div>
                            </div>
                            <div id="tab-4" class="tab-pane">
                                <div class="full-height-scroll">
                                    <div class="ibox-content">
                                        <form class="form-horizontal">
                                            <div class="form-group">
                                                <div class="form-group">
                                                    <label class="col-sm-3 control-label">角色类型：</label>
                                                    <div class="col-sm-8">
                                                        <select name="role" class="input-sm form-control input-s-sm inline">
                                                        <option value="0">请选择</option>
                                                        <option value="1">机构</option>
                                                        <option value="2">销售</option>
                                                        <option value="3">职员</option>
                                                    </select>
                                                    </div>
                                                </div>
                                                <label class="col-sm-3 control-label">名称：</label>
                                                <div class="col-sm-8">
                                                    <input type="text" name="unick" placeholder="名称" class="form-control">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">账号：</label>
                                                <div class="col-sm-8">
                                                    <input type="password" name="uname" placeholder="账号" class="form-control">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">密码：</label>
                                                <div class="col-sm-8">
                                                    <input type="password" name="upwd" placeholder="密码" class="form-control">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">Logo头像：</label>
                                                <div class="col-sm-8">
                                                    <input type="password" placeholder="头像" class="form-control">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">联系方式：</label>
                                                <div class="col-sm-8">
                                                    <input type="password" name="upwd" placeholder="联系方式" class="form-control">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">联系地址：</label>
                                                <div class="col-sm-8">
                                                    <input type="password" name="upwd" placeholder="联系地址" class="form-control">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">结算账户：</label>
                                                <div class="col-sm-8">
                                                    <input type="password" name="upwd" placeholder="结算账户" class="form-control">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">结算名称：</label>
                                                <div class="col-sm-8">
                                                    <input type="password" name="upwd" placeholder="结算名称" class="form-control">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">开户行：</label>
                                                <div class="col-sm-8">
                                                    <input type="password" name="upwd" placeholder="开户行" class="form-control">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-sm-offset-3 col-sm-8">
                                                    <button class="btn btn-lg btn-primary" type="submit">确 认</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-4" id="userinfo">
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/content.min.js"></script>
<script>
    fetchuserlist(2);
    function  fetchuserlist(role){
        $.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/User!FetchRole',
            data: "roleval=" + role,
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.errorMessage != null) {
                    layer.msg(json.errorMessage);
                } else {
                    $("#icount" + role).html("<span class='badge'>" + json[0]["totalcount"] + " </span>");
                    var htmlStr = "<table class='table table-striped table-bordered table-hover'>" + "<thead>";
                    for (var key in json[1]) {
                        htmlStr += "<th>" + key + "</th>";
                    }
                    htmlStr += "</thead><tbody>";
                    for (var i = 1, l = json.length; i < l; i++) {
                        htmlStr += "<tr href='" + json[i]['账号'] + "'>";
                        for (var key in json[i]) {
                            htmlStr += "<td>" + json[i][key] + "</td>";
                        }
                        htmlStr += "</tr>";
                    }
                    htmlStr += "</tobdy></table>";
                    $("#contentdiv" + role).html(htmlStr);
                    $("table > tbody > tr").click(function () {
                        showinfo($(this).attr("href"));
                    });
                }
            }
        })
    }
    function  showinfo(usernum){
        $("#userinfo").load('<%=request.getContextPath()%>/User!userInfo?uname='+usernum);
       // $("#jjdiv").html(usernum);
    }
</script>
</body>

</html>

