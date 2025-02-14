$(() => {

    $("tbody tr").on("click", function(e){
        if("BUTTON" === e.target.nodeName) return;
        let bizNo = $(this).find("td").eq(0).text();
        let url = "/biz/detail?bizNo=" + bizNo;
        document.location.href = url;
    });

    $(".bizBtn").on("click", function(e) {
        let index = $(".bizBtn").index(this);
        let bizNo = $("tbody tr").eq(index).find("td").eq(0).text();
        console.log(index, bizNo);
    });

});