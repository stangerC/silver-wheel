package com.silver.wheel.db.dao;

import java.io.Serializable;
import java.util.Collection;

/**
 * dao接口，定义了常用的持久层方法签名
 *
 * @author liaojian
 *
 * @param <T>	进行增删改查的实体类
 */
public interface CrudDao<T, ID extends Serializable> {

    /**
     * 保存一个实体到库
     *
     * @param entity 需要保存到库的实体类
     */
    public void save(T entity);

    /**
     * 保存多个实体
     *
     * @param entities 需要保存的实体对象
     */
    public void save(Collection<T> entities);

    /**
     * 根据ID删除一个实体
     *
     * @param id 唯一标记实体的ID
     */
    public void delete(ID id);

    /**
     * 删除一个实体
     *
     * @param entity 需要删除的实体
     */
    public void delete(T entity);

    /**
     * 根据多个ID删除多个实体
     *
     * @param ids 唯一标记实体的ID数组
     */
    public void delete(Collection<ID> ids);

    /**
     * 更新一个实体到数据库
     *
     * @param entity 需要更新的实体
     */
    public void update(T entity);

    /**
     * 更新多个实体
     *
     * @param entities 需要更新的实体
     */
    public void update(Collection<T> entities);

    /**
     * 根据id获取一个实体
     *
     * @param id 唯一确定实体的id值
     * @return 对应id的实体
     */
    public T get(ID id);       
    
    /**
     * 返回所有对象
     * @return 
     *  返回所有T类型的对象，以Collection形式返回。
     */
    public Collection<T> findAll();
    
//    /**
//     * 根据分页对象获取当前页的实体
//     * @param pagination
//     * @return 
//     */
//    public Collection<T> find(Pagination pagination);
    
    public long count();
}
