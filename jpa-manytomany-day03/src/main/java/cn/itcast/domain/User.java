package cn.itcast.domain;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "age")
    private Integer age;

    /*
    配置多对多的映射关系
        1. 声明表关系的配置注解
            @ManyToMany(targetEntity = Role.class) // 声明表的关系: 多对多
            targetEntity: 代表对方实体类的calss对象(字节码文件)
        2. 配置中间表(包含连个外键)
            @JoinTable注解配置中间表
                name: 中间表的名称
                joinColumns: 配置当前对象的中间表的外键
                    @JoinColumn的数组
                        name: 外键名
                        referencedColumnName: 参照的主表的主键名
                inverseJoinColumns: 配置对方对象的中间表的外键
                     @JoinColumn的数组
                        name: 外键名
                        referencedColumnName: 参照的主表的主键名
     */
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "sys_user_role",
                joinColumns = {@JoinColumn(name = "sys_user_id", referencedColumnName = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "sys_role_id", referencedColumnName = "role_id")})
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
