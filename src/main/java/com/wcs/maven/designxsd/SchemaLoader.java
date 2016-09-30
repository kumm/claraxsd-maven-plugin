/*
 * Copyright 2014 Webstar Csoport Kft.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.wcs.maven.designxsd;

import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.xml.sax.InputSource;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

/**
 *
 * @author kumm
 */
public class SchemaLoader {

    public static XmlSchema load(InputStream xsdStream) {
        XmlSchemaCollection xmlSchemaCollection = new XmlSchemaCollection();
        xmlSchemaCollection.setSchemaResolver(SchemaLoader::resolveEntity);
        return xmlSchemaCollection.read(new StreamSource(xsdStream), null);
    }

    private static InputSource resolveEntity(String targetNamespace, String schemaLocation, String baseUri) {
        return new InputSource(SchemaLoader.class.getResource(schemaLocation).toString());
    }

}
