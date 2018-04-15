package io.github.nzlong.generate.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.BodyParam;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PostRoute;
import com.blade.mvc.ui.RestResponse;
import io.github.nzlong.generate.entity.ConnectionReqVO;
import io.github.nzlong.generate.entity.BaseRespVO;
import io.github.nzlong.generate.entity.GenerateReqVO;
import io.github.nzlong.generate.entity.PageVO;
import io.github.nzlong.generate.entity.TableInfo;
import io.github.nzlong.generate.kit.ConnKit;
import io.github.nzlong.generate.kit.Const;
import io.github.nzlong.generate.service.GenerationService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 03 31 下午7:57
 */
@Path
public class GenerationController {

    @Inject
    private GenerationService generationService;

    /**
     * 获取所有表信息
     *
     * @throws SQLException
     */
    @PostRoute(value = "getTables")
    @JSON
    public RestResponse<PageVO<TableInfo>> getTables(@BodyParam ConnectionReqVO connectionReqVO) throws SQLException {
        try {
            PageVO pageVO = new PageVO(Const.DEFAULT_PAGE_INDEX, Const.DEFAULT_PAGE_SIZE);
            generationService.getTables(connectionReqVO, pageVO);
            return RestResponse.ok(pageVO, Const.SUCCESS_CODE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return RestResponse.fail(ex.getMessage());
        }
    }

    /**
     * 测试连接
     *
     * @param connectionReqVO
     * @return
     */
    @PostRoute(value = "testConnection")
    @JSON
    public RestResponse<BaseRespVO> testConnection(@BodyParam(required = true) ConnectionReqVO connectionReqVO) {
        BaseRespVO connectionRespVo = null;
        try {
            connectionRespVo = ConnKit.testConnection(connectionReqVO);
            return RestResponse.ok(connectionRespVo, Const.SUCCESS_CODE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return RestResponse.fail(ex.getMessage());
        }
    }

    /**
     * 获取数据库信息
     *
     * @param connectionReqVO
     * @return
     */
    @PostRoute(value = "getSchemas")
    @JSON
    public RestResponse<String> getSchemas(@BodyParam(required = true) ConnectionReqVO connectionReqVO) {
        try {
            List<String> schemaNames = generationService.getSchemas(connectionReqVO);
            return RestResponse.ok(schemaNames, Const.SUCCESS_CODE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return RestResponse.fail(ex.getMessage());
        }
    }

    @PostRoute(value = "generateStart")
    @JSON
    public RestResponse generateStart(@BodyParam(required = true)GenerateReqVO generateReqVO) {
        try {
            generationService.generateStart(generateReqVO);
            return RestResponse.ok(Const.SUCCESS_CODE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return RestResponse.fail(ex.getMessage());
        }
    }

}
