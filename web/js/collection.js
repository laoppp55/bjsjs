      function change_yzcodeimage() {
          $("#yzImageID").attr("src","/common/image.jsp?temp=" + Math.random());
      }

      function savesurvyinfo() {
          var articleid = collectionForm.articleid.value;
          var content = "";
          var gxqval = $("input[name='gxq']:checked").val();
          var acttime = $("input[name='acttime']:checked").val();
          var yzcode = collectionForm.yzcode.value;

          var detailflag = 0;
          var detailinfo = "";
          $('input[name="viewcontent"]:checked').each(function(){
              content = content + $(this).val() + ",";                        //将选中的值添加到数组chk_value中
              if ($(this).val() == 6) detailflag = 1;
          });
          if (detailflag == 1) detailinfo =  collectionForm.brief.value;
          content = content.substring(0,content.length-1);
          content = gxqval + "|" + content + "|" + detailinfo + "|" + acttime;

          htmlobj=$.ajax({
              url:"/collection/addCollectionInfos.jsp",
              data:{
                  article:articleid,
                  content:content,
                  yzcode:yzcode
              },
              dataType:'json',
              async:false,
              success:function(jsondata){
                  if (jsondata.result == "true") {
                      alert('文章删除成功！！！')
                  } else {
                      alert('文章删除失败！！！');
                  }
              },
              error: function (jqXHR, textStatus, errorThrown) {
                  alert(jqXHR.responseText);
                  alert(jqXHR.status);
                  alert(jqXHR.readyState);
                  alert(jqXHR.statusText);
                  alert(textStatus);
                  alert(errorThrown);
              }
          });
      }

      function saveinfo() {
          var articleid = collectionForm.articleid.value;
          var title = collectionForm.title.value;
          var content = collectionForm.content.value;
          var publicflag = $("input[name='pubic']:checked").val();
          var yzcode = collectionForm.yzcode.value;

          var md5str =hex_md5("&article=" + articleid + "&title=" + title + "&content=" + content + "&publicflag=" + publicflag + "&yzcode=" + yzcode);
          htmlobj=$.ajax({
              url:"/collection/addCollectionInfos.jsp",
              data:{
                  article:articleid,
                  title:title,
                  content:content,
                  publicflag:publicflag,
                  yzcode:yzcode,
                  md5:md5str
              },
              dataType:'json',
              async:false,
              success:function(jsondata){
                  if (jsondata.result == "true") {
                      alert('文章删除成功！！！')
                  } else {
                      alert('文章删除失败！！！');
                  }
              },
              error: function (jqXHR, textStatus, errorThrown) {
                  alert(jqXHR.responseText);
                  alert(jqXHR.status);
                  alert(jqXHR.readyState);
                  alert(jqXHR.statusText);
                  alert(textStatus);
                  alert(errorThrown);
              }
          });
      }
