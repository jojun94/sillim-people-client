var userData = null;

$(document).ready(function(){

    $('#btnModalSave').click(function(event){
        /*ajax*/
        userData = {
                       user_name : $("#modalName").val(),
                       user_age : $("#modalAge").val(),
                       user_locale : $("#modalLocale").val()
                   };
        $.ajax({
            type: 'POST',
            url: '/user',
            data : userData,
            dataType : 'json',
            success : function(json){
                      if(json.success == "true"){
                      alert("저장 되었습니다.");
                      }
                      else{
                      alert("저장 실패!");
                      }
            },
            error : function(){
            alert("API Call Fail!");
            }
        });
    });

    $('#btnSearch').click(function(event){
        console.log("Search Click Event");
    });

});