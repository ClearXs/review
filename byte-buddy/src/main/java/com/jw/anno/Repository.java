package com.jw.anno;

public abstract class Repository<T> {

    protected abstract T query(int id);
}
