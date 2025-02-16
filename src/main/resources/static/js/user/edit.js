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
	
	let email = document.getElementById('email').value;
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

// 다음 주소 API
function openPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            console.log(data.zonecode); // 우편번호
            console.log(data.roadAddress); // 도로명 주소
            console.log(data.jibunAddress); // 지번 주소

            $("#zipcode").val(data.zonecode);
            $("#address").val(data.roadAddress);
        }
    }).open();
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
	
	
	const passwordField = document.getElementById('password');
    const confirmPasswordField = document.getElementById('password_confirm');
    const mailAuthSection = document.getElementById('mailauthSection');
	
	let timeoutId;  // 이벤트 반복 방지를 위한 변수

    // 메일 인증 섹션을 보이거나 숨기는 함수
    function toggleMailAuthSection() {
        // 새 비밀번호나 새 비밀번호 확인 입력란에 값이 있으면 인증 섹션을 보이게 하고, 없으면 숨기기
        if (passwordField.value.trim() !== '' || confirmPasswordField.value.trim() !== '') {
            mailAuthSection.style.display = 'block';
        } else {
            mailAuthSection.style.display = 'none';
        }
    }
	
	// 비밀번호 입력 시 이벤트가 과도하게 발생하지 않도록 설정
    function delayedToggleMailAuthSection() {
        clearTimeout(timeoutId);  // 이전 타이머를 취소
        timeoutId = setTimeout(toggleMailAuthSection, 200);  // 200ms 뒤에 toggleMailAuthSection 함수 호출
    }

	// 이벤트 리스너가 중복되지 않도록 설정
	if (!passwordField.hasEventListener) {
	    passwordField.addEventListener('input', delayedToggleMailAuthSection);
	    passwordField.hasEventListener = true;  // hasEventListener 플래그 설정
	}

	if (!confirmPasswordField.hasEventListener) {
	    confirmPasswordField.addEventListener('input', delayedToggleMailAuthSection);
	    confirmPasswordField.hasEventListener = true;  // hasEventListener 플래그 설정
	}
    // 페이지가 로드될 때 초기값을 확인하여 인증 섹션을 설정
    toggleMailAuthSection();
	
	
	
	const roleSelect = document.querySelector("select[name='selectRole']");
    const container = document.querySelector(".role-container");  // 거래처 이름을 삽입할 위치
	
	const businessSelect = document.querySelector("#businessSelectContainer");
	
	
	// 기존 거래처 bizNo 값을 찾는 함수
    function getExistingBizNo() {
        return document.querySelector('input[name="bizNo"]') ? document.querySelector('input[name="bizNo"]').value : null;
    }

    // 거래처 정보를 서버에서 AJAX로 받아오는 함수
    function getBizList() {
		return $.ajax({
		        url: '/api/bizlist',  // 서버에서 거래처 정보 가져오기
		        method: 'GET',        // HTTP 메소드 GET
		        dataType: 'json',     // 응답을 JSON 형식으로 처리
		        }).done(data => {
		            return data;  // 거래처 데이터 반환
				}).fail(error => {
					console.log(error);
					return [];
				});
		   
    }
	
	
	// 거래처 select 박스를 생성하는 함수
    function createBizSelectBox(BizList) {
		
		
		
        const bizSelectBox = document.createElement("select");
        bizSelectBox.id = "bizSelect";
        bizSelectBox.name = "bizSelect";
        bizSelectBox.classList.add("form-select");

        // 거래처 이름 목록을 select 옵션으로 추가
        BizList.forEach(function(biz) {
            const option = document.createElement("option");
            option.value = biz.bizNo;  // 거래처 ID를 값으로 설정
            option.textContent = biz.bizName;  // 거래처 이름을 옵션 텍스트로 설정
            bizSelectBox.appendChild(option);
			
			
        });

        // 거래처 이름 select 박스를 container에 추가
        container.appendChild(bizSelectBox);
		
		console.log("bizSelectBox : " + bizSelectBox);
		
		// 기존의 bizNo와 일치하는 거래처를 자동으로 선택
        const existingBizNo = getExistingBizNo();  // 기존 bizNo 가져오기
        if (existingBizNo) {
            const existingOption = bizSelectBox.querySelector(`option[value="${existingBizNo}"]`);
            if (existingOption) {
                existingOption.selected = true;  // 일치하는 거래처를 자동으로 선택
            }
        }
		
		
    }

    // 페이지 로드 시, 권한에 따라 거래처 select 박스를 생성
    const selectedRole = roleSelect ? roleSelect.value : null;
    if (selectedRole == "4") {  // 거래처 권한일 때
        // 거래처 목록을 받아와서 select 박스를 생성
		
		console.log("selectedRole : " + selectedRole);
		// 거래처 이름 선택 영역을 보이게 하기
	     businessSelect.style.display = "block";
		
        getBizList().then(BizList => {
            createBizSelectBox(BizList);
        });
    }

	
	// 사용자 권한이 변경될 때마다 처리 (거래처가 아닐시 새로그림)
    roleSelect.addEventListener("change", function() {
        const selectedRole = roleSelect.value;

        if (selectedRole == "4") {  // 거래처 권한일 때
			
			console.log("roleSelect");
			
			// 거래처 이름 선택 영역을 보이게 하기
            businessSelectContainer.style.display = "block";
				
						
            if (!document.querySelector("#bizSelect")) {  // 이미 select 박스가 있으면 다시 생성하지 않음
                // 거래처 정보를 받아오고 select 박스를 생성하는 함수
                
				getBizList().then(BizList => {
                    
					// select 박스 생성
                    const bizSelectBox = document.createElement("select");
                    bizSelectBox.id = "bizSelect";
                    bizSelectBox.name = "bizSelect";
                    bizSelectBox.classList.add("form-select");

                    // 거래처 이름 목록을 select 옵션으로 추가
                    BizList.forEach(function(biz) {
                        const option = document.createElement("option");
                        option.value = biz.bizNo;  // 거래처 ID를 값으로 설정
                        option.textContent = biz.bizName;  // 거래처 이름을 옵션 텍스트로 설정
                        bizSelectBox.appendChild(option);
                    });

                    // 거래처 이름 select 박스를 container에 추가                    
                    container.appendChild(bizSelectBox);
					
					// 기존의 bizNo와 일치하는 거래처를 자동으로 선택
	                const existingBizNo = document.querySelector('input[name="bizNo"]') ? document.querySelector('input[name="bizNo"]').value : null;

	                if (existingBizNo) {
	                    const existingOption = bizSelectBox.querySelector(`option[value="${existingBizNo}"]`);
	                    if (existingOption) {
	                        existingOption.selected = true;  // 일치하는 거래처를 자동으로 선택
	                    }
	                }
					
                });
            }
        } else {
			// "거래처" 권한이 아닌 경우 거래처 select 박스를 숨기기
	        businessSelectContainer.style.display = "none";
	        const bizSelect = document.querySelector("#bizSelect");
	        if (bizSelect) {
	            bizSelect.remove();  // 기존 select 박스를 삭제
	        }
        }
    });
	
});

