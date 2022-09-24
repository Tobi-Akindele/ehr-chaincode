package org.hyperledger.fabric.samples.ehr.utils;

public final class PageResult {
    private final Object data;
    private final String previousBookMark;
    private final String bookMark;

    public PageResult(final Object data, final String previousBookMark, final String bookMark) {
        this.data = data;
        this.previousBookMark = previousBookMark;
        this.bookMark = bookMark;
    }

    public Object getData() {
        return data;
    }

    public String getPreviousBookMark() {
        return previousBookMark;
    }

    public String getBookMark() {
        return bookMark;
    }
}
