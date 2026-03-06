/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

File index = new File( basedir, "target/site/index.html" )
assert index.exists()
// mermaid JS should be loaded
assert index.text.contains( 'mermaid-' )
assert index.text.contains( '.min.js' )
// mermaid initialization script should be present
assert index.text.contains( 'mermaid.initialize' )
assert index.text.contains( 'mermaid.run' )
// language-mermaid code block should NOT have prettyprint class
assert !index.text.contains( 'prettyprint"><code class="language-mermaid"' )
// language-mermaid code block should be present without prettyprint
assert index.text.contains( '<code class="language-mermaid"' )
