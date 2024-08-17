package org.example.systemserver.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 线索跟踪记录表
 * t_clue_remark
 */
@Data
public class TClueRemark implements Serializable {

    /**
     * 主键，自动增长，线索备注ID
     */
    private Integer id;

    /**
     * 线索ID
     */
    private Integer clueId;

    /**
     * 跟踪方式
     */
    private Integer noteWay;

    /**
     * 跟踪内容
     */
    private String noteContent;

    /**
     * 跟踪时间
     */
    private Date createTime;

    /**
     * 跟踪人
     */
    private Integer createBy;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 编辑人
     */
    private Integer editBy;

    /**
     * 删除状态（0正常，1删除）
     */
    private Integer deleted;

    /**
     * 一对一关联
     */
    private TUser createByDO;
    private TUser editByDO;
    private TDicValue noteWayDO;

    private static final long serialVersionUID = 1L;
}