package org.hyperledger.fabric.samples.ehr;

import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MockEHRDataResultsIterator implements QueryResultsIterator<KeyValue> {

    private final List<KeyValue> ehrDataList;

    MockEHRDataResultsIterator() {
        super();

        ehrDataList = new ArrayList<>();

        ehrDataList.add(new MockKeyValue("2bb3260-e24-f036-8c-360da8156",
                "{ \"id\": \"2bb3260-e24-f036-8c-360da8156\", \"textData\": \"Sample Text Data\", \"name\": \"Annette KOEPP\", \"size\": \"100 kB\", \"doc\": false, \"fileType\": \"\", \"fileName\": \"\", \"base64String\": \"\" }"));
        ehrDataList.add(new MockKeyValue("2bb3260-e24-f036-8c-360da8156",
                "{ \"id\": \"66faa1f-021a-bfc7-43e7-470cbdebac3\", \"textData\": \"Sample Text Data\", \"name\": \"Tony RUTHERFORD\", \"size\": \"100 kB\", \"doc\": false, \"fileType\": \"\", \"fileName\": \"\", \"base64String\": \"\" }"));

    }

    @Override
    public void close() throws Exception {
        // do nothing
    }

    @Override
    public Iterator<KeyValue> iterator() {
        return ehrDataList.iterator();
    }
}
