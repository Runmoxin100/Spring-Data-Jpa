package cn.itcast.domain;

import javax.persistence.*;

/**
 * 客户的实体类
 *      jpa规范中规定了使用注解的方式配置映射关系
 *      配置映射关系:
 *          1.实体类和表的映射关系
 *              @Entity:声明这是一个实体类
 *              @Table:配置实体类和表的映射关系
 *                  name:数据库中的表的名称
 *          2.实体类的属性和表的字段的映射关系
 */
@Entity
@Table(name = "cst_customer")

public class Customer {

    /**
     * @Id:声明主键的配置
     * @GeneratedValue: 配置主键的生成策略
     *      GenerationType.IDENTITY: 自增 mysql
     *          *如果底层的数据库支持自增,那么使用这个属性值
     *      GenerationType.SEQUENCE: 序列 oracle
     *          *底层的数据库必须支持序列
     *      GenerationType.TABLE: jpa提供的一种机制,通过一张数据库表的形式帮助我们完成自增
     *      GenerationType.AUTO:由程序帮助我们自动的选择生成策略
     *
     * @Column: 配置属性和字段的映射关系
     *      name: 数据库表中字段的名称
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private long custId; // 客户主键

    @Column(name = "cust_name")
    private String custName; // 客户名称

    @Column(name = "cust_source")
    private String custSource; // 客户来源

    @Column(name = "cust_level")
    private String custLevel; // 客户级别

    @Column(name = "cust_industry")
    private String custIdustry; // 客户所属行业

    @Column(name = "cust_phone")
    private String custPhone; // 客户联系方式

    @Column(name = "cust_address")
    private String custAddress; // 客户地址

    public long getCustId() {
        return custId;
    }

    public String getCustName() {
        return custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public String getCustIdustry() {
        return custIdustry;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public void setCustIdustry(String custIdustry) {
        this.custIdustry = custIdustry;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custIdustry='" + custIdustry + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", custAddress='" + custAddress + '\'' +
                '}';
    }
}
