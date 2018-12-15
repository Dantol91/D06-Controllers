
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
import domain.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	// Service under test 

	@Autowired
	private NoteService	noteService;


	// Tests 

	//Tests

	@Test
	public void testCreateNote() {
		Note note;
		note = this.noteService.create();
		Assert.notNull(note);
	}

	@Test
	public void testDeleteNote() {

		final Note note;
		note = this.noteService.findOne(super.getEntityId("note1"));
		this.noteService.delete(note);

	}

	@Test
	public void testFindAllNote() {
		final Collection<Note> notes;
		notes = this.noteService.findAll();
		Assert.notEmpty(notes);
		Assert.notNull(notes);

	}

	@Test
	public void testFindOneNote() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note1"));
		Assert.notNull(note);

	}

}
