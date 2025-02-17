package com.java.biz;

import com.java.common.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BizDaoImp implements BizDao {

    private final BizMapper bizMapper;
    private final JwtToken jwtToken;

    @Override
    public List<BizDTO> findList(BizReqDTO bizReqDTO) {
        return bizMapper.findList(bizReqDTO);
    }

    @Override
    public BizDTO findOne(int no) {
        BizDTO bizDTO = bizMapper.findOne(no);
        bizDTO.setApiKeys(bizMapper.findByApiKey(no));
        return bizDTO;
    }

    public boolean update(BizDTO bizDTO) {
        int state = bizMapper.updateBiz(bizDTO);
        if(state == 1) {
            for(BizApiKeyDTO bizApiKeyDTO : bizDTO.getApiKeys()) {
                state += bizMapper.updateApi(bizApiKeyDTO);
            }
            if(state == 3) return true;
        }
        return false;
    }

    public boolean delete(int bizNo) {
        int state = bizMapper.deleteBiz(bizNo);
        state += bizMapper.deleteApi(bizNo);
        if(state == 3) return true;
        return false;
    }

    public boolean create(BizDTO bizDTO) {
        int state = bizMapper.createBiz(bizDTO);
        if(state == 1) {
            for(BizApiKeyDTO bizApiKeyDTO : bizDTO.getApiKeys()) {
                bizApiKeyDTO.setBizNo(bizDTO.getBizNo());


                String key = Integer.toString(bizApiKeyDTO.getBizNo());
                key= jwtToken.setToken(key);
                System.out.println("자르기전"+key);
                key = key.substring(7);
                System.out.println("자른이후"+key);
                String url = "/quo/order/"+key; //url 생성
                bizApiKeyDTO.setKey(key);
                bizApiKeyDTO.setUrl(url);


                state += bizMapper.createApi(bizApiKeyDTO);
            }
            if(state == 3) return true;
        }
        return false;
    }

}
