package online.javaclass.bookstore.controller.utils;

import online.javaclass.bookstore.service.dto.PageableDto;
import org.springframework.stereotype.Component;

@Component
public class PagingUtil {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;

    public PageableDto getPageable(String pageNum, String pageSize) {
        return new PageableDto(getPage(pageNum), getPageSize(pageSize));
    }

    public Long getTotalPages(Long totalItems, PageableDto pageable) {
        Long pages = totalItems / pageable.getPageSize();
        if (totalItems % pageable.getPageSize() != 0) {
            pages++;
        }
        return pages;
    }

    private int getPage(String pageNum) {
        try {
            return Integer.parseInt(pageNum);
        } catch (NullPointerException | NumberFormatException e) {
            return DEFAULT_PAGE;
        }
    }

    private int getPageSize(String pageSize) {
        try {
            return Integer.parseInt(pageSize);
        } catch (NullPointerException | NumberFormatException e) {
            return DEFAULT_PAGE_SIZE;
        }
    }
}
