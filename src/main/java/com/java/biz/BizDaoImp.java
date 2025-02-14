package com.java.biz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BizDaoImp implements BizDao {

    private final BizMapper bizMapper;

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
                state += bizMapper.createApi(bizApiKeyDTO);
            }
            if(state == 3) return true;
        }
        return false;
    }

}
