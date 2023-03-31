package online.javaclass.bookstore.service.dto;

public class PageableDto {
    private Integer page;
    private Integer pageSize;
    private int offset;

    private Long totalItems;

    private Long totalPages;

    public PageableDto(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    private int calculateOffset() {
        return pageSize * (page - 1);
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getLimit() {
        return pageSize;
    }

    public int getOffset() {
        return calculateOffset();
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

    public void setPage(int page) {
        this.page = page;
    }
}
