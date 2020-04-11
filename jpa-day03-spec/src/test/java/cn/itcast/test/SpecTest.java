package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testSpec(){
        /**
         * 匿名内部类的方式,创建查询条件Specification
         * 自定义查询条件:
         *      1. 要实现Specification接口(提供泛型,也就是查询对象的类型)
         *      2. 实现toPredicate方法(构造查询条件)
         *      3. 需要借助方法参数中的两个参数(
         *          root: 获取需要查询的对象属性
         *          CriteriaBuilder:构造查询条件的,内部封装了许多的查询条件(模糊查询, 精准查询等)
         *      )
         * 案例:根据客户名称查询酷虎信息(
         * 首先要考虑的就是如果构造查询条件:
         *      1. 用于比较的属性的名称(这个可以通过root来获取)
         *      2. 查询的方式(在CriteriaBuilder对象中封装了很多查询方式)
         * )
         */
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");// 返回值是一个path对象
                /**
                 * equal()精准匹配用到的方法
                 * 第一个参数是:path对象,包含了用于查询的属性
                 * 第二个参数是: 精准匹配的值(=号后面的值)
                 */
                Predicate predicate = criteriaBuilder.equal(custName, "传智教育");
                return predicate;
            }
        };
        Optional<Customer> customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    @Test
    public void testSpec1(){
        // 案例: 根据客户名和客户行业查询客户信息
        /*
        root: 获取属性
            1. 客户名
            2. 客户行业
        criteriaBuilder: 构造查询条件
            1. 根据客户名精准匹配
            2. 根据客户行业精准匹配
            3. 将以上两个查询联系起来
         */
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");// 获取客户名属性
                Path<Object> custIndustry = root.get("custIndustry");// 获取客户的行业属性

                // 构造查询条件
                // 1. 根据客户名精准匹配
                Predicate p1 = criteriaBuilder.equal(custName, "传智教育");
                // 2. 根据客户行业精准匹配
                // 第一个参数是path属性,第二个参数是属性的取值
                Predicate p2 = criteriaBuilder.equal(custIndustry, "it教育");
                // 3. 将多个查询条件组合到一起(and或者or)
                Predicate and = criteriaBuilder.and(p1, p2);
                // 4. 最后是将查询条件作为返回值返回即可
                return and;
            }
        };
        Optional<Customer> customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    @Test
    public void testSpec2(){
        /*
        案例:完成根据客户名称模糊匹配,返回查询列表
         */
        // 构造查询条件
        Specification<Customer> specification = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 获取查询属性 客户名
                Path<Object> custName = root.get("custName");
                // 查询方式 模糊匹配
                /**
                 * 这里需要注意的是:
                 * 1. 如果是精准匹配的方法equal()方法,可以直接对path属性进行赋值进行比较了
                 * 2. 对于gt, lt, ge, le , like这些方法:
                 *      得到path对象, 需要根据path指定比较的参数类性,然后再进行比较.
                 *      指定参数类型: path属性.as(类型的字节码对象)
                 */
                Predicate like = criteriaBuilder.like(custName.as(String.class), "传%");

                return like;
            }
        };
//        List<Customer> customers = customerDao.findAll(specification);
//        int i = 0;
//        for (Customer customer: customers){
//            i++;
//            System.out.println("第"+i+"个:"+customer);
//        }
//        System.out.println("总共查询到"+i+"个Customer对象");
        /*
        添加排序: 在大多数的情况下查询列表都是需要排序的
        1. 创建排序对象Sort,需要调用构造方法实例化sort对象
            第一个参数:排序的顺序(降序, 升序)
                Sort.Direction.DESC : 降序
                Sort.Direction.ASC : 升序(默认的排序方式)
            第二个参数:排序的属性名称,也就是要按照什么排序
         */
        Sort sort = new Sort(Sort.Direction.DESC, "custId");
        List<Customer> customers = customerDao.findAll(specification, sort);
        for (Customer customer: customers){
            System.out.println(customer);
        }
    }

    /**
     * 分页查询
     * Specification: 用于自定义查询条件
     * Pageable: 分页参数,查询的页码和每页显示的条数(
     *      1. findAll(Specification, Pageable): 条件查询列表并分页
     *      2. findAll(Pageable): 查询所有并分页.(不设定查询条件)
     *      3. 方法的返回值是一个Page对象(Spring Data Jpa为我们封装好的pageBean对象)
     *          铜鼓哦这个对象调用方法,我们可以获取得到数据列表,数据的条数,总的页数等.
     * )
     */
    @Test
    public void testSpec3(){
        Specification<Customer> specification = null;
        /*
        PageRequest对象是Pageable的实现类
        创建PageRequest对象的时候需要调用它的构造方法,需要传入两个参数.
        第一个参数: 当前查询的页数(从0开始,代表第一页)
        第二个参数: 每页显示的数据的条数
         */
        Pageable pageable = new PageRequest(0,2);

        Page<Customer> page = customerDao.findAll(specification, pageable);
        System.out.println(page.getTotalElements());//
        System.out.println(page.getTotalPages());
        System.out.println(page.getContent());
    }
}
