package online.javaclass.bookstore.service.dto;

public class PageableDto {
    private final int page;
    private final int pageSize;
    private final int offset;

    private Long totalItems;

    private Long totalPages;

    public PageableDto(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        this.offset = calculateOffset();
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
        return pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }
}
