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

jQuery( document ).ready( function()
{
    // initialize the dropdown menu if found
    $( '.topbar' ).dropdown();

    // highlight the code blocks that site.vm marked; line numbers where sourceLineNumbersEnabled added linenums
    if ( window.hljs ) {
        $( 'pre.prettyprint > code' ).each( function() {
            // a language highlight.js does not know (Markdown ```unknown) is detected instead, as prettify did
            var lang = ( this.className.match( /\blanguage-(\S+)/ ) || [] )[ 1 ];
            if ( lang && !hljs.getLanguage( lang ) ) {
                $( this ).removeClass( 'language-' + lang );
            }
            hljs.highlightElement( this );
            if ( hljs.lineNumbersBlock && $( this ).parent().hasClass( 'linenums' ) ) {
                hljs.lineNumbersBlock( this, { singleLine: true } );
            }
        } );
    }

} );

function asyncJs( url ) {
    ( function() {
            var s = document.createElement( 'script' ), t = document.getElementsByTagName( 'script' )[0];
            s.type = 'text/javascript';
            s.async = true;
            s.src = url;
            t.parentNode.insertBefore( s, t );
        } )();
}