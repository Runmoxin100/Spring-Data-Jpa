package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 首先是设置添加注解,使用Spring容器整合的测试方法
@RunWith(SpringJUnit4ClassRunner.class) // 声明是使用Spring提供的单元测试环境
// 指定Spring容器的配置文件
// @ContextConfiguration中也可以通过指定配置类的方法,因为这里用的是spring配置文件不是Spring管理的配置类.
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpqlTests {

    @Autowired
    private CustomerDao customerDao;


    @Test
    public void testFindByName(){
        Customer customer = customerDao.findByName("传智教育");
        System.out.println(customer);
    }

    @Test
    public void testFindByNameAndId(){
        Customer customer = customerDao.findByNameAndId(8L, "黑马程序员");
        System.out.println(customer);
    }

    /**
     * 测试jpql的更新操作
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateName(){
        customerDao.updateById(8L, "Spring data jpa");
        System.out.println("成功的执行更新OK");
    }

    /**
     * 测试spring Data Jpa接口中的Sql查询
     */
    @Test
    public void testFindSql(){
        List<Customer> customers = customerDao.findSql();
        for (Customer customer : customers){
            System.out.println(customer);
        }
    }
}

