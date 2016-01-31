package org.apache.felix.dm.lambda.impl;

import java.util.Objects;
import java.util.function.Consumer;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.lambda.ComponentBuilder;
import org.apache.felix.dm.lambda.ServiceAdapterBuilder;

public class ServiceAdapterBuilderImpl<T> extends ServiceCallbacksBuilderImpl<T, ServiceAdapterBuilder<T>> implements 
    AdapterBase<ServiceAdapterBuilder<T>>, ServiceAdapterBuilder<T> 
{	
    private final Class<T> m_adapteeType;
    private String m_adapteeFilter;
    private boolean m_propagate = true;
    private final DependencyManager m_dm;
    private boolean m_autoAdd = true;
    private Consumer<ComponentBuilder<?>> m_compBuilder = (componentBuilder -> {});

    public ServiceAdapterBuilderImpl(DependencyManager dm, Class<T> adapterType) {
        super(adapterType);
        m_dm = dm;
        m_adapteeType = adapterType;
    }    

    @Override
    public void andThenBuild(Consumer<ComponentBuilder<?>> after) {
        m_compBuilder = m_compBuilder.andThen(after);        
    }

    @Override
    public ServiceAdapterBuilderImpl<T> autoAdd(boolean autoAdd) {
        m_autoAdd = autoAdd;
        return this;
    }
    
    public ServiceAdapterBuilderImpl<T> autoAdd() {
        m_autoAdd = true;
        return this;
    }

    public boolean isAutoAdd() {
        return m_autoAdd;
    }
    
    @Override
    public ServiceAdapterBuilder<T> filter(String adapteeFilter) {
        m_adapteeFilter = adapteeFilter;
        return this;
    }

    @Override
    public ServiceAdapterBuilder<T> propagate(boolean propagate) {
        m_propagate = propagate;
        return this;
    }

    @Override
    public Component build() {
        Objects.nonNull(m_adapteeFilter);
        
        String add = getAdded(), change = getChanged(), remove = getRemoved(), swap = getSwapped();
        Object cbInstance = getCallbackInstance();
        
        if (hasRefs()) {
            // if some method references have been set, use our own callback proxy to redispatch events to method refs.        
        	cbInstance = createCallbackInstance();
            add = "add";
            change = "change";
            remove = "remove";
            swap = m_swapRefs.size() > 0 ? "swap" : null;
        } 

        Component c = m_dm.createAdapterService
        		(m_adapteeType, m_adapteeFilter, getAutoConfigField(), cbInstance, add, change, remove, swap, m_propagate);
        
        ComponentBuilderImpl cb = new ComponentBuilderImpl(c, false);
        // m_compBuilder is a composed consumer that calls in sequence all necessary component builder methods. 
        m_compBuilder.accept (cb);
        return cb.build();
    }
}
