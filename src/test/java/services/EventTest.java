
package services;

import java.util.Calendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Event;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EventTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private EventService	eventService;


	// Tests ------------------------------------------------------------------
	// An actor who is authenticated as a manager must be able to:
	// Manage the events that he or she organises, which includes listing, registering, modifying, and deleting them.
	// In order to register a new event, he must have registered a valid credit card that must not expire in less than one day.
	// Every time he or she registers an event, the system will simulate that he or she's charged a 1.00 euro fee.

	// Crear un nuevo evento
	//	, {
	//		"manager1", "FUERA!!", "15/10/2017 00:00", "Si me quereis irse", "http://image.com", -50, ConstraintViolationException.class
	//	}, {
	//		"manager2", "Examen!!", "05/03/2016 10:30", "Junio de 2016", "http://image.com", 25, IllegalArgumentException.class
	//	}
	@Test
	public void driverCreateEvent() {
		final Object testingData[][] = {
			{
				"manager1", "Fiesta!!", "01/01/2018 00:00", "Es una fiesta", "http://image.com", 10, null
			}, {
				"manager2", "Excursion!!", "03/06/2017 08:00", "A la universidad", "http://image.com", 40, null
			}, {
				"chorbi1", "Prueba", "21/11/2017 00:00", "pruebecita", "http://image.com", 2, IllegalArgumentException.class
			}, {
				"manager3", "", "15/10/2017 00:00", "", "http://image.com", 8, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createEvent((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Integer) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void createEvent(final String manager, final String title, final String moment, final String description, final String picture, final Integer seats, final Class<?> expected) {

		Class<?> caught = null;
		Event event = null;
		String[] date = null;
		String[] time = null;
		final Calendar calendar = Calendar.getInstance();

		try {
			this.authenticate(manager);

			event = this.eventService.create();
			event.setTitle(title);
			date = moment.split(" ");
			time = date[1].split(":");
			date = date[0].split("/");
			calendar.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
			event.setMoment(calendar.getTime());
			event.setDescription(description);
			event.setPicture(picture);
			event.setSeats(seats);

			// Comprobar que se ha cobrado el leru

			this.eventService.save(event);
			this.eventService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Editar un evento

	//	, {
	//		"manager2", 300, "FUERA!!", "15/10/2017 00:00", "Si me quereis irse", "http://image.com", -50, ConstraintViolationException.class
	//	}, {
	//		"manager1", 297, "Examen!!", "05/03/2016 10:30", "Junio de 2016", "http://image.com", 25, IllegalArgumentException.class
	//	}
	@Test
	public void driverEditEvent() {
		final Object testingData[][] = {
			{
				"manager1", 297, "Fiesta!!", "01/01/2018 00:00", "Es una fiesta", "http://image.com", 10, null
			}, {
				"manager2", 300, "Excursion!!", "03/06/2017 08:00", "A la universidad", "http://image.com", 40, null
			}, {
				"manager3", 298, "Prueba", "21/11/2017 00:00", "pruebecita", "http://image.com", 2, IllegalArgumentException.class
			}, {
				"manager1", 299, "", "15/10/2017 00:00", "", "http://image.com", 8, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editEvent((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	protected void editEvent(final String manager, final Integer eventId, final String title, final String moment, final String description, final String picture, final Integer seats, final Class<?> expected) {

		Class<?> caught = null;
		Event event = null;
		String[] date = null;
		String[] time = null;
		final Calendar calendar = Calendar.getInstance();

		try {
			this.authenticate(manager);

			event = this.eventService.findOne(eventId);
			event.setTitle(title);
			date = moment.split(" ");
			time = date[1].split(":");
			date = date[0].split("/");
			calendar.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
			event.setMoment(calendar.getTime());
			event.setDescription(description);
			event.setPicture(picture);
			event.setSeats(seats);

			this.eventService.save(event);
			this.eventService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Borrar un evento
	@Test
	public void driverDeleteEvent() {
		final Object testingData[][] = {
			{
				"manager1", 297, null
			}, {
				"manager2", 300, null
			}, {
				"chorbi1", 298, IllegalArgumentException.class
			}, {
				"manager3", 299, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteEvent((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void deleteEvent(final String manager, final Integer eventId, final Class<?> expected) {

		Class<?> caught = null;
		Event event = null;

		try {
			this.authenticate(manager);

			event = this.eventService.findOne(eventId);

			this.eventService.delete(event);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//An actor who is authenticated as a chorbi must be able to:
	// Register to an event as long as there are enough seats avaible.
	// Un-register from an event to which he or she's registered.

	// Registrarse un chorbi en un evento
	@Test
	public void driverRegisterEvent() {
		final Object testingData[][] = {
			{
				"chorbi1", 300, null
			}, {
				"chorbi4", 297, null
			}, {
				"chorbi1", 297, IllegalArgumentException.class
			}, {
				"chorbi2", 299, IllegalArgumentException.class
			}, {
				"chorbi3", 298, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerEvent((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void registerEvent(final String chorbi, final Integer eventId, final Class<?> expected) {

		Class<?> caught = null;
		Event event;

		try {
			this.authenticate(chorbi);

			event = this.eventService.findOne(eventId);
			this.eventService.registerChorbiToEvent(event);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Cancelar el registro de un chorbi en un evento
	@Test
	public void driverUnregisterEvent() {
		final Object testingData[][] = {
			{
				"chorbi2", 297, null
			}, {
				"chorbi1", 299, null
			}, {
				"chorbi4", 298, IllegalArgumentException.class
			}, {
				"chorbi3", 299, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.unregisterEvent((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void unregisterEvent(final String chorbi, final Integer eventId, final Class<?> expected) {

		Class<?> caught = null;
		Event event;

		try {
			this.authenticate(chorbi);

			event = this.eventService.findOne(eventId);
			this.eventService.unregisterChorbiToEvent(event);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
