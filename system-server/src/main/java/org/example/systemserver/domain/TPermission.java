package org.example.systemserver.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 权限表
 * t_permission
 */
@Data
public class TPermission implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 菜单/按钮的名称（资源名称）
     */
    private String name;

    /**
     * 权限标识符
     */
    private String code;

    /**
     * 资源的请求地址
     */
    private String url;

    /**
     * 资源类型（菜单/按钮）
     */
    private String type;

    /**
     * 资源的父级ID
     */
    private Integer parentId;

    /**
     * 资源的排序
     */
    private Integer orderNo;

    /**
     * 菜单的图标
     */
    private String icon;

    /**
     * 一对多关联
     */
    private List<TPermission> subPermissionList;

    private static final long serialVersionUID = 1L;
}