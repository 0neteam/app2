function autoHyphen(input) {
    input.value = input.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{3})(\d{4})(\d{4})$/, '$1-$2-$3');
}
$( () => {
    $("form").on("submit", (e) => {
        e.preventDefault();

        var _csrf = $("meta[name='_csrf']").attr("content");
        var params = {
            _csrf,
            quoNo: e.target.quoNo.value,
            bizNo: e.target.bizNo.value,
            transpNo: e.target.transpNo.value,
            driverName: e.target.driverName.value,
            driverPhone: e.target.driverPhone.value
        }
        console.log(params);

        $.ajax({
            url: '/InfoSave',
            method: 'POST',
            data: params
        }).done(res => {
            if (res.status) {
                alert("운수 등록이 성공 했습니다.");
                window.location.reload();
            } else {
                alert("운수 등록이 실패 했습니다.");
            }
        }).fail(error => {
            console.log(error);
        });
    });
} );