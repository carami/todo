package carami.jdbc;


import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcTest {
	
	@Test
	public void connectionTest() throws Exception{
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tododb", "carami", "carami");
		Assert.assertNotNull(connection);
	}
}
