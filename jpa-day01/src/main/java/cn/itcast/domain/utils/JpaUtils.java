package cn.itcast.domain.utils;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 解决实体管理器工厂浪费资源和耗时的问题
 *      通过静态代码块的形式,当程序第一次访问此工具类时,创建一个公共的实体管理器工厂对象.
 * 当第一次方法getEntityManager方法的时候,通过静态块创建一个factory对象,再创建一个EntityManager对象.
 * 当第二次调用这个getEntityManager方法的时候,直接通过已经创建好的factory对象来创建EntityManager对象.
 *
 */
public class JpaUtils {

    private static EntityManagerFactory factory;

    static {
        // 1. 通过加载Jpa的核心配置文件,创建EntityManagerFactory对象.
        factory = Persistence.createEntityManagerFactory("myJpa");
    }

    /**
     * 2. 提供一个获取EntityManager类的实例对象的方法
     */
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
