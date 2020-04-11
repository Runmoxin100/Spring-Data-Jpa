package cn.itcast.test;

import cn.itcast.domain.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * 测试Jpql语句查询
 * sql语句: select * from cst_customer
 * jpql: "from cn.itcast.domain.Customer"
 * 可以看到我们的jpql语句中是从实体类中查询,格式是from 实体类的全类名
 */
public class JpqlTest {
    @Test
    public void testFindAll() {
        // 1. 获取EntityManager,通过JpaUtils工具类
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 获取事务对象,并开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 执行增删改查 -- 查询所有的客户数据
        String jpql = "from cn.itcast.domain.Customer";
        Query query = entityManager.createQuery(jpql);// 创建一个Query对象来执行查询的语句
        // 发送查询,并封装结果集
        List list = query.getResultList();
        for (Object object : list) {
            System.out.println("object: " + object);
        }
        // 4. 提交事务
        transaction.commit();
        // 5. 释放资源
        entityManager.close();
    }

    /**
     * 排序查询
     * sql: select * from cst_customer order by id desc
     * jpql: " from Customer order by custId desc"
     */
    @Test
    public void testOrders() {
        // 1. 获取EntityManager,通过JpaUtils工具类
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 获取事务对象,并开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 执行增删改查
        String jpql = "from Customer order by custId desc";
        Query query = entityManager.createQuery(jpql);// 创建一个Query对象来执行查询的语句
        // 发送查询,并封装结果集
        List list = query.getResultList();
        for (Object object : list) {
            System.out.println("object: " + object);
        }
        // 4. 提交事务
        transaction.commit();
        // 5. 释放资源
        entityManager.close();
    }

    /**
     * 统计查询: 需要注意的是不能使用count(*)来进行统计查询,必须使用属性名(对应着表的字段)
     * sql: select count(cust_id) from cst_customer
     * jpql: select count(custId) from Customer
     */
    @Test
    public void testCount() {
        // 1. 获取EntityManager,通过JpaUtils工具类
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 获取事务对象,并开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 执行增删改查
        // i 创建Query对象
        // ii 修改参数
        // iii 发送查询,并封装结果集
        String jpql = "select count(custId) from Customer";
        Query query = entityManager.createQuery(jpql);// 创建一个Query对象来执行查询的语句
        // 发送查询,并封装结果集
        Long rows = (Long) query.getSingleResult();// 方法的返回结果是一个Object类型
        System.out.println("总共查询到了" + rows + "条数据");
        // 4. 提交事务
        transaction.commit();
        // 5. 释放资源
        entityManager.close();
    }

    /**
     * 分页查询
     * sql: select * from cst_customer limit 0,2
     * jpql: from Customer,然后对分页参数进行赋值
     */
    @Test
    public void testPaged() {
        // 1. 获取EntityManager,通过JpaUtils工具类
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 获取事务对象,并开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 执行增删改查
        // i 创建Query对象
        // ii 对参数赋值(对占位符?进行赋值)
        // iii 发送查询,并封装结果集
        String jpql = "from Customer order by custId desc";
        Query query = entityManager.createQuery(jpql);// 创建一个Query对象来执行查询的语句
        query.setFirstResult(2);
        query.setMaxResults(2);
        List list = query.getResultList();
        for (Object object : list) {
            System.out.println(object);
        }
        // 4. 提交事务
        transaction.commit();
        // 5. 释放资源
        entityManager.close();
    }

    /**
     * 条件查询
     * sql: select * from cst_customer where cust_name like "传志%"
     * jpql: from Customer where custName like ?
     */
    @Test
    public void testCondition() {
        // 1. 获取EntityManager,通过JpaUtils工具类
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 获取事务对象,并开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 执行增删改查
        // i 创建Query对象
        // ii 对参数赋值(对占位符?进行赋值)
        // iii 发送查询,并封装结果集
        String jpql = "from Customer where custName like ?";
        Query query = entityManager.createQuery(jpql);// 创建一个Query对象来执行查询的语句
        query.setParameter(1, "传志%");// 第一个参数是第几个占位符,第二个参数是值
        List list = query.getResultList();
        for (Object object : list) {
            System.out.println(object);
        }
        // 4. 提交事务
        transaction.commit();
        // 5. 释放资源
        entityManager.close();
    }
}
