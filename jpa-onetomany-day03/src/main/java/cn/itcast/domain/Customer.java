package cn.itcast.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 1. 实体类和表的映射关系
 * @Entity
 * @Table
 * 2. 实体类的属性和表字段的映射关系
 * @Id
 * @GeneratedValue
 * @Column
 */
@Entity
@Table(name = "cst_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId;
    @Column(name = "cust_address")
    private String custAddress;
    @Column(name = "cust_industry")
    private String custIndustry;
    @Column(name = "cust_level")
    private String custLevel;
    @Column(name = "cust_name")
    private String custName;
    @Column(name = "cust_phone")
    private String custPhone;
    @Column(name = "cust_source")
    private String custSource;

    // 配置客户和联系人之间的关系(一对多的关系)
    /*
    一个客户可以包含多个联系人,则建议使用set集合的方式保存联系人信息
        使用注解的方式配置多表关系
            1. 声明关系: @OneToMany注解就是配置一对多关系, 属性targetEntity的值是对方的字节码对象
            2. 配置外键(中间表): @JoinColumn
                name: 外键字段的名称
                referencedColumnName: 要参照的主表的主键字段的名称
        在客户实体类上(一的一方),添加了外键设置,所以对客户而言,也具备了维护外键的作用.
     */
//    @OneToMany(targetEntity = LinkMan.class)
//    @JoinColumn(name = "lkm_cust_id", referencedColumnName = "cust_id")
    // 将上面的注解注释掉的原因是为了让一的一方放弃对外键的维护
    // 但是一对多关系的声明式必须的,可以使用mappedBy属性的值=对方配置关系的属性名即可
    /*
    在多表映射配置的注解中,有cascade属性,用来配置级联操作
        CascadeType.ALL: 代表的是,对数据库表的所有的操作都是级联操作
        CascadeType.REMOVE: 删除操作时级联操作
        CascadeType.PERSIST: 保存操作时级联操作
        CascadeType.MERGE: 更新数据时级联操作
        推荐使用All
     注意: 级联操作的配置需要在操作的主体上面的,配置映射的注解里面添加cascade属性的值
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<LinkMan> linkMans = new HashSet<LinkMan>();

    public Set<LinkMan> getLinkMans() {
        return linkMans;
    }

    public void setLinkMans(Set<LinkMan> linkMans) {
        this.linkMans = linkMans;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custAddress='" + custAddress + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custName='" + custName + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", custSource='" + custSource + '\'' +
                '}';
    }
}
