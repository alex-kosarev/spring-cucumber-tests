/*
 *  Copyright 2017 Alexander Kosarev
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package name.alexkosarev.sandbox.web.controllers.contacts;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.То;
import cucumber.api.java.ru.Тогда;
import name.alexkosarev.sandbox.entities.Contact;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.cloud.contract.wiremock.restdocs.SpringCloudContractRestDocs.dslContract;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FindAllStepDefs extends SpringCucumberIntegrationTest {

    private ResultActions resultActions;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Дано("^в таблице есть контакты:$")
    public void в_таблице_есть_контакты(List<Contact> contacts) throws NoSuchMethodException {
        doReturn(contacts).when(contactRepository)
                .findAll();
    }

    @Когда("^клиент выполнит запрос GET (.+)$")
    public void клиент_выполнит_запрос_GET(String requestPath) throws Exception {
        resultActions = mockMvc.perform(get(requestPath).with(user("tester")));
    }

    @Тогда("^будет возвращён ответ со статусом (\\d+)$")
    public void будет_возвращён_ответ_со_статусом(int status) throws Exception {
        resultActions.andExpect(status().is(status));
    }

    @То("^также ответ будет выведен в лог$")
    public void также_ответ_будет_выведен_в_лог() throws Exception {
        resultActions.andDo(print());
    }

    @То("^ответ будет содержать (\\d+) элемента$")
    public void ответ_будет_содержать_элемента(int count) throws Exception {
        resultActions.andExpect(jsonPath("$.length()").value(3));
    }

    @То("^тип содержимого будет (.+)$")
    public void тип_содержимого_будет(String contentType) throws Exception {
        resultActions.andExpect(content().contentType(contentType));
    }

    @То("^также запрос будет задокументирован$")
    public void также_запрос_будет_задокументирован() throws Exception {
        resultActions.andDo(document("contacts/findAll",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[0].email").description("E-mail контакта"),
                        fieldWithPath("[0].name").description("Имя контакта")
                ),
                dslContract()
        ));
    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}