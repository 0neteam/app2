function searchEmployee() {
    var searchOption = document.getElementById("searchOption").value;
    var searchKeyword = document.getElementById("searchKeyword").value;

	// 검색어 항목이 선택된 상태이면
	if (searchOption !== "" || searchKeyword !== "") {
		if (!searchOption || !searchKeyword) {
	        alert("검색 항목과 검색어를 모두 입력해 주세요.");
	        return;
	    }
	}
    

    // 서버에 필터링된 데이터 요청 (AJAX 요청)
    var url = "/user/list?searchOption=" + searchOption + "&searchKeyword=" + encodeURIComponent(searchKeyword);

    // 페이지를 새로 고침하여 필터링된 데이터만 표시하도록 요청
    window.location.href = url;  // URL을 갱신하여 서버에 필터링된 데이터를 요청
}

// 직원 삭제 함수
function deleteEmployee(button) {
	
	var userNo = button.getAttribute('data-user-no'); // 삭제 대상 userNo	
	
    // 삭제 전에 확인 메시지 표시
    const isConfirmed = window.confirm('정말로 이 직원을 삭제하시겠습니까?');
	
	const _csrf = document.querySelector('input[name="_csrf"]').value;
		//console.log("_csrf: ", _csrf); // 이메일 값 확인

	// 사용자가 확인을 누르면 삭제 진행
    if (isConfirmed) {
		// ajax 요청
	    $.ajax({
			url: '/user/delete',  // 이메일 중복 확인 API 엔드포인트
			method: 'POST',
			contentType: 'application/x-www-form-urlencoded',  // URL 인코딩 방식으로 전송
		    data: { 
		        userNo: userNo,  // 이메일 값
		        _csrf: _csrf   // CSRF 토큰 값
		    },							
			success: function(res) {
				if (res.status === "OK") {					
					alert("삭제 완료");
					location.reload();
				} else {					
					alert("삭제 오류");
				}

			},
			error: function(xhr, status, error) {
				console.log("Error: " + error);
			}
		});
    } else {
        console.log('직원 삭제가 취소되었습니다.');
    }
}

document.addEventListener('DOMContentLoaded', function() {
	
	document.getElementById("searchOption").value = "";
	document.getElementById("searchKeyword").value = "";
	
    // 'submit' 이벤트 리스너가 한 번만 등록되도록 확인
    const signupForm2 = document.getElementById('signupForm2');
    
	// 폼 요소가 존재하는지 확인
	if (signupForm2) {
		// 기존에 등록된 이벤트 리스너가 있으면 제거
		signupForm2.removeEventListener('submit', handleSubmit);	
		// 새로운 이벤트 리스너 등록
		signupForm2.addEventListener('submit', handleSubmit);
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
    const address = document.getElementById('address').value;
    if (!address) {
        alert("우편번호 검색을 통해 우편번호와 주소를 입력해주세요.");
        isValid = false;
    }

    // 유효성 검사 실패시 폼 제출 방지
    if (!isValid) {
        console.log('폼 제출이 방지됨'); // 디버깅을 위한 로그
        event.preventDefault(); // 폼 제출 방지
    }
}