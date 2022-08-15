/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.ehr;

import java.util.ArrayList;
import java.util.List;


import com.owlike.genson.GenericType;
import org.hyperledger.fabric.Logger;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.samples.ehr.enums.Errors;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.owlike.genson.Genson;

@Contract(
        name = "ehr",
        info = @Info(
                title = "EHR Smart Contract",
                description = "EHR smart contract definition",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "tobi.dev@sharklasers.com",
                        name = "Tobi A.",
                        url = "https://hyperledger.example.com")))
@Default
public final class EHRSmartContract implements ContractInterface {

    public static final Logger LOG = Logger.getLogger(EHRSmartContract.class);

    private final Genson genson = new Genson();

    /**
     * Creates initial EHR data on the ledger.
     *
     * @param ctx the transaction context
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void InitLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        String payload = genson.serialize(List.of(new EHRData(
                "2bb3260-e24-f036-8c-360da8156",
                "Sample Text Data",
                "Annette KOEPP",
                "100 kB",
                false,
                "",
                "",
                ""
        )));

        CreateEHRData(ctx, payload);

    }

    /**
     * Creates a new EHR data on the ledger.
     *
     * @param ctx the transaction context
     * @param payload the stringified list of the new EHR data
     * @return the payload
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String CreateEHRData(final Context ctx, final String payload) {
        ChaincodeStub stub = ctx.getStub();

        LOG.info(String.format("[+] Payload: %s", payload));

        List<EHRData> ehrDataList = genson.deserialize(payload, new GenericType<List<EHRData>>() {
        });

        ehrDataList.stream().forEach(ehrData -> {
            if (!EHRDataExists(ctx, ehrData.getId())) {
                String sortedJSON = genson.serialize(ehrData);
                LOG.info(String.format("[+] GENSON: %s", sortedJSON));
                stub.putStringState(ehrData.getId(), sortedJSON);
            }
        });

        return payload;
    }

    /**
     * Retrieves an EHR data with the specified ID from the ledger.
     *
     * @param ctx the transaction context
     * @param ehrDataId the ID of the EHR data
     * @return the EHR data found on the ledger if there was one
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public EHRData ReadEHRData(final Context ctx, final String ehrDataId) {
        ChaincodeStub stub = ctx.getStub();
        String ehrDataJSON = stub.getStringState(ehrDataId);

        if (ehrDataJSON == null || ehrDataJSON.isEmpty()) {
            String errorMessage = String.format("EHRData %s does not exist", ehrDataId);
            throw new ChaincodeException(errorMessage, Errors.EHRData_NOT_FOUND.toString());
        }

        EHRData ehrData = genson.deserialize(ehrDataJSON, EHRData.class);
        return ehrData;
    }

    /**
     * Checks the existence of the EHR data on the ledger
     *
     * @param ctx the transaction context
     * @param ehrDataId the ID of the EHR data
     * @return boolean indicating the existence of the EHR data
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean EHRDataExists(final Context ctx, final String ehrDataId) {
        ChaincodeStub stub = ctx.getStub();
        String ehrJSON = stub.getStringState(ehrDataId);

        return (ehrJSON != null && !ehrJSON.isEmpty());
    }

    /**
     * Retrieves all EHR data from the ledger.
     *
     * @param ctx the transaction context
     * @return array of EHR data found on the ledger
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String GetAllEHRData(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        List<EHRData> queryResults = new ArrayList<>();

        // To retrieve all EHR data from the ledger use getStateByRange with empty startKey & endKey.
        // Giving empty startKey & endKey is interpreted as all the keys from beginning to end.
        // As another example, if you use startKey = 'asset0', endKey = 'asset9' ,
        // then getStateByRange will retrieve asset with keys between asset0 (inclusive) and asset9 (exclusive) in lexical order.
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");

        for (KeyValue result: results) {
            EHRData ehrData = genson.deserialize(result.getStringValue(), EHRData.class);
            queryResults.add(ehrData);
        }

        final String response = genson.serialize(queryResults);

        return response;
    }
}
