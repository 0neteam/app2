package com.java.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class BizDTO {

    private int bizNo;
    private String bizNum;
    private String bizName;
    private String bizTel;
    private String bizFax;
    private String email;
    private String ceo;
    private String bizType;
    private String zipCode;
    private String adr;
    private String adrDetail;
    private char useYN;
    private String regDate;

    private List<BizApiKeyDTO> apiKeys;

    public static BizDTO setBizDTO(Map<String, String> paramMap) {
        BizDTO bizDTO = BizDTO.builder().build();
        List<BizApiKeyDTO> apiKeys = new ArrayList<BizApiKeyDTO>();
        if(paramMap != null) {
            if(paramMap.get("bizNo") != null){
                try {
                    bizDTO.setBizNo(Integer.parseInt(paramMap.get("bizNo")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            for(String key : paramMap.keySet()) {
                //System.out.println("key : " + key + ", value : " + paramMap.get(key));
                
                if("zipCode".equals(key)) {
                    bizDTO.setZipCode(paramMap.get(key));
                }
                if("adr".equals(key)) {
                    bizDTO.setAdr(paramMap.get(key));
                }
                if("adrDetail".equals(key)) {
                    bizDTO.setAdrDetail(paramMap.get(key));
                }
                if("ceo".equals(key)) {
                    bizDTO.setCeo(paramMap.get(key));
                }
                if("bizTel".equals(key)) {
                    bizDTO.setBizTel(paramMap.get(key));
                }
                if("email".equals(key)) {
                    bizDTO.setEmail(paramMap.get(key));
                }

                
                if("url1".equals(key)) {
                    apiKeys.add(BizApiKeyDTO.builder().bizNo(bizDTO.getBizNo()).type("order").url(paramMap.get(key)).build());
                }
                if("url2".equals(key)) {
                    apiKeys.add(BizApiKeyDTO.builder().bizNo(bizDTO.getBizNo()).type("list").url(paramMap.get(key)).build());
                }
            }
        }
        bizDTO.setApiKeys(apiKeys);
        return bizDTO;
    }

    public static BizDTO setClientDTO(Map<String, String> paramMap) {
        BizDTO bizDTO = BizDTO.builder().build();
        List<BizApiKeyDTO> apiKeys = new ArrayList<BizApiKeyDTO>();
        if(paramMap != null) {
            String orderUrl = null, productUrl = null, orderKey = null, productKey = null;
            for(String key : paramMap.keySet()) {
                // Client info
                if("bizNum".equals(key)) {
                    bizDTO.setBizNum(paramMap.get(key));
                }
                if("bizName".equals(key)) {
                    bizDTO.setBizName(paramMap.get(key));
                }
                if("bizTel".equals(key)) {
                    bizDTO.setBizTel(paramMap.get(key));
                }
                if("bizFax".equals(key)) {
                    bizDTO.setBizFax(paramMap.get(key));
                }
                if("email".equals(key)) {
                    bizDTO.setEmail(paramMap.get(key));
                }
                if("ceo".equals(key)) {
                    bizDTO.setCeo(paramMap.get(key));
                }
                if("bizType".equals(key)) {
                    bizDTO.setBizType(paramMap.get(key));
                }
                if("zipCode".equals(key)) {
                    bizDTO.setZipCode(paramMap.get(key));
                }
                if("adr".equals(key)) {
                    bizDTO.setAdr(paramMap.get(key));
                }
                if("adrDetail".equals(key)) {
                    bizDTO.setAdrDetail(paramMap.get(key));
                }

                // API & KEY
                if("orderUrl".equals(key)) {
                    orderUrl = paramMap.get(key);
                }
                if("productUrl".equals(key)) {
                    productUrl = paramMap.get(key);
                }
                if("orderKey".equals(key)) {
                    orderKey = paramMap.get(key);
                }
                if("productKey".equals(key)) {
                    productKey = paramMap.get(key);
                }
            }

            apiKeys.add(BizApiKeyDTO.builder().bizNo(bizDTO.getBizNo()).type("order").url(orderUrl).key(orderKey).build());
            apiKeys.add(BizApiKeyDTO.builder().bizNo(bizDTO.getBizNo()).type("list").url(productUrl).key(productKey).build());
        }
        bizDTO.setApiKeys(apiKeys);
        return bizDTO;
    }
}
