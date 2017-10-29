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

import name.alexkosarev.sandbox.SandboxTestingApplication;
import name.alexkosarev.sandbox.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.restdocs.WireMockSnippet;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@ContextConfiguration(classes = SandboxTestingApplication.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public abstract class SpringCucumberIntegrationTest {

    @Autowired
    @MockBean
    ContactRepository contactRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    private ManualRestDocumentation restDocumentation;

    public void setUp() {
        restDocumentation = new ManualRestDocumentation("target/generated-snippets");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation)
                        .snippets().withAdditionalDefaults(new WireMockSnippet()))
                .build();

        restDocumentation.beforeTest(FindAllTest.class, "setUp");
    }

    public void tearDown() {
        restDocumentation.afterTest();
    }

}
