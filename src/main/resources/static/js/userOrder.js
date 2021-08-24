$(function(){
    $(".allChoose").click(function() {
        if(this.checked==true){
            $(".chooseBtn").prop("checked",true);
        }else{
            $(".chooseBtn").prop("checked",false);
        }
        allPrice();
    })
    $(".chooseBtn").click(function(){
        var check=$(".chooseBtn").length;
        var checked=$(".chooseBtn:checked").length;
        if(check==checked){
            $(".allChoose").prop("checked",true);
        }else{
            $(".allChoose").prop("checked",false);
        }
        allPrice();
    })
    $(".count_d").click(function(){
        var c = parseInt($(this).siblings(".c_num").children().val());
        if(c <= 1){
            $(this).attr("disabled","disabled");
        }else{
            c--;
            $(this).siblings(".c_num").children().val(c);

            var d = $(this).parents(".number").prev().text();
            $(this).parents(".number").siblings(".One").children().val((Number(c)*d).toFixed(2));
        }
        var num=c;
        var id=$(this).parents(".number").siblings(".id").text();
        var url = "/numupdate?count="+c+"&id="+id;
        window.location.href=encodeURI(url);
    })
    $(".count_i").click(function(){
        var c = parseInt($(this).siblings(".c_num").children().val());
        if(c >= 5){
            $(this).attr("disabled","disabled");
        }else{
            c++;
            $(this).siblings(".c_num").children().val(c);

            var d = $(this).parents(".number").prev().text();
            $(this).parents(".number").siblings(".One").children().val((Number(c)*d).toFixed(2));
        }
        var num=c;
        var id=$(this).parents(".number").siblings(".id").text();
        var url = "/numupdate?count="+c+"&id="+id;
        window.location.href=encodeURI(url);

    })
    function allPrice(){
        var num=0;
        $(".chooseBtn").each(function(){
            if(this.checked == true){
                var itemprice= $(this).parent().siblings(".One").children().val();
                num+=Number(itemprice);
            }
        });
        $(".totalprice").text("￥" + num)
    }


})
function toorder(){
    var str="";
    var count=0;
    $("input[class='chooseBtn']:checked").each(function(index,item){
        if($("input[class='chooseBtn']:checked").length-1 == index){
            str+=$(this).val();
        }else{
            str+=$(this).val()+",";
        }

    })
    $(".chooseBtn").each(function () {
        if (this.checked == true) {
            count++;
        }
    })

    if (count == 0) {
        alert("请选择产品!");
    }else{
        window.location.href="/orderselect?cartidlist="+str;
    }

}

