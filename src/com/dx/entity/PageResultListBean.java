package com.dx.entity;

/**
 * Created by Tom on 16/6/26.
 */
public class PageResultListBean extends ResultListBean{
    int pageCount=0;

    int count=0;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PageResultListBean{" +
                "pageCount=" + pageCount +
                ", count=" + count +
                "} " + super.toString();
    }
}
