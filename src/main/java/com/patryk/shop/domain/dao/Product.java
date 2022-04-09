package com.patryk.shop.domain.dao;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.IOException;


@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(indexes = @Index(name = "idx_name", columnList = "name", unique = true))
public class Product extends Auditable implements IdentifiedDataSerializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Double price;

    private Integer quantity;

    @Override
    public int getFactoryId() {
        return 1;
    }

    @Override
    public int getClassId() {
        return 1;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeLong(id);
        out.writeString(name);
        out.writeDouble(price);


    }

    @Override
    public void readData(ObjectDataInput in) throws
            IOException {

        id = in.readLong();
        name = in.readString();
        price = in.readDouble();

    }
}
