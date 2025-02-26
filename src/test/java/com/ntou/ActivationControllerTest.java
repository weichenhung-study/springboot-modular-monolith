package com.ntou;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntou.creditcard.management.activation.Activation;
import com.ntou.creditcard.management.activation.ActivationRC;
import com.ntou.creditcard.management.activation.ActivationReq;
import com.ntou.creditcard.management.activation.ActivationRes;
import com.ntou.creditcard.BaseController;
import com.ntou.db.cuscredit.Cuscredit;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.exceptions.TException;
import com.ntou.tool.RegexpTool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class ActivationControllerTest extends BaseController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuscreditSvc mockCuscreditSvc;
    private final String path = "/MyCredit/res/Activation";

//    @Test
//    public void whenCheckReqIsFalse_shouldThrowException() {
//        ActivationReq mockReq = mock(ActivationReq.class);
//        when(mockReq.checkReq()).thenReturn(false);
//
//        Activation activation = new Activation();
//        assertThrows(TException.class, () -> activation.doAPI(mockReq,mockCuscreditSvc));
//    }
    @Test
    void testCheckReq_invalidCid() {
        ActivationReq req = new ActivationReq();
        req.setCid("B1876543219");
        req.setCardType("1");

        boolean result = req.checkReq();
        Assertions.assertFalse(result);
        Assertions.assertEquals(req.getErrMsg()
                , "使用者身分證字號(限本國人士)" + RegexpTool.C_INVALID_NUM_LEN);

        Activation activation = new Activation();
        assertThrows(TException.class, () -> activation.doAPI(req,mockCuscreditSvc));
    }
    @Test
    void testDoController_SuccessCase() throws Exception {
        ActivationReq request = new ActivationReq();
        setupRequestData(request);

        Cuscredit mockCuscredit = new Cuscredit();
        mockCuscredit.setEmail("tuluber@gmail.com");

        when(mockCuscreditSvc.selectKey(request.getCid(), request.getCardType())).thenReturn(mockCuscredit);

        MvcResult result = mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resCode").value(ActivationRC.T1310.getCode())).andReturn();

        ActivationRes res = new ActivationRes();
//        res.setStatus("SUCCESS");
//        Assertions.assertEquals("SUCCESS", res.getStatus(), "Status should be set correctly");

        result.getResponse().getContentAsString();
    }

    private void setupRequestData(ActivationReq request) {
        request.setCid("B253654321");
        request.setCardType("2");
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


