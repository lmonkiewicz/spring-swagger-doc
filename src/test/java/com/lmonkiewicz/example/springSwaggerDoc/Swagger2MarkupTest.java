package com.lmonkiewicz.example.springSwaggerDoc;

import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringSwaggerDocApplication.class)
public class Swagger2MarkupTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void saveSwaggerJson() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    File outputDir = new File("src/docs/swagger");
                    if (!outputDir.exists()){
                        outputDir.mkdirs();
                    }
                    try (FileOutputStream fos = new FileOutputStream("src/docs/swagger/swagger.json")) {
                        fos.write(result.getResponse().getContentAsByteArray());
                        fos.flush();
                    }
                })
                .andExpect(status().isOk());
    }

    @Test
    public void convertSwaggerToAsciiDocSingleFile() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    response.setCharacterEncoding("UTF-8");
                    String swaggerJson = response.getContentAsString();
                    Path outputFile = Paths.get("src/docs/asciidoc/single");

                    Swagger2MarkupConverter.from(swaggerJson)
                            .withConfig(new Swagger2MarkupConfigBuilder()
                                    .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                                    .build())
                            .build()
                            .toFile(outputFile);
                })
                .andExpect(status().isOk());
    }
    @Test
    public void convertSwaggerToAsciiDocMultipleFiles() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    response.setCharacterEncoding("UTF-8");
                    String swaggerJson = response.getContentAsString();
                    Path outputFolder = Paths.get("src/docs/asciidoc");

                    Swagger2MarkupConverter.from(swaggerJson)
                            .withConfig(new Swagger2MarkupConfigBuilder()
                                    .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                                    .build())
                            .build()
                            .toFolder(outputFolder);
                })
                .andExpect(status().isOk());
    }

    @Test
    public void convertSwaggerToMarkdownSingleFile() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    response.setCharacterEncoding("UTF-8");
                    String swaggerJson = response.getContentAsString();
                    Path outputFile = Paths.get("src/docs/markdown/single");

                    Swagger2MarkupConverter.from(swaggerJson)
                            .withConfig(new Swagger2MarkupConfigBuilder()
                                    .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                                    .build())
                            .build()
                            .toFile(outputFile);
                });
    }

    @Test
    public void convertSwaggerToMarkdownMultipleFiles() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    response.setCharacterEncoding("UTF-8");
                    String swaggerJson = response.getContentAsString();
                    Path outputFolder = Paths.get("src/docs/markdown");

                    Swagger2MarkupConverter.from(swaggerJson)
                            .withConfig(new Swagger2MarkupConfigBuilder()
                                    .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                                    .build())
                            .build()
                            .toFolder(outputFolder);
                });
    }

    @Test
    public void convertSwaggerToConfluenceSingleFile() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    response.setCharacterEncoding("UTF-8");
                    String swaggerJson = response.getContentAsString();
                    Path outputFile = Paths.get("src/docs/confluence/single");

                    Swagger2MarkupConverter.from(swaggerJson)
                            .withConfig(new Swagger2MarkupConfigBuilder()
                                    .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
                                    .build())
                            .build()
                            .toFile(outputFile);
                });
    }

    @Test
    public void convertSwaggerToConfluenceMultipleFiles() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    response.setCharacterEncoding("UTF-8");
                    String swaggerJson = response.getContentAsString();
                    Path outputFolder = Paths.get("src/docs/confluence");

                    Swagger2MarkupConverter.from(swaggerJson)
                            .withConfig(new Swagger2MarkupConfigBuilder()
                                    .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
                                    .build())
                            .build()
                            .toFolder(outputFolder);
                });
    }
}