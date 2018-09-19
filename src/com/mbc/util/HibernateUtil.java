package com.mbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	public static final Configuration CFG;
	public static final SessionFactory SF;
	
	static {
		CFG = new Configuration().configure();
		SF = CFG.buildSessionFactory();
	}
	
	public static Session openSession() {
		return SF.openSession();
	}
}
