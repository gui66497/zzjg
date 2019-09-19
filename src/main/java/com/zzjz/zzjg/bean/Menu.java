package com.zzjz.zzjg.bean;

public class Menu {
    private Integer id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 访问路径
     */
    private String url;
    /**
     * 父级菜单id
     */
    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
