package com.example.hex.interfaces.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnValidationErrorsWhenProductIsInvalid() throws Exception {
        String invalidProduct = """
            {
                "name": "",
                "price": -1
            }
            """;

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidProduct))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Product name is required"))
                .andExpect(jsonPath("$.price").value("Price must be greater than zero"));
    }

    @Test
    void shouldReturnValidationErrorWhenPriceIsNull() throws Exception {
        String productWithNullPrice = """
            {
                "name": "Test Product",
                "price": null
            }
            """;

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productWithNullPrice))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.price").value("Price is required"));
    }
}
