package com.zzjz.zzjg.bean;

/**
 * @ClassName: Organization
 * @Description: 组织机构实体类
 * @author 房桂堂
 * @date 2019/7/10 9:02
 */
public class Organization {

    private String id;

    private String name;

    private String pid;

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + id.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object var1) {
        if (var1 instanceof Organization) {
            Organization organization = (Organization) var1;
            return id.equals(organization.getId());
        }
        return false;
    }

    public Organization() {
    }

    public Organization(String id, String name, String pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }
}