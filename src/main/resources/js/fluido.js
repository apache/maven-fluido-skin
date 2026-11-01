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

    // label each cell with its column heading, for the stacked tables on narrow screens (maven-base.css);
    // only tables whose first row is a plain heading row, and only rows with one cell per heading
    $( '.table-wrap > table' ).each( function() {
        var rows = $( this ).find( '> thead > tr, > tbody > tr' );
        var head = rows.first();
        if ( head.children( 'td' ).length || !head.children( 'th' ).length || head.children( '[rowspan], [colspan]' ).length ) {
            return;
        }
        var labels = head.children( 'th' ).map( function() { return $.trim( $( this ).text() ); } ).get();
        head.addClass( 'table-head' );
        rows.slice( 1 ).each( function() {
            var cells = $( this ).children();
            if ( cells.length === labels.length ) {
                cells.each( function( i ) { $( this ).attr( 'data-label', labels[ i ] ); } );
            }
        } );
    } );

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