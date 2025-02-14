document.addEventListener("DOMContentLoaded", function () {
    const addBtn = document.getElementById("add-btn");
    const modal = document.getElementById("modal");
    const closeBtn = document.querySelector(".close");
    const saveBtn = document.querySelector(".modal-content button[type='submit']"); // 추가버튼 > 저장버튼
	
	var deleteButton = document.querySelector('.delete-btn'); // 삭제버튼

    const itemCodeInput = document.getElementById("item-code"); // 수정된 부분
    const itemNameInput = document.getElementById("item-name");
    const itemQuantityInput = document.getElementById("item-qty");
    const itemPriceInput = document.getElementById("item-price");
    const itemDateInput = document.getElementById("item-date"); // 수정된 부분

    // 모달 열기
    addBtn.addEventListener("click", function () {
        modal.style.display = "block";
        saveBtn.textContent = "저장"; // 저장 버튼 텍스트 설정
    });

    // 모달 닫기
    closeBtn.addEventListener("click", function () {
        modal.style.display = "none";
    });

    // "저장" 버튼 클릭 시 실행
    saveBtn.addEventListener("click", function () {
		
		event.preventDefault();  // 기본 폼 제출을 막음
		
		var _csrf = document.querySelector('input[name="_csrf"]').value;
		
		
        const itemName = itemNameInput.value;
        const itemQuantity = itemQuantityInput.value;
        const itemPrice = itemPriceInput.value;

        if (saveBtn.textContent === "저장") {
            // 새 품목 추가
            const formData = new FormData();
			formData.append('_csrf', _csrf); 
			
            formData.append("name", itemName); // 변수 이름 수정
            formData.append("qty", itemQuantity); // 변수 이름 수정
            formData.append("price", itemPrice); // 변수 이름 수정

            // AJAX 요청을 사용하여 서버에 폼 데이터 전송 
			$.ajax({
				url: '/prod/add',
				method: 'POST',
				data: formData,  // 폼 데이터
	            processData: false,  // FormData를 사용할 때는 false로 설정
	            contentType: false,  // FormData를 사용할 때는 false로 설정
			}).done(data => {
				
				if (data.status === "OK") {					
					alert("추가 완료");
					window.location.href = '/prod';
				} else {					
					alert("추가 오류");					
				}
			}).fail(error => {
				console.log(error);
			});
        }
    });
	
	
	// 모든 삭제 버튼을 선택
    document.querySelectorAll('.delete-btn').forEach(function(button) {
        // 각 버튼에 클릭 이벤트 리스너 추가
        button.addEventListener('click', function() {
            // data-item-code 값 가져오기
            // 삭제 전에 확인 메시지 표시
			const isConfirmed = window.confirm('정말로 삭제하시겠습니까?');
			
			var itemCode = button.dataset.itemCode; // itemCode
			console.log(itemCode); // 데이터 값 출력 (여기서 활용 가능)
			
			var _csrf = document.querySelector('input[name="_csrf"]').value;
			var params = {itemCode, _csrf}
			console.log(params);
			
			if (isConfirmed) {
				// AJAX 요청을 사용하여 서버에 폼 데이터 전송 
				$.ajax({
					url: '/prod/delete',
					method: 'POST',
					data: params,  // itemCode, csrf
				}).done(data => {
					
					if (data.status === "OK") {					
						alert("삭제 완료");
						window.location.href = '/prod';
					} else {					
						alert("삭제 오류");					
					}
				}).fail(error => {
					console.log(error);
				});
			}
        });
    });
	
	document.getElementById("search-btn").addEventListener("click", function(e) {
	    e.preventDefault(); // 기본 폼 제출을 막습니다.
	
	    // 검색 타입과 검색어 값을 가져옵니다.
	    const searchType = document.getElementById("search-type").value;
	    const searchInput = document.getElementById("search-input").value;
		
		if(searchType === "all" && searchInput === ""){
			location.reload();
			return;
		}
		
		if(searchType === "all") {
			alert("검색할 항목을 선택해주세요");
			return;
		}
		
		if(searchInput === "") {
			alert("검색할 내용을 입력해주세요.");
			return;
		}
		
		console.log("searchType : " + searchType);
		console.log("searchInput : " + searchInput);
		
		var _csrf = document.querySelector('input[name="_csrf"]').value;
		
		var params = {searchType, searchInput, _csrf}
	
		// AJAX 요청을 사용하여 서버에 폼 데이터 전송 
		$.ajax({
			url: '/prod/search',
			method: 'POST',
			data: params,  // searchType, searchInput, csrf
		}).done(data => {
			console.log(data);
			// 검색 결과를 받아서 화면에 출력합니다.
	        const tableBody = document.querySelector("table tbody");
	        tableBody.innerHTML = ""; // 기존 테이블 내용을 비웁니다.

	        // 검색된 데이터를 테이블에 추가합니다.
	        data.forEach(prod => {
	            const row = document.createElement("tr");

	            row.innerHTML = `
	                <td>${prod.itemCode}</td>
	                <td>${prod.name}</td>
	                <td>${prod.qty}</td>
	                <td>${prod.price}</td>
	                <td>${prod.regDate}</td>
	                <td>
	                    <button class="btn btn-primary btn-sm edit-btn" data-item-code="${prod.itemCode}">
	                        <i class="fas fa-edit"></i> 수정
	                    </button>
	                    <button class="btn btn-danger btn-sm delete-btn" data-item-code="${prod.itemCode}">
	                        <i class="fas fa-trash-alt"></i> 삭제
	                    </button>
	                </td>
	            `;
	            tableBody.appendChild(row);
	        });
		}).fail(error => {
			console.log(error);
		});
	}); // 이 부분이 닫히지 않아서 추가

    // 모달 외부 클릭 시 닫기
    window.addEventListener("click", function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
	
	
	//품목관리 > 재고현황에서 수정버튼 클릭시
	document.querySelectorAll('.edit-btn').forEach(button => {
        button.addEventListener('click', function() {
            const row = button.closest('tr');
            
            // 입력 필드를 보이게 하고 텍스트 숨기기
            row.querySelector('.prod-name').style.display = 'none';
            row.querySelector('.prod-name-input').style.display = 'inline-block';
            row.querySelector('.prod-qty').style.display = 'none';
            row.querySelector('.prod-qty-input').style.display = 'inline-block';
            row.querySelector('.prod-price').style.display = 'none';
            row.querySelector('.prod-price-input').style.display = 'inline-block';
            row.querySelector('.save-btn').style.display = 'inline-block';
        });
    });

	//품목관리 > 재고현황에서 저장버튼 클릭시
    document.querySelectorAll('.save-btn').forEach(button => {
	    button.addEventListener('click', function() {
	        const row = button.closest('tr');
	        
	        // 입력 값을 받아오기
			// data-item-code 값 가져오기
			const itemCode = row.querySelector('.prod-itemCode').getAttribute('data-item-code');
	        const name = row.querySelector('.prod-name-input').value;
	        const qty = row.querySelector('.prod-qty-input').value;
	        const price = row.querySelector('.prod-price-input').value;
			
			var _csrf = document.querySelector('input[name="_csrf"]').value;					
			var params = {itemCode, name, qty, price, _csrf}
			
			console.log(params);
	
			// AJAX 요청을 사용하여 서버에 폼 데이터 전송 
			$.ajax({
				url: '/prod/update',
				method: 'POST',
				data: params,  
			}).done(data => {
				console.log("status : " + data.status);
				if (data.status === "OK") {					
					alert("수정 완료");
					window.location.href = '/prod';
				} else {
					alert("수정 오류");					
				}				
		        
			}).fail(error => {
				
			});
	
	        // 수정된 내용을 화면에 반영
	        row.querySelector('.prod-name').textContent = name;
	        row.querySelector('.prod-qty').textContent = qty;
	        row.querySelector('.prod-price').textContent = price;
	
	        // 입력 필드를 숨기고 텍스트를 보이게 함
	        row.querySelector('.prod-name').style.display = 'inline-block';
	        row.querySelector('.prod-name-input').style.display = 'none';
	        row.querySelector('.prod-qty').style.display = 'inline-block';
	        row.querySelector('.prod-qty-input').style.display = 'none';
	        row.querySelector('.prod-price').style.display = 'inline-block';
	        row.querySelector('.prod-price-input').style.display = 'none';
	        row.querySelector('.save-btn').style.display = 'none';
	    });
	});

});
	
