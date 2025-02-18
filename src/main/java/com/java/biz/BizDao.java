package com.java.biz;
import java.util.List;

public interface BizDao {
    public List<BizDTO> findList(BizReqDTO bizReqDTO);

    BizDTO findOne(int no);

    boolean update(BizDTO bizDTO);
    boolean delete(int bizNo);
    boolean create(BizDTO bizDTO);

    public BizDTO findByEmail(String email);

    public int checkemail(String email);
    public int loginpwdupdate(BizDTO bizDTO);

}
