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
                "{\"base64String\":\"\",\"createdAt\":null,\"doc\":false,\"fileName\":\"\",\"fileType\":\"\",\"id\":\"2bb3260-e24-f036-8c-360da8156\",\"name\":\"Annette KOEPP\",\"size\":\"100 kB\",\"textData\":\"Sample Text Data\"}"));
        ehrDataList.add(new MockKeyValue("66faa1f-021a-bfc7-43e7-470cbdebac3",
                "{\"base64String\":\"\",\"createdAt\":null,\"doc\":false,\"fileName\":\"\",\"fileType\":\"\",\"id\":\"66faa1f-021a-bfc7-43e7-470cbdebac3\",\"name\":\"Tony RUTHERFORD\",\"size\":\"100 kB\",\"textData\":\"Sample Text Data\"}"));

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
