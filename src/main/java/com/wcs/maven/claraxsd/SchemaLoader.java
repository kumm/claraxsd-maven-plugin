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
package com.wcs.maven.claraxsd;

import com.wcs.maven.claraxsd.NamingRules.FixedName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.resolver.URIResolver;
import org.xml.sax.InputSource;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringReader;

/**
 *
 * @author kumm
 */
public class SchemaLoader {

    private final XmlSchemaCollection schemaCol;

    public SchemaLoader() {
        schemaCol = new XmlSchemaCollection();
        schemaCol.setSchemaResolver(new URIResolver() {

            @Override
            public InputSource resolveEntity(String targetNamespace, String schemaLocation, String baseUri) {
                for (FixedName fixed : FixedName.values()) {
                    if (fixed.getSystemId().equals(schemaLocation)) {
                        return new InputSource(getClass().getResourceAsStream(fixed.getFileName()));
                    }
                }
                return null;
            }
        });
    }

    public XmlSchema load(String markup) {
        return schemaCol.read(new StringReader(markup), null);
    }

    public XmlSchema load(InputStream xsdStream) {
        return schemaCol.read(new StreamSource(xsdStream), null);
    }

}
