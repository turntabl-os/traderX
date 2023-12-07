package finos.traderx.tradeservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import finos.traderx.messaging.PubSubException;
import finos.traderx.messaging.Publisher;
import finos.traderx.tradeservice.model.Account;
import finos.traderx.tradeservice.model.Security;
import finos.traderx.tradeservice.model.TradeOrder;
import finos.traderx.tradeservice.model.TradeSide;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(WireMockExtension.class)
@ExtendWith(MockitoExtension.class)
public class TradeServiceApplicationTest {

    @MockBean
    Publisher<TradeOrder> tradePublisherMock;

    @LocalServerPort
    int port;

    static int peopleServicePort = 18089;
    static int referenceServicePort = 18085;
    static int accountServicePort = 18088;

    String baseUrl;

    TestRestTemplate restTemplate = new TestRestTemplate();

    TradeOrder tradeOrder;

    @RegisterExtension
    static WireMockExtension peopleMockServer = WireMockExtension.newInstance()
            .options(
                WireMockConfiguration.options()
                    .port(peopleServicePort)
            )
            .build();

    @RegisterExtension
    static WireMockExtension accountMockServer = WireMockExtension.newInstance()
            .options(
                WireMockConfiguration.options()
                    .port(accountServicePort)
            )
            .build();


    @RegisterExtension
    static WireMockExtension referenceMockServer = WireMockExtension.newInstance()
            .options(
                WireMockConfiguration.options()
                    .port(referenceServicePort)
            )
            .build(); 

    @BeforeEach
    private void setUp(){
        baseUrl = "http://localhost:" + port;

        tradeOrder = new TradeOrder(
                "1234",
                1234,
                "MSFT",
                TradeSide.Buy,
                100
        );

    }

    @AfterEach
    public void teardown() {
    }
   
    @Test
    public void testCreateTrade() throws PubSubException{
        referenceMockServer.stubFor(get("/stocks/" + tradeOrder.getSecurity()).willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(Json.write(new Security(tradeOrder.getSecurity(), tradeOrder.getSecurity())))));
        accountMockServer.stubFor(get("/account/" + tradeOrder.getAccountId()).willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(Json.write(new Account(tradeOrder.getAccountId(), "account")))));

        doNothing().when(tradePublisherMock).publish("/trades", tradeOrder);

        ResponseEntity<TradeOrder> response = restTemplate.postForEntity(baseUrl + "/trade/",tradeOrder, TradeOrder.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
