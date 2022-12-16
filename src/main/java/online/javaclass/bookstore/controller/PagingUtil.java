package online.javaclass.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.service.dto.PageableDto;

public class PagingUtil {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;

    public static PageableDto getPageable(HttpServletRequest req) {
        return new PageableDto(getPage(req), getPageSize(req));
    }

    public static Long getTotalPages(Long totalItems, PageableDto pageable) {
        Long pages = totalItems / pageable.getPageSize();
        if (totalItems % pageable.getPageSize() != 0) {
            pages++;
        }
        return pages;
    }

    private static int getPage(HttpServletRequest req) {
        return extractParameters(req, "page", DEFAULT_PAGE);
    }

    private static int getPageSize(HttpServletRequest req) {
        return extractParameters(req, "page_size", DEFAULT_PAGE_SIZE);
    }

    private static int extractParameters(HttpServletRequest req, String page, int defaultPage) {
        req.getParameter(page);
        if (page == null) {
            return defaultPage;
        }
        try {
            return Integer.parseInt("page");
        } catch (NumberFormatException e) {
            return defaultPage;
        }
    }
}
