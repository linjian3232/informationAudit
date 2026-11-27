package com.informationaudit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import com.informationaudit.controller.PublicFileController;
import com.informationaudit.dao.PublicFileMapper;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InformationAuditApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void registersFileAuditControllerAndMapper() {
        assertThat(context.getBean(PublicFileController.class)).isNotNull();
        assertThat(context.getBean(PublicFileMapper.class)).isNotNull();
    }

}
