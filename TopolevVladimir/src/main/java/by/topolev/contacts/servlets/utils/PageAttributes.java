package by.topolev.contacts.servlets.utils;

/**
 * Created by Vladimir on 04.10.2016.
 */
public class PageAttributes {
    public static class PageContact{
        public static final String ATTR_CONTACT = "contact";
        public static final String ATTR_PAGE = "page";
        public static final String ATTR_COUNT_PAGE = "countPage";

        public static final String PARAM_PAGE = "page";
        public static final String PARAM_COUNT_ROW = "countRow";
    }

    public static class PageContactList{
        public static final String ATTR_COUNT = "count";
        public static final String ATTR_PAGE = "page";
        public static final String ATTR_COUNT_ROW = "countRow";
        public static final String ATTR_PAGINATOR = "paginator";
        public static final String ATTR_SORT_TYPE = "sortType";
        public static final String ATTR_SORT_FIELD = "sortField";
        public static final String ATTR_SORT_FIELDS = "sortFields";
        public static final String ATTR_CONTACT_LIST = "contactList";

        public static final String PARAM_SORT_FIELD = "sortField";
        public static final String PARAM_COUNT_ROW = "countRow";
        public static final String PARAM_PAGE = "page";
        public static final String PARAM_SORT_TYPE = "sortType";
    }

    public static class SearchForm{
        public static final String ATTR_COUNT_ROW = "countRow";
        public static final String ATTR_PAGE = "page";
        public static final String ATTR_PAGINATOR = "paginator";
        public static final String ATTR_COUNT = "count";
        public static final String ATTR_CONTACT_LIST = "contactList";
        public static final String ATTR_LIST_FIELDS = "listFields";

        public static final String PARAM_PAGE = "page";
        public static final String PARAM_COUNT_ROW = "countRow";
    }
}
