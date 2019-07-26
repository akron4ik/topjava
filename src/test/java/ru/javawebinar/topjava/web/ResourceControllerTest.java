package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.web.user.AdminRestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest {

    private static final String STYLE_URL = "/resources/css/style.css";

    @Test
    void testStyle() throws Exception{
        mockMvc.perform(get(STYLE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/css"));


   }

}
