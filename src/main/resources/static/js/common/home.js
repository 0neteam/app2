$( () => {
    const users = [
         {
             img: "/img/JE.png",
             name: "임지",
             style: "col-lg-4 col-md-6 col-sm-12",
             desc: `임지는 수주관리 개발을 담당하고 있습니다.<br>
                    - 수주 조회, 상회 조회<br>
                    - 견적서 작성 및 자동 전달<br>
                    - 재고 부족 시 자동 요청 취소<br>
                    - 승인 내역 리스트 및 상세 조회`
         },
         {
             img: "/img/KY.png",
             name: "경일",
             style: "col-lg-4 col-md-6 col-sm-12",
             desc: `경일은 운송관리 개발을 담당하고 있습니다.<br>
                    - 운송 요청 및 발주 요청 (메일 발송 포함)<br>
                    - 운송업체 발주 요청 및 취소<br>
                    - 운송 목록 및 기사 정보 조회<br>
                    - 운송 상태 수정 (시작 및 완료 시 재고 정보 연동)<br>
                    - 납품 수량 반영 후 재고 업데이트`
         },
         {
             img: "/img/HS.png",
             name: "효신",
             style: "col-lg-4 col-md-6 col-sm-12",
             desc: `효신은 재고 및 입고관리를 담당하고 있습니다.<br>
                    - 재고 품목 조회, 수정, 삭제<br>
                    - 신규 입고 등록 및 입고 조회<br>
                    - 거래처 정보 modal 확인, 수정, 삭제`
         },
         {
             img: "/img/JH.png",
             name: "지현",
             style: "col-lg-3 col-md-4 col-sm-6",
             desc: `지현은 인사 및 거래처정보 관리를 담당하고 있습니다.<br>
                    <b>[사원정보]</b><br>
                    - 사용자 계정 생성 (회원가입)<br>
                    - 비밀번호 찾기 및 인증메일 발송<br>
                    - 로그인, 조회, 상세보기, 수정, 삭제<br>
                    <b>[거래처정보]</b><br>
                    - 등록, 수정, 삭제, 조회, 상세보기`
         },
         {
             img: "/img/SW.png",
             name: "승우",
             style: "col-lg-3 col-md-4 col-sm-6",
             desc: `승우는 거래처 관리 개발을 담당하고 있습니다.<br>
                    - 메일로 받은 URL, KEY 정보를 거래처 정보로 등록<br>
                    - 창고 회원가입 시 거래처 정보 자동 등록<br>
                    - 사업자 등록번호 및 주소 API 연동<br>
                    - 발주 요청 URL, KEY 생성`
         },
         {
             img: "/img/DK.png",
             name: "동규",
             style: "col-lg-3 col-md-4 col-sm-6",
             desc: `동규는 품목 관리를 담당하고 있습니다.<br>
                    - 품목 등록, 수정, 삭제<br>
                    - 리스트 조회 및 검색<br>
                    - 검색항목에 따라 조회 검색<br>
                    - 품목명 코드에 따라 조회 검색`
         },
         {
             img: "/img/SI.png",
             name: "선우",
             style: "col-lg-3 col-md-4 col-sm-6",
             desc: `선우는 발주 관리를 담당하고 있습니다.<br>
                    - 제조사 및 품목 조회<br>
                    - 발주 요청 및 취소<br>
                    - 발주 내역 조회 및 상세 보기<br>
                    - 견적서 확인, 승인 및 취소<br>
                    - (최종 발주) 승인된 품목 조회<br>
                    - 물품 운반 및 배송 상태 조회<br>
                    - 최종 수령 확정`
         }
     ];

    $("#use").empty();
    users.forEach((user, index) => {
        let html = `
            <li class="${user.style}">
                <div class="user-card">
                    <div class="card p-3 shadow-sm">
                        <img src="${user.img}" alt="${user.name}" class="img-fluid rounded-circle user-img" data-index="${index}">
                        <p class="mt-3 fw-bold fs-6">${user.name}</p> <!-- fs-6으로 글씨 크기 줄임 -->
                    </div>
                    <div class="desc mt-2 p-2 bg-light rounded d-none text-start small" id="desc-${index}">
                        ${user.desc.replace(/\n/g, "<br>")}
                    </div>
                </div>
            </li>
        `;
        $("#use").append(html);
    });

     // 이미지 클릭 시 설명란 토글
     $(".user-img").on("click", function () {
         let index = $(this).data("index");
         $(`#desc-${index}`).toggleClass("d-none");
     });
 });