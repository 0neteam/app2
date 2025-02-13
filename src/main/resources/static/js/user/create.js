// Select 박스 도메인 선택 시 이메일 입력 필드 업데이트
function select_email_domain() {
    const selectedDomain = document.getElementById("emailDomain").value; // Select 박스 엘레멘트 값
    const customDomainInput = document.getElementById("email_domain");  //  도메인 input 엘레멘트
	
	document.getElementById('email_duple_chk').value = 'WAIT'; // 도메인 변경시 - 중복체크 : 대기 상태로 변경  

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
	
	document.getElementById('email_duple_chk').value = 'WAIT'; // 도메인 변경시 - 중복체크 : 대기 상태로 변경    	
    // 입력값을 가져와서 영문자(A-Z, a-z)만 허용하도록 필터링
    let input = event.target;
    input.value = input.value.replace(/[^a-zA-Z0-9]/g, '');  // 영문자와 숫자 외의 모든 문자 제거	
}


// 다음 주소 API
function openPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            console.log(data.zonecode); // 우편번호
            console.log(data.roadAddress); // 도로명 주소
            console.log(data.jibunAddress); // 지번 주소

            $("#zipcode").val(data.zonecode);
            $("#adr").val(data.roadAddress);
        }
    }).open();
}


//이메일 중복 체크 함수
function checkDuplicateEmail() {
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
            $('#email_duple_chk').val('OK');  // email_duple_chk 아이디 값을 가지는 hidden 엘리먼트 - 상태값 관리 : WAIT(중복확인대기), OK(중복확인완료), FAIL(중복된상태)
            alert("이메일 사용 가능합니다.");
        } else {
            $('#email_duple_chk').val('FAIL');
            alert("이미 사용 중인 이메일입니다.");
        }
    }).fail(error => {
        console.log(error);
    });

}




document.addEventListener('DOMContentLoaded', function() {
    // 'submit' 이벤트 리스너가 한 번만 등록되도록 확인
    const signupForm = document.getElementById('signupForm');
    
	// 폼 요소가 존재하는지 확인
    if (signupForm) {
		// 기존에 등록된 이벤트 리스너가 있으면 제거
		signupForm.removeEventListener('submit', handleSubmit);	
		// 새로운 이벤트 리스너 등록
		signupForm.addEventListener('submit', handleSubmit);
	}
});

function handleSubmit(event) {
    let isValid = true; // 유효성 검사 여부
    console.log('폼 제출 시작'); // 디버깅을 위한 로그

    // 이메일 중복 확인
    const email_du_chk = document.getElementById('email_duple_chk').value;
    if (email_du_chk !== "OK") {
        alert("이메일 중복을 확인해주세요.");
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

    // 이름 입력 여부 체크
    const name = document.getElementById('username').value;
    if (!name) {
        alert("이름을 입력해주세요.");
        isValid = false;
    }

    // 비밀번호 확인
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('password_confirm').value;
    if (password !== passwordConfirm) {
        alert("비밀번호가 일치하지 않습니다.");
        isValid = false;
    }

    // 전화번호 형식 체크
    const phone = document.getElementById('phone').value;
    const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;
    if (!phone || !phoneRegex.test(phone)) {
        alert("유효한 핸드폰 번호를 입력해주세요.");
        isValid = false;
    }

    // 우편번호 입력 체크
    const zipcode = document.getElementById('zipcode').value;
    if (!zipcode) {
        alert("우편번호 검색을 통해 우편번호와 주소를 입력해주세요.");
        isValid = false;
    }

    // 주소 입력 체크
    const adr = document.getElementById('adr').value;
    if (!adr) {
        alert("우편번호 검색을 통해 우편번호와 주소를 입력해주세요.");
        isValid = false;
    }

    // 유효성 검사 실패시 폼 제출 방지
    if (!isValid) {
        console.log('폼 제출이 방지됨'); // 디버깅을 위한 로그
        event.preventDefault(); // 폼 제출 방지
    }
}