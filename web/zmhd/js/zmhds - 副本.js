function getDepartment(){
    $.ajax({
        type: "post",
        url: "/sitesearch/letter.action",
        data: {"getDepment": ""},
        success: function (data) {
            if (data!==null && data.length>0){
                var html = '<option value="0">--------请选择--------</option>';
                for (var i = 0; i < data.length; i++) {
                    html += '<option value="'+data[i].id+'">'+data[i].cname+'</option>';
                }
                $("#deptid").html(html);
            }
        }
    });
}

function getCategory(){
    $.ajax({
        type: "post",
        url: "/sitesearch/letter.action",
        data: {"getCategory": ""},
        success: function (data) {
            if (data!==null && data.length>0){
                var html = '<option value="0" selected="selected">--------请选择--------</option>';
                for (var i = 0; i < data.length; i++) {
                    html += '<option value="'+data[i].id+'">'+data[i].typename+'</option>';
                }
                $("#categoryCode").html(html);
            }
        }
    });

}

function validateInformationForm()
{
    var i = document.getElementById("title").value;
    if(i == null || i == "")
    {
        alert("请输入标题！");
        return false;
    }
    i = document.getElementById("content").value;
    if(i == null || i == "")
    {
        alert("请输入内容！");
        return false;
    }

    i = document.getElementById("linkman").value;
    if(i == null || i == "")
    {
        alert("请输入联系人！");
        return false;
    }
    i = document.getElementById("phone").value;
    if(i == null || i == "")
    {
        alert("请输入手机号码！");
        return false;
    }
    /* if(!isPhoneNum( document.getElementById("phone").value)){
     alert("请输入正确的电话号码！");
     document.getElementById("phone").focus();
     return false;
     }*/
    if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(document.getElementById("phone").value))) {
        alert("请填写正确的手机号码");
        return false;
    }
    i = document.getElementById("yzcode").value;
    if(i == null || i == "")
    {
        alert("请输入验证码！");
        return false;
    }
}
function isEmail(src)
{
    src = lrtrim(src);
    if(isEmpty(src))
    {       //为空合法
        return true;
    }

    if((src.indexOf("@")<=0) || (src.indexOf(".")<=0) || (src.indexOf(".")==src.length-1))
    {
        return false;
    }
    if((src.indexOf("@")>src.indexOf(".")) || (src.indexOf("@")+1==src.indexOf(".")))
    {
        return false;
    }
    return true;
}
function submit2()
{
    if(isEmpty($j("#categoryCode").val()))
    {
        alert("类别不能为空！");
        document.informationForm3.categoryCode.focus();
        return false;
    }

    if(isEmpty(document.informationForm3.title))
    {

        alert("标题不能为空！");
        document.informationForm3.title.focus();
        return false;
    }
    /* if(isEmpty($j("#deptid").val()))
     {
     alert("部门不能为空！");
     document.informationForm3.deptid.focus();
     return false;
     }*/
    if(isEmpty(document.informationForm3.content))
    {

        alert("内容不能为空！");
        document.informationForm3.content.focus();
        return false;
    }

    if(isEmpty(document.informationForm3.linkman))
    {

        alert("联系人不能为空！");
        document.informationForm3.linkman.focus();
        return false;
    }
    if(isEmpty(document.informationForm3.phone))
    {

        alert("联系电话不能为空！");
        document.informationForm3.phone.focus();
        return false;
    }
    if(!isPhoneNum(document.informationForm3.phone)){
        alert("请输入正确的电话号码！");
        document.informationForm3.phone.focus();
        return false;
    }

    if((document.informationForm3.title.value.length>50))
    {

        alert("标题不能超过50个字！");
        document.informationForm3.title.focus();
        return false;
    }

    if(document.informationForm3.content.value.length>1000)
    {
        alert("内容不能超过1000字！"+"已填字数："+document.informationForm3.content.value.length);
        document.informationForm3.content.focus();
        return false;
    }
    if (confirm("您确定已经填好，并提交信件吗？")){
        /* var tmpid = document.getElementById("iframe1").contentWindow.document.getElementById("tmpid");
         var fileListId1 = document.getElementById("fileListId");
         fileListId1.value = tmpid.value; */
        document.informationForm3.submit();

    }


}
function isEmpty(s)
{

    return ((s == null) || (s.value == "")||s.value=="undefine");
}
function isPhoneNum(strSrc)
{
    var strNum = "0123456789-P";
    strSrc=lrtrim(strSrc);
    alert(strSrc);
    var len = strSrc.length;
    if(len==0)
    {
        return false;
    }
    for(var i=0;i<len;i++)
    {
        if (strNum.indexOf(strSrc.charAt(i))<0)
        {
            return false;
        }
    }
    return true;
}

function lrtrim(strSrc){
    strSrc = lefttrim(strSrc);
    strSrc = righttrim(strSrc);
    return strSrc;
}
function lefttrim(strSrc)
{
    var len = strSrc.length;
    if(typeof(strSrc)!="string")
        return strSrc;
    for (var i=0; i<len; i++)
        if(strSrc.charAt(i)!=" ")
            break;
    strSrc=strSrc.substring(i,len);
    return strSrc;
}
function righttrim(strSrc)
{
    var len = strSrc.length;
    if(typeof(strSrc)!="string")
        return strSrc;
    for (var i=len-1; i>=0; i--)
        if(strSrc.charAt(i)!=" ")
            break;
    strSrc=strSrc.substring(0,i+1);
    return strSrc;
}
function change_yzcodeimage() {
    var verify=document.getElementById("yzImageID");
    verify.setAttribute("src","image.jsp?temp=" + Math.random());
}