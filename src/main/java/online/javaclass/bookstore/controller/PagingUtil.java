package online.javaclass.bookstore.controller;

import online.javaclass.bookstore.service.dto.PageableDto;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PagingUtil {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;

    public PageableDto getPageable(HttpServletRequest req) {
        return new PageableDto(getPage(req), getPageSize(req));
    }

    public Long getTotalPages(Long totalItems, PageableDto pageable) {
        Long pages = totalItems / pageable.getPageSize();
        if (totalItems % pageable.getPageSize() != 0) {
            pages++;
        }
        return pages;
    }

    private int getPage(HttpServletRequest req) {
        int page = extractParameters(req, "page", DEFAULT_PAGE);
        if (page < 1) {
            return DEFAULT_PAGE;
        }
        return page;
    }

    private int getPageSize(HttpServletRequest req) {
        return extractParameters(req, "page_size", DEFAULT_PAGE_SIZE);
    }

    private int extractParameters(HttpServletRequest req, String param, int defaultValue) {
        String page = req.getParameter(param);
        if (param == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(page);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
