package com.java.biz;

import com.java.common.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
            String key = jwtToken.setToken(bizDTO.getBizNo() + "");
            key = key.split(" ")[1];
            List<BizApiKeyDTO> apiKeys = new ArrayList<BizApiKeyDTO>();
            apiKeys.add(BizApiKeyDTO.builder().bizNo(bizDTO.getBizNo()).type("order").url("/api/order/{status} [1:'신청', 2:'취소', 3:'확정']").key(key).build());
            apiKeys.add(BizApiKeyDTO.builder().bizNo(bizDTO.getBizNo()).type("list").url("/api/list").key(key).build());
            bizDTO.setApiKeys(apiKeys);
            
            for(BizApiKeyDTO bizApiKeyDTO : apiKeys) {
                state += bizMapper.createApi(bizApiKeyDTO);
            }
            if(state == 3) return true;
        }
        return false;
    }

    public BizDTO findByEmail(String email) {
        return bizMapper.findByEmail(email);
    }

    public int checkemail(String email) {
        return bizMapper.checkemail(email);
    }

    public int loginpwdupdate(BizDTO bizDTO) {
        return bizMapper.loginpwdupdate(bizDTO);
    }

}
