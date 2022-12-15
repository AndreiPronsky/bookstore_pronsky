package online.javaclass.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;

public class PagingUtil {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;

    public static Pageable getPageable(HttpServletRequest req) {
        return new Pageable(getPage(req), getPageSize(req));
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
