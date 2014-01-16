/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.command.runtime.rule;

import org.drools.command.Context;
import org.drools.command.impl.GenericCommand;
import org.drools.command.impl.KnowledgeCommandContext;
import org.drools.common.DisconnectedFactHandle;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class RetractFromEntryPointCommand
implements
GenericCommand<Object> {

    @XmlAttribute(name="entry-point")
    private String entryPoint;

    private DisconnectedFactHandle handle;

    public RetractFromEntryPointCommand() {
    }

    public RetractFromEntryPointCommand(FactHandle handle, String entryPoint) {
        this.handle = DisconnectedFactHandle.newFrom( handle );
        this.entryPoint = entryPoint;
    }

    public Object execute(Context context) {
        StatefulKnowledgeSession ksession = ((KnowledgeCommandContext) context).getStatefulKnowledgesession();
        ksession.getWorkingMemoryEntryPoint( entryPoint ).retract( handle );
        return null;
    }

    public FactHandle getFactHandle() {
        return this.handle;
    }

    @XmlAttribute(name="fact-handle", required=true)
    public void setFactHandleFromString(String factHandleId) {
        handle = new DisconnectedFactHandle(factHandleId);
    }

    public String getFactHandleFromString() {
        return handle.toExternalForm();
    }

    public String toString() {
        return "session.getWorkingMemoryEntryPoint(" + entryPoint + ").retract( " + handle + " );";
    }
}
