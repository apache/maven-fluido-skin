/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.skins.fluido;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Properties;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.apache.maven.shared.invoker.SystemOutLogger;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Cannot use maven-invoker-plugin for this test because it needs to be run in a SCM repository
 * and the invoker always clones the project into target (see <a href="https://github.com/apache/maven-invoker-plugin/issues/712">m-invoker-plugin issue #712</a>).
 */
// TODO: Switch to maven-executor once there is some wrapper classes available
// (https://github.com/apache/maven-invoker/issues/164).
class ScmLastModifiedIT {

    @Test
    void testModificationDate() throws MavenInvocationException, AssertionError, IOException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("src/test/resources/modificationDate"));
        configure(request);
        Invoker invoker = new DefaultInvoker();
        request.addArgs(Arrays.asList("clean", "site"));
        invoker.setLogger(new SystemOutLogger());
        InvocationResult result = invoker.execute(request);
        if (result.getExitCode() != 0) {
            throw new IllegalStateException("Build failed.", result.getExecutionException());
        }
        // Doxia source
        assertHtmlContainsDate(new File("src/test/resources/modificationDate/target/site/index.html"), "Last Modified: 2026-03-20");
        // report
        assertHtmlContainsDate(new File("src/test/resources/modificationDate/target/site/plugins.html"), "Last Published: 1990-01-01");
    }

    static void assertHtmlContainsDate(File htmlFile, String expectedDate) throws AssertionError, IOException {
        assertTrue(htmlFile.exists(), "Generated HTML file does not exist: " + htmlFile.getAbsolutePath());
        String relevantLine = Files.readAllLines(htmlFile.toPath()).stream()
                .filter(line -> line.contains("id=\"publishDate\""))
                .findFirst()
                .orElseThrow(() -> new AssertionError("No line containing 'id=\"publishDate\" found in HTML"));
        assertTrue(
                relevantLine.contains(expectedDate),
                "Expected date '" + expectedDate + "' not found in line: " + relevantLine);
    }

    /**
     * Retrieves the version of the fluido-skin. The version is used in the
     * test poms so that the cli build uses the current plugin. Usually the version is set via the system property
     * {@code plugin.version} via the failsafe plugin. If the property is missing the method tries to read it from the
     * {@code pom.xml} of the project. This is useful when running the tests in an IDE.
     *
     * @return the version of the current {@code filevault-package-maven-plugin}
     * @throws IllegalArgumentException if the version cannot be determined.
     */
    public static String getSkinArtifactVersion() {
        String pluginVersion = System.getProperty("skin.version");
        if (pluginVersion == null) {
            try (FileReader fileReader = new FileReader("pom.xml")) {
                // try to read from project
                MavenXpp3Reader reader = new MavenXpp3Reader();
                Model model = reader.read(fileReader);
                pluginVersion = model.getVersion();
            } catch (IOException | XmlPullParserException e) {
                System.err.println("Unable to read skin version from pom.xml" + e.getMessage());
            }
        }
        if (pluginVersion == null) {
            throw new IllegalArgumentException("Unable to detect skin version");
        }
        return pluginVersion;
    }

    private static void configure(InvocationRequest request) {
        Properties properties = new Properties();
        properties.setProperty("skinVersion", getSkinArtifactVersion());
        request.setProperties(properties);
    }
}
