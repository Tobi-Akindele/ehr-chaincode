package org.hyperledger.fabric.samples.ehr.utils;

public final class PageResult {
    private final Object data;
    private final String page;
    private final String pageSize;
    private final int total;

    public PageResult(final Object data, final String page, final String pageSize, final int total) {
        this.data = data;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public String getPage() {
        return page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public int getTotal() {
        return total;
    }
}
