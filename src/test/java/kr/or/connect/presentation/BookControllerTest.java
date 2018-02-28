package kr.or.connect.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import kr.or.connect.BookServerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookServerApplication.class)
@WebAppConfiguration
@Transactional
public class BookControllerTest {

	@Autowired
	WebApplicationContext wac;
	MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = webAppContextSetup(this.wac)
				.alwaysDo(print(System.out))
				.build();
	}
	
	@Test
	public void shouldCreate() throws Exception {
		String requestBody = "{\"title\":\"사피엔스\",\"author\":\"유발하라리\"}";
		
		mvc.perform(
			post("/api/books/").contentType(MediaType.APPLICATION_JSON).content(requestBody)
		).andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.title").value("사피엔스"))
		.andExpect(jsonPath("$.author").value("유발하라리"));		
	}
	
	@Test
	public void shouldUpdate() throws Exception {
		String requestBody = "{\"title\":\"사피엔스\", \"author\":\"유발하라리\"}";

		mvc.perform(
			put("/api/books/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
			)
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void shouldDelete() throws Exception {
		mvc.perform(
			delete("/api/books/1")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isNoContent());
	}

}
