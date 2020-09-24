var userData = null;
var userId = null;
$(document).ready(function(){

        $('.exit').click(function(event){
         uncheck();
        });
        $('#btnModalDelete').click(function(event){
         callDelete();
        });


});

function pop(){
    var checkbox = $("input:checkbox[name=chk]:checked");

    var tr = checkbox.parent().parent();
    var td = tr.children();

    userId = $("input:checkbox[name=chk]:checked").attr('id');
    console.log(userId);
    var name = td.eq(1).text();
    var age = td.eq(2).text();
    var locale = td.eq(3).text();

    $('#modalName').html(name);
    $('#modalAge').html(age);
    $('#modalLocale').html(locale);

}

function callDelete(){
    console.log(userId);
    if(userId != null){
        var tempData = {
               id : userId
        };

        $.ajax({
                    type: 'DELETE',
                    url: '/user',
                    data : tempData,
                    dataType : 'json',
                    success: function(json){
                       if(json.success == "true"){
                       alert("삭제 되었습니다.");
                       location.reload();
                       }
                       else{
                       alert("삭제 실패!");
                       }
                    },
                    error : function(){
                    alert("API Call Fail!");
                    }
        });
    }
    else{
    alert("선택하신 유저가 없습니다.");
    }
}
function uncheck(){
    //input태그의 name이 chk인 태그들을 찾아서 checked옵션을 false로 정의
    $("input[name=chk]").prop("checked",false);
};
