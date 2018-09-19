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
	 * ���Ų��Գ���
	 */
	public void test1() {
		//1.����Hibernate�ĺ��������ļ�
		Configuration configure = new Configuration().configure();
		/*//���������ļ�Ϊ�����ļ��ķ�ʽ��Ĭ�ϼ��ص��ļ���Ϊhibernate.properties
		Configuration configure = new Configuration();
		//�ֶ�����ӳ��
		configure.addResource("com/mbc/domain/Customer.hbm.xml");*/
		//2.����һ��SessionFactory����������JDBC�е����ӳ�
		SessionFactory sessionFactory = configure.buildSessionFactory();
		//3.ͨ��SessionFactory��ȡ��Session����������JDBC��Connection
		Session session = sessionFactory.openSession();
		//4.��������
		Transaction transaction = session.beginTransaction();
		//5.��д����
		Customer customer = new Customer();
		customer.setCust_name("����");
		session.save(customer);
		//6.�ύ����
		transaction.commit();
		//7.��Դ�ͷ�
		session.close();
	}
	
	@Test
	/**
	 * ���Ų��Գ���
	 * ʹ�ù�������Session����
	 */
	public void test2() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		Customer customer = new Customer();
		customer.setCust_name("����");
		session.save(customer);
		transaction.commit();
		session.close();
	}
	
	/**
	 * Session��API����,save
	 */
	@Test
	public void testSave() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		Customer customer = new Customer();
		customer.setCust_name("����");
		session.save(customer);
		
		transaction.commit();
		session.close();
	}
	
	
	/**
	 * Session��API����,get��load
	 */
	@Test
	public void testGetAndLoad() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		//get����ִ��ʱ�������Ϸ���SQL���ȥ��ѯ
		Customer customer1 = session.get(Customer.class, 1L);					
		System.out.println(customer1);
		
		//load���ӳټ���
		Customer customer2 = session.load(Customer.class, 5L);
		//��Ҫʹ�ò�ѯ�Ķ���ʱ���ŷ���SQLȥ��ѯ����ִ�����´���ʱ�ŷ���SQL���
		System.out.println(customer2);
		
		transaction.commit();
		session.close();
	}
	
	/**
	 * Session��API����,update
	 */
	@Test
	public void testUpdate() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		//ֱ�Ӵ������󣬽����޸�
		Customer customer1 = new Customer();
		customer1.setCust_id(1L);
		customer1.setCust_name("С��");
		session.update(customer1);
		
		//�Ȳ�ѯ�����޸�(ͨ��ʹ�����ַ���)
		Customer customer2 = session.get(Customer.class, 5L);
		customer2.setCust_name("����");
		session.update(customer2);
		
		transaction.commit();
		session.close();
	}

	/**
	 * Session��API����,delete
	 */
	@Test
	public void testDelete() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		//ֱ�Ӵ������󣬽���ɾ��
		Customer customer1 = new Customer();
		customer1.setCust_id(1L);
		session.delete(customer1);
		
		//�Ȳ�ѯ����ɾ��(ͨ��ʹ�����ַ��������Լ���ɾ��)
		Customer customer2 = session.get(Customer.class, 5L);
		session.delete(customer2);
		
		transaction.commit();
		session.close();
	}

	/**
	 * Session��API����,saveOrUpdate(Object obj)
	 */
	@Test
	public void testSaveOrUpdate() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		//�����ݿ��в����ڼ�¼�����б���(save)����
		Customer customer1 = new Customer();
		customer1.setCust_name("СС��");
		session.saveOrUpdate(customer1);
		
		//�����ݿ��д��ڼ�¼�����и���(update)����
		Customer customer2 = session.get(Customer.class, 3L);
		customer2.setCust_name("�����");
		session.saveOrUpdate(customer2);
		
		transaction.commit();
		session.close();
	}
	
	/**
	 * Session��API����,��ѯ����
	 */
	@Test
	public void testQuery() {
		Session session = HibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		
		//ͨ��HQL��ʽ��HQL(Hibernate Query Language)�������Ĳ�ѯ����
		Query query = session.createQuery("from Customer");
		List<Customer> list1 = query.list();
		for (Customer customer : list1) {
			System.out.println(customer);
		}
		
		//�ù�SQL��ʽ
		SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM cst_customer");
		List<Object[]> list2 = sqlQuery.list();
		for (Object[] objects : list2) {
			System.out.println(Arrays.toString(objects));
		}
		
		transaction.commit();
		session.close();
	}
}