function handleSubmit(event) {
    let isValid = true; // 유효성 검사 여부
    console.log('폼 제출 시작'); // 디버깅을 위한 로그

   
	// 비밀번호 확인
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('password_confirm').value;
		
	// 인증코드 인증 확인
    const email_codeauth_chk = document.getElementById('email_codeauth_chk').value;
	if(password !== "" || passwordConfirm !== ""){
	    if (email_codeauth_chk !== "OK") {
	        alert("메일 본인인증을 완료해주세요.");
	        isValid = false;
	    }
	}	
    
	// 비밀번호가 비어있지 않은경우 비밀번호 확인 체크
	if(password !== "" || passwordConfirm !== ""){
	    if (password !== passwordConfirm) {
	        alert("비밀번호가 일치하지 않습니다.");
	        isValid = false;
	    }
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
    const address = document.getElementById('address').value;
    if (!address) {
        alert("우편번호 검색을 통해 우편번호와 주소를 입력해주세요.");
        isValid = false;
    }
	
	// 거래처 선택 체크
    const roleSelect = document.querySelector("select[name='selectRole']");
    const selectedRole = roleSelect ? roleSelect.value : null;
    
    if (selectedRole === "4") {  // 거래처 권한일 때
        const bizSelect = document.querySelector("#bizSelect");
        if (bizSelect && !bizSelect.value) {
            alert("거래처를 선택해주세요.");
            isValid = false;
        }
    }

    // 유효성 검사 실패시 폼 제출 방지
    if (!isValid) {
        console.log('폼 제출이 방지됨'); // 디버깅을 위한 로그
        event.preventDefault(); // 폼 제출 방지
	} else {
        // Ajax로 폼 제출
        event.preventDefault();  // 기본 폼 제출을 막음

		//var _csrf = document.querySelector('input[name="_csrf"]').value;
        var formData = new FormData(event.target);  // FormData 객체를 사용하여 폼 데이터 수집		
		//formData.append('_csrf', _csrf);  // CSRF 토큰을 formData에 추가

		// 업데이트 전에 확인 메시지 표시
		const isConfirmed = window.confirm('정말로 입력한 정보대로 업데이트 하시겠습니까?');
			
		if (isConfirmed) {
	        // Ajax 요청
			$.ajax({
				url: '/user/update',
				method: 'POST',
				data: formData,  // 폼 데이터
	            processData: false,  // FormData를 사용할 때는 false로 설정
	            contentType: false,  // FormData를 사용할 때는 false로 설정
			}).done(data => {
				if (data.status === "OK") {					
					alert("업데이트 완료");
					window.location.href = '/user/detail?userNo=' + data.userNo;
				} else {					
					alert("업데이트 오류");					
				}
			}).fail(error => {
				console.log(error);
			});
		}
    }
}