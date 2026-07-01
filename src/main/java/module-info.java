/*
 * Copyright [2026] [Ahaviss]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

module Banking.App {
    //Data saving
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.bouncycastle.provider;
    //Basic
    requires java.base;
    //Allowing reflection
    exports com.ahaviss.enums to com.fasterxml.jackson.databind;
    exports com.ahaviss.logs.enums to com.fasterxml.jackson.databind;
    opens com.ahaviss.database to com.fasterxml.jackson.core, com.fasterxml.jackson.databind, com.fasterxml.jackson.annotation, org.junit.jupiter;
    opens com.ahaviss.logs.database to com.fasterxml.jackson.core, com.fasterxml.jackson.databind, com.fasterxml.jackson.annotation, org.junit.jupiter;
}