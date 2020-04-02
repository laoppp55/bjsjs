/**
 * Created with IntelliJ IDEA.
 * User: Mark
 * Date: 13-12-3
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
$(function () {
    var yearNow = new Date().getFullYear();
    $("li .ClassDate1").each(function(index,ele){
        if(yearNow == parseInt($(ele).html().substr(0, 4))){
            $("#img" + $(ele).attr("id")).attr("style","");
        }
    });
});