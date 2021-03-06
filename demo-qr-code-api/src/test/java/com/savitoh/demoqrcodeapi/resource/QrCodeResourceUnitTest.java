package com.savitoh.demoqrcodeapi.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.savitoh.demoqrcodeapi.exceptions.CustomApiErroResponse;
import com.savitoh.demoqrcodeapi.exceptions.data.GenerateQrCodeException;
import com.savitoh.demoqrcodeapi.payload.QrCodeResquestPayload;
import com.savitoh.demoqrcodeapi.service.QrCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@WebMvcTest(value = QrCodeResource.class)
public class QrCodeResourceUnitTest {

    private static final String FAKE_URL = "http://www.fake-site.com/";

    private static final String QR_CODE_STRING_BASE_64_STRING = "�PNG\n" +
            "\u001A\n" +
            "\u0000\u0000\u0000\n" +
            "IHDR\u0000\u0000\u0000�\u0000\u0000\u0000�\u0001\u0000\u0000\u0000\u0000�#�3\u0000\u0000\u0001!IDATx^�A��0\fD�*��M!�i��U�=v[U��U�Y`Qh�m,O& ���,��&7A��t��%���a�ѐ\u0011?S�\t\u0001\u007FR!!&����t�vQ6\u0012��͍�ho�;\u0015q���\n" +
            "�*��TJF6>>��JR�b�>�ך���u���7�<���<,�FPb�\u0007\n" +
            "���B|\u0005��\b�!\u0012S44���a;\u0013�Պ�h��\u0004_L�� ��y\u001A�\u001EM���m\u0002b�[�2��;FKD<��\u001Co6�!U#�:j�4$:��\b�\u0013r\u0010<! \u001F[-yH~=ũ,q��\u0001\u0007q�{�!�%����\u0016\bm�'�r�\u000F�C��i8\u0007�aq�$�G�<�s��&��\"\u007FlW,�8\u001EE\n" +
            "\u0000\u0000\u0000\u0000IEND�B`�";

    private static final String QR_CODE_URI_PATH = "/api/v1/qrcodes";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QrCodeService qrCodeService;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void deveRetornarQrCodeResponseHeader() throws Exception {
        final var qrCodeResquestPayloadMock = new QrCodeResquestPayload(FAKE_URL);
        when(qrCodeService.genarateQrCodeFromUri(FAKE_URL)).thenReturn(QR_CODE_STRING_BASE_64_STRING.getBytes());
        when(restTemplate.headForHeaders(FAKE_URL)).thenReturn(HttpHeaders.EMPTY);
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(QR_CODE_URI_PATH)
            .content(objectMapper.writeValueAsString(qrCodeResquestPayloadMock))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.IMAGE_PNG);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();


        assertTrue(response.containsHeader(HttpHeaders.CONTENT_DISPOSITION));
        assertNotNull(response.getOutputStream());
        assertEquals(MediaType.IMAGE_PNG_VALUE, response.getContentType());
    }

    @Test
    public void deveRetornarBadRequestQuandoRequestPayloadForInvalido() throws Exception {
        final var invalidqrCodeRequestPayload =
                "{\n" +
                "   \"uriTarget\":\"\"\n" +
                "}";
        
        final var messageErrorExpected = "URL não pode ser nulla ou vazia.";
        mockMvc.perform(MockMvcRequestBuilders
                .post(QR_CODE_URI_PATH)
                .content(invalidqrCodeRequestPayload)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.error").value(messageErrorExpected))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void deveRetornarBadRequestQuandoURLMalFormatada() throws Exception {
        final QrCodeResquestPayload qrCodeResquestPayloadMock = new QrCodeResquestPayload("Teste");
        final var messageException = "URL não possui formato válido.";

        mockMvc.perform(MockMvcRequestBuilders
                .post(QR_CODE_URI_PATH)
                .content(objectMapper.writeValueAsString(qrCodeResquestPayloadMock))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.error").value(messageException))
                .andExpect(jsonPath("$.timestamp").exists());        
    }


    @Test
    public void deveRetornarServerErroQuandoGerarQrCodeSubirException() throws Exception {
        QrCodeResquestPayload qrCodeResquestPayloadMock = new QrCodeResquestPayload(FAKE_URL);
        final var messageException = "Erro ao Gerar QR CODE!";
        when(qrCodeService.genarateQrCodeFromUri(FAKE_URL)).thenThrow(new GenerateQrCodeException(messageException));
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(QR_CODE_URI_PATH)
            .content(objectMapper.writeValueAsString(qrCodeResquestPayloadMock))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        CustomApiErroResponse customApiErroResponse =
                objectMapper.readValue(response.getContentAsString(), CustomApiErroResponse.class);
        
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertNotNull(customApiErroResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), customApiErroResponse.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), customApiErroResponse.getStatus());
        assertEquals(messageException, customApiErroResponse.getError());
        assertTrue(customApiErroResponse.getTimestamp().isBefore(LocalDateTime.now()));
    }

}
