$( () => {
    const ajaxEvent = (status) => {
        var orderNo = $("#orderNo").val();
        var bizNo = $("#bizNo").val();
        var params = {status, orderNo, bizNo};
        $.ajax({
            url: '/quoState',
            method: 'POST',
            data: params
        }).done(data => {
			console.log(data);
            if(data.status) {
                alert(data.msg);
            } else {
                alert(data.msg);
            }
            window.location.href = "/";
        }).fail(error => {
            console.log(data);
        });
    }
    $("#btn1").on("click", () => ajaxEvent(3));
    $("#btn2").on("click", () => ajaxEvent(2));
});