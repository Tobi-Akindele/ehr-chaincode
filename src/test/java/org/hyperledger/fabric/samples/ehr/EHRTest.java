/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.ehr;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class EHRTest {

    @Nested
    class Equality {

        @Test
        public void isReflexive() {
            EHRData ehrData = new EHRData(
                    "2bb3260-e24-f036-8c-360da8156",
                    "Sample Text Data",
                    "Annette KOEPP",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            );

            assertThat(ehrData).isEqualTo(ehrData);
        }

        @Test
        public void isSymmetric() {
            EHRData ehrDataA = new EHRData(
                    "2bb3260-e24-f036-8c-360da8156",
                    "Sample Text Data",
                    "Annette KOEPP",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            );
            EHRData ehrDataB = new EHRData(
                    "2bb3260-e24-f036-8c-360da8156",
                    "Sample Text Data",
                    "Annette KOEPP",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            );

            assertThat(ehrDataA).isEqualTo(ehrDataB);
            assertThat(ehrDataB).isEqualTo(ehrDataA);
        }

        @Test
        public void isTransitive() {
            EHRData ehrDataA = new EHRData(
                    "2bb3260-e24-f036-8c-360da8156",
                    "Sample Text Data",
                    "Annette KOEPP",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            );
            EHRData ehrDataB = new EHRData(
                    "2bb3260-e24-f036-8c-360da8156",
                    "Sample Text Data",
                    "Annette KOEPP",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            );
            EHRData ehrDataC = new EHRData(
                    "2bb3260-e24-f036-8c-360da8156",
                    "Sample Text Data",
                    "Annette KOEPP",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            );

            assertThat(ehrDataA).isEqualTo(ehrDataB);
            assertThat(ehrDataB).isEqualTo(ehrDataC);
            assertThat(ehrDataA).isEqualTo(ehrDataC);
        }

        @Test
        public void handlesInequality() {
            EHRData ehrDataA = new EHRData(
                    "2bb3260-e24-f036-8c-360da8156",
                    "Sample Text Data",
                    "Annette KOEPP",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            );
            EHRData ehrDataB = new EHRData(
                    "66faa1f-021a-bfc7-43e7-470cbdebac3",
                    "Sample Text Data",
                    "Tony RUTHERFORD",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            );

            assertThat(ehrDataA).isNotEqualTo(ehrDataB);
        }

        @Test
        public void handlesOtherObjects() {
            EHRData ehrDataA = new EHRData(
                    "66faa1f-021a-bfc7-43e7-470cbdebac3",
                    "Sample Text Data",
                    "Tony RUTHERFORD",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            );
            String ehrDataB = "not an EHRData";

            assertThat(ehrDataA).isNotEqualTo(ehrDataB);
        }

        @Test
        public void handlesNull() {
            EHRData ehrData = new EHRData(
                    "66faa1f-021a-bfc7-43e7-470cbdebac3",
                    "Sample Text Data",
                    "Tony RUTHERFORD",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            );

            assertThat(ehrData).isNotEqualTo(null);
        }
    }

    @Test
    public void toStringIdentifiesAsset() {
        EHRData ehrData = new EHRData(
                "66faa1f-021a-bfc7-43e7-470cbdebac3",
                "Sample Text Data",
                "Tony RUTHERFORD",
                "100 kB",
                false,
                "",
                "",
                ""
        );

        assertThat(ehrData.toString()).isEqualTo("EHRData@55ecd14[id=66faa1f-021a-bfc7-43e7-470cbdebac3, textData=Sample Text Data, name=Tony RUTHERFORD, size=100 kB, doc=false, fileType=, fileName=, base64String=]");
    }
}
