package com.roncoo.eshop.page;



import java.util.List;

/**
 * @author chenm
 * @create 2017-11-28 上午10:46
 * @desc
 **/
public class PageInfoDTO extends PageInfo {

    public PageInfoDTO(Integer currentPage,Integer pageSize){
        this.setCurrentPage(currentPage);
        this.setPageSize(pageSize);
        this.setTotalCount(0L);
        this.setTotalPage(0);
    }


    public <T> void  setPageInfoData(List<T> list){
        com.github.pagehelper.PageInfo pageInfo = new com.github.pagehelper.PageInfo(list);
        this.setCurrentPage(pageInfo.getPageNum());
        this.setPageSize(pageInfo.getPageSize());
        this.setTotalPage(pageInfo.getPages());
        this.setTotalCount(pageInfo.getTotal());
    }
}
