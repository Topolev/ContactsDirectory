package by.topolev.contacts.servlets.utils;

import by.topolev.contacts.servlets.formdata.Paginator;
import org.apache.commons.fileupload.FileItem;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ServletUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ServletUtil.class);

    private static ObjectMapper map = new ObjectMapper();




    public static <T> T getRequestParameter(HttpServletRequest req, String parameter, Class<T> clazz, T defaultValue) {
        return getDefaultValueFromString(req.getParameter(parameter), clazz, defaultValue);

    }

    public static <T> T getFileItemParametr(List<FileItem> items, String parameter, Class<T> clazz, T defaultValue){
        Optional<String> first = items.stream()
                .filter(item -> item.isFormField())
                .filter(item -> item.getFieldName().equals(parameter))
                .map(FileItem::getString)
                .findFirst();

        String value = first.isPresent() ? first.get() : null;
        return getDefaultValueFromString(value, clazz, defaultValue);
    }

    private static <T> T getDefaultValueFromString(String value, Class<T> clazz, T defaultValue){
        try {
            if (isEmpty(value)) {
                return defaultValue;
            }
            return (clazz == String.class) ? (T) value : map.readValue(value, clazz);
        } catch (IOException e) {
            return defaultValue;
        }
    }



    public static Paginator createPaginator(int page, int countPage){
        Paginator paginator = new Paginator();
        paginator.setButtonNext((page+1) < countPage);
        paginator.setButtonPrev(page != 0);
        paginator.setCountPage(countPage);

        if (countPage != 0) {
            if (page < 3) {
                addSequencyNumberInCollection(paginator.getListPages(), 0, Math.min(3, Math.max(0, countPage - 1)));
            } else if (page < countPage - 3) {
                addSequencyNumberInCollection(paginator.getListPages(), page - 1, page + 1);
            } else {
                addSequencyNumberInCollection(paginator.getListPages(), Math.max(0, countPage - 3), countPage - 1);
            }

            paginator.setSkipLeft(paginator.getListPages().get(0) != 0);
            paginator.setSkipRight((paginator.getListPages().get(paginator.getListPages().size() - 1)) != (countPage - 1));
        }
        return paginator;
    }

    private static void addSequencyNumberInCollection(List<Integer> list, int begin, int end){
        for (int i = begin; i <= end; i++){
            list.add(i);
        }
    }

    public static Integer[] convertStringInIntArray(String value){
        try {
            if (value == null) throw new IOException();
            Integer[] idList = map.readValue(value, Integer[].class);
            return idList;
        } catch (IOException e) {
            LOG.debug("Invalid array of id", e);
            return new Integer[0];
        }
    }
}
