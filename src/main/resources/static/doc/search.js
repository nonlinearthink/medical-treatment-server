let api = [];
api.push({
    alias: 'api',
    order: '1',
    desc: '药品管理和查询业务',
    link: '药品管理和查询业务',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '管理员创建药品',
});
api[0].list.push({
    order: '2',
    desc: '管理员删除药品',
});
api[0].list.push({
    order: '3',
    desc: '管理员更新药品',
});
api[0].list.push({
    order: '4',
    desc: '查询所有药品列表',
});
api[0].list.push({
    order: '5',
    desc: '通过关键字搜索所有药品列表',
});
api.push({
    alias: 'AdminController',
    order: '2',
    desc: '管理员管理与查询业务',
    link: '管理员管理与查询业务',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '超级管理员添加管理员',
});
api[1].list.push({
    order: '2',
    desc: '超级管理员删除管理员',
});
api[1].list.push({
    order: '3',
    desc: '超级管理员更新管理员权限',
});
api[1].list.push({
    order: '4',
    desc: '超级管理员重置管理员密码',
});
api[1].list.push({
    order: '5',
    desc: '管理员自己更新密码',
});
api[1].list.push({
    order: '6',
    desc: '管理员查询所有管理员（分页）',
});
api.push({
    alias: 'ConsultAskController',
    order: '3',
    desc: '问诊记录管理和查询业务',
    link: '问诊记录管理和查询业务',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '用户创建问诊记录',
});
api[2].list.push({
    order: '2',
    desc: '用户删除问诊记录',
});
api[2].list.push({
    order: '3',
    desc: '用户更新问诊记录',
});
api[2].list.push({
    order: '4',
    desc: '医生更新问诊记录',
});
api[2].list.push({
    order: '5',
    desc: '用户查询自己的所有问诊记录数据（分页）',
});
api[2].list.push({
    order: '6',
    desc: '医生查询所有问诊记录数据（分页）',
});
api[2].list.push({
    order: '7',
    desc: '查询完整问诊信息',
});
api.push({
    alias: 'OrgController',
    order: '4',
    desc: '机构管理与查询业务',
    link: '机构管理与查询业务',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '管理员创建机构',
});
api[3].list.push({
    order: '2',
    desc: '管理员删除机构',
});
api[3].list.push({
    order: '3',
    desc: '管理员更新机构名称',
});
api[3].list.push({
    order: '4',
    desc: '查询所有机构（分页、需携带token）',
});
api.push({
    alias: 'PrescriptionController',
    order: '5',
    desc: '处方管理与查询业务',
    link: '处方管理与查询业务',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '医生创建处方',
});
api[4].list.push({
    order: '2',
    desc: '医生删除处方',
});
api[4].list.push({
    order: '3',
    desc: '医生更新处方',
});
api[4].list.push({
    order: '4',
    desc: '获取电子处方',
});
api.push({
    alias: 'ImageServerController',
    order: '6',
    desc: '图片服务器业务',
    link: '图片服务器业务',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '上传图片（fromData上传）',
});
api[5].list.push({
    order: '2',
    desc: '上传图片（Base64编码）',
});
api[5].list.push({
    order: '3',
    desc: '根据图片ID获取图片地址',
});
api.push({
    alias: 'DiagnosisController',
    order: '7',
    desc: '诊断类型管理和查询业务',
    link: '诊断类型管理和查询业务',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '管理员创建诊断类型',
});
api[6].list.push({
    order: '2',
    desc: '管理员删除诊断类型',
});
api[6].list.push({
    order: '3',
    desc: '管理员更新诊断类型名称',
});
api[6].list.push({
    order: '4',
    desc: '管理员查询诊断类型列表',
});
api.push({
    alias: 'MessageController',
    order: '8',
    desc: '消息发送业务',
    link: '消息发送业务',
    list: []
})
api[7].list.push({
    order: '1',
    desc: '向用户端发送消息',
});
api[7].list.push({
    order: '2',
    desc: '向用户端发送消息',
});
api.push({
    alias: 'DoctorController',
    order: '9',
    desc: '医生管理与查询业务',
    link: '医生管理与查询业务',
    list: []
})
api[8].list.push({
    order: '1',
    desc: '管理员创建医生',
});
api[8].list.push({
    order: '2',
    desc: '管理员删除医生',
});
api[8].list.push({
    order: '3',
    desc: '管理员更新医生',
});
api[8].list.push({
    order: '4',
    desc: '查询医生列表（分页、需携带token）',
});
api[8].list.push({
    order: '5',
    desc: '根据科室id查询医生列表（分页、需携带token）',
});
api[8].list.push({
    order: '6',
    desc: '通过关键字搜索所有医生',
});
api[8].list.push({
    order: '7',
    desc: '医生端查询绑定的医生',
});
api[8].list.push({
    order: '8',
    desc: '根据ID查询医生数据（需携带token）',
});
api.push({
    alias: 'DeptController',
    order: '10',
    desc: '科室管理与查询业务',
    link: '科室管理与查询业务',
    list: []
})
api[9].list.push({
    order: '1',
    desc: '管理员创建科室',
});
api[9].list.push({
    order: '2',
    desc: '管理员删除科室',
});
api[9].list.push({
    order: '3',
    desc: '管理员更新科室信息',
});
api[9].list.push({
    order: '4',
    desc: '查询所有科室（分页、需携带token）',
});
api.push({
    alias: 'PatientController',
    order: '11',
    desc: '问诊人管理与查询业务',
    link: '问诊人管理与查询业务',
    list: []
})
api[10].list.push({
    order: '1',
    desc: '用户端创建问诊人（绑定）',
});
api[10].list.push({
    order: '2',
    desc: '用户端删除问诊人（解绑）',
});
api[10].list.push({
    order: '3',
    desc: '用户端更新问诊人信息',
});
api[10].list.push({
    order: '4',
    desc: '根据问诊人id查询问诊人信息',
});
api[10].list.push({
    order: '5',
    desc: '用户端获取自己创建的问诊人列表',
});
api.push({
    alias: 'LoginController',
    order: '12',
    desc: '登录业务',
    link: '登录业务',
    list: []
})
api[11].list.push({
    order: '1',
    desc: '微信小程序登录',
});
api[11].list.push({
    order: '2',
    desc: 'web医生端登录(正式)',
});
api[11].list.push({
    order: '3',
    desc: 'web医生端登录(无自动注册功能，仅供临时登录)',
});
api[11].list.push({
    order: '4',
    desc: '管理员登录',
});
api.push({
    alias: 'PrescriptionDrugController',
    order: '13',
    desc: '处方药品管理与查询业务',
    link: '处方药品管理与查询业务',
    list: []
})
api[12].list.push({
    order: '1',
    desc: '医生创建处方药品',
});
api[12].list.push({
    order: '2',
    desc: '医生删除处方药品',
});
api[12].list.push({
    order: '3',
    desc: '医生更新处方药品',
});
api[12].list.push({
    order: '4',
    desc: '医生获取处方药品',
});
api.push({
    alias: 'dict',
    order: '14',
    desc: '数据字典',
    link: 'dict_list',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    alias: apiData.alias,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        alias: apiData.alias,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
         for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="' + apiData[j].alias + '.html#header">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="' + apiData[j].alias + '.html#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}