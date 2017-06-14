package db;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class DbManager {
	private static SessionFactory factory;

	public DbManager() {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

		Configuration config = new Configuration();
		config.configure();

		factory = config.buildSessionFactory();
	}

	public void addItem(DbInterface item) {
		Transaction tx = null;
		Session session = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.save(item);
			tx.commit();

		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void update(DbInterface item) {
	}

	public void delete(DbInterface item) {
		Transaction tx = null;
		Session session = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.delete(item);
			tx.commit();

		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null) {

				session.close();
			}
		}
	}

	public List topByTime(int num) {
		Session session = factory.openSession();
		Query query = session.createQuery("FROM Records E " + "ORDER BY E.Time DESC");
		query.setMaxResults(num);

		session.close();
		return query.list();
	}

	public List<Records> recordsByLevels(String levelId, String parameter, int num) {
		Session session = factory.openSession();
		Query query = session.createQuery(
				"From Records Where LevelId LIKE " + "'" + levelId + "'" + " ORDER BY " + parameter + " ASC");
		query.setMaxResults(num);
		List<Records> list = query.list();
		session.close();
		return list;
	}

	//// checking if there is from specific table and specific column that
	//// parameter
	// for example to check if some Eden finish anymap OR if there is Eden
	//// player on the database.
	public boolean checkIfExist(String table, String column, String parameter) {
		Session session = factory.openSession();
		Query query = session.createQuery("FROM " + table + " Where " + column + " LIKE " + "'" + parameter + "'");
		List<DbInterface> list = query.list();
		if (list.size() > 0)
			return true;
		return false;
	}

	public List<Records> recordByNickname(String Nickname, String parameter) {
		Session session = factory.openSession();
		Query query = session.createQuery(
				"From Records Where Nickname LIKE " + "'" + Nickname + "'" + "ORDER BY " + parameter + " ASC");
		List<Records> list = query.list();
		session.close();
		return list;
	}

	public void addRecord(String Nickname, String LevelId, int Time, int Steps) {
		Users user;
		Records record;
		boolean thereisNickname = checkIfExist("Users", "Nickname", Nickname);
		if (!thereisNickname) {// if there arent that nickname on the Users
								// table.
			System.out.println("There is not a User name:" + Nickname + " on the records.");
			user = new Users(Nickname);
			this.addItem(user);
		}
		record = new Records(Nickname, LevelId, Steps, Time);
		this.addItem(record);// adding new record to recordTable
	}
}