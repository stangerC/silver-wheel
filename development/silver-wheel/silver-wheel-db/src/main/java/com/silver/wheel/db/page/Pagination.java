package com.silver.wheel.db.page;

/**
 * 
 * @author Liaojian
 */
public class Pagination {   
    
    private int pageCount;
    
    private int pageNumber;
    
    private int pageSize;        
    
    public int DEFAULT_PAGE_SIZE = 50;
    
    public Pagination() {
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        if(pageCount < 0) {
            this.pageCount = 0;
        }
        else {
            this.pageCount = pageCount;
        }
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        if(pageNumber < 0) {
            this.pageNumber = 0;
        } else {
            this.pageNumber = pageNumber;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(this.pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        else {
            this.pageSize = pageSize;
        }
    }        
    
}
