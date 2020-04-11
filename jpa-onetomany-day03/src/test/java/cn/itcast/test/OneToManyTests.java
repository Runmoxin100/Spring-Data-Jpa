package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.dao.LinkManDao;
import cn.itcast.domain.Customer;
import cn.itcast.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTests {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;

    @Test
    @Transactional // 由于多个事务,所以添加需要事务管理注解
    @Rollback(value = false) // 在测试的时候,事务是都不会真的提交的,测试完成后会默认回滚事务
    public void testAdd() {
        // 要保存一个客户和一个联系人,需要先创建一个实例对象来封装信息
        Customer customer = new Customer();
        customer.setCustName("百度1");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李1");
        // 建立两张表的关系,在客户的属性linkMans集合中添加元素对象linkMan
        // 这样就建立了联系,在从表中的外键字段就会显示主表的主键字段的值
        customer.getLinkMans().add(linkMan);
        // 如果一的一方放弃了外键的维护权,那么下面的建立多对一的关系的代码不能省略
        // 否则在从表中不会显示联系人外键字段的值
        linkMan.setCustomer(customer);
        // 保存答数据库表中
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 测试级联操作(注意: 要把配置文件中自动生成表配置的create改成update)
     * 低版本的hibernate可以使用使用findOne方法,让然后在使用delete(Entity)
     * 高版本的使用的时deleteById(),封装好的方法.会自动先查询再删除
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testCascade() {
        boolean existsById = customerDao.existsById(1L);
        System.out.println(existsById);
        if (existsById) {
            // 执行删除的操作
            customerDao.deleteById(1L);
        }else {
            System.out.println("删除数据失败,要删除的数据不存在!!!");
        }
    }
}
