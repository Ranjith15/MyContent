package mongodb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.edassist.constants.MongoConstants;
import com.edassist.models.domain.General;
import com.edassist.mongodb.config.MongoConfig;
import com.edassist.mongodb.repository.GeneralRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoConfig.class })
public class GeneralRepositoryTest {

	@Autowired
	private GeneralRepository generalRepository;

	@Test
	public void findById() {
		String id = "58a5c37d77a6335790ce729a";
		General content = generalRepository.findOne(id);

		assertNotNull(content);
		assertEquals("madsTest", content.getName());
	}

	@Test
	public void getLoginContent() {
		List<General> contents = generalRepository.findByComponent(MongoConstants.COMPONENT_LOGIN, "166", MongoConstants.PROGRAM_CLIENT_DEFAULT, new Date());

		assertNotNull(contents);
		assertEquals(4, contents.size());

		for (General content : contents) {
			if (content.getName() == "logo") {
				assertEquals("/images/mobile-client-logos/cisco", content.getFilePath());
			}
		}
	}

	@Test
	public void findAll() {
		List<General> contents = generalRepository.findAll();
		Long count = generalRepository.count();

		assertNotNull(contents);
		assertEquals(count.intValue(), contents.size());
	}

	@Test
	public void findAllPageable() {
		PageRequest pageableRequest = new PageRequest(0, 10);
		Page<General> page = generalRepository.findAll(pageableRequest);
		List<General> contents = page.getContent();

		assertNotNull(contents);
		assertEquals(10, contents.size());
	}

	@Test
	public void findByComponent() {
		List<General> contents = generalRepository.findByComponent(MongoConstants.COMPONENT_LOGIN, "166", MongoConstants.PROGRAM_CLIENT_DEFAULT, new Date());
		assertNotNull(contents);
		assertEquals(4, contents.size());
	}

	@Test
	public void findUsernameFor166() {
		List<General> contents = generalRepository.findByName(MongoConstants.COMPONENT_LOGIN, MongoConstants.NAME_USERNAME, "166", MongoConstants.PROGRAM_CLIENT_DEFAULT);
		assertNotNull(contents);
		assertEquals(1, contents.size());
		assertEquals("166", contents.get(0).getClient());
		assertEquals("<html>\n  <head />\n  <body>\n    <p>  Username</p> </body></html>\n", contents.get(0).getData());
	}

	@Test
	public void findClientDocumentsFor168() {
		List<General> contents = generalRepository.findByComponent(MongoConstants.COMPONENT_CLIENT_DOCUMENT, "168", MongoConstants.PROGRAM_957, new Date());
		assertNotNull(contents);
		assertEquals(2, contents.size());
		for (General content : contents) {
			assertEquals("programResources", content.getName());
		}
	}

	@Test
	public void findUniqueComponent() {
		List<String> distinctComponents = generalRepository.findUniqueComponents();
		assertNotNull(distinctComponents);
	}

	@Test
	public void findUniqueNamesForComponent() {
		List<String> distinctNames = generalRepository.findUniqueNamesForComponent("login");
		assertNotNull(distinctNames);
	}

	@Test
	public void findcaseInsensitiveName() {
		List<General> contents = generalRepository.findByName(MongoConstants.COMPONENT_SITE, MongoConstants.NAME_TERMS_AND_CONDITIONS, "168", MongoConstants.PROGRAM_CLIENT_DEFAULT);
		assertNotNull(contents);
		assertEquals(1, contents.size());
		assertEquals("168", contents.get(0).getClient());
	}

	@Test
	public void findAgreementsBetweenDates() {
		Date signedDate = new Date();

		List<General> contents = generalRepository.findByComponent(MongoConstants.COMPONENT_APPLICATION_STEP3, MongoConstants.CLIENT_CISCO, MongoConstants.PROGRAM_CLIENT_DEFAULT,
				signedDate);
		assertNotNull(contents);
		assertEquals(30, contents.size());
	}
	
	@Test
	public void findCollectionNames() {
		List<String> collectionNames = generalRepository.findCollectionNames();
		assertNotNull(collectionNames);
	}

	@Test
	public void findContentByClient() {
		List<General> contents = generalRepository.findByClient(MongoConstants.CLIENT_CISCO);
		assertNotNull(contents);
		assertEquals(38, contents.size());
	}

	@Test
	public void findContentByProgram() {
		List<General> contents = generalRepository.findByProgram(MongoConstants.CLIENT_CISCO, "945");
		assertNotNull(contents);
		assertEquals(7, contents.size());
	}

}