package pro.dianwanjia.official.website.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description:
 * @version V1.0.0
 */
public class ParamsUtil {

    /**
     * 拼装多个参数为Map对象
     *
     * @param keys   参数
     * @param values 参数对应值
     * @return
     */
    public static Map<String, Object> getObjectParams(String[] keys, Object... values) {
        if (keys == null || keys.length < 1 || values == null
                || values.length < keys.length) {
            return null;
        }
        Map<String, Object> params = new HashMap<>();
        for (int i = 0, j = keys.length; i < j; i++) {
            params.put(keys[i], values[i]);
        }
        return params;
    }

    /**
     * 拼装多个参数为Map对象
     *
     * @param keys   参数
     * @param values 参数对应值
     * @return
     */
    public static <T> Map<String, T> getParams(String[] keys, T... values) {
        if (keys == null || keys.length < 1 || values == null
                || values.length < keys.length) {
            return null;
        }
        Map<String, T> params = new HashMap<>();
        for (int i = 0, j = keys.length; i < j; i++) {
            params.put(keys[i], values[i]);
        }
        return params;
    }

    public static <T> Map<String, T> getUnifiedParams(String[] keys, T... values) {
        if (keys == null || keys.length < 1 || values == null
                || values.length < keys.length) {
            return null;
        }
        Map<String, T> params = new HashMap<>();
        for (int i = 0, j = keys.length; i < j; i++) {
            params.put(keys[i], values[i]);
        }
        return params;
    }

    public interface GetChild {
        String getChild(Object json);
    }

    /**
     * 将指定数据转换为json所需的格式去掉不需要的字段
     *
     * @param json
     * @param dataset
     * @return
     */
    public static List<Map<String, Object>> getData(String json,
                                                    List<Map<String, Object>> dataset) {
        if (json == null || "".equals(json.trim()) || dataset == null) {
            return dataset;
        }
        List<Map<String, Object>> newDataset = new ArrayList<>(dataset.size());
        for (Map<String, Object> map : dataset) {
            newDataset.add(getData(json, null, map));
        }
        return newDataset;
    }

    public static List<Map<String, Object>> getData(String json, GetChild getChild,
                                                    List<Map<String, Object>> dataset) {
        if (json == null || "".equals(json.trim()) || dataset == null) {
            return dataset;
        }
        List<Map<String, Object>> newDataset = new ArrayList<>(dataset.size());
        for (Map<String, Object> map : dataset) {
            newDataset.add(getData(json, getChild, map));
        }
        return newDataset;
    }

    public static Map<String, Object> getData(String json,
                                              Map<String, Object> dataset) {
        return getData(json, null, dataset);
    }

