$( () => {

    let countdownTimer; // 카운트다운 타이머 변수
    let countdownValue = 180; // 초기 카운트다운 시간 (180초)

    const startCountdown = () => {
        const countdownElement = document.getElementById('countdown'); // 카운트다운 표시할 요소
        if (countdownElement) {
            countdownElement.innerText = `인증코드 확인 (남은 시간: ${countdownValue}s)`;
        }
        countdownTimer = setInterval(function() {
            countdownValue--;
            if (countdownElement) {
                countdownElement.innerText = `인증코드 확인 (남은 시간: ${countdownValue}s)`;
            }
    
            if (countdownValue <= 0) {
                clearInterval(countdownTimer);
                countdownElement.innerText = '인증시간 만료 - 메일본인인증부터 다시 진행해주세요.';
                alert('인증시간이 만료되었습니다.');
                // 인증코드 체크값 FAIL 변경
                
            }
        }, 1000);
    }

    $(".allowOnlyLetters").on("change", (e) => {
        //let input = e.target;
        //input.value = input.value.replace(/[^a-zA-Z0-9]/g, '');
    });

    $(".select_email_domain").on("change", (e) => {
        if($(e.target).val() === "custom") {
            $("#email_domain").prop("readonly", false);
            $("#email_domain").val("");
            $("#email_domain")[0].focus();
        } else {
            $("#email_domain").prop("readonly", true);
            let domain = ($(e.target).val() === "daum") ? $(e.target).val() + ".net" : $(e.target).val() + ".com";
            $("#email_domain").val( domain );
        }
    });

    $("#check_email_btn").on("click", () => {
        const emailId = $("#email_id").val();
        const emailDomain = $("#email_domain").val();
        const email = emailId + "@" + emailDomain;

        // 이메일아이디, 도메인 입력 체크
        if(!emailId || !emailDomain) {		
            alert("이메일 아이디와 도메인을 확인하세요.");		
            return;
        }

        var _csrf = document.querySelector('input[name="_csrf"]').value;
        var params = {_csrf, email}

        $.ajax({
            url: '/biz/checkEmail',
            method: 'POST',
            data: params
        }).done(data => {
            if (data.status) {
                alert("유효한 이메일 입니다.");
                $("#email_id").prop("readonly", true);
                $("#email_domain").prop("readonly", true);
                $("#emailDomain").prop("disabled", true);
                $("#check_email_btn").prop("disabled", true);
                $("#checkAuthEmail").prop("disabled", false);
            } else {
                alert("유효하지 않은 이메일 입니다.");
            }
        }).fail(error => {
            console.log(error);
        });
    });

    $("#checkAuthEmail").on("click", () => {
        const emailId = $("#email_id").val();
        const emailDomain = $("#email_domain").val();
        const email = emailId + "@" + emailDomain;
        var _csrf = document.querySelector('input[name="_csrf"]').value;
        var params = {_csrf, email}
        $.ajax({
            url: '/biz/loginUpdateAuthCode',
            method: 'POST',
            data: params
        }).done(data => {
            $("#authMsg").text("메일로 전송된 인증코드를 입력하세요.");
            $("#authSection").removeClass("d-none");
            // 카운트다운 타이머 시작
            startCountdown();
        }).fail(error => {
            console.log(error);
        });
    });

    $("#checkAuthCode").on("click", () => {
        let authCode = $("#authCode").val();
        let _csrf = document.querySelector('input[name="_csrf"]').value;
        let params = {_csrf, authCode}
        $.ajax({
            url: '/biz/loginUpdateAuthCodeCheck',
            method: 'POST',
            data: params
        }).done(data => {
            if(data.status) {
                $("#authMsg").text("인증완료");
                $("#authSection").addClass("d-none");
                $("#checkAuthEmail").prop("disabled", true);
                $("#pwd").prop("readonly", false);
                $("#password_confirm").prop("readonly", false);
                clearInterval(countdownTimer); // 카운트다운 멈춤
            } else {
                $("#authMsg").text("인증실패");
            }
        }).fail(error => {
            console.log(error);
        });
    });

    $("#findPwdForm").on("submit", (e) => {
        e.preventDefault();  // 기본 폼 제출을 막음

        // 인증코드 인증 확인
        if($("#authMsg").text() !== "인증완료") {
            alert("이메일 승인 절차를 먼저 해주세요.")
            return;
        }

        // 비밀번호 확인
        let pwd = $("#pwd").val();
        let password_confirm = $("#password_confirm").val();
        if(pwd !== password_confirm) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        // 비밀번호 찾는 대상 이메일 확인
        const emailId = $("#email_id").val();
        const emailDomain = $("#email_domain").val();
        const email = emailId + "@" + emailDomain;

        // 신규 비밀번호 변경 요청
        let _csrf = document.querySelector('input[name="_csrf"]').value;
        let params = {_csrf, email, pwd}
        $.ajax({
            url: '/biz/loginpwdupdate',
            method: 'POST',
            data: params
        }).done(data => {
            console.log(data);
            if(data.status) {
                alert("비밀번호 변경완료");
                window.location.href = '/SignUp';
            } else {
                alert("비밀번호 변경실패");
            }
        }).fail(error => {
            console.log(error);
        });

    });

} );