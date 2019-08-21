package pro.dianwanjia.official.website.util.pagination;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description: 分页结果类
 * @version V1.0.0
 */
@ApiModel(value = "分页结果类")
public class PaginationResponse<T> extends PaginationParameter {

    @ApiModelProperty(value = "总共页数",
            example = "10")
    private Long pageSize;

    @ApiModelProperty(value = "当前页数据")
    private List<T> pageDataList;

    public Long getPageSize() {
        long totalPage = getPageDataSize() / getPageRows();
        return pageSize = (getPageDataSize() % getPageRows() != 0) ? ++totalPage : totalPage;
    }

    public List<T> getPageDataList() {
        return pageDataList;
    }

    public void setPageDataList(List<T> pageDataList) {
        this.pageDataList = pageDataList;
    }

    public static <T> PaginationResponse<T> getPagination(PaginationParameter pagination, Long size,
                                                          List<T> data) {
        PaginationResponse<T> response = new PaginationResponse<>();
        response.setPageDataSize(size);
        response.setPageDataList(data);
        response.setPageOrder(pagination.getPageOrder());
        response.setPageIndex(pagination.getPageIndex());
        response.setPageRows(pagination.getPageRows());
        return response;
    }

}
