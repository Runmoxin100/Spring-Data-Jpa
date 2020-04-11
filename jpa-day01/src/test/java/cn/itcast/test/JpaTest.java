package cn.itcast.test;

import cn.itcast.domain.Customer;
import cn.itcast.domain.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {
    /**
     * 测试jpa的保存:
     *      案例:保存一个客户到数据库中
     * Jpa的操作步骤:
     *      1. 加载配置文件创建工厂对象(这个工厂是实体管理器工厂).
     *      2. 通过实体管理器工厂来获取实体管理器
     *      3. 获取事务对象,启动事务
     *      4. 完成增删改查的操作
     *      5. 提交事务(或者回滚事务)
     *      6. 释放资源
     */

    @Test
    public void testSave(){
//        // 1. 加载配置文件创建工厂对象(这个工厂是实体管理器工厂).
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
//        // 2. 通过实体管理器工厂来获取实体管理器
//        EntityManager em = factory.createEntityManager();
        EntityManager em = JpaUtils.getEntityManager();// 通过工具类获取EntityManager对象
// 3. 获取事务对象,启动事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // 4. 完成增删改查的操作,保存一个客户到数据库中
        Customer customer = new Customer();
        customer.setCustName("传志直播");
        customer.setCustIdustry("演员");
        em.persist(customer); // 保存操作.  通过实体管理器调用方法来保存客户信息到数据库表中
        // 5. 提交事务(或者回滚事务)
        tx.commit();
        // 6. 释放资源
        // 需要注意的是资源的释放顺序,正好和开启顺序倒置.先开启的资源要最后关闭.因为存在依赖的关系
        em.close();
//        factory.close();



    }
    /**
     * 根据id查询客户信息
     */

    @Test
    public void testFind(){
        // 1. 通过工具类JpaUtils来获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 通过实体管理器获取实体事务对象EntityTransaction,并开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 执行增删改查 --根据id查询客户数据
        /**
         * find(Customer.class, 1L);这个方法中需要传入两个参数
         *      class: 查询数据的结果需要包装的实体类类型的字节码(也就是用于封装查询结果的实体类的类对象)
         *      id:查询的主键的取值
         * 立即加载:调用方法的时候会执行查询语句,并立即加载,然后创建一个实例对象.
         */
        Customer customer = entityManager.find(Customer.class, 3L);
        System.err.println("\t执行JpaTest.testFind(): "+customer);
        // 4. 提交事务
        transaction.commit();
        // 5. 释放资源
        entityManager.close();
    }

    @Test
    public void testReference(){
        // 1. 通过工具类JpaUtils来获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 通过实体管理器获取实体事务对象EntityTransaction,并开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 执行增删改查 --根据id查询客户数据
        /**
         * getReference(Customer.class, 3L);也是根据id查询数据
         *      class: 查询数据的结果需要包装的实体类类型的字节码(也就是用于封装查询结果的实体类的类对象)
         *      id:查询的主键的取值
         *延迟加载(懒惰加载):
         *      *当执行这个查询的时候,显示得到一个动态代理对象
         *      *什么时候用,什么时候才会区查询
         *
         */
        Customer customer = entityManager.getReference(Customer.class, 3L);
        System.out.println("\t执行JpaTest.testFind(): "+customer);
        // 4. 提交事务
        transaction.commit();
        // 5. 释放资源
        entityManager.close();
    }

    /**
     * 删除一条数据的案例
     */
    @Test
    public void testRemove(){
        // 1. 通过工具类JpaUtils来获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 通过实体管理器获取实体事务对象EntityTransaction,并开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 执行增删改查 --删除一条数据的案例
        // i 首先应该先查询数据是否存在
        // ii 然后再执行删除数据的操作
        Customer customer = entityManager.find(Customer.class, 1L);
        if(customer != null){
            entityManager.remove(customer);
            System.out.println("根据id删除了一条数据");
        }else {
            System.out.println("删除数据失败,你要删除的数据不存在!!!");
        }
        // 4. 提交事务
        transaction.commit();
        // 5. 释放资源
        entityManager.close();
    }

    /**
     * 更新客户数据的操作
     */
    @Test
    public void testMerge(){
        // 1. 通过工具类JpaUtils来获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 通过实体管理器获取实体事务对象EntityTransaction,并开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 执行增删改查 --更新客户数据的操作
        // i 要更新一条客户的数据,首先要做的还是查询一条数据
        Customer customer = entityManager.find(Customer.class, 5L);
        // ii 然后才是执行修改更新的操作
        if(customer != null){
            customer.setCustIdustry("汽车");
            entityManager.merge(customer);
            System.out.println("更新数据成功");
        }else {
            System.out.println("更新数据失败,你要更新的数据不存在!!!");
        }
        // 4. 提交事务
        transaction.commit();
        // 5. 释放资源
        entityManager.close();
    }
}
