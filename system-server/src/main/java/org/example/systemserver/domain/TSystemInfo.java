package org.example.systemserver.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统信息表
 * @TableName t_system_info
 */
@Data
public class TSystemInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String systemCode;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String site;

    /**
     * 
     */
    private String logo;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private String keywords;

    /**
     * 
     */
    private String shortcuticon;

    /**
     * 
     */
    private String tel;

    /**
     * 
     */
    private String weixin;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String address;

    /**
     * 
     */
    private String version;

    /**
     * 
     */
    private String closemsg;

    /**
     * 
     */
    private String isopen;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Integer createBy;

    /**
     * 
     */
    private Date editTime;

    /**
     * 
     */
    private Integer editBy;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TSystemInfo other = (TSystemInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSystemCode() == null ? other.getSystemCode() == null : this.getSystemCode().equals(other.getSystemCode()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSite() == null ? other.getSite() == null : this.getSite().equals(other.getSite()))
            && (this.getLogo() == null ? other.getLogo() == null : this.getLogo().equals(other.getLogo()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getKeywords() == null ? other.getKeywords() == null : this.getKeywords().equals(other.getKeywords()))
            && (this.getShortcuticon() == null ? other.getShortcuticon() == null : this.getShortcuticon().equals(other.getShortcuticon()))
            && (this.getTel() == null ? other.getTel() == null : this.getTel().equals(other.getTel()))
            && (this.getWeixin() == null ? other.getWeixin() == null : this.getWeixin().equals(other.getWeixin()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getClosemsg() == null ? other.getClosemsg() == null : this.getClosemsg().equals(other.getClosemsg()))
            && (this.getIsopen() == null ? other.getIsopen() == null : this.getIsopen().equals(other.getIsopen()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getEditTime() == null ? other.getEditTime() == null : this.getEditTime().equals(other.getEditTime()))
            && (this.getEditBy() == null ? other.getEditBy() == null : this.getEditBy().equals(other.getEditBy()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSystemCode() == null) ? 0 : getSystemCode().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSite() == null) ? 0 : getSite().hashCode());
        result = prime * result + ((getLogo() == null) ? 0 : getLogo().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getKeywords() == null) ? 0 : getKeywords().hashCode());
        result = prime * result + ((getShortcuticon() == null) ? 0 : getShortcuticon().hashCode());
        result = prime * result + ((getTel() == null) ? 0 : getTel().hashCode());
        result = prime * result + ((getWeixin() == null) ? 0 : getWeixin().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getClosemsg() == null) ? 0 : getClosemsg().hashCode());
        result = prime * result + ((getIsopen() == null) ? 0 : getIsopen().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getEditTime() == null) ? 0 : getEditTime().hashCode());
        result = prime * result + ((getEditBy() == null) ? 0 : getEditBy().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", systemCode=").append(systemCode);
        sb.append(", name=").append(name);
        sb.append(", site=").append(site);
        sb.append(", logo=").append(logo);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", keywords=").append(keywords);
        sb.append(", shortcuticon=").append(shortcuticon);
        sb.append(", tel=").append(tel);
        sb.append(", weixin=").append(weixin);
        sb.append(", email=").append(email);
        sb.append(", address=").append(address);
        sb.append(", version=").append(version);
        sb.append(", closemsg=").append(closemsg);
        sb.append(", isopen=").append(isopen);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", editTime=").append(editTime);
        sb.append(", editBy=").append(editBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}