package org.hyperledger.fabric.samples.ehr;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Date;
import java.util.Objects;

@DataType()
public final class EHRData {

    @Property()
    private final String id;

    @Property()
    private final String textData;

    @Property()
    private final String name;

    @Property()
    private final String size;

    @Property()
    private final boolean doc;

    @Property()
    private final String fileType;

    @Property()
    private final String fileName;

    @Property()
    private final String base64String;

    @Property()
    private final Date createdAt;

    public EHRData(
            @JsonProperty("id") final String id,
            @JsonProperty("textData") final String textData,
            @JsonProperty("name") final String name,
            @JsonProperty("size") final String size,
            @JsonProperty("doc") final boolean doc,
            @JsonProperty("fileType") final String fileType,
            @JsonProperty("fileName") final String fileName,
            @JsonProperty("base64String") final String base64String,
            @JsonProperty("createdAt") final Date createdAt
    ) {
        this.id = id;
        this.textData = textData;
        this.name = name;
        this.size = size;
        this.doc = doc;
        this.fileType = fileType;
        this.fileName = fileName;
        this.base64String = base64String;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getTextData() {
        return textData;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public boolean isDoc() {
        return doc;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getBase64String() {
        return base64String;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        EHRData other = (EHRData) obj;

        return Objects.deepEquals(
                new String[]{
                        getId(),
                        getTextData(),
                        getName(),
                        getSize(),
                        getFileType(),
                        getFileName(),
                        getBase64String()
                },
                new String[]{
                        other.getId(),
                        other.getTextData(),
                        other.getName(),
                        other.getSize(),
                        other.getFileType(),
                        other.getFileName(),
                        other.getBase64String()
                }
        ) && Objects.deepEquals(
                new boolean[]{
                        isDoc()
                },
                new boolean[]{
                        other.isDoc()
                }
        ) && Objects.deepEquals(
                new Date[] {
                        getCreatedAt()
                },
                new Date[] {
                        other.getCreatedAt()
                }
                );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(),
                getTextData(),
                getName(),
                getSize(),
                isDoc(),
                getFileType(),
                getFileName(),
                getBase64String(),
                getCreatedAt()
        );
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + "["
                + "id=" + id + ","
                + " textData=" + textData + ","
                + " name=" + name + ","
                + " size=" + size + ","
                + " doc=" + doc + ","
                + " fileType=" + fileType + ","
                + " fileName=" + fileName + ","
                + " base64String=" + base64String + "]"
                + " createdAt=" + createdAt + "]";
    }
}
