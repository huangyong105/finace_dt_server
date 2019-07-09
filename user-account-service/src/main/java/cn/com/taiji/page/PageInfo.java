package cn.com.taiji.page;

import java.io.Serializable;

/**
 * 分页
 * @author LiuYong
 */
public class PageInfo implements Serializable {

    /**
     * 当前页数
     */
    private Integer currentPage;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 每页的页大小
     */
    private Integer pageSize;

    /**
     * 总记录
     */
    private Long totalCount;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                '}';
    }
}
