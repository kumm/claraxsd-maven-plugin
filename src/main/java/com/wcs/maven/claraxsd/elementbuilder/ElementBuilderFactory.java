/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.maven.claraxsd.elementbuilder;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;
import com.wcs.maven.claraxsd.attributebuilder.AttributeBuilderFactory;
import com.wcs.maven.claraxsd.baseattributegroup.BaseAttributeGroupMngr;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ElementBuilderFactory {

    private final AttributeBuilderFactory attributeBuilderFactory;
    private final BaseAttributeGroupMngr baseAttributeGroupMngr;

    public ElementBuilderFactory(
            AttributeBuilderFactory attributeBuilderFactory,
            BaseAttributeGroupMngr baseAttributeGroupMngr) {
        this.attributeBuilderFactory = attributeBuilderFactory;
        this.baseAttributeGroupMngr = baseAttributeGroupMngr;
    }

    public ElementBuilder getElementBuilder(Class<? extends Component> componentClass) {
        if (!isVaadinComponentSupportedByClara(componentClass)) {
            return new NopElementBuilder();
        }
        if (ComponentContainer.class.isAssignableFrom(componentClass)) {
            return new ContainerElementBuilder(attributeBuilderFactory, baseAttributeGroupMngr);
        }
        if (SingleComponentContainer.class.isAssignableFrom(componentClass)) {
            return new SingleContainerElementBuilder(attributeBuilderFactory, baseAttributeGroupMngr);
        }
        return new ComponentElementBuilder(attributeBuilderFactory, baseAttributeGroupMngr);
    }

    private boolean isVaadinComponentSupportedByClara(Class<? extends Component> componentClass) {
        if (!Component.class.isAssignableFrom(componentClass)) {
            return false;
        }
        if (UI.class.isAssignableFrom(componentClass)) {
            return false;
        }
        if (componentClass.isMemberClass()) {
            return false;
        }
        if (Modifier.isAbstract(componentClass.getModifiers())
                || Modifier.isInterface(componentClass.getModifiers())
                || !Modifier.isPublic(componentClass.getModifiers())) {
            return false;
        }
        try {
            Constructor<? extends Component> constructor = componentClass.getConstructor();
            if (constructor == null || !Modifier.isPublic(constructor.getModifiers())) {
                return false;
            }
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

}
