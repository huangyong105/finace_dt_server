package cn.com.taiji.page;

import java.io.Serializable;
import java.util.List;

/**
 * 用于分页的工具类
 */
public class PageResult<T> implements Serializable {

    /**
     * 对象记录结果集
     */
    private List<T> data;
    /**
     * 分页信息
     */
    private PageInfo pageInfo;

    public PageResult() {
    }

    public PageResult(List<T> data, PageInfo pageInfo){
        this.data = data;
        this.pageInfo = pageInfo;
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "data=" + data +
                ", pageInfo=" + pageInfo +
                '}';
    }
}
