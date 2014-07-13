
/*
 * Copyright (C) 2014 Archie L. Cobbs. All rights reserved.
 *
 * $Id$
 */

package org.jsimpledb;

import com.google.common.base.Converter;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Method;
import java.util.List;

import org.jsimpledb.core.ObjId;
import org.jsimpledb.core.Transaction;
import org.jsimpledb.schema.CounterSchemaField;
import org.objectweb.asm.ClassWriter;

/**
 * Represents a counter field in a {@link JClass}.
 *
 * @see org.jsimpledb.Counter
 */
public class JCounterField extends JField {

    JCounterField(String name, int storageId, String description, Method getter) {
        super(name, storageId, description, getter);
    }

    @Override
    public Counter getValue(JTransaction jtx, ObjId id) {
        if (jtx == null)
            throw new IllegalArgumentException("null jtx");
        return jtx.readCounterField(id, this.storageId, false);
    }

    @Override
    CounterSchemaField toSchemaItem() {
        final CounterSchemaField schemaField = new CounterSchemaField();
        this.initialize(schemaField);
        return schemaField;
    }

    @Override
    void outputMethods(final ClassGenerator<?> generator, ClassWriter cw) {
        this.outputReadMethod(generator, cw, ClassGenerator.READ_COUNTER_FIELD_METHOD);
    }

    @Override
    void registerChangeListener(Transaction tx, int[] path, AllChangesListener listener) {
        throw new UnsupportedOperationException("counter fields do not support change notifications");
    }

    @Override
    <T> void addChangeParameterTypes(List<TypeToken<?>> types, TypeToken<T> targetType) {
        throw new UnsupportedOperationException("counter fields do not support change notifications");
    }

    @Override
    Converter<?, ?> getConverter(JTransaction jtx) {
        return null;
    }
}

