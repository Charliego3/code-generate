package io.github.nzlong.generate.entity;

import java.util.List;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 01 上午11:36
 */
public class PageVO<T> {

    private Integer pageIndex;
    private Integer pageSize;
    private Integer totalCount;
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public PageVO setData(List<T> data) {
        this.data = data;
        return this;
    }

    public PageVO() {
    }

    public PageVO(int pageIndex, int pageSize) {
        if (pageIndex <= 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public PageVO(Integer pageIndex, Integer pageSize, Integer totalCount) {
        new PageVO(pageIndex, pageSize);
        this.totalCount = totalCount;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "PageVO{" + "pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", totalCount=" + totalCount + '}';
    }

}
