// Select 박스 도메인 선택 시 이메일 입력 필드 업데이트
function select_email_domain() {
    const selectedDomain = document.getElementById("emailDomain").value; // Select 박스 엘레멘트 값
    const customDomainInput = document.getElementById("email_domain");  //  도메인 input 엘레멘트
	
	document.getElementById('email_exist_chk').value = 'WAIT'; // 도메인 변경시 - 중복체크 : 대기 상태로 변경  

    if (selectedDomain === "custom") {
        customDomainInput.removeAttribute('readonly');   
        customDomainInput.value="";
        customDomainInput.focus();
    } else {
		customDomainInput.setAttribute('readonly', 'true');   
        if(selectedDomain == "daum"){
            customDomainInput.value = selectedDomain + ".net";
        }
        else {
            customDomainInput.value = selectedDomain + ".com";
        }
        
    }
}

// 이메일 아이디 입력 or 도메인 입력시 영문 숫자만 허용하고 중복체크 : 대기 상태로 변경  
function allowOnlyLetters(event) {
	
	document.getElementById('email_exist_chk').value = 'WAIT'; // 도메인 변경시 - 가입확인필요 : 대기 상태로 변경    	
    // 입력값을 가져와서 영문자(A-Z, a-z)만 허용하도록 필터링
    let input = event.target;
    input.value = input.value.replace(/[^a-zA-Z0-9]/g, '');  // 영문자와 숫자 외의 모든 문자 제거	
}

// 이메일 아이디 입력 or 도메인 입력시 영문 숫자만 허용하고 중복체크 : 대기 상태로 변경  
function allowOnlyLettersAuthCode(event) {
	
	document.getElementById('email_codeauth_chk').value = 'WAIT'; // 도메인 변경시 - 가입확인필요 : 대기 상태로 변경    	
    // 입력값을 가져와서 영문자(A-Z, a-z)만 허용하도록 필터링
    let input = event.target;
    input.value = input.value.replace(/[^a-zA-Z0-9]/g, '');  // 영문자와 숫자 외의 모든 문자 제거	
}


let countdownTimer; // 카운트다운 타이머 변수
let countdownValue = 60; // 초기 카운트다운 시간 (60초)

// 카운트다운 시작
function startCountdown() {
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
			document.getElementById('email_codeauth_chk').value = "FAIL";
           
        }
    }, 1000);
}


//이메일 가입여부 체크 함수
function checkExistEmail() {
    var emailId = document.getElementById('email_id').value;
    var emailDomain = document.getElementById('email_domain').value;
    var email = emailId + "@" + emailDomain;  // 이메일 전체를 결합
	
	// 이메일아이디, 도메인 입력 체크
	if(!emailId || !emailDomain) {		
		alert("이메일 아이디와 도메인을 확인하세요.");		
		return;
	}

	var _csrf = document.querySelector('input[name="_csrf"]').value;
    var params = {email, _csrf}
    console.log(params);

    $.ajax({
        url: '/user/create/checkemail',
        method: 'POST',
        data: params
    }).done(data => {
		if (data.status === "OK") {
            $('#email_exist_chk').val('FAIL');  // email_exist_chk 아이디 값을 가지는 hidden 엘리먼트 - 상태값 관리 : OK()
            alert("해당 메일은 존재하지 않습니다.");
        } else {
            $('#email_exist_chk').val('OK');
            alert("메일가입 확인 완료");
        }
    }).fail(error => {
        console.log(error);
    });

}


//메일본인인증버튼 시작 클릭시
function checkAuthEmail() {	
	
	clearInterval(countdownTimer); // 이전 카운트다운 멈춤
	
	countdownValue = 60;
	
	// authMsg 
	let authMsg = document.getElementById('authMsg');
	
	// authMsg 초기 입력
	authMsg.innerText = '인증을 통해 본인확인을 완료해주세요.';
	authMsg.style.color = 'black';
	authMsg.style.fontWeight = 'normal';
	
	// 본인인증메일 확인전에 가입된 이메일 먼저 확인
    const email_exist_chk = document.getElementById('email_exist_chk').value;
    if (email_exist_chk !== "OK") {
        alert("가입메일 체크를 완료해주세요.");
        isValid = false;
		return;
    }
	
	// 전코드에서 가입된 이메일 확인하고 아래와같이 메일주소를 키값으로 인증코드 입력 요청
	var emailId = document.getElementById('email_id').value;
    var emailDomain = document.getElementById('email_domain').value;
    var email = emailId + "@" + emailDomain;  // 이메일 전체를 결합
		
	
	var _csrf = document.querySelector('input[name="_csrf"]').value;
    var params = {email, _csrf}
    console.log(params);

    $.ajax({
        url: '/user/loginUpdateAuthCode',
        method: 'POST',
        data: params
    }).done(data => {
		if (data.status === "OK") {
            $('#email_codeserverupdate_chk').val('OK');  // email_codeauth_chk 아이디 값을 가지는 hidden 엘리먼트 - 상태값 관리 : OK(인증성공)
            //alert("서버 인증코드 업데이트 완료");
			authMsg.innerText = '메일로 전송된 인증코드를 입력하세요. ';
			
			
			// 인증코드 입력창 & 확인 버튼 보이기
		    document.getElementById('authSection').style.display = 'flex';	
			
			// 카운트다운 타이머 시작
            startCountdown();
			
        } else {
            $('#email_codeserverupdate_chk').val('FAIL');
            alert("인증 인증코드 업데이트 실패");
			authMsg.innerText = '서버 요청실패 관리자에게 문의해주세요. ';
        }
    }).fail(error => {
        console.log(error);
    });

	
}

