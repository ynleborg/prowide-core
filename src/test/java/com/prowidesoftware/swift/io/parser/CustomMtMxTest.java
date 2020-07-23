package com.prowidesoftware.swift.io.parser;

import com.prowidesoftware.swift.model.MxSwiftMessage;
import com.prowidesoftware.swift.model.SwiftMessage;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class CustomMtMxTest {
    @Test
    public void should_parse_small_statement() throws IOException {
        final String content = getContent("/smallStatement.STA");
        long start = System.currentTimeMillis();
        SwiftMessage parse = new SwiftParser(new StringReader(content)).message();
        System.out.println("Elapsed = " + (System.currentTimeMillis() - start));
        assertThat(parse.getBlock4().getTags().size()).isEqualTo(27);
    }

    @Test
    public void should_parse_large_file() throws IOException {
        final String content = getContent("/largeStatement.STA");
        long start = System.currentTimeMillis();
        SwiftMessage parse = new SwiftParser(new StringReader(content)).message();
        System.out.println("Elapsed = " + (System.currentTimeMillis() - start));
        assertThat(parse.getBlock4().getTags().size()).isEqualTo(30971);
    }

    @Test
    public void should_parse_mt_103() throws IOException {
        final String content = getContent("/example_mt103.txt");
        SwiftMessage parse = new SwiftParser(new StringReader(content)).message();
        assertThat(parse.getBlock4().getTagValue("70")).startsWith(" ROZLICZENIE");
    }

    @Test
    public void should_parse_mx_pain_001_001_07() {
        MxSwiftMessage mx = new MxSwiftMessage(getContent("/example_pain_001_001_07.xml"));
        assertNotNull(mx);
        assertThat(mx.getIdentifier()).isEqualTo("pain.001.001.07");
    }

    private String getContent(String s) {
        final Scanner scanner = new Scanner(CustomMtMxTest.class.getResourceAsStream(s), Charset.defaultCharset()
                .name()).useDelimiter("\\Z");
        return scanner.next();
    }
}
