package com.zzjz.zzjg.bean;

/**
 * @author 房桂堂
 * @description BaseRequest
 * @date 2019/7/5 10:47
 */
public class BaseRequest {

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 分页实体
     */
    private PagingEntity paging;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public PagingEntity getPaging() {
        return paging;
    }

    public void setPaging(PagingEntity paging) {
        this.paging = paging;
    }
}