//인증코드 확인
function checkAuthCode() {
	
	
	// authMsg 
	let authMsg = document.getElementById('authMsg'); // 인증 메시지
	let countdown = document.getElementById('countdown'); // 카운트다운 span 요소
	
		
    var authCode = document.getElementById('authCode').value; // 인증코드 입력부분
    console.log("입력한 인증번호:", authCode);
	
	var _csrf = document.querySelector('input[name="_csrf"]').value;
    var params = {authCode, _csrf}
    console.log(params);

    $.ajax({
        url: '/user/loginUpdateAuthCodeCheck',
        method: 'POST',
        data: params
    }).done(data => {
		if (data.status === "OK") {
            $('#email_codeauth_chk').val('OK');  // email_codeauth_chk 아이디 값을 가지는 hidden 엘리먼트 - 상태값 관리 : OK(인증성공)
            alert("인증 완료");
			
			// 텍스트 색상 변경 (빨간색)
			authMsg.style.color = 'green';
			authMsg.style.fontWeight = 'bold';
			authMsg.innerText = '인증완료';
			clearInterval(countdownTimer); // 인증 완료 후 카운트다운 멈춤
			countdown.innerHTML = '';
			
			// 인증코드 입력창 & 확인 버튼 숨기기
			document.getElementById('authSection').style.display = 'none';
			
			// 인증코드 체크값 OK 변경
			document.getElementById('email_codeauth_chk').value = "OK";
			

        } else {
            $('#email_codeauth_chk').val('FAIL');
            alert("인증 실패");
			
			// 텍스트 색상 변경 (빨간색)
			authMsg.style.color = 'red';
			authMsg.style.fontWeight = 'normal';
			authMsg.innerText = '인증에 실패 - 인증코드를 확인하세요 ';
			
			// 인증코드 체크값 FAIL 변경
			document.getElementById('email_codeauth_chk').value = "FAIL";
        }
    }).fail(error => {
        console.log(error);
    });

}



document.addEventListener('DOMContentLoaded', function() {
    // 'submit' 이벤트 리스너가 한 번만 등록되도록 확인
    const signupForm3 = document.getElementById('signupForm3');
    
	// 폼 요소가 존재하는지 확인
    if (signupForm3) {
		// 기존에 등록된 이벤트 리스너가 있으면 제거
		signupForm3.removeEventListener('submit', handleSubmit);	
		// 새로운 이벤트 리스너 등록
		signupForm3.addEventListener('submit', handleSubmit);
	}
	
	
});

function handleSubmit(event) {
    event.preventDefault();  // 기본 폼 제출을 막음
    let isValid = true; // 유효성 검사 여부
    console.log('폼 제출 시작'); // 디버깅을 위한 로그

    // 이메일 가입여부 확인
    const email_exist_chk = document.getElementById('email_exist_chk').value;
    if (email_exist_chk !== "OK") {
        alert("이메일 가입여부를 확인해주세요.");
        isValid = false;
    }
	
	// 인증코드 인증 확인
    const email_codeauth_chk = document.getElementById('email_codeauth_chk').value;
    if (email_codeauth_chk !== "OK") {
        alert("메일 본인인증을 완료해주세요.");
        isValid = false;
    }

    // 이메일 형식 체크
    const emailId = document.getElementById('email_id').value;
    const emailDomain = document.getElementById('email_domain').value;
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (!emailId || !emailDomain || !emailRegex.test(emailId + "@" + emailDomain)) {
        alert("유효한 이메일 주소를 입력해주세요.");
        isValid = false;
    }
	else {
		email.value= emailId + "@" + emailDomain; // 이메일 검토가 완료되면 전체 이메일 주소를 hidden name = email 로 담아 전송
	}    

    // 비밀번호 확인
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('password_confirm').value;
    if (password !== passwordConfirm) {
        alert("비밀번호가 일치하지 않습니다.");
        isValid = false;
    }
   

    // 유효성 검사 실패시 폼 제출 방지
    if (!isValid) {
        console.log('폼 제출이 방지됨'); // 디버깅을 위한 로그
	} else {
        // Ajax로 폼 제출
		//var _csrf = document.querySelector('input[name="_csrf"]').value;
        var formData = new FormData(event.target);  // FormData 객체를 사용하여 폼 데이터 수집		
		//formData.append('_csrf', _csrf);  // CSRF 토큰을 formData에 추가

		// 업데이트 전에 확인 메시지 표시
		const isConfirmed = window.confirm('비밀번호 변경을 완료하시겠습니까?');
			
		if (isConfirmed) {
	        // Ajax 요청
			$.ajax({
				url: '/user/loginpwdupdate',
				method: 'POST',
				data: formData,  // 폼 데이터
	            processData: false,  // FormData를 사용할 때는 false로 설정
	            contentType: false,  // FormData를 사용할 때는 false로 설정
			}).done(data => {
				if (data.status === "OK") {					
					alert("비밀번호 변경완료");
					window.location.href = '/SignUp';
				} else {					
					alert("비밀번호 변경실패");					
				}
			}).fail(error => {
				console.log(error);
			});
		}
    }
}