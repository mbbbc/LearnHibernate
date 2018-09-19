package com.mbc.test;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.mbc.domain.Customer;
import com.mbc.util.HibernateUtil;

public class Test1 {
	
	@Test
	/**
	 * 入门测试程序
	 */
	public void test1() {
		//1.加载Hibernate的核心配置文件
		Configuration configure = new Configuration().configure();
		/*//核心配置文件为属性文件的方式，默认加载的文件名为hibernate.properties
		Configuration configure = new Configuration();
		//手动加载映射
		configure.addResource("com/mbc/domain/Customer.hbm.xml");*/
		//2.创建一个SessionFactory对象：类似以JDBC中的连接池
		SessionFactory sessionFactory = configure.buildSessionFactory();
		//3.通过SessionFactory获取到Session对象，类似于JDBC中Connection
		Session session = sessionFactory.openSession();
		//4.开启事务
		Transaction transaction = session.beginTransaction();
		//5.编写代码
		Customer customer = new Customer();
		customer.setCust_name("张三");
		session.save(customer);
		//6.提交事务
		transaction.commit();
		//7.资源释放
		session.close();
	}
	
	@Test
	/**
	 * 入门测试程序
	 * 使用工具类获得Session对象
	 */
	public void test2() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		Customer customer = new Customer();
		customer.setCust_name("李四");
		session.save(customer);
		transaction.commit();
		session.close();
	}
	
	/**
	 * Session的API测试,save
	 */
	@Test
	public void testSave() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		Customer customer = new Customer();
		customer.setCust_name("张三");
		session.save(customer);
		
		transaction.commit();
		session.close();
	}
	
	
	/**
	 * Session的API测试,get和load
	 */
	@Test
	public void testGetAndLoad() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		//get：当执行时，会马上发送SQL语句去查询
		Customer customer1 = session.get(Customer.class, 1L);					
		System.out.println(customer1);
		
		//load：延迟加载
		Customer customer2 = session.load(Customer.class, 5L);
		//当要使用查询的对象时，才发送SQL去查询，即执行以下代码时才发送SQL语句
		System.out.println(customer2);
		
		transaction.commit();
		session.close();
	}
	
	/**
	 * Session的API测试,update
	 */
	@Test
	public void testUpdate() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		//直接创建对象，进行修改
		Customer customer1 = new Customer();
		customer1.setCust_id(1L);
		customer1.setCust_name("小明");
		session.update(customer1);
		
		//先查询，再修改(通常使用这种方法)
		Customer customer2 = session.get(Customer.class, 5L);
		customer2.setCust_name("大明");
		session.update(customer2);
		
		transaction.commit();
		session.close();
	}

	/**
	 * Session的API测试,delete
	 */
	@Test
	public void testDelete() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		//直接创建对象，进行删除
		Customer customer1 = new Customer();
		customer1.setCust_id(1L);
		session.delete(customer1);
		
		//先查询，再删除(通常使用这种方法，可以级联删除)
		Customer customer2 = session.get(Customer.class, 5L);
		session.delete(customer2);
		
		transaction.commit();
		session.close();
	}

	/**
	 * Session的API测试,saveOrUpdate(Object obj)
	 */
	@Test
	public void testSaveOrUpdate() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		//若数据库中不存在记录，进行保存(save)操作
		Customer customer1 = new Customer();
		customer1.setCust_name("小小明");
		session.saveOrUpdate(customer1);
		
		//若数据库中存在记录，进行跟新(update)操作
		Customer customer2 = session.get(Customer.class, 3L);
		customer2.setCust_name("大大明");
		session.saveOrUpdate(customer2);
		
		transaction.commit();
		session.close();
	}
	
	/**
	 * Session的API测试,查询所有
	 */
	@Test
	public void testQuery() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		//通过HQL方式，HQL(Hibernate Query Language)面向对象的查询语言
		Query query = session.createQuery("from Customer");
		List<Customer> list1 = query.list();
		for (Customer customer : list1) {
			System.out.println(customer);
		}
		
		//用过SQL方式
		SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM cst_customer");
		List<Object[]> list2 = sqlQuery.list();
		for (Object[] objects : list2) {
			System.out.println(Arrays.toString(objects));
		}
		
		transaction.commit();
		session.close();
	}
}
