/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.ehr;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;

public final class EHRSmartContractTest {

    @Test
    public void invokeUnknownTransaction() {
        EHRSmartContract contract = new EHRSmartContract();
        Context ctx = mock(Context.class);

        Throwable thrown = catchThrowable(() -> contract.unknownTransaction(ctx));

        assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                .hasMessage("Undefined contract method called");
        assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo(null);

        verifyZeroInteractions(ctx);
    }

    @Nested
    class InvokeReadEHRTransaction {

        @Test
        public void whenEHRDataExists() {
            EHRSmartContract contract = new EHRSmartContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("2bb3260-e24-f036-8c-360da8156"))
                    .thenReturn("{ \"id\": \"2bb3260-e24-f036-8c-360da8156\", \"textData\": \"Sample Text Data\", \"name\": \"Annette KOEPP\", \"size\": \"100 kB\", \"doc\": false, \"fileType\": \"\", \"fileName\": \"\", \"base64String\": \"\" }");

            EHRData ehrData = contract.ReadEHRData(ctx, "2bb3260-e24-f036-8c-360da8156");

            assertThat(ehrData).isEqualTo(new EHRData(
                    "2bb3260-e24-f036-8c-360da8156",
                    "Sample Text Data",
                    "Annette KOEPP",
                    "100 kB",
                    false,
                    "",
                    "",
                    ""
            ));
        }

        @Test
        public void whenEHRDataDoesNotExist() {
            EHRSmartContract contract = new EHRSmartContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("2bb3260-e24-f036-8c-360da8156")).thenReturn("");

            Throwable thrown = catchThrowable(() -> {
                contract.ReadEHRData(ctx, "2bb3260-e24-f036-8c-360da8156");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("EHRData 2bb3260-e24-f036-8c-360da8156 does not exist");
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("EHRData_NOT_FOUND".getBytes());
        }
    }

    @Nested
    class InvokeCreateEHRDataTransaction {

        @Test
        public void whenEHRDataDoesNotExist() {
            EHRSmartContract contract = new EHRSmartContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("2bb3260-e24-f036-8c-360da8156")).thenReturn("");

            String payload = "{\n"
                    +
                    "  \"textData\" : \"Sample Text Data\",\n"
                    +
                    "  \"fileName\" : \"\",\n"
                    +
                    "  \"size\" : \"100 kB\",\n"
                    +
                    "  \"base64String\" : \"\",\n"
                    +
                    "  \"name\" : \"Annette KOEPP\",\n"
                    +
                    "  \"doc\" : false,\n"
                    +
                    "  \"id\" : \"2bb3260-e24-f036-8c-360da8156\",\n"
                    +
                    "  \"fileType\" : \"\"\n"
                    +
                    "}";

            String ehrData = contract.CreateEHRData(ctx, payload);

            assertThat(ehrData).isEqualTo(ehrData);
        }
    }

    @Test
    void invokeGetAllEHRDataTransaction() {
        EHRSmartContract contract = new EHRSmartContract();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);
        when(stub.getStateByRange("", "")).thenReturn(new MockEHRDataResultsIterator());

        String ehrData = contract.GetAllEHRData(ctx);

        assertThat(ehrData).isEqualTo("[{\"base64String\":\"\",\"doc\":false,\"fileName\":\"\",\"fileType\":\"\",\"id\":\"2bb3260-e24-f036-8c-360da8156\",\"name\":\"Annette KOEPP\",\"size\":\"100 kB\",\"textData\":\"Sample Text Data\"},"
                + "{\"base64String\":\"\",\"doc\":false,\"fileName\":\"\",\"fileType\":\"\",\"id\":\"66faa1f-021a-bfc7-43e7-470cbdebac3\",\"name\":\"Tony RUTHERFORD\",\"size\":\"100 kB\",\"textData\":\"Sample Text Data\"}]");
    }

    @Test
    void invokeInitLedgerTransaction() {
        EHRSmartContract ehrSmartContract = new EHRSmartContract();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);

        ehrSmartContract.InitLedger(ctx);

        InOrder inOrder = inOrder(stub);
        inOrder.verify(stub).putStringState("2bb3260-e24-f036-8c-360da8156", "{\"base64String\":\"\",\"doc\":false,\"fileName\":\"\",\"fileType\":\"\",\"id\":\"2bb3260-e24-f036-8c-360da8156\",\"name\":\"Annette KOEPP\",\"size\":\"100 kB\",\"textData\":\"Sample Text Data\"}");
    }

    @Test
    void invokeGetPaginatedEHRDataTransaction() {
        EHRSmartContract contract = new EHRSmartContract();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);
        when(stub
                .getStateByRangeWithPagination(
                        "",
                        "",
                        10,
                        "")).thenReturn(new MockEHRDataResultsInteratorWithMetadata());

        contract.GetPaginatedEHRData(ctx, "1", "2");

        assertTrue(true);
    }
}
