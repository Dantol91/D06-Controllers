
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private SocialProfileService	socialProfileService;


	//Tests

	@Test
	public void testCreateSocialProfile() {
		final SocialProfile socialProfile;

		//	socialProfile = this.socialProfileService.create();

		//	Assert.notNull(socialProfile);
	}

	/*
	 * @Test
	 * public void testSaveSocialProfile() {
	 * final SocialProfile socialProfile, socialProfileSaved;
	 * 
	 * socialProfile = this.socialProfileService.findOne(super.getEntityId("socialProfile2"));
	 * 
	 * socialProfile.setNick("nick");
	 * socialProfile.setName("name");
	 * socialProfile.setLink("www.link.es/es");
	 * 
	 * socialProfileSaved = this.socialProfileService.save(socialProfile);
	 * 
	 * System.out.println("socialProfile guardada:  " + socialProfile);
	 * 
	 * Assert.notNull(socialProfileSaved);
	 * }
	 */

	@Test
	public void testDeleteSocialProfile() {

		final SocialProfile socialProfile;
		socialProfile = this.socialProfileService.findOne(super.getEntityId("socialProfile1"));
		this.socialProfileService.delete(socialProfile);

	}

	@Test
	public void testFindAllSocialProfile() {
		Collection<SocialProfile> socialProfiles;
		socialProfiles = this.socialProfileService.findAll();
		Assert.notEmpty(socialProfiles);
		Assert.notNull(socialProfiles);

	}

	@Test
	public void testFindOneSocialProfile() {
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.findOne(super.getEntityId("socialProfile1"));
		Assert.notNull(socialProfile);

	}

}
