package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccommodationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accommodation.class);
        Accommodation accommodation1 = new Accommodation();
        accommodation1.setId(1L);
        Accommodation accommodation2 = new Accommodation();
        accommodation2.setId(accommodation1.getId());
        assertThat(accommodation1).isEqualTo(accommodation2);
        accommodation2.setId(2L);
        assertThat(accommodation1).isNotEqualTo(accommodation2);
        accommodation1.setId(null);
        assertThat(accommodation1).isNotEqualTo(accommodation2);
    }
}
