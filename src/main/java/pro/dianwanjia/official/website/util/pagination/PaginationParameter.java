package pro.dianwanjia.official.website.util.pagination;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import pro.dianwanjia.official.website.util.Base.BaseObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description: 分页参数类
 * @version V1.0.0
 */
@ApiModel(value = "分页参数类")
public class PaginationParameter extends BaseObject {

    @ApiModelProperty(value = "单页数据量",
            example = "10")
    private Integer pageRows;

    @ApiModelProperty(value = "当前数据页码",
            example = "1")
    private Long pageIndex;

    @ApiModelProperty(value = "排序字段（可选，格式> 字段:排序类型 `1(desc)/0(asc)` 后端默认 `0` ，多字段排序用 `;` 分隔，eg:\n"
            + "            `field1;field2:1`）",
            example = "field1;field2:1")
    private String pageOrder;

    @ApiModelProperty(value = "数据总量（可选，默认第一次请求不用填写。后端判断如果该字段有值则不会重新查询总数量，如果值无效就会重新查询数据总量并返回）",
            example = "100")
    private Long pageDataSize;

    public Integer getPageRows() {
        return pageRows == null || pageRows < 1 ? (pageRows = 10) : pageRows;
    }

    public void setPageRows(Integer pageRows) {
        this.pageRows = pageRows;
    }

    public Long getPageIndex() {
        return pageIndex == null || pageIndex < 1 ? (pageIndex = 1l)
                : pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(String pageOrder) {
        this.pageOrder = pageOrder;
    }

    public Long getPageDataSize() {
        return pageDataSize;
    }

    public void setPageDataSize(Long pageDataSize) {
        if (pageDataSize == null || pageDataSize < 1) {
            this.pageDataSize = 0l;
        } else {
            this.pageDataSize = pageDataSize;
        }
    }

    public Long getPageDataStart() {
        return (getPageIndex() - 1) * getPageRows();
    }

    public Long getPageDataEnd() {
        return getPageDataStart() + getPageRows() - 1;
    }

    public Map<String, String> getPageOrders() {
        return getOrders(pageOrder);
    }

    private static Map<String, String> getOrders(String pageOrder) {
        Map<String, String> orderMap = null;
        if (StringUtils.isNotBlank(pageOrder)) {
            String[] split = pageOrder.split(";");
            orderMap = new LinkedHashMap<>(split.length);
            for (String orderStr : split) {
                String[] split1 = orderStr.split(":");
                orderMap.put(split1[0], split1.length > 1 ? split1[1] : "0");
            }
        }
        return orderMap;
    }

}
