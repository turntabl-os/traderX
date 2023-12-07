package finos.traderx.tradeprocessor.controller;

import finos.traderx.tradeprocessor.TradeProcessorApplication;
import finos.traderx.tradeprocessor.model.TradeOrder;
import finos.traderx.tradeprocessor.model.TradeSide;
import finos.traderx.tradeprocessor.service.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest(TradeServiceController.class)
@ContextConfiguration(classes= TradeProcessorApplication.class)
@AutoConfigureMockMvc
//@Transactional
@TestPropertySource(locations = "classpath:test-application.properties")
class TradeServiceControllerIntegrationTest {

    @MockBean
    TradeService tradeService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void processOrder() throws Exception {
        TradeOrder tradeOrder = new TradeOrder("ID1", 123, "MSFT", TradeSide.Buy, 2);
        mockMvc.perform(post("/tradeservice/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"order\": \"tradeOrder\"}"))
                .andExpect(status().isOk());
    }

}