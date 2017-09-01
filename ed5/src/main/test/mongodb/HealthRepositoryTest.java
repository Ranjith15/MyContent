package mongodb;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.edassist.models.domain.Health;
import com.edassist.mongodb.config.MongoConfig;
import com.edassist.mongodb.repository.HealthRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoConfig.class })
public class HealthRepositoryTest {

	@Autowired
	private HealthRepository healthRepository;

	@Test
	public void checkConnection() {

		Health health = healthRepository.findByStatus("healthCheck");
		assertNotNull(health);
	}
}