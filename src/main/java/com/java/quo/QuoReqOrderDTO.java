package com.java.quo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuoReqOrderDTO {
    

    private int orderNo;
    private QuoOrderDTO quoOrderDTO;
    private List<QuoOrderItemDTO> quoOrderItem;

}
