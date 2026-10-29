
package com.example.bmi_calculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
class BmiCalculatorApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void healthEndpointReturnsUp() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"UP\"}"));
    }

    @Test
    void bmiEndpointCalculatesBmi() throws Exception {
        String request = """
                {
                    "age": 20,
                    "weight": 60,
                    "weightUnit": "kg",
                    "height": 170,
                    "heightUnit": "cm"
                }
                """;

        mockMvc.perform(post("/api/bmi/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.ageGroup").value("Adult"))
                .andExpect(jsonPath("$.bmi").exists());
    }
}
