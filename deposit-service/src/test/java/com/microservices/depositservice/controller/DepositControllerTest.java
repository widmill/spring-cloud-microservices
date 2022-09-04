package com.microservices.depositservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.deposit.DepositApplication;
import com.microservices.deposit.controller.dto.DepositResponseDto;
import com.microservices.deposit.model.Deposit;
import com.microservices.deposit.repository.DepositRepository;
import com.microservices.deposit.rest.AccountResponseDto;
import com.microservices.deposit.rest.AccountServiceClient;
import com.microservices.deposit.rest.BillResponseDto;
import com.microservices.deposit.rest.BillServiceClient;
import com.microservices.depositservice.config.SpringH2DatabaseConfig;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DepositApplication.class, SpringH2DatabaseConfig.class})
public class DepositControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DepositRepository depositRepository;

    @MockBean
    private BillServiceClient billServiceClient;

    @MockBean
    private AccountServiceClient accountServiceClient;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private static final String REQUEST = "{\n" +
            "    \"billId\": 1,\n" +
            "    \"amount\": 3000\n" +
            "}";

    @Test
    @Ignore
    public void createDeposit() throws Exception {
        BillResponseDto billResponseDto = createBillResponseDto();
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong())).thenReturn(billResponseDto);
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong())).thenReturn(createAccountResponseDto());
        MvcResult mvcResult = mockMvc.perform(post("/deposits")
                .content(REQUEST).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        List<Deposit> deposits = depositRepository.findDepositsByEmail("kakoi-toemail@gmail.com");

        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDto depositResponseDto = objectMapper.readValue(body, DepositResponseDto.class);

        Assertions.assertThat(depositResponseDto.getEmail()).isEqualTo(deposits.get(0).getEmail());
        Assertions.assertThat(depositResponseDto.getAmount()).isEqualTo(BigDecimal.valueOf(300000, 2));
    }

    private AccountResponseDto createAccountResponseDto() {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(1L);
        accountResponseDto.setBills(Arrays.asList(1L, 2L, 3L));
        accountResponseDto.setCreationDate(OffsetDateTime.now());
        accountResponseDto.setEmail("kakoi-toemail@gmail.com");
        accountResponseDto.setName("Name");
        accountResponseDto.setPhone("+123456");
        return accountResponseDto;
    }

    private BillResponseDto createBillResponseDto() {
        BillResponseDto billResponseDto = new BillResponseDto();
        billResponseDto.setAccountId(1L);
        billResponseDto.setAmount(BigDecimal.valueOf(1000));
        billResponseDto.setBillId(1L);
        billResponseDto.setCreationDate(OffsetDateTime.now());
        billResponseDto.setDefault(true);
        billResponseDto.setOverdraftEnabled(true);
        return billResponseDto;

    }
}
