<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.yazuo.erp.base.Constant"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.yazuo.erp.system.vo.SysUserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>雅座ERP</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%
        String path = request.getContextPath();
        int port = request.getServerPort();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + path + "/";
        if(port == 80){
            basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
        }
    %>

    <!-- Place favicon.ico and apple-touch-icon(s) in the root directory -->
    <script>
        var VERSION="9.0.0.0.0"; //版本号设置规则:all.asset.module.page.widget
        var PUBLISH_MODEL = "development";
        var BASE_PATH="<%=basePath%>";  //web根目录地址
        var WIDGET_BASE_PATH="<%=basePath%>portal/origin/widget/";  //widget根目录地址
        (function () {
            var versions = VERSION.split(".");
            var searchParams = location.search.slice(1),
                tempParam;
            searchParams = searchParams.split("&");
            for (var i = 0; i < searchParams.length; i++) {
                tempParam = searchParams[i].split("=");
                if (tempParam[0] === "model" && tempParam[1]) {
                    PUBLISH_MODEL = tempParam[1];
                    break;
                }
            }
            if (PUBLISH_MODEL === "development") {
                document.write('<link rel="stylesheet" href="portal/origin/asset/base.css?v=' + versions[0] + '.' + versions[1] + '">');
                document.write('<link rel="stylesheet" href="portal/origin/widget/widget.css?v=' + versions[0] + '.' + versions[4] + '">');
                document.write('<link rel="stylesheet" href="portal/origin/asset/common.css?v=' + versions[0] + '.' + versions[1] + '">');
                document.write('<link rel="stylesheet" href="portal/origin/asset/index.css?v=' + versions[0] + '.' + versions[1] + '">');
                document.write('<script src="portal/origin/asset/third/underscore/underscore.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/asset/third/underscore/underscore.string.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/asset/third/jquery-1.11.1.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/asset/third/avalon/avalon.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/asset/avalon-ext.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/asset/boot.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
            } else if (PUBLISH_MODEL === "product") {
                document.write('<link rel="stylesheet" href="portal/origin/dist/asset.css?v=' + versions[0] + '.' + versions[1] + '">');
                document.write('<link rel="stylesheet" href="portal/origin/dist/widget.css?v=' + versions[0] + '.' + versions[4] + '">');
                document.write('<link rel="stylesheet" href="portal/origin/dist/page.css?v=' + versions[0] + '.' + versions[3] + '">');
                document.write('<script src="portal/origin/asset/third/underscore/underscore-min.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/asset/third/underscore/underscore.string.min.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/asset/third/jquery-1.11.1.min.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/asset/third/avalon/avalon.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/dist/asset/avalon-ext.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/dist/asset/boot.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/dist/widget.js?v=' + versions[0] + '.' + versions[4] + '"><\/script>');
                document.write('<script src="portal/origin/dist/asset.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/dist/asset/util.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/dist/asset/common.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
                document.write('<script src="portal/origin/dist/module.js?v=' + versions[0] + '.' + versions[2] + '"><\/script>');
                document.write('<script src="portal/origin/dist/page.js?v=' + versions[0] + '.' + versions[3] + '"><\/script>');
                document.write('<script src="portal/origin/dist/asset/index.js?v=' + versions[0] + '.' + versions[1] + '"><\/script>');
            }
        }());
    </script>
    <script>
        <%
            SysUserVO sessionUser = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
            JSONObject jsonSessionUser = sessionUser==null? null: JSONObject.fromObject(sessionUser);
            JSONArray privilege = sessionUser==null? null: JSONArray.fromObject(sessionUser.getListPrivilege());
        %>
        erp.setAppData('permission', <%=privilege%>);   //用户权限
        erp.setAppData('user', <%=jsonSessionUser%>);   //用户
    </script>
</head>
<body>
    <div class="app" ms-controller="app">
        <div class="app-hd">
            <div class="app-inner fn-clear">
                <div class="app-inner-l fn-left">
                    <a class="logo-l" href="<%=basePath%>">雅座ERP</a>
                </div>
                <div class="app-inner-r">
                    <h1 class="page-title">{{pageTitle}}</h1>
                </div>
            </div>
        </div>
        <div class="app-bd">
            <div class="app-inner">
                <div class="app-inner-l fn-left">
                    <div class="app-inner-l-inner">
                        <div class="user-widget" ms-module="userwidget"></div>
                        <div class="page-nav-wrapper"></div>
                    </div>
                    <span class="page-nav-visible-h state-shown iconfont" ms-click="clickNavVisibleHandler">&#xe624;</span>
                </div>
                <div class="app-inner-r" ms-css-min-height="appBdMinHeight">
                    <div id="page-wrapper">

                    </div>
                    <!--<a href="#/video" class="test-page-nav">video</a>
                    <a href="#/video/test" class="test-page-nav" phref="#/video">test</a>-->
                </div>
            </div>
        </div>

    </div>
    <!-- 嵌入插件获得本机MAC地址 -->
    <embed type="application/npsoftpos" width=0 height=0 id="softPos"/>
    <script>
        require(["index","ready!"],function(index){
            index.$init();
        });
    </script>
</body>
</html>


