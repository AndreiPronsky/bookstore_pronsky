package online.javaclass.bookstore.controller;

public class Pageable {
    private final int page;
    private final int pageSize;
    private final int limit;
    private final int offset;

    public Pageable(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        this.limit = calculateLimit();
        this.offset = calculateOffset();
    }

    private int calculateLimit() {
        return pageSize;
    }

    private int calculateOffset() {
        return pageSize * (page - 1);
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
}
