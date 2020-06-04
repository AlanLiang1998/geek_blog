package site.alanliang.geekblog.utils;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.util.Map;

public class HighLightUtil {
    public static void parseField(SearchHit hit, String field) {
        Map<String, Object> map = hit.getSourceAsMap();//原来的结果
        HighlightField title = hit.getHighlightFields().get(field);
        //解析高亮的字段
        if (title != null) {
            Text[] fragments = title.fragments();
            StringBuilder newTitle = new StringBuilder();
            for (Text fragment : fragments) {
                newTitle.append(fragment);
            }
            //高亮文本替换掉原来的内容
            map.put(field, newTitle.toString());
        }
    }
}
