package com.prowidesoftware.swift.io.parser;

import com.prowidesoftware.swift.model.SwiftMessage;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class LargeMt940Test {
    @Test
    public void shouldParseStatement() throws IOException {
        final Scanner scanner = new Scanner(LargeMt940Test.class.getResourceAsStream("/smallStatement.STA"), Charset.defaultCharset()
                .name()).useDelimiter("\\Z");
        final String content = scanner.next();
        long start = System.currentTimeMillis();
        SwiftMessage parse = new SwiftParser(new StringReader(content)).message();
        System.out.println("Elapsed = " + (System.currentTimeMillis() - start));
        assertThat(parse.getBlock4().getTags().size()).isEqualTo(27);
    }

    @Test
    public void shouldParseLargeFile() throws IOException {
        final Scanner scanner = new Scanner(LargeMt940Test.class.getResourceAsStream("/largeStatement.STA"), Charset.defaultCharset()
                .name()).useDelimiter("\\Z");
        final String content = scanner.next();
        long start = System.currentTimeMillis();
        SwiftMessage parse = new SwiftParser(new StringReader(content)).message();
        System.out.println("Elapsed = " + (System.currentTimeMillis() - start));
        assertThat(parse.getBlock4().getTags().size()).isEqualTo(30971);
    }
}
