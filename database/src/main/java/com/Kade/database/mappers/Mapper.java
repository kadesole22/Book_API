package com.Kade.database.mappers;

/**
 * Generic interface for mapping between two types.
 *
 * @param <A> the source type (e.g., Entity)
 * @param <B> the target type (e.g., DTO)
 */
public interface Mapper<A,B> {

    B mapTo(A a);

    A mapFrom(B b);
}
