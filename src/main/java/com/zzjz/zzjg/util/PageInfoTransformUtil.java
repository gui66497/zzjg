package com.zzjz.zzjg.util;

import com.github.pagehelper.PageInfo;

public class PageInfoTransformUtil {
    public static void pageInfoTransform(PageInfo pageInfoOld, PageInfo pageInfoNew) {
        pageInfoNew.setTotal(pageInfoOld.getTotal());
        pageInfoNew.setPageNum(pageInfoOld.getPageNum());
        pageInfoNew.setPageSize(pageInfoOld.getPageSize());
        pageInfoNew.setEndRow(pageInfoOld.getEndRow());
        pageInfoNew.setHasNextPage(pageInfoOld.isHasNextPage());
        pageInfoNew.setHasPreviousPage(pageInfoOld.isHasPreviousPage());
        pageInfoNew.setIsFirstPage(pageInfoOld.isIsFirstPage());
        pageInfoNew.setIsLastPage(pageInfoOld.isIsLastPage());
        pageInfoNew.setNavigateFirstPage(pageInfoOld.getNavigateFirstPage());
        pageInfoNew.setNavigateLastPage(pageInfoOld.getNavigateLastPage());
        pageInfoNew.setNavigatepageNums(pageInfoOld.getNavigatepageNums());
        pageInfoNew.setNavigatePages(pageInfoOld.getNavigatePages());
        pageInfoNew.setNextPage(pageInfoOld.getNextPage());
        pageInfoNew.setPages(pageInfoOld.getPages());
        pageInfoNew.setPrePage(pageInfoOld.getPrePage());
        pageInfoNew.setSize(pageInfoOld.getSize());
        pageInfoNew.setStartRow(pageInfoOld.getStartRow());
    }
}
