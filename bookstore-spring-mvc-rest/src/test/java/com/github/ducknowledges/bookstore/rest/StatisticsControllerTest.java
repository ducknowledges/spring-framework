package com.github.ducknowledges.bookstore.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ducknowledges.bookstore.dto.CounterStatisticsDto;
import com.github.ducknowledges.bookstore.service.StatisticsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticsService statisticsService;

    @Test
    @DisplayName("should correct handle get / request")
    void shouldCorrectHandleRootPageGetRequest() throws Exception {
        CounterStatisticsDto dto = new CounterStatisticsDto(1L, 1L, 1L);
        when(statisticsService.getCounterStatistics()).thenReturn(dto);
        ObjectMapper mapper = new ObjectMapper();
        mvc.perform(get("/api/statistics"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(dto)));
        verify(statisticsService, times(1)).getCounterStatistics();
    }
}