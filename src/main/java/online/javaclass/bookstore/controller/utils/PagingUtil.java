package online.javaclass.bookstore.controller.utils;

import online.javaclass.bookstore.service.dto.PageableDto;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class PagingUtil {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;

    public PageableDto getPageable(Model model) {
        return new PageableDto(getPage(model), getPageSize(model));
    }

    public Long getTotalPages(Long totalItems, PageableDto pageable) {
        Long pages = totalItems / pageable.getPageSize();
        if (totalItems % pageable.getPageSize() != 0) {
            pages++;
        }
        return pages;
    }

    private int getPage(Model model) {
        int page = extractParameters(model, "page", DEFAULT_PAGE);
        if (page < 1) {
            return DEFAULT_PAGE;
        }
        return page;
    }

    private int getPageSize(Model model) {
        return extractParameters(model, "page_size", DEFAULT_PAGE_SIZE);
    }

    private int extractParameters(Model model, String param, int defaultValue) {
        if (param == null) {
            return defaultValue;
        } else {
            String page = model.getAttribute(param).toString();
            try {
                return Integer.parseInt(page);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }
}
