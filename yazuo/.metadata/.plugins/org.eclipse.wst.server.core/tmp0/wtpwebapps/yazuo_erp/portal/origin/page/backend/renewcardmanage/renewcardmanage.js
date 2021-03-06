/**
 * 续卡管理
 */
define(['avalon', 'util', 'json', 'moment', '../../../widget/pagination/pagination', '../../../widget/form/select', '../../../widget/grid/grid','../../../widget/calendar/calendar'], function (avalon, util, JSON, moment) {
    var win = this,
        erp = win.erp,
        pageMod = {},
        tempWidgetIdStore = [];
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName) {
            var getTypeId = function (n) {//设置和获取widget的id;
                var widgetId = pageName + n;
                tempWidgetIdStore.push(widgetId);
                return widgetId;
            };
            var pageVm = avalon.define(pageName, function (vm) {
                vm.permission = {//权限
                    renew_card_status_192 : util.hasPermission("renew_card_status_192"),//修改状态
                    renew_card_history_193 : util.hasPermission("renew_card_history_193")//查看历史
                };
                vm.pageUserId = erp.getAppData('user').id;//用户id



                //状态
                vm.$statusSelectOpts = {
                    "selectId": getTypeId('-status'),
                    "options": [],
                    "onSelect": function(v,t,o){
                        pageVm.searchObj.merchantStatus = v;
                    }
                };
                //修改状态
                vm.$modiStatusSelectOpts = {
                    "selectId": getTypeId('-modistatus'),
                    "options": []
                };
                //分类
                vm.$typeSelectOpts = {
                    "selectId": getTypeId('-type'),
                    "options": [],
                    "onSelect": function(v,t,o){
                        pageVm.searchObj.cardType = v;
                    }
                };
                /*页面逻辑变量*/
                vm.isFirstSearch = true;//是否初始化查询
                vm.modistatusArr = [];//修改时状态列表
                vm.statusArr = [];//状态
                vm.typeArr = [];//分类
                vm.statusId = -1;//状态id
                vm.listId = -1;//列表id
                vm.avgCardNumFor3Months = '';//3个月平均值
                vm.cardTotalNum = '';//开卡总数
                vm.cardTotalNumFor3Months = '';//3个月开卡总数
                vm.isShowPop = false;//是否显示弹层


                /*页面逻辑变量-end*/
                vm.remark = '';//修改状态备注
                vm.searchObj = {
                    merchantName : '',//商户名称
                    availDateFrom : '',//卡天数-开始
                    availDateTo : '',//卡天数-结束
                    merchantStatus : "",//状态
                    cardType : ""//类型
                };

                vm.search = function (){
                    avalon.getVm(getTypeId('-grid')).load();
                };

                /*grid-start*/
                vm.keyword = "";
                vm.$gridOpts = {
                    "gridId": getTypeId('-grid'),
                    "disableCheckAll": true,  //是否禁用全选
                    "disableCheckbox": true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        return '<div class="ui-grid-tbar fn-clear">' +
                            '<div class="fn-left">' +
                            '共<span class="num">{{gridTotalSize}}</span>条' +
                            '</div>' +
                            '</div>MS_OPTION_TBAR' + tmpHade[1];
                    },
                    "columns": [
                        {
                            "dataIndex": "merchantName",
                            "text": "商户",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<div class="grind-left-width">{{$outer.el.merchantName}}</div>'
                            }
                        },
                        {
                            "dataIndex": "username",
                            "text": "前端顾问"
                        },
                        {
                            "dataIndex": "cardType",
                            "text": "卡类型"
                        },
                        {
                            "dataIndex": "noActivatedNum",
                            "text": "未激活数量"
                        },
                        {
                            "dataIndex": "availDateNum",
                            "text": "卡可用天数",
                            "html": true,
                            "renderer": function (v, rowData) {
                                var avgCardNumFor3Months = rowData.avgCardNumFor3Months;//3个月平均值
                                var cardTotalNum = rowData.cardTotalNum;//开卡总数
                                var cardTotalNumFor3Months = rowData.cardTotalNumFor3Months;//3个月开卡总数
                                var availDateNum = rowData.availDateNum || '-';//开可用天数
                                return '<div data-avgCardNumFor3Months='+avgCardNumFor3Months+' data-cardTotalNum='+cardTotalNum+' data-cardTotalNumFor3Months='+cardTotalNumFor3Months+' ms-mouseenter="showCardInfo($event)" ms-mouseleave="hideCardInfo($event)">'+availDateNum+'</div>'
                            }
                        },
                        {
                            "dataIndex": "lastActivateDate",
                            "text": "最后开卡日期",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<div>{{$outer.el.lastActivateDate | date("yyyy-MM-dd")}}</div>'
                            }
                        },
                        {
                            "dataIndex": "statusCn",
                            "text": "状态"
                        },
                        {
                            "dataIndex": "select",
                            "text": "操作",
                            "html": true,
                            "renderer": function (v, rowData) {
                                var id = rowData['id'];
                                var status = rowData['status'];
                                var remark = rowData['remark'];
                                return '<a href="javascript:;" ms-if="permission.renew_card_status_192" class="modify-status remewcard-remark-'+id+'" data-status='+status+' data-id='+id+'  ms-click="modStatus" data-remark='+remark+'>修改状态</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" ms-if="permission.renew_card_history_193"  data-id='+id+' ms-click="lookHistory">查看历史</a>'
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        if(pageVm.isFirstSearch){
                            requestData = _.extend(requestData);
                            var url = "besRenewCard/searchAll.do";
                        }else{
                            requestData = _.extend(requestData, vm.searchObj.$model);
                            var url = "/besRenewCard/search.do";
                        }
                        util.c2s({
                            "url": erp.BASE_PATH + url,
                            "contentType": 'application/json',
                            "data": JSON.stringify(requestData),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    if(data['statuses']){
                                        for(var k in data['statuses']){
                                            pageVm.statusArr.push({'text': data['statuses'][k],value: k});
                                        }
                                        avalon.getVm(getTypeId('-status')).setOptions(pageVm.statusArr,0);
                                        pageVm.modistatusArr = pageVm.statusArr.slice(1);
                                        for(var j in data['cardtypes']){
                                            pageVm.typeArr.push({'text': data['cardtypes'][j],value: j});
                                        }
                                        avalon.getVm(getTypeId('-type')).setOptions(pageVm.typeArr,0);
                                    }
                                    /*测试
                                    for(var i=0;i<data.rows.length;i++){
                                        data.rows[i].id = i;
                                    }
                                    测试-end*/
                                    callback.call(this, data);
                                    pageVm.isFirstSearch = false;
                                }
                            }
                        });
                    }
                };
                /*grid-end*/
                //显示卡详细详细
                vm.showCardInfo = function(event){
                    pageVm.avgCardNumFor3Months = $(this).attr('data-avgCardNumFor3Months');//3个月平均值
                    pageVm.cardTotalNum = $(this).attr('data-cardTotalNum');//开卡总数
                    pageVm.cardTotalNumFor3Months = $(this).attr('data-cardTotalNumFor3Months');//3个月开卡总数
                    pageVm.isShowPop = true;
                    var posX = $(this).offset().left;
                    var posY = $(this).offset().top-$('.renewcardmanage-card-pop')[0].clientHeight-50;
                    $('.renewcardmanage-card-pop').css({left:posX,top:posY});
                };
                vm.hideCardInfo = function(event){
                    pageVm.isShowPop = false;
                };
                //input输入数字现找
                vm.setNum = function(n){
                    var newValue;
                    var reg = /^[0-9]*$/;
                    var val = $(this).val();
                    if(reg.test(val)){
                        newValue = val;
                    }else{
                        newValue = parseInt(val)?parseInt(val):'';
                    }
                    $(this).val(newValue);
                    vm.searchObj.n = newValue;
                };
                //修改状态
                vm.modStatus = function(){
                    vm.statusId = $(this).attr('data-status');
                    vm.listId = $(this).attr('data-id');
                    pageVm.remark = $(this).attr('data-remark');
                    var indexAt = -1;
                    avalon.getVm(getTypeId("-modCardDialog")).open();
                    _.map(vm.modistatusArr,function(item,index){
                        if(item.value === vm.statusId){
                            indexAt = index;
                            return;
                        }
                    });
                    avalon.getVm(getTypeId("-modistatus")).setOptions(vm.modistatusArr,indexAt);
                };
                vm.$modCardDialogOpts = {
                    "title": '修改状态',
                    "dialogId": getTypeId("-modCardDialog"),
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确&nbsp;定</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onOpen": function () {
                    },
                    "onClose": function () {
                        pageVm.remark = '';
                    },
                    "submit": function (evt) {
                        var requestData = {
                            id : pageVm.listId,
                            status : avalon.getVm(getTypeId('-modistatus')).selectedValue,
                            remark : pageVm.remark
                        };
                        /*if(pageVm.statusId == requestData.status){
                            avalon.getVm(getTypeId("-modCardDialog")).close();
                            return;
                        }*/
                        util.c2s({
                            "url": erp.BASE_PATH + "besRenewCard/update.do",
                            "contentType": 'application/json',
                            "data": JSON.stringify(requestData),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    var gridVm = avalon.getVm(getTypeId('-grid'));
                                    gridVm.selectById(pageVm.listId);
                                    var selectedGridData = gridVm.getSelectedData()[0];
                                    gridVm.updateData({ //注意updateData参数应该是个全新的数据结构，如果指定id，则更新对应的record，未指定的话认为是新增的数据
                                        "id": selectedGridData.id,
                                        "status" : avalon.getVm(getTypeId('-modistatus')).selectedValue,
                                        "statusCn" : avalon.getVm(getTypeId('-modistatus')).selectedText,
                                        "remark" : pageVm.remark
                                    });

                                    gridVm.unselectById(selectedGridData.id);
                                    $('.remewcard-remark-'+selectedGridData.id).attr("data-remark",pageVm.remark);
                                    $('.remewcard-remark-'+selectedGridData.id).attr("data-status",avalon.getVm(getTypeId('-modistatus')).selectedValue);
                                    avalon.getVm(getTypeId("-modCardDialog")).close();
                                }
                            }
                        });
                    }
                };
                //修改状态-end
                //查看历史
                vm.lookHistory = function(){
                    vm.listId = $(this).attr('data-id');
                    avalon.getVm(getTypeId("-lookHistoryDialog")).open();
                    avalon.getVm(getTypeId('-gridHistory')).load();
                };
                vm.$lookHistoryDialogOpts = {
                    "title": '状态修改历史',
                    "width": 900,
                    "dialogId": getTypeId("-lookHistoryDialog"),
                    "getTemplate": function (tmpl) {
                        var tmplTemp = tmpl.split('MS_OPTION_FOOTER'),
                            footerHtmlStr = '<div class="ui-dialog-footer fn-clear">' +
                                '<div class="ui-dialog-btns">' +
                                '<button type="button" class="submit-btn ui-dialog-btn" ms-click="submit">确&nbsp;定</button>' +
                                '<span class="separation"></span>' +
                                '<button type="button" class="cancel-btn ui-dialog-btn" ms-click="close">取&nbsp;消</button>' +
                                '</div>' +
                                '</div>';
                        return tmplTemp[0] + 'MS_OPTION_FOOTER' + footerHtmlStr;
                    },
                    "onOpen": function () {
                    },
                    "onClose": function () {
                        var formVm = avalon.getVm(getTypeId('addEditFormId'));
                        formVm.beginTime = '';
                        formVm.endTime = '';
                    },
                    "submit": function (evt) {
                        avalon.getVm(getTypeId("-lookHistoryDialog")).close();
                    }
                };
                /*查看历史grid-start*/
                vm.$gridHistorOpts = {
                    "gridId": getTypeId('-gridHistory'),
                    "disableCheckAll": true,  //是否禁用全选
                    "disableCheckbox": true, //是否禁用选择框，默认不禁用
                    "getTemplate": function (tmpl) {
                        var tmpHade = tmpl.split('MS_OPTION_TBAR');
                        return '<div class="ui-grid-tbar fn-clear">' +
                            '<div class="fn-left">' +
                            '</div>' +
                            '</div>MS_OPTION_TBAR' + tmpHade[1];
                    },
                    "columns": [
                        {
                            "dataIndex": "operatingTime",
                            "text": "时间",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<div class="grind-left-width">{{$outer.el.operatingTime | date("yyyy-MM-dd")}}</div>'
                            }
                        },
                        {
                            "dataIndex": "username",
                            "text": "操作人",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<div class="grind-left-width">{{$outer.el.username}}</div>'
                            }
                        },
                        {
                            "dataIndex": "description",
                            "text": "备注",
                            "html": true,
                            "renderer": function (v, rowData) {
                                return '<div class="grind-decription-width">{{$outer.el.description}}</div>'
                            }
                        }
                    ],
                    "onLoad": function (requestData, callback) {
                        var formVm = avalon.getVm(getTypeId('addEditFormId'));
                        requestData = _.extend(requestData, {
                            id: pageVm.listId,
                            beginDate: moment(formVm.beginTime)/1,
                            endDate: moment(formVm.endTime)/1
                        });
                        util.c2s({
                            "url": erp.BASE_PATH + "besRenewCard/getHistory.do",
                            "contentType": 'application/json',
                            "data": JSON.stringify(requestData),
                            "success": function (responseData) {
                                var data;
                                if (responseData.flag) {
                                    data = responseData.data;
                                    callback.call(this, data);
                                }
                            }
                        });
                    }
                };
                vm.searchHistory = function(){
                    avalon.getVm(getTypeId('-gridHistory')).load();
                };
                /*查看历史grid-end*/
                //查看历史选择时间
                vm.$startDateOpts = {
                    "calendarId": getTypeId('startDateCalendarId'),
                    "onClickDate": function (d) {
                        var formVm = avalon.getVm(getTypeId('addEditFormId'));
                        formVm.beginTime = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
                    }
                };

                vm.openStartDateCalendar = function () {
                    var formVm = avalon.getVm(getTypeId('addEditFormId'));
                    var meEl = $(this),
                        calendarVm = avalon.getVm(getTypeId('startDateCalendarId')),
                        calendarEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$startDateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(getTypeId('startDateCalendarId'));
                        //注册全局点击绑定
                        util.regGlobalMdExcept({
                            "element": calendarEl,
                            "handler": function () {
                                calendarEl.hide();
                            }
                        });
                    } else {
                        calendarEl = $(calendarVm.widgetElement);
                    }
                    //设置focus Date
                    if (formVm.beginTime) {
                        calendarVm.focusDate = moment(formVm.beginTime, 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = new Date();
                    }
                    if (formVm.endTime) {
                        calendarVm.maxDate = moment(formVm.endTime, 'YYYY-MM-DD')._d;
                    }else{
                        calendarVm.maxDate = moment()._d;
                    }
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                vm.$endDateOpts = {
                    "calendarId": getTypeId('endDateCalendarId'),
                    "onClickDate": function (d) {
                        var formVm = avalon.getVm(getTypeId('addEditFormId'));
                        formVm.endTime = moment(d).format('YYYY-MM-DD');
                        $(this.widgetElement).hide();
                    }
                };
                vm.openEndDateCalendar = function () {
                    var formVm = avalon.getVm(getTypeId('addEditFormId'));
                    var meEl = $(this),
                        calendarVm = avalon.getVm(getTypeId('endDateCalendarId')),
                        calendarEl,
                        inputOffset = meEl.offset();
                    if (!calendarVm) {
                        calendarEl = $('<div ms-widget="calendar,$,$endDateOpts"></div>');
                        calendarEl.css({
                            "position": "absolute",
                            "z-index": 10000
                        }).hide().appendTo('body');
                        avalon.scan(calendarEl[0], [vm]);
                        calendarVm = avalon.getVm(getTypeId('endDateCalendarId'));
                        //注册全局点击绑定
                        util.regGlobalMdExcept({
                            "element": calendarEl,
                            "handler": function () {
                                calendarEl.hide();
                            }
                        });
                    } else {
                        calendarEl = $(calendarVm.widgetElement);
                    }
                    //设置focus Date
                    if (formVm.endTime) {
                        calendarVm.focusDate = moment(formVm.endTime, 'YYYY-MM-DD')._d;
                    } else {
                        calendarVm.focusDate = new Date();
                    }
                    if (formVm.beginTime) {
                        calendarVm.minDate = moment(formVm.beginTime, 'YYYY-MM-DD')._d;
                        calendarVm.maxDate = moment()._d;
                    }else{
                        calendarVm.maxDate = moment()._d;
                    }
                    calendarEl.css({
                        "top": inputOffset.top + meEl.outerHeight() - 1,
                        "left": inputOffset.left
                    }).show();
                };
                vm.$addEditFormOpts = {//表单验证
                    "formId": getTypeId('addEditFormId'),
                    "field": [
                        {
                            "selector": ".beginTime",
                            "name": "beginTime",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "开始时间不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        },
                        {
                            "selector": ".endTime",
                            "name": "endTime",
                            "rule": ["noempty", function (val, rs) {
                                if (rs[0] !== true) {
                                    return "结束时间不能为空";
                                } else {
                                    return true;
                                }
                            }]
                        }
                    ],
                    "beginTime": "",
                    "endTime": ""
                };
                //查看历史选择时间end
                //查看历史-end


            });
            avalon.scan(pageEl[0]);
            avalon.getVm(getTypeId('-grid')).load();

            /*页面初始化请求渲染*/
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
            _.each(tempWidgetIdStore, function (widgetId) {
                var vm = avalon.getVm(widgetId);
                vm && $(vm.widgetElement).remove();
            });
            tempWidgetIdStore.length = 0;
        }
    });
    return pageMod;
});


