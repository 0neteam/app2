$(() => {

    $("#category").on("change", () => {
        // SELECT 기능
        var index = $("#category option").index( $("#category option:selected") );
        const categorys = [
			'검색어를 입력하세요', 
			'숫자를 입력하세요', 
			'업체명을 입력하세요', 
			'ex)yyyymmdd', 
			'ex)yyyymmdd', 
			'상태값을 입력하세요'];
        $("input[name='search']").attr("placeholder", categorys[index]);
    });

    $("tbody tr").on("click", function() {
        var quoNo = $(this).attr("data-quoNo");
        var _csrf = document.querySelector('input[name="_csrf"]').value;
        var params = {quoNo, _csrf};
        console.log(params);

        $.ajax({
            url: '/quo',
            method: 'POST',
            data: params
        }).done(data => {
			///////////////////////////
			//$("#supplierModalLabel").text(`수주 상세정보 - ${data.quoNo}`);
			//let tbody = $("#supplierModal tbody");
            //tbody.empty();
			//data.items.forEach((item, index) => {
                //let row = `
                    //<tr>
                        //<td>${index + 1}</td>
                       // <td>${item.itemCode}</td>
                      //  <td>${item.name}</td>
                     //   <td>${item.qty}</td>
                  //      <td>${item.price}</td>
                //        <td>${item.qty * item.price}</td>
                        
              //      </tr>
			//	`;
       //         tbody.append(row);
       //     });
		//	$("#supplierModal tfoot th:nth-child(2)").text(data.deliDate);  // 납기일
		//    let totalAmount = data.items.reduce((sum, item) => sum + item.totalPrice, 0); // 총액 계산
	//	    $("#supplierModal tfoot th:nth-child(5)").text(totalAmount);  // 총액
			////////////////////////////////////
            console.log(data);
            // MODAL 기능
            $("#supplierModal").modal("show");
        }).fail(error => {
            console.log(error);
        });

    });

});