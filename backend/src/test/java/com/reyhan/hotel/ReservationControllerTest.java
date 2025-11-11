package com.reyhan.hotel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReservationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void hotelsListShouldReturnOk() throws Exception {
		mockMvc.perform(get("/api/hotels"))
			.andExpect(status().isOk());
	}

	@Test
	void createReservation_thenConflictOnOverlap() throws Exception {
		// Create a reservation for room id 1 in future dates
		mockMvc.perform(post("/api/reservations")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"roomId\":1,\"guestName\":\"Test\",\"guestEmail\":\"t@t.com\",\"startDate\":\"2030-01-01\",\"endDate\":\"2030-01-03\"}"))
			.andExpect(status().isCreated());

		// Overlapping reservation must fail with 409
		mockMvc.perform(post("/api/reservations")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"roomId\":1,\"guestName\":\"Test2\",\"guestEmail\":\"t2@t.com\",\"startDate\":\"2030-01-02\",\"endDate\":\"2030-01-04\"}"))
			.andExpect(status().isConflict());
	}
}

