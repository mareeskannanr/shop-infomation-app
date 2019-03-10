package com.rakuten.app;

import com.rakuten.app.utils.AppConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

	private static final String UPLOAD_URL = "/api/upload";
	private static final String SHOP_INFOS_URL = "/api/shop-infos";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void invalidFileName() throws Exception {
		MockMultipartFile file = TestUtils.createInvalidFile();

		MvcResult result = mockMvc.perform(multipart(UPLOAD_URL).file(file)).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
		JSONObject errors = new JSONObject(response.getContentAsString());
		assertEquals(errors.getString("error"), AppConstants.INVALID_FILE);
	}

	@Test
	public void emptyFile() throws Exception {
		MockMultipartFile file = TestUtils.createEmptyFile();

		MvcResult result = mockMvc.perform(multipart(UPLOAD_URL).file(file)).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
		JSONObject errors = new JSONObject(response.getContentAsString());
		assertEquals(errors.getString("error"), AppConstants.EMPTY_FILE);
	}

	//File with only header
	@Test
	public void fileWithOnlyHeader() throws Exception {
		MockMultipartFile file = TestUtils.createFile("Only_Header");

		MvcResult result = mockMvc.perform(multipart(UPLOAD_URL).file(file)).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
		JSONObject errors = new JSONObject(response.getContentAsString());
		assertEquals(errors.getString("error"), AppConstants.EMPTY_FILE);
	}

	@Test
	public void invalidLines() throws Exception {
		MockMultipartFile file = TestUtils.createFile("Invalid");

		MvcResult result = mockMvc.perform(multipart(UPLOAD_URL).file(file)).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		JSONArray errors = new JSONArray(response.getContentAsString());
		assertThat(errors.get(0).toString()).startsWith(AppConstants.INVALID_LINE_MESSAGE.split(":")[0]);
	}

	@Test
	public void duplicateShops() throws Exception {
		MockMultipartFile file = TestUtils.createFile("Duplicate");

		MvcResult result = mockMvc.perform(multipart(UPLOAD_URL).file(file)).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		JSONArray errors = new JSONArray(response.getContentAsString());
		assertThat(errors.get(0).toString()).startsWith(AppConstants.DUPLICATE_SHOP_ERROR_MESSAGE.split(":")[0]);
	}

	//End_Date Lesser Than Start_Date
	@Test
	public void invalidDateRange() throws Exception {
		MockMultipartFile file = TestUtils.createFile("Incorrect_Date");

		MvcResult result = mockMvc.perform(multipart(UPLOAD_URL).file(file)).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		JSONArray errors = new JSONArray(response.getContentAsString());
		assertThat(errors.get(0).toString()).startsWith(AppConstants.INVALID_DATE_MESSAGE.split(":")[0]);
	}

	//Future End_Date
	@Test
	public void invalidEndDate() throws Exception {
		MockMultipartFile file = TestUtils.createFile("Future_Date");

		MvcResult result = mockMvc.perform(multipart(UPLOAD_URL).file(file)).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		JSONArray errors = new JSONArray(response.getContentAsString());
		assertThat(errors.get(0).toString()).startsWith(AppConstants.FUTURE_DATE_ERROR_MESSAGE.split(":")[0]);
	}

	//File Upload Success & Reading Info
	@Test
	public void success() throws Exception {
		MockMultipartFile file = TestUtils.createFile("Valid_Data");

		MvcResult result = mockMvc.perform(multipart(UPLOAD_URL).file(file)).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(response.getContentAsString(), AppConstants.FILE_UPLOAD_MSG);

		result = mockMvc.perform(get(SHOP_INFOS_URL)).andReturn();
		response = result.getResponse();
		JSONArray resultArray = new JSONArray(response.getContentAsString());
		assertEquals(resultArray.length(), 3);
	}
}