    /**
     * 将指定数据转换为json所需的格式去掉不需要的字段
     *
     * @param json
     * @param getChild
     * @param dataset
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getData(String json, GetChild getChild,
                                              Map<String, Object> dataset) {
        if (json == null || "".equals(json.trim()) || dataset == null) {
            return dataset;
        }
        Map<String, Object> jsonObj = null;
        try {
            jsonObj = JsonUtils.toObject(json, Map.class);
            Set<String> keys = jsonObj.keySet();
            Iterator<String> keysIter = keys.iterator();
            while (keysIter.hasNext()) {
                String key = keysIter.next();
                Object value = dataset.get(key);
                if (value != null && value instanceof Map) {
                    value = getData((getChild == null ? JsonUtils.toJson(jsonObj.get(key)) : getChild.getChild(jsonObj.get(key))), getChild, (Map<String, Object>) value);
                }
                jsonObj.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj == null ? dataset : jsonObj;
    }

    /**
     * 将对象装换为只包含指定字段的map
     *
     * @param keys    指定字段
     * @param dataset 数据集合
     * @return
     */
    public static Map<String, Object> getData(String[] keys,
                                              Map<String, Object> dataset) {
        if (keys == null || keys.length < 1 || dataset == null) {
            return dataset;
        }
        Map<String, Object> map = new Hashtable<>();
        for (String key : keys) {
            Object value = dataset.get(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 将对象装换为只包含指定字段的map
     *
     * @param <T>     数据类型
     * @param keys    指定字段
     * @param dataset 数据集合
     * @return
     */
    public static <T> List<Map<String, Object>> getData(String[] keys,
                                                        List<T> dataset) {
        if (keys == null || keys.length < 1 || dataset == null) {
            return null;
        }
        Iterator<T> it = dataset.iterator();
        List<Map<String, Object>> list = new ArrayList<>();
        while (it.hasNext()) {
            Map<String, Object> map = getData(keys, it.next());
            if (map == null) {
                continue;
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 将对象装换为只包含指定字段的map
     *
     * @param <T>  数据类型
     * @param keys 指定字段
     * @param obj  数据对象
     * @return
     */
    public static <T> Map<String, Object> getData(String[] keys, T obj) {
        if (keys == null || keys.length < 1 || obj == null) {
            return null;
        }
        Map<String, Object> map = new Hashtable<>();
        Class<?> clazz = obj.getClass();
        for (String key : keys) {
            Object value = null;
            if (obj instanceof Map) {
                value = ((Map<?, ?>) obj).get(key);
            } else {
                String getMethodName = "get" + key.substring(0, 1).toUpperCase()
                        + key.substring(1);
                Method getMethod;
                try {
                    getMethod = clazz.getMethod(getMethodName);
                    value = getMethod.invoke(obj);
                } catch (NoSuchMethodException | SecurityException
                        | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            map.put(key, value);
        }
        return map;
    }


//    @SuppressWarnings("unchecked")
//    public static void main(String[] args) {
//        Map<String, Object> data = JsonUtils.toObject("{\"classification1\":\"马克思主义、列宁主义、毛泽东思想、邓小平理论、“三个代表”重要思想\",\"pdri_id\": 1,\"location\":\"http://localhost\",\"registered_datetime\":\"2016-07-06 15:04:53\",\"periodical_serial_no\":\"A1\",\"pdri_code_issue\":\"108.1.100/ISSN.1234-2345\",\"periodical_column_no\":\"123\",\"classification1_code\":\"classify_A\",\"hit_count\": 0,\"pdri_catalog\":\"PERIODICAL\",\"publisher_code\": 1151,\"periodical_price\": { },\"pdri_suffix\":\"\",\"pdri_code_periodical\":\"108.1.100/ISSN.1234-2345.A1\",\"pdri_code_hashval\": 22,\"pricelabel\":\"人民币元\",\"release_datetime\":\"2016-07-06\",\"plan_column\":\"计划栏目g\",\"periodical1_code\":\"periodical.type2\",\"periodical1\":\"旬刊\", \"price_code\":\"CNY\", \"publication_type1_code\":\"publication.type2\",\"issue_info\": { \"issue_size\": 195,\"publication_type\":\"publication.type2\",\"pdri_id\": 1,\"cn_no\":\"12-1234-ab\",\"issue_price\": { \"price\":\"15.1\",\"label\":\"CNY\"},\"subject\":\"学科\",\"publication_e_datetime\":\"2016\", \"pdri_code_issue\":\"108.1.100/ISSN.1234-2345\",\"publisher_code\": 1151, \"hit_count\": 0, \"pdri_catalog\":\"ISSUE\",\"postal_number\":\"邮发代号\",\"host_unit\":\"dsfsf\", \"supervisor_unit\":\"sdfsdffdfsf\", \"publication_s_datetime\":\"2014\", \"keyword_en\":\"\", \"pdri_suffix\":\"\",\"periodical\":\"periodical.type2\", \"pdri_code_hashval\": 12,\"publish_address\":\"afdasdf\", \"research_field\":\"研究领域\", \"issue_name\":\"adsfasdf\", \"keyword_cn\":\"asfdadfasdf\",\"classification\":\"classify_A\",\"issn_no\":\"ISSN.1234-2345\",\"create_datetime\":\"2016-07-06 15:04:53\", \"hit_count_day\": 0, \"record_status\": 1, \"pdri_publisher\":\"sdfds\"},\"create_datetime\":\"2016-07-06 15:04:53\",\"hit_count_day\": 0,\"publication_type1\":\"网刊\",\"record_status\": 1,\"md5_val\":\"11111111111111111111111111111111\"}", Map.class);
//        Map<String, Object> filter = getData("{\"issue_info\": {\"type\": \"object\", \"properties\": {\"pdri_id\": {\"type\": \"long\"}, \"publisher_code\": {\"type\": \"string\", \"index\": \"not_analyzed\"}, \"pdri_code_issue\": {\"type\": \"string\"}, \"pdri_suffix\": {\"type\": \"string\"}, \"pdri_code_hashval\": {\"type\": \"string\", \"index\": \"not_analyzed\"}, \"issn_no\": {\"type\": \"string\"}, \"cn_no\": {\"type\": \"string\"}, \"issue_name\": {\"type\": \"string\", \"analyzer\": \"ik\"}, \"host_unit\": {\"type\": \"string\"}, \"supervisor_unit\": {\"type\": \"string\"}, \"pdri_publisher\": {\"type\": \"string\", \"analyzer\": \"ik\"}, \"publish_address\": {\"type\": \"string\", \"analyzer\": \"ik\"}, \"periodical\": {\"type\": \"string\"}, \"issue_size\": {\"type\": \"string\"}, \"publication_s_datetime\": {\"type\": \"string\"}, \"publication_e_datetime\": {\"type\": \"string\"}, \"publication_type\": {\"type\": \"string\", \"analyzer\": \"semicolon\"}, \"classification\": {\"type\": \"string\", \"analyzer\": \"semicolon\"}, \"keyword_cn\": {\"type\": \"string\", \"analyzer\": \"ik\"}, \"keyword_en\": {\"type\": \"string\", \"analyzer\": \"ik\"}, \"subject\": {\"type\": \"string\", \"analyzer\": \"ik\"}, \"research_field\": {\"type\": \"string\", \"analyzer\": \"ik\"}, \"postal_number\": {\"type\": \"string\"}, \"issue_price\": {\"type\": \"object\", \"properties\": {\"label\": {\"type\": \"string\", \"analyzer\": \"semicolon\"}, \"price\": {\"type\": \"string\"}}}, \"record_status\": {\"type\": \"string\", \"index\": \"not_analyzed\"}, \"hit_count\": {\"type\": \"long\", \"index\": \"not_analyzed\"}, \"hit_count_day\": {\"type\": \"long\", \"index\": \"not_analyzed\"}, \"update_datetime\": {\"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss\", \"index\": \"not_analyzed\"}, \"create_datetime\": {\"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss\", \"index\": \"not_analyzed\"}}}, \"pdri_id\": {\"type\": \"long\"}, \"publisher_code\": {\"type\": \"string\", \"index\": \"not_analyzed\"}, \"pdri_code_issue\": {\"type\": \"string\"}, \"pdri_code_periodical\": {\"type\": \"string\"}, \"pdri_suffix\": {\"type\": \"string\"}, \"pdri_code_hashval\": {\"type\": \"string\", \"index\": \"not_analyzed\"}, \"issn_no\": {\"type\": \"string\"}, \"periodical_serial_no\": {\"type\": \"string\"}, \"periodical_column_no\": {\"type\": \"string\"}, \"release_datetime\": {\"type\": \"date\", \"format\": \"yyyy-MM-dd\", \"index\": \"not_analyzed\"}, \"registered_datetime\": {\"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss\", \"index\": \"not_analyzed\"}, \"plan_column\": {\"type\": \"string\", \"analyzer\": \"ik\"}, \"location\": {\"type\": \"string\"}, \"md5_val\": {\"type\": \"string\"}, \"record_status\": {\"type\": \"string\", \"index\": \"not_analyzed\"}, \"hit_count\": {\"type\": \"string\", \"index\": \"not_analyzed\"}, \"hit_count_day\": {\"type\": \"string\", \"index\": \"not_analyzed\"}, \"update_datetime\": {\"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss\", \"index\": \"not_analyzed\"}, \"create_datetime\": {\"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss\", \"index\": \"not_analyzed\"}}", new GetChild() {
//            @Override
//            @SuppressWarnings("rawtypes")
//            public String getChild(Object json) {
//                if (json == null)   return null;
//                Object value = null;
//                if (json instanceof Map) {
//                    value = ((Map)json).get("properties");
//                }
//                return value == null ? null : JsonUtils.toJson(value);
//            }
//        }, data);
//        System.out.println(JsonUtils.toJson(filter));
//    }

}
