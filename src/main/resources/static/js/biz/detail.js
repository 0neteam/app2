$( () => {

    $("#searchZipcode").on("click", () => {
        // 다음 주소 API
        new daum.Postcode({
            oncomplete: function(data) {
                $("#zipCode").val(data.zonecode);
                $("#adr").val(data.roadAddress);
            }
        }).open();
        
    });

    $("#bizEdit").on("click", function() {
        $("#bizTitle").text("거래처 상세 수정");
        $("#searchZipcode").prop("disabled", false);
        $("#adrDetail").prop("readonly", false);
        $("#url1").prop("readonly", false);
        $("#url2").prop("readonly", false);
        $("#ceo").prop("readonly", false);
        $("#bizTel").prop("readonly", false);
        $("#email").prop("readonly", false);
        $("#bizFax").prop("readonly", false);
        $("#bizEdit").toggleClass("d-none");
        $("#bizSave").toggleClass("d-none");
    });

    $("#bizSave").on("click", function() {
        // 우편번호 입력 체크
        const zipcode = $("#zipCode").val();
        if (!zipcode) {
            alert("우편번호 검색을 통해 우편번호와 주소를 입력해주세요.");
            return;
        }

        // 주소 입력 체크
        const address = $("#adr").val();
        if (!address) {
            alert("우편번호 검색을 통해 우편번호와 주소를 입력해주세요.");
            return;
        }
        $("#modalCorrectionConfirm").modal("show");
    });

    $("#bizUpdate").on("click", () => {
        $("#modalCorrectionConfirm").modal("hide");
        
        $.ajax({
            url: '/biz/update',
            method: 'POST',
            data: $("form#biz").serialize(),  // 폼 데이터
        }).done(data => {
            if (data.status) {					
                alert("업데이트 완료");
            } else {					
                alert("업데이트 오류");					
            }
        }).fail(error => {
            console.log(error);
        });

    });

    $("#bizDelete").on("click", () => {
        $("#modalDeleteConfirm").modal("hide");
        let _csrf = $($("form#biz")[0]._csrf).val();
        let bizNo = $($("form#biz")[0].bizNo).val(); 
        let params = {_csrf, bizNo};
        $.ajax({
            url: '/biz/delete',
            method: 'POST',
            data: params
        }).done(data => {
            if (data.status) {					
                alert("삭제 완료");
                document.location.href = "/biz/list";
            } else {					
                alert("삭제 오류");					
            }
        }).fail(error => {
            console.log(error);
        });
    });

});