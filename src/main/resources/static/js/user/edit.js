
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
});

function handleSubmit(event) {
    let isValid = true; // 유효성 검사 여부
    console.log('폼 제출 시작'); // 디버깅을 위한 로그

   
    // 비밀번호 확인
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('password_confirm').value;
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