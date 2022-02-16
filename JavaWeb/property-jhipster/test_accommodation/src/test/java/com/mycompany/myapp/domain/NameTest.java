package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Name.class);
        Name name1 = new Name();
        name1.setId(1L);
        Name name2 = new Name();
        name2.setId(name1.getId());
        assertThat(name1).isEqualTo(name2);
        name2.setId(2L);
        assertThat(name1).isNotEqualTo(name2);
        name1.setId(null);
        assertThat(name1).isNotEqualTo(name2);
    }
}
