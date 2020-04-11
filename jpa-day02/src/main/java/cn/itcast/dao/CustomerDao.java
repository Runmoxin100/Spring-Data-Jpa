package cn.itcast.dao;

import cn.itcast.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 符合Spring Data Jpa的dao层接口规范
 * 需要继承两个接口JpaRepository<T, id>和JpaSpecificationExecutor<T>
 *     JpaRepository<T, id>的两个泛型T代表的式实体类,id代表的式主键的字段的类型
 *          上面的接口可以实现增删改查CRUD的操作
 *     paSpecificationExecutor<T>中的泛型是操作的实体类的类型
 *          能够实现复杂查询(例如分页查询)
 */
public interface CustomerDao extends JpaRepository<Customer, Long> , JpaSpecificationExecutor<Customer> {
    /**
     * 案例:根据客户名称查询数据
     * 使用jpql的形式查询 jpql:from Customer where custName = ?1
     * 配置jpql语句使用注解@Query
     */
    @Query(value = "from Customer where custName = ?1")
    public Customer findByName(String custName);

    /**
     * 案例:根据用户名和id擦寻数据,验证多参数查询.
     * jqpl: from Customer where custName = ?2 and custId = ?1
     *      注意: 对于多个占位符,赋值的时候默认情况下,占位符的位置需要和方法中的参数的位置保持一致
     * 也可以指定占位符参数的位置: ? 索引的方式: 指定此占位的来源
     */
    @Query(value = "from Customer where custName = ?2 and custId = ?1")
    public Customer findByNameAndId(Long id, String name);

    /**
     * 使用jpql完成更新数据的操作
     *      案例:更新8号数据的客户名称为黑马程序员
     * sql: update cst_customer set cust_name = ? where cust_id = ?
     * jpql: update Customer set custName = ? where custId = ?
     * 注意:
     * @Query 注解代表的是查询操作
     *      需要我们声明这个是i一个更新的操作,需要用到
     *
     * 注意:在使用jpql执行更新或者删除的操作的时候,在抽象方法的重写的方法上面需要添加事务的支持.
     * 只需要一个注解即可完成:@Transiactional注解,测试的时候默认是自动回滚事务,并不会提交事务.
     * 需要添加@Rollback(value = false),设置不自动回滚事务.
     */
    @Query(value = "update Customer set custName = ?2 where custId = ?1")
    @Modifying // 用来声明此方法是更新操作
    public void updateById(Long id, String name);

    /**
     * 使用sql形式查询: 查询全部的数据
     * sql:select * from cst_customer
     * @Query 注解中的配置value = "select * from cst_customer", nativeQuery = true
     *      nativeQuery: 查询的方式
     *          true: 代表的是sql查询
     *          false: 代表的是使用jpql查询,这个是Spring Data Jpa默认的查询方式.
     */
    @Query(value = "select * from cst_customer", nativeQuery = true)
    public List<Customer> findSql();
}
