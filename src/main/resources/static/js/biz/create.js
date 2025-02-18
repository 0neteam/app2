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

    $("#searchBizNum").on("click", () => {
        //https://jongs-story.tistory.com/entry/jQuery-공공데이터-포털-api를-사용해서-사업자-등록-정보-확인하는-방법-api-사용방법-사업자번호-조회
        $("#bizNum").val($("#bizNum").val().replace(/[^0-9]/g, ""));
        reg_num = $("#bizNum").val();
        if(!reg_num){
            alert("사업자등록번호를 입력해주세요.");
            return false;
        }

        // 사업자 등록번호를 포함하는 JSON 객체 생성
        const data = {
            b_no: [reg_num]
        };

        $.ajax({
            url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=FRwmNG97jOhkLCeTd1P4fHx%2B5nf8Dx6SfFQV%2BBAMuc873IjQux3Fe6gC134S%2Fjdgs1XziXTjYfXe0qYeb9IvFQ%3D%3D",  // serviceKey 값을 xxxxxx에 입력
            type: "POST",
            data: JSON.stringify(data), // json 을 string으로 변환하여 전송
            dataType: "JSON",
            traditional: true,
            contentType: "application/json; charset:UTF-8",
            accept: "application/json",
            success: function(result) {
                //console.log(result);
                if(result.match_cnt === 1) {
                    $("#bizNum").prop("readonly", true);
                }
                alert(result.data[0]["tax_type"]);
                /*
                if(result.match_cnt === 1) {
                    //성공
                    console.log("success");
                    alert(result.data[0]["tax_type"]);
                } else {
                    //실패
                    console.log("fail");
                }
                    */
            },
            error: function(result) {
                console.log("error");
                console.log(result.responseText); //responseText의 에러메세지 확인
            }
        });


    })

    $("form").on("submit", function(e) {
        e.preventDefault();

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

        // 비밀번호 입력 체크
        if($("#pwd").val() !== $("#password_confirm").val()) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        $("#modalCorrectionConfirm").modal("show");
    });

    $("#bizCreate").on("click", () => {
        $("#modalCorrectionConfirm").modal("hide");
        
        $.ajax({
            url: '/biz/create',
            method: 'POST',
            data: $("form#createBiz").serialize(),  // 폼 데이터
        }).done(data => {
            if (data.status) {					
                alert("등록 완료");
                document.location.href = "/biz/list";
            } else {					
                alert("등록 오류");					
            }
        }).fail(error => {
            console.log(error);
        });

    });

});