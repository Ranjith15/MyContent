package mongodb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.edassist.constants.MongoConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.edassist.models.domain.Email;
import com.edassist.mongodb.config.MongoConfig;
import com.edassist.mongodb.repository.EmailRepository;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoConfig.class })
public class EmailRepositoryTest {

	@Autowired
	private EmailRepository emailRepository;

	@Test
	public void findResetPasswordEmail() {
		Email email = emailRepository.findByName("login", "ResetPassword", "168", "ClientDefault");
		assertNotNull(email);
	}

	@Test
	public void findByClient() {
		List<Email> emails = emailRepository.findByClient(MongoConstants.CLIENT_CISCO);
		assertNotNull(emails);
		assertEquals(16, emails.size());
	}

	@Test
	public void findByComponentAndClient() {
		List<Email> emailListNoProgram = emailRepository.findByComponentAndClient("participant", "166");

		assertNotNull(emailListNoProgram);
		assertEquals(14, emailListNoProgram.size());
	}

	@Test
	public void findByComponentAndClientEmptyProgram() {
		List<Email> emailListNoProgram = emailRepository.findByComponentAndClientAndProgram("participant", "168", "957");

		assertNotNull(emailListNoProgram);
		assertEquals(0, emailListNoProgram.size());
	}

}
