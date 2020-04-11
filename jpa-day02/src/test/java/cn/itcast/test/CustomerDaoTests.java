package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// 首先是设置添加注解,使用Spring容器整合的测试方法
@RunWith(SpringJUnit4ClassRunner.class) // 声明是使用Spring提供的单元测试环境
// 指定Spring容器的配置文件
// @ContextConfiguration中也可以通过指定配置类的方法,因为这里用的是spring配置文件不是Spring管理的配置类.
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerDaoTests {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindById(){
        Optional<Customer> customer = customerDao.findById(5L);
        System.out.println(customer);
    }

    /**
     * 这个save()方法: 保存或者更新
     * 使用save方法的时候建议最好包含id属性,如果不设置id属性,那么就是执行的新增操作
     * save方法,不管是保存还是更新一条数据,都要先根据id主键查询用户数据,然后再执行保存和更新.
     * 如果确定是要新增一条数据,则因为主键id还不存在,所以不需要设置这个属性.
     */
    @Test
    public void testSave(){
        Customer customer = new Customer();
        customer.setCustName("黑马程序员真不赖");
        customer.setCustLevel("VIP");
        customer.setCustIndustry("it教育");
        customer.setCustId(9L);
        customerDao.save(customer);
    }

    @Test
    public void testDelete(){
        // java1.8之后的根据id删除数据的方法用的是deleteById(4L);
        // 注意执行的流程是: 先根据id铲鲟数据,当查询的结果不为null,则执行删除数据的操作.
        // 如果查询的结果为null,则会抛出异常org.springframework.dao.EmptyResultDataAccessException
        boolean exists = customerDao.existsById(4L);
        System.out.println("id为4的数据是否存在:" + exists);
        if (exists){
            customerDao.deleteById(4L);// 有异常风险存在,注意当第二次执行的时候会抛出上面的异常
        }else{
            System.out.println("删除数据失败,要删除的数据不存在!!!");
        }

    }

    /**
     * 查询所有数据的方法findAll(),可以进行排序.默认是升序asc排序
     */
    @Test
    public void testFindAll(){
        List<Customer> customers = customerDao.findAll();
        for (Customer customer: customers){
            System.out.println(customer);
        }
        System.out.println("---------------------------");
        List<Customer> customers1 = customerDao.findAll(Sort.by(Sort.Order.desc("custId")));
        for (Customer customer: customers1){
            System.out.println(customer);
        }
    }

    /**
     * 进行统计查询,查询表中酷虎数据的条数
     */
    @Test
    public void testCount(){
        long count = customerDao.count();
        System.out.println(count);
    }

    /**
     * 测试exists()方法
     * 这个方法是判断数据库中是否存在某条数据
     */
    @Test
    public void testExists(){
        boolean exists = customerDao.existsById(4L);
        System.out.println("id为4的数据是否存在:" + exists);
    }

    /**
     * 与findById()方法不同的是:
     * findById()方法的底层实现是通过实体管理器EntityManager对象调用的find()方法,是立即加载(立即初始化)
     * getById()方法是调用的getReference()方法,这个方法是延迟加载(懒惰初始化)
     */
    @Test
    @Transactional // 添加一个事务注解的目的是为了在测试的时候不报错
    public void testGetOneById(){
        Customer customer = customerDao.getOne(5L);
        System.out.println(customer);
    }
}

