package ForeignExchange.ForeignExchangeApp.unitTests;

import ForeignExchange.ForeignExchangeApp.controller.ConversionHistoryController;
import ForeignExchange.ForeignExchangeApp.model.mysql.ConversionHistory;
import ForeignExchange.ForeignExchangeApp.service.ConversionHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ConversionHistoryControllerTest {

    @Mock
    ConversionHistoryService conversionHistoryService;

    @InjectMocks
    ConversionHistoryController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(resolver)
                .build();
    }

    @Test
    void testShowConversionHistory() throws Exception {
        Page<ConversionHistory> page = new PageImpl<>(Collections.emptyList());
        when(conversionHistoryService.getAllConversions(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/conversion-history/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("conversion-history"))
                .andExpect(model().attributeExists("history"));
    }

    @Test
    void testFilterConversionHistory() throws Exception {
        mockMvc.perform(get("/conversion-history/filter"))
                .andExpect(status().isOk())
                .andExpect(view().name("filter-history"));
    }

    @Test
    void testShowFilteredConversions() throws Exception {
        Page<ConversionHistory> page = new PageImpl<>(Collections.emptyList());
        when(conversionHistoryService.getAllConversionsFilteredByDate(any(LocalDate.class), any(LocalDate.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(post("/conversion-history/filter")
                        .param("fromDate", "2024-01-01")
                        .param("toDate", "2024-01-02"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("filtered-data"));
    }

}