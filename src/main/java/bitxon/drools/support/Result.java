package bitxon.drools.support;

import lombok.Data;

@Data
public final class Result<T> {
    private T value;
}
