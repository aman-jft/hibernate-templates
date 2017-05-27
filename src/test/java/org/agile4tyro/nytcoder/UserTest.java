package org.agile4tyro.nytcoder;

import org.agile4tyro.nytcoder.db.User;
import org.agile4tyro.nytcoder.template.HibernateTemplate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import junit.framework.TestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest extends TestCase {
	private final static SessionFactory sessionFactory = HibernateConfig.getConfiguration();

	public void test1_Connection() {
		HibernateTemplate.runQuery(sessionFactory, (Session session) -> {
			User user = (User) session.createQuery("FROM User").uniqueResult();
			assertNull(user);
		});
	}

	public void test2_UserCreateDelete() {
		final User user = User.builder()
			.username("aman")
			.password("Goel")
			.build();
		HibernateTemplate.runQuery(sessionFactory, (Session session) -> {
			session.save(user);
			User dbUser = session.get(User.class, 1l);
			assertNotNull(dbUser);
			assertEquals(dbUser.getId(), 1l);
			assertEquals(user.getUsername(), dbUser.getUsername());
			assertEquals(user.getPassword(), dbUser.getPassword());
			assertEquals(user.getCreatedOn(), dbUser.getCreatedOn());
			assertEquals(user.getDeleted(), dbUser.getDeleted());
		});
		HibernateTemplate.runQuery(sessionFactory, (Session session) -> {
			User dbUser = session.get(User.class, 1l);
			assertNotNull(dbUser);
			session.delete(dbUser);
			dbUser = session.get(User.class, 1l);
			assertNull(dbUser);
		});
	}

	public void test3_Transaction() {
		final User user = User.builder()
			.username("Hitesh")
			.password("Yadav")
			.build();

		HibernateTemplate.runQuery(sessionFactory, (Session session) -> {
			session.save(user);
			User dbUser = (User) session.createQuery("FROM User").uniqueResult();
			assertNotNull(dbUser);
			assertEquals(user.getUsername(), dbUser.getUsername());

			// Open new session
			HibernateTemplate.runQuery(sessionFactory, (Session newSession) -> {
				User testUser = (User) newSession.createQuery("FROM User").uniqueResult();
				assertNull(testUser);
			});
		});
		HibernateTemplate.runQuery(sessionFactory, (Session session) -> {
			User dbUser = (User) session.createQuery("FROM User").uniqueResult();
			assertNotNull(dbUser);
			session.delete(dbUser);
			dbUser = (User) session.createQuery("FROM User").uniqueResult();
			assertNull(dbUser);
		});
	}

	public void test4_CurrentSession() {
		try {
			Session session = sessionFactory.getCurrentSession();
			fail("Should have thrown Hibernate exception");
		} catch (Exception e) {
			assertTrue(e instanceof HibernateException);
		}
	}

}
